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

     function addExamCceFactor() {
	    document.getElementById("method").value="addExamCceFactor";
                            }
    function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
	    }
    function editExamCceFactor(id) {
	document.location.href = "ExamCceFactor.do?method=editExamCceFactor&id="+id;
	}
   function updateExamCceFactor() {
	document.getElementById("method").value = "updateExamCceFactor"; 
	}
   function deleteExamCceFactor(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "ExamCceFactor.do?method=deleteExamCceFactor&id="+id;
		}
	}
    function resetErrorMsgs() {
		document.location.href = "ExamCceFactor.do?method=initExamCceFactor";
	}
	function getformlists(){
		var year=document.getElementById("year").value;
		document.getElementById("method").value = "getCCEfactorLists";
		document.examCceFactorForm.submit();
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
</script>

<html:form action="/ExamCceFactor" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="examCceFactorForm" />
	<html:hidden property="pageType" value="1" />
	<c:choose>
	<c:when test="${CceFactor == 'edit'}">
		<html:hidden property="method" styleId="method" value="editExamCceFactor" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addExamCceFactor" />
	</c:otherwise>
   </c:choose>
	
	<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
	<link href="../css/styles.css" rel="stylesheet" type="text/css">
	<table width="99%" border="0">

		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.exam" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.examCceFactor" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td colspan="2" background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.exam.examCceFactor" /></td>
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
							<td  height="30" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.year" /> :</div></td>
							<td height="25" class="row-even">
							<input type="hidden" id="tempYear" name="tempYear" value="<bean:write name="examCceFactorForm" property="year"/>">
							<html:select property="year" styleId="year" styleClass="combo" onchange="getformlists();">
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							<cms:renderAcademicYear></cms:renderAcademicYear> </html:select></td>
						
								<td width="25%"  height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div></td>
								<td height="25" class="row-even"><html:select
										property="examType" styleClass="combo"  styleId="examType" onchange="getExamsByExamTypeAndYear()" style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="examCceFactorForm">
											<html:optionsCollection property="examTypeList"
												name="examCceFactorForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
						
							</tr>
							<tr>
							<td width="25%" height="30" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.examCceFactor" /> :</div></td>
							<td height="25" class="row-even"> <html:text property="cceFactor" styleClass="TextBox" styleId="cceFactor" 
							size="20" maxlength="4" name="examCceFactorForm" /></td>
							<td width="25%" height="30" class="row-odd"></td>
							<td height="25" class="row-even"></td>
							</tr>
							<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="selectExam" styleClass="body" multiple="multiple" size="10" style="width:480px" styleId="examName">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="examCceFactorForm" property="examNameMap">
										<html:optionsCollection name="examCceFactorForm" property="examNameMap" label="value" value="key" /></logic:notEmpty>
									</html:select></td>

									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admisn.subject.Name" /> :</div>
									</td>
									<td height="25" class="row-even"><html:select
										property="selectedSubject" styleClass="body" styleId="subjectName" multiple="multiple" size="10" style="width:480px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="examCceFactorForm" property="subjectMap">
										<html:optionsCollection name="examCceFactorForm" property="subjectMap" label="value"
													value="key" /></logic:notEmpty>
									</html:select></td>
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
							
						   <tr>
                         <td width="45%" height="35"><div align="center">
                     <c:choose>
            		<c:when test="${CceFactor == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateExamCceFactor()"><bean:message key="knowledgepro.update"/></html:submit>
              	   		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>&nbsp;&nbsp;&nbsp;
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addExamCceFactor()"><bean:message key="knowledgepro.submit"/></html:submit>&nbsp;&nbsp;&nbsp;
                		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>&nbsp;&nbsp;&nbsp;
		               <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                 </tr>
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
											<td width="30%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.examDefinition.examName" /></td>
											<td width="10%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admisn.subject.code" /></td>
											<td width="35%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.admisn.subject.Name" /></td>
											<td width="10%" height="25" class="row-odd" align="center"><bean:message
												key="knowledgepro.exam.examCceFactor" /></td>
											<td width="5%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
											<td width="5%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
										</tr>



										<c:set var="temp" value="0" />
                                       <logic:notEmpty name="examCceFactorForm" property="examCceFactorList">
										<logic:iterate name="examCceFactorForm" property="examCceFactorList" id="CME" indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td width="5%" height="25" class="row-even"><div align="center"><c:out value="${count+1}" /></div></td>
														<td width="30%" height="25" class="row-even" align="left"><bean:write
															name="CME" property="examName" /></td>
														<td width="10%" height="25" class="row-even" align="left"><bean:write
															name="CME" property="code" /></td>
														<td width="35%" height="25" class="row-even" align="left"><bean:write
															name="CME" property="subjectName" /></td>
														<td width="10%" height="25" class="row-even" align="right"><bean:write
															name="CME" property="cceFactor" /></td>
														<td width="5%" height="25" class="row-even">
														<div align="center"><img src="images/edit_icon.gif" width="16" height="16"  style="cursor: pointer"
															onclick="editExamCceFactor('<bean:write name="CME" property="id"/>')"></div></td>
														<td width="5%" height="25" class="row-even" ><div align="center">
                   			                            <img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteExamCceFactor('<bean:write name="CME" property="id"/>')"></div></td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td  width="5%" height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td width="30%" height="25" class="row-white" align="left"><bean:write
															name="CME" property="examName" /></td>
														<td width="10%" height="25" class="row-white" align="left"><bean:write
															name="CME" property="code" /></td>
														<td width="35%" height="25" class="row-white" align="left"><bean:write
															name="CME" property="subjectName" /></td>
														<td width="10%" height="25" class="row-white" align="right"><bean:write
															name="CME" property="cceFactor" /></td>
														<td width="5%" height="25" class="row-white">
														<div align="center"><img src="images/edit_icon.gif" width="16" height="16" style="cursor: pointer"
															onclick="editExamCceFactor('<bean:write name="CME" property="id"/>')">
														</div>
														</td>
														<td width="5%" height="25" class="row-white" ><div align="center">
                   			                            <img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteExamCceFactor('<bean:write name="CME" property="id"/>')"></div></td>
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
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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

</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempYear").value;
	if(year!= 0) {
	 	document.getElementById("year").value=year;
	}
</script>
