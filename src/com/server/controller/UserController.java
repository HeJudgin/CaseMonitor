package com.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.server.constant.Constant;
import com.server.dao.MyDao;
import com.server.filter.myIntercepter;
import com.server.model.UserModel;
import com.server.test.MyStringUtil;
import com.server.utils.StringUtils;

public class UserController extends Controller {

	MyDao myDao = new MyDao();

	// user/getBeans
	public void getBeans() {
		renderJsp("/index.jsp");
		// AutoCreateEntity.process("D:\\coding_work_space\\MyEclipse2015CI\\myeworkspace\\JW_SCMS\\src",
		// "com.server.bean", true,true);

	}

	/**
	 * 网络请求 地址 接收穿参数 返回json
	 * 
	 * 登录验证
	 */
	@Clear(myIntercepter.class)
	public void login() {

		String username = getPara("username");
		String pwd = getPara("pwd");
		String lab_tag = getPara("lab_tag");
		if("".equals(lab_tag.toString())){
			lab_tag = "huazhang";
		}else{
			lab_tag = username+"_"+lab_tag;
		}
		Record re = myDao.userLogin(username, pwd);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(re != null){
			map.put("status", "success");
			map.put("msg", "登录成功");
			myDao.updateUser(username);
			this.setSessionAttr("last_login", StringUtils.getInstance().getLastYearStr(0));
			this.setSessionAttr("username", re.get("username"));
			this.setSessionAttr("lab_tag", lab_tag);
			String user_type = re.getStr("usertype");
			if("999".equals(user_type.toString()) || "111".equals(user_type.toString()) || "110".equals(user_type.toString())){
				this.redirect("/visual.html");
			}else{
				this.redirect("/main.html");
			}
			
		}else{
			map.put("status", "failed");
			map.put("msg", "用户名密码错误");
			this.redirect("/login.html");
		}
		//this.renderJson();
	}
	
	@Clear(myIntercepter.class)
	public void register(){
		String username = getPara("username");
		String pwd = getPara("pwd");
		int usertype = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		Record re = myDao.getUserById(username);
		int row = 0;
		if(re == null){
		    row = myDao.userRegister(username, pwd, usertype);
		}
		map.put("username", username);
		map.put("pwd", pwd);
		if(row < 1 && re == null){
			map.put("status", "failed");
			map.put("msg", "注册失败");
		}else if(re != null){
			map.put("status", "failed");
			map.put("msg", "账户已存在");
		}else{
			map.put("status", "success");
		}
		System.out.println(map.toString());
		this.renderJson(map);
	}
	/**
	 * 获取前台显示用户名
	 */
	public void getSessionAttr(){
		String  page_index = getPara("page_index");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String user_id = getSessionAttr("username");
		String last_login = getSessionAttr("last_login");
		Record user_record = myDao.getUserById(user_id);
		String tag = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString(); 
		List<Record> reds_now = myDao.getLabHisByUserIdAndDate(user_id, tag,
				last_login);
		
		List<Record> reds_today = myDao.getLabHisByUserIdAndDate(user_id, tag,
				StringUtils.getInstance().getLastYearStr(0).substring(0, 10)+" 00:00:00");
		
		float min = (float)StringUtils.getInstance().getTimeInterval(last_login)/(1000*60);
		
		String top_menu_html = StringUtils.getInstance().getTopMenuHtml(user_record.getStr("usertype"), page_index);
		
		map.put("username", user_id+"-"+tag);
		String msg = "当日标注数:"+reds_today.size()+", 本次标注数:"+reds_now.size()+
				",持续时长:"+String.format("%10.2f min",min)+", 速度:"+String.format("%10.2f/min",((float)reds_now.size()/(min)));
		map.put("label_msg", msg);
		map.put("top_menu_html", top_menu_html);
		map.put("foot_msg", "copyright@2019 hejiajin@sogou-inc.com");
		this.renderJson(map);
	}
	@Clear(myIntercepter.class)
	public void updateUserType(){
		String username = getPara("username");
		int usertype = getParaToInt("usertype");
		int row = myDao.updateUserType(username, usertype);
		HashMap<String, Object> map = new HashMap<String, Object>();
		this.renderJson(map);
	}
	
	
	@Clear(myIntercepter.class)
	public void updateUserPwd(){
		String username = getPara("username");
		String pwd = getPara("pwd");
		int row = myDao.updateUserPwd(username, pwd);
		HashMap<String, Object> map = new HashMap<String, Object>();
		this.renderJson(map);
	}
	
	
	
	
	@Clear(myIntercepter.class)
	public void login_page() {
		System.out.println("login_page");
		this.redirect("/login.html");
		System.out.println("---jump--");
	
	}
	
	
	
	
	@Clear(myIntercepter.class)
	public void logout() {
		this.removeSessionAttr("username");
		this.removeSessionAttr("lab_tag");
		this.removeSessionAttr("last_login");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String contxt = this.getRequest().getContextPath();
		String url = this.getRequest().getRequestURL().toString().split(contxt)[0]+contxt;
		map.put("url", url);
		this.renderJson(map);
	}

	
	
	
}
