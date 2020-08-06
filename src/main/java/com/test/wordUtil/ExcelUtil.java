package com.test.wordUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.test.model.TestQuestions;

import jodd.util.StringUtil;

/**
 * 此类是操作Excel工具类，创建Excel表导出数据
 * @author lenovo
 *
 */
public class ExcelUtil {
	
	public static String path = "C:\\Users\\lenovo\\Desktop\\test";
	/**
	 * 导出数据
	 * @param list
	 */
	public static void readData(List<TestQuestions> list) {
		
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = wb.createSheet("sheet1");
		
		//设置列宽
		sheet.setColumnWidth(0, 27 * 256);
		sheet.setColumnWidth(1, 27 * 256);
		sheet.setColumnWidth(2, 27 * 256);
		sheet.setColumnWidth(3, 38 * 256);
		sheet.setColumnWidth(4, 27 * 256);
		sheet.setColumnWidth(5, 38 * 256);
		
		// 创建表格第一行
		HSSFRow row0 = sheet.createRow(0);
		row0.setHeight((short) (36*20));
		row0.createCell(0).setCellValue("题目");
		row0.createCell(1).setCellValue("类型（0-单选，1-多选）");
		row0.createCell(2).setCellValue("解析");
		row0.createCell(3).setCellValue("启用 （0-不启用，1-启用）");
		row0.createCell(4).setCellValue("选项");
		row0.createCell(5).setCellValue("是否正确（0-不是正确答案，1-是）");
		int tem =1 ;
		HSSFRow row = null;
		for (int i = 1; i <= list.size(); i++) {
			Set<String> keySet = list.get(i-1).getOptionsList().keySet();
			//合并单元格
			for (int j = 0; j < 4; j++) {
				sheet.addMergedRegion(new CellRangeAddress(tem,tem+keySet.size()-1,j,j));
			}
			
			// 创建表格行
			row = sheet.createRow(tem);
			row.createCell(0).setCellValue(""+String.valueOf(list.get(i-1).getTopic()));
			row.createCell(1).setCellValue(""+String.valueOf(list.get(i-1).getType()));
			row.createCell(2).setCellValue("");
			row.createCell(3).setCellValue(""+String.valueOf(list.get(i-1).getEnable()));
			int count = tem; 
			for (String key : keySet) {
				row.createCell(4).setCellValue(""+list.get(i-1).getOptionsList().get(key));
				if(list.get(i-1).getAnswer().indexOf(key)>-1) {
					row.createCell(5).setCellValue("1");
				}else {
					row.createCell(5).setCellValue("0");
				}
				count ++;
				row = sheet.createRow(count);
			}
			tem += keySet.size();
		}
		FileOutputStream outputStream = null;
		try{
			
			outputStream = new FileOutputStream(path+new Random().nextInt(10)+".xls");
			// 输出到excel文件中
			wb.write(outputStream);
			outputStream.flush();
		}catch(
		Exception e)
		{
			e.printStackTrace();
		}finally
		{
			try {
				outputStream.close();
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
