/*
 * LESSON 29: REGULAR EXPRESSIONS (REGEX)
 *
 * Regular expressions are patterns used to match character combinations in strings.
 * They are powerful tools for text processing, validation, and searching.
 *
 * KEY CLASSES:
 * - Pattern: Compiled regex pattern
 * - Matcher: Engine that performs match operations
 * - PatternSyntaxException: Invalid regex exception
 *
 * COMMON METACHARACTERS:
 * . - Any character except newline
 * ^ - Start of line
 * $ - End of line
 * * - 0 or more occurrences
 * + - 1 or more occurrences
 * ? - 0 or 1 occurrence
 * {n} - Exactly n occurrences
 * {n,} - n or more occurrences
 * {n,m} - Between n and m occurrences
 * [] - Character class
 * | - OR operator
 * () - Group
 * \ - Escape character
 *
 * CHARACTER CLASSES:
 * [abc] - a, b, or c
 * [^abc] - Any character except a, b, c
 * [a-z] - a through z
 * [A-Z] - A through Z
 * [0-9] - 0 through 9
 * \d - Digit [0-9]
 * \D - Non-digit
 * \w - Word character [a-zA-Z0-9_]
 * \W - Non-word character
 * \s - Whitespace
 * \S - Non-whitespace
 *
 * QUANTIFIERS:
 * * - 0 or more
 * + - 1 or more
 * ? - 0 or 1
 * {n} - Exactly n
 * {n,} - At least n
 * {n,m} - Between n and m
 *
 * USE CASES:
 * - Validation (email, phone, passwords)
 * - Text searching and extraction
 * - Data parsing
 * - String replacement
 * - Log file analysis
 */

import java.util.regex.*;
import java.util.*;

