<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "ScoreSheet.do?method=initScoreSheet";
}
function printICard(){
	var url ="ScoreSheet.do?method=printScoreSheetResult";
	myRef = window.open(url,"scoreSheet","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
    }
</SCRIPT>
<html:form action="/ScoreSheet" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="scoreSheetForm" />
	<html:hidden property="pageType" value="1"/>
	<table width="98%" border="0">
		<tr>
			<td class="heading"><bean:message key="knowledgepro.reports"/>&gt;&gt;
			<span class="Bredcrumbs"><bean:message
				key="knowledgepro.report.scoreSheetreport" /><span
				class="Bredcrumbs">&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.report.scoreSheetreport" /></strong></div>
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
							<td><img src="images/left.gif" width="5" height="10" /></td>
							<td class="heading">
							<bean:message key="knowledgepro.report.scoresheet.totalNo"/>:&nbsp;
							<bean:write name="scoreSheetForm" property="totalStudents"/>
							</td>
							<td><img src="images/right.gif" width="5" height="10" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<div style="overflow: auto; width: 914px;"><c:set
								var="temp" value="0" /> 
								<table >
										<logic:iterate id="screenId" name="SelectedCandidates"  type="com.kp.cms.to.reports.ScoreSheetTO" scope="session" indexId="count">
													<tr class="row-odd">
														<td colspan="7">
														For Date: <c:out value="${screenId.date}"/> at <c:out value="${screenId.time}"/>
														</td>
													</tr>
													<tr class="row-even">
														<td >
														
														<bean:define id="count" name="screenId" property="count" type="java.lang.Integer"></bean:define>										
														
														
															<%if(!CMSConstants.LINK_FOR_CJC){ %>
																<img src='images/StudentPhotos/<c:out value="${screenId.studentId}"/>.jpg?<%=System.currentTimeMillis() %>'  height="90Px" width="90Px" alt="Image not found"/>
										                   <%} else{%>
										                   		<img src='<%=request.getContextPath()%>/StudentPhotoServlet?count=<%=count%>'  height="90Px" width="90Px" alt="Image not found"/>
										                   	<%} %>
															
														</td>
															
														<td >
															<table>
																<tr class="row-even">
																<td>
																Appl NO: <c:out value="${screenId.applicationNo}"/>
																</td>
																</tr>
																<tr class="row-even">
																<td>
																DOB	:   <c:out value="${screenId.dateOfBirth }"></c:out>
																</td>
																</tr>
																<tr class="row-even">
																<td>
																Name: <c:out value="${screenId.applicantName}"/>
																</td>
																</tr>
																<tr class="row-even">
																<td>
																Gender: <c:out value="${screenId.gender}"/>
																</td>
																</tr>
															</table>
														</td>
														<logic:notEmpty name="screenId" property="interviewSubRoundsTOList">
										<logic:iterate id="index" name="screenId" property="interviewSubRoundsTOList" type="com.kp.cms.to.admission.InterviewSubroundsTO">
											<%String title = index.getName(); %>
											<td class="row-odd" width="100">
											<%=title %>
												
												<logic:notEmpty name="index" property="gradeList">
												<table>
												<logic:iterate id="gradeIndex" name="index" property="gradeList" type="com.kp.cms.bo.admin.Grade">
													<tr class="row-even">
													
													<td class="row-even" width="100"><bean:write name="gradeIndex" property="grade"/></td>
													</tr>
												</logic:iterate>
												</table>
												</logic:notEmpty>
											</td>
										</logic:iterate>
									</logic:notEmpty>
									
									<logic:empty name="screenId" property="interviewSubRoundsTOList">
									<td class="row-odd" width="100">
									<bean:write name="screenId" property="name"/>
									
									<logic:notEmpty name="screenId" property="gradeList">
												<table>
												<logic:iterate id="gradeIndex" name="screenId" property="gradeList" type="com.kp.cms.bo.admin.Grade">
													<tr class="row-even">
													
													<td class="row-even" width="100"><bean:write name="gradeIndex" property="grade"/></td>
													</tr>
												</logic:iterate>
												</table>
												</logic:notEmpty>
									</td>
									
									</logic:empty>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="61" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:button property="" styleId="printme" styleClass="formbutton" value="Print" onclick="printICard()"></html:button></div>
							</td>
							<td width="1%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" value="Cancel" onclick="cancelAction()">
								
								</html:button> 
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