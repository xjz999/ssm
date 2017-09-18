package serviceImplement;

import java.io.IOException;
import java.util.Properties;

/*连接池工具类，返回唯一的一个数据库连接池对象,单例模式*/  
public class ConnectionPoolUtils {
	private static Properties config = new Properties();
	public static String mysqlUrl = "";
	public static final String mysqlDriverName = "com.mysql.jdbc.Driver";
	public static String mysqlUser = "";
	public static String mysqlPassword = "";
	
    private ConnectionPoolUtils(){};//私有静态方法  
    private static ConnectionPool poolInstance = null;  
    public static ConnectionPool GetPoolInstance(){
        if(poolInstance == null) {
        	try {
    			config.load(ArtServiceImplement.class.getResourceAsStream("/jdbc.properties"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		mysqlUrl = config.getProperty("jdbc.url"); 
    		mysqlUser = config.getProperty("jdbc.username");
    		mysqlPassword = config.getProperty("jdbc.password");
    		
            poolInstance = new ConnectionPool(                     
            		mysqlDriverName,//"com.mysql.jdbc.Driver",                   
            		mysqlUrl,//"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8",                
            		mysqlUser,//"root",
            		mysqlPassword//"123456"
            		);
            try {  
                poolInstance.createPool();  
            } catch (Exception e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
        return poolInstance;  
    }  
}  