public class Lesson29_RegularExpressions {
    public static void main(String[] args) {

        System.out.println("=== REGULAR EXPRESSIONS ===\n");

        // ============================================================
        // 1. BASIC PATTERN MATCHING
        // ============================================================

        System.out.println("--- Basic Matching ---");

        String text = "Hello World";
        String pattern = "World";

        boolean matches = Pattern.matches(pattern, "World");
        System.out.println("'World' matches 'World': " + matches);

        // Check if text contains pattern
        boolean contains = text.contains("World");
        System.out.println("Text contains 'World': " + contains);

        System.out.println();


        // ============================================================
        // 2. PATTERN AND MATCHER
        // ============================================================

        System.out.println("--- Pattern and Matcher ---");

        Pattern p = Pattern.compile("Java");
        Matcher m = p.matcher("I love Java programming. Java is awesome!");

        // Find all occurrences
        System.out.println("Finding 'Java':");
        while (m.find()) {
            System.out.println("Found at index: " + m.start());
        }

        System.out.println();


        // ============================================================
        // 3. METACHARACTERS
        // ============================================================

        System.out.println("--- Metacharacters ---");

        // Dot . matches any character
        System.out.println("c.t matches 'cat': " + Pattern.matches("c.t", "cat"));
        System.out.println("c.t matches 'cot': " + Pattern.matches("c.t", "cot"));
        System.out.println("c.t matches 'c9t': " + Pattern.matches("c.t", "c9t"));

        // ^ starts with, $ ends with
        System.out.println("^Hello matches 'Hello World': " +
                Pattern.compile("^Hello").matcher("Hello World").find());

        System.out.println("World$ matches 'Hello World': " +
                Pattern.compile("World$").matcher("Hello World").find());

        System.out.println();


        // ============================================================
        // 4. QUANTIFIERS
        // ============================================================

        System.out.println("--- Quantifiers ---");

        // * (0 or more)
        System.out.println("ab*c matches 'ac': " + Pattern.matches("ab*c", "ac"));
        System.out.println("ab*c matches 'abc': " + Pattern.matches("ab*c", "abc"));
        System.out.println("ab*c matches 'abbc': " + Pattern.matches("ab*c", "abbc"));

        // + (1 or more)
        System.out.println("ab+c matches 'ac': " + Pattern.matches("ab+c", "ac"));
        System.out.println("ab+c matches 'abc': " + Pattern.matches("ab+c", "abc"));

        // ? (0 or 1)
        System.out.println("colou?r matches 'color': " + Pattern.matches("colou?r", "color"));
        System.out.println("colou?r matches 'colour': " + Pattern.matches("colou?r", "colour"));

        // {n} (exactly n)
        System.out.println("a{3} matches 'aaa': " + Pattern.matches("a{3}", "aaa"));

        // {n,m} (between n and m)
        System.out.println("a{2,4} matches 'aa': " + Pattern.matches("a{2,4}", "aa"));
        System.out.println("a{2,4} matches 'aaa': " + Pattern.matches("a{2,4}", "aaa"));

        System.out.println();


        // ============================================================
        // 5. CHARACTER CLASSES
        // ============================================================

        System.out.println("--- Character Classes ---");

        // [abc] - matches a, b, or c
        System.out.println("[abc] matches 'a': " + Pattern.matches("[abc]", "a"));
        System.out.println("[abc] matches 'd': " + Pattern.matches("[abc]", "d"));

        // [a-z] - lowercase letters
        System.out.println("[a-z]+ matches 'hello': " + Pattern.matches("[a-z]+", "hello"));

        // [0-9] - digits
        System.out.println("[0-9]+ matches '12345': " + Pattern.matches("[0-9]+", "12345"));

        // [^abc] - NOT a, b, or c
        System.out.println("[^abc] matches 'd': " + Pattern.matches("[^abc]", "d"));

        System.out.println();


        // ============================================================
        // 6. PREDEFINED CHARACTER CLASSES
        // ============================================================

        System.out.println("--- Predefined Classes ---");

        // \\d - digit [0-9]
        System.out.println("\\\\d+ matches '123': " + Pattern.matches("\\d+", "123"));

        // \\w - word character [a-zA-Z0-9_]
        System.out.println("\\\\w+ matches 'Hello123': " + Pattern.matches("\\w+", "Hello123"));

        // \\s - whitespace
        System.out.println("\\\\s matches ' ': " + Pattern.matches("\\s", " "));

        System.out.println();


        // ============================================================
        // 7. EMAIL VALIDATION
        // ============================================================

        System.out.println("--- Email Validation ---");

        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        testEmail("user@example.com", emailPattern);
        testEmail("john.doe123@company.co.uk", emailPattern);
        testEmail("invalid.email", emailPattern);
        testEmail("@example.com", emailPattern);

        System.out.println();


        // ============================================================
        // 8. PHONE NUMBER VALIDATION
        // ============================================================

        System.out.println("--- Phone Number Validation ---");

        String phonePattern = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";

        testPhone("123-456-7890", phonePattern);
        testPhone("(123) 456-7890", phonePattern);
        testPhone("1234567890", phonePattern);
        testPhone("12-345-6789", phonePattern);

        System.out.println();


        // ============================================================
        // 9. PASSWORD VALIDATION
        // ============================================================

        System.out.println("--- Password Validation ---");

        // At least 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        testPassword("Pass123!", passwordPattern);
        testPassword("weakpass", passwordPattern);
        testPassword("NoDigit!", passwordPattern);
        testPassword("Secure@Pass1", passwordPattern);

        System.out.println();


        // ============================================================
        // 10. EXTRACTING DATA
        // ============================================================

        System.out.println("--- Extracting Data ---");

        String text2 = "Contact us at support@example.com or sales@company.org";
        Pattern emailExtract = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}");
        Matcher emailMatcher = emailExtract.matcher(text2);

        System.out.println("Found emails:");
        while (emailMatcher.find()) {
            System.out.println("- " + emailMatcher.group());
        }

        System.out.println();


        // ============================================================
        // 11. CAPTURING GROUPS
        // ============================================================

