package org.openslat.control;

import org.openslat.model.fragilityfunctions.DamageState;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SlatInputStore {

	private Structure structure;
	private CalculationOptions calculationOptions;
	
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

	public void setCalculationOptions(CalculationOptions co){
		this.calculationOptions = co;
	}
	
	public void populateSis() {
		for (Component c : structure.getComponents()){
			c.getEdp().setSis(this);
			c.getFf().setSis(this);
			for (DamageState ds : c.getFf().getDamageStates()){
				ds.setSis(this);
			}
		}
		
	}
	
	
}
