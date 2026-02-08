/*
 * LESSON 24: GENERICS
 *
 * Generics enable types (classes and interfaces) to be parameters when defining classes, interfaces, and methods.
 * They provide compile-time type safety and eliminate the need for casting.
 *
 * KEY CONCEPTS:
 * - Type Parameter: Placeholder for a type (T, E, K, V, etc.)
 * - Generic Class: Class with type parameters
 * - Generic Method: Method with type parameters
 * - Bounded Type Parameters: Restrict types (extends, super)
 * - Wildcards: ? for unknown type
 *
 * BENEFITS:
 * - Type safety at compile time
 * - No casting required
 * - Enable algorithms to work on different types
 * - Reusable code
 *
 * COMMON TYPE PARAMETERS:
 * - T: Type
 * - E: Element
 * - K: Key
 * - V: Value
 * - N: Number
 */

import java.util.ArrayList;
import java.util.List;

public class Lesson24_Generics {
    public static void main(String[] args) {

        System.out.println("=== GENERICS ===\n");

        // ============================================================
        // 1. WHY GENERICS?
        // ============================================================

        System.out.println("--- Before Generics ---");

        // Old way (without generics) - not type-safe
        ArrayList listWithout = new ArrayList();
        listWithout.add("Hello");
        listWithout.add(42);  // Can add any type!

        String str = (String) listWithout.get(0);  // Need to cast
        // String str2 = (String) listWithout.get(1);  // Runtime error!

        System.out.println();

        // New way (with generics) - type-safe
        ArrayList<String> listWith = new ArrayList<>();
        listWith.add("Hello");
        // listWith.add(42);  // Compile error! Type safety

        String str2 = listWith.get(0);  // No cast needed
        System.out.println("With generics: " + str2);

        System.out.println();


        // ============================================================
        // 2. GENERIC CLASS
        // ============================================================

        System.out.println("--- Generic Class ---");

        // Box for Integer
        Box<Integer> intBox = new Box<>(123);
        System.out.println("Integer box: " + intBox.getValue());

        // Box for String
        Box<String> strBox = new Box<>("Hello");
        System.out.println("String box: " + strBox.getValue());

        // Box for custom type
        Box<Person> personBox = new Box<>(new Person("Alice", 25));
        System.out.println("Person box: " + personBox.getValue());

        System.out.println();


        // ============================================================
        // 3. GENERIC CLASS WITH MULTIPLE TYPE PARAMETERS
        // ============================================================

        System.out.println("--- Multiple Type Parameters ---");

        Pair<String, Integer> pair1 = new Pair<>("Age", 25);
        System.out.println(pair1.getKey() + ": " + pair1.getValue());

        Pair<String, String> pair2 = new Pair<>("Name", "Bob");
        System.out.println(pair2.getKey() + ": " + pair2.getValue());

        System.out.println();


        // ============================================================
        // 4. GENERIC METHODS
        // ============================================================

        System.out.println("--- Generic Methods ---");

        // Print array of any type
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"A", "B", "C"};

        System.out.print("Integer array: ");
        printArray(intArray);

        System.out.print("String array: ");
        printArray(strArray);

        // Swap elements
        System.out.println("\nBefore swap: x=" + 10 + ", y=" + 20);
        Pair<Integer, Integer> swapped = swap(10, 20);
        System.out.println("After swap: x=" + swapped.getKey() + ", y=" + swapped.getValue());

        System.out.println();


        // ============================================================
        // 5. BOUNDED TYPE PARAMETERS
        // ============================================================

        System.out.println("--- Bounded Type Parameters ---");

        // Works with Number and its subclasses
        System.out.println("Max of 3 and 7: " + findMax(3, 7));
        System.out.println("Max of 3.5 and 2.1: " + findMax(3.5, 2.1));

        // Calculate sum
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        System.out.println("Sum: " + sumList(numbers));

        System.out.println();


        // ============================================================
        // 6. WILDCARDS
        // ============================================================

        System.out.println("--- Wildcards ---");

        List<Integer> intList = List.of(1, 2, 3);
        List<Double> doubleList = List.of(1.1, 2.2, 3.3);
        List<String> stringList = List.of("A", "B", "C");

        // ? (unbounded wildcard) accepts any type
        printList(intList);
        printList(doubleList);
        printList(stringList);

