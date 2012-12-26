package org.openslat.model.collapse;

import java.util.ArrayList;
import org.openslat.calculators.multiplecomponents.LDSCorrelation;
import org.openslat.control.Openslat;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.numerical.LNConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class LossCollapse {

	@JsonIgnore
	private Openslat openslat;
	
	private double randMeanLoss;
	private double randSigmaLoss;

	private double additionalLoss;
	private double additionalTime;

	private double meanLoss;
	private double epistemicStdDev_Mean_LNloss;

	private double sigmaLoss;
	private double epistemicStdDev_Var_LNloss;

	private double meanTime;
	private double epistemicStdDev_Mean_LNtime;

	private double sigmaTime;
	private double epistemicStdDev_Var_LNTime;

	public double meanLoss() {

		ArrayList<Component> components = new ArrayList<Component>();
		for (PerformanceGroup pg : openslat.getStructure()
				.getPerformanceGroups()) {
			components.addAll(pg.getComponents());
		}

		if (openslat.getCalculationOptions().isCollLossType()) {

			// TODO: does this work? if not return meanLoss
			if (openslat.getCalculationOptions().getEpistemicUncertOptions().isEpistemicUncert()
					&& openslat.getCalculationOptions().getEpistemicUncertOptions()
							.isEpistemicUncertLossCollapse()) {
				double medianLoss = Math.exp(Math.log(meanLoss) - 0.5
						* Math.pow(sigmaLoss, 2) + randMeanLoss
						* epistemicStdDev_Mean_LNloss);
				sigmaLoss = Math.sqrt((Math.pow(sigmaLoss, 2) + randSigmaLoss
						* epistemicStdDev_Var_LNloss));
				meanLoss = medianLoss * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
				return meanLoss;
			}
			return meanLoss;
		}

		// if not CollLossType: need to compute by component

		meanLoss = 0;
		double varLoss = 0;
		LDSCorrelation ldsCorrelation = new LDSCorrelation();
		for (int k = 0; k < components.size(); ++k) {
			meanLoss = meanLoss
					+ components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMeanLoss();
			varLoss = varLoss
					+ LNConverter.variance(components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMuLoss(),
							components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getSigmaLoss());

			for (int l = 0; l < k; ++l) {
				varLoss = varLoss
						+ ldsCorrelation.corr_Lossij_DS(components.get(k),components.get(l))* 
						Math.sqrt(LNConverter.variance(components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMuLoss(),
								components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getSigmaLoss())
								* LNConverter.variance(components.get(l).getFf().getDamageStates().get(components.get(l).getFf().getDamageStates().size() - 1).getMuLoss(),
										components.get(l).getFf().getDamageStates().get(components.get(l).getFf().getDamageStates().size() - 1).getSigmaLoss()));

			}

		}

		
		sigmaLoss = LNConverter.sigma(meanLoss, varLoss);
		meanLoss = meanLoss*(1+additionalLoss);
				
		return meanLoss;
	}

	public double sigmaLoss() {

		ArrayList<Component> components = new ArrayList<Component>();
		for (PerformanceGroup pg : openslat.getStructure()
				.getPerformanceGroups()) {
			components.addAll(pg.getComponents());
		}

		if (openslat.getCalculationOptions().isCollLossType()) {

			// TODO: does this work? if not return meanLoss
			if (openslat.getCalculationOptions().getEpistemicUncertOptions().isEpistemicUncert()
					&& openslat.getCalculationOptions().getEpistemicUncertOptions()
							.isEpistemicUncertLossCollapse()) {
				double medianLoss = Math.exp(Math.log(meanLoss) - 0.5
						* Math.pow(sigmaLoss, 2) + randMeanLoss
						* epistemicStdDev_Mean_LNloss);
				sigmaLoss = Math.sqrt((Math.pow(sigmaLoss, 2) + randSigmaLoss
						* epistemicStdDev_Var_LNloss));
				meanLoss = medianLoss * Math.exp(0.5 * Math.pow(sigmaLoss, 2));
				return sigmaLoss;
			}
			return sigmaLoss;
		}

		// if not CollLossType: need to compute by component
		
		meanLoss = 0;
		double varLoss = 0;
		LDSCorrelation ldsCorrelation = new LDSCorrelation();
		for (int k = 0; k < components.size(); ++k) {
			meanLoss = meanLoss
					+ components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMeanLoss();
			varLoss = varLoss
					+ LNConverter.variance(components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMuLoss(),
							components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getSigmaLoss());

			for (int l = 0; l < k; ++l) {
				varLoss = varLoss
						+ ldsCorrelation.corr_Lossij_DS(components.get(k),components.get(l))* 
						Math.sqrt(LNConverter.variance(components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getMuLoss(),
								components.get(k).getFf().getDamageStates().get(components.get(k).getFf().getDamageStates().size() - 1).getSigmaLoss())
								* LNConverter.variance(components.get(l).getFf().getDamageStates().get(components.get(l).getFf().getDamageStates().size() - 1).getMuLoss(),
										components.get(l).getFf().getDamageStates().get(components.get(l).getFf().getDamageStates().size() - 1).getSigmaLoss()));

			}

		}

		
		sigmaLoss = LNConverter.sigma(meanLoss, varLoss);
		meanLoss = meanLoss*(1+additionalLoss);
				
		return sigmaLoss;
	}

	public double getRandLoss() {
		return randMeanLoss;
	}

	public void setRandLoss(double randLoss) {
		this.randMeanLoss = randLoss;
	}

	public double getAdditionalLoss() {
		return additionalLoss;
	}

	public void setAdditionalLoss(double additionalLoss) {
		this.additionalLoss = additionalLoss;
	}

	public double getAdditionalTime() {
		return additionalTime;
	}

	public void setAdditionalTime(double additionalTime) {
		this.additionalTime = additionalTime;
	}

	public void setMeanLoss(double meanLoss) {
		this.meanLoss = meanLoss;
	}

	public double getEpistemicStdDev_Mean_LNloss() {
		return epistemicStdDev_Mean_LNloss;
	}

	public void setEpistemicStdDev_Mean_LNloss(
			double epistemicStdDev_Mean_LNloss) {
		this.epistemicStdDev_Mean_LNloss = epistemicStdDev_Mean_LNloss;
	}

	public void setSigmaLoss(double sigmaLoss) {
		this.sigmaLoss = sigmaLoss;
	}

	public double getEpistemicStdDev_Var_LNloss() {
		return epistemicStdDev_Var_LNloss;
	}

	public void setEpistemicStdDev_Var_LNloss(double epistemicStdDev_Var_LNloss) {
		this.epistemicStdDev_Var_LNloss = epistemicStdDev_Var_LNloss;
	}

	public double getMeanTime() {
		return meanTime;
	}

	public void setMeanTime(double meanTime) {
		this.meanTime = meanTime;
	}

	public double getEpistemicStdDev_Mean_LNtime() {
		return epistemicStdDev_Mean_LNtime;
	}

	public void setEpistemicStdDev_Mean_LNtime(
			double epistemicStdDev_Mean_LNtime) {
		this.epistemicStdDev_Mean_LNtime = epistemicStdDev_Mean_LNtime;
	}

	public double getSigmaTime() {
		return sigmaTime;
	}

	public void setSigmaTime(double sigmaTime) {
		this.sigmaTime = sigmaTime;
	}

	public double getEpistemicStdDev_Var_LNTime() {
		return epistemicStdDev_Var_LNTime;
	}

	public void setEpistemicStdDev_Var_LNTime(double epistemicStdDev_Var_LNTime) {
		this.epistemicStdDev_Var_LNTime = epistemicStdDev_Var_LNTime;
	}

	public double getRandMeanLoss() {
		return randMeanLoss;
	}

	public void setRandMeanLoss(double randMeanLoss) {
		this.randMeanLoss = randMeanLoss;
	}

	public double getRandSigmaLoss() {
		return randSigmaLoss;
	}

	public void setRandSigmaLoss(double randSigmaLoss) {
		this.randSigmaLoss = randSigmaLoss;
	}

	public Openslat getOpenslat() {
		return openslat;
	}

	public void setOpenslat(Openslat openslat) {
		this.openslat = openslat;
	}

}
