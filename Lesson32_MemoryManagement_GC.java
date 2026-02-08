/*
 * LESSON 32: JAVA MEMORY MANAGEMENT & GARBAGE COLLECTION
 *
 * Understanding memory management is crucial for writing efficient Java applications.
 * Java uses automatic memory management through Garbage Collection (GC).
 *
 * KEY CONCEPTS:
 * - Heap: Memory area where objects are stored
 * - Stack: Memory area for method calls and local variables
 * - Garbage Collection: Automatic memory reclamation
 * - Memory Leak: Objects that can't be GC'd but are no longer needed
 * - OutOfMemoryError: JVM runs out of heap space
 *
 * MEMORY AREAS:
 * 1. Heap Memory:
 *    - Young Generation (Eden, Survivor S0, Survivor S1)
 *    - Old Generation (Tenured)
 * 2. Stack Memory:
 *    - Thread-specific
 *    - Stores method frames, local variables, references
 * 3. Metaspace (Java 8+):
 *    - Stores class metadata
 *    - Replaces PermGen
 *
 * GARBAGE COLLECTORS:
 * 1. Serial GC: Single-threaded, simple
 * 2. Parallel GC: Multi-threaded, throughput-focused
 * 3. CMS (Concurrent Mark Sweep): Low-latency, deprecated in Java 14
 * 4. G1 GC (Garbage First): Default in Java 9+, balanced
 * 5. ZGC: Ultra-low latency, scalable
 * 6. Shenandoah: Low pause times
 *
 * GC PHASES:
 * 1. Mark: Identify live objects
 * 2. Sweep: Remove dead objects
 * 3. Compact: Reduce fragmentation (optional)
 *
 * OBJECT LIFECYCLE:
 * Creation → Young Gen (Eden) → Survivor Space → Old Gen → GC
 *
 * JVM FLAGS FOR MEMORY:
 * -Xms: Initial heap size
 * -Xmx: Maximum heap size
 * -Xss: Stack size per thread
 * -XX:MaxMetaspaceSize: Max metaspace size
 * -XX:+UseG1GC: Use G1 garbage collector
 *
 * MONITORING TOOLS:
 * - jstat: JVM statistics
 * - jmap: Memory map
 * - jconsole: JVM monitoring
 * - VisualVM: Profiling tool
 * - JFR (Java Flight Recorder)
 */

import java.lang.management.*;
import java.util.*;
import java.lang.ref.*;

public class Lesson32_MemoryManagement_GC {
    public static void main(String[] args) {

        System.out.println("=== MEMORY MANAGEMENT & GARBAGE COLLECTION ===\n");

        // ============================================================
        // 1. RUNTIME MEMORY INFORMATION
        // ============================================================

        System.out.println("--- Runtime Memory Info ---");

        Runtime runtime = Runtime.getRuntime();

        System.out.println("Available Processors: " + runtime.availableProcessors());
        System.out.println("Total Memory (bytes): " + runtime.totalMemory());
        System.out.println("Free Memory (bytes): " + runtime.freeMemory());
        System.out.println("Max Memory (bytes): " + runtime.maxMemory());
        System.out.println("Used Memory (bytes): " + (runtime.totalMemory() - runtime.freeMemory()));

        // Convert to MB
        long totalMB = runtime.totalMemory() / (1024 * 1024);
        long freeMB = runtime.freeMemory() / (1024 * 1024);
        long maxMB = runtime.maxMemory() / (1024 * 1024);

        System.out.println("\nIn Megabytes:");
        System.out.println("Total Memory: " + totalMB + " MB");
        System.out.println("Free Memory: " + freeMB + " MB");
        System.out.println("Max Memory: " + maxMB + " MB");
        System.out.println("Used Memory: " + (totalMB - freeMB) + " MB");

        System.out.println();


        // ============================================================
        // 2. MEMORY MX BEANS
        // ============================================================

        System.out.println("--- MemoryMXBean ---");

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory:");
        System.out.println("  Init: " + heapUsage.getInit() / (1024 * 1024) + " MB");
        System.out.println("  Used: " + heapUsage.getUsed() / (1024 * 1024) + " MB");
        System.out.println("  Committed: " + heapUsage.getCommitted() / (1024 * 1024) + " MB");
        System.out.println("  Max: " + heapUsage.getMax() / (1024 * 1024) + " MB");

        System.out.println("\nNon-Heap Memory (Metaspace, etc.):");
        System.out.println("  Init: " + nonHeapUsage.getInit() / (1024 * 1024) + " MB");
        System.out.println("  Used: " + nonHeapUsage.getUsed() / (1024 * 1024) + " MB");
        System.out.println("  Committed: " + nonHeapUsage.getCommitted() / (1024 * 1024) + " MB");

        System.out.println();


        // ============================================================
        // 3. MEMORY POOLS
        // ============================================================

        System.out.println("--- Memory Pools ---");

        List<MemoryPoolMXBean> memoryPools = ManagementFactory.getMemoryPoolMXBeans();

        for (MemoryPoolMXBean pool : memoryPools) {
            System.out.println("\nPool: " + pool.getName());
            System.out.println("  Type: " + pool.getType());
            MemoryUsage usage = pool.getUsage();
            System.out.println("  Used: " + usage.getUsed() / (1024 * 1024) + " MB");
            System.out.println("  Max: " + (usage.getMax() == -1 ? "Undefined" : usage.getMax() / (1024 * 1024) + " MB"));
        }

        System.out.println();


        // ============================================================
        // 4. GARBAGE COLLECTION INFO
        // ============================================================

        System.out.println("--- Garbage Collectors ---");

        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("GC Name: " + gcBean.getName());
            System.out.println("  Collection Count: " + gcBean.getCollectionCount());
            System.out.println("  Collection Time: " + gcBean.getCollectionTime() + " ms");
            System.out.println("  Memory Pool Names: " + Arrays.toString(gcBean.getMemoryPoolNames()));
        }

