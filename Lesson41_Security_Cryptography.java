/*
 * LESSON 41: SECURITY & CRYPTOGRAPHY
 *
 * Security is critical for production applications. This lesson covers
 * Java's security APIs, cryptography, and best practices.
 *
 * KEY CONCEPTS:
 * - Hashing: One-way transformation (passwords)
 * - Encryption: Two-way transformation (data protection)
 * - Digital Signatures: Verify authenticity
 * - SSL/TLS: Secure communication
 * - JWT: JSON Web Tokens for authentication
 * - Secure Random: Cryptographically secure randomness
 *
 * HASHING ALGORITHMS:
 * - MD5: BROKEN, don't use
 * - SHA-1: WEAK, don't use
 * - SHA-256: Good for general use
 * - SHA-512: More secure, slower
 * - bcrypt: Best for passwords (slow by design)
 * - PBKDF2: Good for passwords
 * - Argon2: Modern, recommended for passwords
 *
 * ENCRYPTION TYPES:
 * Symmetric (same key):
 * - AES: Industry standard (128, 192, 256 bit)
 * - DES: OBSOLETE, don't use
 * - 3DES: DEPRECATED
 *
 * Asymmetric (public/private key):
 * - RSA: Most common (2048+ bits)
 * - ECC: Elliptic Curve (smaller keys, same security)
 * - DSA: Digital Signature Algorithm
 *
 * JAVA SECURITY ARCHITECTURE:
 * - JCA: Java Cryptography Architecture
 * - JCE: Java Cryptography Extension
 * - JSSE: Java Secure Socket Extension
 * - JAAS: Java Authentication and Authorization Service
 *
 * COMMON USES:
 * - Password storage (hashing + salt)
 * - Data encryption (AES)
 * - Secure communication (TLS)
 * - API authentication (JWT, OAuth)
 * - Digital signatures
 * - Certificates
 *
 * SECURITY BEST PRACTICES:
 * 1. Never roll your own crypto
 * 2. Use established libraries
 * 3. Keep secrets out of code
 * 4. Use strong algorithms
 * 5. Salt passwords
 * 6. Use HTTPS everywhere
 * 7. Validate all input
 * 8. Keep libraries updated
 * 9. Use parameterized queries (SQL injection)
 * 10. Implement proper access control
 */

