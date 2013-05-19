package org.openslat.model.fragilityfunctions;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.control.SlatInputStore;
import org.openslat.numerical.LNConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class DamageState {

	// TODO: time not yet done. only edp and loss
	@JsonIgnore
	private SlatInputStore sis;
	@JsonIgnore
	private double randDSEDP;
	@JsonIgnore
	private double randLDS;

	private double meanEDPOnset;
	private double epistemicStdDev_Mean_LNedp;

	private double sigmaEDPOnset;
	private double epistemicStdDev_Var_LNedp;

	private double upperLimitMeanLoss;
	private double lowerLimitMeanLoss;
	private int numberComponentsUpperLimitLoss;
	private int numberComponentsLowerLimitLoss;
	@JsonIgnore //calculated from the upper and lower limits
	private double meanLoss;
	private double epistemicStdDev_Mean_LNloss;

	private double sigmaLoss;
	private double epistemicStdDev_Var_LNloss;

	private double upperLimitMeanTime;
	private double lowerLimitMeanTime;
	private int numberComponentsUpperLimitTime;
	private int numberComponentsLowerLimitTime;
	@JsonIgnore //calculated from upper and lower limits
	private double meanTime;
	private double epistemicStdDev_Mean_LNtime;

	private double sigmaTime;
	private double epistemicStdDev_Var_LNtime;

	public void setMeans(int numberOfComponents) {

		if (numberOfComponents <= numberComponentsLowerLimitLoss) {
			meanLoss = lowerLimitMeanLoss;
		}
		if (numberOfComponents >= numberComponentsUpperLimitLoss) {
			meanLoss = upperLimitMeanLoss;
		} else {
			meanLoss = (numberOfComponents - numberComponentsLowerLimitLoss)
					/ (numberComponentsLowerLimitLoss - numberComponentsUpperLimitLoss)
					* (upperLimitMeanLoss - lowerLimitMeanLoss)
					+ lowerLimitMeanLoss;
		}

		if (numberOfComponents <= numberComponentsLowerLimitTime) {
			meanTime = lowerLimitMeanTime;
		}
		if (numberOfComponents >= numberComponentsUpperLimitTime) {
			meanTime = upperLimitMeanTime;
		} else {
			meanTime = (numberOfComponents - numberComponentsLowerLimitTime)
					/ (numberComponentsLowerLimitTime - numberComponentsUpperLimitTime)
					* (upperLimitMeanTime - lowerLimitMeanTime)
					+ lowerLimitMeanTime;
		}
	}

	/**
	 * Returns the Lognormal EDP onset distribution
	 * 
	 * @return
	 */
	public LogNormalDistribution calcEDPOnset() {
		return new LogNormalDistribution(calcMuEDPOnset(), calcSigmaEDPOnset());
	}

	/**
	 * Returns the mean EDP for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcMeanEDPOnset() {
		// TODO: should these be the same randDSEDP for both mean and sigma?
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertDSEDP()) {
			double medianEDPDS = Math.exp(Math.log(meanEDPOnset) - 0.5
					* Math.pow(sigmaEDPOnset, 2) + randDSEDP
					* epistemicStdDev_Mean_LNedp);
			sigmaEDPOnset = Math.sqrt((Math.pow(sigmaEDPOnset, 2) + randDSEDP
					* epistemicStdDev_Var_LNedp));
			meanEDPOnset = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
			return meanEDPOnset;
		}
		return meanEDPOnset;
	}

	/**
	 * Returns the lognormal mu for mean EDP for the onset of the DS uses the
	 * result of the generateEpistemicArrays method in the FragilityFunction
	 * class
	 * 
	 * @return
	 */
	public double calcMuEDPOnset() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertDSEDP()) {
			double medianEDPDS = Math.exp(Math.log(meanEDPOnset) - 0.5
					* Math.pow(sigmaEDPOnset, 2) + randDSEDP
					* epistemicStdDev_Mean_LNedp);
			sigmaEDPOnset = Math.sqrt((Math.pow(sigmaEDPOnset, 2) + randDSEDP
					* epistemicStdDev_Var_LNedp));
			meanEDPOnset = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
			return LNConverter.muGivenMeanSigma(meanEDPOnset, sigmaEDPOnset);
		}
		return LNConverter.muGivenMeanSigma(meanEDPOnset, sigmaEDPOnset);
	}

	/**
	 * Returns the sigma EDP for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcSigmaEDPOnset() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertDSEDP()) {
			sigmaEDPOnset = Math.sqrt((Math.pow(sigmaEDPOnset, 2) + randDSEDP
					* epistemicStdDev_Var_LNedp));
			return sigmaEDPOnset;
		}

		return sigmaEDPOnset;
	}

	/**
	 * Returns the Lognormal Loss distribution
	 * 
	 * @return
	 */
	public LogNormalDistribution calcLoss() {
		return new LogNormalDistribution(calcMuLoss(), calcSigmaLoss());
	}

	/**
	 * Returns the mean Loss for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcMeanLoss() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanLoss) - 0.5
					* Math.pow(sigmaLoss, 2) + randLDS
					* epistemicStdDev_Mean_LNloss);
			sigmaLoss = Math.sqrt((Math.pow(sigmaLoss, 2) + randDSEDP
					* epistemicStdDev_Var_LNloss));
			meanLoss = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
			return meanLoss;
		}
		return meanLoss;

	}

	/**
	 * Returns the lognormal mu for mean Loss for the onset of the DS uses the
	 * result of the generateEpistemicArrays method in the FragilityFunction
	 * class
	 * 
	 * @return
	 */
	public double calcMuLoss() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanLoss) - 0.5
					* Math.pow(sigmaLoss, 2) + randLDS
					* epistemicStdDev_Mean_LNloss);
			sigmaLoss = Math.sqrt((Math.pow(sigmaLoss, 2) + randLDS
					* epistemicStdDev_Var_LNloss));
			meanLoss = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
			return LNConverter.muGivenMeanSigma(meanLoss, sigmaLoss);
		}
		return LNConverter.muGivenMeanSigma(meanLoss, sigmaLoss);

	}

	/**
	 * Returns the sigma Loss for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcSigmaLoss() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			sigmaLoss = Math.sqrt((Math.pow(sigmaLoss, 2) + randLDS
					* epistemicStdDev_Var_LNloss));
			return sigmaLoss;
		}
		return sigmaLoss;
	}

	// // start here
	/**
	 * Returns the Lognormal Time distribution
	 * 
	 * @return
	 */
	public LogNormalDistribution calcTime() {
		return new LogNormalDistribution(calcMuTime(), calcSigmaTime());
	}

	/**
	 * Returns the mean Time for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcMeanTime() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanTime) - 0.5
					* Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Mean_LNtime);
			sigmaTime = Math.sqrt((Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Var_LNtime));
			meanTime = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaTime, 2));
			return meanTime;
		}
		return meanTime;

	}

	/**
	 * Returns the lognormal mu for mean Time for the onset of the DS uses the
	 * result of the generateEpistemicArrays method in the FragilityFunction
	 * class
	 * 
	 * @return
	 */
	public double calcMuTime() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanTime) - 0.5
					* Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Mean_LNtime);
			sigmaTime = Math.sqrt((Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Var_LNtime));
			meanTime = medianEDPDS * Math.exp(0.5 * Math.pow(sigmaTime, 2));
			return LNConverter.muGivenMeanSigma(meanTime, sigmaTime);
		}
		return LNConverter.muGivenMeanSigma(meanTime, sigmaTime);

	}

	/**
	 * Returns the sigma Time for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double calcSigmaTime() {
		if (sis.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncert()
				&& sis.getCalculationOptions().getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			sigmaTime = Math.sqrt((Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Var_LNtime));
			return sigmaTime;
		}

		return sigmaTime;
	}

	public double getRandDSEDP() {
		return randDSEDP;
	}

	public void setRandDSEDP(double randDSEDP) {
		this.randDSEDP = randDSEDP;
	}

	public double getRandLDS() {
		return randLDS;
	}

	public void setRandLDS(double randLDS) {
		this.randLDS = randLDS;
	}

	public double getEpistemicStdDev_Mean_LNedp() {
		return epistemicStdDev_Mean_LNedp;
	}

	public void setEpistemicStdDev_Mean_LNedp(double epistemicStdDev_Mean_LNedp) {
		this.epistemicStdDev_Mean_LNedp = epistemicStdDev_Mean_LNedp;
	}

	public double getEpistemicStdDev_Var_LNedp() {
		return epistemicStdDev_Var_LNedp;
	}

	public void setEpistemicStdDev_Var_LNedp(double epistemicStdDev_Var_LNedp) {
		this.epistemicStdDev_Var_LNedp = epistemicStdDev_Var_LNedp;
	}

	public double getUpperLimitMeanLoss() {
		return upperLimitMeanLoss;
	}

	public void setUpperLimitMeanLoss(double upperLimitMeanLoss) {
		this.upperLimitMeanLoss = upperLimitMeanLoss;
	}

	public double getLowerLimitMeanLoss() {
		return lowerLimitMeanLoss;
	}

	public void setLowerLimitMeanLoss(double lowerLimitMeanLoss) {
		this.lowerLimitMeanLoss = lowerLimitMeanLoss;
	}

	public int getNumberComponentsUpperLimitLoss() {
		return numberComponentsUpperLimitLoss;
	}

	public void setNumberComponentsUpperLimitLoss(
			int numberComponentsUpperLimitLoss) {
		this.numberComponentsUpperLimitLoss = numberComponentsUpperLimitLoss;
	}

	public int getNumberComponentsLowerLimitLoss() {
		return numberComponentsLowerLimitLoss;
	}

	public void setNumberComponentsLowerLimitLoss(
			int numberComponentsLowerLimitLoss) {
		this.numberComponentsLowerLimitLoss = numberComponentsLowerLimitLoss;
	}

	public double getEpistemicStdDev_Mean_LNloss() {
		return epistemicStdDev_Mean_LNloss;
	}

	public void setEpistemicStdDev_Mean_LNloss(
			double epistemicStdDev_Mean_LNloss) {
		this.epistemicStdDev_Mean_LNloss = epistemicStdDev_Mean_LNloss;
	}

	public double getEpistemicStdDev_Var_LNloss() {
		return epistemicStdDev_Var_LNloss;
	}

	public void setEpistemicStdDev_Var_LNloss(double epistemicStdDev_Var_LNloss) {
		this.epistemicStdDev_Var_LNloss = epistemicStdDev_Var_LNloss;
	}

	public double getUpperLimitMeanTime() {
		return upperLimitMeanTime;
	}

	public void setUpperLimitMeanTime(double upperLimitMeanTime) {
		this.upperLimitMeanTime = upperLimitMeanTime;
	}

	public double getLowerLimitMeanTime() {
		return lowerLimitMeanTime;
	}

	public void setLowerLimitMeanTime(double lowerLimitMeanTime) {
		this.lowerLimitMeanTime = lowerLimitMeanTime;
	}

	public int getNumberComponentsUpperLimitTime() {
		return numberComponentsUpperLimitTime;
	}

	public void setNumberComponentsUpperLimitTime(
			int numberComponentsUpperLimitTime) {
		this.numberComponentsUpperLimitTime = numberComponentsUpperLimitTime;
	}

	public int getNumberComponentsLowerLimitTime() {
		return numberComponentsLowerLimitTime;
	}

	public void setNumberComponentsLowerLimitTime(
			int numberComponentsLowerLimitTime) {
		this.numberComponentsLowerLimitTime = numberComponentsLowerLimitTime;
	}

	public double getEpistemicStdDev_Mean_LNtime() {
		return epistemicStdDev_Mean_LNtime;
	}

	public void setEpistemicStdDev_Mean_LNtime(
			double epistemicStdDev_Mean_LNtime) {
		this.epistemicStdDev_Mean_LNtime = epistemicStdDev_Mean_LNtime;
	}

	public double getEpistemicStdDev_Var_LNtime() {
		return epistemicStdDev_Var_LNtime;
	}

	public void setEpistemicStdDev_Var_LNtime(double epistemicStdDev_Var_LNtime) {
		this.epistemicStdDev_Var_LNtime = epistemicStdDev_Var_LNtime;
	}

	public void setMeanEDPOnset(double meanEDPOnset) {
		this.meanEDPOnset = meanEDPOnset;
	}

	public void setSigmaEDPOnset(double sigmaEDPOnset) {
		this.sigmaEDPOnset = sigmaEDPOnset;
	}

	public void setMeanLoss(double meanLoss) {
		this.meanLoss = meanLoss;
	}

	public void setSigmaLoss(double sigmaLoss) {
		this.sigmaLoss = sigmaLoss;
	}

	public void setMeanTime(double meanTime) {
		this.meanTime = meanTime;
	}

	public void setSigmaTime(double sigmaTime) {
		this.sigmaTime = sigmaTime;
	}

	public SlatInputStore getOpenslat() {
		return sis;
	}

	public void setSis(SlatInputStore openslat) {
		this.sis = openslat;
	}

	public double getMeanEDPOnset() {
		return meanEDPOnset;
	}

	public double getSigmaEDPOnset() {
		return sigmaEDPOnset;
	}

	public double getMeanLoss() {
		return meanLoss;
	}

	public double getSigmaLoss() {
		return sigmaLoss;
	}

	public double getMeanTime() {
		return meanTime;
	}

	public double getSigmaTime() {
		return sigmaTime;
	}
}
