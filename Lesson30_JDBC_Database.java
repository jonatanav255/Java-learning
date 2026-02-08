/*
 * LESSON 30: JDBC & DATABASE CONNECTIVITY
 *
 * JDBC (Java Database Connectivity) is an API for connecting and executing queries
 * with databases. It provides methods for querying and updating data in a database.
 *
 * KEY CONCEPTS:
 * - Driver: Software that enables Java applications to interact with a database
 * - Connection: Session between Java app and database
 * - Statement: Object for executing SQL queries
 * - ResultSet: Data returned from a query
 * - PreparedStatement: Precompiled SQL statement (prevents SQL injection)
 * - Transaction: Group of operations that succeed or fail together
 *
 * JDBC DRIVERS (4 TYPES):
 * 1. Type 1: JDBC-ODBC Bridge (deprecated)
 * 2. Type 2: Native-API Driver (partly Java)
 * 3. Type 3: Network Protocol Driver (middleware)
 * 4. Type 4: Thin Driver (pure Java, most common)
 *
 * COMMON DATABASES:
 * - MySQL: mysql-connector-java
 * - PostgreSQL: postgresql
 * - Oracle: ojdbc
 * - SQLite: sqlite-jdbc
 * - H2: h2 (embedded, great for learning)
 *
 * JDBC WORKFLOW:
 * 1. Load/Register Driver
 * 2. Establish Connection
 * 3. Create Statement
 * 4. Execute Query
 * 5. Process ResultSet
 * 6. Close Resources
 *
 * SQL INJECTION PREVENTION:
 * - ALWAYS use PreparedStatement instead of Statement
 * - Never concatenate user input into SQL strings
 * - Validate and sanitize input
 *
 * BEST PRACTICES:
 * - Use try-with-resources for auto-closing
 * - Use connection pooling for production
 * - Handle exceptions properly
 * - Use PreparedStatement to prevent SQL injection
 * - Close resources in finally block or use try-with-resources
 */

import java.sql.*;
import java.util.*;

