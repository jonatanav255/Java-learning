/*
 * LESSON 27: DESIGN PATTERNS (Gang of Four)
 *
 * Design patterns are proven solutions to common software design problems.
 * They represent best practices and provide reusable templates.
 *
 * CATEGORIES:
 * 1. Creational Patterns - Object creation mechanisms
 * 2. Structural Patterns - Object composition
 * 3. Behavioral Patterns - Communication between objects
 *
 * KEY CREATIONAL PATTERNS:
 * - Singleton: Only one instance
 * - Factory: Create objects without specifying exact class
 * - Builder: Construct complex objects step by step
 * - Prototype: Clone existing objects
 *
 * KEY STRUCTURAL PATTERNS:
 * - Adapter: Make incompatible interfaces work together
 * - Decorator: Add responsibilities to objects dynamically
 * - Facade: Simplified interface to complex subsystem
 * - Proxy: Provide surrogate for another object
 *
 * KEY BEHAVIORAL PATTERNS:
 * - Observer: Notify dependents of state changes
 * - Strategy: Define family of algorithms
 * - Template Method: Skeleton of algorithm in base class
 * - Iterator: Access elements sequentially
 *
 * WHY USE DESIGN PATTERNS:
 * - Proven solutions
 * - Common vocabulary
 * - Better code organization
 * - Easier maintenance
 * - Scalable architecture
 */

import java.util.*;

