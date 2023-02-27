package com.task.book_store.model.db;

import com.task.book_store.util.UUIDConverter;
import java.time.OffsetDateTime;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "purchases")
public class DbPurchase {

  private static final String SEQUENCE_NAME = "purchase_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100)
  private Long id;

  @Column(unique = true, nullable = false)
  @Convert(converter = UUIDConverter.class)
  private UUID uuid;

  @CreationTimestamp
  private OffsetDateTime createdAt;

  @NotNull
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  private DbCustomer customer;

  @ToString.Exclude
  @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<DbPurchasedBook> purchasedBooks = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DbPurchase that = (DbPurchase) o;

    return new EqualsBuilder().append(id, that.id).append(uuid, that.uuid).append(createdAt, that.createdAt)
        .append(customer, that.customer).append(purchasedBooks, that.purchasedBooks).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(uuid).append(createdAt).append(customer).toHashCode();
  }
}
