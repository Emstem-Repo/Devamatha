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

<script type="text/javascript" language="javascript">
	// Functions for AJAX 
	function getClasses(examName) {
		getClasesByExamNameNew(examName,document.getElementById("year").value, "classes", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classes", "- Select -");
	}
	function submitEdit(){
		document.adminHallTicketForm.method.value="editReason";
		document.adminHallTicketForm.submit();
	}
	function getExamName() {
		if(document.getElementById("examType").checked==true){
			var examType=document.getElementById("examType").value;
			document.getElementById("classBody").style.display="block";
			document.getElementById("classTitle").style.display="block";
		}else if(document.getElementById("sup").checked==true){
			var examType=document.getElementById("sup").value;
			document.getElementById("classBody").style.display="block";
			document.getElementById("classTitle").style.display="block";
		}else{
			var examType=document.getElementById("internal").value;
			document.getElementById("classBody").style.display="block";
			document.getElementById("classTitle").style.display="block";
		}			
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);

	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examId", "- Select -");
		updateCurrentExam(req, "examId");
		if(document.getElementById("examId").value != null && document.getElementById("examId").value !=""){
			getClasses(document.getElementById("examId").value);
		}
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/adminHallTicket">
	<html:hidden property="formName" value="adminHallTicketForm" />
	<html:hidden property="method" styleId="method" value="getCandidates" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam.exam" /> <span class="Bredcrumbs">&gt;&gt;
			Hall Ticket&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Hall Ticket</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td height="20" class="news">
							<div align="right" class="mandatoryfield"><bean:message
								key="knowledgepro.mandatoryfields" /></div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
										value="<bean:write name="adminHallTicketForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Exam Type:</div></td>
								<td height="25"  class="row-even">
									<div align="left">
									<html:radio property="examType"
										styleId="examType" value="Regular"
										onclick="getExamName()" ></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()" ></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
									<html:radio property="examType" value="Internal"
										styleId="internal"
										onclick="getExamName()" ></html:radio>
									Internal								
									</div>
									</td>
								</tr>
								
								<tr>
									<td width="24%" class="row-odd">
									<div align="right">
									<DIV align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examDefinition.examName" /> :</DIV>
									</div>
									</td>
									<td width="21%"  class="row-even">
									<html:select property="examId" styleClass="combo" styleId="examId" name="adminHallTicketForm" style="width:200px" onchange="getClasses(this.value)">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="adminHallTicketForm" property="examNameMap">
											<html:optionsCollection property="examNameMap" name="adminHallTicketForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td height="15" class="row-odd" >
									<div id="classTitle" align="right"><bean:message
										key="knowledgepro.exam.blockUnblock.class" />:</div>
									</td>
									<td class="row-even">
									<div id="classBody" >
									<nested:select property="classId" styleClass="body" styleId="classes" style="width:45%;height:15">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty property="classMap" name="adminHallTicketForm">
											<html:optionsCollection name="adminHallTicketForm" property="classMap" label="value" value="key" />
										</logic:notEmpty>
									</nested:select>
									</div>
									</td>
								</tr>
								<tr>
									<td width="24%" class="row-odd">
									<div id="regFrom" align="right">
								   	 Register Number:</div>
									</td>
									<td width="21%" class="row-even">
									<html:text property="regNoFrom" size="10"></html:text>
									</td>
									<td width="22%" class="row-odd">
									</td>
									<td width="36%" class="row-even">
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
						<tr>
							<td width="45%" height="35" align="right"><html:submit value="Print Hall Ticket" styleClass="formbutton"></html:submit> </td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="45%" align="left"><html:button property="" value="Reset" onclick="resetFieldAndErrMsgs()" styleClass="formbutton"></html:button> &nbsp; 
								</td>
						</tr>
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
<script language="JavaScript" >
	var print = "<c:out value='${adminHallTicketForm.print}'/>";
	var examType="<c:out value='${adminHallTicketForm.examTypePrint}'/>";
	if(print.length != 0 && print == "true"){
		if(examType=="Regular" || examType=="regular" ||  examType=="Internal")
		{
		var url = "adminHallTicket.do?method=printHallTicket";
		}
		else
		{
			var url = "adminHallTicket.do?method=printHallTicketSupp";
		}
		myRef = window .open(url, "Marks Card", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
		
	}
	if(document.getElementById("examType").checked==true){
		var examType=document.getElementById("examType").value;
		document.getElementById("classBody").style.display="block";
		document.getElementById("classTitle").style.display="block";
		document.getElementById("regTo").style.display="none";
		document.getElementById("regFrom").style.display="none";
		document.getElementById("regFrom1").style.display="block";
		document.getElementById("regTo1").style.display="block";
	}else if(document.getElementById("sup").checked==true){
			var examType=document.getElementById("sup").value;
			document.getElementById("classBody").style.display="block";
			document.getElementById("classTitle").style.display="block";
			document.getElementById("regFrom1").style.display="none";
			document.getElementById("regTo1").style.display="none";
			document.getElementById("regFrom").style.display="block";
			document.getElementById("regTo").style.display="block";
	}else{
		var examType=document.getElementById("internal").value;
		document.getElementById("classBody").style.display="block";
		document.getElementById("classTitle").style.display="block";
		document.getElementById("regTo").style.display="none";
		document.getElementById("regFrom").style.display="none";
		document.getElementById("regFrom1").style.display="block";
		document.getElementById("regTo1").style.display="block";
	}
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examId", updateExamName);
	
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}

	
</script>