import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Lesson41_Security_Cryptography {
    public static void main(String[] args) {

        System.out.println("=== SECURITY & CRYPTOGRAPHY ===\n");

        // ============================================================
        // 1. HASHING (SHA-256)
        // ============================================================

        System.out.println("--- Hashing (SHA-256) ---");

        try {
            String data = "Hello, Security!";

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // Convert to hex
            String hashHex = bytesToHex(hash);
            System.out.println("Original: " + data);
            System.out.println("SHA-256 hash: " + hashHex);

            // Same input = same hash
            byte[] hash2 = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            System.out.println("Same input gives same hash: " + Arrays.equals(hash, hash2));

            // Different input = different hash
            byte[] hash3 = digest.digest("Different data".getBytes(StandardCharsets.UTF_8));
            System.out.println("Different input gives different hash: " + !Arrays.equals(hash, hash3));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 2. PASSWORD HASHING WITH SALT
        // ============================================================

        System.out.println("--- Password Hashing with Salt ---");

        try {
            String password = "mySecretPassword123";

            // Generate random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            System.out.println("Salt (hex): " + bytesToHex(salt));

            // Hash password with salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            System.out.println("Hashed password: " + bytesToHex(hashedPassword));

            // Verify password
            md.update(salt);
            byte[] testHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            boolean verified = Arrays.equals(hashedPassword, testHash);
            System.out.println("Password verified: " + verified);

            System.out.println("""

                    WHY SALT?
                    - Prevents rainbow table attacks
                    - Same password → different hashes (different salts)
                    - Store salt with hash in database
                    - Use unique salt per password

                    STORAGE:
                    database: hash + salt (both stored)
                    """);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 3. PBKDF2 (PASSWORD-BASED KEY DERIVATION)
        // ============================================================

        System.out.println("--- PBKDF2 (Recommended for Passwords) ---");

        try {
            String password = "myPassword123";
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            // PBKDF2 parameters
            int iterations = 65536; // Higher = more secure, slower
            int keyLength = 256; // bits

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    iterations,
                    keyLength
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            System.out.println("PBKDF2 hash: " + bytesToHex(hash));
            System.out.println("Iterations: " + iterations);
            System.out.println("Salt: " + bytesToHex(salt));

            System.out.println("""

                    PBKDF2 ADVANTAGES:
                    - Designed for passwords
                    - Configurable iterations (computational cost)
                    - Resistant to brute force
                    - Industry standard

                    RECOMMENDED SETTINGS:
                    - Iterations: 600,000+ (OWASP 2023)
                    - Key length: 256 bits
                    - Algorithm: PBKDF2WithHmacSHA256
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 4. SYMMETRIC ENCRYPTION (AES)
        // ============================================================

        System.out.println("--- Symmetric Encryption (AES) ---");

        try {
            String plaintext = "This is secret data";

            // Generate AES key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // AES-256
            SecretKey secretKey = keyGen.generateKey();

            // Create cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Initialize for encryption
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV(); // Initialization Vector
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            System.out.println("Original: " + plaintext);
            System.out.println("Encrypted (hex): " + bytesToHex(encrypted));
            System.out.println("IV (hex): " + bytesToHex(iv));

            // Decrypt
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decrypted = cipher.doFinal(encrypted);
            String decryptedText = new String(decrypted, StandardCharsets.UTF_8);

            System.out.println("Decrypted: " + decryptedText);
            System.out.println("Match: " + plaintext.equals(decryptedText));

            System.out.println("""

                    AES MODES:
                    - ECB: Don't use (insecure)
                    - CBC: Good (needs IV)
                    - GCM: Best (authenticated encryption)
                    - CTR: Counter mode

                    KEY SIZES:
                    - AES-128: Fast, sufficient for most
                    - AES-192: More secure
                    - AES-256: Maximum security

                    IMPORTANT:
                    - Never reuse IV with same key
                    - Store IV with ciphertext (not secret)
                    - Use authenticated encryption (GCM)
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 5. ASYMMETRIC ENCRYPTION (RSA)
        // ============================================================

        System.out.println("--- Asymmetric Encryption (RSA) ---");

        try {
            // Generate RSA key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); // 2048-bit key
            KeyPair keyPair = keyGen.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("Public key: " + bytesToHex(publicKey.getEncoded()).substring(0, 50) + "...");
            System.out.println("Private key: " + bytesToHex(privateKey.getEncoded()).substring(0, 50) + "...");

            // Encrypt with public key
            String message = "Secret Message";
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));

            System.out.println("Encrypted: " + bytesToHex(encrypted).substring(0, 50) + "...");

            // Decrypt with private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypted = cipher.doFinal(encrypted);
            String decryptedMessage = new String(decrypted, StandardCharsets.UTF_8);

            System.out.println("Decrypted: " + decryptedMessage);

            System.out.println("""

                    RSA CHARACTERISTICS:
                    - Public key: Share freely (encryption)
                    - Private key: Keep secret (decryption)
                    - Slow compared to AES
                    - Key size: 2048 bits minimum
                    - 4096 bits for high security

                    COMMON USE:
                    - Exchange symmetric keys
                    - Digital signatures
                    - SSL/TLS handshake

                    HYBRID ENCRYPTION:
                    1. Generate random AES key
                    2. Encrypt data with AES (fast)
                    3. Encrypt AES key with RSA (small)
                    4. Send both
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 6. DIGITAL SIGNATURES
        // ============================================================

        System.out.println("--- Digital Signatures ---");

        try {
            String message = "This is an authentic message";

            // Generate key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair keyPair = keyGen.generateKeyPair();

            // Sign with private key
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(keyPair.getPrivate());
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] digitalSignature = signature.sign();

            System.out.println("Message: " + message);
            System.out.println("Signature: " + bytesToHex(digitalSignature).substring(0, 50) + "...");

            // Verify with public key
            signature.initVerify(keyPair.getPublic());
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            boolean verified = signature.verify(digitalSignature);

            System.out.println("Signature verified: " + verified);

            // Tampered message won't verify
            signature.initVerify(keyPair.getPublic());
            signature.update("Tampered message".getBytes(StandardCharsets.UTF_8));
            boolean tamperedVerified = signature.verify(digitalSignature);

            System.out.println("Tampered message verified: " + tamperedVerified);

            System.out.println("""

                    DIGITAL SIGNATURES:
                    - Proves authenticity
                    - Ensures integrity
                    - Non-repudiation
                    - Sign with private key
                    - Verify with public key

                    USE CASES:
                    - Software distribution
                    - Legal documents
                    - Financial transactions
                    - API requests
                    - Code signing
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 7. SECURE RANDOM NUMBERS
        // ============================================================

        System.out.println("--- Secure Random Numbers ---");

        try {
            // DON'T use for crypto: Math.random()
            System.out.println("Math.random() (NOT for crypto): " + Math.random());

            // DON'T use for crypto: java.util.Random
            Random random = new Random();
            System.out.println("java.util.Random (NOT for crypto): " + random.nextInt());

            // DO use for crypto: SecureRandom
            SecureRandom secureRandom = new SecureRandom();
            System.out.println("SecureRandom (GOOD for crypto): " + secureRandom.nextInt());

            // Generate secure bytes
            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);
            System.out.println("Secure random bytes: " + bytesToHex(randomBytes).substring(0, 40) + "...");

            // Generate secure token
            String token = generateSecureToken(32);
            System.out.println("Secure token: " + token);

            System.out.println("""

                    WHY SecureRandom?
                    - Cryptographically secure
                    - Unpredictable
                    - Platform-specific (uses OS entropy)

                    USE FOR:
                    - Encryption keys
                    - Salts
                    - Session tokens
                    - API keys
                    - CSRF tokens

                    DON'T USE Math.random() or Random FOR SECURITY!
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        // ============================================================
        // 8. SECURE CODING PRACTICES
        // ============================================================

        System.out.println("--- Secure Coding Practices ---");

        System.out.println("""
                1. INPUT VALIDATION
                   - Whitelist over blacklist
                   - Validate type, length, format
                   - Sanitize all user input
                   - Use parameterized queries

                2. PASSWORD STORAGE
                   ✓ Use bcrypt, PBKDF2, or Argon2
                   ✓ Use unique salt per password
                   ✓ High iteration count
                   ✗ Plain text
                   ✗ MD5 or SHA-1
                   ✗ Reversible encryption

                3. ENCRYPTION
                   ✓ AES-256 for symmetric
                   ✓ RSA-2048+ for asymmetric
                   ✓ Use GCM mode
                   ✓ Generate keys properly
                   ✗ ECB mode
                   ✗ Hardcoded keys

                4. HTTPS/TLS
                   ✓ Always use HTTPS
                   ✓ TLS 1.2 or 1.3
                   ✓ Valid certificates
                   ✓ HSTS header
                   ✗ Self-signed certs in production
                   ✗ Mixed content

                5. AUTHENTICATION
                   ✓ Multi-factor authentication
                   ✓ Rate limiting
                   ✓ Account lockout
                   ✓ Secure session management
                   ✗ Predictable session IDs
                   ✗ No timeout

                6. AUTHORIZATION
                   ✓ Principle of least privilege
                   ✓ Role-based access control
                   ✓ Check permissions server-side
                   ✗ Client-side only checks
                   ✗ Trusting user input

                7. ERROR HANDLING
                   ✓ Generic error messages to users
                   ✓ Detailed logging server-side
                   ✗ Stack traces to users
                   ✗ Exposing system details

                8. DEPENDENCY MANAGEMENT
                   ✓ Keep libraries updated
                   ✓ Scan for vulnerabilities
                   ✓ Use dependency management
                   ✗ Outdated libraries
                   ✗ Unverified sources

                9. SQL INJECTION PREVENTION
                   ✓ Parameterized queries
                   ✓ Prepared statements
                   ✓ ORM frameworks
                   ✗ String concatenation
                   ✗ Dynamic SQL

                10. XSS PREVENTION
                    ✓ Output encoding
                    ✓ Content Security Policy
                    ✓ HTTPOnly cookies
                    ✗ Unescaped user content
                    ✗ innerHTML with user data
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * CRYPTOGRAPHY SUMMARY:
         *
         * HASHING (One-way):
         * - Passwords: PBKDF2, bcrypt, Argon2
         * - General: SHA-256, SHA-512
         * - Never: MD5, SHA-1
         *
         * ENCRYPTION (Two-way):
         * Symmetric:
         * - Use: AES-256 (GCM mode)
         * - Never: DES, 3DES, ECB mode
         *
         * Asymmetric:
         * - Use: RSA-2048+, ECC
         * - For: Key exchange, signatures
         *
         * RANDOM NUMBERS:
         * - SecureRandom for security
         * - Random for non-security
         * - Never Math.random() for security
         *
         * PASSWORDS:
         * - Hash with salt (unique per password)
         * - Use slow algorithms (PBKDF2, bcrypt)
         * - High iteration count (600,000+)
         * - Never store plain text
         * - Never use reversible encryption
         *
         * KEYS:
         * - Generate with proper randomness
         * - Store securely (key vaults)
         * - Rotate regularly
         * - Never hardcode
         * - Never commit to version control
         *
         * COMMON VULNERABILITIES:
         * - SQL Injection: Use prepared statements
         * - XSS: Escape output
         * - CSRF: Use tokens
         * - Broken Auth: Strong passwords, MFA
         * - Sensitive Data Exposure: Encrypt at rest
         * - XXE: Disable external entities
         * - Broken Access Control: Check permissions
         * - Security Misconfiguration: Harden systems
         * - Deserialization: Don't trust user data
         * - Insufficient Logging: Log security events
         *
         * OWASP TOP 10 (2021):
         * 1. Broken Access Control
         * 2. Cryptographic Failures
         * 3. Injection
         * 4. Insecure Design
         * 5. Security Misconfiguration
         * 6. Vulnerable Components
         * 7. Authentication Failures
         * 8. Software/Data Integrity
         * 9. Logging Failures
         * 10. Server-Side Request Forgery
         *
         * TOOLS:
         * - OWASP Dependency Check
         * - Snyk
         * - Sonar Qube
         * - Checkmarx
         * - Veracode
         *
         * REMEMBER:
         * - Security is not optional
         * - Defense in depth
         * - Assume breach mindset
         * - Keep learning
         * - Stay updated on vulnerabilities
         */
    }

    // Helper method: Convert bytes to hex string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Helper method: Generate secure token
    private static String generateSecureToken(int length) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
