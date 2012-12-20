package org.openslat.model.im;

import java.util.ArrayList;
import org.openslat.control.CalculationOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Session Bean implementation class IM
 */
@JsonSerialize
public class IM {

	@JsonProperty
	private String name = "defaultName";
	@JsonIgnore
	private CalculationOptions calculationOptions;
	@JsonProperty
	private ArrayList<IMR> iMR = new ArrayList<IMR>();

//	/**
//	 * Randomly returns an IM-R relationship according to the epistemic weights.
//	 * 
//	 * @return
//	 */
//	public IMR getImr() {
//		// TODO : exceptions..
//		if (iMR.size() == 0) {
//			return null;
//		} else if (iMR.size() == 1) {
//			return iMR.get(0);
//		} else {
//			double rn = calculationOptions.getEpistemicLogicTreeValues().getRandIMR();
//			ArrayList<Double> epistemicWeights = new ArrayList<Double>();
//			double total = 0;
//			for (IMR each : iMR) {
//				total = total + each.getEpistemicWeight();
//				epistemicWeights.add(total);
//			}
//			// TODO: check total adds to 1
//			for (double each : epistemicWeights) {
//				if (rn <= each) {
//					return iMR.get(epistemicWeights.indexOf(each));
//				}
//			}
//		}
//		return null;
//	}

	public CalculationOptions getCalculationOptions() {
		return calculationOptions;
	}

	public void setCalculationOptions(CalculationOptions calculationOptions) {
		this.calculationOptions = calculationOptions;
	}

	public ArrayList<IMR> getiMR() {
		return iMR;
	}

	public void setiMR(ArrayList<IMR> iMR) {
		this.iMR = iMR;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
