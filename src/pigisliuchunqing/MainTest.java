package pigisliuchunqing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpException;

import Database.VisitedDB;
import Webspader.DownloadPage;
import Webspader.WriteObject;

import com.sleepycat.je.DatabaseException;

import Entity.Page;
import URLget.HtmlParserTool;

public class MainTest {
    public static void main(String[] args){
    	
    	try {
			VisitedDB v=new VisitedDB("/Users/apple/Desktop/java/Spader/db");
			int i=v.count();
			System.out.println(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}