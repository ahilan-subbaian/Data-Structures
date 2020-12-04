package hw1;

public class BinaryNumber {
	//Data Fields
	private int [] data;
	private boolean overflow;
	
	//Constructor
	BinaryNumber(int length){
		data = new int[length]; 
	}
	BinaryNumber(String str){
		data = new int [str.length()];
		for(int i=0;i<str.length();i++) {
			char let = str.charAt(i);
			if (let == '1') {
				data[i] = 1;
			} else if(let == '0'){
				data[i] = 0;
			} else {
				System.out.println("There is a character, " + str.charAt(i) + " in the string.");
			}
		}
	}
	//To String Method
	public String toString() {
		if (overflow == true) {
			return "Overflow";
		} else {
			String estr = "";
			for(int i=0;i<data.length;i++) {
				estr += data[i];
			}
			return estr;
		}
	}
	
	public int[] getInnerArray() {
		return data;
	}
	
	//Methods
	//method to get the length
	public int getLength(){
		return data.length;
	}
	//method to get the value of the string at a given index
	public int getDigit(int index){
		if (getLength() < (index+1)){
			System.out.println("Index out of bounds.");
			return -1;
		}
		else {
			return data[index];
		}
	}
	
	//converts binary to decimal form
	public int toDecimal() {
		int sum = 0;
		for (int i = getLength()-1;i>=0;i--) {
			sum += (Math.pow(2, getLength()-i-1)*data[i]);
		}
		return sum;
	}
	
	public void bitShift(int direction, int amount) {
		int[] newData;
		if (direction != -1 && direction != 1) {
			throw new NullPointerException();
		}
		if (direction == 1) {
			newData = new int[data.length - amount];
			for(int i = 0; i < data.length - amount; i++) {
				newData[i] = data[i];
			}
			
		}
		else{
			newData = new int[data.length + amount];
			for (int i = 0; i < data.length; i++) {
				newData[i] = data[i];
			}
			for (int i = data.length; i < data.length + amount; i++) {
				newData[i] = 0;
			}
		}
		data = new int[newData.length];
		for (int i = 0; i < newData.length; i++) {
			data[i] = newData[i];
		}
	}
	
	public static int[] bwor(BinaryNumber bn1, BinaryNumber bn2) {
		if (bn1.getLength() != bn2.getLength()) {
			throw new NullPointerException();
		}
		
		int[] newData = new int[bn1.getLength()];
		for (int i = 0; i < bn1.getLength(); i++) {
			if (bn1.getInnerArray()[i] == 1 || bn2.getInnerArray()[i] == 1) {
				newData[i] = 1;
			}
			else {
				newData[i] = 0;
			}
		}
		return newData;
	}
	
	public static int[] bwand(BinaryNumber bn1, BinaryNumber bn2) {
		if (bn1.getLength() != bn2.getLength()) {
			throw new NullPointerException();
		}
		
		int[] newData = new int[bn1.getLength()];
		for (int i = 0; i < bn1.getLength(); i++) {
			if (bn1.getInnerArray()[i] == 1 && bn2.getInnerArray()[i] == 1) {
				newData[i] = 1;
			}
			else {
				newData[i] = 0;
			}
		}
		return newData;
	}
	
	//sums two binary numbers
	public void add(BinaryNumber aBinaryNumber) {
		if (this.getLength() != aBinaryNumber.getLength()) {
			System.out.println("Lengths do not coincide.");
		} else {
			int carry = 0;
			for(int i=(this.getLength()-1);i>=0;i--) {
				System.out.println(carry);
				if((this.getDigit(i) + aBinaryNumber.getDigit(i) + carry)==0) {
					data[i] = 0;
					carry = 0;
				}
				else if((this.getDigit(i) + aBinaryNumber.getDigit(i) + carry)==1) {
					data[i] = 1;
					carry = 0;
				}
				else if((this.getDigit(i) + aBinaryNumber.getDigit(i) + carry)==2) {
					data[i] = 0;
					carry = 1;
				}
				else if((this.getDigit(i) + aBinaryNumber.getDigit(i) + carry)==3) {
					data[i] = 1;
					carry = 1;
				}
			}
			if(carry == 1) {
				overflow = true;
			}
		}
	}
	//sets overflow value to true
	public void clearOverflow() {
		overflow = false;
	}


public static void main(String args[]) {
	BinaryNumber one = new BinaryNumber("1100");
	BinaryNumber two = new BinaryNumber("1010");
	int [] three = bwand(one, two);
	for (int i = 0; i < three.length; i++) {
		System.out.println(three[i]);
	}
}
}
