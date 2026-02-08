/*
 * LESSON 22: OBJECT METHODS & ACCESS MODIFIERS
 *
 * Every class in Java inherits from the Object class.
 * Object class provides several important methods that can be overridden.
 * Access modifiers control visibility and access to classes, methods, and fields.
 *
 * KEY OBJECT METHODS:
 * - toString(): String representation of object
 * - equals(): Compare two objects for equality
 * - hashCode(): Generate hash code for object (used in HashMaps, HashSets)
 * - getClass(): Get runtime class of object
 * - clone(): Create a copy of object
 *
 * ACCESS MODIFIERS:
 * - public: Accessible from anywhere
 * - private: Accessible only within the same class
 * - protected: Accessible in same package and subclasses
 * - default (no modifier): Accessible only in same package
 *
 * STATIC vs INSTANCE:
 * - Static: Belongs to class, shared by all instances
 * - Instance: Belongs to object, each instance has its own copy
 */

public class Lesson22_ObjectMethods_AccessModifiers {
    public static void main(String[] args) {

        System.out.println("=== OBJECT METHODS & ACCESS MODIFIERS ===\n");

        // ============================================================
        // 1. toString() METHOD
        // ============================================================

        System.out.println("--- toString() Method ---");

        // Without toString() override
        PersonWithout p1 = new PersonWithout("Alice", 25);
        System.out.println("Without toString(): " + p1);  // Ugly output

        // With toString() override
        PersonWith p2 = new PersonWith("Bob", 30);
        System.out.println("With toString(): " + p2);  // Nice output

        System.out.println();


        // ============================================================
        // 2. equals() METHOD
        // ============================================================

        System.out.println("--- equals() Method ---");

        // Without equals() override
        Student s1 = new Student("Alice", 101);
        Student s2 = new Student("Alice", 101);

        System.out.println("s1 == s2: " + (s1 == s2));  // false (different objects)
        System.out.println("s1.equals(s2): " + s1.equals(s2));  // true (same content)

        // String comparison (String overrides equals())
        String str1 = new String("Hello");
        String str2 = new String("Hello");
        System.out.println("str1 == str2: " + (str1 == str2));  // false
        System.out.println("str1.equals(str2): " + str1.equals(str2));  // true

        System.out.println();


        // ============================================================
        // 3. hashCode() METHOD
        // ============================================================

        System.out.println("--- hashCode() Method ---");

        System.out.println("s1 hashCode: " + s1.hashCode());
        System.out.println("s2 hashCode: " + s2.hashCode());

        // Equal objects must have same hashCode
        if (s1.equals(s2)) {
            System.out.println("Equal objects have same hashCode: " +
                    (s1.hashCode() == s2.hashCode()));
        }

        System.out.println();


        // ============================================================
        // 4. getClass() METHOD
        // ============================================================

        System.out.println("--- getClass() Method ---");

        Student student = new Student("John", 102);
        System.out.println("Class: " + student.getClass());
        System.out.println("Simple name: " + student.getClass().getSimpleName());

        System.out.println();


        // ============================================================
        // 5. ACCESS MODIFIERS
        // ============================================================

        System.out.println("--- Access Modifiers ---");

        AccessModifierDemo demo = new AccessModifierDemo();

        // demo.privateField = 10;    // Error! Cannot access private
        // demo.defaultField = 20;    // Error if in different package
        // demo.protectedField = 30;  // Error if not subclass and different package
        demo.publicField = 40;        // OK - public is accessible

        demo.publicMethod();          // OK
        // demo.privateMethod();      // Error! Cannot access

        System.out.println();


        // ============================================================
        // 6. STATIC vs INSTANCE MEMBERS
        // ============================================================

        System.out.println("--- Static vs Instance ---");

        // Static variable - shared by all instances
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();

        System.out.println("Total counters created: " + Counter.count);  // 3

        // Instance variables - each object has its own
        c1.setId(100);
        c2.setId(200);

        System.out.println("c1 id: " + c1.getId());  // 100
        System.out.println("c2 id: " + c2.getId());  // 200

        // Static method - call without object
        System.out.println("Max: " + MathUtils.max(10, 5));

        System.out.println();


        // ============================================================
        // 7. PRACTICAL EXAMPLE: BOOK CLASS
        // ============================================================

        System.out.println("--- Book Class Example ---");

        Book book1 = new Book("1984", "George Orwell", 1949);
        Book book2 = new Book("1984", "George Orwell", 1949);
        Book book3 = new Book("Animal Farm", "George Orwell", 1945);

        // toString()
        System.out.println(book1);

        // equals()
        System.out.println("book1 equals book2: " + book1.equals(book2));  // true
        System.out.println("book1 equals book3: " + book1.equals(book3));  // false

        // hashCode()
        System.out.println("book1 hashCode: " + book1.hashCode());
        System.out.println("book2 hashCode: " + book2.hashCode());  // Same as book1

        System.out.println();


        // ============================================================
        // 8. USING IN HASHMAP
        // ============================================================

        System.out.println("--- Using in HashMap ---");

        java.util.HashMap<Book, String> bookLocations = new java.util.HashMap<>();

        bookLocations.put(book1, "Shelf A");
        bookLocations.put(book3, "Shelf B");

        // Can find book1 even with new object (because equals() and hashCode() work)
        Book searchBook = new Book("1984", "George Orwell", 1949);
        System.out.println("Location of " + searchBook.getTitle() + ": " +
                bookLocations.get(searchBook));

        System.out.println();


        // ============================================================
        // 9. ENCAPSULATION EXAMPLE
        // ============================================================

        System.out.println("--- Encapsulation ---");

        BankAccount account = new BankAccount("ACC123", 1000);

        // Cannot access private fields directly
        // account.balance = 5000;  // Error!

        // Must use public methods
        System.out.println("Balance: $" + account.getBalance());
        account.deposit(500);
        account.withdraw(200);
        System.out.println("Final balance: $" + account.getBalance());

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * OBJECT METHODS TO OVERRIDE:
         *
         * 1. toString():
         *    - Returns string representation
         *    - Useful for debugging
         *    - Format: ClassName{field1=value1, field2=value2}
         *
         * 2. equals(Object obj):
         *    - Compares object content, not reference
         *    - Rules: reflexive, symmetric, transitive, consistent
         *    - Check: null, same class, field comparison
         *
         * 3. hashCode():
         *    - Returns integer hash code
         *    - Must override if you override equals()
         *    - Equal objects must have equal hash codes
         *    - Used by HashMap, HashSet, Hashtable
         *
         * ACCESS MODIFIERS:
         * ┌──────────────┬─────────┬─────────┬────────────┬───────────┐
         * │ Modifier     │ Class   │ Package │ Subclass   │ World     │
         * ├──────────────┼─────────┼─────────┼────────────┼───────────┤
         * │ public       │ Yes     │ Yes     │ Yes        │ Yes       │
         * │ protected    │ Yes     │ Yes     │ Yes        │ No        │
         * │ default      │ Yes     │ Yes     │ No         │ No        │
         * │ private      │ Yes     │ No      │ No         │ No        │
         * └──────────────┴─────────┴─────────┴────────────┴───────────┘
         *
         * STATIC vs INSTANCE:
         * Static:
         * - Belongs to class
         * - Shared by all instances
         * - Access: ClassName.member
         * - Cannot access instance members
         *
         * Instance:
         * - Belongs to object
         * - Each object has own copy
         * - Access: object.member
         * - Can access static members
         *
         * BEST PRACTICES:
         * - Make fields private (encapsulation)
         * - Provide public getters/setters
         * - Override toString() for debugging
         * - Override equals() and hashCode() together
         * - Use static for utility methods
         * - Use instance for object state
         */
    }
}


