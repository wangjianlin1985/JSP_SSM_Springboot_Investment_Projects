package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.chengxusheji.service.BaomingService;
import com.chengxusheji.po.Baoming;
import com.chengxusheji.service.CompanyService;
import com.chengxusheji.po.Company;
import com.chengxusheji.service.ProjectService;
import com.chengxusheji.po.Project;

//Baoming管理控制层
@Controller
@RequestMapping("/Baoming")
public class BaomingController extends BaseController {

    /*业务层对象*/
    @Resource BaomingService baomingService;

    @Resource CompanyService companyService;
    @Resource ProjectService projectService;
	@InitBinder("projectObj")
	public void initBinderprojectObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("projectObj.");
	}
	@InitBinder("companyObj")
	public void initBindercompanyObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("companyObj.");
	}
	@InitBinder("baoming")
	public void initBinderBaoming(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("baoming.");
	}
	/*跳转到添加Baoming视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Baoming());
		/*查询所有的Company信息*/
		List<Company> companyList = companyService.queryAllCompany();
		request.setAttribute("companyList", companyList);
		/*查询所有的Project信息*/
		List<Project> projectList = projectService.queryAllProject();
		request.setAttribute("projectList", projectList);
		return "Baoming_add";
	}

	/*客户端ajax方式提交添加项目报名信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Baoming baoming, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        baomingService.addBaoming(baoming);
        message = "项目报名添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加项目报名信息*/
	@RequestMapping(value = "/companyAdd", method = RequestMethod.POST)
	public void companyAdd(Baoming baoming, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		 
		
		String user_name = (String) session.getAttribute("user_name");
		if(null == user_name) {
			message = "请先登录网站！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		Company company = new Company();
		company.setCompanyUserName(session.getAttribute("user_name").toString());
		baoming.setCompanyObj(company);
		
		String companyUserName = projectService.getProject(baoming.getProjectObj().getProjectId()).getCompanyObj().getCompanyUserName();
		if(companyUserName.equals(user_name)) {
			message = "你不能报名自己的项目！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		if(baomingService.queryBaoming(baoming.getProjectObj(), company, "").size() > 0) {
			message = "你已经报过名了！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		baoming.setBaomingTime(sdf.format(new java.util.Date()));
		
        baomingService.addBaoming(baoming);
        message = "项目报名添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询项目报名信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("projectObj") Project projectObj,@ModelAttribute("companyObj") Company companyObj,String baomingTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (baomingTime == null) baomingTime = "";
		if(rows != 0)baomingService.setRows(rows);
		List<Baoming> baomingList = baomingService.queryBaoming(projectObj, companyObj, baomingTime, page);
	    /*计算总的页数和总的记录数*/
	    baomingService.queryTotalPageAndRecordNumber(projectObj, companyObj, baomingTime);
	    /*获取到总的页码数目*/
	    int totalPage = baomingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = baomingService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Baoming baoming:baomingList) {
			JSONObject jsonBaoming = baoming.getJsonObject();
			jsonArray.put(jsonBaoming);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询项目报名信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Baoming> baomingList = baomingService.queryAllBaoming();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Baoming baoming:baomingList) {
			JSONObject jsonBaoming = new JSONObject();
			jsonBaoming.accumulate("baomingId", baoming.getBaomingId());
			jsonArray.put(jsonBaoming);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询项目报名信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("projectObj") Project projectObj,@ModelAttribute("companyObj") Company companyObj,String baomingTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (baomingTime == null) baomingTime = "";
		List<Baoming> baomingList = baomingService.queryBaoming(projectObj, companyObj, baomingTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    baomingService.queryTotalPageAndRecordNumber(projectObj, companyObj, baomingTime);
	    /*获取到总的页码数目*/
	    int totalPage = baomingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = baomingService.getRecordNumber();
	    request.setAttribute("baomingList",  baomingList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("projectObj", projectObj);
	    request.setAttribute("companyObj", companyObj);
	    request.setAttribute("baomingTime", baomingTime);
	    List<Company> companyList = companyService.queryAllCompany();
	    request.setAttribute("companyList", companyList);
	    List<Project> projectList = projectService.queryAllProject();
	    request.setAttribute("projectList", projectList);
		return "Baoming/baoming_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询项目报名信息*/
	@RequestMapping(value = { "/companyFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String companyFrontlist(@ModelAttribute("projectObj") Project projectObj,@ModelAttribute("companyObj") Company companyObj,String baomingTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (baomingTime == null) baomingTime = "";
		companyObj = new Company();
		companyObj.setCompanyUserName(session.getAttribute("user_name").toString());
		
		List<Baoming> baomingList = baomingService.queryBaoming(projectObj, companyObj, baomingTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    baomingService.queryTotalPageAndRecordNumber(projectObj, companyObj, baomingTime);
	    /*获取到总的页码数目*/
	    int totalPage = baomingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = baomingService.getRecordNumber();
	    request.setAttribute("baomingList",  baomingList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("projectObj", projectObj);
	    request.setAttribute("companyObj", companyObj);
	    request.setAttribute("baomingTime", baomingTime);
	    List<Company> companyList = companyService.queryAllCompany();
	    request.setAttribute("companyList", companyList);
	    List<Project> projectList = projectService.queryAllProject();
	    request.setAttribute("projectList", projectList);
		return "Baoming/baoming_companyFrontquery_result"; 
	}
	

     /*前台查询Baoming信息*/
	@RequestMapping(value="/{baomingId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer baomingId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键baomingId获取Baoming对象*/
        Baoming baoming = baomingService.getBaoming(baomingId);

        List<Company> companyList = companyService.queryAllCompany();
        request.setAttribute("companyList", companyList);
        List<Project> projectList = projectService.queryAllProject();
        request.setAttribute("projectList", projectList);
        request.setAttribute("baoming",  baoming);
        return "Baoming/baoming_frontshow";
	}

	/*ajax方式显示项目报名修改jsp视图页*/
	@RequestMapping(value="/{baomingId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer baomingId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键baomingId获取Baoming对象*/
        Baoming baoming = baomingService.getBaoming(baomingId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonBaoming = baoming.getJsonObject();
		out.println(jsonBaoming.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新项目报名信息*/
	@RequestMapping(value = "/{baomingId}/update", method = RequestMethod.POST)
	public void update(@Validated Baoming baoming, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			baomingService.updateBaoming(baoming);
			message = "项目报名更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "项目报名更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除项目报名信息*/
	@RequestMapping(value="/{baomingId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer baomingId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  baomingService.deleteBaoming(baomingId);
	            request.setAttribute("message", "项目报名删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "项目报名删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条项目报名记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String baomingIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = baomingService.deleteBaomings(baomingIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出项目报名信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("projectObj") Project projectObj,@ModelAttribute("companyObj") Company companyObj,String baomingTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(baomingTime == null) baomingTime = "";
        List<Baoming> baomingList = baomingService.queryBaoming(projectObj,companyObj,baomingTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Baoming信息记录"; 
        String[] headers = { "报名id","报名的项目","报名的企业","报名时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<baomingList.size();i++) {
        	Baoming baoming = baomingList.get(i); 
        	dataset.add(new String[]{baoming.getBaomingId() + "",baoming.getProjectObj().getProjectName(),baoming.getCompanyObj().getCompanyName(),baoming.getBaomingTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Baoming.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
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
