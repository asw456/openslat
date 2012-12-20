package org.openslat.model.structure;

import java.util.ArrayList;
import org.openslat.calculators.collapse.LossCollapse;
import org.openslat.calculators.collapse.PC;
import org.openslat.model.im.IM;

/**
 * @author alan.williams
 * 
 */
public class Structure {

	private ArrayList<PerformanceGroup> performanceGroups = new ArrayList<PerformanceGroup>();
	private LossCollapse lossCollapse;
	private PC pc;
	private IM im = new IM();

	public ArrayList<Component> getComponents() {
		ArrayList<Component> components = new ArrayList<Component>();
		for (PerformanceGroup pg : this.getPerformanceGroups()) {
			components.addAll(pg.getComponents());
		}
		return components;
	}

	public ArrayList<PerformanceGroup> getPerformanceGroups() {
		return performanceGroups;
	}

	public void addPerformanceGroup(PerformanceGroup performanceGroup) {
		this.performanceGroups.add(performanceGroup);
	}

	public void removePerformanceGroup(PerformanceGroup performanceGroup) {
		this.performanceGroups.remove(performanceGroup);
	}

	public PC getPc() {
		return pc;
	}

	public void setPc(PC pc) {
		this.pc = pc;
	}

	public IM getIm() {
		return im;
	}

	public void setIm(IM im) {
		this.im = im;
	}

	public LossCollapse getLossCollapse() {
		return lossCollapse;
	}

	public void setLossCollapse(LossCollapse lossCollapse) {
		this.lossCollapse = lossCollapse;
	}

}
