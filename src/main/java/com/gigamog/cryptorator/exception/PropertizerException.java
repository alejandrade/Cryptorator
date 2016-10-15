package com.gigamog.cryptorator.exception;

public class PropertizerException extends RuntimeException{
	
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PropertizerException(String property){
			super("Property "+property + " missing in property file.");
			
		}
		public PropertizerException(){
			super();
			
		}
	

}
