/**
 * 
 */
package org.openslat.model.collapse;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.numerical.FitLogNormalDistribution;
import org.openslat.numerical.LNConverter;

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
public class PCIM {

	private LogNormalDistribution distribution;
	private Double epistemicWeight;

	public double probability(double im){
		return distribution.cumulativeProbability(im);
	}
	
	public void setDistribution(double mean, double sigma) {
		distribution = new LogNormalDistribution(LNConverter.muGivenMeanSigma(
				mean, sigma), sigma);
	}

	public void setDistribution(double[] imValues){
		this.distribution = FitLogNormalDistribution.calculateLogNormalParameters(imValues);
	}
	
	public LogNormalDistribution getDistribution() {
		return distribution;
	}

	public void setDistribution(LogNormalDistribution distribution) {
		this.distribution = distribution;
	}

	public Double getEpistemicWeight() {
		return epistemicWeight;
	}

	public void setEpistemicWeight(Double epistemicWeight) {
		this.epistemicWeight = epistemicWeight;
	}

}
