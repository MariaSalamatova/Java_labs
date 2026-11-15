import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Accumulative card: can be topped up; the fare is deducted on every trip.
 * No expiration date. Only the "regular" category is allowed.
 */
public class AccumulativeCard extends Card {
    private BigDecimal balance;

    public AccumulativeCard(String id, CardCategory category, BigDecimal initialBalance) {
        super(id, category, CardType.ACCUMULATIVE);
        if (category != CardCategory.REGULAR) {
            throw new IllegalArgumentException(
                    "An accumulative card can only be regular (not school or student)");
        }
        if (initialBalance.signum() < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void topUp(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Top-up amount must be positive");
        }
        balance = balance.add(amount);
    }

    @Override
    public Optional<String> validate(LocalDate today, BigDecimal fare) {
        if (balance.compareTo(fare) < 0) {
            return Optional.of("Insufficient funds: balance " + balance + ", fare " + fare);
        }
        return Optional.empty();
    }

    @Override
    public void consume(BigDecimal fare) {
        balance = balance.subtract(fare);
    }

    @Override
    public String state() {
        return "balance " + balance;
    }
}
