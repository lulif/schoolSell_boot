package com.gdxx.dto;

import java.util.HashSet;

public class EchartXAxis {
	private String type = "category";
	// HashSetΪ��ȥ��
	private HashSet<String> data;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HashSet<String> getData() {
		return data;
	}

	public void setData(HashSet<String> data) {
		this.data = data;
	}

}
