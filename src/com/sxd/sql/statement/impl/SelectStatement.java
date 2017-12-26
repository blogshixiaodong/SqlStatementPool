package com.sxd.sql.statement.impl;

import com.sxd.sql.statement.SqlStatement;
import com.sxd.util.StringUtil;

/**
* @filename SelectStatement.java
* @author    ShiXiaodong
* @date      2017年12月16日 上午11:48:13
* @describe  生成Select语句
* @version   v1.0
*/

public class SelectStatement extends SqlStatement {
	
	private final String OPERATION = "SELECT";
	
	public SelectStatement(Class<?> clazz, String tableName) {
		this.clazz = clazz;
		this.tableName = tableName;
	}
	
	@Override
	protected String createOperation() {
		return OPERATION;
	}

	@Override
	protected String createSelect() {
		if(null == fieldArr || 0 == fieldArr.length) {
			return "* FROM";
		}
		return StringUtil.join(fieldArr) + " FROM";
	}
}


