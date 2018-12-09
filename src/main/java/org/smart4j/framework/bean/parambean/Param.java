package org.smart4j.framework.bean.parambean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.util.StringUtil;

public class Param {

	private List<FormParam> formParamList;
	
	private List<FileParam> fileParamList;

	public Param(List<FormParam> formParamList) {
		super();
		this.formParamList = formParamList;
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		super();
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}
	
	/**
	 * 获取请求参数映射
	 * @return
	 */
	public Map<String,Object> getFieldMap(){
		Map<String,Object> fieldMap = new HashMap<String,Object>();
		if(CollectionUtils.isNotEmpty(formParamList)){
			for(FormParam formParam:formParamList){
				String fieldName = formParam.getFieldName();
				Object fieldValue = formParam.getFieldValue();
				
				if(fieldMap.containsKey(fieldName)){
					fieldValue = fieldName+StringUtil.SEPARATOR+fieldValue;
				}
				fieldMap.put(fieldName, fieldValue);
			}
		}
		return fieldMap;
	}
	
	/**
	 * 获取上传文件映射
	 * @return
	 */
	public Map<String,List<FileParam>> getFileMap(){
		Map<String,List<FileParam>> fileMap = new HashMap<String,List<FileParam>>();
		if(CollectionUtils.isNotEmpty(fileParamList)){
			for(FileParam fileParam:fileParamList){
				String fileName = fileParam.getFileName();
				List<FileParam> fileParamList;
				if(fileMap.containsKey(fileName)){
					fileParamList = fileMap.get(fileName);
				}else{
					fileParamList = new ArrayList<FileParam>();
				}
				fileParamList.add(fileParam);
				fileMap.put(fileName, fileParamList);
			}
		}
		return fileMap;
	}
	
	/**
	 * 获取所有上传文件
	 * @param fileName
	 * @return
	 */
	public List<FileParam> getFileList(String fileName){
		return getFileMap().get(fileName);
	}
	
	/**
	 * 获取唯一上传文件
	 * @param fileName
	 * @return
	 */
	public FileParam getFile(String fileName){
		List<FileParam> fileParamList = getFileList(fileName);
		
		if(CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1){
			return fileParamList.get(0);
		}
		return null;
	}
	
	/**
	 * 验证参数是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return CollectionUtils.isEmpty(fileParamList) && CollectionUtils.isEmpty(formParamList);
	}
	
	public String getString(String name){
		return getFieldMap().get(name)==null?"":String.valueOf(getFieldMap().get(name));
	}
	
	public double getDouble(String name){
		return getFieldMap().get(name)==null?null:Double.valueOf(getFieldMap().get(name).toString());
	}
	
	public long getLong(String name){
		return getFieldMap().get(name)==null?null:Long.valueOf(getFieldMap().get(name).toString());
	}
	
	public int getInt(String name){
		return getFieldMap().get(name)==null?0:Integer.valueOf(getFieldMap().get(name).toString());
	}
	
	public boolean getBoolean(String name){
		return getFieldMap().get(name)==null?false:true;
	}
}
