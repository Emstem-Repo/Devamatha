<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="java.util.Map,java.util.HashMap"%>

<script language="JavaScript">
function submitExcel() {
	document.getElementById("method").value = "exportToExcel";
}
    function searchpendingExamResults(){
    	 document.getElementById("method").value="searchpendingExamResults";
                    }
	function getExamsByExamTypeAndYear() {
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function cancelclose() {
    	document.location.href = "PendingExamResults.do?method=initPendingExamResults";
    }
	function viewDetails(examId,classId){
		var url = "PendingExamResults.do?method=viewDetails&examId="+examId+"&classId="+classId;
		myRef = window
				.open(url, "ViewResume",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
	function viewValuationStatus(examId,classId,status){
		var url = "PendingExamResults.do?method=displayValuationStatus&examId="+examId+"&classId="+classId+"&examType="+status;
		myRef = window
				.open(url, "ViewResume",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
</script>

<html:form action="/PendingExamResults" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="pendingExamResultsForm" />
	<html:hidden property="pageType" value="1" />
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.exam" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.pendingexamresults" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.exam.pendingexamresults" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" class="news"><div align="right"><FONT color="red"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="42" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							<td  height="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.year" /> :</div></td>
							<td height="25" class="row-even">
							<input type="hidden" id="tempYear" name="tempYear" value="<bean:write name="pendingExamResultsForm" property="year"/>">
							<html:select property="year" styleId="year" styleClass="combo" onchange="getExamsByExamTypeAndYear();">
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<cms:renderAcademicYear></cms:renderAcademicYear> </html:select></td>
						
								<td width="20%"  height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="pendingExamResultsForm">
											<html:optionsCollection property="examTypeList"
												name="pendingExamResultsForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
						
							</tr>
							<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="selectExam" styleClass="body" multiple="multiple" size="10" style="width:400px" styleId="examName">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="pendingExamResultsForm" property="examNameMap">
										<html:optionsCollection name="pendingExamResultsForm" property="examNameMap" label="value" value="key" /></logic:notEmpty>
									</html:select></td>
								<td width="20%" height="25" class="row-odd"></td>
						     	<td height="25" class="row-even"></td>
							     </tr>
							</table>
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr></tr>

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
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>	
						 <tr>
                         <td width="45%" height="35"><div align="center">
                        <html:submit property="" styleClass="formbutton" onclick="searchpendingExamResults()"><bean:message key="knowledgepro.admin.search"/></html:submit>&nbsp;&nbsp;&nbsp;
                        </div></td>
                 </tr>						 
                 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>                 
			 </table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
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
											<td width="5%" height="25" class="row-odd"><div align="center"><bean:message  key="knowledgepro.slno" /></div></td>
											<td width="25%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.examDefinition.examName" /></td>
											<td width="15%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.cancelattendance.batches" /></td>
											<td width="15%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.attendanceentry.class" /></td>
											<td width="15%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admission.details" /></td>
											<td width="25%" height="25" class="row-odd" align="center"><bean:message
											key="knowledgepro.exam.pending.exam.results.valuationstatus" /></td>
											</tr>



										<c:set var="temp" value="0" />
                                       <logic:notEmpty name="pendingExamResultsForm" property="examPendingResultList">
										<logic:iterate name="pendingExamResultsForm" property="examPendingResultList" id="CME" indexId="count" type="com.kp.cms.to.exam.PendingExamResultsTO">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even"><div align="center"><c:out value="${count+1}" /></div></td>
														<td width="25%" height="25" class="row-even" align="left"><bean:write
															name="CME" property="examName" /></td>
														<td width="15%" height="25" class="row-even" align="center"><bean:write
															name="CME" property="batch" /></td>
														<td width="15%" height="25" class="row-even" align="left"><bean:write
															name="CME" property="className" /></td>
													 	<td width="15%" class="row-even" align="center">
											           	<%if(CME.getStatuses().equalsIgnoreCase("Supplementary")) {%>
									                   		<a href="javascript:void(0)" onclick="viewDetails('<bean:write name="CME" property="examId"/>','<bean:write name="CME" property="classId"/>')">
								                   			Show
								                   			</a>
									                   <%} %>
								                   		</td>
								                   		<td width="25%" class="row-even" align="center">
								                   		
								                   		<%if(CME.getStatuses().equalsIgnoreCase("Supplementary")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationStatus('<bean:write name="CME" property="examId"/>','<bean:write name="CME" property="classId"/>','<bean:write name="CME" property="statuses"/>')">
									                   		Valuation Status
								                   			</a>
									                   <%} %>
								                   		</td>
															
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td  width="5%" height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="25%" height="25" class="row-white" align="left"><bean:write
															name="CME" property="examName" /></td>
														<td width="15%" height="25" class="row-white" align="center"><bean:write
															name="CME" property="batch" /></td>
														<td width="15%" height="25" class="row-white" align="left"><bean:write
															name="CME" property="className" /></td>
													  <td width="15%" class="row-white" align="center">
											           	<%if(!CME.getStatuses().contains("Regular")) {%>
									                   		<a href="javascript:void(0)" onclick="viewDetails('<bean:write name="CME" property="examId"/>','<bean:write name="CME" property="classId"/>')">
								                   			Show
								                   			</a>
									                   <%} %>
								                   		</td>
								                   		<td width="25%" class="row-white" align="center">
								                   		<%if(CME.getStatuses().equalsIgnoreCase("Supplementary")) {%>
									                   		<a href="javascript:void(0)" onclick="viewValuationStatus('<bean:write name="CME" property="examId"/>','<bean:write name="CME" property="classId"/>','<bean:write name="CME" property="statuses"/>')">
									                   		Valuation Status
								                   			</a>
									                   <%} %>
								                   		</td>
								                   		
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</logic:iterate>
				                        </logic:notEmpty>
				              		</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
					<td colspan="2" valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<logic:notEmpty name="pendingExamResultsForm" property="examPendingResultList">		
						   <tr><td style="height: 10px" align="center" colspan="1"></td></tr>							   
						   <tr>
                         <td width="45%" height="35"><div align="center">
                        <html:submit property="" styleClass="formbutton" onclick="submitExcel()">Export to Excel</html:submit>&nbsp;&nbsp;&nbsp;
                        <html:button property="" styleClass="formbutton" onclick="cancelclose()"><bean:message key="knowledgepro.close" /></html:button>
                		</div></td>
                 </tr>						 
                 <tr><td style="height: 10px" align="center" colspan="1"></td></tr>	                 
                </logic:notEmpty>
			 </table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td colspan="2" background="images/TcenterD.gif"></td>

					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
<logic:notEmpty name="pendingExamResultsForm" property="mode">
<SCRIPT type="text/javascript">	
		var url ="PendingExamResultsExport.do";
		myRef = window.open(url," ","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
		</SCRIPT>
</logic:notEmpty>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempYear").value;
	if(year!= 0) {
	 	document.getElementById("year").value=year;
	}
</script>
