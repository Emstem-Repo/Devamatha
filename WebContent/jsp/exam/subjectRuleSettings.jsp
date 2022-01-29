<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function getCourses(programTypeID) {
	getCoursesByProgramType1("coursesMap", programTypeID, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMapMultiselect(req, "course", "- Select -");
}
function setCourseName() {
	document.getElementById("courseName").value = document
			.getElementById("course").options[document
			.getElementById("course").selectedIndex].text;
}
function setProgramTypeName() {
	document.getElementById("programTypeName").value = document
			.getElementById("programTypeId").options[document
			.getElementById("programTypeId").selectedIndex].text;
}
function getSchemeNo(schemeType) {
	if(schemeType!=null){
		var destination3 = document.getElementById("schemeNo");
		for (x1=destination3.options.length-1; x1>0; x1--) {
			destination3.options[x1]=null;
		}
		destination3.options[0]=new Option("- Select -","");
			if(schemeType==1){
				destination3.options[1]=new Option("1","1");
				destination3.options[2]=new Option("3","3");
				destination3.options[3]=new Option("5","5");
				destination3.options[4]=new Option("7","7");
				destination3.options[5]=new Option("9","9");
				}else if(schemeType==2){
					destination3.options[1]=new Option("2","2");
					destination3.options[2]=new Option("4","4");
					destination3.options[3]=new Option("6","6");
					destination3.options[4]=new Option("8","8");
					destination3.options[5]=new Option("10","10");
					}else if(schemeType==3){
						destination3.options[1]=new Option("1","1");
						destination3.options[2]=new Option("2","2");
						destination3.options[3]=new Option("3","3");
						destination3.options[4]=new Option("4","4");
						destination3.options[5]=new Option("5","5");
						destination3.options[6]=new Option("6","6");
						destination3.options[7]=new Option("7","7");
						destination3.options[8]=new Option("8","8");
						destination3.options[9]=new Option("9","9");
						destination3.options[10]=new Option("10","10");
						}
		}
}
function performAction(met){
	var year=document.getElementById("academicYear").value;
	var ptId=document.getElementById("programTypeId").value;
	var ob=document.getElementById("course");
	var selected = new Array();
	 for (var i = 0; i < ob.options.length; i++) {
		 if (ob.options[ i ].selected) {
			 selected.push(ob.options[ i ].value);
		 }
	}
	var stype=document.getElementById("schemeType").value;
	var semNo=document.getElementById("schemeNo").value;
	document.location.href ="subjectRuleSettings.do?method="+met+"&academicYear="+year+"&programTypeId="+ptId+"&courseIds="+selected+"&schemeType="+stype+"&schemeNo="+semNo+"&pageType="+1;
}
function resetValues() {
	document.location.href = "subjectRuleSettings.do?method=initSubjectRuleSet";
}


</script>
<html:form action="/subjectRuleSettings" method="post">
	<html:hidden property="method" styleId="method" value="add" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" styleId="formName" value="subjectRuleSettingsForm" />
	<html:hidden property="programTypeName" styleId="programTypeName" value=""/>
	<html:hidden property="courseName" styleId="courseName" value=""/>
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
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.academicYear" />
									:</div>
									</td>
									<td class="row-even" width="25%"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="subjectRuleSettingsForm" property="academicYear"/>' />

									<html:select property="academicYear" styleId="academicYear" styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>
									<td class="row-odd"></td>
									<td class="row-even"></td>
								</tr>
								<tr>
									<td  height="15" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>

									<td   height="15" class="row-even"><html:select
										property="programTypeId" styleClass="body"
										styleId="programTypeId" onchange="getCourses(this.value),setProgramTypeName()">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection name="subjectRuleSettingsForm"
											property="programTypeList" label="programTypeName" value="programTypeId" />

									</html:select></td>
									<td   class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course" />:</div>
									</td>
									<td  class="row-even"><nested:select
										property="courseIds" styleClass="body"
										multiple="multiple" size="5" styleId="course"
										style="width:450px;height:300px" onchange="setCourseName()">
											<c:if test="${coursesMap != null}">
											<html:optionsCollection name="coursesMap" label="value" value="key" />
											</c:if>
									</nested:select></td>
								</tr>
								<tr>
									<td   class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.exam.examDefinition.schemeType" />:</div>
									</td>
									<td class="row-even" ><html:select
										property="schemeType" styleId="schemeType"
										styleClass="combo" onchange="getSchemeNo(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:option value="1">Odd</html:option>
										<html:option value="2">Even</html:option>
										<html:option value="3">Both</html:option>
									</html:select></td>
									<td colspan="2">
											<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
											<tr >
											<td  width="50%"  class="row-odd"><div align="right"><span class="Mandatory"></span>&nbsp;<bean:message
														key="knowledgepro.fee.semister" />:</div> </td>
											<td width="50%" class="row-even">
											<input type="hidden"
										id="sn" name="sn"
										value='<bean:write name="subjectRuleSettingsForm" property="schemeNo"/>' />
											
											<nested:select name="subjectRuleSettingsForm"  property="schemeNo" styleId="schemeNo" styleClass="combo">
												<html:option value="">select</html:option>
											</nested:select>
											</td>
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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td style="height: 10px" align="center" colspan="9"></td></tr>
					<tr>
							<td width="42%" height="35" align="right">
							<html:submit property="method" value="Add" styleClass="formbutton" styleId="submit"></html:submit>
							</td>
							<td width="2%"></td>
							<td width="6%" height="35" align="center"><html:button
								property="submit" value="Reset" styleClass="formbutton"
								onclick="resetValues()"></html:button></td>
							<td width="2%"></td>
							<td width="5%" align="left">
							<input type="button" class="formbutton" value="Edit" onclick="performAction('editSubjectRuleSetting')"/>
							</td>
							<td width="2%"></td>
							<td width="6%" height="35" align="center">
							<input type="button" class="formbutton" value="Delete" onclick="performAction('deleteSubjectRuleSettings')"/>
							</td>
							<td width="2%"></td>
							<td width="41%" height="35" align="left">
							<input type="button" class="formbutton" value="Copy" onclick="performAction('copySubjectRuleSetting')"/>
							</td>
						</tr>					
						<tr><td style="height: 10px" align="center" colspan="9"></td></tr>						
					</table>
					</div>
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
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
	var schemeType=document.getElementById("schemeType").value;
	if(schemeType!=null){
		var destination3 = document.getElementById("schemeNo");
		for (x1=destination3.options.length-1; x1>0; x1--) {
			destination3.options[x1]=null;
		}
		destination3.options[0]=new Option("- Select -","");
			if(schemeType==1){
				destination3.options[1]=new Option("1","1");
				destination3.options[2]=new Option("3","3");
				destination3.options[3]=new Option("5","5");
				destination3.options[4]=new Option("7","7");
				destination3.options[5]=new Option("9","9");
				}else if(schemeType==2){
					destination3.options[1]=new Option("2","2");
					destination3.options[2]=new Option("4","4");
					destination3.options[3]=new Option("6","6");
					destination3.options[4]=new Option("8","8");
					destination3.options[5]=new Option("10","10");
					}else if(schemeType==3){
						destination3.options[1]=new Option("1","1");
						destination3.options[2]=new Option("2","2");
						destination3.options[3]=new Option("3","3");
						destination3.options[4]=new Option("4","4");
						destination3.options[5]=new Option("5","5");
						destination3.options[6]=new Option("6","6");
						destination3.options[7]=new Option("7","7");
						destination3.options[8]=new Option("8","8");
						destination3.options[9]=new Option("9","9");
						destination3.options[10]=new Option("10","10");
						}
	}

	var sn = document.getElementById("sn").value;
	if (sn != null && sn.length != 0) {
		document.getElementById("schemeNo").value = sn;
	}
</script>
