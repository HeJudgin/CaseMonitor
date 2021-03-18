package com.server.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.server.constant.Constant;
import com.server.dao.MyDao;
import com.server.filter.myIntercepter;
import com.server.model.DataModel;
import com.server.model.ImgModel;
import com.server.model.LabHisModel;
import com.server.model.SampleModel;
import com.server.model.SimModel;
import com.server.model.TdmModel;
import com.server.model.TdmRespModel;
import com.server.utils.FileService;
import com.server.utils.SortByLengthComparator;
import com.server.utils.StringUtils;

public class DataController extends Controller{
	private static Logger logger = Logger.getLogger(DataController.class);
	MyDao myDao = new MyDao();
	Gson gson = new Gson();
	
	@Before(Tx.class)
	public void label_case_upload(){
        UploadFile uploadFile=this.getFile("f_upload");
        File file=uploadFile.getFile();    
        String userid = getSessionAttr("username");
        String tag = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString();  //getPara("tag");
        //tag = userid+"_"+tag;
        String fileName=uploadFile.getOriginalFileName();
        System.out.println(tag+","+userid+","+fileName);
        FileService fs=new FileService();    
        fileName = Constant.CASE_FILE_PATH+userid+"_"+UUID.randomUUID().toString();
        File t=new File(fileName);
        try {
            t.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fs.fileChannelCopy(file, t);
        file.delete();
        List<String> lines = fs.readFileForLines(fileName);
        HashMap<String, Object>  map = new HashMap<String, Object>();
        String info = "";
        try{
            map = myDao.batchAddLabelData(lines, tag);
            map.put("status", "SUCCESS");
        }catch (Exception e) {
			// TODO: handle exception
        	map.put("save_rows", 0);
        	map.put("inp_rows", lines.size());
        	map.put("status", "FAILD!!!!!!");
		}
        info = "STATUS:"+map.get("status")+", Success Rows:"+map.get("save_rows")+", Input Rows:"+map.get("inp_rows")+", 点击返回"; 
		//this.renderJavascript("<a href=\"/utils.html\">"+"Success Rows:"+map.get("save_rows")+", Input Rows:"+map.get("inp_rows")+"</a>");
        this.setAttr("msg", info);
        this.render("/alert.jsp");
	}
	
	@Before(Tx.class)
	public void local_label_upload(){
        UploadFile uploadFile=this.getFile("f_upload");
        String inp_labels = getPara("inp_labels");
        String inp_num = getPara("inp_num");
        String inp_sep = getPara("inp_sep");
        String inp_head = getPara("inp_head");
        String inp_title = getPara("inp_title");
        File file=uploadFile.getFile();    
        String userid = getSessionAttr("username");
        String tag = this.getSessionAttr("lab_tag") == null ? "label" : this.getSessionAttr("lab_tag").toString();  //getPara("tag");
        //tag = userid+"_"+tag;
        String fileName=uploadFile.getOriginalFileName();
        System.out.println(tag+","+userid+","+fileName);
        FileService fs = new FileService();    
        fileName = Constant.CASE_FILE_PATH+userid+"_local_labled";
        String des_path_tail = "labels"+File.separator+userid+"_label_"
                +new SimpleDateFormat("yyyyMMdd").format(new Date()).toString()+"_"+inp_num+".html";
        String des_path = Constant.CASE_FILE_PATH + des_path_tail; //Constant.HOME_URL+des_path_tail;
        File t=new File(fileName);
        try {
            t.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fs.fileChannelCopy(file, t);
        file.delete();
        System.out.println(des_path);
        fs.write2html(fileName, des_path, inp_labels, inp_sep, inp_head, inp_title);
        List<String> lines = fs.readFileForLines(fileName);
        HashMap<String, Object>  map = new HashMap<String, Object>();
        String info = "";
        try{
        	map.put("url", des_path);
        	map.put("inp_rows", lines.size());
            map.put("status", "SUCCESS");
        }catch (Exception e) {
			// TODO: handle exception
        	map.put("inp_rows", lines.size());
        	map.put("status", "FAILD!!!!!!");
		}
        info = "STATUS:"+map.get("status")+", Input Rows:"+map.get("inp_rows")+", 点击返回"; 
        System.out.println(Constant.HOME_URL);
        System.out.println(File.separator.toCharArray()[0]);
		//this.renderJavascript("<a href=\"/utils.html\">"+"Success Rows:"+map.get("save_rows")+", Input Rows:"+map.get("inp_rows")+"</a>");
        this.setAttr("msg", info);
        this.setAttr("url", Constant.HOME_URL.substring(Constant.HOME_URL.lastIndexOf(File.separator.toCharArray()[0]))+File.separator+des_path_tail);
        this.render("/alert.jsp");
	}

	//get Image caption data
	//@Before(Tx.class)
	@Clear(myIntercepter.class)
	public void local_image_label_upload(){
		UploadFile uploadFile=this.getFile("f_upload");
		String inp_num = getPara("inp_num");
		String inp_sep = getPara("inp_sep");
		File file=uploadFile.getFile();
		String userid = getSessionAttr("username");
		String tag = this.getSessionAttr("lab_tag") == null ? "label" : this.getSessionAttr("lab_tag").toString();  //getPara("tag");
		String des_path_tail = "labels"+File.separator+userid+"_label_"
				+new SimpleDateFormat("yyyyMMdd").format(new Date()).toString()+"_"+inp_num+".html";
		String des_path = Constant.CASE_FILE_PATH + des_path_tail; //Constant.HOME_URL+des_path_tail;
		String fileName = Constant.CASE_FILE_PATH+userid+"_local_labled";
		FileService fs = new FileService();
		File t=new File(fileName);
		try {
			t.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fs.fileChannelCopy(file, t);
		file.delete();
		System.out.println(des_path);
		fs.write2html(fileName, des_path, inp_sep);
		List<String> lines = fs.readFileForLines(fileName);
		HashMap<String, Object>  map = new HashMap<String, Object>();
		String info = "";
		try{
			map.put("url", des_path);
			map.put("inp_rows", lines.size());
			map.put("status", "SUCCESS");
		}catch (Exception e) {
			// TODO: handle exception
			map.put("inp_rows", lines.size());
			map.put("status", "FAILD!!!!!!");
		}
		info = "STATUS:"+map.get("status")+", Input Rows:"+map.get("inp_rows")+", 点击返回";
		this.setAttr("msg", info);
		this.setAttr("url", Constant.HOME_URL.substring(Constant.HOME_URL.lastIndexOf(File.separator.toCharArray()[0]))+File.separator+des_path_tail);
		this.render("/alert.jsp");
	}


	
	public void file_case_upload(){
        UploadFile uploadFile=this.getFile("f_upload");
        File file=uploadFile.getFile();    
        String userid = getSessionAttr("username");
        String fileName=uploadFile.getOriginalFileName();
        System.out.println(userid+","+fileName);
        FileService fs=new FileService();    
        fileName = Constant.CASE_FILE_PATH+userid+"_"+"cut_file"+"_"+UUID.randomUUID().toString()+"_"+uploadFile.getOriginalFileName();
        File t=new File(fileName);
        try {
            t.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fs.fileChannelCopy(file, t);
        file.delete();
        String info = "返回文件地址";
        this.setAttr("msg", info);
        this.render("/alert.jsp");
	}
	
	
	@Clear(myIntercepter.class)
	public void test_data(){
		//this.renderJavascript("<a href=\"/utils.html\">"+"SUCCESS"+"</a>");
		String info = "Success";
		this.setAttr("msg", info);
        this.render("/alert.jsp");
	}
	
	public void getUnLabeledData(){
		
		String tab_name = "data_msg";
		String data_source = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString();
		String userid = getSessionAttr("username");
		int num = getParaToInt("num");
		List<DataModel> dataModels = myDao.getUnlabeledData(tab_name, data_source, userid, num);
		//this.renderJson(dataModels);
		String html = StringUtils.getInstance().getUnlabeledHtml(dataModels);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("html", html);
		map.put("status", "success");
		this.renderJson(map);
	}
	
	public void setUnlabeledData(){
		
		String userid = getSessionAttr("username");
		int res_id = getParaToInt("res_id");
		Object rel_val = getPara("rel_val");
		Object kwds = getPara("kwds");
		Object entitys = getPara("entitys");
		Object exts = getPara("exts");
		Object date = new Date();
		Object tab = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString();
		LabHisModel labHisModelInp = new LabHisModel(res_id, userid, rel_val, kwds, entitys, exts, date, tab);
		LabHisModel labHisModel = myDao.getLabHisById(res_id, userid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		if(labHisModel==null){
			int row = myDao.addLabelData(labHisModelInp);
			logger.info("新增标注结果:"+row);
			if(row > 0){
				map.put("status", "success");
			}
		}else{
			int row = myDao.updLabelData(labHisModelInp);
			logger.info("修改标注结果:"+row);
			if(row > 0){
				map.put("status", "success");
			}
		}
		this.renderJson(map);
	}
	
	/**
	 * http://localhost:8080/CaseLab1.0/data/getLabeledData?tab=huazhang&pageNumber=1&pageSize=20
	 */
	public void getLabeledData(){
		String user_id = getSessionAttr("username");
		String tab = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString();
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		Page<Record> models = myDao.getLabHisByUserId(user_id, tab, pageNumber, pageSize);
		this.renderJson(models);
		
	}
	
	public void exportLabeledData(){
		
		String user_id = getSessionAttr("username");
		String tab = this.getSessionAttr("lab_tag") == null ? "huazhang" : this.getSessionAttr("lab_tag").toString();
		
		List<Record> models = myDao.getLabHisByUserIdAndDate(user_id, tab, StringUtils.getInstance().getLastYearStr(-10));
		this.getResponse().setContentType("application/vnd.ms-excel");  
		this.getResponse().setHeader("content-disposition", "attachment;filename=" + tab+"-"+System.currentTimeMillis() + ".xls");
		
		OutputStream out = null;
		// 产生工作簿对象  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        //产生工作表对象  
        HSSFSheet sheet = workbook.createSheet(); 
		String []head = {"query","title","query重点词","title重点词","相关性","标注时间","标注人员"};
		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < head.length; i++) {
			HSSFCell cell = headRow.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
         cell.setCellValue(head[i]);
		}
		
		int row = 1;
		String kwds[] = {};
		for(Record re:models){
			kwds = re.getStr("kwds").split("\1");
			if(kwds.length < 2){ logger.info("MISS"+re.getStr("kwds")); continue;  }
			int col = 0;
			HSSFRow temrow = sheet.createRow(row ++);
			HSSFCell cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(re.getStr("query"));
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(re.getStr("title"));
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(kwds[0].replaceAll("\2", ","));
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(kwds[1].replaceAll("\2", ","));
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(re.getStr("rel_val").replaceAll("\2", ""));
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(re.get("date").toString());
			
			cell = temrow.createCell(col ++);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(re.getStr("user_id"));
			
		}
		
		try {
			out = this.getResponse().getOutputStream();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally  
	     {  
	         try  
	         {  
	             out.flush();  
	             out.close();  
	         }  
	         catch (IOException e)  
	         {}  
	     }
		
		this.renderNull();
		
		
	}
	
	
	/**
	 * http://localhost:8080/CaseLab1.0/data/delLabeledDataById?res_id=331
	 */
	public void delLabeledDataById(){
		String res_id = getPara("res_id");
		String user_id = getSessionAttr("username");
		int row = myDao.delLabeledDataById(res_id, user_id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		logger.info("修改标注结果:"+row);
		if(row > 0){
			map.put("status", "success");
		}
		this.renderJson(map);
	}
	
	
	/**
	 * http://localhost:8080/CaseLab1.0/data/getPbaMonitorData?start_date=20190521&&end_date=20190522&&start_hour=0&&end_hour=12&&comp_days=1
	 */
	//@Clear(myIntercepter.class)
	public void getPbaMonitorData(){
		Object start_date = getPara("start_date");
		Object end_date = getPara("end_date");
		Object start_hour = "0"+getPara("start_hour");
		Object end_hour = "0"+getPara("end_hour");
		int comp_days = getParaToInt("comp_days");
		String qry_type = getPara("qry_type");
		start_hour = start_hour.toString().substring(start_hour.toString().length()-2);
		end_hour = end_hour.toString().substring(end_hour.toString().length()-2);
		String log_str = start_date+"\t"+end_date+"\t"+start_hour+"\t"+end_hour;
		logger.info(log_str);
		List<Record> rets = myDao.getPbaDataMonitorDist(start_date, end_date, start_hour, end_hour);
		List<Record> rets_sum = myDao.getPbaDataMonitorSum(start_date, end_date, start_hour, end_hour);
		rets_sum.addAll(rets);
		List<Record> rets_comp = myDao.getPbaDataMonitorDist(StringUtils.getInstance().getLastDaysStr(start_date.toString(), comp_days), 
				StringUtils.getInstance().getLastDaysStr(end_date.toString(), comp_days), start_hour, end_hour);
		List<Record> rets_sum_comp = myDao.getPbaDataMonitorSum(StringUtils.getInstance().getLastDaysStr(start_date.toString(), comp_days), 
				StringUtils.getInstance().getLastDaysStr(end_date.toString(), comp_days), start_hour, end_hour);
		rets_sum_comp.addAll(rets_comp);
		logger.info("rets_num\t"+rets.size());
		String html_str = StringUtils.getInstance().getPbaHtml(rets_sum, rets_sum_comp, qry_type);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("html_str", html_str);
		this.renderJson(map);
	}
	
	
	//@Clear(myIntercepter.class)
	public void getGeneralMonitorData(){
		Object start_date = getPara("start_date");
		Object end_date = getPara("end_date");
		Object start_hour = "0"+getPara("start_hour");
		Object end_hour = "0"+getPara("end_hour");
		int comp_days = getParaToInt("comp_days");
		String qry_type = getPara("qry_type");
		String  table = getPara("table");
		String colums_str = getPara("colums");
		String titles_str = getPara("titles");
		String group_by = getPara("group_by");
		String order_by = getPara("order_by");
		String search_by = getPara("search_by");
		String query = getPara("query");
		List<String> colums = new ArrayList<String>();
		List<String> colums_show = new ArrayList<String>();
		for(String col : colums_str.split(","))
		{
			List<String> res = StringUtils.getInstance().getUsedColums(col);
			System.out.println(res);
			for(String ite : res){
				if(!colums.contains(ite)){
					colums.add(ite);
				}
			}
			colums_show.add(col);
		}
		Collections.sort(colums, new SortByLengthComparator());
		List<String> titles = new ArrayList<String>();
		for(String col : titles_str.split(","))
		{
			titles.add(col);
		}
		start_hour = start_hour.toString().substring(start_hour.toString().length()-2);
		end_hour = end_hour.toString().substring(end_hour.toString().length()-2);
		String log_str = start_date+"\t"+end_date+"\t"+start_hour+"\t"+end_hour;
		logger.info(log_str);
		HashMap<String, Object> map_res = new HashMap<String, Object>();
		map_res = myDao.getGeneralDataMonitorDist(table, start_date, end_date, 
				start_hour, end_hour, colums, group_by, order_by, search_by, query);
		List<Record> rets = (List<Record>) map_res.get("list_records");
		map_res = myDao.getGeneralDataMonitorDist(table,
				StringUtils.getInstance().getLastDaysStr(start_date.toString(), comp_days), 
				StringUtils.getInstance().getLastDaysStr(end_date.toString(), comp_days), 
				start_hour, end_hour, colums, group_by, order_by, search_by, query);
		List<Record> rets_comp = (List<Record>) map_res.get("list_records");
		String html_str = StringUtils.getInstance().getTableReportHtml(rets, rets_comp, titles, colums_show, colums, map_res.get("search_cond").toString());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("html_str", html_str);
		map.put("search_cond", map_res.get("search_cond").toString());
		this.renderJson(map);
	}
	
	@Clear(myIntercepter.class)
	public void bertSugg(){
		//http://10.141.168.42:20191/txt_sim?txt_a=&txt_b=&type=cos
		String txt_a = getPara("txt_a");
		String txt_b = getPara("txt_b");
		String type = getPara("type");
        String url = "http://10.141.168.42:20191/txt_sim?txt_a="+txt_a+"&txt_b="+txt_b+"&type="+type;
        try {
        	url = "http://10.141.168.42:20191/txt_sim?txt_a="+URLEncoder.encode(txt_b, "utf-8")+"&txt_b="+URLEncoder.encode(txt_a, "utf-8")+"&type="+type;
			//url = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(url);
		String ret = HttpKit.get(url);  //(url, null, "?txt_a=开心&txt_b=快乐&type=cos");
		logger.info("ret:"+ret);
		//ret = "{pairs: \"a VS b\", score: \"0.9790672\"}";
		SimModel simModel = gson.fromJson(ret, SimModel.class);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("score", simModel.getScore());
		this.renderJson(map);
	}
	
	@Clear(myIntercepter.class)
	public void tdmSugg(){
		//http://10.141.168.42:20191/txt_sim?txt_a=&txt_b=&type=cos
		String query = getPara("query");
		String beam_num = getPara("beam_num");
        String url = "http://10.145.33.106:20205/retrive?query="+query+"&beam_num="+beam_num;
        try {
        	url = "http://10.145.33.106:20205/retrive?query="+URLEncoder.encode(query, "utf-8")+"&beam_num="+beam_num;
			//url = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(url);
		String ret = HttpKit.get(url);  //(url, null, "?txt_a=开心&txt_b=快乐&type=cos");
		logger.info("ret:"+ret);
		//ret = "{pairs: \"a VS b\", score: \"0.9790672\"}";
		TdmRespModel tdmRespModel = gson.fromJson(ret, TdmRespModel.class);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("kwds", tdmRespModel.getKwds());
		System.out.println(map.toString());
		this.renderJson(tdmRespModel);
	}

    @Clear(myIntercepter.class)
	public void kwdSugg(){
		String query = getPara("query");
		String max_num = getPara("max_num");
		String ret = "query";
		//String url = "http://10.135.33.106:8080/solr/collection1/select?q="+query+"%3A1688&wt=json&indent=true";
		try {
			query = URLEncoder.encode(query, "utf8");
			String url = "http://10.135.33.106:8080/solr/collection1/select?q=query%3A"+query+"&fl=query&rows="+max_num+"&wt=csv&indent=true";
			ret = HttpKit.get(url);
		}catch (Exception e){
			e.printStackTrace();

		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("res", ret.trim().replace("\"", "").replaceAll("\n", "@@@"));
		this.renderJson(map);
	}
	
	//获取图片服务数据
	@Clear(myIntercepter.class)
	public void imgSuggPage(){
		this.setSessionAttr("username", "test");
        this.render("/image_svr.jsp");		
	}
	
	public void imgSugg(){
		//http://"+Constant.SERVER_IMG_CASE+":20197/get_imgs?query=test&query_org=test&ckid=10001207791
		String username = this.getSessionAttr("username");
		if(username == null){
			username = "unk";
		}
		String query = getPara("query");
		String query_org = getPara("query_org");
		String ckid = getPara("ckid");
		String accid = getPara("accid");
		String ccid = getPara("ccid");
		String planid = getPara("planid");
		String grpid = getPara("grpid");
		String kwd = getPara("kwd");
		String match_type = getPara("match_type");
		String qry_cls = getPara("qry_cls") ;
		String kwd_cls = getPara("kwd_cls");
		String pid = getPara("pid");
		String serv_tp = getPara("serv_tp");
		String city_region = getPara("city_region");
		String is_logo = getPara("is_logo");
		String src_ctr = getPara("src_ctr");
		String ip = getPara("ip");
		String port = getPara("port");
		String show_type = getPara("show_type");
		String recall_type = getPara("recall_type");
		System.out.println("show_type:"+show_type);
        String url = "";
        try {
        	url = "http://"+Constant.SERVER_IMG_CASE+":20197/get_imgs?query="+URLEncoder.encode(query, "utf-8")+"&query_org="+
                  URLEncoder.encode(query_org, "utf-8")+"&ckid="+ckid+"&kwd="+URLEncoder.encode(kwd, "utf-8")
                  +"&accid="+URLEncoder.encode(accid, "utf-8")
                  +"&planid="+URLEncoder.encode(planid, "utf-8")
                  +"&grpid="+URLEncoder.encode(grpid, "utf-8")
                  +"&match_type="+URLEncoder.encode(match_type, "utf-8")
                  +"&qry_cls="+URLEncoder.encode(qry_cls, "utf-8")
                  +"&kwd_cls="+URLEncoder.encode(kwd_cls, "utf-8")
                  +"&pid="+URLEncoder.encode(pid, "utf-8")
                  +"&serv_tp="+URLEncoder.encode(serv_tp, "utf-8")
                  +"&city_region="+URLEncoder.encode(city_region, "utf-8")
                  +"&is_logo="+URLEncoder.encode(is_logo, "utf-8")
                  +"&src_ctr="+URLEncoder.encode(src_ctr, "utf-8")
                  +"&ip="+URLEncoder.encode(ip, "utf-8")
                  +"&port="+URLEncoder.encode(port, "utf-8")
        	      +"&username="+URLEncoder.encode(username, "utf-8");
			//url = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(url);
        //String ret = "{pairs: \"a VS b\", score: \"0.9790672\"}";
        //System.exit(1);
		String ret = HttpKit.get(url);  //(url, null, "?txt_a=开心&txt_b=快乐&type=cos");
		logger.info("ret:"+ret);
		//ret = "{pairs: \"a VS b\", score: \"0.9790672\"}";
		String html = "";
		ImgModel imgModel = gson.fromJson(ret, ImgModel.class);
		System.out.println("imgModel.getReq_vec_len():"+imgModel.getReq_num());
		for(int i=0; i < imgModel.getReq_num(); i++){
			System.out.println("mat_type:"+imgModel.getImg_details().get(i).getMat_type().toString());
			if("9".equals(imgModel.getImg_details().get(i).getMat_type().toString()) ){
				
				if (!(show_type.contains("1") || show_type.contains("0"))){
					continue;
				}
				
				if(!show_type.contains("1") && "2410243587".equals(imgModel.getImg_details().get(i).getCkid().toString())){
					continue;
				}
				if(!show_type.contains("0")  && show_type.contains("1") 
						&& !"2410243587".equals(imgModel.getImg_details().get(i).getCkid().toString())){
					continue;
				}
			    HashMap<String, String> map = 
					StringUtils.getInstance().getImgsGrids(imgModel.getImg_details().get(i).getUrls(), 
							imgModel.getImg_details().get(i).getTags(), 
							imgModel.getImg_details().get(i).getScores(), 
							imgModel.getImg_details().get(i).getCkid().toString(),
							imgModel.getImg_details().get(i).getCcid().toString(),
							recall_type);
			    html += map.get("html_str");
			}else if("37".equals(imgModel.getImg_details().get(i).getMat_type().toString())){
				if(!show_type.contains("2")){
					continue;
				}
				HashMap<String, String> map = 
						StringUtils.getInstance().getTextFlows(
								imgModel.getImg_details().get(i).getTags(), 
								imgModel.getImg_details().get(i).getScores(), 
								imgModel.getImg_details().get(i).getCkid().toString(),
								imgModel.getImg_details().get(i).getCcid().toString(),
								imgModel.getImg_details().get(i).getMattypes());
				    html += map.get("html_str");
			}else {
				if(!show_type.contains("3")){
					continue;
				}
				HashMap<String, String> map = 
						StringUtils.getInstance().getTextFlows(
								imgModel.getImg_details().get(i).getTags(), 
								imgModel.getImg_details().get(i).getScores(), 
								imgModel.getImg_details().get(i).getCkid().toString(),
								imgModel.getImg_details().get(i).getCcid().toString(),
								imgModel.getImg_details().get(i).getMattypes());
				    html += map.get("html_str");
			}
		}
		//System.out.println(imgModel.getImg_details().get(0).toString());
		System.out.println("html:"+html);
		HashMap<String, String> map_res = new HashMap<String, String>();
		map_res.put("html_str", html);
		map_res.put("query_grp", imgModel.getQuery_grp());
		this.renderJson(map_res);
	}
	
	public void getRandSample(){
		String kwd = getPara("kwd");
		String url = "";
		try {
			url = "http://"+Constant.SERVER_IMG_CASE+":20197/get_rand_sample?kwd="+URLEncoder.encode(kwd, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ret = HttpKit.get(url);
		SampleModel sampleModel = gson.fromJson(ret, SampleModel.class);
		HashMap<String, Object> map_res = new HashMap<String, Object>();
		map_res.put("query", sampleModel.getQuery());
		map_res.put("accid", sampleModel.getAccid());
		map_res.put("planid", sampleModel.getPlanid());
		map_res.put("grpid", sampleModel.getGrpid());
		map_res.put("ckid", sampleModel.getCkid());
		map_res.put("kwd", sampleModel.getKwd());
		map_res.put("match_type", sampleModel.getMatch_type());
		map_res.put("qry_cls", sampleModel.getQry_cls());
		map_res.put("kwd_cls", sampleModel.getKwd_cls());
		map_res.put("pid", sampleModel.getPid());
		map_res.put("serv_tp", sampleModel.getServ_tp());
		map_res.put("city_region", sampleModel.getCity_region());
		map_res.put("is_logo", sampleModel.getIs_logo());
		map_res.put("src_ctr", sampleModel.getSrc_ctr());
		System.out.println(map_res);
		this.renderJson(map_res);
	}
	/**
	 * 获取图片id，根据图片名称
	 */
	public void getImgidByName(){
		String name = getPara("name");
		String url = "";
		try {
			url = "http://"+Constant.SERVER_IMG_CASE+":20197/get_imgid_by_name?name="+URLEncoder.encode(name, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ret = HttpKit.get(url);
		System.out.println(ret);
		this.renderJson(ret);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(new java.util.Date().getSeconds());
		
	}
	
	
}
