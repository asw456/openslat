package org.openslat.receiver;

/*
 * HTTPFileUploadServer.java
 * Author: S.Prasanna
 * @version 1.00
 */

// A File upload server which will handle files of any type and size
// (Text as well as binary files including images)

import java.io.*;
import java.net.*;
import java.util.*;

import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;

public class HttpReceiver extends Thread {

	static final String HTML_START = "<html>"
			+ "<title>HTTP POST Server in java</title>" + "<body>";

	static final String HTML_END = "</body>" + "</html>";

	Socket connectedClient = null;
	DataInputStream inFromClient = null;
	DataOutputStream outToClient = null;

	public HttpReceiver(Socket client) {
		connectedClient = client;
	}

	void closeStreams() throws Exception {
		inFromClient.close();
		outToClient.close();
		connectedClient.close();
	}

	// A routine to find the POST request end string from the
	// Inputstream
	int sub_array(byte[] array1, byte[] array2) throws Exception {

		int i = array1.length - 1;
		int j = array2.length - 1;
		boolean found = false;

		for (int k = i; k >= 0; k--) {
			if (array1[k] == array2[j]) {
				found = true;
				for (int l = j - 1; l >= 0; l--) {
					k = k - 1;
					if (k < 0)
						return -1;
					if (array1[k] == array2[l])
						continue;
					else {
						found = false;
						break;
					}
				}
				if (found == true)
					return k;
			}
		}
		return -1;
	}

	// Read from InputStream
	public String readLine() throws Exception {
		String line = "";

		char c = (char) inFromClient.read();

		while (c != '\n') {
			line = line + Character.toString(c);
			c = (char) (inFromClient.read());
		}
		return line.substring(0, line.lastIndexOf('\r'));
	}

