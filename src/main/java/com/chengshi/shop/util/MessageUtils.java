package com.chengshi.shop.util;

import java.util.HashMap;

/**
 * 返回信息工具类
 *
 * @author xuxinlong
 * @version 2017年07月07日
 */
public class MessageUtils {

	/**
	 * 生成成功信息
	 * @return
	 */
	public static HashMap<String, Object> success(){
		HashMap<String, Object> hashMap= new HashMap<>();
		hashMap.put("errorCode", "y");
		hashMap.put("errorText", "操作成功");
		return hashMap;
		
	}

	/**
	 * 生成错误信息
	 * @return
	 */
	public static HashMap<String, Object> error(){
		HashMap<String, Object> hashMap= new HashMap<>();
		hashMap.put("errorCode", "n");
		hashMap.put("errorText", "操作失败");
		return hashMap;
	}

	/**
	 * 生成错误信息扩展信息
	 * @param message
	 * @return
	 */
	public static HashMap<String, Object> error(String message){
		HashMap<String, Object> hashMap= new HashMap<>();
		hashMap.put("errorCode", "n");
		hashMap.put("errorText", message);
		return hashMap;
	}

	/**
	 * 成功信息扩展方法
	 * @param message
	 * @return
	 */
	public static HashMap<String, Object> success(String message){
		HashMap<String, Object> hashMap= new HashMap<>();
		hashMap.put("errorCode", "y");
		hashMap.put("errorText", message);
		return hashMap;
		
	}
}
