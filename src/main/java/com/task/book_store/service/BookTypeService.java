package com.task.book_store.service;

import com.task.book_store.exception.BookTypeNotFoundException;
import com.task.book_store.model.db.DbBookType;
import com.task.book_store.model.service.BookType;
import com.task.book_store.model.shared.BookTypeEnum;
import com.task.book_store.repository.BookTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public record BookTypeService(
    BookTypeRepository bookTypeRepository
) {

  public List<BookType> getBookTypes() {
    return BookType.from(bookTypeRepository.findAll());
  }

  public DbBookType getBookType(BookTypeEnum bookTypeEnum) {
    return bookTypeRepository
        .findByType(bookTypeEnum)
        .orElseThrow(BookTypeNotFoundException::new);
  }

}