// ============================================================
// toString() EXAMPLES
// ============================================================

class PersonWithout {
    String name;
    int age;

    PersonWithout(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // No toString() override - uses Object's toString()
}

class PersonWith {
    String name;
    int age;

    PersonWith(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}


// ============================================================
// equals() and hashCode() EXAMPLE
// ============================================================

class Student {
    private String name;
    private int studentId;

    public Student(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if same object
        if (this == obj) return true;

        // Check if null or different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast and compare fields
        Student other = (Student) obj;
        return studentId == other.studentId &&
                name.equals(other.name);
    }

    @Override
    public int hashCode() {
        // Combine hash codes of fields
        int result = name.hashCode();
        result = 31 * result + studentId;
        return result;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', id=" + studentId + "}";
    }
}


// ============================================================
// ACCESS MODIFIERS DEMO
// ============================================================

class AccessModifierDemo {
    private int privateField = 10;        // Only accessible in this class
    int defaultField = 20;                // Accessible in same package
    protected int protectedField = 30;    // Accessible in subclass + same package
    public int publicField = 40;          // Accessible everywhere

    private void privateMethod() {
        System.out.println("Private method");
    }

    void defaultMethod() {
        System.out.println("Default method");
    }

    protected void protectedMethod() {
        System.out.println("Protected method");
    }

    public void publicMethod() {
        System.out.println("Public method");
        // Can access all members within same class
        System.out.println(privateField);
        privateMethod();
    }
}


// ============================================================
// STATIC vs INSTANCE EXAMPLE
// ============================================================

class Counter {
    static int count = 0;  // Static - shared by all instances
    private int id;        // Instance - each object has its own

    Counter() {
        count++;  // Increment shared counter
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static int getCount() {
        return count;
        // Cannot access 'id' here - static method can't access instance variables
    }
}


class MathUtils {
    // Static utility method - doesn't need object state
    public static int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public static int min(int a, int b) {
        return (a < b) ? a : b;
    }
}


// ============================================================
// BOOK CLASS - COMPLETE EXAMPLE
// ============================================================

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', year=" + year + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book book = (Book) obj;
        return year == book.year &&
                title.equals(book.title) &&
                author.equals(book.author);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + year;
        return result;
    }
}


// ============================================================
// ENCAPSULATION EXAMPLE
// ============================================================

class BankAccount {
    private String accountNumber;  // Private - cannot access directly
    private double balance;        // Private - cannot modify directly

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Public getter - controlled read access
    public double getBalance() {
        return balance;
    }

    // Public methods - controlled modification
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Insufficient funds");
        }
    }
}
