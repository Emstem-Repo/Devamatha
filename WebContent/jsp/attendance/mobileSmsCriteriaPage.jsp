<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function getSemistersByCourse(courseId) {
	var academicYear = document.getElementById("academicYear").value;
	getSemistersOnYearAndCourseScheme("semisterMap", courseId, "termNo",
			academicYear, updateSemisters);
}
function getSemisters(year) {
	var courseId = document.getElementById("course").value;
	getSemistersOnYearAndCourseScheme("semisterMap", courseId, "termNo",
			year, updateSemisters);
}
function updateSemisters(req) {
	updateOptionsFromMap(req, "termNo", "- Select -");
}



function  getClassesByYearAndCourse(courseId)
{
	var year1 =document.getElementById("academicYear").value;
	getclassesByYearAndCourse1("" ,courseId,year1,"classSchemWiseID",updateClass);
}

function updateClass(req)
{
	updateOptionsFromMap(req, "classSchemWiseID", "- Select -");
}
function clearField(field){
	if(field.value == "00")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0){
		field.value="00";
	}
}

function deleteSMSCriteria(smsCriteriaId,className)
{
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href = "MobileSmsCriteria.do?method=deleteSMSCriteria&smsCriteriaId="
				+smsCriteriaId+ "&className="+className;
	}
}
function editSMSCriteria(smsCriteriaId,className)
{
	
		document.location.href = "MobileSmsCriteria.do?method=editSMSCriteria&smsCriteriaId="
				+smsCriteriaId+ "&className="+className;
	
}
function showEntryParticular()
{
	document.getElementById("allClass").style.display="block";
	document.getElementById("allClass").style.width="100%";
}
function hideEntryParticular()
{
	document.getElementById("allClass").style.display="none";
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/MobileSmsCriteria">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${stateOperation != null && stateOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateSMSCriteria" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addSMSCriteria" />
		</c:otherwise>
	</c:choose>
	
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.attendanceentry.attendance" />
			 <span class="Bredcrumbs">&gt;&gt; Attendance SMS Criteria &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader">Add Attendance SMS Criteria</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr> 
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					 <FONT color="blue">* Note: Please Enter Time in 24 Hours Format </FONT>
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
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


                       <!-- starts -->
                       <tr>
                       <td height="25" width="50%" align="right" class="row-even" colspan="2"><div align="right">
                       <html:radio property="particular" value="true"   name="mobileSmsCriteriaForm"
								onclick="showEntryParticular()" styleId="particularID"></html:radio>
                       Particular</div></td>
                       <td width="50%" height="25" align="left" class="row-even" colspan="2"><div align="left">
                       
                       <html:radio property="particular" value="false" styleId="particularID"   name="mobileSmsCriteriaForm"
								onclick="hideEntryParticular()"></html:radio>
                       All</div></td>
                       </tr>
                       <tr>
                       <td width="100%" colspan="4">
                       
                       <div id="allClass">
                       <table width="100%"><tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.course.with.col" /></div>
									</td>
									<td height="25" class="row-even"><html:select
										property="courseId" styleClass="combo" styleId="course"
										name="mobileSmsCriteriaForm"
										onchange="getClassesByYearAndCourse(this.value)"
										style="width:200px">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="mobileSmsCriteriaForm" property="courseList">
											<html:optionsCollection property="courseList"
												name="mobileSmsCriteriaForm" label="name" value="id" />
										</logic:notEmpty>
									</html:select></td>
									<td height="25" class="row-odd" >
									<div align="right"> <span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.attendance.classname" />:</div>
										<input type="hidden"
										id="academicYear" name="mobileSmsCriteriaForm"
										value="<bean:write name="mobileSmsCriteriaForm" property="year"/>" ></input> 
									</td>
									
									<td class="row-even" >
					          <html:select property="classSchemWiseID" styleClass="comboLarge" styleId="classSchemWiseID"	>
							<html:option value="">	<bean:message key="knowledgepro.admin.select" />
							</html:option>
								
							
												<c:set var="classMap"	value="${baseActionForm.collectionMap['classMap']}" />
												<c:if test="${classMap != null}">
												<html:optionsCollection name="classMap"	label="value" value="key" />
												</c:if>
												<c:set var="classMap"	value="${baseActionForm.collectionMap['classMap']}" />
												<c:if test="${classMap == null}">
												<html:optionsCollection property="classMap" name="mobileSmsCriteriaForm"	label="value" value="key" />
												</c:if>
											     
												</html:select>
									
									</td>
								</tr>
                       </table>
                       
                       </div>
                       
                       </td>
                       </tr>


								<tr>
									
									
									

     <td width="50%" height="25" align="right" class="row-odd" colspan="2"><div align="right">
     <span class="Mandatory">*</span>&nbsp;SMS Trigger Time(24 Hour Format) :
     </div></td>
     <td width="50%" class="row-even" align="left" height="25" colspan="2"><div align="left">
       <html:text name="mobileSmsCriteriaForm" property="startHours" styleClass="Timings" styleId="smtime" size="2"
        maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/> :
       <html:text name="mobileSmsCriteriaForm" property="startMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" 
       onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
     </div></td>
     
                                     
								</tr>
								
								<tr style="display: none;">
								
								<td height="25" align="center" class="row-odd" colspan="2">
								<div align="right"><span class="Mandatory">*</span>&nbsp; Disable</div>
								</td>
								<td height="25" align="left" class="row-even" colspan="2">
								<div align="left">Yes
								<html:radio property="disableSMS" value="true"   name="mobileSmsCriteriaForm"
								onclick="showDate()"></html:radio>
								
								&nbsp;&nbsp;&nbsp;
								No
								<html:radio property="disableSMS" value="false" name="mobileSmsCriteriaForm"
								onclick="hideDate()"></html:radio>
								</div>
								</td>
								
								</tr>
								
								<tr style="display: none;">
								<td height="25" width="25%" class="row-odd" align="right"><div align="right">
								 From Date :
								</div></td>
								<td height="25" width="25%"  class="row-even" align="left"><div align="left">
								  <html:text readonly="true" name="mobileSmsCriteriaForm" property="fromDate" styleId="fromDateId"></html:text>
								  
								  <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'mobileSmsCriteriaForm',
													// input name
													'controlname' :'fromDate'
												});
												</script>
								</div></td>
								<td height="25" width="25%"  class="row-odd" align="right"><div align="right">
								 To Date :
								</div></td>
								<td height="25" width="25%"  class="row-even" align="left"><div align="left">
								<html:text readonly="true" name="mobileSmsCriteriaForm" property="toDate" styleId="toDateId"></html:text>
								<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'mobileSmsCriteriaForm',
													// input name
													'controlname' :'toDate'
												});
												</script>
								</div></td>
								</tr>
								
						<!-- end -->
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when
											test="${stateOperation != null && stateOperation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td> 
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>

				<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td height="25" class="row-odd" align="center">Course</td>
									<td class="row-odd" align="center">Class</td>
									<td class="row-odd" align="center">SMS Time</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								
								<logic:iterate id="mobileSMSCriteriaTOlist" name="mobileSmsCriteriaForm" property="mobileSMSCriteriaTOlist"
											indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="mobileSMSCriteriaTOlist"
												property="courseName" /></td>
											<td align="center"><bean:write name="mobileSMSCriteriaTOlist"
												property="className" /></td>
											<td align="center"><bean:write name="mobileSMSCriteriaTOlist"
												property="smsTime" /></td>
											
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editSMSCriteria('<bean:write name="mobileSMSCriteriaTOlist" property="id" />','<bean:write name="mobileSMSCriteriaTOlist" property="className" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteSMSCriteria('<bean:write name="mobileSMSCriteriaTOlist" property="id" />','<bean:write name="mobileSMSCriteriaTOlist" property="className" />')" /></div>
											</td>
											</tr>
										</logic:iterate>
								
								
							</table>
							</td>
						</tr>
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
            <tr>
              <td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
              <td width="100%" background="images/TcenterD.gif"></td>
              <td><img src="images/Tright_02.gif" width="9" height="29" /></td>
            </tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
