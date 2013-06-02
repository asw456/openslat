package org.openslat.control;

import org.openslat.model.fragilityfunctions.DamageState;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;
import org.openslat.options.EpistemicUncertOptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SlatInputStore {

	private Structure structure;
	private CalculationOptions calculationOptions;

	public SlatInputStore(
			@JsonProperty("structure") Structure structure,
			@JsonProperty("calculationOptions") CalculationOptions calculationOptions) {
		this.structure = structure;
		this.calculationOptions = calculationOptions;
	}

	public Structure getStructure() {
		return structure;
	}

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions co) {
		this.calculationOptions = co;
	}

	public void setupSis() {

		// set edps within components
		for (PerformanceGroup pg : this.getStructure().getPerformanceGroups()) {
			for (Component c : pg.getComponents()) {
				c.setEdp(pg.getEdp());
			}
		}

		
		// set sis in all classes that need to reference other things
		for (Component c : structure.getComponents()) {
			c.getEdp().setSis(this);
			c.getFf().setSis(this);
			for (DamageState ds : c.getFf().getDamageStates()) {
				ds.setSis(this);
			}
		}

		// set means in ff
		for (Component c : this.getStructure().getComponents()) {
			c.getFf().setMeans(getStructure().getComponents().size());
		}

		// set caches
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component c : pg.getComponents()) {
				c.setImMeanLossMap(pg.getImMeanLossMap());
				c.setImVarLossMap(pg.getImVarLossMap());
			}
		}

		// temporary
		//this.setCalculationOptions(new CalculationOptions());
		//this.getCalculationOptions().setConsiderCollapse(false);
		//this.getCalculationOptions().setEpistemicUncertOptions(
		//		new EpistemicUncertOptions());
		//this.getCalculationOptions().getEpistemicUncertOptions()
		//		.setEpistemicUncert(false);

	}

}
