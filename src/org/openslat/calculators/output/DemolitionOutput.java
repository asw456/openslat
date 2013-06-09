package org.openslat.calculators.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openslat.calculators.collapse.CollapseRate;
import org.openslat.calculators.collapse.DemolitionRate;
import org.openslat.control.SlatInputStore;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DemolitionOutput {
	
	public static String demolitionRateOutput(SlatInputStore sis)
			throws JsonGenerationException, JsonMappingException, IOException {

		final DemolitionRate demolitionRate = new DemolitionRate();
		demolitionRate.setSis(sis);

		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);

		double demolitionRateDouble = demolitionRate.evaluate();
		
		g.writeStartObject();
		g.writeNumberField("rate", demolitionRateDouble);
		g.close();
		
		return ostream.toString();
	}
}
