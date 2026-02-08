/*
 * LESSON 33: ADVANCED COLLECTIONS & DATA STRUCTURES
 *
 * Beyond basic collections, Java offers powerful data structures and techniques
 * for solving complex problems efficiently.
 *
 * KEY CONCEPTS:
 * - Time Complexity: Big O notation (O(1), O(log n), O(n), O(n²))
 * - Space Complexity: Memory usage
 * - Immutability: Collections that cannot be modified
 * - Thread-Safety: Collections safe for concurrent access
 * - Custom Data Structures: Building your own
 *
 * ADVANCED COLLECTIONS:
 * - Deque: Double-ended queue
 * - PriorityQueue: Heap-based priority queue
 * - NavigableSet/Map: Sorted with navigation
 * - EnumSet/EnumMap: Efficient for enums
 * - WeakHashMap: Weak-keyed map
 * - IdentityHashMap: Reference equality
 * - ConcurrentHashMap: Thread-safe map
 * - CopyOnWriteArrayList: Thread-safe list
 *
 * IMMUTABLE COLLECTIONS (Java 9+):
 * - List.of(), Set.of(), Map.of()
 * - Collections.unmodifiableXxx()
 *
 * CUSTOM DATA STRUCTURES:
 * - Linked List
 * - Binary Search Tree
 * - Graph
 * - Trie
 * - Bloom Filter
 *
 * ALGORITHMS:
 * - Binary Search
 * - Graph Traversal (BFS, DFS)
 * - Tree Traversal
 * - Sorting
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class Lesson33_AdvancedCollections {
    public static void main(String[] args) {

        System.out.println("=== ADVANCED COLLECTIONS & DATA STRUCTURES ===\n");

        // ============================================================
        // 1. DEQUE (DOUBLE-ENDED QUEUE)
        // ============================================================

        System.out.println("--- Deque ---");

        Deque<String> deque = new ArrayDeque<>();

        // Add to both ends
        deque.addFirst("Front");
        deque.addLast("Back");
        deque.addFirst("New Front");
        deque.addLast("New Back");

        System.out.println("Deque: " + deque);

        // Remove from both ends
        System.out.println("Remove first: " + deque.removeFirst());
        System.out.println("Remove last: " + deque.removeLast());
        System.out.println("Deque after removal: " + deque);

        // Use as Stack (LIFO)
        deque.push("Stack1");
        deque.push("Stack2");
        System.out.println("Pop: " + deque.pop());

        // Use as Queue (FIFO)
        deque.offer("Queue1");
        deque.offer("Queue2");
        System.out.println("Poll: " + deque.poll());

        System.out.println();


        // ============================================================
        // 2. PRIORITY QUEUE (HEAP)
        // ============================================================

        System.out.println("--- PriorityQueue ---");

        // Min heap (default)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.addAll(Arrays.asList(5, 2, 8, 1, 9, 3));
        System.out.println("Min heap: " + minHeap);
        System.out.println("Polling (ascending): ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println();

        // Max heap (custom comparator)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.addAll(Arrays.asList(5, 2, 8, 1, 9, 3));
        System.out.println("Max heap: " + maxHeap);
        System.out.println("Polling (descending): ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");


        // ============================================================
        // 3. NAVIGABLE SET & MAP
        // ============================================================

        System.out.println("--- NavigableSet ---");

        NavigableSet<Integer> navSet = new TreeSet<>(Arrays.asList(1, 3, 5, 7, 9, 11));

        System.out.println("Set: " + navSet);
        System.out.println("Lower than 7: " + navSet.lower(7));    // 5
        System.out.println("Floor of 7: " + navSet.floor(7));      // 7
        System.out.println("Ceiling of 8: " + navSet.ceiling(8));  // 9
        System.out.println("Higher than 7: " + navSet.higher(7));  // 9

        // Subsets
        System.out.println("Subset [3, 9): " + navSet.subSet(3, true, 9, false));
        System.out.println("Head set < 7: " + navSet.headSet(7, false));
        System.out.println("Tail set >= 7: " + navSet.tailSet(7, true));

        // Descending
        System.out.println("Descending: " + navSet.descendingSet());

        System.out.println();


        // ============================================================
        // 4. ENUM SET & ENUM MAP
        // ============================================================

        System.out.println("--- EnumSet & EnumMap ---");

        // EnumSet - very efficient for enums
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);

        System.out.println("Weekend: " + weekend);
        System.out.println("Weekdays: " + weekdays);
        System.out.println("All days: " + allDays);

        // EnumMap - efficient map for enum keys
        EnumMap<Day, String> activities = new EnumMap<>(Day.class);
        activities.put(Day.MONDAY, "Work");
        activities.put(Day.FRIDAY, "Coding");
        activities.put(Day.SATURDAY, "Relax");

        System.out.println("Activities: " + activities);

        System.out.println();


        // ============================================================
        // 5. WEAK HASH MAP
        // ============================================================

        System.out.println("--- WeakHashMap ---");

        WeakHashMap<Object, String> weakMap = new WeakHashMap<>();
        Object key1 = new Object();
        Object key2 = new Object();

        weakMap.put(key1, "Value1");
        weakMap.put(key2, "Value2");

        System.out.println("Before GC: " + weakMap.size());

        // Make key1 eligible for GC
        key1 = null;
        System.gc();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After GC: " + weakMap.size() + " (may be 1 if GC removed key1)");

        System.out.println();


        // ============================================================
        // 6. IDENTITY HASH MAP
        // ============================================================

        System.out.println("--- IdentityHashMap ---");

        // Regular HashMap uses equals()
        Map<String, Integer> normalMap = new HashMap<>();
        String s1 = new String("Key");
        String s2 = new String("Key");
        normalMap.put(s1, 1);
        normalMap.put(s2, 2);
        System.out.println("HashMap size (equals): " + normalMap.size()); // 1 (same content)

        // IdentityHashMap uses == (reference equality)
        Map<String, Integer> identityMap = new IdentityHashMap<>();
        identityMap.put(s1, 1);
        identityMap.put(s2, 2);
        System.out.println("IdentityHashMap size (==): " + identityMap.size()); // 2 (different objects)

        System.out.println();


        // ============================================================
        // 7. IMMUTABLE COLLECTIONS (Java 9+)
        // ============================================================

        System.out.println("--- Immutable Collections ---");

        // Java 9+ factory methods
        List<String> immutableList = List.of("A", "B", "C");
        Set<Integer> immutableSet = Set.of(1, 2, 3);
        Map<String, Integer> immutableMap = Map.of("One", 1, "Two", 2);

        System.out.println("Immutable List: " + immutableList);
        System.out.println("Immutable Set: " + immutableSet);
        System.out.println("Immutable Map: " + immutableMap);

        // These will throw UnsupportedOperationException:
        // immutableList.add("D");  // ERROR!
        // immutableSet.remove(1);  // ERROR!

        // Using Collections.unmodifiable
        List<String> mutableList = new ArrayList<>(Arrays.asList("X", "Y", "Z"));
        List<String> unmodifiableList = Collections.unmodifiableList(mutableList);
        System.out.println("Unmodifiable: " + unmodifiableList);

        System.out.println();


        // ============================================================
        // 8. CONCURRENT COLLECTIONS
        // ============================================================

        System.out.println("--- Concurrent Collections ---");

        // ConcurrentHashMap - thread-safe, high performance
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("A", 1);
        concurrentMap.put("B", 2);
        concurrentMap.put("C", 3);

        // Atomic operations
        concurrentMap.compute("A", (k, v) -> v + 10);
        concurrentMap.computeIfAbsent("D", k -> 4);
        concurrentMap.merge("B", 5, Integer::sum);

        System.out.println("ConcurrentHashMap: " + concurrentMap);

        // CopyOnWriteArrayList - thread-safe for reads
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
        cowList.addAll(Arrays.asList("One", "Two", "Three"));
        System.out.println("CopyOnWriteArrayList: " + cowList);

        // BlockingQueue - producer-consumer
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(5);
        blockingQueue.offer("Task1");
        blockingQueue.offer("Task2");
        System.out.println("BlockingQueue: " + blockingQueue);

        System.out.println();


        // ============================================================
        // 9. CUSTOM LINKED LIST
        // ============================================================

        System.out.println("--- Custom Linked List ---");

        CustomLinkedList<Integer> customList = new CustomLinkedList<>();
        customList.add(10);
        customList.add(20);
        customList.add(30);
        customList.add(40);

        System.out.println("Custom list size: " + customList.size());
        System.out.println("Element at index 2: " + customList.get(2));
        System.out.println("Contains 20? " + customList.contains(20));

        System.out.print("Custom list: ");
        customList.printList();

        System.out.println();


        // ============================================================
        // 10. BINARY SEARCH TREE
        // ============================================================

        System.out.println("--- Binary Search Tree ---");

        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        System.out.print("In-order traversal: ");
        bst.inOrderTraversal();
        System.out.println();

        System.out.println("Search 40: " + bst.search(40));
        System.out.println("Search 100: " + bst.search(100));

        System.out.println();


        // ============================================================
        // 11. GRAPH & BFS/DFS
        // ============================================================

        System.out.println("--- Graph Traversal ---");

        Graph graph = new Graph(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);

        System.out.print("BFS from vertex 0: ");
        graph.BFS(0);

        System.out.print("DFS from vertex 0: ");
        graph.DFS(0);

        System.out.println();


        // ============================================================
        // 12. TRIE (PREFIX TREE)
        // ============================================================

        System.out.println("--- Trie (Prefix Tree) ---");

        Trie trie = new Trie();
        trie.insert("cat");
        trie.insert("car");
        trie.insert("card");
        trie.insert("care");
        trie.insert("dog");

        System.out.println("Search 'cat': " + trie.search("cat"));
        System.out.println("Search 'can': " + trie.search("can"));
        System.out.println("Starts with 'car': " + trie.startsWith("car"));
        System.out.println("Starts with 'do': " + trie.startsWith("do"));

        System.out.println();


        // ============================================================
        // 13. LRU CACHE
        // ============================================================

        System.out.println("--- LRU Cache ---");

        LRUCache cache = new LRUCache(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("Cache after adding 1,2,3: " + cache);

        cache.get(1); // Access 1 (makes it most recent)
        cache.put(4, "D"); // Evicts 2 (least recently used)
        System.out.println("After accessing 1 and adding 4: " + cache);

        System.out.println();


        // ============================================================
        // 14. BIG O COMPLEXITY REFERENCE
        // ============================================================

        System.out.println("--- Time Complexity Reference ---");

        System.out.println("""
                Common Data Structure Operations:

                ArrayList:
                  - Access: O(1)
                  - Search: O(n)
                  - Insert (end): O(1) amortized
                  - Insert (middle): O(n)
                  - Delete: O(n)

                LinkedList:
                  - Access: O(n)
                  - Search: O(n)
                  - Insert (beginning): O(1)
                  - Insert (end): O(1)
                  - Delete: O(1) if node reference known

                HashMap:
                  - Access: O(1) average
                  - Search: O(1) average
                  - Insert: O(1) average
                  - Delete: O(1) average
                  - Worst case: O(n)

                TreeMap (Red-Black Tree):
                  - Access: O(log n)
                  - Search: O(log n)
                  - Insert: O(log n)
                  - Delete: O(log n)

                PriorityQueue (Heap):
                  - Peek: O(1)
                  - Insert: O(log n)
                  - Remove: O(log n)

                HashSet:
                  - Search: O(1) average
                  - Insert: O(1) average
                  - Delete: O(1) average

                TreeSet:
                  - Search: O(log n)
                  - Insert: O(log n)
                  - Delete: O(log n)
                """);

        System.out.println();


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * CHOOSING THE RIGHT DATA STRUCTURE:
         *
         * Need fast random access? → ArrayList
         * Need fast insertion/deletion at ends? → LinkedList, Deque
         * Need unique elements? → HashSet, TreeSet
         * Need key-value pairs? → HashMap, TreeMap
         * Need priority-based access? → PriorityQueue
         * Need sorted elements? → TreeSet, TreeMap
         * Need thread-safety? → ConcurrentHashMap, CopyOnWriteArrayList
         * Need weak references? → WeakHashMap
         * Need double-ended operations? → Deque
         *
         * TIME COMPLEXITY (Big O):
         * O(1) - Constant: HashMap get/put, ArrayList get
         * O(log n) - Logarithmic: TreeMap, Binary Search, Heap
         * O(n) - Linear: ArrayList search, LinkedList access
         * O(n log n) - Log-linear: Merge Sort, Heap Sort
         * O(n²) - Quadratic: Bubble Sort, nested loops
         * O(2^n) - Exponential: Recursive Fibonacci
         *
         * SPACE COMPLEXITY:
         * Consider both the data structure itself and temporary space
         * ArrayList: O(n)
         * HashMap: O(n)
         * Recursion: O(depth) for call stack
         *
         * IMMUTABILITY BENEFITS:
         * - Thread-safe
         * - Cacheable
         * - Predictable
         * - Safer for HashMap keys
         *
         * WHEN TO USE CUSTOM DATA STRUCTURES:
         * - Specific performance requirements
         * - Domain-specific operations
         * - Learning and interviews
         * - When standard library doesn't fit
         *
         * ALGORITHMS TO KNOW:
         * - Binary Search: O(log n)
         * - BFS/DFS: O(V + E)
         * - QuickSort: O(n log n) average
         * - MergeSort: O(n log n)
         * - Dijkstra: O((V + E) log V)
         * - Dynamic Programming
         *
         * INTERVIEW FAVORITES:
         * - Array manipulation
         * - Two pointers
         * - Sliding window
         * - Hash maps
         * - Trees (traversal, BST)
         * - Graphs (BFS, DFS)
         * - Dynamic programming
         * - Backtracking
         *
         * BEST PRACTICES:
         * 1. Choose based on operations needed
         * 2. Consider time vs space tradeoffs
         * 3. Use appropriate initial capacity
         * 4. Prefer interfaces over concrete types
         * 5. Use immutable collections when possible
         * 6. Consider thread-safety requirements
         * 7. Profile before optimizing
         * 8. Understand the implementation
         */
    }
}


