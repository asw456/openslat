package org.openslat.calculators.rate;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.calculators.multiplecomponents.LossIM;
import org.openslat.control.SlatInputStore;
import org.openslat.model.collapse.CollapseDemolition;

/**
 * @author alan
 * 
 */
public class LossR {

	private UnivariateIntegrator integrator = new SimpsonIntegrator();
	private SlatInputStore sis;

	public double lossRate(double inputLoss) {

		double a = 0;
		double b = 1;
		double k = 0;
		double n = 0;
		double z = 0;
		double h;
		double maxevals = 1000000;
		double eALi2 = 0;

		do {
			++k;
			n = Math.pow(2, k);
			h = (b - a) / n;
			for (int l = 0; l < n / 2; ++l) {
				z = (2 * l - 1) * h;
				eALi2 = integrand(z, inputLoss);
			}
		} while (eALi2 < 0.000001 && n < maxevals);

		a = z - h;
		b = z + h;

		final LossRate lossRate = this;
		final double loss = inputLoss;
		double integrand = integrator.integrate(10000,
				new UnivariateFunction() {
					public double value(double t) {
						return lossRate.integrand(t, loss);
					}
				}, a, b);

		return integrand;
	}

	private double integrand(double t, double loss) {

		if (t < 0)
			return 0;
		if (t > 1)
			return 0;

		double im = 1 / t - 1;

		LossIM lossIM = new LossIM();
		//lossIM.setCalculationOptions(calculationOptions);

		double lnMeanL = Math.log(lossIM.meanLossNC(im) - 0.5
				* Math.pow(lossIM.sigmaLoss(im), 2));
		double probLIMNC = new LogNormalDistribution(lnMeanL,
				lossIM.sigmaLoss(im)).cumulativeProbability(loss);

		double gLIM; 
		if (sis.getCalculationOptions().isConsiderCollapse()) {
			double lnMeanLC = sis.getStructure().getCollapse().getLossCollapse()
					.meanLoss();
			double sigmaLC = sis.getStructure().getCollapse().getLossCollapse()
					.sigmaLoss();
			double probLIMC = new LogNormalDistribution(lnMeanLC, sigmaLC)
					.cumulativeProbability(loss);

			double probCollapse = sis.getStructure().getPc().getPcim()
					.probability(im);
		
			gLIM = (1-probCollapse)*(1-probLIMNC) + probCollapse*(1-probLIMC); 
		} else {
			gLIM = 1-probLIMNC;
		}

		double lossRi = (1/Math.pow(t,2))*gLIM*sis.getStructure().getIm().retrieveImr().derivative(im);
		return lossRi;
	}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

	public SlatInputStore getOpenslat() {
		return sis;
	}

	public void setOpenslat(SlatInputStore openslat) {
		this.sis = openslat;
	}
}
