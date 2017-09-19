package serviceImplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import service.CompetitionService;
import entity.CompomentSql;
import entity.QueryParam;
import entity.Competition;
import entity.CompetitionBackModel;

@Component
public class CompetitionServiceImplement implements CompetitionService {
	//******************* CURD **************************
	public Competition getOne(String oid) throws SQLException{
		Competition competi= new Competition();
		String sql  = "select * from psatmp.competitions where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
			System.out.println("abc============");
			competi.setOid(rs.getString(1));
			competi.setTitle(rs.getString(2));
			competi.setPics(rs.getString(3));
			competi.setTitleen(rs.getString(4));
			competi.setValidecode(rs.getString(5));
			competi.setSource(rs.getString(6));
			competi.setExpdate(rs.getString(7));
			competi.setF1(rs.getString(8));
			competi.setF2(rs.getString(9));
			competi.setF3(rs.getString(10));
			competi.setF4(rs.getString(11));
			competi.setF5(rs.getString(12));
			competi.setFf(rs.getString(13));
			competi.setCreatetime((rs.getDate(14)==null)?0:rs.getDate(14).getTime());
			competi.setEdittime((rs.getDate(15)==null)?0:rs.getDate(15).getTime());
			competi.setChecked(((Integer)rs.getInt(16) == null)?0:rs.getInt(16));
			competi.setDeleted(((Integer)rs.getInt(17) == null)?0:rs.getInt(17));
			
		}//显示数据
        rs.close();  
        pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return competi;
	}
	public boolean addNew(Competition competi) throws SQLException{
		String sql="";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = null;
		List<QueryParam> qpList = new ArrayList<QueryParam>();
		CompomentSql cs = new CompomentSql();
		if (competi.getOid() != null && !competi.getOid().equals("")){//修改
			sql = "UPDATE `psatmp`.`competitions` SET `edittime` = now()";
			if (competi.getTitle() != null){
				qpList.add(new QueryParam("title",competi.getTitle(),0,false));
			}
			if (competi.getPics() != null){
				qpList.add(new QueryParam("pics",competi.getPics(),0,false));
			}
			if (competi.getTitleen() != null){
				qpList.add(new QueryParam("titleen",competi.getTitleen(),0,false));
			}
			if (competi.getValidecode() != null){
				qpList.add(new QueryParam("validecode",competi.getValidecode(),0,false));
			}
			if (competi.getSource() != null){
				qpList.add(new QueryParam("source",competi.getSource(),0,false));
			}
			if (competi.getExpdate() != null){
				qpList.add(new QueryParam("expdate",competi.getExpdate(),0,false));
			}
			if (competi.getF1() != null){
				qpList.add(new QueryParam("f1",competi.getF1(),0,false));
			}
			if (competi.getF2() != null){
				qpList.add(new QueryParam("f2",competi.getF2(),0,false));
			}
			if (competi.getF3() != null){
				qpList.add(new QueryParam("f3",competi.getF3(),0,false));
			}
			if (competi.getF4() != null){
				qpList.add(new QueryParam("f4",competi.getF4(),0,false));
			}
			if (competi.getF5() != null){
				qpList.add(new QueryParam("f5",competi.getF5(),0,false));
			}
			if (competi.getFf() != null){
				qpList.add(new QueryParam("ff",competi.getFf(),0,false));
			}
			if (competi.getChecked() > -1){
				qpList.add(new QueryParam("checked",competi.getChecked(),1,false));
			}
			if (competi.getDeleted() > -1){
				qpList.add(new QueryParam("deleted",competi.getDeleted(),1,false));
			}
			sql = cs.setUpdateStr(sql, qpList);
			//加上oid条件
			sql += " where oid=?";
			pst = conn.prepareStatement(sql);
			pst = cs.setPstByList(pst, qpList);
			//加上oid条件
			pst.setString(qpList.size()+1, competi.getOid());
		}else{//新增
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
		    sql = "INSERT INTO `psatmp`.`competitions` (`oid`,`title`,`pics`,`titleen`,`validecode`,"+
		    	"`source`,`expdate`,`f1`,`f2`,`f3`,`f4`,`f5`,`ff`,`createtime`,`edittime`,`checked`,"+
		    	"`deleted`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),0,0);";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, oid);
		    pst.setString(2, competi.getTitle());
		    pst.setString(3, competi.getPics());
		    pst.setString(4, competi.getTitleen());
		    pst.setString(5, competi.getValidecode());
		    pst.setString(6, competi.getSource());
		    pst.setString(7, competi.getExpdate());
		    pst.setString(8, competi.getF1());
		    pst.setString(9, competi.getF2());
		    pst.setString(10, competi.getF3());
		    pst.setString(11, competi.getF4());
		    pst.setString(12, competi.getF5());
		    pst.setString(13, competi.getFf());
		}
        int isSuccess = pst.executeUpdate();
        pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.competitions where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接 
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		return (isSuccess > 0);
	}
	public CompetitionBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<Competition> list = new ArrayList<Competition>();
		Integer pages = (Integer) mSearch.get("pageindex");            //待显示页面
	    int count=0;            //总条数
	    int totalpages=0;        //总页数
	    Integer limit= (Integer) mSearch.get("pagesize");            //每页显示记录条数 
	    //参数预存
	    List<QueryParam> paramList = new ArrayList<QueryParam>();
	    CompomentSql cs = new CompomentSql();
	    if(mSearch.get("title") != null )
	    	paramList.add(new QueryParam("title",mSearch.get("title"),0,true));
	    if(mSearch.get("deleted") != null )
	    	paramList.add(new QueryParam("deleted",mSearch.get("deleted"),1,false));
	    if(mSearch.get("checked") != null )
	    	paramList.add(new QueryParam("checked",mSearch.get("checked"),1,false));
	    
	    //计算记录总数的第二种办法：使用mysql的聚集函数count(*)
	    String selectsql  = "select count(*) from psatmp.competitions where 1=1"; 
	    
	    selectsql = cs.setParamStr(selectsql, paramList);
	    ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//单例模式创建连接池对象
		Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接  
		PreparedStatement pst = conn.prepareStatement(selectsql);
		pst = cs.setPstByList(pst, paramList);

        ResultSet res = pst.executeQuery();
        if (res.next()) {
        	count = res.getInt(1);//结果为count(*)表，只有一列。这里通过列的下标索引（1）来获取值
		}//显示数据
        res.close();
	    //由记录总数除以每页记录数得出总页数
	    totalpages = (int)Math.ceil(count/(limit*1.0));
	    if (totalpages == 0){//不用查了，直接返回
	    	pst.close();
	        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
	        
	    	CompetitionBackModel cbm0 = new CompetitionBackModel();
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
        selectsql  = "select * from psatmp.competitions where 1=1 ";
        selectsql = cs.setParamStr(selectsql, paramList);
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    pst = conn.prepareStatement(selectsql);
	    pst = cs.setPstByList(pst, paramList);
	    ResultSet rs = pst.executeQuery();
		while(rs.next()){
			Competition competi = new Competition();
			competi.setOid(rs.getString(1));
			competi.setTitle(rs.getString(2));
			competi.setPics(rs.getString(3));
			competi.setTitleen(rs.getString(4));
			competi.setValidecode(rs.getString(5));
			competi.setSource(rs.getString(6));
			competi.setExpdate(rs.getString(7));
			competi.setF1(rs.getString(8));
			competi.setF2(rs.getString(9));
			competi.setF3(rs.getString(10));
			competi.setF4(rs.getString(11));
			competi.setF5(rs.getString(12));
			competi.setFf(rs.getString(13));
			competi.setCreatetime((rs.getDate(14)==null)?0:rs.getDate(14).getTime());
			competi.setEdittime((rs.getDate(15)==null)?0:rs.getDate(15).getTime());
			competi.setChecked(((Integer)rs.getInt(16) == null)?0:rs.getInt(16));
			competi.setDeleted(((Integer)rs.getInt(17) == null)?0:rs.getInt(17));
			list.add(competi);
		}
		rs.close();
		pst.close();
        connPool.returnConnection(conn);// 连接使用完后释放连接到连接池 
		
        CompetitionBackModel cbm = new CompetitionBackModel();
        cbm.setList(list);
        cbm.setPageIndex(pages);
        cbm.setPageSize(limit);
        cbm.setRecCount(count);
		
		return cbm;
	}
}
