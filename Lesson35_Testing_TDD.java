/*
 * LESSON 35: TESTING & TEST-DRIVEN DEVELOPMENT (TDD)
 *
 * Testing is crucial for writing reliable, maintainable software.
 * This lesson covers testing fundamentals, frameworks, and best practices.
 *
 * KEY CONCEPTS:
 * - Unit Test: Tests individual methods/classes in isolation
 * - Integration Test: Tests multiple components together
 * - Test Double: Fake objects for testing (mock, stub, spy)
 * - TDD: Write tests before code (Red-Green-Refactor)
 * - Code Coverage: Percentage of code tested
 * - Assertion: Statement that must be true
 *
 * TESTING FRAMEWORKS:
 * - JUnit 5 (Jupiter): Most popular unit testing framework
 * - TestNG: Alternative to JUnit with more features
 * - Mockito: Mocking framework
 * - AssertJ: Fluent assertions library
 * - Hamcrest: Matcher library
 *
 * JUNIT 5 STRUCTURE:
 * - @Test: Marks test method
 * - @BeforeEach: Run before each test
 * - @AfterEach: Run after each test
 * - @BeforeAll: Run once before all tests
 * - @AfterAll: Run once after all tests
 * - @Disabled: Skip test
 * - @DisplayName: Custom test name
 *
 * TEST TYPES:
 * 1. Unit Tests: Test single unit of code
 * 2. Integration Tests: Test multiple units together
 * 3. Functional Tests: Test features end-to-end
 * 4. Performance Tests: Test speed/scalability
 * 5. Regression Tests: Ensure bugs don't return
 *
 * TDD CYCLE:
 * 1. RED: Write failing test
 * 2. GREEN: Write minimal code to pass
 * 3. REFACTOR: Improve code quality
 * 4. Repeat
 *
 * BEST PRACTICES:
 * - Test one thing per test
 * - Tests should be independent
 * - Use descriptive test names
 * - Follow AAA pattern (Arrange, Act, Assert)
 * - Keep tests simple
 * - Don't test framework code
 * - Aim for high coverage (80%+)
 *
 * NOTE: This lesson demonstrates testing concepts without external dependencies.
 * For real projects, add JUnit/Mockito to your classpath/build tool.
 *
 * Maven dependency:
 * <dependency>
 *     <groupId>org.junit.jupiter</groupId>
 *     <artifactId>junit-jupiter</artifactId>
 *     <version>5.10.0</version>
 *     <scope>test</scope>
 * </dependency>
 *
 * Gradle dependency:
 * testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
 */

import java.util.*;
import java.util.function.*;

