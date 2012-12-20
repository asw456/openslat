package org.openslat.model.edp;

import java.util.ArrayList;
import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.interfaces.DistributionFunction;

/**
 * EDPIM represents the seismic demand - intensity measure relationship where
 * seismic demand is measured by a known engineering demand parameter such as
 * interstory drift or floor acceleration.
 * 
 * EDPIM can be constructed with a discrete function or a combined parametric
 * function as it returns a distribution instead of a single value.
 * 
 * @author James Williams
 */
public class EDPIM {

	private Double epistemicWeight = 0.0;
	private DistributionFunction distributionFunction;

	public void setDistributionFunction(DistributionFunction model) {
		this.distributionFunction = model;
	}

	public void setDistributionFunction(DifferentiableFunction meanModel,
			DifferentiableFunction stddModel) {
		this.distributionFunction = new EDPIMParametricModel(meanModel, stddModel);
	}

	public void setDistributionFunctionDiscreteType1Table(
			ArrayList<ArrayList<Double>> table) {
		this.distributionFunction = new EDPIMDiscreteModel();
		((EDPIMDiscreteModel) distributionFunction).typeOneTableInput(table);
	}

	public void setDistributionFunctionDiscreteType2Table(
			ArrayList<ArrayList<Double>> table) {
		this.distributionFunction = new EDPIMDiscreteModel();
		((EDPIMDiscreteModel) distributionFunction).typeTwoTableInput(table);
	}

	public void setDistributionFunctionParametricType1Table(
			ArrayList<ArrayList<Double>> table) {
		this.distributionFunction = new EDPIMParametricModel();
		((EDPIMParametricModel) distributionFunction).typeOneTableInput(table);
	}

	public void setDistributionFunctionParametricType2Table(
			ArrayList<ArrayList<Double>> table) {
		this.distributionFunction = new EDPIMParametricModel();
		((EDPIMParametricModel) distributionFunction).typeTwoTableInput(table);
	}

	/**
	 * Gets the continuous distributionFunction.
	 * 
	 * @return distribution distributionFunction
	 */
	public DistributionFunction getDistributionFunction() {
		return this.distributionFunction;
	}

	public Double getEpistemicWeight() {
		return epistemicWeight;
	}

	public void setEpistemicWeight(Double epistemicWeight) {
		this.epistemicWeight = epistemicWeight;
	}

}
