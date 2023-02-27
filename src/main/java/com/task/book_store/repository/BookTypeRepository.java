package com.task.book_store.repository;

import com.task.book_store.model.db.DbBookType;
import com.task.book_store.model.shared.BookTypeEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTypeRepository extends JpaRepository<DbBookType, Long> {

  Optional<DbBookType> findByType(BookTypeEnum type);
}
