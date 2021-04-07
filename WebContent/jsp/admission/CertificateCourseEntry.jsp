<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
	function deleteDocType(docId, docType) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "DocumentType.do?method=deleteDocType&id=" + docId ;
		}
	}

	function resetValues() {
		document.getElementById("certificateCourseName").value = "";
		//document.getElementById("program").value = "";
		//document.getElementById("programType").value = "";
		document.getElementById("enhtime").value = "";
		document.getElementById("enmtime").value = "";
		document.getElementById("venue").value = "";
		document.getElementById("shtime").value = "";
		document.getElementById("smtime").value = "";
		document.getElementById("teacherId").selectedIndex = 0;
		document.getElementById("maxIntake").value = "";
		document.getElementById("extracurricular").value="false";
		resetErrMsgs();
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "CertificateCourse.do?method=activateCertificateCourse&id="
				+ id;
	}
	function getPrograms(programTypeId) {
		resetOption("program");
		getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	}
	function updatePrograms(req) {
		resetOption("program");
		updateOptionsFromMap(req,"program","- Select -");
	}
	
	function deleteCourse(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "CertificateCourse.do?method=deleteCertificateCourse&id=" + id ;
		}
	}

	function editCourse(id){
		document.location.href = "CertificateCourse.do?method=editCertificateCourse&id=" + id;
	}

	function clearField(field){
		if(field.value == "00")
			field.value = "";
	}
	function checkForEmpty(field){
		if(field.value.length == 0)
			field.value="00";
	}
	function getCourseByAcademicYear(){
		document.getElementById("method").value="getCourseByAcademicYear";
		document.certificateCourseEntryForm .submit();
	}
</script>

