/*
 * LESSON 5: USER INPUT
 *
 * So far, all our programs have used hardcoded values.
 * To make programs interactive, we need to get input from the user.
 *
 * Java provides the Scanner class to read user input from the keyboard.
 * Scanner is part of the java.util package.
 */

// We need to import the Scanner class before using it
// 'import' tells Java to include code from another package
import java.util.Scanner;

public class Lesson5_UserInput {
    public static void main(String[] args) {

        // ============================================================
        // CREATING A SCANNER OBJECT
        // ============================================================

        // Create a Scanner object to read input
        // 'new' keyword creates a new object
        // System.in means "standard input" (keyboard)
        Scanner scanner = new Scanner(System.in);

        // Scanner is a class (starts with capital letter)
        // scanner is the object we created (variable name, starts with lowercase)


        // ============================================================
        // READING DIFFERENT TYPES OF INPUT
        // ============================================================

        // Reading a String (text)
        System.out.print("Enter your name: "); // print (no 'ln') keeps cursor on same line
        String name = scanner.nextLine(); // nextLine() reads the entire line
        System.out.println("Hello, " + name + "!");

        System.out.println(); // Empty line


        // Reading an integer
        System.out.print("Enter your age: ");
        int age = scanner.nextInt(); // nextInt() reads an integer
        System.out.println("You are " + age + " years old.");

        // IMPORTANT: After reading a number with nextInt(), nextDouble(), etc.,
        // there's still a newline character left in the buffer.
        // We need to consume it before reading the next line of text.
        scanner.nextLine(); // This consumes the leftover newline

        System.out.println();


        // Reading a double (decimal number)
        System.out.print("Enter your height in meters (e.g., 1.75): ");
        double height = scanner.nextDouble(); // nextDouble() reads a decimal number
        System.out.println("Your height is " + height + " meters.");

        scanner.nextLine(); // Consume leftover newline

        System.out.println();


        // Reading a boolean
        // Note: User should type "true" or "false"
        System.out.print("Are you a student? (true/false): ");
        boolean isStudent = scanner.nextBoolean();
        System.out.println("Student status: " + isStudent);

        scanner.nextLine(); // Consume leftover newline

        System.out.println();


        // ============================================================
        // SCANNER METHODS SUMMARY
        // ============================================================
        /*
         * scanner.nextLine()    - reads a full line of text (String)
         * scanner.next()        - reads a single word (String, stops at space)
         * scanner.nextInt()     - reads an integer
         * scanner.nextDouble()  - reads a double
         * scanner.nextFloat()   - reads a float
         * scanner.nextBoolean() - reads a boolean (true/false)
         * scanner.nextByte()    - reads a byte
         * scanner.nextShort()   - reads a short
         * scanner.nextLong()    - reads a long
         */


        // ============================================================
        // PRACTICAL EXAMPLE: SIMPLE CALCULATOR
        // ============================================================

        System.out.println("\n--- SIMPLE CALCULATOR ---");
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        // Calculate results
        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;
        double quotient = num1 / num2;

        // Display results
        System.out.println("\nResults:");
        System.out.println(num1 + " + " + num2 + " = " + sum);
        System.out.println(num1 + " - " + num2 + " = " + difference);
        System.out.println(num1 + " * " + num2 + " = " + product);
        System.out.println(num1 + " / " + num2 + " = " + quotient);


        // ============================================================
        // CLOSING THE SCANNER
        // ============================================================

        // It's good practice to close the Scanner when done
        // This releases the resource (keyboard connection)
        scanner.close();

        // NOTE: After closing, you cannot use the scanner anymore
        // If you try, you'll get an error


        // ============================================================
        // COMMON ISSUES AND TIPS
        // ============================================================

        /*
         * 1. INPUT MISMATCH EXCEPTION
         *    - If user enters "abc" when program expects a number, Java throws an error
         *    - Always validate user input in real programs (we'll learn this later)
         *
         * 2. NEWLINE BUFFER ISSUE
         *    - After nextInt(), nextDouble(), etc., always use scanner.nextLine()
         *    - This clears the leftover newline character
         *
         * 3. next() vs nextLine()
         *    - next() reads one word (stops at space)
         *    - nextLine() reads the entire line (stops at Enter)
         *
         *    Example:
         *    User types: "John Doe"
         *    next() would read "John"
         *    nextLine() would read "John Doe"
         *
         * 4. SCANNER RESOURCE WARNING
         *    - You might see a warning about not closing the Scanner
         *    - Always close it with scanner.close()
         */
    }
}
