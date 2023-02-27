package com.task.book_store.service;

import com.task.book_store.exception.BadRequestException;
import com.task.book_store.exception.BookNotFoundException;
import com.task.book_store.exception.BookTypeNotFoundException;
import com.task.book_store.exception.NotEnoughBooksException;
import com.task.book_store.model.db.DbBook;
import com.task.book_store.model.db.DbCustomer;
import com.task.book_store.model.db.DbPurchase;
import com.task.book_store.model.db.DbPurchasedBook;
import com.task.book_store.model.rest.request.ApiPurchaseRequest;
import com.task.book_store.model.rest.request.ApiPurchasedBookRequest;
import com.task.book_store.model.service.BookType;
import com.task.book_store.model.service.Purchase;
import com.task.book_store.model.shared.BookTypeEnum;
import com.task.book_store.repository.PurchaseRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public record PurchaseService(
    PurchaseRepository purchaseRepository,
    BookService bookService,
    CustomerService customerService,
    BookTypeService bookTypeService
) {

  private static final Double ADDITIONAL_DISCOUNT = 0.05;
  private static final int DISCOUNT_QUANTITY = 3;

  public List<Purchase> getPurchases() {
    return Purchase.from(purchaseRepository.findAll());
  }

  public Purchase purchase(ApiPurchaseRequest request) {

    validateRequest(request);

    List<DbBook> dbBooks = bookService.getDbBooks(
        request.purchasedBooks().stream().map(ApiPurchasedBookRequest::uuid).toList()
    );

    DbCustomer dbCustomer = customerService.getDbCustomer(request.customer().uuid());

    List<BookType> bookTypes = bookTypeService.getBookTypes();

    if (request.purchasedBooks().size() != dbBooks.size()) {
      List<UUID> distinctBookUuids = Stream.concat(request.purchasedBooks().stream().map(ApiPurchasedBookRequest::uuid), dbBooks.stream().map(DbBook::getUuid))
          .distinct()
          .toList();

      throw new BookNotFoundException("Books not found: " + distinctBookUuids.stream().map(UUID::toString).collect(Collectors.joining(", ")));
    }

    DbPurchase dbPurchase = new DbPurchase()
        .setUuid(UUID.randomUUID());

    Set<DbPurchasedBook> purchasedBooks = request.purchasedBooks().stream()
        .map(pb -> findBook(pb, dbBooks, dbPurchase))
        .collect(Collectors.toSet());

    val regularQuantity = purchasedBooks.stream().filter(pb -> pb.getType().equals(BookTypeEnum.REGULAR)).mapToInt(DbPurchasedBook::getQuantity).sum();
    val oldEditionQuantity = purchasedBooks.stream().filter(pb -> pb.getType().equals(BookTypeEnum.OLD_EDITION)).mapToInt(DbPurchasedBook::getQuantity).sum();

    purchasedBooks = purchasedBooks.stream()
        .map(pb -> calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes))
        .collect(Collectors.toSet());

    Set<DbPurchasedBook> mappedPurchasedBooks = applyLoyaltyProgram(purchasedBooks, dbCustomer);

    dbPurchase
        .setPurchasedBooks(mappedPurchasedBooks)
        .setCustomer(dbCustomer);

    return Purchase.from(purchaseRepository.save(dbPurchase));
  }

  private DbPurchasedBook findBook(ApiPurchasedBookRequest pb, List<DbBook> dbBooks, DbPurchase dbPurchase) {
    return dbBooks.stream()
        .filter(dbBook -> dbBook.getUuid().equals(pb.uuid()))
        .findFirst()
        .map(book -> {
              if (book.getQuantity() < pb.quantity()) {
                throw new NotEnoughBooksException(
                    "Not enough books. Book UUID: " + book.getUuid() + ", Book name: " + book.getName() + ", required: " + pb.quantity() +
                        "available: " + book.getQuantity());
              }

              return DbPurchasedBook.from(book, pb.quantity());
            }
        )
        .orElseThrow(BookNotFoundException::new)
        .setPurchase(dbPurchase);
  }

  public DbPurchasedBook calculateFinalPrice(DbPurchasedBook pb, int regularQuantity, int oldEditionQuantity, List<BookType> bookTypes) {
    BookTypeEnum pbType = pb.getType();

    Double typeDiscount = bookTypes.stream()
        .filter(bookType -> bookType.type() == pbType)
        .findFirst()
        .orElseThrow(BookTypeNotFoundException::new)
        .discount();

    pb = pb.setSingleCopyFinalPrice(pb.getSingleCopyOriginalPrice());

    if (pbType == BookTypeEnum.REGULAR && regularQuantity >= DISCOUNT_QUANTITY) {
      pb.setSingleCopyFinalPrice((pb.getSingleCopyOriginalPrice() - (pb.getSingleCopyOriginalPrice() * typeDiscount)));
    } else if (pbType == BookTypeEnum.OLD_EDITION) {
      pb.setSingleCopyFinalPrice(pb.getSingleCopyOriginalPrice() - (pb.getSingleCopyOriginalPrice() * typeDiscount));
      if (oldEditionQuantity >= DISCOUNT_QUANTITY) {
        pb.setSingleCopyFinalPrice(pb.getSingleCopyFinalPrice() - (pb.getSingleCopyFinalPrice() * ADDITIONAL_DISCOUNT));
      }
    }

    return pb;
  }

  public Set<DbPurchasedBook> applyLoyaltyProgram(Set<DbPurchasedBook> purchasedBooks, DbCustomer dbCustomer) {
    int totalBooksQuantity = purchasedBooks.stream().mapToInt(DbPurchasedBook::getQuantity).sum();
    int customerLoyaltyPoints = dbCustomer.getLoyaltyPoints() + totalBooksQuantity;
    int freeBooks = customerLoyaltyPoints / 10;

    if (freeBooks > 0) {
      List<DbPurchasedBook> sortedPurchasedBooks = new ArrayList<>(purchasedBooks);
      sortedPurchasedBooks.sort(Comparator.comparingDouble(DbPurchasedBook::getSingleCopyFinalPrice));

      int remainingFreeBooks = freeBooks;
      Set<DbPurchasedBook> freeBooksSet = new HashSet<>();
      Set<DbPurchasedBook> booksForRemoveSet = new HashSet<>();
      for (DbPurchasedBook pb : sortedPurchasedBooks) {
        if ((pb.getType() == BookTypeEnum.REGULAR || pb.getType() == BookTypeEnum.OLD_EDITION) && remainingFreeBooks > 0) {
          int freeCopies = Math.min(remainingFreeBooks, pb.getQuantity());
          int remainingCopies = pb.getQuantity() - freeCopies;
          remainingFreeBooks -= freeCopies;

          if (freeCopies > 0) {
            freeBooksSet.add(new DbPurchasedBook()
                .setBook(pb.getBook())
                .setPurchase(pb.getPurchase())
                .setQuantity(freeCopies)
                .setType(pb.getType())
                .setSingleCopyOriginalPrice(pb.getSingleCopyOriginalPrice())
                .setSingleCopyFinalPrice(0.0));
          }

          if (remainingCopies > 0) {
            pb.setQuantity(remainingCopies);
          } else {
            booksForRemoveSet.add(pb);
          }
        }
      }

      purchasedBooks.removeAll(booksForRemoveSet);
      purchasedBooks.addAll(freeBooksSet);
    }

    int restOfLoyaltyPoints = customerLoyaltyPoints % 10;
    dbCustomer.setLoyaltyPoints(restOfLoyaltyPoints);

    return purchasedBooks;
  }

  private void validateRequest(ApiPurchaseRequest request) {
    if (request.customer() == null) {
      throw new BadRequestException("Customer cannot be null");
    }

    if (request.customer().uuid() == null || request.customer().uuid().toString().isEmpty()) {
      throw new BadRequestException("Customer UUID cannot be null or empty");
    }

    if (request.purchasedBooks().isEmpty()) {
      throw new BadRequestException("List of books is empty");
    }

    if (request.purchasedBooks().stream().anyMatch(book -> book.uuid() == null || book.uuid().toString().isEmpty())) {
      throw new BadRequestException("Book UUID cannot be null or empty");
    }

    if (request.purchasedBooks().stream().anyMatch(book -> book.quantity() < 1)) {
      throw new BadRequestException("Book quantity must be 1 or greater");
    }
  }
}
