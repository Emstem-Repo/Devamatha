<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript" src="js/jquery.js">
	</script>
<script type="text/javascript">

	function getExamName() {
		if(document.getElementById("examId").checked==true)
			var examType=document.getElementById("examId").value;
			else if(document.getElementById("sup").checked==true)
				var examType=document.getElementById("sup").value;
			else if(document.getElementById("int").checked==true)
				var examType=document.getElementById("int").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
		if (examType == "Supplementary") {
			document.getElementById("showScheme").style.display = "block";
		}
		else{
			document.getElementById("showScheme").style.display = "none";
			
		}
		resetOption("subject");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}

	function getSCodeName(sCName) {
		examId=document.getElementById("examName").value;
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  parseInt(examId));
		
	}

	function updateToSubject(req) {
		var subco ="subco";
		updateOptionsFromMap(req, "subject", "- Select -");
	}
	function getSubjectsType(subjectNo) {
		if (subjectNo != '') {
			var args = "method=getSubjectsTypeBySubjectIdAndCollection&subjectId="
					+ subjectNo;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}
	function updateClass(req) {
		updateOptionsFromMap(req, "subjectType", "- Select -");
		updateSubjectsTypeBySubjectId(req, "subjectType");
	}
	function getSubjects(){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  null);
	}
	/*function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}
		else
		{
			sCName = document.getElementById("sCodeName_2").value;
		}
		getSubjectCodeName("subjectMap", sCName, "subject",
				updateToSubject,  examId);
	}*/
	function getSubjectsByExamName(examId){
		if(document.getElementById("sCodeName_1").checked){
			sCName = document.getElementById("sCodeName_1").value;
		}else{
			sCName = document.getElementById("sCodeName_2").value;
		}
		var examType="";
		if(document.getElementById("examId").checked){
			examType=document.getElementById("examId").value;
		}else if(document.getElementById("sup").checked){
			examType=document.getElementById("sup").value;
		}else if(document.getElementById("int").checked){
			examType=document.getElementById("int").value;
		} 
		getSubjectCodeNameYearWise(examType,examId,document.getElementById("year").value, sCName, "subject", updateToSubject);
	}

	function cancelAction() {
		document.location.href = "valuationStatusSubjectWise.do?method=initValuationStatusSubjectWise";
	}
	
