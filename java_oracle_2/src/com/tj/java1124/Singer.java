package com.tj.java1124;

import java.sql.Date;

public class Singer {
	private int num;
	private String name;
	private Date birthday;
	private String hometown;

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	@Override
	public String toString() {
		return "Singer [num=" + num + ", name=" + name + ", birthday=" + birthday + ", hometown=" + hometown + "]";
	}
}
