package com.tedu.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.common.HttpContext;
import com.tedu.core.HttpServlet;
import com.tedu.dao.UserInfoDAO;
import com.tedu.db.DButil;
import com.tedu.entities.UserInfo;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;

/**
 * 用来完成用户注册功能
 * @author Administrator
 *
 */
public class RegServlet extends HttpServlet{
		  
	 public void service(HttpRequest request,HttpResponse response) {
    	  System.out.println("开始处理注册！");
    	  /*
    	   *将用户的注册信息按行写入
    	   *userinfo.txt文件中。
    	   *每行为一条用户的信息，格式：
    	   *username，password，nickname 
    	   *1:获取所有注册信息 
    	   *2：创建输入流
    	   *3：写入数据
    	   *
    	   */
           String username=request.getParameter("username");
		   String password=request.getParameter("password");
		   String nickname=request.getParameter("nickname");
		   //检测用户是否存在
		   UserInfoDAO dao=new UserInfoDAO();
		   if(dao.findByUsername(username)!=null){
			try {
				forward(response, "/reg_false2.html");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		   
		   }else{
		   //创建一个userInfo实例用于表示该注册的信息
		     UserInfo userInfo=new UserInfo();
		     userInfo.setUsername(username);
		     userInfo.setPassword(password);
		     userInfo.setNickname(nickname);
		     userInfo.setAccount(5000);
		    //保存用户数据信息到dao
		        dao=new UserInfoDAO();
		   boolean success=dao.save(userInfo);
		   try {
		   if(success){
		        System.out.println("注册成功");
		        forward(response, "/reg_ok.html");
		   }else{
				System.out.println("注册失败");
				forward(response, "/reg_false.html");
				}
		   }catch (Exception e) {
				e.printStackTrace();
				}
		   
	 }
				}
				
		}

