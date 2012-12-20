package org.openslat.model.fragilityfunctions;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.control.CalculationOptions;
import org.openslat.numerical.LNConverter;

public class DamageState {

	// TODO: time not yet done. only edp and loss

	private CalculationOptions calculationOptions;
	private double randDSEDP;
	private double randLDS;

	private double meanEDPOnset;
	private double epistemicStdDev_Mean_LNedp;

	private double sigmaEDPOnset;
	private double epistemicStdDev_Var_LNedp;

	private double upperLimitMeanLoss;
	private double lowerLimitMeanLoss;
	private int numberComponentsUpperLimitLoss;
	private int numberComponentsLowerLimitLoss;
	private double meanLoss;
	private double epistemicStdDev_Mean_LNloss;

	private double sigmaLoss;
	private double epistemicStdDev_Var_LNloss;

	private double upperLimitMeanTime;
	private double lowerLimitMeanTime;
	private int numberComponentsUpperLimitTime;
	private int numberComponentsLowerLimitTime;
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
	public LogNormalDistribution getEDPOnset() {
		return new LogNormalDistribution(getMuEDPOnset(), getSigmaEDPOnset());
	}

	/**
	 * Returns the mean EDP for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double getMeanEDPOnset() {
		//TODO: should these be the same randDSEDP for both mean and sigma?
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertDSEDP()) {
			double medianEDPDS = Math.exp(Math.log(meanEDPOnset) - 0.5
					* Math.pow(sigmaEDPOnset, 2)
					+ randDSEDP
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
	public double getMuEDPOnset() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertDSEDP()) {
			double medianEDPDS = Math.exp(Math.log(meanEDPOnset) - 0.5
					* Math.pow(sigmaEDPOnset, 2)
					+ randDSEDP
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
	public double getSigmaEDPOnset() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
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
	public LogNormalDistribution getLoss() {
		return new LogNormalDistribution(getMuLoss(), getSigmaLoss());
	}

	/**
	 * Returns the mean Loss for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double getMeanLoss() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanLoss) - 0.5
					* Math.pow(sigmaLoss, 2)
					+ randLDS
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
	public double getMuLoss() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanLoss) - 0.5
					* Math.pow(sigmaLoss, 2)
					+ randLDS
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
	public double getSigmaLoss() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
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
	public LogNormalDistribution getTime() {
		return new LogNormalDistribution(getMuTime(), getSigmaTime());
	}

	/**
	 * Returns the mean Time for the onset of the DS uses the result of the
	 * generateEpistemicArrays method in the FragilityFunction class
	 * 
	 * @return
	 */
	public double getMeanTime() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanTime) - 0.5
					* Math.pow(sigmaTime, 2)
					+ randLDS
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
	public double getMuTime() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			double medianEDPDS = Math.exp(Math.log(meanTime) - 0.5
					* Math.pow(sigmaTime, 2)
					+ randLDS
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
	public double getSigmaTime() {
		if (calculationOptions.getEpistemicUncertOptions().isEpistemicUncert()
				&& calculationOptions.getEpistemicUncertOptions()
						.isEpistemicUncertLDS()) {
			sigmaTime = Math.sqrt((Math.pow(sigmaTime, 2) + randLDS
					* epistemicStdDev_Var_LNtime));
			return sigmaTime;
		}

		return sigmaTime;
	}

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
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

	public void setNumberComponentsUpperLimitLoss(int numberComponentsUpperLimitLoss) {
		this.numberComponentsUpperLimitLoss = numberComponentsUpperLimitLoss;
	}

	public int getNumberComponentsLowerLimitLoss() {
		return numberComponentsLowerLimitLoss;
	}

	public void setNumberComponentsLowerLimitLoss(int numberComponentsLowerLimitLoss) {
		this.numberComponentsLowerLimitLoss = numberComponentsLowerLimitLoss;
	}

	public double getEpistemicStdDev_Mean_LNloss() {
		return epistemicStdDev_Mean_LNloss;
	}

	public void setEpistemicStdDev_Mean_LNloss(double epistemicStdDev_Mean_LNloss) {
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

	public void setNumberComponentsUpperLimitTime(int numberComponentsUpperLimitTime) {
		this.numberComponentsUpperLimitTime = numberComponentsUpperLimitTime;
	}

	public int getNumberComponentsLowerLimitTime() {
		return numberComponentsLowerLimitTime;
	}

	public void setNumberComponentsLowerLimitTime(int numberComponentsLowerLimitTime) {
		this.numberComponentsLowerLimitTime = numberComponentsLowerLimitTime;
	}

	public double getEpistemicStdDev_Mean_LNtime() {
		return epistemicStdDev_Mean_LNtime;
	}

	public void setEpistemicStdDev_Mean_LNtime(double epistemicStdDev_Mean_LNtime) {
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
}
