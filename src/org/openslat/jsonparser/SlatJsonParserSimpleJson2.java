package org.openslat.jsonparser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SlatJsonParserSimpleJson2 {

	@SuppressWarnings("unchecked")
	private JSONObject encode() {

		JSONArray list1 = new JSONArray();
		list1.add("foo");
		list1.add(new Integer(100));
		list1.add(new Double(1000.21));

		JSONArray list2 = new JSONArray();
		list2.add(new Boolean(true));
		list2.add(null);

		JSONObject obj = new JSONObject();
		obj.put("name", "foo");
		obj.put("num", new Integer(100));
		obj.put("balance", new Double(1000.21));
		obj.put("is_vip", new Boolean(true));
		obj.put("nickname", null);

		obj.put("list1", list1);
		obj.put("list2", list2);

		return obj;
	}

	@SuppressWarnings("rawtypes")
	private void decode(String jsonText) {

		JSONParser parser = new JSONParser();
		ContainerFactory containerFactory = new ContainerFactory() {
			public List creatArrayContainer() {
				return new LinkedList();
			}

			public Map createObjectContainer() {
				return new LinkedHashMap();
			}
		};

		try {
			Map json = (Map) parser.parse(jsonText, containerFactory);
			Iterator iter = json.entrySet().iterator();
			System.out.println("==iterate result==");
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				System.out.println(entry.getKey() + "=>" + entry.getValue());
			}
			System.out.println("==toJSONString()==");
			System.out.println(JSONValue.toJSONString(json));
		} catch (ParseException pe) {
			System.out.println(pe);
		}
	}

	public static void main(String[] args) {
		SlatJsonParserSimpleJson2 slatJsonParser = new SlatJsonParserSimpleJson2();

		JSONObject obj = new JSONObject();
		obj.put("one","{\"header\": [{\"doc_no\": \"DN00001\",\"stage\":\"P\"}],\"section\":[{\"upper\":100,\"lower\":1]}");
		System.out.println(obj);

		slatJsonParser.decode(obj.toJSONString());

	}

}
