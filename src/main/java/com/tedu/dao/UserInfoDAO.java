package com.tedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.db.DButil;
import com.tedu.entities.UserInfo;

/**
 * DAO 数据连接对象
 * DAO是一个层次，该层里的所有类都是和数据库打交道的，
 * 作用是将数据操作的功能从业务逻辑层中分离出来，使得业务逻辑
 * 层更加专注的处理业务操作，而对于数据的维护操作分离到DAO中。
 * 并且DAO与业务逻辑层之间是用JAVA中的对象来传递数据，这也
 * 使得有了DAO可以让业务逻辑层对数据的操作完全面向对象化.
 * @author Administrator
 *
 */
public class UserInfoDAO {
	/**
	 * 修改给定的用户信息
	 * @param userInfo
	 * @return
	 */
	public boolean update(UserInfo userInfo){
		/*
		 * 名字和ID不可改，可以根据用户名
		 * 修改userInfo中该用户的新密码，昵称以及余额
		 * UPADATE userInfo_Elvis
		 * SET password=?,nickname=?,account=?
		 * WHERE username=?
		 */
		 Connection conn=null;
		 try{
	       conn=DButil.getConnection();
   	    String sql="UPDATE userInfo_Elvis "+
		           "SET password=?,nickname=?,account=? "+
		           "WHERE username=?";
		PreparedStatement ps=conn.prepareStatement(sql,new String[]{"id"});
		ps.setString(1,userInfo.getPassword());
		ps.setString(2,userInfo.getNickname());
		ps.setInt(3, userInfo.getAccount());
		ps.setString(4, userInfo.getUsername());
		int d=ps.executeUpdate();
		if(d>0){
			System.out.println("插入成功");
		return true;
		}
		
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	DButil.closeConnection(conn);
}
		return false;
	}
	/**
	 * 根据给定的用户名和密码查询该用户
	 */
   public UserInfo findByUsernameAndPassword(String username,String password){
	   Connection conn=null;
		try {
		conn=DButil.getConnection();
		String sql="SELECT id,username,password,nickname,account  "
   			+  "FROM userinfo_Elvis "
   			+  "WHERE username=? "
   			+ " AND password=?";
   	PreparedStatement ps=conn.prepareStatement(sql);
   	ps.setString(1, username);
   	ps.setString(2, password);
	ResultSet rs=ps.executeQuery();
	if(rs.next()){
		System.out.println("有该用户");
		 username=rs.getString("username");
	     password=rs.getString("password");
	     String nickname=rs.getString("nickname");
		 int account =rs.getInt("account");
		 int id=rs.getInt("id");
		 UserInfo userInfo=new UserInfo(id,username,password,nickname,account);
		 return userInfo;
	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DButil.closeConnection(conn);
		}
	  return null;
   }
	
	
	/**
	 * 根据给定的用户名查找该用户
	 * @param username
	 * @return 若没有此用户则返回值为NULL
	 */
	public UserInfo findByUsername(String username){
		/*
		 * 根据给定的用户名查询该用户名信息，若没有记录
		 * 则返回NUll,若查询到将该条记录个字段的值取出来
		 * 存入一个Userinfo实例中并返回
		 */
		Connection conn=null;
		try {
		conn=DButil.getConnection();
		String sql="SELECT id,username,password,nickname,account  "
    			+  "FROM userinfo_Elvis "
    			+  "WHERE username=? ";
    	PreparedStatement ps=conn.prepareStatement(sql);
    	ps.setString(1, username);
    	ResultSet rs=ps.executeQuery();
    	if(rs.next()){
    		System.out.println("用户已存在");
    		 username=rs.getString("username");
 		     String password=rs.getString("password");
 		     String nickname=rs.getString("nickname");
			 int account =rs.getInt("account");
			 int id=rs.getInt("id");
			 UserInfo userInfo=new UserInfo(id,username,password,nickname,account);
			 return userInfo;
    	}
			} catch (Exception e) {
				e.printStackTrace();
		}finally{
			DButil.closeConnection(conn);
		}
		
		return null;
	}
	
	
     /**
      * 保存给定的UserInfo对象所表示的用户信息
      */
	public boolean save(UserInfo userInfo){
		 Connection conn=null;
		 try{
	       conn=DButil.getConnection();
    	    String sql="INSERT INTO userinfo_Elvis "
					+ "(id,username,password,nickname,account) "
					+ "VALUES "
					+ "(seq_userinfo_id_Elvis.NEXTVAL,?,?,?,?)";
		PreparedStatement ps=conn.prepareStatement(sql,new String[]{"id"});
		ps.setString(1,userInfo.getUsername());
		ps.setString(2,userInfo.getPassword());
		ps.setString(3, userInfo.getNickname());
		ps.setInt(4, userInfo.getAccount());
		int d=ps.executeUpdate();
		if(d>0){
			System.out.println("插入成功");
			ResultSet rs=ps.getGeneratedKeys();
			rs.next();
			int id=rs.getInt(1);
			userInfo.setId(id);
			return true;
		}
		
	
}catch(Exception e){
	e.printStackTrace();
}finally{
	DButil.closeConnection(conn);
}
		return false;
}
	/*public static void main(String[] args) {
		UserInfoDAO dao=new UserInfoDAO();
		UserInfo u=dao.findByUsername("hh");
		System.out.println(u);
	}
	*/
}
