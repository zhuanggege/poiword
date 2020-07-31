package com.test.testcontroller;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

public class Test {

    public static void main(String[] args) {
//        Jedis redis=new Jedis("120.79.191.49", 6379);
//        redis.set("a", "0");
//        System.out.print(redis.get("a"));
        Prop jdbc = PropKit.use("default/jdbc.config");
        String url = jdbc.get("main.url");
        String user = jdbc.get("main.user");
        String pwd = jdbc.get("main.pwd");
        System.out.println(url);
        DruidPlugin druidPlugin = new DruidPlugin(url, user, pwd);
        druidPlugin.start();
        DataSource dataSource = druidPlugin.getDataSource();
        System.err.println(dataSource);

        // base model 所使用的包名
        String baseModelPackageName = "com.test.model.base";
        // base model 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/test/model/base";

        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.test.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator generator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 配置是否生成备注
//     	generator.setGenerateRemarks(true);
     	//设置映射类名
     	generator.setMappingKitClassName("_EssayMappingKit");
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 添加不需要生成的表名
        String[] a= {"address","area_city","data_login","data_money","dicts","hotel","hotel_bed"
        		,"hotel_stock","item","links","member","order_item","orders","product","sale_data","test_info"
        		,"test01","user_info","users","users_exp","users_item","area","wx_answer_log"
                ,"wx_answer_setting","wx_credentials","wx_credentials_config","wx_question_answer"
                ,"wx_question_option","wx_red_packets","wx_share","wx_user","wx_user_credentials","wx_news","wx_answer_illustrate"};
        generator.addExcludedTable(a);
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(true);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        generator.setRemovedTableNamePrefixes("wx_");
        // 生成
        generator.generate();
    }
}




