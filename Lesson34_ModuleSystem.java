/*
 * LESSON 34: JAVA MODULE SYSTEM (JPMS)
 *
 * The Java Platform Module System (JPMS), introduced in Java 9, provides
 * a way to organize code into modules for better encapsulation and maintainability.
 *
 * KEY CONCEPTS:
 * - Module: A self-contained unit of code with explicit dependencies
 * - module-info.java: Module descriptor file
 * - Exports: Makes packages available to other modules
 * - Requires: Declares dependencies on other modules
 * - Strong Encapsulation: Only exported packages are accessible
 *
 * MODULE BENEFITS:
 * 1. Strong Encapsulation: Hide implementation details
 * 2. Reliable Configuration: Explicit dependencies
 * 3. Smaller Runtime: Include only needed modules
 * 4. Improved Security: Better access control
 * 5. Better Performance: JVM optimizations
 *
 * MODULE DIRECTIVES:
 * - exports: Makes a package available to other modules
 * - requires: Declares dependency on another module
 * - requires transitive: Dependency available to consumers
 * - opens: Allows reflection access (for frameworks)
 * - uses: Declares service consumption
 * - provides...with: Declares service implementation
 *
 * STANDARD MODULES:
 * - java.base: Fundamental classes (String, Object, etc.)
 * - java.sql: JDBC API
 * - java.xml: XML processing
 * - java.logging: Logging framework
 * - java.desktop: GUI classes (AWT, Swing)
 *
 * MODULE PATH vs CLASSPATH:
 * - Module Path: For modular JARs (Java 9+)
 * - Classpath: For traditional JARs (pre-Java 9)
 * - Can mix both in applications
 *
 * MIGRATION STRATEGIES:
 * 1. Top-down: Start from application, move to dependencies
 * 2. Bottom-up: Start from libraries, move up
 * 3. Hybrid: Mix of both
 * 4. Unnamed Module: Run on module path without module-info
 * 5. Automatic Module: JAR on module path without module-info
 *
 * NOTE: This lesson demonstrates module concepts.
 * To create actual modules, you need:
 * 1. Separate directories for each module
 * 2. module-info.java in each module root
 * 3. Compile with --module-path
 * 4. Run with --module-path and -m
 */

import java.lang.module.*;
import java.util.*;

