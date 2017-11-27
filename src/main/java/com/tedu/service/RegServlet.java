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
 * ��������û�ע�Ṧ��
 * @author Administrator
 *
 */
public class RegServlet extends HttpServlet{
		  
	 public void service(HttpRequest request,HttpResponse response) {
    	  System.out.println("��ʼ����ע�ᣡ");
    	  /*
    	   *���û���ע����Ϣ����д��
    	   *userinfo.txt�ļ��С�
    	   *ÿ��Ϊһ���û�����Ϣ����ʽ��
    	   *username��password��nickname 
    	   *1:��ȡ����ע����Ϣ 
    	   *2������������
    	   *3��д������
    	   *
    	   */
           String username=request.getParameter("username");
		   String password=request.getParameter("password");
		   String nickname=request.getParameter("nickname");
		   //����û��Ƿ����
		   UserInfoDAO dao=new UserInfoDAO();
		   if(dao.findByUsername(username)!=null){
			try {
				forward(response, "/reg_false2.html");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		   
		   }else{
		   //����һ��userInfoʵ�����ڱ�ʾ��ע�����Ϣ
		     UserInfo userInfo=new UserInfo();
		     userInfo.setUsername(username);
		     userInfo.setPassword(password);
		     userInfo.setNickname(nickname);
		     userInfo.setAccount(5000);
		    //�����û�������Ϣ��dao
		        dao=new UserInfoDAO();
		   boolean success=dao.save(userInfo);
		   try {
		   if(success){
		        System.out.println("ע��ɹ�");
		        forward(response, "/reg_ok.html");
		   }else{
				System.out.println("ע��ʧ��");
				forward(response, "/reg_false.html");
				}
		   }catch (Exception e) {
				e.printStackTrace();
				}
		   
	 }
				}
				
		}

