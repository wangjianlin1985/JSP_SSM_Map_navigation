package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Leaveword {
    /*留言id*/
    private Integer leavewordId;
    public Integer getLeavewordId(){
        return leavewordId;
    }
    public void setLeavewordId(Integer leavewordId){
        this.leavewordId = leavewordId;
    }

    /*留言标题*/
    @NotEmpty(message="留言标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*留言内容*/
    @NotEmpty(message="留言内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*留言人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*留言时间*/
    @NotEmpty(message="留言时间不能为空")
    private String leaveTime;
    public String getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    /*回复内容*/
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
		jsonLeaveword.accumulate("leavewordId", this.getLeavewordId());
		jsonLeaveword.accumulate("title", this.getTitle());
		jsonLeaveword.accumulate("content", this.getContent());
		jsonLeaveword.accumulate("userObj", this.getUserObj().getName());
		jsonLeaveword.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonLeaveword.accumulate("leaveTime", this.getLeaveTime().length()>19?this.getLeaveTime().substring(0,19):this.getLeaveTime());
		jsonLeaveword.accumulate("replyContent", this.getReplyContent());
		jsonLeaveword.accumulate("replyTime", this.getReplyTime().length()>19?this.getReplyTime().substring(0,19):this.getReplyTime());
		return jsonLeaveword;
    }}