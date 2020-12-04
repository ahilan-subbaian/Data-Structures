package hw1;

//Ahilan Subbaian

import java.util.ArrayList;

public class IDLList<E> {
	
	//Private Node Class
	private class Node<E> {
		//Data fields
		E data;
		Node<E> next;
		Node <E> prev;
		
		//Operations
		Node(E elem){
			this.data = elem;
		}
		Node (E elem, Node<E> prev, Node<E> next){
			data = elem;
			this.next = next;
			this.prev = prev;
		}
	}

	//Data Fields
	Node<E> head;
	Node<E> tail;
	int size;
	ArrayList<Node<E>> indices;
	
	//Operations
	public IDLList() {
		head = null;
		tail = null;
		size = 0;
		indices = new ArrayList<Node<E>>();
	}
		
	//adds elem at position index (counting from wherever head is)
	public boolean add (int index, E elem) {
		if (index<0 || index > size) {
			throw new IllegalArgumentException("invalid index.");
		}
		if(index == 0) {
			return add(elem);
		}
		if (index==size) {
			return append(elem);
		}
		Node<E> temp = indices.get(index);
		Node<E> newNode = new Node<E>(elem, temp.prev, temp);
		indices.add(index,newNode);
		temp.prev.next = newNode;
		temp.prev = newNode;
		size++;
		return true;
	}
	
	//adds elem at the head (i.e. it becomes the first element of the list).
	public boolean add (E elem) {
		if(head == null) {
			Node<E> temp = new Node<E>(elem);
			indices.add(0,temp);
			head = temp;
			tail = temp;
			size++;
			return true;
		}
		Node<E> temp = new Node<E>(elem,null,head);
		indices.add(0,temp);
		head.prev = temp;
		head = temp;
		size++;
		return true;
	}
	
	//adds elem as the new last element of the list (i.e. at the tail).
	public boolean append (E elem) {
		Node<E> temp = indices.get(size-1);
		Node<E> newNode = new Node<E>(elem,temp,null);
		indices.add(size,newNode);
		temp.next = newNode;
		tail = newNode;
		size++;
		return true;
	}
	
	//returns the object at position index from the head
	public E get (int index) {
		if (index<0 || index> size-1) {
			throw new IllegalArgumentException("Invalid index.");
		}
		return indices.get(index).data;
	}
	
	//returns the object at the head
	public E getHead () {
		if (head == null) {
			throw new IllegalStateException("Head equals null.");
		}
		return head.data;
	}
	
	//returns the object at the tail
	public E getLast () {
		if(tail == null) {
			throw new IllegalStateException("Tail equals null.");
		}
		return tail.data;
	}
	
	//returns the list size
	public int size() {
		return size;
	}
	
	//removes and returns the element at the head
	public E remove () {
		if(head == null) {
			throw new IllegalStateException("Array List is empty.");
		}
		E save = head.data;
		head = head.next;
		head.prev = null;
		size--;
		indices.remove(0);
		return save;
	}
	
	//removes and returns the element at the tail
	public E removeLast () {
		if(tail == null) {
			throw new IllegalStateException("Array List is empty.");
		}
		E save = tail.data;
		tail = tail.prev;
		tail.next = null;
		indices.remove(size-1);
		size--;
		return save;
	}
	
	//removes and returns the element at the index.
	public E removeAt (int index) {
		if (index<0 || index> size-1) {
			throw new IllegalStateException("Invalid index.");
		}
		if(index == 0) {
			return remove();
		}
		if(index == size-1) {
			return removeLast();
		}
		Node<E> temp = indices.get(index);
		temp.prev.next = temp.next;
		temp.next.prev = temp.prev;
		size--;
		indices.remove(index);
		return temp.data;
	}
	
	//removes the first occurrence of elem in the list and returns true
	public boolean remove (E elem) {
		int i =0;
		Node<E> current = indices.get(0);
		while(current != null) {
			if(current.data == elem) {
				removeAt(i);
				return true;
			}
			current = current.next;
			i++;
		}
		return false;
	}
	
	//That presents a string representation of the list
	public String toString() {
		Node<E> current = head;
		String s = "";
		while(current != null) {
			s += current.data;
			if (current.next != null) {
				s += " ";
			}
			current = current.next;
		}
		return s;
	}
	
	public static void main(String[] args) {
		IDLList<Integer> l1 = new IDLList<Integer>();
		l1.add(3);
		l1.append(5);
		l1.append(9);
		l1.add(3,7);
		System.out.println(l1);
		System.out.println(l1.get(2));
		System.out.println(l1.getHead());
	}
	
}
