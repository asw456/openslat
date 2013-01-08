package org.openslat.calculators.component;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.calculators.component.LossEDPNC;
import org.openslat.model.structure.Component;
import org.openslat.numerical.LNConverter;
import org.openslat.numerical.LognormalPDF;

public class LossIMNC {

	private Component component;
	private UnivariateIntegrator integrator;

	public double meanLoss(Component component, double imIn) {
		this.component = component;

		final LossIMNC lossIMNC = this;
		final double im = imIn;
		integrator = new SimpsonIntegrator(); // temp
		double temp = integrator.integrate(10000000, new UnivariateFunction() {
			public double value(double t) {
				return lossIMNC.calculateMeanIntegrand(t,im);
			}
		}, 0, 1);

		return temp;
	}

	private double calculateMeanIntegrand(double t, double im) {
		if (t <= 0) {
			return 0;
		} else if (t >= 1) {
			return 0;
		} else {
			LogNormalDistribution lgnd = (LogNormalDistribution) component
					.getEdp().retrieveEdpIM().getDistributionFunction()
					.distribution(im);
			double sigmaEDP = lgnd.getShape();
			double muEDP = lgnd.getScale(); // lnMeanEDP

			double edp = 1 / t - 1;
			LossEDPNC lossEDPNC = new LossEDPNC();
			double fEDPIM = LognormalPDF.evaluate(muEDP, sigmaEDP, edp);
			double eLossEDP = lossEDPNC.meanLoss(component, edp);

			// L_IMi=1.0/t**2*EL_EDP*fEDPIM
			return 1.0 / Math.pow(t, 2) * eLossEDP * fEDPIM;
		}
	}

	public double varLoss(Component component, double imIn) {
		this.component = component;

		final LossIMNC lossIMNC = this;
		final double im = imIn;
		integrator = new SimpsonIntegrator(); // temp
		double temp = integrator.integrate(10000000, new UnivariateFunction() {
			public double value(double t) {
				return lossIMNC.calculateVarIntegrand(t, im);
			}
		}, 0, 1);

		return temp;

	}

	private double calculateVarIntegrand(double t, double im) {
		if (t <= 0) {
			return 0;
		} else if (t >= 1) {
			return 0;
		} else {
			LogNormalDistribution lgnd = (LogNormalDistribution) component
					.getEdp().retrieveEdpIM().getDistributionFunction()
					.distribution(im);
			double sigmaEDP = lgnd.getShape();
			double muEDP = lgnd.getScale(); // lnMeanEDP

			double edp = 1 / t - 1;
			LossEDPNC lossEDPNC = new LossEDPNC();
			double fEDPIM = LognormalPDF.evaluate(muEDP, sigmaEDP, edp);
			double meanLossEDP = lossEDPNC.meanLoss(component, edp);
			double sigmaLossEDP = lossEDPNC.sigmaLoss(component, edp);

			// This gives variance in L|EDP. as
	        // LossEDP(1) is E[X] and LossEDP(2) is beta[X] so Var[X] = E[X]^2*(EXP(beta[X]^2)-1)
			double varLedp = Math.pow(meanLossEDP,2)*(Math.exp(Math.pow(sigmaLossEDP, 2))-1.0);
					
			return 1.0/Math.pow(t, 2)*(varLedp+Math.pow((meanLossEDP-meanLoss(component,im)),2))*fEDPIM;
		}
	}

	public double muLoss(Component component, double im){
		return LNConverter.mu(meanLoss(component, im), varLoss(component, im));
	}
	
	public double sigmaLoss(Component component, double im){
		return LNConverter.sigma(meanLoss(component, im), varLoss(component, im));
	}
	
	
	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}

}
