package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.stereotype.Component;

import entity.Art;
import service.ArtService;

@Component
public class ArtServiceImplement implements ArtService {
	
	static String selectsql = null; 
	static ResultSet retsult = null;
	private static Properties config = new Properties(); 

	public  String url = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
	public static final String name = "com.mysql.jdbc.Driver";
	public  String user = "";//"root";
	public  String password = "";//"7DfS4Bcmlcnv";

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	
	ArtServiceImplement(){
		System.out.println("start=================");
		try {
			config.load(ArtServiceImplement.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url = config.getProperty("jdbc.url"); 
		user = config.getProperty("jdbc.username");
		System.out.println("config=================");
		System.out.println(url);
		password = config.getProperty("jdbc.password");
	}
	
	
	public Art selectById(String oid){
		Art art= new Art();
		selectsql  = "select oid,title,useroid,createtime from psatmp.arts";
		try {
			Class.forName(name);//指定连接类型
			conn = DriverManager.getConnection(url, user, password);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}    
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			while (retsult.next()) {
				System.out.println("abc============");
				System.out.println(retsult);
				art.setOid(retsult.getString(1));
				art.setTitle(retsult.getString(2));
				art.setUseroid(retsult.getString(3));
//				art.setCreatetime(retsult.getString(3));
				
			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return art;
	}
}
