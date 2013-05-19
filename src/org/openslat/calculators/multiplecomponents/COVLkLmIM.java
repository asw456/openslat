package org.openslat.calculators.multiplecomponents;

import org.openslat.calculators.component.LossIMNC;
import org.openslat.control.SlatInputStore;
import org.openslat.model.structure.Component;

public class COVLkLmIM {

	private SlatInputStore sis;

	public double covLIMNCkm(Component componentk, Component componentm,
			double im) {

		double corr_lnEDPij_IM = 0;
		double corr_Lossij_DS = 0;
		double corr_DSij_EDP = 0;

		// if components are the same, covariance equals the variance
		if (componentk.equals(componentm)) {
			LossIMNC lossIMNC = new LossIMNC();
			// now convert to non-log form and add to continuing sum
			// return COV_LIMNCkm
			return Math.pow(lossIMNC.muLoss(componentk, im), 2)
					* Math.exp(Math.pow(lossIMNC.sigmaLoss(componentk, im), 2) - 1);
		}

		// DETERMINE aleatory correlation for EDPIM relationship
		if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_EDPIM() == 0) {
			corr_lnEDPij_IM = 0;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_EDPIM() == 1) {
			corr_lnEDPij_IM = 1;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_EDPIM() == 2) {
			if (componentk.getEdp().equals(componentm.getEdp())) {
				corr_lnEDPij_IM = 1;
			} else {
				//EDPIMCorrelations edpIMCorrelations = new EDPIMCorrelations();
				corr_lnEDPij_IM = EDPIMCorrelations.edpCorrelation(componentk,
						componentm, im);
			}
		}
		// end of computing CORR_lnEDPilnEDPj|IM

		// Loss EDP correlation

		// determine aleatory correlation for loss-DS relationship
		if (sis.getCalculationOptions().getCorrelationOptions().getCOR_LDS() == 0) {
			corr_Lossij_DS = 0;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_LDS() == 1) {
			corr_lnEDPij_IM = 1;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_LDS() == 2) {
			corr_lnEDPij_IM = 2; // The exact value is determined in the
									// integration scheme for each EDP
		}
		// end of computing CORR_lnLossilnLossj|DS

		// determine aleatory correlation for DS-EDP relationship
		if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_DSEDP() == 0) {
			corr_DSij_EDP = 0;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_DSEDP() == 1) {
			corr_DSij_EDP = 1;
		} else if (sis.getCalculationOptions().getCorrelationOptions()
				.getCOR_DSEDP() == 2) {
			corr_DSij_EDP = 2; // The exact value is determined in the
								// integration scheme for each EDP
		}
		// end of computing CORR_DSij|EDP

		if (corr_lnEDPij_IM == 0 && corr_Lossij_DS == 0 && corr_DSij_EDP == 0) {
			// return COV_LIMNCkm
			return 0;
		}

		ELkLmIM eLkLmIM = new ELkLmIM();
		double expectedLosskLossmIM = eLkLmIM.muLijIM(componentk, componentm,
				im);

		LossIMNC lossIMNC = new LossIMNC();
		double cov_LIMNCkm = expectedLosskLossmIM
				- lossIMNC.meanLoss(componentk, im)
				* lossIMNC.meanLoss(componentm, im); // cov(x,y)=E(xy)-E(x)E(y)

		double lnCOR_Lossij_IM = cov_LIMNCkm
				/ (lossIMNC.meanLoss(componentk, im)
						* Math.sqrt(Math.exp(Math.pow(
								lossIMNC.sigmaLoss(componentk, im), 2)) - 1)
						* lossIMNC.meanLoss(componentm, im) * Math
							.sqrt(Math.exp(Math.pow(
									lossIMNC.sigmaLoss(componentm, im), 2)) - 1));

		if (lnCOR_Lossij_IM > 1) {
			cov_LIMNCkm = lossIMNC.meanLoss(componentk, im)
					* Math.sqrt(Math.exp(Math.pow(
							lossIMNC.sigmaLoss(componentk, im), 2)))
					* lossIMNC.meanLoss(componentm, im)
					* Math.sqrt(Math.exp(Math.pow(
							lossIMNC.sigmaLoss(componentm, im), 2)));
		}
		return cov_LIMNCkm;
	}

	public SlatInputStore getSlatInputStore() {
		return sis;
	}

	public void setSlatInputStore(SlatInputStore SlatInputStore) {
		this.sis = SlatInputStore;
	}
}
