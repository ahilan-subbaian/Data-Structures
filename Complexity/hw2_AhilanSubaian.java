package hw1;

public class hw2_AhilanSubaian {

	public static void method1(int n) {
		int counter = 1;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print("Operation " + counter);
				counter++;
			}
		}
	}
	
	public static void method2(int n) {
		int counter = 1;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					System.out.print("Operation " + counter);
					counter++;
				}
			}
		}
	}
	
	public static void method3(int n) {
		int counter = 1;
		for(int i = 1; i < n; i *= 10) {
			System.out.print("Operation " + counter);
			counter++;
		}
	}
	
	public static void method4(int n) {
		int counter = 1;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j *= 10) {
				System.out.print("Operation " + counter);
				counter++;
			}
		}
	}
	
	public static void method5(int n) {
		int counter = 1;
		for(int i = 1; i < n; i *= Math.pow(10, 10)) {
			System.out.print("Operation " + counter);
			counter++;
		}
	}
	
	public static void method6(int n) {
		int counter = 1;
		for(int i = 1; i < n; i *= Math.pow(10, 10)) {
			System.out.print("Operation " + counter);
			counter++;
		}
	}
	
}
