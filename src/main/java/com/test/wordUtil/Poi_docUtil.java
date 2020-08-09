package com.test.wordUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

/**
 * 此类是获取word文件中的内容（.doc,.docx格式）
 * @author lenovo
 *
 */
public class Poi_docUtil {
	
	/**
	 * POI 读取  word,保留空格和回车样式
	 * @param path
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * office2003转html
	 */
	public static void Word2003ToHtml(String path,String file) throws Exception{
		InputStream input = new FileInputStream(path + file);
		  HWPFDocument wordDocument = new HWPFDocument(input);
		  WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
		    DocumentBuilderFactory.newInstance().newDocumentBuilder()
		      .newDocument());
		  wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			
			@Override
			public String savePicture(byte[] content, PictureType pictureType,
			     String suggestedName, float widthInches, float heightInches) {
			     return suggestedName;
			}
		  });
		  wordToHtmlConverter.processDocument(wordDocument);
		  List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		  if (pics != null) {
		   for (int i = 0; i < pics.size(); i++) {
		    Picture pic = (Picture) pics.get(i);
		    try {
		     pic.writeImageContent(new FileOutputStream(path
		       + pic.suggestFullFileName()));
		    } catch (FileNotFoundException e) {
		     e.printStackTrace();
		    }
		   }
		  }
		  Document htmlDocument = wordToHtmlConverter.getDocument();
		  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		  DOMSource domSource = new DOMSource(htmlDocument);
		  StreamResult streamResult = new StreamResult(outStream);
		  TransformerFactory tf = TransformerFactory.newInstance();
		  Transformer serializer = tf.newTransformer();
		  serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		  serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		  serializer.setOutputProperty(OutputKeys.METHOD, "html");
		  serializer.transform(domSource, streamResult);
		  outStream.close();
		  String content = new String(outStream.toByteArray());
		  FileUtils.writeStringToFile(new File(path, "人员选择系分.html"), content, "utf-8");
	}
	/**
	 * office2007转html
	 */
	public static void Word2007ToHtml(String path,String fileName,String htmlName) throws Exception {
		final String file = path + fileName;
        File f = new File(file);  
        if (!f.exists()) {  
            System.out.println("Sorry File does not Exists!");  
        } else {  
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {  
                  
                // 1) 加载word文档生成 XWPFDocument对象  
                InputStream in = new FileInputStream(f);  
                XWPFDocument document = new XWPFDocument(in);  
  
                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)  
                File imageFolderFile = new File(path);  
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));  
                options.setExtractor( new FileImageExtractor(imageFolderFile));  
                options.setIgnoreStylesIfUnused(false);  
                options.setFragment(true);  
                  
                // 3) 将 XWPFDocument转换成XHTML  
                OutputStream out = new FileOutputStream(new File(path + htmlName));  
                XHTMLConverter.getInstance().convert(document, out, options);  
                
                //也可以使用字符数组流获取解析的内容
//                ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
//                XHTMLConverter.getInstance().convert(document, baos, options);  
//                String content = baos.toString();
//                System.out.println(content);
//                 baos.close();
            } else {  
                System.out.println("Enter only MS Office 2007+ files");  
            }  
        } 
	}
	
	

}
