package com.test;

import com.sxd.bean.Student;
import com.sxd.sql.statement.SqlStatement;
import com.sxd.sql.statement.impl.*;


/**
* @filename  SqlStringTest.java
* @author    ShiXiaodong
* @date      2017年12月18日 下午3:54:20
* @describe  TODO
* @version   v1.1
*/

public class SqlStringTest {
	public static void main(String[] args) {
		SqlStatement sql = new UpdateStatement(Student.class, "students_info");
		sql.setField(new String[] {"height"});
		sql.setCondition(new String[] {"name", "id"});
		System.out.println(sql.createStatement());
		System.out.println(sql.toString());
	}
}


