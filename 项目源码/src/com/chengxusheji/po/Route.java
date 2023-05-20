package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Route {
    /*路径id*/
    private Integer routeId;
    public Integer getRouteId(){
        return routeId;
    }
    public void setRouteId(Integer routeId){
        this.routeId = routeId;
    }

    /*起始景点*/
    private Scenic startScenic;
    public Scenic getStartScenic() {
        return startScenic;
    }
    public void setStartScenic(Scenic startScenic) {
        this.startScenic = startScenic;
    }

    /*结束景点*/
    private Scenic endScenic;
    public Scenic getEndScenic() {
        return endScenic;
    }
    public void setEndScenic(Scenic endScenic) {
        this.endScenic = endScenic;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRoute=new JSONObject(); 
		jsonRoute.accumulate("routeId", this.getRouteId());
		jsonRoute.accumulate("startScenic", this.getStartScenic().getScenicName());
		jsonRoute.accumulate("startScenicPri", this.getStartScenic().getScenicId());
		jsonRoute.accumulate("endScenic", this.getEndScenic().getScenicName());
		jsonRoute.accumulate("endScenicPri", this.getEndScenic().getScenicId());
		return jsonRoute;
    }}