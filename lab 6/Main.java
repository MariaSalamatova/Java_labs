import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;


public class Main {


    static class RedBlackTree {


        private static final class Node {
            int key;
            boolean red;
            Node left, right, parent;


            Node(int key) {
                this.key = key;
                this.red = true;
            }
        }


        private final Node nil = new Node(0);
        private Node root;
        private int size;


        RedBlackTree() {
            nil.red = false;
            nil.left = nil;
            nil.right = nil;
            nil.parent = nil;
            root = nil;
        }


        int size() {
            return size;
        }


        boolean isEmpty() {
            return root == nil;
        }


        void clear() {
            root = nil;
            size = 0;
        }


        boolean contains(int key) {
            return find(key) != nil;
        }


        private Node find(int key) {
            Node cur = root;
            while (cur != nil) {
                if (key == cur.key) {
                    return cur;
                }
                cur = key < cur.key ? cur.left : cur.right;
            }
            return nil;
        }


        private void rotateLeft(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != nil) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == nil) {
                root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }


        private void rotateRight(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != nil) {
                y.right.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == nil) {
                root = y;
            } else if (x == x.parent.right) {
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
            y.right = x;
            x.parent = y;
        }


        boolean insert(int key) {
            Node parent = nil;
            Node cur = root;
            while (cur != nil) {
                parent = cur;
                if (key == cur.key) {
                    return false;
                }
                cur = key < cur.key ? cur.left : cur.right;
            }
            Node z = new Node(key);
            z.parent = parent;
            z.left = nil;
            z.right = nil;
            if (parent == nil) {
                root = z;
            } else if (key < parent.key) {
                parent.left = z;
            } else {
                parent.right = z;
            }
            size++;
            insertFixup(z);
            return true;
        }


