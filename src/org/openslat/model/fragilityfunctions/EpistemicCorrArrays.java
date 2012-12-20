/**
 * 
 */
package org.openslat.model.fragilityfunctions;

import org.openslat.model.edp.EDP;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.model.structure.Structure;
import org.openslat.numerical.RNGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author alan
 * 
 */
public class EpistemicCorrArrays {

	private double randDSEDPCorrStructure;
	private HashMap<EDP, Double> randDSEDPCorrEDP = new HashMap<EDP, Double>();
	private HashMap<Material, Double> randDSEDPCorrMaterial = new HashMap<Material, Double>();
	private HashMap<FragilityFunction, Double> randDSEDPCorrPG = new HashMap<FragilityFunction, Double>();

	private HashMap<Material, Double> randLDSCorrMaterial = new HashMap<Material, Double>();
	private HashMap<FragilityFunction, Double> randLDSCorrPG = new HashMap<FragilityFunction, Double>();

	public void generateEpistemicArrays(Structure structure) {

		// generate correlation random variations
		generateRandLDSCorrMaterial(structure);
		generateRandLDSCorrPG(structure);
		generateRandDSEDPCorrEDP(structure);
		generateRandDSEDPCorrMaterial(structure);
		generateRandDSEDPCorrPG(structure);

		// can't remember what this was for
		// for (PerformanceGroup pg : structure.getPerformanceGroups()) {
		// for (Component component : pg.getComponents()) {
		// component.getFf().);
		// }
		// }
	}

	private void generateRandLDSCorrMaterial(Structure structure) {
		// find number of materials from Structure -> PG -> component
		HashSet<Material> materialHashSet = new HashSet<Material>();
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component component : pg.getComponents()) {
				materialHashSet.add(component.getFf().getMaterial());
			}
		}
		Iterator<Material> it = materialHashSet.iterator();
		while (it.hasNext()) {
			randLDSCorrMaterial.put(it.next(), RNGenerator.uniformRN());
		}
	}

	private void generateRandLDSCorrPG(Structure structure) {
		// find number of materials from Structure -> PG -> component
		HashSet<FragilityFunction> ffHashSet = new HashSet<FragilityFunction>();
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component component : pg.getComponents()) {
				ffHashSet.add(component.getFf());
			}
		}
		Iterator<FragilityFunction> it = ffHashSet.iterator();
		while (it.hasNext()) {
			randLDSCorrPG.put(it.next(), RNGenerator.uniformRN());
		}
	}

	private void generateRandDSEDPCorrEDP(Structure structure) {
		// find number of edp from Structure -> PG -> component
		HashSet<EDP> edpHashSet = new HashSet<EDP>();
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component component : pg.getComponents()) {
				edpHashSet.add(component.getEdp());
			}
		}
		Iterator<EDP> it = edpHashSet.iterator();
		while (it.hasNext()) {
			randDSEDPCorrEDP.put(it.next(), RNGenerator.uniformRN());
		}
	}

	private void generateRandDSEDPCorrMaterial(Structure structure) {
		// find number of materials from Structure -> PG -> component
		HashSet<Material> materialHashSet = new HashSet<Material>();
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component component : pg.getComponents()) {
				materialHashSet.add(component.getFf().getMaterial());
			}
		}
		Iterator<Material> it = materialHashSet.iterator();
		while (it.hasNext()) {
			randDSEDPCorrMaterial.put(it.next(), RNGenerator.uniformRN());
		}
	}

	private void generateRandDSEDPCorrPG(Structure structure) {
		// find number of materials from Structure -> PG -> component
		HashSet<FragilityFunction> ffHashSet = new HashSet<FragilityFunction>();
		for (PerformanceGroup pg : structure.getPerformanceGroups()) {
			for (Component component : pg.getComponents()) {
				ffHashSet.add(component.getFf());
			}
		}
		Iterator<FragilityFunction> it = ffHashSet.iterator();
		while (it.hasNext()) {
			randDSEDPCorrPG.put(it.next(), RNGenerator.uniformRN());
		}
	}

	public HashMap<Material, Double> getRandLDSCorrMaterial() {
		return randLDSCorrMaterial;
	}

	public void setRandLDSCorrMaterial(
			HashMap<Material, Double> randLDSCorrMaterial) {
		this.randLDSCorrMaterial = randLDSCorrMaterial;
	}

	public HashMap<FragilityFunction, Double> getRandLDSCorrPG() {
		return randLDSCorrPG;
	}

	public void setRandLDSCorrPG(
			HashMap<FragilityFunction, Double> randLDSCorrPG) {
		this.randLDSCorrPG = randLDSCorrPG;
	}

	public HashMap<EDP, Double> getRandDSEDPCorrEDP() {
		return randDSEDPCorrEDP;
	}

	public void setRandDSEDPCorrEDP(HashMap<EDP, Double> randDSEDPCorrEDP) {
		this.randDSEDPCorrEDP = randDSEDPCorrEDP;
	}

	public HashMap<Material, Double> getRandDSEDPCorrMaterial() {
		return randDSEDPCorrMaterial;
	}

	public void setRandDSEDPCorrMaterial(
			HashMap<Material, Double> randDSEDPCorrMaterial) {
		this.randDSEDPCorrMaterial = randDSEDPCorrMaterial;
	}

	public HashMap<FragilityFunction, Double> getRandDSEDPCorrPG() {
		return randDSEDPCorrPG;
	}

	public void setRandDSEDPCorrPG(
			HashMap<FragilityFunction, Double> randDSEDPCorrPG) {
		this.randDSEDPCorrPG = randDSEDPCorrPG;
	}

	public double getRandDSEDPCorrStructure() {
		return randDSEDPCorrStructure;
	}

	public void setRandDSEDPCorrStructure(double randDSEDPCorrStructure) {
		this.randDSEDPCorrStructure = randDSEDPCorrStructure;
	}
}
