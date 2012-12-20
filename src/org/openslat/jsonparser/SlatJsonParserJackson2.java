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

public class SlatJsonParserJackson2 {
	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		HashMap<String, ArrayList<InventoryItem>> fs = new HashMap<String, ArrayList<InventoryItem>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<InventoryItem> its = new ArrayList<InventoryItem>();
			its.add(new InventoryItem("teddy", 20));
			its.add(new InventoryItem("diny", 10));
			fs.put(Long.toString(System.nanoTime()), its);
		}
		
		HashMap<String, ArrayList<InventoryItem>> fs2 = new HashMap<String, ArrayList<InventoryItem>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<InventoryItem> its2 = new ArrayList<InventoryItem>();
			its2.add(new InventoryItem("monster", 20));
			its2.add(new InventoryItem("dinosaur", 10));
			fs2.put(Long.toString(System.nanoTime()), its2);
		}

		StoreContianer2 g = new StoreContianer2(fs, fs2);
		ObjectMapper objm = new ObjectMapper();
		StringWriter sw = new StringWriter();
		objm.writeValue(sw, g);
		System.out.println(sw.toString());

//		StoreContianer c = objm
//				.readValue(
//						"{\"goods\":{\"55278650620460\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650631327\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650628131\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650582748\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}],\"55278650624615\":[{\"product\":\"teddy\",\"available\":20},{\"product\":\"diny\",\"available\":10}]}}",
//						StoreContianer.class);
//		StringWriter sw2 = new StringWriter();
//		objm.writeValue(sw2, c);
//		System.out.println(sw2.toString());
	}
}

@JsonSerialize
class StoreContianer2 {
	private final HashMap<String, ArrayList<InventoryItem>> goods;
	private final HashMap<String, ArrayList<InventoryItem>> othergoods;
	
	public StoreContianer2(
			@JsonProperty("goods") HashMap<String, ArrayList<InventoryItem>> goods, @JsonProperty("othergoods") HashMap<String, ArrayList<InventoryItem>> othergoods) {
		this.goods = goods;
		this.othergoods = othergoods;
	}

	public HashMap<String, ArrayList<InventoryItem>> getGoods() {
		return goods;
	}
	
	public HashMap<String, ArrayList<InventoryItem>> getOtherGoods() {
		return othergoods;
	}
	
}

@JsonSerialize
class InventoryItem2 {
	private final String product;
	private final int available;

	public InventoryItem2(@JsonProperty("product") String product,
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
