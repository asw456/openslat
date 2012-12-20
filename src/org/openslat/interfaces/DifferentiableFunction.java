package org.openslat.interfaces;

import org.apache.commons.math3.analysis.UnivariateFunction;

/**
 * Distribution model interface for one-to-distribution parametric and numerical models.
 * 
 * @author James Williams
 */
public interface DifferentiableFunction extends UnivariateFunction {
	public double derivative(double x);
}
