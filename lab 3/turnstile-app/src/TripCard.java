import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/** Card limited by number of trips (5 or 10), with no expiration date. */
public class TripCard extends Card {
    private int tripsLeft;

    public TripCard(String id, CardCategory category, CardType type) {
        super(id, category, type);
        if (null == type) {
            throw new IllegalArgumentException("Invalid trip card type: " + type);
        } else switch (type) {
            case TRIPS_5:
                this.tripsLeft = 5;
                break;
            case TRIPS_10:
                this.tripsLeft = 10;
                break;
            default:
                throw new IllegalArgumentException("Invalid trip card type: " + type);
        }
    }

    public int getTripsLeft() {
        return tripsLeft;
    }

    @Override
    public Optional<String> validate(LocalDate today, BigDecimal fare) {
        if (tripsLeft <= 0) {
            return Optional.of("No trips left on the card");
        }
        return Optional.empty();
    }

    @Override
    public void consume(BigDecimal fare) {
        tripsLeft--;
    }

    @Override
    public String state() {
        return "trips left: " + tripsLeft;
    }
}
