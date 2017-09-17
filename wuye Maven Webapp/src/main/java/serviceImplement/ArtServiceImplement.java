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
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
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
		}//��ʾ����
        retsult.close();
        stmt.close();
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return art;
	}
	public boolean addNew(Art art) throws SQLException{
		String sql="";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = null;
		List<QueryParam> qpList = new ArrayList<QueryParam>();
		CompomentSql cs = new CompomentSql();
		if (art.getOid() != null && !art.getOid().equals("")){//�޸�
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
			//����oid����
			sql += " where oid=?";
			pst = conn.prepareStatement(sql);
			pst = cs.setPstByList(pst, qpList);
			//����oid����
			pst.setString(qpList.size()+1, art.getOid());
		}else{//����
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
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.competitions where oid='"+ oid +"'";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public ArtBackModel getList(Map mSearch) throws SQLException{//ctype awardtype useroid pagesize pageindex
		List<Art> list = new ArrayList<Art>();
		Integer pages = (Integer) mSearch.get("pageindex");            //����ʾҳ��
	    int count=0;            //������
	    int totalpages=0;        //��ҳ��
	    Integer limit= (Integer) mSearch.get("pagesize");            //ÿҳ��ʾ��¼���� 
	    //����Ԥ��
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
	    
	    //�����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
	    String selectsql  = "select count(*) from psatmp.arts where 1=1"; 
	    
	    selectsql = cs.setParamStr(selectsql, paramList);
	    ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = conn.prepareStatement(selectsql);
		pst = cs.setPstByList(pst, paramList);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
        	count = rs.getInt(1);//���Ϊcount(*)��ֻ��һ�С�����ͨ���е��±�������1������ȡֵ
		}//��ʾ����
        rs.close();
	    //�ɼ�¼��������ÿҳ��¼���ó���ҳ��
	    totalpages = (int)Math.ceil(count/(limit*1.0));
	    if (totalpages == 0){//���ò��ˣ�ֱ�ӷ���
	    	ArtBackModel cbm0 = new ArtBackModel();
	    	cbm0.setList(list);
	    	cbm0.setPageIndex(1);
	    	cbm0.setPageSize(limit);
	    	cbm0.setRecCount(0);
			return cbm0;
	    }
	    //��ȡ��ҳʱ�������ĵ�ǰҳ�����
//	    String strPage = request.getParameter("pages");
	    //�жϵ�ǰҳ������ĺϷ��Բ�����Ƿ�ҳ�ţ�Ϊ������ʾ��һҳ��С��0����ʾ��һҳ��������ҳ������ʾ���һҳ��   
        if (pages < 1){
            pages = 1;
        }
        if (pages > totalpages){
            pages = totalpages;
        }
	    //��(pages-1)*limit�����ǰҳ���һ����¼����limit��ѯlimit����¼����ó���ǰҳ��ļ�¼
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
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		
        ArtBackModel cbm = new ArtBackModel();
        cbm.setList(list);
        cbm.setPageIndex(pages);
        cbm.setPageSize(limit);
        cbm.setRecCount(count);
		
		return cbm;
	}

}
