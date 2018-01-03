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
* @date      2017��12��18�� ����4:24:16
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
	
	//���ò����ֶΣ���ֵ�ֶΣ�ɸѡ�ֶ�,
	public void setField(String[] fieldArr) {
		if(null == fieldArr || 0 == fieldArr.length) {
			throw new IllegalArgumentException("Field Array ����Ϊ��.");
		}
		this.fieldArr = fieldArr;
	}
	
	public void setField(List<String> fieldList) {
		setField(fieldList.toArray(new String[0]));
	}
	
	public void setField(Set<String> fieldSet) {
		setField(new ArrayList<String>(fieldSet));
	}
	
	//����SQL����
	public void setCondition(String[] conditionArr) {
		if(usingMap) {
			throw new IllegalStateException("��ѯ����ʹ��Map.");
		} else if(null == conditionArr || 0 == conditionArr.length) {
			throw new IllegalArgumentException("Condition Array ����Ϊ��.");
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
			throw new IllegalArgumentException("Condition Map ����Ϊ��.");
		}
		usingCondition = true;
		usingMap = true;
		this.conditionMap = conditionMap;
	}
	
	//���ò�ѯ�������� ʹ������
	public void resetCondition() {
		usingCondition = false;
		usingMap = false;
	}
	
	//�������
	public void clear() {
		resetCondition();
		fieldArr = null;
		conditionArr = null;
		conditionMap = null;
	}
	
	//DDL,DML����(CRUD)
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
	
	//SQL WHERE��ѯģ��
	protected String createQuery() {
		String where = "";
		//Map�Ḳ��String[]����
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
	
	//������Ӧ��SQL����
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
	
	//ɾ��һЩ����Ŀո�
	@Override
	public String toString() {
		return createStatement().replaceAll("   ", " ").replaceAll("  ", " ").replaceAll(" ;", ";").replaceAll(" ,", ",");
		
	}
}


