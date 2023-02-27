package com.task.book_store.model.db;

import com.task.book_store.model.shared.BookTypeEnum;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "purchased_books")
public class DbPurchasedBook {

  private static final String SEQUENCE_NAME = "purchased_book_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100)
  private Long id;

  @NotNull
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  private DbPurchase purchase;

  @NotNull
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  private DbBook book;

  @NotNull
  private BookTypeEnum type;

  @NotNull
  private Integer quantity;

  @NotNull
  private Double singleCopyOriginalPrice;

  @NotNull
  private Double singleCopyFinalPrice;

  public static DbPurchasedBook from(DbBook dbBook, Integer purchaseQuantity) {
    return new DbPurchasedBook()
        .setBook(dbBook.setQuantity(dbBook.getQuantity() - purchaseQuantity))
        .setType(dbBook.getBookType().getType())
        .setSingleCopyOriginalPrice(dbBook.getPrice())
        .setQuantity(purchaseQuantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DbPurchasedBook that = (DbPurchasedBook) o;

    return Objects.equals(book, that.book) &&
        Objects.equals(purchase, that.purchase) &&
        type == that.type &&
        Double.compare(that.singleCopyOriginalPrice, singleCopyOriginalPrice) == 0 &&
        Double.compare(that.singleCopyFinalPrice, singleCopyFinalPrice) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(book, purchase, type, singleCopyOriginalPrice, singleCopyFinalPrice);
  }
}
