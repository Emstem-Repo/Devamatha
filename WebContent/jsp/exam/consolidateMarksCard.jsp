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
	function generateMarksCard() {
		document.getElementById("method").value = "generateMarksCard";
		document.consolidateMarksCardForm.submit();
	}

	function getExam(examTypeVal,radioVal){
		var academicYear = document.getElementById("year").value;
		document.getElementById("radioId").value=radioVal;
		getExamNameByAcademicYearAndExamType("listExamName",academicYear,"examName",updateExamNames,examTypeVal);
	}

	function getExamNameByAcademicYearAndExamType(propertyName, academicYear,
			destinationProperty, callback,examTypeVal) {
		var args = "method=getExamNameByAcademicYearAndExamType&academicYear=" + academicYear+ "&examType=" + examTypeVal;
		populateSecondOptionsValues(academicYear, args, destinationProperty,
				callback);

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
	
	function resetFormFields(){	
		resetFieldAndErrMsgs();
	}

	function showButton(values){
		//var buttonId=document.getElementById('consolidatedSuppexamTypId').value;
		var regularId=document.getElementById('examTypId');
		var suppId= document.getElementById('suppexamTypId');
		var conId=document.getElementById('consolidatedSuppexamTypId');
	if((regularId.checked)||(suppId.checked)){
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
	var rad=document.getElementById("radioId").value;
		if(rad==1){	
		document.getElementById("consolidatedregId").style.display = "block";
	}else
	{
		document.getElementById("consolidatedregId").style.display = "none";
		}
	}
	function getClasses(year) {
		
		getPrograms("classMap", year, "course", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "course", "- Select -");
	}

	function getPrograms(programTypeId) {
		getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	}

	function updatePrograms(req) {
		updateOptionsFromMap(req,"program","- Select -");
	}

	function getCourses(programId) {
		getCoursesByProgram("coursesMap",programId,"course",updateCourses);
		
	}

	function updateCourses(req) {
		updateOptionsFromMap(req,"course","- Select -");
	}
</script>


<html:form action="/consolidatemarksCardGenerate" method="post">
	<html:hidden property="method" styleId="method" value=" " />
	<html:hidden property="formName" value="consolidateMarksCardForm" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="radioId" styleId="radioId"/>
	<table width="98%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; Consolidate Marks Card &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> Consolidate Marks Card</strong></div>
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
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>

											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.fee.appliedyear" />:</div>
											</td>
											<td align="left" height="25" class="row-even"><input
												type="hidden" id="tempyear" name="tempyear"
												value="<bean:write name="consolidateMarksCardForm" property="year"/>" />
											<html:select property="year"  styleId="year"
												onchange="getClasses(this.value)" styleClass="combo">
												<html:option value=" ">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>

											<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                							<td height="25" class="row-even" >
                   							 <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 								<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    							<html:optionsCollection name="consolidateMarksCardForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     										</html:select> 
               								 </td>

										</tr>
										<tr>
										<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
               							 <td height="25" class="row-even" >
			      						<html:select property="programId"  styleId="program" styleClass="combo" onchange="getCourses(this.value)">
          		   						<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    				<c:if test="${consolidateMarksCardForm.programTypeId != null && consolidateMarksCardForm.programTypeId != ''}">
            							<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 			<c:if test="${programMap != null}">
            		    	 			<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 			</c:if>	 
            		   					</c:if>
	           							</html:select>	   
										</td>
										<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="curriculumSchemeForm.course" />:</div>
											</td>
											<td align="left" width="30%" class="row-even"><html:select
												property="courseId" styleClass="combo"
												styleId="course"		>
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<c:if test="${consolidateMarksCardForm.programId != null && consolidateMarksCardForm.programId != ''}">
             									<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
             		    					<c:if test="${courseMap != null}">
             		    						<html:optionsCollection name="courseMap" label="value" value="key"/>
             		    					</c:if>	 
             		   					</c:if>
											</html:select></td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="45%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="generateMarksCard()"></html:button>
							</td>
							<td width="2%"></td>
							<td width="53%">
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