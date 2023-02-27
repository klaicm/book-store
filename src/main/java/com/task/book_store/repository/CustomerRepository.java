package com.task.book_store.repository;

import com.task.book_store.model.db.DbCustomer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<DbCustomer, Long> {

  Optional<DbCustomer> findByUuid(UUID uuid);

  List<DbCustomer> findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String searchTerm1, String searchTerm2);

}