public class Lesson30_JDBC_Database {
    public static void main(String[] args) {

        System.out.println("=== JDBC & DATABASE CONNECTIVITY ===\n");

        // ============================================================
        // 1. H2 EMBEDDED DATABASE SETUP
        // ============================================================

        /*
         * NOTE: This example uses H2, an in-memory database perfect for learning.
         * To run this code, you need to add h2-x.x.x.jar to your classpath:
         *
         * Download from: https://h2database.com
         * Or use Maven:
         * <dependency>
         *     <groupId>com.h2database</groupId>
         *     <artifactId>h2</artifactId>
         *     <version>2.2.224</version>
         * </dependency>
         *
         * For MySQL: jdbc:mysql://localhost:3306/dbname
         * For PostgreSQL: jdbc:postgresql://localhost:5432/dbname
         * For SQLite: jdbc:sqlite:path/to/database.db
         */

        // H2 in-memory database URL
        String url = "jdbc:h2:mem:testdb";
        String user = "sa";
        String password = "";

        // ============================================================
        // 2. BASIC CONNECTION
        // ============================================================

        System.out.println("--- Basic Connection ---");

        try {
            // Load driver (optional in JDBC 4.0+)
            // Class.forName("org.h2.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✓ Connected to database");

            // Close connection
            conn.close();
            System.out.println("✓ Connection closed");

        } catch (SQLException e) {
            System.out.println("✗ Connection failed: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 3. CREATE TABLE
        // ============================================================

        System.out.println("--- Creating Table ---");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = """
                    CREATE TABLE employees (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        department VARCHAR(50),
                        salary DECIMAL(10, 2),
                        hire_date DATE
                    )
                    """;

            stmt.executeUpdate(createTableSQL);
            System.out.println("✓ Table 'employees' created");

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 4. INSERT DATA - UNSAFE (DON'T USE IN PRODUCTION)
        // ============================================================

        System.out.println("--- Insert (Unsafe Method) ---");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // WARNING: This is vulnerable to SQL injection!
            // NEVER use this in production
            String insertSQL = "INSERT INTO employees (name, department, salary, hire_date) " +
                    "VALUES ('Alice Johnson', 'Engineering', 75000.00, '2020-01-15')";

            int rowsInserted = stmt.executeUpdate(insertSQL);
            System.out.println("✓ Rows inserted: " + rowsInserted);

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 5. INSERT DATA - SAFE (PREPARED STATEMENT)
        // ============================================================

        System.out.println("--- Insert (Safe Method - PreparedStatement) ---");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String insertSQL = "INSERT INTO employees (name, department, salary, hire_date) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // Insert multiple employees
            String[][] employees = {
                    {"Bob Smith", "Sales", "65000.00", "2019-03-20"},
                    {"Carol White", "HR", "60000.00", "2021-07-01"},
                    {"David Brown", "Engineering", "80000.00", "2018-11-10"},
                    {"Eve Davis", "Marketing", "70000.00", "2022-02-14"}
            };

            for (String[] emp : employees) {
                pstmt.setString(1, emp[0]);
                pstmt.setString(2, emp[1]);
                pstmt.setBigDecimal(3, new java.math.BigDecimal(emp[2]));
                pstmt.setDate(4, java.sql.Date.valueOf(emp[3]));

                pstmt.executeUpdate();
            }

            pstmt.close();
            System.out.println("✓ " + employees.length + " employees inserted safely");

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 6. QUERY DATA (SELECT)
        // ============================================================

        System.out.println("--- Query All Employees ---");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            System.out.println("ID | Name              | Department    | Salary     | Hire Date");
            System.out.println("----------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                double salary = rs.getDouble("salary");
                java.sql.Date hireDate = rs.getDate("hire_date");

                System.out.printf("%-2d | %-17s | %-13s | $%-9.2f | %s%n",
                        id, name, dept, salary, hireDate);
            }

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 7. PARAMETERIZED QUERY
        // ============================================================

        System.out.println("--- Query by Department ---");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String querySQL = "SELECT * FROM employees WHERE department = ?";
            PreparedStatement pstmt = conn.prepareStatement(querySQL);
            pstmt.setString(1, "Engineering");

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Engineering employees:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name") +
                        " (Salary: $" + rs.getDouble("salary") + ")");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 8. UPDATE DATA
        // ============================================================

        System.out.println("--- Update Employee Salary ---");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String updateSQL = "UPDATE employees SET salary = ? WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateSQL);

            pstmt.setDouble(1, 85000.00);
            pstmt.setString(2, "David Brown");

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("✓ Rows updated: " + rowsUpdated);

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 9. DELETE DATA
        // ============================================================

        System.out.println("--- Delete Employee ---");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String deleteSQL = "DELETE FROM employees WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteSQL);

            pstmt.setInt(1, 1);

            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("✓ Rows deleted: " + rowsDeleted);

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 10. TRANSACTIONS
        // ============================================================

        System.out.println("--- Transaction Example ---");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);

            // Disable auto-commit
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO employees (name, department, salary, hire_date) VALUES (?, ?, ?, ?)");

            // Transaction: Add two employees
            pstmt.setString(1, "Frank Miller");
            pstmt.setString(2, "Finance");
            pstmt.setBigDecimal(3, new java.math.BigDecimal("72000"));
            pstmt.setDate(4, java.sql.Date.valueOf("2023-01-01"));
            pstmt.executeUpdate();

            pstmt.setString(1, "Grace Lee");
            pstmt.setString(2, "Operations");
            pstmt.setBigDecimal(3, new java.math.BigDecimal("68000"));
            pstmt.setDate(4, java.sql.Date.valueOf("2023-03-15"));
            pstmt.executeUpdate();

            // Commit transaction
            conn.commit();
            System.out.println("✓ Transaction committed successfully");

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("✓ Transaction rolled back");
                } catch (SQLException ex) {
                    System.out.println("✗ Rollback failed");
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println();


        // ============================================================
        // 11. BATCH OPERATIONS
        // ============================================================

        System.out.println("--- Batch Insert ---");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String batchSQL = "INSERT INTO employees (name, department, salary, hire_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(batchSQL);

            // Add multiple statements to batch
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(1, "Employee " + i);
                pstmt.setString(2, "Department " + (i % 3));
                pstmt.setDouble(3, 50000 + (i * 1000));
                pstmt.setDate(4, java.sql.Date.valueOf("2023-06-" + String.format("%02d", i)));
                pstmt.addBatch();
            }

            // Execute batch
            int[] results = pstmt.executeBatch();
            System.out.println("✓ Batch inserted " + results.length + " records");

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 12. METADATA
        // ============================================================

        System.out.println("--- Database Metadata ---");

        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Database: " + metaData.getDatabaseProductName());
            System.out.println("Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Driver: " + metaData.getDriverName());
            System.out.println("URL: " + metaData.getURL());

            // Get table info
            System.out.println("\nTables:");
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                System.out.println("- " + tables.getString("TABLE_NAME"));
            }
            tables.close();

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 13. PRACTICAL: EMPLOYEE DAO PATTERN
        // ============================================================

