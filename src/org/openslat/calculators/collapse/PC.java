package org.openslat.calculators.collapse;

import java.util.ArrayList;

import org.openslat.control.CalculationOptions;
import org.openslat.model.im.IM;

public class PC {

	private CalculationOptions calculationOptions;
	private IM im;
	private LossCollapse cost = new LossCollapse();
	private ArrayList<PCIM> pcim = new ArrayList<PCIM>();

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
			double rn = calculationOptions.getEpistemicLogicTreeValues().getRandPCIM();
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
			pcimi.setDistribution(table.get(i).get(0), table.get(i).get(1));
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

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
	}

	public IM getIm() {
		return im;
	}

	public void setIm(IM im) {
		this.im = im;
	}

	public LossCollapse getCost() {
		return cost;
	}

	public void setCost(LossCollapse cost) {
		this.cost = cost;
	}

}
