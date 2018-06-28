package pe.maldonado.algorithms.basic;

import java.util.Random;

public class RandomGenerator {

	public static void randomize(int a[], int min, int max) {
		
		Random r = new Random();
		int l = a.length;
	    
		for (int i = 0; i < l; i++){
	    	a[i] = r.nextInt(max - min) + min;
	    }
		
	}

}
