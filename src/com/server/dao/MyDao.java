package com.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.server.model.DataModel;
import com.server.model.LabHisModel;
import com.server.model.UserModel;
import com.server.utils.StringUtils;

public class MyDao {
	
	private static Logger logger = Logger.getLogger(MyDao.class);
	
	/**
	 * 登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Record userLogin(String username,  String pwd){
		String sql = "select * from user where username = ? and pwd = ?";
		Record record = Db.findFirst(sql, username, pwd);
		if(record != null){
			int row = updateUser(username);
			logger.info("Login Success:"+username+", "+pwd+", update last_login_time:"+ row);
		}else{
			logger.info("Login Faild:"+username+", "+pwd);
		}
		
		return record;
	}
	/**
	 * 注册用户
	 * @param username
	 * @param pwd
	 * @param usertype
	 * @return
	 */
	public int userRegister(String username,  String pwd, int usertype){
		String sql = "insert into user (username, pwd, usertype) values (? , ? , ?) ";
		int row = Db.update(sql, username, pwd, usertype);
		return row;
	}
	
	public int updateUserType(String username, int usertype){
		String sql = "update user set usertype = ? where username = ?";
		int row = Db.update(sql, usertype, username);
		return row;
	}
	
	public int updateUserPwd(String username, String pwd){
		String sql = "update user set pwd = ? where username = ?";
		int row = Db.update(sql, pwd, username);
		return row;
	}
	
	/**
	 * 更新登录时间戳
	 * @param username
	 * @return
	 */
	public int updateUser(String username){
		String sql = "update user set last_login_time = ? where username = ?";
		int row = Db.update(sql, new Date(), username);
		return row;
	}
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public Record getUserById(String username){
		String sql = "select * from user where username = ?";
		Record record = Db.findFirst(sql, username);
		return record;
	}
	
	
	
	public HashMap<String, Object> batchAddLabelData(List<String> lines, String  data_tag){
		Object query, title, qry_msg, title_msg, date, ext, ext_msg;
		int row = 0;
		String sql = "insert into data_msg (query, title, qry_msg, title_msg, date, ext, ext_msg, data_tag) values (?, ?, ?, ?, ?, ?, ?, ?)";
		for(int i=0; i<lines.size(); i++){
			String items[] = lines.get(i).split("\t");
			if(items.length < 4){
				continue;
			}
			query = items[0];
			qry_msg = items[1];
			title = items[2];
			title_msg = items[3];
			date = new Date();
			ext = "";
			ext_msg = "";
			if(items.length > 5){
				ext = items[4];
				ext_msg = items[5];
			}
			row+=Db.update(sql, query, title, qry_msg, title_msg, date, ext, ext_msg, data_tag);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("lines:"+lines.size()+", rows:"+row);
		map.put("inp_rows", lines.size());
		map.put("save_rows", row);
		
		return map;
		
	}
	
	
	/**
	 * 选择数据标注
	 * @param tab_name     数据表名
	 * @param data_source  批次数据区分  
	 * @param userid       用户名
	 * @param num          数量选择
	 * @return
	 */
	
	public List<DataModel> getUnlabeledData(String tab_name, String data_source, String userid, int num){
		//String sql = "select * from data_msg where data_tag = 'huazhang' and data_msg.id not in (select res_id from lab_his where tab = "data_msg" ) order by id asc limit 10"
		String sql = "select * from "+tab_name+" where  data_tag = ? and "+tab_name+".id not in (select res_id from lab_his where tab = ? and user_id = ?) order by id asc limit ?";
		List<Record> res = Db.find(sql, data_source, data_source,userid, num);
		List<DataModel> models = new ArrayList<DataModel>();
		for(int i=0; i<res.size(); i++){
			models.add(new DataModel(res.get(i)));
		}
		return models;
		
	}
	
	/**
	 * 添加标注结果
	 * @param labHisModel
	 * @return
	 */
	public int addLabelData(LabHisModel labHisModel){
		
		String sql = "insert into lab_his values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		int row = Db.update(sql, labHisModel.getRes_id(), labHisModel.getUser_id(), labHisModel.getRel_val(),
				labHisModel.getKwds(), labHisModel.getEntitys(), labHisModel.getExts(), labHisModel.getDate(),labHisModel.getTab());
		
		return row;
	}

