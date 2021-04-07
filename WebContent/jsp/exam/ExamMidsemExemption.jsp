<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

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
function goBack(){
	document.location.href = "ExamMidsemExemption.do?method=initExamMidsemExemption";
}
function saveExemptionSubjects(){
	document.getElementById("method").value = "saveExemptionSubjects";
	document.ExamMidsemExemptionForm.submit();
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 300;
	return (Object.value.length < MaxLen);
}
</script>

<html:form action="/ExamMidsemExemption" method="post">
<html:hidden property="formName" value="ExamMidsemExemptionForm" />
<html:hidden property="pageType" value="2" />
<html:hidden property="method" styleId="method" value="searchStudentSubjects"/>
<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs">Exam
		<span class="Bredcrumbs">&gt;&gt;Mid-Sem Repeat Less Weightage Exemption</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Add Subjects for Exemption</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif">
					<div id="errorMessage"></div>
					</td>
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
							<td height="1" colspan="2" class="heading" align="left">
										Student Details
									</td>
							</tr>
							<tr>
								<td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/></div></td>
                                <td width="25%" class="row-even"><bean:write name="ExamMidsemExemptionForm" property="regNo" /></td>
                                
                                <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
                                <td width="25%" class="row-even"><bean:write name="ExamMidsemExemptionForm" property="student.studentName" /></td>
                             </tr>
                             <tr>
                            	 <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.classname"/>:</div></td>
                                <td width="25%" class="row-even"><bean:write name="ExamMidsemExemptionForm" property="student.className" /></td>
                                
                             	<td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.exam.examDefinition.examName"/>:</div></td>
                                <td width="25%" class="row-even"><bean:write name="ExamMidsemExemptionForm" property="examName" /></td>
                             </tr>
                             <tr>
							
							<td width="25%" height="25" class="row-odd"><div align="right">
                                    <bean:message key="knowledgepro.feepays.Reason"/>:</div></td>
                                    <td width="25%" class="row-even" colspan="3"><div align="left"> <span class="star">
                                    <input type="hidden" name="ta" id="ta"
										value='<bean:write name="ExamMidsemExemptionForm" property="reason"/>' />
                                    <html:textarea property="reason" styleClass="TextBox" styleId="reason" 
									cols="40" onkeypress="return imposeMaxLength(event,this)"></html:textarea>
                                    </span></div></td>
							</tr>
                             
                              <tr>
                              <td colspan="4">
                              <table width="100%" cellspacing="1" cellpadding="2" align="center">
								<tr>
								<td height="1" colspan="2" class="heading" align="left">
										Student Subjects
								</td>
								</tr>
								<tr height="30">
							<td width="10%" height="25" class="row-odd" align="center">Sl. No.</td>
							<td width="20%" height="25" class="row-odd" align="center">Subject Code</td>
							<td width="66%" height="25" class="row-odd" align="center">Subject Name</td>
							<td width="4%" height="25" class="row-odd" align="center" >Select
							<input type="checkbox" id="checkAll" onclick="selectAll(this)" height="35"/></td>
						</tr>
						 
						<logic:notEmpty name="ExamMidsemExemptionForm" property="student.subjectList">
						<nested:iterate id="to" name="ExamMidsemExemptionForm" property="student.subjectList" indexId="count">
											<% String midSemExemption="midSemExemption_"+count;
												%>
				       <tr>
				              <td width="3%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
				              
				              	<td height="100%" class="row-even" align="center"><bean:write name="to" property="code" /></td>
								<td height="100%" class="row-even" align="left"><bean:write name="to" property="subjectName" /></td>
										
								<td width="4%" class="row-even" align="center">
									<div align="center">
									
									<input type="hidden" name="student.subjectList[<c:out value='${count}'/>].tempChecked1"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='to' property='tempChecked1'/>" />
									<input type="checkbox" name="student.subjectList[<c:out value='${count}'/>].checked1" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
												<script type="text/javascript">
													var checkedId = document.getElementById("hidden_<c:out value='${count}'/>").value;
													if(checkedId == "on") {
														document.getElementById("<c:out value='${count}'/>").checked = true;
													}
													else
														document.getElementById("<c:out value='${count}'/>").checked = false;
												</script>
									</div>
								</td>
							</tr>
							</nested:iterate>
				             </logic:notEmpty>
				             
							</table>
								
							</td></tr>
							
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
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
					<tr>
							<td width="49%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Save"
										onclick="saveExemptionSubjects()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
									<html:button property="" styleClass="formbutton" value="Back"
										onclick="goBack()"></html:button>
						</tr>					
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
					</table>
					</td>
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