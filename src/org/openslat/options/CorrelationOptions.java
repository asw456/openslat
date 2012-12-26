/**
 * 
 */
package org.openslat.options;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author alan
 * 
 */
@JsonSerialize
public class CorrelationOptions {

	private int COR_LDS = 0;
	private int COR_DSEDP = 0;
	private int COR_EDPIM = 0;
	private int CORE_LDS = 0;
	private int CORE_DSEDP = 0;

	public int getCOR_LDS() {
		return COR_LDS;
	}

	public void setCOR_LDS(int cOR_LDS) {
		COR_LDS = cOR_LDS;
	}

	public int getCOR_DSEDP() {
		return COR_DSEDP;
	}

	public void setCOR_DSEDP(int cOR_DSEDP) {
		COR_DSEDP = cOR_DSEDP;
	}

	public int getCOR_EDPIM() {
		return COR_EDPIM;
	}

	public void setCOR_EDPIM(int cOR_EDPIM) {
		COR_EDPIM = cOR_EDPIM;
	}

	public int getCORE_LDS() {
		return CORE_LDS;
	}

	public void setCORE_LDS(int cORE_LDS) {
		CORE_LDS = cORE_LDS;
	}

	public int getCORE_DSEDP() {
		return CORE_DSEDP;
	}

	public void setCORE_DSEDP(int cORE_DSEDP) {
		CORE_DSEDP = cORE_DSEDP;
	}
	
}
