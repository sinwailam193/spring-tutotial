class Animal {
    class Pet {
        public void run() {
            System.out.println("awesome");
        }
    }
}

public class InnerClass {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal.Pet pet = animal.new Pet();
        pet.run();
    }
}