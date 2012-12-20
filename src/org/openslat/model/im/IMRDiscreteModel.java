/**
 * 
 */
package org.openslat.model.im;

import java.util.ArrayList;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.openslat.interfaces.DifferentiableFunction;

/**
 * @author alan
 * 
 */
public class IMRDiscreteModel implements DifferentiableFunction {

	private ArrayList<ArrayList<Double>> inputTable;
	double[] iMi;
	double[] annualFreqi;
	private double minKnot;
	private double maxKnot;
	private PolynomialSplineFunction annualFreq;
	
	//TODO: deal with out-of-bounds values better

	public void parseTable(ArrayList<ArrayList<Double>> table) {
		this.inputTable = table;
		parseTable();
	}

	/**
	 * method that deals with a type 1 table
	 */
	private void parseTable() {
		iMi = new double[inputTable.size()];
		annualFreqi = new double[inputTable.size()];
		minKnot = inputTable.get(0).get(0);
		maxKnot = inputTable.get(inputTable.size() - 1).get(0);

		for (int i = 0; i < inputTable.size(); i++) {
			iMi[i] = Math.log10(inputTable.get(i).get(0).doubleValue());
			annualFreqi[i] = Math.log10(inputTable.get(i).get(1).doubleValue());
		}
		annualFreq = new LinearInterpolator().interpolate(iMi, annualFreqi);
	}

	/**
	 * 
	 *
	 */
	@Override
	public double value(double x) {
		if (x >= minKnot && x <= maxKnot) {
			return Math.pow(annualFreq.value(Math.log10(x)), 10);
		}
		// easy method: return consant 
		if (x < minKnot)
			return inputTable.get(0).get(1).doubleValue();
		else
			return inputTable.get(inputTable.size() - 1).get(1).doubleValue();

		// Line lowerLine = new Line(new Vector2D(iMi[0], annualFreqi[0]),
		// new Vector2D(iMi[1], annualFreqi[1]));
		// return Math.pow(lowerLine.getPointAt(new Vector1D(Math.log10(x)), 0)
		// .getY(), 10);

		// Line upperLine = new Line(new Vector2D(iMi[iMi.length - 2],
		// annualFreqi[annualFreqi.length - 2]), new Vector2D(
		// iMi[iMi.length - 1], annualFreqi[annualFreqi.length - 1]));
		// return Math.pow(upperLine.getPointAt(new Vector1D(Math.log10(x)), 0)
		// .getY(), 10);

	}

	@Override
	public double derivative(double x) {
		if (x >= minKnot && x <= maxKnot) {
			return annualFreq.derivative().value(x);
		}
		if (x < minKnot)
			return annualFreq.derivative().value(minKnot);
		else
			return annualFreq.derivative().value(maxKnot);

	}
}
