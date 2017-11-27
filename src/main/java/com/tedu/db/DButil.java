package com.tedu.db;

import java.io.FileInputStream;
import java.lang.reflect.GenericArrayType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 数据库连接的管理类
 * @author Administrator
 *
 */
public class DButil {
	
	private static BasicDataSource ds;//数据源
	
	static{
		try {
			Properties prop=new Properties();
			prop.load(new FileInputStream("config.properties"));
			String driverclass=prop.getProperty("driverclass");
			String url=prop.getProperty("url");
			String username=prop.getProperty("username");
			String password=prop.getProperty("password");
			
			int maxActive=Integer.parseInt(prop.getProperty("maxactive"));
			int maxWait=Integer.parseInt(prop.getProperty("maxwait"));
			System.out.println("初始化数据库连接..");
		    ds=new BasicDataSource();
		    //Class.forName(...)
		    ds.setDriverClassName(driverclass);
		    //DriverManager.getConnection(...)
		    ds.setUrl(url);
		    ds.setUsername(username);
		    ds.setPassword(password);
		    //设置最大连接数
		    ds.setMaxActive(maxActive);
		    //设置最大等待时间
		    ds.setMaxWait(maxWait);
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取一个数据库连接
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
   public static Connection getConnection() throws  SQLException{
	   try {
		   /*
		    * 向连接池获取连接
		    * 若连接池中没有可用的连接时，该方法会
		    * 阻塞当前线程，阻塞时间有连接池设置的maxWait决定
		    * 。当阻塞过程中连接池有了可用连接时会立即将连接返回。
		    * 若超时仍然没有可用的连接时，该方法会抛出异常。
		    */
		return ds.getConnection();
	} catch(SQLException e){
	   throw e;
   }

}
   public static void closeConnection(Connection conn){
	   try {
		   if(conn!=null){
		   conn.setAutoCommit(true);	 
		   /*
		    * 连接池的连接对于close方法的
		    * 处理时将连接的状态设置为空闲而非真的将其关闭
		    */
		   conn.close();
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}