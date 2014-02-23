<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/login.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<title>后台管理登录</title>
<script type="text/javascript">

$(function(){
	$.cms_inc.showDate($("#showDate"));
	$.cms_validate.loginValidate($("#myForm"));
	
});
function reCheckcode(img) {
	img.src="drawCode?"+Math.random();
}

</script>
</head>
<body>
	<div id="container">
		<div id="top">
		
		</div>
		<div id="loginBar">
			<span id="showDate">，欢迎你使用昭通市技术监督局网站后台管理程序，请登录</span>
		</div>
		<div id="content">
			<div id="loginForm">
					<table cellpadding="0" cellspacing="0" width="380px" id="loginTable">
						<tr>
							<td align="right" width="90">登录用户:</td>
							<td align="left"><sf:input path="username" size="25"/> </td>
						</tr>
						<tr>
							<td align="right">登录密码:</td>
							<td align="left"><sf:password path="password" size="25"/></td>
						</tr>
						<tr>
							<td align="right">输入验证码:</td>
							<td align="left">
							<input type="text" name="validateCode" id="validateCode" size="15"/>
							${error}
							</td>
						</tr>
						<tr>
							<td align="left" colspan="2">
							<span style="margin-left:94px;cursor:pointer">
							<img src="drawCode" onclick="reCheckcode(this)"/></span>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<input type="submit" value="登录">&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/>
							</td>
						</tr>
					</table>
			</div>
		</div>
	</div>	
</body>
</html>