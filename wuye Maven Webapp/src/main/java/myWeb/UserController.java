package myWeb;
import common.RequstUrl;
import myWeb.Greeting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	//code=011dI2br1Eu3rq0cDJ8r1Vmlbr1dI2bQ&state=yyycbbo98123456
	@RequestMapping(value="/wxReqBack",method=RequestMethod.GET)
	public void wxReqBack(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = request.getParameter("code");
        String state=request.getParameter("state");
		System.out.println(code);
		System.out.println(state);
		//�����û�,ȡ��openid���û�����ͷ���ַ
			//1,ͨ��code��ȡaccess_token
		JsonNode json1 = common.RequstUrl.doGet("https://api.weixin.qq.com", 
				"/sns/oauth2/access_token?appid=wx4b1cd561744729ea&secret=11111111111111&code="+code+
				"&grant_type=authorization_code"
				, "", "");
		JsonNode access_token = json1.path("access_token");
		JsonNode openid = json1.path("openid");
		
		JsonNode json2 = common.RequstUrl.doGet("https://api.weixin.qq.com", 
				"/sns/userinfo?access_token="+access_token+"&openid="+openid
				, "", "");
		JsonNode nickname = json2.path("nickname");
		JsonNode headimgurl = json2.path("headimgurl");
		//��ѯ���ݿ�����Ѿ��󶨣�ע���ѯʱ��ֻ��ͨ��openidȷ��Ψһ��
		
			//���ж��Ƿ��Ѿ��˹���ˣ�����Ѿ����ͨ��������cookies����������Ա���ġ�
			//���û�����ͨ������������ҳ�棬��ʾ������˵���ʾ
		//���û�а󶨣���������ҳ������󶨡�
		
//		Cookie cookie = new Cookie("name",URLEncoder.encode("�����", "utf-8"));//������cookie
//        cookie.setMaxAge(24 * 60 * 60);// ���ô���ʱ��Ϊ5����
//        cookie.setPath("/");//����������
//        response.addCookie(cookie);//��cookie��ӵ�response��cookie�����з��ظ��ͻ���
//        
//        Cookie cookie1 = new Cookie("loginname",code);//������cookie
//        cookie1.setMaxAge(24 * 60 * 60);// ���ô���ʱ��Ϊ5����
//        cookie1.setPath("/");//����������
//        response.addCookie(cookie1);//��cookie��ӵ�response��cookie�����з��ظ��ͻ���
        
		response.sendRedirect("/my/index.html");
	}
	
	@RequestMapping(value = "/GetValidecode",method=RequestMethod.GET)
	public void imageRand(HttpServletResponse response,HttpServletRequest request) throws FileNotFoundException, IOException{
		try {
			response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("image/jpeg");
	          
	        //��������ִ�  
	        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
	        //����Ựsession  
	        HttpSession session = request.getSession(true);  
	        session.setAttribute("_code", verifyCode.toLowerCase());  
	        //����ͼƬ 
	        int w = 146, h = 33;
	        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode,response);  
		} catch (Exception e) {
			System.out.println("��֤�����ɴ���");
		}
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
	
	/********************ע��ҵ��
	 * @throws SQLException **********************/
	@RequestMapping(value = "/ValidEmail",method=RequestMethod.POST)
	public String validEmail(@RequestParam(value="email", defaultValue="1") String email) throws SQLException{
		if (userService.validEmail(email)){
			return "{\"valid\":true}";
		}else{
			return "{\"valid\":false}";
		}
	}
	@RequestMapping(value = "/ValidMobile",method=RequestMethod.POST)
	public String validMobile(@RequestParam(value="mobile", defaultValue="1") String mobile) throws SQLException{
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
	public String saveNewUser(HttpServletRequest request,HttpServletResponse resp) throws IOException, SQLException{
		if (userService.saveNewUser(request)){
			resp.sendRedirect("/register.html#code=100");
		}else{
			resp.sendRedirect("/register.html#code=101");
		}
		return "";
	}
	@RequestMapping(value = "/ValidInfo",method=RequestMethod.POST)
	public String validInfo(@RequestParam(value="logininfo", defaultValue="1") String logininfo,@RequestParam(value="password", 
		defaultValue="1") String password,HttpServletRequest request) throws SQLException{
		User user = userService.validInfo(logininfo, password);
		System.out.println(user.getTruename());
		if (user.getLoginname() == null || user.getLoginname().equals("")){
			return "{\"code\":0}";
		}
		//����session
		request.getSession().setAttribute("truename",user.getTruename());
		request.getSession().setAttribute("loginname",user.getLoginname());
		request.getSession().setAttribute("memlevel",user.getMemlevel());
		//����
		return "{\"code\":1,\"truename\":\""+user.getTruename()+"\",\"loginname\":\""+user.getLoginname()+"\"}";
	}
	
	//******************************* CURD ****************************/
	// ��
	@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
	public User getOne(@PathVariable("oid") String oid) throws SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return userService.getOne(oid);// .toString();
	}

	// �� & ��
	@RequestMapping(value = "/AddNewByJson", method = RequestMethod.POST)
	public String addNew(@RequestBody User user,HttpServletRequest hsq) throws ClassNotFoundException, SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		//���Ȩ�ޣ��Ƿ��ѵ�¼
//		if (hsq.getSession().getAttribute("powerleve")==null ||
//				(String)hsq.getSession().getAttribute("powerleve") != "lv100"
//				){
//			return "{\"code\":10}";
//		}
		boolean success = false;
		try{
			success = userService.addNew(user);
		}catch(Exception e){
			return "{\"code\":0}";
		}
		return (success)?"{\"code\":1}":"{\"code\":0}";
	}

	// ɾ
	@RequestMapping(value = "/DeleteOne/{oid}", method = RequestMethod.GET)
	public boolean deleteOne(@PathVariable("oid") String oid) throws SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return userService.deleteOne(oid);// .toString();
	}

	// �б���ҳ
	@RequestMapping(value = "/List", method = RequestMethod.POST)
	public UserBackModel getList(@RequestBody Map mSearch) throws SQLException {//regtype email mobile truename pagesize pageindex
//		List<Art> list = artService.getList(mSearch);
		return userService.getList(mSearch);// .toString();
	}
}
