package serviceImplement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SMSSent {

//	String token =args[0];  
//    String sr=HttpRequest.sendPost("https://tel.exmail.qq.com/cgi-bin/token", "grant_type=client_credentials&client_id=bizmx&client_secret="+token);  
//    JSONObject jsonObj = JSONObject.fromObject(sr);  
//    String s = (String) jsonObj.get("access_token");  
//    doPost(s);  
	public static boolean doGet(String smsCode,String mobile){  
        final String website = "http://smsapi.api51.cn";
        final int connectTimeOut = 15000;
        final int readDataTimeOut = 50000;  
        HttpURLConnection httpConn = null;  
        String htmlContent = null;  
//        String requestCookie = null;
        String reqUrl = "/code/?code="+smsCode+"&mobile="+mobile;
        boolean isSuccess = false;
        try{
            // 第一次请求【POST】  
            // 1、创建连接  
            URL url = new URL(website + reqUrl);  
            httpConn = (HttpURLConnection) url.openConnection();  
            httpConn.setDoInput(true);  
            httpConn.setDoOutput(true);  
            httpConn.setUseCaches(false);  
            httpConn.setConnectTimeout(connectTimeOut);  
            httpConn.setReadTimeout(readDataTimeOut);  
            // 2、设置请求头  
            httpConn.setRequestProperty("Authorization"," APPCODE cd023b752d2e499ea8a1e4fc702251b1");  
            // 3、连接  
            httpConn.setRequestMethod("GET");  
            httpConn.connect();  
              
            // 4、设置请求参数  
//            OutputStream outStream = httpConn.getOutputStream();  
//            String postData = "";  
//            outStream.write(postData.getBytes());  
//            outStream.flush();  
//            outStream.close();  
              
            // 5、获取响应结果  
            // 获取响应头信息  
//            Map<String, List<String>> resHeaderMap = httpConn.getHeaderFields();  
//            if (null != resHeaderMap && false == resHeaderMap.isEmpty()){  
//                for (Map.Entry<String, List<String>> entry : resHeaderMap.entrySet()){  
//                    String key = entry.getKey();  
//                    String value = java.util.Arrays.toString(entry.getValue().toArray());  
//                    if (null != key && "Set-Cookie".equals(key.trim())){  
//                        requestCookie = value;  
//                        requestCookie = requestCookie.replace("[", "");  
//                        requestCookie = requestCookie.replace("]", "");  
//                    }  
//                }  
//            }
            if (HttpURLConnection.HTTP_OK == httpConn.getResponseCode()){  
                InputStream inStream = httpConn.getInputStream();  
                htmlContent = inputStream2String(inStream);  
                System.out.println(htmlContent);
                if(!htmlContent.equals("")){
                	ObjectMapper mapper = new ObjectMapper();  
                    JsonNode root = mapper.readTree(htmlContent);
                    JsonNode ss = root.path("success");
                    if (ss.asBoolean()==true){
                    	isSuccess = true;
                    }
                }
            }  
            return isSuccess;
        } catch (IOException e){  
            e.printStackTrace(); 
            return false;
        } finally{  
            if (null != httpConn){  
                try{  
                    httpConn.disconnect();  
                } catch (Exception e){  
                }  
            }  
        } 
    }
	
	public static String inputStream2String(InputStream is) throws IOException{   
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   
        int i=-1;   
        while((i=is.read())!=-1){   
            baos.write(i);   
        }   
       return baos.toString();   
    }  
	
}
