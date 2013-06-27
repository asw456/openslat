package org.openslat.control;

import org.openslat.model.edp.EDP;
import org.openslat.model.edp.EDPIM;
import org.openslat.model.edp.EDPIMDiscreteModel;
import org.openslat.model.fragilityfunctions.DamageState;
import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.model.im.IMRDiscreteModel;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.model.structure.Structure;
import org.openslat.options.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class SlatInputStore {

	private Structure structure;
	private IM im;
	private CalculationOptions calculationOptions;

	@JsonIgnore
	private boolean verbose;
	//public SlatInputStore(
	//		@JsonProperty("structure") Structure structure,
	//		@JsonProperty("calculationOptions") CalculationOptions calculationOptions) {
	//	this.structure = structure;
	//	this.calculationOptions = calculationOptions;
	//}

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

		// set edps within pgs and components
		for (PerformanceGroup pg : this.getStructure().getPerformanceGroups()) {

			int pgId = pg.getId();
			for (EDP edp : this.getStructure().getEdps()) {
				if (edp.getIdentifier() == pgId) {
					
					pg.setEdp(edp);
					for (Component c : pg.getComponents()) {
						c.setEdp(edp);
					}
				}
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

		//setup IMR and parse table
		im.setSis(this);
		for (IMR imr : im.getiMR()) {
			if (imr.getModel().getClass().equals(IMRDiscreteModel.class)){
				((IMRDiscreteModel) imr.getModel()).parseTable();
				
			}
		}
		
		//setup parse edp table currently only type two implemented
		for (EDP edp : structure.getEdps()){
			for (EDPIM edpIm : edp.getEdpIM()){
				if (edpIm.getDistributionFunction().getClass().equals(EDPIMDiscreteModel.class)) {
					EDPIMDiscreteModel df = (EDPIMDiscreteModel) edpIm.getDistributionFunction();
					df.typeTwoTableInput();
				}
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

		// set im
		//System.err.println("temp debug:  " + this.im);
		structure.setIm(this.im);
		
		// temporary
		// this.setCalculationOptions(new CalculationOptions());
		// this.getCalculationOptions().setConsiderCollapse(false);
		// this.getCalculationOptions().setEpistemicUncertOptions(
		// new EpistemicUncertOptions());
		// this.getCalculationOptions().getEpistemicUncertOptions()
		// .setEpistemicUncert(false);

	}

	public IM getIm() {
		return im;
	}

	public void setIm(IM im) {
		this.im = im;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

}
