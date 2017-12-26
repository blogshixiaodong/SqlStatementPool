package com.sxd.sql.builder;

import java.text.MessageFormat;

/**
* @filename  SqlBuilder.java
* @author    ShiXiaodong
* @date      2017��12��15�� ����8:59:39
* @describe  Sql���ƴ�Ӱ�����
* @version   v1.1
*/

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sxd.util.StringUtil;



public class SqlBuilder {
	
	public static enum FieldType { SELECT_FIELD, WHERE_FIELD }

	//�������ݿ�����URL
	
	//MySQL
	public static String getMySQLConnectionString(String database) {
		return getMySQLConnectionString("3306", database);
	}
	
	public static String getMySQLConnectionString(String port, String database) {
		return MessageFormat.format("jdbc:mysql://localhost:{0}/{1}", port, database);
	}
	
	//SqlServer
	public static String getSqlServerConnectionString(String database) {
		return getSqlServerConnectionString("1433", database);
	}
	
	public static String getSqlServerConnectionString(String port, String database) {
		//SqlServer 2000: jdbc:microsoft:sqlserver://localhost:1433;databaseName=dbname
		//SqlServer 2005���ϰ汾
		return MessageFormat.format("jdbc:sqlserver://localhost:{0}; databaseName={1}", port, database);
	}
	
	//Oracle
	public static String getOracleConnectionString(String database) {
		return getOracleConnectionString("1521", database);
	}
	
	public static String getOracleConnectionString(String port, String database) {
		return MessageFormat.format("jdbc:oracle:thin:@127.0.0.1:{0}:{1}", port, database);
	}
	
	/**
	 * @param split �ָ���
	 * @param strings ��ƴ������
	 * @return "one, two, three"
	 */
	public static String getFieldString(String split, String... strings) {
		StringBuilder str = new StringBuilder("");
		int argsNum = strings.length;
		for(int i = 0; i < argsNum; i++) {
			if(i != argsNum -1 ) {
				str.append(strings[i]);
				str.append(split);
			} else {
				str.append(strings[i]);
			}
		}
		return str.toString();
	}
	
	/**
	 * @param string ��Ҫƴ�ӵ��ַ���
	 * @param split  �ָ���
	 * @param number ƴ�Ӹ���
	 * @return ����ƴ�Ӹ���ƴ��ָ���ַ���
	 * 
	 *  getFieldString("?", ",", 5)
	 *  ret: ?,?,?,?,?
	 */
	public static String getFieldString(String string, String split, int number) {
		StringBuilder str = new StringBuilder("");
		for(int i = 0; i < number; i++) {
			if(i != number -1 ) {
				str.append(string);
				str.append(split);
			} else {
				str.append(string);
			}
		}
		return str.toString();
	}
	
	/**
	 * @param objs:����ƴ�Ӷ���
	 * @return ����ƴ�ӵĲ�ͬ����ƴ���ַ���
	 * 
	 * Ex:  getFieldString(1, 1.2, "Test");
	 * ret: "1, 1.2, 'Test'"
	 */
	public static String getFieldString(Object... objs) {
		int argsNum = objs.length;
		String str = "";
		for(int i = 0; i < argsNum; i++) {
			if(objs[i] instanceof Integer) {
				str += objs[i].toString();
			} else if(objs[i] instanceof Float) {
				str += objs[i].toString();
			} else if(objs[i] instanceof Double) {
				str += objs[i].toString();
			} else if(objs[i] instanceof String) {
				str += StringUtil.getSingleQuoteMark(objs[i].toString());
			}
			if(i != argsNum - 1) {
				str += ", ";
			}
		}
		return str;
	}
	
	
	
//Ԥ��������ƴ��  set �� where �� select
	
	//field1 = ? and field2 =? and field3 = ?
	public static String getPreFieldString(String split, String... fields) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < fields.length; i++) {
			builder.append(fields[i]);
			builder.append(" = ? ");
			if(i != fields.length - 1) {
				builder.append(split + " ");
			}
		}
		return builder.toString();
	}
	
	
	//set field1 = ?, field2 = ?
	public static String getPreSetString(String... fields) {
		return " set " + getPreFieldString(",", fields);
	}
	
	//where field1 = ? and field2 = ?
	public static String getPreWhereString(String... fields) {
		return " where " + getPreFieldString("and", fields); 
	}
	
	//field1, field2
	public static String getPreQueryString(String... fields) {
		return getFieldString(",", fields);
	}
	
	// key1 = value1, key2 = value2
	public static String getMapKeyValeString(Map<String, Object> map) {
		return getMapKeyValeString(map, ",");
	}
	
	public static String getMapKeyOperString(Map<String, String> map) {
		StringBuilder builder = new StringBuilder();
		Set<Entry<String, String>> sets = map.entrySet();
		int index = 0;
		for(Entry<String, String> set : sets) {
			builder.append(set.getKey() + " ");
			builder.append(set.getValue() + " ");
			if(index != map.size() - 1) {
				builder.append("? AND ");
			} else {
				builder.append("? ");
			}
			index++;
		}
		return builder.toString();
	}
	
	public static String getMapKeyValeString(Map<String, Object> map, String split) {
		StringBuilder builder = new StringBuilder();
		int num = map.size();
		int index = 1;
		Set<Entry<String, Object>> sets = map.entrySet();
		for(Entry<String, Object> set : sets) {
			builder.append(set.getKey());
			builder.append(" = ");
			if(set.getValue() instanceof String) {
				builder.append(StringUtil.getSingleQuoteMark(set.getValue().toString()));
			} else {
				builder.append(set.getValue().toString());
			}
			if(index != num - 1) {
				builder.append(split);
			}
			index++;
		}
		return builder.toString();
	}
	
