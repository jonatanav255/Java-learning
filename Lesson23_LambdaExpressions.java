/*
 * LESSON 23: LAMBDA EXPRESSIONS (Java 8+)
 *
 * Lambda expressions provide a clear and concise way to represent functional interfaces.
 * They enable functional programming in Java.
 *
 * KEY CONCEPTS:
 * - Lambda Expression: Short block of code that takes parameters and returns a value
 * - Functional Interface: Interface with exactly ONE abstract method
 * - Method Reference: Shorthand notation for lambda expressions
 * - Anonymous function: Lambda is like an anonymous method
 *
 * SYNTAX:
 * (parameters) -> expression
 * (parameters) -> { statements; }
 *
 * BENEFITS:
 * - Less code (more concise)
 * - Easier to read (when used appropriately)
 * - Enables functional programming
 * - Works well with Stream API
 *
 * COMMON FUNCTIONAL INTERFACES:
 * - Runnable: () -> void
 * - Consumer<T>: T -> void
 * - Supplier<T>: () -> T
 * - Function<T,R>: T -> R
 * - Predicate<T>: T -> boolean
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

public class Lesson23_LambdaExpressions {
    public static void main(String[] args) {

        System.out.println("=== LAMBDA EXPRESSIONS ===\n");

        // ============================================================
        // 1. LAMBDA BASICS
        // ============================================================

        System.out.println("--- Lambda Basics ---");

        // Old way: Anonymous class
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from anonymous class");
            }
        };
        r1.run();

        // New way: Lambda expression
        Runnable r2 = () -> System.out.println("Hello from lambda");
        r2.run();

        System.out.println();


        // ============================================================
        // 2. LAMBDA WITH PARAMETERS
        // ============================================================

        System.out.println("--- Lambda with Parameters ---");

        // Single parameter (parentheses optional)
        Greeting g1 = name -> System.out.println("Hello, " + name);
        g1.greet("Alice");

        // Multiple parameters
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;

        System.out.println("5 + 3 = " + add.calculate(5, 3));
        System.out.println("5 * 3 = " + multiply.calculate(5, 3));

        System.out.println();


        // ============================================================
        // 3. LAMBDA WITH BLOCK BODY
        // ============================================================

        System.out.println("--- Lambda with Block ---");

        Calculator complex = (a, b) -> {
            System.out.println("Calculating...");
            int result = a + b;
            System.out.println("Result: " + result);
            return result;
        };

        complex.calculate(10, 5);

        System.out.println();


        // ============================================================
        // 4. FUNCTIONAL INTERFACES
        // ============================================================

        System.out.println("--- Functional Interfaces ---");

        // Consumer<T> - takes input, returns nothing
        Consumer<String> printer = str -> System.out.println("Printing: " + str);
        printer.accept("Hello World");

        // Supplier<T> - takes nothing, returns output
        Supplier<Double> randomSupplier = () -> Math.random();
        System.out.println("Random number: " + randomSupplier.get());

        // Function<T, R> - takes input, returns output
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("Length of 'Java': " + stringLength.apply("Java"));

        // Predicate<T> - takes input, returns boolean
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 10 even? " + isEven.test(10));
        System.out.println("Is 7 even? " + isEven.test(7));

        System.out.println();


        // ============================================================
        // 5. LAMBDA WITH LISTS
        // ============================================================

        System.out.println("--- Lambda with Lists ---");

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Diana");

        // forEach with lambda
        System.out.println("Using forEach:");
        names.forEach(name -> System.out.println("- " + name));

        System.out.println();

        // removeIf with lambda
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        numbers.removeIf(n -> n % 2 == 0);  // Remove even numbers
        System.out.println("Odd numbers only: " + numbers);

        System.out.println();


        // ============================================================
        // 6. METHOD REFERENCES
        // ============================================================

        System.out.println("--- Method References ---");

        List<String> words = Arrays.asList("apple", "banana", "cherry");

        // Lambda
        words.forEach(word -> System.out.println(word));

        // Method reference (shorthand)
        words.forEach(System.out::println);

        // Static method reference
        List<String> numbers2 = Arrays.asList("1", "2", "3");
        numbers2.forEach(Lesson23_LambdaExpressions::printDouble);

        System.out.println();


        // ============================================================
        // 7. LAMBDA WITH COMPARATOR
        // ============================================================

        System.out.println("--- Lambda with Comparator ---");

        List<Person> people = Arrays.asList(
            new Person("Alice", 25),
            new Person("Bob", 30),
            new Person("Charlie", 20)
        );

        // Sort by age (ascending)
        people.sort((p1, p2) -> p1.age - p2.age);
        System.out.println("Sorted by age:");
        people.forEach(System.out::println);

        System.out.println();

        // Sort by name (using Comparator.comparing)
        people.sort(java.util.Comparator.comparing(p -> p.name));
        System.out.println("Sorted by name:");
        people.forEach(System.out::println);

        System.out.println();


        // ============================================================
        // 8. PRACTICAL EXAMPLE: FILTERING
        // ============================================================

        System.out.println("--- Filtering with Lambda ---");

        List<Product> products = Arrays.asList(
            new Product("Laptop", 1200),
            new Product("Mouse", 25),
            new Product("Keyboard", 75),
            new Product("Monitor", 300)
        );

        // Filter expensive products (>= 100)
        System.out.println("Expensive products:");
        filterProducts(products, p -> p.price >= 100)
            .forEach(System.out::println);

        System.out.println();

        // Filter cheap products (< 100)
        System.out.println("Affordable products:");
        filterProducts(products, p -> p.price < 100)
            .forEach(System.out::println);

        System.out.println();


        // ============================================================
        // 9. CHAINING FUNCTIONAL INTERFACES
        // ============================================================

        System.out.println("--- Chaining ---");

        // Chaining predicates
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> isEvenNumber = n -> n % 2 == 0;
        Predicate<Integer> isPositiveEven = isPositive.and(isEvenNumber);

        System.out.println("Is 4 positive and even? " + isPositiveEven.test(4));
        System.out.println("Is -4 positive and even? " + isPositiveEven.test(-4));

        // Chaining functions
        Function<String, String> addPrefix = str -> "Hello, " + str;
        Function<String, String> addSuffix = str -> str + "!";
        Function<String, String> complete = addPrefix.andThen(addSuffix);

        System.out.println(complete.apply("Java"));

        System.out.println();


        // ============================================================
        // 10. LAMBDA IN THREADS
        // ============================================================

        System.out.println("--- Lambda in Threads ---");

        // Old way
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 1 running (old way)");
            }
        });

        // Lambda way
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 running (lambda way)");
        });

        thread1.start();
        thread2.start();

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * LAMBDA SYNTAX:
         * () -> expression                    // No parameters
         * (x) -> expression                   // One parameter
         * x -> expression                     // One parameter (no parentheses)
         * (x, y) -> expression                // Multiple parameters
         * (x, y) -> { statements; }           // Block body
         *
         * METHOD REFERENCE TYPES:
         * 1. Static method: ClassName::staticMethod
         * 2. Instance method: object::instanceMethod
         * 3. Instance method of arbitrary object: ClassName::instanceMethod
         * 4. Constructor: ClassName::new
         *
         * COMMON FUNCTIONAL INTERFACES:
         * Consumer<T>:     T -> void
         * Supplier<T>:     () -> T
         * Function<T,R>:   T -> R
         * Predicate<T>:    T -> boolean
         * UnaryOperator<T>: T -> T
         * BinaryOperator<T>: (T,T) -> T
         *
         * FUNCTIONAL INTERFACE RULES:
         * - Must have exactly ONE abstract method
         * - Can have multiple default/static methods
         * - Use @FunctionalInterface annotation (optional but recommended)
         *
         * WHEN TO USE LAMBDAS:
         * - Implementing functional interfaces
         * - Short, simple operations
         * - forEach, map, filter operations
         * - Sorting and comparing
         * - Event handlers
         * - Thread tasks
         *
         * BENEFITS:
         * - Less boilerplate code
         * - More readable (when simple)
         * - Encourages functional programming
         * - Works well with streams
         *
         * LIMITATIONS:
         * - Can be harder to debug
         * - Can reduce readability if complex
         * - Cannot modify external variables (must be final or effectively final)
         */
    }

    // Helper method for method reference
    public static void printDouble(String str) {
        int num = Integer.parseInt(str);
        System.out.println(num * 2);
    }

    // Helper method for filtering
    public static List<Product> filterProducts(List<Product> products, Predicate<Product> condition) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (condition.test(product)) {
                result.add(product);
            }
        }
        return result;
    }
}


// ============================================================
// FUNCTIONAL INTERFACES
// ============================================================

@FunctionalInterface
interface Greeting {
    void greet(String name);
}

@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}


// ============================================================
// HELPER CLASSES
// ============================================================

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

class Product {
    String name;
    double price;

    Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ": $" + price;
    }
}
