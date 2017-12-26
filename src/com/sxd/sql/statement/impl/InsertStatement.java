package com.sxd.sql.statement.impl;

import com.sxd.bean.BeanUtil;
import com.sxd.sql.builder.SqlBuilder;
import com.sxd.sql.statement.SqlStatement;
import com.sxd.util.StringUtil;

/**
* @file_name InsertStatement.java
* @author    ShiXiaodong
* @date      2017年12月14日 下午10:48:28
* @describe  Insert语句池
* @version   v1.1
*/

public class InsertStatement extends SqlStatement {
	private final String OPERATION = "INSERT INTO ";
	

	public InsertStatement(Class<?> clazz, String tableName) {
		this.clazz = clazz;
		this.tableName = tableName;
	}
	
	@Override
	public String createOperation() {
		return OPERATION;
	}

	@Override
	public String createInsert() {
		if(null == fieldArr || 0 == fieldArr.length) {
			return "";
		}
		String insertField = StringUtil.join(fieldArr);
		return StringUtil.getParenthesis(insertField);
	}

	@Override
	public String createValue() {
		int fieldLength = BeanUtil.getFieldCount(clazz);
		if(null != fieldArr && 0 != fieldArr.length) {
			fieldLength = fieldArr.length;
		}
		String insertValue = SqlBuilder.getFieldString("?", ",", fieldLength);
		return "VALUES" + StringUtil.getParenthesis(insertValue);
	}

	@Override
	protected String createQuery() {
		return "";
	}

	
}


