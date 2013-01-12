package org.openslat.models.univariate;

import org.apache.commons.math3.stat.regression.SimpleRegression;
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
	public void constructPowerModel(double[] parameters) {
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
			logdata[i][0] = Math.log(x[i]);
			logdata[i][1] = Math.log(y[i]);
		}
		SimpleRegression regressor = new SimpleRegression();
		regressor.addData(logdata);
		this.parameters[0] = Math.exp(regressor.getIntercept());
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
		return (parameters[0] * Math.pow(x, parameters[1]));
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return parameters[0] * parameters[1] * Math.pow(x, parameters[1] - 1);
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
}
