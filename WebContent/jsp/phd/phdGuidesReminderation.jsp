<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
            }
    }
}

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                  }     
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }        
}

function takePrint(){
	document.getElementById("method").value = "checkGuideReminders";
	document.documentSubmissionScheduleForm.submit();
	}

function resetFormFields(){	

	document.getElementById("startDate").value = "";
	document.getElementById("endDate").value = "";
	resetErrMsgs();
}
function cancel(){
	document.location.href = "PhdGuideRemenderation.do?method=initPhdStudyRemenderation";
	}
</script>
<html:form action="/PhdGuideRemenderation">	
		<html:hidden property="method" styleId="method" value="guideDetailsSearch" />
		<html:hidden property="formName" value="documentSubmissionScheduleForm"/>
		<html:hidden property="pageType" value="3" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.phd.Guide.remenderation"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.phd.Guide.remenderation"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
	                       	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
		                         <tr >
		                           <td height="25" class="row-odd" align="center"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                           		<html:text styleId="startDate" property="startDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
											new tcal( {
												// form name
												'formname' :'documentSubmissionScheduleForm',
												// input name
												'controlname' :'startDate'
											});
											</script>
		                           </td>
		                           <td height="25" class="row-odd" align="center"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                            		<html:text styleId="endDate" property="endDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'documentSubmissionScheduleForm',
													// input name
													'controlname' :'endDate'
												});
											</script>
                                   <br></td>
		                         </tr>
	                       </table>
	                       </td>
	                            </tr>
                    							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
						</tr>
						</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
									<logic:present property="studentDetailsList" name="documentSubmissionScheduleForm">
                        	<tr>
										<td width="5" background="images/left.gif"></td>

										<td valign="top">

										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
											<td width="10%" class="row-odd" align="center">
											 SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
											</td>
											  <td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
											   <td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.course" /></div>
												</td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.document.name" /></div>
												</td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.Guide.submitted_date" /></div>
												</td>
												<td width="12%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.Guide" /></div>
												</td>
												<td width="12%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.CoGuide" /></div>
												</td>
												<td width="6%" height="25" class="row-odd">
												<div align="center">Already Generated</div>
												</td>
											</tr>
											<tr>
											<logic:iterate id="studentList" property="studentDetailsList" name="documentSubmissionScheduleForm" indexId="count">
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td class="row-even"  align="center">
														<input type="hidden" name="studentDetailsList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<bean:write name='studentList' property='tempChecked'/>" />
														
														<input type="checkbox" name="studentDetailsList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
														
														<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}	
														</script>
														
													</td>
													<td class="row-even">
														<div align="center"><bean:write property="registerNo"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="left"><bean:write property="studentName"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="left"><bean:write property="courseName"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="left"><bean:write property="documentName"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="center"><bean:write property="submittedDate"
															name="studentList" /></div>
														<input type="hidden" >
													</td>
													<td class="row-even">
														<div align="left"><bean:write property="guide"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="left"><bean:write property="coGuide"
															name="studentList" /></div>
													</td>
													<td class="row-even">
														<div align="center"><bean:write property="printornot"
															name="studentList" /></div>
													</td>
												</logic:iterate>
											</tr> 
										<tr>
                		              	  <td align="center" colspan="8"><div align="center">
							              <html:button property="" styleClass="formbutton" onclick="takePrint()">
							              <bean:message key="knowledgepro.print" /></html:button>&nbsp;&nbsp;&nbsp;
							              <html:button property="" styleClass="formbutton" onclick="cancel()"><bean:message key="knowledgepro.close"/></html:button></div>
							             </td>
                                          </tr>
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
								</logic:present>
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
<script type="text/javascript">
var print = "<c:out value='${documentSubmissionScheduleForm.print}'/>";
if(print.length != 0 && print == "true"){
	var url = "PhdGuideRemenderation.do?method=printGuideReminders";
	myRef = window .open(url, "Guide Reminderation Advice", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>