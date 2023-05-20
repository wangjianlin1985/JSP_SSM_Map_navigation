﻿package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.ScenicType;
import com.chengxusheji.po.Scenic;

import com.chengxusheji.mapper.ScenicMapper;
@Service
public class ScenicService {

	@Resource ScenicMapper scenicMapper;
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

    /*添加景点记录*/
    public void addScenic(Scenic scenic) throws Exception {
    	scenicMapper.addScenic(scenic);
    }

    /*按照查询条件分页查询景点记录*/
    public ArrayList<Scenic> queryScenic(ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != scenicTypeObj && scenicTypeObj.getTypeId()!= null && scenicTypeObj.getTypeId()!= 0)  where += " and t_scenic.scenicTypeObj=" + scenicTypeObj.getTypeId();
    	if(!scenicGrade.equals("")) where = where + " and t_scenic.scenicGrade like '%" + scenicGrade + "%'";
    	if(!scenicName.equals("")) where = where + " and t_scenic.scenicName like '%" + scenicName + "%'";
    	if(!scenicDate.equals("")) where = where + " and t_scenic.scenicDate like '%" + scenicDate + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return scenicMapper.queryScenic(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Scenic> queryScenic(ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate) throws Exception  { 
     	String where = "where 1=1";
    	if(null != scenicTypeObj && scenicTypeObj.getTypeId()!= null && scenicTypeObj.getTypeId()!= 0)  where += " and t_scenic.scenicTypeObj=" + scenicTypeObj.getTypeId();
    	if(!scenicGrade.equals("")) where = where + " and t_scenic.scenicGrade like '%" + scenicGrade + "%'";
    	if(!scenicName.equals("")) where = where + " and t_scenic.scenicName like '%" + scenicName + "%'";
    	if(!scenicDate.equals("")) where = where + " and t_scenic.scenicDate like '%" + scenicDate + "%'";
    	return scenicMapper.queryScenicList(where);
    }

    public ArrayList<Scenic> queryScenicExceptScenicId(int scenicId) throws Exception {
    	return scenicMapper.queryScenicExceptScenicId(scenicId);
    }
    
    /*查询所有景点记录*/
    public ArrayList<Scenic> queryAllScenic()  throws Exception {
        return scenicMapper.queryScenicList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate) throws Exception {
     	String where = "where 1=1";
    	if(null != scenicTypeObj && scenicTypeObj.getTypeId()!= null && scenicTypeObj.getTypeId()!= 0)  where += " and t_scenic.scenicTypeObj=" + scenicTypeObj.getTypeId();
    	if(!scenicGrade.equals("")) where = where + " and t_scenic.scenicGrade like '%" + scenicGrade + "%'";
    	if(!scenicName.equals("")) where = where + " and t_scenic.scenicName like '%" + scenicName + "%'";
    	if(!scenicDate.equals("")) where = where + " and t_scenic.scenicDate like '%" + scenicDate + "%'";
        recordNumber = scenicMapper.queryScenicCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取景点记录*/
    public Scenic getScenic(int scenicId) throws Exception  {
        Scenic scenic = scenicMapper.getScenic(scenicId);
        return scenic;
    }

    /*更新景点记录*/
    public void updateScenic(Scenic scenic) throws Exception {
        scenicMapper.updateScenic(scenic);
    }

    /*删除一条景点记录*/
    public void deleteScenic (int scenicId) throws Exception {
        scenicMapper.deleteScenic(scenicId);
    }

    /*删除多条景点信息*/
    public int deleteScenics (String scenicIds) throws Exception {
    	String _scenicIds[] = scenicIds.split(",");
    	for(String _scenicId: _scenicIds) {
    		scenicMapper.deleteScenic(Integer.parseInt(_scenicId));
    	}
    	return _scenicIds.length;
    }
}
