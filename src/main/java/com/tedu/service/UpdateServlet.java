package com.tedu.service;
/**
 * 完成用户的修改
 */
import com.tedu.core.HttpServlet;
import com.tedu.dao.UserInfoDAO;
import com.tedu.entities.UserInfo;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;

public class UpdateServlet  extends HttpServlet{
	 public void service(HttpRequest request,HttpResponse response){
		System.out.println("开始修改");
	    String username=request.getParameter("username");
	    String nickname=request.getParameter("nickname");
		String password=request.getParameter("password");
		int account=Integer.parseInt(request.getParameter("account"));
		System.out.println(nickname+","+password+","+account);
		
		
		/*
		 * 1:获取用户输入的用户名等信息
		 * 2：先判断该用户是否存在，不存在则提示
		 * 用户（跳转没有用户的信息的提示页面）
		 * 3：将用户输入的信息存入一个UserInfo实例中
		 * 4：调用UserInfoDAO的update进行修改
		 * 5：若修改成功跳转修改成功的提示页面
		 * 否则跳转失败的提示页面
		 */
		//检测用户是否存在
		   UserInfoDAO dao=new UserInfoDAO();
		   if(dao.findByUsername(username)==null){
			try {
				forward(response, "/update_false2.html");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
          }else{
        
        	  //创建一个userInfo实例用于表示该注册的信息
 		     UserInfo userInfo=new UserInfo();
 		     userInfo.setUsername(username);
 		     userInfo.setPassword(password);
 		     userInfo.setNickname(nickname);
 		     userInfo.setAccount(account);	
 		     System.out.println(1111);
 		    //保存用户数据信息到dao
 		        dao=new UserInfoDAO();
 		   boolean success=dao.update(userInfo);
 		   try {
 		   if(success){
 		        System.out.println("修改成功");
 		        forward(response, "/update_ok.html");
 		   }else{
 				System.out.println("修改失败");
 				forward(response, "/update_false.html");
 				}
 		   }catch (Exception e) {
 				e.printStackTrace();
 				}
 				
          }
	 }
}