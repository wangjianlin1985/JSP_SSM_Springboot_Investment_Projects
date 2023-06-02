package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Hangye {
    /*行业id*/
    private Integer hangyeId;
    public Integer getHangyeId(){
        return hangyeId;
    }
    public void setHangyeId(Integer hangyeId){
        this.hangyeId = hangyeId;
    }

    /*行业名称*/
    @NotEmpty(message="行业名称不能为空")
    private String hangyeName;
    public String getHangyeName() {
        return hangyeName;
    }
    public void setHangyeName(String hangyeName) {
        this.hangyeName = hangyeName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonHangye=new JSONObject(); 
		jsonHangye.accumulate("hangyeId", this.getHangyeId());
		jsonHangye.accumulate("hangyeName", this.getHangyeName());
		return jsonHangye;
    }}