/*
 * LESSON 9: METHODS (FUNCTIONS)
 *
 * A method is a block of code that performs a specific task.
 * Methods help organize code, avoid repetition, and make programs more readable.
 *
 * Benefits of methods:
 * - Code reusability (write once, use many times)
 * - Better organization
 * - Easier debugging and testing
 * - Easier to read and maintain
 *
 * Method syntax:
 * accessModifier returnType methodName(parameters) {
 *     // method body
 *     return value; // if returnType is not void
 * }
 */

public class Lesson9_Methods {

    // ============================================================
    // MAIN METHOD - Entry point of the program
    // ============================================================
    public static void main(String[] args) {

        System.out.println("=== CALLING METHODS ===\n");

        // Calling a method with no parameters and no return value
        sayHello();

        // Calling a method with parameters
        greet("Alice");
        greet("Bob");

        // Calling a method with return value
        int sum = add(5, 3);
        System.out.println("Sum: " + sum);

        // Using return value directly
        System.out.println("Product: " + multiply(4, 7));

        System.out.println();


        // ============================================================
        // METHODS WITH DIFFERENT RETURN TYPES
        // ============================================================

        int result1 = square(5);
        System.out.println("Square of 5: " + result1);

        double result2 = divide(10.0, 3.0);
        System.out.println("10.0 / 3.0 = " + result2);

        boolean result3 = isEven(8);
        System.out.println("Is 8 even? " + result3);

        String result4 = getGreeting("Charlie");
        System.out.println(result4);

        System.out.println();


        // ============================================================
        // METHODS WITH MULTIPLE PARAMETERS
        // ============================================================

        int largest = findMax(15, 23, 8);
        System.out.println("Largest of 15, 23, 8: " + largest);

        printPersonInfo("Diana", 25, "Engineer");

        System.out.println();


        // ============================================================
        // METHOD OVERLOADING
        // ============================================================
        // Multiple methods with the same name but different parameters

        System.out.println("Sum (2 params): " + add(5, 10));
        System.out.println("Sum (3 params): " + add(5, 10, 15));
        System.out.println("Sum (doubles): " + add(5.5, 3.2));

        System.out.println();


        // ============================================================
        // METHODS WITH ARRAYS
        // ============================================================

        int[] numbers = {10, 20, 30, 40, 50};
        printArray(numbers);

        int total = sumArray(numbers);
        System.out.println("Sum of array: " + total);

        double avg = average(numbers);
        System.out.println("Average: " + avg);

        System.out.println();


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        // Temperature converter
        double celsius = 25;
        double fahrenheit = celsiusToFahrenheit(celsius);
        System.out.println(celsius + "°C = " + fahrenheit + "°F");

        // Check if number is prime
        int num = 17;
        if (isPrime(num)) {
            System.out.println(num + " is prime");
        } else {
            System.out.println(num + " is not prime");
        }

        // Calculate factorial
        System.out.println("5! = " + factorial(5));

        // Validate age
        if (isValidAge(25)) {
            System.out.println("Age is valid");
        }

        System.out.println();


        // ============================================================
        // RECURSION - Methods calling themselves
        // ============================================================

        System.out.println("Factorial using recursion: " + factorialRecursive(5));
        System.out.println("Fibonacci(7): " + fibonacci(7));

    } // End of main method


    // ============================================================
    // METHOD DEFINITIONS
    // ============================================================

    // Method with no parameters and no return value (void)
    public static void sayHello() {
        System.out.println("Hello from the method!");
    }

    // Method with one parameter, no return value
    public static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    // Method with parameters and return value
    public static int add(int a, int b) {
        int sum = a + b;
        return sum; // Return the result
    }

    public static int multiply(int a, int b) {
        return a * b; // Can return expression directly
    }

    // Method returning different types
    public static int square(int num) {
        return num * num;
    }

    public static double divide(double a, double b) {
        return a / b;
    }

    public static boolean isEven(int number) {
        return (number % 2 == 0);
    }

    public static String getGreeting(String name) {
        return "Welcome, " + name + "!";
    }


