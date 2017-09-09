package service;

import javax.servlet.http.HttpServletRequest;

public interface SysUserService {
	boolean ModifyPassword(String username,String oldPw,String newPw);
	boolean SysLogin(String username,String password,HttpServletRequest hsq);
}
