package com.test.testcontroller;

import java.util.Date;

import com.eova.common.utils.time.DateUtil;

public class TestController {
	public static void main(String[] args) {
		Date date = new Date();
		String format = DateUtil.format(date);
		System.out.println(format);
	}

}
