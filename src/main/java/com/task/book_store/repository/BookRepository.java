package com.task.book_store.repository;

import com.task.book_store.model.db.DbBook;
import com.task.book_store.model.shared.BookTypeEnum;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<DbBook, Long> {

  List<DbBook> findByUuidIn(List<UUID> bookUuids);

  Optional<DbBook> findByUuid(UUID uuid);

  boolean existsByNameAndBookTypeType(String name, BookTypeEnum type);


}
