<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<style>
body {
	font-size: 200.5%;
}
<!--
-->
</style>
<script type="text/javascript">
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

function submitform() {
	 document.getElementById("method").value="saveStudentPhoto";
	  document.getElementById("myForm").submit();
	}
</script>
<%-- <%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setHeader("Expires", "0"); // Proxies.
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentLoginAction" method="POST" enctype="multipart/form-data" styleId="myForm">
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="saveStudentPhoto"/>
	<html:hidden property="studentId" styleId="studentId"/>
	<div class="pageheader">
		<div class="media">
			<div class="Container">
				<ul class="breadcrumb">
					<li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
					<li><a href="#">Student Login</a></li>
					<li>Edit Photo	</li>
				</ul>

			</div>
		</div>
		<!-- media -->
	</div>
	<!-- pageheader -->


	<div class="panel-body">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h1 class="panel-title">Edit Photo	</h1>
			</div>
		</div>
		<div class="col-sm-12"
			style="border-style: solid; border-color: #29953b;">
			<div class="form-group">
				<div class="col-sm-4">


					<div id="errorMessage">
								<FONT color="red">
									<html:errors/>
								</FONT>
								<FONT color="green">
									 <html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages>
							    </FONT>
						    </div>
				</div>
			</div>
			<logic:equal value="false" property="photoEdited" name="loginform">
				<logic:equal value="false" property="isUpdated" name="loginform">
			<div class="form-group">
				<div class="col-sm-12">
					<div class="col-sm-4">
						<div id="dataSuppLabel">

							<span class="Mandatory">*</span>Photo
						</div>
					</div>
						<div class="col-sm-4">
						 <img src='<%=request.getContextPath()+"/PhotoServlet?"%>' alt=""
				onerror='onImgError(this);' onLoad='setDefaultImage(this);' height="100" width="100" align="middle"/><br><br>
						 <html:file property="studentPhoto" styleId="editDocument"></html:file>
						</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-center">

					<div class="btn btn-success ">
						<span class="form-controler" onclick="submitform()">Submit</span>
					</div>
					<div class="btn btn-danger ">
						<span class="form-controler" onclick="goToHomePage()">Cancel</span>
					</div>
				</div>
			</div>
			</logic:equal>
			<logic:equal value="true" property="isUpdated" name="loginform">
			<div class="form-group">
				<div class="col-sm-10 text-center">
							<b>Personal Data Updated Successfully</b>
				</div>
			</div>
			</logic:equal>
			</logic:equal>
			<logic:equal value="true" property="photoEdited" name="loginform">
				<div class="form-group">
				<div class="col-sm-10 text-center">
							<div id="dataSuppLabel">
							<b>You Have Updated  Photo already</b><br><br>
							<div class="btn btn-danger ">
						<span class="form-controler" onclick="goToHomePage()">Cancel</span>
					</div>
						</div>
				</div>
			</div>
			</logic:equal>
		</div>

	</div>


</html:form>
