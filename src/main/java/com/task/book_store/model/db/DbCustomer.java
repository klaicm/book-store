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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@Table(name = "customers")
public class DbCustomer {

  private static final String SEQUENCE_NAME = "customer_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100)
  private Long id;

  @Column(unique = true, nullable = false)
  @Convert(converter = UUIDConverter.class)
  private UUID uuid;

  @NotNull
  @NotBlank
  private String firstName;

  @NotNull
  @NotBlank
  private String lastName;

  @NotNull
  private Integer loyaltyPoints;

  @ToString.Exclude
  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<DbPurchase> purchases = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DbCustomer that = (DbCustomer) o;

    return new EqualsBuilder().append(id, that.id).append(uuid, that.uuid).append(firstName, that.firstName)
        .append(lastName, that.lastName).append(loyaltyPoints, that.loyaltyPoints).append(purchases, that.purchases).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(uuid).append(firstName).append(lastName).append(loyaltyPoints).toHashCode();
  }
}
