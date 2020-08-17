package com.test.util;

public class CountUtil{
	
	private static int count = 3;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int addCount(int count) {
		this.count= count +1;
		return this.count;
	}
	public int LessenCount(int count) {
		this.count= count -1;
		return this.count;
	}

}