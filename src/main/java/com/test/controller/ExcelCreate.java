package com.test.controller;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelCreate {
	public static void main(String[] args) {
		writeDataToExcel();
	}

	
	public static void writeDataToExcel() {
		
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建sheet页
		HSSFSheet sheet = wb.createSheet("sheet1");
		//合并的单元格样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
	
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
		
		
	
		HSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("1");
		
		row1.createCell(1).setCellValue("2");
				
		row1.createCell(2).setCellValue("3");
				
		row1.createCell(3).setCellValue("4");
		row1.createCell(4).setCellValue("5");
		row1.createCell(5).setCellValue("6");
		sheet.addMergedRegion(new CellRangeAddress(1,5,0,0));
		sheet.addMergedRegion(new CellRangeAddress(1,5,1,1));
		sheet.addMergedRegion(new CellRangeAddress(1,5,2,2));
		sheet.addMergedRegion(new CellRangeAddress(1,5,3,3));
		
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(4).setCellValue("6");
		
		HSSFRow row5 = sheet.createRow(5);
		row5.createCell(0).setCellValue("1");
		
		row5.createCell(1).setCellValue("2");
				
		row5.createCell(2).setCellValue("3");
				
		row5.createCell(3).setCellValue("4");
		row5.createCell(4).setCellValue("5");
		row5.createCell(5).setCellValue("6");
		
		FileOutputStream outputStream = null;
		try{
			
			outputStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\test3.xls");
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