        System.out.println("--- Capturing Groups ---");

        String dateText = "2024-01-15";
        Pattern datePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");
        Matcher dateMatcher = datePattern.matcher(dateText);

        if (dateMatcher.matches()) {
            System.out.println("Year: " + dateMatcher.group(1));
            System.out.println("Month: " + dateMatcher.group(2));
            System.out.println("Day: " + dateMatcher.group(3));
        }

        System.out.println();


        // ============================================================
        // 12. STRING REPLACEMENT
        // ============================================================

        System.out.println("--- String Replacement ---");

        String text3 = "Hello World! Hello Java!";

        // Replace first occurrence
        String result1 = text3.replaceFirst("Hello", "Hi");
        System.out.println("Replace first: " + result1);

        // Replace all occurrences
        String result2 = text3.replaceAll("Hello", "Hi");
        System.out.println("Replace all: " + result2);

        // Remove digits
        String text4 = "abc123def456";
        String result3 = text4.replaceAll("\\d", "");
        System.out.println("Remove digits: " + result3);

        System.out.println();


        // ============================================================
        // 13. SPLITTING STRINGS
        // ============================================================

        System.out.println("--- Splitting ---");

        String csv = "John,Doe,30,Engineer";
        String[] parts = csv.split(",");
        System.out.println("CSV parts:");
        for (String part : parts) {
            System.out.println("- " + part);
        }

        // Split by multiple delimiters
        String text5 = "one,two;three:four";
        String[] tokens = text5.split("[,;:]");
        System.out.println("\nTokens:");
        for (String token : tokens) {
            System.out.println("- " + token);
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * COMMON PATTERNS:
         * Email: ^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$
         * Phone: ^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$
         * URL: https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}
         * IP Address: ^(\\d{1,3}\\.){3}\\d{1,3}$
         * Date: \\d{4}-\\d{2}-\\d{2}
         * Time: \\d{2}:\\d{2}(:\\d{2})?
         * Hex Color: ^#[0-9A-Fa-f]{6}$
         * Username: ^[a-zA-Z0-9_]{3,16}$
         *
         * MATCHER METHODS:
         * - matches(): Entire string matches
         * - find(): Find next match
         * - lookingAt(): Match from beginning
         * - start(): Start index of match
         * - end(): End index of match
         * - group(): Matched text
         * - group(n): nth capturing group
         *
         * PATTERN FLAGS:
         * - Pattern.CASE_INSENSITIVE
         * - Pattern.MULTILINE
         * - Pattern.DOTALL
         * - Pattern.UNICODE_CASE
         *
         * BEST PRACTICES:
         * - Compile Pattern once, reuse
         * - Use raw strings for readability
         * - Test regex thoroughly
         * - Keep patterns simple when possible
         * - Comment complex patterns
         * - Handle PatternSyntaxException
         *
         * PERFORMANCE TIPS:
         * - Precompile patterns
         * - Use possessive quantifiers when possible
         * - Avoid excessive backtracking
         * - Use specific character classes
         *
         * TOOLS:
         * - regex101.com - Online tester
         * - regexr.com - Visual tester
         * - Pattern.quote() - Escape special chars
         */
    }

    // Helper method for email testing
    public static void testEmail(String email, String pattern) {
        boolean valid = Pattern.matches(pattern, email);
        System.out.println(email + " - " + (valid ? "✓ Valid" : "✗ Invalid"));
    }

    // Helper method for phone testing
    public static void testPhone(String phone, String pattern) {
        boolean valid = Pattern.matches(pattern, phone);
        System.out.println(phone + " - " + (valid ? "✓ Valid" : "✗ Invalid"));
    }

    // Helper method for password testing
    public static void testPassword(String password, String pattern) {
        boolean valid = Pattern.matches(pattern, password);
        System.out.println(password + " - " + (valid ? "✓ Valid" : "✗ Invalid"));
    }
}
