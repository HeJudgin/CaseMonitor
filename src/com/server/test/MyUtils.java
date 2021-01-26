package com.server.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
	
	public static MyUtils utils;
	
	public static MyUtils getInstance(){
		if(utils==null){
			return new MyUtils();
		}else{
			return utils;
		}
	}
	
	
	public String getDataStr(Date date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(date);
		
	}

}
