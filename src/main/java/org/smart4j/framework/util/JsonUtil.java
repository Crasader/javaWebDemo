package org.smart4j.framework.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	//将obj转换成json
	public static <T> String toJson(T obj) {
		String json=null;
		try {
			json = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	//将json转为obj
	public static <T> T fromJson(String json,Class<T> type) {
		T obj = null;
		try {
			obj = OBJECT_MAPPER.readValue(json, type);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
