<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Project" %>
<%@ page import="com.chengxusheji.po.Company" %>
<%@ page import="com.chengxusheji.po.Hangye" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的companyObj信息
    List<Company> companyList = (List<Company>)request.getAttribute("companyList");
    //获取所有的hangyeObj信息
    List<Hangye> hangyeList = (List<Hangye>)request.getAttribute("hangyeList");
    Project project = (Project)request.getAttribute("project");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>查看招商项目详情</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li><a href="<%=basePath %>Project/frontlist">招商项目信息</a></li>
  		<li class="active">详情查看</li>
	</ul>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">项目id:</div>
		<div class="col-md-10 col-xs-6"><%=project.getProjectId()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">所属行业:</div>
		<div class="col-md-10 col-xs-6"><%=project.getHangyeObj().getHangyeName() %></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">项目名称:</div>
		<div class="col-md-10 col-xs-6"><%=project.getProjectName()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">项目图片:</div>
		<div class="col-md-10 col-xs-6"><img class="img-responsive" src="<%=basePath %><%=project.getProjectPhoto() %>"  border="0px"/></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">招商日期:</div>
		<div class="col-md-10 col-xs-6"><%=project.getZsDate()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">项目地点:</div>
		<div class="col-md-10 col-xs-6"><%=project.getXmAddress()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">招商地点:</div>
		<div class="col-md-10 col-xs-6"><%=project.getZsAddress()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">投资额度:</div>
		<div class="col-md-10 col-xs-6"><%=project.getTzed()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">联系方式:</div>
		<div class="col-md-10 col-xs-6"><%=project.getLxfs()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">项目介绍:</div>
		<div class="col-md-10 col-xs-6"><%=project.getProjectDesc()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">招商单位:</div>
		<div class="col-md-10 col-xs-6"><%=project.getCompanyObj().getCompanyName() %></div>
	</div>
	<div class="row bottom15" style="display:none;"> 
		<div class="col-md-2 col-xs-4 text-right bold">审核状态:</div>
		<div class="col-md-10 col-xs-6"><%=project.getShzt()%></div>
	</div>
	<div class="row bottom15" style="display:none;"> 
		<div class="col-md-2 col-xs-4 text-right bold">审核回复:</div>
		<div class="col-md-10 col-xs-6"><%=project.getShhf()%></div>
	</div>
	<div class="row bottom15"> 
		<div class="col-md-2 col-xs-4 text-right bold">发布时间:</div>
		<div class="col-md-10 col-xs-6"><%=project.getAddTime()%></div>
	</div>
	<div class="row bottom15">
		<div class="col-md-2 col-xs-4"></div>
		<div class="col-md-6 col-xs-6">
			<button onclick="userBaoming();" class="btn btn-primary">我要报名</button>
			<button onclick="history.back();" class="btn btn-primary">返回</button>
		</div>
	</div>
</div> 
<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script>
var basePath = "<%=basePath%>";

function userBaoming() {

	$.ajax({
		url : basePath + "Baoming/companyAdd",
		type : "post",
		dataType: "json",
		data: {
			"baoming.projectObj.projectId": <%=project.getProjectId() %>, 
		},
		success : function (data, response, status) {
			//var obj = jQuery.parseJSON(data);
			if(data.success){
				alert("回复成功~");
				location.reload();
			}else{
				alert(data.message);
			}
		}
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
 })
 </script> 
</body>
</html>

