/*
 * LESSON 40: PERFORMANCE TUNING & JMH
 *
 * Performance optimization is crucial for production systems.
 * This lesson covers performance best practices and benchmarking with JMH.
 *
 * KEY CONCEPTS:
 * - Profiling: Measure before optimizing
 * - Benchmarking: Accurate performance measurement
 * - JMH: Java Microbenchmark Harness (official tool)
 * - JIT: Just-In-Time compilation
 * - Warm-up: Let JIT optimize before measuring
 * - Dead Code Elimination: JIT removes unused code
 *
 * PERFORMANCE PRINCIPLES:
 * 1. Measure first, optimize later
 * 2. Profile in production-like environment
 * 3. Focus on bottlenecks
 * 4. Premature optimization is evil
 * 5. Readability > micro-optimizations
 *
 * JMH (Java Microbenchmark Harness):
 * - Official benchmarking framework
 * - Handles JIT warm-up
 * - Prevents dead code elimination
 * - Statistical analysis
 * - Accurate measurements
 *
 * COMMON PERFORMANCE ISSUES:
 * - Inefficient algorithms (O(n²) vs O(n log n))
 * - Excessive object creation
 * - String concatenation in loops
 * - Unnecessary synchronization
 * - Reflection overuse
 * - Large object graphs
 * - Memory leaks
 *
 * PROFILING TOOLS:
 * - JProfiler (commercial)
 * - YourKit (commercial)
 * - VisualVM (free)
 * - Java Flight Recorder (built-in)
 * - async-profiler (free, low overhead)
 *
 * NOTE: This lesson demonstrates concepts and simple benchmarks.
 * For production use, add JMH dependency:
 *
 * Maven:
 * <dependency>
 *     <groupId>org.openjdk.jmh</groupId>
 *     <artifactId>jmh-core</artifactId>
 *     <version>1.37</version>
 * </dependency>
 *
 * Gradle:
 * implementation 'org.openjdk.jmh:jmh-core:1.37'
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Lesson40_Performance_JMH {
    public static void main(String[] args) {

        System.out.println("=== PERFORMANCE TUNING & JMH ===\n");

        // ============================================================
        // 1. MEASURING EXECUTION TIME
        // ============================================================

        System.out.println("--- Measuring Execution Time ---");

        // Simple timing (not accurate for benchmarks)
        long start = System.nanoTime();

        // Do some work
        int sum = 0;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }

        long duration = System.nanoTime() - start;
        System.out.println("Naive timing: " + duration / 1_000_000.0 + " ms");
        System.out.println("Result: " + sum);

        System.out.println("""

                WHY SIMPLE TIMING IS INACCURATE:
                - JIT hasn't warmed up
                - GC can interfere
                - OS scheduling variations
                - Cache effects
                - Dead code elimination
                - One-time measurement (no statistics)

                SOLUTION: Use JMH for accurate benchmarks
                """);

        System.out.println();


        // ============================================================
        // 2. COMMON PERFORMANCE PITFALLS
        // ============================================================

        System.out.println("--- Common Performance Pitfalls ---");

        // PITFALL 1: String concatenation in loops
        System.out.println("1. String concatenation:");

        // Bad: O(n²) due to string immutability
        String bad = "";
        for (int i = 0; i < 1000; i++) {
            bad += i; // Creates new String each time
        }

        // Good: O(n)
        StringBuilder good = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            good.append(i);
        }
        System.out.println("  StringBuilder is much faster for loops");

        // PITFALL 2: Unnecessary object creation
        System.out.println("2. Object creation:");

        // Bad: Creates many Integer objects
        List<Integer> badList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            badList.add(new Integer(i)); // Deprecated, wasteful
        }

        // Good: Uses autoboxing cache (-128 to 127)
        List<Integer> goodList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            goodList.add(i); // Reuses cached Integer objects
        }
        System.out.println("  Autoboxing uses cache for small integers");

        // PITFALL 3: Wrong data structure
        System.out.println("3. Data structure choice:");
        System.out.println("  ArrayList: Fast random access, slow insert/delete");
        System.out.println("  LinkedList: Slow random access, fast insert/delete at ends");
        System.out.println("  HashMap: O(1) lookup, unordered");
        System.out.println("  TreeMap: O(log n) lookup, sorted");

        // PITFALL 4: Excessive synchronization
        System.out.println("4. Synchronization:");
        System.out.println("  Synchronized blocks are expensive");
        System.out.println("  Use concurrent collections instead");
        System.out.println("  Minimize critical section size");

        System.out.println();


        // ============================================================
        // 3. ALGORITHM COMPLEXITY MATTERS
        // ============================================================

        System.out.println("--- Algorithm Complexity ---");

        int[] arr = new int[10000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }

        // O(n²) Bubble Sort
        long t1 = System.nanoTime();
        int[] arr1 = arr.clone();
        bubbleSort(arr1);
        long bubbleTime = System.nanoTime() - t1;

        // O(n log n) Arrays.sort (TimSort)
        long t2 = System.nanoTime();
        int[] arr2 = arr.clone();
        Arrays.sort(arr2);
        long sortTime = System.nanoTime() - t2;

        System.out.println("Bubble Sort (O(n²)): " + bubbleTime / 1_000_000.0 + " ms");
        System.out.println("Arrays.sort (O(n log n)): " + sortTime / 1_000_000.0 + " ms");
        System.out.println("Speed up: " + (bubbleTime / (double) sortTime) + "x");

        System.out.println();


        // ============================================================
        // 4. COLLECTION SIZING
        // ============================================================

        System.out.println("--- Collection Sizing ---");

        // Bad: Default capacity, many resizes
        long badStart = System.nanoTime();
        List<Integer> badSize = new ArrayList<>(); // Default capacity: 10
        for (int i = 0; i < 100000; i++) {
            badSize.add(i); // Resizes multiple times
        }
        long badTime = System.nanoTime() - badStart;

        // Good: Pre-sized, no resizes
        long goodStart = System.nanoTime();
        List<Integer> goodSize = new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            goodSize.add(i); // No resizing needed
        }
        long goodTime = System.nanoTime() - goodStart;

        System.out.println("Default size: " + badTime / 1_000_000.0 + " ms");
        System.out.println("Pre-sized: " + goodTime / 1_000_000.0 + " ms");
        System.out.println("Improvement: " + ((badTime - goodTime) / (double) badTime * 100) + "%");

        System.out.println();


        // ============================================================
        // 5. PRIMITIVE vs WRAPPER PERFORMANCE
        // ============================================================

        System.out.println("--- Primitive vs Wrapper ---");

        // Primitives are much faster
        int primitiveSum = 0;
        long primStart = System.nanoTime();
        for (int i = 0; i < 10000000; i++) {
            primitiveSum += i;
        }
        long primTime = System.nanoTime() - primStart;

        // Wrappers have overhead
        Integer wrapperSum = 0;
        long wrapStart = System.nanoTime();
        for (int i = 0; i < 10000000; i++) {
            wrapperSum += i; // Autoboxing/unboxing overhead
        }
        long wrapTime = System.nanoTime() - wrapStart;

        System.out.println("Primitive int: " + primTime / 1_000_000.0 + " ms");
        System.out.println("Wrapper Integer: " + wrapTime / 1_000_000.0 + " ms");
        System.out.println("Wrapper overhead: " + ((wrapTime - primTime) / (double) primTime * 100) + "%");

        System.out.println();


        // ============================================================
        // 6. LAZY EVALUATION
        // ============================================================

        System.out.println("--- Lazy Evaluation ---");

        // Eager: Computes everything
        List<Integer> numbers = IntStream.range(0, 1000000)
                .boxed()
                .collect(Collectors.toList());

        long eagerStart = System.nanoTime();
        int eagerResult = numbers.stream()
                .map(n -> n * 2)
                .filter(n -> n > 1000)
                .map(n -> n + 10)
                .findFirst()
                .orElse(0);
        long eagerTime = System.nanoTime() - eagerStart;

        // Lazy: Stream processing is lazy, stops at first match
        long lazyStart = System.nanoTime();
        int lazyResult = IntStream.range(0, 1000000)
                .map(n -> n * 2)
                .filter(n -> n > 1000)
                .map(n -> n + 10)
                .findFirst()
                .orElse(0);
        long lazyTime = System.nanoTime() - lazyStart;

        System.out.println("Eager (with list): " + eagerTime / 1_000.0 + " μs");
        System.out.println("Lazy (stream): " + lazyTime / 1_000.0 + " μs");
        System.out.println("Lazy is faster (early termination)");

        System.out.println();


        // ============================================================
        // 7. JMH BENCHMARKING EXAMPLE
        // ============================================================

        System.out.println("--- JMH Benchmarking (Conceptual) ---");

        System.out.println("""
                JMH Benchmark Example:

                import org.openjdk.jmh.annotations.*;
                import java.util.concurrent.TimeUnit;

                @State(Scope.Thread)
                @BenchmarkMode(Mode.AverageTime)
                @OutputTimeUnit(TimeUnit.NANOSECONDS)
                @Warmup(iterations = 5, time = 1)
                @Measurement(iterations = 5, time = 1)
                @Fork(1)
                public class MyBenchmark {

                    @Param({"100", "1000", "10000"})
                    private int size;

                    private List<Integer> list;

                    @Setup
                    public void setup() {
                        list = new ArrayList<>(size);
                        for (int i = 0; i < size; i++) {
                            list.add(i);
                        }
                    }

                    @Benchmark
                    public int testArrayList() {
                        return list.get(size / 2);
                    }

                    @Benchmark
                    public int testLinkedList() {
                        LinkedList<Integer> linked = new LinkedList<>(list);
                        return linked.get(size / 2);
                    }
                }

                Running benchmarks:
                $ mvn clean install
                $ java -jar target/benchmarks.jar

                JMH ANNOTATIONS:
                @Benchmark           - Mark benchmark method
                @State               - Shared state scope
                @Setup               - Run before benchmarks
                @TearDown            - Run after benchmarks
                @Param               - Parameterized tests
                @Warmup              - JIT warm-up iterations
                @Measurement         - Measurement iterations
                @Fork                - Number of JVM forks
                @BenchmarkMode       - Throughput, AverageTime, etc.
                @OutputTimeUnit      - Time unit for results

                BENCHMARK MODES:
                - Throughput: ops/time
                - AverageTime: time/op
                - SampleTime: sampling distribution
                - SingleShotTime: cold start time
                - All: all of above
                """);

        System.out.println();


        // ============================================================
        // 8. PERFORMANCE BEST PRACTICES
        // ============================================================

        System.out.println("--- Performance Best Practices ---");

        System.out.println("""
                1. CHOOSE RIGHT ALGORITHM
                   - O(n log n) sorting vs O(n²)
                   - Binary search O(log n) vs linear O(n)
                   - HashMap O(1) vs TreeMap O(log n)

                2. USE APPROPRIATE DATA STRUCTURES
                   - ArrayList for random access
                   - LinkedList for frequent insert/delete
                   - HashSet for uniqueness checks
                   - TreeSet for sorted data
                   - HashMap for key-value lookups

                3. AVOID PREMATURE OPTIMIZATION
                   - Profile first
                   - Focus on bottlenecks
                   - Readability matters

                4. MINIMIZE OBJECT CREATION
                   - Reuse objects when possible
                   - Use object pools for expensive objects
                   - Avoid creating objects in loops

                5. USE PRIMITIVES WHEN POSSIBLE
                   - int instead of Integer
                   - double instead of Double
                   - Less memory, faster access

                6. PRE-SIZE COLLECTIONS
                   - new ArrayList<>(expectedSize)
                   - new HashMap<>(expectedSize)
                   - Avoids resizing

                7. USE STRINGBUILDER FOR CONCATENATION
                   - StringBuilder for loops
                   - String.concat() for one-time
                   - Avoid + in loops

                8. CACHE EXPENSIVE COMPUTATIONS
                   - Memoization
                   - Lazy initialization
                   - Computed once, used many

                9. USE PARALLEL STREAMS WISELY
                   - Good for CPU-intensive tasks
                   - Large datasets (>10000 elements)
                   - Independent operations
                   - Bad for small data or I/O

                10. PROFILE BEFORE OPTIMIZING
                    - Use profilers (JProfiler, YourKit, VisualVM)
                    - Measure in production-like environment
                    - Focus on hot spots
                """);

        System.out.println();


        // ============================================================
        // 9. JIT COMPILATION
        // ============================================================

        System.out.println("--- JIT Compilation ---");

        System.out.println("""
                Java Just-In-Time (JIT) Compiler:

                HOW IT WORKS:
                1. Code runs in interpreter initially
                2. JIT identifies "hot" methods (frequently called)
                3. Compiles hot methods to native code
                4. Optimizes based on runtime profiling
                5. Can deoptimize if assumptions break

                OPTIMIZATIONS:
                - Inlining: Replace method call with method body
                - Dead code elimination: Remove unused code
                - Loop unrolling: Reduce loop overhead
                - Escape analysis: Stack allocation instead of heap
                - Null check elimination
                - Range check elimination
                - Constant folding

                WARM-UP:
                First iterations are slow (interpreted)
                After ~10,000 invocations, JIT optimizes
                Always warm up before benchmarking!

                MONITORING:
                -XX:+PrintCompilation    - Show JIT compilation
                -XX:+UnlockDiagnosticVMOptions
                -XX:+PrintInlining       - Show inlining decisions

                C1 vs C2 COMPILERS:
                C1 (Client): Fast compilation, moderate optimization
                C2 (Server): Slower compilation, aggressive optimization
                Tiered compilation: C1 first, then C2 (default)
                """);

        System.out.println();


        // ============================================================
        // 10. MEMORY OPTIMIZATION
        // ============================================================

        System.out.println("--- Memory Optimization ---");

        System.out.println("""
                REDUCE MEMORY USAGE:

                1. USE PRIMITIVES
                   int: 4 bytes
                   Integer: ~16 bytes (object overhead)

                2. LAZY INITIALIZATION
                   private List<String> cache;
                   public List<String> getCache() {
                       if (cache == null) {
                           cache = loadCache();
                       }
                       return cache;
                   }

                3. WEAK REFERENCES FOR CACHES
                   Map<Key, WeakReference<Value>> cache;

                4. COMPACT STRINGS (Java 9+)
                   Automatically uses byte[] for Latin-1 strings
                   Saves 50% memory for ASCII strings

                5. USE ENUMS INSTEAD OF STRINGS
                   enum Status { ACTIVE, INACTIVE }
                   Better than String "ACTIVE", "INACTIVE"

                6. REMOVE UNUSED FIELDS
                   Dead fields still consume memory

                7. STRING INTERNING FOR DUPLICATES
                   String s = "duplicate".intern();
                   Reuses from string pool

                8. USE STREAMS INSTEAD OF COLLECTIONS
                   For one-time iteration
                   Avoids storing entire collection

                MONITORING:
                -Xmx2g              - Max heap size
                -Xms2g              - Initial heap size
                -XX:+HeapDumpOnOutOfMemoryError
                -XX:HeapDumpPath=/path/to/dump
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * PERFORMANCE OPTIMIZATION WORKFLOW:
         * 1. Measure (profile)
         * 2. Identify bottleneck
         * 3. Optimize
         * 4. Measure again
         * 5. Repeat
         *
         * PROFILING TOOLS:
         * - Java Flight Recorder (JFR)
         * - VisualVM
         * - JProfiler
         * - YourKit
         * - async-profiler
         *
         * BENCHMARKING:
         * - Use JMH for micro-benchmarks
         * - Warm up JIT
         * - Multiple iterations
         * - Statistical analysis
         *
         * COMMON OPTIMIZATIONS:
         * - Algorithm choice (biggest impact)
         * - Data structure selection
         * - Collection pre-sizing
         * - Primitive types
         * - StringBuilder for strings
         * - Caching
         * - Lazy evaluation
         * - Parallel processing
         *
         * ANTI-PATTERNS:
         * - Premature optimization
         * - Micro-optimizations that hurt readability
         * - Optimizing without profiling
         * - Over-engineering
         *
         * WHEN TO OPTIMIZE:
         * ✓ Proven bottleneck
         * ✓ Production performance issue
         * ✓ Profiling data available
         * ✓ Cost-benefit makes sense
         * ✗ "Feeling" it's slow
         * ✗ Before measuring
         * ✗ Everywhere
         *
         * REMEMBER:
         * "Premature optimization is the root of all evil"
         * - Donald Knuth
         *
         * "Make it work, make it right, make it fast"
         * - Kent Beck
         */
    }

    // Helper method: Bubble sort (intentionally inefficient)
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
