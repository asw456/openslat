/**
 * 
 */
package org.openslat.model.structure;

import java.util.concurrent.ConcurrentSkipListMap;

import org.openslat.model.edp.EDP;
import org.openslat.model.fragilityfunctions.FragilityFunction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author alan
 * 
 */
@JsonSerialize
public class Component {

	private FragilityFunction ff;
	
	@JsonIgnore
	private EDP edp;
	
	@JsonIgnore
	private ConcurrentSkipListMap<Double,Double> imMeanLossMap;
	@JsonIgnore
	private ConcurrentSkipListMap<Double,Double> imVarLossMap;
	
	public FragilityFunction getFf() {
		return ff;
	}
	public void setFf(FragilityFunction ff) {
		this.ff = ff;
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

	
	// generate random numbers for each DS
	// multiply random numbers by the Cholesky decomposition
	
	// reach into DS and get mean distribution, sample from distribution using random number for the DS
	
	// reach into DS and get sigma distribution, sample from distribution using random number for the DS
    // convert sigma (a LN distribution parameter) into variance of loss|DSi

}
