package pe.maldonado.algorithms.sort;

import java.util.Scanner;

public class QuickSort {

	public static void sort(int a[]) {
		sort(a, 0, a.length - 1);
	}
	
	public static void sort(int a[], int start, int end) {

		if (start < end) {

			// Partition and get the position of the pivot
			int p = partition (a, start, end);
			
			// sort 'less than' sector
			sort(a, start, p - 1);
			
			// sort 'greater than' sector
			sort(a, p + 1, end);
		
		}

	}
	
	// Hoare's partition algorithm
	static int partition(int a[], int start, int end) {
	
		int p = start; 
		int lt = start + 1;
		int gt = end;
		
		while (lt <= gt) {
			
			while (lt <= end && a[lt] < a[p]) { 
				lt++;
			}
	
			while (a[gt] > a[p]) {
				gt--;
			}
			
			if (lt <= gt) {
				// swap 'greater than' element with the 'less than' element 
				swap(a, lt, gt);
				
				lt++;
				gt--;
			}

		}
		
		// Put the pivot in its final position
		swap(a, p, gt);
		
		return gt;
		
	}
	
	static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
	    int[] a;
	    int n;
	    
	    n = in.nextInt();
	    a = new int[n];
	    
	    for (int i = 0; i < n; i++) {
	    	 a[i] = in.nextInt();
	    }

	    sort(a);
	    
	    for (int i = 0; i < n; i++) {
	    	System.out.print(Integer.toString(a[i]) + " ");
	    }
	    System.out.print("\n");
	    
	    in.close();
	}
	
	
}
