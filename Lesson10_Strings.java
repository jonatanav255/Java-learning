/*
 * LESSON 10: STRINGS
 *
 * Strings are objects that represent sequences of characters.
 * In Java, String is a class, not a primitive type.
 * Strings are IMMUTABLE - once created, they cannot be changed.
 * Any operation that seems to modify a string actually creates a new string.
 *
 * Important String concepts:
 * - String is a reference type (class)
 * - Strings are immutable
 * - String literals are stored in the String pool
 * - Many useful methods for string manipulation
 */

public class Lesson10_Strings {
    public static void main(String[] args) {

        // ============================================================
        // 1. CREATING STRINGS
        // ============================================================

        // Method 1: String literal (most common)
        String str1 = "Hello";

        // Method 2: Using new keyword
        String str2 = new String("Hello");

        // Method 3: From character array
        char[] chars = {'J', 'a', 'v', 'a'};
        String str3 = new String(chars);

        System.out.println("str1: " + str1);
        System.out.println("str2: " + str2);
        System.out.println("str3: " + str3);
        System.out.println();


        // ============================================================
        // 2. STRING COMPARISON
        // ============================================================

        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = new String("Hello");
        String s4 = "hello";

        // WRONG WAY: Using == (compares references, not content)
        System.out.println("s1 == s2: " + (s1 == s2));       // true (same reference in pool)
        System.out.println("s1 == s3: " + (s1 == s3));       // false (different objects)

        // CORRECT WAY: Using .equals() (compares content)
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true
        System.out.println("s1.equals(s4): " + s1.equals(s4)); // false (case-sensitive)

        // Case-insensitive comparison
        System.out.println("s1.equalsIgnoreCase(s4): " + s1.equalsIgnoreCase(s4)); // true

        System.out.println();


        // ============================================================
        // 3. STRING LENGTH
        // ============================================================

        String text = "Hello, World!";
        int length = text.length();
        System.out.println("Length of '" + text + "': " + length);

        // Empty string
        String empty = "";
        System.out.println("Length of empty string: " + empty.length()); // 0

        System.out.println();


        // ============================================================
        // 4. ACCESSING CHARACTERS
        // ============================================================

        String word = "Java";

        // Get character at specific index (0-based)
        char first = word.charAt(0);
        char last = word.charAt(word.length() - 1);
        System.out.println("First character: " + first);
        System.out.println("Last character: " + last);

        // Loop through all characters
        System.out.print("All characters: ");
        for (int i = 0; i < word.length(); i++) {
            System.out.print(word.charAt(i) + " ");
        }
        System.out.println("\n");


        // ============================================================
        // 5. STRING CONCATENATION
        // ============================================================

        String firstName = "John";
        String lastName = "Doe";

        // Method 1: Using + operator
        String fullName1 = firstName + " " + lastName;
        System.out.println("Full name: " + fullName1);

        // Method 2: Using concat() method
        String fullName2 = firstName.concat(" ").concat(lastName);
        System.out.println("Full name: " + fullName2);

        // Concatenating with numbers
        String message = "Age: " + 25; // 25 is converted to string
        System.out.println(message);

        // Be careful with order!
        System.out.println("Sum: " + 5 + 10);     // "Sum: 510" (string concatenation)
        System.out.println("Sum: " + (5 + 10));   // "Sum: 15" (arithmetic first)

        System.out.println();


        // ============================================================
        // 6. SUBSTRING - EXTRACTING PARTS OF A STRING
        // ============================================================

        String sentence = "Hello, World!";

        // substring(startIndex) - from start to end
        String sub1 = sentence.substring(7);
        System.out.println("From index 7: " + sub1); // "World!"

        // substring(startIndex, endIndex) - from start to (end-1)
        String sub2 = sentence.substring(0, 5);
        System.out.println("From 0 to 5: " + sub2); // "Hello"

        String sub3 = sentence.substring(7, 12);
        System.out.println("From 7 to 12: " + sub3); // "World"

        System.out.println();


        // ============================================================
        // 7. SEARCHING IN STRINGS
        // ============================================================

        String text2 = "Java Programming";

        // indexOf - find first occurrence
        int index1 = text2.indexOf('a');
        System.out.println("First 'a' at index: " + index1); // 1

        // indexOf with starting position
        int index2 = text2.indexOf('a', 2); // Start searching from index 2
        System.out.println("Next 'a' at index: " + index2); // 3

        // indexOf for substring
        int index3 = text2.indexOf("gram");
        System.out.println("'gram' starts at: " + index3); // 10

        // lastIndexOf - find last occurrence
        int index4 = text2.lastIndexOf('a');
        System.out.println("Last 'a' at index: " + index4); // 12

        // Not found returns -1
        int index5 = text2.indexOf('z');
        System.out.println("'z' found at: " + index5); // -1

        // Check if string contains substring
        boolean contains = text2.contains("Program");
        System.out.println("Contains 'Program': " + contains); // true

        System.out.println();


        // ============================================================
        // 8. CASE CONVERSION
        // ============================================================

        String original = "Hello World";

        String upper = original.toUpperCase();
        System.out.println("Uppercase: " + upper);

        String lower = original.toLowerCase();
        System.out.println("Lowercase: " + lower);

        // Original is unchanged (strings are immutable!)
        System.out.println("Original: " + original);

        System.out.println();


        // ============================================================
        // 9. TRIMMING AND REPLACING
        // ============================================================

        // Remove leading and trailing whitespace
        String messy = "   Hello World   ";
        String clean = messy.trim();
        System.out.println("Before trim: '" + messy + "'");
        System.out.println("After trim: '" + clean + "'");

        // Replace characters
        String text3 = "Hello World";
        String replaced1 = text3.replace('o', 'a');
        System.out.println("Replace 'o' with 'a': " + replaced1);

        // Replace substring
        String replaced2 = text3.replace("World", "Java");
        System.out.println("Replace 'World' with 'Java': " + replaced2);

        // Replace all occurrences using regex
        String text4 = "apple banana apple";
        String replaced3 = text4.replaceAll("apple", "orange");
        System.out.println("Replace all 'apple': " + replaced3);

        System.out.println();


        // ============================================================
        // 10. STRING SPLITTING
        // ============================================================

        String csv = "Apple,Banana,Cherry,Date";
        String[] fruits = csv.split(",");

        System.out.println("Split by comma:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }

        // Split by space
        String sentence2 = "Java is awesome";
        String[] words = sentence2.split(" ");
        System.out.println("\nNumber of words: " + words.length);

        // Split with limit
        String data = "A:B:C:D:E";
        String[] parts = data.split(":", 3); // Split into max 3 parts
        for (String part : parts) {
            System.out.println(part);
        }

        System.out.println();


        // ============================================================
        // 11. CHECKING STRING PROPERTIES
        // ============================================================

        String test1 = "";
        String test2 = "Hello";
        String test3 = "   ";

        // Check if empty
        System.out.println("test1.isEmpty(): " + test1.isEmpty());     // true
        System.out.println("test2.isEmpty(): " + test2.isEmpty());     // false

        // Check if blank (Java 11+) - empty or only whitespace
        System.out.println("test3.isBlank(): " + test3.isBlank());     // true

        // Check prefix and suffix
        String filename = "document.pdf";
        System.out.println("Starts with 'doc': " + filename.startsWith("doc"));
        System.out.println("Ends with '.pdf': " + filename.endsWith(".pdf"));

        System.out.println();


        // ============================================================
        // 12. STRING COMPARISON (ORDERING)
        // ============================================================

        String str_a = "apple";
        String str_b = "banana";
        String str_c = "apple";

        // compareTo: returns negative if str_a < str_b, 0 if equal, positive if str_a > str_b
        System.out.println("apple compareTo banana: " + str_a.compareTo(str_b)); // negative
        System.out.println("apple compareTo apple: " + str_a.compareTo(str_c));  // 0

        // Useful for sorting
        if (str_a.compareTo(str_b) < 0) {
            System.out.println(str_a + " comes before " + str_b);
        }

        System.out.println();


        // ============================================================
        // 13. STRING TO NUMBER CONVERSION
        // ============================================================

        String numStr = "123";
        int num = Integer.parseInt(numStr);
        System.out.println("String to int: " + num);

        String doubleStr = "3.14";
        double d = Double.parseDouble(doubleStr);
        System.out.println("String to double: " + d);

        // Number to string
        int value = 456;
        String valueStr = String.valueOf(value);
        System.out.println("Int to string: " + valueStr);

        System.out.println();


        // ============================================================
        // 14. STRING BUILDER (FOR MUTABLE STRINGS)
        // ============================================================
        // StringBuilder is more efficient for many string modifications

        StringBuilder sb = new StringBuilder("Hello");

        // Append
        sb.append(" World");
        sb.append("!");
        System.out.println("After append: " + sb);

        // Insert at position
        sb.insert(5, ",");
        System.out.println("After insert: " + sb);

        // Delete
        sb.delete(5, 6); // Remove comma
        System.out.println("After delete: " + sb);

        // Reverse
        sb.reverse();
        System.out.println("After reverse: " + sb);

        // Convert to String
        String result = sb.toString();

        System.out.println();


        // ============================================================
        // PRACTICAL EXAMPLES
        // ============================================================

        // Example 1: Count vowels
        String text5 = "Hello World";
        int vowelCount = countVowels(text5);
        System.out.println("Vowels in '" + text5 + "': " + vowelCount);

        // Example 2: Reverse a string
        String original2 = "Java";
        String reversed = reverseString(original2);
        System.out.println("Original: " + original2 + ", Reversed: " + reversed);

        // Example 3: Check palindrome
        String word1 = "racecar";
        String word2 = "hello";
        System.out.println(word1 + " is palindrome: " + isPalindrome(word1));
        System.out.println(word2 + " is palindrome: " + isPalindrome(word2));

        // Example 4: Title case
        String title = "hello world from java";
        System.out.println("Title case: " + toTitleCase(title));


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * 1. String is a class (reference type), not primitive
         * 2. Strings are IMMUTABLE - operations create new strings
         * 3. Use .equals() to compare content, NOT ==
         * 4. Use .equalsIgnoreCase() for case-insensitive comparison
         * 5. Indices start at 0 and go to length()-1
         * 6. Use StringBuilder for many string modifications (more efficient)
         * 7. Common methods: length(), charAt(), substring(), indexOf()
         * 8. Case conversion: toUpperCase(), toLowerCase()
         * 9. Cleaning: trim(), replace(), replaceAll()
         * 10. Splitting: split()
         * 11. Checking: isEmpty(), startsWith(), endsWith(), contains()
         */
    }

    // Helper method: Count vowels in a string
    public static int countVowels(String str) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (int i = 0; i < str.length(); i++) {
            if (vowels.indexOf(str.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

    // Helper method: Reverse a string
    public static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    // Helper method: Check if string is palindrome
    public static boolean isPalindrome(String str) {
        String cleaned = str.toLowerCase().replaceAll("[^a-z]", "");
        String reversed = reverseString(cleaned);
        return cleaned.equals(reversed);
    }

    // Helper method: Convert to title case
    public static String toTitleCase(String str) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1).toLowerCase());
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
}
