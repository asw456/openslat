package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;

import org.openslat.calculators.rate.EDPR;
import org.openslat.control.SlatInputStore;
import org.openslat.model.edp.EDP;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class LossIMNCOutput {

	public static String edpRateOutput(SlatInputStore sis)
			throws JsonGenerationException, JsonMappingException, IOException {

		/* not finished; copy of EDPOutput class or something
		final EDPR edprCalc = new EDPR();

		HashSet<EDP> edpHs = sis.getStructure().getEDPHashSet();

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		for (EDP edp : edpHs) {
			final double edpMaxValue = edp.getMaxEDPValue();
			final double stepsize = edpMaxValue / 1000.0;
			double[] outputArrayX = new double[1000];
			double[] outputArrayY = new double[1000];
			for (int i = 0; i < 1000; i++) {
				outputArrayX[i] = 0.001 + i * stepsize;
				outputArrayY[i] = edprCalc.edpRate(outputArrayX[i], edp, sis
						.getStructure().getIm(), sis.getStructure().getPc());
				System.err.println(outputArrayX[i] + "," + outputArrayY[i]);

			}

			g.writeStartObject();
			g.writeObjectFieldStart(edp.getName());
			//g.writeStringField("x", Arrays.toString(outputArrayX));
			//g.writeStringField("y", Arrays.toString(outputArrayY));
			g.writeArrayFieldStart("x");
			for (int i=0;i<outputArrayX.length;i++){ g.writeNumber(outputArrayX[i]);};
			g.writeEndArray();
			g.writeArrayFieldStart("y");
			for (int i=0;i<outputArrayY.length;i++){ g.writeNumber(outputArrayY[i]);};			
			g.writeEndArray();
			g.writeEndObject();
		}

		g.close();
		return ostream.toString();
		*/
		return null;
	}

	
}
