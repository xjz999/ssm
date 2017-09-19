package serviceImplement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import service.NewsService;
import entity.CompomentSql;
import entity.News;
import entity.NewsBackModel;
import entity.QueryParam;

@Component
public class NewsServiceImplement implements NewsService {
	//******************* CURD **************************
	public News getOne(String oid) throws SQLException{
		News news= new News();
		String sql  = "select * from psatmp.news where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
			System.out.println("abc============");
			news.setOid(rs.getString(1));
			news.setTitle(rs.getString(2));
			news.setSummary(rs.getString(3));
			news.setAuthor(rs.getString(4));
			news.setEditor(rs.getString(5));
			news.setPic(rs.getString(6));
			news.setContent(rs.getString(7));
			news.setCreateTime((rs.getDate(8)==null)?0:rs.getDate(8).getTime());
			news.setUpdateTime((rs.getDate(9)==null)?0:rs.getDate(9).getTime());
			news.setIsTop(((Integer)rs.getInt(10) == null)?0:rs.getInt(10));
			news.setCtype(((Integer)rs.getInt(11) == null)?0:rs.getInt(11));
			//news.setExpDate("");
			news.setChecked(((Integer)rs.getInt(13) == null)?0:rs.getInt(13));
			news.setDeleted(((Integer)rs.getInt(14) == null)?0:rs.getInt(14));
			news.setSource(rs.getString(15));
			news.setEyebrow(rs.getString(16));
			news.setInterpreter(rs.getString(17));
		}//��ʾ����
        rs.close();  
        pst.close();
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return news;
	}
	public boolean addNew(News news) throws SQLException{
		String sql="";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = null;
		List<QueryParam> qpList = new ArrayList<QueryParam>();
		CompomentSql cs = new CompomentSql();
		if (news.getOid() != null && !news.getOid().equals("")){//�޸�
			sql = "UPDATE `psatmp`.`news` SET `updatetime` = now()";
			if (news.getTitle() != null){
				qpList.add(new QueryParam("title",news.getTitle(),0,false));
			}
			if (news.getSummary() != null){
				qpList.add(new QueryParam("summary",news.getSummary(),0,false));
			}
			if (news.getAuthor() != null){
				qpList.add(new QueryParam("author",news.getAuthor(),0,false));
			}
			if (news.getEditor() != null){
				qpList.add(new QueryParam("editor",news.getEditor(),0,false));
			}
			if (news.getPic() != null){
				qpList.add(new QueryParam("pic",news.getPic(),0,false));
			}
			if (news.getContent() != null){
				qpList.add(new QueryParam("content",news.getContent(),0,false));
			}
			if (news.getIsTop() > -1){
				qpList.add(new QueryParam("istop",news.getIsTop(),1,false));
			}
			if (news.getCtype() > -1){
				qpList.add(new QueryParam("ctype",news.getCtype(),1,false));
			}
			if (news.getChecked() > -1){
				qpList.add(new QueryParam("checked",news.getChecked(),1,false));
			}
			if (news.getDeleted() > -1){
				qpList.add(new QueryParam("deleted",news.getDeleted(),1,false));
			}
			if (news.getSource() != null){
				qpList.add(new QueryParam("source",news.getSource(),0,false));
			}
			if (news.getEyebrow() != null){
				qpList.add(new QueryParam("eyebrow",news.getEyebrow(),0,false));
			}
			if (news.getInterpreter() != null){
				qpList.add(new QueryParam("interpreter",news.getInterpreter(),0,false));
			}
			
			sql = cs.setUpdateStr(sql, qpList);
			//����oid����
			sql += " where oid=?";
			pst = conn.prepareStatement(sql);
			pst = cs.setPstByList(pst, qpList);
			//����oid����
			pst.setString(qpList.size()+1, news.getOid());
		}else{//����
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
		    sql = "INSERT INTO `psatmp`.`news` (`oid`,`title`,`summary`,`author`,`editor`,`pic`,`content`,`createtime`,"+
		    "`updatetime`,`istop`,`ctype`,`checked`,`deleted`,`source`,`eyebrow`,`interpreter`) VALUES "+
		    "(?,?,?,?,?,?,?,now(),now(),?,?,0,0,?,?,?);";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, oid);
		    pst.setString(2, news.getTitle());
		    pst.setString(3, news.getSummary());
		    pst.setString(4, news.getAuthor());
		    pst.setString(5, news.getEditor());
		    pst.setString(6, news.getPic());
		    pst.setString(7, news.getContent());
		    
		    pst.setInt(8, news.getIsTop());
		    pst.setInt(9, news.getCtype());
		    pst.setString(10, news.getSource());
		    pst.setString(11, news.getEyebrow());
		    pst.setString(12, news.getInterpreter());
		}
        int isSuccess = pst.executeUpdate();
        pst.close();
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.news where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ����� 
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public NewsBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<News> list = new ArrayList<News>();
		Integer pages = (Integer) mSearch.get("pageindex");            //����ʾҳ��
	    int count=0;            //������
	    int totalpages=0;        //��ҳ��
	    Integer limit= (Integer) mSearch.get("pagesize");            //ÿҳ��ʾ��¼���� 
	    //����Ԥ��
	    List<QueryParam> paramList = new ArrayList<QueryParam>();
	    CompomentSql cs = new CompomentSql();
	    if(mSearch.get("title") != null )
	    	paramList.add(new QueryParam("title",mSearch.get("title"),0,true));
	    if(mSearch.get("ctype") != null )
	    	paramList.add(new QueryParam("ctype",mSearch.get("ctype"),1,false));
	    if(mSearch.get("deleted") != null )
	    	paramList.add(new QueryParam("deleted",mSearch.get("deleted"),1,false));
	    if(mSearch.get("checked") != null )
	    	paramList.add(new QueryParam("checked",mSearch.get("checked"),1,false));
	    
	    //�����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
	    String selectsql  = "select count(*) from psatmp.news where 1=1"; 
	    
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
	    	
	    	pst.close();
	        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
	    	
	    	NewsBackModel cbm0 = new NewsBackModel();
	    	cbm0.setList(list);
	    	cbm0.setPageIndex(1);
	    	cbm0.setPageSize(limit);
	    	cbm0.setRecCount(0);
			return cbm0;
	    }
	    //��ȡ��ҳʱ�������ĵ�ǰҳ�����
