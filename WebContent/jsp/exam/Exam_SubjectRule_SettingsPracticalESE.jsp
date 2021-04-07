
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="../css/styles.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="calendar_us.js"></script>


<script type="text/javascript">
function theoryPracticalInternal() {
	
	document.getElementById("method").value="Add";
	document.ExamSubjectRuleSettingsForm.submit();

}
function theoryESE() {
	

	document.getElementById("method").value="theoryESE";
	document.ExamSubjectRuleSettingsForm.submit();

}

function practicalESE() {


	document.getElementById("method").value="practicalESE";
	document.ExamSubjectRuleSettingsForm.submit();

}
function subjectFinal() {

	  document.getElementById("method").value="subjectFinal";
	   document.ExamSubjectRuleSettingsForm.submit();

}
function practicalInternal() {
	
	document.getElementById("method").value="practicalInternal";
	document.ExamSubjectRuleSettingsForm.submit();

}
function onlyDecimalNumber(val, e) {
	var keynum;
	var keychar;
	var numcheck;
	if(e.keyCode==46 || e.keyCode>=37 &&  e.keyCode<=40)
	{
		return true;
	}
	var charCode = (e.which) ? e.which : e.keyCode
			if (charCode > 31 && (charCode < 48 || charCode > 57) )
				return false;
			

			return true;
			
	if (window.event) // IE
	{
		keynum = e.keyCode;
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which;
	}
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	if (keynum == 190) {
		var contain = val.indexOf('.');
		if (contain == -1) {
			return true;
		}
	}
	if (charCode > 31 && (charCode < 48 || charCode > 57))
		return false;
	keychar = String.fromCharCode(keynum);
	numcheck = /\d/;
	return numcheck.test(keychar);
}

</script>

