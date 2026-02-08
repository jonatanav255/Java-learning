/*
 * LESSON 20: ENUMERATIONS (ENUMS)
 *
 * An enum is a special class that represents a group of constants (unchangeable variables).
 * Enums make code more readable and less error-prone than using plain constants.
 *
 * KEY CONCEPTS:
 * - Enum: Special type for defining constants
 * - Enum constants are objects of the enum type
 * - Enums can have fields, constructors, and methods
 * - Enums are type-safe (compile-time checking)
 * - Cannot extend other classes (already extends Enum)
 *
 * WHY USE ENUMS:
 * - Type safety (compiler checks)
 * - More readable than int constants
 * - Can have behavior (methods)
 * - Switch statement compatibility
 * - Built-in methods (values(), valueOf(), name(), ordinal())
 *
 * BEFORE ENUMS (Old way):
 * public static final int MONDAY = 1;
 * public static final int TUESDAY = 2;
 * // Problems: not type-safe, can pass any int
 *
 * WITH ENUMS (Better way):
 * enum Day { MONDAY, TUESDAY, WEDNESDAY }
 * // Type-safe, can only use defined constants
 */

public class Lesson20_Enums {
    public static void main(String[] args) {

        System.out.println("=== ENUMS ===\n");

        // ============================================================
        // 1. BASIC ENUM USAGE
        // ============================================================

        System.out.println("--- Basic Enum ---");

        Day today = Day.MONDAY;
        System.out.println("Today is: " + today);

        Day tomorrow = Day.TUESDAY;
        System.out.println("Tomorrow is: " + tomorrow);

        // Compare enums
        if (today == Day.MONDAY) {
            System.out.println("It's Monday!");
        }

        System.out.println();


        // ============================================================
        // 2. ENUM IN SWITCH STATEMENT
        // ============================================================

        System.out.println("--- Enum in Switch ---");

        printDayMessage(Day.MONDAY);
        printDayMessage(Day.WEDNESDAY);
        printDayMessage(Day.FRIDAY);
        printDayMessage(Day.SUNDAY);

        System.out.println();


        // ============================================================
        // 3. ITERATING THROUGH ENUM VALUES
        // ============================================================

        System.out.println("--- All Days ---");

        for (Day day : Day.values()) {
            System.out.println(day);
        }

        System.out.println();


        // ============================================================
        // 4. ENUM METHODS
        // ============================================================

        System.out.println("--- Enum Methods ---");

        Day friday = Day.FRIDAY;

        // name() - returns name as string
        System.out.println("Name: " + friday.name());

        // ordinal() - returns position (0-based)
        System.out.println("Ordinal: " + friday.ordinal());

        // valueOf() - convert string to enum
        Day day = Day.valueOf("MONDAY");
        System.out.println("valueOf('MONDAY'): " + day);

        // compareTo() - compare based on ordinal
        System.out.println("Monday compareTo Friday: " + Day.MONDAY.compareTo(Day.FRIDAY));

        System.out.println();


        // ============================================================
        // 5. ENUM WITH FIELDS AND CONSTRUCTOR
        // ============================================================

        System.out.println("--- Enum with Fields ---");

        for (Size size : Size.values()) {
            System.out.println(size + ": " + size.getAbbreviation());
        }

        System.out.println();

        Size mySize = Size.MEDIUM;
        System.out.println("My size: " + mySize);
        System.out.println("Abbreviation: " + mySize.getAbbreviation());

        System.out.println();


        // ============================================================
        // 6. ENUM WITH METHODS
        // ============================================================

        System.out.println("--- Enum with Methods ---");

        for (Planet planet : Planet.values()) {
            System.out.printf("%s: Mass = %.2e kg, Radius = %.2e m%n",
                    planet, planet.getMass(), planet.getRadius());
        }

        System.out.println();

        // Calculate weight on different planets
        double earthWeight = 75.0;  // kg
        System.out.println("If you weigh " + earthWeight + " kg on Earth:");
        for (Planet planet : Planet.values()) {
            System.out.printf("%s: %.2f kg%n", planet,
                    planet.surfaceWeight(earthWeight));
        }

        System.out.println();


        // ============================================================
        // 7. ENUM IN CLASSES
        // ============================================================

        System.out.println("--- Enum in Classes ---");

        TrafficLight light1 = new TrafficLight(Color.RED);
        light1.displayLight();

        TrafficLight light2 = new TrafficLight(Color.GREEN);
        light2.displayLight();

        System.out.println();


        // ============================================================
        // 8. PRACTICAL EXAMPLE: ORDER STATUS
        // ============================================================

        System.out.println("--- Order Status ---");

        Order order1 = new Order(101, OrderStatus.PENDING);
        order1.displayStatus();

        order1.setStatus(OrderStatus.PROCESSING);
        order1.displayStatus();

        order1.setStatus(OrderStatus.SHIPPED);
        order1.displayStatus();

        order1.setStatus(OrderStatus.DELIVERED);
        order1.displayStatus();

        System.out.println();


        // ============================================================
        // 9. PRACTICAL EXAMPLE: PIZZA ORDER
        // ============================================================

        System.out.println("--- Pizza Order ---");

        PizzaOrder pizza1 = new PizzaOrder(PizzaSize.LARGE, Topping.PEPPERONI);
        pizza1.displayOrder();
        System.out.println("Total: $" + pizza1.calculatePrice());

        System.out.println();

        PizzaOrder pizza2 = new PizzaOrder(PizzaSize.MEDIUM, Topping.VEGGIE);
        pizza2.displayOrder();
        System.out.println("Total: $" + pizza2.calculatePrice());

        System.out.println();


        // ============================================================
        // 10. ENUM FOR CONFIGURATION
        // ============================================================

        System.out.println("--- Configuration ---");

        System.out.println("Environment: " + Environment.PRODUCTION);
        System.out.println("URL: " + Environment.PRODUCTION.getUrl());
        System.out.println("Port: " + Environment.PRODUCTION.getPort());

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * ENUM SYNTAX:
         * enum EnumName {
         *     CONSTANT1, CONSTANT2, CONSTANT3
         * }
         *
         * ENUM WITH CONSTRUCTOR:
         * enum EnumName {
         *     CONSTANT1(value1),
         *     CONSTANT2(value2);
         *
         *     private final Type field;
         *
         *     EnumName(Type field) {
         *         this.field = field;
         *     }
         *
         *     public Type getField() {
         *         return field;
         *     }
         * }
         *
         * BUILT-IN METHODS:
         * - values(): Returns array of all constants
         * - valueOf(String): Convert string to enum
         * - name(): Returns constant name as string
         * - ordinal(): Returns position (0-based)
         * - compareTo(): Compare enums by ordinal
         *
         * BENEFITS:
         * - Type safety
         * - Readability
         * - Switch compatibility
         * - Can have fields and methods
         * - Singleton pattern (only one instance per constant)
         *
         * RULES:
         * - Cannot extend classes (already extends Enum)
         * - Can implement interfaces
         * - All enum constants are public static final
         * - Constructor is always private
         * - Cannot instantiate with new
         *
         * COMMON USE CASES:
         * - Days of week, months
         * - Status values (PENDING, APPROVED, REJECTED)
         * - Directions (NORTH, SOUTH, EAST, WEST)
         * - Sizes (SMALL, MEDIUM, LARGE)
         * - Configuration options
         * - Types/categories
         */
    }

