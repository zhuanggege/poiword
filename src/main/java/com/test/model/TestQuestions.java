package com.test.model;

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
		return "TestQuestions [topic=" + topic + ", answer=" + answer + "]";
	}
	
	

}
