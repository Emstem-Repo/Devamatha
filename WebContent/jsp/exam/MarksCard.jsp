<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function getExamsByExamTypeAndYear() {		
		if(document.getElementById("examTypId").checked==true)
			var examType=document.getElementById("examTypId").value;
		else if(document.getElementById("suppexamTypId").checked==true)
			var examType=document.getElementById("suppexamTypId").value;
		
		var year=document.getElementById("year").value;		
		getExamNameByExamTypeAndYear("listExamName", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function generateMarksCard() {
		document.getElementById("method").value = "generateMarksCard";
		document.marksCardGenerateForm.submit();
	}

	function getExam(examTypeVal,radioVal){
		var academicYear = document.getElementById("year").value;
		document.getElementById("radioId").value=radioVal;
		getExamNameByAcademicYearAndExamType("listExamName",academicYear,"examName",updateExamNames,examTypeVal);
	}

	function getExamNameByAcademicYearAndExamType(propertyName, academicYear,
			destinationProperty, callback,examTypeVal) {
		var args = "method=getExamNameByAcademicYearAndExamType&academicYear=" + academicYear+ "&examType=" + examTypeVal;
		populateSecondOptionsValues(academicYear, args, destinationProperty,callback);

	}		
	function updateExamNames(req)
	{
		updateOptionsFromMap(req, "examName", "--Select--");
	}

	function getClassByExamId(examId){
	var radioButton=document.getElementById('examTypId');
		if(radioButton.checked){
		    getClassNameByExamName("listClassName",examId,"classId",updateClassNames);
			}
		else{
			getClassNameByExamNameForSupplementary("listClassName",examId,"classId",updateClassNames);
		}
	}
	
	function getClassNameByExamName(propertyName, examNam, destinationProperty,
			callback) {
		var args = "method=getClassNameByExamName&examName=" + examNam;
		populateSecondOptionsValues(examNam, args, destinationProperty, callback);
	}
	function getClassNameByExamNameForSupplementary(propertyName, examNam, destinationProperty,
			callback) {
		var args = "method=getClassNameByExamNameForSupplementary&examName=" + examNam;
		populateSecondOptionsValues(examNam, args, destinationProperty, callback);
	}
	
	function updateClassNames(req)
	{
		updateOptionsFromMap(req, "classId", "--Select--");
	}
	
	function getSemestersByExamId(examId)
	{
		var args = "method=getSemestersForExams&examId=" + examId;
		populateSecondOptionsValues(examId, args, "semister", updateSemsters);
	}
	function updateSemsters(req) {
		updateOptionsFromMap(req, "semister", "- Select -");
	}
	function resetFormFields(){	
		resetFieldAndErrMsgs();
	}

	function showButton(values){
		//var buttonId=document.getElementById('consolidatedSuppexamTypId').value;
		var regularId=document.getElementById('examTypId');
		var suppId= document.getElementById('suppexamTypId');
		var revId=document.getElementById('revaluationId');
		var impId= document.getElementById('improvementId');
		var grcId= document.getElementById('graceId');
		//var conId=document.getElementById('consolidatedSuppexamTypId');
	if((regularId.checked)||(suppId.checked)||(revId.checked)||(impId.checked)||(grcId.checked)){
		if(values=='yes'){
			alert("Enter One Register No & Choose Correct Class and Exam Name....!");
			document.getElementById("regId").style.display = "block";
		   
			}else{
				document.getElementById("regId").style.display = "none";
				}
		}
	else{
		document.getElementById("regId").style.display = "none";
	    }
		}
	function showTextBox() {
	/*var rad=document.getElementById("radioId").value;
		if(rad==1){	
		document.getElementById("consolidatedregId").style.display = "block";
	}else
	{
		document.getElementById("consolidatedregId").style.display = "none";
		}*/
		if(document.getElementById("suppexamTypId").checked==true)
		{
			document.getElementById("semister").value = null;
			document.getElementById("schemeRow").style.display = "none";
			document.getElementById("classRow").style.display = "table-row";
		}
		else
		{
			document.getElementById("classId").value = null;
			document.getElementById("classRow").style.display = "none";
			document.getElementById("schemeRow").style.display = "table-row";
		}	
	}
</script>


<html:form action="/marksCardGenerate" method="post">
	<html:hidden property="method" styleId="method" value=" " />
	<html:hidden property="formName" value="marksCardGenerateForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="radioId" styleId="radioId"/>
	<table width="98%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; Marks Card &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Marks Card</strong></div>
					</td>

					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
					<td height="70" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr height="20px">
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">								
								<tr>
									<td class="row-odd" valign="middle" width="50%">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div>
									</td>
									<td class="row-even" valign="middle" width="50%"><input type="hidden" id="tempyear"
										name="tempyear"
										value="<bean:write name="marksCardGenerateForm" property="year"/>" /><html:select
										name="marksCardGenerateForm" property="year" styleId="year" onchange="getExamsByExamTypeAndYear()"
										styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>																	
								</tr>
								<tr>
									<td class="row-odd" valign="middle">
										<div align="right"><span class="Mandatory">*</span>Exam Type:</div>
									</td>
									<td class="row-even" valign="middle">
										<html:radio property="examType" value="Regular" styleId="examTypId" onclick="getExam(this.value,0),showTextBox()">Regular</html:radio>  
										<html:radio property="examType" value="Supplementary" styleId="suppexamTypId" onclick="getExam(this.value,0),showTextBox()">Supplementary</html:radio>
									<%--<html:radio property="examType" value="Revaluation" styleId="revaluationId" onclick="getExam(this.value,0),showTextBox()">Revaluation</html:radio><br> 
									<html:radio property="examType" value="Improvement" styleId="improvementId" onclick="getExam(this.value,0),showTextBox()">Improvement</html:radio><br> 
									<html:radio property="examType" value="Grace" styleId="graceId" onclick="getExam(this.value,0),showTextBox()">Grace</html:radio><br> --%>
									 
									<%-- 
									<html:radio property="examType" value="Supplementary" styleId="consolidatedSuppexamTypId" onclick="getExam(this.value,1),showTextBox()">SemesterConsolidated</html:radio><br> 
									--%>
									<!-- Below <div> is commented because it is not being used and display is kept as none -->
		                          	<!-- <div id="consolidatedregId">
									Reg No: &nbsp; <html:text property="regForConsolidate" name="marksCardGenerateForm" styleId="regNo" size="10">
									</html:text>
									</div> -->									 
									</td>
								</tr>
								<tr>
									<td class="row-odd" valign="middle">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam"/>:</div>
									</td>
									<td  class="row-even" valign="middle" >
									<html:select property="examName" name="marksCardGenerateForm" styleClass="combo" 
									styleId="examName" style="width:200px" onchange="getSemestersByExamId(this.value),getClassByExamId(this.value)">
									<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
									 <logic:notEmpty name="marksCardGenerateForm"
											property="listExamName">
											<html:optionsCollection property="listExamName" label="value" value="key" />
										</logic:notEmpty>  
									</html:select>  
									</td>
								</tr>								
								<tr height="20px" id="schemeRow">
									<td align="right" class="row-odd" valign="middle" width="50%"><span class="Mandatory">*</span>Scheme:</td>
									<td class="row-even" valign="middle" width="50%">
										<html:select property="semister" styleId="semister" onchange="getClasses(this.value)">
											<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
											<logic:notEmpty name="marksCardGenerateForm" property="semesters">
												<html:optionsCollection property="semesters" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</td>
									<!-- -->
									
									<!--  
									<td class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span>Date :</div>
									</td>
									
									<td class="row-even" width="100">
				                  			<table width="82" border="0" cellspacing="0" cellpadding="0">
				                   				 <tr>
				                      				<td width="60">
				                      				<html:text name="marksCardGenerateForm" styleId="date" property="date" styleClass="TextBox" size="10"/>
				                      			</td>
				                      			<td width="40"><script language="JavaScript">
											new tcal ({
												// form name
												'formname': 'marksCardGenerateForm',
												// input name
												'controlname': 'date'
											});</script></td>
				                    </tr>
				                  </table></td>
				                  -->
				                  <!-- Below <td> is commented because it is not being used and display is kept as none -->
				                  
				                  <!-- <td class="row-odd" valign="top" style=display:none;>
									<div align="right"><span class="Mandatory"></span>Is Duplicate:</div>
									</td>
									<td class="row-even" valign="top" colspan="3" style=display:none>
									<html:radio property="isDuplicate" name="marksCardGenerateForm" styleId="isDuplicateId" value="yes" onclick="showButton(this.value)">Yes</html:radio>
									<html:radio property="isDuplicate" name="marksCardGenerateForm" styleId="isDuplicateId" value="no" onclick="showButton(this.value)">No</html:radio>
									
									<div id="regId">
									Reg No: &nbsp; <html:text property="regNo" name="marksCardGenerateForm" styleId="regNo" size="10">
									</html:text>
									</div>
								</td> -->
                  				</tr>
                  				<tr id="classRow" style="display: none;">
                  					<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Class :</div>
									</td>
									<td class="row-even" valign="top">
										<html:select property="classId" name="marksCardGenerateForm" styleClass="combo" styleId="classId" style="width:200px">
									<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
									 <logic:notEmpty name="marksCardGenerateForm"
											property="listClassName">
											<html:optionsCollection property="listClassName" label="value" value="key" />
										</logic:notEmpty>  
									</html:select>
									</td> 
                  				</tr>
                  				<tr>
                  					<td class="row-odd" valign="middle">
									<div align="right"><span class="Mandatory">*</span>Applied Year:</div>
									</td>
									<td class="row-even" valign="middle"><html:select
										name="marksCardGenerateForm" property="programYear" styleId="programYear"
										styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
                  				</tr>
								<!-- 
								<tr>
									<td class="row-even" valign="top" colspan="6" align="center">
									&nbsp;(OR)
									</td>
								</tr>
								 -->
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
				<tr height="5px">
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="49%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="generateMarksCard()"></html:button></div>
							</td>
							<td width="2%"></td>
							<td width="49%">
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFormFields()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="97" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
	document.getElementById("regId").style.display = "none";
</script>
<script type="text/javascript">
document.getElementById("consolidatedregId").style.display = "none";

</script>