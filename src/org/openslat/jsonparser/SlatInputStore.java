package org.openslat.jsonparser;

import org.openslat.model.im.IM;
import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SlatInputStore {

	private final IM im;
	private final Structure structure;
	private final CalculationOptions calculationOptions;
	
	public SlatInputStore(@JsonProperty("im") IM im, @JsonProperty("structure") Structure structure, @JsonProperty("calculationOptions") CalculationOptions calculationOptions) {
		this.im = im;
		this.structure = structure;
		this.calculationOptions = calculationOptions;
	}

	public IM getIm() {
		return im;
	}
	
	public Structure getStructure(){
		return structure;
	}
	
	public CalculationOptions getCalculationOptions(){
		return calculationOptions;
	}
}
