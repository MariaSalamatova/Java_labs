import java.util.ArrayList;
import java.util.List;

public class Zoo {
    public List<Cage<? extends Animal>> cages = new ArrayList<>();

    /** Total number of animals in all cages of the zoo. */
    public int getCountOfAnimals() {
        int total = 0;
        for (Cage<? extends Animal> cage : cages) {
            total += cage.getOccupiedCount();
        }
        return total;
    }

    public void addCage(Cage<? extends Animal> cage) {
        cages.add(cage);
    }
}
