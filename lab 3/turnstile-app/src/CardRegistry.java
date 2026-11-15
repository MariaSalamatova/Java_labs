import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** System that keeps the registry of issued cards and issues new ones. */
public class CardRegistry {
    private final Map<String, Card> issued = new LinkedHashMap<>();
    private int counter = 0;

    private String nextId() {
        return String.format("TR-%06d", ++counter);
    }

    private <T extends Card> T register(T card) {
        issued.put(card.getId(), card);
        return card;
    }

    /** Issues a monthly or 10-day card. */
    public TermCard issueTermCard(CardCategory category, CardType type, LocalDate issueDate) {
        return register(new TermCard(nextId(), category, type, issueDate));
    }

    /** Issues a 5-trip or 10-trip card. */
    public TripCard issueTripCard(CardCategory category, CardType type) {
        return register(new TripCard(nextId(), category, type));
    }

    /** Issues an accumulative card (regular category only). */
    public AccumulativeCard issueAccumulativeCard(CardCategory category, BigDecimal initialBalance) {
        return register(new AccumulativeCard(nextId(), category, initialBalance));
    }

    public Optional<Card> find(String id) {
        return Optional.ofNullable(issued.get(id));
    }

    public int size() {
        return issued.size();
    }

    public Iterable<Card> all() {
        return issued.values();
    }
}
