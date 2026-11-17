public class Main {
    public static void main(String[] args) throws Exception {
        Zoo zoo = new Zoo();

        LionCage lions = new LionCage(2);
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

        System.out.println("Animals in the zoo: " + zoo.getCountOfAnimals());

        try {
            lions.addAnimal(new Lion("Mufasa"));
        } catch (CageFullException e) {
            System.out.println("Expected: " + e.getMessage());
        }

        try {
            birds.removeAnimal(new Eagle("Ghost"));
        } catch (AnimalNotFoundException e) {
            System.out.println("Expected: " + e.getMessage());
        }
    }
}
