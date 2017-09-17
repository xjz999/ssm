package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.springframework.stereotype.Component;

import service.ArtService;
import entity.Art;
import entity.ArtBackModel;
import entity.Competition;
import entity.CompetitionBackModel;
import entity.CompomentSql;
import entity.NewsBackModel;
import entity.QueryParam;

@Component
public class ArtServiceImplement implements ArtService {

	public Art getOne(String oid) throws SQLException{
		Art art= new Art();
		String sql  = "select * from psatmp.arts where oid='" + oid + "'";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
        Statement stmt = conn.createStatement();  
        ResultSet retsult = stmt.executeQuery(sql);
        if (retsult.next()) {
			System.out.println("abc============");
			art.setOid(retsult.getString(1));
			art.setTitle(retsult.getString(2));
			art.setUseroid(retsult.getString(3));
			art.setPhotoaddr(retsult.getString(4));
			art.setPhototime(retsult.getString(5));
			art.setStory(retsult.getString(6));
			art.setUrl(retsult.getString(7));
			art.setOrderindex(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
			art.setCreatetime((retsult.getDate(9)==null)?0:retsult.getDate(9).getTime());
			art.setEdittime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
			art.setCtype(((Integer)retsult.getInt(11) == null)?0:retsult.getInt(11));
			art.setAwardtype(((Integer)retsult.getInt(12) == null)?0:retsult.getInt(12));
			art.setAligntype(((Integer)retsult.getInt(13) == null)?0:retsult.getInt(13));
			art.setChecked(((Integer)retsult.getInt(14) == null)?0:retsult.getInt(14));
			art.setDeleted(((Integer)retsult.getInt(15) == null)?0:retsult.getInt(15));
			art.setAuthor(retsult.getString(16));
		}//显示数据
        retsult.close();
        stmt.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return art;
	}
	public boolean addNew(Art art) throws SQLException{
		String sql="";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = null;
		List<QueryParam> qpList = new ArrayList<QueryParam>();
		CompomentSql cs = new CompomentSql();
		if (art.getOid() != null && !art.getOid().equals("")){//修改
			sql = "UPDATE `psatmp`.`arts` SET `edittime` = now()";
			if (art.getTitle() != null && !art.getTitle().equals("")){
				qpList.add(new QueryParam("title",art.getTitle(),0,false));
			}
			if (art.getPhotoaddr() != null && !art.getPhotoaddr().equals("")){
				qpList.add(new QueryParam("photoaddr",art.getPhotoaddr(),0,false));
			}
			if (art.getPhototime() != null && !art.getPhototime().equals("")){
				qpList.add(new QueryParam("phototime",art.getPhototime(),0,false));
			}
			if (art.getStory() != null && !art.getStory().equals("")){
				qpList.add(new QueryParam("story",art.getStory(),0,false));
			}
			if (art.getUrl() != null && !art.getUrl().equals("")){
				qpList.add(new QueryParam("url",art.getUrl(),0,false));
			}
			if (art.getOrderindex() > -1){
				qpList.add(new QueryParam("orderindex",art.getOrderindex(),1,false));
			}
			if (art.getCtype() > -1){
				qpList.add(new QueryParam("ctype",art.getCtype(),1,false));
			}
			if (art.getAwardtype() > -1){
				qpList.add(new QueryParam("awardtype",art.getAwardtype(),1,false));
			}
			if (art.getAligntype() > -1){
				qpList.add(new QueryParam("aligntype",art.getAligntype(),1,false));
			}
			if (art.getChecked() > -1){
				qpList.add(new QueryParam("checked",art.getChecked(),1,false));
			}
			if (art.getDeleted() > -1){
				qpList.add(new QueryParam("deleted",art.getDeleted(),1,false));
			}
			if (art.getAuthor() != null && !art.getAuthor().equals("")){
				qpList.add(new QueryParam("author",art.getAuthor(),0,false));
			}
			sql = cs.setUpdateStr(sql, qpList);
			//加上oid条件
			sql += " where oid=?";
			pst = conn.prepareStatement(sql);
			pst = cs.setPstByList(pst, qpList);
			//加上oid条件
			pst.setString(qpList.size()+1, art.getOid());
		}else{//新增
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
		    sql = "INSERT INTO `psatmp`.`arts`(`oid`,`title`,`useroid`,`photoaddr`,`phototime`,`story`,`url`,`orderindex`,`createtime`,`edittime`,`ctype`,`awardtype`,`aligntype`,`checked`,`deleted`,`author`)"+
		    " VALUES (?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?);";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, oid);
		    pst.setString(2, art.getTitle());
		    pst.setString(3, art.getUseroid());
		    pst.setString(4, art.getPhotoaddr());
		    pst.setString(5, art.getPhototime());
		    pst.setString(6, art.getStory());
		    pst.setString(7, art.getUrl());
		    pst.setInt(8, art.getOrderindex());
		    
		    pst.setInt(9, art.getCtype());
		    pst.setInt(10, art.getAwardtype());
		    pst.setInt(11, art.getAligntype());
		    pst.setInt(12, art.getChecked());
		    pst.setInt(13, art.getDeleted());
		    pst.setString(14, art.getAuthor());
		}
        int isSuccess = pst.executeUpdate();
        pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.competitions where oid='"+ oid +"'";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess > 0);
	}
	public ArtBackModel getList(Map mSearch) throws SQLException{//ctype awardtype useroid pagesize pageindex
		List<Art> list = new ArrayList<Art>();
		Integer pages = (Integer) mSearch.get("pageindex");            //待显示页面
	    int count=0;            //总条数
	    int totalpages=0;        //总页数
	    Integer limit= (Integer) mSearch.get("pagesize");            //每页显示记录条数 
	    //参数预存
	    List<QueryParam> paramList = new ArrayList<QueryParam>();
	    CompomentSql cs = new CompomentSql();
	    if(mSearch.get("title") != null )
	    	paramList.add(new QueryParam("title",mSearch.get("title"),0,true));
	    if(mSearch.get("ctype") != null ){
	    	paramList.add(new QueryParam("ctype",mSearch.get("ctype"),1,true));
	    }
	    if (mSearch.get("awardtype") != null){
	    	paramList.add(new QueryParam("awardtype",mSearch.get("awardtype"),1,true));
	    }
	    
	    //计算记录总数的第二种办法：使用mysql的聚集函数count(*)
	    String selectsql  = "select count(*) from psatmp.arts where 1=1"; 
	    
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
	    	ArtBackModel cbm0 = new ArtBackModel();
	    	cbm0.setList(list);
	    	cbm0.setPageIndex(1);
	    	cbm0.setPageSize(limit);
	    	cbm0.setRecCount(0);
			return cbm0;
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
        selectsql  = "select * from psatmp.arts where 1=1 ";
        selectsql = cs.setParamStr(selectsql, paramList);
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    pst = conn.prepareStatement(selectsql);
	    pst = cs.setPstByList(pst, paramList);
	    ResultSet retsult = pst.executeQuery();
		while(retsult.next()){
			Art art = new Art();
			art.setOid(retsult.getString(1));
			art.setTitle(retsult.getString(2));
			art.setUseroid(retsult.getString(3));
			art.setPhotoaddr(retsult.getString(4));
			art.setPhototime(retsult.getString(5));
			art.setStory(retsult.getString(6));
			art.setUrl(retsult.getString(7));
			art.setOrderindex(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
			art.setCreatetime((retsult.getDate(9)==null)?0:retsult.getDate(9).getTime());
			art.setEdittime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
			art.setCtype(((Integer)retsult.getInt(11) == null)?0:retsult.getInt(11));
			art.setAwardtype(((Integer)retsult.getInt(12) == null)?0:retsult.getInt(12));
			art.setAligntype(((Integer)retsult.getInt(13) == null)?0:retsult.getInt(13));
			art.setChecked(((Integer)retsult.getInt(14) == null)?0:retsult.getInt(14));
			art.setDeleted(((Integer)retsult.getInt(15) == null)?0:retsult.getInt(15));
			art.setAuthor(retsult.getString(16));
			list.add(art);
		}
		retsult.close();
		pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		
        ArtBackModel cbm = new ArtBackModel();
        cbm.setList(list);
        cbm.setPageIndex(pages);
        cbm.setPageSize(limit);
        cbm.setRecCount(count);
		
		return cbm;
	}

}
