package org.openslat.examples;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.openslat.control.SlatInputStore;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		g.writeObjectFieldStart("name");
		g.writeStringField("first", "Joe");
		g.writeStringField("last", "Sixpack");
		g.writeEndObject(); // for field 'name'
		g.writeBooleanField("verified", false);
		//g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
		g.writeArrayFieldStart("edp1");
		g.writeNumber(1.0);
		g.writeNumber(2.0);
		g.writeNumber(3.0);
		g.writeNumber(4.0);
		g.writeEndArray(); // for field 'edp1'
		g.writeArrayFieldStart("edp2");
		g.writeNumber(5.0);
		g.writeNumber(6.0);
		g.writeNumber(7.0);
		g.writeNumber(8.0);
		g.writeEndArray(); // for field 'edp1'
		g.writeEndObject();
		g.close();
		
		System.out.println(ostream.toString());
		
		
		
		ArrayList<ArrayList<Double>> temp = new ArrayList<ArrayList<Double>>();
		temp.add(new ArrayList<Double>());
		temp.get(0).add(1.0);
		temp.get(0).add(2.0);
		temp.add(new ArrayList<Double>());
		temp.get(1).add(1.0);
		temp.get(1).add(2.0);
		
		ArrayList<double[]> temp1 = new ArrayList<double[]>();
		temp1.add(new double[5]);
		temp1.get(0)[0] = 1.0;
		temp1.get(0)[1] = 2.0;
		temp1.get(0)[2] = 3.0;
		temp1.get(0)[3] = 4.0;
		temp1.get(0)[4] = 5.0;
		temp1.add(new double[5]);
		temp1.get(1)[0] = 1.0;
		temp1.get(1)[1] = 2.0;
		temp1.get(1)[2] = 3.0;
		temp1.get(1)[3] = 4.0;
		temp1.get(1)[4] = 5.0;
		
		ArrayListJsonTest jt = new ArrayListJsonTest();
		jt.temp = temp;
		jt.temp1 = temp1;
		
		ObjectMapper objmapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objmapper.writeValue(stringWriter, jt);
		
		System.out.println("printing the (created) object ot string  ");
		System.out.println(stringWriter.toString());
		
		ObjectMapper objmapper1 = new ObjectMapper();
		ArrayListJsonTest jt1 = objmapper1.readValue(stringWriter.toString(), ArrayListJsonTest.class);
		
		StringWriter stringWriter1 = new StringWriter();
		objmapper.writeValue(stringWriter1, jt1);
		
		System.out.println("printing the thing after conversion from (created) object to string to object to string  ");
		System.out.println(stringWriter1.toString());
		
	}

}
