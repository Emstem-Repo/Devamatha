<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="employee.info.title"/></title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="js/employee/employeeInfo.js"></script>


<script language="JavaScript" >
function editEmployeeInfo(name){
	document.location.href = "employeeInfoSubmit.do?method=getEmployeeInfoDetails&firstName="+name;
}
</script>


<link href="css/styles.css" rel="stylesheet" type="text/css">

</head>
<html:form action="/employeeInfoSubmit">
<html:hidden property="formName" value="employeeInfoForm"/>
<table width="99%" border="0">

		<tr>
			<td><span class="heading"><bean:message key="employee.info.title"/><span class="Bredcrumbs"></span></span></td>

		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message key="employee.info.title"/></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">		</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							 <table width="100%" cellspacing="1" cellpadding="2">
				<tr>
				  	<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /> </div>	</td>
					<td height="25" class="row-odd"><bean:message key="employee.info.code" /></td>
					<td class="row-odd"><bean:message key="employee.info.firstName" /></td>
					<td class="row-odd"><bean:message key="employee.info.midName" /></td>
					<td class="row-odd"><bean:message key="employee.info.lastName" /></td>
					<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /> </div></td>
				</tr>

		<logic:iterate name="employeeInfoForm" property="listOfEmployees" id="listOfEmployees" indexId="count" type="com.kp.cms.to.admin.EmployeeInformactionTO">
			<c:choose>
				<c:when test="${count%2 == 0}">
					<tr class="row-even">
				</c:when>
				<c:otherwise>
					<tr class="row-white">
				</c:otherwise>
			</c:choose>
			<td class="bodytext"><div align="center"><c:out value="${count + 1}" /></div></td>
			<td align="center" class="bodytext"><bean:write name="listOfEmployees" property="code" /></td>
			<td align="center" class="bodytext"><bean:write name="listOfEmployees" property="firstName" /></td>
			<td align="center" class="bodytext"><bean:write name="listOfEmployees" property="middleName" /></td>
			<td align="center" class="bodytext"><bean:write name="listOfEmployees" property="lastName" /></td>
			<td align="center" class="bodytext"><div align="center"><img src="images/edit_icon.gif" height="18" style="cursor: pointer" onclick="editEmployeeInfo('<bean:write name="listOfEmployees" property="id"/>')"></div> </td>
		</tr>
		</logic:iterate>

		</table>

							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>


