package com.test.config;

/**
 * 
 * @author lenovo
 *
 */
public class Timer implements Runnable{
	int count=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		System.out.println(++count);
		
	}
	

}
