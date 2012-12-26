package org.openslat.jsonparser.examples;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.openslat.model.im.IM;
import org.openslat.model.im.IMR;
import org.openslat.models.univariate.PowerModel;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SlatAttempt1 {
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		PowerModel pm = new PowerModel();
		double[] params = {2.2, 2.3};
		pm.constructPowerModel(params);
		
		IMR imr1 = new IMR();
		imr1.setImRName("imr1");
		imr1.setModel(pm);
		IMR imr2 = new IMR();
		imr2.setImRName("imr2");
		imr2.setModel(pm);
		
		ArrayList<IMR> iMR = new ArrayList<IMR>();
		iMR.add(imr1);
		iMR.add(imr2);
				
		
		IM im = new IM();
		im.setName("happiness");
		im.setiMR(iMR);
		
		SlatInputStoreExample g = new SlatInputStoreExample(im);
		ObjectMapper objm = new ObjectMapper();
		StringWriter sw = new StringWriter();
		objm.writeValue(sw, g);
		System.out.println(sw.toString());

		 SlatInputStoreExample c = objm
		 .readValue(
		 "{\"im\":{\"name\":\"happiness\",\"iMR\":[{\"imRName\":\"imr1\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}},{\"imRName\":\"imr2\",\"epistemicWeight\":1.0,\"model\":{\"type\":\"PowerModel\",\"a\":2.2,\"b\":2.3}}]}}",
		 SlatInputStoreExample.class);
		 StringWriter sw2 = new StringWriter();
		 objm.writeValue(sw2, c);
		 System.out.println(sw2.toString());
		 
		 System.out.println("the IM's name is: " + c.getIm().getName());
	}	
}

@JsonSerialize
class SlatInputStoreExample {
	private final IM im;

	public SlatInputStoreExample(@JsonProperty("im") IM im) {
		this.im = im;
	}

	public IM getIm() {
		return im;
	}

}
