/*
 * LESSON 28: REFLECTION & ANNOTATIONS
 *
 * REFLECTION:
 * Reflection allows you to inspect and manipulate classes, methods, and fields at runtime.
 * You can examine or modify the runtime behavior of applications.
 *
 * KEY REFLECTION CAPABILITIES:
 * - Get class information at runtime
 * - Create objects dynamically
 * - Invoke methods dynamically
 * - Access private fields and methods
 * - Inspect annotations
 *
 * ANNOTATIONS:
 * Annotations provide metadata about code.
 * They don't directly affect program logic but provide information to:
 * - Compiler
 * - Development tools
 * - Runtime (via reflection)
 *
 * BUILT-IN ANNOTATIONS:
 * - @Override
 * - @Deprecated
 * - @SuppressWarnings
 * - @FunctionalInterface
 *
 * CUSTOM ANNOTATIONS:
 * - @Retention: How long annotation is retained
 * - @Target: Where annotation can be used
 * - @Documented: Include in Javadoc
 * - @Inherited: Inherited by subclasses
 *
 * USE CASES:
 * - Frameworks (Spring, Hibernate)
 * - Testing (JUnit)
 * - Serialization
 * - Dependency injection
 * - ORM mapping
 * - Validation
 */

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

public class Lesson28_Reflection_Annotations {
    public static void main(String[] args) throws Exception {

        System.out.println("=== REFLECTION & ANNOTATIONS ===\n");

        // ============================================================
        // 1. GETTING CLASS INFORMATION
        // ============================================================

        System.out.println("--- Class Information ---");

        Person person = new Person("Alice", 25);
        Class<?> personClass = person.getClass();

        System.out.println("Class name: " + personClass.getName());
        System.out.println("Simple name: " + personClass.getSimpleName());
        System.out.println("Package: " + personClass.getPackage());

        System.out.println();


        // ============================================================
        // 2. INSPECTING FIELDS
        // ============================================================

        System.out.println("--- Fields ---");

        Field[] fields = personClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field: " + field.getName() +
                    ", Type: " + field.getType() +
                    ", Modifiers: " + Modifier.toString(field.getModifiers()));
        }

        System.out.println();


        // ============================================================
        // 3. INSPECTING METHODS
        // ============================================================

        System.out.println("--- Methods ---");

        Method[] methods = personClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName() +
                    ", Return type: " + method.getReturnType().getSimpleName());
        }

        System.out.println();


        // ============================================================
        // 4. ACCESSING PRIVATE FIELDS
        // ============================================================

        System.out.println("--- Accessing Private Fields ---");

        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true);  // Bypass private access

        String name = (String) nameField.get(person);
        System.out.println("Private field 'name': " + name);

        // Modify private field
        nameField.set(person, "Bob");
        System.out.println("Modified name: " + person.getName());

        System.out.println();


        // ============================================================
        // 5. INVOKING METHODS DYNAMICALLY
        // ============================================================

        System.out.println("--- Dynamic Method Invocation ---");

        Method greetMethod = personClass.getMethod("greet");
        greetMethod.invoke(person);  // Calls person.greet()

        Method setAgeMethod = personClass.getMethod("setAge", int.class);
        setAgeMethod.invoke(person, 30);  // Calls person.setAge(30)

        System.out.println("Age after dynamic call: " + person.getAge());

        System.out.println();


        // ============================================================
        // 6. CREATING OBJECTS DYNAMICALLY
        // ============================================================

        System.out.println("--- Dynamic Object Creation ---");

        // Get constructor
        Constructor<?> constructor = personClass.getConstructor(String.class, int.class);

        // Create new instance
        Person newPerson = (Person) constructor.newInstance("Charlie", 35);
        System.out.println("Dynamically created: " + newPerson.getName());

        System.out.println();


        // ============================================================
        // 7. BUILT-IN ANNOTATIONS
        // ============================================================

        System.out.println("--- Built-in Annotations ---");

        Calculator calc = new Calculator();
        calc.add(5, 3);
        calc.oldMethod();  // Deprecated method

        System.out.println();


        // ============================================================
        // 8. CUSTOM ANNOTATIONS - DEFINING
        // ============================================================

        System.out.println("--- Custom Annotations ---");

        // See @Author and @Version annotations defined below
        // Used on Book class


        // ============================================================
        // 9. READING ANNOTATIONS VIA REFLECTION
        // ============================================================

        System.out.println("--- Reading Annotations ---");

        Class<?> bookClass = Book.class;

        // Check if annotation present
        if (bookClass.isAnnotationPresent(Author.class)) {
            Author author = bookClass.getAnnotation(Author.class);
            System.out.println("Author: " + author.name());
            System.out.println("Date: " + author.date());
        }

        if (bookClass.isAnnotationPresent(Version.class)) {
            Version version = bookClass.getAnnotation(Version.class);
            System.out.println("Version: " + version.major() + "." + version.minor());
        }

        System.out.println();


        // ============================================================
        // 10. METHOD ANNOTATIONS
        // ============================================================

        System.out.println("--- Method Annotations ---");

        Method[] bookMethods = bookClass.getDeclaredMethods();
        for (Method method : bookMethods) {
            if (method.isAnnotationPresent(Test.class)) {
                System.out.println("Test method found: " + method.getName());
                Test testAnnotation = method.getAnnotation(Test.class);
                System.out.println("  Timeout: " + testAnnotation.timeout());
                System.out.println("  Expected: " + testAnnotation.expected().getSimpleName());

                // Execute test method
                Book book = new Book();
                method.invoke(book);
            }
        }

        System.out.println();


        // ============================================================
        // 11. FIELD ANNOTATIONS
        // ============================================================

        System.out.println("--- Field Annotations ---");

        Field[] employeeFields = Employee.class.getDeclaredFields();
        for (Field field : employeeFields) {
            if (field.isAnnotationPresent(NotNull.class)) {
                System.out.println(field.getName() + " is marked as @NotNull");
            }
            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                System.out.println(field.getName() + " max length: " + maxLength.value());
            }
        }

        System.out.println();


        // ============================================================
        // 12. PRACTICAL: SIMPLE DEPENDENCY INJECTION
        // ============================================================

        System.out.println("--- Simple DI Framework ---");

        ServiceContainer container = new ServiceContainer();
        container.resolve(UserService.class);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * REFLECTION USES:
         * - Frameworks (Spring, Hibernate)
         * - Testing tools (JUnit)
         * - Serialization libraries
         * - Dependency injection
         * - Plugin systems
         * - IDEs and debugging tools
         *
         * REFLECTION METHODS:
         * - Class.forName(String): Get class by name
         * - getClass(): Get class of object
         * - getDeclaredFields(): Get all fields
         * - getDeclaredMethods(): Get all methods
         * - getConstructors(): Get all constructors
         * - newInstance(): Create object
         * - setAccessible(true): Access private members
         *
         * ANNOTATION RETENTION:
         * - RetentionPolicy.SOURCE: Discarded by compiler
         * - RetentionPolicy.CLASS: In class file, not runtime
         * - RetentionPolicy.RUNTIME: Available at runtime (reflection)
         *
         * ANNOTATION TARGETS:
         * - ElementType.TYPE: Class, interface, enum
         * - ElementType.FIELD: Fields
         * - ElementType.METHOD: Methods
         * - ElementType.PARAMETER: Method parameters
         * - ElementType.CONSTRUCTOR: Constructors
         *
         * BENEFITS:
         * - Dynamic behavior
         * - Framework development
         * - Generic tools
         * - Metadata processing
         *
         * DRAWBACKS:
         * - Performance overhead
         * - Security concerns
         * - Breaks encapsulation
         * - Complex code
         *
         * BEST PRACTICES:
         * - Use sparingly
         * - Cache reflection results
         * - Handle exceptions properly
         * - Consider security implications
         * - Document heavily
         */
    }
}


