/*
 * LESSON 18: INTERFACES
 *
 * An interface is a completely abstract contract that defines what a class must do.
 * It's like a blueprint of methods that a class must implement.
 *
 * KEY CONCEPTS:
 * - Interface: Defines method signatures (what), not implementation (how)
 * - Class "implements" an interface
 * - A class can implement multiple interfaces (multiple inheritance)
 * - All interface methods are public and abstract by default
 * - Interfaces cannot have instance variables (only constants)
 * - Since Java 8: Can have default and static methods
 *
 * INTERFACE vs ABSTRACT CLASS:
 * Interface:
 * - 100% abstraction (all methods abstract, before Java 8)
 * - Multiple inheritance (implement many)
 * - No constructors
 * - Only constants (public static final)
 * - Use when: defining a contract for unrelated classes
 *
 * Abstract Class:
 * - Partial abstraction (mix of abstract and concrete)
 * - Single inheritance (extend one)
 * - Can have constructors
 * - Can have instance variables
 * - Use when: sharing code among related classes
 *
 * WHEN TO USE INTERFACES:
 * - Define capabilities that classes should have
 * - Achieve multiple inheritance
 * - Create loose coupling
 * - Define contracts for unrelated classes
 */

public class Lesson18_Interfaces {
    public static void main(String[] args) {

        System.out.println("=== INTERFACES ===\n");

        // ============================================================
        // 1. BASIC INTERFACE IMPLEMENTATION
        // ============================================================

        System.out.println("--- Basic Interface ---");

        // Cannot instantiate interface
        // Drawable drawable = new Drawable(); // ERROR!

        Circle circle = new Circle(5);
        circle.draw();
        circle.resize(2.0);

        Rectangle rectangle = new Rectangle(4, 6);
        rectangle.draw();
        rectangle.resize(1.5);

        System.out.println();


        // ============================================================
        // 2. POLYMORPHISM WITH INTERFACES
        // ============================================================

        System.out.println("--- Polymorphism with Interfaces ---");

        Drawable[] drawables = {
            new Circle(3),
            new Rectangle(5, 8),
            new Triangle(4, 6)
        };

        for (Drawable d : drawables) {
            d.draw();
            d.resize(1.2);
        }

        System.out.println();


        // ============================================================
        // 3. MULTIPLE INTERFACE IMPLEMENTATION
        // ============================================================

        System.out.println("--- Multiple Interfaces ---");

        SmartPhone phone = new SmartPhone("iPhone 15");
        phone.makeCall("123-456-7890");
        phone.connectToWifi("MyNetwork");
        phone.takePhoto();
        phone.charge();

        System.out.println();

        Laptop laptop = new Laptop("MacBook Pro");
        laptop.connectToWifi("MyNetwork");
        laptop.charge();
        laptop.typeDocument();

        System.out.println();


        // ============================================================
        // 4. INTERFACE AS TYPE
        // ============================================================

        System.out.println("--- Interface as Type ---");

        Chargeable chargeableDevice1 = new SmartPhone("Samsung");
        Chargeable chargeableDevice2 = new Laptop("Dell");

        chargeableDevice1.charge();
        chargeableDevice2.charge();

        System.out.println();


        // ============================================================
        // 5. CONSTANTS IN INTERFACES
        // ============================================================

        System.out.println("--- Constants ---");

        System.out.println("Max file size: " + FileOperations.MAX_FILE_SIZE);
        System.out.println("Default encoding: " + FileOperations.DEFAULT_ENCODING);

        System.out.println();


        // ============================================================
        // 6. DEFAULT METHODS (Java 8+)
        // ============================================================

        System.out.println("--- Default Methods ---");

        Dog dog = new Dog("Buddy");
        dog.makeSound();  // Implemented method
        dog.sleep();      // Default method from interface

        Cat cat = new Cat("Whiskers");
        cat.makeSound();
        cat.sleep();

        System.out.println();


        // ============================================================
        // 7. STATIC METHODS IN INTERFACES (Java 8+)
        // ============================================================

        System.out.println("--- Static Methods ---");

        MathOperations.printInfo();
        System.out.println("Max: " + MathOperations.max(10, 5));

        System.out.println();


        // ============================================================
        // 8. REAL-WORLD EXAMPLE: PAYMENT SYSTEM
        // ============================================================

        System.out.println("--- Payment System ---");

        Payable[] payments = {
            new CreditCard("1234-5678-9012-3456", 5000),
            new PayPal("user@email.com", 2000),
            new BankTransfer("ACC123456", 10000)
        };

        for (Payable payment : payments) {
            payment.processPayment(100);
            payment.printReceipt();
            System.out.println();
        }


        // ============================================================
        // 9. COMPARABLE INTERFACE
        // ============================================================

        System.out.println("--- Comparable Interface ---");

        Student s1 = new Student("Alice", 3.8);
        Student s2 = new Student("Bob", 3.5);

        if (s1.compareTo(s2) > 0) {
            System.out.println(s1.getName() + " has higher GPA");
        } else {
            System.out.println(s2.getName() + " has higher GPA");
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * INTERFACE SYNTAX:
         * interface InterfaceName {
         *     // Constants (public static final by default)
         *     int MAX = 100;
         *
         *     // Abstract methods (public abstract by default)
         *     void method1();
         *     void method2();
         *
         *     // Default methods (Java 8+)
         *     default void method3() {
         *         // implementation
         *     }
         *
         *     // Static methods (Java 8+)
         *     static void method4() {
         *         // implementation
         *     }
         * }
         *
         * IMPLEMENTING INTERFACES:
         * class MyClass implements Interface1, Interface2, Interface3 {
         *     // Must implement all abstract methods
         * }
         *
         * INTERFACE RULES:
         * 1. All methods are public by default
         * 2. All fields are public static final (constants)
         * 3. Cannot have constructors
         * 4. Cannot be instantiated
         * 5. Can extend other interfaces
         * 6. Class must implement all abstract methods
         *
         * BENEFITS:
         * - Achieve multiple inheritance
         * - Define contracts
         * - Loose coupling
         * - Flexibility in design
         * - Testability (mock implementations)
         *
         * COMMON JAVA INTERFACES:
         * - Comparable: Define natural ordering
         * - Comparator: Custom sorting
         * - Serializable: Object serialization
         * - Cloneable: Object cloning
         * - Runnable: Threading
         * - ActionListener: GUI events
         */
    }
}


// ============================================================
// BASIC INTERFACE EXAMPLE
// ============================================================

interface Drawable {
    // Abstract methods (public abstract by default)
    void draw();

