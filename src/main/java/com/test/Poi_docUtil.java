package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

/**
 * 此类是获取word文件中的内容（.doc,.docx格式）
 * @author lenovo
 *
 */
public class Poi_docUtil {
	
	public static String readWord(String path) throws Exception {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
            	//txt文件获取
            	buffer = readFileByPath(path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer;
    }

	/**
	 * 读取文件（.txt文件）
	 * @param file
	 * @return
	 */
	public static String readFileByPath(String fullFilePath) throws Exception {
		StringBuilder result = new StringBuilder();
		try {
			File file = new File(fullFilePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	/**
	* @Description: POI 读取  word,去掉回车换行
	* @create: 2019-07-27 9:48
	* @update logs
	* @throws Exception
	*/
	public static List<String> readWordLinst(String filePath) throws Exception{
		
		List<String> linList = new ArrayList<String>();
		String buffer = "";
		try {
			if (filePath.endsWith(".doc")) {
				InputStream is = new FileInputStream(new File(filePath));
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();
				
				if(buffer.length() > 0){
					//使用回车换行符分割字符串
					String [] arry = buffer.split("\\r\\n");
					for (String string : arry) {
						linList.add(string.trim());
					}
				}
			} else if (filePath.endsWith(".docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				extractor.close();
				
				if(buffer.length() > 0){
					//使用换行符分割字符串
					String [] arry = buffer.split("\\n");
					for (String string : arry) {
						linList.add(string.trim());
					}
				}
			} else {
				return null;
			}
			
			return linList;
		} catch (Exception e) {
			System.out.print("error---->"+filePath);
			e.printStackTrace();
			return null;
		}
	}
	
	
	

}
