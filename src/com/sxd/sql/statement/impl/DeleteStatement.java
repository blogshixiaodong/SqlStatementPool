package com.sxd.sql.statement.impl;

import com.sxd.sql.statement.SqlStatement;

/**
* @filename DeleteStatament.java
* @author    ShiXiaodong
* @date      2017年12月16日 下午8:18:45
* @describe  TODO
* @version   v1.1
*/

public class DeleteStatement extends SqlStatement {
	private final String OPERATION = "DELETE FROM";
	
	public DeleteStatement(String tableName) {
		this(null, tableName);
	}
	
	public DeleteStatement(Class<?> clazz, String tableName) {
		this.clazz = clazz;
		this.tableName = tableName;
	}
	
	@Override
	protected String createOperation() {
		return OPERATION;
	}
}


