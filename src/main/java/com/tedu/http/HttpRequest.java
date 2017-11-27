package com.tedu.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedu.common.HttpContext;

/**
 * Http请求对象
 * 封装一个Http请求相关信息
 * @author Administrator
 *
 */
public class HttpRequest {
	/*
	 * 请求的相关信息
	 */
	//请求方法
	private String method;
	//请求资源URI（URI统一资源定位）
	private String uri;
	//请求协议版本
	private String protocol;
	//消息报头信息
	private Map<String,String> header;
	
	//存储客户端传递过来的参数
	private Map<String,String> parameters;
	//名字数组
	private String[] name=null;
	
	
      public HttpRequest(InputStream in) throws Exception{
    	  try {
    		  System.out.println("http构造");
		//1.解析请求行
    	  parseRequestLine(in);
    	//2.解析消息头
    	  parseRequestHeader(in);
        //3.解析消息正文（略）  
    	  
		} catch (Exception e) {
			throw e;
		}
    	
      }
      /**
       * 解析请求行信息
       * @param in
     * @throws Exception 
       */
      private void parseRequestLine(InputStream in) throws Exception{
    	  /*
    	   * 实现步骤：
    	   * 1：先读取一行字符串（CRLF结尾）
    	   * 2：根据空格拆分（\s）
    	   * 3：将请求行中三项内容设置到HttpRequest对应的属性上
    	   */
    	   try {//1
			String line=readLine(in);
			System.out.println(line);
			if(line.length()==0){
				throw new RuntimeException("空的请求行");
			}
			//2
		    String[] date=line.split("\\s"); 
		    //3
			this.method=date[0];
			this.uri=date[1];
			parseUri(uri);
			this.protocol=date[2];
			System.out.println("请求行解析完成");
		} catch (Exception e) {
			throw e;
		}
			
			
		
    }
     
	/**
       * 处理URI
       */
      private void parseUri(String uri){
    	  /*/index.html
    	   * reg?usename=ixix&password=123456&nickname=haha
    	   *在GET请求中，URI可能会有上面两种情况。
    	   *HTTP协议中规定，GET请求中的URI可以传递参数，而规则
    	   *是请求的资源后面以“？”分割，之后则为所有要传递的参数，
    	   *每个参数由：name=value的格式保存，每个参数之间使用“&”分割。
    	   *这里的处理要求：
    	   *将“？”之前的内容保存到属性uri上。
    	   *之后得每个参数都存入属性parameters中
    	   *其中key为参数名，value为参数值。
    	   *1:实例化HashMap用于初始化属性parameters 
    	   *
    	   */
    	   //1
    	  this.parameters=new HashMap<String,String>();
    	  //判断是否有？
    	  int index=-1;
    	  if((index=uri.indexOf("?"))>0){
    		  //拆问号
    		  this.uri=uri.substring(0, index);
    		  //截取所有参数
    	  String paras=uri.substring(index+1);  
    	  String[] paraArray=paras.split("&");
    	  for(String para:paraArray){
    		  //拆等号
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
       * 解析消息头
       * @param in
     * @throws IOException 
       */
      private void parseRequestHeader(InputStream in) throws IOException{
    	  /**
    	   * 消息头由若干行组成
    	   * 每行格式：
    	   * name：valueCRLF
    	   *当所有消息全部发送过来后，浏览器会单独发送一个CRLF结束
    	   *
    	   * 实现思路：
    	   * 1：死循环下面步骤
    	   * 2：读取一行字符串
    	   * 3：判断该字符串是否为空字符串
    	   * 若是空字符串说明读到最后的CRLF那么可以停止
    	   * 循环，不用再解析消息头信息
    	   * 4：若不是空串，则按照“：”截取出名字与对应的值
    	   * 并存入header这个Map中
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
    	    System.out.println("解析消息头完毕");
    	    }
    	
    	    
      /**
       * 根据输入流读取第一行字符串，
       * 根据HTTP协议读取请求中的一行内容
       * 以CRLF结尾的一行字符串
       * @param in
       * @return
       * @throws IOException
       */
      private String readLine(InputStream in)throws IOException{
        	 StringBuilder builder=new StringBuilder();
        	 /*
    			 * 连续读取若干字符，直到连续读取到了CR（13）、LF（10）为止
    			 */
    			//c1是本次 c2是上一次
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
	 * 根据参数名获取参数值
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
