package com.task.book_store.service;

import com.task.book_store.exception.BadRequestException;
import com.task.book_store.exception.BookAlreadyExistsException;
import com.task.book_store.exception.BookNotFoundException;
import com.task.book_store.model.db.DbBook;
import com.task.book_store.model.db.DbBookType;
import com.task.book_store.model.rest.request.ApiBookRequest;
import com.task.book_store.model.service.Book;
import com.task.book_store.repository.BookRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public record BookService(
    BookRepository bookRepository,
    BookTypeService bookTypeService
) {

  public List<Book> getAllBooks() {
    return Book.from(bookRepository.findAll());
  }

  public Book getBookByUuid(UUID uuid) {
    return Book.from(
        bookRepository
            .findByUuid(uuid)
            .orElseThrow(BookNotFoundException::new)
    );
  }

  public List<DbBook> getDbBooks(List<UUID> bookUuids) {
    return bookRepository.findByUuidIn(bookUuids);
  }

  public Book saveBook(ApiBookRequest request) {

    validateRequest(request);

    DbBookType dbBookType = bookTypeService().getBookType(request.bookTypeEnum());

    if (bookRepository.existsByNameAndBookTypeType(request.name(), request.bookTypeEnum())) {
      throw new BookAlreadyExistsException();
    }

    return Book.from(
        bookRepository.save(
            new DbBook()
                .setUuid(UUID.randomUUID())
                .setName(request.name())
                .setQuantity(request.quantity())
                .setBookType(dbBookType)
                .setPrice(request.price())
        )
    );
  }

  private void validateRequest(ApiBookRequest request) {
    if (request.name() == null || request.name().isEmpty()) {
      throw new BadRequestException("Book name cannot be empty");
    }

    if (request.quantity() < 1) {
      throw new BadRequestException("Quantity must be 1 or greater");
    }

    if (request.price() < 0) {
      throw new BadRequestException("Price invalid");
    }
  }
}
