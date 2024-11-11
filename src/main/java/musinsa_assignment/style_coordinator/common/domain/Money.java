package musinsa_assignment.style_coordinator.common.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class Money {

  private BigDecimal value;

  public BigDecimal getValue() {
    return value;
  }

  public boolean isLessThan(Money other) {
    return this.value.compareTo(other.value) < 0;
  }

  public boolean isGreaterThan(Money other) {
    return this.value.compareTo(other.value) > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Money that)) {
      return false;
    }
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