	/**
	 * 修改标注结果
	 * @param labHisModel
	 * @return
	 */
    public int updLabelData(LabHisModel labHisModel){
		
		String sql = "update lab_his set rel_val = ?, kwds = ?, entitys = ?, exts = ?, date = ?, tab = ? where  res_id = ? and user_id = ?";
		
		int row = Db.update(sql, labHisModel.getRel_val(),labHisModel.getKwds(), labHisModel.getEntitys(),
				labHisModel.getExts(), labHisModel.getDate(),labHisModel.getTab(), labHisModel.getRes_id(), labHisModel.getUser_id());
		
		return row;
	}
    
    
    /**
     * 
     * @param res_id
     * @param user_id
     * @return
     */
    
    public LabHisModel getLabHisById(int res_id, String user_id){
    	
    	String sql = "select * from lab_his where res_id = ? and user_id = ?";
    	Record re = Db.findFirst(sql, res_id, user_id); 
    	if(re!=null){
    		return new LabHisModel(re);
    	}else{
    		return null;
    	}
    	
    }
    
    
    /**
     * 获取已标注数据
     * @param user_id
     * @param tab
     * @return
     */
    
    public Page<Record> getLabHisByUserId(String user_id, String tab,  int pageNumber, int pageSize){
    	
    	String sql = "select lab_his.*, data_msg.query, data_msg.title, data_msg.id from lab_his right join data_msg on data_msg.id = lab_his.res_id where user_id = ? and tab = ? order by lab_his.date desc";
    	Page<Record> reds = Db.paginate(pageNumber, pageSize, sql, user_id, tab); 
    	System.out.println(reds.toString());
    	List<LabHisModel> labHisModels = new ArrayList<LabHisModel>();
//    	for(Record re : reds){
//    		labHisModels.add(new LabHisModel(re));
//    	}
    	
    	return reds;
    }
    
    
    public List<Record> getLabHisByUserIdAndDate(String user_id, String tab,  String date){
    	
    	String sql = "select lab_his.*, data_msg.query, data_msg.title, data_msg.id from lab_his right join data_msg on data_msg.id = lab_his.res_id where user_id = ? and tab = ? and lab_his.date > ? order by lab_his.date desc";
    	List<Record> reds = Db.find(sql, user_id, tab, date); 
    	return reds;
    }
    
    /**
     * 删除标注记录
     * @param res_id
     * @param user_id
     * @return
     */
    public int delLabeledDataById(String res_id, String user_id){
    	String sql = "delete from lab_his where res_id = ? and user_id = ?";
    	int row = Db.update(sql, res_id, user_id);
    	return row;
    }
    
    
    
