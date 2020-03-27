// Abstract vs Interface
// Abstract: you can have variables inherited
// Abstract: methods can be public or private while interface has to be public

abstract class Animal {
    private final String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void eat();
    public abstract void breathe();
    
    public String getName() {
        return this.name;
    }
}

class Dog extends Animal implements CanFly {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " inside eat");
    }

    @Override
    public void breathe() {
        System.out.println(getName() + " inside breathe");
    }

    @Override
    public void fly() {
        System.out.println("I believe I can fly");
    }
}

interface CanFly {
    public void fly();
}

public class Abstract {
    public static void main(String[] args) {
        Dog dog = new Dog("German Retriever");
        dog.eat();
        dog.breathe();
    }
}