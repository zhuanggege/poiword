package com.test.sqltemplate;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * 数据语句模板
 * @author lenovo
 *
 */
public class SqlTemplate {
	
	public static void add(ActiveRecordPlugin arp) {
		//添加SQL
		arp.addSqlTemplate("/default/sql/test.sql");
		
	}
}
