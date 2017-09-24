package URLQueue;

import java.io.File;
import java.util.LinkedList;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

//存储将要访问的队列
public class Queue {
	
	private LinkedList queue=new LinkedList();
	
	//入
	public void enQueue(Object t){
		queue.addLast(t);
	}
	
	//出
	public Object deQueue(){
		Object url=queue.removeFirst();
		return url;
	}
	
	//判断是否为空
	public boolean isQueueEmpty(){
		return queue.isEmpty();
	}
	
	//判断队列是否含有t
	public boolean contians(Object t){
		return queue.contains(t);
	}
}
