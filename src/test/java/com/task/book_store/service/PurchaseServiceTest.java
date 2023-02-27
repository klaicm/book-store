package com.task.book_store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.task.book_store.model.db.DbBook;
import com.task.book_store.model.db.DbBookType;
import com.task.book_store.model.db.DbCustomer;
import com.task.book_store.model.db.DbPurchasedBook;
import com.task.book_store.model.service.BookType;
import com.task.book_store.model.shared.BookTypeEnum;
import com.task.book_store.repository.PurchaseRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

  @Mock
  private PurchaseRepository purchaseRepository;

  @Mock
  private BookService bookService;

  @Mock
  private CustomerService customerService;

  @Mock
  private BookTypeService bookTypeService;

  @InjectMocks
  private PurchaseService purchaseService;

  @Test
  public void testCalculateFinalPriceNewRelease() {
    DbPurchasedBook pb = new DbPurchasedBook()
        .setType(BookTypeEnum.NEW_RELEASE)
        .setSingleCopyOriginalPrice(10.0);
    int regularQuantity = 0;
    int oldEditionQuantity = 0;
    List<BookType> bookTypes = List.of(
        new BookType(BookTypeEnum.NEW_RELEASE, 0.0),
        new BookType(BookTypeEnum.NEW_RELEASE, 0.1),
        new BookType(BookTypeEnum.NEW_RELEASE, 0.2)
    );

    DbPurchasedBook purchasedBook = purchaseService.calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes);

    assertEquals(pb, purchasedBook);
    assertEquals(10.0, purchasedBook.getSingleCopyFinalPrice(), 0.001);
  }

  @Test
  public void testCalculateFinalPriceRegularReleaseNoDiscount() {
    DbPurchasedBook pb = new DbPurchasedBook()
        .setType(BookTypeEnum.REGULAR)
        .setSingleCopyOriginalPrice(10.0);
    int regularQuantity = 2;
    int oldEditionQuantity = 2;
    List<BookType> bookTypes = List.of(
        new BookType(BookTypeEnum.NEW_RELEASE, 0.0),
        new BookType(BookTypeEnum.REGULAR, 0.1),
        new BookType(BookTypeEnum.OLD_EDITION, 0.2)
    );

    DbPurchasedBook purchasedBook = purchaseService.calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes);

    assertEquals(pb, purchasedBook);
    assertEquals(10.0, purchasedBook.getSingleCopyFinalPrice(), 0.001);
  }

  @Test
  public void testCalculateFinalPriceRegularReleaseWithDiscount() {
    DbPurchasedBook pb = new DbPurchasedBook()
        .setType(BookTypeEnum.REGULAR)
        .setSingleCopyOriginalPrice(10.0);
    int regularQuantity = 5;
    int oldEditionQuantity = 5;
    List<BookType> bookTypes = List.of(
        new BookType(BookTypeEnum.NEW_RELEASE, 0.0),
        new BookType(BookTypeEnum.REGULAR, 0.1),
        new BookType(BookTypeEnum.OLD_EDITION, 0.2)
    );

    DbPurchasedBook purchasedBook = purchaseService.calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes);

    assertEquals(pb, purchasedBook);
    assertEquals(9.0, purchasedBook.getSingleCopyFinalPrice(), 0.001);
  }

  @Test
  public void testCalculateFinalPriceOldReleaseRegularDiscount() {
    DbPurchasedBook pb = new DbPurchasedBook()
        .setType(BookTypeEnum.OLD_EDITION)
        .setSingleCopyOriginalPrice(10.0);
    int regularQuantity = 2;
    int oldEditionQuantity = 2;
    List<BookType> bookTypes = List.of(
        new BookType(BookTypeEnum.NEW_RELEASE, 0.0),
        new BookType(BookTypeEnum.REGULAR, 0.1),
        new BookType(BookTypeEnum.OLD_EDITION, 0.2)
    );

    DbPurchasedBook purchasedBook = purchaseService.calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes);

    assertEquals(pb, purchasedBook);
    assertEquals(8.0, purchasedBook.getSingleCopyFinalPrice(), 0.001);
  }

  @Test
  public void testCalculateFinalPriceOldReleaseAdditionalDiscount() {
    DbPurchasedBook pb = new DbPurchasedBook()
        .setType(BookTypeEnum.OLD_EDITION)
        .setSingleCopyOriginalPrice(10.0);
    int regularQuantity = 5;
    int oldEditionQuantity = 5;
    List<BookType> bookTypes = List.of(
        new BookType(BookTypeEnum.NEW_RELEASE, 0.0),
        new BookType(BookTypeEnum.REGULAR, 0.1),
        new BookType(BookTypeEnum.OLD_EDITION, 0.2)
    );

    DbPurchasedBook purchasedBook = purchaseService.calculateFinalPrice(pb, regularQuantity, oldEditionQuantity, bookTypes);

    assertEquals(pb, purchasedBook);
    assertEquals(7.6, purchasedBook.getSingleCopyFinalPrice(), 0.001);
  }

  @Test
  public void testApplyLoyaltyProgram() {
    DbBookType regularBookType = new DbBookType().setType(BookTypeEnum.REGULAR).setDiscount(0.1);
    DbBookType oldEditionBookType = new DbBookType().setType(BookTypeEnum.OLD_EDITION).setDiscount(0.1);

    DbBook book1 = new DbBook().setUuid(UUID.randomUUID()).setName("Book A").setBookType(regularBookType);

    DbPurchasedBook purchasedBook1 = new DbPurchasedBook()
        .setBook(book1)
        .setQuantity(3)
        .setType(BookTypeEnum.REGULAR)
        .setSingleCopyOriginalPrice(10.0)
        .setSingleCopyFinalPrice(10.0);

    DbBook book2 = new DbBook().setUuid(UUID.randomUUID()).setName("Book B").setBookType(oldEditionBookType);

    DbPurchasedBook purchasedBook2 = new DbPurchasedBook()
        .setBook(book2)
        .setQuantity(5)
        .setType(BookTypeEnum.OLD_EDITION)
        .setSingleCopyOriginalPrice(15.0)
        .setSingleCopyFinalPrice(15.0);

    DbCustomer dbCustomer = new DbCustomer()
        .setUuid(UUID.randomUUID())
        .setFirstName("Ivan")
        .setLastName("Ivic")
        .setLoyaltyPoints(7);

    Set<DbPurchasedBook> purchasedBooks = new HashSet<>();
    purchasedBooks.add(purchasedBook1);
    purchasedBooks.add(purchasedBook2);

    Set<DbPurchasedBook> testResult = purchaseService.applyLoyaltyProgram(purchasedBooks, dbCustomer);

    Set<DbPurchasedBook> expected = new HashSet<>();

    expected.add(new DbPurchasedBook()
        .setBook(book1)
        .setQuantity(2)
        .setType(BookTypeEnum.REGULAR)
        .setSingleCopyOriginalPrice(10.0)
        .setSingleCopyFinalPrice(10.0));

    expected.add(new DbPurchasedBook()
        .setBook(book2)
        .setQuantity(5)
        .setType(BookTypeEnum.OLD_EDITION)
        .setSingleCopyOriginalPrice(15.0)
        .setSingleCopyFinalPrice(15.0));

    expected.add(new DbPurchasedBook()
        .setBook(book1)
        .setPurchase(purchasedBook1.getPurchase())
        .setQuantity(1)
        .setType(BookTypeEnum.REGULAR)
        .setSingleCopyOriginalPrice(10.0)
        .setSingleCopyFinalPrice(0.0));

    System.out.println(expected);
    System.out.println(testResult);

    assertEquals(expected, testResult);

    assertEquals(5, dbCustomer.getLoyaltyPoints());
  }

}
