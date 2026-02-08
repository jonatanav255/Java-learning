/*
 * LESSON 8: ARRAYS
 *
 * An array is a container that holds a FIXED number of values of a SINGLE type.
 * Arrays are objects in Java and have a fixed size once created.
 *
 * Key concepts:
 * - Arrays store multiple values of the same type
 * - Array size is fixed after creation
 * - Array indices start at 0
 * - Array elements are accessed using index: array[index]
 */

public class Lesson8_Arrays {
    public static void main(String[] args) {

        // ============================================================
        // 1. DECLARING AND CREATING ARRAYS
        // ============================================================

        // Method 1: Declare, then create
        int[] numbers; // Declare an array of integers
        numbers = new int[5]; // Create array with 5 elements

        // Method 2: Declare and create in one line
        int[] scores = new int[10]; // Array of 10 integers

        // Method 3: Declare, create, and initialize with values
        int[] ages = {18, 25, 30, 22, 35}; // Array with 5 elements

        // Method 4: Using new keyword with initialization
        String[] names = new String[]{"Alice", "Bob", "Charlie"};

        // Note: These are equivalent ways to declare arrays
        int[] arr1; // Preferred style (brackets after type)
        int arr2[]; // Also valid (C-style, but less common in Java)

        System.out.println("=== ARRAY BASICS ===");


        // ============================================================
        // 2. ACCESSING ARRAY ELEMENTS
        // ============================================================
        // Array indices start at 0 and go to (length - 1)

        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Elderberry"};

        // Access elements using index
        System.out.println("First fruit: " + fruits[0]);  // Apple
        System.out.println("Third fruit: " + fruits[2]);  // Cherry
        System.out.println("Last fruit: " + fruits[4]);   // Elderberry

        // Array length property
        System.out.println("Total fruits: " + fruits.length); // 5

        // Access last element using length
        System.out.println("Last fruit (using length): " + fruits[fruits.length - 1]);

        System.out.println();


        // ============================================================
        // 3. MODIFYING ARRAY ELEMENTS
        // ============================================================

        int[] values = {10, 20, 30, 40, 50};
        System.out.println("Original array:");
        System.out.println("values[1] = " + values[1]); // 20

        // Change element at index 1
        values[1] = 99;
        System.out.println("After modification:");
        System.out.println("values[1] = " + values[1]); // 99

        System.out.println();


        // ============================================================
        // 4. LOOPING THROUGH ARRAYS
        // ============================================================

        int[] numbers2 = {5, 10, 15, 20, 25};

        // Method 1: Traditional for loop
        System.out.println("Using traditional for loop:");
        for (int i = 0; i < numbers2.length; i++) {
            System.out.println("numbers2[" + i + "] = " + numbers2[i]);
        }

        System.out.println();

        // Method 2: Enhanced for loop (for-each)
        System.out.println("Using enhanced for loop:");
        for (int num : numbers2) {
            System.out.println("Value: " + num);
        }

        System.out.println();


        // ============================================================
        // 5. DEFAULT VALUES
        // ============================================================
        // When you create an array without initializing, elements have default values

        int[] integers = new int[3];      // Default: 0
        double[] doubles = new double[3]; // Default: 0.0
        boolean[] bools = new boolean[3]; // Default: false
        String[] strings = new String[3]; // Default: null

        System.out.println("Default values:");
        System.out.println("int: " + integers[0]);    // 0
        System.out.println("double: " + doubles[0]);  // 0.0
        System.out.println("boolean: " + bools[0]);   // false
        System.out.println("String: " + strings[0]);  // null

        System.out.println();


        // ============================================================
        // 6. MULTI-DIMENSIONAL ARRAYS
        // ============================================================
        // Arrays of arrays (2D, 3D, etc.)

        // 2D array (like a table/matrix)
        int[][] matrix = {
            {1, 2, 3},    // Row 0
            {4, 5, 6},    // Row 1
            {7, 8, 9}     // Row 2
        };

        // Access elements: array[row][column]
        System.out.println("2D Array:");
        System.out.println("Element at [0][0]: " + matrix[0][0]); // 1
        System.out.println("Element at [1][2]: " + matrix[1][2]); // 6
        System.out.println("Element at [2][1]: " + matrix[2][1]); // 8

        System.out.println();

        // Loop through 2D array
        System.out.println("Full 2D array:");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }

        System.out.println();

