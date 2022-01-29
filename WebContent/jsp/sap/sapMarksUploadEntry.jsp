<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<SCRIPT>
$(document).ready(function() {
	$('#submit').click(function(e){
		var examName=$('#examName').val();
		var uploadedFile=$('#uploadedFile').val();
		 if(examName=="" && uploadedFile=="")
		 {
			 $('#errorMessage').slideDown().html("<span>Exam Name is required.<br> Upload File is required.</span>");
			 return false;
		 }
		 else if(examName=="")
		  {
		  $('#errorMessage').slideDown().html("<span>Exam Name is required.</span>");
		  return false;
		  }else if(uploadedFile==""){
			  $('#errorMessage').slideDown().html("<span>Upload File is required.</span>");
			  return false;
		  }
	});
});
	function goToFirstPage() {
		document.location.href = "sapMarksUploadEntry.do?method=initSapMarksUpload";
	}
	function getExamsByExamTypeAndYear() {
		if(document.getElementById("reg").checked==true)
		var examType=document.getElementById("reg").value;
		else if(document.getElementById("int").checked==true)
			var examType=document.getElementById("int").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
</SCRIPT>
<html:form action="/sapMarksUploadEntry.do" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="sapUploadMarks" />
	<html:hidden property="formName" value="sapMarksUploadEntryForm" styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden property="validateMsg" styleId="validateMsg" name="sapMarksUploadEntryForm"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">SAP Exam	&gt;&gt;SAP Upload Marks &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">SAP Upload Marks</td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
							<font color="red"><div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<FONT color="green">
									<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages>
								</FONT>
							</div></font>
							<div align="right" class="mandatoryfield">
								<bean:message key="knowledgepro.mandatoryfields" />
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
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

									<td height="25" colspan="8" class="row-even">
									<div align="Center">
									<%  boolean disableInternal=false; 
										boolean disableRegular=false;
									%>
									<c:if test="${uploadMarksEntryForm.regular==false && uploadMarksEntryForm.internal==true}">
										<%   disableInternal=false; 
											 disableRegular=true;
										%>
									</c:if>
									<c:if test="${uploadMarksEntryForm.regular==true && uploadMarksEntryForm.internal==false}">
										<%   disableInternal=true; 
											 disableRegular=false;
										%>
									</c:if>
									<html:radio property="examType"
										styleId="reg" value="Regular"
										onclick="getExamsByExamTypeAndYear()" disabled="<%=disableRegular %>"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamsByExamTypeAndYear()" disabled="<%=disableInternal %>"></html:radio>
									Internal
									
									</div>
									</td>

					</tr>
					<tr>
							<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="25%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="sapMarksUploadEntryForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
												<td width="25%" class="row-odd">
												</td>
												<td width="25%" class="row-even" >
												</td>
											</tr>
											<tr>
											    <td width="25%" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.examname"/> :</div>
												</td>
												<td width="25%" class="row-even">
													<html:select property="examId" styleClass="comboLarge" styleId="examName" name="sapMarksUploadEntryForm"  style="width:200px">
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
														<logic:notEmpty name="sapMarksUploadEntryForm" property="listExamName">
															<html:optionsCollection property="listExamName" name="sapMarksUploadEntryForm" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="25%" height="25" class="row-odd">
													<div align="right"><span class="Mandatory">*</span><bean:message key="knowldgepro.exam.upload.marks.file"/> :</div>
												</td>
												<td width="25%" height="25" class="row-even">
													<html:file property="uploadedFile" styleId="uploadedFile"></html:file>
												</td>
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
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="49%" height="35" align="right">
										<input name="Submit7" type="submit" class="formbutton" value="Submit" id="submit"/>
									</td>
									<td width="2%" height="35" align="center">&nbsp;</td>
									<td width="49%" height="35" align="left">
										<input name="Submit8" type="button" class="formbutton" value="Reset" onclick="goToFirstPage()" />
									</td>
								</tr>
							</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
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
<script language="JavaScript" >
	var msg=document.getElementById("validateMsg").value;
	if(msg!=null && msg != ""){
		 $.confirm({
				'message'	: msg,
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.location.href = "sapMarksUploadEntry.do?method=sapUploadMarks&checking=yes";
						}
					},
 	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
							document.location.href = "sapMarksUploadEntry.do?method=initSapMarksUpload";
						}
					}
				}
			});
	}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
</script>

