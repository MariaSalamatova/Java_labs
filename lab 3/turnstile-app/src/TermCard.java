import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/** Card limited by validity period (monthly or 10 days), unlimited trips. */
public class TermCard extends Card {
    private final LocalDate validUntil;

    public TermCard(String id, CardCategory category, CardType type, LocalDate issueDate) {
        super(id, category, type);
        if (null == type) {
            throw new IllegalArgumentException("Invalid term card type: " + type);
        } else switch (type) {
            case MONTHLY:
                this.validUntil = issueDate.plusMonths(1);
                break;
            case TEN_DAYS:
                this.validUntil = issueDate.plusDays(10);
                break;
            default:
                throw new IllegalArgumentException("Invalid term card type: " + type);
        }
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    @Override
    public Optional<String> validate(LocalDate today, BigDecimal fare) {
        if (today.isAfter(validUntil)) {
            return Optional.of("Card expired on " + validUntil);
        }
        return Optional.empty();
    }

    @Override
    public void consume(BigDecimal fare) {
        // Trips on a term card are unlimited - nothing to deduct.
    }

    @Override
    public String state() {
        return "valid until " + validUntil;
    }
}
