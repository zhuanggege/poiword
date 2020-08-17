package com.test.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 测试试题类
 * @author lenovo
 *
 */
public class Blog extends Model<Blog>{
	public static Blog dao = new Blog().dao();
	
	private String name;
	private String age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Blog [name=" + name + ", age=" + age + "]";
	}
	

}
