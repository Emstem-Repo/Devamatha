<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	
	function getExamName(examType) {
		var year = document.getElementById("year").value;
		getExamNameByExamTypeYearWise("examMap", examType,year, "examName", updateExamName);
		resetOption("subject");
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	<%-- code added by sudhir--%>
	function getExamNameByYear(year) {
		getExamByYearOnlyInternal("examMap", year, "examName", updateExams);
	}
	function updateExams(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("examName");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1]=null;
		}
		destination.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("option");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination.options[j+1] = new Option(label,value);
		 }
	}<%-- code added by sudhir ends here--%>
	function cancelAction() {
		<%--document.getElementById('selectColumn').innerHTML=null;--%>
		resetFieldAndErrMsgs();
	}

	function editExam(examId){
		document.location.href = "InternalExamEntry.do?method=editExam&id="+examId;
	}
	function deleteExam(examId){
		document.location.href = "InternalExamEntry.do?method=deleteExam&id="+examId;
	}
	function editMethod(){
		document.location.href = "InternalExamEntry.do?method=editExam"
	}
	function getCourses(programId) {
		document.getElementById("selsubMap").innerHTML=null;
		var year = document.getElementById("year").value;
		getClassesForProgram(programId,year,updateClasses);	
	}
	function getClassesMap(programId){
		resetOption1("selsubMap");
		resetOption1("classes");
		var examId= document.getElementById("examName").value;
		args =  "method=getClassesByProgramIdAndExamId&newProgramTypeId="+programId+"&examId="+examId;
		var url = "InternalExamEntry.do";
		requestOperationProgram(url, args, updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMapNew(req,"classes","- Select -");
	}
	function resetOption1(destinationOption) {
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length-1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
	}	
	function moveoutid() {
		var mapFrom = document.getElementById('classes');
		var len = mapFrom.length;
		var mapTo = document.getElementById('selsubMap');

		if (mapTo.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}
		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

				//listClasses.push(mapFrom[j].value);
				var tmp = mapFrom.options[j].text;
				var tmp1 = mapFrom.options[j].value;
				mapFrom.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if (mapFrom.length <= 0)
					document.getElementById("moveOut").disabled = true;
				else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');

				y.text = tmp;
				y.value = tmp1;
				y.setAttribute("class", "comboBig");
				try {
					mapTo.add(y, null);
				} catch (ex) {
					mapTo.add(y);
				}
			}
		}
	}

	function moveinid() {
		var mapFrom = document.getElementById('classes');
		var mapTo = document.getElementById('selsubMap');
		var len = mapTo.length;

		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;
				if (j < 0) {
					document.getElementById("moveIn").disabled = true;
					document.getElementById("moveOut").disabled = false;
				}
				if (mapTo.length != 0) {
					document.getElementById("moveOut").disabled = false;
					document.getElementById("moveIn").disabled = false;
				} else
					document.getElementById("moveOut").disabled = false;
				var y = document.createElement('option');
				y.setAttribute("class", "comboBig");
				y.text = tmp;
				y.value = tmp1;
				try {
					mapFrom.add(y, null);
				} catch (ex) {
					mapFrom.add(y);
				}
			}
		}

	}
	function getClassValues() {
		var listClasses = new Array();
		var mapTo1 = document.getElementById('selsubMap');
		var len1 = mapTo1.length;
		for ( var k = 0; k < len1; k++) {
			listClasses.push(mapTo1[k].value);
		}
		document.getElementById("stayClass").value = listClasses;
	}
</SCRIPT>

