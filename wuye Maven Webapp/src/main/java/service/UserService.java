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
	public User getUserByWXOpenid(String openid) throws SQLException;//通过微信openid取用户对像。
	public Boolean bindUserByWXOpenid(String oid,String openid,String wxName,String wxImg) throws SQLException;//微信与已有用户绑定
	public String getOidByMobile(String mobile) throws SQLException;
	public String getOidByUserPassword(String loginname,String password) throws SQLException;
	public User getUserByMine(HttpServletRequest request) throws SQLException;
	public Boolean ModifyPassword(String oldPw,String newPw,String oid) throws SQLException;
	public Boolean GetModifyPWOldSMS(String mobile,HttpServletRequest request1) throws SQLException;//修改手机号时，旧的验证码
	public Boolean GetModifyPWNewSMS(String mobile,HttpServletRequest request1) throws SQLException;//修改手机号时，新的验证码
	public Boolean FindMyPW(Map mSearch,HttpServletRequest request1) throws SQLException;//找回密码
	public Boolean ModifyMyMobile(Map mSearch,HttpServletRequest request1) throws SQLException;//修改手机号
	
	public User getOne(String oid) throws SQLException;
	public boolean addNew(User user) throws SQLException, ClassNotFoundException;
	public boolean deleteOne(String oid) throws SQLException;
	public UserBackModel getList(Map mSearch) throws SQLException;
}
