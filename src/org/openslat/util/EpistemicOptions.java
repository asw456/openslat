/**
 * 
 */
package org.openslat.util;

/**
 * @author alan
 * 
 */
public class EpistemicOptions {

	private boolean EPI_UNC = false;
	private boolean E_DSEDP = false;
	private boolean E_LDS = false;
	private boolean E_LC = false;
	private boolean E_Corr = false;

	public boolean isEPI_UNC() {
		return EPI_UNC;
	}

	public void setEPI_UNC(boolean ePI_UNC) {
		EPI_UNC = ePI_UNC;
	}

	public boolean isE_DSEDP() {
		return E_DSEDP;
	}

	public void setE_DSEDP(boolean e_DSEDP) {
		E_DSEDP = e_DSEDP;
	}

	public boolean isE_LDS() {
		return E_LDS;
	}

	public void setE_LDS(boolean e_LDS) {
		E_LDS = e_LDS;
	}

	public boolean isE_LC() {
		return E_LC;
	}

	public void setE_LC(boolean e_LC) {
		E_LC = e_LC;
	}

	public boolean isE_Corr() {
		return E_Corr;
	}

	public void setE_Corr(boolean e_Corr) {
		E_Corr = e_Corr;
	}

}
