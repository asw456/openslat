package org.openslat.control;

import java.io.IOException;
import java.io.StringWriter;

import org.openslat.calculators.output.CollapseOutput;
import org.openslat.calculators.output.DemolitionOutput;
import org.openslat.calculators.output.EDPROutput;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;
import com.fasterxml.jackson.core.JsonGenerationException;
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
			
			String results = calculate(sis);
			
			return results;
			
			
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}		
		
	}
	
	
	private static String calculate(SlatInputStore sis) throws IOException{
		
		String edprOutput;
		if (sis.getCalculationOptions().isEdpRateCalc()){
			edprOutput = EDPROutput.edpRateOutput(sis);

		} else {
			edprOutput = "";
		}
		
		if (sis.getCalculationOptions().isCollapseRateCalc()){
			String collapseOutput = ""; //CollapseOutput.collapseRateOutput(sis);
		} else {
			String collapseOutput = "";
		}
		
		if (sis.getCalculationOptions().isDemolitionRateCalc()){
			String demolitionOutput = ""; //DemolitionOutput.demolitionRateOutput(sis);
		} else {
			String demolitionOutput = "";
		}
		
		
		return edprOutput;
	}
	
	
	
	
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":1,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]}}}],\"minEDPValue\":0,\"maxEDPValue\":0.15}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.01}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"example_im1.csv\",\"epistemicWeight\":1,\"model\":{\"type\":\"IMRDiscreteModel\",\"table\":[[0.010001702004705473,0.24120393568725848],[0.01649001883836203,0.13630549473470716],[0.027187444813053307,0.07097272649072182],[0.044824518559266856,0.03341024107112686],[0.07390313719755591,0.013863709144431753],[0.12184567426908031,0.0048986123016447666],[0.20088955491023197,0.0014041126716546276],[0.3312108822419808,0.0003043618655169967],[0.5460744266397092,0.00004489705178994815],[0.9003245225862652,0.000003816566457067227],[1.4843841909209134,1.415641845840542e-7]]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":false,\"collapseRateCalc\":false,\"edpRateCalc\":true}}";
		//String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":1,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]}}}],\"minEDPValue\":0,\"maxEDPValue\":0.15}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.01}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"relationship-1\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,27,65]}},{\"name\":\"relationship-2\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29,68]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":false,\"collapseRateCalc\":false,\"edpRateCalc\":true}}";
		
		String outputString = run(inputJsonString);
		System.out.println(outputString);
		
		
		//String lossIMOutput = LossIMOutput.lossOutput(sis,50);
		//String collapseOutput = CollapseOutput.collapseOutput(sis,50);
		//String lossEDPOutput = LossEDPOutput.lossEDPOutput(sis.getStructure().getComponents().get(0), 50);

		//System.err.println(edprOutput);
		//System.err.println(lossIMOutput);
		//System.err.println(collapseOutput);
		//System.err.println(lossEDPOutput);
		
		//FileUtils.writeStringToFile(new File("C:\\temp\\lossIM.json"), lossIMOutput);
		//FileUtils.writeStringToFile(new File("C:\\temp\\collapseRate.json"), collapseOutput);
		//FileUtils.writeStringToFile(new File("C:\\temp\\lossEDP.json"), lossEDPOutput);
		
		
	}
	
}
