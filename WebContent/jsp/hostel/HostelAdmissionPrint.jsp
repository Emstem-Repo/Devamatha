<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">

body {
	margin-left: 20px;
	margin-top: 30px;
	margin-right: 10px;
	margin-bottom: 30px;
}

</style>
<style>
checkprint
{
table {page-break-after:always}
}
</style>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>

<html:form action="/HostelAdmission" method="post">
	<html:hidden property="formName" value="hlAdmissionForm" />
	
	<table height="28" width="100%" border="1" bordercolor="black" rules='all' >
		<tr>
			<td valign="top">
				<table width="100%">
              	<tr>
			<td colspan="6" height="10" class="row-white">
			<div align="center"><img
				src='<%=CMSConstants.LOGO_URL%>'
				alt="Logo not available"></div>
			<br></br>
			</td>
		</tr>
		 <tr>
		 <td colspan="8" align="center"><b>Fee Advice</b></td>
		 </tr>
						<logic:iterate id="admission" name="SelectedData"  type="com.kp.cms.to.hostel.HlAdmissionTo" scope="session" indexId="count">
			            <tr>
			            </tr>
			            <tr>
			            <td width="25%" height="25" class="row-white" align="right">
						<div align="right"><bean:message key="knowledgepro.hostel.admission.Applicationregister" /> : </div></td>
					  <td height="25" class="row-white" align="left">
					  <logic:notEmpty name="hlAdmissionForm" property="checkRegNo" >
                      <B><bean:write name="hlAdmissionForm" property="checkRegNo" /></B>
                      </logic:notEmpty>
                      <logic:empty name="hlAdmissionForm" property="checkRegNo" >
                      <B><bean:write name="admission" property="regNo" /></B>
                      </logic:empty>
                      </td>
			            </tr>
			            
			            <tr>
			            <td width="25%" height="25" class="row-white" align="right">
								<div align="right"><bean:message key="knowledgepro.fee.studentname" /> : </div></td>
						<td height="25" class="row-white" align="left"><bean:write name="admission" property="studentName" /></td>
			           </tr>
			           
			           <tr>
			           <td width="25%" height="25" class="row-white" align="right">
								<div align="right"><bean:message key="knowledgepro.admin.course" /> : </div></td>
					  <td height="25" class="row-white" align="left"><bean:write name="admission" property="courseName" /></td>
			           </tr>
			           
			           <tr>
			           	<td width="25%" height="25" class="row-white" align="right">
								<div align="right"><bean:message key="knowledgepro.hostel" /> : </div></td>
						<td height="25" class="row-white" align="left"><B><bean:write name="admission" property="hostelName" /></B></td>
			           </tr>
			           
			           <tr>
			           <td width="25%" height="25" class="row-white" align="right">
								<div align="right"><bean:message key="knowledgepro.roomtype" /> : </div></td>
					  <td height="25" class="row-white" align="left"><B><bean:write name="admission" property="roomTypeName" /></B></td>
			           </tr>
			           <tr height="50"></tr>
				</logic:iterate>
			          </table>
					</td>
		</tr>
	   </table>					
</html:form>
<script type="text/javascript">
	window.print();
</script>