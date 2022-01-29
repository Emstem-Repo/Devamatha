<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function submitAndPrint() {
		document.getElementById("method").value = "addOldStudentAndPrint";
		document.tcDetailsOldStudentsForm.submit();
	}

	function addStudent(){
		document.getElementById("method").value = "addOldStudentData";
		document.tcDetailsOldStudentsForm.submit();
	}
	function show(){
		document.getElementById("subjectlabel").style.display="block";
		document.getElementById("subjectsPassed").style.display="block";
		document.getElementById("passlabel").style.display="none";
	}
	function hide(){
		document.getElementById("subjectlabel").style.display="none";
		document.getElementById("subjectsPassed").style.display="none";
		document.getElementById("passlabel").style.display="block";
	}

	function cancelSlipPrint(){
		document.getElementById("method").value = "initTcDetails";
		document.tcDetailsOldStudentsForm.submit();
	}
</script>
<script language="JavaScript" src="js/admission/admissionBiodata.js"></script>
<html:form action="/TcDetailsOldStudents" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="tCDetailsOldStudentsForm" />
	<html:hidden property="pageType" value="2" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admission" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admission.tcdetailsoldstudents" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.admission.tcdetailsoldstudents" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="red"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
							<%String dynastyle=""; %>
								<tr>
					                <td class="row-odd">
					                <div id="classsdiv" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.tc.for"/>:</div>
					                </td>
					                <td class="row-even">
					                <html:select name="tcDetailsOldStudentsForm" styleId="tcFor" property="tcFor">
					                <html:option value="CJC">Christ Junior College</html:option>
					                <html:option value="CEC">Christ Evening College</html:option>
					                </html:select>
					                </td>
					                <td class="row-odd">
					                <div id="classsdiv" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.tc.type"/>:</div>
					                </td>
					                <td class="row-even">
					                <html:select name="tcDetailsOldStudentsForm" styleId="tcType" property="tcType">
					                <html:option value="Normal">Normal</html:option>
					                <html:option value="Discontinued">Discontinued</html:option>
					                <html:option value="Duplicate">Duplicate</html:option>
					                </html:select>
					                </td>
					           </tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.interview.Year" /></div>
									</td>
									<td  class="row-even">
										<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="tcDetailsOldStudentsForm" property="year"/>" />
									<nested:select property="year" styleId="year" styleClass="combo">
										<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
										<cms:renderYear></cms:renderYear>
									</nested:select>
									</td>
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.hostel.reservation.registerNo" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="registerNo" styleId="registerNo" size="10" maxlength="20"/>
									</td>
								</tr>
								
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.studentEligibilityEntry.studentname" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="name" styleId="name" size="30" maxlength="25"/>
									</td>
									
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.studentnumber" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="studentNo" styleId="studentNo" size="10" maxlength="16"/>
									</td>
									
								</tr>
								<tr>
								<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.dateofbirth" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="dateOfBirth" styleId="dateOfBirth" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsOldStudentsForm',
										// input name
										'controlname' :'dateOfBirth'
									});
									</script>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.employee.gender.required" /></div>
									</td>
									<td height="17" class="row-even" align="left"><nested:radio property="gender" value="MALE" name="tcDetailsOldStudentsForm"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
										<nested:radio property="gender" value="FEMALE" ><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio></td>
								</tr>
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.religion" /></div>
									</td>
									<td   class="row-even">
									<table>
									<tr><td>
									<input type="hidden" id="religionType" name="religionType" value='<bean:write name="tcDetailsOldStudentsForm" property="religionId"/>'/>
									<logic:equal value="Other" property="religionId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="religionId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
									<html:select property="religionId" styleId="religionId"  styleClass="combo" onchange="funcOtherShowHide('religionId','religionOthersCatg')">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="religionToList" label="religionName" value="religionId" />
										<html:option value="Other">Other</html:option>
									</html:select>
									</td></tr>
									<tr><td><nested:text property="religionOthers" size="30" maxlength="30" styleId="religionOthersCatg" style="<%=dynastyle %>"></nested:text></td></tr>
									</table>
									</td>
									
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.caste" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="caste" styleId="caste" size="10" maxlength="16"/>
									</td>
								</tr>
								
								<tr>
								
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.subcaste" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="subCaste" styleId="subCaste" size="10" maxlength="16"/>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowladgepro.admin.castecategory" /></div>
									</td>
									<td   class="row-even">
									<table><tr><td>
									<input type="hidden" id="categoryType" name="categoryType" value='<bean:write name="tcDetailsOldStudentsForm" property="categoryId"/>'/>
									<logic:equal value="Other" property="categoryId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="categoryId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
									<html:select property="categoryId" styleId="categoryId"  styleClass="combo" onchange="funcOtherShowHide('categoryId','otherCastCatg')">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="categoryToList" label="casteName" value="casteId" />
										<html:option value="Other">Other</html:option>
									</html:select>
									</td></tr>
									<tr><td><nested:text property="casteOthers" size="30" maxlength="30" styleId="otherCastCatg" style="<%=dynastyle %>"></nested:text></td></tr>
									</table>
									</td>
								</tr>
								
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.nationality" /></div>
									</td>
									<td   class="row-even">
									<table><tr><td>
									<input type="hidden" id="nationalityType" name="nationalityType" value='<bean:write name="tcDetailsOldStudentsForm" property="nationalityId"/>'/>
									<logic:equal value="Other" property="nationalityId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="nationalityId" name="tcDetailsOldStudentsForm">
										<%dynastyle="display:none;"; %>
									</logic:notEqual>
									<html:select property="nationalityId" styleId="nationalityId"  styleClass="combo" onchange="funcOtherShowHide('nationalityId','nationalityOthersCatg')">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="nationalityToList" label="name" value="id" />
										<html:option value="Other">Other</html:option>
									</html:select>
									</td></tr>
									<tr><td><nested:text property="nationalityOthers" size="30" maxlength="30" styleId="nationalityOthersCatg" style="<%=dynastyle %>"></nested:text></td></tr>
									</table>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.fatherName" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="fatherName" styleId="fatherName" size="30" maxlength="30"/>
									</td>
								</tr>
								
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admission.motherName" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="motherName" styleId="motherName" size="30" maxlength="30"/>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="admissionFormForm.admissionDate" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="admissionDate" styleId="admissionDate" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsOldStudentsForm',
										// input name
										'controlname' :'admissionDate'
									});
									</script>
									</td>
									</tr>
									
									<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateOfLeaving" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="dateOfLeaving" styleId="dateOfLeaving" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsOldStudentsForm',
										// input name
										'controlname' :'dateOfLeaving'
									});
									</script>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.classname" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="className" styleId="className" size="10" maxlength="16"/>
									</td>
								</tr>
								
								
								<tr>
									
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.part1subjects" /></div>
									</td>
									<td   class="row-even">
									<html:textarea name="tcDetailsOldStudentsForm" property="part1Subjects" styleId="part1Subjects" cols="22" rows="2"/>
									</td>
									<td   height="25" class="row-odd" align="right">
									<div ><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.part2subjects" /></div>
									</td>
									<td  class="row-even">
									<html:textarea name="tcDetailsOldStudentsForm" property="part2Subjects" styleId="part2Subjects" cols="22" rows="2"/>
									</td>
								</tr>
								
								<tr>
									<td class="row-odd">
									<table align="right"><tr>
									<td class="row-odd">
									<div align="right" id="passlabel"><bean:message
										key="knowledgepro.admission.tc.details.passed"/></div>
									</td>
									</tr>
									<tr><td class="row-odd">
									<div align="right" id="subjectlabel"><bean:message
										key="knowledgepro.admin.subjectspassed"/></div>
									</td>
									</tr></table>
									</td>
									<td class="row-even">
									<table>
									<tr>
									<td class="row-even">
									
									<nested:radio property="passed" value='YES' styleId="pyes" onclick="hide()">
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="passed" value='NO' styleId="pno" onclick="show()">
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
									</tr>
									<tr>
									<td class="row-even">
									<html:textarea name="tcDetailsOldStudentsForm" property="subjectsPassed" styleId="subjectsPassed" cols="22" rows="2"/>
									</td>
									</tr>
									</table>
									</td>
									
									<td height="25" class="row-odd">
										<div align="right">
										<bean:message
											key="knowledgepro.admission.student.tcDetails.publicExaminationName.label" />
										</div>
									</td>
									<td height="25" class="row-even">
										<nested:text property="publicExamName" styleId="publicExamName" maxlength="50"/>
									</td>
									
								</tr>
								<tr>
									<td height="25" class="row-odd">
										<div align="right"><bean:message
											key="knowledgepro.admission.examregno" />
										</div>
									</td>
									<td height="25" class="row-even">
										<nested:text property="examRegisterNo" styleId="examRegisterNo" maxlength="50"/>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.admin.academicyear"/></div>
									</td>
									<td  class="row-even">
									<input type="hidden" id="tempyr" name="tempyr" value='<bean:write name="tcDetailsOldStudentsForm" property="yr"/>' />
									<nested:select property="yr" styleId="syear" styleClass="comboSmall">
										<html:option value="">Select</html:option>
						              	<cms:renderYear normalYear="true"></cms:renderYear>
									</nested:select></td>
								</tr>
								
								<tr>
									<td  height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.examDefinition.month" /></div>
									</td>
									<td   class="row-even">
									<nested:select property="month" styleId="month" styleClass="comboSmall">
										<html:option value="">Select</html:option>
										<html:option value="JAN">JAN</html:option>
						              	<html:option value="FEB">FEB</html:option>
										<html:option value="MAR">MAR</html:option>
										<html:option value="APR">APR</html:option>
										<html:option value="MAY">MAY</html:option>
										<html:option value="JUN">JUN</html:option>
										<html:option value="JUL">JUL</html:option>
										<html:option value="AUG">AUG</html:option>
										<html:option value="SEPT">SEPT</html:option>
										<html:option value="OCT">OCT</html:option>
										<html:option value="NOV">NOV</html:option>
										<html:option value="DEC">DEC</html:option>
									</nested:select>
									</td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.particular"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="scolorship" value='YES'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="scolorship" value='NO'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
								</tr>
								
								<tr>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.feespaid"/></div>
									</td>
									<td class="row-even">
									<nested:radio property="feePaid" value='YES'>
										<bean:message key="knowledgepro.yes" />
									</nested:radio> 
									<nested:radio property="feePaid" value='NO'>
										<bean:message key="knowledgepro.no" />
									</nested:radio>									
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.characterandConduct" /></div>
									</td>
									<td   class="row-even">
									<html:select property="characterAndConductId" styleId="characterAndConductId"  styleClass="combo">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<html:optionsCollection property="charAndConductToList" label="name" value="id" />
									</html:select>
									</td>
								</tr>
								
								<tr>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.tc.details.dateofApplication" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="dateOfApplication" styleId="dateOfApplication" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsOldStudentsForm',
										// input name
										'controlname' :'dateOfApplication'
									});
									</script>
									</td>
									<td   height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.dateofissue" /></div>
									</td>
									<td   class="row-even">
									<html:text name="tcDetailsOldStudentsForm" property="dateOfIssue" styleId="dateOfIssue" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									new tcal( {
										// form name
										'formname' :'tcDetailsOldStudentsForm',
										// input name
										'controlname' :'dateOfIssue'
									});
									</script>
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
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							<html:submit styleClass="formbutton" onclick="addStudent()"></html:submit> &nbsp;
							<html:button property="" styleId="sap" styleClass="formbutton" value="Submit And Print" onclick="submitAndPrint()"></html:button>
							<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelSlipPrint()"></html:button>
							</td>
						</tr>
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
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>
<script type="text/javascript">
	var yearId = document.getElementById("tempyr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("syear").value = yearId;
	}
</script>
<script>
	var check="YES";
	if(document.getElementById("pno").checked)
		check=document.getElementById("pno").value;
	if(check=="YES"){
		hide();
	}else if(check=="NO"){
		show();
	}
</script>



