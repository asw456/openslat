/**
 * 
 */
package org.openslat.calculators.multiplecomponents;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.openslat.calculators.multiplecomponents.ELijEDPij;
import org.openslat.control.SlatMainController;
import org.openslat.model.structure.Component;
import org.openslat.numerical.LognormalPDF;
import org.openslat.numerical.LognormalPDFBivariate;
import org.openslat.numerical.LNConverter;

/**
 * @author alanslaptop
 * 
 */
public class ELkLmIM {

	private SlatMainController slatMC;
	private ELijEDPij eLijEDPij = new ELijEDPij();
	private UnivariateIntegrator integrator;
	private Component componentk;
	private Component componentm;

	public double muLijIM(Component componentk, Component componentm, double im) {
		this.componentk = componentk;
		this.componentm = componentm;

		double z = 5.0;
		double sigmaI = LNConverter.sigma(componentk.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean(),
				componentk.getEdp().getEdpIM().getDistributionFunction()
						.distribution(im).getNumericalVariance());

		double a = 1 / (componentk.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean()
				* Math.exp(-0.5 * Math.pow(sigmaI, 2) + z * sigmaI) + 1);
		double b = 1 / (componentk.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean()
				* Math.exp(-0.5 * Math.pow(sigmaI, 2) - z * sigmaI) + 1);

		// check for special case in which |COR_EDPIM|=1.0 therefore the
		// integral reduces to a single one
		// if
		// (slatMC.getCalculationOptions().getCorrelationOptions().getCOR_EDPIM()
		// == 1) {
		// return innerIntegral(im,0);
		// }

		// CALCULATE OVERALL OUTER INTERVAL
		final double imVal = im;
		final ELkLmIM eLkLmIM = this;
		double result = integrator.integrate(10000000,
				new UnivariateFunction() {

					public double value(double t) {
						return eLkLmIM.innerIntegral(imVal, t);
					}
				}, a, b);
		// TODO: should these be 0 and 1 or a and b

		return result;
	}