public class Lesson35_Testing_TDD {
    public static void main(String[] args) {

        System.out.println("=== TESTING & TEST-DRIVEN DEVELOPMENT ===\n");

        // ============================================================
        // 1. SIMPLE ASSERTIONS (MANUAL)
        // ============================================================

        System.out.println("--- Manual Assertions ---");

        // Simple assertion implementation
        SimpleAssert.assertEquals(5, 2 + 3, "Addition should work");
        SimpleAssert.assertTrue(10 > 5, "10 should be greater than 5");
        SimpleAssert.assertFalse(3 > 10, "3 should not be greater than 10");
        SimpleAssert.assertNotNull("Hello", "String should not be null");

        System.out.println("✓ All manual assertions passed");
        System.out.println();


        // ============================================================
        // 2. CALCULATOR EXAMPLE (TDD APPROACH)
        // ============================================================

        System.out.println("--- Calculator Tests ---");

        Calculator calc = new Calculator();

        // Test addition
        SimpleAssert.assertEquals(5, calc.add(2, 3), "2 + 3 = 5");
        SimpleAssert.assertEquals(0, calc.add(-5, 5), "-5 + 5 = 0");

        // Test subtraction
        SimpleAssert.assertEquals(2, calc.subtract(5, 3), "5 - 3 = 2");
        SimpleAssert.assertEquals(-8, calc.subtract(2, 10), "2 - 10 = -8");

        // Test multiplication
        SimpleAssert.assertEquals(15, calc.multiply(3, 5), "3 * 5 = 15");
        SimpleAssert.assertEquals(0, calc.multiply(0, 100), "0 * 100 = 0");

        // Test division
        SimpleAssert.assertEquals(5, calc.divide(15, 3), "15 / 3 = 5");

        // Test division by zero
        try {
            calc.divide(10, 0);
            SimpleAssert.fail("Should throw ArithmeticException");
        } catch (ArithmeticException e) {
            System.out.println("✓ Division by zero correctly throws exception");
        }

        System.out.println("✓ All calculator tests passed");
        System.out.println();


        // ============================================================
        // 3. STRING UTIL TESTS
        // ============================================================

        System.out.println("--- StringUtil Tests ---");

        // Test isPalindrome
        SimpleAssert.assertTrue(StringUtil.isPalindrome("racecar"), "racecar is palindrome");
        SimpleAssert.assertTrue(StringUtil.isPalindrome("A man a plan a canal Panama"),
                "Ignores spaces and case");
        SimpleAssert.assertFalse(StringUtil.isPalindrome("hello"), "hello is not palindrome");

        // Test reverse
        SimpleAssert.assertEquals("olleh", StringUtil.reverse("hello"), "Reverse hello");
        SimpleAssert.assertEquals("", StringUtil.reverse(""), "Reverse empty string");

        // Test countWords
        SimpleAssert.assertEquals(5, StringUtil.countWords("The quick brown fox jumps"),
                "Count words");
        SimpleAssert.assertEquals(0, StringUtil.countWords(""), "Empty string has 0 words");

        System.out.println("✓ All StringUtil tests passed");
        System.out.println();


        // ============================================================
        // 4. LIST MANAGER TESTS
        // ============================================================

        System.out.println("--- ListManager Tests ---");

        ListManager<Integer> manager = new ListManager<>();

        // Test add
        manager.add(10);
        manager.add(20);
        manager.add(30);
        SimpleAssert.assertEquals(3, manager.size(), "Size should be 3");

        // Test contains
        SimpleAssert.assertTrue(manager.contains(20), "Should contain 20");
        SimpleAssert.assertFalse(manager.contains(99), "Should not contain 99");

        // Test remove
        manager.remove(20);
        SimpleAssert.assertEquals(2, manager.size(), "Size should be 2 after removal");
        SimpleAssert.assertFalse(manager.contains(20), "Should not contain 20 after removal");

        // Test clear
        manager.clear();
        SimpleAssert.assertEquals(0, manager.size(), "Size should be 0 after clear");

        System.out.println("✓ All ListManager tests passed");
        System.out.println();


        // ============================================================
        // 5. JUNIT 5 STYLE EXAMPLES
        // ============================================================

        System.out.println("--- JUnit 5 Style Examples ---");

        System.out.println("""
                JUnit 5 Test Class Example:

                import org.junit.jupiter.api.*;
                import static org.junit.jupiter.api.Assertions.*;

                public class CalculatorTest {

                    private Calculator calculator;

                    @BeforeEach
                    void setUp() {
                        calculator = new Calculator();
                        System.out.println("Setting up test");
                    }

                    @AfterEach
                    void tearDown() {
                        calculator = null;
                        System.out.println("Cleaning up test");
                    }

                    @Test
                    @DisplayName("Addition of two positive numbers")
                    void testAddition() {
                        assertEquals(5, calculator.add(2, 3));
                    }

                    @Test
                    void testDivisionByZero() {
                        assertThrows(ArithmeticException.class,
                            () -> calculator.divide(10, 0));
                    }

                    @Test
                    @Disabled("Not implemented yet")
                    void testSquareRoot() {
                        // TODO: implement later
                    }
                }

                Running tests:
                mvn test                 (Maven)
                gradle test              (Gradle)
                """);

        System.out.println();


        // ============================================================
        // 6. COMMON ASSERTIONS
        // ============================================================

        System.out.println("--- Common JUnit 5 Assertions ---");

        System.out.println("""
                // Equality
                assertEquals(expected, actual);
                assertEquals(expected, actual, "Custom message");
                assertNotEquals(unexpected, actual);

                // Boolean
                assertTrue(condition);
                assertFalse(condition);

                // Null checks
                assertNull(object);
                assertNotNull(object);

                // Same/Different objects
                assertSame(expected, actual);      // Same reference
                assertNotSame(expected, actual);

                // Array equality
                assertArrayEquals(expectedArray, actualArray);

                // Exceptions
                assertThrows(Exception.class, () -> {
                    // code that should throw
                });

                // Timeout
                assertTimeout(Duration.ofSeconds(1), () -> {
                    // code that should complete within 1 second
                });

                // Multiple assertions
                assertAll(
                    () -> assertEquals(2, 1 + 1),
                    () -> assertEquals(4, 2 * 2),
                    () -> assertEquals(6, 3 + 3)
                );

                // AssertJ (fluent assertions)
                assertThat(actual)
                    .isNotNull()
                    .isEqualTo(expected)
                    .isInstanceOf(String.class);
                """);

        System.out.println();


        // ============================================================
        // 7. MOCKING CONCEPTS
        // ============================================================

        System.out.println("--- Mocking (Mockito Style) ---");

        System.out.println("""
                Mocking with Mockito:

                // Create mock
                UserRepository mockRepo = mock(UserRepository.class);

                // Define behavior
                when(mockRepo.findById(1))
                    .thenReturn(new User(1, "John"));

                // Use mock
                User user = mockRepo.findById(1);
                assertEquals("John", user.getName());

                // Verify interactions
                verify(mockRepo).findById(1);
                verify(mockRepo, times(1)).findById(1);
                verify(mockRepo, never()).deleteUser(anyInt());

                // Argument matchers
                when(mockRepo.findById(anyInt()))
                    .thenReturn(new User(0, "Default"));

                // Stubbing void methods
                doNothing().when(mockRepo).deleteUser(1);
                doThrow(new RuntimeException()).when(mockRepo).deleteUser(999);

                // Spy (partial mock)
                List<String> list = new ArrayList<>();
                List<String> spy = spy(list);
                when(spy.size()).thenReturn(100);
                spy.add("one");  // Real method called
                assertEquals(1, spy.size());  // Returns 100 (stubbed)

                Types of Test Doubles:
                1. Mock: Object with predefined behavior
                2. Stub: Provides canned answers
                3. Spy: Real object with some methods mocked
                4. Fake: Working implementation (simplified)
                5. Dummy: Passed but never used
                """);

        System.out.println();


        // ============================================================
        // 8. TDD RED-GREEN-REFACTOR
        // ============================================================

        System.out.println("--- TDD Cycle ---");

        System.out.println("""
                Test-Driven Development (TDD) Workflow:

                STEP 1: RED - Write a failing test
                ┌─────────────────────────────────────────────┐
                │ @Test                                       │
                │ void testFizzBuzz_returns_Fizz_for_3() {    │
                │     assertEquals("Fizz", fizzBuzz(3));      │
                │ }                                           │
                └─────────────────────────────────────────────┘
                Result: Test FAILS (method doesn't exist)

                STEP 2: GREEN - Write minimal code to pass
                ┌─────────────────────────────────────────────┐
                │ String fizzBuzz(int n) {                    │
                │     if (n == 3) return "Fizz";             │
                │     return String.valueOf(n);               │
                │ }                                           │
                └─────────────────────────────────────────────┘
                Result: Test PASSES

                STEP 3: REFACTOR - Improve code quality
                ┌─────────────────────────────────────────────┐
                │ String fizzBuzz(int n) {                    │
                │     if (n % 3 == 0) return "Fizz";         │
                │     return String.valueOf(n);               │
                │ }                                           │
                └─────────────────────────────────────────────┘
                Result: Test still PASSES, code is better

                STEP 4: Repeat with more tests
                Add test for 5 → Buzz
                Add test for 15 → FizzBuzz
                Keep refactoring

                TDD Benefits:
                ✓ Better design
                ✓ Built-in documentation
                ✓ Confidence in changes
                ✓ Fewer bugs
                ✓ Easier refactoring
                """);

        System.out.println();


        // ============================================================
        // 9. FIZZBUZZ EXAMPLE (TDD)
        // ============================================================

        System.out.println("--- FizzBuzz Tests ---");

        FizzBuzz fb = new FizzBuzz();

        SimpleAssert.assertEquals("1", fb.convert(1), "1 returns 1");
        SimpleAssert.assertEquals("2", fb.convert(2), "2 returns 2");
        SimpleAssert.assertEquals("Fizz", fb.convert(3), "3 returns Fizz");
        SimpleAssert.assertEquals("Fizz", fb.convert(6), "6 returns Fizz");
        SimpleAssert.assertEquals("Buzz", fb.convert(5), "5 returns Buzz");
        SimpleAssert.assertEquals("Buzz", fb.convert(10), "10 returns Buzz");
        SimpleAssert.assertEquals("FizzBuzz", fb.convert(15), "15 returns FizzBuzz");
        SimpleAssert.assertEquals("FizzBuzz", fb.convert(30), "30 returns FizzBuzz");

        System.out.println("✓ All FizzBuzz tests passed");
        System.out.println();


        // ============================================================
        // 10. PARAMETERIZED TESTS
        // ============================================================

        System.out.println("--- Parameterized Tests (JUnit 5) ---");

        System.out.println("""
                Parameterized tests run same test with different inputs:

                @ParameterizedTest
                @ValueSource(ints = {1, 3, 5, 7, 9})
                void testOddNumbers(int number) {
                    assertTrue(number % 2 != 0);
                }

                @ParameterizedTest
                @CsvSource({
                    "1, 1",
                    "2, 4",
                    "3, 9",
                    "4, 16"
                })
                void testSquare(int input, int expected) {
                    assertEquals(expected, input * input);
                }

                @ParameterizedTest
                @MethodSource("provideStringsForPalindrome")
                void testPalindrome(String str, boolean expected) {
                    assertEquals(expected, isPalindrome(str));
                }

                static Stream<Arguments> provideStringsForPalindrome() {
                    return Stream.of(
                        Arguments.of("racecar", true),
                        Arguments.of("hello", false),
                        Arguments.of("noon", true)
                    );
                }

                Benefits:
                - Less code duplication
                - Easy to add test cases
                - Clear what's being tested
                """);

        System.out.println();


        // ============================================================
        // 11. TEST ORGANIZATION
        // ============================================================

        System.out.println("--- Test Organization ---");

        System.out.println("""
                Project Structure:
                ┌────────────────────────────────────────────┐
                │ src/                                       │
                │ ├── main/                                  │
                │ │   └── java/                              │
                │ │       └── com/myapp/                     │
                │ │           ├── Calculator.java            │
                │ │           └── StringUtil.java            │
                │ └── test/                                  │
                │     └── java/                              │
                │         └── com/myapp/                     │
                │             ├── CalculatorTest.java        │
                │             └── StringUtilTest.java        │
                └────────────────────────────────────────────┘

                Naming Conventions:
                - Test class: ClassNameTest.java
                - Test method: testMethodName_condition_expectedResult()
                - Example: testDivide_byZero_throwsException()

                Test Categories:
                @Tag("unit")      - Unit tests
                @Tag("integration") - Integration tests
                @Tag("slow")      - Slow tests

                Run specific category:
                mvn test -Dgroups="unit"
                gradle test --tests "*unit*"
                """);

        System.out.println();


        // ============================================================
        // 12. CODE COVERAGE
        // ============================================================

        System.out.println("--- Code Coverage ---");

        System.out.println("""
                Code Coverage Tools:
                - JaCoCo (most popular)
                - Cobertura
                - Emma

                Maven JaCoCo Plugin:
                <plugin>
                    <groupId>org.jacoco</groupId>
                    <artifactId>jacoco-maven-plugin</artifactId>
                    <version>0.8.10</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>prepare-agent</goal>
                            </goals>
                        </execution>
                        <execution>
                            <id>report</id>
                            <phase>test</phase>
                            <goals>
                                <goal>report</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>

                Generate coverage report:
                mvn clean test jacoco:report

                Coverage Metrics:
                - Line Coverage: % of lines executed
                - Branch Coverage: % of if/else branches taken
                - Method Coverage: % of methods called
                - Class Coverage: % of classes loaded

                Target Coverage:
                - 80%+ is good
                - 90%+ is excellent
                - 100% not always practical/necessary

                What NOT to test:
                - Getters/Setters (unless complex logic)
                - Framework code
                - Third-party libraries
                - Configuration files
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * TESTING FUNDAMENTALS:
         * - Unit tests verify individual components
         * - Integration tests verify components working together
         * - Tests are documentation of expected behavior
         * - Good tests catch bugs early
         * - Tests enable confident refactoring
         *
         * TEST STRUCTURE (AAA PATTERN):
         * 1. Arrange: Set up test data and conditions
         * 2. Act: Execute the code being tested
         * 3. Assert: Verify the results
         *
         * BEST PRACTICES:
         * 1. One assertion per test (ideally)
         * 2. Tests should be independent
         * 3. Use descriptive test names
         * 4. Keep tests simple and readable
         * 5. Don't test implementation details
         * 6. Test edge cases and boundaries
         * 7. Use test doubles (mocks) wisely
         * 8. Maintain tests like production code
         * 9. Run tests frequently
         * 10. Fix failing tests immediately
         *
         * TDD BENEFITS:
         * - Forces thinking about design
         * - Creates comprehensive test suite
         * - Reduces debugging time
         * - Documents expected behavior
         * - Increases confidence in code
         * - Prevents over-engineering
         *
         * WHEN TO USE MOCKS:
         * ✓ External dependencies (database, API)
         * ✓ Slow operations
         * ✓ Non-deterministic behavior
         * ✓ Testing error conditions
         * ✗ Simple logic (use real objects)
         * ✗ Value objects
         *
         * TESTING PYRAMID:
         * Many Unit Tests (fast, isolated)
         *   ↑
         * Some Integration Tests (moderate speed)
         *   ↑
         * Few E2E Tests (slow, comprehensive)
         *
         * COMMON MISTAKES:
         * - Testing too much in one test
         * - Fragile tests (break with small changes)
         * - Not testing edge cases
         * - Ignoring failing tests
         * - Over-mocking
         * - Testing framework code
         * - Poor test names
         *
         * TOOLS ECOSYSTEM:
         * - JUnit 5: Testing framework
         * - Mockito: Mocking
         * - AssertJ: Fluent assertions
         * - TestContainers: Docker containers for tests
         * - WireMock: HTTP mocking
         * - ArchUnit: Architecture testing
         * - JaCoCo: Code coverage
         *
         * CONTINUOUS INTEGRATION:
         * - Run tests on every commit
         * - Fail build on test failures
         * - Track code coverage
         * - Fast feedback loop
         *
         * PERFORMANCE TESTING:
         * - JMH: Microbenchmarking
         * - JMeter: Load testing
         * - Gatling: Performance testing
         */
    }
}


// ============================================================
// SIMPLE ASSERT UTILITY
// ============================================================

class SimpleAssert {

    public static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
        System.out.println("  ✓ " + message);
    }

    public static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
        System.out.println("  ✓ " + message);
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message + " - Expected true but was false");
        }
        System.out.println("  ✓ " + message);
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message + " - Expected false but was true");
        }
        System.out.println("  ✓ " + message);
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError(message + " - Expected non-null but was null");
        }
        System.out.println("  ✓ " + message);
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }
}


// ============================================================
// CALCULATOR (EXAMPLE CLASS TO TEST)
// ============================================================

class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}


// ============================================================
// STRING UTIL (EXAMPLE CLASS TO TEST)
// ============================================================

class StringUtil {

    public static boolean isPalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    public static int countWords(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        return str.trim().split("\\s+").length;
    }
}


// ============================================================
// LIST MANAGER (EXAMPLE CLASS TO TEST)
// ============================================================

class ListManager<T> {
    private List<T> items;

    public ListManager() {
        this.items = new ArrayList<>();
    }

    public void add(T item) {
        items.add(item);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public int size() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}


// ============================================================
// FIZZBUZZ (TDD EXAMPLE)
// ============================================================

class FizzBuzz {

    public String convert(int number) {
        if (number % 15 == 0) {
            return "FizzBuzz";
        }
        if (number % 3 == 0) {
            return "Fizz";
        }
        if (number % 5 == 0) {
            return "Buzz";
        }
        return String.valueOf(number);
    }
}
