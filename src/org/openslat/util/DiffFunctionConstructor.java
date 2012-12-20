package org.openslat.util;

import org.openslat.interfaces.DifferentiableFunction;
import org.openslat.models.univariate.BradleyModel;
import org.openslat.models.univariate.PowerModel;

public class DiffFunctionConstructor {

	public static DifferentiableFunction construct(String modelName,
			double[] parameters) {

		if (modelName.toLowerCase().contains("bradley")) {
			BradleyModel bm = new BradleyModel();
			bm.setBradleyModelParams(parameters);
			return bm;
		}
		if (modelName.toLowerCase().contains("power")) {
			PowerModel pm = new PowerModel();
			pm.setPowerModelParams(parameters);
			return pm;
		}
		// if etc
		
		return null;
	}
	
//	public static 
//	
//	if (meanModelName.toLowerCase().contains("power")) {
//		model.setimr(new IMR(new BradleyModel(meanParameters)));
//	}
//	if (meanModelName.toLowerCase().contains("parabolic")) {
//		model.setimr(new IMR(new PowerModel(meanParameters)));
//	} else {
//		//error handling
//	}
//		
//	if (stddModelName.toLowerCase().contains("power")) {
//		model.setimr(new IMR(new BradleyModel(stddParameters)));
//	}
//	if (stddModelName.toLowerCase().contains("parabolic")) {
//		model.setimr(new IMR(new PowerModel(stddParameters)));
//	} else {
//		//error handling
//	}
//	
	
	
}
