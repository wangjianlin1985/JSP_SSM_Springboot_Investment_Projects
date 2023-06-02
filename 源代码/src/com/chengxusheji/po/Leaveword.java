package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Leaveword {
    /*留言id*/
    private Integer leaveWordId;
    public Integer getLeaveWordId(){
        return leaveWordId;
    }
    public void setLeaveWordId(Integer leaveWordId){
        this.leaveWordId = leaveWordId;
    }

    /*留言标题*/
    @NotEmpty(message="留言标题不能为空")
    private String leaveTitle;
    public String getLeaveTitle() {
        return leaveTitle;
    }
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    /*留言内容*/
    @NotEmpty(message="留言内容不能为空")
    private String leaveContent;
    public String getLeaveContent() {
        return leaveContent;
    }
    public void setLeaveContent(String leaveContent) {
        this.leaveContent = leaveContent;
    }

    /*留言单位*/
    private Company companyObj;
    public Company getCompanyObj() {
        return companyObj;
    }
    public void setCompanyObj(Company companyObj) {
        this.companyObj = companyObj;
    }

    /*留言时间*/
    private String leaveTime;
    public String getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    /*管理回复*/
    private String replyContent;
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    /*回复时间*/
    private String replyTime;
    public String getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLeaveword=new JSONObject(); 
		jsonLeaveword.accumulate("leaveWordId", this.getLeaveWordId());
		jsonLeaveword.accumulate("leaveTitle", this.getLeaveTitle());
		jsonLeaveword.accumulate("leaveContent", this.getLeaveContent());
		jsonLeaveword.accumulate("companyObj", this.getCompanyObj().getCompanyName());
		jsonLeaveword.accumulate("companyObjPri", this.getCompanyObj().getCompanyUserName());
		jsonLeaveword.accumulate("leaveTime", this.getLeaveTime().length()>19?this.getLeaveTime().substring(0,19):this.getLeaveTime());
		jsonLeaveword.accumulate("replyContent", this.getReplyContent());
		jsonLeaveword.accumulate("replyTime", this.getReplyTime().length()>19?this.getReplyTime().substring(0,19):this.getReplyTime());
		return jsonLeaveword;
    }}