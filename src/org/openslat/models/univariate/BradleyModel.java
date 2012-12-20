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
	private double a;
	private double b;
	private double c;

	/**
	 * sets the model parameters directly
	 * 
	 * <p align="center">
	 * <code>y(x) = a*exp(c/ln(x/b))</code>
	 * </p>
	 * 
	 * from a specified set of parameters <code>[a b c]</code>.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void setBradleyModelParams(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public void setBradleyModelParams(double[] parameters) {
		this.a = parameters[0];
		this.b = parameters[1];
		this.c = parameters[2];
	}

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {
		return a * Math.exp(c / Math.log(x / b));
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return -(a * c * Math.exp(c / Math.log(x / b)))
				/ (x * Math.pow(Math.log(x / b), 2));
	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {
		return "Bradley model y(x) = a*exp(c/ln(x/b)) with parameters a = "
				+ this.a + " and b = " + this.b + " and c = " + this.c;
	}
}