        System.out.println("--- DAO Pattern Example ---");

        try {
            EmployeeDAO dao = new EmployeeDAO(url, user, password);

            // Get all employees
            List<Employee> allEmployees = dao.getAllEmployees();
            System.out.println("Total employees: " + allEmployees.size());

            // Get by ID
            Employee emp = dao.getEmployeeById(2);
            if (emp != null) {
                System.out.println("Employee #2: " + emp.getName());
            }

            // Get by department
            List<Employee> engineers = dao.getEmployeesByDepartment("Engineering");
            System.out.println("Engineers: " + engineers.size());

        } catch (SQLException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * JDBC ARCHITECTURE:
         * Application → JDBC API → Driver Manager → JDBC Driver → Database
         *
         * KEY INTERFACES:
         * - Driver: Database driver
         * - Connection: Database connection
         * - Statement: Execute static SQL
         * - PreparedStatement: Execute parameterized SQL (PREFERRED)
         * - CallableStatement: Execute stored procedures
         * - ResultSet: Query results
         *
         * STATEMENT TYPES:
         * 1. Statement - Basic SQL execution (avoid for user input)
         * 2. PreparedStatement - Precompiled, prevents SQL injection (USE THIS)
         * 3. CallableStatement - For stored procedures
         *
         * TRANSACTION ISOLATION LEVELS:
         * - READ_UNCOMMITTED: Lowest isolation, best performance
         * - READ_COMMITTED: Default for most databases
         * - REPEATABLE_READ: Prevents dirty and non-repeatable reads
         * - SERIALIZABLE: Highest isolation, worst performance
         *
         * RESULT SET TYPES:
         * - TYPE_FORWARD_ONLY: Can only move forward (default)
         * - TYPE_SCROLL_INSENSITIVE: Scrollable, not sensitive to changes
         * - TYPE_SCROLL_SENSITIVE: Scrollable, sensitive to changes
         *
         * COMMON METHODS:
         * executeQuery(): For SELECT (returns ResultSet)
         * executeUpdate(): For INSERT/UPDATE/DELETE (returns int)
         * execute(): For any SQL (returns boolean)
         *
         * CONNECTION POOLING LIBRARIES:
         * - HikariCP (fastest, recommended)
         * - Apache DBCP
         * - C3P0
         * - Tomcat JDBC Pool
         *
         * BEST PRACTICES:
         * 1. Use try-with-resources
         * 2. Always use PreparedStatement
         * 3. Close resources properly
         * 4. Use connection pooling in production
         * 5. Handle exceptions appropriately
         * 6. Use transactions for multiple operations
         * 7. Avoid N+1 query problem
         * 8. Use batch operations for bulk inserts
         * 9. Set appropriate fetch size
         * 10. Never store passwords in code (use config)
         *
         * SQL INJECTION PREVENTION:
         * BAD:  "SELECT * FROM users WHERE id = " + userId
         * GOOD: "SELECT * FROM users WHERE id = ?" with PreparedStatement
         *
         * COMMON ERRORS:
         * - SQLException: SQL error
         * - SQLTimeoutException: Query timeout
         * - BatchUpdateException: Batch operation failed
         * - SQLSyntaxErrorException: Invalid SQL syntax
         *
         * ORMs (Object-Relational Mapping):
         * After learning JDBC, consider learning:
         * - Hibernate (most popular)
         * - JPA (Java Persistence API)
         * - MyBatis
         * - JOOQ
         *
         * TESTING:
         * - Use H2 for in-memory testing
         * - Use Testcontainers for integration tests
         * - Mock JDBC with Mockito for unit tests
         */
    }
}


// ============================================================
// EMPLOYEE MODEL
// ============================================================

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private java.sql.Date hireDate;

    public Employee(int id, String name, String department, double salary, java.sql.Date hireDate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public java.sql.Date getHireDate() { return hireDate; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', dept='%s', salary=$%.2f}",
                id, name, department, salary);
    }
}


// ============================================================
// DAO PATTERN (DATA ACCESS OBJECT)
// ============================================================

class EmployeeDAO {
    private String url;
    private String user;
    private String password;

    public EmployeeDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Get all employees
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }
        }

        return employees;
    }

    // Get employee by ID
    public Employee getEmployeeById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractEmployee(rs);
            }
        }

        return null;
    }

    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                employees.add(extractEmployee(rs));
            }
        }

        return employees;
    }

    // Helper method to extract Employee from ResultSet
    private Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getDouble("salary"),
                rs.getDate("hire_date")
        );
    }
}
