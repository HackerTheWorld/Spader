package URLget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;


public class HttpUtil {

	public static List<Header> getHeads() {
		// headers
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)";
		List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Charset",
				"GB2312,utf-8;q=0.7,*;q=0.7"));
		headers.add(new BasicHeader("Accept-Language", "zh-cn,zh;q=0.5"));
		headers.add(new BasicHeader("User-Agent", userAgent));

		return headers;
	}

	public static String getContent(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		return getContent(httpclient, url);
	}

	// 下载utf-8编码的网页
	public static String getContent(CloseableHttpClient httpclient, String url)
			throws Exception {
		String content = null;

		do {
			content = down(httpclient, url, "utf-8");
			if (content != null) {
				break;
			}
			Thread.sleep(1000);
			System.out.println("retry get content ...");
		} while (true);

		return content;
	}

	public static String getContentGBK(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		return getContentGBK(httpclient, url);
	}

	// 下载GBK编码的网页
	public static String getContentGBK(CloseableHttpClient httpclient,
			String url) throws Exception {
		String content = null;

		do {
			try{
				content = down(httpclient, url, "gbk");
				if (content != null) {
					break;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				Thread.sleep(1000);
				//httpclient.close();
			}
			Thread.sleep(1000);
			System.out.println("retry get content ...");
		} while (true);

		return content;
	}

	public static String getContentGBK(HttpClientBuilder hb, String url) throws InterruptedException, IOException {
		String content = null;

		CloseableHttpClient httpclient = hb.build();
		do {
			try{
				content = down(httpclient, url, "gbk");
				if (content != null) {
					break;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				httpclient.close();
				Thread.sleep(90000);
				httpclient = hb.build();
			}
			Thread.sleep(1000);
			System.out.println("retry get content ...");
		} while (true);

		httpclient.close();
		return content;
	}
	
	public static String down(CloseableHttpClient httpclient, String url,
			String encode) throws Exception {
		HttpGet httpget = new HttpGet(url);
		String content = null;
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		int code = response.getStatusLine().getStatusCode();
		// System.out.println(code);
		if (code != HttpStatus.SC_OK) {
			throw new Exception("down error httpcode:" + code);
		}
		// 查看返回的内容，类似于在浏览器察看网页源代码
		HttpEntity entity = response.getEntity();
		if (entity != null) {

			try {
				content = EntityUtils.toString(entity, encode); // "utf-8"
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}

			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}// 关闭内容流
		}
		return content;
	}

}
