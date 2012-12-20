package org.openslat.numerical;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.LogNormalDistribution;

/**
 * @author alan
 * 
 */
public class FitLogNormalDistribution {

	/**
	 * method that calculates the parameters for a lognormal distribution using
	 * MLE formulas from Wikipedia
	 * 
	 */
	public static LogNormalDistribution calculateLogNormalParameters(
			ArrayList<Double> randomSamples) {
		double sumTerms = 0;
		double mu = 0;
		double sigma = 0;
		for (int i = 1; i < randomSamples.size(); i++) {
			sumTerms = sumTerms + randomSamples.get(i);
		}
		mu = sumTerms / randomSamples.size();
		sumTerms = 0;
		for (int i = 1; i < randomSamples.size(); i++) {
			sumTerms = sumTerms + Math.pow((randomSamples.get(i) - mu), 2);
		}
		sigma = sumTerms / randomSamples.size();

		return new LogNormalDistribution(mu, sigma);
	}
	
	/**
	 * method that calculates the parameters for a lognormal distribution using
	 * MLE formulas from Wikipedia
	 * 
	 */
	public static LogNormalDistribution calculateLogNormalParameters(
			double[] randomSamples) {
		double sumTerms = 0;
		double mu = 0;
		double sigma = 0;
		for (int i = 1; i < randomSamples.length; i++) {
			sumTerms = sumTerms + randomSamples[i];
		}
		mu = sumTerms / randomSamples.length;
		sumTerms = 0;
		for (int i = 1; i < randomSamples.length; i++) {
			sumTerms = sumTerms + Math.pow((randomSamples[i] - mu), 2);
		}
		sigma = sumTerms / randomSamples.length;

		return new LogNormalDistribution(mu, sigma);
	}

}
