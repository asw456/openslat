package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openslat.calculators.component.LossEDPNC;
import org.openslat.model.structure.Component;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class LossEDPOutput {
	
	public static String lossEDPOutput(Component c, int numSteps)
			throws JsonGenerationException, JsonMappingException, IOException {

		final LossEDPNC lossEDPNC = new LossEDPNC();

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		final double edpMaxValue = c.getEdp().getMaxValue();
		
		final double stepsize = edpMaxValue / numSteps;
		
		double[] edpArray = new double[numSteps];
		double[] meanLossArray = new double[numSteps];
		double[] sigmaLossArray = new double[numSteps];

		for (int i = 0; i < numSteps; i++){
			//precache results?
			
		}
		
		
		for (int i = 0; i < numSteps; i++) {
			System.out.println("iteration:    ----------      " + i);
			edpArray[i] = 0.00 + i * stepsize;
			meanLossArray[i] = lossEDPNC.meanLoss(c,edpArray[i]);
			sigmaLossArray[i] = lossEDPNC.sigmaLoss(c,edpArray[i]);
			System.out.println(edpArray[i] + ",   " + meanLossArray[i] + ",    " + sigmaLossArray[i]);
		}

		g.writeStartObject();
		g.writeObjectFieldStart("structureLoss");
		// g.writeStringField("x", Arrays.toString(outputArrayX));
		// g.writeStringField("y", Arrays.toString(outputArrayY));
		g.writeArrayFieldStart("edp");
		for (int i = 0; i < edpArray.length; i++) {
			g.writeNumber(edpArray[i]);
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
