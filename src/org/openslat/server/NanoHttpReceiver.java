package org.openslat.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.openslat.server.ServerRunner;
import org.openslat.server.NanoHTTPD.Response.Status;

public class NanoHttpReceiver extends NanoHTTPD {

	private boolean calculating = false;
	private boolean calcDone = true;

	public NanoHttpReceiver(String host, int port) {
		super(host, port);
	}

	/**
	 * URL-encodes everything between "/"-characters. Encodes spaces as '%20'
	 * instead of '+'.
	 */
	private String encodeUri(String uri) {
		String newUri = "";
		StringTokenizer st = new StringTokenizer(uri, "/ ", true);
		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			if (tok.equals("/"))
				newUri += "/";
			else if (tok.equals(" "))
				newUri += "%20";
			else {
				try {
					newUri += URLEncoder.encode(tok, "UTF-8");
				} catch (UnsupportedEncodingException ignored) {
				}
			}
		}
		return newUri;
	}

	/**
	 * Serves file from homeDir and its' subdirectories (only). Uses only URI,
	 * ignores all headers and HTTP parameters.
	 */
	public Response serveString(String uri, Map<String, String> header,
			String calcResult) {
		Response res = null;

		// Make sure we won't die of an exception later
		if (res == null && 0 == 1)
			res = new Response(Response.Status.INTERNAL_ERROR,
					NanoHTTPD.MIME_PLAINTEXT, "INTERNAL ERRROR: I'm confused");

		if (res == null && 0 == 1)
			res = new Response(Response.Status.NOT_FOUND,
					NanoHTTPD.MIME_PLAINTEXT, "Error 404, file not found.");

		InputStream is = new ByteArrayInputStream(calcResult.getBytes());

		res = new Response(Status.OK, MIME_PLAINTEXT, is);

		res.addHeader("Access-Control-Allow-Origin", "*");
		return res;
	}

	@Override
	public Response serve(String uri, Method method,
			Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		System.out.println(method + " '" + uri + "' ");

		Boolean debug = false;

		if (debug) {
			Iterator<String> e = header.keySet().iterator();
			while (e.hasNext()) {
				String value = e.next();
				System.out.println("  HDR: '" + value + "' = '"
						+ header.get(value) + "'");
			}
			e = parms.keySet().iterator();
			while (e.hasNext()) {
				String value = e.next();
				System.out.println("  PRM: '" + value + "' = '"
						+ parms.get(value) + "'");
			}
			e = files.keySet().iterator();
			while (e.hasNext()) {
				String value = e.next();
				System.out.println("  UPLOADED: '" + value + "' = '"
						+ files.get(value) + "'");
			}
		}

		if (method.toString().equals("GET")) {

			if (calculating) {
				System.out.println("calculating response");
				Response res = new Response("calculating");
				res.addHeader("Access-Control-Allow-Origin", "*");
				return res;

			} else if (calcDone) {
				String calcResult = "{\"one\":\"myresult\"}";
				return serveString(uri, header, calcResult);
			}
		}

		if (method.toString().equals("POST")) {
			System.out.println("POST request, receiving data");
			System.out
					.println("it works. data is: " + parms.get("inputString"));

			Response res = new Response(Status.OK, NanoHTTPD.MIME_PLAINTEXT,
					"POST method, OK");
			res.addHeader("Access-Control-Allow-Origin", "*");
			return res;
		}

		Response res = new Response("OTHER method, OK");
		res.addHeader("Access-Control-Allow-Origin", "*");
		return res;

	}

	/**
	 * Starts as a standalone file server and waits for Enter.
	 */
	public static void main(String[] args) {
		System.out
				.println("NanoHttpd 2.0: Command line options: [-h hostname] [-p port]\n"
						+ "Alan Williams \n");

		int port = 5000;
		String host = "127.0.0.1";

		for (int i = 0; i < args.length; ++i) {
			if (args[i].equalsIgnoreCase("-h"))
				host = args[i + 1];
			else if (args[i].equalsIgnoreCase("-p"))
				port = Integer.parseInt(args[i + 1]);
		}

		ServerRunner.executeInstance(new NanoHttpReceiver(host, port));
	}

	public boolean isCalculating() {
		return calculating;
	}

	public void setCalculating(boolean calculating) {
		this.calculating = calculating;
	}

	public boolean isCalcDone() {
		return calcDone;
	}

	public void setCalcDone(boolean calcDone) {
		this.calcDone = calcDone;
	}
}
