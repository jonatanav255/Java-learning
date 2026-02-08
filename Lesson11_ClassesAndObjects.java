/*
 * LESSON 11: OBJECT-ORIENTED PROGRAMMING - CLASSES AND OBJECTS
 *
 * Java is an Object-Oriented Programming (OOP) language.
 * Everything in Java revolves around objects and classes.
 *
 * KEY CONCEPTS:
 * - Class: A blueprint or template for creating objects
 * - Object: An instance of a class (actual thing created from the blueprint)
 * - Fields (Attributes): Variables that belong to a class
 * - Methods: Functions that belong to a class
 * - Constructor: Special method to initialize objects
 *
 * Think of it like:
 * - Class = Blueprint of a house
 * - Object = Actual house built from the blueprint
 */

public class Lesson11_ClassesAndObjects {

    public static void main(String[] args) {

        System.out.println("=== CREATING AND USING OBJECTS ===\n");

        // ============================================================
        // CREATING OBJECTS
        // ============================================================

        // Create a Dog object using the default constructor
        Dog dog1 = new Dog();
        dog1.name = "Buddy";
        dog1.breed = "Golden Retriever";
        dog1.age = 3;

        System.out.println("Dog 1:");
        dog1.displayInfo();
        dog1.bark();
        System.out.println();

        // Create another Dog object
        Dog dog2 = new Dog();
        dog2.name = "Max";
        dog2.breed = "Beagle";
        dog2.age = 2;

        System.out.println("Dog 2:");
        dog2.displayInfo();
        dog2.bark();
        System.out.println();


        // ============================================================
        // USING CONSTRUCTORS
        // ============================================================

        // Create a Person object using constructor
        Person person1 = new Person("Alice", 25);
        person1.introduce();

        Person person2 = new Person("Bob", 30);
        person2.introduce();

        // Using default constructor
        Person person3 = new Person();
        person3.name = "Charlie";
        person3.age = 28;
        person3.introduce();

        System.out.println();


        // ============================================================
        // MULTIPLE CONSTRUCTORS (CONSTRUCTOR OVERLOADING)
        // ============================================================

        Car car1 = new Car();
        car1.displayInfo();

        Car car2 = new Car("Toyota", "Camry");
        car2.displayInfo();

        Car car3 = new Car("Honda", "Civic", 2023);
        car3.displayInfo();

        System.out.println();


        // ============================================================
        // METHODS WITH RETURN VALUES
        // ============================================================

        Rectangle rect = new Rectangle(5, 3);
        System.out.println("Rectangle dimensions: " + rect.width + " x " + rect.height);

        int area = rect.calculateArea();
        System.out.println("Area: " + area);

        int perimeter = rect.calculatePerimeter();
        System.out.println("Perimeter: " + perimeter);

        System.out.println();


        // ============================================================
        // ENCAPSULATION - GETTERS AND SETTERS
        // ============================================================

        BankAccount account = new BankAccount("123456", 1000);

        System.out.println("Account: " + account.getAccountNumber());
        System.out.println("Balance: $" + account.getBalance());

        account.deposit(500);
        account.withdraw(200);
        account.withdraw(2000); // This should fail

        System.out.println("Final balance: $" + account.getBalance());

        System.out.println();


        // ============================================================
        // STATIC MEMBERS
        // ============================================================

        // Access static variables without creating object
        System.out.println("Circle count: " + Circle.circleCount);

        Circle c1 = new Circle(5);
        Circle c2 = new Circle(3);
        Circle c3 = new Circle(7);

        System.out.println("Circle count: " + Circle.circleCount); // 3

        // Access static method
        System.out.println("PI value: " + Circle.getPI());

        System.out.println("Circle 1 area: " + c1.calculateArea());

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * 1. CLASS: Blueprint for creating objects
         *    - Contains fields (attributes/properties)
         *    - Contains methods (behaviors/functions)
         *    - Contains constructors (to initialize objects)
         *
         * 2. OBJECT: Instance of a class
         *    - Created using 'new' keyword
         *    - Each object has its own copy of instance variables
         *    - Can call methods on objects
         *
         * 3. CONSTRUCTOR:
         *    - Special method with same name as class
         *    - No return type (not even void)
         *    - Called automatically when object is created
         *    - Can be overloaded (multiple constructors)
         *
         * 4. THIS KEYWORD:
         *    - Refers to the current object
         *    - Used to distinguish between parameters and fields
         *    - Example: this.name = name;
         *
         * 5. STATIC:
         *    - Belongs to the class, not to objects
         *    - Shared by all instances
         *    - Access using ClassName.member
         *    - Example: Math.PI, Math.max()
         *
         * 6. ENCAPSULATION:
         *    - Hide internal details
         *    - Use private fields
         *    - Provide public getters and setters
         *    - Control access to data
         */
    }
}


// ============================================================
// DOG CLASS - Basic class example
// ============================================================
class Dog {
    // Fields (attributes/properties)
    String name;
    String breed;
    int age;

    // Method (behavior)
    void bark() {
        System.out.println(name + " says: Woof! Woof!");
    }

    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Breed: " + breed);
        System.out.println("Age: " + age);
    }
}


// ============================================================
// PERSON CLASS - Class with constructor
// ============================================================
class Person {
    String name;
    int age;

    // Constructor with parameters
    Person(String name, int age) {
        // 'this' refers to the current object
        this.name = name;  // this.name = field, name = parameter
        this.age = age;
    }

    // Default constructor (no parameters)
    Person() {
        this.name = "Unknown";
        this.age = 0;
    }

    void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}


// ============================================================
// CAR CLASS - Constructor overloading
// ============================================================
class Car {
    String make;
    String model;
    int year;

    // Constructor 1: No parameters
    Car() {
        this.make = "Unknown";
        this.model = "Unknown";
        this.year = 0;
    }

    // Constructor 2: Make and model
    Car(String make, String model) {
        this.make = make;
        this.model = model;
        this.year = 2024; // Default year
    }

    // Constructor 3: All parameters
    Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    void displayInfo() {
        System.out.println("Car: " + year + " " + make + " " + model);
    }
}


// ============================================================
// RECTANGLE CLASS - Methods with return values
// ============================================================
class Rectangle {
    int width;
    int height;

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Method that returns a value
    int calculateArea() {
        return width * height;
    }

    int calculatePerimeter() {
        return 2 * (width + height);
    }

    boolean isSquare() {
        return width == height;
    }
}


// ============================================================
// BANKACCOUNT CLASS - Encapsulation example
// ============================================================
class BankAccount {
    // Private fields (cannot be accessed directly from outside)
    private String accountNumber;
    private double balance;

    // Constructor
    BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Getter methods (read-only access)
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Methods to modify balance (controlled access)
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds");
        }
    }
}


// ============================================================
// CIRCLE CLASS - Static members
// ============================================================
class Circle {
    // Static variable - shared by all Circle objects
    static int circleCount = 0;

    // Static constant
    static final double PI = 3.14159;

    // Instance variable - each Circle has its own radius
    double radius;

    // Constructor
    Circle(double radius) {
        this.radius = radius;
        circleCount++; // Increment count when a new circle is created
    }

    // Instance method - operates on specific object
    double calculateArea() {
        return PI * radius * radius;
    }

    // Static method - belongs to the class
    static double getPI() {
        return PI;
    }

    static int getCircleCount() {
        return circleCount;
    }
}
