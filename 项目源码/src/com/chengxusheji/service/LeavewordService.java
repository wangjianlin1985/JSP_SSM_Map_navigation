package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Leaveword;

import com.chengxusheji.mapper.LeavewordMapper;
@Service
public class LeavewordService {

	@Resource LeavewordMapper leavewordMapper;
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

    /*添加留言记录*/
    public void addLeaveword(Leaveword leaveword) throws Exception {
    	leavewordMapper.addLeaveword(leaveword);
    }

    /*按照查询条件分页查询留言记录*/
    public ArrayList<Leaveword> queryLeaveword(String title,UserInfo userObj,String leaveTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_leaveword.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_leaveword.userObj='" + userObj.getUser_name() + "'";
    	if(!leaveTime.equals("")) where = where + " and t_leaveword.leaveTime like '%" + leaveTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return leavewordMapper.queryLeaveword(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Leaveword> queryLeaveword(String title,UserInfo userObj,String leaveTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_leaveword.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_leaveword.userObj='" + userObj.getUser_name() + "'";
    	if(!leaveTime.equals("")) where = where + " and t_leaveword.leaveTime like '%" + leaveTime + "%'";
    	return leavewordMapper.queryLeavewordList(where);
    }

    /*查询所有留言记录*/
    public ArrayList<Leaveword> queryAllLeaveword()  throws Exception {
        return leavewordMapper.queryLeavewordList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,UserInfo userObj,String leaveTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_leaveword.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_leaveword.userObj='" + userObj.getUser_name() + "'";
    	if(!leaveTime.equals("")) where = where + " and t_leaveword.leaveTime like '%" + leaveTime + "%'";
        recordNumber = leavewordMapper.queryLeavewordCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取留言记录*/
    public Leaveword getLeaveword(int leavewordId) throws Exception  {
        Leaveword leaveword = leavewordMapper.getLeaveword(leavewordId);
        return leaveword;
    }

    /*更新留言记录*/
    public void updateLeaveword(Leaveword leaveword) throws Exception {
        leavewordMapper.updateLeaveword(leaveword);
    }

    /*删除一条留言记录*/
    public void deleteLeaveword (int leavewordId) throws Exception {
        leavewordMapper.deleteLeaveword(leavewordId);
    }

    /*删除多条留言信息*/
    public int deleteLeavewords (String leavewordIds) throws Exception {
    	String _leavewordIds[] = leavewordIds.split(",");
    	for(String _leavewordId: _leavewordIds) {
    		leavewordMapper.deleteLeaveword(Integer.parseInt(_leavewordId));
    	}
    	return _leavewordIds.length;
    }
}
