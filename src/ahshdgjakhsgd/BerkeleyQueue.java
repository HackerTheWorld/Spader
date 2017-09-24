package ahshdgjakhsgd;

import java.io.File;
import java.util.Iterator;
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


public  class BerkeleyQueue {
	
	
	Environment ment;
	StoredClassCatalog stored;
	
    Database keydb;
    Database  sapderdb;
    StoredMap map;
   
    
   
    public BerkeleyQueue(){	
    	
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
		
		DatabaseConfig dbconfig1=new DatabaseConfig();
		dbconfig1.setAllowCreate(true);
		dbconfig1.setTransactional(true);
		sapderdb = ment.openDatabase(null, "sapderdb", dbconfig1);
		
		EntryBinding valuebind=new SerialBinding(stored,Page.class);
		
		map=new StoredMap(sapderdb,keybind,valuebind,true);
		
	}
	
   //出
    public void removeQueue(String url){
    		map.remove(url);
	}
   //入
    public void enQueue(Page p){
		map.put(p.getOldurl(), p);
	}
   //判断是否为空
    public boolean isQueueEmpty(){
		return map.isEmpty();
	}
   //判断是否含有url
    public boolean contians(String url){
	return map.containsKey(url);
}
    
}
