<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:form action="/absenceInformationSummary">	
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0">
	<tr>
		<td><img src="images/01.gif" width="5" height="5"></td>
		<td width="914" background="images/02.gif"></td>
		<td><img src="images/03.gif" width="5" height="5"></td>
	</tr>
	<tr>
		<td width="5" background="images/left.gif"></td>
		<td valign="top">
		<table width="100%" cellspacing="1" cellpadding="2" border="1">

			<tr>
				<td  height="25" class="row-odd">
				<div align="center">Registration No./Roll No.</div>
				</td>
				<logic:iterate id="belowReportid" scope="session"  name="belowReport" indexId="count">
					<c:if test="${count == 0}">
						<logic:notEmpty name="belowReportid" property="subjectSummaryList">
							<logic:iterate id="index" name="belowReportid" property="subjectSummaryList" type=" com.kp.cms.to.attendance.SubjectSummaryTO">
									<%String title = index.getSubjectCode(); %>
									<td  class="row-even">
									<div align="center"><%=title%> </div>
									</td>
							</logic:iterate>
						</logic:notEmpty>
					</c:if>
				</logic:iterate>
			</tr>
			<logic:iterate id="belowReportid" scope="session"  name="belowReport" indexId="count">
				<tr>
				<td  height="25" class="row-even">
				<div align="center"><bean:write name="belowReportid" property="regNo"/>
				</div>
				</td>
				<logic:notEmpty name="belowReportid" property="subjectSummaryList">
					<logic:iterate id="index" name="belowReportid" property="subjectSummaryList" type=" com.kp.cms.to.attendance.SubjectSummaryTO">
							<td  class="row-even">
							<div align="center"><bean:write name="index" property="percentage" /></div>
							</td>
					</logic:iterate>
				</logic:notEmpty>
			</tr>
			</logic:iterate>
		</table>
		</td>
		<td width="5" height="30" background="images/right.gif"></td>
	</tr>
	<tr>
		<td height="5"><img src="images/04.gif" width="5"
			height="5"></td>
		<td background="images/05.gif"></td>
		<td><img src="images/06.gif"></td>
	</tr>
</table>
<script type="text/javascript">
	window.print();
</script>
</html:form>