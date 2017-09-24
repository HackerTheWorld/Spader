package pigisliuchunqing;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Entity.Page;
import URLQueue.LinkQueue;
import URLget.HtmlParserTool;
import URLget.LinkFilter;
import Webspader.WriteObject;

public class test {
	private LinkQueue linkqueue=new LinkQueue("http://www.163.com/");
	private Redirect rerdirect=new Redirect();
	private List list=new LinkedList();
  //种子初始化
	private void initvisited(String[ ] seed){
		for(String a:seed){
			linkqueue.oneVisited(a);
		}
	}
	
	/*开始抓取*/
	public void crawling(String[ ] seed){
		
		LinkFilter filter=new LinkFilter(){
			public boolean accpect(String url){
				if(url.startsWith("http://"))
					return true;
				else
					return false;
			}	
		};
		
		initvisited(seed);
		while(!linkqueue.unvisitedIsEmpty()){
			String visiturl=(String)linkqueue.outUnvisitedUrl();
				try {
					String str=rerdirect.spader(visiturl);
					Page p=new Page();
					p.setOldurl(visiturl);
					p.setHtml(str);
					list=HtmlParserTool.analysisPage(visiturl);
					
					WriteObject.write(list,null,0);
					
					linkqueue.addVisitedUrl(p);
					Set<String> links=HtmlParserTool.extracLinks(visiturl, null, filter);
					for(String link:links){
						linkqueue.oneVisited(link);
					}
					
				} 
				catch (Exception e) {
					e.toString();

					e.getMessage();
				}
			
		}
	}

	public static void main(String[ ] args){	
		test t=new test();
		t.crawling(new String[]{"http://www.jstu.edu.cn"});
	}	
}
