package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Company {
    /*企业用户名*/
    @NotEmpty(message="企业用户名不能为空")
    private String companyUserName;
    public String getCompanyUserName(){
        return companyUserName;
    }
    public void setCompanyUserName(String companyUserName){
        this.companyUserName = companyUserName;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*企业名称*/
    @NotEmpty(message="企业名称不能为空")
    private String companyName;
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /*工商注册号*/
    @NotEmpty(message="工商注册号不能为空")
    private String gszch;
    public String getGszch() {
        return gszch;
    }
    public void setGszch(String gszch) {
        this.gszch = gszch;
    }

    /*营业执照*/
    private String yyzz;
    public String getYyzz() {
        return yyzz;
    }
    public void setYyzz(String yyzz) {
        this.yyzz = yyzz;
    }

    /*公司性质*/
    @NotEmpty(message="公司性质不能为空")
    private String gsxz;
    public String getGsxz() {
        return gsxz;
    }
    public void setGsxz(String gsxz) {
        this.gsxz = gsxz;
    }

    /*公司规模*/
    @NotEmpty(message="公司规模不能为空")
    private String gsgm;
    public String getGsgm() {
        return gsgm;
    }
    public void setGsgm(String gsgm) {
        this.gsgm = gsgm;
    }

    /*公司行业*/
    private Hangye hangyeObj;
    public Hangye getHangyeObj() {
        return hangyeObj;
    }
    public void setHangyeObj(Hangye hangyeObj) {
        this.hangyeObj = hangyeObj;
    }

    /*联系人*/
    @NotEmpty(message="联系人不能为空")
    private String lxr;
    public String getLxr() {
        return lxr;
    }
    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String lxdh;
    public String getLxdh() {
        return lxdh;
    }
    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    /*公司介绍*/
    @NotEmpty(message="公司介绍不能为空")
    private String companyDesc;
    public String getCompanyDesc() {
        return companyDesc;
    }
    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    /*注册时间*/
    @NotEmpty(message="注册时间不能为空")
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCompany=new JSONObject(); 
		jsonCompany.accumulate("companyUserName", this.getCompanyUserName());
		jsonCompany.accumulate("password", this.getPassword());
		jsonCompany.accumulate("companyName", this.getCompanyName());
		jsonCompany.accumulate("gszch", this.getGszch());
		jsonCompany.accumulate("yyzz", this.getYyzz());
		jsonCompany.accumulate("gsxz", this.getGsxz());
		jsonCompany.accumulate("gsgm", this.getGsgm());
		jsonCompany.accumulate("hangyeObj", this.getHangyeObj().getHangyeName());
		jsonCompany.accumulate("hangyeObjPri", this.getHangyeObj().getHangyeId());
		jsonCompany.accumulate("lxr", this.getLxr());
		jsonCompany.accumulate("lxdh", this.getLxdh());
		jsonCompany.accumulate("companyDesc", this.getCompanyDesc());
		jsonCompany.accumulate("shzt", this.getShzt());
		jsonCompany.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		return jsonCompany;
    }}