// ============================================================
// EXAMPLE CLASS FOR REFLECTION
// ============================================================

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void greet() {
        System.out.println("Hello, I'm " + name);
    }
}


// ============================================================
// BUILT-IN ANNOTATIONS EXAMPLE
// ============================================================

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    @Deprecated
    public void oldMethod() {
        System.out.println("This method is deprecated!");
    }
}


// ============================================================
// CUSTOM ANNOTATIONS
// ============================================================

@Retention(RetentionPolicy.RUNTIME)  // Available at runtime
@Target(ElementType.TYPE)  // Can be used on classes
@interface Author {
    String name();
    String date();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Version {
    int major() default 1;
    int minor() default 0;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Test {
    int timeout() default 0;
    Class<? extends Exception> expected() default Exception.class;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}


// ============================================================
// CLASS WITH ANNOTATIONS
// ============================================================

@Author(name = "John Doe", date = "2024-01-01")
@Version(major = 2, minor = 1)
class Book {
    private String title;

    @Test(timeout = 1000)
    public void testMethod() {
        System.out.println("  Executing test method");
    }

    @Test(timeout = 500, expected = IllegalArgumentException.class)
    public void anotherTest() {
        System.out.println("  Executing another test");
    }
}


// ============================================================
// FIELD ANNOTATIONS EXAMPLE
// ============================================================

class Employee {
    @NotNull
    @MaxLength(50)
    private String name;

    @NotNull
    private String email;

    private Integer age;
}


// ============================================================
// DEPENDENCY INJECTION ANNOTATION
// ============================================================

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Inject {
}


// ============================================================
// SIMPLE DI FRAMEWORK
// ============================================================

class ServiceContainer {
    public <T> T resolve(Class<T> type) throws Exception {
        System.out.println("Resolving: " + type.getSimpleName());

        // Create instance
        T instance = type.getDeclaredConstructor().newInstance();

        // Inject dependencies
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object dependency = resolve(field.getType());
                field.set(instance, dependency);
                System.out.println("  Injected: " + field.getType().getSimpleName());
            }
        }

        return instance;
    }
}

class DatabaseService {
    public DatabaseService() {
        System.out.println("DatabaseService created");
    }
}

class UserService {
    @Inject
    private DatabaseService databaseService;

    public UserService() {
        System.out.println("UserService created");
    }
}
