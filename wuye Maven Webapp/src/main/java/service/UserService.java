package service;

import javax.servlet.http.HttpServletRequest;

import entity.User;

public interface UserService {
	public User selectById(String oid);
	public Boolean validEmail(String email);//验证邮箱有没有重复
	public Boolean validMobile(String mobile);//验证手机有没有重复
	public Boolean validSMS(String sms,HttpServletRequest request1);//验证短信码是否正确
	public Boolean GetSMS(String mobile,HttpServletRequest request1);//发送手机验证码，
	public Boolean saveNewUser(HttpServletRequest request); //用户注册
	public User validInfo(String info,String pw); //用户验证
}
