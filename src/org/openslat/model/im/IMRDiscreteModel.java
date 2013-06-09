/**
 * 
 */
package org.openslat.model.im;

import java.util.ArrayList;

import org.apache.commons.math3.analysis.differentiation.FiniteDifferencesDifferentiator;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.util.FastMath;
import org.openslat.interfaces.DifferentiableFunction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author alan
 * 
 */
@JsonSerialize
public class IMRDiscreteModel implements DifferentiableFunction {

	private ArrayList<ArrayList<Double>> table;

	@JsonIgnore
	double[] iMi;
	@JsonIgnore
	double[] annualFreqi;
	@JsonIgnore
	private double minKnot;
	@JsonIgnore
	private double maxKnot;
	@JsonIgnore
	private PolynomialSplineFunction annualFreq;

	// TODO: deal with out-of-bounds values better

	public void parseTable(ArrayList<ArrayList<Double>> table) {
		this.table = table;
		parseTable();
	}

	/**
	 * method that deals with a type 1 table
	 */
	public void parseTable() {
		iMi = new double[table.size()];
		annualFreqi = new double[table.size()];
		minKnot = table.get(0).get(0);
		maxKnot = table.get(table.size() - 1).get(0);

		for (int i = 0; i < table.size(); i++) {
			iMi[i] = FastMath.log(table.get(i).get(0).doubleValue());
			annualFreqi[i] = FastMath.log(table.get(i).get(1).doubleValue());
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
			return Math.exp(annualFreq.value(Math.log(x)));
		}
		// easy method: return consant
		if (x < minKnot)
			return table.get(0).get(1).doubleValue();
		else
			return 0;//table.get(table.size() - 1).get(1).doubleValue();

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
	public double derivative(double x_in) {
		double e = 1e-7;

		if (x_in >= minKnot+2*e && x_in <= maxKnot-2*e) {
			return (value(x_in+e)-value(x_in-e))/(2*e);
		}
		// easy method: return consant
		if (x_in < minKnot + 2*e)
			return 0; //table.get(0).get(1).doubleValue();
		else
			return 0; //table.get(table.size() - 1).get(1).doubleValue();
		
		
		
		// double x = FastMath.log(x_in);
		//
		// if (x >= minKnot && x <= maxKnot) {
		// return annualFreq.derivative().value(x);
		// }
		// if (x < minKnot)
		// return annualFreq.derivative().value(minKnot);
		// else
		// return annualFreq.derivative().value(maxKnot);

	}

	public ArrayList<ArrayList<Double>> getTable() {
		return table;
	}

	public void setTable(ArrayList<ArrayList<Double>> table) {
		this.table = table;
	}
}
