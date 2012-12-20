package org.openslat.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.String;

import org.openslat.util.ImportModel;

/**
 * ImportUtil contains utilities for importing data from files with various formatting.
 * 
 * @author James Williams
 */
public class ImportControl {
	
	/**
	 * Reads data from a text file that is associated with a specific keyword
	 * and delimited by a regular expression. Note that a dynamic two
	 * dimensional array is created by using an
	 * <code>ArrayList&lt;double[]&gt;</code> with a fixed number of columns.
	 * 
	 * @param pathname pathname of the file
	 * @param heading keyword of the data that is required
	 * @param regex delimiting regular expression
	 * @return two dimensional array of data that is read
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ImportModel read(String pathname,String heading,String regex) throws FileNotFoundException, IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(pathname));
		String line = reader.readLine();
		
		while (line != null) {
			
			if (line.contains(heading)) {
				
				line = reader.readLine();
				
				String[] parameters = line.split(regex);
				
				int rows = Integer.parseInt(parameters[0]);
				int cols = Integer.parseInt(parameters[1]);
				
				Double[][] values = new Double[rows][cols];
				
				for (int i = 0 ; i < rows ; i++) {
					
					line = reader.readLine();
					String[] words = line.split(regex);
					
					for (int j = 0 ; j < cols ; j++) {
						
						values[i][j] = Double.parseDouble(words[j]);
						
					}
					
				}
				
				reader.close();
				return new ImportModel(parameters,values);
				
			}
			
			line = reader.readLine();
			
		}
		
		reader.close();
		return null;
		
	}
	
}
