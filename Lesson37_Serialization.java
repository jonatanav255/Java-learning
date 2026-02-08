/*
 * LESSON 37: SERIALIZATION & DESERIALIZATION
 *
 * Serialization is the process of converting objects into a byte stream
 * for storage or transmission. Deserialization is the reverse process.
 *
 * KEY CONCEPTS:
 * - Serialization: Object → Bytes
 * - Deserialization: Bytes → Object
 * - Persistent Storage: Save objects to disk
 * - Network Transfer: Send objects over network
 * - Deep Copy: Clone objects via serialization
 *
 * SERIALIZATION TYPES:
 * 1. Java Serialization (built-in, binary)
 * 2. JSON (human-readable, JavaScript Object Notation)
 * 3. XML (human-readable, Extensible Markup Language)
 * 4. Protocol Buffers (Google, efficient binary)
 * 5. MessagePack (compact binary)
 * 6. YAML (human-readable, configuration)
 *
 * JAVA SERIALIZATION:
 * - Implement Serializable interface (marker interface)
 * - Use ObjectOutputStream to serialize
 * - Use ObjectInputStream to deserialize
 * - serialVersionUID for version control
 * - transient keyword excludes fields
 * - Externalizable for custom control
 *
 * PROS & CONS:
 *
 * Java Serialization:
 * + Built-in, no dependencies
 * + Handles complex object graphs
 * + Type-safe
 * - Binary (not human-readable)
 * - Java-specific (not interoperable)
 * - Security concerns
 * - Larger size
 *
 * JSON:
 * + Human-readable
 * + Language-independent
 * + Web-friendly
 * + Smaller than XML
 * - Not type-safe without schema
 * - Slower than binary formats
 *
 * COMMON LIBRARIES:
 * - Jackson: JSON/XML (most popular)
 * - Gson: Google's JSON library
 * - JSON-B: Jakarta EE standard
 * - XStream: Object to XML
 * - Protocol Buffers: Google's binary format
 *
 * SECURITY CONCERNS:
 * - Deserialization vulnerabilities
 * - Never deserialize untrusted data
 * - Use input validation
 * - Consider alternatives to Java serialization
 *
 * USE CASES:
 * - REST APIs (JSON)
 * - Configuration files (JSON, YAML, XML)
 * - Caching (Java serialization)
 * - Message queues (Protocol Buffers)
 * - Session storage
 * - Deep cloning
 */

import java.io.*;
import java.util.*;
import java.time.*;

