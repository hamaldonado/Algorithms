package pe.maldonado.algorithms.sort;

import java.util.Scanner;

public class CountingSort {
	
	public static void sort(int[] a) {
		
		int i, j;
		
		// calculate min and max
		int min = a[0];
		int max = a[0];
		
		for (i = 1; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
			}
			else if (a[i] < min) {
				min = a[i];
			}
		}
		
		// fill counts
		int[] counts = new int[max - min + 1];
		
		for (i = 0; i < a.length; i++) {
			counts[a[i] - min]++;
		}
		
		// rebuild a[]
		for (i = 0, j = 0; i < counts.length; i++) {
			while (counts[i]-- > 0) {
				a[j++] = i + min;
			}
		}
		
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
