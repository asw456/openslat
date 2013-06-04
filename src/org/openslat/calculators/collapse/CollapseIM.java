package org.openslat.calculators.collapse;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.util.FastMath;
import org.openslat.calculators.component.LossEDPNC;
import org.openslat.control.SlatInputStore;
import org.openslat.model.structure.Component;
import org.openslat.numerical.LNConverter;
import org.openslat.numerical.LognormalPDF;
import org.openslat.numerical.MagnitudeAdaptiveQuadratureIntegrator;

public class CollapseIM {

	private SlatInputStore sis;
	private UnivariateIntegrator integrator;

	public double meanLoss(SlatInputStore sis, double imIn) {

		// if (sis.getImMeanLossMap().containsKey(imIn)) {
		// return sis.getImMeanLossMap().get(imIn);
		// }

		this.sis = sis;

		final CollapseIM collapseIM = this;
		final double im = imIn;
		integrator = new SimpsonIntegrator();
		// integrator = new RombergIntegrator();

		long startTime = System.nanoTime();
		double temp = integrator.integrate(10000000, new UnivariateFunction() {
			public double value(double t) {
				return collapseIM.calculateMeanIntegrand(t);
			}
		}, 0, (1 / imIn - 1) );
		long endTime = System.nanoTime();
		long duration = endTime - startTime;
		System.err.println("collapse integral duration: " + duration);

		//sis.getImMeanLossMap().put(imIn, temp);

		return temp;
	}

	private double calculateMeanIntegrand(double t) {
		double epsilon = 1e-10;
		if (FastMath.abs(t) < epsilon) {
			return 0;
		}
		if (FastMath.abs(1 - t) < epsilon) {
			return 0;
		} else {
			double edp = (1 / t) - 1;
			
			LogNormalDistribution lgnd = (LogNormalDistribution) sis.getStructure().getPc().getPcim().calcDistribution();

			// double fEDPIM = LognormalPDF.evaluate(lgnd.getScale(),
			// lgnd.getShape(), edp);
			double fEDPIM = lgnd.density(im);
			double eLossEDP = lossEDPNC.meanLoss(sis, edp);

			// L_IMi=1.0/t**2*EL_EDP*fEDPIM
			return (1.0 / Math.pow(t, 2)) * eLossEDP * fEDPIM;
		}
	}

	//public double muLoss(Component component, double im) {
	//	return LNConverter.mu(meanLoss(component, im), varLoss(component, im));
	//}

	//public double sigmaLoss(Component component, double im) {
	//	return LNConverter.sigma(meanLoss(component, im),
	//			varLoss(component, im));
	//}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}
}
