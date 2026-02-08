/*
 * LESSON 4: OPERATORS
 *
 * Operators are symbols that perform operations on variables and values.
 * Java has several types of operators:
 * 1. Arithmetic Operators
 * 2. Assignment Operators
 * 3. Comparison Operators (Relational)
 * 4. Logical Operators
 * 5. Increment/Decrement Operators
 */

public class Lesson4_Operators {
    public static void main(String[] args) {

        // ============================================================
        // 1. ARITHMETIC OPERATORS
        // ============================================================
        // Used to perform mathematical operations

        int a = 10;
        int b = 3;

        // Addition (+)
        int sum = a + b;
        System.out.println("Addition: " + a + " + " + b + " = " + sum);

        // Subtraction (-)
        int difference = a - b;
        System.out.println("Subtraction: " + a + " - " + b + " = " + difference);

        // Multiplication (*)
        int product = a * b;
        System.out.println("Multiplication: " + a + " * " + b + " = " + product);

        // Division (/)
        // Note: Integer division discards the decimal part
        int quotient = a / b;
        System.out.println("Division (int): " + a + " / " + b + " = " + quotient); // 3, not 3.33

        // To get decimal result, use double
        double quotientDecimal = (double) a / b;
        System.out.println("Division (double): " + a + " / " + b + " = " + quotientDecimal);

        // Modulus (%) - returns the remainder after division
        int remainder = a % b;
        System.out.println("Modulus: " + a + " % " + b + " = " + remainder);
        // 10 divided by 3 is 3 with remainder 1

        System.out.println(); // Empty line for readability


        // ============================================================
        // 2. ASSIGNMENT OPERATORS
        // ============================================================
        // Used to assign values to variables

        int x = 10; // Simple assignment

        // Compound assignment operators (shorthand)
        x += 5;  // Same as: x = x + 5
        System.out.println("x += 5 results in: " + x); // 15

        x -= 3;  // Same as: x = x - 3
        System.out.println("x -= 3 results in: " + x); // 12

        x *= 2;  // Same as: x = x * 2
        System.out.println("x *= 2 results in: " + x); // 24

        x /= 4;  // Same as: x = x / 4
        System.out.println("x /= 4 results in: " + x); // 6

        x %= 4;  // Same as: x = x % 4
        System.out.println("x %= 4 results in: " + x); // 2

        System.out.println();


        // ============================================================
        // 3. COMPARISON OPERATORS (RELATIONAL OPERATORS)
        // ============================================================
        // Used to compare two values
        // They return a boolean (true or false)

        int num1 = 5;
        int num2 = 10;

        // Equal to (==)
        boolean isEqual = (num1 == num2);
        System.out.println(num1 + " == " + num2 + " is " + isEqual); // false

        // Not equal to (!=)
        boolean isNotEqual = (num1 != num2);
        System.out.println(num1 + " != " + num2 + " is " + isNotEqual); // true

        // Greater than (>)
        boolean isGreater = (num1 > num2);
        System.out.println(num1 + " > " + num2 + " is " + isGreater); // false

        // Less than (<)
        boolean isLess = (num1 < num2);
        System.out.println(num1 + " < " + num2 + " is " + isLess); // true

        // Greater than or equal to (>=)
        boolean isGreaterOrEqual = (num1 >= 5);
        System.out.println(num1 + " >= 5 is " + isGreaterOrEqual); // true

        // Less than or equal to (<=)
        boolean isLessOrEqual = (num1 <= 10);
        System.out.println(num1 + " <= 10 is " + isLessOrEqual); // true

        System.out.println();


        // ============================================================
        // 4. LOGICAL OPERATORS
        // ============================================================
        // Used to combine multiple boolean expressions

        boolean hasLicense = true;
        boolean isAdult = true;
        boolean isTired = false;

        // AND operator (&&)
        // Returns true only if BOTH conditions are true
        boolean canDrive = hasLicense && isAdult;
        System.out.println("Can drive (has license AND is adult): " + canDrive); // true

        boolean shouldDrive = hasLicense && isAdult && !isTired;
        System.out.println("Should drive (license AND adult AND NOT tired): " + shouldDrive); // true

        // OR operator (||)
        // Returns true if AT LEAST ONE condition is true
        boolean hasTicket = false;
        boolean isVIP = true;
        boolean canEnter = hasTicket || isVIP;
        System.out.println("Can enter (has ticket OR is VIP): " + canEnter); // true

        // NOT operator (!)
        // Reverses the boolean value
        boolean isSunny = true;
        boolean isRainy = !isSunny;
        System.out.println("Is rainy (NOT sunny): " + isRainy); // false

        // Combining logical operators
        int age = 25;
        boolean hasID = true;
        boolean canBuyAlcohol = (age >= 21) && hasID;
        System.out.println("Can buy alcohol (age >= 21 AND has ID): " + canBuyAlcohol); // true

        System.out.println();


        // ============================================================
        // 5. INCREMENT AND DECREMENT OPERATORS
        // ============================================================
        // Used to increase or decrease a value by 1

        int count = 5;

        // Post-increment (count++)
        // Uses the current value, THEN increments
        System.out.println("Original count: " + count);
        System.out.println("Post-increment (count++): " + count++); // prints 5
        System.out.println("Count after post-increment: " + count); // now 6

        // Pre-increment (++count)
        // Increments FIRST, then uses the new value
        System.out.println("Pre-increment (++count): " + ++count); // prints 7
        System.out.println("Count after pre-increment: " + count); // still 7

        // Post-decrement (count--)
        // Uses the current value, THEN decrements
        System.out.println("Post-decrement (count--): " + count--); // prints 7
        System.out.println("Count after post-decrement: " + count); // now 6

        // Pre-decrement (--count)
        // Decrements FIRST, then uses the new value
        System.out.println("Pre-decrement (--count): " + --count); // prints 5
        System.out.println("Count after pre-decrement: " + count); // still 5

        System.out.println();


        // ============================================================
        // 6. OPERATOR PRECEDENCE
        // ============================================================
        // When multiple operators are used, Java follows precedence rules
        // Similar to PEMDAS in mathematics

        // Parentheses have highest precedence
        int result1 = (5 + 3) * 2; // 8 * 2 = 16
        int result2 = 5 + 3 * 2;   // 5 + 6 = 11 (multiplication first)
        System.out.println("(5 + 3) * 2 = " + result1);
        System.out.println("5 + 3 * 2 = " + result2);

        // Precedence order (high to low):
        // 1. Parentheses ()
        // 2. Unary operators (++, --, !)
        // 3. Multiplication, Division, Modulus (*, /, %)
        // 4. Addition, Subtraction (+, -)
        // 5. Comparison operators (<, >, <=, >=)
        // 6. Equality operators (==, !=)
        // 7. Logical AND (&&)
        // 8. Logical OR (||)
        // 9. Assignment (=, +=, -=, etc.)

        // Complex example
        int complexResult = 10 + 5 * 2 - 3;
        // Step 1: 5 * 2 = 10
        // Step 2: 10 + 10 = 20
        // Step 3: 20 - 3 = 17
        System.out.println("10 + 5 * 2 - 3 = " + complexResult);

        // Always use parentheses when in doubt!
        int clearResult = ((10 + 5) * 2) - 3;
        System.out.println("((10 + 5) * 2) - 3 = " + clearResult);


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        // Example 1: Calculate area of rectangle
        int length = 10;
        int width = 5;
        int area = length * width;
        System.out.println("\nRectangle area (10 x 5): " + area);

        // Example 2: Check if number is even
        int number = 8;
        boolean isEven = (number % 2 == 0);
        System.out.println(number + " is even: " + isEven);

        // Example 3: Temperature check
        int temperature = 75;
        boolean isComfortable = (temperature >= 68 && temperature <= 78);
        System.out.println("Temperature " + temperature + "°F is comfortable: " + isComfortable);

        // Example 4: Discount eligibility
        boolean isMember = true;
        int purchaseAmount = 150;
        boolean getsDiscount = isMember && (purchaseAmount > 100);
        System.out.println("Customer gets discount: " + getsDiscount);
    }
}
