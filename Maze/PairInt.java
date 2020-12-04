package hw4;

public class PairInt {
	private int x;
	private int y;
	
	public PairInt(int a, int b) {
		x = a;
		y = b;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int a) {
		x = a;
	}
	
	public void setY(int a) {
		y = a;
	}
	
	public boolean equals(Object a) {
		if(a == null)
			return false;
		if(!(a instanceof PairInt))
			return false;
		PairInt b = (PairInt) a;
		return x == b.getX() && y == b.getY();
	}
	
	public String toString() {
		return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
	}
	
	public PairInt copy() {
		return new PairInt(x,y);
	}

}