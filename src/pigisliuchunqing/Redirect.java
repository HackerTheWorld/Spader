package pigisliuchunqing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sleepycat.je.tree.SearchResult;

import URLget.AutoEncodeHttpUtil;
import URLget.HttpUtil;

public class Redirect  {

	public  String spader(String url) throws ClientProtocolException,
			IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();

		HttpGet request = new HttpGet(url);

		HttpContext context = new BasicHttpContext();
		HttpResponse response = client.execute(request, context);
		URI finalUrl = request.getURI();
		RedirectLocations locations = (RedirectLocations) context
				.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
		if (locations != null) {
			finalUrl = locations.getAll().get(locations.getAll().size() - 1);
		}
		HttpEntity entity = response.getEntity();
		String content = AutoEncodeHttpUtil.downHtml(entity);
		return content;

		//writeString(content,"/Users/apple/Desktop/java/DeepCrawler/test.html");
	}
	
//	public static void testAnyCode()throws Exception {
//		String searchWord="江苏理工学院";
//		Document doc = Redirect.search(searchWord);
//		if(doc==null)
//			return;
//		String className = "t";
//		Elements es = doc.getElementsByClass(className);
//		//System.out.println(es);
//		int i=0;
//		for(Element e:es){
//			i++;
//			String url = e.child(0).attr("href");
//			System.out.println(url);
//			String title = e.text();
//			System.out.println(title);
//			String content = SearchResult.down(url); //处理重定向
//			
//			CloseableHttpClient client = HttpClientBuilder.create().build();
//
//			HttpGet request = new HttpGet(url);
//
//			HttpContext context = new BasicHttpContext();
//			HttpResponse response = client.execute(request, context);
//			URI finalUrl = request.getURI();
//			RedirectLocations locations = (RedirectLocations) context
//					.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
//			if (locations != null) {
//				finalUrl = locations.getAll().get(locations.getAll().size() - 1);
//			}
//			System.out.println(finalUrl);
//			HttpEntity entity = response.getEntity();
//			
//			AutoEncodeHttpUtil httpUtil = new AutoEncodeHttpUtil();
//			String content = httpUtil.getHtml(entity);
//			
//			String fileName="f:/test/"+i+".html";
//			SearchResult.writeToFile(content, fileName, httpUtil.htmlCharSet);
			//String ret = AutoEncodeHttpUtil.downHtml(entity);
			
			//content = TextHtml.html2text(content);
			//writeString(content,"f:/test/"+i+".html");
//		}
//	}

//	public static void writeString(String s, String savePath) throws IOException {
//		File f = new File(savePath);
//		FileWriter fw = new FileWriter(f);
//		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write(s);
//		bw.close();
//	}
//
//	public static void testBasic()throws Exception {
//		CloseableHttpClient client = HttpClientBuilder.create().build();
//
//		String url = "";
//		
//		HttpGet request = new HttpGet(url);
//
//		HttpContext context = new BasicHttpContext();
//		HttpResponse response = client.execute(request, context);
//		URI finalUrl = request.getURI();
//		RedirectLocations locations = (RedirectLocations) context
//				.getAttribute(HttpClientContext.REDIRECT_LOCATIONS);
//		if (locations != null) {
//			finalUrl = locations.getAll().get(locations.getAll().size() - 1);
//		}
//		System.out.println(finalUrl);
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//			// 读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
//			System.out.println(EntityUtils.toString(entity, "utf-8")); // 网页的Meta标签中指定了编码
//			EntityUtils.consume(entity);// 关闭内容流
//		}
//	}
//	
//	public static void testGBK() throws Exception{
//		String url = "";
//		String ret = HttpUtil.getContentGBK(url);
//		System.out.println(ret);
//	}
//
	public static void testAutoEncode() throws Exception{
		//不能指定固定编码
		String url = "";
		String ret = AutoEncodeHttpUtil.getHtml(new URL(url));
		System.out.println(ret);
	}
}
