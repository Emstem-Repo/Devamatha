<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script type="text/javascript">


function editAttMaster(id) {
	document.location.href = "ExamSettings.do?method=editExamSettings&id="+id;
	document.getElementById("submit").value="Update";
	//resetErrMsgs();
}

function deleteAttMaster(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	{
	document.location.href = "ExamSettings.do?method=deleteExamSettings&id="+id;
	}
}

function isValidNumber(field) {
	if (isNaN(field.value)) {
		field.value = "";
	}
}

function reActivate(id) {
	document.location.href = "ExamSettings.do?method=reActivateExamSettings&id="+id;
}
function resetMessages() {
	document.getElementById("absentCodeMarkEntry").value =document.getElementById("orgAbsentCodeMarkEntry").value;
	document.getElementById("withHeldCodeMarkEntry").value =document.getElementById("orgWithHeldCodeMarkEntry").value;
	document.getElementById("notProcedCodeMarkEntry").value =document.getElementById("orgNotProcedCodeMarkEntry").value ;
	document.getElementById("maxAllwdDiffPrcntgMultiEvaluator").value =document.getElementById("orgMaxAllwdDiffPrcntgMultiEvaluator").value ;
	document.getElementById("gradeForFail").value =document.getElementById("orgGradeForFail").value;
	document.getElementById("gradePointForFail").value =document.getElementById("orgGradePointForFail").value;
	var valueName=document.getElementById("orgSecuredMarkEntryBy").value;
	if(valueName.length>0){
	document.getElementById("securedMarkEntryBy").value =document.getElementById("orgSecuredMarkEntryBy").value;
	}
	if(valueName.length==0){
		document.getElementById("securedMarkEntryBy").value ="";
		}
	resetErrMsgs();
	
}

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamSettings.do">
	<html:hidden property="formName" value="ExamSettingsForm" />
    <html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when
			test="${examSettingOperation != null && examSettingOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updatEexamSettings" />
			<html:hidden property="id" styleId="id" />
			<html:hidden property="orgAbsentCodeMarkEntry" styleId="orgAbsentCodeMarkEntry"  />
			<html:hidden property="orgWithHeldCodeMarkEntry" styleId="orgWithHeldCodeMarkEntry"  />
			<html:hidden property="orgNotProcedCodeMarkEntry" styleId="orgNotProcedCodeMarkEntry" />
			<html:hidden property="orgSecuredMarkEntryBy" styleId="orgSecuredMarkEntryBy"  />
			<html:hidden property="orgMaxAllwdDiffPrcntgMultiEvaluator" styleId="orgMaxAllwdDiffPrcntgMultiEvaluator" />
			<html:hidden property="orgGradeForFail" styleId="orgGradeForFail" />
			<html:hidden property="orgGradePointForFail" styleId="orgGradePointForFail"  />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addExamSettings" />
		</c:otherwise>
	</c:choose>

