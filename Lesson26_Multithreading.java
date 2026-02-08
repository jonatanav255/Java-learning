/*
 * LESSON 26: MULTITHREADING & CONCURRENCY
 *
 * Multithreading allows concurrent execution of two or more parts of a program.
 * This is essential for building responsive, high-performance applications.
 *
 * KEY CONCEPTS:
 * - Thread: Lightweight process, smallest unit of execution
 * - Concurrency: Multiple tasks making progress (not necessarily simultaneously)
 * - Parallelism: Multiple tasks executing simultaneously (requires multiple cores)
 * - Synchronization: Coordinating thread access to shared resources
 * - Thread Safety: Code that works correctly with multiple threads
 *
 * CREATING THREADS:
 * 1. Extend Thread class
 * 2. Implement Runnable interface (preferred)
 * 3. Implement Callable interface (returns result)
 * 4. Use ExecutorService (thread pool)
 * 5. Lambda expressions
 *
 * THREAD STATES:
 * - NEW: Thread created but not started
 * - RUNNABLE: Thread ready to run or running
 * - BLOCKED: Waiting for monitor lock
 * - WAITING: Waiting indefinitely for another thread
 * - TIMED_WAITING: Waiting for specified time
 * - TERMINATED: Thread completed execution
 *
 * SYNCHRONIZATION:
 * - synchronized keyword
 * - Locks (ReentrantLock)
 * - Atomic variables
 * - Volatile keyword
 * - Thread-safe collections
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Lesson26_Multithreading {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== MULTITHREADING & CONCURRENCY ===\n");

        // ============================================================
        // 1. CREATING THREADS - EXTENDING THREAD CLASS
        // ============================================================

        System.out.println("--- Method 1: Extend Thread ---");

        MyThread thread1 = new MyThread("Thread-1");
        thread1.start();  // Start the thread
        thread1.join();   // Wait for thread to complete

        System.out.println();


        // ============================================================
        // 2. CREATING THREADS - IMPLEMENTING RUNNABLE
        // ============================================================

        System.out.println("--- Method 2: Implement Runnable (Preferred) ---");

        Thread thread2 = new Thread(new MyRunnable("Thread-2"));
        thread2.start();
        thread2.join();

        System.out.println();


        // ============================================================
        // 3. CREATING THREADS - LAMBDA EXPRESSION
        // ============================================================

        System.out.println("--- Method 3: Lambda Expression ---");

        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Lambda Thread: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
        thread3.join();

        System.out.println();


        // ============================================================
        // 4. MULTIPLE THREADS RUNNING CONCURRENTLY
        // ============================================================

        System.out.println("--- Multiple Threads ---");

        Thread t1 = new Thread(new Counter("Counter-A"));
        Thread t2 = new Thread(new Counter("Counter-B"));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println();


        // ============================================================
        // 5. THREAD PRIORITY
        // ============================================================

        System.out.println("--- Thread Priority ---");

        Thread highPriority = new Thread(() -> {
            System.out.println("High priority thread");
        });

        Thread lowPriority = new Thread(() -> {
            System.out.println("Low priority thread");
        });

        highPriority.setPriority(Thread.MAX_PRIORITY);  // 10
        lowPriority.setPriority(Thread.MIN_PRIORITY);   // 1

        highPriority.start();
        lowPriority.start();

        highPriority.join();
        lowPriority.join();

        System.out.println();


        // ============================================================
        // 6. RACE CONDITION PROBLEM
        // ============================================================

        System.out.println("--- Race Condition (Problem) ---");

        UnsafeCounter unsafeCounter = new UnsafeCounter();

        Thread inc1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        });

        Thread inc2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        });

        inc1.start();
        inc2.start();
        inc1.join();
        inc2.join();

        System.out.println("Unsafe counter (should be 2000): " + unsafeCounter.getCount());
        System.out.println();


        // ============================================================
        // 7. SYNCHRONIZED KEYWORD - SOLUTION
        // ============================================================

        System.out.println("--- Synchronized (Solution) ---");

        SafeCounter safeCounter = new SafeCounter();

        Thread inc3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                safeCounter.increment();
            }
        });

        Thread inc4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                safeCounter.increment();
            }
        });

        inc3.start();
        inc4.start();
        inc3.join();
        inc4.join();

        System.out.println("Safe counter (correct): " + safeCounter.getCount());
        System.out.println();


        // ============================================================
        // 8. ATOMIC VARIABLES
        // ============================================================

        System.out.println("--- Atomic Variables ---");

        AtomicInteger atomicCounter = new AtomicInteger(0);

        Thread at1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.incrementAndGet();
            }
        });

        Thread at2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.incrementAndGet();
            }
        });

        at1.start();
        at2.start();
        at1.join();
        at2.join();

        System.out.println("Atomic counter: " + atomicCounter.get());
        System.out.println();


        // ============================================================
        // 9. EXECUTOR SERVICE - THREAD POOL
        // ============================================================

        System.out.println("--- Executor Service ---");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " executed by " +
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println();


        // ============================================================
        // 10. CALLABLE AND FUTURE
        // ============================================================

        System.out.println("--- Callable and Future ---");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        java.util.concurrent.Callable<Integer> task = new java.util.concurrent.Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 42;
            }
        };

        Future<Integer> future = executorService.submit(task);

        System.out.println("Waiting for result...");
        try {
            Integer result = future.get();  // Blocks until result is available
            System.out.println("Result from Callable: " + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();

        System.out.println();


        // ============================================================
        // 11. PRODUCER-CONSUMER PATTERN
        // ============================================================

        System.out.println("--- Producer-Consumer ---");

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    Integer item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println();


        // ============================================================
        // 12. REENTRANT LOCK
        // ============================================================

        System.out.println("--- ReentrantLock ---");

        BankAccount account = new BankAccount(1000);

        Thread deposit = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(100);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread withdraw = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(50);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        deposit.start();
        withdraw.start();
        deposit.join();
        withdraw.join();

        System.out.println("Final balance: " + account.getBalance());
        System.out.println();


        // ============================================================
        // 13. THREAD-SAFE COLLECTIONS
        // ============================================================

        System.out.println("--- Thread-Safe Collections ---");

        // ConcurrentHashMap - thread-safe
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        Thread t10 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                map.put("Key" + i, i);
            }
        });

        Thread t20 = new Thread(() -> {
            for (int i = 100; i < 200; i++) {
                map.put("Key" + i, i);
            }
        });

        t10.start();
        t20.start();
        t10.join();
        t20.join();

        System.out.println("ConcurrentHashMap size: " + map.size());
        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * CREATING THREADS:
         * 1. Extend Thread class
         * 2. Implement Runnable (preferred)
         * 3. Lambda expression
         * 4. ExecutorService (best for production)
         *
         * THREAD LIFECYCLE:
         * NEW → RUNNABLE → (RUNNING) → TERMINATED
         *
         * SYNCHRONIZATION:
         * - synchronized methods/blocks
         * - ReentrantLock (explicit locking)
         * - Atomic variables (AtomicInteger, etc.)
         * - volatile keyword (visibility guarantee)
         *
         * EXECUTOR SERVICE:
         * - newFixedThreadPool(n) - fixed number of threads
         * - newCachedThreadPool() - creates threads as needed
         * - newSingleThreadExecutor() - single worker thread
         * - newScheduledThreadPool(n) - scheduled tasks
         *
         * THREAD-SAFE COLLECTIONS:
         * - ConcurrentHashMap
         * - CopyOnWriteArrayList
         * - ConcurrentLinkedQueue
         * - BlockingQueue implementations
         *
         * BEST PRACTICES:
         * - Prefer Runnable over Thread
         * - Use ExecutorService instead of creating threads manually
         * - Always close ExecutorService (shutdown)
         * - Use thread-safe collections
         * - Minimize synchronized blocks
         * - Avoid nested locks (deadlock risk)
         * - Use higher-level concurrency utilities
         *
         * COMMON ISSUES:
         * - Race conditions
         * - Deadlock
         * - Livelock
         * - Thread starvation
         * - Memory consistency errors
         *
         * WHEN TO USE MULTITHREADING:
         * - I/O operations
         * - Network requests
         * - Large computations
         * - Background tasks
         * - Responsive UI applications
         * - Server applications
         */
    }
}


// ============================================================
// METHOD 1: EXTEND THREAD CLASS
// ============================================================

class MyThread extends Thread {
    private String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(100);  // Sleep for 100ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


// ============================================================
// METHOD 2: IMPLEMENT RUNNABLE
// ============================================================

class MyRunnable implements Runnable {
    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


// ============================================================
// COUNTER RUNNABLE
// ============================================================

class Counter implements Runnable {
    private String name;

    public Counter(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": " + i);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


// ============================================================
// UNSAFE COUNTER (RACE CONDITION)
// ============================================================

class UnsafeCounter {
    private int count = 0;

    public void increment() {
        count++;  // Not atomic! Race condition possible
    }

    public int getCount() {
        return count;
    }
}


// ============================================================
// SAFE COUNTER (SYNCHRONIZED)
// ============================================================

class SafeCounter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // Thread-safe
    }

    public synchronized int getCount() {
        return count;
    }
}


// ============================================================
// BANK ACCOUNT WITH REENTRANT LOCK
// ============================================================

class BankAccount {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println("Deposited: " + amount + ", Balance: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(double amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew: " + amount + ", Balance: " + balance);
            }
        } finally {
            lock.unlock();
        }
    }

    public double getBalance() {
        return balance;
    }
}
