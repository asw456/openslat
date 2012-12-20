/**
 * 
 */
package org.openslat.calculators.multiplecomponents;

import java.util.HashMap;

import org.openslat.control.CalculationOptions;
import org.openslat.model.edp.EDP;
import org.openslat.model.fragilityfunctions.FragilityFunction;
import org.openslat.model.fragilityfunctions.Material;
import org.openslat.model.structure.Component;
import org.openslat.numerical.RNGenerator;

/**
 * To obtain the CORRELATIONS (DS|EDP) for different components (my description)
 * fragility functions (fortran description) returns CORR_DSij_EDP -- the
 * correlation between DSEDPij
 * 
 * @author alan
 * 
 */
public class DSEDPCorrelation {

	private CalculationOptions calculationOptions;

	public double corr_DSij_EDP(Component componenti, Component componentj) {
		if (calculationOptions.getCorrelationOptions().getCOR_DSEDP() == 0) {
			return 0;
		} else if (calculationOptions.getCorrelationOptions().getCOR_DSEDP() == 1) {
			return 1;
		} else {
			return partialCorrelation(componenti, componentj);
		}
	}

	private double partialCorrelation(Component componenti, Component componentj) {

		// Define the macro factors for the generalised equi-correlated model
		double var_demand = 0.5; // common to same materials
		double var_capacity = 0.35; // common to same component (fragility)
									// types
		double var_structure = 0.67 * var_demand; // common to everything in
													// structure
		double var_edp = 0.33 * var_demand; // common to same edps
		double var_mat = 0.5 * var_capacity; // common to same materials
		double var_comp = 0.35 * var_capacity; // common to same component
												// (fragility) types
		double var_ij = 0.15 * var_capacity; // common to individual components

		Material materiali = componenti.getFf().getMaterial();
		Material materialj = componentj.getFf().getMaterial();

		if (calculationOptions.getEpistemicUncertOptions()
				.isEpistemicUncertDSEDPCorrelations()) {

			double randDSEDPCorrStructure = calculationOptions.getEpistemicCorrArrays()
					.getRandDSEDPCorrStructure();
			HashMap<EDP, Double> randDSEDPCorrEDP = calculationOptions
					.getEpistemicCorrArrays().getRandDSEDPCorrEDP();
			HashMap<Material, Double> randDSEDPCorrMaterial = calculationOptions
					.getEpistemicCorrArrays().getRandDSEDPCorrMaterial();
			HashMap<FragilityFunction, Double> randDSEDPCorrPG = calculationOptions
					.getEpistemicCorrArrays().getRandDSEDPCorrPG();

			double pc_var = 0.3; // half of the width of the uniform
									// distirbution for the correlations

			var_structure = var_structure
					* (1.0 + pc_var * (2.0 * randDSEDPCorrStructure - 0.5));
			double var_edpi = var_edp
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrEDP.get(componenti.getEdp()) - 0.5));
			double var_edpj = var_edp
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrEDP.get(componentj.getEdp()) - 0.5));
			var_edp = Math.sqrt(var_edpi * var_edpj);

			double var_mati = var_mat
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrMaterial.get(materiali) - 0.5));
			double var_matj = var_mat
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrMaterial.get(materialj) - 0.5));
			var_mat = Math.sqrt(var_mati * var_matj);

			double var_compi = var_comp
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrPG.get(componenti
									.getFf()) - 0.5));
			double var_compj = var_comp
					* (1.0 + pc_var
							* (2.0 * randDSEDPCorrPG.get(componenti
									.getFf()) - 0.5));
			var_comp = Math.sqrt(var_compi * var_compj);

			double randij = RNGenerator.uniformRN();
			var_ij = var_ij * (1.0 + pc_var * (2.0 * randij - 0.5));
		}

		int delta_edp = 0;
		int delta_mat = 0;
		int delta_comp = 0;
		if (componenti.getEdp().equals(componentj.getEdp())) {
			delta_edp = 1;
		}
		if (materiali.equals(materialj)) {
			delta_mat = 1;
		}
		if (componenti.getFf().equals(componentj.getFf())) {
			delta_comp = 1;
		}

		// CORR_DSij_EDP
		return (var_structure + delta_edp * var_edp + delta_mat * var_mat + delta_comp
				* var_comp)
				/ (var_structure + var_edp + var_mat + var_comp + var_ij);
	}
}
