package org.openslat.control;

import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

public class Openslat {

	private Structure structure;
	private CalculationOptions calculationOptions;
	
	
	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Structure getStructure() {
		return structure;
	}

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
	}
	
}
