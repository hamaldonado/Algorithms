package pe.maldonado.algorithms.sort;

import java.util.Scanner;

public class QuickSort {

	static void sort(int a[]) {
		partition(a, 0, a.length - 1);
	}
	
	static void partition(int a[], int start, int end) {
		
		// has reached maximum level of recursion?
		if (end <= start) { 
			return;
		}
		
		int temp;
		int p = start; 
		int lt = start + 1;
		int gt = end;
		
		while (true) {
		
			while (lt <= end  && a[lt] < a[p]) {
				lt++;
			}

			while (gt > start && a[gt] > a[p]) {
				gt--;
			}
			
			if (lt < gt) {
				temp = a[lt];
				a[lt] = a[gt];
				a[gt] = temp;
			}
			else {
				temp = a[gt];
				a[gt] = a[p];
				a[p] = temp;
				break;
			}
		}
		
		// less than sector
		partition(a, start, gt - 1);
		
		// greater than sector
		partition(a, gt + 1, end);

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