// ============================================================
// ENUMS FOR EXAMPLES
// ============================================================

enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}


// ============================================================
// CUSTOM LINKED LIST IMPLEMENTATION
// ============================================================

class CustomLinkedList<T> {
    private Node<T> head;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printList() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}


// ============================================================
// BINARY SEARCH TREE
// ============================================================

class BinarySearchTree {
    private TreeNode root;

    private static class TreeNode {
        int value;
        TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
            left = right = null;
        }
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            return new TreeNode(value);
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(TreeNode root, int value) {
        if (root == null) {
            return false;
        }
        if (root.value == value) {
            return true;
        }
        return value < root.value
                ? searchRec(root.left, value)
                : searchRec(root.right, value);
    }

    public void inOrderTraversal() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.value + " ");
            inOrderRec(root.right);
        }
    }
}


// ============================================================
// GRAPH (ADJACENCY LIST)
// ============================================================

class Graph {
    private int vertices;
    private LinkedList<Integer>[] adjList;

    @SuppressWarnings("unchecked")
    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
        adjList[dest].add(source); // For undirected graph
    }

    public void BFS(int start) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");

            for (int neighbor : adjList[vertex]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void DFS(int start) {
        boolean[] visited = new boolean[vertices];
        DFSUtil(start, visited);
        System.out.println();
    }

    private void DFSUtil(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.print(vertex + " ");

        for (int neighbor : adjList[vertex]) {
            if (!visited[neighbor]) {
                DFSUtil(neighbor, visited);
            }
        }
    }
}


// ============================================================
// TRIE (PREFIX TREE)
// ============================================================

class Trie {
    private TrieNode root;

    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return null;
            }
            current = current.children.get(ch);
        }
        return current;
    }
}


// ============================================================
// LRU CACHE (LEAST RECENTLY USED)
// ============================================================

class LRUCache extends LinkedHashMap<Integer, String> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // accessOrder = true
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > capacity;
    }
}