<html:form action="/InternalExamEntry" onsubmit="getClassValues()">
	<html:hidden property="formName" value="openInternalExamForm" styleId="formName" />
	<html:hidden property="stayClass" styleId="stayClass" />
	<c:choose>
		<c:when test="${operation != null && operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveDetails" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt;Internal Exam Entry &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Internal Exam Entry</td>
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
									<td class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
		                            <td class="row-even" align="left">
		                            <input type="hidden" id="tempyear" name="tempyear" 	value="<bean:write name="openInternalExamForm" property="academicYear"/>" />
		                            <html:select property="academicYear" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderAcademicYear></cms:renderAcademicYear>
	                     			   		</html:select>
		                            </td>
									<td class="row-odd" >
									<div align="right"><span class="Mandatory">*</span>
									Exam Name :</div>
									</td>

									<td class="row-even"><html:select
										name="openInternalExamForm" property="examId"
										styleId="examName" styleClass="comboExtraLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
											<logic:notEmpty name="openInternalExamForm"
												property="examMap">
												<html:optionsCollection property="examMap"
													name="openInternalExamForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									 <td height="25" class="row-odd">
										<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
									</td>
									<td class="row-even">
											<html:text name="openInternalExamForm" property="startDate" styleId="startDate" size="11" maxlength="11" />
												<script language="JavaScript">
														new tcal( {
															// form name
															'formname' :'openInternalExamForm',
															// input name
															'controlname' :'startDate'
														});
												</script>
									</td>
									<td height="25" class="row-odd">
									 	<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
									</td>
									<td height="25" class="row-even">
										<html:text property="endDate" styleId="endDate" size="11" maxlength="11" ></html:text>
										<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'openInternalExamForm',
													// input name
													'controlname' :'endDate'
													});
										</script>
									</td>
								</tr>
								<tr>
									<td class="row-odd" align="right">
										<span class="Mandatory">*</span> Open For:
									</td>
									<td class="row-even" ><html:select
												property="theoryPractical" styleId="theoryPractical" name="openInternalExamForm">
												<html:option value="">Select</html:option>
												<html:option value="T">Theory</html:option>
												<html:option value="P">Practical</html:option>
												<html:option value="B">Both</html:option>
									</html:select></td>
									<td class="row-odd" align="right"><span class="Mandatory">*</span> Program Type: </td>
									<td class="row-even" ><html:select
												property="newProgramTypeId" styleId="programTypeId" name="openInternalExamForm" styleClass="combo" onchange="getClassesMap(this.value)">
												<html:option value="">Select</html:option>
												<logic:notEmpty property="programTypeMap" name="openInternalExamForm">
													<html:optionsCollection property="programTypeMap" name="openInternalExamForm" value="key" label="value"/>
												</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
										<td class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;Classes:</div>
										</td>
										<td height="25" class="row-even">
											<table border="0">
												<tr>
													<td width="112"><label>
														<nested:select	property="classes" styleClass="body" multiple="multiple" size="8" styleId="classes" style="width:200px">
															<logic:notEmpty name="openInternalExamForm" property="classesMap">
																	<nested:optionsCollection name="openInternalExamForm" property="classesMap" label="value" value="key" styleClass="comboBig" />
															</logic:notEmpty>
														</nested:select> </label>
													</td>
													<td width="49"><c:choose>
														<c:when	test="${operation != null && operation == 'edit'}">
															<table border="0">
																<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
																<tr>
																	<td>
																		<input type="button" id="moveOut" onClick="moveoutid()" value="&gt;&gt;" >
																	</td>
																</tr>
																<tr>
																	<td><input type="button" value="<<" id="moveIn" onclick="moveinid()"></td>
																</tr>																
																<tr><td style="height: 10px" align="center" colspan="1"></td></tr>																
															</table>
														</c:when>
														<c:otherwise>
															<table border="0">
																<tr><td style="height: 10px" align="center" colspan="1"></td></tr>																
																<tr>
																	<td>
																		<input type="button" onClick="moveoutid()" id="moveOut" value="&gt;&gt;">
																	</td>
																</tr>
																<tr>
																	<td><input type="button" value="<<" id="moveIn" onclick="moveinid()"></td>
																</tr>																
																<tr><td style="height: 10px" align="center" colspan="1"></td></tr>																
															</table>
														</c:otherwise>
													</c:choose>
												</td>
												<td width="120"><label> 
													<nested:select property="selClasses" styleId="selsubMap" styleClass="body" multiple="multiple" size="8"	style="width:200px;">
														<logic:notEmpty name="openInternalExamForm" property="selClassesMap">
															<nested:optionsCollection name="openInternalExamForm"	property="selClassesMap" label="value" value="key" styleClass="comboBig" />
														</logic:notEmpty>
													</nested:select> </label></td>
												</tr>
											</table>
										</td>
										<td class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Display Exam Name:</div></td>
										<td class="row-even"><html:text property="displayName" name="openInternalExamForm" styleId="displayNameId" maxlength="20" size="15"></html:text></td>
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
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
						<tr>
							<td width="20%" height="35" align="center">
							<c:choose>
								<c:when test="${operation != null && operation == 'edit'}">
									<html:submit value="Update" styleClass="formbutton"></html:submit>
								</c:when>
								<c:otherwise>
									<html:submit value="Submit" styleClass="formbutton"></html:submit>
								</c:otherwise>
							</c:choose>	
										&nbsp;&nbsp;
							<c:choose>
								<c:when test="${operation != null && operation == 'edit'}">
									<input type="button" class="formbutton" value="Reset" onclick="editMethod()" />
								</c:when>
								<c:otherwise>
									<input type="button" class="formbutton" value="Reset" onclick="cancelAction()" />
								</c:otherwise>
							</c:choose>	
										
							
							</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>						
					</table>
					</td>
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>

									<td class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Exam Name</td>
									<td height="25" class="row-odd">Start Date</td>
									<td class="row-odd">End Date</td>
									<td class="row-odd">Open For</td>
									<td class="row-odd">Program Type</td>
									<td class="row-odd">Display Exam Name</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty  property="examList" name="openInternalExamForm">
								<nested:iterate property="examList" id="to" name="openInternalExamForm" indexId="count">
									<tr>
										<td width="5%" class="row-even">
										<div align="center"><c:out value="${count+1}"></c:out></div>
										</td>
										<td width="15%" class="row-even"><nested:write
											property="examName" /></td>
										<td width="15%" class="row-even"><nested:write
											property="startDate" /></td>
										<td width="15%" class="row-even"><nested:write
											property="endDate" /></td>
										<td width="10%" class="row-even"><nested:write
											property="theoryPractical" /></td>
										<td width="10%" class="row-even"><nested:write
											property="programType" /></td>
										<td width="10%" class="row-even"><nested:write
											property="displayExamName" /></td>
										<td width="10%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editExam('<bean:write name="to" property="id"/>')">
												</div>
										</td>
										<td width="9%" height="25" class="row-even" align="center">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor:pointer" 
												onclick="deleteExam('<bean:write name="to" property="id"/>')">
											</div>
										</td>
									</tr>
								</nested:iterate>
								</logic:notEmpty>
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
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>
