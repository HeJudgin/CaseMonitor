package com.server.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;


import com.jfinal.plugin.activerecord.Record;
import com.server.model.DataModel;
import com.server.model.PbaModel;

public class StringUtils {
	
	public static StringUtils inst = null;
	private static Logger logger = Logger.getLogger(StringUtils.class);
	
	public static StringUtils getInstance(){
		if (inst == null){
			inst = new StringUtils();
		}
		return inst;
	}
	/**
	 * 获取头部菜单
	 * @param user_type
	 * @param page_index
	 * @return
	 */
	public String getTopMenuHtml(String user_type, String page_index){
		String html_str = "";
		String class_home, class_labeled, class_utils,class_report_tab ,class_visual_tab, class_blog_tab;
		class_home="";class_labeled="";class_utils="";class_report_tab=""; class_visual_tab=""; class_blog_tab="";
		if("visual".equals(page_index)){class_visual_tab="active";}
		if("main".equals(page_index)){class_home="active";}
		if("labeled".equals(page_index)){class_labeled="active";}
		if("utils".equals(page_index)){class_utils="active";}
		if("pba_table".equals(page_index)){class_report_tab="active";}
		if("tech_blog".equals(page_index)){class_blog_tab="active";}
		//html_str+="<li class=\""+class_home+"\"><a href=\"#\" onclick=\"getUnlabeledData();\" data-hover=\"Home\">去标注</a></li>";
		if("999".equals(user_type.toString()) || "111".equals(user_type.toString()) || "110".equals(user_type.toString()) ){
		    
		}
		html_str+="<li class=\""+class_visual_tab+"\"><a data-hover=\"Photos\" href=\"visual.html\">可视化</a></li>";
		html_str+="<li class=\""+class_home+"\"><a data-hover=\"Home\" href=\"main.html\">去标注</a></li>";
		html_str+="<li class=\""+class_labeled+"\"><a data-hover=\"Photos\" href=\"labeled.html\">已标注</a></li>";
		html_str+="<li class=\""+class_utils+"\"><a data-hover=\"Photos\" href=\"utils.html\">工具</a></li>";
		//999 admin,  111 组内, 110 可访问查询系统数据, 0普通用户
		if ("999".equals(user_type.toString()) || "111".equals(user_type.toString()) || "110".equals(user_type.toString())){
		    html_str+="<li class=\""+class_report_tab+"\"><a data-hover=\"Photos\" href=\"table_report.html\">监测</a></li>";
		}
		html_str+="<li class=\""+class_blog_tab+"\"><a data-hover=\"Photos\" href=\"editor_test.html\">TechBlog</a></li>";
		return html_str;
	}
	
