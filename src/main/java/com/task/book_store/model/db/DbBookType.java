package com.task.book_store.model.db;

import com.task.book_store.model.shared.BookTypeEnum;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "book_types")
public class DbBookType {

  private static final String SEQUENCE_NAME = "book_type_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100)
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  private BookTypeEnum type;

  private Double discount;

  @ToString.Exclude
  @OneToMany(mappedBy = "bookType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<DbBook> books = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DbBookType that = (DbBookType) o;

    return new EqualsBuilder().append(id, that.id).append(type, that.type).append(discount, that.discount)
        .append(books, that.books).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(type).append(discount).toHashCode();
  }
}
