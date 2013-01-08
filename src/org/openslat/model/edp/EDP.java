package org.openslat.model.edp;

import java.util.ArrayList;

import org.openslat.control.SlatMainController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EDP {

	@JsonIgnore
	private SlatMainController slatMC;
	private String name;
	private ArrayList<EDPIM> edpIM = new ArrayList<EDPIM>();

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
			double rn = slatMC.getCalculationOptions().getEpistemicLogicTreeValues().getRandEDPIM();
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

	public SlatMainController getSlatMC() {
		return slatMC;
	}

	public void setSlatMC(SlatMainController slatMC) {
		this.slatMC = slatMC;
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
}
