package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Baoming {
    /*报名id*/
    private Integer baomingId;
    public Integer getBaomingId(){
        return baomingId;
    }
    public void setBaomingId(Integer baomingId){
        this.baomingId = baomingId;
    }

    /*报名的项目*/
    private Project projectObj;
    public Project getProjectObj() {
        return projectObj;
    }
    public void setProjectObj(Project projectObj) {
        this.projectObj = projectObj;
    }

    /*报名的企业*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }

    /*报名时间*/
    @NotEmpty(message="报名时间不能为空")
    private String baomingTime;
    public String getBaomingTime() {
        return baomingTime;
    }
    public void setBaomingTime(String baomingTime) {
        this.baomingTime = baomingTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBaoming=new JSONObject(); 
		jsonBaoming.accumulate("baomingId", this.getBaomingId());
		jsonBaoming.accumulate("projectObj", this.getProjectObj().getProjectName());
		jsonBaoming.accumulate("projectObjPri", this.getProjectObj().getProjectId());
		jsonBaoming.accumulate("companyObj", this.getCompanyObj().getCompanyName());
		jsonBaoming.accumulate("companyObjPri", this.getCompanyObj().getCompanyUserName());
		jsonBaoming.accumulate("baomingTime", this.getBaomingTime().length()>19?this.getBaomingTime().substring(0,19):this.getBaomingTime());
		return jsonBaoming;
    }}