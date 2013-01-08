package org.openslat.calculators.rate;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.util.FastMath;
import org.openslat.model.collapse.PC;
import org.openslat.model.edp.EDP;
import org.openslat.model.im.IM;
import org.openslat.numerical.MagnitudeAdaptiveQuadratureIntegrator;

/**
 * EDP hazard relationship or EDP-Rate EPDR represents the seismic demand - rate
 * of exceedance relationship where seismic demand is measured by a known
 * engineering demand parameter such as interstory drift or floor acceleration.
 * 
 * EDPR can be constructed with an im relationship and an EDPIM relationship and
 * an integration method.
 * 
 * @author Alan Williams
 */
public class EDPR {

	//private UnivariateIntegrator integrator = new SimpsonIntegrator();
	private UnivariateIntegrator integrator = new MagnitudeAdaptiveQuadratureIntegrator();
	
	/**
	 * Returns the rate of exceedance for a specified seismic demand.
	 * 
	 * @param edp
	 *            seismic demand
	 * @return rate of exceedance
	 */
	public double edpRate(final double val, final EDP edp, final IM im, final PC pc) {

		if (pc != null) {
			// TODO : Check that this works!!!
			// final EDPR edpr = new SessionContext.getEJBObject(this);

			UnivariateFunction temp = new UnivariateFunction() {
				public double value(double t) {
					return (edp.retrieveEdpIM().getDistributionFunction()
							.distribution(1 / t - 1).cumulativeProbability(val)
							* (1 - pc.getPcim().getDistribution()
									.cumulativeProbability(1 / t - 1)) + pc
							.getPcim().getDistribution()
							.cumulativeProbability(1 / t - 1))
							* FastMath.abs(im.retrieveImr().derivative(1 / t - 1))
							* (-1 / FastMath.pow(t, 2));
				}
			};
			
			return integrator.integrate(1000000000, temp, 0, 1);
		}

		else {
			// TODO : Check that this works!!!
			return integrator.integrate(1000000000, new UnivariateFunction() {
				public double value(double t) {
					return (edp.retrieveEdpIM().getDistributionFunction()
							.distribution(1 / t - 1).cumulativeProbability(val))
							* FastMath.abs(im.retrieveImr().derivative(1 / t - 1))
							* (-1 / FastMath.pow(t, 2));
				}
			}, 0, 1);
		}
	}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

}