	private double innerIntegral(double im, double tk) {

		double z = 5.0;
		double sigmaJ = LNConverter.sigma(componentm.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean(),
				componentm.getEdp().getEdpIM().getDistributionFunction()
						.distribution(im).getNumericalVariance());

		double a = 1 / (componentm.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean()
				* Math.exp(-0.5 * Math.pow(sigmaJ, 2) + z * sigmaJ) + 1);
		double b = 1 / (componentm.getEdp().getEdpIM()
				.getDistributionFunction().distribution(im).getNumericalMean()
				* Math.exp(-0.5 * Math.pow(sigmaJ, 2) - z * sigmaJ) + 1);

		final double imVal = im;
		final double tK = tk;
		final ELkLmIM eLkLmIM = this;
		return integrator.integrate(10000000, new UnivariateFunction() {
			public double value(double t) {
				// special case - if COR_EDPIM=1 then double integral reduces to
				// single integral
				if (slatMC.getCalculationOptions().getCorrelationOptions()
						.getCOR_EDPIM() == 1) {
					if (tK <= 0) {
						return 0;
					} else if (tK >= 1) {
						return 0;
					} else {
						double edpK = 1.0 / tK - 1;

						double lnMeanX = LNConverter.mu(eLkLmIM.componentk
								.getEdp().getEdpIM().getDistributionFunction()
								.distribution(imVal).getNumericalMean(),
								eLkLmIM.componentk.getEdp().getEdpIM()
										.getDistributionFunction()
										.distribution(imVal)
										.getNumericalVariance());
						double lnMeanY = LNConverter.mu(eLkLmIM.componentm
								.getEdp().getEdpIM().getDistributionFunction()
								.distribution(imVal).getNumericalMean(),
								eLkLmIM.componentm.getEdp().getEdpIM()
										.getDistributionFunction()
										.distribution(imVal)
										.getNumericalVariance());
						double sigmaX = LNConverter.sigma(eLkLmIM.componentk
								.getEdp().getEdpIM().getDistributionFunction()
								.distribution(imVal).getNumericalMean(),
								eLkLmIM.componentk.getEdp().getEdpIM()
										.getDistributionFunction()
										.distribution(imVal)
										.getNumericalVariance());
						double sigmaY = LNConverter.sigma(eLkLmIM.componentm
								.getEdp().getEdpIM().getDistributionFunction()
								.distribution(imVal).getNumericalMean(),
								eLkLmIM.componentm.getEdp().getEdpIM()
										.getDistributionFunction()
										.distribution(imVal)
										.getNumericalVariance());

						double z = (Math.log(edpK) - lnMeanX) / sigmaX;
						double edpM = Math.exp(lnMeanY + z * sigmaY);

						// determine the pdf for EDPk
						double fEDPijIM = LognormalPDF.evaluate(lnMeanX,
								sigmaX, edpK);

						double muLEDPij = eLijEDPij.muLEDPij(
								eLkLmIM.componentk, eLkLmIM.componentm, edpK,
								edpM);

						// the following line gives the integrand for E[xy].
						// after convergence is reached
						// in EVAL_relation the square of the expectation is
						// removed to get the covariance in xy.
						// this returns L_IMi
						return 1.0 / (Math.pow(tK, 2) * muLEDPij * fEDPijIM);
					}
				}

				if (tK <= 0 || t <= 0) {
					return 0;
				} else if (tK >= 1 || t >= 1) {
					return 0;
				} else {
					double edpM = 1.0 / t - 1;
					double edpK = 1.0 / tK - 1;

					double lnMeanX = LNConverter
							.mu(eLkLmIM.componentk.getEdp().getEdpIM()
									.getDistributionFunction()
									.distribution(imVal).getNumericalMean(),
									eLkLmIM.componentk.getEdp().getEdpIM()
											.getDistributionFunction()
											.distribution(imVal)
											.getNumericalVariance());
					double lnMeanY = LNConverter
							.mu(eLkLmIM.componentm.getEdp().getEdpIM()
									.getDistributionFunction()
									.distribution(imVal).getNumericalMean(),
									eLkLmIM.componentm.getEdp().getEdpIM()
											.getDistributionFunction()
											.distribution(imVal)
											.getNumericalVariance());
					double sigmaX = LNConverter
							.sigma(eLkLmIM.componentk.getEdp().getEdpIM()
									.getDistributionFunction()
									.distribution(imVal).getNumericalMean(),
									eLkLmIM.componentk.getEdp().getEdpIM()
											.getDistributionFunction()
											.distribution(imVal)
											.getNumericalVariance());
					double sigmaY = LNConverter
							.sigma(eLkLmIM.componentm.getEdp().getEdpIM()
									.getDistributionFunction()
									.distribution(imVal).getNumericalMean(),
									eLkLmIM.componentm.getEdp().getEdpIM()
											.getDistributionFunction()
											.distribution(imVal)
											.getNumericalVariance());
					double rho = slatMC.getCalculationOptions()
							.getCorrelationOptions().getCOR_EDPIM();

					double fEDPijIM = LognormalPDFBivariate.evaluate(lnMeanX,
							sigmaX, lnMeanY, sigmaY, rho, edpK, edpM);

					double muLEDPij = eLijEDPij.muLEDPij(eLkLmIM.componentk,
							eLkLmIM.componentm, edpK, edpM);

					// the following line gives the integrand for E[xy].
					// after convergence is reached
					// in EVAL_relation the square of the expectation is
					// removed to get the covariance in xy.
					// this returns L_IMi
					return (1.0 / Math.pow(t, 2)) * (1.0 / Math.pow(tK, 2))
							* muLEDPij * fEDPijIM;
				}
			}
		}, a, b);
		// TODO: should these be 0 and 1 or a and b
	}

	public UnivariateIntegrator getIntegrator() {
		return integrator;
	}

	public void setIntegrator(UnivariateIntegrator integrator) {
		this.integrator = integrator;
	}
}
