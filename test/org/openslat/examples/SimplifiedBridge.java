package org.openslat.examples;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.openslat.calculators.output.CollapseOutput;
import org.openslat.calculators.output.LossEDPOutput;
import org.openslat.calculators.output.LossIMOutput;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;
import org.openslat.model.collapse.LossCollapse;
import org.openslat.model.structure.Component;
import org.openslat.options.CalculationOptions;
import org.openslat.options.EpistemicUncertOptions;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimplifiedBridge {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		String inputJsonString = "{\"structure\":{\"performanceGroups\":[{\"components\":[{\"ff\":{\"identifier\":1,\"name\":\"simplified_bridge_FF_name\",\"material\":null,\"damageStates\":[{\"meanEDPOnset\":0.0062,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.03,\"lowerLimitMeanLoss\":0.03,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.023,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.08,\"lowerLimitMeanLoss\":0.08,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.044,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.25,\"lowerLimitMeanLoss\":0.25,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.0564,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":1,\"lowerLimitMeanLoss\":1,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0}]}}],\"edp\":{\"name\":\"simplified_bridge_EDP_name\",\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]},\"table\":null}}],\"maxValue\":0.15},\"name\":\"simplified_bridge_PG_name\"}],\"lossCollapse\":null,\"pc\":null,\"im\":{\"name\":\"simplified_bridge_IM_name\",\"iMR\":[{\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62.2]},\"imRName\":\"imr1\"}],\"maxValue\":2.5}},\"calculationOptions\":null}";
		SlatInputStore sis = SlatParser.parseInputJsonString(inputJsonString);		

		sis.setupSis();
		
		LossCollapse lossCollapse = new LossCollapse();
		
		ObjectMapper objm = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objm.writeValue(stringWriter, sis);
		System.out
				.println("printing the JSON string for debugging, after generating Objects and converting to JSON  ");
		System.out.println(stringWriter.toString());
		
		//String edprOutput = EDPROutput.edpRateOutput(sis);
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
