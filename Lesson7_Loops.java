/*
 * LESSON 7: LOOPS
 *
 * Loops allow you to execute a block of code repeatedly.
 * Instead of writing the same code multiple times, use a loop!
 *
 * Types of loops in Java:
 * 1. for loop
 * 2. while loop
 * 3. do-while loop
 * 4. Enhanced for loop (for-each)
 *
 * Loop control statements:
 * - break: exits the loop completely
 * - continue: skips to the next iteration
 */

public class Lesson7_Loops {
    public static void main(String[] args) {

        // ============================================================
        // 1. FOR LOOP
        // ============================================================
        // Use when you know HOW MANY times to repeat
        // Syntax: for (initialization; condition; update) { }

        System.out.println("=== FOR LOOP ===");

        // Print numbers 1 to 5
        for (int i = 1; i <= 5; i++) {
            System.out.println("Count: " + i);
        }
        // i = 1: starts at 1
        // i <= 5: continues while i is less than or equal to 5
        // i++: increases i by 1 after each iteration

        System.out.println();


        // Countdown from 10 to 1
        for (int i = 10; i >= 1; i--) {
            System.out.println("Countdown: " + i);
        }

        System.out.println();


        // Count by 2s (even numbers)
        for (int i = 0; i <= 10; i += 2) {
            System.out.println("Even number: " + i);
        }

        System.out.println();


        // ============================================================
        // 2. WHILE LOOP
        // ============================================================
        // Use when you DON'T know how many times to repeat
        // Condition is checked BEFORE each iteration

        System.out.println("=== WHILE LOOP ===");

        int count = 1;
        while (count <= 5) {
            System.out.println("While count: " + count);
            count++; // Don't forget to update! Otherwise, infinite loop
        }

        System.out.println();


        // Practical example: Keep doubling until > 100
        int number = 1;
        while (number <= 100) {
            System.out.println("Number: " + number);
            number *= 2; // double the number
        }

        System.out.println();


        // ============================================================
        // 3. DO-WHILE LOOP
        // ============================================================
        // Similar to while, but condition is checked AFTER each iteration
        // Executes AT LEAST ONCE, even if condition is false

        System.out.println("=== DO-WHILE LOOP ===");

        int num = 1;
        do {
            System.out.println("Do-while num: " + num);
            num++;
        } while (num <= 5);

        System.out.println();


        // Key difference: This runs once even though condition is false
        int x = 10;
        do {
            System.out.println("This runs once: " + x);
        } while (x < 5); // Condition is false, but code ran once

        System.out.println();


        // ============================================================
        // 4. NESTED LOOPS
        // ============================================================
        // A loop inside another loop

        System.out.println("=== NESTED LOOPS ===");

        // Multiplication table
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i + " x " + j + " = " + (i * j) + "\t");
            }
            System.out.println(); // New line after inner loop completes
        }

        System.out.println();


        // Print a pattern
        for (int row = 1; row <= 4; row++) {
            for (int col = 1; col <= row; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }
        /*
         * Output:
         * *
         * * *
         * * * *
         * * * * *
         */

        System.out.println();


        // ============================================================
        // 5. BREAK STATEMENT
        // ============================================================
        // Exits the loop immediately

        System.out.println("=== BREAK STATEMENT ===");

        // Find first number divisible by 7
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("First number divisible by 7: " + i);
                break; // Exit the loop
            }
        }

        System.out.println();


        // Stop when condition is met
        int counter = 0;
        while (true) { // Infinite loop
            counter++;
            System.out.println("Counter: " + counter);
            if (counter == 3) {
                System.out.println("Breaking out!");
                break; // Exit the infinite loop
            }
        }

        System.out.println();


        // ============================================================
        // 6. CONTINUE STATEMENT
        // ============================================================
        // Skips the current iteration and moves to the next one

        System.out.println("=== CONTINUE STATEMENT ===");

        // Print odd numbers (skip even)
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip even numbers
            }
            System.out.println("Odd number: " + i);
        }

        System.out.println();


        // Skip multiples of 3
        for (int i = 1; i <= 10; i++) {
            if (i % 3 == 0) {
                continue; // Skip this iteration
            }
            System.out.print(i + " "); // 1 2 4 5 7 8 10
        }
        System.out.println("\n");


        // ============================================================
        // 7. ENHANCED FOR LOOP (FOR-EACH)
        // ============================================================
        // Used to iterate through arrays or collections
        // We'll cover arrays in the next lesson, but here's a preview

        System.out.println("=== ENHANCED FOR LOOP ===");

        // Array of numbers
        int[] numbers = {10, 20, 30, 40, 50};

        // Traditional for loop
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Traditional: " + numbers[i]);
        }

        System.out.println();

        // Enhanced for loop (easier!)
        for (int value : numbers) {
            System.out.println("Enhanced: " + value);
        }
        // Reads as: "for each value in numbers"

        System.out.println();


        // With strings
        String[] fruits = {"Apple", "Banana", "Cherry"};
        for (String fruit : fruits) {
            System.out.println("Fruit: " + fruit);
        }

        System.out.println();


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        System.out.println("=== PRACTICAL EXAMPLES ===");

        // Example 1: Calculate factorial
        int n = 5;
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        System.out.println(n + "! = " + factorial); // 5! = 120

        System.out.println();


        // Example 2: Sum of numbers 1 to 100
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        System.out.println("Sum of 1 to 100: " + sum); // 5050

        System.out.println();


        // Example 3: Find prime numbers up to 20
        System.out.println("Prime numbers up to 20:");
        for (int i = 2; i <= 20; i++) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");


        // Example 4: Fibonacci sequence (first 10 numbers)
        System.out.println("Fibonacci sequence:");
        int prev = 0, curr = 1;
        System.out.print(prev + " " + curr + " ");
        for (int i = 2; i < 10; i++) {
            int next = prev + curr;
            System.out.print(next + " ");
            prev = curr;
            curr = next;
        }
        System.out.println("\n");


        // Example 5: Reverse a number
        int original = 12345;
        int reversed = 0;
        while (original != 0) {
            int digit = original % 10; // Get last digit
            reversed = reversed * 10 + digit; // Add to reversed
            original /= 10; // Remove last digit
        }
        System.out.println("Reversed number: " + reversed); // 54321

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * 1. FOR LOOP: Use when you know the number of iterations
         *    - Best for counting, iterating through ranges
         *
         * 2. WHILE LOOP: Use when condition-based looping
         *    - Condition checked BEFORE execution
         *    - May not execute at all if condition is false
         *
         * 3. DO-WHILE LOOP: Use when you need at least one execution
         *    - Condition checked AFTER execution
         *    - Executes at least once
         *
         * 4. ENHANCED FOR: Use for arrays/collections
         *    - Simpler syntax
         *    - Cannot modify the index
         *
         * 5. BREAK: Exit loop immediately
         * 6. CONTINUE: Skip current iteration, move to next
         *
         * 7. INFINITE LOOPS: Make sure your loop has an exit condition!
         *    - Always update loop variables
         *    - Use break carefully in while(true) loops
         */
    }
}
