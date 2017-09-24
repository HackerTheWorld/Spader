package Webspader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;



public class DownloadPage {
	
	private static int n=0;
	
	private String page;
	
	private static HttpClient httpclient = new DefaultHttpClient();
	
//	static {
//		httpclient.getConnectionManager().setProxy("172.17.18.84", 8080);
//	}
	@SuppressWarnings("resource")
	public void downLoad(String strurl){
		URL url;
		try {
			 File file = new File("/Users/apple/Desktop/picture/"+cutString(strurl));  
	         FileOutputStream fops = new FileOutputStream(file);  
			url = new URL(strurl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		    conn.setRequestMethod("GET");  
		    conn.setConnectTimeout(5 * 1000);  
		    InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
		  
		
		    ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1 ){  
	            outStream.write(buffer, 0, len);  
	        }  
	        inStream.close(); 
	        byte[] btImg = outStream.toByteArray();//得到图片的二进制数据 
	        fops.write(btImg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    
	}
	
	protected String cutString(String str){
		String[] strs;
		strs=str.split("/");
		return strs[strs.length-1];
	}
	
	
}
