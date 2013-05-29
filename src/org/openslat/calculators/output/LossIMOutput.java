package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.openslat.calculators.multiplecomponents.LossIM;
import org.openslat.control.SlatInputStore;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class LossIMOutput {
	
	public static String lossOutput(SlatInputStore sis, int numSteps)
			throws JsonGenerationException, JsonMappingException, IOException {

		final LossIM lossIM = new LossIM();
		lossIM.setSis(sis);

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		final double imMaxValue = sis.getStructure().getIm().getMaxValue();
		
		final double stepsize = imMaxValue / numSteps;
		
		double[] imArray = new double[numSteps];
		double[] meanLossArray = new double[numSteps];
		double[] sigmaLossArray = new double[numSteps];

		for (int i = 0; i < numSteps; i++){
			
			
		}
		
		
		for (int i = 0; i < numSteps; i++) {
			System.out.println("iteration:    ----------      " + i);
			imArray[i] = 0 + i * stepsize;
			meanLossArray[i] = lossIM.meanLossNC(imArray[i]);
			sigmaLossArray[i] = lossIM.sigmaLoss(imArray[i]);
			System.out.println(imArray[i] + ",   " + meanLossArray[i] + ",    " + sigmaLossArray[i]);
		}

		g.writeStartObject();
		g.writeObjectFieldStart("structureLoss");
		// g.writeStringField("x", Arrays.toString(outputArrayX));
		// g.writeStringField("y", Arrays.toString(outputArrayY));
		g.writeArrayFieldStart("im");
		for (int i = 0; i < imArray.length; i++) {
			g.writeNumber(imArray[i]);
		}
		g.writeEndArray();
		g.writeArrayFieldStart("meanLoss");
		for (int i = 0; i < meanLossArray.length; i++) {
			g.writeNumber(meanLossArray[i]);
		}
		g.writeEndArray();
		g.writeArrayFieldStart("sigmaLoss");
		for (int i = 0; i < meanLossArray.length; i++) {
			g.writeNumber(sigmaLossArray[i]);
		}
		g.writeEndArray();
		
		g.writeEndObject();

		g.close();
		return ostream.toString();
	}
}
