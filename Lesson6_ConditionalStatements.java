/*
 * LESSON 6: CONDITIONAL STATEMENTS (IF/ELSE, SWITCH)
 *
 * Conditional statements allow your program to make decisions.
 * They execute different code based on whether conditions are true or false.
 *
 * Types of conditional statements:
 * 1. if statement
 * 2. if-else statement
 * 3. if-else-if ladder
 * 4. Nested if statements
 * 5. Ternary operator
 * 6. Switch statement
 */

public class Lesson6_ConditionalStatements {
    public static void main(String[] args) {

        // ============================================================
        // 1. SIMPLE IF STATEMENT
        // ============================================================
        // Executes code only if the condition is true

        int temperature = 30;

        if (temperature > 25) {
            System.out.println("It's hot outside!");
        }
        // If temperature is NOT > 25, the code inside { } is skipped

        System.out.println(); // Empty line


        // ============================================================
        // 2. IF-ELSE STATEMENT
        // ============================================================
        // Executes one block if condition is true, another if false

        int age = 16;

        if (age >= 18) {
            System.out.println("You are an adult.");
        } else {
            System.out.println("You are a minor.");
        }
        // Only ONE of these blocks will execute

        System.out.println();


        // ============================================================
        // 3. IF-ELSE-IF LADDER
        // ============================================================
        // Tests multiple conditions in sequence
        // Executes the first block whose condition is true

        int score = 85;

        if (score >= 90) {
            System.out.println("Grade: A (Excellent!)");
        } else if (score >= 80) {
            System.out.println("Grade: B (Good job!)");
        } else if (score >= 70) {
            System.out.println("Grade: C (Passing)");
        } else if (score >= 60) {
            System.out.println("Grade: D (Needs improvement)");
        } else {
            System.out.println("Grade: F (Failed)");
        }
        // Only ONE block executes - the first one with a true condition

        System.out.println();


        // ============================================================
        // 4. NESTED IF STATEMENTS
        // ============================================================
        // An if statement inside another if statement

        boolean hasLicense = true;
        int driverAge = 20;

        if (hasLicense) {
            // This block runs only if hasLicense is true
            if (driverAge >= 18) {
                System.out.println("You can drive!");
            } else {
                System.out.println("You have a license but you're too young.");
            }
        } else {
            System.out.println("You need a license to drive.");
        }

        System.out.println();


        // ============================================================
        // 5. LOGICAL OPERATORS IN CONDITIONS
        // ============================================================
        // Combine multiple conditions using &&, ||, !

        int hour = 14; // 2 PM
        boolean isWeekend = false;

        // AND operator (&&) - both must be true
        if (hour >= 9 && hour <= 17) {
            System.out.println("Office hours: 9 AM - 5 PM");
        }

        // OR operator (||) - at least one must be true
        if (hour < 9 || hour > 17 || isWeekend) {
            System.out.println("Outside office hours");
        }

        // NOT operator (!) - reverses the condition
        if (!isWeekend) {
            System.out.println("It's a weekday");
        }

        // Complex condition
        boolean canTakeBreak = (hour == 12) || (hour == 15 && !isWeekend);
        if (canTakeBreak) {
            System.out.println("Break time!");
        }

        System.out.println();


        // ============================================================
        // 6. TERNARY OPERATOR (CONDITIONAL OPERATOR)
        // ============================================================
        // Shorthand for simple if-else statements
        // Syntax: condition ? valueIfTrue : valueIfFalse

        int number = 7;

        // Long way with if-else:
        String result1;
        if (number % 2 == 0) {
            result1 = "even";
        } else {
            result1 = "odd";
        }

        // Short way with ternary operator:
        String result2 = (number % 2 == 0) ? "even" : "odd";

        System.out.println(number + " is " + result2);

        // Another example
        int a = 10, b = 20;
        int max = (a > b) ? a : b; // Find the larger number
        System.out.println("Maximum of " + a + " and " + b + " is: " + max);

        System.out.println();


        // ============================================================
        // 7. SWITCH STATEMENT
        // ============================================================
        // Tests a variable against multiple values
        // Good for checking one variable against many possible values

        int dayNumber = 3;

        switch (dayNumber) {
            case 1:
                System.out.println("Monday");
                break; // Exit the switch block
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                // Executes if no case matches
                System.out.println("Invalid day number");
        }

        System.out.println();


        // Switch with strings (Java 7+)
        String month = "March";

        switch (month) {
            case "January":
            case "February":
            case "December":
                System.out.println(month + " is a winter month");
                break;
            case "March":
            case "April":
            case "May":
                System.out.println(month + " is a spring month");
                break;
            case "June":
            case "July":
            case "August":
                System.out.println(month + " is a summer month");
                break;
            case "September":
            case "October":
            case "November":
                System.out.println(month + " is a fall month");
                break;
            default:
                System.out.println("Invalid month");
        }

        System.out.println();


        // ============================================================
        // IMPORTANT: THE BREAK STATEMENT IN SWITCH
        // ============================================================

        // Without break, execution "falls through" to the next case
        int level = 2;

        System.out.println("Without break:");
        switch (level) {
            case 1:
                System.out.println("Level 1");
                // No break - falls through to case 2
            case 2:
                System.out.println("Level 2");
                // No break - falls through to case 3
            case 3:
                System.out.println("Level 3");
                break; // Stops here
            default:
                System.out.println("Unknown level");
        }
        // This will print "Level 2" and "Level 3"

        System.out.println();


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        // Example 1: Login system
        String username = "admin";
        String password = "12345";

        if (username.equals("admin") && password.equals("12345")) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials.");
        }
        // Note: Use .equals() to compare strings, NOT ==

        System.out.println();


        // Example 2: Grade calculator with multiple criteria
        int examScore = 75;
        int attendance = 90; // percentage

        if (examScore >= 80 && attendance >= 80) {
            System.out.println("Excellent performance!");
        } else if (examScore >= 70 && attendance >= 70) {
            System.out.println("Good performance.");
        } else if (examScore >= 60) {
            System.out.println("Passing, but improve attendance.");
        } else {
            System.out.println("Need to improve both score and attendance.");
        }

        System.out.println();


        // Example 3: Menu selection
        char menuChoice = 'B';

        switch (menuChoice) {
            case 'A':
            case 'a':
                System.out.println("You selected: Add new record");
                break;
            case 'B':
            case 'b':
                System.out.println("You selected: View records");
                break;
            case 'C':
            case 'c':
                System.out.println("You selected: Delete record");
                break;
            case 'Q':
            case 'q':
                System.out.println("Exiting program...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * 1. Use 'if' for simple true/false decisions
         * 2. Use 'if-else' when you have two options
         * 3. Use 'if-else-if' for multiple conditions in a sequence
         * 4. Use 'ternary operator' for simple one-line if-else
         * 5. Use 'switch' when comparing one variable against many values
         * 6. Always use .equals() to compare strings, not ==
         * 7. Use break in switch to prevent fall-through
         * 8. Combine conditions with && (AND), || (OR), ! (NOT)
         */
    }
}
