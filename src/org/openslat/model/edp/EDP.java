package org.openslat.model.edp;

import java.util.ArrayList;

import org.openslat.control.SlatInputStore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EDP {

	@JsonIgnore
	private SlatInputStore sis;
	private String name;
	private ArrayList<EDPIM> edpIM = new ArrayList<EDPIM>();
	private double maxValue;
	
	/**
	 * Randomly returns an EDP-IM relationship according to the epistemic
	 * weights.
	 * 
	 * @return
	 */
	public EDPIM retrieveEdpIM() {
		// TODO : exceptions..
		if (edpIM.size() == 0) {
			return null;
		} else if (edpIM.size() == 1) {
			return edpIM.get(0);
		} else {
			double rn = sis.getCalculationOptions().getEpistemicLogicTreeValues().getRandEDPIM();
			ArrayList<Double> epistemicWeights = new ArrayList<Double>();
			double total = 0;
			for (EDPIM each : edpIM) {
				total = total + each.getEpistemicWeight();
				epistemicWeights.add(total);
			}
			for (double each : epistemicWeights) {
				if (rn <= each) {
					return edpIM.get(epistemicWeights.indexOf(each));
				}
			}
		}
		return null;
	}

	public void addEdpIM(EDPIM edpIM) {
		this.edpIM.add(edpIM);
	}

	public boolean removeEdpIM(EDPIM edpIM) {
		return this.edpIM.remove(edpIM);
	}

	public void setEdpIM(ArrayList<EDPIM> edpIM) {
		this.edpIM = edpIM;
	}

	public SlatInputStore getSis() {
		return sis;
	}

	public void setSis(SlatInputStore slatMC) {
		this.sis = slatMC;
	}

	public ArrayList<EDPIM> getEdpIM() {
		return edpIM;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edpIM == null) ? 0 : edpIM.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EDP))
			return false;
		EDP other = (EDP) obj;
		if (edpIM == null) {
			if (other.edpIM != null)
				return false;
		} else if (!edpIM.equals(other.edpIM))
			return false;
		return true;
	}
}
