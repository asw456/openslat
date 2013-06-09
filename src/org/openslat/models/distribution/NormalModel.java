package org.openslat.models.distribution;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.interfaces.DistributionFunction;

/**
 * Combines two continuous parametric models into a distribution model with mean
 * and standard deviation.
 * 
 * @author Alan Williams
 */
public class NormalModel implements DistributionFunction {

	private DifferentiableFunction meanModel;
	private DifferentiableFunction stddModel;

	/**
	 * Constructs the combined model from the two parametric models.
	 * 
	 * @param meanModel
	 * @param stddModel
	 */
	public NormalModel(DifferentiableFunction meanModel,
			DifferentiableFunction stddModel) {

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((meanModel == null) ? 0 : meanModel.hashCode());
		result = prime * result
				+ ((stddModel == null) ? 0 : stddModel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NormalModel other = (NormalModel) obj;
		if (meanModel == null) {
			if (other.meanModel != null)
				return false;
		} else if (!meanModel.equals(other.meanModel))
			return false;
		if (stddModel == null) {
			if (other.stddModel != null)
				return false;
		} else if (!stddModel.equals(other.stddModel))
			return false;
		return true;
	}

}
