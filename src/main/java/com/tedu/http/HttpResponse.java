package com.tedu.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedu.common.HttpContext;

/**
 * 表示一个HTTP的响应
 * @author Administrator
 *
 */
public class HttpResponse {
	//状态代码
	private int status;
	//响应头contentType
	private String contentType;
	//响应头 ContentLength
	private int contentLength=-1;
	//响应实体
	private File entity;
	private OutputStream out;
	
	
   public HttpResponse(OutputStream out){
	   this.out=out;
	  
   }

public int getStatus() {
	return status;
}

public void setStatus(int status) {
	this.status = status;
}

public String getContentType() {
	return contentType;
}

public void setContentType(String contentType) {
	this.contentType = contentType;
}

public int getContentLength() {
	return contentLength;
}

public void setContentLength(int contentLength) {
	this.contentLength = contentLength;
}

public File getEntity() {
	return entity;
}

public void setEntity(File entity) {
	this.entity = entity;
}
   /**
    * 将响应信息发送给客户端
 * @throws Exception 
    */
public void flush() throws Exception{
try {
	 /*
	  *响应页面要向用户发送的内容
	  *HTTP/1.1 200 OKCRLF
	  *Contend-Type:text/htmlCRLF
	  *Content-Length:273CRLF
	  *CRLF
	  *10101010101010101010101010(index.html)
	  */
	System.out.println("发送响应信息");
	//1.发送状态行
	responseStatusLine();
	//2.发送响应头
	responseHeader();
	
	//3.响应正文
	responseEntity();
	
} catch (Exception e) {
    System.out.println("响应客户端失败！");
    throw e;
}	   
}
    /**
     * 向客户端发送一行字符串，以CRLF结尾（CRLF自动追加） 
     * @throws Exception 
     */
private void println(String line) throws Exception{
	try {
		out.write(line.getBytes("ISO8859-1"));
	    out.write(HttpContext.CR);//CR
	    out.write(HttpContext.LF);//LF
	} catch (Exception e) {
		throw e;
	}
	
}
/**
 * 响应状态行
 * @throws Exception 
 */
private void responseStatusLine() throws Exception{
	try {
		String line="HTTP/1.1"+" "+status+" "+HttpContext.statusMap.get(status);
		System.out.println(line);
		println(line);
	} catch (Exception e) {
		throw e;
	}
}
/**
 * 响应头
 * @throws Exception 
 */
private void responseHeader() throws Exception{
	  try {
		String line=null;
		if(contentType!=null){
			line="Content-Type:"+contentType;
			System.out.println(line);
			println(line);
		}
		if(contentLength>=0){
			line="Content-Length:"+contentLength;
			System.out.println(line);
			println(line);
		}
		//单独发送CRLF表示响应头信息完毕
		println("");
	} catch (Exception e) {
		throw e;
	}
}
/**
 * 响应正文
 */
private void responseEntity(){
	BufferedInputStream bis=null;
	/*
	 * 将entity文件中所有字节发送给客户端
	 */
	try {
		 bis=new BufferedInputStream(new FileInputStream(entity));
		BufferedOutputStream bos=new BufferedOutputStream(out);
		int d=-1;
		while((d=bis.read())!=-1){
			bos.write(d);
		}
		bos.flush();
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		if(bis!=null){
			try {
			bis.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
	}
}
