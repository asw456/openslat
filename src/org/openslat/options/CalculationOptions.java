package org.openslat.options;

import org.openslat.model.fragilityfunctions.EpistemicCorrArrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Session Bean implementation class CalculationOptions
 */
@JsonSerialize
public class CalculationOptions {

	private boolean considerCollapse;
	private boolean componentBasedCollapseCost;
	private boolean considerDemolition;
	private boolean componentBasedDemolitionCost;
	private boolean considerDownTime;

	private int imCalcSteps;
	private int edpCalcSteps;

	private boolean demolitionRateCalc;
	private boolean collapseRateCalc;
	private boolean edpRateCalc;
	
	@JsonIgnore
	private EpistemicUncertOptions epistemicUncertOptions;
	@JsonIgnore
	private CorrelationOptions correlationOptions;

	// is this where this goes?
	// private EDPIMCorrelations edpIMCorrelations;
	
	@JsonIgnore
	private EpistemicCorrArrays epistemicCorrArrays;
	@JsonIgnore
	private EpistemicLogicTreeValues epistemicLogicTreeValues;
	
	public boolean isConsiderCollapse() {
		return considerCollapse;
	}
	public void setConsiderCollapse(boolean considerCollapse) {
		this.considerCollapse = considerCollapse;
	}
	public boolean isComponentBasedCollapseCost() {
		return componentBasedCollapseCost;
	}
	public void setComponentBasedCollapseCost(boolean componentBasedCollapseCost) {
		this.componentBasedCollapseCost = componentBasedCollapseCost;
	}
	public boolean isConsiderDemolition() {
		return considerDemolition;
	}
	public void setConsiderDemolition(boolean considerDemolition) {
		this.considerDemolition = considerDemolition;
	}
	public boolean isComponentBasedDemolitionCost() {
		return componentBasedDemolitionCost;
	}
	public void setComponentBasedDemolitionCost(boolean componentBasedDemolitionCost) {
		this.componentBasedDemolitionCost = componentBasedDemolitionCost;
	}
	public boolean isConsiderDownTime() {
		return considerDownTime;
	}
	public void setConsiderDownTime(boolean considerDownTime) {
		this.considerDownTime = considerDownTime;
	}
	public int getImCalcSteps() {
		return imCalcSteps;
	}
	public void setImCalcSteps(int imCalcSteps) {
		this.imCalcSteps = imCalcSteps;
	}
	public int getEdpCalcSteps() {
		return edpCalcSteps;
	}
	public void setEdpCalcSteps(int edpCalcSteps) {
		this.edpCalcSteps = edpCalcSteps;
	}
	public boolean isDemolitionRateCalc() {
		return demolitionRateCalc;
	}
	public void setDemolitionRateCalc(boolean demolitionRateCalc) {
		this.demolitionRateCalc = demolitionRateCalc;
	}
	public boolean isCollapseRateCalc() {
		return collapseRateCalc;
	}
	public void setCollapseRateCalc(boolean collapseRateCalc) {
		this.collapseRateCalc = collapseRateCalc;
	}
	public boolean isEdpRateCalc() {
		return edpRateCalc;
	}
	public void setEdpRateCalc(boolean edpRateCalc) {
		this.edpRateCalc = edpRateCalc;
	}
	public EpistemicUncertOptions getEpistemicUncertOptions() {
		return epistemicUncertOptions;
	}
	public void setEpistemicUncertOptions(
			EpistemicUncertOptions epistemicUncertOptions) {
		this.epistemicUncertOptions = epistemicUncertOptions;
	}
	public CorrelationOptions getCorrelationOptions() {
		return correlationOptions;
	}
	public void setCorrelationOptions(CorrelationOptions correlationOptions) {
		this.correlationOptions = correlationOptions;
	}
	public EpistemicCorrArrays getEpistemicCorrArrays() {
		return epistemicCorrArrays;
	}
	public void setEpistemicCorrArrays(EpistemicCorrArrays epistemicCorrArrays) {
		this.epistemicCorrArrays = epistemicCorrArrays;
	}
	public EpistemicLogicTreeValues getEpistemicLogicTreeValues() {
		return epistemicLogicTreeValues;
	}
	public void setEpistemicLogicTreeValues(
			EpistemicLogicTreeValues epistemicLogicTreeValues) {
		this.epistemicLogicTreeValues = epistemicLogicTreeValues;
	}

	// public EDPIMCorrelations getEdpIMCorrelations() {
	// return edpIMCorrelations;
	// }
	//
	// public void setEdpIMCorrelations(EDPIMCorrelations edpIMCorrelations) {
	// this.edpIMCorrelations = edpIMCorrelations;
	// }

	
}
