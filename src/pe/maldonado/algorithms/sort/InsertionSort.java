package pe.maldonado.algorithms.sort;

public class InsertionSort {

	static void sort(int a[]) {
		
		int i, j, temp;
		int l = a.length;
		
		for (i = 1; i < l; i++) {
			for (j = i; j > 0; j--) {
				if (a[j] < a[j-1]) {
					temp = a[j-1];
					a[j-1] = a[j];
					a[j] = temp;
				}
			}
		}

	}
	
	public static void main(String[] args) {

		int a[] = {7,4,9,3,5,1,6,0};
		
		sort(a);
		
		for (int i = 0; i < a.length; i++) {
			System.out.print(Integer.toString(a[i]) + " ");
		}
	}
}
