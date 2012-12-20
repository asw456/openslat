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
	
	private double a;
	private double b;

	/**
	 * sets the power model parameters directly
	 * <p align="center">
	 * <code>y(x) = a*x^b</code>
	 * </p>
	 * from a specified set of parameters <code>[a b]</code>.
	 * 
	 * @param a
	 * @param b
	 */
	public void constructPowerModel(double[] parameters) {
		this.a = parameters[0];
		this.b = parameters[1];
	}

	/**
	 * estimates the power model parameters from data
	 * <p align="center">
	 * <code>y(x) = a*x^b</code>
	 * </p>
	 * by regression from two <code>double[]</code> arrays
	 * 
	 * @param a
	 * @param b
	 */
	public void estimatePowerModelParams(double[] x, double[] y) {
		double[][] logdata = new double[x.length][2];
		for (int i = 0; i < x.length; i++) {
			logdata[i][0] = Math.log(x[i]);
			logdata[i][1] = Math.log(y[i]);
		}
		SimpleRegression regressor = new SimpleRegression();
		regressor.addData(logdata);
		this.a = Math.exp(regressor.getIntercept());
		this.b = regressor.getSlope();
	}

	/**
	 * Evaluates the power model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {
		return a * Math.pow(x, b);
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return a * b * Math.pow(x, b - 1);
	}

	public String toString() {
		return "power model y(x) = a*x^b with parameters a = " + this.a
				+ " and b = " + this.b;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}
}
