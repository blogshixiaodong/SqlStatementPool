package com.sxd.bean;

/**
* @filename Student.java
* @author    ShiXiaodong
* @date      2017年12月15日 下午4:18:56
* @describe  JavaBean测试类
* @version   v1.0
*/

public class Student {
	private String id;
	private String name;
	private String sex;
	private int age;
	private float weight;
	private float height;
	
	public Student() { }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
}


