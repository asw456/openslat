package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;

import org.openslat.control.SlatInputStore;
import org.openslat.model.structure.Component;
import org.openslat.model.structure.PerformanceGroup;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlatParser {

	public static SlatInputStore parseInputJsonString(String inputJsonString)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objmapper = new ObjectMapper();
		SlatInputStore slatInputStore = objmapper.readValue(inputJsonString, SlatInputStore.class);
		
		StringWriter stringWriter = new StringWriter();
		objmapper.writeValue(stringWriter, slatInputStore);
		
		System.out.println("printing the JSON string for debugging, after parsing into Objects and returning to string  ");
		System.out.println(stringWriter.toString());

		return slatInputStore;
	}
}
