package org.openslat.model.structure;

import java.util.ArrayList;
import java.util.HashSet;

import org.openslat.model.collapse.LossCollapse;
import org.openslat.model.collapse.PC;
import org.openslat.model.edp.EDP;
import org.openslat.model.im.IM;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author alan.williams
 * 
 */
@JsonSerialize
public class Structure {

	private ArrayList<PerformanceGroup> performanceGroups = new ArrayList<PerformanceGroup>();
	private LossCollapse lossCollapse;
	private PC pc;
	private IM im;

	@JsonIgnore
	public ArrayList<Component> getComponents() {
		ArrayList<Component> components = new ArrayList<Component>();
		for (PerformanceGroup pg : this.getPerformanceGroups()) {
			components.addAll(pg.getComponents());
		}
		return components;
	}
	
	@JsonIgnore
	public HashSet<EDP> getEDPHashSet(){
		HashSet<EDP> edpHashSet = new HashSet<EDP>();
		for (PerformanceGroup pg: performanceGroups){
			edpHashSet.add(pg.getEdp());
		}
		return edpHashSet;
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
