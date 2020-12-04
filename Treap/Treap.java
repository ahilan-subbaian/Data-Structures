/**
 * @author Ahilan Subbaian
 * * I pledge my honor that i abided by Stevens honor system
 */

import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
    private class Node<E> {
        public E data;  // Key for search
        public int priority;  //Random heap priority
        public Node<E> left;
        public Node<E> right;

        public Node(E data, int priority) {
            if (data == null) {
                throw new IllegalArgumentException();
            } else {
                this.data = data;
                this.priority = priority;
                this.left = null;
                this.right = null;
            }
        }

        public Node<E> rotateRight() {  //preforms a right rotation
            Node<E> head = this.left;
            Node<E> left = head.right;
            head.right = this;
            this.left = left;
            return head;
        }

        public Node<E> rotateLeft() {  //preforms a left rotation
            Node<E> head = this.right;
            Node<E> right = head.left;
            head.left = this;
            this.right = right;
            return head;
        }
    }

    private Random priorityGenerator;
    private Node<E> root;

    public Treap() {  //creates an empty treap
        root = null;
        priorityGenerator = new Random();
    }

    public Treap(long seed) {  //creates an empty treap and intializes the priority generator using random(seed)
        root = null;
        priorityGenerator = new Random(seed);
    }

    public void reheap(Node<E> curr, Stack<Node<E>> path) {
        while (!path.isEmpty()) {
            Node<E> parent = path.pop();
            if (parent.priority < curr.priority) {
                if (parent.data.compareTo(curr.data) > 0) {
                    curr = parent.rotateRight();
                } else {
                    curr = parent.rotateLeft();
                }
                if (!path.isEmpty()) {
                    if (path.peek().left == parent) {
                        path.peek().left = curr;
                    } else {
                        path.peek().right = curr;
                    }
                } else {
                    this.root = curr;
                }
            } else {
                break;
            }
        }
    }

    boolean add(E key, int priority) {  //inserts a given element into the tree
        if (root == null) {
            root = new Node<E>(key, priority);
            return true;
        }
        else {
            Node<E> temp = new Node<E>(key, priority);
            Stack<Node<E>> stackTemp = new Stack<Node<E>>();
            Node<E> current = root;
            while (current != null) {
                int comparison = current.data.compareTo(key);
                if (comparison == 0) {
                    return false;
            }
                else {
                    if (comparison < 0) {
                        stackTemp.push(current);
                        if (current.right == null) {
                            current.right = temp;
                            reheap(temp, stackTemp);
                            return true;
                        }
                        else {
                            current = current.right;
                        }
                    } else {
                        stackTemp.push(current);
                        if (current.left == null) {
                            current.left = temp;
                            reheap(temp, stackTemp);
                            return true;
                        } else {
                            current = current.left;
                        }
                    }
                }
            }
            return false;
        }
    }

    boolean add(E key) {
        return add(key, priorityGenerator.nextInt());
    }

    private Node<E> delete(E key, Node<E> n) {  //deletes the node with the given key
        if (n == null) {
            return n;
        }
        else {
            if (n.data.compareTo(key) < 0 ) {
                n.right = delete(key, n.right);
            }
            else {
                if (n.data.compareTo(key) > 0) {
                    n.left = delete(key, n.left);
                }
                else {
                    if (n.right == null) {
                        n = n.left;
                    }
                    else if (n.left == null) {
                        n = n.right;
                    }
                    else {
                        if (n.right.priority < n.left.priority) {
                            n = n.rotateRight();
                            n.right = delete(key, n.right);
                        }
                        else {
                            n= n.rotateLeft();
                            n.left = delete(key, n.left);
                        }
                    }
                }
            }
        }
        return n;
    }

    public boolean delete (E key) {
        if (root == null || find(key) == false) {
            return false;
        }
        else {
            root = delete(key,root);
            return true;
        }
    }

    private boolean find(Node<E> root, E key) {  // Finds a node with the given key in the treap root at root
        if (root == null) {
            return false;
        } else {
            int comparison = root.data.compareTo(key);
            if (comparison == 0) {
                return true;
            } else {
                return find(root.right, key) || find(root.left, key);
            }
        }
    }

    public boolean find(E key) {  //finds the node with the given key within the treap
        return find(root, key);
    }

    private String toString(Node<E> n, int depth) {  //Carries out a preorder traversal of the tree and returns a representation of the nodes as a string.
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            b.append("--");
        }
        if (n == null) {
            b.append("null");
        }
        else {
            b.append("(key = " + n.data +", priority = " + n.priority + ")" );
            b.append("\n");
            b.append(toString(n.left, depth+1));
            b.append("\n");
            b.append(toString(n.right, depth+1));
        }
        return b.toString();
    }

    public String toString() {
        return toString(root, 0);
    }

    public static void main(String[] args) {
        Treap<Integer> testTree = new Treap<Integer>();
        testTree.add(3,12);
        testTree.add(5,71);
        testTree.add(7,72);
        testTree.add(2,34);
        testTree.add(6,16);
        testTree.add(4,53);
        testTree.add(1,45);
        System.out.println(testTree.delete(55));
        System.out.println(testTree.toString());
    }
}



















