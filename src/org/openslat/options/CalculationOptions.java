package org.openslat.options;

import org.openslat.calculators.multiplecomponents.EDPIMCorrelations;
import org.openslat.model.fragilityfunctions.EpistemicCorrArrays;
import org.openslat.model.structure.Structure;

/**
 * Session Bean implementation class CalculationOptions
 */
public class CalculationOptions {

	private Structure structure;

	private boolean collapse;
	private boolean collLossType;
	private boolean downTime;

	private EpistemicUncertOptions epistemicUncertOptions;
	private CorrelationOptions correlationOptions;

	// is this where this goes?
	private EDPIMCorrelations edpIMCorrelations;

	private EpistemicCorrArrays epistemicCorrArrays;
	private EpistemicLogicTreeValues epistemicLogicTreeValues;

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public boolean isCollapse() {
		return collapse;
	}

	public void setCollapse(boolean collapse) {
		this.collapse = collapse;
	}

	public boolean isCollLossType() {
		return collLossType;
	}

	public void setCollLossType(boolean collLossType) {
		this.collLossType = collLossType;
	}

	public boolean isDownTime() {
		return downTime;
	}

	public void setDownTime(boolean downTime) {
		this.downTime = downTime;
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

	public EDPIMCorrelations getEdpIMCorrelations() {
		return edpIMCorrelations;
	}

	public void setEdpIMCorrelations(EDPIMCorrelations edpIMCorrelations) {
		this.edpIMCorrelations = edpIMCorrelations;
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

}
