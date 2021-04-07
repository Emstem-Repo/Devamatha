<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript" language="javascript">
	// Functions for AJAX 
	var status="";
	function getClasses(examName) {
		getClasesByExamName("classMap", examName, "classes", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classes", "- Select -");
	}
	function submitEdit(){
		document.grievanceStudentForm.method.value="editReason";
		document.grievanceStudentForm.submit();
	}
	function getExamName() {
		if(document.getElementById("examType").checked==true)
			var examType=document.getElementById("examType").value;
			else if(document.getElementById("sup").checked==true)
				var examType=document.getElementById("sup").value;
			
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
	//	getExamNameByExamType("examMap", examType, "examId", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examId", "- Select -");
		updateCurrentExam(req, "examId");
	}

	function getSemisters(examName) {
		getSemistersByExamName("classMap", examName, "classes", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classes", "- Select -");
	}
	function printPass(){
		var  acyear = document.getElementById("year").value;
		var  eType = document.getElementById("examType").checked;
		var  exType = document.getElementById("examType").value;
		var  eId = document.getElementById("examId").value;
		var cla = document.getElementById("classes").value;
		
        if(status=="")
        {
        	document.getElementById("displayingErrorMessage").innerHTML="Please Enter Anything"; 
        	
        }else if(status=="regNo")
        {
            if(acyear==null || acyear=="")
            {
            	document.getElementById("displayingErrorMessage").innerHTML="Please Enter Academic year"; 
            }
            else if(eType=='false'){
            	document.getElementById("displayingErrorMessage").innerHTML="Please select exam type"; 
                  
            }else if(eId==null || eId==""){
            	document.getElementById("displayingErrorMessage").innerHTML="Please select exam"; 
                  
            }else if(cla==null || cla==""){
            	document.getElementById("displayingErrorMessage").innerHTML="Please select semister"; 
                  
            }else {
            	document.getElementById("displayingErrorMessage").innerHTML="";
            	document.location.href  = "GrievanceStudent.do?method=getCandidates&year=" +acyear + "&examType=" + exType + "&examId=" + eId + "&classId=" + cla;
				
            }
        }
	}


	function disableClassProgramm() {
		status = "regNo";
		var grievNo=document.getElementById('year').value;
		var regNo=document.getElementById('examType').value;
		var stuName=document.getElementById('examId').value;
		var sem=document.getElementById('classes').value;
		if((year!=null && year!="")||(examType!=null && examType!="") || (examId!=null && examId!="")||(classes!=null && classes!=""))
		{
		document.getElementById('date').disabled = true;
		document.getElementById('year').disabled = false;
		document.getElementById('examType').disabled = false;
		document.getElementById('examId').disabled = false;
		document.getElementById('classes').disabled = false;
		}else{
			enableFields();
		}
	}

	function enableFields() {
		status = "";
		document.getElementById('date').disabled = false;
		document.getElementById('year').disabled = false;
		document.getElementById('examType').disabled = false;
		document.getElementById('examId').disabled = false;
		document.getElementById('classes').disabled = false;
		document.getElementById("displayingErrorMessage").innerHTML="";
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/GrievanceStudent">
	<html:hidden property="formName" value="grievanceStudentForm" />
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Grievance Student List &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Grievance Student List</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <FONT size="2px" color="red"><div id="displayingErrorMessage"></div></FONT>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
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
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="grievanceStudentForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamName(),disableClassProgramm()" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> Exam Type:</div></td>
									<td height="25" colspan="6" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="examType" value="Regular"
										onclick="getExamName()" ></html:radio>
									Regular
							<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()" ></html:radio>
									Supplementary									
									</div>
									</td>

								</tr>
								<tr>
									<td width="14%" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</DIV>
									</div>
									</td>
									<td width="15%"  class="row-even">
									<html:select property="examId" styleClass="combo" styleId="examId" name="grievanceStudentForm" style="width:200px" onchange="getSemisters(this.value),disableClassProgramm()">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="grievanceStudentForm" property="examNameMap">
											<html:optionsCollection property="examNameMap" name="grievanceStudentForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td width="15%" height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Semester:</div>
									</td>
									<td width="15%" class="row-even">
									<nested:select property="classId" styleClass="body" styleId="classes" style="width:45%;height:15" onchange="disableClassProgramm()">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty property="classMap" name="grievanceStudentForm">
											<html:optionsCollection name="grievanceStudentForm" property="classMap" label="value" value="key" />
										</logic:notEmpty>
									</nested:select></td>
									
								</tr>
								
								  <tr><td align="center" colspan="4">OR</td></tr>
								  
								  <tr>
									<td width="14%" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span>Date :</DIV>
									</div>
									</td>
									<td class="row-even" width="16%"><html:text name="grievanceStudentForm" size="15" styleId="date" property="date" styleClass="TextBox"/>
                 <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'grievanceStudentForm',
													// input name
													'controlname' :'date'
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35" align="right"><html:submit value="Search" styleClass="formbutton" ></html:submit> </td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:button property="" value="Reset" onclick="resetFieldAndErrMsgs()" styleClass="formbutton"></html:button> &nbsp; 
								</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
					</table>
					</td>
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