    // ============================================================
    // METHODS WITH MULTIPLE PARAMETERS
    // ============================================================

    public static int findMax(int a, int b, int c) {
        int max = a;
        if (b > max) max = b;
        if (c > max) max = c;
        return max;
    }

    public static void printPersonInfo(String name, int age, String occupation) {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Occupation: " + occupation);
    }


    // ============================================================
    // METHOD OVERLOADING
    // ============================================================
    // Same method name, different parameter lists
    // Note: add(int a, int b) is already defined above

    // Add three integers (overloaded version)
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    // Add two doubles (overloaded version)
    public static double add(double a, double b) {
        return a + b;
    }


    // ============================================================
    // METHODS WITH ARRAYS
    // ============================================================

    public static void printArray(int[] arr) {
        System.out.print("Array: ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static int sumArray(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    public static double average(int[] arr) {
        if (arr.length == 0) return 0; // Avoid division by zero
        return (double) sumArray(arr) / arr.length;
    }


    // ============================================================
    // PRACTICAL UTILITY METHODS
    // ============================================================

    // Convert Celsius to Fahrenheit
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    // Check if a number is prime
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false; // Found a divisor, not prime
            }
        }
        return true; // No divisors found, is prime
    }

    // Calculate factorial iteratively
    public static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Validate age (between 0 and 120)
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120;
    }


    // ============================================================
    // RECURSION - Methods calling themselves
    // ============================================================

    // Factorial using recursion
    public static int factorialRecursive(int n) {
        // Base case: stop recursion
        if (n == 0 || n == 1) {
            return 1;
        }
        // Recursive case: method calls itself
        return n * factorialRecursive(n - 1);
        // Example: factorialRecursive(5)
        // = 5 * factorialRecursive(4)
        // = 5 * 4 * factorialRecursive(3)
        // = 5 * 4 * 3 * factorialRecursive(2)
        // = 5 * 4 * 3 * 2 * factorialRecursive(1)
        // = 5 * 4 * 3 * 2 * 1
        // = 120
    }

    // Fibonacci sequence using recursion
    public static int fibonacci(int n) {
        // Base cases
        if (n == 0) return 0;
        if (n == 1) return 1;
        // Recursive case
        return fibonacci(n - 1) + fibonacci(n - 2);
    }


    // ============================================================
    // KEY CONCEPTS EXPLAINED IN COMMENTS
    // ============================================================

    /*
     * ACCESS MODIFIERS:
     * - public: accessible from anywhere
     * - private: accessible only within the same class
     * - protected: accessible within the same package and subclasses
     * - default (no modifier): accessible within the same package
     *
     * STATIC KEYWORD:
     * - 'static' means the method belongs to the CLASS, not to objects
     * - You can call static methods without creating an object
     * - main() is static because it's called before any objects are created
     * - Static methods can only call other static methods directly
     *
     * RETURN TYPES:
     * - void: method doesn't return anything
     * - int, double, boolean, String, etc.: method returns that type
     * - Must use 'return' keyword to return a value
     *
     * PARAMETERS:
     * - Variables defined in the method signature
     * - Values passed when calling the method
     * - Can have 0 or more parameters
     * - Parameters are local to the method
     *
     * METHOD OVERLOADING:
     * - Multiple methods with same name but different parameter lists
     * - Different number of parameters OR
     * - Different types of parameters
     * - Return type alone is NOT enough to overload
     *
     * RECURSION:
     * - Method calling itself
     * - Must have a BASE CASE to stop (otherwise infinite loop!)
     * - Useful for problems that can be divided into similar sub-problems
     * - Examples: factorial, fibonacci, tree traversal
     *
     * BEST PRACTICES:
     * 1. Use descriptive method names (verbs): calculate(), get(), print()
     * 2. Keep methods short and focused (one task per method)
     * 3. Use parameters instead of global variables
     * 4. Add comments to explain complex logic
     * 5. Return early to avoid deep nesting
     * 6. Validate input parameters
     */

} // End of class
