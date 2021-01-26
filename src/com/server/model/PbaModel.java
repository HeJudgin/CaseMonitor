package com.server.model;

import java.math.BigInteger;

import com.jfinal.plugin.activerecord.Record;

public class PbaModel {
	
	private BigInteger date;
	private String pro_tag;
	private String sq_tag;
	private BigInteger pv;
	private BigInteger adnum;
	private BigInteger adpv;
	private BigInteger clk;
	private BigInteger cost;
	private BigInteger pv_rec;
	private BigInteger adnum_rec;
	private BigInteger adpv_rec;
	private BigInteger clk_rec;
	private BigInteger cost_rec;
	private BigInteger pv_pront;
	public BigInteger getDate() {
		return date;
	}
	public void setDate(BigInteger date) {
		this.date = date;
	}
	public String getPro_tag() {
		return pro_tag;
	}
	public void setPro_tag(String pro_tag) {
		this.pro_tag = pro_tag;
	}
	public String getSq_tag() {
		return sq_tag;
	}
	public void setSq_tag(String sq_tag) {
		this.sq_tag = sq_tag;
	}
	public BigInteger getPv() {
		return pv;
	}
	public void setPv(BigInteger pv) {
		this.pv = pv;
	}
	public BigInteger getAdnum() {
		return adnum;
	}
	public void setAdnum(BigInteger adnum) {
		this.adnum = adnum;
	}
	public BigInteger getAdpv() {
		return adpv;
	}
	public void setAdpv(BigInteger adpv) {
		this.adpv = adpv;
	}
	public BigInteger getClk() {
		return clk;
	}
	public void setClk(BigInteger clk) {
		this.clk = clk;
	}
	public BigInteger getCost() {
		return cost;
	}
	public void setCost(BigInteger cost) {
		this.cost = cost;
	}
	public BigInteger getPv_rec() {
		return pv_rec;
	}
	public void setPv_rec(BigInteger pv_rec) {
		this.pv_rec = pv_rec;
	}
	public BigInteger getAdnum_rec() {
		return adnum_rec;
	}
	public void setAdnum_rec(BigInteger adnum_rec) {
		this.adnum_rec = adnum_rec;
	}
	public BigInteger getAdpv_rec() {
		return adpv_rec;
	}
	public void setAdpv_rec(BigInteger adpv_rec) {
		this.adpv_rec = adpv_rec;
	}
	public BigInteger getClk_rec() {
		return clk_rec;
	}
	public void setClk_rec(BigInteger clk_rec) {
		this.clk_rec = clk_rec;
	}
	public BigInteger getCost_rec() {
		return cost_rec;
	}
	public void setCost_rec(BigInteger cost_rec) {
		this.cost_rec = cost_rec;
	}
	public BigInteger getPv_pront() {
		return pv_pront;
	}
	public void setPv_pront(BigInteger pv_pront) {
		this.pv_pront = pv_pront;
	}
	public PbaModel(Record re) {
		super();
		this.date = (BigInteger) (re.get("date") == null ? BigInteger.ZERO : new BigInteger(re.get("date").toString()));
		this.pro_tag = re.get("pro_tag") == null ? "" :re.getStr("pro_tag");
		this.sq_tag = re.get("sq_tag") == null ? "" :re.getStr("sq_tag");
		this.pv = (BigInteger) (re.get("pv") == null ? BigInteger.ZERO : new BigInteger(re.get("pv").toString()));
		this.adnum = (BigInteger) (re.get("adnum") == null ? BigInteger.ZERO : new BigInteger(re.get("adnum").toString()));
		this.adpv = (BigInteger) (re.get("adpv") == null ? BigInteger.ZERO : new BigInteger(re.get("adpv").toString()));
		this.clk = (BigInteger) (re.get("clk") == null ? BigInteger.ZERO : new BigInteger(re.get("clk").toString()));
		this.cost = (BigInteger) (re.get("cost") == null ? BigInteger.ZERO : new BigInteger(re.get("cost").toString()));
		this.pv_rec = (BigInteger) (re.get("pv_rec") == null ? BigInteger.ZERO : new BigInteger(re.get("pv_rec").toString()));
		this.adnum_rec = (BigInteger) (re.get("adnum_rec") == null ? BigInteger.ZERO : new BigInteger(re.get("adnum_rec").toString()));
		this.adpv_rec = (BigInteger) (re.get("adpv_rec") == null ? BigInteger.ZERO : new BigInteger(re.get("adpv_rec").toString()));
		this.clk_rec = (BigInteger) (re.get("clk_rec") == null ? BigInteger.ZERO : new BigInteger(re.get("clk_rec").toString()));
		this.cost_rec = (BigInteger) (re.get("cost_rec") == null ? BigInteger.ZERO : new BigInteger(re.get("cost_rec").toString()));
		this.pv_pront = (BigInteger) (re.get("pv_front") == null ? BigInteger.ZERO : new BigInteger(re.get("pv_front").toString()));
	}
	
	public void div_model(String num){
		setPv(getPv().divide(new BigInteger(num)));
		setAdnum(getAdnum().divide(new BigInteger(num)));
		setAdpv(getAdpv().divide(new BigInteger(num)));
		setClk(getClk().divide(new BigInteger(num)));
		setCost(getCost().divide(new BigInteger(num)));
		
		setPv_rec(getPv_rec().divide(new BigInteger(num)));
		setAdnum_rec(getAdnum_rec().divide(new BigInteger(num)));
		setAdpv_rec(getAdpv_rec().divide(new BigInteger(num)));
		setClk_rec(getClk_rec().divide(new BigInteger(num)));
		setCost_rec(getCost_rec().divide(new BigInteger(num)));
		
		setPv_pront(getPv_pront().divide(new BigInteger(num)));
		
		
	}
    
	
	
	

}
