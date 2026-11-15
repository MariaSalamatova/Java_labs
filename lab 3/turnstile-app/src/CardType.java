/** Card kind: by validity period, by number of trips, or accumulative. */
public enum CardType {
    MONTHLY("Monthly"),
    TEN_DAYS("10 days"),
    TRIPS_5("5 trips"),
    TRIPS_10("10 trips"),
    ACCUMULATIVE("Accumulative");

    private final String label;

    CardType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
