package com.test.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.test.util.CountUtil;

import jodd.util.StringUtil;

public class ThreadInterceptor implements Interceptor{
	
	private Invocation inv = null;
	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		this.inv = inv;
		inv.invoke();
		
	}
	public void test() {
		CountUtil count = new CountUtil();
		String key = inv.getActionKey();
		if(StringUtil.equals(key, "/count/add")) {
			System.out.println("add主线程");
			count.LessenCount(count.getCount());
			System.out.println(count.getCount());
		}else if(StringUtil.equals(key, "/count/Lessen")) {
			System.out.println("Lessen主线程");
			count.addCount(count.getCount());
			System.out.println(count.getCount());
		}
	}

}
