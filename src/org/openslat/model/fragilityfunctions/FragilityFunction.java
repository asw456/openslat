/**
 * 
 */
package org.openslat.model.fragilityfunctions;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.openslat.options.CalculationOptions;

/**
 * @author alanslaptop
 * 
 */
public class FragilityFunction {

	private int identifier;
	private CalculationOptions calculationOptions;
	private String name;

	private Material material;
	private ArrayList<DamageState> damageStates = new ArrayList<DamageState>();

	private double[] randDSEDP;
	private double[] randLDS;

	private RealMatrix corrMatrixDSEDP;
	private RealMatrix corrMatrixLDS;

	//private LossEDPCalculator
	/**
	 * Method reaches into each DS and sets the mean values from the upper and
	 * lower limits based on the number of components
	 * 
	 * @param numberOfComponents
	 */
	public void setMeans(int numberOfComponents) {
		for (DamageState each : damageStates) {
			each.setMeans(numberOfComponents);
		}
	}

	/**
	 * Method generates randDSEDP and randLDS epistemic arrays. Used to generate
	 * realisations of the mean EDP for DS and the mean L for DS. If NO
	 * CORRELATIONS, unique normal random variables If PERFECT CORRELATIONS, all
	 * the same normal random variable If PARTIAL CORRELATIONS, unique normal
	 * random variables multiplied by the Cholesky Decomposition of the
	 * correlation matrix.
	 */
	public void generateEpistemicArrays() {
		randDSEDP = new double[damageStates.size()];
		randLDS = new double[damageStates.size()];

		if (calculationOptions.getCorrelationOptions().getCORE_DSEDP() == 0) {
			NormalDistribution nd = new NormalDistribution();
			for (int i = 0; i < damageStates.size(); i = i + 1) {
				randDSEDP[i] = nd.sample();
				randLDS[i] = nd.sample();
			}
		}

		if (calculationOptions.getCorrelationOptions().getCORE_DSEDP() == 1) {
			NormalDistribution nd = new NormalDistribution();
			double rn = nd.sample();
			for (int i = 0; i < damageStates.size(); i = i + 1) {
				randDSEDP[i] = rn;
				randLDS[i] = rn;
			}
		}

		else {
			NormalDistribution nd = new NormalDistribution();
			for (int i = 0; i < damageStates.size(); i = i + 1) {
				randDSEDP[i] = nd.sample();
				randLDS[i] = nd.sample();
			}

			// TODO: properly specify correlation matrix
			corrMatrixDSEDP = MatrixUtils.createRealIdentityMatrix(damageStates
					.size());
			corrMatrixLDS = MatrixUtils.createRealIdentityMatrix(damageStates
					.size());

			CholeskyDecomposition cholDSEDP = new CholeskyDecomposition(
					corrMatrixDSEDP);
			CholeskyDecomposition cholLDS = new CholeskyDecomposition(
					corrMatrixLDS);

			RealMatrix corrMatrixDSEDPChol = cholDSEDP.getL();
			RealMatrix corrMatrixLDSChol = cholLDS.getL();

			double[] randDSEDPResult = corrMatrixDSEDPChol.operate(randDSEDP);
			double[] randLDSResult = corrMatrixLDSChol.operate(randLDS);
			// possibly unnecessary
			randDSEDP = randDSEDPResult;
			randLDS = randLDSResult;
		}

		// propagate through to damage state classes
		for (int i = 0; i < damageStates.size(); i = i + 1) {
			damageStates.get(i).setRandDSEDP(randDSEDP[i]);
			damageStates.get(i).setRandLDS(randLDS[i]);
		}
	}

	public ArrayList<DamageState> getDamageStates() {
		return damageStates;
	}

	public void setDamageStates(ArrayList<DamageState> damageStates) {
		this.damageStates = damageStates;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	// TODO: implement a more sophisticated equals and hashcode method here

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof FragilityFunction))
			return false;
		FragilityFunction p = (FragilityFunction) o;
		return this.getIdentifier() == p.getIdentifier();
	}

	@Override
	public int hashCode() {
		return identifier;
	}

	public double[] getRandDSEDP() {
		return randDSEDP;
	}

	public void setRandDSEDP(double[] randDSEDP) {
		this.randDSEDP = randDSEDP;
	}

	public double[] getRandLDS() {
		return randLDS;
	}

	public void setRandLDS(double[] randLDS) {
		this.randLDS = randLDS;
	}

}
