package org.openslat.receiver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openslat.control.SlatInputStore;
import org.openslat.jsonparser.SlatParser;

public class CommandLineReceiver {
	
	/**
	 * args[0] is the path for the input JSON file
	 * args[1] is the path for the output JSON file 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		try {
			String inputString = FileUtils.readFileToString(new File(args[0]));
			SlatInputStore slatInputStore = SlatParser.parseInputJsonString(inputString);

			SlatInputStore SlatInputStore = new SlatInputStore();
			SlatInputStore.setCalculationOptions(slatInputStore.getCalculationOptions());
			SlatInputStore.setStructure(slatInputStore.getStructure());

			// and some magic happens
			String outputString = SlatInputStore.generateOutputString();
			
			File outputFile = new File(args[1]);
			outputFile.mkdirs();
			outputFile.createNewFile();
			FileUtils.writeStringToFile(outputFile, outputString);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
