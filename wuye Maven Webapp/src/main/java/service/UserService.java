package service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import entity.User;
import entity.UserBackModel;

public interface UserService {
	public User selectById(String oid);
	public Boolean validEmail(String email) throws SQLException;//��֤������û���ظ�
	public Boolean validMobile(String mobile) throws SQLException;//��֤�ֻ���û���ظ�
	public Boolean validSMS(String sms,HttpServletRequest request1);//��֤�������Ƿ���ȷ
	public Boolean GetSMS(String mobile,HttpServletRequest request1);//�����ֻ���֤�룬
	public Boolean saveNewUser(HttpServletRequest request) throws SQLException; //�û�ע��
	public User validInfo(String info,String pw) throws SQLException; //�û���֤
	public User getUserByWXOpenid(String openid) throws SQLException;//ͨ��΢��openidȡ�û�����
	public Boolean bindUserByWXOpenid(String oid,String openid,String wxName,String wxImg) throws SQLException;//΢���������û���
	public String getOidByMobile(String mobile) throws SQLException;
	public String getOidByUserPassword(String loginname,String password) throws SQLException;
	
	public User getOne(String oid) throws SQLException;
	public boolean addNew(User user) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public UserBackModel getList(Map mSearch) throws SQLException;
}
