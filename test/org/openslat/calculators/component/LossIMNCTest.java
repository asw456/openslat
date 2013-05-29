package org.openslat.calculators.component;

import org.junit.Before;
import org.junit.Test;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;
import org.openslat.model.structure.Component;
import org.openslat.options.CalculationOptions;
import org.openslat.options.EpistemicUncertOptions;

public class LossIMNCTest {

	private SlatInputStore sis;
	
	@Before
	public void setUp() throws Exception {
		String inputJsonString = "{\"structure\":{\"performanceGroups\":[{\"components\":[{\"ff\":{\"identifier\":1,\"name\":\"simplified_bridge_FF_name\",\"material\":null,\"damageStates\":[{\"meanEDPOnset\":0.0062,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.03,\"lowerLimitMeanLoss\":0.03,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.023,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.08,\"lowerLimitMeanLoss\":0.08,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.044,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":0.25,\"lowerLimitMeanLoss\":0.25,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0},{\"meanEDPOnset\":0.0564,\"epistemicStdDev_Mean_LNedp\":0,\"sigmaEDPOnset\":0.4,\"epistemicStdDev_Var_LNedp\":0,\"upperLimitMeanLoss\":1,\"lowerLimitMeanLoss\":1,\"numberComponentsUpperLimitLoss\":1,\"numberComponentsLowerLimitLoss\":1,\"epistemicStdDev_Mean_LNloss\":0,\"sigmaLoss\":0.4,\"epistemicStdDev_Var_LNloss\":0,\"upperLimitMeanTime\":1,\"lowerLimitMeanTime\":1,\"numberComponentsUpperLimitTime\":1,\"numberComponentsLowerLimitTime\":1,\"epistemicStdDev_Mean_LNtime\":0,\"sigmaTime\":0,\"epistemicStdDev_Var_LNtime\":0}]},\"edp\":{\"name\":\"simplified_bridge_EDP_name\",\"edpIM\":[{\"name\":null,\"epistemicWeight\":1,\"distributionFunction\":{\"type\":\"LogNormalModel\",\"meanModel\":{\"type\":\"PowerModel\",\"parameters\":[0.1,1.5]},\"stddModel\":{\"type\":\"PowerModel\",\"parameters\":[0.5,0]},\"table\":null}}],\"maxValue\":5.7}}],\"name\":\"simplified_bridge_PG_name\"}],\"lossCollapse\":null,\"pc\":null,\"im\":{\"name\":\"simplified_bridge_IM_name\",\"iMR\":[{\"epistemicWeight\":1,\"model\":{\"type\":\"HyperbolicModel\",\"parameters\":[1221,29.8,62.2]},\"imRName\":\"imr1\"}],\"maxValue\":2.5}},\"calculationOptions\":null}";
		sis = SlatParser.parseInputJsonString(inputJsonString);
		sis.setupSis();

		sis.setCalculationOptions(new CalculationOptions());
		sis.getCalculationOptions().setCollapse(false);
		sis.getCalculationOptions().setEpistemicUncertOptions(
				new EpistemicUncertOptions());
		sis.getCalculationOptions().getEpistemicUncertOptions()
				.setEpistemicUncert(false);
		
		for (Component c : sis.getStructure().getComponents()){
			c.getFf().setMeans(1);
		}
	
	}

	@Test
	public void test() {

		Component c = sis.getStructure().getPerformanceGroups().get(0).getComponents().get(0);
		
		LossIMNC lossIMNC = new LossIMNC();
		
		double meanLoss = lossIMNC.meanLoss(c, 1);
		System.out.println(meanLoss);
		
		
	}

}
