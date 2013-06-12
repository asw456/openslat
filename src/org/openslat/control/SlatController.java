package org.openslat.control;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.openslat.calculators.output.CollapseOutput;
import org.openslat.calculators.output.DemolitionOutput;
import org.openslat.calculators.output.EDPROutput;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlatController {

	public static String run(String inputJsonString) {
		
		SlatInputStore sis;
		try {
			
			sis = SlatParser.parseInputJsonString(inputJsonString);
			sis.setupSis();
			
			//verbose
			sis.setVerbose(true);
			
			//testing
			StringWriter stringWriter = new StringWriter();
			(new ObjectMapper()).writeValue(stringWriter, sis);
			if (sis.isVerbose()) System.err.println("JSON string after parsing and re-stringifying");
			if (sis.isVerbose()) System.err.println(stringWriter.toString());
			//end testing
			
			return "parsed correctly";
			
			//String results = calculate(sis);
			
			//return results;
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}		
		
	}
	
	
	private static String calculate(SlatInputStore sis) throws IOException{
		
		String edprOutput;
		String collapseOutput;
		String demolitionOutput;
		
		if (sis.getCalculationOptions().isEdpRateCalc()){
			edprOutput = EDPROutput.edpRateOutput(sis);

		} else {
			edprOutput = "{}";
		}
		
		if (sis.getCalculationOptions().isCollapseRateCalc()){
			collapseOutput = CollapseOutput.collapseRateOutput(sis);
		} else {
			collapseOutput = "{}";
		}
		
		if (sis.getCalculationOptions().isDemolitionRateCalc()){
			demolitionOutput = DemolitionOutput.demolitionRateOutput(sis);
		} else {
			demolitionOutput = "{}";
		}
		
		
		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		g.writeStartObject();
		g.writeFieldName("collapseOutput");
		g.writeRawValue(collapseOutput);
		g.writeFieldName("demolitionOutput");
		g.writeRawValue(demolitionOutput);
		g.writeFieldName("edprOutput");
		g.writeRawValue(edprOutput);
		g.writeEndObject();
		g.close();
		
		
		//System.out.println(edprOutput);
		return ostream.toString();
		
		
//		return edprOutput;
//		return collapseOutput;
		
	}
	
	
	
	
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		//discrete IMR
		//String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":1,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]}}}],\"minEDPValue\":0,\"maxEDPValue\":0.15}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.1}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"example_im1.csv\",\"epistemicWeight\":1,\"model\":{\"type\":\"IMRDiscreteModel\",\"table\":[[0.010001702004705473,0.24120393568725848],[0.01649001883836203,0.13630549473470716],[0.027187444813053307,0.07097272649072182],[0.044824518559266856,0.03341024107112686],[0.07390313719755591,0.013863709144431753],[0.12184567426908031,0.0048986123016447666],[0.20088955491023197,0.0014041126716546276],[0.3312108822419808,0.0003043618655169967],[0.5460744266397092,0.00004489705178994815],[0.9003245225862652,0.000003816566457067227],[1.4843841909209134,1.415641845840542e-7]]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":true,\"collapseRateCalc\":true,\"edpRateCalc\":false}}";
	
		//discrete EDP
		String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"asdf\",\"identifier\":2,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"EDPIMDiscreteModel\",\"table\":[[0.1,0.09621,0.121,0.295,0.185,0.08081,0.121,0.156,0.116,0.125,0.137,0.139,0.135,0.08495,0.05544,0.09933,0.09933,0.159,0.197,0.131,0.07607,0.09361,0.136,0.0837,0.301,0.22,0.22,0.108,0.135,0.158,0.282,0.178,0.187,0.08124,0.279,0.142,0.277,0.259,0.108,0.07594,0.123],[0.2,0.193,0.242,0.589,0.371,0.162,0.243,0.313,0.231,0.251,0.274,0.278,0.269,0.17,0.111,0.199,0.199,0.317,0.393,0.261,0.152,0.187,0.271,0.167,0.603,0.439,0.439,0.215,0.271,0.315,0.564,0.357,0.374,0.163,0.559,0.284,0.553,0.519,0.216,0.152,0.246],[0.3,0.289,0.363,0.884,0.556,0.242,0.364,0.469,0.347,0.376,0.411,0.417,0.404,0.255,0.166,0.298,0.298,0.476,0.59,0.392,0.228,0.281,0.407,0.251,0.904,0.659,0.659,0.323,0.406,0.473,0.846,0.535,0.561,0.244,0.838,0.426,0.83,0.778,0.324,0.228,0.368],[0.4,0,0.484,1.18,0.742,0.323,0.486,0.625,0.462,0.501,0,0,0.538,0.34,0.222,0.397,0.397,0.634,0.786,0.523,0.304,0.374,0.542,0,1.21,0.879,0.879,0.431,0.542,0.63,1.13,0.714,0.749,0.325,1.12,0.569,1.1,1.04,0.432,0.304,0.491],[0.5,0,0.605,1.47,0.927,0.404,0.607,0.782,0.578,0.626,0,0,0.672,0.425,0.277,0.497,0.497,0.793,0.983,0.653,0.38,0.468,0.678,0,1.51,1.1,1.1,0.539,0.677,0.788,1.41,0.892,0.936,0.406,1.4,0.711,1.38,1.3,0.54,0.38,0.614],[0.6,0,0.726,1.77,1.11,0.485,0.729,0.939,0.693,0.751,0,0,0.807,0.51,0.333,0.596,0.596,0,0,0,0.456,0.562,0.814,0,1.81,1.32,1.32,0.646,0.813,0,1.69,1.07,1.12,0.488,1.68,0.853,1.66,0,0.649,0.456,0],[0.7,0,0.847,2.06,1.3,0,0.85,0,0.809,0.877,0,0,0.941,0.595,0.388,0.695,0,0,0,0,0,0,0.949,0,2.11,1.54,1.54,0.754,0.948,0,1.98,1.25,1.31,0.569,0,0.995,0,0,0.757,0.532,0],[0.8,0,0.968,2.36,1.48,0,0.972,0,0.924,1,0,0,1.07,0,0.444,0.794,0,0,0,0,0,0,1.08,0,0,1.76,1.76,0.862,1.08,0,2.26,1.43,0,0.65,0,1.14,0,0,0.865,0.608,0],[0.9,0,1.09,0,1.67,0,1.09,0,1.04,0,0,0,0,0,0.499,0,0,0,0,0,0,0,1.22,0,0,1.98,1.98,0.97,1.22,0,2.54,1.61,0,0.731,0,1.28,0,0,0.973,0.683,0],[1,0,1.21,0,0,0,1.22,0,1.16,0,0,0,0,0,0,0,0,0,0,0,0,0,1.36,0,0,2.2,2.2,1.08,1.35,0,2.82,1.78,0,0.812,0,1.42,0,0,1.08,0.759,0],[1.1,0,0,0,0,0,0,0,1.27,0,0,0,0,0,0,0,0,0,0,0,0,0,1.49,0,0,0,0,1.19,0,0,0,1.96,0,0.894,0,1.56,0,0,1.19,0.835,0],[1.2,0,0,0,0,0,0,0,1.39,0,0,0,0,0,0,0,0,0,0,0,0,0,1.63,0,0,0,0,1.29,0,0,0,0,0,0.975,0,1.71,0,0,0,0.911,0],[1.3,0,0,0,0,0,0,0,1.5,0,0,0,0,0,0,0,0,0,0,0,0,0,1.76,0,0,0,0,1.4,0,0,0,0,0,1.06,0,1.85,0,0,0,0.987,0],[1.4,0,0,0,0,0,0,0,1.62,0,0,0,0,0,0,0,0,0,0,0,0,0,1.9,0,0,0,0,1.51,0,0,0,0,0,1.14,0,1.99,0,0,0,1.06,0],[1.5,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2.04,0,0,0,0,1.62,0,0,0,0,0,1.22,0,0,0,0,0,1.14,0]]}}],\"minEDPValue\":0,\"maxEDPValue\":1}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.1}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"Hyperbolic Model\",\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,27,65]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":false,\"collapseRateCalc\":false,\"edpRateCalc\":false}}";
		
		//String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":1,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]}}}],\"minEDPValue\":0,\"maxEDPValue\":0.15}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.1}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"relationship-1\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62]}},{\"name\":\"relationship-2\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29,68]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":true,\"collapseRateCalc\":true,\"edpRateCalc\":false}}";
		
		//String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":2,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[\"0.1\",\"1\"]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[\"0.4\",\"0\"]}}}],\"minEDPValue\":0,\"maxEDPValue\":1}],\"collapse\":{\"pcim\":[{\"name\":\"Collapse Relationship\",\"epistemicWeight\":1,\"mean\":0.6,\"sigma\":0.4}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"Demolition Relationship\",\"epistemicWeight\":1,\"mean\":0.3,\"sigma\":0.4}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"Power Model\",\"epistemicWeight\":1,\"model\":{\"type\":\"PowerModel\",\"parameters\":[\"0.0002\",\"3\"]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":true,\"collapseRateCalc\":true,\"edpRateCalc\":false}}";
		
		String outputString = run(inputJsonString);
		System.out.println(outputString);

		FileUtils.writeStringToFile(new File("C:\\temp\\output.json"), outputString);
		
	}
	
}
