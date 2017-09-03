package service;

import javax.servlet.http.HttpServletRequest;

import entity.User;

public interface UserService {
	public User selectById(String oid);
	public Boolean validEmail(String email);//��֤������û���ظ�
	public Boolean validMobile(String mobile);//��֤�ֻ���û���ظ�
	public Boolean validSMS(String sms,HttpServletRequest request1);//��֤�������Ƿ���ȷ
	public Boolean GetSMS(String mobile,HttpServletRequest request1);//�����ֻ���֤�룬
	public Boolean saveNewUser(HttpServletRequest request); //�û�ע��
	public User validInfo(String info,String pw); //�û���֤
}
