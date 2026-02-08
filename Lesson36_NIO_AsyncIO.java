/*
 * LESSON 36: NIO (NEW I/O) & ASYNCHRONOUS I/O
 *
 * Java NIO (New I/O) provides high-performance, non-blocking I/O operations.
 * It's more scalable than traditional I/O for handling many concurrent connections.
 *
 * KEY CONCEPTS:
 * - Buffer: Container for data (like arrays but more powerful)
 * - Channel: Connection for I/O operations (like streams but bidirectional)
 * - Selector: Multiplexes multiple channels (handles many connections)
 * - Non-blocking I/O: Thread doesn't block waiting for I/O
 * - Direct Buffer: Outside JVM heap, faster for I/O
 *
 * NIO vs Traditional I/O:
 * Traditional I/O (java.io):
 *   - Stream-oriented (one byte at a time)
 *   - Blocking (thread waits)
 *   - One thread per connection
 *
 * NIO (java.nio):
 *   - Buffer-oriented (chunks of data)
 *   - Non-blocking (thread can do other work)
 *   - One thread handles many connections
 *
 * BUFFERS:
 * - ByteBuffer: Most commonly used
 * - CharBuffer, IntBuffer, LongBuffer, etc.
 * - FloatBuffer, DoubleBuffer, ShortBuffer
 *
 * BUFFER PROPERTIES:
 * - capacity: Maximum size
 * - position: Current read/write position
 * - limit: End of accessible data
 * - mark: Saved position for reset
 *
 * CHANNELS:
 * - FileChannel: File I/O
 * - SocketChannel: TCP client
 * - ServerSocketChannel: TCP server
 * - DatagramChannel: UDP
 * - Pipe: Inter-thread communication
 *
 * ASYNC I/O (NIO.2, Java 7+):
 * - AsynchronousFileChannel
 * - AsynchronousSocketChannel
 * - AsynchronousServerSocketChannel
 * - CompletionHandler for callbacks
 *
 * WHEN TO USE NIO:
 * ✓ Many concurrent connections
 * ✓ High-performance I/O
 * ✓ Large file transfers
 * ✓ Network servers
 * ✗ Simple file operations (use java.io)
 * ✗ Few connections
 */

