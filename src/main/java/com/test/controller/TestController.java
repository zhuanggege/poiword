package com.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.alibaba.druid.util.JdbcUtils;
import com.eova.common.utils.time.DateUtil;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.test.model.QuestionBank;
import com.test.model.TestQuestions;
import com.test.wordUtil.ExcelUtil;
import com.test.wordUtil.Poi_docUtil;

import jodd.util.StringUtil;

/**
 * 使用poi获取word，创建Excel表
 * @author lenovo
 *
 */
public class TestController {
	
	//主线任务
	private static TestQuestions tqQuestions;
		
	public static final String FILEPATH = "C://Users//lenovo//Desktop//题库(1).docx";
	
	public static final String XZT = "单选";
	public static final int XZTN = 242;    //单选题题数
	
	public static final String PDT = "多选题";
	
	public static final Integer PDTN = 25;   //多选题题数
	
	public static String url = "jdbc:mysql://127.0.0.1:3308/begonia_main?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	public static String username = "root";
	public static String password = "123456";
	public static Connection conn = null;
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		
//    	String text = read(FILEPATH);
		String text = null;
    	try {
			text = Poi_docUtil.readWord(FILEPATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
		//----------------------------------单选题---------------------------------------
    		String xztContent = getContent(text,XZT,PDT);
        	List<TestQuestions> list2 = getSingleContent(xztContent, XZTN,XZT);
        	List<TestQuestions> getPDTList = getPDTList(list2,XZT);
    		ExcelUtil.readData(getPDTList);
    		//插入数据库
//        	add(list2,XZT);
        	
    	//----------------------------------多选题---------------------------------------
        	String pdtContent = getContent(text,PDT,null);
        	List<TestQuestions> list = getSingleContent(pdtContent,PDTN,PDT);
        	List<TestQuestions> getList2 = getPDTList(list,PDT);
        	ExcelUtil.readData(getList2);
        	//插入数据库
//        	add(list, PDT);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	
	}
	/**
	 * 多选题的内容拆分
	 * @param list
	 * @return
	 */
	public static List<TestQuestions> getPDTList(List<TestQuestions> list,String passPDT) {
		
		for (int i = 0; i < list.size(); i++) {
			Map<String , String> optionsList = new HashMap<String, String>();
			String topic = list.get(i).getTopic();
			String[] split = topic.split("A");
			list.get(i).setTopic(split[0]);
			
			String[] split2 = split[1].trim().split("\\．");
			for (int j = 1; j < split2.length; j++) {
				
				if(split2[j].indexOf('B')>-1) {
					optionsList.put("A", split2[j].substring(0, split2[j].indexOf('B')));
					if(j == split2.length-2) {
						optionsList.put("B", split2[j+1]);
						break;
					}
				}else if(split2[j].indexOf('C')>-1){
					optionsList.put("B", split2[j].substring(0, split2[j].indexOf('C')));
					if(j == split2.length-2) {
						optionsList.put("C", split2[j+1]);
						break;
					}
				}else if(split2[j].indexOf('D')>-1){
					optionsList.put("C", split2[j].substring(0, split2[j].indexOf('D')));
					if(j == split2.length-2) {
						optionsList.put("D", split2[j+1]);
						break;
					}
				}else if(split2[j].indexOf('E')>-1) {
					optionsList.put("D", split2[j].substring(0, split2[j].indexOf('E')));
					if(j == split2.length-2) {
						optionsList.put("E", split2[j+1]);
						break;
					}
				}else if(split2[j].indexOf('F')>-1) {
					optionsList.put("E", split2[j].substring(0, split2[j].indexOf('F')));
					if(j == split2.length-2) {
						optionsList.put("F", split2[j+1]);
						break;
					}
				}else if(split2[j].indexOf('G')>-1) {
					optionsList.put("F", split2[j].substring(0, split2[j].indexOf('G')));
					if(j == split2.length-2) {
						optionsList.put("F", split2[j+1]);
						break;
					}
				}
			}
			list.get(i).setOptionsList(optionsList);
			list.get(i).setEnable(1);
			if(StringUtil.equals(passPDT, PDT)) {
				list.get(i).setType(1);
			}else {
				list.get(i).setType(0);
			}
			
			
		}
		
		return list;
	}
	
	/**
	 * 添加数据到数据库
	 * @param list
	 * @param tag
	 */
	public static void add(List<TestQuestions> list ,String tag) {
		
		
		for (int i = 0; i < list.size(); i++) {
			
			TestQuestions testQuestions = list.get(i);
			String topic = testQuestions.getTopic();
			String answer = testQuestions.getAnswer();
			//单选题
			//启动状态
			String format = DateUtil.format(new Date());
			try {
				//2.获取与数据库的链接
				Class.forName("com.mysql.jdbc.Driver");
				conn = (Connection) DriverManager.getConnection(url, username, password);
				String sql = "insert into wx_question_bank(id,type,info,analysis,status,create_time,create_user)"
						+ " values(default,?,?,?,1,?,?)";
				PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
				//单选是0
				if(StringUtil.equals(tag, XZT)) {
					statement.setInt(1, 0);
				}else {
					statement.setInt(1, 1);
				}
				statement.setString(2, topic);
				statement.setString(3, answer);
				statement.setString(4, format);
				statement.setInt(5, 1);
				int update = statement.executeUpdate();
				statement.close();
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
		
	}
	/**
	 * 获取标签中的内容
	 * @param text
	 * @param tag
	 * @param endTag   
	 * @return
	 */
	public static String getContent(String text,String tag,String endTag)throws Exception{
		String bt = "";
		int btIndex;
		int btLastIndex;
		if(endTag == null) {
			//如果结束标签时空的就是截取到内容末端
			btIndex = text.indexOf(""+tag+"");
			btLastIndex = text.length();
		}else {
			btIndex = text.indexOf(""+tag+"");
			btLastIndex = text.indexOf(""+endTag+"");
		}
		if(btIndex!=-1&&btLastIndex!=-1){
    		bt = text.substring(btIndex+(""+tag+"").length(), btLastIndex);
    	}
		return bt;
	}
	
	/**
	 * 内容解析，返回集合list<TestQuestions>
	 * @param text  内容
	 * @param end   截取结束段
	 * @param tag   单项标签名，（例如：题）
	 * @return
	 */
	
	public static List<TestQuestions> getSingleContent(String text,int end,String tag) throws Exception {
		List<TestQuestions> list = new ArrayList<>();
		String bs = "";
		int bsBegin;
		int bsEnd;
		for(int i=1;i <= end;i++ ) {
			
			if(StringUtil.equals(tag, XZT)) {
				LogKit.info(""+i);
				if(i==end) {
					bsBegin = text.indexOf(""+i);
					bsEnd = text.length();
				}else {
					bsBegin = text.indexOf(""+i+"、");
					bsEnd = text.indexOf(""+(i+1)+"、");
				}
				
				if(bsBegin!=-1&&bsEnd!=-1){
					bs = text.substring(bsBegin+(i+"、").length(), bsEnd);
					String[] split = bs.split("答案：");
					tqQuestions = new TestQuestions();
					tqQuestions.setTopic(split[0]);
					tqQuestions.setAnswer(split[1]);
					list.add(tqQuestions);
					LogKit.info(""+bs);
				}
			}else {
				if(i==end) {
					bsBegin = text.indexOf(""+i+"．");
					bsEnd = text.length();
				}else {
					bsBegin = text.indexOf(""+i+"．");
					bsEnd = text.indexOf(""+(i+1)+"．");
				}
				if(bsBegin!=-1&&bsEnd!=-1){
					bs = text.substring(bsBegin+(i+"．").length(), bsEnd);
					String[] split = bs.split("\\(");
					String[] split2 = split[1].split("\\)");
					String join = StringUtil.join(split[0],split2[1]);
					tqQuestions = new TestQuestions();
					tqQuestions.setTopic(join);
					tqQuestions.setAnswer(split2[0]);
					list.add(tqQuestions);
				}
			}
				
		}
		return list;
		
	}
	
}
