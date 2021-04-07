<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>

<script type="text/javascript">
var feeId;
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	resetOption("course");
//	resetOption("semister");
	resetOption("subjectgroup");
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);
//	resetOption("semister");
	resetOption("subjectgroup");
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}

function resetCoursesChilds() {
//	resetOption("semister");
	resetOption("subjectgroup");
}

function addFee() {
	document.getElementById("method").value = "initAddFeeAssignmentAccount";
	document.getElementById("programTypeName").value = document.getElementById("programtype").options[document.getElementById("programtype").selectedIndex].text;
	document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
	document.getElementById("programName").value = document.getElementById("program").options[document.getElementById("program").selectedIndex].text;
	document.feeAssignmentForm.submit();	
}

function updateFee() {
	document.getElementById("programTypeName").value = document.getElementById("programtype").options[document.getElementById("programtype").selectedIndex].text;
	document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
	document.getElementById("programName").value = document.getElementById("program").options[document.getElementById("program").selectedIndex].text;
	document.getElementById("method").value = "initEditFeeAssignmentAccount";
	document.feeAssignmentForm.submit();
}

function deleteFee(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href="FeeAssignment.do?method=deleteFeeAssignment&id="+id;
	}
}

function viewFee(id) {
	document.getElementById("programTypeName").value = document.getElementById("programtype").options[document.getElementById("programtype").selectedIndex].text;
	document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
	document.getElementById("programName").value = document.getElementById("program").options[document.getElementById("program").selectedIndex].text;
	var url ="FeeAssignment.do?method=viewFeeAssignment&id="+id;
	myRef = window.open(url,"viewFees","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}
function editFee(id) {
	document.location.href="FeeAssignment.do?method=editFeeAssignment&id="+id;
}

function getSemistersByCourse(courseId) {
	var academicYear = document.getElementById("academicYear").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",academicYear,updateSemisters);
	resetOption("subjectgroup");        
}

function getSemisters(year) {
	var courseId = document.getElementById("course").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",year,updateSemisters);
	resetOption("subjectgroup");        
}

function updateSemisters(req){
	updateOptionsFromMap(req,"semister","- Select -");
}

function resetDataErrMsgs()	{
	resetErrMsgs();
	var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("academicYear").value=year;
	}
}

function reActivate() {
	var id = document.getElementById("feeId").value;
	document.location.href="FeeAssignment.do?method=activateFeeAssignment&id="+id;
}

</script>

<html:form action="/FeeAssignment">

	<html:hidden property="method" styleId="method"
		value="addFeeAssignment" />
	<html:hidden property="formName" value="feeAssignmentForm" />
	<html:hidden property="programTypeName" styleId="programTypeName"
		value="" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	<html:hidden property="programName" styleId="programName" value="" />


	<html:hidden property="pageType" value="1" />
	<input type="hidden" id="feeId" name="id"
		value="<bean:write name="feeAssignmentForm" property="id"/>" />
	<!-- usefull while edit -->
	<input type="hidden" id="operation" name="operation"
		value="<c:out value='${feeOperation}'/>" />
	<c:set var="collectionMap" value="${baseActionForm.collectionMap}"
		scope="request" />
	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.fee" /><span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.fee.assignment" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.fee.assignment" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="185" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" class="body">
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
											<td width="24%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program.type" /></div>
											</td>
											<td width="24%" height="25" class="row-even" align="left"><label>
											<html:select property="programTypeId" styleId="programtype"
												styleClass="combo" onchange="getPrograms(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection name="programTypeList"
													label="programTypeName" value="programTypeId" />
											</html:select> </label></td>
											<td width="23%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.program.name" />:</div>
											</td>
											<td width="29%" class="row-even" align="left"><html:select
												property="programId" styleId="program" styleClass="combo"
												onchange="getCourses(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<c:choose>
													<c:when test="${feeOperation == 'edit'}">
														<html:optionsCollection name="programMap" label="value"
															value="key" />
													</c:when>
													<c:otherwise>
														<c:if
															test="${feeAssignmentForm.programTypeId != null && feeAssignmentForm.programTypeId != ''}">
															<c:set var="programMap"
																value="${baseActionForm.collectionMap['programMap']}" />
															<c:if test="${programMap != null}">
																<html:optionsCollection name="programMap" label="value"
																	value="key" />
															</c:if>
														</c:if>
													</c:otherwise>
												</c:choose>
											</html:select></td>
										</tr>
										<tr>
											<td height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.course" />:</div>
											</td>
											<td height="25" class="row-even" align="left"><html:select
												property="courseId" styleId="course" styleClass="combo"
												onchange="resetCoursesChilds()">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<c:choose>
													<c:when test="${feeOperation == 'edit'}">
														<html:optionsCollection name="courseMap" label="value"
															value="key" />
													</c:when>
													<c:otherwise>
														<c:if
															test="${feeAssignmentForm.programId != null && feeAssignmentForm.programId != ''}">
															<c:set var="courseMap"
																value="${baseActionForm.collectionMap['coursesMap']}" />
															<c:if test="${courseMap != null}">
																<html:optionsCollection name="courseMap" label="value"
																	value="key" />
															</c:if>
														</c:if>
													</c:otherwise>
												</c:choose>
											</html:select></td>
											<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.fee.academicyear" />:</div>
											</td>
											<td class="row-even" align="left"><input type="hidden"
												id="tempyear" name="tempyear"
												value="<bean:write name="feeAssignmentForm" property="academicYear"/>" />
											<html:select property="academicYear" styleId="academicYear"
												styleClass="combo">
												<html:option value="">- Select -</html:option>
												<cms:renderYear></cms:renderYear>
											</html:select></td>
										</tr>
										<tr>
											<td width="24%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.year.with.col" /></div>
											</td>
											<td width="24%" height="25" class="row-even" align="left"><label>
											<html:select property="semister" styleId="semister"
												styleClass="combo">
												<html:option value="">- Select -</html:option>
												<html:option value="1">1</html:option>
												<html:option value="2">2</html:option>
												<html:option value="3">3</html:option>

												<!--  <c:choose>
                       	   					 <c:when test="${feeOperation == 'edit'}">
                          				 		<html:optionsCollection name="semisterMap" label="value" value="key"/>
                         				     </c:when>
                         				     <c:otherwise>
                         				    	<c:if test="${feeAssignmentForm.academicYear != null && feeAssignmentForm.academicYear != ''}">
	                           						<c:set var="semistersMap" value="${baseActionForm.collectionMap['semistersMap']}"/>
                            		    			<c:if test="${semistersMap != null}">
                            		    				<html:optionsCollection name="semistersMap" label="value" value="key"/>
                            		    			</c:if>	 
	                           		   			</c:if>
	                           		   		</c:otherwise>
	                           		   		</c:choose> -->

											</html:select> </label></td>
											<td width="23%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.fee.aidedUnaided" />:</div>
											</td>
											<td width="29%" class="row-even" align="left"><html:select
												property="aidedUnAided" styleId="aidedUnAided"
												styleClass="combo">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:option value="Aided">Aided</html:option>
												<html:option value="Unaided">Unaided</html:option>
											</html:select></td>
										</tr>
										<tr>

											<td width="212" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.feeCategory" /></div>
											</td>
											<td width="236" height="25" class="row-even" align="left">
											<logic:notEmpty name="feeAssignmentForm"
												property="feeCategoryList">
												<html:select property="feeCategory"
													styleId="feeCategoryId" styleClass="combo">
													<html:option value="">- Select -</html:option>
													<html:optionsCollection property="feeCategoryList"
														label="feeCategoryName" value="feeCategoryId" />

												</html:select>
											</logic:notEmpty></td>
											<td width="212" height="25" class="row-odd"></td>
											<td width="236" height="25" class="row-even" align="left"></td>
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
						<tr>
							<td height="35" colspan="6" class="body">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td style="height: 10px" align="center" colspan="3"></td>
								</tr>
								<tr>
									<td width="45%">
									<div align="right"><c:choose>
										<c:when test="${feeOperation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="UpdateFee" onclick="updateFee();hideButton(this)"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Add Fee" onclick="addFee();hideButton(this)"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%" height="45" align="left"><c:choose>
										<c:when test="${feeOperation == 'edit'}">
											<html:reset property="" styleClass="formbutton" value="Reset"
												onclick="resetDataErrMsgs()"></html:reset>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
										</c:otherwise>
									</c:choose></td>
								</tr>
								<tr>
									<td style="height: 10px" align="center" colspan="3"></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6"><logic:notEmpty
								name="feeAssignmentForm" property="feeList">
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
												<td width="5%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.slno" /></div>
												</td>
												<td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.programtype" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.program" /></div>
												</td>
												<td width="14%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.course" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.fee.academicyear" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admission.appliedyear" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.fee.aidedUnaided" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.feeCategory" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.view" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.edit" /></div>
												</td>
												<td width="6%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.delete" /></div>
												</td>
											</tr>

											<c:set var="temp" value="0" />

											<logic:iterate id="fee" name="feeAssignmentForm"
												property="feeList" type="com.kp.cms.to.fee.FeeTO"
												indexId="count">
												<c:choose>
													<c:when test="${temp == 0}">
														<tr>
															<td height="25" class="row-even">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td height="25" class="row-even">
															<div align="center"><bean:write name="fee"
																property="programTypeTo.programTypeName" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="programTo.name" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="courseTo.code" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="year" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="semister" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="aidedUnaided" /></div>
															</td>
															<td class="row-even">
															<div align="center"><bean:write name="fee"
																property="feeCategory" /></div>
															</td>
															<td class="row-even">
															<div align="center"><img src="images/View_icon.gif"
																width="16" height="18" style="cursor: pointer"
																onclick="viewFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
															<td height="25" class="row-even">
															<div align="center"><img src="images/edit_icon.gif"
																width="16" style="cursor: pointer" height="18"
																onclick="editFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
															<td height="25" class="row-even">
															<div align="center"><img
																src="images/delete_icon.gif" width="16"
																style="cursor: pointer" height="16"
																onclick="deleteFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
														</tr>
														<c:set var="temp" value="1" />
													</c:when>
													<c:otherwise>
														<tr>
															<td height="25" class="row-white">
															<div align="center"><c:out value="${count + 1}" /></div>
															</td>
															<td height="25" class="row-white">
															<div align="center"><bean:write name="fee"
																property="programTypeTo.programTypeName" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="programTo.name" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="courseTo.code" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="year" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="semister" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="aidedUnaided" /></div>
															</td>
															<td class="row-white">
															<div align="center"><bean:write name="fee"
																property="feeCategory" /></div>
															</td>
															<td class="row-white">
															<div align="center">
															<div align="center"><img src="images/View_icon.gif"
																width="16" height="18" style="cursor: pointer"
																onclick="viewFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
															<td height="25" class="row-white">
															<div align="center"><img src="images/edit_icon.gif"
																width="16" style="cursor: pointer" height="18"
																onclick="editFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
															<td height="25" class="row-white">
															<div align="center"><img
																src="images/delete_icon.gif" width="16"
																style="cursor: pointer" height="16"
																onclick="deleteFee('<bean:write name="fee" property="id"/>')"></div>
															</td>
														</tr>
														<c:set var="temp" value="0" />
													</c:otherwise>
												</c:choose>
											</logic:iterate>

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
							</logic:notEmpty></td>
						</tr>

						<tr>
							<td height="10" colspan="6" class="body"></td>
						</tr>
					</table>
					</div>
					</td>
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
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
 
</script>