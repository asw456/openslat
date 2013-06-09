package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.openslat.calculators.rate.EDPR;
import org.openslat.control.SlatInputStore;
import org.openslat.model.edp.EDP;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class EDPROutput {

	public static String edpRateOutput(SlatInputStore sis)
			throws JsonGenerationException, JsonMappingException, IOException {

		final EDPR edprCalc = new EDPR();

		ArrayList<EDP> edpHs = sis.getStructure().getEdps();

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		g.writeStartObject();
		g.writeArrayFieldStart("edpRates");
		
		for (EDP edp : edpHs) {
			final double edpMaxValue = edp.getMaxEDPValue();
			final double stepsize = edpMaxValue / 20.0;
			double[] outputArrayX = new double[20];
			double[] outputArrayY = new double[20];
			for (int i = 0; i < 20; i++) {
				if (sis.isVerbose()) System.err.println("edpr step " + i);
				outputArrayX[i] = 0.001 + i * stepsize;
				outputArrayY[i] = edprCalc.edpRate(outputArrayX[i], edp, sis
						.getStructure().getIm(), sis.getStructure().getPc());
				if (sis.isVerbose()) System.err.println(outputArrayX[i] + "," + outputArrayY[i]);

			}


			g.writeStartObject();
			g.writeStringField("name", edp.getName());
			//g.writeStringField("y", Arrays.toString(outputArrayY));
			g.writeArrayFieldStart("x");
			for (int i=0;i<outputArrayX.length;i++){ g.writeNumber(outputArrayX[i]);};
			g.writeEndArray();
			g.writeArrayFieldStart("y");
			for (int i=0;i<outputArrayY.length;i++){ g.writeNumber(outputArrayY[i]);};			
			g.writeEndArray();
			g.writeEndObject();
		
		}

		g.writeEndArray();
		g.close();
		
		return ostream.toString();
	}

}
