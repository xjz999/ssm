package myWeb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
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
//
//	@RequestMapping(value = "/hello/{oid}", method = RequestMethod.GET)
//	public Art greetingByGet(@PathVariable("oid") String oid) {
//		// return new Greeting(counter.incrementAndGet(),
//		// String.format(template, name));
//		return artService.selectById(oid);// .toString();
//	}

	// HttpServletRequest request,HttpServletResponse response
	// throws ServletException, IOException
	// @RequestParam(value = "file",required = false) MultipartFile file
	@RequestMapping(value = "/fileUpload")
	public String fileUpload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "modeltype", required = false) String modelType,
			HttpServletRequest request) throws ServletException, IOException {
		String picType="0";//
		if (modelType != null && !modelType.equals("")){
			picType = modelType;
		}
		//modelType 1-图片新闻
//		2-图片故事
//		3-个人图片
//		4-获奖图片
//		5-上传赛事图标
		
		// String savePath="/home/wwwroot/ftpuser/uploadfiles";
		System.out.println(modelType);
		System.out.println("开始");
		if (file == null) {
			return "{\"filename\":\"\",\"code\":\"file null\"}";
		}
		if (file.isEmpty()) {
			return "{\"filename\":\"\",\"code\":\"file empty\"}";
		}
		if (file.getSize() > 5 * 1024 * 1024) {
			return "{\"filename\":\"\",\"code\":\"file size err\"}";
		}
		String orgName = file.getOriginalFilename();
		System.out.println(orgName);
		String extName = orgName.substring(orgName.lastIndexOf("."));
		if (!extName.toLowerCase().equals(".gif") && !extName.toLowerCase().equals(".jpg") && !extName.toLowerCase().equals(".png")){
			return "{\"filename\":\"\",\"code\":\"file type err\"}";
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
		
		String fileName = allStr + String.valueOf(randomInt) + "_b_d" + extName;// file.getOriginalFilename();
		// String fileName = new Date().getTime()+".jpg";
		System.out.println("保存之前");
		System.out.println(path + File.separator + monthStr);
		System.out.println(fileName);
//		
		File targetFile = new File(pathDir,fileName);
		// if(!targetFile.exists()){
		// targetFile.mkdirs();
		// }

		// 保存
		try {
			file.transferTo(targetFile);
			String bigFilename = path + File.separator + monthStr + File.separator+fileName;
			String midFilename = bigFilename.replace("_b_d", "_mid");
			String firFilename = bigFilename.replace("_b_d", "_fir");
			String smallFilename = bigFilename.replace("_b_d", "");
			switch(picType){
				case "0":
					toSmaillImg(bigFilename,bigFilename,900, 900,0);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,smallFilename,150, 150,0);
				break;
				case "1"://新闻图片
//					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,midFilename,1024, 682,1);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,firFilename,818, 545,1);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,smallFilename,160, 106,0);
					break;
				case "2"://图片故事
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,midFilename,1024, 682,1);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,firFilename,574, 454,1);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,smallFilename,160, 106,0);
					break;
				case "4":
					toSmaillImg(bigFilename,bigFilename,900, 900,0);
					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,smallFilename,168, 112,0);
					break;
				case "5":
					toSmaillImg(bigFilename,bigFilename,120, 90,0);
//					toSmaillImg(path + File.separator + monthStr + File.separator+fileName,smallFilename,168, 112,0);
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"filename\":\"\",\"code\":\"save err\"}";
		}
		return "{\"filename\":\"" + monthStr + "/" + fileName + "\"}";
	}
	
	public static void toSmaillImg(String filePath,String thumbPath,float tarWidth,float tarHeight,int outView) throws Exception{    
		String newurl =thumbPath;    
		java.awt.Image bigJpg = javax.imageio.ImageIO.read(new java.io.File(filePath));    
//		float tagsize = 150;    
		 int old_w = bigJpg.getWidth(null);    
		int old_h = bigJpg.getHeight(null);       
		int new_w = 0;    
		 int new_h = 0;    
		 float tempdouble;
		 if (outView  == 1){
			 if(old_w > tarWidth && old_h > tarHeight){
				 tempdouble = ((float)old_w / (float)old_h > tarWidth / tarHeight) ?  old_h / tarHeight : old_w / tarWidth; 
				 new_w = Math.round(old_w/tempdouble);    
				 new_h = Math.round(old_h/tempdouble);
			 }else{
				 new_w = old_w;
				 new_h = old_h;
			 }
		 }else {
			 if (old_w > tarWidth || old_h > tarHeight){
				 tempdouble = ((float)old_w / (float)old_h > tarWidth / tarHeight) ? old_w / tarWidth : old_h / tarHeight;    
				 new_w = Math.round(old_w/tempdouble);    
				 new_h = Math.round(old_h/tempdouble);    
			 }else{
				 new_w = old_w;
				 new_h = old_h;
			 }
		 }
		 
		 java.awt.image.BufferedImage tag = new java.awt.image.BufferedImage(new_w,new_h,java.awt.image.BufferedImage.TYPE_INT_RGB);    
		 tag.getGraphics().drawImage(bigJpg,0,0,new_w,new_h,null);    
		 FileOutputStream newimage = new FileOutputStream(newurl);    
//		 com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(newimage);           
//		 encoder.encode(tag);
		 String ext = newurl.substring(newurl.lastIndexOf(".")+1);
		 System.out.println(ext);
		 ImageIO.write(tag,ext,new File(newurl));
		 newimage.close();    
}    

	// 查
	@RequestMapping(value = "/GetOne/{oid}", method = RequestMethod.GET)
	public Art getOne(@PathVariable("oid") String oid) throws SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		return artService.getOne(oid);// .toString();
	}

	// 增 & 改
	@RequestMapping(value = "/AddNew", method = RequestMethod.POST)
	public String addNew(@RequestBody Art art,HttpServletRequest hsq) throws ClassNotFoundException, SQLException {
		// return new Greeting(counter.incrementAndGet(),
		// String.format(template, name));
		//检查权限，是否已登录
//		System.out.println(hsq.getSession().getAttribute("powerleve"));
//		if (hsq.getSession().getAttribute("powerleve")==null ||
//				(String)hsq.getSession().getAttribute("powerleve") != "lv100"
//				){
//			return "{\"code\":10}";
//		}
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
	public boolean deleteOne(@PathVariable("oid") String oid) throws SQLException {
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
