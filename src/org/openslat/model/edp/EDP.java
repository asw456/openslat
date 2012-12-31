package org.openslat.model.edp;

import java.util.ArrayList;

import org.openslat.options.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EDP {

	@JsonIgnore
	private CalculationOptions calculationOptions;
	private ArrayList<EDPIM> edpIM = new ArrayList<EDPIM>();

	/**
	 * Randomly returns an EDP-IM relationship according to the epistemic
	 * weights.
	 * 
	 * @return
	 */
	public EDPIM getEdpIM() {
		// TODO : exceptions..
		if (edpIM.size() == 0) {
			return null;
		} else if (edpIM.size() == 1) {
			return edpIM.get(0);
		} else {
			double rn = calculationOptions.getEpistemicLogicTreeValues().getRandEDPIM();
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

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
	}

	public void setEdpIM(ArrayList<EDPIM> edpIM) {
		this.edpIM = edpIM;
	}
}
