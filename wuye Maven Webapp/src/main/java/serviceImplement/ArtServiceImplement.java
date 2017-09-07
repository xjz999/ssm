package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@Component
public class ArtServiceImplement implements ArtService {
	
	static String selectsql = null; 
	static ResultSet retsult = null;
	private static Properties config = new Properties(); 
	public static PreparedStatement pst = null;

//	public  String url = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
//	public static final String name = "com.mysql.jdbc.Driver";
//	public  String user = "";//"root";
//	public  String password = "";//"7DfS4Bcmlcnv";
	public  String mysqlUrl = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
	public  String mysqlUser = "";//"root";
	public  String mysqlPassword = "";//"7DfS4Bcmlcnv";

	public static Connection conn = null;
	
	ArtServiceImplement(){
		System.out.println("start=================");
		try {
			config.load(ArtServiceImplement.class.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mysqlUrl = config.getProperty("jdbc.url"); 
		mysqlUser = config.getProperty("jdbc.username");
		System.out.println("config=================");
		System.out.println(mysqlUser);
		mysqlPassword = config.getProperty("jdbc.password");
	}
	
	
	public Art selectById(String oid){
		Art art= new Art();
		selectsql  = "select oid,title,useroid,createtime from psatmp.arts";
		try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			retsult = pst.executeQuery();//ִ����䣬�õ������
			while (retsult.next()) {
				System.out.println("abc============");
				System.out.println(retsult);
				art.setOid(retsult.getString(1));
				art.setTitle(retsult.getString(2));
				art.setUseroid(retsult.getString(3));
//				art.setCreatetime(retsult.getString(3));
				
			}//��ʾ����
			retsult.close();
			conn.close();//�ر�����
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return art;
	}
	
	public Art getOne(String oid){
		Art art= new Art();
		selectsql  = "select * from psatmp.arts where oid='" + oid + "'";
		try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			retsult = pst.executeQuery();//ִ����䣬�õ������
			if (retsult.next()) {
				System.out.println("abc============");
				art.setOid(retsult.getString(1));
				art.setTitle(retsult.getString(2));
				art.setUseroid(retsult.getString(3));
				art.setPhotoAddr(retsult.getString(4));
				art.setPhototime(retsult.getString(5));
				art.setStory(retsult.getString(6));
				art.setUrl(retsult.getString(7));
				art.setOrderindex(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
				art.setCreatetime((retsult.getDate(9)==null)?0:retsult.getDate(9).getTime());
				art.setEdittime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
				art.setCtype(((Integer)retsult.getInt(11) == null)?0:retsult.getInt(11));
				art.setAwardtype(((Integer)retsult.getInt(12) == null)?0:retsult.getInt(12));
			}//��ʾ����
			retsult.close();
			conn.close();//�ر�����
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return art;
	}
	public boolean addNew(Art art){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (art.getOid() != null && art.getOid() != ""){//�޸�
			selectsql = "UPDATE `psatmp`.`arts`"+
			" SET `title` = '"+ art.getTitle() +"',`useroid` = '"+ art.getUseroid() +"',`photoaddr` = '"
			+ art.getPhotoaddr() +"',`phototime` = '"+ art.getPhototime() +"',`story` = '"+ art.getStory() +"',"+
			" `url` = '"+ art.getUrl() +"',`orderindex` = "+art.getOrderindex()+
			",`edittime` = now(),`ctype` = "+art.getCtype()+",`awardtype` = " + art.getAwardtype() +
			" WHERE `oid` = '"+ art.getOid() + "';";
		}else{//����
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
			selectsql = "INSERT INTO psatmp.arts (oid,title,useroid,photoaddr,phototime,"+
			"story,url,orderindex,createtime,edittime,"+
			"ctype,awardtype) VALUES ('"+oid+"','"+art.getTitle()+"','"+art.getUseroid()+"','"+art.getPhotoaddr()+"','"+art.getPhototime()+"',"+
			"'"+art.getStory()+"','"+art.getUrl()+"',"+art.getOrderindex()+",now(),now(),"+
			art.getCtype() +","+art.getAwardtype()+")";
		}
		try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
			boolean insertSuccess = pst.execute();
			System.out.println("��ӻ��޸�һ��art�ɹ�");
			System.out.println(insertSuccess);
			conn.close();//�ر�����
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
			return false;
		}
		return true;
	}
	public boolean deleteOne(String oid){
		selectsql = "delete from psatmp.arts where oid='"+ oid +"'";
		try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
			boolean insertSuccess = pst.execute();
			System.out.println("ɾ��һ��art�ɹ�");
			System.out.println(insertSuccess);
			conn.close();//�ر�����
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
			return false;
		}
		return true;
	}
	public ArtBackModel getList(Map mSearch) throws SQLException{//ctype awardtype useroid pagesize pageindex
		List<Art> list = new ArrayList<Art>();
		Integer pages = (Integer) mSearch.get("pageindex");            //����ʾҳ��
	    int count=0;            //������
	    int totalpages=0;        //��ҳ��
	    Integer limit= (Integer) mSearch.get("pagesize");            //ÿҳ��ʾ��¼����    
	    //�����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
	    selectsql  = "select count(*) from psatmp.arts where 1=1";
	    if(mSearch.get("ctype") != null )
	    	selectsql += " and  ctype = " + mSearch.get("ctype");
	    if (mSearch.get("awardtype") != null)
	    	selectsql += " and awardtype="+mSearch.get("awardtype");
	    if (mSearch.get("useroid") != null)
	    	selectsql += " and useroid="+mSearch.get("useroid");
	    
	    try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl, mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
		} catch (Exception e) {
			e.printStackTrace();
			conn=null;
			return null;
		}
	    try {
			retsult = pst.executeQuery();//ִ����䣬�õ������
			if(retsult.next()){
		        count = retsult.getInt(1);//���Ϊcount(*)��ֻ��һ�С�����ͨ���е��±�������1������ȡֵ
		    }    
		}catch(Exception e){
			e.printStackTrace();
			retsult.close();
			conn.close();//�ر�����
			pst.close();
			conn = null;
			return null;
		} 
	    //�ɼ�¼��������ÿҳ��¼���ó���ҳ��
	    totalpages = (int)Math.ceil(count/(limit*1.0));
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
        selectsql  = "select * from psatmp.arts where 1=1";
        if(mSearch.get("ctype") != null )
	    	selectsql += " and  ctype = " + mSearch.get("ctype");
	    if (mSearch.get("awardtype") != null)
	    	selectsql += " and awardtype="+mSearch.get("awardtype");
	    if (mSearch.get("useroid") != null)
	    	selectsql += " and useroid="+mSearch.get("useroid");
	    
	    selectsql += " order by createtime desc limit " + (pages - 1) * limit + "," + limit;
	    
	    try {
	    	pst = conn.prepareStatement(selectsql);//׼��ִ�����
			retsult = pst.executeQuery();//ִ����䣬�õ������
			while(retsult.next()){
				Art art = new Art();
				art.setOid(retsult.getString(1));
				art.setTitle(retsult.getString(2));
				art.setUseroid(retsult.getString(3));
				art.setPhotoAddr(retsult.getString(4));
				art.setPhototime(retsult.getString(5));
				art.setStory(retsult.getString(6));
				art.setUrl(retsult.getString(7));
				art.setOrderindex(((Integer)retsult.getInt(8) == null)?0:retsult.getInt(8));
				art.setCreatetime((retsult.getDate(9)==null)?0:retsult.getDate(9).getTime());
				art.setEdittime((retsult.getDate(10)==null)?0:retsult.getDate(10).getTime());
				art.setCtype(((Integer)retsult.getInt(11) == null)?0:retsult.getInt(11));
				art.setAwardtype(((Integer)retsult.getInt(12) == null)?0:retsult.getInt(12));
				list.add(art);
		    }    
		}catch(Exception e){
			e.printStackTrace();
			retsult.close();
			conn.close();//�ر�����
			pst.close();
			conn = null;
			return null;
		}
	    retsult.close();
		conn.close();//�ر�����
		pst.close();
		conn = null;
		
		ArtBackModel abm = new ArtBackModel();
		abm.setList(list);
		abm.setPageIndex(pages);
		abm.setPageSize(limit);
		abm.setRecCount(count);
		
		return abm;
	}

}
