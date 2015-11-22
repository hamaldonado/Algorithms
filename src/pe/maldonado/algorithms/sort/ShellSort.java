package pe.maldonado.algorithms.sort;

public class ShellSort {
	
	// ShellSort con calculo dinamico de incrementos en base al tamaño del array
	static void sort(int a[]) {
			
		int i, j, temp;
		int l = a.length;
		int h = 1;
		
		// Hallamos el h más grande posible usando la serie de Knuth (3x + 1 => 1, 4, 13, 40, ..)
		while (h < l/2) {
			h = h * 3 + 1;
		}
		
		while (h >= 1) {
			// h-sort		
			for (i = h; i < l; i++) {
				for (j = i; j > 0; j-= h) {
					if (a[j] < a[j-1]) {
						temp = a[j-1];
						a[j-1] = a[j];
						a[j] = temp;
					}
				}
			}
			h = (h - 1) / 3;
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
