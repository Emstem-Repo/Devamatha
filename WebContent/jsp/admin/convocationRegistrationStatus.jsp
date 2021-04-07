<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>
<SCRIPT><!--
function resetFields() {
	document.getElementById("regNo").value="";
}
function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  if(inputObj.checked) {
                	  document.getElementById("checked1").value="on";
                  }     
            }
    }
}
function cancel(){
	document.location.href="ConvocationRegistrationStatus.do?method=initConvocationRegistrationStatus";
}
function saveData() {
	document.getElementById("method").value="saveDetails";
	document.convocationRegistrationStatusForm.submit();
}
function send_data()
{   
	var textBox = document.getElementById("regNo");
	var textLength = textBox.value.length;
	if(textLength ==7){
		document.getElementById("method").value="getResults";
		document.convocationRegistrationStatusForm.submit();
	}
}


</SCRIPT>
</head>
<html:form action="/ConvocationRegistrationStatus" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="getResults" />
	<html:hidden property="formName" value="convocationRegistrationStatusForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Admin
			&gt;&gt; Convocation Registration Status&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Convocation Registration Status</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
							<table width="100%" cellspacing="1" cellpadding="2">
							<% boolean disable=false;%>
										<logic:equal value="true" name="convocationRegistrationStatusForm" property="flag">
										<% disable=true;%>
										</logic:equal>
								<tr>
								     <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<html:select 
										property="year" styleId="year"
										styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td height="25" width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.verification.student.regNo" /></div>
									</td>
									<td  height="25" class="row-even"><html:text
										property="registerNo" styleId="regNo"
										styleClass="TextBox" size="14" maxlength="7"  onkeyup="send_data()" disabled='<%=disable%>'/></td>
								</tr>
							</table>
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
				<logic:equal value="true" property="flag" name="convocationRegistrationStatusForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          				 <tr>
								<td valign="top" class="news">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5" /></td>
										<td width="914" background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5" /></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td valign="top" >
										<table width="100%" cellspacing="1" cellpadding="2" >
										<% boolean disable1=false;%>
										<logic:equal value="true" name="convocationRegistrationStatusForm" property="flag1">
										<% disable1=true;%>
										</logic:equal>
										<% boolean disable2=false;%>
										<logic:equal value="true" name="convocationRegistrationStatusForm" property="flag2">
										<% disable2=true;%>
										</logic:equal>
											<tr>
											<td   height="25" class="row-odd" colspan="2">
												<div align="right" >Registered for Convocation:</div>
												</td>
												<td  height="25" class="row-even" colspan="2">
												<div align="left"><c:if test="${convocationRegistrationStatusForm.studentRegistration != null && convocationRegistrationStatusForm.studentRegistration  != ''}">
												<FONT size="3"><bean:write name="convocationRegistrationStatusForm" property="studentRegistration"/></FONT><br/></c:if></div>
												</td>
											</tr>
											<tr>
											<td   height="25" class="row-odd" colspan="2">
												<div align="right" >Course Name:</div>
												</td>
												<td  height="25" class="row-even" colspan="2">
												<div align="left"><c:if test="${convocationRegistrationStatusForm.courseName != null && convocationRegistrationStatusForm.courseName != ''}">
												<FONT size="3"><bean:write name="convocationRegistrationStatusForm" property="courseName"/></FONT><br/></c:if></div>
												</td>
											</tr>
											<tr>
											<td   height="25" class="row-odd" colspan="2">
												<div align="right" >Convocation Session Date:</div>
												</td>
												<td  height="25" class="row-even" colspan="2">
												<div align="left"><c:if test="${convocationRegistrationStatusForm.cdate != null && convocationRegistrationStatusForm.cdate  != ''}">
												<FONT size="2"><bean:write name="convocationRegistrationStatusForm" property="cdate"/></FONT></c:if>
												<c:if test="${convocationRegistrationStatusForm.timeType != null && convocationRegistrationStatusForm.timeType != ''}">
												<FONT size="2"><bean:write name="convocationRegistrationStatusForm" property="timeType"/></FONT></c:if></div>
												</td>
											</tr>
											
											<tr>
											<td  height="25" class="row-odd" colspan="2">
												<div align="right" align="center">No of Guest Pass:</div>
												</td>
												<td align="center" height="25" class="row-even" colspan="2">
												<div align="left"><c:if test="${convocationRegistrationStatusForm.guestPassCount != null && convocationRegistrationStatusForm.guestPassCount  != ''}">
												<FONT size="3"><bean:write name="convocationRegistrationStatusForm" property="guestPassCount"/></FONT><br/></c:if></div>
												</td>
											</tr>
											<tr>
											<td height="25" width="25%" class="row-odd" colspan="2">
												<div align="right">Convocation Pass No1:</div>
											</td>
												<td  height="25" class="row-even" colspan="2"><html:text
													property="passNo1" styleId="passNo1" maxlength="20"
													styleClass="TextBox" size="20"  disabled='<%=disable2%>'/>
												</td>
												</tr>
												<tr>
											<td height="25" width="25%" class="row-odd" colspan="2">
												<div align="right">Convocation Pass No2:</div>
											</td>
											<logic:equal value="true" name="convocationRegistrationStatusForm" property="flag2">
												<td  height="25" class="row-even" colspan="2"><html:text
													property="passNo2" styleId="passNo2" maxlength="20"
													styleClass="TextBox" size="20" disabled='<%=disable2%>'/>
												</td>
											</logic:equal>
											<logic:notEqual value="true" name="convocationRegistrationStatusForm" property="flag2">
												<td  height="25" class="row-even"><html:text
													property="passNo2" styleId="passNo2" maxlength="20"
													styleClass="TextBox" size="20" disabled='<%=disable1%>' />
												</td>
											</logic:notEqual>
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
								</td>
							</tr>
	          		</table>
	          	</td>
	          	<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	          </tr>
	          <tr>
	          <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				<td class="heading">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td style="height: 10px" align="center" colspan="2"></td></tr>
					<tr>
						<td width="49%" height="35" align="right"><input
							type="button" class="formbutton" value="Submit"
							onclick="saveData()" />&nbsp;&nbsp;&nbsp;&nbsp;</td>

						<td width="49%" height="35" align="left"><input
							type="button" class="formbutton" value="Close"
							onclick="cancel()" /></td>
					</tr>					
					<tr><td style="height: 10px" align="center" colspan="2"></td></tr>					
				</table>
				</td>
				<td valign="top" background="images/Tright_3_3.gif"></td>
			</tr>
				</logic:equal>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"></td>
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
