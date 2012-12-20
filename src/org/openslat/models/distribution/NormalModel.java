package org.openslat.models.distribution;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.openslat.interfaces.DistributionFunction;

/**
 * Combines two continuous parametric models into a distribution model with mean
 * and standard deviation.
 * 
 * @author James Williams
 */
public class NormalModel implements DistributionFunction {

	private UnivariateFunction meanModel;
	private UnivariateFunction stddModel;

	/**
	 * Constructs the combined model from the two parametric models.
	 * 
	 * @param meanModel
	 * @param stddModel
	 */
	public NormalModel(UnivariateFunction meanModel,
			UnivariateFunction stddModel) {

		this.meanModel = meanModel;
		this.stddModel = stddModel;

	}

	/**
	 * Evaluates the model and returns a distribution for a specified input
	 * value x.
	 * 
	 * @param x
	 *            input value
	 * @return output distribution
	 */
	public NormalDistribution distribution(double x) {

		// TODO : Check if this is required

		return new NormalDistribution(meanModel.value(x), stddModel.value(x));

	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {

		String s = "Combined Structure";

		s = s + "\n" + "Mean : " + meanModel.toString();
		s = s + "\n" + "Stdd : " + stddModel.toString();

		return s;

	}

	@Override
	public ArrayList<ArrayList<Double>> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
