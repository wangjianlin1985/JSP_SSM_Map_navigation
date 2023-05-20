﻿package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.ScenicService;
import com.chengxusheji.po.Scenic;
import com.chengxusheji.service.ScenicTypeService;
import com.chengxusheji.po.ScenicType;

//Scenic管理控制层
@Controller
@RequestMapping("/Scenic")
public class ScenicController extends BaseController {

    /*业务层对象*/
    @Resource ScenicService scenicService;

    @Resource ScenicTypeService scenicTypeService;
	@InitBinder("scenicTypeObj")
	public void initBinderscenicTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("scenicTypeObj.");
	}
	@InitBinder("scenic")
	public void initBinderScenic(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("scenic.");
	}
	/*跳转到添加Scenic视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Scenic());
		/*查询所有的ScenicType信息*/
		List<ScenicType> scenicTypeList = scenicTypeService.queryAllScenicType();
		request.setAttribute("scenicTypeList", scenicTypeList);
		return "Scenic_add";
	}

	/*客户端ajax方式提交添加景点信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Scenic scenic, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			scenic.setScenicPhoto(this.handlePhotoUpload(request, "scenicPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        scenicService.addScenic(scenic);
        message = "景点添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询景点信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("scenicTypeObj") ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (scenicGrade == null) scenicGrade = "";
		if (scenicName == null) scenicName = "";
		if (scenicDate == null) scenicDate = "";
		if(rows != 0)scenicService.setRows(rows);
		List<Scenic> scenicList = scenicService.queryScenic(scenicTypeObj, scenicGrade, scenicName, scenicDate, page);
	    /*计算总的页数和总的记录数*/
	    scenicService.queryTotalPageAndRecordNumber(scenicTypeObj, scenicGrade, scenicName, scenicDate);
	    /*获取到总的页码数目*/
	    int totalPage = scenicService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = scenicService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Scenic scenic:scenicList) {
			JSONObject jsonScenic = scenic.getJsonObject();
			jsonArray.put(jsonScenic);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询景点信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Scenic> scenicList = scenicService.queryAllScenic();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Scenic scenic:scenicList) {
			JSONObject jsonScenic = new JSONObject();
			jsonScenic.accumulate("scenicId", scenic.getScenicId());
			jsonScenic.accumulate("scenicName", scenic.getScenicName());
			jsonArray.put(jsonScenic);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询景点信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("scenicTypeObj") ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (scenicGrade == null) scenicGrade = "";
		if (scenicName == null) scenicName = "";
		if (scenicDate == null) scenicDate = "";
		List<Scenic> scenicList = scenicService.queryScenic(scenicTypeObj, scenicGrade, scenicName, scenicDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    scenicService.queryTotalPageAndRecordNumber(scenicTypeObj, scenicGrade, scenicName, scenicDate);
	    /*获取到总的页码数目*/
	    int totalPage = scenicService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = scenicService.getRecordNumber();
	    request.setAttribute("scenicList",  scenicList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("scenicTypeObj", scenicTypeObj);
	    request.setAttribute("scenicGrade", scenicGrade);
	    request.setAttribute("scenicName", scenicName);
	    request.setAttribute("scenicDate", scenicDate);
	    List<ScenicType> scenicTypeList = scenicTypeService.queryAllScenicType();
	    request.setAttribute("scenicTypeList", scenicTypeList);
		return "Scenic/scenic_frontquery_result"; 
	}

     /*前台查询Scenic信息*/
	@RequestMapping(value="/{scenicId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer scenicId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键scenicId获取Scenic对象*/
        Scenic scenic = scenicService.getScenic(scenicId); 
        ArrayList<Scenic> otherScenicList = scenicService.queryScenicExceptScenicId(scenicId);
        List<ScenicType> scenicTypeList = scenicTypeService.queryAllScenicType();
        request.setAttribute("scenicTypeList", scenicTypeList);
        request.setAttribute("scenic",  scenic);
        request.setAttribute("otherScenicList", otherScenicList);
        return "Scenic/scenic_frontshow";
	}

	/*ajax方式显示景点修改jsp视图页*/
	@RequestMapping(value="/{scenicId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer scenicId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键scenicId获取Scenic对象*/
        Scenic scenic = scenicService.getScenic(scenicId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonScenic = scenic.getJsonObject();
		out.println(jsonScenic.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新景点信息*/
	@RequestMapping(value = "/{scenicId}/update", method = RequestMethod.POST)
	public void update(@Validated Scenic scenic, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String scenicPhotoFileName = this.handlePhotoUpload(request, "scenicPhotoFile");
		if(!scenicPhotoFileName.equals("upload/NoImage.jpg"))scenic.setScenicPhoto(scenicPhotoFileName); 


		try {
			scenicService.updateScenic(scenic);
			message = "景点更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "景点更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除景点信息*/
	@RequestMapping(value="/{scenicId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer scenicId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  scenicService.deleteScenic(scenicId);
	            request.setAttribute("message", "景点删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "景点删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条景点记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String scenicIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = scenicService.deleteScenics(scenicIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出景点信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("scenicTypeObj") ScenicType scenicTypeObj,String scenicGrade,String scenicName,String scenicDate, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(scenicGrade == null) scenicGrade = "";
        if(scenicName == null) scenicName = "";
        if(scenicDate == null) scenicDate = "";
        List<Scenic> scenicList = scenicService.queryScenic(scenicTypeObj,scenicGrade,scenicName,scenicDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Scenic信息记录"; 
        String[] headers = { "景点类型","景区等级 ","景点名称","建造日期","景点照片"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<scenicList.size();i++) {
        	Scenic scenic = scenicList.get(i); 
        	dataset.add(new String[]{scenic.getScenicTypeObj().getTypeName(),scenic.getScenicGrade(),scenic.getScenicName(),scenic.getScenicDate(),scenic.getScenicPhoto()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Scenic.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
