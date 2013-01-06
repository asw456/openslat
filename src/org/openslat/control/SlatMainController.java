package org.openslat.control;

import org.openslat.calculators.output.CompLossEdpOutput;
import org.openslat.calculators.output.ImROutput;
import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

public class SlatMainController {

	private Structure structure;
	private CalculationOptions calculationOptions;

	public String generateOutputString() {
		String outputString = "";

		CompLossEdpOutput compLossEdpOutput = new CompLossEdpOutput(this);
		
		
		
		return outputString;

		
		
		
		// TODO: presentation of these can be taken care of in the browser
		// ImROutput imROutput = new ImROutput();
		// imROutput.setSlatMainController(this);
		// EDP-IM output
		// 

	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
	}

}
