package com.tedu.service;
/**
 * ����û����޸�
 */
import com.tedu.core.HttpServlet;
import com.tedu.dao.UserInfoDAO;
import com.tedu.entities.UserInfo;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;

public class UpdateServlet  extends HttpServlet{
	 public void service(HttpRequest request,HttpResponse response){
		System.out.println("��ʼ�޸�");
	    String username=request.getParameter("username");
	    String nickname=request.getParameter("nickname");
		String password=request.getParameter("password");
		int account=Integer.parseInt(request.getParameter("account"));
		System.out.println(nickname+","+password+","+account);
		
		
		/*
		 * 1:��ȡ�û�������û�������Ϣ
		 * 2�����жϸ��û��Ƿ���ڣ�����������ʾ
		 * �û�����תû���û�����Ϣ����ʾҳ�棩
		 * 3�����û��������Ϣ����һ��UserInfoʵ����
		 * 4������UserInfoDAO��update�����޸�
		 * 5�����޸ĳɹ���ת�޸ĳɹ�����ʾҳ��
		 * ������תʧ�ܵ���ʾҳ��
		 */
		//����û��Ƿ����
		   UserInfoDAO dao=new UserInfoDAO();
		   if(dao.findByUsername(username)==null){
			try {
				forward(response, "/update_false2.html");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
          }else{
        
        	  //����һ��userInfoʵ�����ڱ�ʾ��ע�����Ϣ
 		     UserInfo userInfo=new UserInfo();
 		     userInfo.setUsername(username);
 		     userInfo.setPassword(password);
 		     userInfo.setNickname(nickname);
 		     userInfo.setAccount(account);	
 		     System.out.println(1111);
 		    //�����û�������Ϣ��dao
 		        dao=new UserInfoDAO();
 		   boolean success=dao.update(userInfo);
 		   try {
 		   if(success){
 		        System.out.println("�޸ĳɹ�");
 		        forward(response, "/update_ok.html");
 		   }else{
 				System.out.println("�޸�ʧ��");
 				forward(response, "/update_false.html");
 				}
 		   }catch (Exception e) {
 				e.printStackTrace();
 				}
 				
          }
	 }
}