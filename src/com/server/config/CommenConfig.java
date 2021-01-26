package com.server.config;

import java.io.File;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.mchange.v2.c3p0.cfg.C3P0Config;
import com.server.constant.Constant;
import com.server.controller.DataController;
import com.server.controller.TestController;
import com.server.controller.UserController;
import com.server.filter.myIntercepter;

public class CommenConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setBaseViewPath("/WEB-INF");  
        me.setUrlParaSeparator("-");  
        me.setBaseDownloadPath("/download"); 
        me.setBaseUploadPath(Constant.CASE_FILE_PATH);
        me.setError401View("/WEB-INF/jsp/error.jsp");  
        me.setError403View("/WEB-INF/jsp/error.jsp");  
        me.setError404View("/WEB-INF/jsp/error.jsp");  
        me.setError500View("/WEB-INF/jsp/error.jsp");  
        me.setErrorView(404, "/WEB-INF/jsp/error.jsp");  
     
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub  
		me.add("/", TestController.class);
		me.add("/user", UserController.class);
		me.add("/data", DataController.class);
		
		
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub  C3p0Plugin
		DruidPlugin db = new DruidPlugin(getProperty("jdbcUrl").trim(), getProperty("user").trim(), getProperty("password").trim());
		me.add(db);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(db);
		me.add(arp);
		
		/**
		C3p0Plugin  c3p0 = new C3p0Plugin(getProperty("jdbcUrl").trim(), getProperty("user").trim(), getProperty("password").trim());
		me.add(c3p0);
		System.out.println(getProperty("user").trim()+", "+getProperty("password").trim());
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0);
		me.add(activeRecordPlugin);
		
		*/
	
       
		

		
	} 

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		me.add(new myIntercepter());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}
	
  
    public static void main(String[] args) {  
        JFinal.start("WebRoot", 90, "/", 5);  
    }  

}
