<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎使用Java第二课堂后台管理程序</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript">
$(function(){
	$("#left").myaccordion();
});
</script>
</head>
<body>
<div id="left">
<ul class="navMenu navSelected">
	<h3 class="navTitle">
		<span class="navTilteTxt">用户管理</span>
	</h3>
	<li class="navChild">
		<a href="<%=request.getContextPath() %>/admin/user/users" target="content">用户信息管理</a>
	</li>
	<li class="navChild">
		<a href="<%=request.getContextPath() %>/admin/group/groups" target="content">用户组管理</a>
	</li>
	<li class="navChild">
		<a href="<%=request.getContextPath() %>/admin/role/roles" target="content">用户角色管理</a>
	</li>
</ul>

<ul class="navMenu navSelected">
	<h3 class="navTitle">
		<span class="navTilteTxt">文章管理</span>
	</h3>
	<li class="navChild">
		<a href="<%=request.getContextPath() %>/admin/channel/cm" target="content">栏目信息管理</a>
	</li>
	<li class="navChild">
		<a href="<%=request.getContextPath() %>/admin/article/publishs" target="content">文章信息管理</a>
	</li>
</ul>
	<ul class="navMenu">
		<h3 class="navTitle">
			<span class="navTilteTxt">系统配置</span>
		</h3>
		<li class="navChild">
			<a href="<%=request.getContextPath() %>/admin/hyperlink/hyperlinks" target="content">超级链接管理</a>
		</li>
		<li class="navChild">
			<a href="<%=request.getContextPath() %>/admin/webInfo/showWebInfo" target="content">网站信息管理</a>
		</li>
		<li class="navChild">
			<a href="<%=request.getContextPath() %>/admin/newImgManage/newImgManage" target="content">首页图片管理</a>
		</li>
		<li class="navChild">
			<a href="<%=request.getContextPath() %>/admin/backup/sqls" target="content">网站数据备份</a>
		</li>
		<li class="navChild">
			<a href="<%=request.getContextPath() %>/admin/systemClean/articleList" target="content">系统清理管理</a>
		</li>
	</ul>
		</div>
</body>
</html>