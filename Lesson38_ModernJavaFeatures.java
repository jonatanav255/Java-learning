/*
 * LESSON 38: MODERN JAVA FEATURES (Java 14-21)
 *
 * Java has evolved significantly with new features that make code more concise,
 * readable, and performant. This lesson covers modern features from Java 14-21.
 *
 * KEY FEATURES COVERED:
 * - var (Java 10): Local variable type inference
 * - Records (Java 16): Immutable data classes
 * - Sealed Classes (Java 17): Restricted inheritance
 * - Pattern Matching for instanceof (Java 16)
 * - Pattern Matching for switch (Java 21)
 * - Text Blocks (Java 15): Multiline strings
 * - Switch Expressions (Java 14)
 * - Virtual Threads (Java 21, Project Loom)
 *
 * JAVA VERSION TIMELINE:
 * Java 8 (2014): Lambdas, Streams, Optional
 * Java 9 (2017): Modules
 * Java 10 (2018): var keyword
 * Java 11 (2018): LTS, String methods
 * Java 14 (2020): Switch expressions, Records (preview)
 * Java 15 (2020): Text blocks
 * Java 16 (2021): Records, Pattern matching instanceof
 * Java 17 (2021): LTS, Sealed classes
 * Java 21 (2023): LTS, Virtual threads, Pattern matching switch
 *
 * LTS VERSIONS:
 * - Java 8 (until 2030+)
 * - Java 11 (until 2026)
 * - Java 17 (until 2029)
 * - Java 21 (until 2031)
 *
 * BENEFITS:
 * - Less boilerplate code
 * - Better type safety
 * - Improved readability
 * - Better performance
 * - More expressive code
 *
 * NOTE: Some features shown here require Java 17+ to compile.
 * The code demonstrates the syntax and concepts even if your
 * version doesn't support all features.
 */

import java.util.*;
import java.util.stream.*;

public class Lesson38_ModernJavaFeatures {
    public static void main(String[] args) {

        System.out.println("=== MODERN JAVA FEATURES (Java 14-21) ===\n");

        // ============================================================
        // 1. VAR KEYWORD (Java 10)
        // ============================================================

        System.out.println("--- var Keyword ---");

        // Type inference for local variables
        var message = "Hello, Modern Java!"; // String
        var number = 42; // int
        var list = new ArrayList<String>(); // ArrayList<String>
        var map = new HashMap<String, Integer>(); // HashMap<String, Integer>

        System.out.println("message type: String");
        System.out.println("number type: int");
        System.out.println("list type: ArrayList<String>");

        // var with loops
        var numbers = List.of(1, 2, 3, 4, 5);
        for (var num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        // LIMITATIONS:
        // var cannot be used for:
        // - Fields
        // - Method parameters
        // - Return types
        // - Without initializer (var x;)
        // - Initialized to null (var x = null;)

        System.out.println("""
                var BEST PRACTICES:
                ✓ Use when type is obvious from right side
                ✓ Use for long generic types
                ✓ Use in loops
                ✗ Don't use if it reduces readability
                ✗ Don't use with ambiguous types
                """);

        System.out.println();


        // ============================================================
        // 2. TEXT BLOCKS (Java 15)
        // ============================================================

        System.out.println("--- Text Blocks ---");

        // Old way (before Java 15)
        String oldJson = "{\n" +
                "  \"name\": \"John\",\n" +
                "  \"age\": 30\n" +
                "}";

        // New way with text blocks
        String newJson = """
                {
                  "name": "John",
                  "age": 30
                }
                """;

        System.out.println("JSON with text block:");
        System.out.println(newJson);

        // SQL example
        String sql = """
                SELECT id, name, email
                FROM users
                WHERE age > 18
                ORDER BY name
                """;

        System.out.println("SQL query:");
        System.out.println(sql);

        // HTML example
        String html = """
                <html>
                    <body>
                        <h1>Welcome</h1>
                        <p>Hello, World!</p>
                    </body>
                </html>
                """;

        System.out.println("HTML:");
        System.out.println(html);

        System.out.println();


        // ============================================================
        // 3. SWITCH EXPRESSIONS (Java 14)
        // ============================================================

        System.out.println("--- Switch Expressions ---");

        // Old switch statement
        String dayType1;
        int day = 3;
        switch (day) {
            case 1:
            case 7:
                dayType1 = "Weekend";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                dayType1 = "Weekday";
                break;
            default:
                dayType1 = "Invalid";
        }
        System.out.println("Old switch: " + dayType1);

        // New switch expression
        String dayType2 = switch (day) {
            case 1, 7 -> "Weekend";
            case 2, 3, 4, 5, 6 -> "Weekday";
            default -> "Invalid";
        };
        System.out.println("New switch: " + dayType2);

        // Switch with yield
        String result = switch (day) {
            case 1, 7 -> {
                System.out.println("  It's the weekend!");
                yield "Weekend";
            }
            case 2, 3, 4, 5, 6 -> {
                System.out.println("  It's a weekday.");
                yield "Weekday";
            }
            default -> "Invalid";
        };
        System.out.println("Result: " + result);

        System.out.println();


        // ============================================================
        // 4. RECORDS (Java 16)
        // ============================================================

        System.out.println("--- Records ---");

        // Create records
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);

        System.out.println("Point: " + p1);
        System.out.println("x: " + p1.x() + ", y: " + p1.y());
        System.out.println("p1 equals p2: " + p1.equals(p2));
        System.out.println("p1 hashCode: " + p1.hashCode());

        // Records in action
        Person person = new Person("Alice", 30);
        System.out.println("Person: " + person);

        // Records with validation
        try {
            EmailRecord email = new EmailRecord("alice@example.com");
            System.out.println("Valid email: " + email);

            // This will throw exception
            // EmailRecord invalid = new EmailRecord("not-an-email");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email caught");
        }

        System.out.println("""

                RECORDS:
                - Immutable data carriers
                - Automatic: constructor, getters, equals, hashCode, toString
                - Final class (cannot be extended)
                - All fields are final
                - Compact constructor for validation
                - Can implement interfaces
                - Can have static fields/methods
                - Cannot extend other classes
                """);

        System.out.println();


        // ============================================================
        // 5. PATTERN MATCHING FOR INSTANCEOF (Java 16)
        // ============================================================

        System.out.println("--- Pattern Matching for instanceof ---");

        Object obj = "Hello, Pattern Matching!";

        // Old way
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println("Old way - Length: " + str.length());
        }

