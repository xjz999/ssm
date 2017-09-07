package myWeb;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import service.NewsService;
import entity.NewsBackModel;
import entity.News;

@RestController
@RequestMapping("/News")
public class NewsController {
	@Autowired
	private NewsService newsService;
	// 查
	@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
	public News getOne(@PathVariable("oid") String oid) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return newsService.getOne(oid);// .toString();
	}

	// 增 & 改
	@RequestMapping(value = "/AddNew", method = RequestMethod.POST)
	public String addNew(@RequestBody News news) throws ClassNotFoundException, SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		boolean success = false;
		try{
			success = newsService.addNew(news);
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
		return newsService.deleteOne(oid);// .toString();
	}

	// 列表及翻页
	@RequestMapping(value = "/List", method = RequestMethod.POST)
	public NewsBackModel getList(@RequestBody Map mSearch) throws SQLException {//title
//		List<Art> list = artService.getList(mSearch);
		return newsService.getList(mSearch);// .toString();
	}
}
