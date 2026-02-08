/*
 * LESSON 42: LOGGING FRAMEWORKS
 *
 * Proper logging is essential for debugging, monitoring, and troubleshooting
 * production applications. This lesson covers logging best practices.
 *
 * KEY CONCEPTS:
 * - Logging: Recording application events
 * - Log Levels: TRACE, DEBUG, INFO, WARN, ERROR
 * - Appenders: Where logs go (console, file, database)
 * - Formatters: How logs are formatted
 * - SLF4J: Simple Logging Facade (abstraction)
 * - Structured Logging: Machine-readable logs
 *
 * LOGGING FRAMEWORKS:
 * 1. java.util.logging (JUL) - Built-in, basic
 * 2. Log4j 2 - Popular, feature-rich
 * 3. Logback - Modern, fast
 * 4. SLF4J - Facade (use with Logback/Log4j)
 * 5. Apache Commons Logging - Legacy
 *
 * LOG LEVELS (in order):
 * - TRACE: Very detailed, diagnostic
 * - DEBUG: Detailed debugging info
 * - INFO: Informational messages
 * - WARN: Warning, potential issues
 * - ERROR: Error events
 * - FATAL: Critical errors (Log4j)
 *
 * WHY USE LOGGING FRAMEWORKS?
 * - Configurable without code changes
 * - Multiple outputs (file, console, network)
 * - Flexible formatting
 * - Performance (async logging)
 * - Log rotation
 * - Filtering
 *
 * BEST PRACTICES:
 * 1. Use SLF4J facade
 * 2. Log at appropriate level
 * 3. Use parameterized logging
 * 4. Don't log sensitive data
 * 5. Use structured logging
 * 6. Configure log rotation
 * 7. Monitor log volume
 * 8. Include context (user ID, request ID)
 * 9. Don't use System.out.println
 * 10. Handle exceptions properly
 *
 * DEPENDENCIES:
 * Maven (SLF4J + Logback):
 * <dependency>
 *     <groupId>org.slf4j</groupId>
 *     <artifactId>slf4j-api</artifactId>
 *     <version>2.0.9</version>
 * </dependency>
 * <dependency>
 *     <groupId>ch.qos.logback</groupId>
 *     <artifactId>logback-classic</artifactId>
 *     <version>1.4.11</version>
 * </dependency>
 *
 * NOTE: This lesson demonstrates logging concepts using java.util.logging
 * (built-in). For production, use SLF4J + Logback or Log4j 2.
 */