<html:form action="/ExamSubjectRuleSettings.do" method="POST" styleId="ExamSubjectRuleSettingsForm">


	<html:hidden property="formName" value="ExamSubjectRuleSettingsForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="subjectFinal" styleId="method"/>

	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs">&gt;&gt;Exams
			&gt;&gt;Subjects Definition&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Subjects
					Definition</td>
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
								<tr>
									<td width="22%" class="row-odd" height="28">
									<div align="right"><bean:message
										key="knowledgepro.petticash.academicYear" />:</div>
									</td>
									<td width="34%" class="row-even"><bean:write
										name="ExamSubjectRuleSettingsForm" property="academicYearName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="academicYearName" /></td>
									<logic:notEqual value="" property="subjectName" name="ExamSubjectRuleSettingsForm">
										<td height="25" class="row-odd" align="right">
											Subject Name : 
										</td>
										<td class="row-even">
											<bean:write name="ExamSubjectRuleSettingsForm" property="subjectName" />
											<html:hidden name="ExamSubjectRuleSettingsForm" styleId="subjectName" property="subjectName" />
										</td>
									</logic:notEqual>
									<logic:equal value=""  property="subjectName" name="ExamSubjectRuleSettingsForm">
										<td class="row-even" colspan="2"></td>
									</logic:equal>

								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.programtype" /> :</div>
									</td>
									<td width="25%" height="25" class="row-even"><bean:write
										name="ExamSubjectRuleSettingsForm" property="programName" />
									<html:hidden name="ExamSubjectRuleSettingsForm"
										property="programName" /></td>
									<td height="25" class="row-odd" align="right">Course:</td>

									<td class="row-even"><logic:iterate
										name="ExamSubjectRuleSettingsForm" property="listCourseName"
										id="listCourses" type="com.kp.cms.to.exam.ExamCourseUtilTO">

										<nested:write name="listCourses" property="display" />
                                        &nbsp;&nbsp;&nbsp;

									</logic:iterate></td>
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
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><img src="images/practical_ese.gif"
						alt="CMS" width="790" height="33" border="0" usemap="#Map">

					<map name="Map">
						<area shape="rect" coords="243,9,361,29"
							onclick="practicalInternal()">
						<area shape="rect" coords="373,5,473,28" onclick="practicalESE()">
						<area shape="rect" coords="128,5,228,28" onclick="theoryESE()">
						<area shape="rect" coords="10,5,115,28"
							onclick="theoryPracticalInternal()">
						<area shape="rect" coords="490,4,590,27"
							onclick="subjectFinal()">
					</map> 
               <map name="Map">
						<area shape="rect" coords="491,6,591,29" href="#">
						<area shape="rect" coords="368,6,468,29" href="#">
						<area shape="rect" coords="245,5,345,28" href="#">
						<area shape="rect" coords="128,5,228,28" href="#">
						<area shape="rect" coords="11,5,111,28" href="#">
					</map></td>

					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="25" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Practical : ESE</td>
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
									<td height="25" class="row-white">
									<table width="100%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<tr>
											<td><img src="images/01.gif" width="5" height="5" /></td>
											<td width="914" background="images/02.gif"></td>
											<td><img src="images/03.gif" width="5" height="5" /></td>
										</tr>
										<tr>
											<td width="5" background="images/left.gif"></td>
											<td height="150" valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" colspan="8" class="row-white"><span
														class="heading">
														<nested:hidden	styleId="isRegularPractESEChecked"	property="practicalESETO.isRegularTheoryESEChecked" />
														<html:checkbox
														property="practicalESETO.regularPracticalESEChecked"
														styleId="regularPracticalESEChecked"></html:checkbox> </span><strong
														class="heading">Regular</strong></td>
												</tr>



												<tr>
													<td height="25" class="row-white"><span>
														<nested:hidden	styleId="isMultipleAnswerScriptsChecked" property="practicalESETO.isMultipleAnswerScriptsChecked" />
														<html:checkbox
														property="practicalESETO.multipleAnswerScriptsChecked"
														styleId="multipleAnswerScriptsChecked"></html:checkbox></span><strong
														class="heading">Multiple
													Answer Scripts</strong>
													
													</td>
												</tr>

												<nested:iterate property="practicalESETO.multipleAnswerScriptList" indexId="count">


													<tr>
														<td width="50%" class="row-odd" colspan="2">
                                                       
                                                       <nested:hidden property="id" />
														<nested:hidden property="isTheoryPractical"  value="p"/>
														<div align="right"><nested:write
															 property="name" /></div>
														</td>
														<td width="50%" class="row-even" colspan="2"><nested:text
															property="multipleAnswerScriptTheoryESE"
															styleId="multipleAnswerScriptTheoryESE"  onkeypress="return onlyDecimalNumber(this.value, event)"/> +</td>
													</tr>

												</nested:iterate>
												<tr>
													<td height="25" class="heading">
													<nested:hidden styleId="isMultipleEvaluatorChecked" property="practicalESETO.isMultipleEvaluatorChecked" />
													<html:checkbox
														property="practicalESETO.multipleEvaluatorChecked"
														styleId="multipleEvaluatorChecked" />Multiple Evaluator</td>
													<td colspan="7" class="row-white">&nbsp;</td>
												</tr>
												
												
												<tr>
													<td height="25" class="row-odd">
													<div align="right">No. of Evaluations :</div>
													</td>
													<td class="row-even"><html:text
														property="practicalESETO.noOfEvaluations" onblur="isValidNumber(this)"></html:text></td>
													<td width="8%" class="row-odd">
													<div align="right">Type Of Evaluation:</div>
													</td>
													<td width="31%" colspan="5" class="row-even"><html:select
														property="practicalESETO.typeOfEvaluation">
														<html:option value="0">
															<bean:message key="knowledgepro.select" />
														</html:option>
														<html:option value="1">Average</html:option>
														<html:option value="2">Highest</html:option>
														<html:option value="3">Lowest</html:option>
													</html:select></td>
												</tr>
											   
												<tr>
													<td class="row-odd" height="28">
													<div align="right">Evaluator ID:</div>
													</td>
                                                    
													<td class="row-even" colspan="8">
													<html:hidden property="practicalESETO.id1"/>
												<html:text property="practicalESETO.evalId1" size="2" onkeypress="return onlyDecimalNumber(this.value, event)"/><br/>
												<html:hidden property="practicalESETO.id2"/>
												<html:text property="practicalESETO.evalId2" size="2" onkeypress="return onlyDecimalNumber(this.value, event)"/><br/>
												<html:hidden property="practicalESETO.id3"/>
												<html:text property="practicalESETO.evalId3" size="2" onkeypress="return onlyDecimalNumber(this.value, event)"/><br/>
												<html:hidden property="practicalESETO.id4"/>
												<html:text property="practicalESETO.evalId4" size="2" onkeypress="return onlyDecimalNumber(this.value, event)"/><br/>
												<html:hidden property="practicalESETO.id5"/>
												<html:text property="practicalESETO.evalId5" size="2" onkeypress="return onlyDecimalNumber(this.value, event)"/><br/>		
							
												  </td>
												  <html:hidden property="practicalESETO.isTheoryPractical"  value="p"/>
												</tr>
 										
											</table>

											</td>
											<td width="5" height="30" background="images/right.gif"></td>
										</tr>
										<tr>
											<td height="5"><img src="images/04.gif" width="5"
												height="5" /></td>
											<td background="images/05.gif"></td>
											<td><img src="images/06.gif" /></td>
										</tr>
									</table>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
									<td width="17%" class="row-odd">
									<div align="right">Minimum :</div>
									</td>
									<td width="8%" height="25" class="row-even"><html:text property="practicalESETO.minimumMarksPracticalESE" onkeypress="return onlyDecimalNumber(this.value, event)"/></td>

									<td width="36%" class="row-odd">
									<div align="right"><span class="style1">(Marks
									entered in)</span>Entry Maximum mark :</div>
									</td>
									<td width="7%" height="17%" class="row-even"><html:text property="practicalESETO.maximumEntryMarksPracticalESE" onkeypress="return onlyDecimalNumber(this.value, event)"/></td>

									<td width="26%" class="row-odd">
									<div align="right">Maximum Mark:</div>
									</td>
									<td width="6%" height="17%" class="row-even"><html:text property="practicalESETO.maximumMarksPracticalESE" onkeypress="return onlyDecimalNumber(this.value, event)"/></td>
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
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">Theory Final(CIA Practical+ESE Practical)</td>
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
									<td class="row-odd" width="25%">
									<div align="right">Minimum Marks:</div>
									</td>
									<td height="25" class="row-even" width="25%"><html:text property="practicalESETO.minimumTheoryFinalMarksPracticalESE" onkeypress="return onlyDecimalNumber(this.value, event)"/></td>


									<td class="row-odd" width="25%">
									<div align="right">Maximum Marks:</div>
									</td>
									<td width="25%" height="17%" class="row-even"><html:text property="practicalESETO.maximumTheoryFinalMarksPracticalESE" onkeypress="return onlyDecimalNumber(this.value, event)"/></td>
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
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr> 
							<td width="49%" height="35" align="right"><input
								name="Submit2" type="submit" class="formbutton" value="Continue" /></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" align="left"><input type="submit"
								name="back" value=Back class="formbutton"
								onclick="practicalInternal()"></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>							
					</table>
					</td>
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
	var v1 = document.getElementById("isRegularPractESEChecked").value;
	var v2 = document.getElementById("isMultipleAnswerScriptsChecked").value;
	var v3 = document.getElementById("isMultipleEvaluatorChecked").value;
	if (v1 == 'true') {
		document.getElementById("regularPracticalESEChecked").checked = true;

	}
	if (v2 == 'true') {
		document.getElementById("multipleAnswerScriptsChecked").checked = true;
	}
	if (v3 == 'true') {
		document.getElementById("multipleEvaluatorChecked").checked = true;
	}
</script>									
					
</html:form>
