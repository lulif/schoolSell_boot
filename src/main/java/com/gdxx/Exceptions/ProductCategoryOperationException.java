package com.gdxx.Exceptions;

public class ProductCategoryOperationException extends RuntimeException {

	private static final long serialVersionUID = -6187969661165488459L;

	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}
