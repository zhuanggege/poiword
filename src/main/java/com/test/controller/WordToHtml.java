package com.test.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import jodd.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.usermodel.Picture;

public class WordToHtml {
	
	public static void main(String[] args) throws Throwable {
		  final String path = "C:\\Users\\lenovo\\Desktop\\";
		  final String fileName1 = "附件：国家数字乡村试点申报书.doc";
		  final String fileName = "海棠区湾坡村“乡村振兴”示范村建设情况简介.docx";
		  final String htmlName = "123.html";
		  //2003
		  Word2003ToHtml(path+fileName1);
		  //2007
		  Word2007ToHtml(path+fileName,null);
		  
		 }
	
	/**
	 * 
	 * @param path  存储路径
	 * @param content  2003word转换成HTML的内容
	 * @throws Exception
	 */
	public static void StorageLocal2003(String path,String content)throws Exception {
		
		 FileUtils.writeStringToFile(new File(path, "人员选择系分.html"), content, "utf-8");
		
	}
	/**
	 * 
	 * @param StorgePath  存储路径
	 * @param htmlName    存储的HTML名
	 * @param map         转2007word后的属性
	 * @throws Exception
	 */
	public static void StorgeLoca2007(String StorgePath,String htmlName,Map<Object, Object> map) throws Exception {
//		 3) 将 XWPFDocument转换成XHTML  
		  OutputStream out = new FileOutputStream(new File(StorgePath + htmlName));  
		  XHTMLConverter.getInstance().convert((XWPFDocument)map.get("document"), out, (XHTMLOptions)map.get("options"));  
	}
	
	/**
	 * office2003转html
	 * @param filepath   需要转换的文件路径（包含文件名）
	 * @return
	 * @throws Exception
	 */
	public static String Word2003ToHtml(String filepath) throws Exception{
		
		  InputStream input = new FileInputStream(filepath);
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
		     pic.writeImageContent(new FileOutputStream(filepath
		       + pic.suggestFullFileName()));
		    } catch (FileNotFoundException e) {
		     e.printStackTrace();
		    }
		   }
		  }
		  Document htmlDocument = wordToHtmlConverter.getDocument();
		  ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	      OutputStream outStream = new BufferedOutputStream(baos);
		  
		  
		  DOMSource domSource = new DOMSource(htmlDocument);
		  StreamResult streamResult = new StreamResult(outStream);
		  TransformerFactory tf = TransformerFactory.newInstance();
		  Transformer serializer = tf.newTransformer();
		  serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		  serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		  serializer.setOutputProperty(OutputKeys.METHOD, "html");
		  serializer.transform(domSource, streamResult);
		  
		  String content = baos.toString();
		  
		  outStream.close();
		  baos.close();
		  return content;
	}
	/***
	 * office2007转html
	 * @param filePath 存储文件的路径（包含文件名）   
	 * @param htmlName  转换后的html文件名
	 * @return
	 * @throws Exception
	 */
	public static Map<Object, Object> Word2007ToHtml(String filePath,String htmlName) throws Exception {
//		String path = filePath.split("\\\\")[0];
		String path = filePath.substring(0,filePath.lastIndexOf("\\"));
		Map<Object, Object> map = new HashMap<Object, Object>();
        File f = new File(filePath);  
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
                if(!StringUtil.isBlank(htmlName)) {
                	
	                OutputStream out = new FileOutputStream(new File(path + htmlName));  
	                XHTMLConverter.getInstance().convert(document, out, options);  
	                System.out.println(out);
                }
                
                //也可以使用字符数组流获取解析的内容,显示在html页面中
                ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
                XHTMLConverter.getInstance().convert(document, baos, options);  
                String content = baos.toString();
                map.put("document", document);
                map.put("options", options);
                map.put("content", content);
                baos.close();
                
            } else {  
                System.out.println("Enter only MS Office 2007+ files");  
            }  
        }
        return map;
	}
	

}
