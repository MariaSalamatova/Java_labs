import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Travel card. All card data (id, type, validity, trips, balance) is stored
 * on the card itself; the turnstile reads and updates it.
 */
public abstract class Card {
    private final String id;
    private final CardCategory category;
    private final CardType type;
    private boolean damaged; // simulates a card whose data cannot be read

    protected Card(String id, CardCategory category, CardType type) {
        this.id = id;
        this.category = category;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public CardCategory getCategory() {
        return category;
    }

    public CardType getType() {
        return type;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    /** Name used for statistics by type, e.g. "Student, Monthly". */
    public String describe() {
        return category.getLabel() + ", " + type.getLabel();
    }

    /** Returns the denial reason, or empty if the passage is allowed. */
    public abstract Optional<String> validate(LocalDate today, BigDecimal fare);

    /** Deducts one trip / the fare. Called only after a successful validation. */
    public abstract void consume(BigDecimal fare);

    /** Short description of the current card state. */
    public abstract String state();

    @Override
    public String toString() {
        return id + " (" + describe() + "; " + state() + ")";
    }
}
