package com.sxd.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @filename  BeanUtil.java
* @author    ShiXiaodong
* @date      2017年12月15日 下午4:14:11
* @describe  通过反射获取JavaBean基本信息
* @version   v1.1
*/

public class BeanUtil {
	
	private BeanUtil() {
		//do nothing
	}
	
	//获取字段数
	public static int getFieldCount(Class<?> clazz) {
		return clazz.getDeclaredFields().length;
	}
	
	public static int getFieldCount(Object obj) {
		return getFieldCount(obj.getClass());
	}
	
	//设置字段值
	public static void setFieldValue(Object bean, String fieldName, Object value) {
		try {
			Field field = bean.getClass().getDeclaredField(fieldName);
			setFieldValue(bean, field, value);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}	
	}
	
	private static void setFieldValue(Object bean, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(bean, value);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setFieldValues(Object bean, Object... values) {
		Field[] fields = bean.getClass().getDeclaredFields();
		int length =  Math.min(fields.length, values.length);
		for(int i = 0; i < length; i++) {
			setFieldValue(bean, fields[i], values[i]);
		}
	}
	
	//获取字段类型
	private static String getFieldType(Field field, boolean isSimpleType) {
		if(isSimpleType) {
			return field.getType().getSimpleName();
		}
		return field.getType().getName();
	}
	
	private static List<String> getFieldTypeList(Class<?> clazz, boolean isSimpleType) {
		List<String> list = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			list.add(getFieldType(fields[i], isSimpleType));
		}
		return list;
	}
	
	private static List<String> getFieldTypeList(Object obj, boolean isSimpleType) {
		return getFieldTypeList(obj.getClass(), isSimpleType);
	}
	
	public static List<String> getFieldTypeList(Object obj) {
		return getFieldTypeList(obj, false);	
	}
	
	public static List<String> getFieldSimpleTypeList(Object obj) {
		return getFieldTypeList(obj, true);
	}
	
	public static List<String> getFieldTypeList(Class<?> clazz) {
		return getFieldTypeList(clazz, false);
	}
	
	public static List<String> getFieldSimpleTypeList(Class<?> clazz) {
		return getFieldTypeList(clazz, true);
	}
	
	//获取字段名
	public static List<String> getFieldName(Object obj) {
		return getFieldName(obj.getClass());
	}

	public static List<String> getFieldName(Class<?> clazz) {
		List<String> list = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			list.add(fields[i].getName());
		}
		return list;
	}
	
	//获取字段值
	public static Object getFieldValue(Object bean, String fieldName) {	
		Field field = null;
		try {
			field = bean.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}	
		return getFieldValue(bean, field);
		
	}
	
	private static Object getFieldValue(Object obj, Field field) {
		if(null == field) {
			return null;
		}
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Object> getFieldValues(Object obj) {
		List<Object> list = new ArrayList<Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			list.add(getFieldValue(obj, fields[i]));
		}
		return list;
	}
	
	//以键值对形式返回
	public static Map<String, Object> getKeyValue(Object obj) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<String> key = getFieldName(obj);
		List<Object> value = getFieldValues(obj);
		for(int i = 0; i < key.size(); i++) {
			map.put(key.get(i), value.get(i));
		}
		return map;
	}
	
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Student student = new Student();
		student.setAge(10);
		student.setHeight(175.5f);
		student.setId("3151911208");
		student.setName("SXD");
		student.setSex("man");
		student.setWeight(70.0f);
		System.out.println(getKeyValue(student));
	}
}