</SCRIPT>
	<html:form action="/valuationStatusSubjectWise">
	<html:hidden property="formName" value="valuationStatusSubjectWiseForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="getValuationStatusSubjectWiseDetails" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="valuationStatusSubjectWiseForm" property="examType"/>' />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Valuation Status SubjectWise &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Valuation Status SubjectWise</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
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

								<tr>

									<td height="25" colspan="4" class="row-even">
									<div align="Center"><html:radio property="examType"
										styleId="examId" value="Regular"
										onclick="getExamName()"></html:radio>
									Regular&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Supplementary"
										styleId="sup"
										onclick="getExamName()"></html:radio>
									Supplementary&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<html:radio property="examType" value="Internal" styleId="int"
										onclick="getExamName()"></html:radio>
									Internal</div>
									</td>
								</tr>
								<tr>
								<td height="25" class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top" width="25%">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="valuationStatusSubjectWiseForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamName()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd"  width="25%">
									<div align="right"><span class="Mandatory">*</span>
									ExamName :</div>
									</td>

									<td class="row-even"  width="25%"><html:select
										name="valuationStatusSubjectWiseForm" property="examId"
										styleId="examName" styleClass="combo" onchange="getSubjectsByExamName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="valuationStatusSubjectWiseForm"
												property="examNameMap">
												<html:optionsCollection property="examNameMap"
													name="valuationStatusSubjectWiseForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" colspan="4" class="row-even" width="25%">
									<div align="left">
									<html:radio property="displaySubType" styleId="sCodeName_1" value="sCode" onchange="getSCodeName(this.value)">Subject Code</html:radio>
									 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 <html:radio property="displaySubType" styleId="sCodeName_2" value="sName" onchange="getSCodeName(this.value)">Subject Name</html:radio>
									</div>
									</td>
								</tr>
								<tr>
									<td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span>Subject:</div>
									</td>
									<td height="12" class="row-even" width="25%">
									<input type="hidden" name="subjId" id="subjId" value='<bean:write name="valuationStatusSubjectWiseForm" property="subjectId"/>'></input>
									<html:select property="subjectId" styleClass="combo" styleId="subject"
										name="valuationStatusSubjectWiseForm" style="width:450px"
										onchange="getSubjectsType(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="valuationStatusSubjectWiseForm"
											property="subjectMap">
											<html:optionsCollection property="subjectMap"
												name="valuationStatusSubjectWiseForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<td class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span>Subject Type :</div>
									</td>
									<td class="row-even" width="25%"><html:select
										property="subjectType" styleClass="combo"
										styleId="subjectType" name="valuationStatusSubjectWiseForm"
										style="width:90px" onchange="displayEvaluAndAnswerScript(this.value)">
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
									<logic:notEmpty name="valuationStatusSubjectWiseForm"	property="subjectTypeList">
										<html:optionsCollection property="subjectTypeList" name="valuationStatusSubjectWiseForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td width="13%" height="25" class="row-odd" >
										<div align="right">Exam</div>
										</td>
										<td width="19%" height="25" class="row-even">
										<html:radio property="isPreviousExam" value="true" styleId="isPreviousExam_1">Previous</html:radio>
										<html:radio property="isPreviousExam" value="false" styleId="isPreviousExam_2">Current</html:radio>
		    						</td>
		    						 	<td colspan="2">
		    						 	<div id = "showScheme">
		    						 	<table width="100%" height="100%">
		    						 	<tr>
			    						<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Scheme No</div></td>
						                <td class="row-even">
										<html:select property="schemeNo" styleId="schemeNo" styleClass="combo">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
											<html:option value="1">1</html:option>
											<html:option value="2">2</html:option>
											<html:option value="3">3</html:option>
											<html:option value="4">4</html:option>
											<html:option value="5">5</html:option>
											<html:option value="6">6</html:option>
											<html:option value="7">7</html:option>
											<html:option value="8">8</html:option>
											<html:option value="9">9</html:option>
											<html:option value="10">10</html:option>
										</html:select>
										</td>
										</tr>
										</table>
										 </div>
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
							<input type="button" class="formbutton" value="Reset"
								onclick="cancelAction()" />
							</td>
						</tr>
						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				
				<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				            <td height="45"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				              <tr>
				                <td ><img src="images/01.gif" width="5" height="5" /></td>
				                <td width="914" background="images/02.gif"></td>
				                <td><img src="images/03.gif" width="5" height="5" /></td>
				              </tr>
				              <tr>
				                <td width="5"  background="images/left.gif"></td>
				                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
				                  <tr >
				                    <td height="25" class="row-odd" width="50%" align="center" >Total No. of AnswerScripts</td>
				                    <td  height="25" width="50%" class="row-even" align="left">&nbsp;&nbsp;&nbsp;&nbsp;
				                    <b><bean:write name="valuationStatusSubjectWiseForm" property="totalAnswerScripts"/></b></td>
				                  </tr>
				                  <logic:notEqual value="0" name="valuationStatusSubjectWiseForm" property="totalAnswerScripts">
				                  <logic:notEmpty name="valuationStatusSubjectWiseForm" property="valuationMap">
				                  <logic:iterate id="id" name="valuationStatusSubjectWiseForm" property="valuationMap">
				                 <logic:equal value="Internal Valuator" name="id" property="key">
				                <tr>
				                <td height="25" class="row-odd" width="50%" align="center"><bean:write name="id" property="key"/></td>
				                <td height="25" class="row-odd" width="50%" align="center"></td>
				                </tr>
				                <nested:iterate id="to" name="id" property="value" indexId="count">
				             			 <tr align="left">
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <%=count+1%>.&nbsp;&nbsp;<bean:write name="to" property="employeeName"/></td>
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="to" property="answerScripts"/></td>
									       </tr>
				                </nested:iterate>
				                <tr><td width="50%" align="center" height="25" class="row-even"><b>Total Issued</b></td>
				                <td height="25" width="50%"align="left" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><bean:write name="valuationStatusSubjectWiseForm" property="totalInternalValuator"/></b></td>
				                </tr>
				                </logic:equal>
				                  </logic:iterate>
				                  <logic:iterate id="id" name="valuationStatusSubjectWiseForm" property="valuationMap">
				                 <logic:equal value="External Valuator" name="id" property="key">
				                <tr>
				                <td height="25" class="row-odd" width="50%" align="center" ><bean:write name="id" property="key" /></td>
				                <td height="25" class="row-odd" width="50%" align="center"></td>
				                </tr>
				                <nested:iterate id="to" name="id" property="value" indexId="count">
				             			 <tr align="left">
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <%=count+1%>.&nbsp;&nbsp;<bean:write name="to" property="employeeName"/></td>
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="to" property="answerScripts"/></td>
									       </tr>
				                </nested:iterate>
				                <tr><td width="50%" align="center" height="25" class="row-even"><b>Total Issued</b></td>
				                <td height="25" width="50%"align="left" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><bean:write name="valuationStatusSubjectWiseForm" property="totalExternalValuator"/></b></td>
				                </tr>
				                </logic:equal>
				                  </logic:iterate>
				                  <logic:iterate id="id" name="valuationStatusSubjectWiseForm" property="valuationMap">
				                 <logic:equal value="Project Minor" name="id" property="key">
				                <tr>
				                <td height="25" class="row-odd" width="50%" align="center" ><bean:write name="id" property="key" /></td>
				                <td height="25" class="row-odd" width="50%" align="center"></td>
				                </tr>
				                <nested:iterate id="to" name="id" property="value" indexId="count">
				             			 <tr align="left">
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <%=count+1%>.&nbsp;&nbsp;<bean:write name="to" property="employeeName"/></td>
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="to" property="answerScripts"/></td>
									       </tr>
				                </nested:iterate>
				                <tr><td width="50%" align="center" height="25" class="row-even"><b>Total Issued</b></td>
				                <td height="25" width="50%"align="left" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><bean:write name="valuationStatusSubjectWiseForm" property="totalMinorValuator"/></b></td>
				                </tr>
				                </logic:equal>
				                </logic:iterate>
				                <logic:iterate id="id" name="valuationStatusSubjectWiseForm" property="valuationMap">
				                 <logic:equal value="Project Major" name="id" property="key">
				                <tr>
				                <td height="25" class="row-odd" width="50%" align="center" ><bean:write name="id" property="key" /></td>
				                <td height="25" class="row-odd" width="50%" align="center"></td>
				                </tr>
				                <nested:iterate id="to" name="id" property="value" indexId="count">
				             			 <tr align="left">
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <%=count+1%>.&nbsp;&nbsp;<bean:write name="to" property="employeeName"/></td>
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="to" property="answerScripts"/></td>
									       </tr>
				                </nested:iterate>
				                <tr><td width="50%" align="center" height="25" class="row-even"><b>Total Issued</b></td>
				                <td height="25" width="50%"align="left" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><bean:write name="valuationStatusSubjectWiseForm" property="totalMajorValuator"/></b></td>
				                </tr>
				                </logic:equal>
				                </logic:iterate>
				                <logic:iterate id="id" name="valuationStatusSubjectWiseForm" property="valuationMap">
				                 <logic:equal value="Reviewer" name="id" property="key">
				                <tr>
				                <td height="25" class="row-odd" width="50%" align="center" ><bean:write name="id" property="key" /></td>
				                <td height="25" class="row-odd" width="50%" align="center"></td>
				                </tr>
				                <nested:iterate id="to" name="id" property="value" indexId="count">
				             			 <tr align="left">
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									                <%=count+1%>.&nbsp;&nbsp;<bean:write name="to" property="employeeName"/></td>
									                <td width="50%" align="left" height="25" class="row-even">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="to" property="answerScripts"/></td>
									       </tr>
				                </nested:iterate>
				                <tr><td width="50%" align="center" height="25" class="row-even"><b>Total Issued</b></td>
				                <td height="25" width="50%"align="left" class="row-even" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><bean:write name="valuationStatusSubjectWiseForm" property="totalReviewerValuator"/></b></td>
				                </tr>
				                </logic:equal>
				                </logic:iterate>
				                </logic:notEmpty>
				                </logic:notEqual>
				                </table>
				                </td>
				                <td width="5" height="30"  background="images/right.gif"></td>
				              </tr>
				              <tr>
				                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
				                <td background="images/05.gif"></td>
				                <td><img src="images/06.gif" /></td>
				              </tr>
				            </table></td>
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
<script type="text/javascript">
	var examtype = document.getElementById("typeExam").value;
	if (examtype == "Supplementary") {
		document.getElementById("showScheme").style.display = "block";
	}
	else{
		document.getElementById("showScheme").style.display = "none";
	}
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
	var subject = document.getElementById("subjId").value;
	if(subject.length != 0) {
	 	document.getElementById("subject").value=year;
	}
</script>
</html:form>