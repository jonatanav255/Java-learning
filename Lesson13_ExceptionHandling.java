/*
 * LESSON 13: EXCEPTION HANDLING
 *
 * An exception is an unexpected event that disrupts the normal flow of a program.
 * Exception handling allows you to handle errors gracefully instead of crashing.
 *
 * KEY CONCEPTS:
 * - Exception: An object representing an error or unexpected condition
 * - try: Block of code that might throw an exception
 * - catch: Block that handles the exception
 * - finally: Block that always executes (cleanup code)
 * - throw: Manually throw an exception
 * - throws: Declare that a method might throw an exception
 *
 * EXCEPTION HIERARCHY:
 * Throwable
 * ├── Error (serious problems, usually can't recover)
 * └── Exception
 *     ├── IOException
 *     ├── SQLException
 *     ├── RuntimeException (unchecked)
 *     │   ├── NullPointerException
 *     │   ├── ArrayIndexOutOfBoundsException
 *     │   ├── ArithmeticException
 *     │   └── NumberFormatException
 *     └── ... (checked exceptions)
 */

public class Lesson13_ExceptionHandling {
    public static void main(String[] args) {

        System.out.println("=== EXCEPTION HANDLING ===\n");

        // ============================================================
        // 1. BASIC TRY-CATCH
        // ============================================================

        System.out.println("Example 1: Division by zero");
        try {
            // Code that might throw an exception
            int result = 10 / 0; // ArithmeticException!
            System.out.println("Result: " + result); // This line won't execute
        } catch (ArithmeticException e) {
            // Handle the exception
            System.out.println("Error: Cannot divide by zero!");
            System.out.println("Exception message: " + e.getMessage());
        }
        System.out.println("Program continues...\n");


        // ============================================================
        // 2. ARRAY INDEX OUT OF BOUNDS
        // ============================================================

        System.out.println("Example 2: Array index");
        int[] numbers = {1, 2, 3};

        try {
            System.out.println(numbers[5]); // Index doesn't exist!
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Invalid array index");
        }
        System.out.println();


        // ============================================================
        // 3. NULL POINTER EXCEPTION
        // ============================================================

        System.out.println("Example 3: Null pointer");
        String text = null;

        try {
            int length = text.length(); // Calling method on null!
        } catch (NullPointerException e) {
            System.out.println("Error: Cannot call method on null object");
        }
        System.out.println();


        // ============================================================
        // 4. NUMBER FORMAT EXCEPTION
        // ============================================================

        System.out.println("Example 4: Parsing number");
        String invalidNumber = "abc";

        try {
            int num = Integer.parseInt(invalidNumber); // Cannot parse "abc"!
        } catch (NumberFormatException e) {
            System.out.println("Error: '" + invalidNumber + "' is not a valid number");
        }
        System.out.println();


        // ============================================================
        // 5. MULTIPLE CATCH BLOCKS
        // ============================================================

        System.out.println("Example 5: Multiple catch blocks");
        try {
            String str = null;
            System.out.println(str.length());
        } catch (NullPointerException e) {
            System.out.println("Caught: NullPointerException");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught: ArrayIndexOutOfBoundsException");
        } catch (Exception e) {
            // Catch-all for any other exception
            System.out.println("Caught: Some other exception");
        }
        System.out.println();


        // ============================================================
        // 6. TRY-CATCH-FINALLY
        // ============================================================

        System.out.println("Example 6: Finally block");
        try {
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Error occurred");
        } finally {
            // This ALWAYS executes, whether exception occurs or not
            System.out.println("Finally block executed (cleanup code)");
        }
        System.out.println();


        // ============================================================
        // 7. TRY-CATCH WITH FINALLY (EXCEPTION OCCURS)
        // ============================================================

        System.out.println("Example 7: Finally with exception");
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught exception");
        } finally {
            System.out.println("Finally executes even when exception occurs");
        }
        System.out.println();


        // ============================================================
        // 8. GETTING EXCEPTION INFORMATION
        // ============================================================