//		    String strPage = request.getParameter("pages");
	    //�жϵ�ǰҳ������ĺϷ��Բ�����Ƿ�ҳ�ţ�Ϊ������ʾ��һҳ��С��0����ʾ��һҳ��������ҳ������ʾ���һҳ��   
        if (pages < 1){
            pages = 1;
        }
        if (pages > totalpages){
            pages = totalpages;
        }
	    //��(pages-1)*limit�����ǰҳ���һ����¼����limit��ѯlimit����¼����ó���ǰҳ��ļ�¼
        selectsql  = "select * from psatmp.news where 1=1 ";
        selectsql = cs.setParamStr(selectsql, paramList);
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    pst = conn.prepareStatement(selectsql);
	    pst = cs.setPstByList(pst, paramList);
	    ResultSet retsult = pst.executeQuery();
		while(retsult.next()){
			News news = new News();
			news.setOid(retsult.getString(1));
			news.setTitle(retsult.getString(2));
			news.setSummary(retsult.getString(3));
			news.setAuthor(retsult.getString(4));
			news.setEditor(retsult.getString(5));
			news.setPic(retsult.getString(6));
			news.setContent(retsult.getString(7));
			news.setCreateTime((retsult.getDate(8)==null)?0:retsult.getDate(8).getTime());
			news.setUpdateTime((retsult.getDate(9)==null)?0:retsult.getDate(9).getTime());
			news.setIsTop(((Integer)retsult.getInt(10) == null)?0:retsult.getInt(10));
			news.setCtype(((Integer)retsult.getInt(11) == null)?0:retsult.getInt(11));
			//news.setExpDate("");
			news.setChecked(((Integer)retsult.getInt(13) == null)?0:retsult.getInt(13));
			news.setDeleted(((Integer)retsult.getInt(14) == null)?0:retsult.getInt(14));
			news.setSource(retsult.getString(15));
			news.setEyebrow(retsult.getString(16));
			news.setInterpreter(retsult.getString(17));
			list.add(news);
		}
		retsult.close();
		pst.close();
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		
        NewsBackModel cbm = new NewsBackModel();
        cbm.setList(list);
        cbm.setPageIndex(pages);
        cbm.setPageSize(limit);
        cbm.setRecCount(count);
		
		return cbm;
	}
	
}
