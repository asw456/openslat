package org.openslat.options;

import org.openslat.model.fragilityfunctions.EpistemicCorrArrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Session Bean implementation class CalculationOptions
 */
@JsonSerialize
public class CalculationOptions {

	/*
				"considerCollapse":true,
				"componentBasedCollapseCost":false,
				"considerDemolition":true,
				"componentBasedDemolitionCost":false,
				"considerDownTime":false,
				"demolitionRateCalc": false,
				"collapseRateCalc": false,
				"edpRateCalc": false
				"imCalcSteps": 50
				"edpCalcSteps": 50
	*/
	
	private boolean considerCollapse;
	private boolean collapseLossMethod;
	private boolean considerDownTime;
	
	private boolean demolition_rate_calc;
	private boolean collapse_rate_calc;
	private boolean edp_rate_calc;
	private boolean IM_calc_steps;
	private boolean EDP_calc_steps;

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

	public boolean isCollapse() {
		return considerCollapse;
	}

	public void setCollapse(boolean collapse) {
		this.considerCollapse = collapse;
	}

	public boolean isCollLossType() {
		return collapseLossMethod;
	}

	public void setCollLossType(boolean collLossType) {
		this.collapseLossMethod = collLossType;
	}

	public boolean isDownTime() {
		return considerDownTime;
	}

	public void setDownTime(boolean downTime) {
		this.considerDownTime = downTime;
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

	// public EDPIMCorrelations getEdpIMCorrelations() {
	// return edpIMCorrelations;
	// }
	//
	// public void setEdpIMCorrelations(EDPIMCorrelations edpIMCorrelations) {
	// this.edpIMCorrelations = edpIMCorrelations;
	// }

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

	public boolean isConsiderCollapse() {
		return considerCollapse;
	}

	public void setConsiderCollapse(boolean considerCollapse) {
		this.considerCollapse = considerCollapse;
	}

	public boolean isCollapseLossMethod() {
		return collapseLossMethod;
	}

	public void setCollapseLossMethod(boolean collapseLossMethod) {
		this.collapseLossMethod = collapseLossMethod;
	}

	public boolean isConsiderDownTime() {
		return considerDownTime;
	}

	public void setConsiderDownTime(boolean considerDownTime) {
		this.considerDownTime = considerDownTime;
	}

	public boolean isDemolition_rate_calc() {
		return demolition_rate_calc;
	}

	public void setDemolition_rate_calc(boolean demolition_rate_calc) {
		this.demolition_rate_calc = demolition_rate_calc;
	}

	public boolean isCollapse_rate_calc() {
		return collapse_rate_calc;
	}

	public void setCollapse_rate_calc(boolean collapse_rate_calc) {
		this.collapse_rate_calc = collapse_rate_calc;
	}

	public boolean isEdp_rate_calc() {
		return edp_rate_calc;
	}

	public void setEdp_rate_calc(boolean edp_rate_calc) {
		this.edp_rate_calc = edp_rate_calc;
	}

	public boolean isIM_calc_steps() {
		return IM_calc_steps;
	}

	public void setIM_calc_steps(boolean iM_calc_steps) {
		IM_calc_steps = iM_calc_steps;
	}

	public boolean isEDP_calc_steps() {
		return EDP_calc_steps;
	}

	public void setEDP_calc_steps(boolean eDP_calc_steps) {
		EDP_calc_steps = eDP_calc_steps;
	}
}
