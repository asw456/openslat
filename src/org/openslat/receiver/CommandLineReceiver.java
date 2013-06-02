package org.openslat.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openslat.control.SlatController;

public class CommandLineReceiver {

	/**
	 * Reads from stdin, writes to stdout.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(
					System.in));

			String input;

			StringBuilder sb = new StringBuilder();
			while ((input = stdin.readLine()) != null) {
				sb.append(input);
			}

			String inputString = sb.toString();
			SlatController.run(inputString);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
