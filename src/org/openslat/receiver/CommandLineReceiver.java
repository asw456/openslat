package org.openslat.receiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;

public class CommandLineReceiver {
	
	/**
	 * Reads from stdin, writes to stdout.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

			String input;
			
			StringBuilder sb = new StringBuilder();
			while((input = stdin.readLine()) != null){
				sb.append(input);
			}
			
			String inputString = sb.toString();

			// TODO: Perform the calculation here
			String outputString = "{\"demolitionRate\": 0.002,\"collapseRate\": 0.0015,\"edpRates\": [{\"name\": \"2nd storey drift (cm)\",\"x\": [0.010001702,0.016490019,0.027187445,0.044824519,0.073903137,0.121845674,0.200889555,0.331210882,0.546074427,0.900324523,1.484384191],\"y\": [0.241203936,0.136305495,0.070972726,0.033410241,0.013863709,0.004898612,0.001404113,0.000304362,4.48971E-05,3.81657E-06,1.41564E-07]}]}";
			
			System.out.println(outputString);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
