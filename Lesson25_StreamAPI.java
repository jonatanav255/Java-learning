/*
 * LESSON 25: STREAM API (Java 8+)
 *
 * The Stream API provides a functional approach to process collections of objects.
 * Streams represent a sequence of elements and support operations to process them.
 *
 * KEY CONCEPTS:
 * - Stream: Sequence of elements supporting sequential and parallel operations
 * - Intermediate Operations: Return a stream (filter, map, sorted)
 * - Terminal Operations: Produce a result (forEach, collect, reduce)
 * - Lazy Evaluation: Intermediate operations are not executed until terminal operation
 * - Pipelines: Chain of operations
 *
 * STREAM OPERATIONS:
 * Intermediate (lazy):
 * - filter() - select elements
 * - map() - transform elements
 * - sorted() - sort elements
 * - distinct() - remove duplicates
 * - limit() - limit size
 * - skip() - skip elements
 *
 * Terminal (eager):
 * - forEach() - perform action
 * - collect() - gather into collection
 * - reduce() - combine elements
 * - count() - count elements
 * - anyMatch(), allMatch(), noneMatch()
 * - findFirst(), findAny()
 *
 * BENEFITS:
 * - Functional programming style
 * - More readable code
 * - Easy parallelization
 * - Lazy evaluation (efficient)
 */

import java.util.*;
import java.util.stream.*;

public class Lesson25_StreamAPI {
    public static void main(String[] args) {

        System.out.println("=== STREAM API ===\n");

        // ============================================================
        // 1. CREATING STREAMS
        // ============================================================

        System.out.println("--- Creating Streams ---");

        // From collection
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        Stream<String> stream1 = names.stream();

        // From array
        String[] array = {"A", "B", "C"};
        Stream<String> stream2 = Arrays.stream(array);

        // From values
        Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);

        // Infinite stream
        Stream<Integer> infiniteStream = Stream.iterate(0, n -> n + 1);

        System.out.println("Streams created (not executed yet)");
        System.out.println();


        // ============================================================
        // 2. FILTER - SELECT ELEMENTS
        // ============================================================

        System.out.println("--- filter() ---");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Get even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Even numbers: " + evenNumbers);

        // Get numbers greater than 5
        List<Integer> greaterThan5 = numbers.stream()
                .filter(n -> n > 5)
                .collect(Collectors.toList());

        System.out.println("Greater than 5: " + greaterThan5);

        System.out.println();


        // ============================================================
        // 3. MAP - TRANSFORM ELEMENTS
        // ============================================================

        System.out.println("--- map() ---");

        List<String> words = Arrays.asList("apple", "banana", "cherry");

        // Convert to uppercase
        List<String> upperCase = words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Uppercase: " + upperCase);

        // Get lengths
        List<Integer> lengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println("Lengths: " + lengths);

