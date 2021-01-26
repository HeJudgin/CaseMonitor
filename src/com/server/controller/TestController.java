package com.server.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.server.filter.myIntercepter;

public class TestController extends Controller {
	@Clear(myIntercepter.class)
	public void index(){
		
		this.renderJsp("/login.html");
		
	}

}
