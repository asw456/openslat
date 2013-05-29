package org.openslat.model.structure;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.openslat.model.edp.EDP;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PerformanceGroup {

	private ArrayList<Component> components = new ArrayList<Component>();
	private String name;
	private EDP edp;
	
	@JsonIgnore
	private ConcurrentSkipListMap<Double,Double> imMeanLossMap = new ConcurrentSkipListMap<Double,Double>();
	@JsonIgnore
	private ConcurrentSkipListMap<Double,Double> imVarLossMap = new ConcurrentSkipListMap<Double,Double>();

	
	public void setNumberOfComponents() {
		for (Component each : components) {
			each.getFf().setMeans(components.size());
		}
	}

	public void addComponent(Component c) {
		components.add(c);
		setNumberOfComponents();
	}

	public void removeComponent(Component c) {
		components.remove(c);
	}

	public ArrayList<Component> getComponents() {
		return components;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}
	public EDP getEdp() {
		return edp;
	}

	public void setEdp(EDP edp) {
		this.edp = edp;
	}

	public ConcurrentSkipListMap<Double, Double> getImMeanLossMap() {
		return imMeanLossMap;
	}

	public void setImMeanLossMap(ConcurrentSkipListMap<Double, Double> imMeanLossMap) {
		this.imMeanLossMap = imMeanLossMap;
	}

	public ConcurrentSkipListMap<Double, Double> getImVarLossMap() {
		return imVarLossMap;
	}

	public void setImVarLossMap(ConcurrentSkipListMap<Double, Double> imVarLossMap) {
		this.imVarLossMap = imVarLossMap;
	}
}