        // ? extends Number (upper bounded) - accepts Number and subclasses
        System.out.println("Sum of integers: " + sumNumbers(intList));
        System.out.println("Sum of doubles: " + sumNumbers(doubleList));

        System.out.println();


        // ============================================================
        // 7. PRACTICAL EXAMPLE: GENERIC STACK
        // ============================================================

        System.out.println("--- Generic Stack ---");

        Stack<String> stringStack = new Stack<>();
        stringStack.push("First");
        stringStack.push("Second");
        stringStack.push("Third");

        System.out.println("Pop: " + stringStack.pop());
        System.out.println("Peek: " + stringStack.peek());
        System.out.println("Size: " + stringStack.size());

        System.out.println();

        Stack<Integer> intStack = new Stack<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println("Pop: " + intStack.pop());

        System.out.println();


        // ============================================================
        // 8. PRACTICAL EXAMPLE: GENERIC PAIR
        // ============================================================

        System.out.println("--- Practical Pairs ---");

        Pair<String, Integer> ageMap = new Pair<>("Alice", 25);
        Pair<String, Double> priceMap = new Pair<>("Laptop", 1299.99);
        Pair<Integer, String> idMap = new Pair<>(101, "Employee");

        System.out.println(ageMap);
        System.out.println(priceMap);
        System.out.println(idMap);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * GENERIC CLASS SYNTAX:
         * class ClassName<T> {
         *     private T value;
         *     public T getValue() { return value; }
         * }
         *
         * GENERIC METHOD SYNTAX:
         * public <T> void methodName(T param) {
         *     // method body
         * }
         *
         * MULTIPLE TYPE PARAMETERS:
         * class ClassName<K, V> {
         *     private K key;
         *     private V value;
         * }
         *
         * BOUNDED TYPE PARAMETERS:
         * <T extends UpperBound>     // T must be UpperBound or subclass
         * <T super LowerBound>        // T must be LowerBound or superclass
         *
         * WILDCARDS:
         * <?>                  // Unbounded wildcard (any type)
         * <? extends Type>     // Upper bounded (Type or subclass)
         * <? super Type>       // Lower bounded (Type or superclass)
         *
         * COMMON CONVENTIONS:
         * T - Type
         * E - Element (used by collections)
         * K - Key (used by maps)
         * V - Value (used by maps)
         * N - Number
         *
         * BENEFITS:
         * - Stronger type checking at compile time
         * - Elimination of casts
         * - Enabling programmers to implement generic algorithms
         * - Code reusability
         *
         * TYPE ERASURE:
         * - Generics are compile-time feature only
         * - Type information is erased at runtime
         * - Cannot create generic arrays: new T[10] // Error
         * - Cannot use instanceof with generics
         *
         * RESTRICTIONS:
         * - Cannot instantiate generic type: new T()
         * - Cannot create generic array: new T[10]
         * - Cannot use primitives: Box<int> // Error (use Box<Integer>)
         * - Cannot create static generic fields
         * - Cannot use instanceof with parameterized types
         *
         * WHEN TO USE:
         * - Collections (ArrayList<T>, HashMap<K,V>)
         * - Utility classes that work with multiple types
         * - Type-safe containers
         * - Generic algorithms
         */
    }

    // Generic method to print array
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // Generic method to swap
    public static <T> Pair<T, T> swap(T first, T second) {
        return new Pair<>(second, first);
    }

    // Bounded type parameter - only works with Number and subclasses
    public static <T extends Number> double findMax(T num1, T num2) {
        return (num1.doubleValue() > num2.doubleValue()) ? num1.doubleValue() : num2.doubleValue();
    }

    // Sum list of numbers
    public static double sumList(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // Wildcard - accepts list of any type
    public static void printList(List<?> list) {
        System.out.print("List: ");
        for (Object obj : list) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    // Upper bounded wildcard
    public static double sumNumbers(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }
}


// ============================================================
// GENERIC CLASS
// ============================================================

class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box{" + value + "}";
    }
}


// ============================================================
// GENERIC CLASS WITH TWO TYPE PARAMETERS
// ============================================================

class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pair{" + key + "=" + value + "}";
    }
}


// ============================================================
// GENERIC STACK IMPLEMENTATION
// ============================================================

class Stack<T> {
    private ArrayList<T> items;

    public Stack() {
        items = new ArrayList<>();
    }

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return items.remove(items.size() - 1);
    }

    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}


// ============================================================
// HELPER CLASS
// ============================================================

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
