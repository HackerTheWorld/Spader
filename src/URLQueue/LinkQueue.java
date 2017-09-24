package URLQueue;

import java.util.HashSet;
import java.util.Set;

import Database.VisitedDB;
import Entity.Page;

public class LinkQueue {
	
	private static String seed;
	
	//以访问的url
	private static VisitedDB visitedDB=null;
	//待访问的url集合
	private static Queue unvisitedurl=new Queue();
	
	//初始化访问队列
	public LinkQueue(String seed){
		this.seed=seed;
		try {
			visitedDB=new VisitedDB("/Users/apple/Desktop/java/Spader/db");
			visitedDB.clearDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//获得待访问的url队列
	public  Queue getUnvisitedUrl(){
		return unvisitedurl;
	}
	
	//添加到访问过的url队列
	public  void addVisitedUrl(Page url){
		visitedDB.put(url.getOldurl(),url);
	}
	
	//移除访问过的url队列
	public  void removeVisitedUrl(String url){
		visitedDB.delete(url);
	}
	//未访问过的url出列
	public  Object outUnvisitedUrl( ){
		return unvisitedurl.deQueue();
	}
	//保证url只被访问过一次
	public  void oneVisited(String url){
		
		if(!visitedDB.isHave(url)){
			unvisitedurl.enQueue(url);
		}
	}
	//获得以访问过的个数
	public  int getNumberUrl(){
		return visitedDB.count();
	}
	//判断未访问过的url队列是否为空
	public  boolean unvisitedIsEmpty(){
		return unvisitedurl.isQueueEmpty();
	}
}
