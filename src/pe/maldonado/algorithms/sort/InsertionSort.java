package pe.maldonado.algorithms.sort;

public class InsertionSort {

	public static void sort(int a[], int start, int end) {
		
		int i, j, temp;
		
		for (i = start + 1; i < end; i++) {
			for (j = i; j > start; j--) {
				if (a[j] < a[j-1]) {
					temp = a[j-1];
					a[j-1] = a[j];
					a[j] = temp;
				}
			}
		}

	}
	
	public static void sort(int a[]) {
		sort(a, 0, a.length);
	}
	
	public static void main(String[] args) {

		int a[] = {7,4,9,3,5,1,6,0};
		
		sort(a);
		
		for (int i = 0; i < a.length; i++) {
			System.out.print(Integer.toString(a[i]) + " ");
		}
	}
}
