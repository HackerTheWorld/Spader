package Database;

import java.io.FileNotFoundException;
import java.util.Map.Entry;
import java.util.Set;

import Entity.Page;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;


public class VisitedDB extends AbstractBerkeleyDB
 {  
    private StoredMap pendingUrisDB = null;  
 
    //使用默认的路径和缓存大小构造函数  
    public VisitedDB(String homeDirectory) throws Exception  
    {  
        super(homeDirectory);  
        // 打开  
        database = env.openDatabase(null, "UnvisitedURL", dbConfig);
        EntryBinding keyBinding =new SerialBinding (javaCatalog,String.class);  
        EntryBinding valueBinding =new SerialBinding(javaCatalog, Page.class);  
        pendingUrisDB = new StoredMap(database,keyBinding,valueBinding,true);  
    }  
 
    //获得下一条记录  
    public Page getNext() throws Exception {  
        Page result = null;  
        if(!pendingUrisDB.isEmpty()){  
            Set entrys = pendingUrisDB.entrySet();    
            Entry<String,Page> entry=(Entry<String,Page>)pendingUrisDB.entrySet().iterator().next();  
            result = entry.getValue();  
            delete(entry.getKey());  
        }  
        return result;  
    }  
//    //存入URL  
//    public void putUrl(Page url){  
//   // 	String str=url.getOldurl();
//    	   pendingUrisDB.put(url.getOldurl(),url);
//    	   Page p=(Page)pendingUrisDB.get(url.getOldurl());
//    	   System.out.println("存入-------------------");
//    	   System.out.println(p.getOldurl());
//			
//    	   
//    }  

    //取出  
    public  Object get(Object key){ 
        		return pendingUrisDB.get(key);  
    }  
    
    //是否访问过
    public boolean isHave(Object key){
    			if(pendingUrisDB.containsKey(key))
    				return true; 
    			else
    				return false;
    }
    
    //删除  
    public void delete(Object key){  
         pendingUrisDB.remove(key);  
    }  
 
 
    public int count(){
    		int i=pendingUrisDB.size();
    		return i;
    }
    
    //	清空数据库
    public void clearDB(){
    		pendingUrisDB.clear();
    }

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		 pendingUrisDB.put(key,value);
  	   Page p=(Page)pendingUrisDB.get(key);
  	   System.out.println("存入-------------------"+p.getOldurl());

	}
    
//    根据URL计算键值，可以使用各种压缩算法，包括MD5等压缩算法  
//    private String caculateUrl(String url) {  
//        return url;  
//    } 
 }