        // New way with pattern matching
        if (obj instanceof String str) {
            System.out.println("New way - Length: " + str.length());
            System.out.println("Uppercase: " + str.toUpperCase());
        }

        // Multiple patterns
        Object value = 42;
        if (value instanceof Integer num && num > 0) {
            System.out.println("Positive number: " + num);
        }

        // In methods
        String description = describe(123);
        System.out.println(description);
        description = describe("Hello");
        System.out.println(description);
        description = describe(List.of(1, 2, 3));
        System.out.println(description);

        System.out.println();


        // ============================================================
        // 6. SEALED CLASSES (Java 17)
        // ============================================================

        System.out.println("--- Sealed Classes ---");

        System.out.println("""
                Sealed classes restrict which classes can extend them.

                EXAMPLE:
                public sealed class Shape
                    permits Circle, Rectangle, Triangle {
                }

                public final class Circle extends Shape { }
                public final class Rectangle extends Shape { }
                public non-sealed class Triangle extends Shape { }

                KEYWORDS:
                - sealed: Declares a sealed class
                - permits: Lists allowed subclasses
                - final: Cannot be extended further
                - sealed: Can be extended with restrictions
                - non-sealed: Opens hierarchy again

                BENEFITS:
                - Controlled inheritance
                - Exhaustive switch (compiler knows all types)
                - Better pattern matching
                - Domain modeling
                - API design

                USE CASES:
                - State machines
                - AST (Abstract Syntax Trees)
                - Domain models
                - Type hierarchies
                """);

