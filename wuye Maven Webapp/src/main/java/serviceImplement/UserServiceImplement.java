package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.User;
import entity.UserBackModel;
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
		selectsql  = "select oid from psatmp.users where email='"+email+"'";
		System.out.print(selectsql);
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isConflict = false;
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			int rowCount = 0;
			while(retsult.next()) {
			  rowCount++; 
			}
			if (rowCount > 0)isConflict = true;
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return !isConflict;
	}
	public Boolean validMobile(String mobile){
		selectsql  = "select oid from psatmp.users where mobile='"+mobile+"'";
		System.out.print(selectsql);
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isConflict = false;
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			int rowCount = 0; 
			while(retsult.next()) {
			  rowCount++; 
			}
			if (rowCount > 0)isConflict = true;
//			if (retsult.next()) {
//				if (retsult.getString(1) != null)
//					
//			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return !isConflict;
	}
	public Boolean validSMS(String sms,HttpServletRequest request1){
		System.out.println("读取");
		String smsIntCode = (String)request1.getSession().getAttribute("smsIntCode");
		if (smsIntCode == null)return false;
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
		user.setRegphoto(request.getParameter("regphoto"));
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
				"'"+user.getRegphoto()+"');";
//<{regphoto: }>);";
		System.out.println(selectsql);
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
			boolean insertSuccess = pst.execute();
			System.out.println("注册插入");
			System.out.println(insertSuccess);
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
	
	//******************* CURD **************************
	public User getOne(String oid){
		User user= new User();
		selectsql  = "select * from psatmp.users where oid='" + oid + "'";
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			if (retsult.next()) {
				System.out.println("abc============");
				user.setOid(retsult.getString(1));
				user.setTruename(retsult.getString(2));
				user.setLoginname(retsult.getString(3));
				user.setPassword(retsult.getString(4));
				user.setSex(((Integer)retsult.getInt(5) == null)?0:retsult.getInt(5));
				user.setEmail(retsult.getString(6));
				user.setMobile(retsult.getString(7));
				user.setMemlevel(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
				user.setPortrait(retsult.getString(9));
				user.setCreatetime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
				user.setEdittime((retsult.getDate(11)==null)?0:retsult.getDate(11).getTime());
				user.setQqtoken(retsult.getString(12));
				user.setWechattoken(retsult.getString(13));
				user.setWeibotoken(retsult.getString(14));
				user.setRegtype(((Integer)retsult.getInt(15) == null)?0:retsult.getInt(15));
				user.setRegphoto(retsult.getString(16));
				user.setBackgroundimg(retsult.getString(17));
			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
	public boolean addNew(User user){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (user.getOid() != null && user.getOid() != ""){//修改
			selectsql = "UPDATE `psatmp`.`users` SET `truename` = '"+user.getTruename()+"',"+
			"`loginname` = '"+user.getLoginname()+"',"
				+"`email` = '"+user.getEmail() +"',`mobile` = '"+user.getMobile()+"',`memlevel` = "+user.getMemlevel()+",";
				if (user.getPassword() != null && !user.getPassword().equals(""))
					selectsql+= "`password` = '"+user.getPassword()+"',";
				if (user.getPortrait() != null)	
					selectsql += "`portrait` = '"+user.getPortrait() +"',";
				if (user.getQqtoken() != null)	
					selectsql += "`qqtoken` = '"+user.getQqtoken()+"',";
				if (user.getWechattoken() != null)
					selectsql += "`wechattoken` = '"+user.getWechattoken()+"',";
				if (user.getWeibotoken() != null)	
					selectsql += "`weibotoken` = '"+user.getWeibotoken()+"',";
				if (user.getRegphoto() != null)
					selectsql += "`regphoto` = '"+user.getRegphoto() +"',";
				if (user.getQqtoken() != null)	
					selectsql += "`backgroundimg` = '"+user.getBackgroundimg()+"',";
				
				selectsql += "`eidttime` = now() WHERE `oid` = '"+user.getOid() +"';";
				System.out.println(selectsql);
		}else{//新增
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
			selectsql = "INSERT INTO `psatmp`.`users` (`oid`,`truename`,`loginname`,`password`,`sex`,"+
		    "`email`,`mobile`,`memlevel`,`portrait`,`createtime`,"+
			"`eidttime`,`qqtoken`,`wechattoken`,`weibotoken`,`regtype`,"+
		    "`regphoto`,`backgroundimg`) VALUES("+
			" '"+oid+"', '"+user.getTruename()+"', '"+user.getLoginname()+"', '"+user.getPassword()+"', "+user.getSex()+","+
		    " '"+user.getEmail()+"', '"+user.getMobile()+"', "+user.getMemlevel()+", '"+user.getPortrait()+"',now(),"+
			"now(), '"+user.getQqtoken()+"', '"+user.getWechattoken()+"', '"+user.getWeibotoken()+"', "+user.getRegtype()+","+
		    " '"+user.getRegphoto()+"', '"+user.getBackgroundimg()+"');";
		}
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
	public boolean deleteOne(String oid){
		selectsql = "delete from psatmp.users where oid='"+ oid +"'";
		try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
			boolean insertSuccess = pst.execute();
			System.out.println("删除一个art成功");
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
	public UserBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<User> list = new ArrayList<User>();
		Integer pages = (Integer) mSearch.get("pageindex");            //待显示页面
	    int count=0;            //总条数
	    int totalpages=0;        //总页数
	    Integer limit= (Integer) mSearch.get("pagesize");            //每页显示记录条数    
	    //计算记录总数的第二种办法：使用mysql的聚集函数count(*)
	    selectsql  = "select count(*) from psatmp.users where 1=1";
	    if(mSearch.get("regtype") != null )
	    	selectsql += " and  regtype = " + mSearch.get("regtype");
	    if (mSearch.get("mobile") != null)
	    	selectsql += " and mobile like '%"+mSearch.get("mobile")+"%'";
	    if (mSearch.get("email") != null)
	    	selectsql += " and email like '%"+mSearch.get("email")+"%'";
	    if (mSearch.get("truename") != null)
	    	selectsql += " and truename like '%"+mSearch.get("truename")+"%'";
	    
	    
	    try {
			Class.forName(mysqlDriverName);//指定连接类型
			conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
			conn=null;
			return null;
		}
	    try {
			retsult = pst.executeQuery();//执行语句，得到结果集
			if(retsult.next()){
		        count = retsult.getInt(1);//结果为count(*)表，只有一列。这里通过列的下标索引（1）来获取值
		    }    
		}catch(Exception e){
			e.printStackTrace();
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
			return null;
		} 
	    //由记录总数除以每页记录数得出总页数
	    totalpages = (int)Math.ceil(count/(limit*1.0));
	    //获取跳页时传进来的当前页面参数
//	    String strPage = request.getParameter("pages");
	    //判断当前页面参数的合法性并处理非法页号（为空则显示第一页，小于0则显示第一页，大于总页数则显示最后一页）   
        if (pages < 1){
            pages = 1;
        }
        if (pages > totalpages){
            pages = totalpages;
        }
	    //由(pages-1)*limit算出当前页面第一条记录，由limit查询limit条记录。则得出当前页面的记录
        selectsql  = "select * from psatmp.users where 1=1";
        if(mSearch.get("regtype") != null )
	    	selectsql += " and  regtype = " + mSearch.get("regtype");
	    if (mSearch.get("mobile") != null)
	    	selectsql += " and mobile like '%"+mSearch.get("mobile")+"%'";
	    if (mSearch.get("email") != null)
	    	selectsql += " and email like '%"+mSearch.get("email")+"%'";
	    if (mSearch.get("truename") != null)
	    	selectsql += " and truename like '%"+mSearch.get("truename")+"%'";
	    
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    
	    try {
	    	pst = conn.prepareStatement(selectsql);//准备执行语句
			retsult = pst.executeQuery();//执行语句，得到结果集
			while(retsult.next()){
				User user = new User();
				user.setOid(retsult.getString(1));
				user.setTruename(retsult.getString(2));
				user.setLoginname(retsult.getString(3));
				user.setPassword(retsult.getString(4));
				user.setSex(((Integer)retsult.getInt(5) == null)?0:retsult.getInt(5));
				user.setEmail(retsult.getString(6));
				user.setMobile(retsult.getString(7));
				user.setMemlevel(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
				user.setPortrait(retsult.getString(9));
				user.setCreatetime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
				user.setEdittime((retsult.getDate(11)==null)?0:retsult.getDate(11).getTime());
				user.setQqtoken(retsult.getString(12));
				user.setWechattoken(retsult.getString(13));
				user.setWeibotoken(retsult.getString(14));
				user.setRegtype(((Integer)retsult.getInt(15) == null)?0:retsult.getInt(15));
				user.setRegphoto(retsult.getString(16));
				user.setBackgroundimg(retsult.getString(17));
				list.add(user);
		    }    
		}catch(Exception e){
			e.printStackTrace();
			retsult.close();
			conn.close();//关闭连接
			pst.close();
			conn = null;
			return null;
		}
	    retsult.close();
		conn.close();//关闭连接
		pst.close();
		conn = null;
		
		UserBackModel ubm = new UserBackModel();
		ubm.setList(list);
		ubm.setPageIndex(pages);
		ubm.setPageSize(limit);
		ubm.setRecCount(count);
		
		return ubm;
	}

}
