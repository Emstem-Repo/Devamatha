<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<html:form action="PerformaIII" method="post">
	<html:hidden property="method" styleId="method" value="initPerformaIII" />
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.performaIII" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.performaIII" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="8" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="8" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center" class="heading"> <bean:write name="performaIIIForm" property="organizationName"/> </td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
						<tr>
			              <td width="5"  background="images/left.gif"></td>
			      		  <td align="center"> <span class="heading"><bean:message key="knowledgepro.report.performaIII" /></span></td>
			              <td width="5" height="30"  background="images/right.gif"></td>
			            </tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<div style="overflow: auto; width: 914px;">
							<display:table export="true" uid="studentAttendance" name="sessionScope.performaIIIReport" requestURI="" defaultorder="ascending" pagesize="20" style="width:100%">
								<display:setProperty name="export.excel.filename" value="PerformaIII.xls" />
								<display:setProperty name="export.xml" value="false" />
								<display:setProperty name="export.csv.filename" value="PerformaIII.csv" />
									<display:column title="Course Name" property="courseName" headerClass="row-odd" class="row-even" sortable="true" group="1" style="width:10%"></display:column>
									<display:column title="Max. Intake" property="courseIntake" headerClass="row-odd" class="row-even" sortable="true" style="width:20%"></display:column>
									<% int total = 0;
									   String title = "";
									   if(studentAttendance != null){%>
									<logic:notEmpty name="studentAttendance" property="categoryList">
									<logic:iterate id="index" name="studentAttendance" property="categoryList" type=" com.kp.cms.to.reports.PerformaIIIMapTO">
									<%  title = index.getCategoryName();
										total = total + index.getIntakeValue();
									%>
										<display:column title="<%=title %>" class="row-even" headerClass="row-odd" style="width:10%" sortable="true">
										<bean:write name="index" property="intakeValue" />
									</display:column>
									</logic:iterate>
									</logic:notEmpty>
									<%} %>
									<display:column title="Total" class="row-even" headerClass="row-odd" style="width:10%" sortable="true"><%=total %></display:column>
							</display:table>
							</div>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="61" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">

					<table width="100%" height="48" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="25">
							<div align="center">
							<html:submit property="Cancel" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:submit>
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
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>