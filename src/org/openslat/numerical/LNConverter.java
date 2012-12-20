/**
 * 
 */
package org.openslat.numerical;

/**
 * @author Alan.Williams
 * 
 */
public class LNConverter {

	public static double mean(double mu, double sigma) {
		return Math.exp(mu + Math.pow(sigma, 2) / 2.0);
	}

	public static double variance(double mu, double sigma) {
		return Math.exp(2 * mu + Math.pow(sigma, 2))
				* (Math.exp(Math.pow(sigma, 2)) - 1);
	}

	// mu ??
	public static double mu(double mean, double variance) {
		return Math.log(Math.pow(mean, 2)
				/ Math.sqrt(variance + Math.pow(mean, 2)));
	}

	// sigma ??
	public static double sigma(double mean, double variance) {
		return Math.sqrt(Math.log(variance / Math.pow(mean, 2) + 1));
	}

	public static double muGivenMeanSigma(double mean, double sigma) {
        return Math.log(mean) - 0.5*Math.pow(sigma, 2);
	}

//	probably wrong but might work in some situations?? Maple's second solution
//	public static double muGivenMeanSigma2(double mean, double sigma) {
//        return 0.5*Math.log(-Math.pow(mean,2)/(Math.exp(Math.pow(sigma, 2))-1));
//	}

}

/*
 * lognstat - Lognormal mean and variance Syntax [M,V] = lognstat(mu,sigma)
 * 
 * Description [M,V] = lognstat(mu,sigma) returns the mean of and variance of
 * the lognormal distribution with parameters mu and sigma. mu and sigma are the
 * mean and standard deviation, respectively, of the associated normal
 * distribution. mu and sigma can be vectors, matrices, or multidimensional
 * arrays that all have the same size, which is also the size of M and V. A
 * scalar input for mu or sigma is expanded to a constant array with the same
 * dimensions as the other input.
 * 
 * The normal and lognormal distributions are closely related. If X is
 * distributed lognormally with parameters u and s, then log(X) is distributed
 * normally with mean u and standard deviation s.
 * 
 * The mean m and variance v of a lognormal random variable are functions of u
 * and s that can be calculated with the lognstat function. They are:
 * 
 * m = exp() v = exp()()
 * 
 * A lognormal distribution with mean m and variance v has parameters
 * 
 * u = log() s = sqrt(log())
 * 
 * Examples Generate one million lognormally distributed random numbers with
 * mean 1 and variance 2:
 * 
 * m = 1; v = 2; mu = log((m^2)/sqrt(v+m^2)); sigma = sqrt(log(v/(m^2)+1));
 * 
 * [M,V]= lognstat(mu,sigma) M = 1 V = 2.0000
 * 
 * X = lognrnd(mu,sigma,1,1e6);
 * 
 * MX = mean(X) MX = 0.9974 VX = var(X) VX = 1.9776
 */