public class Lesson27_DesignPatterns {
    public static void main(String[] args) {

        System.out.println("=== DESIGN PATTERNS ===\n");

        // ============================================================
        // 1. SINGLETON PATTERN
        // ============================================================

        System.out.println("--- Singleton Pattern ---");

        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        System.out.println("db1 == db2: " + (db1 == db2));  // true - same instance
        db1.connect();

        System.out.println();


        // ============================================================
        // 2. FACTORY PATTERN
        // ============================================================

        System.out.println("--- Factory Pattern ---");

        ShapeFactory factory = new ShapeFactory();

        Shape circle = factory.createShape("CIRCLE");
        circle.draw();

        Shape rectangle = factory.createShape("RECTANGLE");
        rectangle.draw();

        Shape triangle = factory.createShape("TRIANGLE");
        triangle.draw();

        System.out.println();


        // ============================================================
        // 3. BUILDER PATTERN
        // ============================================================

        System.out.println("--- Builder Pattern ---");

        // Building complex object step by step
        Computer computer = new Computer.Builder()
                .setCPU("Intel i9")
                .setRAM(32)
                .setStorage(1000)
                .setGPU("NVIDIA RTX 4090")
                .build();

        System.out.println(computer);

        // Can build with different configurations
        Computer laptop = new Computer.Builder()
                .setCPU("Intel i5")
                .setRAM(16)
                .setStorage(512)
                .build();

        System.out.println(laptop);

        System.out.println();


        // ============================================================
        // 4. OBSERVER PATTERN
        // ============================================================

        System.out.println("--- Observer Pattern ---");

        NewsAgency agency = new NewsAgency();

        NewsChannel channel1 = new NewsChannel("CNN");
        NewsChannel channel2 = new NewsChannel("BBC");

        agency.addObserver(channel1);
        agency.addObserver(channel2);

        agency.setNews("Breaking: Java 21 Released!");

        System.out.println();


        // ============================================================
        // 5. STRATEGY PATTERN
        // ============================================================

        System.out.println("--- Strategy Pattern ---");

        ShoppingCart cart = new ShoppingCart();

        // Pay with credit card
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678"));
        cart.checkout(100);

        // Pay with PayPal
        cart.setPaymentStrategy(new PayPalPayment("user@email.com"));
        cart.checkout(200);

        // Pay with Bitcoin
        cart.setPaymentStrategy(new BitcoinPayment("1A2B3C"));
        cart.checkout(300);

        System.out.println();


        // ============================================================
        // 6. DECORATOR PATTERN
        // ============================================================

        System.out.println("--- Decorator Pattern ---");

        // Simple coffee
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println(simpleCoffee.getDescription() + " $" + simpleCoffee.getCost());

        // Coffee with milk
        Coffee milkCoffee = new MilkDecorator(new SimpleCoffee());
        System.out.println(milkCoffee.getDescription() + " $" + milkCoffee.getCost());

        // Coffee with milk and sugar
        Coffee fancyCoffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));
        System.out.println(fancyCoffee.getDescription() + " $" + fancyCoffee.getCost());

        System.out.println();


        // ============================================================
        // 7. ADAPTER PATTERN
        // ============================================================

        System.out.println("--- Adapter Pattern ---");

        // Old printer
        OldPrinter oldPrinter = new OldPrinter();

        // Adapter makes old printer work with new interface
        ModernPrinter adapter = new PrinterAdapter(oldPrinter);
        adapter.printDocument("Hello World");

        System.out.println();


        // ============================================================
        // 8. FACADE PATTERN
        // ============================================================

        System.out.println("--- Facade Pattern ---");

        // Complex subsystem
        HomeTheaterFacade homeTheater = new HomeTheaterFacade();

        homeTheater.watchMovie("Inception");
        System.out.println();
        homeTheater.endMovie();

        System.out.println();


        // ============================================================
        // 9. TEMPLATE METHOD PATTERN
        // ============================================================

        System.out.println("--- Template Method Pattern ---");

        DataProcessor csvProcessor = new CSVDataProcessor();
        csvProcessor.process();

        System.out.println();

        DataProcessor jsonProcessor = new JSONDataProcessor();
        jsonProcessor.process();

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * DESIGN PATTERNS BENEFITS:
         * - Reusable solutions
         * - Common vocabulary
         * - Best practices
         * - Proven architectures
         * - Easier to understand
         *
         * WHEN TO USE:
         * Singleton: Database connections, configuration, logging
         * Factory: Object creation with many types
         * Builder: Complex objects with many parameters
         * Observer: Event handling, MVC pattern
         * Strategy: Multiple algorithms for same task
         * Decorator: Add functionality dynamically
         * Adapter: Integrate legacy code
         * Facade: Simplify complex systems
         * Template Method: Define algorithm skeleton
         *
         * ANTI-PATTERNS (AVOID):
         * - God Object: Too much responsibility
         * - Spaghetti Code: Tangled control flow
         * - Copy-Paste Programming: Duplicated code
         * - Golden Hammer: Using one pattern everywhere
         * - Premature Optimization: Optimize too early
         *
         * SOLID PRINCIPLES:
         * S - Single Responsibility
         * O - Open/Closed
         * L - Liskov Substitution
         * I - Interface Segregation
         * D - Dependency Inversion
         */
    }
}


// ============================================================
// SINGLETON PATTERN
// ============================================================

class DatabaseConnection {
    private static DatabaseConnection instance;

    // Private constructor prevents instantiation
    private DatabaseConnection() {
        System.out.println("Database connection created");
    }

    // Thread-safe singleton
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void connect() {
        System.out.println("Connected to database");
    }
}


// ============================================================
// FACTORY PATTERN
// ============================================================

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing Rectangle");
    }
}

class Triangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing Triangle");
    }
}

class ShapeFactory {
    public Shape createShape(String type) {
        switch (type.toUpperCase()) {
            case "CIRCLE":
                return new Circle();
            case "RECTANGLE":
                return new Rectangle();
            case "TRIANGLE":
                return new Triangle();
            default:
                throw new IllegalArgumentException("Unknown shape: " + type);
        }
    }
}


// ============================================================
// BUILDER PATTERN
// ============================================================

