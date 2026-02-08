/*
 * LESSON 15: INHERITANCE
 *
 * Inheritance is one of the four pillars of Object-Oriented Programming (OOP).
 * It allows a class to inherit properties and methods from another class.
 *
 * KEY CONCEPTS:
 * - Parent Class (Superclass, Base Class): The class being inherited from
 * - Child Class (Subclass, Derived Class): The class that inherits
 * - "extends" keyword: Used to create inheritance relationship
 * - "super" keyword: Used to access parent class members
 * - Method Overriding: Child class provides its own implementation
 * - Code Reusability: Don't repeat yourself (DRY principle)
 *
 * BENEFITS OF INHERITANCE:
 * - Code reusability
 * - Method overriding (runtime polymorphism)
 * - Hierarchical classification
 * - Extensibility
 *
 * IMPORTANT RULES:
 * - Java supports single inheritance (one parent class only)
 * - A class can only extend ONE class
 * - Multiple inheritance is achieved through interfaces (next lesson)
 * - Private members are not inherited (but can be accessed via getters/setters)
 * - Constructors are not inherited
 */

public class Lesson15_Inheritance {
    public static void main(String[] args) {

        System.out.println("=== INHERITANCE BASICS ===\n");

        // ============================================================
        // 1. BASIC INHERITANCE
        // ============================================================

        // Create parent class object
        Animal animal = new Animal("Generic Animal", 5);
        animal.eat();
        animal.sleep();
        animal.displayInfo();

        System.out.println();

        // Create child class objects
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        dog.eat();          // Inherited from Animal
        dog.sleep();        // Inherited from Animal
        dog.bark();         // Dog's own method
        dog.displayInfo();  // Overridden method

        System.out.println();

        Cat cat = new Cat("Whiskers", 2, "Siamese");
        cat.eat();
        cat.sleep();
        cat.meow();
        cat.displayInfo();

        System.out.println();


        // ============================================================
        // 2. METHOD OVERRIDING
        // ============================================================

        System.out.println("=== METHOD OVERRIDING ===\n");

        Bird bird = new Bird("Tweety", 1, true);
        bird.eat();         // Overridden - different implementation
        bird.makeSound();   // Overridden
        bird.fly();         // Bird's own method

        System.out.println();


        // ============================================================
        // 3. SUPER KEYWORD
        // ============================================================

        System.out.println("=== SUPER KEYWORD ===\n");

        Student student = new Student("Alice", 20, "S12345", 3.8);
        student.displayInfo();

        System.out.println();


        // ============================================================
        // 4. INHERITANCE HIERARCHY
        // ============================================================

        System.out.println("=== INHERITANCE HIERARCHY ===\n");

        // Vehicles example
        Vehicle vehicle = new Vehicle("Generic Vehicle", 2020);
        vehicle.start();
        vehicle.stop();

        System.out.println();

        Car car = new Car("Toyota Camry", 2023, 4);
        car.start();    // Overridden
        car.honk();     // Car's method
        car.stop();     // Inherited

        System.out.println();

        Motorcycle motorcycle = new Motorcycle("Harley Davidson", 2022, true);
        motorcycle.start();     // Overridden
        motorcycle.wheelie();   // Motorcycle's method

        System.out.println();


        // ============================================================
        // 5. PROTECTED ACCESS MODIFIER
        // ============================================================

        System.out.println("=== PROTECTED MEMBERS ===\n");

        BankAccount account = new BankAccount("123456", 1000);
        // account.balance = 5000; // Error! balance is protected

        SavingsAccount savings = new SavingsAccount("789012", 2000, 0.05);
        savings.addInterest();
        savings.displayBalance();

        System.out.println();


        // ============================================================
        // 6. instanceof OPERATOR
        // ============================================================

        System.out.println("=== instanceof OPERATOR ===\n");

        // Check object type
        Animal myAnimal = new Dog("Rex", 4, "German Shepherd");

        if (myAnimal instanceof Dog) {
            System.out.println("myAnimal is a Dog");
            Dog myDog = (Dog) myAnimal; // Safe casting
            myDog.bark();
        }

        if (myAnimal instanceof Animal) {
            System.out.println("myAnimal is also an Animal");
        }

        System.out.println();


        // ============================================================
        // 7. MULTILEVEL INHERITANCE
        // ============================================================

        System.out.println("=== MULTILEVEL INHERITANCE ===\n");

        // GrandParent -> Parent -> Child
        Puppy puppy = new Puppy("Max", 1, "Labrador", 5);
        puppy.eat();        // From Animal (grandparent)
        puppy.bark();       // From Dog (parent)
        puppy.play();       // From Puppy (child)

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * INHERITANCE SYNTAX:
         * class Child extends Parent {
         *     // Child class members
         * }
         *
         * SUPER KEYWORD USES:
         * 1. super.method() - Call parent's method
         * 2. super.field - Access parent's field
         * 3. super() - Call parent's constructor (must be first line)
         *
         * METHOD OVERRIDING RULES:
         * 1. Same method name
         * 2. Same parameters (signature)
         * 3. Same or more accessible (public > protected > default > private)
         * 4. Return type must be same or covariant
         * 5. Cannot override final or static methods
         * 6. Use @Override annotation (recommended)
         *
         * ACCESS MODIFIERS IN INHERITANCE:
         * - public: Inherited and accessible everywhere
         * - protected: Inherited and accessible in subclass and same package
         * - default: Inherited only in same package
         * - private: NOT inherited (but exists in object)
         *
         * CONSTRUCTOR CHAINING:
         * - Constructors are not inherited
         * - Child constructor must call parent constructor
         * - super() is automatically called if not explicitly written
         * - super() must be first statement in constructor
         *
         * TYPES OF INHERITANCE (in general OOP):
         * 1. Single: A -> B
         * 2. Multilevel: A -> B -> C
         * 3. Hierarchical: A -> B, A -> C
         * 4. Multiple: A,B -> C (NOT in Java via classes, use interfaces)
         * 5. Hybrid: Combination (use interfaces)
         */
    }
}


