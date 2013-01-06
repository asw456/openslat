package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SlatParser {

	public static SlatInputStore parseInputJsonString(String s)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objm = new ObjectMapper();
		SlatInputStore c = objm.readValue(s, SlatInputStore.class);
		// {"im":{"name":"happiness","iMR":[{"imRName":"imr1","epistemicWeight":1.0,"model":{"type":"PowerModel","a":2.2,"b":2.3}},{"imRName":"imr2","epistemicWeight":1.0,"model":{"type":"PowerModel","a":2.2,"b":2.3}}]},"structure":null,"calculationOptions":null}
		StringWriter sw2 = new StringWriter();
		objm.writeValue(sw2, c);
		System.out.println(sw2.toString());

		System.out.println("the IM's name is: "
				+ c.getStructure().getIm().getName());
		return c;
	}
}