    public static void printDayMessage(Day day) {
        switch (day) {
            case MONDAY:
                System.out.println("Start of work week!");
                break;
            case WEDNESDAY:
                System.out.println("Midweek!");
                break;
            case FRIDAY:
                System.out.println("TGIF!");
                break;
            case SATURDAY:
            case SUNDAY:
                System.out.println("Weekend!");
                break;
            default:
                System.out.println("Regular day");
        }
    }
}


// ============================================================
// BASIC ENUM
// ============================================================

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}


// ============================================================
// ENUM WITH FIELDS AND CONSTRUCTOR
// ============================================================

enum Size {
    // Enum constants with constructor arguments
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L"),
    EXTRA_LARGE("XL");

    private final String abbreviation;

    // Constructor (always private)
    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    // Getter method
    public String getAbbreviation() {
        return abbreviation;
    }
}


// ============================================================
// ENUM WITH METHODS
// ============================================================

enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6),
    JUPITER(1.9e+27, 7.1492e7),
    SATURN(5.688e+26, 6.0268e7),
    URANUS(8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

    private final double mass;    // in kilograms
    private final double radius;  // in meters

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    // Calculate surface gravity
    public double surfaceGravity() {
        final double G = 6.67300E-11;  // gravitational constant
        return G * mass / (radius * radius);
    }

    // Calculate weight on this planet
    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }
}


// ============================================================
// ENUM WITH COLORS
// ============================================================

enum Color {
    RED, YELLOW, GREEN
}

class TrafficLight {
    private Color color;

    public TrafficLight(Color color) {
        this.color = color;
    }

    public void displayLight() {
        switch (color) {
            case RED:
                System.out.println("🔴 STOP!");
                break;
            case YELLOW:
                System.out.println("🟡 CAUTION!");
                break;
            case GREEN:
                System.out.println("🟢 GO!");
                break;
        }
    }
}


// ============================================================
// ORDER STATUS ENUM
// ============================================================

enum OrderStatus {
    PENDING("Order received, pending processing"),
    PROCESSING("Order is being processed"),
    SHIPPED("Order has been shipped"),
    DELIVERED("Order delivered"),
    CANCELLED("Order cancelled");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

class Order {
    private int orderId;
    private OrderStatus status;

    public Order(int orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void displayStatus() {
        System.out.println("Order #" + orderId + ": " +
                status + " - " + status.getDescription());
    }
}


// ============================================================
// PIZZA ORDER EXAMPLE
// ============================================================

enum PizzaSize {
    SMALL(8.99),
    MEDIUM(10.99),
    LARGE(12.99),
    EXTRA_LARGE(14.99);

    private final double basePrice;

    PizzaSize(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}

enum Topping {
    CHEESE(0.50),
    PEPPERONI(1.50),
    MUSHROOMS(1.00),
    ONIONS(0.75),
    VEGGIE(2.00);

    private final double price;

    Topping(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

class PizzaOrder {
    private PizzaSize size;
    private Topping topping;

    public PizzaOrder(PizzaSize size, Topping topping) {
        this.size = size;
        this.topping = topping;
    }

    public double calculatePrice() {
        return size.getBasePrice() + topping.getPrice();
    }

    public void displayOrder() {
        System.out.println("Pizza Order:");
        System.out.println("Size: " + size);
        System.out.println("Topping: " + topping);
    }
}


// ============================================================
// ENVIRONMENT CONFIGURATION
// ============================================================

enum Environment {
    DEVELOPMENT("http://localhost", 8080),
    STAGING("http://staging.example.com", 80),
    PRODUCTION("http://www.example.com", 443);

    private final String url;
    private final int port;

    Environment(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }
}
