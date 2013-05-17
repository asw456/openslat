package org.openslat.models.distribution;

import java.util.ArrayList;

import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.interfaces.DistributionFunction;
import org.openslat.numerical.LNConverter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Combines two continuous parametric models into a distribution model with mean
 * and standard deviation.
 * 
 * @author Alan Williams
 */
@JsonSerialize
public class LogNormalModel implements DistributionFunction {

	private DifferentiableFunction meanModel;
	private DifferentiableFunction stddModel;

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output distribution
	 */
	@Override
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

	public DifferentiableFunction getMeanModel() {
		return meanModel;
	}

	public void setMeanModel(DifferentiableFunction meanModel) {
		this.meanModel = meanModel;
	}

	public DifferentiableFunction getStddModel() {
		return stddModel;
	}

	public void setStddModel(DifferentiableFunction stddModel) {
		this.stddModel = stddModel;
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
		if (!(obj instanceof LogNormalModel))
			return false;
		LogNormalModel other = (LogNormalModel) obj;
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
