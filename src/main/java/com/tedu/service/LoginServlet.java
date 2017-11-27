package com.tedu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.core.HttpServlet;
import com.tedu.dao.UserInfoDAO;
import com.tedu.db.DButil;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;

public class LoginServlet extends HttpServlet{
	  public void service(HttpRequest request,HttpResponse response) throws Exception{
		    String username=request.getParameter("username");
			String password=request.getParameter("password");
			System.out.println(username+","+password);
			/*
			 * 
			 * 连接数据库，根据用户名密码查询该用户会否存在，
			 * 存在则显示登录成功，否则是登录失败
			 */
			
		     try{
		    	 UserInfoDAO dao=new UserInfoDAO();
		    	if(dao.findByUsernameAndPassword(username, password)!=null){
				System.out.println("登录成功");
				forward(response, "/login_ok.html");
			    }else{
				System.out.println("登录失败");
				forward(response, "/login_false.html");
			    }
			
		      } catch (Exception e) {
		
			   e.printStackTrace();
		      }
}
}