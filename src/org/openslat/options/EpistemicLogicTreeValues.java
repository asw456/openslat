package org.openslat.options;

import org.openslat.numerical.RNGenerator;

/**
 * @author alan
 * 
 */
public class EpistemicLogicTreeValues {

	private double randEDPIM = 0;
	private double randIMR = 0;
	private double randPCIM = 0;

	public void setEpistemicValues() {
		randEDPIM = RNGenerator.uniformRN();
		randIMR = RNGenerator.uniformRN();
		randPCIM = RNGenerator.uniformRN();
	}

	public double getRandEDPIM() {
		return randEDPIM;
	}

	public void setRandEDPIM(double randEDPIM) {
		this.randEDPIM = randEDPIM;
	}

	public double getRandIMR() {
		return randIMR;
	}

	public void setRandIMR(double randIMR) {
		this.randIMR = randIMR;
	}

	public double getRandPCIM() {
		return randPCIM;
	}

	public void setRandPCIM(double randPCIM) {
		this.randPCIM = randPCIM;
	}

}
