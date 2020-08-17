package com.test.testcontroller;

import java.util.Date;

import com.eova.common.utils.time.DateUtil;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

public class TestController {
	public static void main(String[] args) {
		Date date = new Date();
		String format = DateUtil.format(date);
		System.out.println(format);
		Prop use = PropKit.use("/default/timer.txt");
		String string = use.get("Timer.cron");
		System.out.println(string);
	}

}
