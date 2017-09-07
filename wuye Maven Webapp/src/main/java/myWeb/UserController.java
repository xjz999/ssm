package myWeb;

import myWeb.Greeting;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import entity.Art;
import entity.ArtBackModel;
import entity.User;
import entity.UserBackModel;
import service.UserService;

@RestController
@RequestMapping("/Users")
public class UserController {
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
	@Autowired
	private UserService userService;
	
    
//	@RequestMapping("hello")
//	public void helloworld(HttpServletRequest request, HttpServletResponse response ) throws IOException{
//		response.getWriter().write("world");
//	}
	@RequestMapping(value = "/hello",method=RequestMethod.POST)
	public User greetingByPost(@RequestParam(value="userid", defaultValue="1") String userid) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
		return userService.selectById(userid);//.toString();
    }
	
	@RequestMapping(value = "/hello/{userid}",method=RequestMethod.GET)
	public User greetingByGet(@PathVariable("userid") String oid) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
		return userService.selectById(oid);//.toString();
    }
	
	@RequestMapping(value = "/persons", method = RequestMethod.PUT)  
	public String handle(@RequestBody String body) throws IOException {  
	  //writer.write(body); 
		return body;
	}
	
	@RequestMapping(value = "/persons", method = RequestMethod.DELETE)  
	public String handle1(@RequestParam(value="id", defaultValue="101") String id) throws IOException {  
	  //writer.write(body); 
		return id;
	}
	
	//@PathVariable("id") String id,@PathVariable("menuId") String menuId
	@RequestMapping(value="/persons/{classification}/{id}")
	public String getPersonsByClassAndId(@PathVariable("classification") String classification,@PathVariable("id") String id){
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("result",classification + "_"+id);
		return classification+"_"+id;
	}
	
	/********************注册业务**********************/
	@RequestMapping(value = "/ValidEmail",method=RequestMethod.POST)
	public String validEmail(@RequestParam(value="email", defaultValue="1") String email){
		if (userService.validEmail(email)){
			return "{\"valid\":true}";
		}else{
			return "{\"valid\":false}";
		}
	}
	@RequestMapping(value = "/ValidMobile",method=RequestMethod.POST)
	public String validMobile(@RequestParam(value="mobile", defaultValue="1") String mobile){
		if (userService.validMobile(mobile)){
			return "{\"valid\":true}";
		}else{
			return "{\"valid\":false}";
		}
	}
	@RequestMapping(value = "/ValidSMS",method=RequestMethod.POST)
	public String validSMS(@RequestParam(value="mobileValidCode", defaultValue="1") String sms,HttpServletRequest request1){
		if (userService.validSMS(sms,request1)){
			return "{\"valid\":true}";
		}else{
			return "{\"valid\":false}";
		}
	}
	@RequestMapping(value = "/GetSMS",method=RequestMethod.POST)
	public String GetSMS(@RequestParam(value="mobileVal", defaultValue="1") String mobile,HttpServletRequest request1){
		if (userService.GetSMS(mobile,request1)){
			return "{\"code\":1}";
		}else{
			return "{\"code\":0}";
		}
	}
	@RequestMapping(value = "/AddNew",method=RequestMethod.POST)
	public String saveNewUser(HttpServletRequest request,HttpServletResponse resp) throws IOException{
		if (userService.saveNewUser(request)){
			resp.sendRedirect("/register.html#code=100");
		}else{
			resp.sendRedirect("/register.html#code=101");
		}
		return "";
	}
	@RequestMapping(value = "/ValidInfo",method=RequestMethod.POST)
	public String validInfo(@RequestParam(value="logininfo", defaultValue="1") String logininfo,@RequestParam(value="password", 
		defaultValue="1") String password,HttpServletRequest request){
		User user = userService.validInfo(logininfo, password);
		System.out.println(user.getTruename());
		if (user.getLoginname() == null || user.getLoginname().equals("")){
			return "{\"code\":0}";
		}
		//设置session
		request.getSession().setAttribute("truename",user.getTruename());
		request.getSession().setAttribute("loginname",user.getLoginname());
		request.getSession().setAttribute("memlevel",user.getMemlevel());
		//返回
		return "{\"code\":1,\"truename\":\""+user.getTruename()+"\",\"loginname\":\""+user.getLoginname()+"\"}";
	}
	
	//******************************* CURD ****************************/
	// 查
	@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
	public User getOne(@PathVariable("oid") String oid) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return userService.selectById(oid);// .toString();
	}

	// 增 & 改
	@RequestMapping(value = "/AddNewByJson", method = RequestMethod.POST)
	public String addNew(@RequestBody User user) throws ClassNotFoundException, SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		boolean success = false;
		try{
			success = userService.addNew(user);
		}catch(Exception e){
			return "{\"code\":0}";
		}
		return (success)?"{\"code\":1}":"{\"code\":0}";
	}

	// 删
	@RequestMapping(value = "/DeleteOne/{oid}", method = RequestMethod.GET)
	public boolean deleteOne(@PathVariable("oid") String oid) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return userService.deleteOne(oid);// .toString();
	}

	// 列表及翻页
	@RequestMapping(value = "/List", method = RequestMethod.POST)
	public UserBackModel getList(@RequestBody Map mSearch) throws SQLException {//regtype email mobile truename pagesize pageindex
//		List<Art> list = artService.getList(mSearch);
		return userService.getList(mSearch);// .toString();
	}
}