	/**
	 * 
	 * @param rets
	 * @param rets_comp
	 * @param qry_type
	 * @return
	 */
	public String getPbaHtml(List<Record> rets, List<Record> rets_comp, String qry_type){
		
		HashMap<String, String> fac_num_hash = getFacNumHash();
		String font_size = "1";
		String titles[] = {"产品标签","流量标签","前卡PV","详情PV", "前卡点击率","详情消耗",
				"搜索PV","搜索PV2","搜索PV3","搜索点击","搜索消耗", "搜索CTR3(%)",
				"推荐PV","推荐PV2","推荐PV3","推荐点击","推荐消耗", "推荐CTR3(%)","操作"};
		String html_str = "<caption>监控数据</caption><thead><tr>";
		for(String title : titles){
			html_str+=String.format("<th><font size=\"%s\">%s</th>", font_size, title);
		}
		html_str+="</tr></thead>";
		html_str+="<tbody>";
		HashMap<String, PbaModel> map = getInstance().getLastDayDateHash(rets_comp);
		PbaModel pbaModel = null;
		PbaModel pbaModel_comp = null;
		int idx = 0;
		String[] styles = {"active", "danger", "warning", "success", "", "info"};
		
		for(Record re : rets){
			pbaModel = new PbaModel(re);
			pbaModel_comp = new PbaModel(re); 
			//System.out.println(re.toJson());
			//System.out.println(styles[idx%styles.length]);
			String key = pbaModel.getPro_tag()+"\t"+pbaModel.getSq_tag();
			if(map.containsKey(key)){
				pbaModel_comp = map.get(key);
			}
			if("avg".equals(qry_type)){
				//System.out.println(qry_type);
				//System.out.println(fac_num_hash.get(pbaModel.getSq_tag()));
			    pbaModel.div_model(fac_num_hash.get(pbaModel.getSq_tag()));
			    pbaModel_comp.div_model(fac_num_hash.get(pbaModel.getSq_tag()));
			}
			html_str+="<tr class=\""+styles[idx++%styles.length]+"\">";
			html_str+="<th><font size=\""+font_size+"\">"+pbaModel.getPro_tag()+"</th>";
			html_str+="<th><font size=\""+font_size+"\">"+pbaModel.getSq_tag()+"</th>";
			html_str+= getValueMSG(pbaModel.getPv_pront(), pbaModel_comp.getPv_pront());  //"<th>"+pbaModel.getPv_pront()+"</th>";
			html_str+= getValueMSG(pbaModel.getPv().add(pbaModel.getPv_rec()), pbaModel_comp.getPv().add(pbaModel_comp.getPv_rec()));
			html_str+= getValueMSGFloat(pbaModel.getPv().doubleValue()/pbaModel.getPv_pront().doubleValue(), 
					pbaModel_comp.getPv().doubleValue()/pbaModel_comp.getPv_pront().doubleValue());
			html_str+= getValueMSG(pbaModel.getCost().add(pbaModel.getCost_rec()), pbaModel_comp.getCost().add(pbaModel_comp.getCost_rec()));
			
			html_str+= getValueMSG(pbaModel.getPv(), pbaModel_comp.getPv());
			html_str+= getValueMSG(pbaModel.getAdnum(), pbaModel_comp.getAdnum());
			html_str+= getValueMSG(pbaModel.getAdpv(), pbaModel_comp.getAdpv());
			html_str+= getValueMSG(pbaModel.getClk(), pbaModel_comp.getClk());
			html_str+= getValueMSG(pbaModel.getCost(), pbaModel_comp.getCost());
			html_str+= getValueMSGFloat(pbaModel.getClk().doubleValue()/pbaModel.getAdpv().doubleValue(), 
					pbaModel_comp.getClk().doubleValue()/pbaModel_comp.getAdpv().doubleValue());
			
			
			html_str+= getValueMSG(pbaModel.getPv_rec(), pbaModel_comp.getPv_rec());
			html_str+= getValueMSG(pbaModel.getAdnum_rec(), pbaModel_comp.getAdnum_rec());
			html_str+= getValueMSG(pbaModel.getAdpv_rec(), pbaModel_comp.getAdpv_rec());
			html_str+= getValueMSG(pbaModel.getClk_rec(), pbaModel_comp.getClk_rec());
			html_str+= getValueMSG(pbaModel.getCost_rec(), pbaModel_comp.getCost_rec());
			html_str+= getValueMSGFloat(pbaModel.getClk_rec().doubleValue()/pbaModel.getAdpv_rec().doubleValue(), 
					pbaModel_comp.getClk_rec().doubleValue()/pbaModel_comp.getAdpv_rec().doubleValue());
			
			html_str+="<th>"+"<a onclick=\"#\"><font size=\""+font_size+"\">曲线</font></a>"+"</th>";
			html_str+="</tr>";
		}
		html_str+="</tr></tbody>";
		return html_str;
	}
	
