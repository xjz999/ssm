package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import entity.CompomentSql;
import entity.QueryParam;
import entity.User;
import entity.UserBackModel;
import service.UserService;

@Component
public class UserServiceImplement implements UserService {
	
//	static String selectsql = null; 
//	static ResultSet retsult = null;
//	private static Properties config = new Properties(); 
//
//	public  String mysqlUrl = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
//	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
//	public  String mysqlUser = "";//"root";
//	public  String mysqlPassword = "";//"7DfS4Bcmlcnv";
//
//	public static Connection conn = null;
//	public static PreparedStatement pst = null;
	
	UserServiceImplement(){
////		System.out.println("start=================");
//		try {
//			config.load(ArtServiceImplement.class.getResourceAsStream("/jdbc.properties"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mysqlUrl = config.getProperty("jdbc.url"); 
//		mysqlUser = config.getProperty("jdbc.username");
////		System.out.println("config=================");
////		System.out.println(url);
//		mysqlPassword = config.getProperty("jdbc.password");
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
	public Boolean validEmail (String email) throws SQLException{
		String sql  = "select oid from psatmp.users where email=?";
		System.out.print(sql);
		boolean isConflict = false;
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next()){
        	isConflict = true;
        }
        rs.close();
        pst.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return !isConflict;
	}
	public Boolean validMobile(String mobile) throws SQLException{
		String sql  = "select oid from psatmp.users where mobile=?";
		System.out.print(sql);
		boolean isConflict = false;
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, mobile);
        ResultSet rs = pst.executeQuery();
        if (rs.next()){
        	isConflict = true;
        }
        rs.close();  
        pst.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		
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
		int a = (int)(Math.random()*(9999-1000+1))+1000;//产生1000-9999的随机数
		//String.valueOf(i);
		//与手机一起存入临时session
		System.out.println("SMS存入");
		System.out.println(request1);
		request1.getSession().setAttribute("smsIntCode",String.valueOf(a));
		String smsIntCode = (String)request1.getSession().getAttribute("smsIntCode");
		System.out.println(smsIntCode);
		//todo //发送手机信息
		return SMSSent.doGet(String.valueOf(a), mobile);
//		return true;//发送成功
	}
	public Boolean saveNewUser(HttpServletRequest request) throws SQLException, NumberFormatException{
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
	      
		String sql  = "INSERT INTO `psatmp`.`users`"+
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
		System.out.println(sql);
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
        PreparedStatement pst = conn.prepareStatement(sql);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess>0);
	}
	public User validInfo(String info,String pw) throws SQLException{
		User user = new User();
		String sql = "";
		if(info.indexOf("@")!=-1){//邮箱
			sql  = "select truename,loginname,memlevel from psatmp.users where memlevel > 0 and email=? and password=?";
		}else{
			sql  = "select truename,loginname,memlevel from psatmp.users where memlevel > 0 and mobile=? and password=?";
		}
		System.out.print(sql);
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, info);
		pst.setString(2, pw);
        ResultSet rs = pst.executeQuery();
        if (rs.next()){
        	System.out.println("abc============");
			System.out.println(rs);
			user.setTruename(rs.getString(1));
			user.setLoginname(rs.getString(2));
			user.setMemlevel(Integer.parseInt(rs.getString(3)));
        }
        rs.close();  
        pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return user;
	}
	
	//******************* CURD **************************
	public User getOne(String oid) throws SQLException{
		User user= new User();
		String sql  = "select * from psatmp.users where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
			System.out.println("abc============");
			user.setOid(rs.getString(1));
			user.setTruename(rs.getString(2));
			user.setLoginname(rs.getString(3));
			user.setPassword(rs.getString(4));
			user.setSex(((Integer)rs.getInt(5) == null)?0:rs.getInt(5));
			user.setEmail(rs.getString(6));
			user.setMobile(rs.getString(7));
			user.setMemlevel(((Integer)rs.getInt(8) == null)?0:rs.getInt(8));
			user.setPortrait(rs.getString(9));
			user.setCreatetime((rs.getDate(10)==null)?0:rs.getDate(10).getTime());
			user.setEdittime((rs.getDate(11)==null)?0:rs.getDate(11).getTime());
			user.setQqtoken(rs.getString(12));
			user.setWechattoken(rs.getString(13));
			user.setWeibotoken(rs.getString(14));
			user.setRegtype(((Integer)rs.getInt(15) == null)?0:rs.getInt(15));
			user.setRegphoto(rs.getString(16));
			user.setBackgroundimg(rs.getString(17));
		}//显示数据
        rs.close();  
        pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return user;
	}
	public boolean addNew(User user) throws SQLException{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String sql="";
		if (user.getOid() != null && !user.getOid().equals("")){//修改
			sql = "UPDATE `psatmp`.`users` SET `truename` = '"+user.getTruename()+"',"+
			"`loginname` = '"+user.getLoginname()+"',"
				+"`email` = '"+user.getEmail() +"',`mobile` = '"+user.getMobile()+"',`memlevel` = "+user.getMemlevel()+",";
				if (user.getPassword() != null && !user.getPassword().equals(""))
					sql+= "`password` = '"+user.getPassword()+"',";
				if (user.getPortrait() != null)	
					sql += "`portrait` = '"+user.getPortrait() +"',";
				if (user.getQqtoken() != null)	
					sql += "`qqtoken` = '"+user.getQqtoken()+"',";
				if (user.getWechattoken() != null)
					sql += "`wechattoken` = '"+user.getWechattoken()+"',";
				if (user.getWeibotoken() != null)	
					sql += "`weibotoken` = '"+user.getWeibotoken()+"',";
				if (user.getRegphoto() != null)
					sql += "`regphoto` = '"+user.getRegphoto() +"',";
				if (user.getQqtoken() != null)	
					sql += "`backgroundimg` = '"+user.getBackgroundimg()+"',";
				
				sql += "`eidttime` = now() WHERE `oid` = '"+user.getOid() +"';";
				System.out.println(sql);
		}else{//新增
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
		    sql = "INSERT INTO `psatmp`.`users` (`oid`,`truename`,`loginname`,`password`,`sex`,"+
		    "`email`,`mobile`,`memlevel`,`portrait`,`createtime`,"+
			"`eidttime`,`qqtoken`,`wechattoken`,`weibotoken`,`regtype`,"+
		    "`regphoto`,`backgroundimg`) VALUES("+
			" '"+oid+"', '"+user.getTruename()+"', '"+user.getLoginname()+"', '"+user.getPassword()+"', "+user.getSex()+","+
		    " '"+user.getEmail()+"', '"+user.getMobile()+"', "+user.getMemlevel()+", '"+user.getPortrait()+"',now(),"+
			"now(), '"+user.getQqtoken()+"', '"+user.getWechattoken()+"', '"+user.getWeibotoken()+"', "+user.getRegtype()+","+
		    " '"+user.getRegphoto()+"', '"+user.getBackgroundimg()+"');";
		}
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		int isSuccess = pst.executeUpdate();
		pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.users where oid='"+ oid +"'";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
        Statement stmt = conn.createStatement();  
        boolean isSuccess = stmt.execute(sql); 
        stmt.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return isSuccess;
	}
	public UserBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<User> list = new ArrayList<User>();
		Integer pages = (Integer) mSearch.get("pageindex");            //待显示页面
	    int count=0;            //总条数
	    int totalpages=0;        //总页数
	    Integer limit= (Integer) mSearch.get("pagesize");            //每页显示记录条数 
	    //参数预存
	    List<QueryParam> paramList = new ArrayList<QueryParam>();
	    CompomentSql cs = new CompomentSql();
	    if(mSearch.get("regtype") != null )
	    	paramList.add(new QueryParam("regtype",mSearch.get("regtype"),1,false));
	    if(mSearch.get("mobile") != null )
	    	paramList.add(new QueryParam("mobile",mSearch.get("mobile"),0,false));
	    if(mSearch.get("email") != null )
	    	paramList.add(new QueryParam("email",mSearch.get("email"),0,false));
	    if(mSearch.get("truename") != null )
	    	paramList.add(new QueryParam("truename",mSearch.get("truename"),0,true));
	    
	    //计算记录总数的第二种办法：使用mysql的聚集函数count(*)
	    String selectsql  = "select count(*) from psatmp.users where 1=1"; 
	    
	    selectsql = cs.setParamStr(selectsql, paramList);
	    ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(selectsql);
		pst = cs.setPstByList(pst, paramList);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
        	count = rs.getInt(1);//结果为count(*)表，只有一列。这里通过列的下标索引（1）来获取值
		}//显示数据
        rs.close();
	    //由记录总数除以每页记录数得出总页数
	    totalpages = (int)Math.ceil(count/(limit*1.0));
	    if (totalpages == 0){//不用查了，直接返回
	    	pst.close();
	        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
	    	UserBackModel ubm0 = new UserBackModel();
			ubm0.setList(list);
			ubm0.setPageIndex(1);
			ubm0.setPageSize(limit);
			ubm0.setRecCount(0);
			return ubm0;
	    }
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
        selectsql  = "select * from psatmp.users where 1=1 ";
        selectsql = cs.setParamStr(selectsql, paramList);
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    pst = conn.prepareStatement(selectsql);
	    pst = cs.setPstByList(pst, paramList);
	    ResultSet retsult = pst.executeQuery();
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
		retsult.close();
		pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		
		UserBackModel ubm = new UserBackModel();
		ubm.setList(list);
		ubm.setPageIndex(pages);
		ubm.setPageSize(limit);
		ubm.setRecCount(count);
		
		return ubm;
	}

}
