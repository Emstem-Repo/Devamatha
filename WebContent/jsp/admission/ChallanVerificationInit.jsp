<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
checked = false;
function checkAll () {
	if (checked == false) {
		checked = true;
	} else {
		checked = false;
	}

	for (var i=0;i<document.forms[0].elements.length;i++)
	{	
		var e=document.forms[0].elements[i];
		if ((e.type=='checkbox'))
		{
			e.checked=checked;
		}
	}
}
function unCheckSelectAll(field) {

    if(field.checked == false) {
    	document.getElementById("selectall").checked = false;
    }

}
function challanVerifySelected(){
	document.getElementById("method").value="verifyChallan";
}
function cancelAction() {
	document.location.href = "challanVerification.do?method=initChallanVerification";
}

function UnselectCheckBox(selected,val,certId)
{
	var Temp = document.getElementById("hidden_"+val).checked;
	var checkedVal = document.getElementById(val).checked;
	if(!checkedVal) 
		{
		    document.getElementById("hidden_"+val).checked=false;
	   		document.getElementById("hidden_"+val).value=false;
		 }
	else
	{
		document.getElementById(val).checked=true;
		document.getElementById(val).value = "on";
		document.getElementById("hidden_"+val).checked=true;
   		document.getElementById("hidden_"+val).value=true;
	}	
}

</script>
<html:form action="/challanVerification" >
	<html:hidden property="method" styleId="method" value="getStudentInfo" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="challanVerificationForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knwoledgepro.admission.challan.verification" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knwoledgepro.admission.challan.verification" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
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
							<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                    <td width="161" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.challandate"/>:</div></td>
                    <td width="164" class="row-even"><html:text styleId="challanDate" property="challanDate"  styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'challanVerificationForm',
													// input name
													'controlname' :'challanDate'
												});
											</script>
		                           </td> 
		             <td width="10%" height="25" class="row-even" align="center">OR</td>
                    <td width="140" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.appNo"/></div></td>
                    <td width="167" class="row-even"><html:text	property="applicationNo" styleId="applicationNo" styleClass="TextBox" size="20" maxlength="30" /></td>
                  </tr>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="46%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								Search
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="52%" align="left"><html:button property=""
								styleClass="formbutton" onclick="resetFieldAndErrMsgs()">
								<bean:message key="knowledgepro.admin.reset" />
								</html:button> 
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
		<!--  added the result table -->
				<logic:notEmpty name="challanVerificationForm" property="studentInfoList">	
				<tr>
							<td height="25" colspan="6">
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
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											
											<td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.fee.applicationno" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.fee.studentname" /></td>
											<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.course" /></td>
											<td class="row-odd" align="center"><bean:message key="admissionFormForm.challanNo.required" /></td>	
											<td class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.select" /><br>
											<input type="checkbox" id="selectall" name="selectall"
												onclick="checkAll()" /></div>
											</td>
										</tr>
										
										<nested:iterate id="studentInfoList" name="challanVerificationForm" property="studentInfoList" indexId="count">
										<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
															<td width="20%" class="row-even" align="center"><bean:write
																name="studentInfoList" property="applicationNo" /></td>
															<td width="41%" height="25" class="row-even"
																align="center"><bean:write 
																name="studentInfoList" property="studentName" /></td>
															<td width="15%" class="row-even" align="center"><bean:write
																name="studentInfoList" property="courseName" /></td>	
															<td width="15%" class="row-even" align="center"><bean:write
																name="studentInfoList" property="challanNo" /></td>
															<td width="9%" class="row-even">
															<div align="center">
															<input type="hidden" name="studentInfoList[<c:out value='${count}'/>].tempChecked"
															id="hidden_<c:out value='${count}'/>" value="<nested:write name="studentInfoList" property='tempChecked'/>" />
															
															<input type="checkbox" name="studentInfoList[<c:out value='${count}'/>].checked1"
															id="<c:out value='${count}'/>" onclick="unCheckSelectAll(this),UnselectCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')"/>
															
																</div>
																<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																	if(studentId=="true" || studentId=="TRUE"){
																		document.getElementById("<c:out value='${count}'/>").value = "on";
																		document.getElementById("<c:out value='${count}'/>").checked = true;
																	}
																	else
																		{
																		document.getElementById("<c:out value='${count}'/>").value = "off";
																		document.getElementById("<c:out value='${count}'/>").checked = false;
																	}		
																</script>
															</td>
											</tr>	
										</nested:iterate>
										
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
																	<div align="right"><html:submit
																		styleClass="formbutton" onclick="challanVerifySelected()">
																		Submit
																	</html:submit></div>
																	</td>
																	<td width="2%"></td>
																	<td width="53"><html:button property="cancel"
																		onclick="cancelAction()" styleClass="formbutton">
																		<bean:message key="knowledgepro.cancel" />
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
											</div>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
						</logic:notEmpty>
				
				
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
