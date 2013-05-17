package org.openslat.examples;

import java.io.IOException;
import org.openslat.calculators.output.EDPROutput;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SimplifiedBridge {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		String inputJsonString = "{\"structure\":{\"performanceGroups\":[{\"components\":[{\"ff\":{\"identifier\":1,\"name\":null,\"material\":null,\"damageStates\":[{\"meanEDPOnset\":0.62,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.03,\"lowerLimitMeanLoss\":0.03,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":2.3,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.08,\"lowerLimitMeanLoss\":0.08,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":4.4,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.25,\"lowerLimitMeanLoss\":0.25,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":5.64,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":1,\"lowerLimitMeanLoss\":1,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0}]},\"edp\":{\"name\":\"edpname1\",\"edpIM\":[{\"name\":\"edpimname1\",\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]},\"table\":null}}],\"maxValue\":5.7}}],\"name\":\"performanceGroupName\"}],\"lossCollapse\":null,\"pc\":null,\"im\":{\"name\":\"happiness\",\"iMR\":[{\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62.2]},\"imRName\":\"imr1\"}],\"maxValue\":1}},\"calculationOptions\":null}";
		SlatInputStore sis = SlatParser.parseInputJsonString(inputJsonString);		
		
		String edprOutput = EDPROutput.edpRateOutput(sis);
		System.out.println(edprOutput);
		
		
		
	}
	



}