	// Thread for processing individual clients
	public void run() {

		String currentLine = null, postBoundary = null, contentength = null, filename = null, contentLength = null;
		FileOutputStream fout = null;

		// Change these two parameters depending on the size of the file to be
		// uploaded
		// For a very large file > 200 MB, increase THREAD_SLEEP_TIME to prevent
		// connection
		// resets during upload, current settings are good tor handling upto 100
		// MB file size
		long THREAD_SLEEP_TIME = 20;
		int BUFFER_SIZE = 65535;

		// Upload File size limit = 25MB, can be increased
		long FILE_SIZE_LIMIT = 25000000;

		try {

			System.out.println("The Client " + connectedClient.getInetAddress()
					+ ":" + connectedClient.getPort() + " is connected");

			inFromClient = new DataInputStream(connectedClient.getInputStream());
			outToClient = new DataOutputStream(
					connectedClient.getOutputStream());

			connectedClient.setReceiveBufferSize(BUFFER_SIZE);

			currentLine = readLine();
			String headerLine = currentLine;
			StringTokenizer tokenizer = new StringTokenizer(headerLine);
			String httpMethod = tokenizer.nextToken();
			String httpQueryString = tokenizer.nextToken();

			System.out.println(currentLine);

			if (httpMethod.equals("GET")) { // GET Request
				System.out.println("GET request");
				if (httpQueryString.equals("/")) {
					// The default home page
					String responseString = HttpReceiver.HTML_START
							+ "<form action=\"http://localhost:5000\" enctype=\"multipart/form-data\""
							+ "method=\"post\">"
							+ "Enter the name of the File <input name=\"file\" type=\"file\"><br>"
							+ "<input value=\"Upload\" type=\"submit\"></form>"
							+ "Upload only text files." + HttpReceiver.HTML_END;
					sendResponse(200, responseString, false);
				} else {
					sendResponse(404,
							"<b>The Requested resource not found ...."
									+ "Usage: http://localhost:5000</b>", false);
				}

			} // if
			else { // POST Request

				System.out.println("POST request");
				while (true) {
					currentLine = readLine();

					if (currentLine
							.indexOf("Content-Type: multipart/form-data") != -1) {
						postBoundary = currentLine.split("boundary=")[1];
						// The POST boundary

						while (true) {
							currentLine = readLine();
							if (currentLine.indexOf("Content-Length:") != -1) {
								contentLength = currentLine.split(" ")[1];
								System.out.println("Content Length = "
										+ contentLength);
								break;
							}
						}

						// Content length should be <= 25MB
						if (Long.valueOf(contentLength) > FILE_SIZE_LIMIT) {
							inFromClient.skip(Long.valueOf(contentLength));
							sendResponse(200,
									"File size should be less than 25MB", false);
							break;
						}

						while (true) {
							currentLine = readLine();
							System.out.println(currentLine);
							if (currentLine.indexOf("--" + postBoundary) != -1) {
								filename = readLine().split("filename=")[1]
										.replaceAll("\"", "");
								String[] filelist = filename.split("\\"
										+ System.getProperty("file.separator"));
								filename = filelist[filelist.length - 1];
								filename = filename.trim();
								break;
							}
						}

						if (filename.length() == 0) {
							System.out.println("No input file selected.");
							sendResponse(200,
									"Please select a valid file to upload..",
									false);
							break;
						}

						String fileContentType = null;

						try {
							fileContentType = readLine().split(" ")[1];
						} catch (Exception e) {
							System.out
									.println("Can't determine POST request length");
						}

						System.out.println("File content type = "
								+ fileContentType);

						readLine(); // assert(readLine(inFromClient).equals(""))
									// : "Expected line in POST request is "" ";
						fout = new FileOutputStream(filename);

						byte[] buffer = new byte[BUFFER_SIZE], endarray;
						String end_flag = "--" + postBoundary + "--";

						endarray = end_flag.getBytes();

						int bytesRead, bytesAvailable;

						while ((bytesAvailable = inFromClient.available()) > 0) {

							Thread.sleep(THREAD_SLEEP_TIME);

							// System.out.println("Available = " +
							// inFromClient.available());
							bytesRead = inFromClient.read(buffer, 0,
									BUFFER_SIZE);

							int end_byte = 0;

							// When number of bytes to be read in the stream <
							// BUFFER_SIZE
							if (bytesAvailable < BUFFER_SIZE) {

								// System.out.println("End array length =" +
								// endarray.length);
								// System.out.println("Bytes read = " +
								// bytesRead);

								// Case where part of POST Boundary comes in the
								// last buffer
								if (bytesAvailable < endarray.length) {
									byte[] extendedArray = new byte[BUFFER_SIZE
											+ bytesAvailable];
									System.arraycopy(buffer, 0, extendedArray,
											0, bytesRead);
									bytesRead = inFromClient.read(
											extendedArray, BUFFER_SIZE,
											bytesAvailable);
									end_byte = sub_array(extendedArray,
											endarray);
									if (end_byte == -1)
										fout.write(buffer, 0, bytesRead);
									else
										fout.write(extendedArray, 0,
												end_byte - 2);
								} else {
									// Case where POST Boundary is part of last
									// buffer
									end_byte = sub_array(buffer, endarray);
									System.out
											.println("End byte = " + end_byte);
									if (end_byte == -1)
										fout.write(buffer, 0, bytesRead);
									else
										fout.write(buffer, 0, end_byte - 2);
								}
							} else {
								// Case where POST Boundary is part of the full
								// buffer
								if (bytesAvailable == 65535)
									end_byte = sub_array(buffer, endarray);
								else
									end_byte = sub_array(buffer, endarray);
								if (end_byte == -1)
									fout.write(buffer, 0, bytesRead);
								else
									fout.write(buffer, 0, end_byte - 2);
							}
						}// while

						sendResponse(200, "File " + filename + " Uploaded..",
								false);
						fout.close();
						break;
					} // if
				}// while (true); //End of do-while
			}// else
				// Close all streams
			System.out.println("Closing All Streams....");
			closeStreams();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done....");
	}

	public void sendResponse(int statusCode, String responseString,
			boolean isFile) throws Exception {

		String statusLine = null;
		String serverdetails = "Server: Java HTTPServer";
		String contentLengthLine = null;
		String fileName = null;
		String contentTypeLine = "Content-Type: text/html" + "\r\n";
		FileInputStream fin = null;

		if (statusCode == 200)
			statusLine = "HTTP/1.1 200 OK" + "\r\n";
		else
			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";

		if (isFile) {
			fileName = responseString;
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: "
					+ Integer.toString(fin.available()) + "\r\n";
			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
				contentTypeLine = "Content-Type: \r\n";
		} else {
			responseString = HttpReceiver.HTML_START + responseString
					+ HttpReceiver.HTML_END;
			contentLengthLine = "Content-Length: " + responseString.length()
					+ "\r\n";
		}

		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(serverdetails);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");

		if (isFile)
			sendFile(fin);
		else
			outToClient.writeBytes(responseString);
	}

	// Send the requested file
	public void sendFile(FileInputStream fin) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;

		while ((bytesRead = fin.read(buffer)) != -1) {
			outToClient.write(buffer, 0, bytesRead);
		}
		fin.close();
	}

	
	private static String generateResults(String inputString) {

		SlatInputStore slatInputStore;
		try {
			slatInputStore = SlatParser.parseInputJsonString(inputString);

			//SlatInputStore SlatInputStore = new SlatInputStore();
			//SlatInputStore.setCalculationOptions(slatInputStore
			//		.getCalculationOptions());
			//SlatInputStore.setStructure(slatInputStore.getStructure());

			// and some magic happens
			//String outputString = SlatInputStore.generateOutputString();
			String outputString = "";
			
			return outputString;

		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	public static void main(String args[]) throws Exception {

		@SuppressWarnings("resource")
		ServerSocket Server = new ServerSocket(5000, 10,
				InetAddress.getByName("localhost"));
		System.out.println("HTTP Server Waiting for client on port 5000");

		// Create a new thread for processing clients
		while (true) {
			Socket connected = Server.accept();
			(new HttpReceiver(connected)).start();
		}
	}
}