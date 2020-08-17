package com.test.routes;

import com.jfinal.config.Routes;
import com.test.controller.CountCotroller;
import com.test.controller.NewTestController;

/**
 * 配置前台路由
 * @author lenovo
 *
 */
public class FrontRoutes extends Routes {
	@Override
	public void config() {
		// TODO Auto-generated method stub
		add("/NewTest",NewTestController.class);
		add("/count",CountCotroller.class);
	}

}
