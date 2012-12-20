package org.openslat.control;

import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.models.univariate.PowerModel;
import org.openslat.options.CalculationOptions;

public class IMController {
	
	private CalculationOptions calculationOptions;
	
	public void addIM(String name) {
		IM im = new IM();
		im.setCalculationOptions(calculationOptions);
		im.setName(name);
		calculationOptions.getStructure().setIm(im);
	}

	public void addIMRWithPowerModel(String jsonPowerModel) {
		//Gson gson = new Gson(); // json is {\"a\":1.0,\"b\":1.0}
		//PowerModel powerModel = gson.fromJson(jsonPowerModel, PowerModel.class);
		//IMR newIMR = new IMR();
		//newIMR.setModel(powerModel);
		//calculationOptions.getStructure().getIm().getiMR().add(newIMR);
	}
	
	
}