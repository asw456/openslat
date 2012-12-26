package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;
import org.openslat.model.structure.Structure;
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

		// Construct an IM
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

		// Construct and add example Structure to Store
		Structure structure = new Structure();
		structure.setIm(im);
		structure.setPc(null);
		structure.setLossCollapse(null);

		PerformanceGroup pg = new PerformanceGroup();
		pg.setName("performanceGroupName");
		pg.setComponents(new ArrayList<Component>());
		Component component = new Component();
		component.setFf(null);
		component.setEdp(null);
		pg.addComponent(component);
		
		structure.addPerformanceGroup(pg);
		// =====================================

		// Construct and add example CalculationOptions to Store
		// null ;)
		// =====================================

		SlatInputStore g = new SlatInputStore(structure, null);
		ObjectMapper objm = new ObjectMapper();
		StringWriter sw = new StringWriter();
		objm.writeValue(sw, g);
		System.out.println(sw.toString());

		// String myJsonString =
		// "{\"im\":{\"name\":\"happiness\",\"iMR\":[{\"imRName\":\"imr1\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}},{\"imRName\":\"imr2\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}}]}}";
		// SlatParser parser = new SlatParser();
		// parser.parseInputJsonString(myJsonString);
	}

}