import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Lesson36_NIO_AsyncIO {
    public static void main(String[] args) {

        System.out.println("=== NIO & ASYNCHRONOUS I/O ===\n");

        // ============================================================
        // 1. BUFFER BASICS
        // ============================================================

        System.out.println("--- Buffer Basics ---");

        // Create buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println("Initial state:");
        printBufferState(buffer);

        // Write data
        buffer.put((byte) 'H');
        buffer.put((byte) 'e');
        buffer.put((byte) 'l');
        buffer.put((byte) 'l');
        buffer.put((byte) 'o');

        System.out.println("\nAfter writing 'Hello':");
        printBufferState(buffer);

        // Flip to read mode
        buffer.flip();
        System.out.println("\nAfter flip():");
        printBufferState(buffer);

        // Read data
        System.out.print("Reading: ");
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        System.out.println();

        System.out.println("\nAfter reading all:");
        printBufferState(buffer);

        // Rewind to read again
        buffer.rewind();
        System.out.println("\nAfter rewind():");
        printBufferState(buffer);

        // Clear for new write
        buffer.clear();
        System.out.println("\nAfter clear():");
        printBufferState(buffer);

        System.out.println();


        // ============================================================
        // 2. BUFFER OPERATIONS
        // ============================================================

        System.out.println("--- Buffer Operations ---");

        ByteBuffer buf = ByteBuffer.allocate(20);

        // Bulk put
        buf.put("Java NIO".getBytes());
        System.out.println("After put: position=" + buf.position());

        // Mark position
        buf.mark();
        buf.put((byte) '!');
        buf.put((byte) '!');

        // Reset to mark
        buf.reset();
        System.out.println("After reset: position=" + buf.position());

        // Compact (for partial reads)
        buf.flip();
        buf.get(); // Read one byte
        buf.compact(); // Shift remaining to beginning
        System.out.println("After compact: position=" + buf.position());

        System.out.println();


        // ============================================================
        // 3. DIRECT vs HEAP BUFFERS
        // ============================================================

        System.out.println("--- Direct vs Heap Buffers ---");

        // Heap buffer (in JVM heap)
        ByteBuffer heapBuffer = ByteBuffer.allocate(1024);
        System.out.println("Heap buffer: " + !heapBuffer.isDirect());

        // Direct buffer (outside JVM heap, faster for I/O)
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println("Direct buffer: " + directBuffer.isDirect());

        System.out.println("""
                Direct buffers:
                + Faster for I/O operations
                + Better for large data transfers
                - More expensive to create/destroy
                - Not garbage collected normally

                Use direct buffers for:
                - Long-lived buffers
                - Heavy I/O operations
                - Network communication
                """);

        System.out.println();


        // ============================================================
        // 4. FILE CHANNEL - READING
        // ============================================================

        System.out.println("--- FileChannel Reading ---");

        try {
            // Create test file
            String testFileName = "nio_test.txt";
            Files.write(Paths.get(testFileName),
                    "Hello from NIO FileChannel!\nLine 2\nLine 3".getBytes());

            // Read using FileChannel
            try (RandomAccessFile file = new RandomAccessFile(testFileName, "r");
                 FileChannel channel = file.getChannel()) {

                ByteBuffer readBuffer = ByteBuffer.allocate(100);

                int bytesRead = channel.read(readBuffer);
                System.out.println("Bytes read: " + bytesRead);

                readBuffer.flip();
                byte[] data = new byte[readBuffer.remaining()];
                readBuffer.get(data);
                System.out.println("Content: " + new String(data));
            }

            // Clean up
            Files.deleteIfExists(Paths.get(testFileName));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 5. FILE CHANNEL - WRITING
        // ============================================================

        System.out.println("--- FileChannel Writing ---");

        try {
            String outFile = "nio_output.txt";

            try (RandomAccessFile file = new RandomAccessFile(outFile, "rw");
                 FileChannel channel = file.getChannel()) {

                String data = "Writing with FileChannel";
                ByteBuffer writeBuffer = ByteBuffer.wrap(data.getBytes());

                while (writeBuffer.hasRemaining()) {
                    channel.write(writeBuffer);
                }

                System.out.println("✓ Written to " + outFile);
            }

            // Verify
            String content = Files.readString(Paths.get(outFile));
            System.out.println("Content: " + content);

            // Clean up
            Files.deleteIfExists(Paths.get(outFile));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 6. FILE CHANNEL - TRANSFER
        // ============================================================

        System.out.println("--- FileChannel Transfer ---");

        try {
            // Create source file
            String sourceFile = "source.txt";
            String destFile = "destination.txt";
            Files.write(Paths.get(sourceFile), "This is the source content".getBytes());

            // Transfer using channels (zero-copy)
            try (FileChannel sourceChannel = FileChannel.open(Paths.get(sourceFile), StandardOpenOption.READ);
                 FileChannel destChannel = FileChannel.open(Paths.get(destFile),
                         StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

                long transferred = sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
                System.out.println("✓ Transferred " + transferred + " bytes");
            }

            // Verify
            String content = Files.readString(Paths.get(destFile));
            System.out.println("Destination content: " + content);

            // Clean up
            Files.deleteIfExists(Paths.get(sourceFile));
            Files.deleteIfExists(Paths.get(destFile));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 7. MEMORY-MAPPED FILES
        // ============================================================

        System.out.println("--- Memory-Mapped Files ---");

        try {
            String mmFile = "mmap.txt";

            try (RandomAccessFile file = new RandomAccessFile(mmFile, "rw");
                 FileChannel channel = file.getChannel()) {

                // Map file to memory
                MappedByteBuffer mappedBuffer = channel.map(
                        FileChannel.MapMode.READ_WRITE, 0, 100);

                // Write to mapped buffer
                mappedBuffer.put("Memory-mapped file content".getBytes());

                // Read from mapped buffer
                mappedBuffer.flip();
                byte[] data = new byte[mappedBuffer.remaining()];
                mappedBuffer.get(data);
                System.out.println("Content: " + new String(data));

                System.out.println("""
                        Memory-mapped files:
                        + Very fast for large files
                        + OS handles paging
                        - Uses more memory
                        - Platform-dependent behavior
                        """);
            }

            // Clean up
            Files.deleteIfExists(Paths.get(mmFile));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 8. ASYNCHRONOUS FILE I/O
        // ============================================================

        System.out.println("--- Asynchronous File I/O ---");

        try {
            Path asyncFile = Paths.get("async_test.txt");
            AsynchronousFileChannel asyncChannel = AsynchronousFileChannel.open(
                    asyncFile,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.READ);

            // Async write
            ByteBuffer writeBuffer = ByteBuffer.wrap("Async write content".getBytes());
            Future<Integer> writeFuture = asyncChannel.write(writeBuffer, 0);

            // Do other work while writing
            System.out.println("Writing asynchronously...");

            // Wait for completion
            int bytesWritten = writeFuture.get();
            System.out.println("✓ Async write complete: " + bytesWritten + " bytes");

            // Async read
            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            Future<Integer> readFuture = asyncChannel.read(readBuffer, 0);

            System.out.println("Reading asynchronously...");

            int bytesRead = readFuture.get();
            System.out.println("✓ Async read complete: " + bytesRead + " bytes");

            readBuffer.flip();
            byte[] data = new byte[readBuffer.remaining()];
            readBuffer.get(data);
            System.out.println("Content: " + new String(data));

            asyncChannel.close();
            Files.deleteIfExists(asyncFile);

        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 9. ASYNC I/O WITH COMPLETION HANDLER
        // ============================================================

        System.out.println("--- CompletionHandler ---");

        try {
            Path handlerFile = Paths.get("handler_test.txt");
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                    handlerFile,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);

            ByteBuffer writeBuffer = ByteBuffer.wrap("Callback-based async I/O".getBytes());

            channel.write(writeBuffer, 0, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("✓ Write completed: " + result + " bytes");
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("✗ Write failed: " + exc.getMessage());
                }
            });

            // Give time for callback
            Thread.sleep(100);

            Files.deleteIfExists(handlerFile);

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // 10. PATHS & FILES UTILITY
        // ============================================================

        System.out.println("--- Paths & Files Utility ---");

        try {
            // Path operations
            Path path1 = Paths.get("folder", "subfolder", "file.txt");
            System.out.println("Path: " + path1);
            System.out.println("File name: " + path1.getFileName());
            System.out.println("Parent: " + path1.getParent());
            System.out.println("Absolute: " + path1.toAbsolutePath());

            // Create directory
            Path dir = Paths.get("test_dir");
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
                System.out.println("✓ Directory created");
            }

            // Create file
            Path file = dir.resolve("test.txt");
            Files.write(file, "Test content".getBytes());
            System.out.println("✓ File created");

            // File attributes
            System.out.println("Size: " + Files.size(file) + " bytes");
            System.out.println("Readable: " + Files.isReadable(file));
            System.out.println("Writable: " + Files.isWritable(file));
            System.out.println("Hidden: " + Files.isHidden(file));

            // Read all lines
            List<String> lines = Files.readAllLines(file);
            System.out.println("Content: " + lines);

            // Clean up
            Files.delete(file);
            Files.delete(dir);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * NIO CORE CONCEPTS:
         *
         * BUFFER LIFECYCLE:
         * 1. Allocate: ByteBuffer.allocate(size)
         * 2. Write: buffer.put(data)
         * 3. Flip: buffer.flip() - switch to read mode
         * 4. Read: buffer.get()
         * 5. Clear/Compact: buffer.clear() or buffer.compact()
         *
         * BUFFER POSITION MANAGEMENT:
         * - position: Current index
         * - limit: End of valid data
         * - capacity: Total size
         * - mark: Saved position
         *
         * CHANNEL TYPES:
         * FileChannel: Files (read, write, map, transfer)
         * SocketChannel: TCP client
         * ServerSocketChannel: TCP server
         * DatagramChannel: UDP
         * AsynchronousFileChannel: Async file I/O
         * AsynchronousSocketChannel: Async TCP
         *
         * NIO vs IO:
         * IO (java.io):
         * - Stream-oriented
         * - Blocking
         * - Simple API
         * - Good for simple operations
         *
         * NIO (java.nio):
         * - Buffer-oriented
         * - Non-blocking (with Selector)
         * - More complex
         * - Better for scalability
         *
         * WHEN TO USE NIO:
         * ✓ Server handling many connections
         * ✓ Large file transfers
         * ✓ High-performance requirements
         * ✓ Need non-blocking I/O
         * ✗ Simple file operations
         * ✗ Few connections
         * ✗ Code simplicity is priority
         *
         * SELECTOR (Not shown in detail):
         * - Multiplexes multiple channels
         * - Single thread handles many connections
         * - Used in high-performance servers
         * - Register channels with selector
         * - Poll for ready channels
         *
         * ASYNC I/O PATTERNS:
         * 1. Future-based: future.get()
         * 2. Callback-based: CompletionHandler
         *
         * PERFORMANCE TIPS:
         * - Use direct buffers for large I/O
         * - Reuse buffers
         * - Use transferTo() for file copying
         * - Memory-map large files
         * - Use async I/O for concurrent operations
         *
         * COMMON OPERATIONS:
         * Read file: FileChannel + ByteBuffer
         * Write file: FileChannel + ByteBuffer
         * Copy file: transferTo() or transferFrom()
         * Async read: AsynchronousFileChannel
         * Memory map: FileChannel.map()
         *
         * FILES UTILITY METHODS:
         * Files.readAllBytes(path)
         * Files.readAllLines(path)
         * Files.write(path, bytes)
         * Files.copy(source, dest)
         * Files.move(source, dest)
         * Files.delete(path)
         * Files.exists(path)
         * Files.createDirectory(path)
         * Files.walk(path) - tree traversal
         *
         * ZERO-COPY TRANSFER:
         * transferTo() and transferFrom() use OS-level
         * operations for efficient file copying without
         * copying data to JVM heap.
         *
         * MEMORY-MAPPED FILES:
         * - Very fast for large files
         * - OS manages paging
         * - Good for random access
         * - Limited by address space
         * - Platform-dependent
         *
         * BEST PRACTICES:
         * 1. Always close channels/buffers
         * 2. Use try-with-resources
         * 3. Reuse buffers when possible
         * 4. Choose appropriate buffer size
         * 5. Use direct buffers for I/O
         * 6. Handle exceptions properly
         * 7. Test with different file sizes
         * 8. Profile before optimizing
         */
    }

    // Helper method to print buffer state
    private static void printBufferState(ByteBuffer buffer) {
        System.out.println("  position=" + buffer.position() +
                ", limit=" + buffer.limit() +
                ", capacity=" + buffer.capacity() +
                ", remaining=" + buffer.remaining());
    }
}
