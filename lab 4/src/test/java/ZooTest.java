import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZooTest {

    @Test
    void lionCageCapacityAndOccupancy() throws Exception {
        LionCage cage = new LionCage(2);
        assertEquals(2, cage.getCapacity());
        assertEquals(0, cage.getOccupiedCount());

        cage.addAnimal(new Lion("Simba"));
        assertEquals(1, cage.getOccupiedCount());
    }

    @Test
    void addingToFullCageThrows() throws Exception {
        LionCage cage = new LionCage(1);
        cage.addAnimal(new Lion("Simba"));
        assertThrows(CageFullException.class, () -> cage.addAnimal(new Lion("Nala")));
        assertEquals(1, cage.getOccupiedCount());
    }

    @Test
    void removeAnimal() throws Exception {
        LionCage cage = new LionCage(2);
        Lion simba = new Lion("Simba");
        cage.addAnimal(simba);
        cage.removeAnimal(simba);
        assertEquals(0, cage.getOccupiedCount());
    }

    @Test
    void removingMissingAnimalThrows() {
        LionCage cage = new LionCage(2);
        assertThrows(AnimalNotFoundException.class, () -> cage.removeAnimal(new Lion("Ghost")));
    }

    @Test
    void ungulateCageHoldsZebrasAndGiraffes() throws Exception {
        UngulateCage<Ungulate> cage = new UngulateCage<>(3);
        cage.addAnimal(new Zebra("Marty"));
        cage.addAnimal(new Giraffe("Melman"));
        assertEquals(2, cage.getOccupiedCount());
    }

    @Test
    void birdCageHoldsBirds() throws Exception {
        BirdCage<Bird> cage = new BirdCage<>(2);
        cage.addAnimal(new Eagle("Sam"));
        assertEquals(1, cage.getOccupiedCount());
    }

    @Test
    void addAllWithExtendsWildcard() throws Exception {
        UngulateCage<Ungulate> cage = new UngulateCage<>(3);
        List<Zebra> zebras = List.of(new Zebra("Z1"), new Zebra("Z2"));
        cage.addAll(zebras);
        assertEquals(2, cage.getOccupiedCount());
    }

    @Test
    void transferWithSuperWildcard() throws Exception {
        UngulateCage<Zebra> zebraCage = new UngulateCage<>(2);
        zebraCage.addAnimal(new Zebra("Z1"));
        zebraCage.addAnimal(new Zebra("Z2"));

        UngulateCage<Ungulate> mixedCage = new UngulateCage<>(5);
        zebraCage.transferAllTo(mixedCage);

        assertEquals(0, zebraCage.getOccupiedCount());
        assertEquals(2, mixedCage.getOccupiedCount());
    }

    @Test
    void zooCountsAnimalsInAllCages() throws Exception {
        Zoo zoo = new Zoo();
        assertEquals(0, zoo.getCountOfAnimals());

        LionCage lions = new LionCage(3);
        lions.addAnimal(new Lion("Simba"));
        lions.addAnimal(new Lion("Nala"));

        UngulateCage<Ungulate> ungulates = new UngulateCage<>(3);
        ungulates.addAnimal(new Zebra("Marty"));
        ungulates.addAnimal(new Giraffe("Melman"));

        BirdCage<Bird> birds = new BirdCage<>(2);
        birds.addAnimal(new Eagle("Sam"));

        zoo.addCage(lions);
        zoo.addCage(ungulates);
        zoo.addCage(birds);

        assertEquals(5, zoo.getCountOfAnimals());
    }

    @Test
    void zooCountUpdatesAfterRemoval() throws Exception {
        Zoo zoo = new Zoo();
        BirdCage<Eagle> birds = new BirdCage<>(2);
        Eagle sam = new Eagle("Sam");
        birds.addAnimal(sam);
        zoo.addCage(birds);
        assertEquals(1, zoo.getCountOfAnimals());

        birds.removeAnimal(sam);
        assertEquals(0, zoo.getCountOfAnimals());
    }
}
