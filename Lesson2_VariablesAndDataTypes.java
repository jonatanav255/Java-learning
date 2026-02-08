/*
 * LESSON 2: VARIABLES AND DATA TYPES
 *
 * A variable is a container that holds data.
 * Java is a "strongly typed" language - every variable must have a type.
 *
 * PRIMITIVE DATA TYPES (8 types in Java):
 * - byte, short, int, long (for whole numbers)
 * - float, double (for decimal numbers)
 * - char (for single characters)
 * - boolean (for true/false values)
 */

public class Lesson2_VariablesAndDataTypes {
    public static void main(String[] args) {

        // ===== INTEGER TYPES =====
        // These store whole numbers (no decimals)

        // byte: -128 to 127 (8 bits, 1 byte)
        // Use when you need to save memory and know the value is small
        byte myByte = 100;
        System.out.println("Byte value: " + myByte);

        // short: -32,768 to 32,767 (16 bits, 2 bytes)
        short myShort = 5000;
        System.out.println("Short value: " + myShort);

        // int: -2,147,483,648 to 2,147,483,647 (32 bits, 4 bytes)
        // This is the most commonly used integer type
        int myInt = 100000;
        System.out.println("Int value: " + myInt);

        // long: very large range (64 bits, 8 bytes)
        // Add 'L' at the end to indicate it's a long
        long myLong = 15000000000L;
        System.out.println("Long value: " + myLong);


        // ===== DECIMAL TYPES =====
        // These store numbers with decimal points

        // float: single-precision decimal (32 bits)
        // Add 'f' at the end to indicate it's a float
        float myFloat = 5.99f;
        System.out.println("Float value: " + myFloat);

        // double: double-precision decimal (64 bits)
        // This is the default for decimal numbers and most commonly used
        double myDouble = 19.99;
        System.out.println("Double value: " + myDouble);


        // ===== CHARACTER TYPE =====
        // char: stores a single character (16 bits)
        // Use single quotes for char
        char myChar = 'A';
        System.out.println("Char value: " + myChar);

        // You can also use Unicode values
        char myUnicode = 65; // 65 is the Unicode for 'A'
        System.out.println("Unicode char: " + myUnicode);


        // ===== BOOLEAN TYPE =====
        // boolean: stores true or false
        // Used for conditions and logic
        boolean isJavaFun = true;
        boolean isFishTasty = false;
        System.out.println("Is Java fun? " + isJavaFun);
        System.out.println("Is fish tasty? " + isFishTasty);


        // ===== STRING TYPE =====
        // String is NOT a primitive type - it's a class (reference type)
        // Strings store text and use double quotes
        // String starts with capital S because it's a class
        String greeting = "Hello, Java learner!";
        System.out.println(greeting);


        // ===== VARIABLE NAMING RULES =====
        // 1. Must start with a letter, $ or _
        // 2. Cannot start with a number
        // 3. Cannot contain spaces
        // 4. Cannot use Java keywords (like int, class, public, etc.)
        // 5. Are case-sensitive (myVar and myvar are different)

        // Good variable names (camelCase convention):
        int studentAge = 20;
        String firstName = "John";
        boolean isActive = true;

        // Variables can be reassigned (changed)
        int number = 10;
        System.out.println("Number before: " + number);
        number = 20; // changing the value
        System.out.println("Number after: " + number);


        // ===== CONSTANTS =====
        // Use 'final' keyword to create constants (cannot be changed)
        // Convention: use UPPERCASE for constant names
        final double PI = 3.14159;
        System.out.println("PI value: " + PI);

        // This would cause an error if uncommented:
        // PI = 3.14; // Error! Cannot assign a value to final variable


        // ===== TYPE CASTING =====
        // Converting one type to another

        // Automatic casting (widening) - smaller to larger type
        int myInt2 = 9;
        double myDouble2 = myInt2; // int to double (automatic)
        System.out.println("Int to double: " + myDouble2);

        // Manual casting (narrowing) - larger to smaller type
        double myDouble3 = 9.78;
        int myInt3 = (int) myDouble3; // double to int (loses decimal part)
        System.out.println("Double to int: " + myInt3); // outputs 9
    }
}
