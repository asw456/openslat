package org.openslat.models.distribution;

import org.apache.commons.math3.distribution.LogNormalDistribution;

public class SimpleLogNormalDistribution extends LogNormalDistribution {

	private double mean;
	private double sigma;
	
	
	public double getMean() {
		return mean;
	}
	public void setMean(double mean) {
		this.mean = mean;
	}
	public double getSigma() {
		return sigma;
	}
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}
	
}
