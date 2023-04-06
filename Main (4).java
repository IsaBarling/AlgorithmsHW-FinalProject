import java.util.*;

public class Main {
  public static void main(String[] args) {
    RedBlackTree tree = new RedBlackTree();

    tree.put(10);
    tree.put(5);
    tree.put(15);
    tree.put(3);
    tree.put(7);
    tree.put(13);
    tree.put(20);

    tree.traverseInOrder();
  }

  public static class RedBlackTree {
    private Node root;

    private class Node {
        private int key;
        private Node left, right;
        private boolean color;

        public Node(int key, boolean color) {
            this.key = key;
            this.color = color;
        }
    }

    public RedBlackTree() {
        root = null;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == true;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = true;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = true;
        return x;
    }

    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    public void put(int key) {
        root = put(root, key);
        root.color = false;
    }

    private Node put(Node h, int key) {
        if (h == null) return new Node(key, true);

        if (key < h.key) h.left = put(h.left, key);
        else if (key > h.key) h.right = put(h.right, key);

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    public void traverseInOrder() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node x) {
        if (x == null) return;
        traverseInOrder(x.left);
        System.out.print(x.key + " ");
        traverseInOrder(x.right);
    }
  }

  public static class HashTable {
    private int size; // размер таблицы
    private Object[] keys; // массив ключей
    private Object[] values; // массив значений

    public HashTable(int size) {
        this.size = size;
        keys = new Object[size];
        values = new Object[size];
    }

    // хэш-функция
    private int hashFunction(Object key) {
        return Math.abs(key.hashCode()) % size;
    }

    // добавление элемента в таблицу
    public void put(Object key, Object value) {
        int index = hashFunction(key);
        while (keys[index] != null && !keys[index].equals(key)) {
            index = (index + 1) % size;
        }
        keys[index] = key;
        values[index] = value;
    }

    // поиск элемента в таблице
    public Object get(Object key) {
        int index = hashFunction(key);
        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index];
            }
            index = (index + 1) % size;
        }
        return null;
    }
  }
}
