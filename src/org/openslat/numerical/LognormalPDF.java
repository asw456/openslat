package org.openslat.numerical;

/**
 * Compute the PDF value of x for a lognormal distribution with lognormal mean
 * lnmean and lognormal standard deviation lnstdev
 * 
 * @author alanslaptop
 * 
 */
public class LognormalPDF {

	/**
	 * Compute the PDF value of x for a lognormal distribution with lognormal
	 * mean lnmean and lognormal standard deviation lnstdev
	 */
	public static double evaluate(double lnMeanX, double sigmaX, double x) {

		double lnstdev = sigmaX;

		if (x < 0) {
			return 0;
		} else {
			return Math.exp(-0.5
					* Math.pow((Math.log(x) - lnMeanX) / lnstdev, 2))
					/ (x * lnstdev * Math.sqrt(2 * Math.PI));
		}
	}
}