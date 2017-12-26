package com.sxd.sql.statement.impl;

import com.sxd.bean.BeanUtil;
import com.sxd.sql.builder.SqlBuilder;
import com.sxd.sql.statement.SqlStatement;


/**
* @filename  UpdateStatement.java
* @author    ShiXiaodong
* @date      2017年12月16日 下午8:43:17
* @describe  TODO
* @version   v1.1
*/

public class UpdateStatement extends SqlStatement {
	private final String OPERATION = "UPDATE";
	
	public UpdateStatement(Class<?> clazz, String tableName) {
		this.clazz = clazz;
		this.tableName = tableName;
	}
	
	@Override
	protected String createOperation() {
		return OPERATION;
	}

	@Override
	protected String createSet() {
		String set = "SET ";
		if(null == fieldArr || 0 == fieldArr.length) {
			return set + SqlBuilder.getPreFieldString(",", BeanUtil.getFieldName(clazz).toArray(new String[0]));
		}
		return  set + SqlBuilder.getPreFieldString(",", fieldArr);
	}
}


