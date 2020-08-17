package com.test.controller;

import com.eova.common.base.BaseController;
import com.jfinal.aop.Before;
import com.test.interceptor.ThreadInterceptor;
import com.test.util.CountUtil;

/**
 * 测试计数
 * @author lenovo
 *
 */
@Before(ThreadInterceptor.class)
public class CountCotroller extends BaseController {
	public static void main(String[] args) {
		String a = "*3*4*11";
		String[] split = a.split("\\*");
		System.out.println();
		
	}
	
	public void index() {
		render("/count.jsp");
	}
	public void add() {
		
		System.out.println("add修改完成");
		render("/count.jsp");
	}
	public void Lessen() {
		System.out.println("Lessen修改完成");
		render("/count.jsp");
	}
}
