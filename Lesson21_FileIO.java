/*
 * LESSON 21: FILE INPUT/OUTPUT (FILE I/O)
 *
 * File I/O allows programs to read from and write to files.
 * Java provides many classes for file operations.
 *
 * KEY CLASSES:
 * - File: Represents a file or directory path
 * - FileWriter: Write characters to files
 * - FileReader: Read characters from files
 * - BufferedWriter: Efficient writing with buffering
 * - BufferedReader: Efficient reading with buffering
 * - Scanner: Read and parse files
 * - PrintWriter: Easy formatted writing
 * - Files (Java NIO): Modern file operations
 *
 * IMPORTANT CONCEPTS:
 * - Always close resources (use try-with-resources)
 * - Handle IOExceptions
 * - Use buffered readers/writers for better performance
 * - Check if file exists before reading
 * - Create parent directories if needed
 *
 * FILE OPERATIONS:
 * - Create file
 * - Write to file
 * - Read from file
 * - Append to file
 * - Delete file
 * - Check file properties
 */

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Lesson21_FileIO {
    public static void main(String[] args) {

        System.out.println("=== FILE INPUT/OUTPUT ===\n");

        // ============================================================
        // 1. FILE CLASS BASICS
        // ============================================================

        System.out.println("--- File Class ---");

        File file = new File("example.txt");

        // Check if file exists
        if (file.exists()) {
            System.out.println("File exists: " + file.getName());
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("Size: " + file.length() + " bytes");
            System.out.println("Can read: " + file.canRead());
            System.out.println("Can write: " + file.canWrite());
        } else {
            System.out.println("File does not exist");
        }

        System.out.println();


        // ============================================================
        // 2. WRITING TO FILE - FileWriter
        // ============================================================

        System.out.println("--- Writing with FileWriter ---");

        try {
            FileWriter writer = new FileWriter("output1.txt");
            writer.write("Hello, World!\n");
            writer.write("This is a test file.\n");
            writer.write("Java File I/O is powerful!\n");
            writer.close();
            System.out.println("✓ File written successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 3. WRITING TO FILE - Try-with-resources (RECOMMENDED)
        // ============================================================

        System.out.println("--- Try-with-resources ---");

        // Automatically closes the writer
        try (FileWriter writer = new FileWriter("output2.txt")) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3\n");
            System.out.println("✓ File written with try-with-resources!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 4. APPENDING TO FILE
        // ============================================================

        System.out.println("--- Appending to File ---");

        try (FileWriter writer = new FileWriter("output2.txt", true)) { // true = append mode
            writer.write("Line 4 (appended)\n");
            writer.write("Line 5 (appended)\n");
            System.out.println("✓ Content appended!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 5. BUFFERED WRITER (MORE EFFICIENT)
        // ============================================================

        System.out.println("--- BufferedWriter ---");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"))) {
            writer.write("First line");
            writer.newLine();  // Platform-independent newline
            writer.write("Second line");
            writer.newLine();
            writer.write("Third line");
            System.out.println("✓ File written with BufferedWriter!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 6. PRINTWRITER (EASY FORMATTED WRITING)
        // ============================================================

        System.out.println("--- PrintWriter ---");

        try (PrintWriter writer = new PrintWriter("output4.txt")) {
            writer.println("Name: John Doe");
            writer.println("Age: 30");
            writer.printf("Balance: $%.2f%n", 1234.56);
            writer.println("Active: true");
            System.out.println("✓ File written with PrintWriter!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 7. READING FROM FILE - FileReader
        // ============================================================

        System.out.println("--- Reading with FileReader ---");

        try (FileReader reader = new FileReader("output1.txt")) {
            int character;
            System.out.print("Content: ");
            while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 8. READING FROM FILE - BufferedReader (RECOMMENDED)
        // ============================================================

        System.out.println("--- Reading with BufferedReader ---");

        try (BufferedReader reader = new BufferedReader(new FileReader("output2.txt"))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber + ": " + line);
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 9. READING FROM FILE - Scanner
        // ============================================================

        System.out.println("--- Reading with Scanner ---");

        try (Scanner scanner = new Scanner(new File("output3.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println("→ " + line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 10. MODERN FILE I/O (Java NIO)
        // ============================================================

        System.out.println("--- Java NIO Files ---");

        try {
            // Write to file
            String content = "Line 1\nLine 2\nLine 3";
            Files.writeString(Paths.get("nio_output.txt"), content);
            System.out.println("✓ Written with Files.writeString()");

            // Read from file
            String readContent = Files.readString(Paths.get("nio_output.txt"));
            System.out.println("Content:\n" + readContent);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 11. PRACTICAL EXAMPLE: STUDENT DATA
        // ============================================================

        System.out.println("--- Student Data Example ---");

        // Write student data
        try (PrintWriter writer = new PrintWriter("students.txt")) {
            writer.println("Alice,20,3.8");
            writer.println("Bob,22,3.5");
            writer.println("Charlie,21,3.9");
            System.out.println("✓ Student data written!");
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Read and parse student data
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                double gpa = Double.parseDouble(parts[2]);
                students.add(new Student(name, age, gpa));
            }
            System.out.println("✓ Student data loaded!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display students
        System.out.println("\nStudents:");
        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println();


        // ============================================================
        // 12. FILE OPERATIONS
        // ============================================================

        System.out.println("--- File Operations ---");

        File testFile = new File("test_file.txt");

        try {
            // Create file
            if (testFile.createNewFile()) {
                System.out.println("✓ File created: " + testFile.getName());
            } else {
                System.out.println("File already exists");
            }

            // Write to it
            try (FileWriter writer = new FileWriter(testFile)) {
                writer.write("Test content");
            }

            // Get file info
            System.out.println("File name: " + testFile.getName());
            System.out.println("Absolute path: " + testFile.getAbsolutePath());
            System.out.println("Size: " + testFile.length() + " bytes");

            // Delete file
            if (testFile.delete()) {
                System.out.println("✓ File deleted");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 13. DIRECTORY OPERATIONS
        // ============================================================

        System.out.println("--- Directory Operations ---");

        File dir = new File("test_directory");

        // Create directory
        if (dir.mkdir()) {
            System.out.println("✓ Directory created: " + dir.getName());
        }

        // Check if directory
        System.out.println("Is directory: " + dir.isDirectory());

        // List files in directory
        File currentDir = new File(".");
        String[] files = currentDir.list();
        if (files != null) {
            System.out.println("\nFiles in current directory:");
            int count = 0;
            for (String filename : files) {
                System.out.println("- " + filename);
                count++;
                if (count >= 10) {  // Limit output
                    System.out.println("... and more");
                    break;
                }
            }
        }

        // Delete directory
        if (dir.delete()) {
            System.out.println("\n✓ Directory deleted");
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * WRITING TO FILE:
         * 1. FileWriter - Basic character writing
         * 2. BufferedWriter - Efficient writing with buffer
         * 3. PrintWriter - Easy formatted writing (println, printf)
         * 4. Files.writeString() - Modern, simple (Java 11+)
         *
         * READING FROM FILE:
         * 1. FileReader - Basic character reading
         * 2. BufferedReader - Efficient line-by-line reading
         * 3. Scanner - Parse file content
         * 4. Files.readString() - Modern, simple (Java 11+)
         *
         * BEST PRACTICES:
         * - Always use try-with-resources
         * - Handle IOException
         * - Close resources
         * - Use BufferedReader/Writer for large files
         * - Check file exists before reading
         * - Use absolute paths or be aware of working directory
         *
         * FILE OPERATIONS:
         * - file.exists() - Check if exists
         * - file.createNewFile() - Create file
         * - file.delete() - Delete file
         * - file.length() - Get file size
         * - file.getName() - Get filename
         * - file.getAbsolutePath() - Get full path
         * - file.canRead() - Check read permission
         * - file.canWrite() - Check write permission
         * - file.isDirectory() - Check if directory
         * - dir.mkdir() - Create directory
         * - dir.list() - List directory contents
         *
         * COMMON PATTERNS:
         * - CSV files: Write/read with comma separation
         * - Configuration files: Store key=value pairs
         * - Log files: Append mode for logging
         * - Data persistence: Save/load application data
         */
    }
}


// ============================================================
// HELPER CLASS
// ============================================================

class Student {
    private String name;
    private int age;
    private double gpa;

    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return name + " (Age: " + age + ", GPA: " + gpa + ")";
    }
}
