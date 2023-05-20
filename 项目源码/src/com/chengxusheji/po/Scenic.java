package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Scenic {
    /*景点id*/
    private Integer scenicId;
    public Integer getScenicId(){
        return scenicId;
    }
    public void setScenicId(Integer scenicId){
        this.scenicId = scenicId;
    }

    /*景点类型*/
    private ScenicType scenicTypeObj;
    public ScenicType getScenicTypeObj() {
        return scenicTypeObj;
    }
    public void setScenicTypeObj(ScenicType scenicTypeObj) {
        this.scenicTypeObj = scenicTypeObj;
    }

    /*景区等级 */
    @NotEmpty(message="景区等级 不能为空")
    private String scenicGrade;
    public String getScenicGrade() {
        return scenicGrade;
    }
    public void setScenicGrade(String scenicGrade) {
        this.scenicGrade = scenicGrade;
    }

    /*景点名称*/
    @NotEmpty(message="景点名称不能为空")
    private String scenicName;
    public String getScenicName() {
        return scenicName;
    }
    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    /*建造日期*/
    @NotEmpty(message="建造日期不能为空")
    private String scenicDate;
    public String getScenicDate() {
        return scenicDate;
    }
    public void setScenicDate(String scenicDate) {
        this.scenicDate = scenicDate;
    }

    /*景点照片*/
    private String scenicPhoto;
    public String getScenicPhoto() {
        return scenicPhoto;
    }
    public void setScenicPhoto(String scenicPhoto) {
        this.scenicPhoto = scenicPhoto;
    }

    /*景点介绍*/
    @NotEmpty(message="景点介绍不能为空")
    private String scenicDesc;
    public String getScenicDesc() {
        return scenicDesc;
    }
    public void setScenicDesc(String scenicDesc) {
        this.scenicDesc = scenicDesc;
    }

    /*纬度*/
    @NotNull(message="必须输入纬度")
    private Float latitude;
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    /*经度*/
    @NotNull(message="必须输入经度")
    private Float longitude;
    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonScenic=new JSONObject(); 
		jsonScenic.accumulate("scenicId", this.getScenicId());
		jsonScenic.accumulate("scenicTypeObj", this.getScenicTypeObj().getTypeName());
		jsonScenic.accumulate("scenicTypeObjPri", this.getScenicTypeObj().getTypeId());
		jsonScenic.accumulate("scenicGrade", this.getScenicGrade());
		jsonScenic.accumulate("scenicName", this.getScenicName());
		jsonScenic.accumulate("scenicDate", this.getScenicDate().length()>19?this.getScenicDate().substring(0,19):this.getScenicDate());
		jsonScenic.accumulate("scenicPhoto", this.getScenicPhoto());
		jsonScenic.accumulate("scenicDesc", this.getScenicDesc());
		jsonScenic.accumulate("latitude", this.getLatitude());
		jsonScenic.accumulate("longitude", this.getLongitude());
		return jsonScenic;
    }}