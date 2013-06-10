package org.openslat.models.univariate;

import java.util.Arrays;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.FastMath;
import org.openslat.interfaces.DifferentiableFunction;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the power model
 * 
 * <p align="center">
 * <code>y(x) = a*x^b</code>
 * </p>
 * 
 * where <code>a</code> and <code>b</code> are specified parameters.
 * 
 * @author Alan Williams
 */
@JsonSerialize
public class PowerModel implements DifferentiableFunction {
	
	private double[] parameters;

	/**
	 * sets the power model parameters directly
	 * <p align="center">
	 * <code>y(x) = a*x^b</code>
	 * </p>
	 * from a specified set of parameters <code>[a b]</code>.
	 * 
	 * @param a
	 * @param parameters[1]
	 */
	public void setPowerModelParams(double[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * estimates the power model parameters from data
	 * <p align="center">
	 * <code>y(x) = a*x^b</code>
	 * </p>
	 * by regression from two <code>double[]</code> arrays
	 * 
	 * @param a
	 * @param parameters[1]
	 */
	public void estimatePowerModelParams(double[] x, double[] y) {
		double[][] logdata = new double[x.length][2];
		for (int i = 0; i < x.length; i++) {
			logdata[i][0] = FastMath.log(x[i]);
			logdata[i][1] = FastMath.log(y[i]);
		}
		SimpleRegression regressor = new SimpleRegression();
		regressor.addData(logdata);
		this.parameters[0] = FastMath.exp(regressor.getIntercept());
		this.parameters[1] = regressor.getSlope();
	}

	/**
	 * Evaluates the power model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {
		return (parameters[0] * FastMath.pow(x, - parameters[1]));
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return parameters[0] * (parameters[1]) * FastMath.pow(x, - parameters[1] - 1);
	}

	public String toString() {
		return "power model y(x) = a*x^b with parameters a = " + this.parameters[0]
				+ " and b = " + this.parameters[1];
	}

	public double[] getParameters() {
		return parameters;
	}

	public void setParameters(double[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(parameters);
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
		PowerModel other = (PowerModel) obj;
		if (!Arrays.equals(parameters, other.parameters))
			return false;
		return true;
	}
}
