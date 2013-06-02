/**
 * 
 */
package org.openslat.model.collapse;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.numerical.FitLogNormalDistribution;
import org.openslat.numerical.LNConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Probability of Collapse given IM Relationship. Class for the prob of collapse
 * given IM. Represented by a log-normal distribution (moment-based analytical
 * collapse fragility function) Number of epistemic uncertainties will be
 * multiple classes but should equal no. of epistemic uncertanties for the
 * structural response
 * 
 * @author Alan Williams
 * 
 */
@JsonSerialize
public class PCIM {

	private String name;
	@JsonIgnore
	private LogNormalDistribution distribution;
	private double mean;
	private double sigma;
	private Double epistemicWeight;

	public double probability(double im) {
		return distribution.cumulativeProbability(im);
	}

	public void generateDistribution(double mean, double sigma) {
		distribution = new LogNormalDistribution(LNConverter.muGivenMeanSigma(
				mean, sigma), sigma);
	}

	public void generateDistribution(double[] imValues) {
		this.distribution = FitLogNormalDistribution
				.calculateLogNormalParameters(imValues);
	}

	public LogNormalDistribution calcDistribution() {
		return (new LogNormalDistribution(LNConverter.muGivenMeanSigma(this.mean, this.sigma),this.sigma));
	}

	public void replaceDistribution(LogNormalDistribution distribution) {
		this.distribution = distribution;
	}

	public Double getEpistemicWeight() {
		return epistemicWeight;
	}

	public void setEpistemicWeight(Double epistemicWeight) {
		this.epistemicWeight = epistemicWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
