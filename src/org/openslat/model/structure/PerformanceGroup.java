package org.openslat.model.structure;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class PerformanceGroup {

	private ArrayList<Component> components = new ArrayList<Component>();
	private String name;

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

}
