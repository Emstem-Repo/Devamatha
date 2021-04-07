<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
	function back(){
		document.location.href = "subjectRuleSettings.do?method=backPracticalInternal";
	}
	function rev(){
		document.getElementById("r3").checked=true;
		for(var i=1;i<=5;i++){
			if(i!=3)
				document.getElementById("r"+i).disabled=true;
		}
	}
</script>
<html:form action="/subjectRuleSettings">
	<html:hidden property="method" styleId="method" value="subjectFinal" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="subjectRuleSettingsForm" />
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.subjectrulesettings" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.exam.subjectrulesettings" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<bean:message key="knowledgepro.exam.studentEligibilityEntry.academicYear" />:
									</div>
									</td>
									<td height="25" class="row-even">
										<bean:write name="subjectRuleSettingsForm" property="academicYear"/>
									</td>
									<td   class="row-odd">
									<div align="right"><span class="Mandatory"></span>
									&nbsp;<bean:message key="knowledgepro.admin.programtype" />:
									</div>
									</td>
									<td class="row-even">
										<bean:write name="subjectRuleSettingsForm" property="programTypeName"/>
									</td>
								</tr>
								<tr>
									<td  class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td class="row-even">
									<c:choose>
										<c:when test="${subjectRuleSettingsForm.methodType=='update'}">
										<bean:write name="subjectRuleSettingsForm" property="course"/>
										</c:when>
										<c:otherwise>
										<bean:write name="subjectRuleSettingsForm" property="courseName"/>
										</c:otherwise>
									</c:choose>
									
										
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory"></span>&nbsp;
									<c:if test="${subjectRuleSettingsForm.methodType=='update'}">
										<bean:message key="knowledgepro.admin.detailsubject.subjectname" />
									</c:if>
									</div>
									</td>
									<td class="row-even">
										<c:if test="${subjectRuleSettingsForm.methodType=='update'}">
											<bean:write name="subjectRuleSettingsForm" property="subject"/>
										</c:if>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
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
               </td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Practical : ESE / Final Exam</td>
						</tr>
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
							<table width="100%" border="0"  cellspacing="1" cellpadding="1">
								<tr>
									<td>
									<input type="hidden" name="peseTo.tempregularPracticalESEChecked"
										id="tempregularPracticalESEChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='peseTo.dupregularPracticalESEChecked'/>" />
									<input type="checkbox" name="peseTo.regularPracticalESEChecked"
											id="regularPracticalESEChecked" />
											<script type="text/javascript">
												var reg = document.getElementById("tempregularPracticalESEChecked").value;
												if(reg == "on") {
													document.getElementById("regularPracticalESEChecked").checked = true;
												}else{
													document.getElementById("regularPracticalESEChecked").checked = false;
													}		
									</script>
									
									</td>
									<td>Regular </td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
									<input type="hidden" name="peseTo.tempmultipleAnswerScriptsChecked"
										id="tempmultipleAnswerScriptsChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='peseTo.dupmultipleAnswerScriptsChecked'/>" />
									<input type="checkbox" name="peseTo.multipleAnswerScriptsChecked"
											id="multipleAnswerScriptsChecked" />
											<script type="text/javascript">
												var ma = document.getElementById("tempmultipleAnswerScriptsChecked").value;
												if(ma == "on") {
													document.getElementById("multipleAnswerScriptsChecked").checked = true;
												}else{
													document.getElementById("multipleAnswerScriptsChecked").checked = false;
													}		
									</script>
									
									</td>
									<td>Multiple Answer Script </td>
									<td></td>
									<td></td>
								</tr>
								<logic:notEmpty name="subjectRuleSettingsForm" property="peseTo.multipleAnswerScriptList">
									<nested:iterate id="mas" name="subjectRuleSettingsForm" property="peseTo.multipleAnswerScriptList">
										<tr>
											<td colspan="2" class="row-odd"> <bean:write name="mas" property="name"/> </td>
											<td colspan="2" class="row-even"><nested:text property="multipleAnswerScriptTheoryESE" styleId="multipleAnswerScriptTheoryESE"></nested:text> </td>
										</tr>
									</nested:iterate>
								</logic:notEmpty>
								<tr>
									<td>
									<input type="hidden" name="peseTo.tempmultipleEvaluatorChecked"
										id="tempmultipleEvaluatorChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='peseTo.dupmultipleEvaluatorChecked'/>" />
									<input type="checkbox" name="peseTo.multipleEvaluatorChecked"
											id="multipleEvaluatorChecked" />
											<script type="text/javascript">
												var ms = document.getElementById("tempmultipleEvaluatorChecked").value;
												if(ms == "on") {
													document.getElementById("multipleEvaluatorChecked").checked = true;
												}else{
													document.getElementById("multipleEvaluatorChecked").checked = false;
													}		
									</script>
									
									</td>
									<td>Multiple Evaluator</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
								<td class="row-odd" align="right" > No. of Evaluations :</td>
								<td class="row-even">
								<nested:text name="subjectRuleSettingsForm" property="peseTo.noOfEvaluations" styleId="noOfEvaluations" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
								</td>
								<td class="row-odd" align="right">
								Type Of Evaluation:
								</td>
								<td class="row-even">
								<nested:select name="subjectRuleSettingsForm" property="peseTo.selectTypeOfEvaluation" >
									<html:option value="">Select</html:option>
									<html:option value="Average">Average</html:option>
									<html:option value="Highest">Highest</html:option>
									<html:option value="Lowest">Lowest</html:option>
								</nested:select>
								</td>
								</tr>
								<tr>
									<td class="row-odd">
									Evaluator ID:
									</td>
									<td colspan="3" class="row-even">
									<table height="100%" cellpadding="0" cellspacing="0">
									<tr>									
									<td class="row-even">
										<nested:text name="subjectRuleSettingsForm" property="peseTo.evalId1" styleId="evalId1" size="3" maxlength="6" onkeypress="return isNumberKey(event)"/><html:radio property="peseTo.reviewer" value="r1" styleId="r1" name="subjectRuleSettingsForm"/><br/>
										<nested:text name="subjectRuleSettingsForm" property="peseTo.evalId2" styleId="evalId2" size="3" maxlength="6" onkeypress="return isNumberKey(event)"/><html:radio property="peseTo.reviewer" value="r2" styleId="r2" name="subjectRuleSettingsForm"/><br/>
										<nested:text name="subjectRuleSettingsForm" property="peseTo.evalId3" styleId="evalId3" size="3" maxlength="6" onkeypress="return isNumberKey(event)" onkeyup="rev()"/><html:radio property="peseTo.reviewer" value="r3" styleId="r3" name="subjectRuleSettingsForm"/><br/>
										<nested:text name="subjectRuleSettingsForm" property="peseTo.evalId4" styleId="evalId4" size="3" maxlength="6" onkeypress="return isNumberKey(event)"/><html:radio property="peseTo.reviewer" value="r4" styleId="r4" name="subjectRuleSettingsForm"/><br/>
										<nested:text name="subjectRuleSettingsForm" property="peseTo.evalId5" styleId="evalId5" size="3" maxlength="6" onkeypress="return isNumberKey(event)"/><html:radio property="peseTo.reviewer" value="r5" styleId="r5" name="subjectRuleSettingsForm"/>
									</td>
									<td class="row-even" style="font-size:75pt; font-family: monospace;" valign="top">}</td>
									<td class="row-even" valign="middle" style="font-size:10pt;"><strong>Is Third Evaluator</strong></td>
									</tr>
									</table>
									</td>
								</tr>
								
								<tr>
									<td>
									<input type="hidden" name="peseTo.tempSupplementaryChecked"
										id="tempSupplementaryChecked"
										value="<nested:write name='subjectRuleSettingsForm' property='peseTo.dupSupplementaryChecked'/>" />
									<input type="checkbox" name="peseTo.supplementaryChecked"
											id="supplementaryChecked" />
											<script type="text/javascript">
												var sup = document.getElementById("tempSupplementaryChecked").value;
												if(sup == "on") {
													document.getElementById("supplementaryChecked").checked = true;
												}else{
													document.getElementById("supplementaryChecked").checked = false;
													}		
									</script>
									
									</td>
									<td>Supplementary </td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td class="row-odd">Supplementary Minimum Marks:</td>
									<td class="row-even"> <nested:text name="subjectRuleSettingsForm" property="peseTo.supplementaryMinMarks" styleId="supplementaryMinMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/></td>
									<td class="row-odd">Supplementary Maximum Marks:</td>
									<td class="row-even"><nested:text name="subjectRuleSettingsForm" property="peseTo.supplementaryMaxMarks" styleId="supplementaryMaxMarks" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/> </td>
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
					
					<table width="100%" border="0"  cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
							<table width="100%" border="0" cellspacing="1" cellpadding="1">
								<tr>
									<td class="row-odd">Minimum :</td>
									<td class="row-even">
									<nested:text name="subjectRuleSettingsForm" property="peseTo.minimumMarksPracticalESE" styleId="minimumMarksPracticalESE" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,event)"/>
									</td>
									<td class="row-odd">(Marks entered in)Entry Maximum mark :</td>
									<td class="row-even">
									<nested:text name="subjectRuleSettingsForm" property="peseTo.maximumEntryMarksPracticalESE" styleId="maximumEntryMarksPracticalESE" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
									</td>
									<td class="row-odd">Maximum Mark:</td>
									<td class="row-even">
									<nested:text name="subjectRuleSettingsForm" property="peseTo.maximumMarksPracticalESE" styleId="maximumMarksPracticalESE" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/>
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
					
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="heading">Practical Final</td>
						</tr>
					</table>
					
					<table width="100%" border="0"  cellspacing="0" cellpadding="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td>
							<table width="100%" border="0"  cellspacing="1" cellpadding="1">
								<tr>
								<td class="row-odd">Minimum Marks:</td>
								<td class="row-even"> <nested:text name="subjectRuleSettingsForm" property="peseTo.minimumTheoryFinalMarksPracticalESE" styleId="minimumTheoryFinalMarksPracticalESE" size="10" maxlength="6" onkeypress="return isDecimalNumberKey(this.value,event)"/></td>
								<td class="row-odd">Maximum Marks:</td>
								<td class="row-even"><nested:text name="subjectRuleSettingsForm" property="peseTo.maximumTheoryFinalMarksPracticalESE" styleId="maximumTheoryFinalMarksPracticalESE" size="10" maxlength="6" onkeypress="return isNumberKey(event)"/> </td>
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td align="center">
								<html:submit value="Continue" styleClass="formbutton" styleId="submit"></html:submit>
								&nbsp;
								<html:button property="" value="Back" styleClass="formbutton" onclick="back()"></html:button>
							</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript">
var eval3=document.getElementById("evalId3");
var r3=document.getElementById("r3");
if(eval3.value!=null || eval3.value!=""){
	r3.checked=true;	
}
for(var i=1;i<=5;i++){
	if(i!=3)
	document.getElementById("r"+i).disabled=true;
}
</script>