package org.openslat.model.structure;

import java.util.ArrayList;
import java.util.HashSet;

import org.openslat.model.collapse.CollapseDemolition;
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
	private ArrayList<EDP> edps;
	private CollapseDemolition collapse;
	private CollapseDemolition demolition;
	@JsonIgnore
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

	public CollapseDemolition getPc() {
		return collapse;
	}

	public void setPc(CollapseDemolition pc) {
		this.collapse = pc;
	}

	public IM getIm() {
		return im;
	}

	public void setIm(IM im) {
		this.im = im;
	}

	public CollapseDemolition getDemolition() {
		return demolition;
	}

	public void setDemolition(CollapseDemolition demolition) {
		this.demolition = demolition;
	}

	public CollapseDemolition getCollapse() {
		return collapse;
	}

	public void setCollapse(CollapseDemolition collapse) {
		this.collapse = collapse;
	}

	public void setPerformanceGroups(ArrayList<PerformanceGroup> performanceGroups) {
		this.performanceGroups = performanceGroups;
	}

	public ArrayList<EDP> getEdps() {
		return edps;
	}

	public void setEdps(ArrayList<EDP> edps) {
		this.edps = edps;
	}

}
