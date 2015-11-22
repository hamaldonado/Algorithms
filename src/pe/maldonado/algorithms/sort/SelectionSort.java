package pe.maldonado.algorithms.sort;

public class SelectionSort {

	static void sort(int a[]) {
		
		int i, j, min, temp;
		int l = a.length;
		
		for (i = 0; i < l; i++) {
			min = i;
			for (j = i+1; j < l; j++) {
				if (a[j] < a[min]) {
					min = j;
				}
			}
			temp = a[i];
			a[i] = a[min];
			a[min] = temp;
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
