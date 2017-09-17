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
	
	public User getOne(String oid) throws SQLException;
	public boolean addNew(User user) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public UserBackModel getList(Map mSearch) throws SQLException;
}