    public HashMap<String, Object> getGeneralDataMonitorDist(Object table, Object start_date, Object end_date, Object start_hour, Object end_hour, 
    		List<String> colums, String group_by, String order_by, String search_by, String query){
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	String search_condition = "1 = 1";
    	List<String> search_bys = new ArrayList<String>();
    	for(String ite : search_by.split("@")){
    		search_bys.add(ite.trim());
    	}
    	String[] querys = query.split("@"); 
    	/*
    	if(search_bys.size() ==querys.length ){
    	    for(int i=0 ;i<search_bys.size(); i++){
    	    	search_condition += " and " + search_bys.get(i)+" like '%"+querys[i]+"%'";
    	    }
    	}else{
    		search_condition = "1 != 1";
    	}
    	*/
    	
    	search_condition = StringUtils.getInstance().getLogicSqlExpress(search_bys, querys);
    	System.out.println(search_condition);
        if(search_condition == "1 != 1"){
        	map.put("search_cond", search_condition);
        	map.put("list_records", new ArrayList<Record>());
    		return  map;
    	}
    	
    	String sql = "select ";
    	for(String col : colums){
    		if(!col.equals(group_by) && !search_bys.contains(col)){
    		    sql += String.format("sum(%s) as %s, ", col, col);
    		}else{
    			sql += String.format("%s, ", col);
    		}
    	}
    	//补充未包含的查询项目
    	for(String search : search_bys){
    		sql += String.format("%s, ", search); 
    	}
    	sql += String.format("count(*) as _cnt_records ");
    	System.out.println(sql);
    	System.out.println(sql.endsWith(", "));
    	if(sql.endsWith(", ")){
    		sql = sql.trim().substring(0, sql.length()-2);
    	}
    	sql += " from ";
    	sql += "(select  *, concat(date, substring(concat('0', hour), -2, 2)) as time_tag from "+table
				+ " where date >= ? and date <= ?) as tmp "
				+ "where time_tag >= ? and time_tag < ? and "+search_condition;
    	String sql_upd = sql+" group by ? order by ?";
    	String[]  params = { start_date+"", end_date+"", start_date+""+start_hour, end_date+""+end_hour, group_by, order_by};
    	for(String para : params){
    		sql_upd = sql_upd.replaceFirst("\\?", para);    		
    	}
    	System.out.println(sql_upd);
    	//System.out.println(sql);
    	
    	List<Record> rets = Db.find(sql_upd); //, start_date, end_date, start_date+""+start_hour, end_date+""+end_hour, group_by);
    	String sql_sum = sql;
    	List<Record> rets_sum = Db.find(sql_sum, start_date+"", end_date+"", start_date+""+start_hour, end_date+""+end_hour);
    	if(rets_sum.size() > 0){
    		Record record_sum = rets_sum.get(0);
    		record_sum.set("channel", "合计");
    		rets.add(record_sum);
    	}
    	//System.out.println(rets.size());
    	//System.out.println(rets.toString());
    	
    	map.put("list_records", rets);
    	map.put("search_cond", search_condition);
    	return map;
    }
    
    
    
    /**
     * pba  根据起始时间获取数据
     */
    
    public List<Record> getPbaDataMonitorDist(Object start_date, Object end_date, Object start_hour, Object end_hour){
    	
    	String sql = "select pro_tag, sq_tag, sum(pv) as pv, sum(adnum) as adnum, sum(adpv) as adpv, sum(clk) as clk, sum(cost) as cost,  "
    			+ "sum(pv_rec) as pv_rec, sum(adnum_rec) as adnum_rec, sum(adpv_rec) as adpv_rec, sum(clk_rec) as clk_rec, "
    			+ "sum(cost_rec) as cost_rec, sum(pv_front) as pv_front from "
    			+ "(select  *, concat(date, substring(concat('0', hour), -2, 2)) as time_tag from mseach_sq_pba_kpi  "
    					+ "where pro_tag = ? and date >= ? and date <= ?) as tmp "
    					+ "where time_tag >=? and time_tag<? group by sq_tag";
    	List<Record> rets = Db.find(sql, "PBA", start_date, end_date, start_date+""+start_hour, end_date+""+end_hour);
    	return rets;
    }
    
    
   public List<Record> getPbaDataMonitorSum(Object start_date, Object end_date, Object start_hour, Object end_hour){
    	
    	String sql = "select pro_tag, sq_tag, sum(pv) as pv, sum(adnum) as adnum, sum(adpv) as adpv, sum(clk) as clk, sum(cost) as cost,  "
    			+ "sum(pv_rec) as pv_rec, sum(adnum_rec) as adnum_rec, sum(adpv_rec) as adpv_rec, sum(clk_rec) as clk_rec, "
    			+ "sum(cost_rec) as cost_rec, sum(pv_front) as pv_front from "
    			+ "(select  *, concat(date, substring(concat('0', hour), -2, 2)) as time_tag from mseach_sq_pba_kpi  "
    					+ "where pro_tag = ? and date >= ? and date <= ?) as tmp "
    					+ "where time_tag >=? and time_tag< ?";
    	List<Record> rets = Db.find(sql, "PBA", start_date, end_date, start_date+""+start_hour, end_date+""+end_hour);
    	for(Record re : rets){
    		re.set("sq_tag", "SUM");
    	}
    	String[]  params = {"PBA", start_date+"", end_date+"", start_date+""+start_hour, end_date+""+end_hour};
    	for(String para : params){
    		sql = sql.replaceFirst("\\?", para);    		
    	}
    	System.out.println(sql);
    	
    	return rets;
    }
    
  
	

}
