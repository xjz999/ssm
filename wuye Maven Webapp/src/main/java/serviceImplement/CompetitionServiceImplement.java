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
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
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
			
		}//��ʾ����
        rs.close();  
        pst.close();
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return competi;
	}
	public boolean addNew(Competition competi) throws SQLException{
		String sql="";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = null;
		List<QueryParam> qpList = new ArrayList<QueryParam>();
		CompomentSql cs = new CompomentSql();
		if (competi.getOid() != null && !competi.getOid().equals("")){//�޸�
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
			//����oid����
			sql += " where oid=?";
			pst = conn.prepareStatement(sql);
			pst = cs.setPstByList(pst, qpList);
			//����oid����
			pst.setString(qpList.size()+1, competi.getOid());
		}else{//����
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
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public boolean deleteOne(String oid) throws SQLException{
		String sql = "delete from psatmp.competitions where oid=?";
		ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ����� 
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, oid);
        int isSuccess = pst.executeUpdate();
        pst.close();  
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		return (isSuccess > 0);
	}
	public CompetitionBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<Competition> list = new ArrayList<Competition>();
		Integer pages = (Integer) mSearch.get("pageindex");            //����ʾҳ��
	    int count=0;            //������
	    int totalpages=0;        //��ҳ��
	    Integer limit= (Integer) mSearch.get("pagesize");            //ÿҳ��ʾ��¼���� 
	    //����Ԥ��
	    List<QueryParam> paramList = new ArrayList<QueryParam>();
	    CompomentSql cs = new CompomentSql();
	    if(mSearch.get("title") != null )
	    	paramList.add(new QueryParam("title",mSearch.get("title"),0,true));
	    if(mSearch.get("deleted") != null )
	    	paramList.add(new QueryParam("deleted",mSearch.get("deleted"),1,false));
	    if(mSearch.get("checked") != null )
	    	paramList.add(new QueryParam("checked",mSearch.get("checked"),1,false));
	    
	    //�����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
	    String selectsql  = "select count(*) from psatmp.competitions where 1=1"; 
	    
	    selectsql = cs.setParamStr(selectsql, paramList);
	    ConnectionPool  connPool=ConnectionPoolUtils.GetPoolInstance();//����ģʽ�������ӳض���
		Connection conn = connPool.getConnection(); // �����ӿ��л�ȡһ�����õ�����  
		PreparedStatement pst = conn.prepareStatement(selectsql);
		pst = cs.setPstByList(pst, paramList);

        ResultSet res = pst.executeQuery();
        if (res.next()) {
        	count = res.getInt(1);//���Ϊcount(*)��ֻ��һ�С�����ͨ���е��±�������1������ȡֵ
		}//��ʾ����
        res.close();
	    //�ɼ�¼��������ÿҳ��¼���ó���ҳ��
	    totalpages = (int)Math.ceil(count/(limit*1.0));
	    if (totalpages == 0){//���ò��ˣ�ֱ�ӷ���
	    	pst.close();
	        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
	        
	    	CompetitionBackModel cbm0 = new CompetitionBackModel();
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
        connPool.returnConnection(conn);// ����ʹ������ͷ����ӵ����ӳ� 
		
        CompetitionBackModel cbm = new CompetitionBackModel();
        cbm.setList(list);
        cbm.setPageIndex(pages);
        cbm.setPageSize(limit);
        cbm.setRecCount(count);
		
		return cbm;
	}
}