class Computer {
    private String CPU;
    private int RAM;
    private int storage;
    private String GPU;

    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.GPU = builder.GPU;
    }

    public static class Builder {
        private String CPU;
        private int RAM;
        private int storage;
        private String GPU;

        public Builder setCPU(String CPU) {
            this.CPU = CPU;
            return this;
        }

        public Builder setRAM(int RAM) {
            this.RAM = RAM;
            return this;
        }

        public Builder setStorage(int storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGPU(String GPU) {
            this.GPU = GPU;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    @Override
    public String toString() {
        return "Computer{CPU='" + CPU + "', RAM=" + RAM + "GB, Storage=" + storage + "GB, GPU='" + GPU + "'}";
    }
}


// ============================================================
// OBSERVER PATTERN
// ============================================================

interface Observer {
    void update(String news);
}

class NewsChannel implements Observer {
    private String name;

    public NewsChannel(String name) {
        this.name = name;
    }

    @Override
    public void update(String news) {
        System.out.println(name + " received news: " + news);
    }
}

class NewsAgency {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }
}


// ============================================================
// STRATEGY PATTERN
// ============================================================

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with Credit Card: " + cardNumber);
    }
}

class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with PayPal: " + email);
    }
}

class BitcoinPayment implements PaymentStrategy {
    private String walletAddress;

    public BitcoinPayment(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " with Bitcoin: " + walletAddress);
    }
}

class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount) {
        paymentStrategy.pay(amount);
    }
}


// ============================================================
// DECORATOR PATTERN
// ============================================================

interface Coffee {
    String getDescription();
    double getCost();
}

class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 2.0;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.2;
    }
}


// ============================================================
// ADAPTER PATTERN
// ============================================================

class OldPrinter {
    public void printOldWay(String text) {
        System.out.println("[Old Printer] " + text);
    }
}

interface ModernPrinter {
    void printDocument(String document);
}

class PrinterAdapter implements ModernPrinter {
    private OldPrinter oldPrinter;

    public PrinterAdapter(OldPrinter oldPrinter) {
        this.oldPrinter = oldPrinter;
    }

    @Override
    public void printDocument(String document) {
        oldPrinter.printOldWay(document);
    }
}


// ============================================================
// FACADE PATTERN
// ============================================================

class TV {
    public void on() {
        System.out.println("TV is ON");
    }

    public void off() {
        System.out.println("TV is OFF");
    }
}

class SoundSystem {
    public void on() {
        System.out.println("Sound system is ON");
    }

    public void setVolume(int level) {
        System.out.println("Volume set to " + level);
    }

    public void off() {
        System.out.println("Sound system is OFF");
    }
}

class DVDPlayer {
    public void on() {
        System.out.println("DVD player is ON");
    }

    public void play(String movie) {
        System.out.println("Playing: " + movie);
    }

    public void off() {
        System.out.println("DVD player is OFF");
    }
}

class HomeTheaterFacade {
    private TV tv;
    private SoundSystem soundSystem;
    private DVDPlayer dvdPlayer;

    public HomeTheaterFacade() {
        this.tv = new TV();
        this.soundSystem = new SoundSystem();
        this.dvdPlayer = new DVDPlayer();
    }

    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        tv.on();
        soundSystem.on();
        soundSystem.setVolume(5);
        dvdPlayer.on();
        dvdPlayer.play(movie);
    }

    public void endMovie() {
        System.out.println("Shutting down...");
        dvdPlayer.off();
        soundSystem.off();
        tv.off();
    }
}


// ============================================================
// TEMPLATE METHOD PATTERN
// ============================================================

abstract class DataProcessor {
    // Template method
    public final void process() {
        readData();
        processData();
        saveData();
    }

    protected abstract void readData();
    protected abstract void processData();

    protected void saveData() {
        System.out.println("Saving processed data");
    }
}

class CSVDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading CSV data");
    }

    @Override
    protected void processData() {
        System.out.println("Processing CSV data");
    }
}

class JSONDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading JSON data");
    }

    @Override
    protected void processData() {
        System.out.println("Processing JSON data");
    }
}
