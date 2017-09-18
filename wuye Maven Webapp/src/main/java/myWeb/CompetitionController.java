package myWeb;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.CompetitionService;
import entity.Competition;
import entity.CompetitionBackModel;

@RestController
@RequestMapping("/Competitions")
public class CompetitionController {
	@Autowired
	private CompetitionService competitionService;
	//******************************* CURD ****************************/
		// 查
		@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
		public Competition getOne(@PathVariable("oid") String oid) throws SQLException {
			// return new Greeting(counter.incrementAndGet(),
			// String.format(template, name));
			return competitionService.getOne(oid);// .toString();
		}

		// 增 & 改
		@RequestMapping(value = "/AddNew", method = RequestMethod.POST)
		public String addNew(@RequestBody Competition competi,HttpServletRequest hsq) throws ClassNotFoundException, SQLException {
			// return new Greeting(counter.incrementAndGet(),
			// String.format(template, name));
			//检查权限，是否已登录
//			if (hsq.getSession().getAttribute("powerleve")==null ||
//					(String)hsq.getSession().getAttribute("powerleve") != "lv100"
//					){
//				return "{\"code\":10}";
//			}
			boolean success = false;
			try{
				success = competitionService.addNew(competi);
			}catch(Exception e){
				return "{\"code\":0}";
			}
			return (success)?"{\"code\":1}":"{\"code\":0}";
		}

		// 删
		@RequestMapping(value = "/DeleteOne/{oid}", method = RequestMethod.GET)
		public boolean deleteOne(@PathVariable("oid") String oid) throws SQLException {
			// return new Greeting(counter.incrementAndGet(),
			// String.format(template, name));
			return competitionService.deleteOne(oid);// .toString();
		}

		// 列表及翻页
		@RequestMapping(value = "/List", method = RequestMethod.POST)
		public CompetitionBackModel getList(@RequestBody Map mSearch) throws SQLException {//regtype email mobile truename pagesize pageindex
//			List<Art> list = artService.getList(mSearch);
			return competitionService.getList(mSearch);// .toString();
		}
}
