<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
    <script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
	function resetValue() {
		 resetFieldAndErrMsgs();
	}
	function cancel(){
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelHome(){
		document.location.href = "fineCategory.do?method=initFineCategory";
	}
	function examMap(year){
		getExamByYear("examMap", year, "examId", updateExams);
		}	
	function updateExams(req){
		updateOptionsFromMap(req,"examId","- Select -");
		}	
	function searchTheInvigilators(){
		document.getElementById("method").value="searchInvigilatorsToEdit";
		document.examInvigilatorDutyEditForm.submit();
		}
	function getRoomNoMap(id){
		roomNosByCampus("roomNoMap", id, "roomNo", updateRooNos);
		facultyByCampus("facultyMap", id, "facultyId", updateFaculty);
		}
	function updateRooNos(req){
		updateOptionsFromMap(req,"roomNo","- Select -");
		}	
	function updateFaculty(req){
		updateOptionsFromMap(req,"facultyId","- Select -");
		}	
</script>

<html:form action="/examInvigilationDutyEdit">
<html:hidden property="formName" value="examInvigilatorDutyEditForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method"	value="searchInvigilatorsToEdit" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			Exam Invigilators Duty Edit &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Exam Invigilators Duty Edit</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span
						class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></FONT></div>
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
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory">* </span><bean:message key="knowledgepro.admin.year" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										<html:hidden property="tempAcademicYear" styleId="tempAcademicYear"/>
										<html:select property="academicYear" styleClass="combo"	styleId="academicYear" name="examInvigilatorDutyEditForm" onchange="examMap(this.value)">
											<html:option value=""> <bean:message key="knowledgepro.admin.select" />	</html:option>
											<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
										</html:select>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory">* </span><bean:message key="knowledgepro.exam" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="examId" styleId="examId" name="examInvigilatorDutyEditForm">
                    						<html:option value="">--Select--</html:option>
                    						<c:choose>
				             					<c:when test="${examMap != null}">
				             					<html:optionsCollection name="examMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
	                    							<logic:notEmpty property="examMap" name="examInvigilatorDutyEditForm">
							   							<html:optionsCollection property="examMap" label="value" value="key"/>
							   						</logic:notEmpty>
						   						</c:otherwise>
						   					</c:choose>
						   					</html:select>
									</td>
								</tr>
								<tr>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"> *</span><bean:message key="knowledgepro.exam.room.availability.campus" /></div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="locationId" styleId="locationId" name="examInvigilatorDutyEditForm" onchange="getRoomNoMap(this.value)">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="workLocationMap" name="examInvigilatorDutyEditForm">
						   							<html:optionsCollection property="workLocationMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span>Room No:</div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="examRoomNo" styleId="roomNo" name="examInvigilatorDutyEditForm">
                    						<html:option value="">--Select--</html:option>
                    						<c:choose>
                    							<c:when test="${roomNoMap != null}">
				             					<html:optionsCollection name="roomNoMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
													<logic:notEmpty property="roomNoMap" name="examInvigilatorDutyEditForm">
							   							<html:optionsCollection property="roomNoMap" label="value" value="key"/>
							   						</logic:notEmpty>
												</c:otherwise>
												</c:choose>
						   					</html:select>
									</td>
								</tr>
								<tr>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span>Examiner Type:</div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="examExaminerType" styleId="examinerType" name="examInvigilatorDutyEditForm">
                    						<html:option value="">--Select--</html:option>
                    						<html:option value="I">Invigilator</html:option>
                    						<html:option value="R">Reliever</html:option>
						   					</html:select>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span><bean:message key="knowledgepro.employee.faculty"/>:</div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="examFacultyId" styleId="facultyId" name="examInvigilatorDutyEditForm" styleClass="comboMediumBig">
                    						<html:option value="">--Select--</html:option>
                    						<c:choose>
                    							<c:when test="${facultyMap != null}">
				             					<html:optionsCollection name="facultyMap" label="value" value="key" />
												</c:when>
												<c:otherwise>
													<logic:notEmpty property="facultyMap" name="examInvigilatorDutyEditForm">
							   							<html:optionsCollection property="facultyMap" label="value" value="key"/>
							   						</logic:notEmpty>
												</c:otherwise>
												</c:choose>
						   					</html:select>
									</td>
								</tr>
								<tr>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span><bean:message key="knowledgepro.admin.template.Date"/>:</div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:text  property="examDate" styleId="examDate" size="10" maxlength="16"/>
                                    				<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#examDate").datepicker(pickerOpts);
														});
                                     				</script>
									</td>
									<td align="right" class="row-odd" width="25%">
										<div><span class="Mandatory"></span><bean:message key="knowledgepro.admin.session"/>:</div>
									</td>
									<td width="25%" height="25" class="row-even" align="left">
										 <html:select property="examSession" styleId="session" name="examInvigilatorDutyEditForm">
                    						<html:option value="">--Select--</html:option>
                    							<logic:notEmpty property="examSessionMap" name="examInvigilatorDutyEditForm">
						   							<html:optionsCollection property="examSessionMap" label="value" value="key"/>
						   						</logic:notEmpty>
						   					</html:select>
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

					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news" >
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="40%" height="35" align="center"></td>
							<td width="5%" height="35" align="center">
								<html:submit property="" styleClass="formbutton" value="Submit"	styleId="submitbutton">
								</html:submit>
							</td>
							<td width="1%" height="35" align="center">
							</td>
							<td width="5%" height="35" align="left">
								<html:button property="" value="Reset" styleClass="formbutton" onclick="resetValue()"></html:button>
							</td>
							<td width="50%" height="35" align="left">
									<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
							</td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
	<script type="text/javascript">
	document.getElementById("academicYear").value=document.getElementById("tempAcademicYear").value;
	</script>
</html:form>
