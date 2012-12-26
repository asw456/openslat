package org.openslat.jsonparser;

import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SlatInputStore {

	private final Structure structure;
	private final CalculationOptions calculationOptions;
	
	public SlatInputStore(@JsonProperty("structure") Structure structure, @JsonProperty("calculationOptions") CalculationOptions calculationOptions) {
		this.structure = structure;
		this.calculationOptions = calculationOptions;
	}
	
	public Structure getStructure(){
		return structure;
	}
	
	public CalculationOptions getCalculationOptions(){
		return calculationOptions;
	}
}