public class Lesson34_ModuleSystem {
    public static void main(String[] args) {

        System.out.println("=== JAVA MODULE SYSTEM (JPMS) ===\n");

        // ============================================================
        // 1. MODULE LAYER BASICS
        // ============================================================

        System.out.println("--- Module Layer ---");

        // Get the boot layer (where JDK modules live)
        ModuleLayer bootLayer = ModuleLayer.boot();
        System.out.println("Boot layer: " + bootLayer);

        // List all modules in boot layer
        System.out.println("\nSome JDK Modules (first 10):");
        bootLayer.modules().stream()
                .limit(10)
                .forEach(module -> System.out.println("  - " + module.getName()));

        System.out.println();


        // ============================================================
        // 2. MODULE INFORMATION
        // ============================================================

        System.out.println("--- Module Information ---");

        // Get information about java.base module
        Module javaBase = String.class.getModule();
        System.out.println("String is in module: " + javaBase.getName());

        ModuleDescriptor descriptor = javaBase.getDescriptor();
        System.out.println("Module: " + descriptor.name());
        System.out.println("Is automatic: " + descriptor.isAutomatic());
        System.out.println("Is open: " + descriptor.isOpen());

        // Exports (first 5)
        System.out.println("\nSome exported packages:");
        descriptor.exports().stream()
                .limit(5)
                .forEach(export -> System.out.println("  exports " + export.source()));

        // Requires (first 5)
        System.out.println("\nSome required modules:");
        descriptor.requires().stream()
                .limit(5)
                .forEach(require -> System.out.println("  requires " + require.name()));

        System.out.println();


        // ============================================================
        // 3. CURRENT MODULE
        // ============================================================

        System.out.println("--- Current Module ---");

        // This class's module (unnamed if not modular)
        Module currentModule = Lesson34_ModuleSystem.class.getModule();
        System.out.println("Current module name: " +
                (currentModule.isNamed() ? currentModule.getName() : "UNNAMED"));
        System.out.println("Is named: " + currentModule.isNamed());

        // Unnamed modules can read all modules
        System.out.println("Can read java.base: " + currentModule.canRead(javaBase));

        System.out.println();


        // ============================================================
        // 4. MODULE FINDER
        // ============================================================

        System.out.println("--- Module Finder ---");

        // Find modules in the module path
        ModuleFinder systemFinder = ModuleFinder.ofSystem();

        System.out.println("Some system modules (first 10):");
        systemFinder.findAll().stream()
                .limit(10)
                .forEach(moduleRef -> {
                    ModuleDescriptor desc = moduleRef.descriptor();
                    System.out.println("  - " + desc.name());
                });

        System.out.println();


        // ============================================================
        // 5. PACKAGE ACCESS
        // ============================================================

        System.out.println("--- Package Access ---");

        // Check if a package is exported
        Module sqlModule = java.sql.Connection.class.getModule();
        System.out.println("java.sql module: " + sqlModule.getName());

        // Check exports
        boolean exportsJavaSql = sqlModule.getDescriptor().exports().stream()
                .anyMatch(export -> export.source().equals("java.sql"));
        System.out.println("Exports java.sql package: " + exportsJavaSql);

        System.out.println();


        // ============================================================
        // 6. MODULE EXAMPLE STRUCTURE
        // ============================================================

        System.out.println("--- Example Module Structure ---");

        System.out.println("""
                Creating a simple module:

                my.module/
                ├── module-info.java
                └── com/
                    └── mycompany/
                        └── MyClass.java

                module-info.java content:
                ┌─────────────────────────────────────────────┐
                │ module my.module {                          │
                │     // Export package to all modules        │
                │     exports com.mycompany;                  │
                │                                             │
                │     // Export to specific modules only      │
                │     exports com.mycompany.internal          │
                │         to my.other.module;                 │
                │                                             │
                │     // Require other modules                │
                │     requires java.sql;                      │
                │     requires transitive java.logging;       │
                │                                             │
                │     // Open for reflection (frameworks)     │
                │     opens com.mycompany.entity;             │
                │                                             │
                │     // Services                             │
                │     uses com.mycompany.api.Service;         │
                │     provides com.mycompany.api.Service      │
                │         with com.mycompany.impl.ServiceImpl;│
                │ }                                           │
                └─────────────────────────────────────────────┘
                """);

        System.out.println();


        // ============================================================
        // 7. MODULE DIRECTIVES EXPLAINED
        // ============================================================

        System.out.println("--- Module Directives ---");

        System.out.println("""
                1. exports <package>
                   - Makes package accessible to all modules
                   - Example: exports com.mycompany.api;

                2. exports <package> to <module1>, <module2>
                   - Qualified export (only to specific modules)
                   - Example: exports com.internal to my.test.module;

                3. requires <module>
                   - Declares dependency on another module
                   - Example: requires java.sql;

                4. requires transitive <module>
                   - Dependency propagates to consumers
                   - Example: requires transitive java.logging;

                5. requires static <module>
                   - Optional dependency (compile-time only)
                   - Example: requires static lombok;

                6. opens <package>
                   - Allows reflection access at runtime
                   - Example: opens com.mycompany.entity;
                   - Needed for frameworks like Hibernate, Spring

                7. opens <package> to <module>
                   - Opens package to specific modules only
                   - Example: opens com.entity to hibernate.core;

                8. open module
                   - Opens entire module for reflection
                   - Example: open module my.module { }

                9. uses <service-interface>
                   - Declares service consumption
                   - Example: uses com.mycompany.api.PaymentService;

                10. provides <interface> with <implementation>
                    - Declares service provider
                    - Example: provides com.api.Service
                              with com.impl.ServiceImpl;
                """);

        System.out.println();


        // ============================================================
        // 8. COMPILATION & RUNNING
        // ============================================================

        System.out.println("--- Compiling & Running Modules ---");

        System.out.println("""
                Compile a module:
                ┌────────────────────────────────────────────────────────┐
                │ javac -d out/my.module \\                              │
                │       src/my.module/module-info.java \\                │
                │       src/my.module/com/mycompany/MyClass.java        │
                └────────────────────────────────────────────────────────┘

                Compile with dependencies:
                ┌────────────────────────────────────────────────────────┐
                │ javac --module-path mods \\                            │
                │       -d out/my.module \\                              │
                │       src/my.module/module-info.java \\                │
                │       src/my.module/com/mycompany/MyClass.java        │
                └────────────────────────────────────────────────────────┘

                Run a module:
                ┌────────────────────────────────────────────────────────┐
                │ java --module-path out \\                              │
                │      -m my.module/com.mycompany.Main                  │
                └────────────────────────────────────────────────────────┘

                Create modular JAR:
                ┌────────────────────────────────────────────────────────┐
                │ jar --create \\                                        │
                │     --file mods/my.module.jar \\                       │
                │     --main-class com.mycompany.Main \\                 │
                │     -C out/my.module .                                │
                └────────────────────────────────────────────────────────┘

                Run from modular JAR:
                ┌────────────────────────────────────────────────────────┐
                │ java --module-path mods -m my.module                  │
                └────────────────────────────────────────────────────────┘
                """);

        System.out.println();


        // ============================================================
        // 9. MIGRATION SCENARIOS
        // ============================================================

        System.out.println("--- Migration Scenarios ---");

        System.out.println("""
                SCENARIO 1: UNNAMED MODULE
                - Traditional JAR on classpath
                - No module-info.java
                - Can read all named modules
                - Cannot be required by named modules

                SCENARIO 2: AUTOMATIC MODULE
                - Traditional JAR on module path (not classpath)
                - No module-info.java
                - Module name derived from JAR filename
                - Exports all packages
                - Reads all other modules
                - Example: library-1.0.jar → module library

                SCENARIO 3: EXPLICIT MODULE
                - Has module-info.java
                - Explicitly declares exports and requires
                - Strong encapsulation enforced
                - Best practice for new code

                SCENARIO 4: MIXED CLASSPATH & MODULE PATH
                - Some JARs on classpath (unnamed module)
                - Some JARs on module path (named modules)
                - Allows gradual migration

                MIGRATION STEPS:
                1. Add module-info.java to root of source
                2. Declare requires for dependencies
                3. Declare exports for public API
                4. Fix compilation errors (illegal access)
                5. Use 'opens' for reflection if needed
                6. Test thoroughly
                7. Update build scripts (Maven/Gradle)
                """);

        System.out.println();


        // ============================================================
        // 10. JDEPS TOOL
        // ============================================================

        System.out.println("--- jdeps Tool ---");

        System.out.println("""
                jdeps - Java Dependency Analysis Tool

                Analyze dependencies:
                ┌────────────────────────────────────────────────────────┐
                │ jdeps myapp.jar                                       │
                └────────────────────────────────────────────────────────┘

                Generate module-info suggestions:
                ┌────────────────────────────────────────────────────────┐
                │ jdeps --generate-module-info out/ myapp.jar           │
                └────────────────────────────────────────────────────────┘

                Check module dependencies:
                ┌────────────────────────────────────────────────────────┐
                │ jdeps --module-path mods -m my.module                 │
                └────────────────────────────────────────────────────────┘

                Find JDK internal API usage:
                ┌────────────────────────────────────────────────────────┐
                │ jdeps --jdk-internals myapp.jar                       │
                └────────────────────────────────────────────────────────┘

                Summary mode:
                ┌────────────────────────────────────────────────────────┐
                │ jdeps -s myapp.jar                                    │
                └────────────────────────────────────────────────────────┘
                """);

        System.out.println();


        // ============================================================
        // 11. COMMON ISSUES & SOLUTIONS
        // ============================================================

        System.out.println("--- Common Issues ---");

        System.out.println("""
                ISSUE 1: IllegalAccessError
                Problem: Trying to access non-exported package
                Solution: Add 'opens' directive or export package

                ISSUE 2: Module not found
                Problem: Dependency not on module path
                Solution: Add JAR to module path with --module-path

                ISSUE 3: Split package
                Problem: Same package in multiple modules
                Solution: Reorganize code to avoid package conflicts

                ISSUE 4: Reflection not working
                Problem: Framework can't access private fields/methods
                Solution: Use 'opens' directive in module-info.java

                ISSUE 5: Transitive dependency issues
                Problem: Module B requires A, you need both
                Solution: Use 'requires transitive' in module A

                ISSUE 6: Circular dependencies
                Problem: Module A requires B, B requires A
                Solution: Refactor to break circular dependency

                JVM FLAGS FOR DEBUGGING:
                --show-module-resolution : Show module resolution
                --list-modules            : List observable modules
                --describe-module <name>  : Describe a module
                --add-exports             : Emergency export (avoid in production)
                --add-opens               : Emergency open (avoid in production)
                """);

        System.out.println();


        // ============================================================
        // 12. SERVICES (SPI)
        // ============================================================

        System.out.println("--- Service Provider Interface ---");

        System.out.println("""
                Service Provider Interface (SPI) Example:

                1. Define Service Interface:
                   package com.mycompany.api;
                   public interface PaymentService {
                       void processPayment(double amount);
                   }

                2. Provider Module (module-info.java):
                   module payment.provider {
                       requires payment.api;
                       provides com.mycompany.api.PaymentService
                           with com.provider.CreditCardPayment;
                   }

                3. Consumer Module (module-info.java):
                   module payment.consumer {
                       requires payment.api;
                       uses com.mycompany.api.PaymentService;
                   }

                4. Load Services:
                   ServiceLoader<PaymentService> loader =
                       ServiceLoader.load(PaymentService.class);

                   for (PaymentService service : loader) {
                       service.processPayment(100.0);
                   }

                Benefits:
                - Loose coupling
                - Plugin architecture
                - Runtime extensibility
                - Multiple implementations
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * MODULE SYSTEM BENEFITS:
         * 1. Strong Encapsulation: Hide implementation details
         * 2. Reliable Configuration: Explicit dependencies at compile time
         * 3. Reduced Footprint: Custom runtime images with jlink
         * 4. Improved Security: Better access control
         * 5. Scalability: Better for large codebases
         *
         * WHEN TO USE MODULES:
         * - Large applications with clear boundaries
         * - Libraries with public API and internal implementation
         * - When strong encapsulation is needed
         * - Multi-team projects
         * - Microservices architecture
         *
         * WHEN NOT TO USE MODULES:
         * - Small applications
         * - Legacy codebases (migration cost)
         * - Quick prototypes
         * - When dependencies don't support modules
         *
         * MODULE BEST PRACTICES:
         * 1. One module = one responsibility
         * 2. Export only public API packages
         * 3. Keep module-info.java simple and clean
         * 4. Use 'requires transitive' sparingly
         * 5. Avoid split packages
         * 6. Document exported API thoroughly
         * 7. Use jdeps to analyze dependencies
         * 8. Test modules independently
         * 9. Version modules consistently
         * 10. Use semantic versioning
         *
         * JLINK - CUSTOM RUNTIME:
         * Create custom JRE with only needed modules:
         * jlink --module-path mods:$JAVA_HOME/jmods \
         *       --add-modules my.module \
         *       --output custom-jre
         *
         * Benefits:
         * - Smaller distribution size
         * - Faster startup
         * - Reduced attack surface
         * - No unused code
         *
         * MIGRATION CHECKLIST:
         * □ Run jdeps to analyze dependencies
         * □ Identify module boundaries
         * □ Create module-info.java
         * □ Add requires for dependencies
         * □ Add exports for public API
         * □ Fix compilation errors
         * □ Add opens for reflection
         * □ Update build tools (Maven/Gradle)
         * □ Test thoroughly
         * □ Update documentation
         *
         * JAVA 9+ MODULAR JDK:
         * The JDK itself is modularized into ~90 modules:
         * - java.base (core, always available)
         * - java.sql (JDBC)
         * - java.xml (XML)
         * - java.logging
         * - java.desktop (AWT/Swing)
         * - And many more...
         *
         * TOOLS:
         * - jdeps: Dependency analysis
         * - jlink: Custom runtime images
         * - jmod: Create JMOD files
         * - jar: Create modular JARs
         *
         * MAVEN/GRADLE SUPPORT:
         * Both build tools support modules via plugins:
         * - Maven: maven-compiler-plugin 3.6+
         * - Gradle: Native support in 6.4+
         *
         * RESOURCES:
         * - JEP 261: Module System
         * - JEP 376: ZGC: Concurrent Thread-Stack Processing
         * - https://openjdk.org/projects/jigsaw/
         */
    }
}
