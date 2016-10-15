package com.gigamog.cryptorator.exception;

public class CryptoratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CryptoratorException(String message, Exception e){
		super(message,e);
		
	}
	
	public CryptoratorException(String message){
		super(message);
		
	}
	public CryptoratorException(){
		super();
		
	}
	
}
