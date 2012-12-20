package org.openslat.control;

public class EpistemicUncertOptions {

	private boolean epistemicUncert = false;

	// used in
	private boolean epistemicUncertDSEDP = false;
	private boolean epistemicUncertLDS = false;

	// used in 
	private boolean epistemicUncertDSEDPCorrelations = false;
	private boolean epistemicUncertLDSCorrelations = false;

	// not currently used as only uses logic tree if more than 1 IMR/PCIM
	private boolean epistemicUncertIMR = false;
	private boolean epistemicUncertPCIM = false;

	// if collapse loss is directly specified with meand/uncert and sigma/uncert
	private boolean epistemicUncertLossCollapse = false;

	public boolean isEpistemicUncert() {
		return epistemicUncert;
	}

	public void setEpistemicUncert(boolean epistemicUncert) {
		this.epistemicUncert = epistemicUncert;
	}

	public boolean isEpistemicUncertDSEDP() {
		return epistemicUncertDSEDP;
	}

	public void setEpistemicUncertDSEDP(boolean epistemicUncertDSEDP) {
		this.epistemicUncertDSEDP = epistemicUncertDSEDP;
	}

	public boolean isEpistemicUncertLDS() {
		return epistemicUncertLDS;
	}

	public void setEpistemicUncertLDS(boolean epistemicUncertLDS) {
		this.epistemicUncertLDS = epistemicUncertLDS;
	}

	public boolean isEpistemicUncertDSEDPCorrelations() {
		return epistemicUncertDSEDPCorrelations;
	}

	public void setEpistemicUncertDSEDPCorrelations(
			boolean epistemicUncertDSEDPCorrelations) {
		this.epistemicUncertDSEDPCorrelations = epistemicUncertDSEDPCorrelations;
	}

	public boolean isEpistemicUncertLDSCorrelations() {
		return epistemicUncertLDSCorrelations;
	}

	public void setEpistemicUncertLDSCorrelations(
			boolean epistemicUncertLDSCorrelations) {
		this.epistemicUncertLDSCorrelations = epistemicUncertLDSCorrelations;
	}

	public boolean isEpistemicUncertIMR() {
		return epistemicUncertIMR;
	}

	public void setEpistemicUncertIMR(boolean epistemicUncertIMR) {
		this.epistemicUncertIMR = epistemicUncertIMR;
	}

	public boolean isEpistemicUncertPCIM() {
		return epistemicUncertPCIM;
	}

	public void setEpistemicUncertPCIM(boolean epistemicUncertPCIM) {
		this.epistemicUncertPCIM = epistemicUncertPCIM;
	}

	public boolean isEpistemicUncertLossCollapse() {
		return epistemicUncertLossCollapse;
	}

	public void setEpistemicUncertCostCollapse(
			boolean epistemicUncertCostCollapse) {
		this.epistemicUncertLossCollapse = epistemicUncertCostCollapse;
	}

}
