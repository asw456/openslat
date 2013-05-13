package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openslat.model.edp.EDP;
import org.openslat.model.edp.EDPIM;
import org.openslat.model.fragilityfunctions.DamageState;
import org.openslat.model.fragilityfunctions.FragilityFunction;
import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.model.structure.Structure;
import org.openslat.models.distribution.LogNormalModel;
import org.openslat.models.univariate.PowerModel;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlatParserTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		// String inputJsonString =
		// "{\"im\":{\"name\":\"happiness\",\"iMR\":[{\"imRName\":\"imr1\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}},{\"imRName\":\"imr2\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}}]}}";
		String inputJsonString = generateJsonString();

		SlatInputStore myInputStore = SlatParser.parseInputJsonString(inputJsonString);		

	}

	private static String generateJsonString() throws IOException,
			JsonGenerationException, JsonMappingException {

		// Construct an IM ====================
		PowerModel pm = new PowerModel();
		double[] params = { 2.2, 2.3 };
		pm.constructPowerModel(params);

		IMR imr1 = new IMR();
		imr1.setImRName("imr1");
		imr1.setModel(pm);
		IMR imr2 = new IMR();
		imr2.setImRName("imr2");
		imr2.setModel(pm);

		ArrayList<IMR> iMR = new ArrayList<IMR>();
		iMR.add(imr1);
		iMR.add(imr2);

		IM im = new IM();
		im.setName("happiness");
		im.setiMR(iMR);
		// =====================================

		// Construct EDP =======================
		LogNormalModel lgnmdl = new LogNormalModel();
		lgnmdl.setMeanModel(pm);
		lgnmdl.setStddModel(pm);

		EDPIM edpIm = new EDPIM();
		edpIm.setDistributionFunction(lgnmdl);

		EDP edp = new EDP();
		edp.setEdpIM(new ArrayList<EDPIM>());
		edp.addEdpIM(edpIm);
		// =====================================

		// Construct FF ========================
		DamageState ds = new DamageState();
		FragilityFunction ff = new FragilityFunction();
		ff.setDamageStates(new ArrayList<DamageState>());
		ff.getDamageStates().add(ds);
		// =====================================

		// Construct component
		Component component = new Component();
		component.setFf(ff);
		component.setEdp(edp);
		// =====================================

		// Construct Performance group
		PerformanceGroup pg = new PerformanceGroup();
		pg.setName("performanceGroupName");
		pg.setComponents(new ArrayList<Component>());
		pg.addComponent(component);
		// =====================================

		// Construct and add example Structure to Store
		Structure structure = new Structure();
		structure.setIm(im);
		structure.setPc(null);
		structure.setLossCollapse(null);
		structure.addPerformanceGroup(pg);
		// =====================================

		// Construct and add example CalculationOptions to Store
		// null ;)
		// =====================================

		SlatInputStore slatInputStore = new SlatInputStore(structure, null);
		ObjectMapper objm = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objm.writeValue(stringWriter, slatInputStore);
		System.out.println("printing the JSON string for debugging, after generating Objects and converting to JSON  ");
		System.out.println(stringWriter.toString());
		
		return stringWriter.toString();
	}

}
