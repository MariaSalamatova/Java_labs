import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Model of the turnstile processor: reads a card, validates it, deducts a trip
 * and keeps statistics of allowed and denied passages (total and by card type).
 */
public class Turnstile {
    private static final String UNKNOWN = "Unreadable / unknown card";

    private final CardRegistry registry;
    private final BigDecimal fare;
    private final Clock clock;

    private int totalAllowed;
    private int totalDenied;
    // card type -> {allowed, denied}
    private final Map<String, int[]> byType = new TreeMap<>();

    public Turnstile(CardRegistry registry, BigDecimal fare, Clock clock) {
        this.registry = registry;
        this.fare = fare;
        this.clock = clock;
    }

    /** Attempt to pass with a physically presented card (it may be damaged). */
    public PassResult pass(Card card) {
        if (card == null || card.isDamaged()) {
            return record(UNKNOWN, false, "Card data could not be read");
        }
        if (registry.find(card.getId()).isEmpty()) {
            return record(UNKNOWN, false, "Card " + card.getId() + " is not in the registry");
        }

        Optional<String> denial = card.validate(LocalDate.now(clock), fare);
        if (denial.isPresent()) {
            return record(card.describe(), false, denial.get());
        }
        card.consume(fare);
        return record(card.describe(), true, "Card " + card.getId() + " (" + card.state() + ")");
    }

    private PassResult record(String typeKey, boolean allowed, String message) {
        int[] counters = byType.computeIfAbsent(typeKey, k -> new int[2]);
        if (allowed) {
            totalAllowed++;
            counters[0]++;
        } else {
            totalDenied++;
            counters[1]++;
        }
        return new PassResult(allowed, message);
    }

    /** Report 1: summary totals. */
    public String totalReport() {
        return String.format("Total attempts: %d, allowed: %d, denied: %d",
                totalAllowed + totalDenied, totalAllowed, totalDenied);
    }

    /** Report 2: data broken down by travel card type. */
    public String byTypeReport() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, int[]> e : byType.entrySet()) {
            sb.append(String.format("%-30s allowed: %d, denied: %d%n",
                    e.getKey(), e.getValue()[0], e.getValue()[1]));
        }
        return sb.toString();
    }
}
