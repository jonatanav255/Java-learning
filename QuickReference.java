/*
 * JAVA QUICK REFERENCE GUIDE
 *
 * A cheat sheet with common Java syntax and patterns.
 * Use this as a quick lookup when coding.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class QuickReference {
    public static void main(String[] args) {

        // ==================== VARIABLES ====================
        // Primitive types
        int age = 25;
        double price = 19.99;
        boolean isActive = true;
        char grade = 'A';

        // String
        String name = "John";

        // Constants
        final double PI = 3.14159;


        // ==================== OPERATORS ====================
        // Arithmetic: + - * / %
        int sum = 10 + 5;
        int product = 10 * 5;
        int remainder = 10 % 3;

        // Comparison: == != < > <= >=
        boolean isEqual = (5 == 5);
        boolean isGreater = (10 > 5);

        // Logical: && || !
        boolean canDrive = (age >= 18) && hasLicense();
        boolean hasAccess = isAdmin() || isModerator();
        boolean isNotEmpty = !name.isEmpty();

        // Increment/Decrement
        age++;      // Post-increment
        ++age;      // Pre-increment
        age--;      // Post-decrement
        --age;      // Pre-decrement


        // ==================== CONDITIONALS ====================
        // If-else
        if (age >= 18) {
            System.out.println("Adult");
        } else if (age >= 13) {
            System.out.println("Teenager");
        } else {
            System.out.println("Child");
        }

        // Ternary operator
        String status = (age >= 18) ? "Adult" : "Minor";

        // Switch
        int day = 1;
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            default:
                System.out.println("Other day");
        }


        // ==================== LOOPS ====================
        // For loop
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }

        // While loop
        int count = 0;
        while (count < 5) {
            System.out.println(count);
            count++;
        }

        // Do-while loop
        do {
            System.out.println("Runs at least once");
        } while (false);

        // Enhanced for (for-each)
        int[] numbers = {1, 2, 3, 4, 5};
        for (int num : numbers) {
            System.out.println(num);
        }


        // ==================== ARRAYS ====================
        // Declaration and initialization
        int[] arr1 = new int[5];
        int[] arr2 = {1, 2, 3, 4, 5};

        // Access and modify
        int first = arr2[0];
        arr2[0] = 10;

        // Length
        int length = arr2.length;

        // 2D Array
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int element = matrix[0][1]; // 2


        // ==================== ARRAYLIST ====================
        // Create
        ArrayList<String> list = new ArrayList<>();

        // Add
        list.add("Apple");
        list.add("Banana");

        // Get
        String item = list.get(0);

        // Set (replace)
        list.set(0, "Orange");

        // Remove
        list.remove(0);              // by index
        list.remove("Banana");       // by value

        // Size
        int size = list.size();

        // Check
        boolean contains = list.contains("Apple");
        boolean empty = list.isEmpty();

        // Loop
        for (String fruit : list) {
            System.out.println(fruit);
        }


        // ==================== STRINGS ====================
        String str = "Hello World";

        // Common methods
        int len = str.length();
        char ch = str.charAt(0);
        String sub = str.substring(0, 5);
        String upper = str.toUpperCase();
        String lower = str.toLowerCase();
        String trimmed = str.trim();
        String replaced = str.replace("World", "Java");
        String[] parts = str.split(" ");

        // Comparison
        boolean equals = str.equals("Hello World");
        boolean equalsIgnoreCase = str.equalsIgnoreCase("hello world");

        // Checking
        boolean startsWith = str.startsWith("Hello");
        boolean endsWith = str.endsWith("World");
        boolean containsWord = str.contains("World");

        // StringBuilder (for mutable strings)
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" World");
        String result = sb.toString();


        // ==================== USER INPUT ====================
        Scanner scanner = new Scanner(System.in);

        // Read different types
        // String line = scanner.nextLine();
        // int number = scanner.nextInt();
        // double decimal = scanner.nextDouble();
        // boolean bool = scanner.nextBoolean();

        scanner.close();


        // ==================== EXCEPTION HANDLING ====================
        try {
            int division = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Always runs");
        }

        // Multiple catch blocks
        try {
            // risky code
        } catch (NullPointerException e) {
            // handle null pointer
        } catch (ArrayIndexOutOfBoundsException e) {
            // handle array index
        } catch (Exception e) {
            // catch all other exceptions
        }


        // ==================== METHODS ====================
        // See method definitions below
        printMessage("Hello");
        int sum2 = add(5, 3);
        boolean isEven = isEven(10);


        // ==================== CLASSES AND OBJECTS ====================
        // Create object
        Person person = new Person("Alice", 25);
        person.introduce();
        String personName = person.getName();


        // ==================== COMMON PATTERNS ====================

        // Find max in array
        int[] nums = {5, 2, 8, 1, 9};
        int max = nums[0];
        for (int n : nums) {
            if (n > max) max = n;
        }

        // Count occurrences
        String text = "hello world hello";
        int countHello = 0;
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.equals("hello")) countHello++;
        }

        // Reverse array
        int[] toReverse = {1, 2, 3, 4, 5};
        for (int i = 0; i < toReverse.length / 2; i++) {
            int temp = toReverse[i];
            toReverse[i] = toReverse[toReverse.length - 1 - i];
            toReverse[toReverse.length - 1 - i] = temp;
        }

        // Check if palindrome
        String word = "racecar";
        boolean isPalindrome = true;
        for (int i = 0; i < word.length() / 2; i++) {
            if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
                isPalindrome = false;
                break;
            }
        }
    }

    // ==================== METHOD EXAMPLES ====================

    public static void printMessage(String msg) {
        System.out.println(msg);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static boolean hasLicense() {
        return true;
    }

    public static boolean isAdmin() {
        return false;
    }

    public static boolean isModerator() {
        return false;
    }
}

// ==================== CLASS EXAMPLE ====================
class Person {
    private String name;
    private int age;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    // Method
    public void introduce() {
        System.out.println("Hi, I'm " + name);
    }
}


/*
 * ==================== COMMON CONVENTIONS ====================
 *
 * NAMING:
 * - Classes: PascalCase (MyClass)
 * - Methods/Variables: camelCase (myVariable, calculateTotal)
 * - Constants: UPPER_SNAKE_CASE (MAX_SIZE, PI)
 * - Packages: lowercase (com.company.project)
 *
 * CODE STYLE:
 * - Indent with 4 spaces
 * - Opening brace on same line
 * - One statement per line
 * - Meaningful variable names
 * - Comment complex logic
 *
 * BEST PRACTICES:
 * - Use final for constants
 * - Make fields private (encapsulation)
 * - Handle exceptions appropriately
 * - Close resources (Scanner, Files, etc.)
 * - Avoid magic numbers
 * - Keep methods short and focused
 * - Use ArrayList over Array when size changes
 * - Compare strings with .equals(), not ==
 */
