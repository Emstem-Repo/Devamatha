<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
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

function cancelAction() {
		document.location.href = "updateExamHallTicket.do?method=initUpdateExamPublishHallTicket";
	}

function updateAction(){
	var isUpdate=validateCheckBox();
	if(isUpdate){
			document.getElementById("method").value="updatePublishHallTicket";
			document.getElementById("pageType").value="2";
			document.updateExamHallTicketForm.submit();
		}
}

function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }

    if(checkBoxselectedCount == 0) {
        document.getElementById("err").innerHTML = "Please select at least one record.";
        document.getElementById("errorMessage").innerHTML = "";
    	return false;
    }    
    else { 
        return true;
    }    
            
}
</script>
<html:form action="/updateExamHallTicket" method="post">
	<html:hidden property="method" styleId="method" value="getPublishHallTicket" />
	<html:hidden property="formName" value="updateExamHallTicketForm" />
	<html:hidden property="pageType" styleId="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.boardDetails.label" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.boardDetails.label" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div id="err"></div>
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.feepays.enddate" /></div>
									</td>
									<td  class="row-even">
									<html:text name="updateExamHallTicketForm" property="endDate" styleId="endDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'updateExamHallTicketForm',
										// input name
										'controlname' :'endDate'
									});
								</script>	
									</td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td height="35" align="center"><html:submit property=""
								styleClass="formbutton" value="search"></html:submit></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<logic:notEmpty property="listTo" name="updateExamHallTicketForm">
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
								<td colspan="2"  class="row-odd"> <div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.update.exam.publish.hallticket.toendDate" /></div></td>
								<td colspan="2"  class="row-even">
								<html:text name="updateExamHallTicketForm" property="toEndDate" styleId="toEndDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'updateExamHallTicketForm',
										// input name
										'controlname' :'toEndDate'
									});
								</script>
								</td>
								<td colspan="2"  class="row-odd"> <div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.revaluationEndDate" /></div></td>
								<td colspan="3"  class="row-even">
								<html:text name="updateExamHallTicketForm" property="toRevEndDate" styleId="toRevEndDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'updateExamHallTicketForm',
										// input name
										'controlname' :'toRevEndDate'
									});
								</script>
								</td>
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="center">Select All
									<br/>
									<input type="checkbox" id="selectall" name="selectall"
												onclick="checkAll()" />
									</div>
									</td>
									<td  height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.hostel.absentees.slno" /></div>
									</td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.Exam.Type" /></td>
									<td  class="row-odd"><bean:message
										key="knowledgepro.petticash.programType" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.classCode" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.ExamName" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.publishType" /></td>
									<td  class="row-odd"><bean:message
										key="knowledgepro.hostel.adminmessage.startDate" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.hostel.adminmessage.enddate" /></td>
								</tr>
								
								<nested:iterate property="listTo" indexId="count">

									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25">
									<div align="center">
									<nested:checkbox property="checked" onclick="unCheckSelectAll(this)"></nested:checkbox>
									</div>
									</td>
									<td height="25">
									<div align="center"><c:out value="${count+1}"></c:out></div>
									</td>
									<td height="25"><nested:write property="examType" /></td>
									<td height="25"><nested:write property="programType" /></td>
									<td height="25"><nested:write property="classcode" /></td>

									<td height="25"><nested:write property="examName" /></td>
									<td height="25"><nested:write property="publishFor" /></td>
									<td height="25"><nested:write property="downloadStartDate" /></td>
									<td height="25"><nested:write property="downloadEndDate" /></td>
								</nested:iterate>
								
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
					<td height="19" valign="top" background="images/left.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
						<tr>
							<td height="35" align="center">
							<html:button property="" styleClass="formbutton" value="update" onclick="updateAction()"></html:button> &nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()"></html:button>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/right.gif"></td>
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
				
				</logic:notEmpty>
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
