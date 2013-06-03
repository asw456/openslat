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
			
			//testing
			StringWriter stringWriter = new StringWriter();
			(new ObjectMapper()).writeValue(stringWriter, sis);
			System.out.println("JSON string after parsing and re-stringifying");
			System.out.println(stringWriter.toString());
			//end testing
			
			String results = calculate(sis);
			System.out.println(results);
			
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
			System.out.println("edprOutput:   " + edprOutput);

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

		//String inputJsonString = "";
		String inputJsonString = "{\"structure\":{\"performanceGroups\":[],\"edps\":[{\"name\":\"2nd storey drift (cm)\",\"identifier\":1,\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]}}}],\"minEDPValue\":0,\"maxEDPValue\":0.15}],\"collapse\":{\"pcim\":[{\"name\":\"relationship-2\",\"epistemicWeight\":1,\"mean\":2,\"sigma\":0.01}],\"lossCollapse\":{\"meanLoss\":1500000000,\"sigmaLoss\":10000000,\"epistemicStdDev_Mean_LNloss\":200000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"relationship-1\",\"epistemicWeight\":1,\"mean\":1,\"sigma\":0.1}],\"lossCollapse\":{\"additionalLoss\":1000000,\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalTime\":-99,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}}},\"im\":{\"name\":\"Peak Ground Acceleration (m/s^2)\",\"iMR\":[{\"name\":\"relationship-1\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,27,65]}},{\"name\":\"relationship-2\",\"epistemicWeight\":0.5,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29,68]}}],\"minIMValue\":0,\"maxIMValue\":2.5},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":false,\"collapseRateCalc\":false,\"edpRateCalc\":true}}";
		
		String outputString = run(inputJsonString);
		System.out.println("output:  " + outputString);
		
		
		//String lossIMOutput = LossIMOutput.lossOutput(sis,50);
		//String collapseOutput = CollapseOutput.collapseOutput(sis,50);
		//String lossEDPOutput = LossEDPOutput.lossEDPOutput(sis.getStructure().getComponents().get(0), 50);

		//System.out.println(edprOutput);
		//System.out.println(lossIMOutput);
		//System.out.println(collapseOutput);
		//System.out.println(lossEDPOutput);
		
		//FileUtils.writeStringToFile(new File("C:\\temp\\lossIM.json"), lossIMOutput);
		//FileUtils.writeStringToFile(new File("C:\\temp\\collapseRate.json"), collapseOutput);
		//FileUtils.writeStringToFile(new File("C:\\temp\\lossEDP.json"), lossEDPOutput);
		
		
	}
	
}
