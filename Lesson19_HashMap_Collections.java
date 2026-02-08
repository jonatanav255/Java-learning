/*
 * LESSON 19: HASHMAP AND COLLECTIONS
 *
 * HashMap is part of the Java Collections Framework.
 * It stores data in key-value pairs.
 *
 * KEY CONCEPTS:
 * - HashMap: Stores key-value pairs
 * - Key: Unique identifier (no duplicates)
 * - Value: Data associated with the key (can have duplicates)
 * - HashSet: Stores unique elements (no duplicates)
 * - LinkedList: Doubly-linked list implementation
 * - TreeMap: Sorted map
 * - TreeSet: Sorted set
 *
 * HASHMAP vs ARRAYLIST:
 * HashMap:
 * - Key-value pairs
 * - Fast lookup by key O(1)
 * - No order guarantee
 * - Keys must be unique
 *
 * ArrayList:
 * - Index-value pairs
 * - Fast access by index
 * - Maintains insertion order
 * - Can have duplicates
 *
 * WHEN TO USE HASHMAP:
 * - Need to associate keys with values (like a dictionary)
 * - Need fast lookup by key
 * - Keys are unique identifiers
 * - Order doesn't matter
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

public class Lesson19_HashMap_Collections {
    public static void main(String[] args) {

        System.out.println("=== HASHMAP AND COLLECTIONS ===\n");

        // ============================================================
        // 1. HASHMAP BASICS
        // ============================================================

        System.out.println("--- HashMap Basics ---");

        // Create HashMap
        HashMap<String, Integer> ages = new HashMap<>();

        // Add key-value pairs
        ages.put("Alice", 25);
        ages.put("Bob", 30);
        ages.put("Charlie", 28);
        ages.put("Diana", 22);

        System.out.println("HashMap: " + ages);
        System.out.println();

        // Get value by key
        int aliceAge = ages.get("Alice");
        System.out.println("Alice's age: " + aliceAge);

        // Check if key exists
        if (ages.containsKey("Bob")) {
            System.out.println("Bob is in the map");
        }

        // Check if value exists
        if (ages.containsValue(30)) {
            System.out.println("Someone is 30 years old");
        }

        // Size
        System.out.println("Size: " + ages.size());

        System.out.println();


        // ============================================================
        // 2. ITERATING THROUGH HASHMAP
        // ============================================================

        System.out.println("--- Iterating HashMap ---");

        // Method 1: Using keySet()
        System.out.println("Using keySet():");
        for (String name : ages.keySet()) {
            System.out.println(name + " is " + ages.get(name) + " years old");
        }

        System.out.println();

        // Method 2: Using entrySet()
        System.out.println("Using entrySet():");
        for (Map.Entry<String, Integer> entry : ages.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();

        // Method 3: Using forEach (Java 8+)
        System.out.println("Using forEach:");
        ages.forEach((name, age) -> System.out.println(name + " -> " + age));

        System.out.println();


        // ============================================================
        // 3. UPDATING AND REMOVING
        // ============================================================

        System.out.println("--- Update and Remove ---");

        // Update value
        ages.put("Alice", 26);  // Overwrites old value
        System.out.println("Updated Alice: " + ages.get("Alice"));

        // Remove entry
        ages.remove("Bob");
        System.out.println("After removing Bob: " + ages);

        // Clear all
        // ages.clear();

        System.out.println();


        // ============================================================
        // 4. PRACTICAL EXAMPLE: STUDENT GRADES
        // ============================================================

        System.out.println("--- Student Grades ---");

        HashMap<String, Double> grades = new HashMap<>();
        grades.put("Math", 95.5);
        grades.put("Science", 88.0);
        grades.put("English", 92.5);
        grades.put("History", 85.0);

        // Calculate average
        double total = 0;
        for (double grade : grades.values()) {
            total += grade;
        }
        double average = total / grades.size();
        System.out.println("Average grade: " + average);

        // Find highest grade
        double highest = 0;
        String bestSubject = "";
        for (Map.Entry<String, Double> entry : grades.entrySet()) {
            if (entry.getValue() > highest) {
                highest = entry.getValue();
                bestSubject = entry.getKey();
            }
        }
        System.out.println("Best subject: " + bestSubject + " (" + highest + ")");

        System.out.println();


        // ============================================================
        // 5. HASHSET - UNIQUE ELEMENTS
        // ============================================================

        System.out.println("--- HashSet ---");

        HashSet<String> fruits = new HashSet<>();

        // Add elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // Duplicate - won't be added

        System.out.println("HashSet: " + fruits);
        System.out.println("Size: " + fruits.size());  // 3, not 4

        // Check contains
        System.out.println("Contains Apple: " + fruits.contains("Apple"));

        // Remove
        fruits.remove("Banana");
        System.out.println("After removing Banana: " + fruits);

        // Iterate
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }

        System.out.println();


        // ============================================================
        // 6. LINKEDLIST
        // ============================================================

        System.out.println("--- LinkedList ---");

        LinkedList<String> names = new LinkedList<>();

        // Add elements
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");

        // Add at beginning
        names.addFirst("Aaron");

        // Add at end
        names.addLast("Zoe");

        System.out.println("LinkedList: " + names);

        // Get first and last
        System.out.println("First: " + names.getFirst());
        System.out.println("Last: " + names.getLast());

        // Remove first and last
        names.removeFirst();
        names.removeLast();
        System.out.println("After removing first and last: " + names);

        System.out.println();


        // ============================================================
        // 7. TREEMAP - SORTED MAP
        // ============================================================

        System.out.println("--- TreeMap (Sorted) ---");

        TreeMap<String, Integer> sortedAges = new TreeMap<>();
        sortedAges.put("Charlie", 28);
        sortedAges.put("Alice", 25);
        sortedAges.put("Bob", 30);
        sortedAges.put("Diana", 22);

        // TreeMap maintains sorted order by keys
        System.out.println("TreeMap (sorted by key): " + sortedAges);

        for (String name : sortedAges.keySet()) {
            System.out.println(name + ": " + sortedAges.get(name));
        }

        System.out.println();


        // ============================================================
        // 8. TREESET - SORTED SET
        // ============================================================

        System.out.println("--- TreeSet (Sorted) ---");

        TreeSet<Integer> numbers = new TreeSet<>();
        numbers.add(45);
        numbers.add(12);
        numbers.add(78);
        numbers.add(23);
        numbers.add(56);

        // TreeSet maintains sorted order
        System.out.println("TreeSet (sorted): " + numbers);

        System.out.println("First: " + numbers.first());
        System.out.println("Last: " + numbers.last());

        System.out.println();


        // ============================================================
        // 9. PRACTICAL: WORD FREQUENCY COUNTER
        // ============================================================

        System.out.println("--- Word Frequency Counter ---");

        String text = "apple banana apple cherry banana apple";
        HashMap<String, Integer> wordCount = new HashMap<>();

        String[] words = text.split(" ");
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();


        // ============================================================
        // 10. PRACTICAL: PHONE BOOK
        // ============================================================

        System.out.println("--- Phone Book ---");

        HashMap<String, String> phoneBook = new HashMap<>();
        phoneBook.put("Alice", "555-1234");
        phoneBook.put("Bob", "555-5678");
        phoneBook.put("Charlie", "555-9012");

        // Lookup
        String name = "Alice";
        if (phoneBook.containsKey(name)) {
            System.out.println(name + "'s number: " + phoneBook.get(name));
        }

        // Add new contact
        phoneBook.put("Diana", "555-3456");

        // Display all contacts
        System.out.println("\nAll contacts:");
        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println();


        // ============================================================
        // 11. NESTED HASHMAP
        // ============================================================

        System.out.println("--- Nested HashMap ---");

        // HashMap of HashMaps (student -> subject -> grade)
        HashMap<String, HashMap<String, Double>> studentGrades = new HashMap<>();

        HashMap<String, Double> aliceGrades = new HashMap<>();
        aliceGrades.put("Math", 95.0);
        aliceGrades.put("Science", 88.0);

        HashMap<String, Double> bobGrades = new HashMap<>();
        bobGrades.put("Math", 85.0);
        bobGrades.put("Science", 92.0);

        studentGrades.put("Alice", aliceGrades);
        studentGrades.put("Bob", bobGrades);

        // Access nested data
        System.out.println("Alice's Math grade: " +
                studentGrades.get("Alice").get("Math"));

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * HASHMAP METHODS:
         * - put(key, value): Add/update entry
         * - get(key): Get value by key
         * - remove(key): Remove entry
         * - containsKey(key): Check if key exists
         * - containsValue(value): Check if value exists
         * - keySet(): Get all keys
         * - values(): Get all values
         * - entrySet(): Get all key-value pairs
         * - size(): Number of entries
         * - clear(): Remove all entries
         * - isEmpty(): Check if empty
         * - getOrDefault(key, defaultValue): Get value or default
         *
         * HASHSET METHODS:
         * - add(element): Add element
         * - remove(element): Remove element
         * - contains(element): Check if exists
         * - size(): Number of elements
         * - clear(): Remove all
         *
         * COLLECTION COMPARISON:
         * ArrayList: Ordered, allows duplicates, fast index access
         * LinkedList: Ordered, allows duplicates, fast insertion/deletion
         * HashSet: Unordered, no duplicates, fast contains check
         * TreeSet: Sorted, no duplicates
         * HashMap: Key-value pairs, fast lookup by key
         * TreeMap: Sorted key-value pairs
         *
         * WHEN TO USE:
         * - HashMap: Dictionary, cache, configuration
         * - HashSet: Remove duplicates, membership testing
         * - LinkedList: Queue, frequent insertions/deletions
         * - TreeMap/TreeSet: Need sorted data
         */
    }
}