        System.out.println("Example 8: Exception information");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);
        } catch (Exception e) {
            System.out.println("Exception class: " + e.getClass().getName());
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("Stack trace:");
            e.printStackTrace(); // Prints full error details
        }
        System.out.println();


        // ============================================================
        // 9. THROWING EXCEPTIONS
        // ============================================================

        System.out.println("Example 9: Throwing exceptions");

        try {
            checkAge(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            checkAge(25);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 10. CUSTOM EXCEPTIONS
        // ============================================================

        System.out.println("Example 10: Custom exceptions");

        try {
            withdraw(1000, 500);
            withdraw(1000, 1500); // Throws custom exception
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Amount short: $" + e.getAmountShort());
        }

        System.out.println();


        // ============================================================
        // 11. PRACTICAL EXAMPLE: SAFE DIVISION
        // ============================================================

        System.out.println("Example 11: Safe division");
        System.out.println("10 / 2 = " + safeDivide(10, 2));
        System.out.println("10 / 0 = " + safeDivide(10, 0));
        System.out.println();


        // ============================================================
        // 12. PRACTICAL EXAMPLE: SAFE PARSING
        // ============================================================

        System.out.println("Example 12: Safe parsing");
        System.out.println("Parse '123': " + safeParseInt("123"));
        System.out.println("Parse 'abc': " + safeParseInt("abc"));
        System.out.println();


        // ============================================================
        // 13. MULTI-CATCH (JAVA 7+)
        // ============================================================

        System.out.println("Example 13: Multi-catch");
        try {
            // Simulate some operation
            String str = "hello";
            int num = Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            // Catch multiple exception types in one block
            System.out.println("Caught: " + e.getClass().getSimpleName());
        }
        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * CHECKED vs UNCHECKED EXCEPTIONS:
         *
         * 1. CHECKED EXCEPTIONS (must handle or declare):
         *    - IOException, SQLException, ClassNotFoundException
         *    - Compiler forces you to handle them
         *    - Use try-catch or declare with 'throws'
         *
         * 2. UNCHECKED EXCEPTIONS (optional to handle):
         *    - RuntimeException and its subclasses
         *    - NullPointerException, ArrayIndexOutOfBoundsException
         *    - Usually indicate programming errors
         *
         * BEST PRACTICES:
         * 1. Catch specific exceptions first, general ones last
         * 2. Don't catch Exception unless necessary
         * 3. Use finally for cleanup (close files, connections)
         * 4. Don't ignore exceptions (empty catch blocks are bad!)
         * 5. Throw meaningful exception messages
         * 6. Log exceptions for debugging
         * 7. Create custom exceptions for domain-specific errors
         *
         * COMMON EXCEPTIONS:
         * - NullPointerException: Using null reference
         * - ArrayIndexOutOfBoundsException: Invalid array index
         * - ArithmeticException: Divide by zero
         * - NumberFormatException: Invalid string to number conversion
         * - IllegalArgumentException: Invalid method argument
         * - IOException: File/network errors
         * - ClassCastException: Invalid type casting
         */
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    // Method that throws an exception
    public static void checkAge(int age) {
        if (age < 18) {
            // Throw an exception
            throw new IllegalArgumentException("Age must be 18 or older. Got: " + age);
        }
        System.out.println("Age " + age + " is valid");
    }

    // Method that declares it might throw an exception
    public static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            double shortage = amount - balance;
            throw new InsufficientFundsException(shortage);
        }
        System.out.println("Withdrawal successful: $" + amount);
    }

    // Safe division method
    public static double safeDivide(double a, double b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero, returning 0");
            return 0;
        }
    }

    // Safe integer parsing
    public static int safeParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format, returning 0");
            return 0;
        }
    }
}


// ============================================================
// CUSTOM EXCEPTION CLASS
// ============================================================
class InsufficientFundsException extends Exception {
    private double amountShort;

    public InsufficientFundsException(double amountShort) {
        super("Insufficient funds. Short by: $" + amountShort);
        this.amountShort = amountShort;
    }

    public double getAmountShort() {
        return amountShort;
    }
}
