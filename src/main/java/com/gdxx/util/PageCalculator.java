package com.gdxx.util;
/*
 * 前端传过来 【第几页||一页几项】 
 * 这里把它转换为sql语句中应如何表达
 */
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return pageIndex > 1 ? (pageIndex-1) * pageSize : 0;
	}
}
