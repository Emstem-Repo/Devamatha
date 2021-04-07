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
	document.getElementById("method").value="initRevaluationOrRetotallingMarks";
	document.revaluationOrRetotallingMarksEntryForm.submit();
}
function saveData() {
	document.getElementById("method").value="saveSelectedSubjectData";
	document.revaluationOrRetotallingMarksEntryForm.submit();
}
	function populateOptionForSup(isSup){
		var destination = document.getElementById("subjectType");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		if(isSup){
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", 1);
			destination.options[2] = new Option("Practical", 0);
		}
		else{
			destination.options[0] = new Option("Select", "");
			destination.options[1] = new Option("Theory", 1);
			destination.options[2] = new Option("Practical", 0);
		}
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

	function selectAll(obj) {
		var count=document.getElementById("count").value;
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
}

function unCheckSelectAll(check,styleId,styleId1,styleId2,styleId3,hidden,hidden1,hidden2,hidden3,evalsize) {
	var evalcount=document.getElementById(evalsize).value;
	 if(check == true){
		 if(document.getElementById(hidden).value == "true"){
			 document.getElementById(styleId).disabled= false;
			 }
		 else{
			if(evalcount=='1'){
			 	document.getElementById(styleId1).disabled=false;
	 		}
			if(evalcount=='2'){
			 	document.getElementById(styleId1).disabled=false;
			 	document.getElementById(styleId2).disabled=false;
			}	 
			if(evalcount=='3'){
			 	document.getElementById(styleId1).disabled=false;
			 	document.getElementById(styleId2).disabled=false;
			 	document.getElementById(styleId3).disabled=false;
			}
		 }
	 }else{
		 if(document.getElementById(hidden).value == "true" ){
			 document.getElementById(styleId).disabled=true;
		 }
		 else {

			 if(evalcount=='1'){
				 document.getElementById(styleId1).disabled=true;
		 	}
			if(evalcount=='2'){
				 document.getElementById(styleId1).disabled=true;
				 document.getElementById(styleId2).disabled=true;
			}	 
			if(evalcount=='3'){
				 document.getElementById(styleId1).disabled=true;
				 document.getElementById(styleId2).disabled=true;
				 document.getElementById(styleId3).disabled=true;
			}
			 	 
		 }
	 }
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var disable=false;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                        inputObj.value="on";
	                       
	                  }else{
	                	  inputObj.value="off";	
	                	 
	                      }   
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }
}
</SCRIPT>
</head>
<html:form action="/revaluationOrRetotalling" method="POST" enctype="multipart/form-data">
	<html:hidden property="method" styleId="method" value="getCandidateRecord" />
	<html:hidden property="formName" value="revaluationOrRetotallingMarksEntryForm" styleId="formName" />
	<%	String str="";
		if(request.getAttribute("count") != null){
		 str=request.getAttribute("count").toString();
		}
	 %>
	<html:hidden property="count" styleId="count" value="<%=str %>" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Revaluation/Re-totaling  Marks Entry&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Revaluation/Re-totaling  
					Marks Entry</td>
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
							<% boolean disable1=false;%>
										<logic:equal value="true" name="revaluationOrRetotallingMarksEntryForm" property="flag">
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
								     <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input type="hidden" id="tempyear" value="<bean:write property="year" name="revaluationOrRetotallingMarksEntryForm" />"/>
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()" disabled='<%=disable1%>'>
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examNameId"
										name="revaluationOrRetotallingMarksEntryForm" 
										style="width:200px" disabled='<%=disable1%>'>
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="revaluationOrRetotallingMarksEntryForm"
											property="examNameList">
											<html:optionsCollection property="examNameList"
												name="revaluationOrRetotallingMarksEntryForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									
								</tr>
								<tr>
									<td height="25" width="25%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.verification.student.regNo" /></div>
									</td>
									<td  height="25" class="row-even"><html:text
										property="registerNo" styleId="regNo" maxlength="50"
										styleClass="TextBox" size="20" disabled='<%=disable1%>'/></td>
									<td height="25" width="25%" class="row-odd">
									
									</td>
									<td height="25" class="row-even"></td>
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
							<td width="49%" height="35" align="right"><input
								name="Submit" type="submit" class="formbutton" value="Submit" /></td>

							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" height="35" align="left"><input
								type="button" class="formbutton" value="Reset"
								onclick="resetFields()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				
				
				
				<logic:notEmpty property="studentDetailsList" name="revaluationOrRetotallingMarksEntryForm">		
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
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Class</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Subject Code</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Subject Name</div> 
												</td>
												<td align="center" height="25" class="row-odd">
												<!--  <input type="checkbox" id="checkAll" onclick="selectAll(this)"/>--> Enter Marks
												</td>
												<td align="center" height="25" class="row-odd">
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
												<nested:iterate id="to" property="studentDetailsList" name="revaluationOrRetotallingMarksEntryForm" indexId="count">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="10%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													
													<td align="center" width="15%" height="25"><nested:write  name="to"
														 property="className" /></td>
													<td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="code" /></td>
													<td align="center" width="20%" height="25"><nested:write  name="to"  
														 property="name" /></td>
													<td width="20%" height="25">
													<div align="center">
													
													<% boolean disableMarks=true;
													 String styleId="marks_"+count;
													 String styleId1="marks1_"+count;
													 String styleId2="marks2_"+count;
													 String styleId3="marks3_"+count;
													 String hidden = "marks_hidden_"+count;
													 String hidden1 = "marks1_hidden_"+count;
													 String hidden2 = "marks2_hidden_"+count;
													 String hidden3 = "marks3_hidden_"+count;
													 String hiddensize = "evaluator_size_hidden_"+count;
													 %>
													 
													 <html:hidden name="to" property="evaluatorType" styleId="<%=hidden %>"/>
													 <html:hidden name="to" property="evaluatorType1" styleId="<%=hidden1 %>"/>
													 <html:hidden name="to" property="evaluatorType2" styleId="<%=hidden2 %>"/>
													 <html:hidden name="to" property="evaluatorType3" styleId="<%=hidden3 %>"/>
													 <html:hidden name="to" property="evaluatorTypeSize" styleId="<%=hiddensize %>"/>
													 
													<input type="checkbox" name="studentDetailsList[<c:out value='${count}'/>].checked1" id="check_<c:out value='${count}'/>" onclick="unCheckSelectAll(this.checked,'<%=styleId %>','<%=styleId1 %>','<%=styleId2 %>','<%=styleId3 %>','<%=hidden %>','<%=hidden1 %>','<%=hidden2 %>','<%=hidden3 %>','<%=hiddensize %>')" /> 
													</div>
													</td> 
														<logic:equal value="false"  name="revaluationOrRetotallingMarksEntryForm"  property="flag1">
														<% disableMarks=false;%>
														</logic:equal>
														<logic:equal value="true" name="to" property="evaluatorType">
														<td align="center" width="20%" height="25">
															<nested:text   property="marks" styleId="<%=styleId %>"
															  disabled='<%=disableMarks %>'/>
															  </td>
														</logic:equal> 
														<logic:equal value="false" name="to" property="evaluatorType">
														<td align="center" width="20%" height="25"></td>
														</logic:equal>
														<logic:equal value="true" name="to" property="evaluatorType1">
														<td align="center" width="20%" height="25">
															<nested:text   property="marks1" styleId="<%=styleId1 %>"
															  disabled='<%=disableMarks %>'/>
															  </td>
														</logic:equal> 
														<logic:equal value="false" name="to" property="evaluatorType1">
														<td align="center" width="20%" height="25"></td>
														</logic:equal>
														<logic:equal value="true" name="to" property="evaluatorType2">
														<td align="center" width="20%" height="25">
															<nested:text   property="marks2" styleId="<%=styleId2 %>"
															  disabled='<%=disableMarks %>'/>
															  </td>
														</logic:equal> 
														<logic:equal value="false" name="to" property="evaluatorType2">
														<td align="center" width="20%" height="25"></td>
														</logic:equal>
														<logic:equal value="true" name="to" property="evaluatorType3">
														<td align="center" width="20%" height="25">
															<nested:text   property="marks3" styleId="<%=styleId3 %>"
															  disabled='<%=disableMarks %>'/>
															  </td>
														</logic:equal> 
														<logic:equal value="false" name="to" property="evaluatorType3">
														<td align="center" width="20%" height="25"></td>
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
									<td width="45%" height="35">
										<div align="right">
											<html:button property="" styleClass="formbutton" value="Submit"
												styleId="submitbutton" onclick="saveData()">
											</html:button>
										</div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""  styleClass="formbutton" value="Close"
									onclick="resetFields()"></html:button></td>
								</tr>
							</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				</logic:notEmpty>
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
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var sessionId = document.getElementById("tempSession").value;
	if (sessionId != null && sessionId.length != 0) {
		document.getElementById("sessionId").value = sessionId;
	}
</script>