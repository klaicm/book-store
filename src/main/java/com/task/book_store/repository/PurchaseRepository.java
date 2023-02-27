package com.task.book_store.repository;

import com.task.book_store.model.db.DbPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<DbPurchase, Long> {

}
