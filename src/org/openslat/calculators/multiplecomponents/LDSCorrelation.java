/**
 * 
 */
package org.openslat.calculators.multiplecomponents;

import java.util.HashMap;

import org.openslat.control.SlatMainController;
import org.openslat.model.fragilityfunctions.FragilityFunction;
import org.openslat.model.fragilityfunctions.Material;
import org.openslat.model.structure.Component;
import org.openslat.numerical.RNGenerator;

/**
 * To obtain the CORRELATION (L|DS) for different components (my description)
 * fragility functions (fortran description) returns CORR_Lossij_DS -- the
 * correlation between LDS
 * 
 * @author alan
 * 
 */
public class LDSCorrelation {

	private SlatMainController slatMC;

	public double corr_Lossij_DS(Component componenti, Component componentj) {
		if (slatMC.getCalculationOptions().getCorrelationOptions().getCOR_LDS() == 0) {
			return 0;
		} else if (slatMC.getCalculationOptions().getCorrelationOptions()
				.getCOR_LDS() == 1) {
			return 1;
		} else {
			return partialCorrelation(componenti, componentj);
		}
	}

	private double partialCorrelation(Component componenti, Component componentj) {

		// Define the macro factors for the generalised equi-correlated model
		// Values below based on: Bradley, BA, Lee, DS. Component correlations
		// in structure-specific
		// seismic loss estimation. Earthquake Engineering and Structural
		// Dynamics 2010; 39(3): 237-258.
		double var_mat = 0.5; // common to same materials
		double var_comp = 0.35; // common to same component (fragility) types
		double var_ij = 0.15; // common to individual components

		Material materiali = componenti.getFf().getMaterial();
		Material materialj = componentj.getFf().getMaterial();

		if (slatMC.getCalculationOptions().getEpistemicUncertOptions()
				.isEpistemicUncertDSEDPCorrelations()) {

			HashMap<Material, Double> randLDSCorrMaterial = slatMC
					.getCalculationOptions().getEpistemicCorrArrays()
					.getRandLDSCorrMaterial();
			HashMap<FragilityFunction, Double> randLDSCorrPG = slatMC
					.getCalculationOptions().getEpistemicCorrArrays()
					.getRandLDSCorrPG();

			double pc_var = 0.3; // half of the width of the uniform
									// distirbution for the correlations

			double var_mati = var_mat
					* (1.0 + pc_var
							* (2.0 * randLDSCorrMaterial.get(materiali) - 0.5));
			double var_matj = var_mat
					* (1.0 + pc_var
							* (2.0 * randLDSCorrMaterial.get(materialj) - 0.5));
			var_mat = Math.sqrt(var_mati * var_matj);

			double var_compi = var_comp
					* (1.0 + pc_var
							* (2.0 * randLDSCorrPG.get(componenti.getFf()) - 0.5));
			double var_compj = var_comp
					* (1.0 + pc_var
							* (2.0 * randLDSCorrPG.get(componenti.getFf()) - 0.5));
			var_comp = Math.sqrt(var_compi * var_compj);

			double randij = RNGenerator.uniformRN();
			var_ij = var_ij * (1.0 + pc_var * (2.0 * randij - 0.5));
		}

		int delta_mat = 0;
		int delta_comp = 0;
		if (materiali.equals(materialj)) {
			delta_mat = 1;
		}
		if (componenti.getFf().equals(componentj.getFf())) {
			delta_comp = 1;
		}

		// CORR_Lossij_DS
		return (delta_mat * var_mat + delta_comp * var_comp)
				/ (var_mat + var_comp + var_ij);
	}

	public SlatMainController getSlatMainController() {
		return slatMC;
	}

	public void setSlatMainController(SlatMainController slatMainController) {
		this.slatMC = slatMainController;
	}
}
