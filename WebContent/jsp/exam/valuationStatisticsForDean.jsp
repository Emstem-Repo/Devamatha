<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function getSubjectWise(deptId,departmentName){
		document.getElementById("departmentName").value=departmentName;
		document.location.href="ValuationStatistics.do?method=getSubjectWise&deptId="+deptId+"&departmentName="+departmentName;
	}
	function cancelPage(){
		document.location.href="ValuationStatistics.do?method=initValuationStatisticsForDean";
	}
</script>
<html:form action="/ValuationStatistics">
	<html:hidden property="formName" value="valuationStatisticsForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="displayDeaneryWise" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="valuationStatisticsForm" property="examType"/>' />
	<html:hidden property="deaneryName" styleId="deaneryName" name="valuationStatisticsForm"/>
	<html:hidden property="departmentName" styleId="departmentName" name="valuationStatisticsForm"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Statistics</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
							<logic:notEmpty property="departmentWise" name="valuationStatisticsForm">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr class="row-odd" height="25"><td align="center" colspan="6"><span class="heading">Department Wise View</span></td></tr>
								<tr class="row-odd">
									<td height="25" align="center">Department</td>
									<td height="25" align="center">Total no of papers	</td>
									<td height="25" align="center">Valuation not yet started	</td>
									<td height="25" align="center">Valuation in progress	</td>
									<td height="25" align="center">Valuation Completed </td>
									<td height="25" align="center">Subject Wise </td>
								</tr>
									<nested:iterate id="to" property="departmentWise" name="valuationStatisticsForm" indexId="count" type="com.kp.cms.to.exam.ValuationStatisticsTO">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
											<td height="25">&nbsp;&nbsp;<nested:write property="departmentName"/> </td>
											<td height="25" align="center"><nested:write property="totalSubjects"/></td>
											<td height="25" align="center"><nested:write property="valuationNotStarted"/></td>
											<td height="25" align="center"><nested:write property="valuationInProcess"/></td>
											<td height="25" align="center"><nested:write property="valuationCompleted"/></td>
											<% String url = "ValuationStatistics.do?method=getSubjectWise&deptId="+to.getDepartmentId()+"&departmentName="+to.getDeaneryName(); %>
											<td height="25" align="center"><a target="_blank" href="<%=url %>">Subject Wise</a></td>
									</nested:iterate>
							</table>
							</logic:notEmpty>
							</td>

							<td width="5" background="images/right.gif"></td>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" height="35" align="center">
								<input type="button" class="formbutton" value="Close" onclick="cancelPage()" />
							</td>
						</tr>
						
					</table>
					</td>
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