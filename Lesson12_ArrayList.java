/*
 * LESSON 12: ARRAYLIST
 *
 * ArrayList is a resizable array from the Java Collections Framework.
 * Unlike regular arrays which have fixed size, ArrayList can grow or shrink dynamically.
 *
 * KEY DIFFERENCES FROM ARRAYS:
 * - Array: Fixed size, can hold primitives and objects
 * - ArrayList: Dynamic size, can only hold objects (not primitives)
 *
 * ArrayList advantages:
 * - Automatic resizing
 * - Many useful methods (add, remove, contains, etc.)
 * - Part of Collections Framework
 *
 * ArrayList limitations:
 * - Slightly slower than arrays
 * - Cannot store primitives directly (must use wrapper classes)
 * - Uses more memory
 */

// Import ArrayList from java.util package
import java.util.ArrayList;
import java.util.Collections; // For sorting and other operations

public class Lesson12_ArrayList {
    public static void main(String[] args) {

        // ============================================================
        // 1. CREATING ARRAYLIST
        // ============================================================

        // Create an ArrayList of Strings
        ArrayList<String> fruits = new ArrayList<>();
        // <String> is the type parameter (what the ArrayList holds)

        // Create ArrayList of Integers (wrapper class for int)
        ArrayList<Integer> numbers = new ArrayList<>();

        // Create with initial capacity (optimization)
        ArrayList<String> names = new ArrayList<>(100);

        // Create from another collection
        ArrayList<String> fruits2 = new ArrayList<>(fruits);

        System.out.println("=== ARRAYLIST BASICS ===\n");


        // ============================================================
        // 2. ADDING ELEMENTS
        // ============================================================

        // Add elements to the end
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");

        System.out.println("Fruits: " + fruits);

        // Add element at specific index
        fruits.add(1, "Apricot"); // Insert at index 1
        System.out.println("After insert at index 1: " + fruits);

        System.out.println();


        // ============================================================
        // 3. ACCESSING ELEMENTS
        // ============================================================

        // Get element at index
        String first = fruits.get(0);
        String third = fruits.get(2);
        System.out.println("First fruit: " + first);
        System.out.println("Third fruit: " + third);

        // Get size
        int size = fruits.size();
        System.out.println("Total fruits: " + size);

        // Get last element
        String last = fruits.get(fruits.size() - 1);
        System.out.println("Last fruit: " + last);

        System.out.println();


        // ============================================================
        // 4. MODIFYING ELEMENTS
        // ============================================================

        System.out.println("Before: " + fruits);

        // Replace element at index
        fruits.set(2, "Blueberry"); // Replace element at index 2
        System.out.println("After set(2, 'Blueberry'): " + fruits);

        System.out.println();


        // ============================================================
        // 5. REMOVING ELEMENTS
        // ============================================================

        // Remove by index
        fruits.remove(1); // Removes "Apricot"
        System.out.println("After remove(1): " + fruits);

        // Remove by object
        fruits.remove("Date"); // Removes "Date"
        System.out.println("After remove('Date'): " + fruits);

        // Remove all elements
        // fruits.clear();

        System.out.println();


        // ============================================================
        // 6. CHECKING CONTENTS
        // ============================================================

        // Check if contains element
        boolean hasApple = fruits.contains("Apple");
        System.out.println("Contains 'Apple': " + hasApple);

        boolean hasGrape = fruits.contains("Grape");
        System.out.println("Contains 'Grape': " + hasGrape);

        // Check if empty
        boolean empty = fruits.isEmpty();
        System.out.println("Is empty: " + empty);

        // Find index of element
        int index = fruits.indexOf("Cherry");
        System.out.println("Index of 'Cherry': " + index);

        // If not found, returns -1
        int notFound = fruits.indexOf("Grape");
        System.out.println("Index of 'Grape': " + notFound);

        System.out.println();


        // ============================================================
        // 7. LOOPING THROUGH ARRAYLIST
        // ============================================================

        System.out.println("=== LOOPING ===");

        // Method 1: Traditional for loop
        System.out.println("Using for loop:");
        for (int i = 0; i < fruits.size(); i++) {
            System.out.println(i + ": " + fruits.get(i));
        }

        System.out.println();

        // Method 2: Enhanced for loop (for-each)
        System.out.println("Using enhanced for loop:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }

        System.out.println();

        // Method 3: forEach with lambda (Java 8+)
        System.out.println("Using forEach with lambda:");
        fruits.forEach(fruit -> System.out.println("* " + fruit));

        System.out.println();


        // ============================================================
        // 8. WORKING WITH NUMBERS
        // ============================================================

        System.out.println("=== ARRAYLIST WITH NUMBERS ===");

        ArrayList<Integer> nums = new ArrayList<>();

        // Add numbers
        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);
        nums.add(50);

        System.out.println("Numbers: " + nums);

        // Calculate sum
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        System.out.println("Sum: " + sum);

        // Find maximum
        int max = nums.get(0);
        for (int num : nums) {
            if (num > max) {
                max = num;
            }
        }
        System.out.println("Maximum: " + max);

        // Or use Collections.max()
        System.out.println("Max (using Collections): " + Collections.max(nums));
        System.out.println("Min (using Collections): " + Collections.min(nums));

        System.out.println();


        // ============================================================
        // 9. SORTING
        // ============================================================

        System.out.println("=== SORTING ===");

        ArrayList<Integer> randomNumbers = new ArrayList<>();
        randomNumbers.add(45);
        randomNumbers.add(12);
        randomNumbers.add(78);
        randomNumbers.add(23);
        randomNumbers.add(56);

        System.out.println("Before sort: " + randomNumbers);

        // Sort in ascending order
        Collections.sort(randomNumbers);
        System.out.println("After sort: " + randomNumbers);

        // Sort in descending order
        Collections.sort(randomNumbers, Collections.reverseOrder());
        System.out.println("Descending: " + randomNumbers);

        // Sort strings
        ArrayList<String> names2 = new ArrayList<>();
        names2.add("Charlie");
        names2.add("Alice");
        names2.add("Bob");

        Collections.sort(names2);
        System.out.println("Sorted names: " + names2);

        System.out.println();


        // ============================================================
        // 10. CONVERTING BETWEEN ARRAY AND ARRAYLIST
        // ============================================================

        System.out.println("=== ARRAY <-> ARRAYLIST ===");

        // ArrayList to Array
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String[] array = list.toArray(new String[0]);
        System.out.print("Array from ArrayList: ");
        for (String s : array) {
            System.out.print(s + " ");
        }
        System.out.println();

        // Array to ArrayList
        String[] colors = {"Red", "Green", "Blue"};
        ArrayList<String> colorList = new ArrayList<>();
        for (String color : colors) {
            colorList.add(color);
        }
        System.out.println("ArrayList from array: " + colorList);

        // Or use Arrays.asList() (creates fixed-size list)
        // ArrayList<String> colorList2 = new ArrayList<>(Arrays.asList(colors));

        System.out.println();


        // ============================================================
        // 11. ARRAYLIST OF OBJECTS
        // ============================================================

        System.out.println("=== ARRAYLIST OF OBJECTS ===");

        ArrayList<Student> students = new ArrayList<>();

        students.add(new Student("Alice", 20, 3.8));
        students.add(new Student("Bob", 22, 3.5));
        students.add(new Student("Charlie", 21, 3.9));

        System.out.println("All students:");
        for (Student student : students) {
            student.displayInfo();
        }

        // Find student by name
        Student found = findStudentByName(students, "Bob");
        if (found != null) {
            System.out.println("\nFound student:");
            found.displayInfo();
        }

        // Remove student
        students.remove(1); // Remove Bob
        System.out.println("\nAfter removing Bob:");
        for (Student student : students) {
            System.out.println("- " + student.name);
        }

        System.out.println();


        // ============================================================
        // 12. COMMON OPERATIONS
        // ============================================================

        System.out.println("=== COMMON OPERATIONS ===");

        ArrayList<String> animals = new ArrayList<>();
        animals.add("Dog");
        animals.add("Cat");
        animals.add("Bird");
        animals.add("Fish");

        // Sublist (view of portion)
        ArrayList<String> subList = new ArrayList<>(animals.subList(1, 3));
        System.out.println("Sublist (1-3): " + subList);

        // Clone
        @SuppressWarnings("unchecked")
        ArrayList<String> animalsCopy = (ArrayList<String>) animals.clone();
        System.out.println("Clone: " + animalsCopy);

        // Check if two lists are equal
        boolean equal = animals.equals(animalsCopy);
        System.out.println("Are equal: " + equal);

        // Retain only certain elements
        ArrayList<String> toKeep = new ArrayList<>();
        toKeep.add("Dog");
        toKeep.add("Cat");
        animalsCopy.retainAll(toKeep);
        System.out.println("After retainAll: " + animalsCopy);

        System.out.println();


        // ============================================================
        // WRAPPER CLASSES
        // ============================================================

        /*
         * ArrayList cannot store primitives, so we use wrapper classes:
         *
         * Primitive  ->  Wrapper Class
         * ---------      -------------
         * int        ->  Integer
         * double     ->  Double
         * boolean    ->  Boolean
         * char       ->  Character
         * byte       ->  Byte
         * short      ->  Short
         * long       ->  Long
         * float      ->  Float
         *
         * Auto-boxing: Automatic conversion from primitive to wrapper
         * Auto-unboxing: Automatic conversion from wrapper to primitive
         *
         * Example:
         * ArrayList<Integer> nums = new ArrayList<>();
         * nums.add(5); // Auto-boxing: int -> Integer
         * int x = nums.get(0); // Auto-unboxing: Integer -> int
         */


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * COMMON ARRAYLIST METHODS:
         * - add(element): Add to end
         * - add(index, element): Insert at index
         * - get(index): Get element
         * - set(index, element): Replace element
         * - remove(index): Remove by index
         * - remove(object): Remove by value
         * - clear(): Remove all
         * - size(): Get number of elements
         * - isEmpty(): Check if empty
         * - contains(element): Check if contains
         * - indexOf(element): Find index
         *
         * COLLECTIONS UTILITY METHODS:
         * - Collections.sort(list): Sort
         * - Collections.reverse(list): Reverse
         * - Collections.shuffle(list): Shuffle randomly
         * - Collections.max(list): Find max
         * - Collections.min(list): Find min
         *
         * WHEN TO USE:
         * - Use ArrayList when size changes frequently
         * - Use Array when size is fixed and performance is critical
         * - ArrayList is easier to use and more flexible
         */
    }

    // Helper method to find student by name
    public static Student findStudentByName(ArrayList<Student> students, String name) {
        for (Student student : students) {
            if (student.name.equals(name)) {
                return student;
            }
        }
        return null; // Not found
    }
}


// ============================================================
// STUDENT CLASS - For ArrayList of objects example
// ============================================================
class Student {
    String name;
    int age;
    double gpa;

    Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age + ", GPA: " + gpa);
    }
}
