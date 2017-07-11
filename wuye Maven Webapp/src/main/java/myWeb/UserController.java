package myWeb;

import myWeb.Greeting;

import java.io.IOException;
import java.io.Writer;
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

import entity.User;
import service.UserService;

@RestController
//@RequestMapping("/myFirstMvc")
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
	public User greetingByPost(@RequestParam(value="userid", defaultValue="1") int userid) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
		return userService.selectById(userid);//.toString();
    }
	
	@RequestMapping(value = "/hello/{userid}",method=RequestMethod.GET)
	public User greetingByGet(@PathVariable("userid") int userid) {
//        return new Greeting(counter.incrementAndGet(),
//                            String.format(template, name));
		return userService.selectById(userid);//.toString();
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
	
}