import java.util.logging.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class Lesson42_Logging {
    // Java Util Logging (built-in)
    private static final Logger logger = Logger.getLogger(Lesson42_Logging.class.getName());

    public static void main(String[] args) {

        System.out.println("=== LOGGING FRAMEWORKS ===\n");

        // ============================================================
        // 1. BASIC LOGGING (java.util.logging)
        // ============================================================

        System.out.println("--- Basic Logging ---");

        logger.severe("This is a SEVERE (ERROR) message");
        logger.warning("This is a WARNING message");
        logger.info("This is an INFO message");
        logger.config("This is a CONFIG message");
        logger.fine("This is a FINE (DEBUG) message");
        logger.finer("This is a FINER (TRACE) message");
        logger.finest("This is a FINEST (TRACE) message");

        System.out.println("\nBy default, only INFO and above are shown.");
        System.out.println();


        // ============================================================
        // 2. LOG LEVELS
        // ============================================================

        System.out.println("--- Log Levels ---");

        System.out.println("""
                LOG LEVELS (java.util.logging):
                - SEVERE   (ERROR)   - Serious failure
                - WARNING  (WARN)    - Potential problem
                - INFO     (INFO)    - Informational
                - CONFIG   (INFO)    - Configuration
                - FINE     (DEBUG)   - General debugging
                - FINER    (TRACE)   - Detailed tracing
                - FINEST   (TRACE)   - Very detailed

                SLF4J/Log4j/Logback:
                - ERROR    - Error events
                - WARN     - Warning conditions
                - INFO     - Informational messages
                - DEBUG    - Debug-level messages
                - TRACE    - Very detailed trace messages

                WHEN TO USE:
                ERROR:  Errors, exceptions, failures
                WARN:   Deprecated API usage, poor use of API, almost errors
                INFO:   Important business process finished, startup, shutdown
                DEBUG:  Detailed information for debugging
                TRACE:  Very detailed diagnostic information
                """);

        System.out.println();


        // ============================================================
        // 3. PARAMETERIZED LOGGING
        // ============================================================

        System.out.println("--- Parameterized Logging ---");

        String user = "Alice";
        int userId = 12345;

        // BAD: String concatenation (always evaluated)
        // logger.info("User logged in: " + user + " with ID: " + userId);

        // GOOD: Parameterized (only evaluated if logged)
        logger.log(Level.INFO, "User logged in: {0} with ID: {1}",
                new Object[]{user, userId});

        System.out.println("""

                WHY PARAMETERIZED LOGGING?
                1. Performance: String not built if log level disabled
                2. Readability: Cleaner code
                3. Consistency: Structured format

                SLF4J style (even better):
                logger.info("User {} logged in with ID {}", user, userId);
                """);

        System.out.println();


        // ============================================================
        // 4. EXCEPTION LOGGING
        // ============================================================

        System.out.println("--- Exception Logging ---");

        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Log exception with stack trace
            logger.log(Level.SEVERE, "Error in calculation", e);
        }

        System.out.println("""

                EXCEPTION LOGGING BEST PRACTICES:
                ✓ Log at appropriate level (usually ERROR)
                ✓ Include exception object (for stack trace)
                ✓ Add context in message
                ✓ Log once at the appropriate layer
                ✗ Don't log and rethrow (log spam)
                ✗ Don't swallow exceptions silently
                ✗ Don't just log getMessage()
                """);

        System.out.println();


        // ============================================================
        // 5. CUSTOM LOG CONFIGURATION
        // ============================================================

        System.out.println("--- Custom Log Configuration ---");

        try {
            // Create custom logger
            Logger customLogger = Logger.getLogger("CustomLogger");

            // Remove default handlers
            customLogger.setUseParentHandlers(false);

            // Create console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            customLogger.addHandler(consoleHandler);

            // Create file handler
            FileHandler fileHandler = new FileHandler("app.log", true); // Append mode
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            customLogger.addHandler(fileHandler);

            // Set logger level
            customLogger.setLevel(Level.ALL);

            // Test custom logger
            customLogger.info("This goes to console and file");
            customLogger.fine("This DEBUG message also logs");

            // Close handlers
            fileHandler.close();

            System.out.println("✓ Logged to console and app.log");

            // Clean up
            new File("app.log").delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 6. SLF4J EXAMPLES (CONCEPTUAL)
        // ============================================================

        System.out.println("--- SLF4J Usage (Conceptual) ---");

        System.out.println("""
                SLF4J (Simple Logging Facade for Java):

                import org.slf4j.Logger;
                import org.slf4j.LoggerFactory;

                public class MyService {
                    private static final Logger log =
                        LoggerFactory.getLogger(MyService.class);

                    public void doSomething() {
                        log.trace("Entering method");
                        log.debug("Debug information");
                        log.info("Service started");
                        log.warn("Potential issue");
                        log.error("Error occurred", exception);
                    }

                    // Parameterized logging
                    public void process(String user, int id) {
                        log.info("Processing user {} with id {}", user, id);
                    }

                    // With multiple params
                    public void logDetails(String a, String b, String c) {
                        log.debug("Details: {}, {}, {}", a, b, c);
                    }
                }

                WHY SLF4J?
                - Facade pattern (swap implementations)
                - Clean API
                - Parameterized logging
                - No vendor lock-in
                - Performance
                """);

        System.out.println();


        // ============================================================
        // 7. LOGBACK CONFIGURATION (XML)
        // ============================================================

        System.out.println("--- Logback Configuration (logback.xml) ---");

        System.out.println("""
                <configuration>
                    <!-- Console appender -->
                    <appender name="CONSOLE"
                              class="ch.qos.logback.core.ConsoleAppender">
                        <encoder>
                            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
                        </encoder>
                    </appender>

                    <!-- File appender -->
                    <appender name="FILE"
                              class="ch.qos.logback.core.rolling.RollingFileAppender">
                        <file>logs/app.log</file>
                        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                            <fileNamePattern>logs/app.%d{yyyy-MM-dd}.log</fileNamePattern>
                            <maxHistory>30</maxHistory>
                        </rollingPolicy>
                        <encoder>
                            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger - %msg%n</pattern>
                        </encoder>
                    </appender>

                    <!-- Async appender (better performance) -->
                    <appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
                        <appender-ref ref="FILE" />
                    </appender>

                    <!-- Root logger -->
                    <root level="INFO">
                        <appender-ref ref="CONSOLE" />
                        <appender-ref ref="ASYNC" />
                    </root>

                    <!-- Package-specific logging -->
                    <logger name="com.myapp.service" level="DEBUG" />
                    <logger name="org.springframework" level="WARN" />
                    <logger name="org.hibernate" level="ERROR" />
                </configuration>

                PATTERN TOKENS:
                %d{...}       - Date/time
                %thread       - Thread name
                %level        - Log level
                %logger{36}   - Logger name (max 36 chars)
                %msg          - Log message
                %n            - New line
                %exception    - Exception stack trace
                """);

        System.out.println();


        // ============================================================
        // 8. STRUCTURED LOGGING
        // ============================================================

        System.out.println("--- Structured Logging ---");

        System.out.println("""
                STRUCTURED LOGGING (JSON):

                Traditional:
                2024-01-15 10:30:45 INFO User Alice logged in

                Structured (JSON):
                {
                  "timestamp": "2024-01-15T10:30:45.123Z",
                  "level": "INFO",
                  "logger": "com.myapp.auth.AuthService",
                  "thread": "http-nio-8080-exec-1",
                  "message": "User logged in",
                  "userId": 12345,
                  "username": "alice",
                  "ip": "192.168.1.1",
                  "requestId": "abc-123-def"
                }

                BENEFITS:
                - Machine parseable
                - Easy to query (Elasticsearch, Splunk)
                - Indexable fields
                - Better analysis
                - Metrics extraction

                LIBRARIES:
                - Logstash Logback Encoder
                - Log4j2 JSON layout
                - SLF4J with JSON encoder

                EXAMPLE (Logback + Logstash):
                <dependency>
                    <groupId>net.logstash.logback</groupId>
                    <artifactId>logstash-logback-encoder</artifactId>
                </dependency>

                <encoder class="net.logstash.logback.encoder.LogstashEncoder">
                    <customFields>{"app":"my-service"}</customFields>
                </encoder>
                """);

        System.out.println();


        // ============================================================
        // 9. MDC (MAPPED DIAGNOSTIC CONTEXT)
        // ============================================================

        System.out.println("--- MDC (Mapped Diagnostic Context) ---");

        System.out.println("""
                MDC adds context to all logs in current thread:

                import org.slf4j.MDC;

                // Set context
                MDC.put("userId", "12345");
                MDC.put("requestId", UUID.randomUUID().toString());
                MDC.put("sessionId", "abc-xyz");

                try {
                    log.info("Processing request");  // Includes MDC data
                    service.process();
                    log.info("Request completed");   // Includes MDC data
                } finally {
                    MDC.clear();  // Always clear!
                }

                // In logback.xml pattern:
                <pattern>%X{requestId} %X{userId} - %msg%n</pattern>

                OUTPUT:
                abc123 user456 - Processing request
                abc123 user456 - Request completed

                USE CASES:
                - Request tracking
                - User identification
                - Session tracking
                - Trace IDs (distributed tracing)

                IMPORTANT:
                - Thread-local storage
                - Clear in finally block
                - Not inherited by child threads
                - Use with servlet filters
                """);

        System.out.println();


        // ============================================================
        // 10. LOGGING BEST PRACTICES
        // ============================================================

        System.out.println("--- Logging Best Practices ---");

        System.out.println("""
                1. USE APPROPRIATE LOG LEVELS
                   ERROR:  Production issues, exceptions
                   WARN:   Recoverable issues, deprecated usage
                   INFO:   Important business events
                   DEBUG:  Development debugging
                   TRACE:  Very detailed diagnostics

                2. LOG AT RIGHT PLACE
                   ✓ Service layer (business logic)
                   ✓ Integration points (external calls)
                   ✓ Exception handling
                   ✗ Getter/setter methods
                   ✗ Loops (unless necessary)

                3. USE PARAMETERIZED LOGGING
                   ✓ log.info("User {} logged in", username);
                   ✗ log.info("User " + username + " logged in");

                4. DON'T LOG SENSITIVE DATA
                   ✗ Passwords
                   ✗ Credit card numbers
                   ✗ Social security numbers
                   ✗ API keys
                   ✗ Personal data (GDPR)

                5. INCLUDE CONTEXT
                   ✓ User ID, request ID, session ID
                   ✓ Method names, class names
                   ✓ Relevant business data
                   ✓ Timing information

                6. LOG EXCEPTIONS PROPERLY
                   ✓ log.error("Failed to process order", exception);
                   ✗ log.error(exception.getMessage());
                   ✗ exception.printStackTrace();

                7. USE ASYNC LOGGING FOR PERFORMANCE
                   - Non-blocking
                   - Better throughput
                   - Lower latency

                8. CONFIGURE LOG ROTATION
                   - Size-based: 10MB per file
                   - Time-based: Daily rotation
                   - Keep 30 days
                   - Compress old logs

                9. MONITOR LOG VOLUME
                   - Too much: Performance impact
                   - Too little: Missing information
                   - Adjust levels per environment

                10. ENVIRONMENT-SPECIFIC CONFIGURATION
                    - Development: DEBUG level, console
                    - Staging: INFO level, file + console
                    - Production: WARN level, file + monitoring

                11. USE LOG AGGREGATION
                    - ELK Stack (Elasticsearch, Logstash, Kibana)
                    - Splunk
                    - Datadog
                    - New Relic
                    - CloudWatch (AWS)

                12. TESTING LOGS
                    - Use log capture in tests
                    - Verify critical logs exist
                    - Check log format
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * LOGGING FUNDAMENTALS:
         *
         * FRAMEWORKS:
         * 1. SLF4J - Facade (recommended)
         * 2. Logback - Implementation (fast, modern)
         * 3. Log4j 2 - Implementation (feature-rich)
         * 4. java.util.logging - Built-in (basic)
         *
         * RECOMMENDED STACK:
         * SLF4J (API) + Logback (Implementation)
         *
         * LOG LEVELS:
         * ERROR > WARN > INFO > DEBUG > TRACE
         *
         * CONFIGURATION:
         * - XML (Logback, Log4j2)
         * - Properties (Log4j2)
         * - Programmatic
         *
         * APPENDERS (OUTPUTS):
         * - Console
         * - File (with rotation)
         * - Database
         * - Network (syslog)
         * - Email
         * - Async wrapper
         *
         * PERFORMANCE:
         * - Use async logging
         * - Parameterized messages
         * - Appropriate log levels
         * - Conditional logging
         *
         * PRODUCTION:
         * - Structured logging (JSON)
         * - Log aggregation
         * - Monitoring & alerts
         * - Log rotation
         * - Retention policy
         *
         * SECURITY:
         * - Don't log sensitive data
         * - Sanitize user input
         * - Access control on logs
         * - Compliance (GDPR, etc.)
         *
         * COMMON MISTAKES:
         * - Using System.out.println
         * - Not using parameterized logging
         * - Logging in loops
         * - Logging sensitive data
         * - No log rotation
         * - Ignoring log levels
         *
         * TOOLS:
         * - ELK Stack: Log aggregation & analysis
         * - Kibana: Log visualization
         * - Grafana: Metrics & dashboards
         * - Sentry: Error tracking
         * - Datadog: APM & logging
         *
         * DISTRIBUTED TRACING:
         * - Zipkin
         * - Jaeger
         * - OpenTelemetry
         * - AWS X-Ray
         */
    }
}
