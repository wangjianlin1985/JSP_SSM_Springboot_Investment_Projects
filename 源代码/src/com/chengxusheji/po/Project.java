package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Project {
    /*项目id*/
    private Integer projectId;
    public Integer getProjectId(){
        return projectId;
    }
    public void setProjectId(Integer projectId){
        this.projectId = projectId;
    }

    /*所属行业*/
    private Hangye hangyeObj;
    public Hangye getHangyeObj() {
        return hangyeObj;
    }
    public void setHangyeObj(Hangye hangyeObj) {
        this.hangyeObj = hangyeObj;
    }

    /*项目名称*/
    @NotEmpty(message="项目名称不能为空")
    private String projectName;
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /*项目图片*/
    private String projectPhoto;
    public String getProjectPhoto() {
        return projectPhoto;
    }
    public void setProjectPhoto(String projectPhoto) {
        this.projectPhoto = projectPhoto;
    }

    /*招商日期*/
    @NotEmpty(message="招商日期不能为空")
    private String zsDate;
    public String getZsDate() {
        return zsDate;
    }
    public void setZsDate(String zsDate) {
        this.zsDate = zsDate;
    }

    /*项目地点*/
    @NotEmpty(message="项目地点不能为空")
    private String xmAddress;
    public String getXmAddress() {
        return xmAddress;
    }
    public void setXmAddress(String xmAddress) {
        this.xmAddress = xmAddress;
    }

    /*招商地点*/
    @NotEmpty(message="招商地点不能为空")
    private String zsAddress;
    public String getZsAddress() {
        return zsAddress;
    }
    public void setZsAddress(String zsAddress) {
        this.zsAddress = zsAddress;
    }

    /*投资额度*/
    @NotEmpty(message="投资额度不能为空")
    private String tzed;
    public String getTzed() {
        return tzed;
    }
    public void setTzed(String tzed) {
        this.tzed = tzed;
    }

    /*联系方式*/
    @NotEmpty(message="联系方式不能为空")
    private String lxfs;
    public String getLxfs() {
        return lxfs;
    }
    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    /*项目介绍*/
    @NotEmpty(message="项目介绍不能为空")
    private String projectDesc;
    public String getProjectDesc() {
        return projectDesc;
    }
    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    /*招商单位*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
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

    /*审核回复*/
    @NotEmpty(message="审核回复不能为空")
    private String shhf;
    public String getShhf() {
        return shhf;
    }
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonProject=new JSONObject(); 
		jsonProject.accumulate("projectId", this.getProjectId());
		jsonProject.accumulate("hangyeObj", this.getHangyeObj().getHangyeName());
		jsonProject.accumulate("hangyeObjPri", this.getHangyeObj().getHangyeId());
		jsonProject.accumulate("projectName", this.getProjectName());
		jsonProject.accumulate("projectPhoto", this.getProjectPhoto());
		jsonProject.accumulate("zsDate", this.getZsDate().length()>19?this.getZsDate().substring(0,19):this.getZsDate());
		jsonProject.accumulate("xmAddress", this.getXmAddress());
		jsonProject.accumulate("zsAddress", this.getZsAddress());
		jsonProject.accumulate("tzed", this.getTzed());
		jsonProject.accumulate("lxfs", this.getLxfs());
		jsonProject.accumulate("projectDesc", this.getProjectDesc());
		jsonProject.accumulate("companyObj", this.getCompanyObj().getCompanyName());
		jsonProject.accumulate("companyObjPri", this.getCompanyObj().getCompanyUserName());
		jsonProject.accumulate("shzt", this.getShzt());
		jsonProject.accumulate("shhf", this.getShhf());
		jsonProject.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonProject;
    }}