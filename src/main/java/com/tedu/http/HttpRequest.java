package com.tedu.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedu.common.HttpContext;

/**
 * Http�������
 * ��װһ��Http���������Ϣ
 * @author Administrator
 *
 */
public class HttpRequest {
	/*
	 * ����������Ϣ
	 */
	//���󷽷�
	private String method;
	//������ԴURI��URIͳһ��Դ��λ��
	private String uri;
	//����Э��汾
	private String protocol;
	//��Ϣ��ͷ��Ϣ
	private Map<String,String> header;
	
	//�洢�ͻ��˴��ݹ����Ĳ���
	private Map<String,String> parameters;
	//��������
	private String[] name=null;
	
	
      public HttpRequest(InputStream in) throws Exception{
    	  try {
    		  System.out.println("http����");
		//1.����������
    	  parseRequestLine(in);
    	//2.������Ϣͷ
    	  parseRequestHeader(in);
        //3.������Ϣ���ģ��ԣ�  
    	  
		} catch (Exception e) {
			throw e;
		}
    	
      }
      /**
       * ������������Ϣ
       * @param in
     * @throws Exception 
       */
      private void parseRequestLine(InputStream in) throws Exception{
    	  /*
    	   * ʵ�ֲ��裺
    	   * 1���ȶ�ȡһ���ַ�����CRLF��β��
    	   * 2�����ݿո��֣�\s��
    	   * 3�����������������������õ�HttpRequest��Ӧ��������
    	   */
    	   try {//1
			String line=readLine(in);
			System.out.println(line);
			if(line.length()==0){
				throw new RuntimeException("�յ�������");
			}
			//2
		    String[] date=line.split("\\s"); 
		    //3
			this.method=date[0];
			this.uri=date[1];
			parseUri(uri);
			this.protocol=date[2];
			System.out.println("�����н������");
		} catch (Exception e) {
			throw e;
		}
			
			
		
    }
     
	/**
       * ����URI
       */
      private void parseUri(String uri){
    	  /*/index.html
    	   * reg?usename=ixix&password=123456&nickname=haha
    	   *��GET�����У�URI���ܻ����������������
    	   *HTTPЭ���й涨��GET�����е�URI���Դ��ݲ�����������
    	   *���������Դ�����ԡ������ָ֮����Ϊ����Ҫ���ݵĲ�����
    	   *ÿ�������ɣ�name=value�ĸ�ʽ���棬ÿ������֮��ʹ�á�&���ָ
    	   *����Ĵ���Ҫ��
    	   *��������֮ǰ�����ݱ��浽����uri�ϡ�
    	   *֮���ÿ����������������parameters��
    	   *����keyΪ��������valueΪ����ֵ��
    	   *1:ʵ����HashMap���ڳ�ʼ������parameters 
    	   *
    	   */
    	   //1
    	  this.parameters=new HashMap<String,String>();
    	  //�ж��Ƿ��У�
    	  int index=-1;
    	  if((index=uri.indexOf("?"))>0){
    		  //���ʺ�
    		  this.uri=uri.substring(0, index);
    		  //��ȡ���в���
    	  String paras=uri.substring(index+1);  
    	  String[] paraArray=paras.split("&");
    	  for(String para:paraArray){
    		  //��Ⱥ�
    		 String []paraDate=para.split("=");
    		 if(paraDate.length>0){
    			 if(paraDate.length==1){
    				 this.parameters.put(paraDate[0], null);
    			 }else{
    				this.parameters.put(paraDate[0], paraDate[1]);
    		     }
    		 }
    		 
    	  }
    	  
    	  }else{
    		  this.uri=uri;
    	  }
    	  System.out.println(uri);
    	  System.out.println(parameters);
    	  }
    	  
      
      /**
       * ������Ϣͷ
       * @param in
     * @throws IOException 
       */
      private void parseRequestHeader(InputStream in) throws IOException{
    	  /**
    	   * ��Ϣͷ�����������
    	   * ÿ�и�ʽ��
    	   * name��valueCRLF
    	   *��������Ϣȫ�����͹�����������ᵥ������һ��CRLF����
    	   *
    	   * ʵ��˼·��
    	   * 1����ѭ�����沽��
    	   * 2����ȡһ���ַ���
    	   * 3���жϸ��ַ����Ƿ�Ϊ���ַ���
    	   * ���ǿ��ַ���˵����������CRLF��ô����ֹͣ
    	   * ѭ���������ٽ�����Ϣͷ��Ϣ
    	   * 4�������ǿմ������ա�������ȡ���������Ӧ��ֵ
    	   * ������header���Map��
    	   *
    	   */
    	    try {
    	  header=new HashMap<String,String>();
    	   String line=null;
    	  while(true){
    		  line=readLine(in);
				if(line.length()==0){
					break;
				}
				int index=line.indexOf(":");
			String key=line.substring(0,index);
			String value=line.substring(index+1);
		    header.put(key, value);
		    System.out.println(key+"-----------"+value);
		 }	
    	  	
			} catch (IOException e) {
			    throw e;
			}
    	    System.out.println("������Ϣͷ���");
    	    }
    	
    	    
      /**
       * ������������ȡ��һ���ַ�����
       * ����HTTPЭ���ȡ�����е�һ������
       * ��CRLF��β��һ���ַ���
       * @param in
       * @return
       * @throws IOException
       */
      private String readLine(InputStream in)throws IOException{
        	 StringBuilder builder=new StringBuilder();
        	 /*
    			 * ������ȡ�����ַ���ֱ��������ȡ����CR��13����LF��10��Ϊֹ
    			 */
    			//c1�Ǳ��� c2����һ��
        	 int c1=-1,c2=-1;
        	 while((c1=in.read())!=-1){
        		 if(c1==HttpContext.LF&&c2==HttpContext.CR){
        			 break;
        		 }
        		 builder.append((char)c1);
        		 c2=c1;
        	 }
        	 return builder.toString().trim();
         }
	public String getMethod() {
		return method;
	}
	public String getUri() {
		return uri;
	}
	public String getProtocol() {
		return protocol;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	/*
	 * ���ݲ�������ȡ����ֵ
	 */
	public String getParameter(String name){
		return   parameters.get(name);
	}
	public HttpRequest(){
		
	}
	
	/*
     public static void main(String[] args) {
		HttpRequest r=new HttpRequest();
		r.parseUri("reg?usename=ixix&password=123456&nickname=haha");
		System.out.println("uri:"+r.uri);
		System.out.println("param:"+r.parameters);
	}
	*/
}