// ============================================================
// BASIC INHERITANCE EXAMPLE
// ============================================================

// Parent class (Superclass)
class Animal {
    protected String name;  // protected: accessible in subclasses
    protected int age;

    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Methods that can be inherited
    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}


// Child class (Subclass) - Dog extends Animal
class Dog extends Animal {
    private String breed;

    // Constructor
    public Dog(String name, int age, String breed) {
        // Call parent class constructor using super()
        super(name, age);  // Must be first line
        this.breed = breed;
    }

    // Dog's own method
    public void bark() {
        System.out.println(name + " says: Woof! Woof!");
    }

    // Override parent's method
    @Override  // Annotation (optional but recommended)
    public void displayInfo() {
        // Call parent's method first
        super.displayInfo();
        System.out.println("Breed: " + breed);
    }
}


// Another child class - Cat extends Animal
class Cat extends Animal {
    private String color;

    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    public void meow() {
        System.out.println(name + " says: Meow!");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Color: " + color);
    }
}


// ============================================================
// METHOD OVERRIDING EXAMPLE
// ============================================================

class Bird extends Animal {
    private boolean canFly;

    public Bird(String name, int age, boolean canFly) {
        super(name, age);
        this.canFly = canFly;
    }

    // Override parent's method with different implementation
    @Override
    public void eat() {
        System.out.println(name + " pecks at food.");
    }

    public void makeSound() {
        System.out.println(name + " chirps!");
    }

    public void fly() {
        if (canFly) {
            System.out.println(name + " is flying!");
        } else {
            System.out.println(name + " cannot fly.");
        }
    }
}


// ============================================================
// SUPER KEYWORD EXAMPLE
// ============================================================

class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

class Student extends Person {
    private String studentId;
    private double gpa;

    public Student(String name, int age, String studentId, double gpa) {
        super(name, age);  // Call parent constructor
        this.studentId = studentId;
        this.gpa = gpa;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent's displayInfo
        System.out.println("Student ID: " + studentId);
        System.out.println("GPA: " + gpa);
    }
}


// ============================================================
// VEHICLE HIERARCHY EXAMPLE
// ============================================================

class Vehicle {
    protected String model;
    protected int year;

    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }

    public void start() {
        System.out.println(model + " is starting...");
    }

    public void stop() {
        System.out.println(model + " is stopping...");
    }
}

class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String model, int year, int numberOfDoors) {
        super(model, year);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void start() {
        System.out.println(model + " car is starting with key...");
    }

    public void honk() {
        System.out.println(model + " goes: Beep! Beep!");
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String model, int year, boolean hasSidecar) {
        super(model, year);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void start() {
        System.out.println(model + " motorcycle is starting with kick...");
    }

    public void wheelie() {
        System.out.println(model + " is doing a wheelie!");
    }
}


// ============================================================
// PROTECTED ACCESS MODIFIER EXAMPLE
// ============================================================

class BankAccount {
    private String accountNumber;
    protected double balance;  // Protected: accessible in subclasses

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void displayBalance() {
        System.out.println("Account: " + accountNumber + ", Balance: $" + balance);
    }
}

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        // Can access 'balance' because it's protected
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest added: $" + interest);
    }
}


// ============================================================
// MULTILEVEL INHERITANCE
// ============================================================

// Animal -> Dog -> Puppy
class Puppy extends Dog {
    private int energyLevel;

    public Puppy(String name, int age, String breed, int energyLevel) {
        super(name, age, breed);
        this.energyLevel = energyLevel;
    }

    public void play() {
        System.out.println(name + " is playing with energy level: " + energyLevel);
    }
}
