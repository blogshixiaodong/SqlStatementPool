package com.sxd.sql.statement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sxd.sql.builder.SqlBuilder;

/**
* @filename  SqlStatement.java
* @author    ShiXiaodong
* @date      2017年12月18日 下午4:24:16
* @describe  TODO
* @version   v1.1
*/

public abstract class SqlStatement {
	protected Class<?> clazz = null;
	protected String tableName = null;
	private String SQL_TEMPLET = "{0} {1} {2} {3} {4} {5} {6};";
	protected String[] fieldArr = null;
	protected String[] conditionArr = null;
	protected Map<String, String> conditionMap = null;
	private boolean usingMap = false;
	private boolean usingCondition = false;
	
	//设置插入字段，赋值字段，筛选字段,
	public void setField(String[] fieldArr) {
		if(null == fieldArr || 0 == fieldArr.length) {
			throw new IllegalArgumentException("Field Array 不能为空.");
		}
		this.fieldArr = fieldArr;
	}
	
	public void setField(List<String> fieldList) {
		setField(fieldList.toArray(new String[0]));
	}
	
	public void setField(Set<String> fieldSet) {
		setField(new ArrayList<String>(fieldSet));
	}
	
	//设置SQL条件
	public void setCondition(String[] conditionArr) {
		if(usingMap) {
			throw new IllegalStateException("查询条件使用Map.");
		} else if(null == conditionArr || 0 == conditionArr.length) {
			throw new IllegalArgumentException("Condition Array 不能为空.");
		}
		usingCondition = true;
		this.conditionArr = conditionArr;
	}
	
	public void setCondition(List<String> conditionList) {
		setCondition(conditionList.toArray(new String[0]));
	}
	
	public void setCondition(Set<String> conditionSet) {
		setCondition(new ArrayList<String>(conditionSet));
	}
	
	public void setCondition(Map<String, String> conditionMap) {
		if(null == conditionMap || 0 == conditionMap.size()) {
			throw new IllegalArgumentException("Condition Map 不能为空.");
		}
		usingCondition = true;
		usingMap = true;
		this.conditionMap = conditionMap;
	}
	
	//重置查询条件容器 使用条件
	public void resetCondition() {
		usingCondition = false;
		usingMap = false;
	}
	
	//清除参数
	public void clear() {
		resetCondition();
		fieldArr = null;
		conditionArr = null;
		conditionMap = null;
	}
	
	//DDL,DML操作(CRUD)
	protected abstract String createOperation();
	
	protected String createSelect() {
		return "";
	}
	
	protected String createTableName() {
		return tableName;
	}
	
	protected String createInsert() {
		return "";
	}
	
	protected String createValue() {
		return "";
	}
	
	protected String createSet() {
		return "";
	}
	
	//SQL WHERE查询模块
	protected String createQuery() {
		String where = "";
		//Map会覆盖String[]参数
		if(usingCondition) {
			where += "WHERE ";
			if(usingMap) {
				where += SqlBuilder.getMapKeyOperString(conditionMap);
			} else {
				where += SqlBuilder.getPreFieldString("AND", conditionArr);
			}
		}
		return where;
	}
	
	//创建相应的SQL代码
	public String createStatement() {
		return MessageFormat.format(SQL_TEMPLET, 
			createOperation(),
			createSelect(),
			createTableName(),
			createInsert(),
			createValue(),
			createSet(),
			createQuery()
		);
	}
	
	//删除一些多余的空格
	@Override
	public String toString() {
		return createStatement().replaceAll("   ", " ").replaceAll("  ", " ").replaceAll(" ;", ";").replaceAll(" ,", ",");
		
	}
}