        private void insertFixup(Node z) {
            while (z.parent.red) {
                if (z.parent == z.parent.parent.left) {
                    Node uncle = z.parent.parent.right;
                    if (uncle.red) {
                        z.parent.red = false;
                        uncle.red = false;
                        z.parent.parent.red = true;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.right) {
                            z = z.parent;
                            rotateLeft(z);
                        }
                        z.parent.red = false;
                        z.parent.parent.red = true;
                        rotateRight(z.parent.parent);
                    }
                } else {
                    Node uncle = z.parent.parent.left;
                    if (uncle.red) {
                        z.parent.red = false;
                        uncle.red = false;
                        z.parent.parent.red = true;
                        z = z.parent.parent;
                    } else {
                        if (z == z.parent.left) {
                            z = z.parent;
                            rotateRight(z);
                        }
                        z.parent.red = false;
                        z.parent.parent.red = true;
                        rotateLeft(z.parent.parent);
                    }
                }
            }
            root.red = false;
        }


        boolean delete(int key) {
            Node z = find(key);
            if (z == nil) {
                return false;
            }
            Node y = z;
            boolean yOriginalRed = y.red;
            Node x;
            if (z.left == nil) {
                x = z.right;
                transplant(z, z.right);
            } else if (z.right == nil) {
                x = z.left;
                transplant(z, z.left);
            } else {
                y = minimum(z.right);
                yOriginalRed = y.red;
                x = y.right;
                if (y.parent == z) {
                    x.parent = y;
                } else {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
                y.red = z.red;
            }
            size--;
            if (!yOriginalRed) {
                deleteFixup(x);
            }
            return true;
        }


        private void transplant(Node u, Node v) {
            if (u.parent == nil) {
                root = v;
            } else if (u == u.parent.left) {
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
            v.parent = u.parent;
        }


        private Node minimum(Node n) {
            while (n.left != nil) {
                n = n.left;
            }
            return n;
        }


        private void deleteFixup(Node x) {
            while (x != root && !x.red) {
                if (x == x.parent.left) {
                    Node w = x.parent.right;
                    if (w.red) {
                        w.red = false;
                        x.parent.red = true;
                        rotateLeft(x.parent);
                        w = x.parent.right;
                    }
                    if (!w.left.red && !w.right.red) {
                        w.red = true;
                        x = x.parent;
                    } else {
                        if (!w.right.red) {
                            w.left.red = false;
                            w.red = true;
                            rotateRight(w);
                            w = x.parent.right;
                        }
                        w.red = x.parent.red;
                        x.parent.red = false;
                        w.right.red = false;
                        rotateLeft(x.parent);
                        x = root;
                    }
                } else {
                    Node w = x.parent.left;
                    if (w.red) {
                        w.red = false;
                        x.parent.red = true;
                        rotateRight(x.parent);
                        w = x.parent.left;
                    }
                    if (!w.right.red && !w.left.red) {
                        w.red = true;
                        x = x.parent;
                    } else {
                        if (!w.left.red) {
                            w.right.red = false;
                            w.red = true;
                            rotateLeft(w);
                            w = x.parent.left;
                        }
                        w.red = x.parent.red;
                        x.parent.red = false;
                        w.left.red = false;
                        rotateRight(x.parent);
                        x = root;
                    }
                }
            }
            x.red = false;
        }


        List<Integer> inOrder() {
            List<Integer> out = new ArrayList<>();
            inOrder(root, out);
            return out;
        }


        private void inOrder(Node n, List<Integer> out) {
            if (n == nil) {
                return;
            }
            inOrder(n.left, out);
            out.add(n.key);
            inOrder(n.right, out);
        }


        List<Integer> preOrder() {
            List<Integer> out = new ArrayList<>();
            preOrder(root, out);
            return out;
        }


        private void preOrder(Node n, List<Integer> out) {
            if (n == nil) {
                return;
            }
            out.add(n.key);
            preOrder(n.left, out);
            preOrder(n.right, out);
        }


        List<Integer> postOrder() {
            List<Integer> out = new ArrayList<>();
            postOrder(root, out);
            return out;
        }


        private void postOrder(Node n, List<Integer> out) {
            if (n == nil) {
                return;
            }
            postOrder(n.left, out);
            postOrder(n.right, out);
            out.add(n.key);
        }


        List<Integer> levelOrder() {
            List<Integer> out = new ArrayList<>();
            if (root == nil) {
                return out;
            }
            List<Node> queue = new ArrayList<>();
            queue.add(root);
            for (int i = 0; i < queue.size(); i++) {
                Node n = queue.get(i);
                out.add(n.key);
                if (n.left != nil) {
                    queue.add(n.left);
                }
                if (n.right != nil) {
                    queue.add(n.right);
                }
            }
            return out;
        }


        void display() {
            if (root == nil) {
                System.out.println("(tree is empty)");
                return;
            }
            display(root, "", 0);
        }


        private void display(Node n, String prefix, int depth) {
            if (n == nil) {
                return;
            }
            display(n.right, prefix, depth + 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                sb.append("      ");
            }
            sb.append(n.key).append(n.red ? " (R)" : " (B)");
            System.out.println(sb);
            display(n.left, prefix, depth + 1);
        }


        boolean isValid() {
            if (root.red) {
                return false;
            }
            return checkNode(root, Long.MIN_VALUE, Long.MAX_VALUE) != -1;
        }

        private int checkNode(Node n, long min, long max) {
            if (n == nil) {
                return 1;
            }
            if (n.key <= min || n.key >= max) {
                return -1;
            }
            if (n.red && (n.left.red || n.right.red)) {
                return -1;
            }
            int lh = checkNode(n.left, min, n.key);
            int rh = checkNode(n.right, n.key, max);
            if (lh == -1 || rh == -1 || lh != rh) {
                return -1;
            }
            return lh + (n.red ? 0 : 1);
        }


        int blackHeight() {
            int h = 0;
            for (Node n = root; n != nil; n = n.left) {
                if (!n.red) {
                    h++;
                }
            }
            return h;
        }
    }


    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();


    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        System.out.println("=== Red-Black Tree ===");
        try {
            while (true) {
                printMenu();
                int choice = readInt("Your choice: ");
                System.out.println();
                switch (choice) {
                    case 1 -> fillRandom(tree, false);
                    case 2 -> fillRandom(tree, true);
                    case 3 -> fillFromKeyboard(tree);
                    case 4 -> insertOne(tree);
                    case 5 -> deleteOne(tree);
                    case 6 -> showTraversals(tree);
                    case 7 -> {
                        System.out.println("Tree (root at the left, right subtree above):");
                        tree.display();
                    }
                    case 8 -> searchOne(tree);
                    case 9 -> showValidation(tree);
                    case 10 -> {
                        tree.clear();
                        System.out.println("Tree cleared.");
                    }
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Unknown option, try again.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nInput closed. Goodbye!");
        }
    }


    private static void printMenu() {
        System.out.println();
        System.out.println("--------------- MENU ---------------");
        System.out.println(" 1  - Fill with random numbers (random order)");
        System.out.println(" 2  - Fill with random numbers (sorted ascending)");
        System.out.println(" 3  - Enter numbers from the keyboard");
        System.out.println(" 4  - Insert one number");
        System.out.println(" 5  - Delete one number");
        System.out.println(" 6  - Show traversals");
        System.out.println(" 7  - Display the tree");
        System.out.println(" 8  - Search for a number");
        System.out.println(" 9  - Validate red-black properties");
        System.out.println(" 10 - Clear the tree");
        System.out.println(" 0  - Exit");
        System.out.println("------------------------------------");
    }


    private static void fillRandom(RedBlackTree tree, boolean sorted) {
        int count = readInt("How many numbers? ");
        if (count <= 0) {
            System.out.println("Count must be positive.");
            return;
        }
        int bound = readInt("Maximum value (numbers are in range 0..max): ");
        if (bound < 0) {
            System.out.println("Maximum must not be negative.");
            return;
        }
        int[] data = new int[count];
        for (int i = 0; i < count; i++) {
            data[i] = RANDOM.nextInt(bound + 1);
        }
        if (sorted) {
            Arrays.sort(data);
        }
        System.out.println("Array (" + (sorted ? "sorted ascending" : "random order")
                + ", insertion order):");
        System.out.println(Arrays.toString(data));


        int added = 0;
        for (int value : data) {
            if (tree.insert(value)) {
                added++;
            }
        }
        System.out.println("Inserted: " + added + ", duplicates skipped: " + (count - added));
        System.out.println();
        tree.display();
    }


    private static void fillFromKeyboard(RedBlackTree tree) {
        System.out.println("Enter integers separated by spaces (one line):");
        String line = SCANNER.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("Nothing entered.");
            return;
        }
        int added = 0;
        int skipped = 0;
        for (String token : line.split("\\s+")) {
            try {
                if (tree.insert(Integer.parseInt(token))) {
                    added++;
                } else {
                    skipped++;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ignored invalid token: " + token);
            }
        }
        System.out.println("Inserted: " + added + ", duplicates skipped: " + skipped);
        System.out.println();
        tree.display();
    }


    private static void insertOne(RedBlackTree tree) {
        int value = readInt("Number to insert: ");
        if (tree.insert(value)) {
            System.out.println(value + " inserted.");
            System.out.println();
            tree.display();
        } else {
            System.out.println(value + " already exists in the tree.");
        }
    }


    private static void deleteOne(RedBlackTree tree) {
        if (tree.isEmpty()) {
            System.out.println("The tree is empty.");
            return;
        }
        int value = readInt("Number to delete: ");
        if (tree.delete(value)) {
            System.out.println(value + " deleted.");
            System.out.println();
            tree.display();
        } else {
            System.out.println(value + " was not found.");
        }
    }


    private static void searchOne(RedBlackTree tree) {
        int value = readInt("Number to search for: ");
        System.out.println(value + (tree.contains(value) ? " is in the tree." : " is NOT in the tree."));
    }


    private static void showTraversals(RedBlackTree tree) {
        if (tree.isEmpty()) {
            System.out.println("The tree is empty.");
            return;
        }
        System.out.println("In-order    (sorted)  : " + tree.inOrder());
        System.out.println("Pre-order             : " + tree.preOrder());
        System.out.println("Post-order            : " + tree.postOrder());
        System.out.println("Level-order (BFS)     : " + tree.levelOrder());
    }


    private static void showValidation(RedBlackTree tree) {
        System.out.println("Nodes        : " + tree.size());
        if (tree.isEmpty()) {
            System.out.println("The tree is empty (trivially valid).");
            return;
        }
        System.out.println("Black height : " + tree.blackHeight());
        System.out.println("Valid        : " + (tree.isValid() ? "YES" : "NO"));
    }


    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}