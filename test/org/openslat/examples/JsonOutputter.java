package org.openslat.examples;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonOutputter {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		
		/*JsonFactory f = new JsonFactory();
		JsonGenerator g = f.createGenerator(new File("C:/Users/alan/Downloads/test.json"), JsonEncoding.UTF8);
		g.writeStartObject();
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		g.writeEndObject(); // for field 'name'
		g.writeBooleanField("verified", false);
		g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
		g.writeEndObject();
		g.close();*/
		
		JsonFactory f = new JsonFactory();
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		JsonGenerator g = f.createGenerator(ostream, JsonEncoding.UTF8);
		g.writeStartObject();
		//g.writeArrayFieldStart("edp1");
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		//g.writeEndArray(); // for field 'edp1'
		g.writeEndObject(); // for field 'name'
		g.writeBooleanField("verified", false);
		g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
		g.writeEndObject();
		g.close();
		
		System.out.println(ostream.toString());


	}

}
