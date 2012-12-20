/**
 * 
 */
package org.openslat.model.structure;

import org.openslat.model.edp.EDP;
import org.openslat.model.fragilityfunctions.FragilityFunction;

/**
 * @author alan
 * 
 */
public class Component {

	private FragilityFunction ff;
	private EDP edp;
	
	// generate random numbers for each DS
	// multiply random numbers by the Cholesky decomposition
	
	// reach into DS and get mean distribution, sample from distribution using random number for the DS
	
	// reach into DS and get sigma distribution, sample from distribution using random number for the DS
    // convert sigma (a LN distribution parameter) into variance of loss|DSi

	public EDP getEdp() {
		return edp;
	}

	public void setEdp(EDP edp) {
		this.edp = edp;
	}

	public FragilityFunction getFf() {
		return ff;
	}

	public void setFf(FragilityFunction ff) {
		this.ff = ff;
	}

}