<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.examSettings" /> &gt;&gt;</span></span></td>  
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message key="knowledgepro.exam.examSettings" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					
					<div id="errorMessage"><FONT color="red"></FONT>
					<FONT color="green"> </FONT>
					<div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /> </div>
					</div>

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
                                <tr >
                <td height="26" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.examSettings.AbsentCodeinMarksEntry" /></div></td>
                <td class="row-even"><html:text	property="absentCodeMarkEntry" styleId="absentCodeMarkEntry" maxlength="50"	styleClass="TextBox" size="10"/></td>
                <td class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.examSettings.NotProcessedCodeinMarksEntry" /></div></td>
                <td class="row-even"><html:text	property="notProcedCodeMarkEntry" styleId="notProcedCodeMarkEntry" maxlength="50" styleClass="TextBox" size="10"/></td>
              </tr>
              <tr>
               
                
                <td class="row-odd" height="25"><div align="right"><bean:message key="knowledgepro.exam.examSettings.SecuredMarksEntryBy" /> :</div></td>
                <td class="row-even">
                
                
                <html:select property="securedMarkEntryBy" styleId="securedMarkEntryBy">
									    <html:option value="">Select</html:option>	
									    <html:option value="Roll No">Roll No</html:option>
										<html:option value="Register No">Register No</html:option>	
				</html:select>
                
                
                
                
                
              </td>
                <td class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.examSettings.maxAllowed" /></div></td>
                <td class="row-even"><html:text	property="maxAllwdDiffPrcntgMultiEvaluator" styleId="maxAllwdDiffPrcntgMultiEvaluator" maxlength="10" styleClass="TextBox" size="10" onblur="isValidNumber(this)"/></td>
                  </tr>
                  <tr> 
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.examSettings.Gradeforfail"/></div></td>
                  <td class="row-even"><html:text property="gradeForFail" styleId="gradeForFail" maxlength="10" styleClass="TextBox" size="10"/></td>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.examSettings.Gradepointforfail"/></div></td>
                   <td class="row-even"><html:text property="gradePointForFail" styleId="gradePointForFail" maxlength="10" styleClass="TextBox" size="10" onblur="isValidNumber(this)"/></td>
                   </tr>
                   <tr>
                   <td height="26" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.exam.examSettings.WithHeldCodeinMarksEntry" /></div></td>
                  <td class="row-even"><html:text	property="withHeldCodeMarkEntry" styleId="withHeldCodeMarkEntry" maxlength="50"	styleClass="TextBox" size="10"/></td>
                   </tr>
							</table>							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				
				
				

				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">							
							<div align="right"><c:choose>
								<c:when test="${examSettingOperation != null && examSettingOperation == 'edit'}">
									<input name="submit" type="submit" class="formbutton" value="Update" />
								</c:when>
								<c:otherwise>
									<input name="Submit" type="Submit" class="formbutton" value="Submit" />
								</c:otherwise>
							</c:choose></div>							</td>
							<td width="2%"></td>
							<td width="53%">
							
							<c:choose>
								<c:when test="${examSettingOperation != null && examSettingOperation == 'edit'}">
									<input type="button"  value="Reset" class="formbutton" onclick="resetMessages()">
								</c:when>
								<c:otherwise>
									<input type="Reset"	class="formbutton" value="Reset" onclick="resetErrMsgs()";/>
								</c:otherwise>
							</c:choose>
							
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
									<td width="4%"  height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /> </div>	</td>
									<td width="21%" align="center" class="row-odd"><bean:message key="knowledgepro.exam.examSettings.organisationName" /></td>
									<td width="21%" align="center" height="25" class="row-odd"><bean:message key="knowledgepro.exam.examSettings.AbsentCodeinMarksEntry" /></td>
								    <td width="18%" align="center" class="row-odd"><bean:message key="knowledgepro.exam.examSettings.NotProcessedCodeinMarksEntry" /></td>
								    <td width="21%" align="center" height="25" class="row-odd">With Held code in Marks Entry</td>
								    <td width="18%"align="center" class="row-odd"><bean:message key="knowledgepro.exam.examSettings.SecuredMarksEntryBy" /></td>
								    <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
								    <td width="6%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
								</tr>
								
								   	<c:set var="temp" value="0" />
 									   <logic:iterate name="ExamSettingsForm" property="listExamSetting" id="listExamSetting" type="com.kp.cms.to.exam.ExamSettingsTO" indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td height="25" class="row-even"><div align="center"><c:out value="${count+1}" /></div></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="organisationName" /></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="absentCodeMarkEntry" /></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="notProcedCodeMarkEntry" /></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="withHeldCodeMarkEntry" /></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="securedMarkEntryBy" /></td>
														<td height="25" class="row-even"> <div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editAttMaster('<bean:write name="listExamSetting" property="id"/>')">	</div></td>
														<td height="25" class="row-even"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteAttMaster('<bean:write name="listExamSetting" property="id"/>')"></div></td>
													</tr>
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td class="row-white"><div align="center"><c:out value="${count+1}" /></div></td>
														<td height="25" class="row-white" align="center"><bean:write name="listExamSetting" property="organisationName" /></td>
														<td height="25" class="row-white" align="center"><bean:write name="listExamSetting" property="absentCodeMarkEntry" /></td>
														<td height="25" class="row-white" align="center"><bean:write name="listExamSetting" property="notProcedCodeMarkEntry" /></td>
														<td height="25" class="row-even" align="center"><bean:write name="listExamSetting" property="withHeldCodeMarkEntry" /></td>
														<td height="25" class="row-white" align="center"><bean:write name="listExamSetting" property="securedMarkEntryBy" /></td>
														<td height="25" class="row-white"> <div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editAttMaster('<bean:write name="listExamSetting" property="id"/>')">	</div></td>
														<td height="25" class="row-white"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteAttMaster('<bean:write name="listExamSetting" property="id"/>')"></div></td>
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</logic:iterate>
						</table>							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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