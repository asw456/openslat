package org.openslat.calculators.collapse;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.openslat.model.im.IM;

/**
 * Collapse hazard relationship or Collapse-Rate. Represents the annual
 * frequency of collapse.
 * 
 * CR can be constructed with an IMR relationship and an PCIM relationship and
 * an integration method.
 * 
 * @author Alan Williams
 */
public class CR {

	private IM im;
	private PC pC;
	private UnivariateIntegrator integrator = new SimpsonIntegrator();

	/**
	 * Returns the rate of exceedance for a specified probability of collapse.
	 * 
	 * @param edp
	 *            probability of collapse
	 * @return rate of exceedance
	 */
	public double evaluate() {

		final CR cr = this;
		return integrator.integrate(10000, new UnivariateFunction() {
			public double value(double t) {
				return cr.pC.getPcim().getDistribution()
						.cumulativeProbability(1 / t - 1)
						* Math.abs(cr.im.getImr().derivative(1 / t - 1))
						* (-1 / Math.pow(t, 2));
			}
		}, 0, 1);
	}

	public PC getpC() {
		return pC;
	}

	public void setpC(PC pC) {
		this.pC = pC;
	}

	public IM getIm() {
		return im;
	}

	public void setIm(IM im) {
		this.im = im;
	}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

}