public class Lesson37_Serialization {
    public static void main(String[] args) {

        System.out.println("=== SERIALIZATION & DESERIALIZATION ===\n");

        // ============================================================
        // 1. JAVA SERIALIZATION BASICS
        // ============================================================

        System.out.println("--- Java Serialization ---");

        try {
            // Create object
            Person person = new Person("Alice", 30, "alice@example.com");
            System.out.println("Original: " + person);

            // Serialize to file
            String fileName = "person.ser";
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(person);
                System.out.println("✓ Object serialized to " + fileName);
            }

            // Deserialize from file
            try (FileInputStream fis = new FileInputStream(fileName);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {

                Person deserializedPerson = (Person) ois.readObject();
                System.out.println("Deserialized: " + deserializedPerson);
            }

            // Clean up
            new File(fileName).delete();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 2. TRANSIENT KEYWORD
        // ============================================================

        System.out.println("--- Transient Fields ---");

        try {
            User user = new User("bob", "secretPassword123");
            System.out.println("Original: " + user);

            // Serialize
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            oos.close();

            // Deserialize
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            User deserializedUser = (User) ois.readObject();
            ois.close();

            System.out.println("Deserialized: " + deserializedUser);
            System.out.println("Notice: password is null (transient)");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 3. SERIAL VERSION UID
        // ============================================================

        System.out.println("--- serialVersionUID ---");

        System.out.println("""
                serialVersionUID is a version identifier for serialized classes.

                WHY USE IT:
                - Ensures class compatibility during deserialization
                - Prevents InvalidClassException
                - Allows controlled evolution of classes

                EXAMPLE:
                class MyClass implements Serializable {
                    private static final long serialVersionUID = 1L;
                    // fields...
                }

                BEST PRACTICE:
                - Always declare serialVersionUID explicitly
                - Use 1L for initial version
                - Increment when making incompatible changes
                - Use IDE or serialver tool to generate

                WHAT HAPPENS WITHOUT IT:
                - JVM generates one based on class structure
                - Any change breaks deserialization
                - Hard to maintain compatibility
                """);

        System.out.println();


        // ============================================================
        // 4. CUSTOM SERIALIZATION
        // ============================================================

        System.out.println("--- Custom Serialization ---");

        try {
            CustomSerializable obj = new CustomSerializable("Important Data", "Computed Value");
            System.out.println("Original: " + obj);

            // Serialize
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            System.out.println("✓ Custom writeObject() called during serialization");

            // Deserialize
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            CustomSerializable deserialized = (CustomSerializable) ois.readObject();
            ois.close();

            System.out.println("Deserialized: " + deserialized);
            System.out.println("✓ Custom readObject() called during deserialization");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 5. SERIALIZATION OF COLLECTIONS
        // ============================================================

        System.out.println("--- Serializing Collections ---");

        try {
            List<String> list = new ArrayList<>(Arrays.asList("One", "Two", "Three"));
            Map<String, Integer> map = new HashMap<>();
            map.put("A", 1);
            map.put("B", 2);

            // Serialize collection
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(list);
            oos.writeObject(map);
            oos.close();

            System.out.println("✓ Collections serialized");

            // Deserialize
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);

            @SuppressWarnings("unchecked")
            List<String> deserializedList = (List<String>) ois.readObject();
            @SuppressWarnings("unchecked")
            Map<String, Integer> deserializedMap = (Map<String, Integer>) ois.readObject();
            ois.close();

            System.out.println("List: " + deserializedList);
            System.out.println("Map: " + deserializedMap);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 6. SIMPLE JSON SERIALIZATION (MANUAL)
        // ============================================================

        System.out.println("--- Simple JSON Serialization ---");

        Person person = new Person("Charlie", 25, "charlie@example.com");

        // Manual JSON creation (for demonstration)
        String json = SimpleJson.toJson(person);
        System.out.println("JSON: " + json);

        // Manual JSON parsing
        Person fromJson = SimpleJson.fromJson(json);
        System.out.println("From JSON: " + fromJson);

        System.out.println("""

                Real-world JSON libraries:
                1. Jackson (most popular):
                   ObjectMapper mapper = new ObjectMapper();
                   String json = mapper.writeValueAsString(object);
                   Person p = mapper.readValue(json, Person.class);

                2. Gson (Google):
                   Gson gson = new Gson();
                   String json = gson.toJson(object);
                   Person p = gson.fromJson(json, Person.class);

                3. JSON-B (Jakarta EE):
                   Jsonb jsonb = JsonbBuilder.create();
                   String json = jsonb.toJson(object);
                   Person p = jsonb.fromJson(json, Person.class);
                """);

        System.out.println();


        // ============================================================
        // 7. XML SERIALIZATION EXAMPLE
        // ============================================================

        System.out.println("--- XML Serialization (Manual) ---");

        String xml = SimpleXml.toXml(person);
        System.out.println("XML:\n" + xml);

        System.out.println("""
                Real-world XML libraries:
                1. JAXB (Jakarta XML Binding):
                   @XmlRootElement
                   class Person { }

                   JAXBContext context = JAXBContext.newInstance(Person.class);
                   Marshaller marshaller = context.createMarshaller();
                   marshaller.marshal(person, file);

                2. XStream:
                   XStream xstream = new XStream();
                   String xml = xstream.toXML(object);
                   Person p = (Person) xstream.fromXML(xml);

                3. Jackson (also supports XML):
                   XmlMapper mapper = new XmlMapper();
                   String xml = mapper.writeValueAsString(object);
                """);

        System.out.println();


        // ============================================================
        // 8. DEEP COPY VIA SERIALIZATION
        // ============================================================

        System.out.println("--- Deep Copy via Serialization ---");

        try {
            Person original = new Person("David", 35, "david@example.com");
            Person deepCopy = DeepCopy.copy(original);

            System.out.println("Original: " + original);
            System.out.println("Deep Copy: " + deepCopy);
            System.out.println("Same object? " + (original == deepCopy));
            System.out.println("Equal? " + original.equals(deepCopy));

            // Modify copy
            deepCopy.setName("David Jr.");
            System.out.println("\nAfter modifying copy:");
            System.out.println("Original: " + original);
            System.out.println("Deep Copy: " + deepCopy);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 9. SERIALIZATION BEST PRACTICES
        // ============================================================

        System.out.println("--- Best Practices ---");

        System.out.println("""
                JAVA SERIALIZATION:
                ✓ Always declare serialVersionUID
                ✓ Mark sensitive fields as transient
                ✓ Override writeObject/readObject for custom logic
                ✓ Be careful with inheritance
                ✓ Test deserialization thoroughly
                ✗ Don't serialize sensitive data
                ✗ Avoid for long-term storage
                ✗ Don't deserialize untrusted data

                JSON SERIALIZATION:
                ✓ Use for APIs and configuration
                ✓ Use for human-readable data
                ✓ Consider schema validation
                ✓ Handle null values properly
                ✓ Use appropriate libraries (Jackson, Gson)
                ✗ Not suitable for binary data
                ✗ Slower than binary formats
                ✗ Larger size than binary

                GENERAL:
                ✓ Choose format based on use case
                ✓ Version your data formats
                ✓ Handle backward compatibility
                ✓ Validate on deserialization
                ✓ Use compression for large data
                ✓ Consider security implications
                ✓ Test with different versions
                ✗ Don't expose internal structure
                ✗ Don't trust deserialized data
                """);

        System.out.println();


        // ============================================================
        // 10. FORMAT COMPARISON
        // ============================================================

        System.out.println("--- Format Comparison ---");

        System.out.println("""
                FORMAT              | SIZE | SPEED  | READABLE | USE CASE
                ────────────────────|────--|--------|──────────|──────────────────
                Java Serialization  | Large| Fast   | No       | Caching, Sessions
                JSON                | Medium| Medium| Yes      | APIs, Config
                XML                 | Large| Slow   | Yes      | Legacy, SOAP
                Protocol Buffers    | Small| Fast   | No       | Microservices
                MessagePack         | Small| Fast   | No       | High-performance
                YAML                | Medium| Slow   | Yes      | Configuration
                Avro                | Small| Fast   | No       | Big Data
                Thrift              | Small| Fast   | No       | RPC

                WHEN TO USE WHAT:
                - REST APIs: JSON
                - Configuration: JSON, YAML
                - Java-only caching: Java Serialization
                - Microservices: Protocol Buffers, JSON
                - Big Data: Avro, Parquet
                - Legacy systems: XML
                - High performance: Protocol Buffers, MessagePack
                - Human editing: JSON, YAML
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * SERIALIZATION FUNDAMENTALS:
         * - Converting objects to bytes and back
         * - Essential for persistence and network transfer
         * - Many formats available, choose based on needs
         * - Security is crucial (don't trust deserialized data)
         *
         * JAVA SERIALIZATION:
         * - Implement Serializable interface
         * - Use transient for non-serialized fields
         * - Always declare serialVersionUID
         * - Custom control via writeObject/readObject
         * - Externalizable for full control
         *
         * SERIALIZATION SECURITY:
         * - Major vulnerability: arbitrary code execution
         * - Never deserialize untrusted data
         * - Use ObjectInputFilter (Java 9+)
         * - Consider alternatives (JSON, Protocol Buffers)
         * - Validate all deserialized data
         *
         * JSON ADVANTAGES:
         * - Human-readable
         * - Language-independent
         * - Web ecosystem support
         * - Smaller than XML
         * - Easy debugging
         *
         * PERFORMANCE CONSIDERATIONS:
         * - Binary formats faster than text
         * - Protocol Buffers very efficient
         * - JSON good balance
         * - Compression helps with large data
         * - Consider caching serialized data
         *
         * VERSION CONTROL:
         * - Use serialVersionUID for Java
         * - Schema evolution for Protocol Buffers
         * - Backward compatibility crucial
         * - Test with old and new versions
         * - Document format changes
         *
         * COMMON ISSUES:
         * - InvalidClassException: Version mismatch
         * - NotSerializableException: Missing Serializable
         * - Security vulnerabilities
         * - Large object graphs
         * - Circular references
         *
         * LIBRARIES TO KNOW:
         * - Jackson: JSON/XML/YAML
         * - Gson: Google's JSON
         * - Protocol Buffers: Google's binary
         * - JAXB: XML binding
         * - XStream: Object to XML
         *
         * BEST PRACTICES:
         * 1. Choose appropriate format
         * 2. Version your schemas
         * 3. Handle compatibility
         * 4. Validate input
         * 5. Use established libraries
         * 6. Test thoroughly
         * 7. Consider security
         * 8. Document format
         * 9. Handle errors gracefully
         * 10. Monitor performance
         *
         * ALTERNATIVES TO JAVA SERIALIZATION:
         * - JSON (Jackson, Gson)
         * - Protocol Buffers
         * - Avro
         * - MessagePack
         * - Kryo
         * - FST
         */
    }
}


// ============================================================
// SERIALIZABLE PERSON CLASS
// ============================================================

class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, email='%s'}", name, age, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }
}


// ============================================================
// USER WITH TRANSIENT PASSWORD
// ============================================================

class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private transient String password; // Not serialized

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User{username='%s', password='%s'}", username, password);
    }
}


// ============================================================
// CUSTOM SERIALIZATION
// ============================================================

class CustomSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String data;
    private transient String computedValue;

    public CustomSerializable(String data, String computedValue) {
        this.data = data;
        this.computedValue = computedValue;
    }

    // Custom serialization
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject(); // Write non-transient fields
        // Custom logic here
        System.out.println("  [writeObject] Custom serialization logic executed");
    }

    // Custom deserialization
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject(); // Read non-transient fields
        // Custom logic here
        this.computedValue = "Recomputed Value"; // Recompute transient field
        System.out.println("  [readObject] Custom deserialization logic executed");
    }

    @Override
    public String toString() {
        return String.format("CustomSerializable{data='%s', computedValue='%s'}",
                data, computedValue);
    }
}


// ============================================================
// SIMPLE JSON UTILITY (BASIC IMPLEMENTATION)
// ============================================================

class SimpleJson {

    public static String toJson(Person person) {
        return String.format("{\"name\":\"%s\",\"age\":%d,\"email\":\"%s\"}",
                person.getName(), person.getAge(), person.getEmail());
    }

    public static Person fromJson(String json) {
        // Very basic parsing (not production-ready!)
        String name = extractValue(json, "name");
        int age = Integer.parseInt(extractValue(json, "age"));
        String email = extractValue(json, "email");
        return new Person(name, age, email);
    }

    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"?([^,}\"]+)\"?";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        return m.find() ? m.group(1) : "";
    }
}


// ============================================================
// SIMPLE XML UTILITY
// ============================================================

class SimpleXml {

    public static String toXml(Person person) {
        return String.format("""
                        <?xml version="1.0" encoding="UTF-8"?>
                        <person>
                            <name>%s</name>
                            <age>%d</age>
                            <email>%s</email>
                        </person>
                        """,
                person.getName(), person.getAge(), person.getEmail());
    }
}


// ============================================================
// DEEP COPY UTILITY
// ============================================================

class DeepCopy {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T copy(T object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        T copy = (T) ois.readObject();
        ois.close();

        return copy;
    }
}