    void resize(double factor);
}


class Circle implements Drawable {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle with radius: " + radius);
    }

    @Override
    public void resize(double factor) {
        radius *= factor;
        System.out.println("Circle resized. New radius: " + radius);
    }
}


class Rectangle implements Drawable {
    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing rectangle: " + width + "x" + height);
    }

    @Override
    public void resize(double factor) {
        width *= factor;
        height *= factor;
        System.out.println("Rectangle resized: " + width + "x" + height);
    }
}


class Triangle implements Drawable {
    private double base, height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing triangle");
    }

    @Override
    public void resize(double factor) {
        base *= factor;
        height *= factor;
        System.out.println("Triangle resized");
    }
}


// ============================================================
// MULTIPLE INTERFACES EXAMPLE
// ============================================================

interface Callable {
    void makeCall(String number);
}

interface WifiConnectable {
    void connectToWifi(String network);
}

interface Camera {
    void takePhoto();
}

interface Chargeable {
    void charge();
}


// Class implementing multiple interfaces
class SmartPhone implements Callable, WifiConnectable, Camera, Chargeable {
    private String model;

    public SmartPhone(String model) {
        this.model = model;
    }

    @Override
    public void makeCall(String number) {
        System.out.println(model + " calling " + number);
    }

    @Override
    public void connectToWifi(String network) {
        System.out.println(model + " connected to " + network);
    }

    @Override
    public void takePhoto() {
        System.out.println(model + " taking photo");
    }

    @Override
    public void charge() {
        System.out.println(model + " is charging");
    }
}


class Laptop implements WifiConnectable, Chargeable {
    private String model;

    public Laptop(String model) {
        this.model = model;
    }

    @Override
    public void connectToWifi(String network) {
        System.out.println(model + " laptop connected to " + network);
    }

    @Override
    public void charge() {
        System.out.println(model + " laptop is charging");
    }

    public void typeDocument() {
        System.out.println("Typing document on " + model);
    }
}


// ============================================================
// INTERFACE WITH CONSTANTS
// ============================================================

interface FileOperations {
    // Constants (public static final by default)
    int MAX_FILE_SIZE = 10000;
    String DEFAULT_ENCODING = "UTF-8";

    void readFile(String filename);

    void writeFile(String filename, String content);
}


// ============================================================
// DEFAULT AND STATIC METHODS (Java 8+)
// ============================================================

interface AnimalBehavior {
    // Abstract method
    void makeSound();

    // Default method (has implementation)
    default void sleep() {
        System.out.println("Animal is sleeping...");
    }

    // Static method
    static void printInfo() {
        System.out.println("This is an animal behavior interface");
    }
}


class Dog implements AnimalBehavior {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof!");
    }

    // Can override default method if needed
    @Override
    public void sleep() {
        System.out.println(name + " is sleeping in the doghouse");
    }
}


class Cat implements AnimalBehavior {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow!");
    }

    // Uses default sleep() method
}


// ============================================================
// INTERFACE WITH STATIC METHODS
// ============================================================

interface MathOperations {
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    static void printInfo() {
        System.out.println("Math operations interface");
    }
}


// ============================================================
// PAYMENT SYSTEM EXAMPLE
// ============================================================

interface Payable {
    void processPayment(double amount);

    void printReceipt();
}


class CreditCard implements Payable {
    private String cardNumber;
    private double balance;

    public CreditCard(String cardNumber, double balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    @Override
    public void processPayment(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Credit card payment processed: $" + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("Card: ****" + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Remaining balance: $" + balance);
    }
}


class PayPal implements Payable {
    private String email;
    private double balance;

    public PayPal(String email, double balance) {
        this.email = email;
        this.balance = balance;
    }

    @Override
    public void processPayment(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("PayPal payment processed: $" + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("Email: " + email);
        System.out.println("Remaining balance: $" + balance);
    }
}


class BankTransfer implements Payable {
    private String accountNumber;
    private double balance;

    public BankTransfer(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @Override
    public void processPayment(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Bank transfer completed: $" + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }

    @Override
    public void printReceipt() {
        System.out.println("Account: " + accountNumber);
        System.out.println("Remaining balance: $" + balance);
    }
}


// ============================================================
// COMPARABLE INTERFACE EXAMPLE
// ============================================================

class Student implements Comparable<Student> {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Student other) {
        // Compare by GPA
        if (this.gpa > other.gpa) return 1;
        if (this.gpa < other.gpa) return -1;
        return 0;
    }
}
