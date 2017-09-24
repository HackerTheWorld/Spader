package URLget;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.sleepycat.je.tree.Node;

public class AutoEncodeHttpUtil {
	static final String defaultCharSet = "utf-8";
	static HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler(
			16, true);
	
	static List<Header> headers = getHeads();

	private static List<Header> getHeads() {
		// 设置头标签
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)";
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Charset","GB2312,utf-8;q=0.7,*;q=0.7"));
		headers.add(new BasicHeader("Accept-Language","zh-cn,zh;q=0.5"));
		headers.add(new BasicHeader("User-Agent",userAgent));
		
		return headers;
	}

	public static String getHtml(URL url) throws Exception {
		String value = null;
		
		
		int socketTimeout = 9000;
		int connectionTimeout = 9000;
	
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectionTimeout)
				.setSocketTimeout(socketTimeout).build();
		
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.setDefaultHeaders(headers)
				.setRetryHandler(retryHandler).build();
		HttpGet httpget = new HttpGet(url.toURI());

		HttpResponse response = httpClient.execute(httpget);

		// 判断页面返回状态判断
		int statusCode = response.getStatusLine().getStatusCode();
		if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (statusCode == HttpStatus.SC_SEE_OTHER)
				|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
			// 重定向
			String newUri = response.getLastHeader("Location").getValue();
			//System.out.println("重定向:"+newUri);
			httpClient = HttpClientBuilder.create().build();
			httpget = new HttpGet(newUri);
			response = httpClient.execute(httpget);
		}
	
		HttpEntity entity = response.getEntity();
		
		if (entity != null) {
			// 保存网页源码
			byte[] bytes = EntityUtils.toByteArray(entity);
		
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset cSet = contentType.getCharset();
			String charSet = null;
			if (cSet != null)
				charSet = cSet.toString();
			//从头文件中找到
			if (charSet == null) {
				value = new String(bytes, defaultCharSet);			
				Pattern pt = Pattern.compile("<meta(.*?)charset=(.*?)\"",
						Pattern.CASE_INSENSITIVE);
				Matcher mc = pt.matcher(value);
				if (mc.find()) {
					charSet = mc.group(2).trim();
				} else {
					pt = Pattern.compile("<?xml(.*?)encoding=\"(.*?)\"",
							Pattern.CASE_INSENSITIVE);
					mc = pt.matcher(value);
					if (mc.find()) {
						charSet = mc.group(2).trim();
					}
				}
			}

			if (charSet == null|| "".equals(charSet)) {
				UniversalDetector detector = new UniversalDetector(null);
				detector.handleData(bytes, 0, bytes.length);
				detector.dataEnd();
				charSet = detector.getDetectedCharset();
				//System.out.println("detect charset:" + charSet+" url "+url);
				if (charSet == null)
					return null;
			}
			if (charSet != null && charSet.toLowerCase().equals("gb2312")) {
				charSet = "GBK";
			}

			if (!defaultCharSet.equals(charSet) || value == null){
				//System.out.println("charset:" + charSet);
				value = new String(bytes, charSet);
			}
		}

		httpClient.close();
		// System.out.println(value);
		return value;
	}

	public String htmlCharSet = "utf-8";  //网页编码
	
	public String getHtml(HttpEntity entity) throws IOException{
		String value = null;
		
		if (entity != null) {
			
			byte[] bytes = EntityUtils.toByteArray(entity);
			

			ContentType contentType = ContentType.getOrDefault(entity);
			Charset cSet = contentType.getCharset();
			String charSet = null;
			if (cSet != null)
				charSet = cSet.toString();
			if (charSet == null) {
				
				value = new String(bytes, defaultCharSet);
			
				Pattern pt = Pattern.compile("<meta(.*?)charset=(.*?)\"",
						Pattern.CASE_INSENSITIVE);
				Matcher mc = pt.matcher(value);
				if (mc.find()) {
					charSet = mc.group(2).trim();
					// System.out.println("find charset meta:"+charSet);
				} else {
					pt = Pattern.compile("<?xml(.*?)encoding=\"(.*?)\"",
							Pattern.CASE_INSENSITIVE);
					mc = pt.matcher(value);
					if (mc.find()) {
						charSet = mc.group(2).trim();
					}
				}
			}

			if (charSet == null|| "".equals(charSet)) {
				UniversalDetector detector = new UniversalDetector(null);
				detector.handleData(bytes, 0, bytes.length);
				detector.dataEnd();
				charSet = detector.getDetectedCharset();
				//System.out.println("detect charset:" + charSet+" url "+url);
				if (charSet == null)
					return null;
			}
			if (charSet != null && charSet.toLowerCase().equals("gb2312")) {
				charSet = "GBK";
			}

			if (!defaultCharSet.equals(charSet) || value == null){
				//System.out.println("charset:" + charSet);
				value = new String(bytes, charSet);
			}
			
			htmlCharSet = charSet;
		}

		return value;
	}
	
	public static String downHtml(HttpEntity entity) throws IOException{
		String value = null;
		
		if (entity != null) {
		
			byte[] bytes = EntityUtils.toByteArray(entity);
			
			ContentType contentType = ContentType.getOrDefault(entity);
			Charset cSet = contentType.getCharset();
			String charSet = null;
			if (cSet != null)
				charSet = cSet.toString();
			
			if (charSet == null) {
			
				value = new String(bytes, defaultCharSet);
				
				Pattern pt = Pattern.compile("<meta(.*?)charset=(.*?)\"",
						Pattern.CASE_INSENSITIVE);
				Matcher mc = pt.matcher(value);
				if (mc.find()) {
					charSet = mc.group(2).trim();
					// System.out.println("find charset meta:"+charSet);
				} else {
					pt = Pattern.compile("<?xml(.*?)encoding=\"(.*?)\"",
							Pattern.CASE_INSENSITIVE);
					mc = pt.matcher(value);
					if (mc.find()) {
						charSet = mc.group(2).trim();
					}
				}
			}

			if (charSet == null|| "".equals(charSet)) {
				UniversalDetector detector = new UniversalDetector(null);
				detector.handleData(bytes, 0, bytes.length);
				detector.dataEnd();
				charSet = detector.getDetectedCharset();
				//System.out.println("detect charset:" + charSet+" url "+url);
				if (charSet == null)
					return null;
			}
			if (charSet != null && charSet.toLowerCase().equals("gb2312")) {
				charSet = "GBK";
			}

			if (!defaultCharSet.equals(charSet) || value == null){
				//System.out.println("charset:" + charSet);
				value = new String(bytes, charSet);
			}
		}

		return value;
	}
}
