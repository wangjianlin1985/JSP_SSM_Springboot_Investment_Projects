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
import com.chengxusheji.service.ProjectService;
import com.chengxusheji.po.Project;
import com.chengxusheji.service.CompanyService;
import com.chengxusheji.po.Company;
import com.chengxusheji.service.HangyeService;
import com.chengxusheji.po.Hangye;

//Project管理控制层
@Controller
@RequestMapping("/Project")
public class ProjectController extends BaseController {

    /*业务层对象*/
    @Resource ProjectService projectService;

    @Resource CompanyService companyService;
    @Resource HangyeService hangyeService;
	@InitBinder("hangyeObj")
	public void initBinderhangyeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("hangyeObj.");
	}
	@InitBinder("companyObj")
	public void initBindercompanyObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("companyObj.");
	}
	@InitBinder("project")
	public void initBinderProject(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("project.");
	}
	/*跳转到添加Project视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Project());
		/*查询所有的Company信息*/
		List<Company> companyList = companyService.queryAllCompany();
		request.setAttribute("companyList", companyList);
		/*查询所有的Hangye信息*/
		List<Hangye> hangyeList = hangyeService.queryAllHangye();
		request.setAttribute("hangyeList", hangyeList);
		return "Project_add";
	}

	/*客户端ajax方式提交添加招商项目信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Project project, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			project.setProjectPhoto(this.handlePhotoUpload(request, "projectPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        projectService.addProject(project);
        message = "招商项目添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加招商项目信息*/
	@RequestMapping(value = "/companyAdd", method = RequestMethod.POST)
	public void companyAdd(Project project, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		 
		try {
			project.setProjectPhoto(this.handlePhotoUpload(request, "projectPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		Company company = new Company();
		company.setCompanyUserName(session.getAttribute("user_name").toString());
		project.setCompanyObj(company);
		
		project.setShhf("--");
		project.setShzt("待审核");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		project.setAddTime(sdf.format(new java.util.Date()));
		
		
        projectService.addProject(project);
        message = "招商项目添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询招商项目信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("hangyeObj") Hangye hangyeObj,String projectName,String zsDate,String xmAddress,String zsAddress,String lxfs,@ModelAttribute("companyObj") Company companyObj,String shzt,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (projectName == null) projectName = "";
		if (zsDate == null) zsDate = "";
		if (xmAddress == null) xmAddress = "";
		if (zsAddress == null) zsAddress = "";
		if (lxfs == null) lxfs = "";
		if (shzt == null) shzt = "";
		if (addTime == null) addTime = "";
		if(rows != 0)projectService.setRows(rows);
		List<Project> projectList = projectService.queryProject(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime, page);
	    /*计算总的页数和总的记录数*/
	    projectService.queryTotalPageAndRecordNumber(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = projectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = projectService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Project project:projectList) {
			JSONObject jsonProject = project.getJsonObject();
			jsonArray.put(jsonProject);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询招商项目信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Project> projectList = projectService.queryAllProject();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Project project:projectList) {
			JSONObject jsonProject = new JSONObject();
			jsonProject.accumulate("projectId", project.getProjectId());
			jsonProject.accumulate("projectName", project.getProjectName());
			jsonArray.put(jsonProject);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询招商项目信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("hangyeObj") Hangye hangyeObj,String projectName,String zsDate,String xmAddress,String zsAddress,String lxfs,@ModelAttribute("companyObj") Company companyObj,String shzt,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (projectName == null) projectName = "";
		if (zsDate == null) zsDate = "";
		if (xmAddress == null) xmAddress = "";
		if (zsAddress == null) zsAddress = "";
		if (lxfs == null) lxfs = "";
		if (shzt == null) shzt = "";
		if (addTime == null) addTime = "";
		
		shzt = "已审核";
		List<Project> projectList = projectService.queryProject(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    projectService.queryTotalPageAndRecordNumber(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = projectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = projectService.getRecordNumber();
	    request.setAttribute("projectList",  projectList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("hangyeObj", hangyeObj);
	    request.setAttribute("projectName", projectName);
	    request.setAttribute("zsDate", zsDate);
	    request.setAttribute("xmAddress", xmAddress);
	    request.setAttribute("zsAddress", zsAddress);
	    request.setAttribute("lxfs", lxfs);
	    request.setAttribute("companyObj", companyObj);
	    request.setAttribute("shzt", shzt);
	    request.setAttribute("addTime", addTime);
	    List<Company> companyList = companyService.queryAllCompany();
	    request.setAttribute("companyList", companyList);
	    List<Hangye> hangyeList = hangyeService.queryAllHangye();
	    request.setAttribute("hangyeList", hangyeList);
		return "Project/project_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询招商项目信息*/
	@RequestMapping(value = { "/companyFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String companyFrontlist(@ModelAttribute("hangyeObj") Hangye hangyeObj,String projectName,String zsDate,String xmAddress,String zsAddress,String lxfs,@ModelAttribute("companyObj") Company companyObj,String shzt,String addTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (projectName == null) projectName = "";
		if (zsDate == null) zsDate = "";
		if (xmAddress == null) xmAddress = "";
		if (zsAddress == null) zsAddress = "";
		if (lxfs == null) lxfs = "";
		if (shzt == null) shzt = "";
		if (addTime == null) addTime = "";
		companyObj = new Company();
		companyObj.setCompanyUserName(session.getAttribute("user_name").toString());
		List<Project> projectList = projectService.queryProject(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    projectService.queryTotalPageAndRecordNumber(hangyeObj, projectName, zsDate, xmAddress, zsAddress, lxfs, companyObj, shzt, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = projectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = projectService.getRecordNumber();
	    request.setAttribute("projectList",  projectList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("hangyeObj", hangyeObj);
	    request.setAttribute("projectName", projectName);
	    request.setAttribute("zsDate", zsDate);
	    request.setAttribute("xmAddress", xmAddress);
	    request.setAttribute("zsAddress", zsAddress);
	    request.setAttribute("lxfs", lxfs);
	    request.setAttribute("companyObj", companyObj);
	    request.setAttribute("shzt", shzt);
	    request.setAttribute("addTime", addTime);
	    List<Company> companyList = companyService.queryAllCompany();
	    request.setAttribute("companyList", companyList);
	    List<Hangye> hangyeList = hangyeService.queryAllHangye();
	    request.setAttribute("hangyeList", hangyeList);
		return "Project/project_companyFrontquery_result"; 
	}
	

     /*前台查询Project信息*/
	@RequestMapping(value="/{projectId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer projectId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键projectId获取Project对象*/
        Project project = projectService.getProject(projectId);

        List<Company> companyList = companyService.queryAllCompany();
        request.setAttribute("companyList", companyList);
        List<Hangye> hangyeList = hangyeService.queryAllHangye();
        request.setAttribute("hangyeList", hangyeList);
        request.setAttribute("project",  project);
        return "Project/project_frontshow";
	}

	/*ajax方式显示招商项目修改jsp视图页*/
	@RequestMapping(value="/{projectId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer projectId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键projectId获取Project对象*/
        Project project = projectService.getProject(projectId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonProject = project.getJsonObject();
		out.println(jsonProject.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新招商项目信息*/
	@RequestMapping(value = "/{projectId}/update", method = RequestMethod.POST)
	public void update(@Validated Project project, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String projectPhotoFileName = this.handlePhotoUpload(request, "projectPhotoFile");
		if(!projectPhotoFileName.equals("upload/NoImage.jpg"))project.setProjectPhoto(projectPhotoFileName); 


		try {
			projectService.updateProject(project);
			message = "招商项目更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "招商项目更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除招商项目信息*/
	@RequestMapping(value="/{projectId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer projectId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  projectService.deleteProject(projectId);
	            request.setAttribute("message", "招商项目删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "招商项目删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条招商项目记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String projectIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = projectService.deleteProjects(projectIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出招商项目信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("hangyeObj") Hangye hangyeObj,String projectName,String zsDate,String xmAddress,String zsAddress,String lxfs,@ModelAttribute("companyObj") Company companyObj,String shzt,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(projectName == null) projectName = "";
        if(zsDate == null) zsDate = "";
        if(xmAddress == null) xmAddress = "";
        if(zsAddress == null) zsAddress = "";
        if(lxfs == null) lxfs = "";
        if(shzt == null) shzt = "";
        if(addTime == null) addTime = "";
        List<Project> projectList = projectService.queryProject(hangyeObj,projectName,zsDate,xmAddress,zsAddress,lxfs,companyObj,shzt,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Project信息记录"; 
        String[] headers = { "项目id","所属行业","项目名称","项目图片","招商日期","项目地点","招商地点","投资额度","联系方式","招商单位","审核状态","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<projectList.size();i++) {
        	Project project = projectList.get(i); 
        	dataset.add(new String[]{project.getProjectId() + "",project.getHangyeObj().getHangyeName(),project.getProjectName(),project.getProjectPhoto(),project.getZsDate(),project.getXmAddress(),project.getZsAddress(),project.getTzed(),project.getLxfs(),project.getCompanyObj().getCompanyName(),project.getShzt(),project.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Project.xls");//filename是下载的xls的名，建议最好用英文 
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