        // Enhanced for loop with 2D array
        System.out.println("Using enhanced for loop:");
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

        System.out.println();


        // ============================================================
        // 7. COMMON ARRAY OPERATIONS
        // ============================================================

        int[] data = {45, 23, 67, 12, 89, 34};

        // Find maximum
        int max = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        System.out.println("Maximum value: " + max);

        // Find minimum
        int min = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        System.out.println("Minimum value: " + min);

        // Calculate sum
        int sum = 0;
        for (int num : data) {
            sum += num;
        }
        System.out.println("Sum: " + sum);

        // Calculate average
        double average = (double) sum / data.length;
        System.out.println("Average: " + average);

        System.out.println();


        // ============================================================
        // 8. SEARCHING IN ARRAYS
        // ============================================================

        int[] nums = {10, 25, 30, 45, 50};
        int target = 30;

        // Linear search
        boolean found = false;
        int position = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                found = true;
                position = i;
                break;
            }
        }

        if (found) {
            System.out.println(target + " found at index " + position);
        } else {
            System.out.println(target + " not found");
        }

        System.out.println();


        // ============================================================
        // 9. COPYING ARRAYS
        // ============================================================

        int[] original = {1, 2, 3, 4, 5};

        // Method 1: Manual copy using loop
        int[] copy1 = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            copy1[i] = original[i];
        }

        // Method 2: Using System.arraycopy()
        int[] copy2 = new int[original.length];
        System.arraycopy(original, 0, copy2, 0, original.length);
        // Parameters: (source, srcPos, dest, destPos, length)

        // Method 3: Using Arrays.copyOf() (need to import java.util.Arrays)
        // We'll cover this in future lessons

        System.out.println("Original array:");
        for (int num : original) {
            System.out.print(num + " ");
        }
        System.out.println();

        System.out.println("Copied array:");
        for (int num : copy1) {
            System.out.print(num + " ");
        }
        System.out.println("\n");


        // ============================================================
        // 10. ARRAY LIMITATIONS AND COMMON MISTAKES
        // ============================================================

        int[] arr = {1, 2, 3};

        // Common mistake 1: ArrayIndexOutOfBoundsException
        // This would cause an error:
        // System.out.println(arr[5]); // Error! Index 5 doesn't exist

        // Common mistake 2: Cannot change array size after creation
        // Arrays have fixed size. If you need dynamic size, use ArrayList (later lesson)

        // Common mistake 3: Comparing arrays with ==
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};
        System.out.println("a == b: " + (a == b)); // false! Compares references, not contents
        // Use Arrays.equals() to compare contents (we'll learn this later)


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        System.out.println("=== PRACTICAL EXAMPLES ===");

        // Example 1: Reverse an array
        int[] toReverse = {1, 2, 3, 4, 5};
        System.out.print("Original: ");
        for (int n : toReverse) System.out.print(n + " ");
        System.out.println();

        for (int i = 0; i < toReverse.length / 2; i++) {
            int temp = toReverse[i];
            toReverse[i] = toReverse[toReverse.length - 1 - i];
            toReverse[toReverse.length - 1 - i] = temp;
        }

        System.out.print("Reversed: ");
        for (int n : toReverse) System.out.print(n + " ");
        System.out.println("\n");


        // Example 2: Count occurrences
        int[] items = {5, 3, 5, 7, 5, 2, 5};
        int searchValue = 5;
        int count = 0;
        for (int item : items) {
            if (item == searchValue) {
                count++;
            }
        }
        System.out.println(searchValue + " appears " + count + " times\n");


        // Example 3: Student grades system
        String[] students = {"Alice", "Bob", "Charlie", "Diana"};
        int[] grades = {85, 92, 78, 95};

        System.out.println("Student Grade Report:");
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i] + ": " + grades[i]);
        }


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * 1. Arrays store multiple values of the SAME type
         * 2. Array size is FIXED after creation
         * 3. Indices start at 0 and go to (length - 1)
         * 4. Use array.length to get the size
         * 5. Use for loops or enhanced for loops to iterate
         * 6. Default values: 0 for numbers, false for boolean, null for objects
         * 7. Multi-dimensional arrays are arrays of arrays
         * 8. Watch out for ArrayIndexOutOfBoundsException
         * 9. == compares references, not contents
         * 10. For dynamic size, use ArrayList (next lesson!)
         */
    }
}
