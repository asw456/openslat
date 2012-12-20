package org.openslat.models.univariate;

import org.openslat.interfaces.DifferentiableFunction;
import java.lang.Math;

/**
 * Represents the parabolic model
 * 
 * <p align="center">
 * <code>y(x) = a + b*x + c*x^2</code>
 * </p>
 * 
 * where <code>a</code>, <code>b</code> and <code>c</code> are specified
 * parameters.
 * 
 * @author Alan Williams
 */
public class ParabolicModel implements DifferentiableFunction {
	private static final String NAME = "Parabolic model";
	private double a;
	private double b;
	private double c;

	/**
	 * sets the parabolic model parameters directly
	 * 
	 * <p align="center">
	 * <code>y(x) = a + b*x + c*x^2</code>
	 * </p>
	 * 
	 * from a specified set of parameters <code>[a b c]</code>.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void setParabolicModelParams(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * sets the parabolic model parameters directly
	 */
	public void setParabolicModelParams(double[] parameters) {
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
		return a + b * x + c * Math.pow(x, 2);
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return b + 2 * c * x;
	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {
		return ("parabolic model y(x) = a + b*x + c*x^2 with parameters a = "
				+ this.a + " and b = " + this.c + " and c = " + this.c);
	}

	public static String getName() {
		return NAME;
	}
}
