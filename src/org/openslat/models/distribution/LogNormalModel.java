package org.openslat.models.distribution;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.openslat.interfaces.DistributionFunction;
import org.openslat.numerical.LNConverter;

/**
 * Combines two continuous parametric models into a distribution model with mean
 * and standard deviation.
 * 
 * @author Alan Williams
 */
public class LogNormalModel implements DistributionFunction {

	private UnivariateFunction meanModel;
	private UnivariateFunction stddModel;

	/**
	 * Constructs the combined model from the two parametric models.
	 * 
	 * @param meanModel
	 * @param stddModel
	 */
	public LogNormalModel(UnivariateFunction meanModel,
			UnivariateFunction stddModel) {
		this.meanModel = meanModel;
		this.stddModel = stddModel;
	}

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output distribution
	 */
	public LogNormalDistribution distribution(double x) {
		// TODO : Check if this is correct..check 1 passed
		return new LogNormalDistribution(LNConverter.muGivenMeanSigma(
				meanModel.value(x), stddModel.value(x)), stddModel.value(x));
	}

	@Override
	public ArrayList<ArrayList<Double>> getTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
