package com.test.controller;

import com.eova.common.base.BaseController;
import com.test.config.Timer;
import com.test.model.Blog;
import com.test.model.QuestionBank;

public class NewTestController extends BaseController {
	
	/**
	 * 主页路由
	 */
	public void index() {
		render("/test.jsp");
	}
	
	public void test() {
		
		new Timer();
//		Blog blog = getModel(Blog.class);
//		QuestionBank blog2 = getModel(QuestionBank.class,"");
		QuestionBank blog = getBean(QuestionBank.class,"");
		String type = blog.getType();
		String info = blog.getInfo();
		System.out.println(blog.toString());
//		
//		Blog blog3 = getModel(Blog.class,"blog");
//		
//		Blog bean = getBean(Blog.class,"");
	}

}
