<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%><SCRIPT type="text/javascript">
	
	function printMe()
	{
		window.print();
	}
	function closeMe()
	{
		window.close();
	}
</SCRIPT>

<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<html:form action="/admissionFormSubmit" method="POST">
	<html:hidden property="method" value="updateApproval" />
	<html:hidden property="formName" value="admissionFormForm" />
	<html:hidden property="pageType" value="16" />
	<input type="hidden" name="applicationId"
		value='<bean:write name="admissionFormForm" property="applicantDetails.applicationId" />' />
	<input type="hidden" name="courseId" value='<bean:write	name="admissionFormForm" property="applicantDetails.course.id" />' />
	<table width="98%" border="0">
		
		<tr>
			<td>
			<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="black">
				
				<tr>
					
					<td valign="top" class="news">
					<div align="center">
					<table width="98%"  border="0" cellpadding="1"
						cellspacing="2" bordercolor="black">
						
						<tr>
							<td align="left">
							<div id="errorMessage">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages></FONT>
							</div>
							</td>
						</tr>
						<tr>
							<td class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0" bordercolor="black">
								
								<tr>
									
									<td height="22" valign="top">
									<table width="98%" height="22" border="0" cellpadding="0"
										cellspacing="1">
										<tr >
									        <td width="20%" height="25" rowspan="2" class="row-white" ><div align="center"><img src='<%=CMSConstants.LOGO_URL%>' alt="Logo not available" width="98" height="103"></div></td>
									        <td height="62" class="row-white" ><div align="center">
									            <table width="98%" height="22" border="0" cellpadding="0"
														cellspacing="1" bordercolor="black">
														<tr class="row-white">
															<td height="20" class="row-odd">
															<div align="center"><b><bean:write property="organizationName" name="admissionFormForm"/></b></div>
															</td>
															
														</tr>
														<tr class="row-white">
															
															<td width="515" height="20" class="row-even" align="center">&nbsp;<bean:write property="orgAddress" name="admissionFormForm"/>
															</td>
														</tr>
													</table>
											</div></td>
									        <td width="20%" height="25" class="row-white" ><div align="center"><img src='<%=request.getContextPath()%>/PhotoServlet'  height="150Px" width="150Px" /></div></td>
									     </tr>
									</table>
									</td>
									
								</tr>
								<tr>
							<td class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0" bordercolor="black">
								
								<tr>
									
									<td height="76" valign="top">
									<table width="100%" height="76" border="0" cellpadding="0"
										cellspacing="1" bordercolor="black">
										<tr class="row-white">
											
											<td  height="23" width="50%" class="row-odd">
											<div align="right"><b>APPLICATION FORM FOR </b></div>
											</td>
											<td  height="23" width="50%" class="row-even" align="left">&nbsp;
											<bean:write
												name="admissionFormForm"
												property="applicantDetails.course.programCode" />
											</td>
											
										</tr>
										
									</table>
									</td>
									
								</tr>
								
							</table>
							</td>
						</tr>
								<tr>
									
									<td height="22" valign="top">
									<table width="98%" height="22" border="0" cellpadding="0"
										cellspacing="1" bordercolor="black">
										<tr class="row-white">
											<td width="394" height="20" class="row-odd">
											<div align="right"><b><bean:message
												key="knowledgepro.admission.applicationnumber" />:</b></div>
											</td>
											<td width="515" height="20" class="row-even" align="left">&nbsp;<bean:write
												name="admissionFormForm"
												property="applicantDetails.applnNo" /></td>
										</tr>
									</table>
									</td>
									
								</tr>
								
							</table>
							</td>
						</tr>
						
						
						<tr>
							<td class="heading" align="center">&nbsp;<B>QUALIFYING EXAM DETAILS:</B></td>
						</tr>
						<tr>
							<td width="100%" class="heading">
							
							<table width="98%" border="0" align="top" cellpadding="0"
								cellspacing="0" bordercolor="black">
							
								<nested:iterate property="applicantDetails.ednQualificationList" id="ednQualList" indexId="count">
									<logic:equal value="true" name="ednQualList" property="lastExam">
										<tr>
										
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Qualification:</B></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;
													<bean:write name="ednQualList" property="docName" />
											</td>
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Year and Month of Pass:</B></div>
											</td>
											<td width="25%" colspan="2" height="25" class="row-even" align="left">&nbsp;
																<bean:write name="ednQualList" property="yearPassing" />,
																<logic:equal name="ednQualList" property="monthPassing" value="1">JANUARY</logic:equal>
												              	<logic:equal name="ednQualList" property="monthPassing" value="2">FEBRUARY</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="3">MARCH</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="4">APRIL</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="5">MAY</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="6">JUNE</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="7">JULY</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="8">AUGUST</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="9">SEPTEMBER</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="10">OCTOBER</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="11">NOVEMBER</logic:equal>
																<logic:equal name="ednQualList" property="monthPassing" value="12">DECEMBER</logic:equal>
											</td>
										
										</tr>
										<tr>
											
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Institute:</B></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;
													<bean:write name="ednQualList"
																property="institutionName" />
											</td>
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Board or University:</B></div>
											</td>
											<td width="25%" height="25" colspan="2" class="row-even" align="left">&nbsp;
													<bean:write name="ednQualList"
																property="universityName" />
											</td>
											
										</tr>
										<tr>
											
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Total Mark:</B></div>
											</td>
											<td width="25%" height="25" class="row-even" align="left">&nbsp;
													<nested:write property="marksObtained"></nested:write>
											</td>
											<td width="25%" height="45" class="row-odd">
													<div align="left"><B>Obtained Mark:</B></div>
											</td>
											<td width="25%" height="25" colspan="2" class="row-even" align="left">&nbsp;
													<nested:write property="totalMarks"></nested:write>
											</td>
											
										</tr>
										<logic:equal value="true" property="semesterWise" name="ednQualList">
										<logic:equal value="false" property="consolidated" name="ednQualList">
											
										<nested:iterate property="semesterList" id="semId"  type="com.kp.cms.to.admin.ApplicantMarkDetailsTO" name="ednQualList" indexId="count">
												
											 <tr >
									            <td colspan="2"><div align="left"><nested:write property="semesterName" name="semId"></nested:write></div> </td>
												<td><div align="left"><nested:write property="maxMarks" name="semId"></nested:write> </div></td>
									            <td><div align="left"><nested:write property="marksObtained" name="semId"></nested:write> </div></td>
								            </tr>
												
										</nested:iterate> 




										</logic:equal>
										</logic:equal>
										<logic:equal value="false" property="consolidated" name="ednQualList">
										<logic:equal value="false" property="semesterWise" name="ednQualList">
											<% for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SUBJECTS;i++) {
													String propertyName="detailmark.subject"+i;
													String totalMarkprop="detailmark.subject"+i+"TotalMarks";
													String obtainMarkprop="detailmark.subject"+i+"ObtainedMarks";
											%>	
											<tr>
						                         
												
												 <td colspan="2"><nested:write property="<%=propertyName %>" ></nested:write></td>
												<td ><nested:write property="<%=obtainMarkprop %>" name="ednQualList"></nested:write></td>	
						 						<td ><nested:write property="<%=totalMarkprop %>" name="ednQualList"></nested:write></td>
												
											</tr>
											<%} %>
											

										</logic:equal>
										</logic:equal>


									</logic:equal>


								</nested:iterate>
								
							</table>
							
							</td>
							
						</tr>
						


						
					
						<tr>
							<td align="center">
							<table width="98%" border="0" cellspacing="0" cellpadding="0" bordercolor="black">
								<tr>
									<td width="48%" height="21">
									<div align="right"><html:button property="" onclick="printMe()" styleClass="formbutton">
										Print
									</html:button></div>
									</td>
									<td width="1%"><div align="center"></div></td>
									<td width="51%" height="45" align="left"><html:button
										property="" styleClass="formbutton"
										onclick="closeMe()">
										<bean:message key="knowledgepro.close" />
									</html:button></td>
								</tr>
							</table>
							</td>
						</tr>
						
					</table>
					</div>
					</td>
				
				</tr>
				
			</table>
			</td>
		</tr>
	</table>
</html:form>