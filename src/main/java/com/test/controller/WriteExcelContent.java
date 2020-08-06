package com.test.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

public class WriteExcelContent {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		try {
			CreateExcel(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ByteArrayOutputStream CreateExcel(List<String> list) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 读取源文件
        FileInputStream fis = new FileInputStream("C:\\Users\\lenovo\\Desktop\\template - 1.xls");
        HSSFWorkbook workBook = new HSSFWorkbook(fis);    //HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls

//        XSSFWorkbook workBook = new XSSFWorkbook(fis);		//XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx

        // 进行模板的克隆(接下来的操作都是针对克隆后的sheet)
        HSSFSheet sheet = workBook.cloneSheet(0);
        workBook.setSheetName(1, "询价单"); // 给sheet命名
        workBook.removeSheetAt(0);
        HSSFCellStyle cellStyle = workBook.createCellStyle();
//        cellStyle.setBorderBottom(HSSFCellStyle); //下边框
//        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());//底框黑色
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边黑色
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边黑色
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  // 右边黑色
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 字体居中

        for (int i=10;i<list.size();i++){
            Row row = sheet.createRow(i);

            Cell cell = row.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(i-9);

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue((list.get(i)));

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(list.get(i));

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(list.get(i));

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue(list.get(i));

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(cellStyle);
            cell5.setCellValue(list.get(i-10));

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(cellStyle);
            cell6.setCellValue(list.get(i));

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(cellStyle);
            cell7.setCellValue(list.get(i-10));

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(cellStyle);
            cell8.setCellValue(list.get(i));

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(cellStyle);
            cell9.setCellValue(list.get(i));
        }

        try {
//            workBook.write(baos);
//            baos.close();
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\aaa.xls");
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
	
	public static void writeExcel() {
	
	        //获取文件名和模板
	        String templateName = "C:\\Users\\lenovo\\Desktop\\template - 1.xls";//EXCEL模板的名字
	        String fileName = "特许经营考核评分表.xlsx";//导出时下载的EXCEL文件名

	        Map<String, String> map = new HashMap<>();
	        //从数据中取出所要导出的数据存放在List集合对象中

	        InputStream inputStream =null;
	       //读取模板文件

	        Workbook wb = null;
	        try {
	        	inputStream = new FileInputStream(templateName);
	            wb = new XSSFWorkbook(inputStream);//根据不同的excel版本采用不同的Workbook

	            Sheet sheet = wb.getSheetAt(0);//读取模板中的sheet内容
	            //在相应的单元格进行赋值
	            for (int i = 0; i < 5; i++) {//对数据进行循环导出
	               Row row = sheet.getRow(1+i);
	               //模板表头占用两行，i为第一条数据 从第三行开始
	                if (row == null) {
	                    row = sheet.createRow(1 + i);
	                    row.createCell(0).setCellValue(i+1);
	                    row.createCell(1).setCellValue("标题一");
	                    row.createCell(2).setCellValue("标题二");
	                    row.createCell(3).setCellValue("标题三");
	                } else {
	                    row.getCell(0).setCellValue(i);
	                    row.getCell(1).setCellValue("标题一");
	                    row.getCell(2).setCellValue("标题二");
	                    row.createCell(3).setCellValue("标题三");
	                }
	            }

	        } catch (Exception ex) {
	            try {
					wb = new HSSFWorkbook(inputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            //读取了模板内所有sheet内容
	            HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
	            //在相应的单元格进行赋值
	            for (int i = 0; i < 5; i++) {
	                HSSFRow row = sheet.getRow(2 + i);
	                if (row == null) {
	                    row = sheet.createRow(1 + i);
	                    row.createCell(0).setCellValue(i+1);
	                    row.createCell(1).setCellValue("标题一");
	                    row.createCell(2).setCellValue("标题二");
	                    row.createCell(3).setCellValue("标题三");
	                } else {
	                    row.getCell(0).setCellValue(i);
	                    row.getCell(1).setCellValue("标题一");
	                    row.getCell(2).setCellValue("标题二");
	                    row.createCell(3).setCellValue("标题三");
	                }
	            }
	        }
	        OutputStream output;
			try {
				output = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\");
				wb.write(output);//写入到Excel模板文件中
		        output.close();//关闭
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	}

}
