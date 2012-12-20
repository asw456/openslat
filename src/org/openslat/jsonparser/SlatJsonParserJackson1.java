package org.openslat.jsonparser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SlatJsonParserJackson1 {
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		HashMap<String, ArrayList<InventoryItem2>> fs = new HashMap<String, ArrayList<InventoryItem2>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<InventoryItem2> its = new ArrayList<InventoryItem2>();
			its.add(new InventoryItem2("teddy", 20));
			its.add(new InventoryItem2("diny", 10));
			fs.put(Long.toString(System.nanoTime()), its);
		}

		StoreContianer g = new StoreContianer(fs);
		ObjectMapper objm = new ObjectMapper();
		StringWriter sw = new StringWriter();
		objm.writeValue(sw, g);
		System.out.println(sw.toString());

		StoreContianer c = objm
				.readValue(
						"{\"goods\":{\"55278650620460\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650631327\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650628131\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650582748\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650624615\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}]}}",
						StoreContianer.class);
		StringWriter sw2 = new StringWriter();
		objm.writeValue(sw2, c);
		System.out.println(sw2.toString());
	}
}

@JsonSerialize
class StoreContianer {
	private final HashMap<String, ArrayList<InventoryItem2>> goods;

	public StoreContianer(
			@JsonProperty("goods") HashMap<String, ArrayList<InventoryItem2>> goods) {
		this.goods = goods;
	}

	public HashMap<String, ArrayList<InventoryItem2>> getGoods() {
		return goods;
	}
}

@JsonSerialize
class InventoryItem {
	private final String product;
	private final int available;

	public InventoryItem(@JsonProperty("product") String product,
			@JsonProperty("available") int available) {
		this.product = product;
		this.available = available;
	}

	public String getProduct() {
		return product;
	}

	public int getAvailable() {
		return available;
	}
}
