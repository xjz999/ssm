package service;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import entity.User;
import entity.UserBackModel;

public interface UserService {
	public User selectById(String oid);
	public Boolean validEmail(String email) throws SQLException;//验证邮箱有没有重复
	public Boolean validMobile(String mobile) throws SQLException;//验证手机有没有重复
	public Boolean validSMS(String sms,HttpServletRequest request1);//验证短信码是否正确
	public Boolean GetSMS(String mobile,HttpServletRequest request1);//发送手机验证码，
	public Boolean saveNewUser(HttpServletRequest request) throws SQLException; //用户注册
	public User validInfo(String info,String pw) throws SQLException; //用户验证
	
	public User getOne(String oid) throws SQLException;
	public boolean addNew(User user) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public UserBackModel getList(Map mSearch) throws SQLException;
}
