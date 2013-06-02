package org.openslat.control;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.jetlang.core.Callback;
import org.openslat.calculators.output.CollapseOutput;
import org.openslat.calculators.output.EDPROutput;
import org.openslat.calculators.output.LossEDPOutput;
import org.openslat.calculators.output.LossIMOutput;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;
import org.openslat.model.collapse.LossCollapse;
import org.openslat.model.structure.Component;
import org.openslat.options.CalculationOptions;
import org.openslat.options.EpistemicUncertOptions;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlatController {

	private String inputJsonString;
	
	public SlatController(String inputString){
		this.inputJsonString = inputString;
	}
	
	public String run() {
		
		SlatInputStore sis;
		try {
			sis = SlatParser.parseInputJsonString(inputJsonString);
			
			sis.setupSis();
			
			StringWriter stringWriter = new StringWriter();
			(new ObjectMapper()).writeValue(stringWriter, sis);
			System.out.println("JSON string after parsing and re-stringifying");
			System.out.println(stringWriter.toString());
			
			
			
			return null;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		String inputJsonString = "{\"structure\":{\"performanceGroups\":[{\"components\":[{\"ff\":{\"identifier\":1,\"name\":\"simplified_bridge_FF_name\",\"material\":null,\"damageStates\":[{\"meanEDPOnset\":0.0062,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.03,\"lowerLimitMeanLoss\":0.03,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.023,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.08,\"lowerLimitMeanLoss\":0.08,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.044,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.25,\"lowerLimitMeanLoss\":0.25,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.0564,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":1,\"lowerLimitMeanLoss\":1,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0}]}}],\"edp\":{\"name\":\"simplified_bridge_EDP_name\",\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]},\"table\":null}}],\"minEDPValue\":0,\"maxEDPValue\":0.15},\"name\":\"simplified_bridge_PG_name\"}],\"collapse\":{\"pcim\":[{\"name\":\"pcim-collapse-relationship-1\",\"epistemicWeight\":0.5,\"mean\":2,\"sigma\":0.01}],\"lossCollapse\":{\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalLoss\":10000,\"additionalTime\":-99,\"meanLoss\":1500000000,\"epistemicStdDev_Mean_LNloss\":200000,\"sigmaLoss\":10000000,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"demolition\":{\"pcim\":[{\"name\":\"pcim-demolition-relationship-1\",\"epistemicWeight\":0.5,\"mean\":2,\"sigma\":0.01}],\"lossCollapse\":{\"randMeanLoss\":-99,\"randSigmaLoss\":-99,\"additionalLoss\":10000,\"additionalTime\":-99,\"meanLoss\":1500000000,\"epistemicStdDev_Mean_LNloss\":200000,\"sigmaLoss\":10000000,\"epistemicStdDev_Var_LNloss\":-99,\"meanTime\":-99,\"epistemicStdDev_Mean_LNtime\":-99,\"sigmaTime\":-99,\"epistemicStdDev_Var_LNTime\":-99}},\"im\":{\"name\":\"simplified_bridge_IM_name\",\"iMR\":[{\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62.2]},\"imRName\":\"imr1\"}],\"minIMValue\":0,\"maxIMValue\":2.5}},\"calculationOptions\":{\"considerCollapse\":true,\"componentBasedCollapseCost\":false,\"considerDemolition\":false,\"componentBasedDemolitionCost\":false,\"considerDownTime\":false,\"imCalcSteps\":50,\"edpCalcSteps\":50,\"demolitionRateCalc\":false,\"collapseRateCalc\":false,\"edpRateCalc\":true}}		";
		SlatInputStore sis = SlatParser.parseInputJsonString(inputJsonString);		

		sis.setupSis();
		
		StringWriter stringWriter = new StringWriter();
		(new ObjectMapper()).writeValue(stringWriter, sis);
		System.out.println("JSON string after parsing and re-stringifying");
		System.out.println(stringWriter.toString());
		
		String edprOutput = EDPROutput.edpRateOutput(sis);
		System.out.println(edprOutput);
		
		
		
		// this is what the output needs to be like
		/*
		{
			   "demolitionRate":0.002,
			   "collapseRate":0.0015,
			   "edpRates":[
			      {
			         "name":"<edpname>",
			         "x":[]
			         "y":[]
			         ]
			      }
			   ]
			}
		*/
		
		
		
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
