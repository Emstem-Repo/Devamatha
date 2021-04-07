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
<script type="text/javascript">
function closeWindow() {
	
	document.location.href="revaluationMarksUpdate.do?method=initRevaluationMarksUpdate";
}
function updateAll() {
	document.getElementById("method").value="updateAll";
	document.revaluationMarksUpdateForm.submit();
}
function thirdEvaluation(studentId,subjectId,comment,classId,examRevaluationAppId) {
	
	document.location.href="revaluationMarksUpdate.do?method=thirdEvaluation&studentid="+studentId+
						   "&subjectid="+subjectId+"&comment="+comment+"&classid="+classId+
						   "&examRevaluationAppId="+examRevaluationAppId;
}
function updateForRetotaling(studentId,subjectId,classId,examRevaluationAppId,examMarksEntryId,examMarksEntryDetailsId,
				examMarksEntryIdForSecondEvl2,examMarksEntryDetailsIdForSecondEvl2,examMarksEntryIdForNoEvl,examMarksEntryDetailsIdForNoEvl,
				examMarksEntryIdForSecondEvl3,examMarksEntryDetailsIdForSecondEvl3,oldMarks,oldMark1,oldMark2,oldMark3,newMark,newMark1,newMark2,newMark3,
				examRevaluationAppIdForEvL1,examRevaluationAppIdForEvL2,examRevaluationAppIdForEvL2,courseId,schemeNumber,subjectCode,count) {
	var com =document.getElementById("comments1_"+count).value;
	if(document.getElementById("newMarks_"+count)!=null){
		if(document.getElementById("newMarks_"+count).value !=''){
			var newMarks =document.getElementById("newMarks_"+count).value;
		}
	}else{
		var newMarks=null;
	}
	if(document.getElementById("newMarks1_"+count)!=null){
		if(document.getElementById("newMarks1_"+count).value !=''){
			var newMarks1 =document.getElementById("newMarks1_"+count).value;
		}
	}else{
		var newMarks1=null;
	}
	if(document.getElementById("newMarks2_"+count)!=null){
		if(document.getElementById("newMarks2_"+count).value !=''){
			var newMarks2 =document.getElementById("newMarks2_"+count).value;
		}
	}else{
		var newMarks2=null;
	}
	if(document.getElementById("newMarks2_"+count)!=null){
		if(document.getElementById("newMarks2_"+count).value !=''){
			var newMarks2 =document.getElementById("newMarks2_"+count).value;
		}
	}else{
		var newMarks2=null;
	}
	//document.location.href="revaluationMarksUpdate.do?method=saveChangedMarksForRetoaling&studentid="+studentId+"&subjectid="+subjectId+
	  // "&comment="+com+"&classid="+classId+"&examRevaluationAppId="+examRevaluationAppId+
	  // "&examMarksEntryId="+examMarksEntryId+"&examMarksEntryDetailsId="+examMarksEntryDetailsId+
	  // "&examMarksEntryIdForSecondEvl2="+examMarksEntryIdForSecondEvl2+"&examMarksEntryDetailsIdForSecondEvl2="+examMarksEntryDetailsIdForSecondEvl2+
	   //"&examMarksEntryIdForNoEvl="+examMarksEntryIdForNoEvl+"&examMarksEntryDetailsIdForNoEvl="+examMarksEntryDetailsIdForNoEvl+
	   //"&oldMarks="+oldMarks+"&oldMark1="+oldMark1+"&oldMark2="+oldMark2+
	   //"&newMarks="+newMarks+"&newMark1="+newMarks1+"&newMark2="+newMarks2+
	   //"&examRevaluationAppIdForEvL1="+examRevaluationAppIdForEvL1+"&examRevaluationAppIdForEvL2="+examRevaluationAppIdForEvL2+
	  // "&courseid="+courseId+"&schemeNumber="+schemeNumber+"&subjectname="+subjectCode; 
	document.getElementById("hid_studentid").value=studentId;
	document.getElementById("hid_subjectid").value =subjectId;
	document.getElementById("hid_comment").value =com;
	document.getElementById("hid_classid").value =classId;
	document.getElementById("hid_examRevaluationAppId").value =examRevaluationAppId;
	document.getElementById("hid_examMarksEntryId").value =examMarksEntryId;
	document.getElementById("hid_examMarksEntryDetailsId").value =examMarksEntryDetailsId;
	document.getElementById("hid_examMarksEntryIdForSecondEvl2").value =examMarksEntryIdForSecondEvl2;
	document.getElementById("hid_examMarksEntryDetailsIdForSecondEvl2").value =examMarksEntryDetailsIdForSecondEvl2;
	document.getElementById("hid_examMarksEntryIdForNoEvl").value =examMarksEntryIdForNoEvl;
	document.getElementById("hid_examMarksEntryDetailsIdForNoEvl").value =examMarksEntryDetailsIdForNoEvl;
	document.getElementById("hid_examMarksEntryIdForThirdEvl3").value =examMarksEntryIdForThirdEvl3;
	document.getElementById("hid_examMarksEntryDetailsIdForThirdEvl3").value =examMarksEntryDetailsIdForThirdEvl3;
	document.getElementById("hid_oldMarks").value =oldMarks;
	document.getElementById("hid_oldMark1").value =oldMark1;
	document.getElementById("hid_oldMark2").value =oldMark2;
	document.getElementById("hid_oldMark3").value =oldMark3;
	document.getElementById("hid_newMarks").value =newMarks;
	document.getElementById("hid_newMark1").value =newMarks1;
	document.getElementById("hid_newMark2").value =newMarks2;
	document.getElementById("hid_newMark3").value =newMarks3;
	document.getElementById("hid_examRevaluationAppIdForEvL1").value =examRevaluationAppIdForEvL1;
	document.getElementById("hid_examRevaluationAppIdForEvL2").value =examRevaluationAppIdForEvL2;
	document.getElementById("hid_examRevaluationAppIdForEvL3").value =examRevaluationAppIdForEvL3;
	document.getElementById("hid_courseid").value =courseId;
	document.getElementById("hid_schemeNumber").value =schemeNumber;
	document.getElementById("hid_subjectname").value =subjectCode;
	
	document.getElementById("method").value="saveChangedMarksForRetoaling";
	document.revaluationMarksUpdateForm.submit();

	   
	}
