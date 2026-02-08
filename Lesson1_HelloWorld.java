// This is a single-line comment. Comments are ignored by the compiler.
// They help explain the code to humans.

/*
 * This is a multi-line comment.
 * You can write multiple lines of explanation here.
 * Java programs are saved in files with .java extension
 * The filename MUST match the public class name (Lesson1_HelloWorld.java = class Lesson1_HelloWorld)
 */

// 'public' means this class can be accessed from anywhere
// 'class' is the keyword to define a class
// 'Lesson1_HelloWorld' is the name of our class
public class Lesson1_HelloWorld {

    // This is the main method - the entry point of every Java program
    // When you run a Java program, execution starts here

    // 'public' - can be called from outside the class
    // 'static' - belongs to the class itself, not to objects of the class
    // 'void' - this method doesn't return any value
    // 'main' - special name that Java looks for to start the program
    // 'String[] args' - array of strings for command-line arguments
    public static void main(String[] args) {

        // System.out.println() prints text to the console and adds a new line
        // 'System' is a built-in class
        // 'out' is the output stream
        // 'println' means "print line"
        // Text in double quotes is called a String
        System.out.println("Hello, World!");

        // Every statement in Java ends with a semicolon ;
    }

    // Curly braces { } define blocks of code
    // They mark the beginning and end of classes, methods, etc.
}
