package Webspader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;

public class WriteObject {
	
	static RandomAccessFile randomFile = null;
	public static  void write(List list,String str,int type) {	
		  try {
			  if(type==1){
	            // 打开一个随机访问文件流，按读写方式     
	            randomFile = new RandomAccessFile("/Users/apple/Desktop/html/"+(API.API.count-1)+".html", "rw");     
	            // 文件长度，字节数     
	            long fileLength = randomFile.length();     
	            // 将写文件指针移到文件尾。     
	            randomFile.seek(fileLength);     
	            randomFile.write(str.getBytes("gbk")); 
	            randomFile.close();
			  }else if(type==2){
				  randomFile = new RandomAccessFile("/Users/apple/Desktop/html/"+(API.API.count-1)+".html", "rw");     
		            // 文件长度，字节数     
		            long fileLength = randomFile.length();     
		            // 将写文件指针移到文件尾。     
		            randomFile.seek(fileLength);     
				  if(list!=null){
					   Iterator it = list.iterator();
					   while(it.hasNext()) {
						   randomFile.write(it.next().toString().getBytes("gbk")); 
					   }
					   randomFile.close();
				   }    
			  }
			  if(type==0){
				   File file = new File("/Users/apple/Desktop/html/"+API.API.count+++".html");
				   FileOutputStream outStream = new FileOutputStream(file);
				   OutputStreamWriter outputstreamwriter =new OutputStreamWriter(outStream,"gbk");	   
				   if(list!=null){
					   Iterator it = list.iterator();
					   while(it.hasNext()) {
						  outputstreamwriter.write(it.next().toString());
					   }
				   }     
				   outputstreamwriter.close();
				   outStream.close();
			  }
		  } catch (Exception e) {
		   e.printStackTrace();
		  }    

		 }
	
}
