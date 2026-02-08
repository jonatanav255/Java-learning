/*
 * LESSON 31: NETWORKING & SOCKETS
 *
 * Java provides extensive networking capabilities through the java.net package.
 * You can create client-server applications, work with URLs, HTTP requests, and more.
 *
 * KEY CONCEPTS:
 * - Socket: Endpoint for communication between two machines
 * - ServerSocket: Listens for incoming client connections
 * - Port: Logical endpoint for network communication (0-65535)
 * - Protocol: Rules for communication (TCP, UDP, HTTP, etc.)
 * - IP Address: Unique identifier for network devices
 *
 * SOCKET TYPES:
 * 1. TCP Sockets (Socket, ServerSocket)
 *    - Connection-oriented, reliable, ordered
 *    - Used for: HTTP, FTP, email
 *
 * 2. UDP Sockets (DatagramSocket, DatagramPacket)
 *    - Connectionless, fast, unreliable
 *    - Used for: Video streaming, gaming, DNS
 *
 * COMMON PORTS:
 * - 20-21: FTP
 * - 22: SSH
 * - 23: Telnet
 * - 25: SMTP (email)
 * - 53: DNS
 * - 80: HTTP
 * - 443: HTTPS
 * - 3306: MySQL
 * - 5432: PostgreSQL
 * - 8080: Alternative HTTP
 *
 * NETWORKING CLASSES:
 * - InetAddress: Represents IP address
 * - URL: Represents Uniform Resource Locator
 * - URLConnection: Connection to URL
 * - HttpURLConnection: HTTP-specific connection
 * - Socket: Client-side TCP socket
 * - ServerSocket: Server-side TCP socket
 * - DatagramSocket: UDP socket
 *
 * USE CASES:
 * - Chat applications
 * - File transfer
 * - Web scraping
 * - REST API clients
 * - Game servers
 * - IoT communication
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class Lesson31_Networking {
    public static void main(String[] args) {

        System.out.println("=== NETWORKING & SOCKETS ===\n");

        // ============================================================
        // 1. INET ADDRESS
        // ============================================================

        System.out.println("--- InetAddress ---");

        try {
            // Get local host
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Local Host: " + localhost.getHostName());
            System.out.println("IP Address: " + localhost.getHostAddress());

            System.out.println();

            // Get by hostname
            InetAddress google = InetAddress.getByName("www.google.com");
            System.out.println("Google: " + google.getHostAddress());

            // Get all addresses
            InetAddress[] allAddresses = InetAddress.getAllByName("www.google.com");
            System.out.println("All Google IPs:");
            for (InetAddress addr : allAddresses) {
                System.out.println("  - " + addr.getHostAddress());
            }

        } catch (UnknownHostException e) {
            System.out.println("Host not found: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 2. URL PARSING
        // ============================================================

        System.out.println("--- URL Parsing ---");

        try {
            URL url = new URL("https://www.example.com:443/path/page.html?key=value#section");

            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Ref: " + url.getRef());
            System.out.println("Full URL: " + url.toString());

        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 3. READING FROM URL
        // ============================================================

        System.out.println("--- Reading from URL ---");

        try {
            URL url = new URL("https://www.example.com");
            URLConnection connection = url.openConnection();

            // Set user agent to avoid being blocked
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line;
            int lineCount = 0;
            System.out.println("First 5 lines from example.com:");
            while ((line = reader.readLine()) != null && lineCount < 5) {
                System.out.println(line);
                lineCount++;
            }
            System.out.println("...");

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading URL: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 4. HTTP URL CONNECTION
        // ============================================================

        System.out.println("--- HttpURLConnection ---");

        try {
            URL url = new URL("https://httpbin.org/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Java-Client");

            // Get response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();

                System.out.println("Response (first 300 chars):");
                System.out.println(response.substring(0, Math.min(300, response.length())));
                System.out.println("...");
            }

            conn.disconnect();

        } catch (IOException e) {
            System.out.println("HTTP request failed: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 5. POST REQUEST
        // ============================================================

        System.out.println("--- POST Request ---");

        try {
            URL url = new URL("https://httpbin.org/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configure POST request
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON data
            String jsonInput = "{\"name\": \"John\", \"age\": 30}";

            // Write to output stream
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = conn.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                System.out.println("Response (first 5 lines):");
                int count = 0;
                while ((line = in.readLine()) != null && count++ < 5) {
                    System.out.println(line);
                }
                System.out.println("...");
                in.close();
            }

            conn.disconnect();

        } catch (IOException e) {
            System.out.println("POST request failed: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 6. SIMPLE TCP SERVER (DEMO)
        // ============================================================

        System.out.println("--- TCP Server Demo ---");

        /*
         * The following demonstrates a simple TCP server.
         * To test it, uncomment and run in a separate thread:
         */

        System.out.println("Server example (commented out - see code):");
        System.out.println("""
                // Start server in background thread
                Thread serverThread = new Thread(() -> {
                    try (ServerSocket serverSocket = new ServerSocket(9999)) {
                        System.out.println("Server listening on port 9999");
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Client connected: " + clientSocket.getInetAddress());

                        BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        String message = in.readLine();
                        System.out.println("Received: " + message);
                        out.println("Echo: " + message);

                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                serverThread.start();
                """);

        System.out.println();


        // ============================================================
        // 7. SIMPLE TCP CLIENT (DEMO)
        // ============================================================

        System.out.println("--- TCP Client Demo ---");

        System.out.println("Client example (commented out - see code):");
        System.out.println("""
                // Connect to server
                try (Socket socket = new Socket("localhost", 9999)) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                    // Send message
                    out.println("Hello Server!");

                    // Receive response
                    String response = in.readLine();
                    System.out.println("Server response: " + response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                """);

        System.out.println();


        // ============================================================
        // 8. UDP SOCKET (DATAGRAM)
        // ============================================================

        System.out.println("--- UDP Socket Demo ---");

        System.out.println("UDP example (commented out - see code):");
        System.out.println("""
                // UDP Server
                DatagramSocket serverSocket = new DatagramSocket(9876);
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("UDP received: " + message);
                serverSocket.close();

                // UDP Client
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress address = InetAddress.getByName("localhost");
                byte[] sendData = "Hello UDP".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 9876);
                clientSocket.send(sendPacket);
                clientSocket.close();
                """);

        System.out.println();


        // ============================================================
        // 9. SOCKET OPTIONS
        // ============================================================

        System.out.println("--- Socket Options ---");

        try {
            Socket socket = new Socket();

            System.out.println("Default Socket Options:");
            System.out.println("  SO_TIMEOUT: " + socket.getSoTimeout());
            System.out.println("  TCP_NODELAY: " + socket.getTcpNoDelay());
            System.out.println("  SO_KEEPALIVE: " + socket.getKeepAlive());
            System.out.println("  SO_REUSEADDR: " + socket.getReuseAddress());

            // Set options
            socket.setSoTimeout(5000);  // 5 seconds timeout
            socket.setTcpNoDelay(true); // Disable Nagle's algorithm
            socket.setKeepAlive(true);   // Enable keep-alive

            System.out.println("\nModified Socket Options:");
            System.out.println("  SO_TIMEOUT: " + socket.getSoTimeout());
            System.out.println("  TCP_NODELAY: " + socket.getTcpNoDelay());
            System.out.println("  SO_KEEPALIVE: " + socket.getKeepAlive());

            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 10. NETWORK INTERFACE INFORMATION
        // ============================================================

        System.out.println("--- Network Interfaces ---");

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            System.out.println("Available network interfaces:");
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();

                if (ni.isUp()) {
                    System.out.println("\nInterface: " + ni.getName());
                    System.out.println("  Display Name: " + ni.getDisplayName());
                    System.out.println("  Loopback: " + ni.isLoopback());
                    System.out.println("  Virtual: " + ni.isVirtual());

                    // Get IP addresses
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        System.out.println("  IP: " + addr.getHostAddress());
                    }
                }
            }

        } catch (SocketException e) {
            System.out.println("Error getting network interfaces: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 11. PRACTICAL: SIMPLE HTTP CLIENT
        // ============================================================

        System.out.println("--- Simple HTTP Client ---");

        SimpleHttpClient client = new SimpleHttpClient();

        // GET request
        String getResponse = client.get("https://httpbin.org/get");
        if (getResponse != null) {
            System.out.println("GET Success (first 200 chars):");
            System.out.println(getResponse.substring(0, Math.min(200, getResponse.length())));
            System.out.println("...");
        }

        System.out.println();


        // ============================================================
        // 12. DOWNLOAD FILE FROM URL
        // ============================================================

        System.out.println("--- Download File ---");

        try {
            URL fileUrl = new URL("https://www.example.com");
            String fileName = "example.html";

            try (BufferedInputStream in = new BufferedInputStream(fileUrl.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }

                System.out.println("✓ File downloaded: " + fileName);

                // Clean up
                new File(fileName).delete();
                System.out.println("✓ Test file deleted");
            }

        } catch (IOException e) {
            System.out.println("Download failed: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * NETWORKING FUNDAMENTALS:
         * - Client-Server Architecture: Client requests, server responds
         * - Protocols: TCP (reliable), UDP (fast), HTTP (web)
         * - Sockets: Endpoints for bidirectional communication
         * - Ports: Virtual endpoints (1-65535, well-known: 0-1023)
         *
         * TCP vs UDP:
         * TCP: Connection-oriented, reliable, ordered, slower
         * UDP: Connectionless, fast, unreliable, unordered
         *
         * COMMON CLASSES:
         * - InetAddress: IP address representation
         * - URL: Uniform Resource Locator
         * - Socket: Client-side TCP socket
         * - ServerSocket: Server-side TCP socket
         * - DatagramSocket: UDP socket
         * - HttpURLConnection: HTTP client
         *
         * HTTP METHODS:
         * - GET: Retrieve data
         * - POST: Submit data
         * - PUT: Update data
         * - DELETE: Delete data
         * - HEAD: Get headers only
         * - OPTIONS: Get supported methods
         * - PATCH: Partial update
         *
         * HTTP STATUS CODES:
         * 1xx: Informational
         * 2xx: Success (200 OK, 201 Created)
         * 3xx: Redirection (301 Moved, 302 Found)
         * 4xx: Client Error (400 Bad Request, 404 Not Found, 401 Unauthorized)
         * 5xx: Server Error (500 Internal Error, 503 Unavailable)
         *
         * SOCKET OPTIONS:
         * - SO_TIMEOUT: Read timeout
         * - TCP_NODELAY: Disable Nagle's algorithm
         * - SO_KEEPALIVE: Keep connection alive
         * - SO_REUSEADDR: Reuse address
         * - SO_LINGER: Linger on close
         *
         * BEST PRACTICES:
         * 1. Always close sockets/connections
         * 2. Use try-with-resources
         * 3. Handle timeouts appropriately
         * 4. Set user-agent for HTTP requests
         * 5. Handle exceptions properly
         * 6. Use connection pooling for production
         * 7. Implement retry logic
         * 8. Use HTTPS for sensitive data
         * 9. Validate URLs and inputs
         * 10. Consider using modern libraries (OkHttp, Apache HttpClient)
         *
         * SECURITY:
         * - Always use HTTPS for sensitive data
         * - Validate SSL certificates
         * - Sanitize user input
         * - Implement authentication
         * - Use encryption for sensitive data
         * - Be aware of firewall and proxy settings
         *
         * MODERN ALTERNATIVES:
         * - Java 11+ HttpClient (built-in, modern)
         * - Apache HttpClient
         * - OkHttp
         * - Retrofit (REST client)
         * - Spring RestTemplate
         * - Spring WebClient (reactive)
         *
         * COMMON ISSUES:
         * - Connection timeout
         * - Read timeout
         * - Connection refused (server not running)
         * - Unknown host (DNS issue)
         * - Port already in use
         * - Firewall blocking
         *
         * MULTITHREADING:
         * - Use ExecutorService for handling multiple clients
         * - Each client connection should run in separate thread
         * - Use thread pool to limit concurrent connections
         * - Consider NIO for high-performance servers
         *
         * ADVANCED TOPICS:
         * - NIO (Non-blocking I/O)
         * - WebSockets
         * - HTTP/2
         * - gRPC
         * - REST APIs
         * - GraphQL
         */
    }
}


// ============================================================
// SIMPLE HTTP CLIENT CLASS
// ============================================================

class SimpleHttpClient {

    public String get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "SimpleHttpClient/1.0");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line).append("\n");
                }
                in.close();
                conn.disconnect();

                return response.toString();
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
                conn.disconnect();
                return null;
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String post(String urlString, String jsonData) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "SimpleHttpClient/1.0");
            conn.setDoOutput(true);

            // Write JSON data
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line).append("\n");
                }
                in.close();
                conn.disconnect();

                return response.toString();
            } else {
                System.out.println("POST request failed. Response Code: " + responseCode);
                conn.disconnect();
                return null;
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
