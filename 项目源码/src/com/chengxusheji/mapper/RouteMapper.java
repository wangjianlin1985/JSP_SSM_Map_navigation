package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Route;

public interface RouteMapper {
	/*添加路径信息*/
	public void addRoute(Route route) throws Exception;

	/*按照查询条件分页查询路径记录*/
	public ArrayList<Route> queryRoute(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有路径记录*/
	public ArrayList<Route> queryRouteList(@Param("where") String where) throws Exception;

	/*按照查询条件的路径记录数*/
	public int queryRouteCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条路径记录*/
	public Route getRoute(int routeId) throws Exception;

	/*更新路径记录*/
	public void updateRoute(Route route) throws Exception;

	/*删除路径记录*/
	public void deleteRoute(int routeId) throws Exception;

}
