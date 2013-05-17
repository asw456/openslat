package org.openslat.models.univariate;

import org.openslat.interfaces.DifferentiableFunction;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the hyperbolic model
 * 
 * <p align="center">
 * <code>y(x) = a*x/(1-b*x)</code>
 * </p>
 * 
 * where <code>a</code> and <code>b</code> are specified parameters.
 * 
 * @author Alan Williams
 */
@JsonSerialize
public class HyperbolicModel2 implements DifferentiableFunction {
	private double a;
	private double b;

	/**
	 * sets the model parameters directly
	 * 
	 * <p align="center">
	 * <code>y(x) = a*x/(1-b*x)</code>
	 * </p>
	 * 
	 * from a specified set of parameters <code>[a b]</code>.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void setHyperbolicModelParams(double a, double b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * sets the model parameters directly
	 */
	public void setHyperbolicModelParams(double[] parameters) {
		this.a = parameters[0];
		this.b = parameters[1];
	}

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {

		return a * x / (1 - b * x);

	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {

		return a / Math.pow(1 - b * x, 2);

	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {
		return "hyperbolic model y(x) = a*x/(1-b*x) with parameters a = "
				+ this.a + " and b = " + this.b;
	}
}
