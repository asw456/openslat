/**
 * 
 */
package org.openslat.calculators.multiplecomponents;

import org.jquantlib.math.distributions.BivariateNormalDistribution;
import org.openslat.model.fragilityfunctions.DamageState;
import org.openslat.model.structure.Component;

/**
 * To provide a library of fragility data CORRELATIONS for different fragility
 * functions in computing the loss damage relationships
 * 
 * output: muLEDPij -- the expectation of Lij|EDPij
 * 
 * @author alan
 * 
 */
public class ELijEDPij {

	private BivariateNormalDistribution bvnd;
	private DSEDPCorrelation dsEDPCorrelation;
	private LDSCorrelation lDSCorrelation;

	public double muLEDPij(Component componenti, Component componentj,
			double edpi, double edpj) {

		double corr_Lossij_DS = lDSCorrelation.corr_Lossij_DS(componenti,
				componentj);

		double[][] CDFArray = jointFFProbabilities(componenti, componentj,
				edpi, edpj);

		double[][] pDSEDP = damageStateProbabilities(componenti, componentj,
				CDFArray);

		double muLEDPij = 0;
		// TODO: why are these not used?? FORTRAN seems not to use them either
		// double stdLDSi = 0;
		// double stdLDSj = 0;
		for (int i = 0; i < componenti.getFf().getDamageStates().size(); i = i + 1) {
			// stdLDSi =
			// componenti.getFf().getDamageStates().get(i).getMeanLoss()
			// * Math.sqrt(Math.exp(Math.pow(componenti.getFf()
			// .getDamageStates().get(i).getSigmaEDPOnset(), 2) - 1.0));
			for (int j = 0; j < componentj.getFf().getDamageStates().size(); j = j + 1) {
				// stdLDSj = componentj.getFf().getDamageStates().get(j)
				// .getMeanLoss()
				// * Math.sqrt(Math.exp(Math
				// .pow(componentj.getFf().getDamageStates()
				// .get(j).getSigmaEDPOnset(), 2) - 1.0));
				muLEDPij = muLEDPij
						+ (componenti.getFf().getDamageStates().get(i)
								.getMeanLoss()
								* componentj.getFf().getDamageStates().get(j)
										.getMeanLoss() + corr_Lossij_DS
								* componenti.getFf().getDamageStates().get(i)
										.getMeanLoss()
								* componentj.getFf().getDamageStates().get(j)
										.getMeanLoss() * pDSEDP[i][j]);

			}
		}

		return muLEDPij;
	}

	// compute joint fragility function probabilities
	private double[][] jointFFProbabilities(Component componenti,
			Component componentj, double edpi, double edpj) {

		double[][] CDFArray = new double[componenti.getFf().getDamageStates()
				.size()][componentj.getFf().getDamageStates().size()];

		double corr_DSij_EDP = dsEDPCorrelation.corr_DSij_EDP(componenti,
				componentj);

		int i = 0;
		int j = 0;
		for (DamageState dsi : componenti.getFf().getDamageStates()) {
			for (DamageState dsj : componentj.getFf().getDamageStates()) {
				// TODO: test getSigmaEDPOnset should equal
				// dsi.getLnMeanEDPOnset().getStandardDeviation()
				double zi = (Math.log(edpi) - dsi.getMuEDPOnset())
						/ dsi.getSigmaEDPOnset();
				double zj = (Math.log(edpj) - dsj.getMuEDPOnset())
						/ dsj.getSigmaEDPOnset();

				bvnd = new BivariateNormalDistribution(corr_DSij_EDP);
				CDFArray[i][j] = bvnd.op(zi, zj);
				j = j + 1;
			}
			i = i + 1;
		}

		return CDFArray;
	}

	// compute damage state probabiltiies
	// TODO: make sure indexing here isn't f..d up. NDSi - i + 1 is in the
	// fortran
	private double[][] damageStateProbabilities(Component componenti,
			Component componentj, double[][] CDFArray) {

		double[][] pDSEDP = new double[componenti.getFf().getDamageStates()
				.size()][componentj.getFf().getDamageStates().size()];

		int NDSi = componenti.getFf().getDamageStates().size();
		int NDSj = componentj.getFf().getDamageStates().size();
		double sumF;
		for (int i = 0; i < NDSi; i = i + 1) {
			for (int j = 0; j < NDSi; j = j + 1) {
				sumF = 0;
				for (int k = NDSi - i; k <= NDSi; k = k + 1) {
					for (int m = NDSj - j; m <= NDSj; m = m + 1) {
						if (!(k == NDSi - i && m == NDSj - j)) {
							sumF = sumF + pDSEDP[k][m];
						}
					}
				}
				pDSEDP[NDSi][NDSj] = CDFArray[NDSi][NDSj];

				j = j + 1;
			}
			i = i + 1;
		}
		return pDSEDP;
	}

	public BivariateNormalDistribution getBvnd() {
		return bvnd;
	}

	public void setBvnd(BivariateNormalDistribution bvnd) {
		this.bvnd = bvnd;
	}

	public DSEDPCorrelation getDsEDPCorrelation() {
		return dsEDPCorrelation;
	}

	public void setDsEDPCorrelation(DSEDPCorrelation dsEDPCorrelation) {
		this.dsEDPCorrelation = dsEDPCorrelation;
	}

	public LDSCorrelation getlDSCorrelation() {
		return lDSCorrelation;
	}

	public void setlDSCorrelation(LDSCorrelation lDSCorrelation) {
		this.lDSCorrelation = lDSCorrelation;
	}

}
