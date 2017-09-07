package myWeb;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import service.ArtService;
import serviceImplement.ArtServiceImplement;
import entity.Art;
import entity.ArtBackModel;

@RestController
@RequestMapping("/Arts")
public class ArtController {
	@Autowired
	private ArtService artService;

	Properties config = new Properties();
	public String savePathSet = "";

	public ArtController() {
		try {
			config.load(ArtServiceImplement.class
					.getResourceAsStream("/jdbc.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		savePathSet = config.getProperty("servlet.savepath");
	}

	@RequestMapping(value = "/hello/{oid}", method = RequestMethod.GET)
	public Art greetingByGet(@PathVariable("oid") String oid) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return artService.selectById(oid);// .toString();
	}

	// HttpServletRequest request,HttpServletResponse response
	// throws ServletException, IOException
	// @RequestParam(value = "file",required = false) MultipartFile file
	@RequestMapping(value = "/fileUpload")
	public String fileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) throws ServletException, IOException {
		// String savePath="/home/wwwroot/ftpuser/uploadfiles";

		System.out.println("开始");
		if (file == null) {
			return "{\"filename\":\"\",\"code\":0}";
		}
		if (file == null) {
			return "{\"filename\":\"\",\"code\":0}";
		}
		if (file.isEmpty()) {
			return "{\"filename\":\"\",\"code\":0}";
		}
		if (file.getSize() > 5 * 1024 * 1024) {
			return "{\"filename\":\"\",\"code\":2}";
		}

		String path = savePathSet;// request.getSession().getServletContext().getRealPath("upload");
		Date date = new Date();

		SimpleDateFormat yyMMFormat = new SimpleDateFormat("yyyy-MM");// 24小时制
		SimpleDateFormat allFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 24小时制
		String monthStr = yyMMFormat.format(date);
		String allStr = allFormat.format(date);
		int randomInt = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;
		File pathDir = new File(path + File.separator + monthStr);
		
		if (!pathDir.exists() || !pathDir.isDirectory()) {
			pathDir.mkdirs();
		}
		System.out.println(pathDir.exists());
		String orgName = file.getOriginalFilename();
		System.out.println(orgName);
		String extName = orgName.substring(orgName.lastIndexOf("."));
		String fileName = allStr + String.valueOf(randomInt) + extName;// file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";
		System.out.println("保存之前");
		System.out.println(path + File.separator + monthStr);
		System.out.println(fileName);
		File targetFile = new File(pathDir,fileName);
		// if(!targetFile.exists()){
		// targetFile.mkdirs();
		// }

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"filename\":\"\"}";
		}
		return "{\"filename\":\"" + monthStr + "/" + fileName + "\"}";
	}

	// 查
	@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
	public Art getOne(@PathVariable("oid") String oid) {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return artService.selectById(oid);// .toString();
	}

	// 增 & 改
	@RequestMapping(value = "/AddNew", method = RequestMethod.POST)
	public String addNew(@RequestBody Art art) throws ClassNotFoundException, SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		boolean success = false;
		try{
			success = artService.addNew(art);
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
		return artService.deleteOne(oid);// .toString();
	}

	// 列表及翻页
	@RequestMapping(value = "/List", method = RequestMethod.POST)
	public ArtBackModel getList(@RequestBody Map mSearch) throws SQLException {//ctype awardtype useroid pagesize pageindex
//		List<Art> list = artService.getList(mSearch);
		return artService.getList(mSearch);// .toString();
	}

}
