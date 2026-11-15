/** Card holder category. */
public enum CardCategory {
    SCHOOL("School"),
    STUDENT("Student"),
    REGULAR("Regular");

    private final String label;

    CardCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