        System.out.println();


        // ============================================================
        // 5. FORCING GARBAGE COLLECTION
        // ============================================================

        System.out.println("--- Garbage Collection ---");

        System.out.println("Before GC:");
        printMemoryUsage();

        // Create objects that will become garbage
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            tempList.add("String " + i);
        }

        System.out.println("\nAfter creating 100,000 strings:");
        printMemoryUsage();

        // Make objects eligible for GC
        tempList = null;

        // Suggest GC (not guaranteed to run immediately)
        System.gc();

        // Give GC time to run
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAfter GC:");
        printMemoryUsage();

        System.out.println();


        // ============================================================
        // 6. OBJECT REFERENCES (STRONG, SOFT, WEAK, PHANTOM)
        // ============================================================

        System.out.println("--- Reference Types ---");

        // Strong Reference (normal)
        Object strongRef = new Object();
        System.out.println("Strong Reference: " + strongRef);

        // Soft Reference (cleared when memory is low)
        SoftReference<Object> softRef = new SoftReference<>(new Object());
        System.out.println("Soft Reference: " + softRef.get());

        // Weak Reference (cleared at next GC)
        WeakReference<Object> weakRef = new WeakReference<>(new Object());
        System.out.println("Weak Reference before GC: " + weakRef.get());

        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Weak Reference after GC: " + weakRef.get());

        // Phantom Reference (for post-mortem cleanup)
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantomRef = new PhantomReference<>(new Object(), queue);
        System.out.println("Phantom Reference (always null): " + phantomRef.get());

        System.out.println();


        // ============================================================
        // 7. FINALIZATION (DEPRECATED)
        // ============================================================

        System.out.println("--- Finalization (Deprecated) ---");

        System.out.println("Creating FinalizableObject...");
        FinalizableObject obj = new FinalizableObject("TestObject");
        obj = null;  // Make eligible for GC

        System.out.println("Suggesting GC...");
        System.gc();

        try {
            Thread.sleep(500);  // Give time for finalizer to run
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Note: finalize() is deprecated. Use try-with-resources or Cleaner API instead.");

        System.out.println();


        // ============================================================
        // 8. MEMORY LEAK EXAMPLES
        // ============================================================

        System.out.println("--- Common Memory Leak Patterns ---");

        System.out.println("""
                Common memory leaks in Java:

                1. Static Collections:
                   static List<Object> list = new ArrayList<>();
                   list.add(object); // Objects never removed

                2. Listeners Not Removed:
                   button.addActionListener(listener);
                   // Listener not removed when done

                3. Inner Classes:
                   class Outer {
                       class Inner { } // Holds reference to Outer
                   }

                4. ThreadLocal Not Cleaned:
                   ThreadLocal<Object> tl = new ThreadLocal<>();
                   tl.set(object);
                   // Not removed with tl.remove()

                5. Unclosed Resources:
                   Stream stream = new FileInputStream(file);
                   // Stream not closed

                6. HashMap with Bad hashCode/equals:
                   Map with objects that don't implement hashCode properly
                """);

        System.out.println();


        // ============================================================
        // 9. BEST PRACTICES
        // ============================================================

        System.out.println("--- Best Practices ---");

        // Use try-with-resources
        System.out.println("✓ Use try-with-resources for auto-closing");

        // Nullify references when done
        System.out.println("✓ Nullify large object references when done");

        // Use weak references for caches
        System.out.println("✓ Use WeakHashMap or SoftReference for caches");

        // Avoid finalizers
        System.out.println("✓ Avoid finalize(), use try-with-resources or Cleaner");

        // Size collections appropriately
        System.out.println("✓ Initialize collections with appropriate size");
        ArrayList<String> list = new ArrayList<>(1000); // Better than default

        // Remove listeners
        System.out.println("✓ Always remove event listeners when done");

        // Clean ThreadLocal
        System.out.println("✓ Always call ThreadLocal.remove()");

        System.out.println();


        // ============================================================
        // 10. MEMORY-EFFICIENT PROGRAMMING
        // ============================================================

        System.out.println("--- Memory-Efficient Code ---");

        // Use primitives instead of wrapper classes when possible
        int primitive = 100; // 4 bytes
        Integer wrapper = 100; // ~16 bytes (object overhead)

        // Use String intern for repeated strings
        String s1 = "Java";
        String s2 = new String("Java").intern(); // Reuses from pool
        System.out.println("String interning (same object): " + (s1 == s2));

        // Use StringBuilder for string concatenation
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i); // More efficient than += in loop
        }

        // Stream processing (lazy evaluation)
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
                .filter(n -> n > 2)
                .map(n -> n * 2)
                .findFirst(); // Only processes until first match

        System.out.println("✓ Used memory-efficient techniques");

        System.out.println();


        // ============================================================
        // 11. MONITORING GC ACTIVITY
        // ============================================================

        System.out.println("--- GC Monitoring ---");

        long gcCountBefore = getGCCount();
        long gcTimeBefore = getGCTime();

        // Create some garbage
        for (int i = 0; i < 10; i++) {
            List<byte[]> garbage = new ArrayList<>();
            for (int j = 0; j < 1000; j++) {
                garbage.add(new byte[1024]); // 1KB each
            }
            garbage = null;
        }

        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long gcCountAfter = getGCCount();
        long gcTimeAfter = getGCTime();

        System.out.println("GC Collections: " + (gcCountAfter - gcCountBefore));
        System.out.println("GC Time: " + (gcTimeAfter - gcTimeBefore) + " ms");

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * MEMORY STRUCTURE:
         * Heap:
         *   Young Generation:
         *     - Eden Space (new objects)
         *     - Survivor Space S0
         *     - Survivor Space S1
         *   Old Generation:
         *     - Tenured (long-lived objects)
         * Stack:
         *   - Method frames
         *   - Local variables
         *   - References
         * Metaspace:
         *   - Class metadata
         *   - Static variables (Java 8+)
         *
         * GC ALGORITHMS:
         * 1. Serial GC (-XX:+UseSerialGC)
         *    - Single thread
         *    - Small apps, single CPU
         *
         * 2. Parallel GC (-XX:+UseParallelGC)
         *    - Multiple threads
         *    - High throughput
         *    - Stop-the-world
         *
         * 3. G1 GC (-XX:+UseG1GC) [DEFAULT Java 9+]
         *    - Balanced throughput/latency
         *    - Heap divided into regions
         *    - Predictable pause times
         *
         * 4. ZGC (-XX:+UseZGC)
         *    - Ultra-low latency (<10ms)
         *    - Large heaps (multi-TB)
         *    - Concurrent
         *
         * 5. Shenandoah (-XX:+UseShenandoahGC)
         *    - Low pause times
         *    - Concurrent compaction
         *
         * GC TUNING FLAGS:
         * -Xms512m                    # Initial heap size
         * -Xmx2g                      # Maximum heap size
         * -Xss1m                      # Stack size per thread
         * -XX:NewRatio=2              # Old/Young ratio
         * -XX:SurvivorRatio=8         # Eden/Survivor ratio
         * -XX:MaxGCPauseMillis=200    # Max GC pause target
         * -XX:+UseG1GC                # Use G1 collector
         * -XX:+PrintGCDetails         # Print GC logs
         * -XX:+HeapDumpOnOutOfMemoryError  # Dump on OOM
         *
         * REFERENCE TYPES:
         * 1. Strong Reference (default)
         *    - Object obj = new Object();
         *    - Never GC'd while reachable
         *
         * 2. Soft Reference
         *    - SoftReference<Object> soft = new SoftReference<>(obj);
         *    - GC'd when memory low
         *    - Good for caches
         *
         * 3. Weak Reference
         *    - WeakReference<Object> weak = new WeakReference<>(obj);
         *    - GC'd at next collection
         *    - Good for weak caches
         *
         * 4. Phantom Reference
         *    - PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);
         *    - For post-mortem cleanup
         *    - get() always returns null
         *
         * MEMORY LEAKS IN JAVA:
         * Despite GC, leaks can occur:
         * 1. Static collections holding objects
         * 2. Listeners not unregistered
         * 3. ThreadLocal not cleaned
         * 4. Inner classes holding outer reference
         * 5. Unclosed resources (streams, connections)
         * 6. Custom caches without eviction
         *
         * MEMORY PROFILING TOOLS:
         * - jstat: Monitor GC and heap
         * - jmap: Heap dumps
         * - jhat: Heap analysis
         * - jconsole: Real-time monitoring
         * - VisualVM: Visual profiling
         * - Java Mission Control (JMC)
         * - Eclipse MAT: Memory analysis
         * - YourKit: Commercial profiler
         *
         * BEST PRACTICES:
         * 1. Use appropriate data structures
         * 2. Close resources (try-with-resources)
         * 3. Nullify references when done with large objects
         * 4. Use object pools for expensive objects
         * 5. Avoid creating unnecessary objects
         * 6. Use primitives instead of wrappers when possible
         * 7. Use StringBuilder for string concatenation
         * 8. Clear collections when done
         * 9. Remove event listeners
         * 10. Clean ThreadLocal variables
         *
         * WHEN TO TUNE GC:
         * - High GC overhead (>10% CPU time)
         * - Frequent full GCs
         * - Long GC pauses
         * - OutOfMemoryError
         * - High allocation rate
         *
         * COMMON ERRORS:
         * - OutOfMemoryError: Java heap space
         * - OutOfMemoryError: GC overhead limit exceeded
         * - OutOfMemoryError: Metaspace
         * - StackOverflowError (recursion)
         *
         * MONITORING COMMANDS:
         * jstat -gc <pid>              # GC statistics
         * jstat -gcutil <pid> 1000     # GC utilization every 1s
         * jmap -heap <pid>             # Heap summary
         * jmap -histo <pid>            # Object histogram
         * jstack <pid>                 # Thread dump
         * jcmd <pid> GC.heap_info      # Heap info
         */
    }

    // Helper method to print memory usage
    private static void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long used = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long total = runtime.totalMemory() / (1024 * 1024);
        System.out.println("Memory: " + used + " MB used / " + total + " MB total");
    }

    // Helper method to get total GC count
    private static long getGCCount() {
        long count = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            long gcCount = gc.getCollectionCount();
            if (gcCount > 0) {
                count += gcCount;
            }
        }
        return count;
    }

    // Helper method to get total GC time
    private static long getGCTime() {
        long time = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            long gcTime = gc.getCollectionTime();
            if (gcTime > 0) {
                time += gcTime;
            }
        }
        return time;
    }
}


// ============================================================
// OBJECT WITH FINALIZER (DEPRECATED - DON'T USE)
// ============================================================

class FinalizableObject {
    private String name;

    public FinalizableObject(String name) {
        this.name = name;
        System.out.println("  " + name + " created");
    }

    // Deprecated and not recommended
    @Override
    @Deprecated
    protected void finalize() throws Throwable {
        System.out.println("  " + name + " finalized (being garbage collected)");
        super.finalize();
    }
}
