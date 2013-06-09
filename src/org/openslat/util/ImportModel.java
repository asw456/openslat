package org.openslat.util;

/**
 * ImportModel provides a data structure for importing data from files with
 * various formatting.
 * 
 * @author Alan Williams
 */
public class ImportModel {
	
	private String[] parameters;
	
	private Double[][] data;
	
	/**
	 * Constructs an ImportModel using a generic list of parameters and a two 
	 * dimensional array of data that is read.
	 * 
	 * @param parameters generic list of parameters
	 * @param data two dimensional array of data that is read
	 */
	public ImportModel(String[] parameters,Double[][] data) {
		
		this.parameters = parameters;
		this.data = data;
		
	}
	
	/**
	 * Returns the generic list of parameters
	 * 
	 * @return generic list of parameters
	 */
	public String[] getParameters() {
		
		return this.parameters;
		
	}
	
	/**
	 * Returns the two dimensional array of data that is read.
	 * 
	 * @return two dimensional array of data that is read
	 */
	public Double[][] getData() {
		
		return this.data;
		
	}
	
}
