package org.openslat.calculators.multiplecomponents;

import java.util.ArrayList;
import org.openslat.calculators.component.LossIMNC;
import org.openslat.control.SlatInputStore;
import org.openslat.model.collapse.LossCollapse;
import org.openslat.model.structure.Component;
import org.openslat.numerical.LNConverter;

/**
 * @author alan
 * 
 */
public class LossIM {

	private SlatInputStore sis;
	private LossIMNC lossIMNC = new LossIMNC();
	private COVLkLmIM covLkLmIM = new COVLkLmIM();

	public double meanLoss(double im) {
		ArrayList<Component> components = sis.getStructure().getComponents();

		// compute mean loss (no collapse) TODO:replicated in sigma. Combine
		double lossNC = 0;
		double lossC = 0;
		for (Component c : components) {
			lossNC = lossNC + lossIMNC.meanLoss(c, im);
		}

		// COMPUTE LOSS taking col/no col into account
		double probCollapse;
		if (sis.getCalculationOptions().isConsiderCollapse()) {
			probCollapse = sis.getStructure().getPc().getPcim().probability(im);
		} else {
			probCollapse = 0;
		}

		LossCollapse lossCollapse = new LossCollapse();
		lossCollapse.setSis(sis);
		lossC = lossCollapse.meanLoss();
		double totalLoss = lossNC * (1 - probCollapse) + lossC * (probCollapse);
		return totalLoss;
	}

	public double meanLossNC(double im) {
		ArrayList<Component> components = sis.getStructure().getComponents();

		// compute mean loss (no collapse)
		double lossNC = 0;
		double lossC = 0;
		for (Component c : components) {
			lossNC = lossNC + lossIMNC.meanLoss(c, im);
		}

		double probCollapse;
		probCollapse = 0;

		LossCollapse lossCollapse = new LossCollapse();
		lossCollapse.setSis(sis);
		lossC = lossCollapse.meanLoss();
		double totalLoss = lossNC * (1 - probCollapse) + lossC * (probCollapse);
		return totalLoss;
	}

	public double sigmaLoss(double im) {
		ArrayList<Component> components = sis.getStructure().getComponents();
		// TODO: return sigma (as coded...?) or variance?
		// compute mean loss (no collapse)
		double lossNC = 0;
		double lossC = 0;
		for (Component c : components) {
			lossNC = lossNC + lossIMNC.meanLoss(c, im);
		}

		// compute variance (no collapse)
		double varTotalLossNC = 0;
		for (int k = 0; k < components.size(); k++) {
			for (int m = 0; m <= k; ++m) {
				double cov_LIMNCkm = covLkLmIM.covLIMNCkm(components.get(k),
						components.get(m), im);

				System.out.println("varLoss:  " + varTotalLossNC);

				if (k == m) {
					varTotalLossNC = varTotalLossNC + cov_LIMNCkm;
				} else {
					varTotalLossNC = varTotalLossNC + 2 * cov_LIMNCkm;
				}
			}
		}

		// convert variance back to dispersion
		// double sigmaLossNC = Math.sqrt(Math.log(varLossNC / Math.pow(lossNC,
		// 2)
		// + 1.0));

		// COMPUTE LOSS taking col/no col into account
		double probCollapse;
		double varTotalLossC;
		if (sis.getCalculationOptions().isConsiderCollapse()) {
			probCollapse = sis.getStructure().getPc().getPcim().probability(im);
			varTotalLossC = sis.getStructure().getCollapse().getLossCollapse()
					.calculateCollapseVariance();

		} else {
			probCollapse = 0;
			varTotalLossC = 0;
		}

		LossCollapse lossCollapse = new LossCollapse();
		lossCollapse.setSis(sis);
		lossC = lossCollapse.meanLoss();
		double totalLoss = lossNC * (1 - probCollapse) + lossC * (probCollapse);

		// Brendon: is there a massive glaring error here????
		// manual page 28 top gives an overview of this calculation and the bit below shouldn't be here.

		// double varTotalLossNC = Math.pow(lossNC, 2)
		// * (Math.exp(Math.pow(lossNC, 2) - 1));
		// double varTLossC = Math.pow(lossC, 2) * (Math.exp(Math.pow(lossC, 2)
		// - 1));

		double varTotalLoss = varTotalLossNC * (1.0 - probCollapse) + varTotalLossC
				* probCollapse + Math.pow((lossNC - totalLoss), 2)
				* probCollapse * (1.0 - probCollapse); // + Math.pow((lossC -
														// totalLoss), 2)

		// convert variance back to dispersion
		double sigmaTotalLoss = Math.sqrt(Math.log(varTotalLoss
				/ Math.pow(totalLoss, 2) + 1.0));
		return LNConverter.sigma(totalLoss, varTotalLoss); //sigmaTotalLoss;
	}

	public SlatInputStore getSis() {
		return sis;
	}

	public void setSis(SlatInputStore sis) {
		this.sis = sis;
	}

	public LossIMNC getLossIMNC() {
		return lossIMNC;
	}

	public void setLossIMNC(LossIMNC lossIMNC) {
		this.lossIMNC = lossIMNC;
	}

	public COVLkLmIM getCovLkLmIM() {
		return covLkLmIM;
	}

	public void setCovLkLmIM(COVLkLmIM covLkLmIM) {
		this.covLkLmIM = covLkLmIM;
	}
}
