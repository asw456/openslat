package org.openslat.numerical;

/**
 * Compute the PDF value of x for a bivariate lognormal distribution with
 * lognormal mean lnmean and lognormal standard deviation lnstdev
 * 
 * @author alanslaptop
 * 
 */
public class LognormalPDFBivariate {

	/**
	 * Compute the PDF value of x for a bivariate lognormal distribution with
	 * lognormal mean lnmean and lognormal standard deviation lnstdev
	 */
	public static double evaluate(double lnMeanX, double sigmaX,
			double lnMeanY, double sigmaY, double rho, double x, double y) {

		// TODO: is this OR?
		if (x <= 0 || y <= 0) {
			return 0;
		}

		double B = Math.pow((((Math.log(x)) - lnMeanX) / sigmaX), 2) - 2.0
				* rho * ((Math.log(x) - lnMeanX) / sigmaX)
				* ((Math.log(y) - lnMeanY) / sigmaY)
				+ Math.pow(((Math.log(y) - lnMeanY) / sigmaY), 2);

		double A = 1 / (2.0 * Math.PI * x * y * sigmaX * sigmaY * Math
				.sqrt(1.0 - Math.pow(rho, 2)));

		return A * Math.exp(-1.0 / (2.0 * (1.0 - Math.pow(rho, 2))) * B);
	}
}
