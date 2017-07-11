package common;

import org.springframework.http.MediaType;
import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;

public class MessageConvert2 extends StringHttpMessageConverter {
	private static final MediaType utf8 = new MediaType("text", "plain",Charset.forName("UTF-8"));//Charset.forName("UTF-8")   
	  
    @Override  
    protected MediaType getDefaultContentType(String dumy) {  
        return utf8;  
    }  
}