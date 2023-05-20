package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Scenic;
import com.chengxusheji.po.Scenic;
import com.chengxusheji.po.Route;

import com.chengxusheji.mapper.RouteMapper;
@Service
public class RouteService {

	@Resource RouteMapper routeMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加路径记录*/
    public void addRoute(Route route) throws Exception {
    	routeMapper.addRoute(route);
    }

    /*按照查询条件分页查询路径记录*/
    public ArrayList<Route> queryRoute(Scenic startScenic,Scenic endScenic,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != startScenic && startScenic.getScenicId()!= null && startScenic.getScenicId()!= 0)  where += " and t_route.startScenic=" + startScenic.getScenicId();
    	if(null != endScenic && endScenic.getScenicId()!= null && endScenic.getScenicId()!= 0)  where += " and t_route.endScenic=" + endScenic.getScenicId();
    	int startIndex = (currentPage-1) * this.rows;
    	return routeMapper.queryRoute(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Route> queryRoute(Scenic startScenic,Scenic endScenic) throws Exception  { 
     	String where = "where 1=1";
    	if(null != startScenic && startScenic.getScenicId()!= null && startScenic.getScenicId()!= 0)  where += " and t_route.startScenic=" + startScenic.getScenicId();
    	if(null != endScenic && endScenic.getScenicId()!= null && endScenic.getScenicId()!= 0)  where += " and t_route.endScenic=" + endScenic.getScenicId();
    	return routeMapper.queryRouteList(where);
    }

    /*查询所有路径记录*/
    public ArrayList<Route> queryAllRoute()  throws Exception {
        return routeMapper.queryRouteList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Scenic startScenic,Scenic endScenic) throws Exception {
     	String where = "where 1=1";
    	if(null != startScenic && startScenic.getScenicId()!= null && startScenic.getScenicId()!= 0)  where += " and t_route.startScenic=" + startScenic.getScenicId();
    	if(null != endScenic && endScenic.getScenicId()!= null && endScenic.getScenicId()!= 0)  where += " and t_route.endScenic=" + endScenic.getScenicId();
        recordNumber = routeMapper.queryRouteCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取路径记录*/
    public Route getRoute(int routeId) throws Exception  {
        Route route = routeMapper.getRoute(routeId);
        return route;
    }

    /*更新路径记录*/
    public void updateRoute(Route route) throws Exception {
        routeMapper.updateRoute(route);
    }

    /*删除一条路径记录*/
    public void deleteRoute (int routeId) throws Exception {
        routeMapper.deleteRoute(routeId);
    }

    /*删除多条路径信息*/
    public int deleteRoutes (String routeIds) throws Exception {
    	String _routeIds[] = routeIds.split(",");
    	for(String _routeId: _routeIds) {
    		routeMapper.deleteRoute(Integer.parseInt(_routeId));
    	}
    	return _routeIds.length;
    }
}
