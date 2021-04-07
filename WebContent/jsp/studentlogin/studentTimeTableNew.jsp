<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

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
.row-ev{
	background-color: #3498DB !important;
    color: #fff;
    font-size: 20px;
}
.table-active{
	font-size: 15px;
}
<!--
-->
</style>
<script type="text/javascript">
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/StudentLoginAction" >
<html:hidden property="formName" value="loginform"/>
<html:hidden property="pageType" value="1"/>
	<div class="pageheader">
		<div class="media">
			<div class="Container">
				<ul class="breadcrumb">
					<li><a href="#"><i class="glyphicon glyphicon-log-in"></i></a></li>
					<li><a href="#">Student Login</a></li>
					<li>Time Table	</li>
				</ul>

			</div>
		</div>
		<!-- media -->
	</div>
	<!-- pageheader -->


	<div class="panel-body">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h1 class="panel-title">Time Table</h1>
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
			<div class="form-group">
				<div class="col-sm-12">
				<div class="dt-responsive table-responsive">
						<table width="100%" class="table" cellspacing="1" cellpadding="1" >
									 <logic:empty name="loginform" property="classTos">
									 	Time Table Not Updated
									 </logic:empty>
									 <logic:notEmpty name="loginform" property="classTos">
										
										<tr class="table-success row-ev">
	            	                        <td width="7%" align="center">Day</td>
	                                         	<logic:notEmpty name="loginform" property="periodList">
	            		                        <logic:iterate id="bo" name="loginform" property="periodList">
	            		                        <th width="10%"><bean:write name="bo" property="periodName"/><%-- (<bean:write name="bo" property="startTime"/> - <bean:write name="bo" property="endTime"/>)  --%></th>&nbsp;&nbsp;
	            		                         </logic:iterate>
	            	                             </logic:notEmpty>
	                                    </tr>
										<logic:notEmpty name="loginform" property="periodList">
										 <logic:iterate id="to" name="loginform" property="classTos">
	                                        <tr class="table-active" height="50px">
					                          <td align="center"><bean:write name="to" property="showWeek"/> </td>
				                        	<logic:notEmpty name="to" property="timeTablePeriodTos">
	            	                    	<logic:iterate id="pto" name="to" property="timeTablePeriodTos">
	            		                           <td>
	            		                         <bean:write name="pto" property="periodName"/> </td>
	            	                   	</logic:iterate>
	            	                   	</logic:notEmpty>
	            	                   	</tr>
	            	                   </logic:iterate>
										</logic:notEmpty>
										</logic:notEmpty>
									</table>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-center">

					<div class="btn btn-danger ">
						<span class="form-controler" onclick="goToHomePage()">Cancel</span>
					</div>
				</div>
			</div>
		</div>

	</div>


</html:form>
