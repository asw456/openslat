package org.openslat.interfaces;

import org.apache.commons.math3.distribution.RealDistribution;
import java.util.ArrayList;

/**
 * Distribution model interface for one-to-distribution parametric and numerical models.
 * 
 * @author James Williams
 */
public interface DistributionFunction {
	public RealDistribution distribution(double x);
	public ArrayList<ArrayList<Double>> getTable();
}
