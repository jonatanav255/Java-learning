/*
 * HOW JAVA WORKS: Understanding .java vs .class files
 *
 * ============================================================
 * WHY DOES JAVA CREATE .CLASS FILES?
 * ============================================================
 *
 * Java is designed with the principle: "Write Once, Run Anywhere"
 *
 * THE TWO-STEP PROCESS:
 *
 * Step 1: COMPILATION (happens once)
 * -----------------------------------
 * Source Code (.java)  →  [javac compiler]  →  Bytecode (.class)
 *
 * - YOU write human-readable code in .java files
 * - The compiler (javac) translates it into BYTECODE
 * - Bytecode is stored in .class files
 * - Bytecode is NOT human-readable
 * - Bytecode is NOT machine code (not specific to any CPU)
 *
 *
 * Step 2: EXECUTION (happens every time you run the program)
 * -----------------------------------------------------------
 * Bytecode (.class)  →  [JVM]  →  Runs on ANY computer
 *
 * - The JVM (Java Virtual Machine) reads the .class file
 * - JVM translates bytecode into actual machine code for YOUR computer
 * - This happens at runtime (when you type: java HelloWorld)
 *
 *
 * ============================================================
 * WHY THIS APPROACH?
 * ============================================================
 *
 * 1. PLATFORM INDEPENDENCE
 *    - You compile ONCE on any computer
 *    - The .class file works on Windows, Mac, Linux, etc.
 *    - As long as there's a JVM, your code runs
 *    - You DON'T need to recompile for each operating system
 *
 * 2. SECURITY
 *    - JVM can verify bytecode before running it
 *    - Protects against malicious code
 *
 * 3. OPTIMIZATION
 *    - JVM can optimize code for YOUR specific machine at runtime
 *    - Can make code run faster over time
 *
 * 4. SEPARATION OF CONCERNS
 *    - You can distribute .class files without source code
 *    - Protects intellectual property
 *
 *
 * ============================================================
 * COMPARISON WITH OTHER LANGUAGES:
 * ============================================================
 *
 * C/C++ (Compiled directly to machine code):
 * -------------------------------------------
 * Source Code (.c) → Compiler → Machine Code (.exe)
 * - Fast execution
 * - BUT: must recompile for Windows, Mac, Linux separately
 * - Machine code is specific to CPU architecture
 *
 * Python/JavaScript (Interpreted):
 * --------------------------------
 * Source Code (.py) → Interpreter → Runs directly
 * - No compilation step
 * - More flexible, but generally slower
 * - Source code is distributed
 *
 * Java (Compiled to bytecode, then interpreted):
 * ----------------------------------------------
 * Source Code (.java) → Compiler → Bytecode (.class) → JVM → Runs
 * - Best of both worlds
 * - Platform independent
 * - Reasonably fast (JVM optimizes at runtime)
 *
 *
 * ============================================================
 * WHAT'S IN A .CLASS FILE?
 * ============================================================
 *
 * - Magic number (identifies it as Java bytecode)
 * - Version information
 * - Constant pool (strings, numbers, class names)
 * - Class structure information
 * - Methods in bytecode format
 * - Attributes and metadata
 *
 *
 * ============================================================
 * PRACTICAL EXAMPLE:
 * ============================================================
 *
 * When you type:  javac HelloWorld.java
 * Result: Creates HelloWorld.class (bytecode)
 *
 * When you type:  java HelloWorld
 * The JVM:
 *   1. Loads HelloWorld.class
 *   2. Verifies the bytecode is safe
 *   3. Interprets/compiles it to machine code
 *   4. Executes the main method
 *   5. Your program runs!
 *
 *
 * ============================================================
 * KEY TAKEAWAY:
 * ============================================================
 *
 * .java files = What YOU write (human-readable source code)
 * .class files = What the COMPUTER runs (bytecode)
 *
 * You NEED the .class file to run your program.
 * The JVM cannot run .java files directly.
 *
 */

public class Lesson3_HowJavaWorks {
    public static void main(String[] args) {
        System.out.println("This explanation is in the comments above!");
        System.out.println("\nThink of it like this:");
        System.out.println("- .java file = Recipe (human-readable)");
        System.out.println("- .class file = Pre-packaged meal (ready for the JVM)");
        System.out.println("- JVM = Microwave (heats it up and makes it run)");
    }
}
