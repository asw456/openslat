package org.openslat.model.collapse;

import java.util.ArrayList;
import org.openslat.control.SlatInputStore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class BuildingPancaked {
	
	@JsonIgnore
	private SlatInputStore openslat;
	private ArrayList<PCIM> pcim = new ArrayList<PCIM>();
	private LossCollapse lossCollapse;

	/**
	 * Randomly returns an PC-IM relationship according to the epistemic
	 * weights.
	 * 
	 * @return
	 */
	public PCIM getPcim() {
		// TODO : exceptions..
		if (pcim.size() == 0) {
			return null;
		} else if (pcim.size() == 1) {
			return pcim.get(0);
		} else {
			double rn = openslat.getCalculationOptions()
					.getEpistemicLogicTreeValues().getRandPCIM();
			ArrayList<Double> epistemicWeights = new ArrayList<Double>();
			double total = 0;
			for (PCIM each : pcim) {
				total = total + each.getEpistemicWeight();
				epistemicWeights.add(total);
			}
			// TODO: check total adds to 1
			for (double each : epistemicWeights) {
				if (rn <= each) {
					return pcim.get(epistemicWeights.indexOf(each));
				}
			}
		}
		return null;
	}

	public void parseTable(ArrayList<ArrayList<Double>> table) {

		for (int i = 0; i < table.size(); ++i) {
			PCIM pcimi = new PCIM();
			pcimi.generateDistribution(table.get(i).get(0), table.get(i).get(1));
			pcimi.setEpistemicWeight(table.get(i).get(2));
			pcim.add(pcimi);
		}

	}

	public void addPcim(PCIM pcim) {
		this.pcim.add(pcim);
	}

	public boolean removePcim(PCIM pcim) {
		return this.pcim.remove(pcim);
	}

	public SlatInputStore getOpenslat() {
		return openslat;
	}

	public void setOpenslat(SlatInputStore openslat) {
		this.openslat = openslat;
	}

	public void setPcim(ArrayList<PCIM> pcim) {
		this.pcim = pcim;
	}

	public LossCollapse getLossCollapse() {
		return lossCollapse;
	}

	public void setLossCollapse(LossCollapse lossCollapse) {
		this.lossCollapse = lossCollapse;
	}
}
