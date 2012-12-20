/**
 * 
 */
package org.openslat.numerical;

import java.util.Random;

/**
 * Class so that different random number generators can be implemented if desired
 * 
 * @author alan
 *
 */
public class RNGenerator {

	//nine..nine..nine..nine..nine...
	public static double uniformRN(){
		Random generator = new Random();
		return generator.nextDouble();
	}
	
}
