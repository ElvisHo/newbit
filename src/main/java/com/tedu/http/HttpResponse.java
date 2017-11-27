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
 * ��ʾһ��HTTP����Ӧ
 * @author Administrator
 *
 */
public class HttpResponse {
	//״̬����
	private int status;
	//��ӦͷcontentType
	private String contentType;
	//��Ӧͷ ContentLength
	private int contentLength=-1;
	//��Ӧʵ��
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
    * ����Ӧ��Ϣ���͸��ͻ���
 * @throws Exception 
    */
public void flush() throws Exception{
try {
	 /*
	  *��Ӧҳ��Ҫ���û����͵�����
	  *HTTP/1.1 200 OKCRLF
	  *Contend-Type:text/htmlCRLF
	  *Content-Length:273CRLF
	  *CRLF
	  *10101010101010101010101010(index.html)
	  */
	System.out.println("������Ӧ��Ϣ");
	//1.����״̬��
	responseStatusLine();
	//2.������Ӧͷ
	responseHeader();
	
	//3.��Ӧ����
	responseEntity();
	
} catch (Exception e) {
    System.out.println("��Ӧ�ͻ���ʧ�ܣ�");
    throw e;
}	   
}
    /**
     * ��ͻ��˷���һ���ַ�������CRLF��β��CRLF�Զ�׷�ӣ� 
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
 * ��Ӧ״̬��
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
 * ��Ӧͷ
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
		//��������CRLF��ʾ��Ӧͷ��Ϣ���
		println("");
	} catch (Exception e) {
		throw e;
	}
}
/**
 * ��Ӧ����
 */
private void responseEntity(){
	BufferedInputStream bis=null;
	/*
	 * ��entity�ļ��������ֽڷ��͸��ͻ���
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