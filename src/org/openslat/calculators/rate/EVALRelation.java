/**
 * 
 */
package org.openslat.calculators.rate;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.openslat.calculators.multiplecomponents.LossIM;
import org.openslat.model.structure.Structure;

/**
 * @author alan
 * 
 */
public class EVALRelation {

	UnivariateIntegrator integrator = new SimpsonIntegrator();
	
	private LossIM lossIM;
	
	public double meanAnnualLoss(final Structure structure) {

		double a = 0;
		double b = 1;
		double k = 0;
		double n = 0;
		double z = 0;
		double h;
		double maxevals = 1000000;
		double eALi = 0;

		do {
			++k;
			n = Math.pow(2, k);
			h = (b - a) / n;
			for (int l = 0; l < n / 2; ++l) {
				z = (2 * l - 1) * h;
				eALi = integrandEALi(structure, z);
			}
		} while (eALi < 0.000001 && n < maxevals);

		a = z - h;
		b = z + h;

		final EVALRelation evalRelation = this;
		double eVAL = integrator.integrate(10000, new UnivariateFunction() {
			public double value(double t) {
				return evalRelation.integrandEALi(structure, t);
			}
		}, a, b);

		return eVAL;
	}
	
	public double varianceAnnualLoss(final Structure structure){

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
				eALi2 = integrandEALiSquared(structure, z);
			}
		} while (eALi2 < 0.000001 && n < maxevals);

		a = z - h;
		b = z + h;

		final EVALRelation evalRelation = this;
		double integrand = integrator.integrate(10000, new UnivariateFunction() {
			public double value(double t) {
				return evalRelation.integrandEALiSquared(structure, t);
			}
		}, a, b);

		//TODO: wrong equation - missing a + 1??
		//SQRT(LOG(integral/EVAL(1)**2))
		
		return Math.sqrt(Math.log(integrand/Math.pow(this.meanAnnualLoss(structure),2)));
	}
	
	private double integrandEALi(Structure structure, double t) {

		if (t < 0)
			return 0;
		if (t > 1)
			return 0;

		double im = 1 / t - 1;

		double eALi = (1 / Math.pow(t, 2)) * lossIM.meanLoss(im)
				* structure.getIm().retrieveImr().derivative(im);
		return eALi;
	}

	private double integrandEALiSquared(Structure structure, double t) {

		if (t < 0)
			return 0;
		if (t > 1)
			return 0;

		double im = 1 / t - 1;

		double totalLoss = lossIM.meanLoss(im);

		// TODO: use LNConverter here?
		double varTLossEDP = Math.pow(totalLoss, 2)
				* (Math.exp(Math.pow(lossIM.sigmaLoss(im), 2)) - 1);

		// TODO: correct brackets?
		double EALi = (1 / Math.pow(t, 2))
				* (Math.pow(totalLoss, 2) + varTLossEDP)
				* structure.getIm().retrieveImr().derivative(im);
		return EALi;
	}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

}
