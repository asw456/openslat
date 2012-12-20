package org.openslat.model.edp;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.interfaces.DistributionFunction;
import org.openslat.numerical.FitLogNormalDistribution;
import org.openslat.numerical.LNConverter;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import java.lang.Math;

/**
 * Interpolation-based model
 * 
 * @author Alan Williams
 */
public class EDPIMDiscreteModel implements DistributionFunction {

	private double logMinKnot;
	private double logMaxKnot;
	
	private PolynomialSplineFunction logMean;
	private PolynomialSplineFunction logSigma;
	private PolynomialSplineFunction logEpistemic;
	private ArrayList<ArrayList<Double>> table = null;

	/**
	 * method that deals with a type 1 table
	 */
	public void typeOneTableInput(ArrayList<ArrayList<Double>> table) {
		double[] logiMi = new double[table.size()];
		double[] logMeani = new double[table.size()];
		double[] logSigmai = new double[table.size()];
		double[] logEpistemici = new double[table.size()];

		for (int i = 0; i < table.size(); i++) {
			// hmmm....log???? I think so given power-law parametric curve used.
			// note log10-log10 interpolation, ASSUMES NO leading zero in table
			// currently works but will break down if first row in table is 0.0
			// , 0.0
			logiMi[i] = Math.log10(table.get(i).get(0).doubleValue());
			logMeani[i] = Math.log10(table.get(i).get(1).doubleValue());
			logSigmai[i] = Math.log10(table.get(i).get(2).doubleValue());
			logEpistemici[i] = Math.log10(table.get(i).get(3)
					.doubleValue());
		}

		logMean = new LinearInterpolator().interpolate(logiMi, logMeani);
		logSigma = new LinearInterpolator().interpolate(logiMi, logSigmai);
		logEpistemic = new LinearInterpolator().interpolate(logiMi,
				logEpistemici);
		logMinKnot = logiMi[0];
		logMaxKnot = logiMi[logiMi.length - 1];
	}

	/**
	 * method that deals with a type 2 table
	 */
	public void typeTwoTableInput(ArrayList<ArrayList<Double>> table) {
		this.table = table;
		double[] logiMi = new double[table.size()];
		double[] logMeani = new double[table.size()];
		double[] logaSigmai = new double[table.size()];
		double[] logEpistemici = new double[table.size()];
		// minKnot = inputTable.get(0).get(0);
		// maxKnot = inputTable.get(inputTable.size()).get(0);

		for (int i = 0; i < table.size(); i++) {
			logiMi[i] = Math.log10(table.get(i).get(0).doubleValue());
			table.get(i).remove(0);
			logMeani[i] = Math.log10(FitLogNormalDistribution.calculateLogNormalParameters(table
					.get(i)).getNumericalMean());
			logaSigmai[i] = Math.log10(FitLogNormalDistribution.calculateLogNormalParameters(table
					.get(i)).getShape());
		}

		logMean = new LinearInterpolator().interpolate(logiMi, logMeani);
		logSigma = new LinearInterpolator().interpolate(logiMi, logaSigmai);

		logMinKnot = logiMi[0];
		logMaxKnot = logiMi[logiMi.length - 1];

	}

	/**
	 * evaluates the model and returns a distribution
	 */
	@Override
	public LogNormalDistribution distribution(double x) {

		if (Math.log10(x) >= logMinKnot && Math.log10(x) <= logMaxKnot) {
			return new LogNormalDistribution(LNConverter.muGivenMeanSigma(
					Math.pow(logMean.value(Math.log10(x)), 10),
					Math.pow((logSigma.value(Math.log10(x))), 10)), Math.pow(
					(logSigma.value(Math.log10(x))), 10));
		}
		if (Math.log10(x) >= 0 && Math.log10(x) < logMinKnot) {
			return null;
		}
		// Do something with out-of-bounds values here
		return null;
	}

	public ArrayList<ArrayList<Double>> getTable() {
		return table;
	}

	public void setTable(ArrayList<ArrayList<Double>> table) {
		this.table = table;
	}

}