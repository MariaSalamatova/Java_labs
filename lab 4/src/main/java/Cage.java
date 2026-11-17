import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Cage<T extends Animal> {
    private final int capacity;
    private final List<T> animals = new ArrayList<>();

    protected Cage(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    /** Maximum number of animals the cage can hold. */
    public int getCapacity() {
        return capacity;
    }

    /** Number of occupied places. */
    public int getOccupiedCount() {
        return animals.size();
    }

    /** Places the animal in the cage; throws if all places are occupied. */
    public void addAnimal(T animal) throws CageFullException {
        if (animals.size() >= capacity) {
            throw new CageFullException("Cage is full (capacity " + capacity + "), cannot add " + animal);
        }
        animals.add(animal);
    }

    /** Removes the animal from the cage; throws if it is not there. */
    public void removeAnimal(T animal) throws AnimalNotFoundException {
        if (!animals.remove(animal)) {
            throw new AnimalNotFoundException(animal + " is not in this cage");
        }
    }

    /** "? extends T": we only read from the source, so any subtype collection works. */
    public void addAll(Collection<? extends T> source) throws CageFullException {
        for (T a : source) {
            addAnimal(a);
        }
    }

    /** "? super T": the target cage may be for T or any of its supertypes. */
    public void transferAllTo(Cage<? super T> target) throws CageFullException {
        for (T a : new ArrayList<>(animals)) {
            target.addAnimal(a);
            animals.remove(a);
        }
    }

    /** Read-only copy of the animals. */
    public List<? extends T> getAnimals() {
        return List.copyOf(animals);
    }
}
