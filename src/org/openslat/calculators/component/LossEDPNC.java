package org.openslat.calculators.component;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.model.structure.Component;

public class LossEDPNC {

	public double meanLoss(Component component, double edp) {
		double[] cdfValue = new double[component.getFf().getDamageStates()
				.size() + 1];
		double[] pDSedp = new double[component.getFf().getDamageStates().size() + 1];
		double meanLoss = 0;

		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			LogNormalDistribution lgnd = component.getFf().getDamageStates()
					.get(i).calcEDPOnset();
			cdfValue[i] = lgnd.cumulativeProbability(edp);
		}
		// TODO: hmm...
		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			if (cdfValue[i + 1] > cdfValue[i]) {
				cdfValue[i + 1] = cdfValue[i];
			}

			pDSedp[i] = cdfValue[i] - cdfValue[i + 1];
			// TODO: paint fragility modification goes here
			//BAB comment: In response to the above I suggest that we leave this modification at the moment and think how 
			//we can solve this more generally by introducing (non-probabilistic) dependence among the components
			//Rameriz and Miranda (Stanford report) have discussed this.  To implement its easy, so its more a matter 
			//of thinking how it can be implemented in the interface to prevent things from getting messy
			meanLoss = meanLoss
					+ component.getFf().getDamageStates().get(i).calcMeanLoss()
					* pDSedp[i];
		}
		return meanLoss;
	}

	public double sigmaLoss(Component component, double edp) {
		double[] cdfValue = new double[component.getFf().getDamageStates()
				.size() + 1];
		double[] pDSedp = new double[component.getFf().getDamageStates().size() + 1];
		double meanLoss = 0;
		double sigmaLoss = 0;
		double mu_L2_EDP = 0;

		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			LogNormalDistribution lgnd = component.getFf().getDamageStates()
					.get(i).calcEDPOnset();
			cdfValue[i] = lgnd.cumulativeProbability(edp);
		}
		// TODO: hmm...
		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			if (cdfValue[i + 1] > cdfValue[i]) {
				cdfValue[i + 1] = cdfValue[i];
			}

			pDSedp[i] = cdfValue[i] - cdfValue[i + 1];
			// TODO: paint fragility modification goes here //BAB - see comment on TODO above
			meanLoss = meanLoss
					+ component.getFf().getDamageStates().get(i).calcMeanLoss()
					* pDSedp[i];
			
			// TODO: make sure this is mean and not mu
			// could be this instead: (try this out if it doesn't work)
			// fortran: mu_L2_DS=(mu_L_DS)^2+var_L_DS
			// java: double mu_L2_DS = Math.pow(
			// component.getFf().getDamageStates().get(i)
			// .getMeanEDPOnset(), 2)
			// + LNConverter.variance(component.getFf().getDamageStates()
			// .get(i).getMuLoss(), component.getFf()
			// .getDamageStates().get(i).getSigmaLoss());
			double mu_L2_DS = Math
					.pow(component.getFf().getDamageStates().get(i)
							.calcMeanLoss(), 2)
					+ Math.pow(component.getFf().getDamageStates().get(i)
							.calcMeanLoss(), 2)
					* (Math.exp(Math.pow(component.getFf().getDamageStates()
							.get(i).calcSigmaLoss(), 2) - 1) - 1.0);
			
			mu_L2_EDP = mu_L2_EDP + mu_L2_DS * pDSedp[i];
			//System.out.println("mu_L2_EDP:  " + mu_L2_EDP);
			
		}

		if (meanLoss <= 0) {
			sigmaLoss = 0;
		} else {
			System.out.println("mu_L2_EDP:    " + Math.log(mu_L2_EDP / Math.pow(meanLoss, 2)));
			// SQUARE ROOT OF A NEGATIVE HERE!!
			sigmaLoss = Math.sqrt(Math.log(mu_L2_EDP / Math.pow(meanLoss, 2)));
		}

		return sigmaLoss;
	}

	public double meanTime(Component component, double edp) {
		double[] cdfValue = new double[component.getFf().getDamageStates()
				.size() + 1];
		double[] pDSedp = new double[component.getFf().getDamageStates().size() + 1];
		double meanTime = 0;

		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			LogNormalDistribution lgnd = component.getFf().getDamageStates()
					.get(i).calcEDPOnset();
			cdfValue[i] = lgnd.cumulativeProbability(edp);
		}
		// TODO: hmm...
		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			if (cdfValue[i + 1] > cdfValue[i]) {
				cdfValue[i + 1] = cdfValue[i];
			}

			pDSedp[i] = cdfValue[i] - cdfValue[i + 1];
			// TODO: paint fragility modification goes here  //BAB - see comment on TODO above
			meanTime = meanTime
					+ component.getFf().getDamageStates().get(i).calcMeanTime()
					* pDSedp[i];
		}
		return meanTime;
	}

	public double sigmatime(Component component, double edp) {
		double[] cdfValue = new double[component.getFf().getDamageStates()
				.size() + 1];
		double[] pDSedp = new double[component.getFf().getDamageStates().size() + 1];
		double meanTime = 0;
		double sigmaTime = 0;
		double mu_L2_EDP = 0;

		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			LogNormalDistribution lgnd = component.getFf().getDamageStates()
					.get(i).calcEDPOnset();
			cdfValue[i] = lgnd.cumulativeProbability(edp);
		}
		// TODO: hmm...
		for (int i = 0; i < component.getFf().getDamageStates().size(); i = i + 1) {
			if (cdfValue[i + 1] > cdfValue[i]) {
				cdfValue[i + 1] = cdfValue[i];
			}

			pDSedp[i] = cdfValue[i] - cdfValue[i + 1];
			// TODO: paint fragility modification goes here  //BAB - see comment on TODO above
			meanTime = meanTime
					+ component.getFf().getDamageStates().get(i)
							.calcMeanEDPOnset() * pDSedp[i];
			// mu_L2_DS=(mu_L_DS)^2+var_L_DS
			// TODO: make sure this is mean and not mu
			// could be:
			// double mu_L2_DS = Math.pow(
			// component.getFf().getDamageStates().get(i)
			// .getMeanEDPOnset(), 2)
			// + LNConverter.variance(component.getFf().getDamageStates()
			// .get(i).getMuTime(), component.getFf()
			// .getDamageStates().get(i).getSigmaTime());
			double mu_L2_DS = Math.pow(
					component.getFf().getDamageStates().get(i)
							.calcMeanEDPOnset(), 2)
					+ Math.pow(component.getFf().getDamageStates().get(i)
							.calcMeanTime(), 2)
					* (Math.exp(Math.pow(component.getFf().getDamageStates()
							.get(i).calcSigmaTime(), 2) - 1) - 1.0);
			mu_L2_EDP = mu_L2_EDP + mu_L2_DS * pDSedp[i];
		}

		if (meanTime <= 0) {
			sigmaTime = 0;
		} else {
			sigmaTime = Math.sqrt(Math.log(mu_L2_EDP / Math.pow(meanTime, 2)));
		}

		return sigmaTime;
	}

}
