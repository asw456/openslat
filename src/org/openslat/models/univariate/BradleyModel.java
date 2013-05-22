package org.openslat.models.univariate;

import org.openslat.interfaces.DifferentiableFunction;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.Math;

/**
 * Represents the model
 * 
 * <p align="center">
 * <code>y(x) = a*exp(c/ln(x/b))</code>
 * </p>
 * 
 * proposed by Bradley <i>et al</i>. (2007) as an improvement to the power model
 * where <code>a</code>, <code>b</code> and <code>c</code> are specified
 * parameters.
 * 
 * @author Alan Williams
 */
@JsonSerialize
public class BradleyModel implements DifferentiableFunction {
	private double[] parameters;

	/**
	 * sets the model parameters directly
	 * 
	 * <p align="center">
	 * <code>y(x) = a*exp(c/ln(x/b))</code>
	 * </p>
	 * 
	 * from a specified set of parameters <code>[a b c]</code>.
	 * 
	 * @param parameters[0]
	 * @param parameters[1]
	 * @param parameters[2]
	 */
	public void constructBradleyModel(double[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {
		return parameters[0] * Math.exp(parameters[2] / Math.log(x / parameters[1]));
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return -(parameters[0] * parameters[2] * Math.exp(parameters[2] / Math.log(x / parameters[1])))
				/ (x * Math.pow(Math.log(x / parameters[1]), 2));
	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {
		return "Bradley model y(x) = a*exp(c/ln(x/b)) with parameters a = "
				+ this.parameters[0] + " and b = " + this.parameters[1] + " and c = " + this.parameters[2];
	}

	public double[] getParameters() {
		return parameters;
	}

	public void setParameters(double[] parameters) {
		this.parameters = parameters;
	}
}
