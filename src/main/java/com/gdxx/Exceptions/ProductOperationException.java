package com.gdxx.Exceptions;

public class ProductOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
