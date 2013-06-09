package org.openslat.model.edp;

import java.util.ArrayList;
import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.interfaces.DistributionFunction;
import org.openslat.models.distribution.LogNormalModel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * EDPIM represents the seismic demand - intensity measure relationship where
 * seismic demand is measured by a known engineering demand parameter such as
 * interstory drift or floor acceleration.
 * 
 * EDPIM can be constructed with a discrete function or a combined parametric
 * function as it returns a distribution instead of a single value.
 * 
 * @author Alan Williams
 */
@JsonSerialize
public class EDPIM {

	private String name;
	private Double epistemicWeight = 1.0;
	private DistributionFunction distributionFunction;

	/**
	 * Gets the continuous distributionFunction.
	 * 
	 * @return distribution distributionFunction
	 */
	public DistributionFunction getDistributionFunction() {
		return this.distributionFunction;
	}

	public void setDistributionFunction(DistributionFunction model) {
		this.distributionFunction = model;
	}

	public void constructDistributionFunctionFromModels(
			DifferentiableFunction meanModel, DifferentiableFunction stddModel) {
		this.distributionFunction = new LogNormalModel(meanModel,
				stddModel);
	}

//	public void constructDistributionFunctionDiscreteType1Table(
//			ArrayList<ArrayList<Double>> table) {
//		this.distributionFunction = new EDPIMDiscreteModel();
//		((EDPIMDiscreteModel) distributionFunction).typeOneTableInput();
//	}
//
//	public void constructDistributionFunctionDiscreteType2Table(
//			ArrayList<ArrayList<Double>> table) {
//		this.distributionFunction = new EDPIMDiscreteModel();
//		((EDPIMDiscreteModel) distributionFunction).typeTwoTableInput();
//	}
//
//	public void constructDistributionFunctionParametricType1Table(
//			ArrayList<ArrayList<Double>> table) {
//		this.distributionFunction = new EDPIMParametricModel();
//		((EDPIMParametricModel) distributionFunction).typeOneTableInput();
//	}
//
//	public void constructDistributionFunctionParametricType2Table(
//			ArrayList<ArrayList<Double>> table) {
//		this.distributionFunction = new EDPIMParametricModel();
//		((EDPIMParametricModel) distributionFunction).typeTwoTableInput();
//	}

	public Double getEpistemicWeight() {
		return epistemicWeight;
	}

	public void setEpistemicWeight(Double epistemicWeight) {
		this.epistemicWeight = epistemicWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((distributionFunction == null) ? 0 : distributionFunction
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EDPIM))
			return false;
		EDPIM other = (EDPIM) obj;
		if (distributionFunction == null) {
			if (other.distributionFunction != null)
				return false;
		} else if (!distributionFunction.equals(other.distributionFunction))
			return false;
		return true;
	}

}
