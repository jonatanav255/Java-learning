/*
 * LESSON 16: POLYMORPHISM
 *
 * Polymorphism means "many forms" - the ability of an object to take many forms.
 * It's one of the four pillars of Object-Oriented Programming.
 *
 * TYPES OF POLYMORPHISM:
 * 1. Compile-time Polymorphism (Static):
 *    - Method Overloading
 *    - Operator Overloading (not in Java)
 *
 * 2. Runtime Polymorphism (Dynamic):
 *    - Method Overriding
 *    - Parent reference holding child object
 *
 * KEY CONCEPTS:
 * - Upcasting: Child object assigned to parent reference (automatic)
 * - Downcasting: Parent reference cast to child type (manual, risky)
 * - Dynamic Method Dispatch: JVM decides which method to call at runtime
 * - Late Binding: Method binding happens at runtime
 *
 * BENEFITS:
 * - Flexibility in code
 * - Easy to extend
 * - Code reusability
 * - Loose coupling
 */

public class Lesson16_Polymorphism {
    public static void main(String[] args) {

        System.out.println("=== POLYMORPHISM ===\n");

        // ============================================================
        // 1. METHOD OVERLOADING (COMPILE-TIME POLYMORPHISM)
        // ============================================================

        System.out.println("--- Method Overloading ---");

        Calculator calc = new Calculator();

        System.out.println("Add two ints: " + calc.add(5, 3));
        System.out.println("Add three ints: " + calc.add(5, 3, 2));
        System.out.println("Add two doubles: " + calc.add(5.5, 3.2));
        System.out.println("Add int and double: " + calc.add(5, 3.5));

        System.out.println();


        // ============================================================
        // 2. METHOD OVERRIDING (RUNTIME POLYMORPHISM)
        // ============================================================

        System.out.println("--- Method Overriding ---");

        // Create objects
        Shape shape1 = new Shape();
        Shape shape2 = new Circle(5);      // Parent reference, child object
        Shape shape3 = new Rectangle(4, 6); // Parent reference, child object
        Shape shape4 = new Triangle(3, 7);  // Parent reference, child object

        // Call methods - JVM decides which version to call at runtime
        shape1.draw();
        shape2.draw();  // Calls Circle's draw()
        shape3.draw();  // Calls Rectangle's draw()
        shape4.draw();  // Calls Triangle's draw()

        System.out.println();


        // ============================================================
        // 3. POLYMORPHIC BEHAVIOR
        // ============================================================

        System.out.println("--- Polymorphic Behavior ---");

        // Array of parent type holding different child objects
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 7),
            new Circle(3)
        };

        // Polymorphism in action!
        for (Shape shape : shapes) {
            shape.draw();           // Different behavior for each
            System.out.println("Area: " + shape.calculateArea());
            System.out.println();
        }


        // ============================================================
        // 4. UPCASTING (AUTOMATIC)
        // ============================================================

        System.out.println("--- Upcasting ---");

        // Automatic conversion from child to parent
        Circle circle = new Circle(10);
        Shape shapeRef = circle;  // Upcasting (automatic)

        shapeRef.draw();  // Calls Circle's draw() method
        // shapeRef.getRadius(); // Error! Parent reference can't access child methods

        System.out.println();


        // ============================================================
        // 5. DOWNCASTING (MANUAL)
        // ============================================================

        System.out.println("--- Downcasting ---");

        Shape genericShape = new Circle(7);

        // Check type before downcasting
        if (genericShape instanceof Circle) {
            Circle specificCircle = (Circle) genericShape;  // Downcasting
            System.out.println("Radius: " + specificCircle.getRadius());
            specificCircle.draw();
        }

        System.out.println();


        // ============================================================
        // 6. POLYMORPHISM WITH METHODS
        // ============================================================

        System.out.println("--- Polymorphism with Methods ---");

        printShapeInfo(new Circle(5));
        printShapeInfo(new Rectangle(4, 6));
        printShapeInfo(new Triangle(3, 7));

        System.out.println();


        // ============================================================
        // 7. REAL-WORLD EXAMPLE: PAYMENT SYSTEM
        // ============================================================

        System.out.println("--- Payment System Example ---");

        PaymentProcessor processor = new PaymentProcessor();

        Payment creditCard = new CreditCardPayment("1234-5678-9012-3456");
        Payment paypal = new PayPalPayment("user@email.com");
        Payment bitcoin = new BitcoinPayment("1A2B3C4D5E6F");

        processor.processPayment(creditCard, 100.00);
        processor.processPayment(paypal, 75.50);
        processor.processPayment(bitcoin, 200.00);

        System.out.println();


        // ============================================================
        // 8. POLYMORPHISM WITH ANIMALS
        // ============================================================

        System.out.println("--- Animal Sounds ---");

        Animal[] animals = {
            new Dog("Buddy"),
            new Cat("Whiskers"),
            new Cow("Bessie"),
            new Dog("Max")
        };

        for (Animal animal : animals) {
            animal.makeSound();  // Each animal makes different sound
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * COMPILE-TIME POLYMORPHISM (Method Overloading):
         * - Same method name, different parameters
         * - Decided at compile time
         * - No inheritance required
         * - Examples: print(), add(), max()
         *
         * RUNTIME POLYMORPHISM (Method Overriding):
         * - Same method signature in parent and child
         * - Decided at runtime (Dynamic Method Dispatch)
         * - Requires inheritance
         * - Parent reference can hold child object
         *
         * UPCASTING:
         * - Child object → Parent reference
         * - Automatic, safe
         * - Lose access to child-specific methods
         * - Example: Shape s = new Circle();
         *
         * DOWNCASTING:
         * - Parent reference → Child type
         * - Manual, can be unsafe
         * - Use instanceof to check before casting
         * - Example: Circle c = (Circle) shape;
         *
         * BENEFITS:
         * - Write flexible, extensible code
         * - Work with parent type, handle all children
         * - Add new types without changing existing code
         * - Core principle of OOP design patterns
         *
         * RULES:
         * - Overridden method must have same signature
         * - Access level can be more public, not less
         * - Cannot override final, static methods
         * - Runtime type determines which method runs
         */
    }

    // Helper method demonstrating polymorphism
    public static void printShapeInfo(Shape shape) {
        System.out.println("--- Shape Information ---");
        shape.draw();
        System.out.println("Area: " + shape.calculateArea());
        System.out.println("Perimeter: " + shape.calculatePerimeter());
        System.out.println();
    }
}


