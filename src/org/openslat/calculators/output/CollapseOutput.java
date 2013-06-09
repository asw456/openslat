package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openslat.calculators.collapse.CollapseRate;
import org.openslat.control.SlatInputStore;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class CollapseOutput {
	
	public static String collapseRateOutput(SlatInputStore sis)
			throws JsonGenerationException, JsonMappingException, IOException {

		final CollapseRate collapseRate = new CollapseRate();
		collapseRate.setSis(sis);

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		double collapseRateDouble = collapseRate.evaluate();
		
		g.writeStartObject();
		g.writeNumberField("rate", collapseRateDouble);
		g.close();
		
		return ostream.toString();
	}
}
