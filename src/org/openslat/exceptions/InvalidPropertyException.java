package org.openslat.exceptions;

public class InvalidPropertyException extends RuntimeException {
	
	private static final long serialVersionUID = 271551638404344697L;

	/**
	 * Constructs an InvalidPropertyException with no detail message.
	 */
	public InvalidPropertyException() {
		
		super();
		
	}

	/**
	 * Constructs an InvalidPrpertyExceptoin with the specified detail message
	 * 
	 * @param s the detail message
	 */
	public InvalidPropertyException(String s) {
		
		super(s);
		
	}
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param s the detail message
	 * @param cause the cause
	 */
	public InvalidPropertyException(String s, Throwable cause) {
			
		super(s,cause);
		
	}
	
	/**
	 * Constructs a new exception with the specified cause.
	 * 
	 * @param cause the cause
	 */
	public InvalidPropertyException(Throwable cause) {
		
		super(cause);
		
	}
	
}
