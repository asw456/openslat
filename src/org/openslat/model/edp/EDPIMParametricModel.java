package org.openslat.model.edp;

import java.util.ArrayList;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.distribution.LogNormalDistribution;
import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.interfaces.DistributionFunction;
import org.openslat.numerical.LNConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Combines two continuous parametric models into a distribution model with mean
 * and standard deviation.
 * 
 * @author Alan Williams
 */
public class EDPIMParametricModel implements DistributionFunction {

	// 3. fit data to a power-law parametric model (cornell et. al. 2002)
	// 4. fit data to a parabolic parametric model (Aslani and Miranda


	@JsonIgnore
	private double logMinKnot;
	@JsonIgnore
	private double logMaxKnot;
	@JsonIgnore
	private PolynomialSplineFunction logMean;
	@JsonIgnore
	private PolynomialSplineFunction logSigma;
	@JsonIgnore
	private PolynomialSplineFunction logEpistemic;

	public EDPIMParametricModel(){
		super();
	}
	

	/**
	 * Evaluates the model for a specified input value x.
	 * 
	 * @param x
	 *            input value
	 * @return output distribution
	 */
	@Override
	public LogNormalDistribution distribution(double x) {
		return new LogNormalDistribution(LNConverter.muGivenMeanSigma(
				mean.value(x), aStdD.value(x)), aStdD.value(x));
	}

	/**
	 * method that deals with a type 1 table
	 */
	public void typeOneTableInput() {
		double[] iMi = new double[inputTable.size()];
		double[] meani = new double[inputTable.size()];
		double[] aStddi = new double[inputTable.size()];
		double[] eStddi = new double[inputTable.size()];
		minKnot = inputTable.get(0).get(0);
		maxKnot = inputTable.get(inputTable.size()).get(0);

		for (int i = 0; i < inputTable.size(); i++) {
			iMi[i] = inputTable.get(i).get(0).doubleValue();
			meani[i] = inputTable.get(i).get(1).doubleValue();
			aStddi[i] = inputTable.get(i).get(2).doubleValue();
			eStddi[i] = inputTable.get(i).get(3).doubleValue();
		}
		// now need to fit univariate models to mean
		// and fit a univariate model to alea. std. dev.
		// which means each model has to have an array-based constructor
		this.mean = null;
		this.aStdD = null;
	}

	/**
	 * method that deals with a type 2 table
	 */
	public void typeTwoTableInput() {
		this.table = table;
		double[] iMi = new double[table.size()];
		double[] meani = new double[table.size()];
		double[] aStddi = new double[table.size()];

		minKnot = table.get(0).get(0);
		maxKnot = table.get(table.size()).get(0);

		for (int i = 0; i < table.size(); i++) {
			iMi[i] = table.get(i).get(0).doubleValue();
			meani[i] = calculateLogNormalParameters(table.get(i))[0];
			aStddi[i] = calculateLogNormalParameters(table.get(i))[1];
		}
		// now need to fit univariate models to mean
		// and fit a univariate model to alea. std. dev.
		// which means each model has to have an array-based constructor
		this.mean = null;
		this.aStdD = null;
	}

	/**
	 * utility method that calculates the parameters for a lognormal
	 * distribution using MLE formulas from Wikipedia NOT SURE IF CORRECT
	 * 
	 */
	private double[] calculateLogNormalParameters(
			ArrayList<Double> randomSamples) {
		double sumTerms = 0;
		double mu = 0;
		double sigma = 0;
		for (int i = 1; i < randomSamples.size(); i++) {
			sumTerms = sumTerms + randomSamples.get(i);
		}
		mu = sumTerms / randomSamples.size();
		sumTerms = 0;
		for (int i = 1; i < randomSamples.size(); i++) {
			sumTerms = sumTerms + Math.pow((randomSamples.get(i) - mu), 2);
		}
		sigma = sumTerms / randomSamples.size();
		double[] results = new double[2];
		results[0] = mu;
		results[1] = sigma;
		return results;
	}

	public ArrayList<ArrayList<Double>> getTable() {
		return table;
	}

	public void setTable(ArrayList<ArrayList<Double>> table) {
		this.table = table;
	}

	public DifferentiableFunction getMean() {
		return mean;
	}

	public void setMean(DifferentiableFunction mean) {
		this.mean = mean;
	}

	public DifferentiableFunction getaStdD() {
		return aStdD;
	}

	public void setaStdD(DifferentiableFunction aStdD) {
		this.aStdD = aStdD;
	}

	public String getTableInputString() {
		return tableInputString;
	}

	public void setTableInputString(String tableInputString) {
		this.tableInputString = tableInputString;
	}

}
