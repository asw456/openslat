package org.openslat.models.univariate;

import org.openslat.interfaces.DifferentiableFunction;
import java.lang.Math;
/**
 * Represents the Aslani model
 * 
 * <p align="center">
 * <code>y(x) = a*(b^x)*(x^c)</code>
 * </p>
 * 
 * where <code>a</code>, <code>b</code> and <code>c</code> are specified
 * parameters.
 * 
 * @author James Williams
 */
public class AslaniModel implements DifferentiableFunction {
	private static final String NAME = "Aslani model";
	private double a;
	private double b;
	private double c;

	/**
	 * sets the Aslani model parameters directly
	 * 
	 * <p align="center">
	 * <code>y(x) = a*(b^x)*(x^c)</code>
	 * </p>
	 * 
	 * from a specified set of parameters <code>[a b c]</code>.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void setAslaniModelParams(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * sets the Aslani model parameters directly
	 */
	public void setAslaniModelParams(double[] parameters) {
		this.a = parameters[0];
		this.b = parameters[1];
		this.c = parameters[2];
	}

	/**
	 * Evaluates the function for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double value(double x) {
		return a * Math.pow(b, x) * Math.pow(x, c);
	}

	/**
	 * Evaluates the derivative for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output value
	 */
	public double derivative(double x) {
		return a * Math.pow(b, x) * c * Math.pow(x, c - 1);
	}

	/**
	 * Returns a string representation of the model.
	 * 
	 * @return string representation of the model
	 */
	public String toString() {
		return "Aslani model y(x) = a*(b^x)*(x^c) with parameters a = "
				+ this.a + " and b = " + this.b + " and c = " + this.c;
	}

	public static String getName() {
		return NAME;
	}
}
