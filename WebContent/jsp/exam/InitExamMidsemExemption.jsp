<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">

function verifyRegAndGetClasses(regNo){
	document.getElementById("regNo").value = regNo;
	document.getElementById("method").value = "assignClassListToForm";
	document.ExamMidsemExemptionForm.submit();
}
function resetMessages(){
	document.location.href = "ExamMidsemExemption.do?method=initExamMidsemExemption";
	resetErrMsgs();
}
function searchStudentSubjects() {
	document.getElementById("method").value = "searchStudentSubjects";
	document.ExamMidsemExemptionForm.submit();
}
function changeYear(year){
	document.getElementById("academicYear").value = year;
	getExamByYearOnlyInternal("examList", year, "examId", updateExams);
}
function updateExams(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("examId");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	destination.options[0]=new Option("- Select -","");
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j+1] = new Option(label,value);
	 }
}
</script>

<html:form action="/ExamMidsemExemption" method="post">
<html:hidden property="formName" value="ExamMidsemExemptionForm" />
<html:hidden property="pageType" value="1" />
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
						class="boxheader">Mid-Sem Repeat Less Weightage Exemption</strong></td>
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
							<td class="row-odd" width="25%" height="25">
							<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
							<td class="row-even" width="25%" height="25"><div align="left">
							<input type="hidden" value="<bean:write name="ExamMidsemExemptionForm" property="year"/>" id="tempYear">
		                           <html:select property="year" styleId="academicYear" name="ExamMidsemExemptionForm" style="width:130px" onchange="changeYear(this.value)">
                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       	   				 <cms:renderAcademicYear></cms:renderAcademicYear>
                       			   </html:select></div>
		        			</td>
							<td class="row-odd" width="20%" height="25"><div align="right">
							<span class="Mandatory">*</span>&nbsp;Exam Name:</div></td>
							<td class="row-even"  width="30%" height="25">
							<html:select name="ExamMidsemExemptionForm" property="examId" styleId="examId" styleClass="comboLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="examList"
													name="ExamMidsemExemptionForm" label="value" value="key" />
											
							</html:select></td>
							</tr>
							<tr>
							<td width="25%" height="25" class="row-odd" ><div align="right">
                                    <span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.exam.blockUnblock.regNo"/>:</div></td>
                                    <td width="25%" class="row-even"><div align="left"> <span class="star">
                                               <html:text property="regNo" styleId="regNo" size="17" onchange="verifyRegAndGetClasses(this.value)" />
                                    </span></div></td>
							
								<td width="20%" height="25" class="row-odd" ><div align="right">
								<span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.attendance.activityattendence.class"/>:</div></td>
								
								<td class="row-even"  width="30%" height="25">
								<logic:notEmpty name="ExamMidsemExemptionForm" property="classList">
								<html:select name="ExamMidsemExemptionForm" property="classId" styleId="classId" styleClass="comboLarge">
										
										<html:optionsCollection property="classList"
													name="ExamMidsemExemptionForm" label="value" value="key" />
								</html:select></logic:notEmpty>
								<logic:empty name="ExamMidsemExemptionForm" property="classList">
								<html:select name="ExamMidsemExemptionForm" property="classId" styleId="classId" styleClass="comboLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										
								</html:select>
								</logic:empty>								
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
									<html:button property="" styleClass="formbutton" value="Search"
										onclick="searchStudentSubjects()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetMessages()"></html:button></td>
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
<script type="text/javascript">
var tempYear=document.getElementById("tempYear").value;
if(tempYear!=null && tempYear!=""){
	document.getElementById("academicYear").value=tempYear;
}
</script>