package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openslat.control.SlatInputStore;
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

		String inputJsonString = "{\"structure\":{\"performanceGroups\":[{\"components\":[{\"ff\":{\"identifier\":1,\"name\":\"simplified_bridge_FF_name\",\"material\":null,\"damageStates\":[{\"meanEDPOnset\":0.0062,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.03,\"lowerLimitMeanLoss\":0.03,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.023,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.08,\"lowerLimitMeanLoss\":0.08,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.044,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.25,\"lowerLimitMeanLoss\":0.25,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.0564,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":1,\"lowerLimitMeanLoss\":1,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0}]}}],\"edp\":{\"name\":\"simplified_bridge_EDP_name\",\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]},\"table\":null}}],\"maxValue\":5.7},\"name\":\"simplified_bridge_PG_name\"}],\"lossCollapse\":null,\"pc\":null,\"im\":{\"name\":\"simplified_bridge_IM_name\",\"iMR\":[{\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62.2]},\"imRName\":\"imr1\"}],\"maxValue\":2.5}},\"calculationOptions\":null}";
		// String inputJsonString = generateJsonString();

		@SuppressWarnings("unused")
		SlatInputStore sis = SlatParser
				.parseInputJsonString(inputJsonString);
		
		

	}

	@SuppressWarnings("unused")
	private static String generateJsonString() throws IOException,
			JsonGenerationException, JsonMappingException {

		// Construct an IM ====================
		PowerModel pm = new PowerModel();
		double[] params = { 2.2, 2.3 };
		pm.setPowerModelParams(params);

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
		edp.setMaxValue(5.7);
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
		structure.setCollapse(null);
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
		System.out
				.println("printing the JSON string for debugging, after generating Objects and converting to JSON  ");
		System.out.println(stringWriter.toString());

		return stringWriter.toString();
	}

}
