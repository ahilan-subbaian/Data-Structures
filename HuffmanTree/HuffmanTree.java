package ExtraCredit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * Instructions: 
 * First: Read through the assignment specification, make sure you understand what the assignment is asking for.
 * Second: There are number of "
 * Third: Test you code.
 */




// Pledge: I pledge my honor to abide by the Stevens Honor Code
// Name: Ahilan Subbaian


/**
 * HW6 CS284 Fall 2019
 * Implements a Huffman encoding tree.
 * The included code has been commented for the student's benefit, feel free to read through it.
 */
public class HuffmanTree {
	public class Pair<E, F>{
		private E x;
		private F y;
		
		public Pair(E a, F b){
			x = a;
			y = b;
		}
	}
	// ******************** Start of Stub Code ******************** //
	// ************************************************************ //
    /** Node<E> is an inner class and it is abstract.
     * There will be two kinds
     * of Node, one for leaves and one for internal nodes. */
    abstract static class Node implements Comparable<Node>{
        /** The frequency of all the items below this node */
        protected int frequency;
        
        public Node(int freq) {
        	this.frequency = freq;
        }
        
		/** Needed for the Minimum Heap used later in this stub. */
		public int compareTo(Node other) {
			return this.frequency - other.frequency;
		}
    }
    /** Leaves of a Huffman tree contain the data items */
    protected static class LeafNode extends Node {
        // Data Fields
        /** The data in the node */
        protected char data;
        /** Constructor to create a leaf node (i.e. no children) */
        public LeafNode(char data, int freq) {
            super(freq);
            this.data = data;
        }
        /** toString method */
        public String toString() {
            return "[value= "+this.data + ",freq= "+frequency+"]";
        }
    }
    /** Internal nodes contain no data,
     * just references to left and right subtrees */
    protected static class InternalNode extends Node {
        /** A reference to the left child */
        protected Node left;
        /** A reference to the right child */
        protected Node right;

        /** Constructor to create an internal node */
        public InternalNode(Node leftC, Node rightC) {
            super(leftC.frequency + rightC.frequency);
            left = leftC; right = rightC;
        }
        public String toString() {
            return "(freq= "+frequency+")";
        }
    }
	
	// Enough space to encode all "extended ascii" values
	// This size is probably overkill (since many of the values are not "printable" in the usual sense)
	private static final int codex_size = 256;
	
	/* Data Fields for Huffman Tree */
	private Node root;
	
	public HuffmanTree(String s) {
		root = buildHuffmanTree(s);
	}
	
	/**
	 * Returns the frequencies of all characters in s.
	 * @param s
	 * @return
	 */
	public static int[] frequency(String s) {
		int[] freq = new int[codex_size];
		for (char c: s.toCharArray()) {
			freq[c]++;
		}
		return freq;
	}
	
	/**
	 * Builds the actual Huffman tree for that particular string.
	 * @param s
	 * @return
	 */
	private static Node buildHuffmanTree(String s) {
		int[] freq = frequency(s);
		
		// Create a minimum heap for creating the Huffman Tree
		// Note to students: You probably won't know what this data structure
		// is yet, and that is okay.
		PriorityQueue<Node> min_heap = new PriorityQueue<Node>();
		
		// Go through and create all the nodes we need
		// as in, all the nodes that actually appear in our string (have a frequency greater then 0)
		for(int i = 0; i < codex_size; i++) {
			if (freq[i] > 0) {
				// Add a new node (for that character) to the min_heap, notice we have to cast our int i into a char.
				min_heap.add(new LeafNode((char) i, freq[i]));
			}
		}
		
		// Edge case (string was empty)
		if (min_heap.isEmpty()) {
			throw new NullPointerException("Cannot encode an empty String");
		}
		
		// Now to create the actual Huffman Tree 
		// NOTE: this algorithm is a bit beyond what we cover in cs284, 
		// you'll see this in depth in cs385
		
		// Merge smallest subtrees together
		while (min_heap.size() > 1) {
			Node left = min_heap.poll();
			Node right = min_heap.poll();
			Node merged_tree = new InternalNode(left, right);
			min_heap.add(merged_tree);
		}
		
		// Return our structured Huffman Tree
		return min_heap.poll();
	}
	
	// ******************** End of Stub Code ******************** //
	// ********************************************************** //
	
	
	public String bitsToString(Boolean[] encoding) {
		String bit = "";
		for(int i = 0; i < encoding.length; i++) {
			if(encoding[i])
				bit += "1";
			else
				bit += "0";
		}
		return bit;
	}
	
	public String toString() {
		return toString(root, 1);
    }
	
	//look at the assignment the freq is off
	//how to access each specific type of variable
	private String toString(Node n, int x) {
		if(n == null)
			return "";
		if(n instanceof InternalNode)
			return "(frequency = " + n.frequency + ")\n" + tabs(x) + toString(((InternalNode) n).right, x + 1) 
			+ "\n" + tabs(x) + toString(((InternalNode) n).left, x + 1);
		else
			return "[value = " + ((LeafNode) n).data + ", frequency = " + ((LeafNode) n).frequency + "]";
	}
	
