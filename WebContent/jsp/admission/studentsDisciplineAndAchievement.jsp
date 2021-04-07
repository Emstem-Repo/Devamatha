<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

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
#basicTable td{
	padding: 15px;
}
<!--
-->
</style>
<script type="text/javascript">
	function goToHomePage() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage"; 
	}
	function downLoadFile(id) {
		document.location.href = "StudentLoginAction.do?method=downloadFile&fileName="+id; 
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentLoginAction">
	<html:hidden property="formName" value="loginform" />
	<div class="pageheader">
		<div class="media">
			<div class="Container">
				<ul class="breadcrumb">
					<li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
					<li><a href="#">Student Login</a></li>
					<li><logic:equal value="Discipline action" name="loginform"
							property="type">Discipline action</logic:equal> <logic:equal
							value="Achievement" name="loginform" property="type">Achievements</logic:equal>
					</li>
				</ul>

			</div>
		</div>
		<!-- media -->
	</div>
	<!-- pageheader -->


	<div class="panel-body">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h1 class="panel-title"><logic:equal value="Discipline action" name="loginform"
							property="type">Discipline action</logic:equal> <logic:equal
							value="Achievement" name="loginform" property="type">Achievements</logic:equal></h1>
			</div>
		</div>
		<div class="col-sm-12"
			style="border-style: solid; border-color: #29953b;">



			<div class="form-group">
				<div class="col-sm-12">
					<logic:notEmpty name="loginform"
						property="disciplineAndAchivementToList">
						<logic:iterate id="to" name="loginform"
							property="disciplineAndAchivementToList">
							<table id="basicTable" class="table table-bordered table-striped"
								width="100%">
								<tr>
									<td width="10%"><bean:write name="to" property="date" /></td>
									<td width="75%"><bean:write name="to" property="descryption" /></td>
									<td width="15%" style="padding: 0" onclick="downLoadFile('<bean:write name="to" property="fileName"/>')"><logic:notEmpty name="to" property="fileName"><ul class="nav nav-pills nav-stacked">
											<li class="parent"  ><a href="#"><i class="fa fa-arrow-circle-o-down"></i> <span>Certificate</span></a></li>
										</ul></logic:notEmpty></td>
								</tr>
							</table>
						</logic:iterate>
					</logic:notEmpty>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-10 text-center">

					<div class="btn btn-danger ">
						<span class="form-controler" onclick="goToHomePage()">Cancel</span>
					</div>
				</div>
			</div>
		</div>

	</div>


</html:form>
