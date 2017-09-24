package Database;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class UnvisitedDB extends AbstractBerkeleyDB{

	public UnvisitedDB(String homeDirectory) throws Exception {
		super(homeDirectory);
		// TODO Auto-generated constructor stub 
        database = env.openDatabase(null, "VisitedURL", dbConfig);
	}

	//将访问过的url放入
	protected void put(Object key) {
		 DatabaseEntry thekey;
		try {
			thekey = new DatabaseEntry(key.toString().getBytes("gb2312"));
			database.put(null,thekey,thekey);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	//获取指定key的值
	@Override
	protected Object get(Object key) {
		 DatabaseEntry thekey;
		 DatabaseEntry thedata = new DatabaseEntry();
		try {
			thekey = new DatabaseEntry(key.toString().getBytes("gb2312"));
			if(database.get(null,thekey, thedata, LockMode.DEFAULT)== OperationStatus.SUCCESS)
			 {
				 	String data = new String(thedata.getData(),"gb2312");
					return data;	
			 }	 	 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 return null;
		 
	}

	//删除访问过的url
	@Override
	protected void delete(Object key) {
		// TODO Auto-generated method stub
		DatabaseEntry thekey;
		try {
			thekey = new DatabaseEntry(key.toString().getBytes("gb2312"));
			database.delete(null, thekey);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//判断是否访问过此url
	protected boolean isVistied(Object url){
		if(get(url)!=null){
			return true;
		}else{
			return false;
		}
	}
	

	@Override
	protected void put(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
