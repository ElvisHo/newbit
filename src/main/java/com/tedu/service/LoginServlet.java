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
			 * �������ݿ⣬�����û��������ѯ���û������ڣ�
			 * ��������ʾ��¼�ɹ��������ǵ�¼ʧ��
			 */
			
		     try{
		    	 UserInfoDAO dao=new UserInfoDAO();
		    	if(dao.findByUsernameAndPassword(username, password)!=null){
				System.out.println("��¼�ɹ�");
				forward(response, "/login_ok.html");
			    }else{
				System.out.println("��¼ʧ��");
				forward(response, "/login_false.html");
			    }
			
		      } catch (Exception e) {
		
			   e.printStackTrace();
		      }
}
}