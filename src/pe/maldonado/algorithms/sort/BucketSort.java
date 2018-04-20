package pe.maldonado.algorithms.sort;

public class BucketSort {

	static void sort(int a[], int n) {
		
		int i, j, k, min, max, s;
		int l = a.length;
		
		int[][] buckets;
		int[] indexes;
		
		// get min and max values
		min = max = a[0]; 
		
		for (i = 1; i < l; i++) {
			if (a[i] > max) {
				max = a[i];
			}
			else if (a[i] < min) {
				min = a[i];
			}
		}
		
		// calculate interval size
		s = ((max - min) / n) + 1; 
				
		// initialize n buckets, each bucket has the same size of a[] to cover the worst case scenario
		buckets = new int[n][l];
		
		// initialize n indexes. indexes[x] represent the next position in the bucket "x" in which a new element will be added  
		indexes = new int[n];
		
		// put each element of a in their respective bucket
		for (i = 0; i < l; i++) {
			k = (a[i] - min) / s;
			buckets[k][indexes[k]++] = a[i];
		}
		
		// sort each bucket
		for (i = 0; i < n; i++) {
			InsertionSort.sort(buckets[i], 0, indexes[i]);
		}
		
		// put all back into original array
		k = 0;
		
		for (i = 0; i < n; i++) {
			for (j = 0; j < indexes[i]; j++) {
				a[k++] = buckets[i][j];
			}
		}
		
	}
	
	public static void main(String[] args) {
				
		int a[] = {232,119,100,526,345,116,599,490,118};
		
		sort(a, 25);
		
		for (int i = 0; i < a.length; i++) {
			System.out.print(Integer.toString(a[i]) + " ");
		}

	}

}
