<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>


<script type="text/javascript" language="javascript"><!--
	function editElegSetUp(id) {
		document.location.href = "ExamExamResults.do?method=editExamExamResults&id="
				+ id;
	}

	function deleteElegSetUp(id, examName) {
		deleteConfirm = confirm("Are you sure to delete '" + examName
				+ "'  entry?");
		if (deleteConfirm) {
			document.location.href = "ExamExamResults.do?method=deleteExamExamResults&id="
					+ id;
		}
	}

	function moveoutid() {

		var mapFrom = document.getElementById('mapClass');
		var len = mapFrom.length;
		var mapTo = document.getElementById('selsubMap');
		if (mapTo.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}

		for ( var j = 0; j < len; j++) {
			if (mapFrom[j].selected) {

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

	function getClassValues() {
		var listClasses = new Array();
		var mapTo1 = document.getElementById('selsubMap');
		var len1 = mapTo1.length;
		for ( var k = 0; k < len1; k++) {
			listClasses.push(mapTo1[k].value);

		}
		document.getElementById("classValues").value = listClasses;

	}

	function moveinid() {
		var mapFrom = document.getElementById('mapClass');
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

	function getChkBoxValues() {
		var finalValue = "";
		var courseCount = document.getElementById("addEligibilityCount").value;

		for ( var i = 0; i < courseCount; i++) {

			if (document.getElementById("checkbox_" + i.checked == true)) {

				finalValue = finalValue + i + ",";
			}

		}

	}
	function resetAll() {
		resetErrMsgs();
		document.location.href = "ExamExamResults.do?method=initExamResults";

	}

	function updateClass(req) {
		updateInternalComponentsByClasses(req,
				"publishOverallInternalComponentsOnly");
	}

	function getInternalComponents(examName) {
		var mapTo = document.getElementById('selsubMap');
		var len = mapTo.length;
		for ( var j = 0; j <= len; j++) {
			mapTo.remove(j);

		}
		for ( var k = 0; k <= len; k++) {
			mapTo.remove(k);
		}
		for ( var l = 0; l <= len; l++) {
			mapTo.remove(l);
		}
		for ( var l = 0; l <= len; l++) {
			mapTo.remove(l);
		}
		for ( var l = 0; l <= len; l++) {
			mapTo.remove(l);
		}
		getClassCodeByExamName("mapClass", examName, "mapClass", updateCourses);

		if (examName != '') {
			var args = "method=getInternalComponentsByClasses&examName="
					+ examName;
			var url = "AjaxRequest.do";
			requestOperation(url, args, updateClass);
		}
	}

	function updateCourses(req) {
		updateOptionsFromMapMultiselect(req, "mapClass", "--Select--");
	}
	function getExamsByExamTypeAndYear() {
		var examType = document.getElementById("examType").value;
		var year = document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType, year, "examName",
				updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function getExamListResults() {
		document.getElementById("method").value = "getExamListByClick";
		document.ExamExamResultsForm.submit();
		//	var examType=document.getElementById("examType").value;
		//	var year=document.getElementById("year").value;
		//	document.location.href = "ExamExamResults.do?method=getExamListByClick&year="+year;
	}

	function getExamDetailsResults() {
		//document.getElementById("method").value = "getExamDetailsResults";
		//document.ExamExamResultsForm.submit();
		var examType = document.getElementById("examType").value;
		var year = document.getElementById("year").value;
		var examName = document.getElementById("examName").value;
		document.location.href = "ExamExamResults.do?method=getExamDetailsResults&year=" + year + " &extype=" + examType + " &examName=" + examName;
	}
	function getClassesMap1(){
		var examName= document.getElementById("examName").value;
 		if(examName==null || examName==''){
       		alert("Please select the exam name");
 		}
		resetOption1("selsubMap");
		resetOption1("mapClass");
		var examType= document.getElementById("examType").value;
		var programId=null;
		var deanaryId=document.getElementById("deanaryId").value;
		getClassMap("mapClass",examType,examName,programId, deanaryId, "mapClass", updateClasses);
		getList();
		}
	function updateClasses(req){
		updateOptionsFromMapForNonDefaultSelection(req,"mapClass");
		}
	function resetOption1(destinationOption) {
		var destination = document.getElementById(destinationOption);
		for (x1 = destination.options.length-1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
	}
</script>

</head>
<html:form action="/ExamExamResults.do">

	<html:hidden property="formName" value="ExamExamResultsForm" />
	<html:hidden property="classValues" styleId="classValues" />

	<c:choose>
		<c:when
			test="${ExamExamResultsOperation != null && ExamExamResultsOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateExamExamResults" />
			<html:hidden property="id" styleId="id" />
			<html:hidden property="className" styleId="className" />
			<html:hidden property="pageType" value="2" />

		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addExamExamResults" />
			<html:hidden property="pageType" value="1" />
		</c:otherwise>
	</c:choose>


	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.ExamResults" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.ExamResults" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield"><bean:message
						key="knowledgepro.mandatoryfields" /></div>
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
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div>
									</td>
									<td width="16%" class="row-even" valign="top"><input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamExamResultsForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo"
										onchange="getExamListResults(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.examDefinition.examType" /> :</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="exTypeId" name="exTypeName"
										value="<bean:write name="ExamExamResultsForm" property="extype"/>"/>
									<html:select property="extype" styleClass="combo" name="ExamExamResultsForm"
										styleId="examType" onchange="getExamListResults(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty property="examTypeList"
											name="ExamExamResultsForm">
											<html:optionsCollection property="examTypeList"
												name="ExamExamResultsForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamName" /> :</div>
									</td>
									<td height="25" class="row-even"><input type="hidden"
										id="ExnameId" name="Examname"
										value="<bean:write name="ExamExamResultsForm" property="exname"/>" />
									<html:select property="exname" styleClass="combo"
										styleId="examName" name="ExamExamResultsForm"
										style="width:200px"
										onchange="getExamDetailsResults(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="ExamExamResultsForm"
											property="examNameMap">
											<html:optionsCollection property="examNameMap"
												name="ExamExamResultsForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
									<c:choose>
										<c:when
											test="${ExamExamResultsOperation != null && ExamExamResultsOperation == 'edit'}">

											<script type="text/javascript" language="javascript">
	document.getElementById("examName").disabled = true;
</script>
											<html:hidden property="examName" />
										</c:when>
										<c:otherwise>
											<script type="text/javascript" language="javascript">
	document.getElementById("examName").disabled = false;
</script>
										</c:otherwise>
									</c:choose>


									<td width="21%" align="center" valign="middle" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamResults.publishDate" /> :</div>
									</td>
									<td width="16%" height="25" class="row-even"><html:text
										name="ExamExamResultsForm" property="publishDate"
										styleId="publishDate" size="10" maxlength="16" /> <script
										language="JavaScript">
	new tcal( {
		// form name
		'formname' : 'ExamExamResultsForm',
		// input name
		'controlname' : 'publishDate'
	});
</script></td>
								</tr>
								<tr>
								<td width="18%" class="row-odd">
										<div align="right">Deanery:</div>
									</td>
									<td class="row-even">
									<html:select name="ExamExamResultsForm" property="deanaryName" styleId="deanaryId" styleClass="combo" onchange="getClassesMap1()">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option> 
												<logic:notEmpty name="ExamExamResultsForm" property="deaneryMap">
													<html:optionsCollection property="deaneryMap" name="ExamExamResultsForm" label="value" value="key" />
												</logic:notEmpty>
									</html:select>
									</td>
									<td width="35%" height="25"  class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.class" /> :</div>
									</td>
									<!--<td width="28%" height="25"  class="row-even">
									<table border="0">
										<tr>
											<td width="112"><label> <nested:select
												property="courseIdsFrom" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">

												<c:if
													test="${ExamExamResultsForm.mapClass!=null && ExamExamResultsForm.mapClass.size!=0}">
													<nested:optionsCollection name="ExamExamResultsForm"
														property="mapClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
												


											</nested:select> </label></td>
											<td width="49"><c:choose>
												<c:when
													test="${ExamExamResultsOperation != null && ExamExamResultsOperation == 'edit'}">
													<table border="0">

														<tr>
															<td><input type="button" id="moveOut" value=">>"
																disabled="disabled"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;"
																id="moveIn" disabled="disabled"></td>
														</tr>
													</table>
												</c:when>
												<c:otherwise>
													<table border="0">

														<tr>
															<td><input type="button" onClick=
	moveoutid();
id="moveOut" value=">>"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;"
																id="moveIn" onclick=
	moveinid();
></td>
														</tr>
													</table>
												</c:otherwise>
											</c:choose></td>
											<td width="120"><label> <nested:select
												property="courseIdsTo" styleId="selsubMap" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${ExamExamResultsForm.mapSelectedClass!=null && ExamExamResultsForm.mapSelectedClass.size!=0}">
													<nested:optionsCollection name="ExamExamResultsForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> </label></td>
										</tr>
									</table>
									</td>

								-->
								<td width="28%" height="25"  class="row-even">
								<table border="0">
										<tr>
											<td width="112"><label> <nested:select
												property="courseIdsFrom" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">

												<c:if
													test="${ExamExamResultsForm.mapClass!=null && ExamExamResultsForm.mapClass.size!=0}">
													<nested:optionsCollection name="ExamExamResultsForm"
														property="mapClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> </label></td>
											<td width="49"><c:choose>
												<c:when
													test="${ExamExamResultsOperation != null && ExamExamResultsOperation == 'edit'}">
													<table border="0">
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
														<tr>
															<td><input type="button" id="moveOut" value=">>"
																disabled="disabled"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;"
																id="moveIn" disabled="disabled"></td>
														</tr>														
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>														
													</table>
													<script type="text/javascript" language="javascript">
			
                                                  document.getElementById("examName").disabled=true;
                                                  document.getElementById("examType").disabled=true;
                                                  document.getElementById("deanaryId").disabled=true;
                                                  </script>
                                              	<html:hidden property="examName"/>
												<html:hidden property="examType"/>
                                                  
												</c:when>
												<c:otherwise>
												<script type="text/javascript" language="javascript">
		
                                                  document.getElementById("examName").disabled=false;
                                                  document.getElementById("examType").disabled=false;
                                                  document.getElementById("deanaryId").disabled=false;
                                                  </script>
													<table border="0">
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
														<tr> 
															<td><input type="button" onClick=moveoutid();
																	id="moveOut" value=">>"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;"
																id="moveIn" onclick=moveinid();></td>
														</tr>														
														<tr><td style="height: 10px" align="center" colspan="1"></td></tr>														
													</table>
												</c:otherwise>
											</c:choose></td>
											<td width="120"><label> <nested:select
												property="courseIdsTo" styleId="selsubMap" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${ExamExamResultsForm.mapSelectedClass!=null && ExamExamResultsForm.mapSelectedClass.size!=0}">
													<nested:optionsCollection name="ExamExamResultsForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> </label></td>
										</tr>
									</table>
								</td>
								</tr>
								<tr>
									<td class="row-odd" colspan="2">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamResults.publishComponents" /></div>
									</td>
									<td class="row-even" colspan="2"><logic:equal
										name="ExamExamResultsForm" property="internalComponents"
										value="0">
										<logic:equal name="ExamExamResultsForm"
											property="publishOverallInternalComponentsOnly" value="on">
											<html:checkbox
												property="publishOverallInternalComponentsOnly"
												disabled="true"
												styleId="publishOverallInternalComponentsOnly" value="on" />

										</logic:equal>
										<logic:equal name="ExamExamResultsForm"
											property="publishOverallInternalComponentsOnly" value="off">
											<html:checkbox
												property="publishOverallInternalComponentsOnly"
												disabled="true"
												styleId="publishOverallInternalComponentsOnly" />
										</logic:equal>
									</logic:equal> <logic:notEqual name="ExamExamResultsForm"
										property="internalComponents" value="0">
										<logic:equal name="ExamExamResultsForm"
											property="publishOverallInternalComponentsOnly" value="on">
											<html:checkbox
												property="publishOverallInternalComponentsOnly"
												disabled="false"
												styleId="publishOverallInternalComponentsOnly" value="on" />

										</logic:equal>
										<logic:equal name="ExamExamResultsForm"
											property="publishOverallInternalComponentsOnly" value="off">
											<html:checkbox
												property="publishOverallInternalComponentsOnly"
												disabled="false"
												styleId="publishOverallInternalComponentsOnly" />
										</logic:equal>
									</logic:notEqual></td>
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
							<td width="49%" height="35" align="right"><c:choose>
								<c:when
									test="${ExamExamResultsOperation != null && ExamExamResultsOperation == 'edit'}">
									<input type="Submit" class="formbutton" value="Update" />


								</c:when>
								<c:otherwise>
									<input type="Submit" class="formbutton" value="Publish"
										onclick=
	getClassValues();
/>
								</c:otherwise>
							</c:choose></td>
							<td width="2%" height="35" align="center">&nbsp;</td>
							<td width="49%" align="left"><input name="Submit3"
								type="button" class="formbutton" value="Cancel"
								onclick=
	resetAll();
/></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>						
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
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td width="16%" height="25" class="row-odd"><bean:message
										key="knowledgepro.exam.ExamName" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.examEligibilitySetUp.class" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.ExamResults.publishDate" /></td>
									<td class="row-odd"><bean:message
										key="knowledgepro.exam.ExamResults.publishComponents" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>

								<logic:notEmpty name="ExamExamResultsForm"
									property="listExamResult">
									<logic:iterate name="ExamExamResultsForm"
										property="listExamResult" id="listExamResult" indexId="count"
										type="com.kp.cms.to.exam.ExamExamResultsTO">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td class="bodytext">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="left" class="bodytext"><bean:write
											name="listExamResult" property="examName" /></td>
										<td align="left" class="bodytext"><bean:write
											name="listExamResult" property="className" /></td>
										<td align="left" class="bodytext"><bean:write
											name="listExamResult" property="publishDate" /></td>
										<td align="center" class="bodytext"><bean:write
											name="listExamResult"
											property="publishOverallInternalComponentsOnly" /></td>
										<td align="center" class="bodytext">
										<div align="center"><img src="images/edit_icon.gif"
											height="18" style="cursor: pointer"
											onclick="editElegSetUp('<bean:write name="listExamResult" property="id"/>')"></div>
										</td>

										<td align="center" class="bodytext">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor: pointer"
											onclick="deleteElegSetUp('<bean:write name="listExamResult" property="id"/>','<bean:write name="listExamResult" property="examName" />')"></div>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
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
	var exam = document.getElementById("ExnameId").value;
	if (exam.length != 0) {
		document.getElementById("examName").value = exam;
	}
	var examT = document.getElementById("exTypeId").value;
	if (examT.length != 0) {
		document.getElementById("examType").value = examT;
	}
</script>


