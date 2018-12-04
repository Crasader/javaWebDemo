package org.smart4j.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CodecUtil {
	
	//将url编码
	public static String encodeURL(String source) {
		String encodeSource = null;
		try {
			URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeSource;
	}
	
	//将url解码
	public static String decodeURL(String source) {
		String decodeSource = null;
		try {
			decodeSource = URLDecoder.decode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodeSource;
	}

}
