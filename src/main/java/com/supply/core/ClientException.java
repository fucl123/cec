package com.supply.core;
public class ClientException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public ClientException() 
	{
        super();
    }
    
	public ClientException(String msg) 
	{
        super(msg);
    }
    
    
}