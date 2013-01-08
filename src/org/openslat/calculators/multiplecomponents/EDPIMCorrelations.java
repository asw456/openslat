/**
 * 
 */
package org.openslat.calculators.multiplecomponents;

import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.openslat.model.structure.Component;

/**
 * Compute correlations if IDA form of data was input. This class computes a 3/4
 * dimensional array giving the correlations between different EDPs as a
 * function of the IM level. If the EDP data is not given then this array is
 * left unpopulated. it has dimensions (COVN_EDPIM,N_EDP+1,N_EDP). the array
 * (:,1,1) gives the IM values which the correlation coefficients are computed
 * for and element (k,i+1,j) gives the correlation between EDPi and EDPj for an
 * IM level given in (k,1,1). Possible 4th dim. is epistemic if these are given.
 * 
 * @author alan
 * 
 */
public class EDPIMCorrelations {

	public static double edpCorrelation(Component componenti,
			Component componentj, double im) {

		PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

		// TODO: same IM not checked?? but never checked elsewhere in program
		// TODO: same IM values down column 1 of table not checked
		if (componenti.getEdp().retrieveEdpIM().getDistributionFunction().getTable() == null) {
			return 0; // TODO: exception, hard failure instead of soft
		}
		if (componentj.getEdp().retrieveEdpIM().getDistributionFunction().getTable() == null) {
			return 0; // TODO: exception, hard failure instead of soft
		}
		if (componenti.getEdp().retrieveEdpIM().getDistributionFunction().getTable()
				.size() != componentj.getEdp().retrieveEdpIM()
				.getDistributionFunction().getTable().size()) {
			return 0; // TODO: exception, hard failure instead of soft
		}

		ArrayList<ArrayList<Double>> tablei = componenti.getEdp().retrieveEdpIM()
				.getDistributionFunction().getTable();
		ArrayList<ArrayList<Double>> tablej = componentj.getEdp().retrieveEdpIM()
				.getDistributionFunction().getTable();

		double[] imi = new double[tablei.size()];
		double[] correlations = new double[tablej.size()];

		for (int p = 0; p < tablei.size(); ++p) {

			imi[p] = tablei.get(p).get(0).doubleValue(); // same as table j
			ArrayList<Double> edpRowI = tablei.get(p);
			ArrayList<Double> edpRowJ = tablej.get(p);
			edpRowI.remove(0);
			edpRowJ.remove(0);

			correlations[p] = pearsonsCorrelation.correlation(ArrayUtils
					.toPrimitive(edpRowI.toArray(new Double[edpRowI.size()])),
					ArrayUtils.toPrimitive(edpRowJ.toArray(new Double[edpRowI
							.size()])));

		}

		PolynomialSplineFunction correlationIMRelationship = new LinearInterpolator()
				.interpolate(imi, correlations);

		// flat if im value lies outside range
		if (im < correlationIMRelationship.getKnots()[0]) {
			im = correlationIMRelationship.getKnots()[0];
		}

		if (im > correlationIMRelationship.getKnots()[correlationIMRelationship
				.getKnots().length - 1]) {
			im = correlationIMRelationship.getKnots()[correlationIMRelationship
					.getKnots().length - 1];
		}

		return correlationIMRelationship.value(im);
	}
}
