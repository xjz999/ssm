package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import service.SysUserService;
@Component
public class SysUserServiceImplement implements SysUserService {
	static String selectsql = null; 
	static ResultSet retsult = null;
	private static Properties config = new Properties(); 

	public  String mysqlUrl = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
	public  String mysqlUser = "";//"root";
	public  String mysqlPassword = "";//"7DfS4Bcmlcnv";

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	
	SysUserServiceImplement(){
//		System.out.println("start=================");
		try {
			config.load(ArtServiceImplement.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mysqlUrl = config.getProperty("jdbc.url"); 
		mysqlUser = config.getProperty("jdbc.username");
//		System.out.println("config=================");
//		System.out.println(url);
		mysqlPassword = config.getProperty("jdbc.password");
	}
	
	public boolean ModifyPassword(String username, String oldPw, String newPw) {
		selectsql = "update psatmp.sysusers set password='"+ newPw +"' where username='"+username+"' and password='"+oldPw+"'";
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
			boolean insertSuccess = pst.execute();
			System.out.println("添加或修改一个user成功");
			System.out.println(insertSuccess);
			conn.close();//关闭连接
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
			return false;
		}
		return true;
	}

	public boolean SysLogin(String username, String password, HttpServletRequest hsq) {
		selectsql = "select * from psatmp.sysusers where username='"+username+"' and password='"+password+"'";
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}    
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			if (retsult.next()) {
				hsq.getSession().setAttribute("sysusername",username);
				hsq.getSession().setAttribute("sysuseroid",retsult.getString(1));
				System.out.println("abc============");
				System.out.println(retsult);
				retsult.close();
				conn.close();//关闭连接
				pst.close();
				conn = null;
				return true;
//				art.setCreatetime(retsult.getString(3));
				
			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
}
