package com.tedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.db.DButil;
import com.tedu.entities.UserInfo;

/**
 * DAO �������Ӷ���
 * DAO��һ����Σ��ò���������඼�Ǻ����ݿ�򽻵��ģ�
 * �����ǽ����ݲ����Ĺ��ܴ�ҵ���߼����з��������ʹ��ҵ���߼�
 * �����רע�Ĵ���ҵ����������������ݵ�ά���������뵽DAO�С�
 * ����DAO��ҵ���߼���֮������JAVA�еĶ������������ݣ���Ҳ
 * ʹ������DAO������ҵ���߼�������ݵĲ�����ȫ�������.
 * @author Administrator
 *
 */
public class UserInfoDAO {
	/**
	 * �޸ĸ������û���Ϣ
	 * @param userInfo
	 * @return
	 */
	public boolean update(UserInfo userInfo){
		/*
		 * ���ֺ�ID���ɸģ����Ը����û���
		 * �޸�userInfo�и��û��������룬�ǳ��Լ����
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
			System.out.println("����ɹ�");
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
	 * ���ݸ������û����������ѯ���û�
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
		System.out.println("�и��û�");
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
	 * ���ݸ������û������Ҹ��û�
	 * @param username
	 * @return ��û�д��û��򷵻�ֵΪNULL
	 */
	public UserInfo findByUsername(String username){
		/*
		 * ���ݸ������û�����ѯ���û�����Ϣ����û�м�¼
		 * �򷵻�NUll,����ѯ����������¼���ֶε�ֵȡ����
		 * ����һ��Userinfoʵ���в�����
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
    		System.out.println("�û��Ѵ���");
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
      * ���������UserInfo��������ʾ���û���Ϣ
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
			System.out.println("����ɹ�");
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
