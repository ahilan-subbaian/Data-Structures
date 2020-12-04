package hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IDLListTest {

	@Test
	void test() {
		IDLList<Integer> l1 = new IDLList<Integer>();
		assertEquals(l1.add(3),true);
		assertEquals(l1.append(5),true);
		assertEquals(l1.append(7),true);
		assertEquals("3 5 7",l1.toString());
		assertEquals("3",l1.getHead().toString());
		assertEquals("7",l1.getLast().toString());
		assertEquals(3,l1.size());
		assertEquals("3",l1.remove().toString());
		l1.add(9);
		assertEquals("9 5 7",l1.toString());
		assertEquals("7",l1.removeLast().toString());
		l1.add(9);
		l1.add(7);
		l1.add(1);
		assertEquals("1 7 9 9 5",l1.toString());
		assertEquals("7",l1.removeAt(1).toString());
		l1.add(2,8);
		assertEquals("1 9 8 9 5",l1.toString());
	}

}