//Ԥ����SQL���ƴ��
		
	//INSERT  ��ȡԤ�������sql���  insert into tableName values(?, ?, ?)
	public static String getPreInsertString(String tableName, int fieldsNum) {
		String valueString = StringUtil.getParenthesis(getFieldString("?", ", ", fieldsNum));
		return "insert into " + tableName  + " values " + valueString;
	}
	
	//insert into tableName(field1, field2, field3) values(?, ?, ?)
	public static String getPreInsertString(String tableName, String[] insertFields) {
		String insertFieldsString =  StringUtil.getParenthesis(getFieldString(",", insertFields));
		String valueString = StringUtil.getParenthesis(getFieldString("?", ", ", insertFields.length));
		return "insert into " + tableName + insertFieldsString + " values " + valueString; 
	}
	

	//SELECT  ���Ԥ�����ѯsql��� select field1, field2 from table where field1 = ? and field2 = ?
	public static String getPreSelectString(String tableName, String[] selectFields, String[] whereFields) {
		//selectField �� whereField �ж�ѡ����Ӧ��������
		if(StringUtil.isNull(selectFields)) {
			if(StringUtil.isNull(whereFields)) {
				return getPreSelectString(tableName);
			} else {
				return getPreSelectString(tableName, whereFields, FieldType.WHERE_FIELD);
			}
		} else {
			if(StringUtil.isNull(whereFields)) {
				return getPreSelectString(tableName, selectFields, FieldType.SELECT_FIELD);
			}
		}
		//����Ϊ��
		String selectFieldsString = getPreQueryString(selectFields);
		String whereString = getPreWhereString(whereFields);
		return "select " + selectFieldsString + " from " + tableName + " " + whereString;
	}
	
	//select * from tableName
	public static String getPreSelectString(String tableName) {
		return "select * from " + tableName;
	}
	
	//����Field�ֶ������ж�
	public static String getPreSelectString(String tableName, String[] Fields, FieldType type) {
		if(type == FieldType.WHERE_FIELD) {
			return getPreSelectString(tableName, Fields, true);
		} else if(type == FieldType.SELECT_FIELD) {
			return getPreSelectString(tableName, Fields);
		}
		return "";
	}
	
	//select field1, field2 from tableName
	public static String getPreSelectString(String tableName, String[] selectFields) {
		return "select " +  getPreQueryString(selectFields) + " from " + tableName;
	}
	
	//select * from tableName where field1 = ? and field2 = ?
	public static String getPreSelectString(String tableName, String[] whereFields, boolean hasCondition) {
		return "select * from " + tableName + getPreWhereString(whereFields);
	}
	
	
	//��ȡԤ����ĸ���sql���  update tableName set field1 = 1 where field2 = 2
	public static String getPreUpdateString(String tableName, String[] updateFields, String[] whereFields) {
		if(StringUtil.isNull(whereFields)) {
			return getPreUpdateString(tableName, updateFields);
		}
		return "update " + tableName + " " + getPreSetString(updateFields) + getPreWhereString(whereFields);
	}
	
	//update tableName set field1 = 1
	public static String getPreUpdateString(String tableName, String[] updateFields) {
		return "update " + tableName + " " + getPreSetString(updateFields);
	}
	

	//��ȡԤ�����ɾ��sql���  delete from tableName where field1 = 1
	public static String getPreDeleteString(String tableName, String[] whereFields) {
		String whereString = getPreWhereString(whereFields);
		return "delete from " + tableName + " " + whereString;
	}
	
//��ִ��SQL���ƴ��
	//field1, field2
	public static String getQueryString(String... fields) {
		return getFieldString(",", fields);
	}
		
	public static String getSelectString(String tableName) {
		return "select * from " + tableName;
	}
	
	public static String getSelectString(String tableName, String[] selectFields) {
		if(StringUtil.isNull(selectFields)) {
			return getSelectString(tableName);
		}
		return  "select " + getQueryString(selectFields) + " from " + tableName;
	}
	
	public static String getSelectString(String tableName, String[] selectFields, Map<String, Object> whereMap) {
		String selectFieldsString = StringUtil.isNull(selectFields) == true ? "*" : getPreQueryString(selectFields);
		String whereString = StringUtil.isNull(whereMap) == true ? "" : "where " + getMapKeyValeString(whereMap);
		return "select " + selectFieldsString + " from " + tableName + " " + whereString;
	}
	
	public static String getSelectString(String tableName, String[] selectFields, String where) {
		String selectFieldsString = StringUtil.isNull(selectFields) == true ? "*" : getPreQueryString(selectFields);
		String whereString = "";
		if(!StringUtil.isNullOrEmpty(where)) {
			whereString = " where " + where;
		}
		return "select " + selectFieldsString + " from " + tableName + " " + whereString;
	}
}


