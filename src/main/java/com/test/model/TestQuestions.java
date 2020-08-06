package com.test.model;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;

/**
 * 试题实体类
 * @author lenovo
 *
 */
public class TestQuestions{
	/**
	 * 题目
	 */
	private String topic;  
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 是否启用
	 */
	private int enable;
	/**
	 * 选项
	 */
	private Map<String, String> optionsList;
	
	
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public Map<String, String> getOptionsList() {
		return optionsList;
	}
	public void setOptionsList(Map<String, String> optionsList) {
		this.optionsList = optionsList;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "TestQuestions [topic=" + topic + ", answer=" + answer + ", type=" + type + ", enable=" + enable
				+ ", optionsList=" + optionsList + "]";
	}
	
	

}
