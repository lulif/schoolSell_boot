package com.gdxx.util;
/*
 * ǰ�˴����� ���ڼ�ҳ||һҳ��� 
 * �������ת��Ϊsql�����Ӧ��α��
 */
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex, int pageSize) {
		return pageIndex > 1 ? (pageIndex-1) * pageSize : 0;
	}
}
