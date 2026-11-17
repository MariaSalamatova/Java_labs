/** Cage for ungulates only (zebras and giraffes). */
public class UngulateCage<T extends Ungulate> extends MammalCage<T> {
    public UngulateCage(int capacity) {
        super(capacity);
    }
}