<html:form action="/CertificateCourse">

	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateCertificateCourse" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addCerficateCourse" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="certificateCourseEntryForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			Certificate Course &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Certificate Course </strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<FONT color="blue">* Note: Please Enter Time in 24 Hours Format </FONT>
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
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td width="16%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Certificate Course Name</div></td>
						 <td width="16%" class="row-even">
                         	<html:text property="certificateCourseName" styleId="certificateCourseName" styleClass="TextBox"
											size="25" maxlength="100" /> <span class="star"></span>
						 </td>
									<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Academic Year</div>
									</td>
									<td width="16%" class="row-even">
										<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="certificateCourseEntryForm" property="academicYear" />"/>
                 							<html:select property="academicYear" styleId="year" styleClass="combo" onchange="getCourseByAcademicYear()">
  	   											 <html:option value="">- Select -</html:option>
  	   											 <cms:renderAcademicYear></cms:renderAcademicYear>
   			  								 </html:select>
									</td>
									</tr>
									<tr>
									<td width="200" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Teacher</div>
									</td>
									<td width="300" height="25" class="row-even"><label>
									<logic:notEmpty name="certificateCourseEntryForm" property="teachersMap">
	                 				 	<html:select property="teacherId" name="certificateCourseEntryForm" styleId="teacherId" styleClass="comboLarge">
	                  					<html:option value="">--Select--</html:option>
	                  					<html:optionsCollection property="teachersMap" name="certificateCourseEntryForm" label="value" value="key"/>
	                  					</html:select>
	                  				</logic:notEmpty></label></td>
																
								<td width="16%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Max Intake</div>
									</td>
									<td width="16%" class="row-even">
										<html:text property="maxIntake" styleId="maxIntake" styleClass="TextBox"
											size="25" maxlength="100" onkeypress="return isNumberKey(event)"/> <span class="star"></span>
									</td>
								</tr>
								<tr>
								 <td width="16%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.Venue"/> </div></td>
	                  			<td width="16%" class="row-even" >
	                 				 <html:textarea property="venue" styleClass="TextBox" cols="18" rows="2" styleId="venue"></html:textarea>
								</td>
								<td  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admission.sem.type" />:</div>
									</td>
									<td  class="row-even">
									<html:select property="semType" styleId="semType" styleClass="combo" onchange="getCourseByAcademicYear()">
										<html:option value="ODD">ODD</html:option>
										<html:option value="EVEN">EVEN</html:option>
									</html:select>
									 </td>
								</tr>
								<tr >
	                      <td width="16%" class="row-odd" ><div align="right"><bean:message key = "knowledgepro.attn.period.from.time"/></div></td>
	                      <td width="16%" class="row-even" align="left" ><html:text name="certificateCourseEntryForm" property="startHours" styleClass="Timings" styleId="shtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="certificateCourseEntryForm" property="startMins" styleClass="Timings" styleId="smtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      <td width="16%" class="row-odd" ><div align="right"><bean:message key = "knowledgepro.attn.period.to.time"/></div></td>
	                      <td width="16%" class="row-even" align="left"><html:text name="certificateCourseEntryForm" property="endHours" styleClass="Timings" styleId="enhtime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="certificateCourseEntryForm" property="endMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      </tr>
							<tr>
								 <td class="row-odd" ><div align="right">
								 <span class="Mandatory">*</span>
								 <bean:message key="knowledgepro.admin.selectedSubjects"/> </div></td>
	                  			<td class="row-even" >
	                 				 <logic:notEmpty name="subjectList" scope="request">
	                 				 	<html:select property="subjectId" name="certificateCourseEntryForm" styleId="subjectId" styleClass="comboLarge">
	                  					<html:option value="">--Select--</html:option>
	                  					<html:optionsCollection name="subjectList" label="name" value="id"/>
	                  					</html:select>
	                  				</logic:notEmpty>
								</td>
								<td width="16%"  class="row-odd"><div align="right" ><span class="Mandatory">*</span>Extra Curricular:</div></td>
	                          <td width="16%" class="row-even" colspan="3">
					           <html:radio property="extracurricular" name="certificateCourseEntryForm" value="true" styleId="fixed">Yes</html:radio>
					           <html:radio property="extracurricular"  name="certificateCourseEntryForm" value="false" styleId="fixed">No</html:radio>
					            </td>
								</tr>
								<tr>
								 <td class="row-odd" ><div align="right">
								 <bean:message key="knowledgepro.admin.desc.with.col"/> </div></td>
	                  			<td class="row-even" colspan="3" >
	                  			<html:textarea property="description" styleClass="TextBox" cols="18" rows="2" style="width: 643px; height: 293px;" styleId="description">
	                  			</html:textarea>
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
				
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="bottom" class="heading">
						Groups
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<logic:notEmpty  name="certificateCourseEntryForm" property="groupList1">
				
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
							<nested:iterate name="certificateCourseEntryForm" property="groupList1" indexId="count">
							<tr class="row-even">
								<td> <c:out value="${count+1}"></c:out> </td>
								<td><nested:write property="name"/> </td>
								<td><nested:text  property="maxInTake"/> </td>
							</tr>
							</nested:iterate>
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
				</logic:notEmpty>
				
				<tr>

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							
							<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="method" styleClass="formbutton"
												value="Submit" ></html:submit>
										</c:otherwise>
									</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton" onclick="updateCertificateCourse()"></html:cancel>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetValues()"></html:button>
										</c:otherwise>
									</c:choose></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td class="row-odd" align="center">Course Name</td>
									<td class="row-odd" align="center">Teacher</td>
									<td class="row-odd" align="center">Max Intake</td>
									<td class="row-odd" align="center"><bean:message key="knowledgepro.admin.year"/> </td>
									<td class="row-odd" align="center">Sem Type</td>
									<td class="row-odd" align="center">Venue</td>
									<td class="row-odd" align="center">From Time:</td>
									<td class="row-odd" align="center">To Time:</td>
									<td class="row-odd" align="center">Extra Curricular:</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="courseList" scope="request">
								<logic:iterate id="courseList" name="courseList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td height="25" align="center">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td  align="center"><bean:write
										name="courseList" property="courseName" /></td>
									<td  align="center"><bean:write
										name="courseList" property="teacherName" /></td>	
									<td  align="center"><bean:write
										name="courseList" property="maxIntake" /></td>	
									<td  align="center"><bean:write
										name="courseList" property="year"/></td>		
									<td   align="center"><bean:write
										name="courseList" property="semType" /></td>
										<td   align="center"><bean:write
										name="courseList" property="venue" /></td>
									<td   align="center"><bean:write
										name="courseList" property="startTime" /></td>
									<td   align="center"><bean:write
										name="courseList" property="endTime" /></td>
									<td   align="center"><bean:write
										name="courseList" property="extracurricular" /></td>
									<td  height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="editCourse('<bean:write name="courseList" property="id"/>')">
									</div>
									</td>
									<td width="10%" height="25"  align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteCourse('<bean:write name="courseList" property="id"/>')">
									</div>
									</td>
									</tr>
								</logic:iterate>
								</logic:notEmpty>
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
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>