    public String getTableReportHtml(List<Record> rets, List<Record> rets_comp, List<String> titles,
    		List<String> colums_show, List<String> cols, String search_cond){
		
    	cols.add("_cnt_records");
    	colums_show.add("_cnt_records@0");
    	titles.add("记录数");
    	titles.add("曲线");
    	
		String font_size = "1";
		
		String html_str = "<caption>监控数据(查询条件:"+search_cond+")</caption><thead><tr>";
		for(String title : titles){
			html_str+=String.format("<th><font size=\"%s\">%s</th>", font_size, title);
		}
		html_str+="</tr></thead>";
		html_str+="<tbody>";
		HashMap<String, Record> map = getInstance().getCompDataHash(rets_comp);
		int idx = 0;
		String[] styles = {"active", "danger", "warning", "success", "", "info"};
		
		for(Record re : rets){
			Record re_comp = re;
			if(map.containsKey(re.getStr("channel"))){
				re_comp = map.get(re.getStr("channel"));  
			}
			html_str+="<tr class=\""+styles[idx++%styles.length]+"\">";
			html_str+="<th><font size=\""+font_size+"\">"+re.getStr("channel")+"</th>";
			for(int i=1; i<colums_show.size(); i++ ){
				html_str+=getValueMSGItems(colums_show.get(i), cols, re, re_comp);
			}
			html_str+="<th>"+"<a onclick=\"#\"><font size=\""+font_size+"\">曲线</font></a>"+"</th>";
			html_str+="</tr>";
		}
		html_str+="</tr></tbody>";
		return html_str;
	}
	
	
	public HashMap<String, String> getImgsTabs(String urls[], String tags[], String[] scores){
		String[] styles = {"active", "danger", "warning", "success", "", "info"};
		String html_str = "<tr class=\"active\">";
		int col = 3;
		int hight = (int)(150 * (5/(float)(col)));
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i=0;i <urls.length;i++){
            String url = urls[i]; //.split("url=")[urls[i].split("url=").length-1];
            String tag = tags[i];  //.substring(0, 10)+"...";
            String score = scores[i];
            String tag_sub = tag;
            String score_sub = score;
            if (tag.length() > 20){
            	tag_sub = tags[i].substring(0, 20)+"...";
            }
            if (score_sub.length() > 40){
            	score_sub = scores[i].substring(0, 25)+"...";
            }
            String html_item = "" ;
            html_item += String.format("<td align=\"center\"><img "
            		+ "style=\" border-radius:10%%; height:%dpx; \" alt=\"%s\" src=\"%s\" >"
            		+ "<br><span  class=\"contant\" align=\"left\" title=\"%s\" ><font size=\"2\" color=\"red\">%s</font><span >"
            		+ "<br><span  class=\"contant\" align=\"left\" title=\"%s\" >%s<span >", 
            		hight, tag, url, score, score_sub, tag, tag_sub);
            html_item += String.format("</td>");
            if((i)%col == 0 && i!=0){  
            	html_item = String.format("</tr><tr class=\"%s\">", styles[((i+1)/col)%styles.length]) 
            			+ html_item; 
            	};
            html_str+=html_item;
		}
		map.put("html_str", html_str);
		System.out.println(html_str);
		return map;
	}
	
	
	public HashMap<String, String> getImgsGrids(String urls[], String tags[], String[] scores, String ckid, String ccid, String recall_type){
		String[] styles = {"active", "danger", "warning", "success", "", "info"};
		String html_str = "";
		String label = "CKID="+ckid+" CAID="+ccid;
		if("2410243587".equals(ckid)){  label += "(特殊广告)"; }
		label = "<button lab=\"title\" style=\"fontSize:15px\"  type=\"button\" class=\"btn btn-1 btn-info\">"+label+"</button>";
		html_str += "<div class=\"clearfix\"></div><div style=\"boder:3px\"></div><hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />"
				+ label+"<hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />"; 
		int col = 3;
		int hight = (int)(150 * (5/(float)(col)));
		HashMap<String, String> map = new HashMap<String, String>();
		for(int i=0;i <urls.length;i++){
            String url = urls[i]; //.split("url=")[urls[i].split("url=").length-1];
            String tag = tags[i];  //.substring(0, 10)+"...";
            String score = scores[i];
			String r_type = score.split(" ")[1];
			//System.out.println(r_type);
			if(recall_type.contains(r_type.trim())==false && recall_type.contains("ALL")==false){ 
				System.out.println(recall_type+" VS "+r_type.trim());
				System.out.println(recall_type.contains(r_type.trim()) + "  " + recall_type.contains("ALL"));
				continue; 
		    }
            String tag_sub = tag;
            String score_sub = score;
            if (tag.length() > 200){
            	tag = tags[i].substring(0, 200)+"...";
            }
            if (tag.length() > 100){
            	tag_sub = tags[i].substring(0, 100)+"...";
            }
            if (score.length() > 40){
            	score_sub = scores[i].substring(0, 40)+"...";
            }
            /**
             * 蒙层，抖动特效
             */
            String html_item = "<div class=\"col-md-4 couple-grid\" style=\"margin_bottom:20px; \">";
            html_item += String.format("<div class=\"couple-top\"><div class=\"couple-img  multi-gd-text\"><a href=\"%s\">"
            		+ "<img src=\"%s\" class=\"img-responsive\" alt=\"\"></a></div>"
            		+ "<div class=\"couple-text hvr-wobble-bottom\"><h6>%s</h6><h4>%s</h4><span class=\"contant\" title=\"%s\">%s</span></div></div></div>",
            		url, url, score_sub, "", tag, tag_sub);
            html_str+=html_item;
		}
		map.put("html_str", html_str);
		System.out.println(html_str);
		return map;
	}
	
	public HashMap<String, String> getTextFlows(String tags[], String[] scores, String ckid, String ccid, String[] mattypes){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] styles = {"danger", "warning", "success", "primary", "info"};
		String html_str = "";
		String label_base = "CKID="+ckid+" CAID="+ccid;
		if("2410243587".equals(ckid)){  label_base += "(特殊广告)"; }
		HashMap<String, String> matTYPE = new HashMap<String, String>();
		matTYPE.put("7", "短子链组");matTYPE.put("9", "单图");matTYPE.put("11", "短子链");matTYPE.put("37", "长子链");
		matTYPE.put("8", "长子链组");matTYPE.put("5", "大图");matTYPE.put("18", "多图");matTYPE.put("19", "皇冠列表");
		matTYPE.put("20", "皇冠通用词");matTYPE.put("52", "图集");matTYPE.put("53", "视频");
		String label = "";
		String last_mat_type = "";
		for(int i = 0; i < tags.length; i++){
			if(!last_mat_type.equals(mattypes[i])){
				last_mat_type = mattypes[i];
				if(matTYPE.get(last_mat_type) != null) {
					label = label_base + "【" + matTYPE.get(last_mat_type) + "】";
				}else{
					label = label_base + "【" + last_mat_type + "】";
				}

				label = "<button lab=\"title\" style=\"fontSize:15px\"  type=\"button\" class=\"btn btn-1 btn-info\">"+label+"</button>";
				html_str += "<div class=\"clearfix\"></div><div style=\"boder:3px\"></div><hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />"
						+ label+"<hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />"; 
				
			}
		    html_str += "<button lab=\"title\" style=\"fontSize:15px\"  type=\"button\" class=\"btn btn-1 btn-"+styles[i%styles.length]+"\">"+tags[i]+"("+scores[i]+")"+"</button><br><br>&nbsp;";
		}
		map.put("html_str", html_str);
		return map;

	}
	
	public List<String> getUsedColums(String col_show){
		List<String> res = new ArrayList<String>();
		String items[] = col_show.split("@|\\+|\\-|\\*|\\/|\\(|\\)|Math.abs");
		for(int i=0; i <items.length-1; i++){
			String ite = items[i];
			if(!"+-/*".contains(ite) && !isNumeric(ite)){
				res.add(ite);
			}
		}
		return res;
	}
	
	
	/*
	 * 获取对比信息
	 */
	public HashMap<String, Record> getCompDataHash(List<Record> rets_comp){
		HashMap<String, Record> map = new HashMap<String, Record>();
		for(Record re : rets_comp){
			String key = re.getStr("channel");
			map.put(key, re);
		}
		return map;
	}   
	
	
	public HashMap<String, PbaModel> getLastDayDateHash(List<Record> rets_comp){
		HashMap<String, PbaModel> map = new HashMap<String, PbaModel>();
		for(Record re : rets_comp){
			String key = re.getStr("pro_tag")+"\t"+re.getStr("sq_tag");
			map.put(key, new PbaModel(re));
		}
		return map;
	}
	
	public String getValueMSG(BigInteger value, BigInteger value_comp){
		String msg = "";
		float rate = (value.subtract(value_comp)).floatValue() / value_comp.floatValue(); 
		String color = rate > 0 ? "red" : "green";
		String size = "1";
		//logger.info(String.format("%s, %s, %s", value.toString(), value_comp.toString(), rate+""));
		msg = String.format("<th><font size=\"%s\">%s</font>(<font size=\"%s\" color=\"%s\">%.1f %%</font>)</th>", size, value.toString(), size, color, rate*100);
		return msg;
	}
	
	public String getValueMSGFloat(double value, double value_comp){
		String msg = "";
		float rate = (float) ((value-value_comp) / value_comp); 
		String color = rate > 0 ? "red" : "green";
		String size = "1";
		//logger.info(String.format("%s, %s, %s", value.toString(), value_comp.toString(), rate+""));
		msg = String.format("<th><font size=\"%s\">%.2f %%</font>(<font size=\"%s\" color=\"%s\">%.1f %%</font>)</th>", size, value*100, size, color, rate*100);
		return msg;
	}
	public String getValueMSGItems(String colums_show, List<String> cols, Record re, Record re_comp){
		String label_format = colums_show.substring(colums_show.lastIndexOf("@")+1);
		colums_show = colums_show.substring(0, colums_show.lastIndexOf("@"));
		String colums_show_comp = colums_show;
		String items[] = colums_show.split("@|\\(|\\)");
		
		for(String ite : cols){
			if(re.get(ite) != null){
				colums_show = colums_show.replace(ite, re.get(ite).toString());
			}else{
				colums_show = colums_show.replace(ite, "1");
			}
			
			if(re_comp.get(ite) != null){
			    colums_show_comp = colums_show_comp.replace(ite, re_comp.get(ite).toString());
			}else{
				colums_show_comp = colums_show_comp.replace(ite, "1");
			}
		}
		double value = eval(colums_show+";");
		double value_comp = eval(colums_show_comp+";");
		String msg = "";
		float rate = (float) ((value-value_comp) / value_comp); 
		String color = rate > 0 ? "red" : "green";
		String size = "1";
		//logger.info(String.format("%s, %s, %s", value.toString(), value_comp.toString(), rate+""));
		if(re.toString().equals(re_comp.toString())){
			msg = String.format("<th><font size=\"%s\">%."+label_format+"f</font></th>", size, value);
		}else{
			msg = String.format("<th><font size=\"%s\">%."+label_format+"f</font>(<font size=\"%s\" color=\"%s\">%."+label_format+"f %%</font>)</th>", size, value, size, color, rate*100);
		}
		return msg;
	}	
	
	/**
     * 使用java eval 计算字符串表达式
     *
     * @param str 符串表达式
     * @return double 类型的结果
     */
    public static Double eval(String str) {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine se = manager.getEngineByName("js");
        Double result = null;

        try {
            result = Double.parseDouble(se.eval(str).toString()) ;
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String getLogicSqlExpress(List<String> search_bys, String[] querys){
    	
    	String search_condition = "1 = 1";
    	if(search_bys.size() ==querys.length ){
    	    for(int i=0 ;i<search_bys.size(); i++){
    	    	String[] sub_searchs = querys[i].split("#");
    	    	String sub_condition = "(";
    	    	for(String sub_search : sub_searchs){
    	    		if(sub_search.startsWith("&^")){
    	    			sub_condition += " and " + search_bys.get(i)+" not like '%"+sub_search.substring(2)+"%'";
    	    		}else if(sub_search.startsWith("|^")){
    	    			sub_condition += " or " + search_bys.get(i) +" not like '%"+sub_search.substring(2)+"%'";
    	    		}else if(sub_search.startsWith("|")){
    	    			sub_condition += " or " + search_bys.get(i) +"  like '%"+sub_search.substring(1)+"%'";
    	    		}else if(sub_search.startsWith("&")){
    	    			sub_condition += " and " + search_bys.get(i) +"  like '%"+sub_search.substring(1)+"%'";
    	    		}else{
    	    			sub_condition += " " + search_bys.get(i) +"  like '%"+sub_search+"%'";
    	    		}
    	    	}
    	    	sub_condition += ")";
    	    	search_condition += " and "+sub_condition;
    	    }
    	}else{
    		search_condition = "1 != 1";
    	}
    	
    	return search_condition; 
    	
    }

	public String getUnlabeledHtml(List<DataModel> models){
		String html_str = "";
		int idx = 0;
		String row_tag = "success";
		for(DataModel model : models){
			String html = "";
			try {
				if(idx % 2 == 0){
					row_tag = "info";
				}else{
					row_tag = "success";
				}
				html+="<div id=\""+model.getId()+"\" class=\"alert alert-"+row_tag+"\" role=\"alert\">";
				String[] qry_msg = model.getQry_msg().toString().split("\1");
				String[] title_msg = model.getTitle_msg().toString().split("\1");
				for(String ite : qry_msg){
					//System.out.println(ite);
					String word = ite.split("\2")[0];
					String tag = ite.split("\2")[1];
					html+="<button lab=\"qry\" style=\"fontSize:15px\" onclick=\"addKwds(this);\" type=\"button\" class=\"btn btn-1 btn-"+getLabByWordTag(tag)+"\">"+word+"</button>&nbsp;";
				}
				html+="<strong>&nbsp;VS&nbsp;</strong>";
				for(String ite : title_msg){
					String word = ite.split("\2")[0];
					String tag = ite.split("\2")[1];
					html+="<button lab=\"title\" style=\"fontSize:15px\" onclick=\"addKwds(this);\" type=\"button\" class=\"btn btn-1 btn-"+getLabByWordTag(tag)+"\">"+word+"</button>&nbsp;";
				}
				//html+="<button id=\""+model.getId()+"_out\" type=\"button\" class=\"btn btn-sm btn-default\"></button>";
				html+="<br><br>";
				html+="<button lab=\"rel\" style=\"fontSize:15px\" onclick=\"addLabel(this, "+model.getId()+");\" type=\"button\" class=\"btn btn-1 btn-success\">2</button>&nbsp;";
				html+="<button lab=\"rel\" style=\"fontSize:15px\" onclick=\"addLabel(this, "+model.getId()+");\" type=\"button\" class=\"btn btn-1 btn-warning\">1</button>&nbsp;";
				html+="<button lab=\"rel\" style=\"fontSize:15px\" onclick=\"addLabel(this, "+model.getId()+");\" type=\"button\" class=\"btn btn-1 btn-primary\">0</button>&nbsp;";
				html+="<button lab=\"rel\" style=\"fontSize:15px\" onclick=\"addLabel(this, "+model.getId()+");\" type=\"button\" class=\"btn btn-1 btn-danger\">-1</button>&nbsp;";
				html+="<button onclick=\"submitRow("+model.getId()+");\" type=\"button\" class=\"btn btn-1 btn-info\">提交</button>&nbsp;";
				html+="</div>";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.warn(e.getMessage());
				continue;
			}
			idx ++;
			html_str += html;
		}
		
		
		return html_str;
	}
	
	
	public List<String> getLocalLabeledHtml(List<String> datas, String inp_labels, String lab_sep,
			String inp_head, String inp_title){
		
		List<String> htmls = new ArrayList<String>();
		String labs[] = inp_labels.split("@");
		String heads[] = inp_head.split("@");
		String html_head = "<h3 class=\"tittle\">"+inp_title+"</h3>"
				+ "<div style=\"width:89%; height:80%; overflow:scroll;  \" align=\"center\" ><table border=1 id=\"tab\" "
				+ "class=\"table table-bordered table-striped animated wow fadeInUp\" >"
				+ "<thead><th >Idx</th>";
		html_head = "<h3 class=\"tittle\">"+inp_title+"</h3>"
				+"<div class=\"table-responsive\"  style=\"width:99%; height:90%; overflow-y:auto; border: 1px solid #eee\" >"
				+ "<table class=\"table table-bordered table-striped animated wow fadeInUp \" "
				+ "id=\"tab\" style=\"display: table; table-layout:fixed; word-wrap: break-word; \" ><thead><th width=\"10%%\">Idx</th>";
		//width=\"50px\"
		int rate_sum = 0;
		for(int j = 0; j < heads.length; j++){
			int size = Integer.parseInt(heads[j].split(":")[2]);
			rate_sum += size;
		}
		for(int j = 0; j < heads.length; j++){
			String head = heads[j].split(":")[0];
			String type = heads[j].split(":")[1];
			int size = (int) (Float.parseFloat(heads[j].split(":")[2]) * 70 / rate_sum);
			if(size>0){
        	    html_head += String.format("<th width=\"%d%%\">%s</th>", size, head);
			}
		}
		html_head += "<th width=\"20%%\">Labels</th></thead>";
		htmls.add(html_head);
		for(int i = 0 ;i < datas.size(); i++){
			String html = "";
			String items[] = datas.get(i).split(lab_sep);
			if(items.length >= 2){
			    int width = 200;
		        //if(items.length > 3){
		    	//    width =  800 / (items.length-2);
		        //}
		        /*
			    String img = "";
		        for(int j = 2; j < items.length; j++){
		    	    img += String.format("<img  id=\"img_%d_%d\" src=\"%s\" style=\"width:%dpx; height:%dpx; padding:10px\" />", i+1, j, items[j], width, width);
		        }
		        */
		        String radios = "";
		        String id_jump = String.format("jump_focus('rad_%d_0')", i+2);
		        if( i == datas.size() - 1){ id_jump = String.format("jump_focus('rad_%d_0')", i+1); }
		        for(int j = 0; j < labs.length; j++){
		        	String stars = labs[j]+"&nbsp;&nbsp;";
		        	for(int k=j; k<labs.length; k++){
		        		stars+=String.format("<img src=\"../images/label_rate.png\" style=\"width:20px; height:20px; padding:2px\" /> ");
		        	}
		        	
		        	radios+=String.format("<p><label><input id=\"rad_%d_%d\" name=\"rad_%d\" type=\"radio\" value=\"%s\" onclick=\"%s\" /> %s</label></p>",
		        			i+1, j, i+1, labs[j], id_jump, stars);
		        }
		        html+=String.format("<tr id=\"%d\"><th height=\"30%%\" width=\"10%%\">%d</th>", i+1, i+1);
		        for(int j = 0; j < heads.length && j < items.length; j++){
		        	String head = heads[j].split(":")[0];
		        	String type = heads[j].split(":")[1];
		        	int size = (int) (Float.parseFloat(heads[j].split(":")[2]) * 70 / rate_sum);
		        	if(size <= 0){
		        		continue;
		        	}
		        	if("txt".equals(type)){
		        		html += String.format("<th height=\"30%%\" width=\"%d%%\">%s</th>", size, items[j]);
		        	}else if("img".equals(type)){
			    	    html += String.format("<th height=\"30%%\" width=\"%d%%\"><img  id=\"img_%d_%d\" src=\"%s\" style=\"width:%d%%; height:%dpx; padding:10px\" /></th>", size, i+1, j, items[j], 80, 150);
			    	    //html += String.format("<th width=\"50px\"><img  id=\"img_%d_%d\" src=\"%s\" style=\"width:%dpx; height:%dpx; padding:10px\" /></th>", i+1, j, items[j], width, width);
		        	}
		        }
		        html += String.format("<th width=\"20%%\"> <div>%s </div></th></tr>\n", radios);
			    //html+=String.format("<tr id=\"%d\"><th width=\"50px\">%d</th><th width=\"%s\">%s</th><th width=\"%s\">%s</th><th>%s</th>" +
                //"<th width=\"200px\"> <div>%s </div></th></tr>\n", i+1, i+1, heads[0].split(":")[2], items[0],items[1], img, radios);
			}else{
				System.out.println(items.length+", "+datas.get(i));
			}
			htmls.add(html);
		}
		
		htmls.add("</table></div><br><br><button class=\"btn btn-lg btn-default\" type=\"button\" value=\"write\"  onclick=\"click_button()\" style=\"width:99%\"> SUBMIT</button><br><br></html>");
		//System.out.println(htmls);
		return htmls;
	}


	public List<String> getLocalImageLabeledHtml(List<String> datas, String inp_title, String inp_seq){
		List<String> htmls = new ArrayList<>();
		String html_head = "<h3 class=\"tittle\">"+inp_title+"</h3>"
				+"<div class=\"table-responsive\"  style=\"width:99%; height:90%; overflow-y:auto; border: 1px solid #eee\" >"
				+ "<table class=\"table table-bordered table-striped animated wow fadeInUp \" "
				+ "id=\"tab\" style=\"display: table; table-layout:fixed; word-wrap: break-word; \" >" +
				"<thead><th width=\"5%%\">Idx</th><th width=\"25%%\">图片</th><th width=\"40%%\">候选词</th><th width=\"40%%\">补充词</th>";

    	htmls.add(html_head);
    	for(int i = 0; i < datas.size(); i++){
    		String html = "";
			html+=String.format("<tr id=\"%d\"><th height=\"30%%\" width=\"10%%\">%d</th>", i+1, i+1);
			String items[] = datas.get(i).split(inp_seq);
			String col = "<th id = \"trid_%d_%d\" height=\"40%%\" width=\"%d%%\">%s</th>";
			String choice_items = "";
			for(int j = 0; j < items.length; j++){
            	if(j == 0) {
					html += String.format("<th height=\"40%%\" width=\"%d%%\"><img  id=\"img_%d_%d\" src=\"%s\" style=\"width:%dpx; height:%dpx; padding:10px\" /></th>", 20, i + 1, 0, items[0], 220, 220);
				}else{
					choice_items += String.format("<label><input name=\"keys_%d\" type=\"checkbox\" value=\"%s\" />%s</label>",
							i + 1, items[j], items[j]);
				}
			}
			String search_items = String.format("<p><input id=\"search_key_%d\" type=\"text\" name=\"fname\" />" +
					"<input id=\"search_num_%d\" type=\"text\" size='2' name=\"fname\" value='20' />" +
					"<button type=\"button\" onclick=\"search_new_word(%d)\">search</button></p>" +
					"<p id=\"search_list_%d\"></p></tr>", i + 1, i + 1, i + 1, i + 1);
			html += String.format(col, i + 1, 2, 40, choice_items);
			html += String.format(col, i + 1, 3, 40, search_items);
			htmls.add(html);
		}
		htmls.add("</table></div><br><br><button class=\"btn btn-lg btn-default\" type=\"button\" value=\"write\"  onclick=\"click_button()\" style=\"width:99%\"> SUBMIT</button><br><br></html>");

		return htmls;
	}
	
	
	public String getLabByWordTag(String tag){
		//System.out.println(tag);
		if("100001".equals(tag)){
			return "danger";
		}
		if("100002".equals(tag)){
			return "warning";
		}
		if("100003".equals(tag)){
			return "success";
		}
		if("100004".equals(tag)){
			return "primary";
		}
		
		return "info";
		
	}
	
	public HashMap<String, String> getFacNumHash(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("A", "3");map.put("A_JS", "2");map.put("A_S", "3");
		map.put("B", "2");map.put("B_JS", "1");map.put("B_S", "2");
		map.put("S1", "1");map.put("SUM", "14");
		return map;
	}
	
	
	
	public static boolean isNumeric(String str){
		
	    for (int i = str.length();--i>=0;){  
	        if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.'){
	            return false;
	        }
	    }
	    System.out.println(str+" is number");
	    return true;
	}
	
	
	
	
	
	
	
	public String getLastYearStr(int num){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, num);
		Date y = c.getTime();
		String year = format.format(y);
		//System.out.println("过去"+num+"年："+year);
		return year;
	}
	
	public String getLastDaysStr(String date, int delta){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        //过去七天
        try {
			c.setTime(format.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        c.add(Calendar.DATE, -1*delta);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
		
	}
	
	
	public long getTimeInterval(String data_str){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = format.parse(data_str);
			Date now = new Date();
			long delta = now.getTime()-date.getTime();
			return delta;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println(getInstance().getLastYearStr(-3));
		
		//getInstance().getProcessedMsg("hdsjhdsj[emj:ABCDEFG]dhsj[emj:ABCDEFG][emj:ABCDEFG]dshjk");
	}
	
	

}