	private String tabs(int x) {
		if(x == 0)
			return "";
		return "\t" + tabs(x - 1);
	}
	
//	public String decode(Boolean[] coding) {
//		String code = bitsToString(coding);
//		String uncode = "";
//		int i = 0;
//		Node current;
//		while(i < code.length()) {
//			current = root;
//			while(i < code.length() && current instanceof InternalNode) {
//				if(code.charAt(i) == '0')
//					current = ((InternalNode) current).left;
//				else
//					current = ((InternalNode) current).right;
//				i++;
//			}
//			if(current instanceof InternalNode)
//				throw new IllegalArgumentException();
//			uncode += ((LeafNode) current).data;
//		}
//		return uncode;
//	}
	
	public String decode(Boolean[] coding) {
		String uncode = "";
		int i = 0;
		Node current;
		while(i < coding.length) {
			current = root;
			while(i < coding.length && current instanceof InternalNode) {
				if(!coding[i])
					current = ((InternalNode) current).left;
				else
					current = ((InternalNode) current).right;
				i++;
			}
			if(current instanceof InternalNode)
				throw new IllegalArgumentException();
			uncode += ((LeafNode) current).data;
		}
		return uncode;
	}
	
	public Boolean[] encode(String inputText) {
		String code = "";
		int i = 0;
		while(i < inputText.length()) {
			Pair<Boolean, String> dogs = find(inputText.charAt(i), root);
			if(!dogs.x)
				throw new IllegalArgumentException();
			code += dogs.y;
//			String dogs = find(inputText.charAt(i), root);
//			if(dogs.equals("."))
//				throw new IllegalArgumentException();
//			code += dogs;
			i++;
		}
		return stringToBits(code);
	}
	
	private Pair<Boolean, String> find(char chars, Node current) {
		if(current instanceof LeafNode)
			return new Pair<Boolean, String>(((LeafNode) current).data == chars, "");
		Pair<Boolean, String> maybe = find(chars, ((InternalNode) current).right);
		if(maybe.x)
			return new Pair<Boolean, String>(true, "1" + maybe.y);
		maybe = find(chars, ((InternalNode) current).left);
		if(maybe.x)
			return new Pair<Boolean, String>(true, "0" + maybe.y);
		return new Pair<Boolean, String>(false, "");
	}
	
//	private String find(char chars, Node current) {
//		String code = "";
//		if(current instanceof LeafNode && ((LeafNode) current).data == chars)
//			return "";
//		else if(current instanceof LeafNode)
//			return ".";
//		code = find(chars, ((InternalNode) current).right);
//		if(!code.equals("."))
//			return "1" + code;
//		code = find(chars, ((InternalNode) current).left);
//		if(!code.equals("."))
//			return "0" + code;
//		return ".";
//	}
	
	private Boolean[] stringToBits(String code) {
		Boolean[] coded = new Boolean[code.length()];
		for(int i = 0; i < coded.length; i++)
			coded[i] = code.charAt(i) == '1';
		return coded;
	}
	
	public Boolean[] efficientEncode(String inputText) {
		// NOTE: Should only go through the tree once.
		HashMap<Character, String> map = new HashMap<Character, String>();
		PriorityQueue<Pair<Node, String>> look = new PriorityQueue<Pair<Node, String>>();
		look.add(new Pair<Node, String>(root, ""));
		while(!look.isEmpty()) {
			Pair<Node, String> temp = look.poll();
			if(temp.x instanceof InternalNode) {
				look.add(new Pair<Node, String>(((InternalNode) temp.x).right, temp.y + "1"));
				look.add(new Pair<Node, String>(((InternalNode) temp.x).left, temp.y + "0"));
			}
			else
				map.put(((LeafNode) temp.x).data, temp.y);
		}
		String combine = "";
		for(int i = 0; i < inputText.length(); i++) {
			if(!map.containsKey(inputText.charAt(i)))
				throw new IllegalArgumentException();
			combine += map.get(inputText.charAt(i));
		}
		return stringToBits(combine);
	}
	
	public static Boolean equals(Boolean[] one, Boolean[] two) {
		if(one.length != two.length)
			return false;
		for(int i = 0; i < one.length; i++)	
			if(one[i] != two[i])
				return false;
		return true;
	}
	
	public static void main(String[] args) {
		// Code to see if stuff works...
		String s = "quantitative finance";
		HuffmanTree t = new HuffmanTree(s); // Creates specific Huffman Tree for "s"
		String check = "finance";
		System.out.println(Arrays.toString(t.encode(check)));
		Boolean[] sample = t.encode(check);
	//	Boolean[] answer = new Boolean[]{false,true,true,false,false,false,false,false,true,true,false,true,false,true,false,false,true,true,false,false,true,true,true,false,true,true};
	//	System.out.println(sample.equals(answer));
	//	System.out.println(t.decode(answer));
		System.out.println(t.decode(sample));
		System.out.println(t);
		// Now you can use encode, decode, and toString to interact with your specific Huffman Tree
	}
}
