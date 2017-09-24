package ahshdgjakhsgd;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Entity.Page;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;


public  class BerkeleyDB {
	
	
	Environment ment;
	StoredClassCatalog stored;
	
    Database keydb;
    Database  sapderdb;
    StoredMap map;
   
    
   
    public BerkeleyDB(){	
    	
    		EnvironmentConfig config=new EnvironmentConfig();
    		config.setTransactional(true);
    		config.setAllowCreate(true);
    		
    		ment=new Environment(new File("/Users/apple/Desktop/java/Spader/db"),config);
    		
    		DatabaseConfig dbconfig=new DatabaseConfig();
    		dbconfig.setAllowCreate(true);
		dbconfig.setTransactional(true);
		
		keydb=ment.openDatabase(null, "keydb",dbconfig);
		stored=new StoredClassCatalog(keydb);
		EntryBinding keybind=new SerialBinding(stored,String.class);
		//TupleBinding keybind=TupleBinding.getPrimitiveBinding(String.class);
		
		DatabaseConfig dbconfig1=new DatabaseConfig();
		dbconfig1.setAllowCreate(true);
		dbconfig1.setTransactional(true);
		sapderdb = ment.openDatabase(null, "sapderdb", dbconfig1);
		
		EntryBinding valuebind=new SerialBinding(stored,Page.class);
		//stored=new StoredClassCatalog(keydb);
		//classbind=new SerialBinding(new StoredClassCatalog(db),Page.class);
		
		map=new StoredMap(sapderdb,keybind,valuebind,true);
	    
		keybind=null;
		valuebind=null;
		
		//map=new StoredMap();
		
	}
    
    //获取下一条数据
	public Page GetNext() throws Exception{
		Page page = null;
		if(!map.isEmpty()){
			Set entrys =map.entrySet();
			List<String> list = new ArrayList<String> ();  
			list.addAll(entrys);  
			int i=0;
			while(!list.isEmpty()){
				System.out.println(list.get(i++));
			}
//			Entry entry=(Entry) entrys.next();
//			page=(Page)entry.getValue();
//			Remove(entry.getKey().toString());
		}
		return page;
	}
    
/**
 * 添加到未访问过的url**/	
	public void WriteToDB(String key,Page p){
		map.put(key, p);
		return ;
	}	
	public void WriteToURL(Page p){
		WriteToDB(p.getOldurl(),p);
	}
	
	//读取数据	
	public  Object ReadToDB(String key){
		if(map.get(key)!=null)
			return map.get(key);
		else
			return null;
	}
		
    //移除访问过的队列
    public  void Remove(String key){
         map.remove(key);
    }
	
	public  void closeConfig(){
		keydb.close();
		sapderdb.close();
	}
	
}
