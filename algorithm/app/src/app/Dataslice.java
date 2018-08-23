package app;

import java.util.ArrayList;
import java.util.List;

/*
 * In order to perform the FFT, the length of the data must be raised to the power of n. 
 * Therefore, this class finds the largest n based on the data length. 
 * It also finds the value of m next to n based on the remaining alpha delta.
 * m is greater than 6 (assume srate is 50).
 * input length = 2^n + 2^m + ... + a
 * a discard to less than 6 powers of two.
 */

public class Dataslice {
	
	public List<Integer> Dataslice(int n) {	
		List<Integer> session = new ArrayList<Integer>();
		
		for(int m = n; m > 125*3;) {
			int i = 0;
			for (int k = m ; k != 1; i++)
				k = k / 2;
			session.add(i);
			m = (int)(m - Math.pow(2, i));
			}
		return session;
	}
}
