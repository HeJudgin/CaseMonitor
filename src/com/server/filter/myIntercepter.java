package com.server.filter;

import java.util.HashMap;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.server.constant.Constant;
import com.server.model.UserModel;

public class myIntercepter implements Interceptor{

    public void intercept(Invocation inv) {
    	String key = inv.getControllerKey();
        String method = inv.getMethodName();
        //System.out.println(key+"-"+method);
        Controller controller = inv.getController();
        String user = controller.getSessionAttr("username");
        //System.out.println();
        //System.out.println(("loginUser".equals(method)));
        if(method.contains("image_svr")){
        	inv.invoke();
        }
        if(user==null){
        	//System.out.println("FILTER....");
        	String contxt = controller.getRequest().getContextPath();
        	String url = controller.getRequest().getRequestURL().toString().split(contxt)[0]+contxt;
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	map.put("status", "failed");
        	map.put("ret", -1);
        	map.put("url", url);
        	//controller.redirect("/user/login_page");
        	controller.renderJson(map);
        	return;
        }
        //System.out.println("send "+key+"-"+method);
        inv.invoke();
    }
}