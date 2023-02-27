package com.task.book_store.model.db;

import com.task.book_store.util.UUIDConverter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "books",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "book_type_id"})}
)
public class DbBook {

  private static final String SEQUENCE_NAME = "book_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100)
  private Long id;

  @Column(unique = true, nullable = false)
  @Convert(converter = UUIDConverter.class)
  private UUID uuid;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  private Double price;

  @NotNull
  private Integer quantity;

  @NotNull
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  private DbBookType bookType;

  @ToString.Exclude
  @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<DbPurchasedBook> bookPurchases = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DbBook dbBook = (DbBook) o;

    return new EqualsBuilder().append(id, dbBook.id).append(uuid, dbBook.uuid).append(name, dbBook.name)
        .append(price, dbBook.price).append(quantity, dbBook.quantity).append(bookType, dbBook.bookType).append(bookPurchases, dbBook.bookPurchases).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(uuid).append(name).append(price).append(quantity).append(bookType).toHashCode();
  }
}
