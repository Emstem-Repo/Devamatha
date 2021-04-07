<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">

<script type="text/javascript">

function exportExcel() {
	document.getElementById("method").value = "initexportToExcel";
	document.studentDetailsReportForm.submit();
}
function cancelAction() {
	document.location.href = "studentDetailsReport.do?method=initStudentDetailsReport";
}

</script>

<html:form action="/studentDetailsReport">
<html:hidden property="method" styleId="method" value="initexportToExcel"/>
<html:hidden property="formName" value="studentDetailsReportForm" />
<html:hidden property="pageType" value="2"/>

<table width="98%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.reports.student.details.report" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.reports.student.details.report" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td height="15" colspan="10" class="row-white">
							<div align="left"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>

							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
					<div style="overflow: auto; width: 750px; ">		
									<c:set var="temp" value="0" />
										<display:table export="false" name="sessionScope.StudentSearch" requestURI="" defaultorder="descending" pagesize="10">
										<c:choose>
										<c:when test="${temp == 0}">											
											<display:setProperty name="export.excel.filename" value="studentDetailsReport.xls"/>	
											<display:column style="padding-right: 40px;" property="registerNo" sortable="true" title="Register No" class="row-even" headerClass="row-odd"/>
											<display:column style="padding-right: 80px;" property="studentName" sortable="true" title="Name" class="row-even" headerClass="row-odd"/>
											<display:column style="padding-right: 40px;" property="className" sortable="true" title="Class Name" class="row-even" headerClass="row-odd"/>
											<display:column style="padding-right: 40px;" property="gender" sortable="true" title="Gender" class="row-even" headerClass="row-odd"/>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<display:column style="padding-right: 40px;" property="registerNo" sortable="true" title="Register No" class="row-white" headerClass="row-odd"/>
											<display:column style="padding-right: 80px;" property="studentName" sortable="true" title="Name" class="row-white" headerClass="row-odd"/>
											<display:column style="padding-right: 40px;" property="className" sortable="true" title="Class Name" class="row-white" headerClass="row-odd"/>
											<display:column style="padding-right: 40px;" property="gender" sortable="true" title="Gender" class="row-white" headerClass="row-odd"/>
											<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>										
										</display:table>
									</div>	
						Export options:&nbsp;<a href="#" onclick="exportExcel()" >Excel</a>
						</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" height="54" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="50" class="heading">
							<div align="center">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">

								<tr>
									<td>
									<div align="center">
									<table width="100%" height="27" border="0" cellpadding="0"
										cellspacing="0">
										<tr>
											<td width="52%" height="45">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="45%" height="35">
													<div align="right"></div>
													</td>
													<td width="2%"><html:button property="cancel"
														onclick="cancelAction()" styleClass="formbutton">
														<bean:message key="knowledgepro.cancel" />
													</html:button></td>
													<td width="53"></td>
												</tr>
											</table>
											</td>
										</tr>
									</table>
									</div>
									</td>
								</tr>
							</table>
							</div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				 <tr>
      <td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
        <td background="images/TcenterD.gif" width="100%"></td>
        <td><img src="images/Tright_02.gif" height="29" width="9"></td>        
      </tr>
			</table>
		</td>
	</tr>
</table>
    
<logic:notEmpty name="studentDetailsReportForm" property="downloadExcel">

	<bean:define id="downloadExcels" property="downloadExcel" name="studentDetailsReportForm"></bean:define>
	<logic:equal name="studentDetailsReportForm" property="downloadExcel" value="download">

		<SCRIPT type="text/javascript">	
		var url ="DownloadStudentDetailsExcelAction.do";
		myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
		</SCRIPT>
	</logic:equal>

</logic:notEmpty>
    
</html:form>
															