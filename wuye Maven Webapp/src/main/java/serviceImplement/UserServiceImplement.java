package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.Art;
import entity.User;
import service.UserService;

@Component
public class UserServiceImplement implements UserService {
	
	static String selectsql = null; 
	static ResultSet retsult = null;
	private static Properties config = new Properties(); 

	public  String mysqlUrl = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
	public  String mysqlUser = "";//"root";
	public  String mysqlPassword = "";//"7DfS4Bcmlcnv";

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	
	UserServiceImplement(){
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

	@Autowired
	private UserDao userDao;
	
	public User selectById(String oid) {
		User uu = new User();
		uu.setOid(oid);
		uu.setTruename("小晴");
		uu.setPassword("wwwddd");
		return uu;
//		return this.userDao.selectById(id);
	}
	public Boolean validEmail(String email){
		return true;
	}
	public Boolean validMobile(String mobile){
		return true;
	}
	public Boolean validSMS(String sms,HttpServletRequest request1){
		System.out.println("读取");
		String smsIntCode = (String)request1.getSession().getAttribute("smsIntCode");
		System.out.println(smsIntCode);
		System.out.println(sms);
		if (smsIntCode.equals(sms))
			return true;
		else
			return false;
	}
	public Boolean GetSMS(String mobile,HttpServletRequest request1){
		//生成4位随机数
		int a = 1111;// (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
		//String.valueOf(i);
		//与手机一起存入临时session
		System.out.println("存入");
		System.out.println(request1);
		request1.getSession().setAttribute("smsIntCode",String.valueOf(a));
		String smsIntCode = (String)request1.getSession().getAttribute("smsIntCode");
		System.out.println(smsIntCode);
		//todo //发送手机信息
		return true;//发送成功
	}
	public Boolean saveNewUser(HttpServletRequest request){
		User user = new User();
		user.setEmail(request.getParameter("email"));
		user.setTruename(request.getParameter("truename"));
		user.setLoginname(request.getParameter("loginName"));
		user.setPassword(request.getParameter("password"));
		user.setSex(Integer.parseInt(request.getParameter("optionsRadiosSex")));
		user.setMobile(request.getParameter("mobile"));
		user.setRegtype(Integer.parseInt(request.getParameter("optionsRadiosAward")));
		UUID uuid = UUID.randomUUID();
	    String oid = uuid.toString();
	    oid = oid.toUpperCase();
	      
		selectsql  = "INSERT INTO `psatmp`.`users`"+
				"(`oid`,`truename`,`loginname`,`password`,`sex`,"+
				"`email`,`mobile`,`memlevel`,`portrait`,`createtime`,"+
				"`eidttime`,`qqtoken`,`wechattoken`,`weibotoken`,`regtype`,"+
				"`regphoto`) VALUES ("+
				"'"+ oid +"','"+user.getTruename()+"','"+user.getLoginname()+"','"+user.getPassword()+"',"+String.valueOf(user.getSex())+","+
//<{oid: }>,
//<{truename: }>,
//<{loginname: }>,
//<{password: }>,
//<{sex: }>,
				"'"+user.getEmail()+"','"+user.getMobile()+"',0,'',now(),"+
//<{email: }>,
//<{mobile: }>,
//<{memlevel: }>,
//<{portrait: }>,
//<{createtime: }>,
				"now(),'','','', "+String.valueOf(user.getRegtype())+","+
//<{eidttime: }>,
//<{qqtoken: }>,
//<{wechattoken: }>,
//<{weibotoken: }>,
//<{regtype: }>,
				"'');";
//<{regphoto: }>);";
		System.out.println(selectsql);
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
			retsult = pst.executeQuery();
			System.out.println(retsult);
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
			return false;
		}
//		System.out.println(pst);
		return true;
	}
	public User validInfo(String info,String pw){
		User user = new User();
		if(info.indexOf("@")!=-1){//邮箱
			selectsql  = "select truename,loginname,memlevel from psatmp.users where memlevel > 0 and email='"+info+
					"' and password='"+ pw +"'";
		}else{
			selectsql  = "select truename,loginname,memlevel from psatmp.users where memlevel > 0 and mobile='"+info+
					"' and password='"+ pw +"'";
		}
		System.out.print(selectsql);
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}    
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			while (retsult.next()) {
				System.out.println("abc============");
				System.out.println(retsult);
				user.setTruename(retsult.getString(1));
				user.setLoginname(retsult.getString(2));
				user.setMemlevel(Integer.parseInt(retsult.getString(3)));
//				art.setCreatetime(retsult.getString(3));
				
			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	

}