        // Example usage (conceptual)
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);

        System.out.println("Circle area: " + calculateArea(circle));
        System.out.println("Rectangle area: " + calculateArea(rectangle));

        System.out.println();


        // ============================================================
        // 7. PATTERN MATCHING FOR SWITCH (Java 21)
        // ============================================================

        System.out.println("--- Pattern Matching for Switch ---");

        System.out.println("""
                Pattern matching in switch (Java 21):

                EXAMPLE:
                String formatted = switch (obj) {
                    case Integer i -> String.format("int %d", i);
                    case Long l    -> String.format("long %d", l);
                    case Double d  -> String.format("double %f", d);
                    case String s  -> String.format("String %s", s);
                    default        -> obj.toString();
                };

                WITH GUARDS:
                String result = switch (obj) {
                    case String s when s.length() > 5 -> "Long string";
                    case String s -> "Short string";
                    case Integer i when i > 0 -> "Positive";
                    case Integer i -> "Non-positive";
                    default -> "Other";
                };

                RECORD PATTERNS:
                String msg = switch (point) {
                    case Point(int x, int y) when x == y -> "Diagonal";
                    case Point(int x, int y) when x == 0 -> "On Y-axis";
                    case Point(int x, int y) when y == 0 -> "On X-axis";
                    default -> "General point";
                };

                BENEFITS:
                - More expressive
                - Type-safe
                - Less casting
                - Exhaustiveness checking
                - Guards for conditions
                """);

        // Simulate pattern matching (limited in older Java)
        Object testObj = 42;
        String formattedValue = formatValue(testObj);
        System.out.println("Formatted: " + formattedValue);

        System.out.println();


        // ============================================================
        // 8. VIRTUAL THREADS (Java 21, Project Loom)
        // ============================================================

        System.out.println("--- Virtual Threads (Project Loom) ---");

        System.out.println("""
                Virtual Threads revolutionize Java concurrency!

                TRADITIONAL THREADS:
                - Platform threads (OS threads)
                - Expensive (1MB stack each)
                - Limited (~few thousand max)
                - Context switching overhead

                VIRTUAL THREADS:
                - Lightweight (few KB)
                - Millions possible
                - Managed by JVM
                - No pooling needed
                - Perfect for I/O-bound tasks

                CREATING VIRTUAL THREADS:
                // Single virtual thread
                Thread vThread = Thread.ofVirtual().start(() -> {
                    System.out.println("Running in virtual thread");
                });

                // With ExecutorService
                try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                    executor.submit(() -> {
                        // Task code
                    });
                }

                // From platform thread
                Thread.startVirtualThread(() -> {
                    // Task code
                });

                WHEN TO USE:
                ✓ I/O-bound operations
                ✓ Many concurrent requests
                ✓ Blocking operations
                ✓ Microservices
                ✓ REST API servers
                ✗ CPU-intensive tasks
                ✗ Need ThreadLocal heavily

                BENEFITS:
                - Simpler concurrency model
                - Better resource utilization
                - No need for async frameworks
                - Same APIs as regular threads
                - Massive scalability

                EXAMPLE USE CASE:
                Handle 1 million concurrent HTTP requests
                without complex async code!
                """);

        System.out.println();


        // ============================================================
        // 9. STRING ENHANCEMENTS
        // ============================================================

        System.out.println("--- String Enhancements ---");

        String text = "  Hello, World!  ";

        // Java 11+ methods
        System.out.println("isBlank: " + "   ".isBlank());
        System.out.println("strip: '" + text.strip() + "'");
        System.out.println("stripLeading: '" + text.stripLeading() + "'");
        System.out.println("stripTrailing: '" + text.stripTrailing() + "'");

        // repeat
        System.out.println("repeat: " + "Java ".repeat(3));

        // lines
        String multiline = "Line 1\nLine 2\nLine 3";
        System.out.println("lines count: " + multiline.lines().count());

        // indent (Java 12+)
        String indented = "Hello".indent(4);
        System.out.println("indented: '" + indented + "'");

        // transform (Java 12+)
        String transformed = "hello".transform(String::toUpperCase);
        System.out.println("transformed: " + transformed);

        System.out.println();


        // ============================================================
        // 10. COLLECTION FACTORY METHODS (Java 9+)
        // ============================================================

        System.out.println("--- Collection Factory Methods ---");

        // Immutable collections
        List<String> immutableList = List.of("A", "B", "C");
        Set<Integer> immutableSet = Set.of(1, 2, 3, 4, 5);
        Map<String, Integer> immutableMap = Map.of(
                "One", 1,
                "Two", 2,
                "Three", 3
        );

        System.out.println("List: " + immutableList);
        System.out.println("Set: " + immutableSet);
        System.out.println("Map: " + immutableMap);

        // Map.ofEntries for more than 10 entries
        Map<String, String> largeMap = Map.ofEntries(
                Map.entry("key1", "value1"),
                Map.entry("key2", "value2"),
                Map.entry("key3", "value3")
        );
        System.out.println("Large map: " + largeMap);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * MODERN JAVA EVOLUTION:
         *
         * PRODUCTIVITY FEATURES:
         * - var: Less typing, cleaner code
         * - Records: Immutable data classes
         * - Text blocks: Better multiline strings
         * - Switch expressions: More concise
         *
         * TYPE SAFETY:
         * - Pattern matching: Less casting
         * - Sealed classes: Controlled inheritance
         * - Records: Immutable by default
         *
         * PERFORMANCE:
         * - Virtual threads: Massive scalability
         * - Compact strings
         * - JIT improvements
         *
         * MIGRATION STRATEGY:
         * 1. Stay on LTS versions (8, 11, 17, 21)
         * 2. Test thoroughly before upgrading
         * 3. Update dependencies
         * 4. Use jdeps for compatibility
         * 5. Gradual adoption of new features
         *
         * FEATURE ADOPTION:
         * High Priority:
         * - var (readability)
         * - Text blocks (SQL, JSON, HTML)
         * - Switch expressions (conciseness)
         * - Records (data classes)
         *
         * Medium Priority:
         * - Pattern matching instanceof
         * - Collection factories
         * - String enhancements
         *
         * Long-term:
         * - Virtual threads (Java 21+)
         * - Sealed classes (domain modeling)
         * - Pattern matching switch (Java 21+)
         *
         * BEST PRACTICES:
         * 1. Use LTS versions in production
         * 2. Adopt features gradually
         * 3. Use var judiciously
         * 4. Prefer records for DTOs
         * 5. Use text blocks for multiline strings
         * 6. Use switch expressions for conciseness
         * 7. Consider virtual threads for I/O
         * 8. Test on target Java version
         *
         * WHEN TO UPGRADE:
         * Reasons to upgrade:
         * ✓ Better performance
         * ✓ New features needed
         * ✓ Security patches
         * ✓ LTS support ending
         * ✓ Dependency requirements
         *
         * Reasons to wait:
         * - Stable production system
         * - Dependencies not ready
         * - Major refactoring needed
         * - Team training required
         *
         * JAVA 21 HIGHLIGHTS:
         * - Virtual Threads (Project Loom)
         * - Pattern Matching for switch
         * - Record Patterns
         * - Sequenced Collections
         * - String Templates (preview)
         *
         * FUTURE FEATURES:
         * - Project Valhalla (value types)
         * - Project Panama (foreign functions)
         * - Project Amber (language features)
         */
    }

    // Helper method for pattern matching example
    static String describe(Object obj) {
        if (obj instanceof Integer num) {
            return "Integer: " + num;
        } else if (obj instanceof String str) {
            return "String of length " + str.length();
        } else if (obj instanceof List<?> list) {
            return "List with " + list.size() + " elements";
        }
        return "Unknown type";
    }

    // Helper method for formatting values
    static String formatValue(Object obj) {
        if (obj instanceof Integer i) {
            return String.format("int %d", i);
        } else if (obj instanceof Double d) {
            return String.format("double %.2f", d);
        } else if (obj instanceof String s) {
            return String.format("String '%s'", s);
        }
        return obj.toString();
    }

    // Helper for sealed classes example
    static double calculateArea(Shape shape) {
        if (shape instanceof Circle c) {
            return Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle r) {
            return r.width() * r.height();
        }
        return 0.0;
    }
}


// ============================================================
// RECORD EXAMPLES (Java 16+)
// ============================================================

// Simple record
record Point(int x, int y) {
    // Compact constructor for validation
    public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }
}

// Record with custom methods
record Person(String name, int age) {
    public boolean isAdult() {
        return age >= 18;
    }

    public static Person of(String name, int age) {
        return new Person(name, age);
    }
}

// Record with validation
record EmailRecord(String email) {
    public EmailRecord {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}


// ============================================================
// SEALED CLASS EXAMPLE (Java 17+)
// ============================================================

// Note: In older Java versions, we simulate with regular inheritance
// In Java 17+, you would use: public sealed class Shape permits Circle, Rectangle

abstract class Shape {
    // In Java 17+: sealed class with permits
}

// Note: Records cannot extend classes, so we use regular classes here
final class Circle extends Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double radius() {
        return radius;
    }
}

final class Rectangle extends Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }
}