// ============================================================
// METHOD OVERLOADING EXAMPLE
// ============================================================

class Calculator {
    // Same method name, different parameters

    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public double add(int a, double b) {
        return a + b;
    }
}


// ============================================================
// METHOD OVERRIDING EXAMPLE - SHAPES
// ============================================================

class Shape {
    public void draw() {
        System.out.println("Drawing a shape");
    }

    public double calculateArea() {
        return 0.0;
    }

    public double calculatePerimeter() {
        return 0.0;
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius: " + radius);
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a rectangle: " + width + " x " + height);
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a triangle: base=" + base + ", height=" + height);
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        // Simplified - assuming equilateral for demonstration
        return 3 * base;
    }
}


// ============================================================
// PAYMENT SYSTEM EXAMPLE
// ============================================================

class Payment {
    protected String accountInfo;

    public Payment(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount);
    }

    public void sendConfirmation() {
        System.out.println("Payment confirmation sent");
    }
}

class CreditCardPayment extends Payment {
    public CreditCardPayment(String cardNumber) {
        super(cardNumber);
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment...");
        System.out.println("Card: " + maskCardNumber(accountInfo));
        System.out.println("Amount: $" + amount);
        System.out.println("✓ Credit card payment successful");
    }

    private String maskCardNumber(String cardNumber) {
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}

class PayPalPayment extends Payment {
    public PayPalPayment(String email) {
        super(email);
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment...");
        System.out.println("Email: " + accountInfo);
        System.out.println("Amount: $" + amount);
        System.out.println("✓ PayPal payment successful");
    }
}

class BitcoinPayment extends Payment {
    public BitcoinPayment(String walletAddress) {
        super(walletAddress);
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Bitcoin payment...");
        System.out.println("Wallet: " + accountInfo);
        System.out.println("Amount: $" + amount);
        System.out.println("✓ Bitcoin payment successful");
    }
}

class PaymentProcessor {
    // This method accepts ANY type of Payment (polymorphism!)
    public void processPayment(Payment payment, double amount) {
        payment.processPayment(amount);  // Calls appropriate version
        payment.sendConfirmation();
        System.out.println("------------------------");
    }
}


// ============================================================
// ANIMAL SOUNDS EXAMPLE
// ============================================================

class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void makeSound() {
        System.out.println(name + " makes a sound");
    }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow!");
    }
}

class Cow extends Animal {
    public Cow(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Moo!");
    }
}
