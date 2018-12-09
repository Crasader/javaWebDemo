package org.smart4j.framework.bean;

import java.io.InputStream;

public class FileParam {

	/**
	 * 文件表单的字段名
	 */
	private String FieldName;

	/**
	 * 上传文件的文件名
	 */
	private String fileName;
	
	/**
	 * 文件大小
	 */
	private long fileSize;
	
	/**
	 * 文件类型
	 */
	private String contentType;

	/**
	 * 文件流
	 */
	private InputStream inputStream;

	public FileParam(String fieldName, String fieldName2, long fileSize, String contentType, InputStream inputStream) {
		super();
		FieldName = fieldName;
		fieldName = fieldName2;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

	public String getFieldName() {
		return FieldName;
	}


	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
}
