package com.sxd.util;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
* @filename StringUtil.java
* @author    ShiXiaodong
* @date      2017��12��15�� ����9:10:57
* @describe  TODO
* @version   v1.0
*/


public class StringUtil {
	
	/**
	 * @param str �������ַ���
	 * @return string --> 'string'
	 */
	public static String getSingleQuoteMark(String str) {
		return "'" + str + "'";
	}
	
	/**
	 * @param str �������ַ���
	 * @return sting --> "string"
	 */
	public static String getDoubleQuoteMark(String str) {
		return "\"" + str + "\""; 
	}
	
	/**
	 * @param str �������ַ���
	 * @return sting --> {string}
	 */
	public static String getBrace(String str) {
		return "{" + str + "}";
	}
	
	/**
	 * @param str �������ַ���
	 * @return sting --> (string)
	 */
	public static String getParenthesis(String str) {
		return "(" + str + ")";
	}
	
	/**
	 * @param str �������ַ���
	 * @return sting --> [string]
	 */
	public static String getBracket(String str) {
		return "[" + str + "]";
	}
	
	
	/**
	 * ������������תΪ�ַ���
	 * @param bool ����ֵ
	 * @return �ַ���
	 */
	public static String getBaseTypeString(Boolean bool) {
		return Boolean.toString(bool);
	}
	
	/**
	 * ������������תΪ�ַ���
	 * @param integer ����ֵ
	 * @return �ַ���
	 */
	public static String getBaseTypeString(int integer) {
		return Integer.toString(integer);
	}
	
	/**
	 * ������������תΪ�ַ���
	 * @param _float �����ȸ�����
	 * @return �ַ���
	 */
	public static String getBaseTypeString(float _float) {
		return Float.toString(_float);
	}
	
	/**
	 * ������������תΪ�ַ���
	 * @param _double ˫���ȸ�����
	 * @return �ַ���
	 */
	public static String getBaseTypeString(double _double) {
		return Double.toString(_double);
	}
	
	
	/**
	 * ʵ������C#��String.Format�������ܣ� String.Format("{0} + {1} = {2}", 1, 2, 1+2)   -->   "1+2=3"
	 * @param Source ��ʾ���������ַ���
	 * @param strings ƥ�����
	 * @return ƥ�����ַ���
	 */
	public static String Format(String Source, String... strings) {
		int replaceNum = strings.length;
		for(int i = 0; i < replaceNum; i++) {	
			Source = Source.replace(getBrace(getBaseTypeString(i)), strings[i]);		
		}
		return Source;
	}
	
	
	/**
	 * �ж��Ƿ�Ϊnull
	 * @param obj �ж϶���
	 * @return �Ƿ�Ϊnull
	 */
	public static Boolean isNull(Object obj) {
		return null == obj;
	}
	
	
	/**
	 * �ж��ַ����Ƿ�Ϊnull��Ϊ""
	 * @param str �жϵ��ַ���
	 * @return �Ƿ�Ϊnull��Ϊ""
	 */
	public static Boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	
	/**
	 * �ж��Ƿ��ʼ�������ѳ�ʼ������ԭֵsource����֮����value
	 * @param source ԭֵ
	 * @param value  ��ʼ��ֵ
	 * @return
	 */
	public static String setInitValue(String source, String value) {
		if(isNullOrEmpty(source)) {
			return value;
		}
		return source;
	}
	
	
	/**
	 * �����ò������������ʽ����
	 * @param strings ���ò���
	 * @return ����
	 */
	public static String[] getArray(String... strings) {
		return (String[])getArray((Object[])strings);
	}
	
	public static Object[] getArray(Object... obj) {
		Object[] array = new String[obj.length];
		for(int i = 0; i < obj.length; i++) {
			array[i] = obj[i];
		}
		return array;
	}
	
	/**
	 * @param obj ����System.out.print(obj);
	 */
	public static void print(Object obj) {
		System.out.print(obj);
	}
	
	/**
	 * @param obj ����System.out.println(obj);
	 */
	public static void println(Object obj) {

		System.out.println(obj);
	}
	
	//����Type���ͣ���Objectת����Ӧ���ͣ����ṩ���������������͵�ת��{String, Integer, Float, Double}
	public static Object convert(Type type, Object obj) {
		String typeString = type.toString().toLowerCase();
		String value = obj.toString();
		if(typeString.contains("String")) {
			return value;
		} else if(typeString.contains("int") || typeString.contains("Integer")) {
			return new Integer(value);
		} else if(typeString.contains("float") || typeString.contains("Float")) {
			return new Float(value);
		} else if(typeString.contains("double") || typeString.contains("Double")) {
			return new Double(value);
		} else if(typeString.contains("boolean") || typeString.contains("Boolean")) {
			return new Integer(value);
		}
		return value;
	}
	
	public static String join(String[] arr) {
		String str = Arrays.asList(arr).toString();
		return str.replace("[", "").replace("]", "");
	}
	
	public static String join(String[] arr, String split) {
		return join(arr).replaceAll(",", split);
	}
	
	public static void main(String[] args) {
		String[] str = {"212", "324", "3234", "565", "3878"};
		System.out.println(join(str));
	}
}


