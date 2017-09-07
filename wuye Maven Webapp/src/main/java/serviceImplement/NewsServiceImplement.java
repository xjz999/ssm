package serviceImplement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.springframework.stereotype.Component;

import service.NewsService;
import entity.News;
import entity.NewsBackModel;

@Component
public class NewsServiceImplement implements NewsService {
	static String selectsql = null; 
	static ResultSet retsult = null;
	private static Properties config = new Properties(); 

	public  String mysqlUrl = "";//"jdbc:mysql://60.205.212.45:3306/psatmp";
	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
	public  String mysqlUser = "";//"root";
	public  String mysqlPassword = "";//"7DfS4Bcmlcnv";

	public static Connection conn = null;
	public static PreparedStatement pst = null;
	
	NewsServiceImplement(){
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
	//******************* CURD **************************
	public News getOne(String oid){
		News news= new News();
		selectsql  = "select * from psatmp.news where oid='" + oid + "'";
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
				news.setExpDate((retsult.getDate(12)==null)?0:retsult.getDate(12).getTime());
			}//��ʾ����
			retsult.close();
			conn.close();//�ر�����
			pst.close();
			conn = null;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return news;
	}
	public boolean addNew(News news){
		String ctime = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (news.getExpDate() >1000000000){
			ctime = sdf.format(new Date(news.getExpDate())); 
		}else{
			ctime = sdf.format(dt); 
		}
		
		if (news.getOid() != null && news.getOid() != ""){//�޸�
			selectsql = "UPDATE `psatmp`.`news` SET `title` = '"+news.getTitle()+"',`summary` = '"+news.getSummary()+"',`author` = '"+news.getAuthor()+"',"+
			"`editor` = '"+news.getEditor()+"',`pic` = '"+news.getPic()+"',`content` = '"+news.getContent()+"',"+
			"`updatetime` = now(),`istop` = "+news.getIsTop()+",`ctype` = "+news.getCtype()+",`expDate` = '"+ctime+"'"+
			" WHERE `oid` = '"+news.getOid()+"';";
		}else{//����
			UUID uuid = UUID.randomUUID();
		    String oid = uuid.toString();
		    oid = oid.toUpperCase();
			selectsql = "INSERT INTO `psatmp`.`news` (`oid`,`title`,`summary`,`author`,`editor`,"+
		    "`pic`,`content`,`createtime`,`updatetime`,`istop`,"+
			"`ctype`,`expDate`) VALUES" +
		    "('"+oid+"','"+news.getTitle()+"','"+news.getSummary()+"','"+news.getAuthor()+"','"+news.getEditor()+"',"+
		    "'"+news.getPic()+"','"+news.getContent()+"',now(),now(),"+news.getIsTop()+","+
			news.getCtype()+",'"+ctime+"');";
		}
		try {
			Class.forName(mysqlDriverName);//ָ����������
			conn = DriverManager.getConnection(mysqlUrl , mysqlUser, mysqlPassword);//��ȡ����
			pst = conn.prepareStatement(selectsql);//׼��ִ�����
			boolean insertSuccess = pst.execute();
			System.out.println("��ӻ��޸�һ��user�ɹ�");
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
		selectsql = "delete from psatmp.news where oid='"+ oid +"'";
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
	public NewsBackModel getList(Map mSearch) throws SQLException{//regtype,mobile,email,truename
		List<News> list = new ArrayList<News>();
		Integer pages = (Integer) mSearch.get("pageindex");            //����ʾҳ��
	    int count=0;            //������
	    int totalpages=0;        //��ҳ��
	    Integer limit= (Integer) mSearch.get("pagesize");            //ÿҳ��ʾ��¼����    
	    //�����¼�����ĵڶ��ְ취��ʹ��mysql�ľۼ�����count(*)
	    selectsql  = "select count(*) from psatmp.news where 1=1";
	    if (mSearch.get("ctype") != null)
        	selectsql += " and ctype =" + mSearch.get("ctype");
	    if (mSearch.get("title") != null)
	    	selectsql += " and title like '%"+mSearch.get("title")+"%'";
	    
	    
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
        selectsql  = "select * from psatmp.news where 1=1";
        if (mSearch.get("ctype") != null)
        	selectsql += " and ctype =" + mSearch.get("ctype");
        if (mSearch.get("title") != null)
	    	selectsql += " and title like '%"+mSearch.get("title")+"%'";
	    
	    selectsql += " order by istop desc, createtime desc limit " + (pages - 1) * limit + "," + limit;
	    
	    try {
	    	pst = conn.prepareStatement(selectsql);//׼��ִ�����
			retsult = pst.executeQuery();//ִ����䣬�õ������
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
				news.setExpDate((retsult.getDate(12)==null)?0:retsult.getDate(12).getTime());
				list.add(news);
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
		
		NewsBackModel nbm = new NewsBackModel();
		nbm.setList(list);
		nbm.setPageIndex(pages);
		nbm.setPageSize(limit);
		nbm.setRecCount(count);
		
		return nbm;
	}
}