        // Double each number
        List<Integer> doubled = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());

        System.out.println("Doubled: " + doubled);

        System.out.println();


        // ============================================================
        // 4. SORTED - SORT ELEMENTS
        // ============================================================

        System.out.println("--- sorted() ---");

        List<Integer> unsorted = Arrays.asList(5, 2, 8, 1, 9);

        // Natural order
        List<Integer> sorted = unsorted.stream()
                .sorted()
                .collect(Collectors.toList());

        System.out.println("Sorted: " + sorted);

        // Reverse order
        List<Integer> reverseSorted = unsorted.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        System.out.println("Reverse sorted: " + reverseSorted);

        System.out.println();


        // ============================================================
        // 5. DISTINCT - REMOVE DUPLICATES
        // ============================================================

        System.out.println("--- distinct() ---");

        List<Integer> withDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5, 5);

        List<Integer> unique = withDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Unique: " + unique);

        System.out.println();


        // ============================================================
        // 6. LIMIT AND SKIP
        // ============================================================

        System.out.println("--- limit() and skip() ---");

        List<Integer> first5 = numbers.stream()
                .limit(5)
                .collect(Collectors.toList());

        System.out.println("First 5: " + first5);

        List<Integer> skip5 = numbers.stream()
                .skip(5)
                .collect(Collectors.toList());

        System.out.println("Skip first 5: " + skip5);

        System.out.println();


        // ============================================================
        // 7. forEach - PERFORM ACTION
        // ============================================================

        System.out.println("--- forEach() ---");

        System.out.print("Numbers: ");
        numbers.stream()
                .forEach(n -> System.out.print(n + " "));

        System.out.println("\n");


        // ============================================================
        // 8. COLLECT - GATHER RESULTS
        // ============================================================

        System.out.println("--- collect() ---");

        // To List
        List<String> list = words.stream()
                .collect(Collectors.toList());

        // To Set
        Set<Integer> set = numbers.stream()
                .collect(Collectors.toSet());

        // Join strings
        String joined = words.stream()
                .collect(Collectors.joining(", "));

        System.out.println("Joined: " + joined);

        System.out.println();


        // ============================================================
        // 9. REDUCE - COMBINE ELEMENTS
        // ============================================================

        System.out.println("--- reduce() ---");

        // Sum
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        System.out.println("Sum: " + sum);

        // Product
        int product = numbers.stream()
                .reduce(1, (a, b) -> a * b);

        System.out.println("Product (1-10): " + product);

        // Max
        Optional<Integer> max = numbers.stream()
                .reduce((a, b) -> a > b ? a : b);

        max.ifPresent(m -> System.out.println("Max: " + m));

        System.out.println();


        // ============================================================
        // 10. COUNT, MIN, MAX, AVERAGE
        // ============================================================

        System.out.println("--- Statistics ---");

        long count = numbers.stream().count();
        System.out.println("Count: " + count);

        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        min.ifPresent(m -> System.out.println("Min: " + m));

        Optional<Integer> maximum = numbers.stream().max(Integer::compareTo);
        maximum.ifPresent(m -> System.out.println("Max: " + m));

        // Average (need to convert to IntStream)
        double average = numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        System.out.println("Average: " + average);

        System.out.println();


        // ============================================================
        // 11. MATCHING
        // ============================================================

        System.out.println("--- Matching ---");

        boolean anyEven = numbers.stream()
                .anyMatch(n -> n % 2 == 0);

        System.out.println("Any even number? " + anyEven);

        boolean allPositive = numbers.stream()
                .allMatch(n -> n > 0);

        System.out.println("All positive? " + allPositive);

        boolean noneNegative = numbers.stream()
                .noneMatch(n -> n < 0);

        System.out.println("None negative? " + noneNegative);

        System.out.println();


        // ============================================================
        // 12. FIND FIRST / FIND ANY
        // ============================================================

        System.out.println("--- Finding ---");

        Optional<Integer> first = numbers.stream()
                .filter(n -> n > 5)
                .findFirst();

        first.ifPresent(n -> System.out.println("First number > 5: " + n));

        System.out.println();


        // ============================================================
        // 13. CHAINING OPERATIONS
        // ============================================================

        System.out.println("--- Chaining Operations ---");

        List<Integer> result = numbers.stream()
                .filter(n -> n % 2 == 0)    // Get even numbers
                .map(n -> n * 2)             // Double them
                .sorted()                    // Sort
                .limit(3)                    // Take first 3
                .collect(Collectors.toList());

        System.out.println("Even, doubled, sorted, first 3: " + result);

        System.out.println();


        // ============================================================
        // 14. PRACTICAL EXAMPLE: PERSON LIST
        // ============================================================

        System.out.println("--- Practical Example ---");

        List<Person> people = Arrays.asList(
            new Person("Alice", 25, 50000),
            new Person("Bob", 30, 60000),
            new Person("Charlie", 20, 40000),
            new Person("Diana", 35, 70000),
            new Person("Eve", 28, 55000)
        );

        // Get names of people over 25
        System.out.println("People over 25:");
        people.stream()
                .filter(p -> p.age > 25)
                .map(p -> p.name)
                .forEach(System.out::println);

        System.out.println();

        // Average salary
        double avgSalary = people.stream()
                .mapToDouble(p -> p.salary)
                .average()
                .orElse(0.0);

        System.out.println("Average salary: $" + avgSalary);

        // Highest paid person
        Optional<Person> highestPaid = people.stream()
                .max(Comparator.comparing(p -> p.salary));

        highestPaid.ifPresent(p -> System.out.println("Highest paid: " + p.name));

        // Group by age range
        Map<String, Long> ageGroups = people.stream()
                .collect(Collectors.groupingBy(
                    p -> p.age < 25 ? "Young" : p.age < 30 ? "Adult" : "Senior",
                    Collectors.counting()
                ));

        System.out.println("Age groups: " + ageGroups);

        System.out.println();


        // ============================================================
        // 15. PARALLEL STREAMS
        // ============================================================

        System.out.println("--- Parallel Streams ---");

        // Sequential stream
        long start = System.currentTimeMillis();
        long sumSequential = Stream.iterate(1, n -> n + 1)
                .limit(1000000)
                .reduce(0, Integer::sum);
        long timeSequential = System.currentTimeMillis() - start;

        // Parallel stream (uses multiple threads)
        start = System.currentTimeMillis();
        long sumParallel = Stream.iterate(1, n -> n + 1)
                .limit(1000000)
                .parallel()
                .reduce(0, Integer::sum);
        long timeParallel = System.currentTimeMillis() - start;

        System.out.println("Sequential: " + timeSequential + "ms");
        System.out.println("Parallel: " + timeParallel + "ms");

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * STREAM PIPELINE:
         * collection.stream()
         *     .intermediateOp1()
         *     .intermediateOp2()
         *     .terminalOp()
         *
         * INTERMEDIATE OPERATIONS (return Stream):
         * - filter(Predicate)      - select elements
         * - map(Function)          - transform elements
         * - flatMap(Function)      - flatten nested streams
         * - distinct()             - remove duplicates
         * - sorted()               - sort elements
         * - peek(Consumer)         - perform action (debugging)
         * - limit(long)            - limit size
         * - skip(long)             - skip elements
         *
         * TERMINAL OPERATIONS (produce result):
         * - forEach(Consumer)      - perform action
         * - collect(Collector)     - gather into collection
         * - reduce(BinaryOperator) - combine elements
         * - count()                - count elements
         * - min/max(Comparator)    - find min/max
         * - anyMatch(Predicate)    - check if any match
         * - allMatch(Predicate)    - check if all match
         * - noneMatch(Predicate)   - check if none match
         * - findFirst()            - find first element
         * - findAny()              - find any element
         *
         * BENEFITS:
         * - Functional programming style
         * - More readable and concise
         * - Easy to parallelize (.parallel())
         * - Lazy evaluation (efficient)
         * - Composable operations
         *
         * WHEN TO USE:
         * - Filtering collections
         * - Transforming data
         * - Aggregating data
         * - Processing sequences
         *
         * COMMON PATTERNS:
         * - Filter + Map + Collect
         * - Filter + Count
         * - Map + Reduce
         * - Filter + FindFirst
         * - Sorted + Limit
         */
    }
}


// ============================================================
// HELPER CLASS
// ============================================================

class Person {
    String name;
    int age;
    double salary;

    Person(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return name + " (" + age + ", $" + salary + ")";
    }
}