function updateForRevaluation(studentId,subjectId,classId,examRevaluationAppId,
								examMarksEntryIdForNoEvl,examMarksEntryDetailsIdForNoEvl,oldMarks,courseId,schemeNumber,subjectCode,oldAvgMarks,count) {
	//var avgMarks=document.getElementById("avgMarks1_"+count).value;
	var avg =document.getElementById("avgMarks_"+count).value;
	var com =document.getElementById("comments_"+count).value;

	var errors="false";
	var a = oldAvgMarks;
	if(avg !=null && avg!=''){
		document.location.href="revaluationMarksUpdate.do?method=saveChangedMarksForRevaluation&studentid="+studentId+"&subjectid="+subjectId+
			"&comment="+com+"&classid="+classId+"&examRevaluationAppId="+examRevaluationAppId+
			 "&examMarksEntryIdForNoEvl="+examMarksEntryIdForNoEvl+"&examMarksEntryDetailsIdForNoEvl="+examMarksEntryDetailsIdForNoEvl+
			  "&avgMarks="+avg+"&oldMarks="+oldMarks+"&courseid="+courseId+"&schemeNumber="+schemeNumber+"&subjectname="+subjectCode+"&oldAvgMarks="+oldAvgMarks+"&count="+count;

			}else{
				 alert("Please enter the Marks");
				 }
		}
	function getExamNameByYear(year) {
		getExamNameByAcademicYear("examMap",year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function resetFields() {
		document.getElementById("method").value="resetForValuationStatus";
		document.revaluationMarksUpdateForm.submit();
	}
	function getExamsByExamTypeAndYear() {
		if(document.getElementById("examId").checked==true)
		var examType=document.getElementById("examId").value;
		else if(document.getElementById("sup").checked==true)
			var examType=document.getElementById("sup").value;
			var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examNameId", "- Select -");
		updateCurrentExam(req, "examNameId");
		getCourse(document.getElementById("examNameId").value);
	}
</script>
</head>


<html:form action="/revaluationMarksUpdate">
	<html:hidden property="formName" value="revaluationMarksUpdateForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="getCandidatesRecords" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<html:hidden name="revaluationMarksUpdateForm"  property="studentid" styleId="hid_studentid" />
	<html:hidden name="revaluationMarksUpdateForm"  property="subjectid" styleId="hid_subjectid" />
	<html:hidden name="revaluationMarksUpdateForm"  property="comment" styleId="hid_comment" />
	<html:hidden name="revaluationMarksUpdateForm"  property="classid" styleId="hid_classid" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examRevaluationAppId" styleId="hid_examRevaluationAppId" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryId" styleId="hid_examMarksEntryId" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryDetailsId" styleId="hid_examMarksEntryDetailsId" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryIdForSecondEvl2" styleId="hid_examMarksEntryIdForSecondEvl2" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryDetailsIdForSecondEvl2" styleId="hid_examMarksEntryDetailsIdForSecondEvl2" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryIdForThirdEvl3" styleId="hid_examMarksEntryIdForThirdEvl3" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryDetailsIdForThirdEvl3" styleId="hid_examMarksEntryDetailsIdForThirdEvl3" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryIdForNoEvl" styleId="hid_examMarksEntryIdForNoEvl" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examMarksEntryDetailsIdForNoEvl" styleId="hid_examMarksEntryDetailsIdForNoEvl" />
	<html:hidden name="revaluationMarksUpdateForm"  property="oldMarks" styleId="hid_oldMarks" />
	<html:hidden name="revaluationMarksUpdateForm"  property="oldMark1" styleId="hid_oldMark1" />
	<html:hidden name="revaluationMarksUpdateForm"  property="oldMark2" styleId="hid_oldMark2" />
	<html:hidden name="revaluationMarksUpdateForm"  property="oldMark3" styleId="hid_oldMark3" />
	<html:hidden name="revaluationMarksUpdateForm"  property="newMarks" styleId="hid_newMarks" />
	<html:hidden name="revaluationMarksUpdateForm"  property="newMark1" styleId="hid_newMark1" />
	<html:hidden name="revaluationMarksUpdateForm"  property="newMark2" styleId="hid_newMark2" />
	<html:hidden name="revaluationMarksUpdateForm"  property="newMark3" styleId="hid_newMark3" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examRevaluationAppIdForEvL1" styleId="hid_examRevaluationAppIdForEvL1" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examRevaluationAppIdForEvL2" styleId="hid_examRevaluationAppIdForEvL2" />
	<html:hidden name="revaluationMarksUpdateForm"  property="examRevaluationAppIdForEvL3" styleId="hid_examRevaluationAppIdForEvL3" />
	<html:hidden name="revaluationMarksUpdateForm"  property="courseid" styleId="hid_courseid" />
	<html:hidden name="revaluationMarksUpdateForm"  property="schemeNumber" styleId="hid_schemeNumber" />
	<html:hidden name="revaluationMarksUpdateForm"  property="subjectname" styleId="hid_subjectname" />
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Revaluation Marks Update &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Revaluation Marks Update</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<logic:notEmpty name="revaluationMarksUpdateForm" property="errorMessage">
								<FONT color="red"><bean:write name="revaluationMarksUpdateForm"  property="errorMessage"/> </FONT>	
							</logic:notEmpty>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
							<% boolean disable1=false;%>
										<logic:equal value="true" name="revaluationMarksUpdateForm" property="flag">
										<% disable1=true;%>
										</logic:equal>
							
								<tr>
									<td height="25" colspan="8" class="row-even">
									<div align="Center">
									<html:radio property="examType"
										styleId="examId" value="Regular"
										onclick="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'></html:radio>
									Supplementary
									</div>
									</td>

								</tr>
								<tr>
									<td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left">
		                            <input type="hidden" id="tempyear" value="<bean:write property="academicYear" name="revaluationMarksUpdateForm" />"/>
		                            <html:select property="academicYear" styleId="year" styleClass="combo" onchange="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'>
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderAcademicYear></cms:renderAcademicYear>
	                     			   		</html:select>
		                            </td>
									<td class="row-odd" >
									<div align="right"><span class="Mandatory">*</span>
									Exam Name :</div>
									</td>

									<td class="row-even">
									 <input type="hidden" id="examName" value="<bean:write property="examId" name="revaluationMarksUpdateForm" />"/>
									<html:select
										name="revaluationMarksUpdateForm" property="examId"
										styleId="examNameId" styleClass="comboLarge" disabled='<%=disable1%>'>
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="revaluationMarksUpdateForm"
												property="examNameMap">
												<html:optionsCollection property="examNameMap"
													name="revaluationMarksUpdateForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25"  colspan="2" class="row-even">
									<div align="Center">
									<html:radio property="revaluation"
										styleId="revaluation" value="Revaluation"  disabled='<%=disable1%>'></html:radio>
									Revaluation&nbsp;&nbsp;&nbsp;
									<html:radio property="revaluation" value="Re-totaling"
										styleId="Retotaling" disabled='<%=disable1%>'></html:radio>
									Re-totalling
									</div>
									</td>
									<td class="row-odd">
									<div align="right">Search</div>
									</td>
									<td class="row-even">
									<html:select property="option" styleId="option" styleClass="combo" disabled='<%=disable1%>'>
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:option value="Updated">Updated</html:option>
										<html:option value="NotUpdated"> Not Updated</html:option>
										<!-- 
										<html:option value="Requested_for_thirdEvaluation">Requested For Third Evaluation</html:option>
										 -->
									</html:select>
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
							<td width="20%" height="35" align="center">
							
							<html:submit value="Submit" styleClass="formbutton"></html:submit>
										&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset" onclick="closeWindow()" ></html:button>
							
							</td>
						</tr>
						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				
				
				<logic:equal value="Revaluation" name="revaluationMarksUpdateForm" property="revaluation">	
				<logic:notEmpty property="studentDetailsList" name="revaluationMarksUpdateForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          		<tr>
			          		<td>
			          				 <div align="right"><FONT color="red" size="2"><bean:message key="knowledgepro.gracing.symbol"/></FONT></div>
			          		</td>
			          		</tr>
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
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="0" border="1" bordercolor="darkgreen">
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student RegisterNumber</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student Name</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Class</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Subject Code</div> 
												</td>
												<td valign="top" class="news">
												<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" >
													<div align="center" >Current Marks</div> 
													</td>
													</tr>
													<tr>
													<td align="center" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													</tr>
													</table>
												</td>
												<td valign="top" class="news">
												<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" >
													<div align="center">Revaluation Marks</div> 
													</td>
													</tr>
													<tr>
													<td align="center" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													</tr>
													</table>
												</td>
												<td valign="top" class="news">
												<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" >
													<div align="center">Third Evaluation</div> 
													</td>
													</tr>
													<tr>
													<td align="center" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													</tr>
													</table>
												</td>
												<td align="center" height="25" class="row-odd">
													<div align="center">New Marks</div> 
												</td>
												<td align="center" height="25" class="row-odd">
													<div align="center">Comment</div>
												</td>
												<td align="center" height="25" class="row-odd">
													<div align="center">Update</div>
												</td>
												<!--  
													<td align="center" height="25" class="row-odd">
													<div align="center">Third Evaluation</div> 
													</td>
													-->
											</tr>
												<nested:iterate id="to" property="studentDetailsList" name="revaluationMarksUpdateForm" indexId="count">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<% String avgMarks1 = "avgMarks_"+count; %>
													<% String comments1 = "comments_"+count; %>
													<td width="25%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="20%" height="25"><nested:write  name="to"
														 property="registerNo" /></td>
													<td align="left" width="30%" height="25"><nested:write  name="to"  
														 property="studentName" /></td>
													<td align="left" width="30%" height="25"><nested:write  name="to"  
														 property="className" /></td>
													<td align="left" width="30%" height="25"><nested:write  name="to"  
														 property="subjectCode" /></td>
													 <logic:notEmpty property="oldMarks" name="to">		
													 <logic:equal value="true" name="to" property="gracedMark">
													 <td align="left" width="10%" height="25"><p style="font-weight:">&nbsp;<nested:write  name="to"  
														 property="oldMarks" />&nbsp;<FONT color="red"><b>$</b></FONT></p></td>
													 </logic:equal>
													  <logic:equal value="false" name="to" property="gracedMark">
													<td align="left" width="10%" height="25"><p style="font-weight:">&nbsp;<nested:write  name="to"  
														 property="oldMarks" /></p></td>
													</logic:equal>
													 </logic:notEmpty>	 
													 <logic:empty property="oldMarks" name="to">		
													<td align="center" width="10%" height="25"></td>
													 </logic:empty>	
													  <logic:notEmpty property="newMarks" name="to">
													 <td align="center" width="10%" height="25"><p style="font-weight:">&nbsp;&nbsp;&nbsp;&nbsp;<nested:write  name="to"  
														 property="newMarks" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="newMarks" name="to">		
													<td align="center" width="10%" height="25"></td>
													 </logic:empty>	 
													 <logic:notEmpty property="thirdEvlMarks" name="to">
													 <td align="center" width="10%" height="25"><p style="font-weight:">&nbsp;&nbsp;&nbsp;&nbsp;<nested:write  name="to"  
														 property="thirdEvlMarks" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="thirdEvlMarks" name="to">		
													 <td align="center" width="10%" height="25"></td>
													 </logic:empty>	
													<td align="center" width="5%" height="25" >
													<nested:text styleClass="comboSmall" styleId='<%=avgMarks1 %>' property="avgMarks" name="to" />
													</td>
													 <td align="center" width="10%" height="25">
													 <nested:text styleId='<%=comments1 %>' styleClass="combo" name="to"  
														 property="comment" /></td>
													<% String updateid = "updateid_"+count;
													 %>	 
													<logic:notEmpty property="newMarks" name="to">
													<logic:notEqual value="Updated" name="revaluationMarksUpdateForm" property="option">
													 <td width="35%" height="35" align="center" >
													 <input type="hidden" id="updatebuthide" value="<bean:write property="count" name="revaluationMarksUpdateForm" />"/>
														<div align="center"><img src="images/update.jpg" id='<%=updateid%>'
																width="53" height="32" style="cursor:pointer" 
																onclick="updateForRevaluation('<bean:write name="to" property="studentId"/>',
																					'<bean:write name="to" property="subjectId"/>',
																					'<bean:write name="to" property="classId"/>',
																					'<bean:write name="to" property="examRevaluationAppId"/>',
																					'<bean:write name="to" property="examMarksEntryIdForNoEvl"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForNoEvl"/>',
																					'<bean:write name="to" property="oldMarks"/>',
																					'<bean:write name="to" property="courseId"/>',
																					'<bean:write name="to" property="schemeNumber"/>',
																					'<bean:write name="to" property="subjectCode"/>',
																					'<bean:write name="to" property="oldAvgMarks"/>',
																					'<c:out value="${count}"/>')">
														</div>
														</td>
														</logic:notEqual >
														<logic:equal value="Updated" name="revaluationMarksUpdateForm" property="option">
															<td align="center" width="30%" height="25"></td>
														</logic:equal >
														 </logic:notEmpty>	
														 <logic:empty property="newMarks" name="to">
														 <td align="center" width="30%" height="25"></td>
														 </logic:empty>
														 
														 <%-- 
														 <logic:notEmpty property="newMarks" name="to">
														 <logic:equal value="NotUpdated" name="revaluationMarksUpdateForm" property="option">
														 
														<td width="25%" height="25"  align="center" >
														<div align="center"><img src="images/ThirdEval.jpg" id='<%=updateid%>'
														width="53" height="27" style="cursor:pointer"
														onclick="thirdEvaluation('<bean:write name="to" property="studentId"/>',
																					'<bean:write name="to" property="subjectId"/>',
																					'<bean:write name="to" property="comment"/>',
																					'<bean:write name="to" property="classId"/>',
																					'<bean:write name="to" property="examRevaluationAppId"/>',
																					'<c:out value="${count}"/>')">
																					</div>
														</td>
													</logic:equal >
													<logic:notEqual value="NotUpdated" name="revaluationMarksUpdateForm" property="option">
															<td align="center" width="30%" height="25"></td>
														</logic:notEqual >
													 </logic:notEmpty>	
													 <logic:empty property="newMarks" name="to">
														 <td align="center" width="10%" height="25"></td>
														 </logic:empty>
														 
														 --%>
													</nested:iterate>
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
	          <td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="49%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button></td>
								</tr>
							</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			</logic:notEmpty>
			</logic:equal >
			
			
		<logic:equal value="Re-totaling" name="revaluationMarksUpdateForm" property="revaluation">	
			<logic:notEmpty property="studentDetailsList" name="revaluationMarksUpdateForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			          		<tr>
			          		<td>
			          				 <div align="right"><FONT color="red" size="2"><bean:message key="knowledgepro.gracing.symbol"/></FONT></div>
			          		</td>
			          		</tr>
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
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="0" border="1" bordercolor="darkgreen">
											
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student RegisterNumber</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Student Name</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Class</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Subject Code</div> 
												</td>
												<td valign="top" class="news">
												<table width="100%"  align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" colspan="3">
													<div align="center" >Current Marks</div> 
													</td>
													</tr>
													<tr>
													<td align="center" width="5%" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator1" /></div>
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator2" /></div> 
													</td>
													</tr>
													</table>
												</td>
												<td valign="top" class="news">
												<table width="100%"  align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" colspan="3">
													<div align="center">Re-totaling Marks</div> 
													</td>
													</tr>
													<tr>
													<td align="center" width="6%" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator1" /></div>
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator2" /></div> 
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator3" /></div> 
													</td>
													</tr>
													</table>
												</td>
												<td valign="top" class="news">
												<table width="100%"  align="center" cellpadding="1" cellspacing="1" >
													<tr>
													<td align="center" height="25" class="row-odd" colspan="3">
													<div align="center">Marks Difference</div> 
													</td>
													</tr>
													<tr>
													<td align="center" width="5%" height="25" class="row-odd">
													<div align="center">Marks</div> 
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator1" /></div>
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator2" /></div> 
													</td>
													<td align="center" height="25" class="row-odd">
													<div align="center"><bean:message
													key="knowledgepro.exam.evaluator3" /></div> 
													</td>
													</tr>
													</table>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Comment</div>
												</td>
												 
												<td align="center" height="25" class="row-odd">
												<div align="center">Update</div>
												</td>
												 
											</tr>
												<nested:iterate id="to" property="studentDetailsList" name="revaluationMarksUpdateForm" indexId="count">
												<logic:equal value="false" name="to" property="isUpdated">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<% String comments2 = "comments1_"+count; %>
													<% String newMarks = "newMarks_"+count; %>
													<% String newMarks1 = "newMarks1_"+count; %>
													<% String newMarks2 = "newMarks2_"+count; %>
													<% String newMarks3 = "newMarks3_"+count; %>
													<td width="25%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													
													<td align="center" width="20%" height="25"><nested:write  name="to"
														 property="registerNo" /></td>
													<td align="left" width="20%" height="25"><nested:write  name="to"  
														 property="studentName" /></td>
													<td align="left" width="20%" height="25"><nested:write  name="to"  
														 property="className" /></td>
													<td align="left" width="20%" height="25"><nested:write  name="to"  
														 property="subjectCode" /></td>
													
													<td class="news" >
													
													<table width="100%" border="1" align="center" cellpadding="0" cellspacing="1" bordercolor="darkgreen">
													<tr>
													 <logic:notEmpty property="oldMarks" name="to">
													 <logic:equal value="true" name="to" property="gracedMark">
													 <td align="left" width="3%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMarks" />&nbsp;<FONT color="red" ><b>$</b></FONT></td>
													 </logic:equal>		
													 <logic:equal value="false" name="to" property="gracedMark">
														<td align="left" width="4%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMarks" /></td>
														</logic:equal>
													 </logic:notEmpty>	 
													 <logic:empty property="oldMarks" name="to" >		
													<td align="center" width="4%" height="25"></td>
													 </logic:empty>	
													 <logic:notEmpty property="oldMark1" name="to">	
													 <logic:equal value="true" name="to" property="gracedMark1">
													 <td align="center" width="3%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark1" />&nbsp;<FONT color="red"><b>$</b></FONT></td>
													 </logic:equal>	
													 <logic:equal value="false" name="to" property="gracedMark1">
													 <td align="center" width="4%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark1" /></td>
														 </logic:equal>
													  </logic:notEmpty>	 
													  <logic:empty property="oldMark1" name="to">		
													 <td align="center" width="9%" height="25" ></td>
													 </logic:empty>	 
												     <logic:notEmpty property="oldMark2" name="to" >
												     <logic:equal value="true" name="to" property="gracedMark2">
												     <td align="center" width="3%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark2" />&nbsp;<FONT color="red"><b>$</b></FONT></td>
												     </logic:equal>		
												     <logic:equal value="false" name="to" property="gracedMark2">
													 <td align="center" width="4%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark2" /></td>
														 </logic:equal>
													  </logic:notEmpty>	
													 <logic:empty property="oldMark2" name="to">		
													 <td align="center" width="9%" height="25" ></td>
													 </logic:empty>	
													 
													 <logic:notEmpty property="oldMark3" name="to" >
												     <logic:equal value="true" name="to" property="gracedMark3">
												     <td align="center" width="3%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark3" />&nbsp;<FONT color="red"><b>$</b></FONT></td>
												     </logic:equal>		
												     <logic:equal value="false" name="to" property="gracedMark3">
													 <td align="center" width="4%" height="25" >&nbsp;<nested:write  name="to"  
														 property="oldMark3" /></td>
														 </logic:equal>
													  </logic:notEmpty>	
													 <logic:empty property="oldMark3" name="to">		
													 <td align="center" width="9%" height="25" ></td>
													 </logic:empty>	
													 
													 </tr>
													 </table>
													 </td>
												    <td class="news">
													<table width="100%" border="1" align="center" cellpadding="0" cellspacing="1" bordercolor="darkgreen">
													<tr>
													  <logic:notEmpty property="newMarks" name="to">
													 <td align="left" width="2%" height="25"><p style="font-weight:">&nbsp;<nested:text styleId='<%=newMarks %>' size="1" maxlength="3"   
							  												property="newMarks" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="newMarks" name="to">		
													<td align="left" width="7%" height="25"></td>
													 </logic:empty>	 
													 <logic:notEmpty property="newMark1" name="to">
													 <td align="center" width="4%" height="25"><p style="font-weight:">&nbsp;<nested:text styleId='<%=newMarks1 %>' size="1" maxlength="3"    
																			property="newMark1" /></p></td>
													  </logic:notEmpty>	
													  <logic:empty property="newMark1" name="to">		
													  <td align="center" width="9%" height="25"></td>
													  </logic:empty>	
													  <logic:notEmpty property="newMark2" name="to">
													  <td align="center" width="4%" height="25"><p style="font-weight:">&nbsp;<nested:text styleId='<%=newMarks2 %>' size="1" maxlength="3"   
																			property="newMark2" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="newMark2" name="to">		
													 <td align="center" width="9%" height="25"></td>
													 </logic:empty>
													 	 
													 <logic:notEmpty property="newMark3" name="to">
													  <td align="center" width="4%" height="25"><p style="font-weight:">&nbsp;<nested:text styleId='<%=newMarks3 %>' size="1" maxlength="3"   
																			property="newMark3" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="newMark3" name="to">		
													 <td align="center" width="9%" height="25"></td>
													 </logic:empty>	 
													 
													 </tr>
													 </table>
													 </td>
													 <td   class="news">
													 <table width="100%" border="1" align="center" cellpadding="0" cellspacing="1" bordercolor="darkgreen">
													 <tr>
													 <logic:notEmpty property="diffOfMarks" name="to">
													 <td align="center" width="6%" height="25"><p style="font-weight:bold;">
													 <nested:write  name="to"  
														 property="diffOfMarks" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="diffOfMarks" name="to">		
													 <td align="center" width="6%" height="25"></td>
													 </logic:empty>	
													 <logic:notEmpty property="diffOfMarks1" name="to">
													 <td align="center" width="10%" height="25"><p style="font-weight:bold;">
													 <nested:write  name="to"  
														 property="diffOfMarks1" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="diffOfMarks1" name="to">		
													 <td align="center" width="11%" height="25"></td>
													 </logic:empty>	
													 <logic:notEmpty property="diffOfMarks2" name="to">
													 <td align="center" width="10%" height="25"><p style="font-weight:bold;">
													 <nested:write  name="to"  
														 property="diffOfMarks2" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="diffOfMarks2" name="to">		
													 <td align="center" width=11%" height="25"></td>
													 </logic:empty>	
													 
													  <logic:notEmpty property="diffOfMarks3" name="to">
													 <td align="center" width="10%" height="25"><p style="font-weight:bold;">
													 <nested:write  name="to"  
														 property="diffOfMarks3" /></p></td>
													 </logic:notEmpty>	
													 <logic:empty property="diffOfMarks3" name="to">		
													 <td align="center" width=11%" height="25"></td>
													 </logic:empty>
													 
													 </tr>
													 </table>
													 </td>
													 <td align="center" width="10%" height="25">
													 <nested:text styleId='<%=comments2 %>' styleClass="combo" name="to"  
														 property="comment" /></td>
													<logic:notEmpty property="oldMarks" name="to">		
														<logic:notEmpty property="newMarks" name="to">
															<logic:equal value="NotUpdated" name="revaluationMarksUpdateForm" property="option">
															<td width="30%" height="25" align="center" >
															<div align="center"><img src="images/update.jpg"
																width="53" height="32" style="cursor:pointer" 
																onclick="updateForRetotaling('<bean:write name="to" property="studentId"/>',
																					'<bean:write name="to" property="subjectId"/>',
																					'<bean:write name="to" property="classId"/>',
																					'<bean:write name="to" property="examRevaluationAppId"/>',
																					'<bean:write name="to" property="examMarksEntryId"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsId"/>',
																					'<bean:write name="to" property="examMarksEntryIdForSecondEvl2"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForSecondEvl2"/>',
																					'<bean:write name="to" property="examMarksEntryIdForNoEvl"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForNoEvl"/>',
																					'<bean:write name="to" property="examMarksEntryIdForThirdEvl3"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForThirdEvl3"/>',
																					'<bean:write name="to" property="oldMarks"/>',
																					'<bean:write name="to" property="oldMark1"/>',
																					'<bean:write name="to" property="oldMark2"/>',
																					'<bean:write name="to" property="oldMark3"/>',
																					'<bean:write name="to" property="newMarks"/>',
																					'<bean:write name="to" property="newMark1"/>',
																					'<bean:write name="to" property="newMark2"/>',
																					'<bean:write name="to" property="newMark3"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL1"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL2"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL3"/>',
																					'<bean:write name="to" property="courseId"/>',
																					'<bean:write name="to" property="schemeNumber"/>',
																					'<bean:write name="to" property="subjectCode"/>',
																					'<c:out value="${count}"/>')">
															</div>
															</td>
															</logic:equal >
															<logic:equal value="Updated" name="revaluationMarksUpdateForm" property="option">
															<td align="center" width="10%" height="25"></td>
															</logic:equal >
													 	</logic:notEmpty>	
													 <logic:empty property="newMarks" name="to">		
													<td align="center" width="10%" height="25"></td>
													 </logic:empty>	
												    </logic:notEmpty>	 
												 	<logic:empty property="oldMarks" name="to">	
													      
													 	<logic:notEmpty property="newMark1" name="to">
													 			  <logic:notEmpty property="newMark2" name="to">	
													 			  <logic:equal value="NotUpdated" name="revaluationMarksUpdateForm" property="option">
													 				<td width="30%" height="25" align="center" >
																	<div align="center"><img src="images/update.jpg"
																		width="53" height="32" style="cursor:pointer" 
																		onclick="updateForRetotaling('<bean:write name="to" property="studentId"/>',
																					'<bean:write name="to" property="subjectId"/>',
																					'<bean:write name="to" property="classId"/>',
																					'<bean:write name="to" property="examRevaluationAppId"/>',
																					'<bean:write name="to" property="examMarksEntryId"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsId"/>',
																					'<bean:write name="to" property="examMarksEntryIdForSecondEvl2"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForSecondEvl2"/>',
																					'<bean:write name="to" property="examMarksEntryIdForNoEvl"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForNoEvl"/>',
																					'<bean:write name="to" property="examMarksEntryIdForThirdEvl3"/>',
																					'<bean:write name="to" property="examMarksEntryDetailsIdForThirdEvl3"/>',
																					'<bean:write name="to" property="oldMarks"/>',
																					'<bean:write name="to" property="oldMark1"/>',
																					'<bean:write name="to" property="oldMark2"/>',
																					'<bean:write name="to" property="oldMark3"/>',
																					'<bean:write name="to" property="newMarks"/>',
																					'<bean:write name="to" property="newMark1"/>',
																					'<bean:write name="to" property="newMark2"/>',
																					'<bean:write name="to" property="newMark3"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL1"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL2"/>',
																					'<bean:write name="to" property="examRevaluationAppIdForEvL3"/>',
																					'<bean:write name="to" property="courseId"/>',
																					'<bean:write name="to" property="schemeNumber"/>',
																					'<bean:write name="to" property="subjectCode"/>',
																					'<c:out value="${count}"/>')">
																</div>
																</td>
																</logic:equal>
													 			</logic:notEmpty>	
													 			<logic:equal value="Updated" name="revaluationMarksUpdateForm" property="option">
															<td align="center" width="10%" height="25"></td>
															</logic:equal >
													 			<logic:empty property="newMark2" name="to">		
													  			<td align="center" width="10%" height="25"></td>
													  		</logic:empty>	
													  </logic:notEmpty>	
												  	<logic:empty property="newMark1" name="to">	
											  			<td align="center" width="10%" height="25"></td>
													</logic:empty>
												 	</logic:empty>	 
												 	</logic:equal>
													</nested:iterate>
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
	          <td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="49%" height="35" align="center">
									<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()">
									</html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<logic:equal value="NotUpdated" name="revaluationMarksUpdateForm" property="option">
									<logic:notEmpty  name="revaluationMarksUpdateForm" property="verificationListHavingMarksForUpdateButton">
									<html:button property="" styleClass="formbutton" value="Update All" onclick="updateAll()">
									</html:button>
									 </logic:notEmpty>	
									</logic:equal>
									</td>
							
								</tr>
							</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			</logic:notEmpty>
			</logic:equal >
			
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
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var examId = document.getElementById("examName").value;
	if (examId != null && examId.length != 0) {
		document.getElementById("examNameId").value = examId;
	}
</script>