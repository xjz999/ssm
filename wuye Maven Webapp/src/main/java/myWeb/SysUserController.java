package myWeb;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.SysUserService;
import service.UserService;

@RestController
@RequestMapping("/SysUser")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	// ¸Ä
	@RequestMapping(value = "/Modify", method = RequestMethod.POST)
	public boolean modifyPw(@RequestBody Map mPw) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return sysUserService.ModifyPassword((String)mPw.get("username"), (String)mPw.get("oldPw"), (String)mPw.get("newPw"));// .toString();
	}
	//µÇ
	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public boolean doLogin(@RequestBody Map mPw,HttpServletRequest hsq) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return sysUserService.SysLogin((String)mPw.get("username"), (String)mPw.get("password"),hsq);// .toString();
	}
}
