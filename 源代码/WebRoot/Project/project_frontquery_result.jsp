<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Project" %>
<%@ page import="com.chengxusheji.po.Company" %>
<%@ page import="com.chengxusheji.po.Hangye" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Project> projectList = (List<Project>)request.getAttribute("projectList");
    //获取所有的companyObj信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的hangyeObj信息
    List<Hangye> hangyeList = (List<Hangye>)request.getAttribute("hangyeList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Hangye hangyeObj = (Hangye)request.getAttribute("hangyeObj");
    String projectName = (String)request.getAttribute("projectName"); //项目名称查询关键字
    String zsDate = (String)request.getAttribute("zsDate"); //招商日期查询关键字
    String xmAddress = (String)request.getAttribute("xmAddress"); //项目地点查询关键字
    String zsAddress = (String)request.getAttribute("zsAddress"); //招商地点查询关键字
    String lxfs = (String)request.getAttribute("lxfs"); //联系方式查询关键字
    Company companyObj = (Company)request.getAttribute("companyObj");
    String shzt = (String)request.getAttribute("shzt"); //审核状态查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>招商项目查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Project/frontlist">招商项目信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Project/project_frontAdd.jsp" style="display:none;">添加招商项目</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<projectList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Project project = projectList.get(i); //获取到招商项目对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Project/<%=project.getProjectId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=project.getProjectPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		所属行业:<%=project.getHangyeObj().getHangyeName() %>
			     	</div>
			     	<div class="field">
	            		项目名称:<%=project.getProjectName() %>
			     	</div>
			     	<div class="field">
	            		招商日期:<%=project.getZsDate() %>
			     	</div>
			     	<div class="field">
	            		项目地点:<%=project.getXmAddress() %>
			     	</div>
			     	<div class="field">
	            		招商地点:<%=project.getZsAddress() %>
			     	</div>
			     	<div class="field">
	            		投资额度:<%=project.getTzed() %>
			     	</div>
			     	<div class="field">
	            		联系方式:<%=project.getLxfs() %>
			     	</div>
			     	<div class="field">
	            		招商单位:<%=project.getCompanyObj().getCompanyName() %>
			     	</div>
			     	
			     	<div class="field">
	            		发布时间:<%=project.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Project/<%=project.getProjectId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="projectEdit('<%=project.getProjectId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="projectDelete('<%=project.getProjectId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>招商项目查询</h1>
		</div>
		<form name="projectQueryForm" id="projectQueryForm" action="<%=basePath %>Project/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="hangyeObj_hangyeId">所属行业：</label>
                <select id="hangyeObj_hangyeId" name="hangyeObj.hangyeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Hangye hangyeTemp:hangyeList) {
	 					String selected = "";
 					if(hangyeObj!=null && hangyeObj.getHangyeId()!=null && hangyeObj.getHangyeId().intValue()==hangyeTemp.getHangyeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=hangyeTemp.getHangyeId() %>" <%=selected %>><%=hangyeTemp.getHangyeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="projectName">项目名称:</label>
				<input type="text" id="projectName" name="projectName" value="<%=projectName %>" class="form-control" placeholder="请输入项目名称">
			</div>
			<div class="form-group">
				<label for="zsDate">招商日期:</label>
				<input type="text" id="zsDate" name="zsDate" class="form-control"  placeholder="请选择招商日期" value="<%=zsDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="xmAddress">项目地点:</label>
				<input type="text" id="xmAddress" name="xmAddress" value="<%=xmAddress %>" class="form-control" placeholder="请输入项目地点">
			</div>
			<div class="form-group">
				<label for="zsAddress">招商地点:</label>
				<input type="text" id="zsAddress" name="zsAddress" value="<%=zsAddress %>" class="form-control" placeholder="请输入招商地点">
			</div>
			<div class="form-group">
				<label for="lxfs">联系方式:</label>
				<input type="text" id="lxfs" name="lxfs" value="<%=lxfs %>" class="form-control" placeholder="请输入联系方式">
			</div>
            <div class="form-group">
            	<label for="companyObj_companyUserName">招商单位：</label>
                <select id="companyObj_companyUserName" name="companyObj.companyUserName" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Company companyTemp:companyList) {
	 					String selected = "";
 					if(companyObj!=null && companyObj.getCompanyUserName()!=null && companyObj.getCompanyUserName().equals(companyTemp.getCompanyUserName()))
 						selected = "selected";
	 				%>
 				 <option value="<%=companyTemp.getCompanyUserName() %>" <%=selected %>><%=companyTemp.getCompanyName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group" style="display:none;">
				<label for="shzt">审核状态:</label>
				<input type="text" id="shzt" name="shzt" value="<%=shzt %>" class="form-control" placeholder="请输入审核状态">
			</div>
			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="projectEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;招商项目信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="projectEditForm" id="projectEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="project_projectId_edit" class="col-md-3 text-right">项目id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="project_projectId_edit" name="project.projectId" class="form-control" placeholder="请输入项目id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="project_hangyeObj_hangyeId_edit" class="col-md-3 text-right">所属行业:</label>
		  	 <div class="col-md-9">
			    <select id="project_hangyeObj_hangyeId_edit" name="project.hangyeObj.hangyeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_projectName_edit" class="col-md-3 text-right">项目名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_projectName_edit" name="project.projectName" class="form-control" placeholder="请输入项目名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_projectPhoto_edit" class="col-md-3 text-right">项目图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="project_projectPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="project_projectPhoto" name="project.projectPhoto"/>
			    <input id="projectPhotoFile" name="projectPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_zsDate_edit" class="col-md-3 text-right">招商日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date project_zsDate_edit col-md-12" data-link-field="project_zsDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="project_zsDate_edit" name="project.zsDate" size="16" type="text" value="" placeholder="请选择招商日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_xmAddress_edit" class="col-md-3 text-right">项目地点:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_xmAddress_edit" name="project.xmAddress" class="form-control" placeholder="请输入项目地点">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_zsAddress_edit" class="col-md-3 text-right">招商地点:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_zsAddress_edit" name="project.zsAddress" class="form-control" placeholder="请输入招商地点">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_tzed_edit" class="col-md-3 text-right">投资额度:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_tzed_edit" name="project.tzed" class="form-control" placeholder="请输入投资额度">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_lxfs_edit" class="col-md-3 text-right">联系方式:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_lxfs_edit" name="project.lxfs" class="form-control" placeholder="请输入联系方式">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_projectDesc_edit" class="col-md-3 text-right">项目介绍:</label>
		  	 <div class="col-md-9">
			 	<textarea name="project.projectDesc" id="project_projectDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_companyObj_companyUserName_edit" class="col-md-3 text-right">招商单位:</label>
		  	 <div class="col-md-9">
			    <select id="project_companyObj_companyUserName_edit" name="project.companyObj.companyUserName" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_shzt_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="project_shzt_edit" name="project.shzt" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_shhf_edit" class="col-md-3 text-right">审核回复:</label>
		  	 <div class="col-md-9">
			    <textarea id="project_shhf_edit" name="project.shhf" rows="8" class="form-control" placeholder="请输入审核回复"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="project_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date project_addTime_edit col-md-12" data-link-field="project_addTime_edit">
                    <input class="form-control" id="project_addTime_edit" name="project.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#projectEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxProjectModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var project_projectDesc_edit = UE.getEditor('project_projectDesc_edit'); //项目介绍编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.projectQueryForm.currentPage.value = currentPage;
    document.projectQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.projectQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.projectQueryForm.currentPage.value = pageValue;
    documentprojectQueryForm.submit();
}

/*弹出修改招商项目界面并初始化数据*/
function projectEdit(projectId) {
	$.ajax({
		url :  basePath + "Project/" + projectId + "/update",
		type : "get",
		dataType: "json",
		success : function (project, response, status) {
			if (project) {
				$("#project_projectId_edit").val(project.projectId);
				$.ajax({
					url: basePath + "Hangye/listAll",
					type: "get",
					success: function(hangyes,response,status) { 
						$("#project_hangyeObj_hangyeId_edit").empty();
						var html="";
		        		$(hangyes).each(function(i,hangye){
		        			html += "<option value='" + hangye.hangyeId + "'>" + hangye.hangyeName + "</option>";
		        		});
		        		$("#project_hangyeObj_hangyeId_edit").html(html);
		        		$("#project_hangyeObj_hangyeId_edit").val(project.hangyeObjPri);
					}
				});
				$("#project_projectName_edit").val(project.projectName);
				$("#project_projectPhoto").val(project.projectPhoto);
				$("#project_projectPhotoImg").attr("src", basePath +　project.projectPhoto);
				$("#project_zsDate_edit").val(project.zsDate);
				$("#project_xmAddress_edit").val(project.xmAddress);
				$("#project_zsAddress_edit").val(project.zsAddress);
				$("#project_tzed_edit").val(project.tzed);
				$("#project_lxfs_edit").val(project.lxfs);
				project_projectDesc_edit.setContent(project.projectDesc, false);
				$.ajax({
					url: basePath + "Company/listAll",
					type: "get",
					success: function(companys,response,status) { 
						$("#project_companyObj_companyUserName_edit").empty();
						var html="";
		        		$(companys).each(function(i,company){
		        			html += "<option value='" + company.companyUserName + "'>" + company.companyName + "</option>";
		        		});
		        		$("#project_companyObj_companyUserName_edit").html(html);
		        		$("#project_companyObj_companyUserName_edit").val(project.companyObjPri);
					}
				});
				$("#project_shzt_edit").val(project.shzt);
				$("#project_shhf_edit").val(project.shhf);
				$("#project_addTime_edit").val(project.addTime);
				$('#projectEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除招商项目信息*/
function projectDelete(projectId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Project/deletes",
			data : {
				projectIds : projectId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#projectQueryForm").submit();
					//location.href= basePath + "Project/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交招商项目信息表单给服务器端修改*/
function ajaxProjectModify() {
	$.ajax({
		url :  basePath + "Project/" + $("#project_projectId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#projectEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#projectQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*招商日期组件*/
    $('.project_zsDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*发布时间组件*/
    $('.project_addTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

