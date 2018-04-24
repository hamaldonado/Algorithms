package pe.maldonado.algorithms.sort;

import java.util.Scanner;

public class QuickSort3 {
	
	public static void sort(int a[]) {
		sort(a, 0, a.length - 1);
	}
	
	public static void sort(int a[], int start, int end) {

		// switch to Insertion Sort if number of elements <= 11
		if (end - start <= 11) {
			InsertionSort.sort(a, start, end);
			return;
		}
		
		// calculate the pivot using the median-of-three and put it in front of the array
		int m = median(a, start, start + (end - start) / 2, end);
		swap (a, start, m);
		
		// Partition using three-way partitioning
		int p = a[start];
		int lt = start;
		int gt = end;
		int i = lt + 1;
				
		while (i <= gt) {
			
			if (a[i] < p) {
				swap (a, i, lt);
				i++;
				lt++;
			}
			else if (a[i] > p) {
				swap (a, i, gt);
				gt--;
			} 
			else {
				i++;
			}
			
		}
		
		/* 
		 * At this point: 
		 * 		a[start]..a[lt-1] < a[lt]..a[gt] < a[gt+1]..a[end]
		 * 		a[lt]..a[gt] are all equal to p (pivot)
		 */
			
		// sort 'less than' sector
		sort(a, start, lt - 1);
			
		// sort 'greater than' sector
		sort(a, gt + 1, end);
		
	}
	
	// median-of-three
	static int median(int[] a, int x, int y, int z) {
		
		if (a[x] < a[y]) {
			if (a[y] < a[z]) {
				return y;
			}
			else if (a[x] < a[z]) {
				return z;
			}
			else {
				return x;
			}
		}
		else {
			if (a[z] < a[y]) {
				return y;
			}
			else if (a[z] < a[x]) {
				return z;
			}
			else {
				return x;
			}
		}

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
