package org.smart4j.framework.bean;

import java.util.Map;

public class Param {

	private Map<String,Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		super();
		this.paramMap = paramMap;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}
}
