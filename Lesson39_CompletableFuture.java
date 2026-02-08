/*
 * LESSON 39: COMPLETABLEFUTURE & ADVANCED ASYNC PROGRAMMING
 *
 * CompletableFuture is Java's powerful API for asynchronous programming
 * introduced in Java 8. It allows composing complex async operations.
 *
 * KEY CONCEPTS:
 * - CompletableFuture: Represents an async computation
 * - Non-blocking: Thread doesn't wait for result
 * - Composable: Chain multiple async operations
 * - Exception Handling: Handle errors in async chains
 * - Combinators: Combine multiple futures
 *
 * CORE METHODS:
 * - supplyAsync: Start async computation with result
 * - runAsync: Start async computation without result
 * - thenApply: Transform result
 * - thenAccept: Consume result
 * - thenRun: Run action after completion
 * - thenCompose: Flat map (chain dependent futures)
 * - thenCombine: Combine two independent futures
 * - exceptionally: Handle exceptions
 * - handle: Handle both result and exception
 *
 * ASYNC VARIANTS:
 * - thenApply vs thenApplyAsync
 * - thenAccept vs thenAcceptAsync
 * - thenRun vs thenRunAsync
 * - Async versions run in different thread
 *
 * COMBINING FUTURES:
 * - thenCombine: Combine two futures
 * - thenCompose: Chain dependent futures
 * - allOf: Wait for all futures
 * - anyOf: Wait for any future
 *
 * EXECUTORS:
 * - Default: ForkJoinPool.commonPool()
 * - Custom: Provide your own executor
 *
 * USE CASES:
 * - REST API calls
 * - Database queries
 * - File I/O
 * - Microservices communication
 * - Parallel processing
 *
 * BENEFITS:
 * - Non-blocking code
 * - Better resource utilization
 * - Composable operations
 * - Error handling
 * - Timeouts
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.time.*;

public class Lesson39_CompletableFuture {
    public static void main(String[] args) throws Exception {

        System.out.println("=== COMPLETABLEFUTURE & ASYNC PROGRAMMING ===\n");

        // ============================================================
        // 1. CREATING COMPLETABLEFUTURE
        // ============================================================

        System.out.println("--- Creating CompletableFuture ---");

        // Completed future
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("Hello");
        System.out.println("Completed: " + completedFuture.get());

        // Supply async (with result)
        CompletableFuture<Integer> supplyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("  Running in: " + Thread.currentThread().getName());
            return 42;
        });
        System.out.println("Supply result: " + supplyFuture.get());

        // Run async (without result)
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> {
            System.out.println("  Running task without result");
        });
        runFuture.get(); // Wait for completion

        System.out.println();


        // ============================================================
        // 2. TRANSFORMATION (thenApply)
        // ============================================================

        System.out.println("--- Transformation (thenApply) ---");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10)
                .thenApply(x -> {
                    System.out.println("  Multiply by 2: " + x);
                    return x * 2;
                })
                .thenApply(x -> {
                    System.out.println("  Add 5: " + x);
                    return x + 5;
                });

        System.out.println("Final result: " + future.get());

        // Real-world example: Fetch and process data
        CompletableFuture<String> userFuture = CompletableFuture
                .supplyAsync(() -> fetchUser(1))
                .thenApply(user -> user.toUpperCase())
                .thenApply(user -> "Processed: " + user);

        System.out.println(userFuture.get());

        System.out.println();


        // ============================================================
        // 3. CONSUMPTION (thenAccept, thenRun)
        // ============================================================

        System.out.println("--- Consumption ---");

        // thenAccept - consumes result
        CompletableFuture.supplyAsync(() -> "Hello")
                .thenAccept(result -> System.out.println("  Consumed: " + result))
                .get();

        // thenRun - runs action without consuming result
        CompletableFuture.supplyAsync(() -> "Data")
                .thenRun(() -> System.out.println("  Task completed (no result access)"))
                .get();

        System.out.println();


        // ============================================================
        // 4. COMPOSING (thenCompose)
        // ============================================================

        System.out.println("--- Composing (thenCompose) ---");

        // thenCompose - for dependent async operations (flatMap)
        CompletableFuture<String> composedFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  Fetching user ID...");
                    return 123;
                })
                .thenCompose(userId -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("  Fetching user details for ID: " + userId);
                    return "User-" + userId;
                }))
                .thenCompose(username -> CompletableFuture.supplyAsync(() -> {
                    System.out.println("  Fetching orders for: " + username);
                    return username + " has 5 orders";
                }));

        System.out.println("Result: " + composedFuture.get());

        System.out.println();


        // ============================================================
        // 5. COMBINING (thenCombine)
        // ============================================================

        System.out.println("--- Combining (thenCombine) ---");

        // thenCombine - combine two independent futures
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            System.out.println("  Future 1 completed");
            return 10;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            System.out.println("  Future 2 completed");
            return 20;
        });

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(
                future2,
                (result1, result2) -> {
                    System.out.println("  Combining: " + result1 + " + " + result2);
                    return result1 + result2;
                }
        );

        System.out.println("Combined result: " + combinedFuture.get());

        System.out.println();


        // ============================================================
        // 6. EXCEPTION HANDLING
        // ============================================================

        System.out.println("--- Exception Handling ---");

        // exceptionally - handle exception
        CompletableFuture<Integer> exceptionFuture = CompletableFuture
                .supplyAsync(() -> {
                    if (true) throw new RuntimeException("Error occurred");
                    return 42;
                })
                .exceptionally(ex -> {
                    System.out.println("  Exception: " + ex.getMessage());
                    return 0; // Default value
                });

        System.out.println("Result with exception: " + exceptionFuture.get());

        // handle - process both result and exception
        CompletableFuture<String> handleFuture = CompletableFuture
                .supplyAsync(() -> {
                    if (Math.random() > 0.5) {
                        return "Success";
                    } else {
                        throw new RuntimeException("Failed");
                    }
                })
                .handle((result, ex) -> {
                    if (ex != null) {
                        return "Error: " + ex.getMessage();
                    }
                    return "Result: " + result;
                });

        System.out.println(handleFuture.get());

        // whenComplete - side effect (doesn't transform)
        CompletableFuture.supplyAsync(() -> "Data")
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("  Failed: " + ex.getMessage());
                    } else {
                        System.out.println("  Success: " + result);
                    }
                })
                .get();

        System.out.println();


        // ============================================================
        // 7. WAITING FOR MULTIPLE FUTURES
        // ============================================================

        System.out.println("--- Waiting for Multiple Futures ---");

        // allOf - wait for all
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return "Task 1";
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            sleep(150);
            return "Task 2";
        });

        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "Task 3";
        });

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(f1, f2, f3);
        allFutures.get(); // Wait for all

        System.out.println("All completed:");
        System.out.println("  " + f1.get());
        System.out.println("  " + f2.get());
        System.out.println("  " + f3.get());

        // Collect results
        List<String> results = Stream.of(f1, f2, f3)
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("Results: " + results);

        // anyOf - wait for any
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> {
                    sleep(100);
                    return "Fast";
                }),
                CompletableFuture.supplyAsync(() -> {
                    sleep(500);
                    return "Slow";
                })
        );

        System.out.println("First to complete: " + anyFuture.get());

        System.out.println();


        // ============================================================
        // 8. TIMEOUTS (Java 9+)
        // ============================================================

        System.out.println("--- Timeouts ---");

        try {
            CompletableFuture<String> timeoutFuture = CompletableFuture
                    .supplyAsync(() -> {
                        sleep(3000); // Simulate long operation
                        return "Completed";
                    })
                    .orTimeout(1, TimeUnit.SECONDS); // Java 9+

            System.out.println(timeoutFuture.get());
        } catch (Exception e) {
            System.out.println("Timeout occurred (as expected)");
        }

        // completeOnTimeout - provide default value on timeout
        CompletableFuture<String> defaultFuture = CompletableFuture
                .supplyAsync(() -> {
                    sleep(3000);
                    return "Completed";
                })
                .completeOnTimeout("Default Value", 1, TimeUnit.SECONDS);

        System.out.println("Result: " + defaultFuture.get());

        System.out.println();


        // ============================================================
        // 9. CUSTOM EXECUTOR
        // ============================================================

        System.out.println("--- Custom Executor ---");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<String> customFuture = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("  Thread: " + Thread.currentThread().getName());
                    return "Custom executor";
                }, executor);

        System.out.println(customFuture.get());

        executor.shutdown();

        System.out.println();


        // ============================================================
        // 10. PRACTICAL EXAMPLE: PARALLEL API CALLS
        // ============================================================

        System.out.println("--- Practical: Parallel API Calls ---");

        long start = System.currentTimeMillis();

        CompletableFuture<String> userService = CompletableFuture
                .supplyAsync(() -> callService("User", 200));

        CompletableFuture<String> orderService = CompletableFuture
                .supplyAsync(() -> callService("Order", 300));

        CompletableFuture<String> productService = CompletableFuture
                .supplyAsync(() -> callService("Product", 150));

        // Wait for all and combine
        CompletableFuture<Void> allServices = CompletableFuture.allOf(
                userService, orderService, productService
        );

        allServices.get();

        long duration = System.currentTimeMillis() - start;

        System.out.println("Results:");
        System.out.println("  " + userService.get());
        System.out.println("  " + orderService.get());
        System.out.println("  " + productService.get());
        System.out.println("Total time: " + duration + "ms (parallel execution)");
        System.out.println("Sequential would take: ~650ms");

        System.out.println();


        // ============================================================
        // 11. ASYNC PIPELINE EXAMPLE
        // ============================================================

        System.out.println("--- Async Pipeline ---");

        CompletableFuture<String> pipeline = CompletableFuture
                .supplyAsync(() -> {
                    System.out.println("1. Fetch user");
                    return 123;
                })
                .thenApplyAsync(userId -> {
                    System.out.println("2. Validate user: " + userId);
                    return "User-" + userId;
                })
                .thenApplyAsync(username -> {
                    System.out.println("3. Get permissions: " + username);
                    return username + ":ADMIN";
                })
                .thenApplyAsync(userWithRole -> {
                    System.out.println("4. Generate token: " + userWithRole);
                    return "TOKEN-" + userWithRole.hashCode();
                })
                .exceptionally(ex -> {
                    System.out.println("Error in pipeline: " + ex.getMessage());
                    return "ERROR";
                });

        System.out.println("Pipeline result: " + pipeline.get());

        System.out.println();


        // ============================================================
        // 12. COMMON PATTERNS
        // ============================================================

        System.out.println("--- Common Patterns ---");

        System.out.println("""
                PATTERN 1: Fallback on error
                CompletableFuture<String> result = service.fetchData()
                    .exceptionally(ex -> "default-value");

                PATTERN 2: Retry logic
                CompletableFuture<String> result = retry(
                    () -> service.call(),
                    3  // max attempts
                );

                PATTERN 3: Parallel aggregation
                List<CompletableFuture<Data>> futures = ids.stream()
                    .map(id -> fetchDataAsync(id))
                    .collect(Collectors.toList());

                List<Data> results = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());

                PATTERN 4: First successful
                CompletableFuture<String> result = CompletableFuture.anyOf(
                    service1.fetch(),
                    service2.fetch(),
                    service3.fetch()
                );

                PATTERN 5: Dependent chain
                CompletableFuture<Order> order = getUser(userId)
                    .thenCompose(user -> getOrder(user.getOrderId()))
                    .thenCompose(order -> enrichOrder(order));

                PATTERN 6: Independent combination
                CompletableFuture<Result> result = getUser(userId)
                    .thenCombine(
                        getSettings(userId),
                        (user, settings) -> new Result(user, settings)
                    );
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * COMPLETABLEFUTURE FUNDAMENTALS:
         *
         * CREATION:
         * completedFuture(value)     - Already completed
         * supplyAsync(supplier)      - Async with result
         * runAsync(runnable)         - Async without result
         *
         * TRANSFORMATION:
         * thenApply(fn)              - Transform result (sync)
         * thenApplyAsync(fn)         - Transform result (async)
         * thenCompose(fn)            - Flat map (chain futures)
         *
         * CONSUMPTION:
         * thenAccept(consumer)       - Consume result
         * thenRun(runnable)          - Run after completion
         *
         * COMBINATION:
         * thenCombine(other, fn)     - Combine two futures
         * thenAcceptBoth(other, fn)  - Consume both results
         *
         * EXCEPTION HANDLING:
         * exceptionally(fn)          - Handle exception
         * handle(fn)                 - Handle result and exception
         * whenComplete(fn)           - Callback (doesn't transform)
         *
         * WAITING:
         * allOf(...futures)          - Wait for all
         * anyOf(...futures)          - Wait for any
         *
         * GETTING RESULTS:
         * get()                      - Blocking (throws checked)
         * join()                     - Blocking (throws unchecked)
         * getNow(defaultValue)       - Non-blocking
         *
         * SYNC vs ASYNC METHODS:
         * thenApply  - Same thread
         * thenApplyAsync - Different thread
         *
         * BEST PRACTICES:
         * 1. Use async methods for I/O operations
         * 2. Handle exceptions properly
         * 3. Use custom executors for control
         * 4. Avoid blocking in callbacks
         * 5. Use thenCompose for dependent operations
         * 6. Use thenCombine for independent operations
         * 7. Set timeouts for external calls
         * 8. Log errors in pipelines
         * 9. Use join() in streams
         * 10. Consider backpressure
         *
         * WHEN TO USE:
         * ✓ REST API calls
         * ✓ Database queries
         * ✓ File I/O
         * ✓ Microservices
         * ✓ Parallel processing
         * ✗ CPU-intensive tasks (use parallelStream)
         * ✗ Simple sequential code
         *
         * COMMON MISTAKES:
         * - Blocking in callbacks
         * - Not handling exceptions
         * - Creating too many threads
         * - Mixing sync/async incorrectly
         * - Not using timeouts
         * - Ignoring executor shutdown
         *
         * PERFORMANCE TIPS:
         * - Use async for I/O, sync for CPU
         * - Reuse executor pools
         * - Set appropriate thread pool size
         * - Avoid excessive chaining
         * - Profile before optimizing
         *
         * ALTERNATIVES:
         * - RxJava (more operators)
         * - Reactor (reactive streams)
         * - Vert.x (reactive toolkit)
         * - Akka (actor model)
         */
    }

    // Helper methods

    static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static String fetchUser(int id) {
        sleep(100);
        return "User-" + id;
    }

    static String callService(String name, int delay) {
        sleep(delay);
        return name + " service response (" + delay + "ms)";
    }
}
