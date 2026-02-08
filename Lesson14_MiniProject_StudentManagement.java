/*
 * LESSON 14: MINI PROJECT - STUDENT MANAGEMENT SYSTEM
 *
 * This lesson combines ALL the concepts you've learned:
 * - Classes and Objects
 * - ArrayList
 * - Methods
 * - Conditionals and Loops
 * - User Input
 * - Exception Handling
 * - Strings
 *
 * This is a simple Student Management System where you can:
 * - Add students
 * - View all students
 * - Search for a student
 * - Calculate average GPA
 * - Remove students
 *
 * Study this code to see how everything works together!
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Lesson14_MiniProject_StudentManagement {

    // ArrayList to store all students
    private static ArrayList<StudentRecord> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1; // Auto-increment ID

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("  STUDENT MANAGEMENT SYSTEM");
        System.out.println("=======================================\n");

        // Add some sample data
        addSampleData();

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getMenuChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    displayStatistics();
                    break;
                case 6:
                    updateStudent();
                    break;
                case 7:
                    System.out.println("\nThank you for using Student Management System!");
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // ============================================================
    // MENU METHODS
    // ============================================================

    private static void displayMenu() {
        System.out.println("\n=======================================");
        System.out.println("MAIN MENU");
        System.out.println("=======================================");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by Name");
        System.out.println("4. Remove Student");
        System.out.println("5. Display Statistics");
        System.out.println("6. Update Student GPA");
        System.out.println("7. Exit");
        System.out.println("=======================================");
        System.out.print("Enter your choice (1-7): ");
    }

    private static int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }

    // ============================================================
    // STUDENT MANAGEMENT METHODS
    // ============================================================

    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");

        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty!");
                return;
            }

            System.out.print("Enter student age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (age < 5 || age > 100) {
                System.out.println("Error: Invalid age!");
                return;
            }

            System.out.print("Enter student major: ");
            String major = scanner.nextLine().trim();

            System.out.print("Enter GPA (0.0 - 4.0): ");
            double gpa = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            if (gpa < 0.0 || gpa > 4.0) {
                System.out.println("Error: GPA must be between 0.0 and 4.0!");
                return;
            }

            StudentRecord student = new StudentRecord(nextId++, name, age, major, gpa);
            students.add(student);

            System.out.println("\n✓ Student added successfully!");
            System.out.println("Student ID: " + student.getId());

        } catch (Exception e) {
            System.out.println("Error: Invalid input!");
            scanner.nextLine(); // Clear buffer
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        System.out.println("\nTotal Students: " + students.size());
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.printf("%-5s %-20s %-5s %-20s %-5s%n",
                "ID", "Name", "Age", "Major", "GPA");
        System.out.println("─────────────────────────────────────────────────────────────");

        for (StudentRecord student : students) {
            System.out.printf("%-5d %-20s %-5d %-20s %-5.2f%n",
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getMajor(),
                    student.getGpa());
        }
        System.out.println("─────────────────────────────────────────────────────────────");
    }

    private static void searchStudent() {
        System.out.println("\n--- SEARCH STUDENT ---");
        System.out.print("Enter student name to search: ");
        String searchName = scanner.nextLine().trim().toLowerCase();

        ArrayList<StudentRecord> results = new ArrayList<>();

        for (StudentRecord student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                results.add(student);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No students found with name containing: " + searchName);
        } else {
            System.out.println("\nFound " + results.size() + " student(s):");
            for (StudentRecord student : results) {
                student.displayInfo();
                System.out.println();
            }
        }
    }

    private static void removeStudent() {
        System.out.println("\n--- REMOVE STUDENT ---");

        if (students.isEmpty()) {
            System.out.println("No students to remove.");
            return;
        }

        System.out.print("Enter student ID to remove: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            StudentRecord toRemove = null;
            for (StudentRecord student : students) {
                if (student.getId() == id) {
                    toRemove = student;
                    break;
                }
            }

            if (toRemove != null) {
                System.out.println("\nStudent to be removed:");
                toRemove.displayInfo();

                System.out.print("\nAre you sure? (yes/no): ");
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("yes") || confirmation.equals("y")) {
                    students.remove(toRemove);
                    System.out.println("✓ Student removed successfully!");
                } else {
                    System.out.println("Removal cancelled.");
                }
            } else {
                System.out.println("Error: Student with ID " + id + " not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input!");
            scanner.nextLine();
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- UPDATE STUDENT GPA ---");

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        System.out.print("Enter student ID: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();

            StudentRecord student = null;
            for (StudentRecord s : students) {
                if (s.getId() == id) {
                    student = s;
                    break;
                }
            }

            if (student != null) {
                System.out.println("\nCurrent GPA: " + student.getGpa());
                System.out.print("Enter new GPA (0.0 - 4.0): ");
                double newGpa = scanner.nextDouble();
                scanner.nextLine();

                if (newGpa >= 0.0 && newGpa <= 4.0) {
                    student.setGpa(newGpa);
                    System.out.println("✓ GPA updated successfully!");
                } else {
                    System.out.println("Error: Invalid GPA!");
                }
            } else {
                System.out.println("Error: Student not found!");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input!");
            scanner.nextLine();
        }
    }

    private static void displayStatistics() {
        System.out.println("\n--- STATISTICS ---");

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        double totalGpa = 0;
        double highestGpa = students.get(0).getGpa();
        double lowestGpa = students.get(0).getGpa();
        StudentRecord topStudent = students.get(0);
        int totalAge = 0;

        for (StudentRecord student : students) {
            double gpa = student.getGpa();
            totalGpa += gpa;
            totalAge += student.getAge();

            if (gpa > highestGpa) {
                highestGpa = gpa;
                topStudent = student;
            }
            if (gpa < lowestGpa) {
                lowestGpa = gpa;
            }
        }

        double averageGpa = totalGpa / students.size();
        double averageAge = (double) totalAge / students.size();

        System.out.println("\nTotal Students: " + students.size());
        System.out.println("Average GPA: " + String.format("%.2f", averageGpa));
        System.out.println("Highest GPA: " + String.format("%.2f", highestGpa));
        System.out.println("Lowest GPA: " + String.format("%.2f", lowestGpa));
        System.out.println("Average Age: " + String.format("%.1f", averageAge));
        System.out.println("\nTop Student:");
        topStudent.displayInfo();
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private static void addSampleData() {
        students.add(new StudentRecord(nextId++, "Alice Johnson", 20, "Computer Science", 3.8));
        students.add(new StudentRecord(nextId++, "Bob Smith", 22, "Mathematics", 3.5));
        students.add(new StudentRecord(nextId++, "Charlie Brown", 21, "Physics", 3.9));
        students.add(new StudentRecord(nextId++, "Diana Prince", 23, "Engineering", 3.7));
    }
}


// ============================================================
// STUDENT RECORD CLASS
// ============================================================
class StudentRecord {
    private int id;
    private String name;
    private int age;
    private String major;
    private double gpa;

    // Constructor
    public StudentRecord(int id, String name, int age, String major, double gpa) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.major = major;
        this.gpa = gpa;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }

    public double getGpa() {
        return gpa;
    }

    // Setter for GPA (allows updates)
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    // Display student information
    public void displayInfo() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│ Student ID: " + id);
        System.out.println("│ Name: " + name);
        System.out.println("│ Age: " + age);
        System.out.println("│ Major: " + major);
        System.out.println("│ GPA: " + String.format("%.2f", gpa));
        System.out.println("└─────────────────────────────────────┘");
    }

    // Get grade based on GPA
    public String getGrade() {
        if (gpa >= 3.7) return "A";
        if (gpa >= 3.3) return "B+";
        if (gpa >= 3.0) return "B";
        if (gpa >= 2.7) return "C+";
        if (gpa >= 2.0) return "C";
        return "F";
    }

    @Override
    public String toString() {
        return "Student[ID=" + id + ", Name=" + name + ", GPA=" + gpa + "]";
    }
}
