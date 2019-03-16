package com.gdxx.Exceptions;

public class WechatAuthException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public WechatAuthException(String msg) {
		super(msg);
	}
}
