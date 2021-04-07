<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<link rel="stylesheet" href="calendar.css">
<script type="text/javascript">
	function getExamName(examType) {

		getExamNameByExamTypeId("examMap", examType, "examName", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
	}

	function getClasses(selsubMap) {

		if (programTypeId.length > 0) {
			getProgramsByType("mapClass", selsubMap, "selsubMap",
					updateClasses);
			resetOption("mapClass");
		} else {
			var destination = document.getElementById("selsubMap");
			for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
				destination.options[x1] = null;
			}
			resetOption("mapClass");
			resetOption("selsubMap");
		}
	}

	function resetValue() {
		var destination = document.getElementById("mapClass");
		for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
		var destination = document.getElementById("selsubMap");
		for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
			destination.options[x1] = null;
		}
		resetFieldAndErrMsgs();
		//document.location.href = "ExamPublishHallTicket.do?method=initExamPublishHallTicket";
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
	function getClassValues() {
		var listClasses = new Array();
		var mapTo1 = document.getElementById('selsubMap');
		var len1 = mapTo1.length;
		for ( var k = 0; k < len1; k++) {
			listClasses.push(mapTo1[k].value);
		}
		document.getElementById("stayClass").value = listClasses;
	}

	function getClass(examName) {
		var mapTo = document.getElementById('selsubMap');

	
		var len = mapTo.length;
		for ( var j = 0; j < len; j++) {
			if (mapTo[j].selected) {
				var tmp = mapTo.options[j].text;
				var tmp1 = mapTo.options[j].value;
				mapTo.remove(j);
				len--;
				j--;

			}
		}
		getClassCodeByExamNameWithYear("mapClass", examName, "mapClass",
				updateClass);
		
	
	}

	function updateClass(req) {
		updateOptionsFromMapMultiselect(req, "mapClass", "");

	}

	function getClassVal() {

		var listClasses1 = new Array();
		var mapTo1 = document.getElementById('selsubMap');

		for ( var k = 0; k < mapTo1.length; k++) {
			if (mapTo1.options[k].selected) {

				listClasses1.push(mapTo1[k].value);
			}

		}

		return listClasses1;
	}
	function getAgreementName(programId) {

		getAgreementNameByProgramTypeId("mapAgree", programId, "agreementName",
				updateAgree);

	}
	function updateAgree(req) {
		updateOptionsFromMap(req, "agreementName", "- Select -");
	}

	function getFooterName(programId) {

		getFooterNameByProgramTypeId("mapFooter", programId, "footerName",
				updateFooter);
	}
	function updateFooter(req) {
		updateOptionsFromMap(req, "footerName", "- Select -");
	}

	function editAttMaster(id) {

		document.location.href = "ExamPublishHallTicket.do?method=editPublishHallTicket&id="
				+ id;
		document.getElementById("submit").value = "Update";

		//resetErrMsgs();
	}

	function deleteAttMaster(id) {
		deleteConfirm = confirm("already published... Still wish to delete?");
		if (deleteConfirm) {
			document.location.href = "ExamPublishHallTicket.do?method=deletePublishHallTicket&id="
					+ id;
		}
	}

	function setListToForm(programTypeId){
		var examType= document.getElementById("examType").value;
		var examName= document.getElementById("examName").value;
		var year=document.getElementById("year").value;
		var deanaryId=document.getElementById("deanaryId").value;
		var publishFor=document.getElementById("publishFor").value;
		document.location.href="ExamPublishHallTicket.do?method=setListByExamName&examType="+examType+"&examName="+examName+"&programTypeId="+programTypeId+"&year="+year+"&deanaryName="+deanaryId+"&publishFor="+publishFor;
	}
	function getExamsByExamTypeAndYear() {
		resetOption1("selsubMap");
		resetOption1("mapClass");
		document.getElementById("deanaryId").value="";
		document.getElementById("programTypeId").value="";
		var examType=document.getElementById("examType").value;
		var year=document.getElementById("year").value;
		getExamNameByExamTypeAndYear("examMap", examType,year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function changeMethod1(){
		document.getElementById("method").value="update";
		}
	function changeMethod2(){
		document.getElementById("method").value="add";
	}
	function getList1(){
		resetOption1("selsubMap");
		resetOption1("mapClass");
		document.getElementById("deanaryId").value="";
		document.getElementById("programTypeId").value="";
		getList();
		}
	function getList(){
		document.getElementById("hDetails").innerHTML = "";
		var publishFor=document.getElementById("publishFor").value;
		var examName= document.getElementById("examName").value;
		var examType= document.getElementById("examType").value;
		var deanaryName=document.getElementById("deanaryId").value;
		var id=document.getElementById("id").value;
		if(examName!=null && examName!=""){
			var url = "ExamPublishHallTicket.do";
			var args = "method=getTheGridList&examType="+examType+"&examName="+examName+"&publishFor="+publishFor+"&deanaryName="+deanaryName+"&id="+id;
			requestOperationProgram(url, args, displayList);
			}
		}
	function displayList(req){
		var responseObj = req.responseXML.documentElement;
		var TList = responseObj.getElementsByTagName("TList");
		var htm="<tr><td class='heading'>";
		htm=htm+"<table width='98%' border='0' align='center' cellpadding='0' cellspacing='0'><tr>";
		htm=htm+"<td><img src='images/01.gif' width='5' height='5'></td><td width='914' background='images/02.gif'></td>";
		htm=htm+"<td><img src='images/03.gif' width='5' height='5'></td></tr><tr><td width='5' background='images/left.gif'></td>";
		htm=htm+"<td height='54' valign='top'><table width='98%' cellspacing='1' cellpadding='2'><tr>";
		htm=htm+"<td width='37' height='25' class='row-odd'><div align='center'><bean:message key='knowledgepro.hostel.absentees.slno' /></div></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.ExamMarksEntry.Students.classes.Exam.Type' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.studentEligibilityEntry.classCode' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.ExamName' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.ExamMarksEntry.Students.classes.publishType' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.hostel.adminmessage.startDate' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.hostel.adminmessage.enddate' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.ExamMarksEntry.Students.classes.revaluationEndDate' /></td>";
		//htm=htm+"<td class='row-odd'>Extended Date</td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.edit' /></td>";
		htm=htm+"<td class='row-odd'><bean:message key='knowledgepro.exam.delete' /></td></tr>";
		for ( var I = 0; I < TList.length; I++) {
			if(TList[I]!=null){
				var EType = TList[I].getElementsByTagName("EType")[0].firstChild.nodeValue;
				var CCode= TList[I].getElementsByTagName("CCode")[0].firstChild.nodeValue;
				var EName = TList[I].getElementsByTagName("EName")[0].firstChild.nodeValue;
				var PType = TList[I].getElementsByTagName("PType")[0].firstChild.nodeValue;
				var SDate= TList[I].getElementsByTagName("SDate")[0].firstChild.nodeValue;
				var EDate = TList[I].getElementsByTagName("EDate")[0].firstChild.nodeValue;
				var RDate = TList[I].getElementsByTagName("RDate")[0].firstChild.nodeValue;
				//var ExDate = TList[I].getElementsByTagName("ExDate")[0].firstChild.nodeValue;
				var id = TList[I].getElementsByTagName("id")[0].firstChild.nodeValue;
				htm=htm+"<tr><td height='25' class='row-even'><div align='center'>"+(parseInt(I)+1)+"</div></td>";
				htm=htm+"<td height='25' class='row-even'>"+EType+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+CCode+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+EName+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+PType+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+SDate+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+EDate+"</td>";
				htm=htm+"<td height='25' class='row-even'>"+RDate+"</td>";
				//htm=htm+"<td height='25' class='row-even'>"+ExDate+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'><div align='center'>";
				htm=htm+"<img src='images/edit_icon.gif' height='18' style='cursor:pointer'"; 
				htm=htm+"onclick=";
				htm=htm+"editAttMaster("+parseInt(id)+")";
				htm=htm+"></div></td>";
				htm=htm+"<td  height='25' class='row-even' align='center'><div align='center'>";
				htm=htm+"<img src='images/delete_icon.gif' width='16' height='18' style='cursor:pointer'"; 
				htm=htm+"onclick=";
				htm=htm+"deleteAttMaster("+parseInt(id)+")";
				htm=htm+"></div></td></tr>";
				
			}
		}
		htm=htm+"</table></td><td background='images/right.gif' width='5' height='54'></td></tr>";
		htm=htm+"<tr><td height='5'><img src='images/04.gif' width='5' height='5'></td><td background='images/05.gif'></td>";
		htm=htm+"<td><img src='images/06.gif'></td></tr></table></td><td valign='top' background='images/Tright_3_3.gif'></td></tr>";
		document.getElementById("hDetails").innerHTML = htm;
		}
	function getClassesMap1(){
		resetOption1("selsubMap");
		resetOption1("mapClass");
		var examType= document.getElementById("examType").value;
		var examName= document.getElementById("examName").value;
		var programId=document.getElementById("programTypeId").value;
		var deanaryId=document.getElementById("deanaryId").value;
		getClassMap("mapClass",examType,examName,programId, deanaryId, "mapClass", updateClasses);
		getList();
		}
	function getClassesMap(){
		resetOption1("selsubMap");
		resetOption1("mapClass");
		var examType= document.getElementById("examType").value;
		var examName= document.getElementById("examName").value;
		var programId=document.getElementById("programTypeId").value;
		var deanaryId=document.getElementById("deanaryId").value;
		getAgreementName(programId);
		getFooterName(programId);
		getClassMap("mapClass",examType,examName,programId, deanaryId, "mapClass", updateClasses);
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

<html:form action="/ExamPublishHallTicket.do" onsubmit="getClassValues()">
	<html:hidden property="formName" styleId="formName" value="ExamPublishHallTicketForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="add" />
	<html:hidden property="stayClass" styleId="stayClass" />
	<html:hidden property="id" styleId="id" />
	<table width="99%" border="0">

		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><bean:message
				key="knowledgepro.exam" /> &gt;&gt; <bean:message
				key="knowledgepro.exam.publishHallTicket" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.exam.publishHallTicket" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
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
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="ExamPublishHallTicketForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.publishFor" />:</div>
									</td>
									<td colspan="3" class="row-even"><html:select
										property="publishFor" styleId="publishFor" onchange="getList1()">
										<html:option value="">-Select-</html:option>
										<html:option value="Hall Ticket">Hall Ticket</html:option>
										<html:option value="Marks Card">Marks Card</html:option>
										<html:option value="Marks Entry">Marks Entry</html:option>
										<html:option value="Regular Application">Regular Application</html:option>
										<html:option value="Revaluation/Scrutiny">Revaluation/Scrutiny</html:option>
										<html:option value="Grievance Form">Grievance Form</html:option>
										<html:option value="Consolidate MarksCard">Consolidate MarksCard</html:option>
									    <html:option value="Internal Grievance Form">Internal Grievance Form</html:option>
									    <html:option value="Internal Redo">Internal Redo</html:option>
									    <html:option value="Internal Marks Entry">Internal Marks Entry</html:option>
									</html:select></td>
							</tr>
								<tr>

									<td width="22%" class="row-odd">

									<div align="right"><span class="mandatoryfield">*</span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.Exam.Type" />:</div>
									</td>
									<td width="34%" class="row-even"><html:select
										property="examType" styleClass="combo" styleId="examType"
										onchange="getExamsByExamTypeAndYear()">
									 	<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option> 
										<logic:notEmpty name="ExamPublishHallTicketForm"
											property="listExamtype">
											<html:optionsCollection property="listExamtype"
												name="ExamPublishHallTicketForm" label="display" value="id" />
										</logic:notEmpty>
									</html:select></td>

									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamName" /> :</div>
									</td>
									<td height="25" class="row-even">
									<html:select name="ExamPublishHallTicketForm" property="examName" styleId="examName" styleClass="combo" onchange="getList1()">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option> 
										<logic:notEmpty name="ExamPublishHallTicketForm"
											property="examNameMap">
										<html:optionsCollection property="examNameMap"
														name="ExamPublishHallTicketForm" label="value"
														value="key" />
														</logic:notEmpty>
									</html:select></td>
								</tr>

								<tr>
									<td width="18%" class="row-odd">
										<div align="right">Deanery:</div>
										<div align="right"><span class="mandatoryfield">*</span><bean:message
											key="knowledgepro.petticash.programType" />:</div>
									</td>
									<td class="row-even">
										<html:select name="ExamPublishHallTicketForm" property="deanaryName" styleId="deanaryId" styleClass="combo" onchange="getClassesMap1()">
											<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option> 
											<logic:notEmpty name="ExamPublishHallTicketForm" property="deaneryMap">
												<html:optionsCollection property="deaneryMap" name="ExamPublishHallTicketForm" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
										<html:select property="programTypeId"
											styleClass="combo" styleId="programTypeId"
											name="ExamPublishHallTicketForm" style="width:200px"
											onchange="getClassesMap()">
											<html:option value="">
												<bean:message key="knowledgepro.admin.select" />
											</html:option>
											<logic:notEmpty name="ExamPublishHallTicketForm"
												property="programTypeList">
												<html:optionsCollection property="programTypeList"
													name="ExamPublishHallTicketForm" label="display" value="id" />
											</logic:notEmpty>
										</html:select>
									</td>

									<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.studentEligibilityEntry.classCode" />
									:</div>
									</td>
									<td height="25" class="row-even">

									<table border="0">
										<tr>
											<td width="112"><label>
											 <nested:select property="classCodeIdsFrom" styleClass="body" multiple="multiple" size="8" styleId="mapClass" style="width:200px">
												<c:choose>
						             					<c:when test="${mapClass != null}">
						             					<html:optionsCollection name="mapClass" label="value" value="key" />
														</c:when>
														<c:otherwise>
															<logic:notEmpty name="ExamPublishHallTicketForm"
																	property="mapClass" >
																	<nested:optionsCollection name="ExamPublishHallTicketForm"
																		property="mapClass" label="value" value="key"
																		styleClass="comboBig" />
																</logic:notEmpty>
														</c:otherwise>
													</c:choose>
												
												

										<!-- 		<c:if test="${editPublishHallTicket == 'edit'}">
													<logic:notEmpty name="ExamPublishHallTicketForm"
														property="mapClass">
														<nested:optionsCollection name="ExamPublishHallTicketForm"
															property="mapClass" label="value" value="key"
															styleClass="comboBig" />
													</logic:notEmpty>
												</c:if>
												<c:if test="${editPublishHallTicket == 'add'}">
													<logic:notEmpty name="ExamPublishHallTicketForm"
														property="mapClass">
														<nested:optionsCollection name="ExamPublishHallTicketForm"
															property="mapClass" label="value" value="key"
															styleClass="comboBig" />
													</logic:notEmpty>
												</c:if>	

												<c:if
													test="${ExamPublishHallTicketForm.examName != null && ExamPublishHallTicketForm.examName != ''}">
													<c:set var="mapClass"
														value="${baseActionForm.collectionMap['mapClass']}" />
													<c:if test="${mapClass != null}">
														<nested:optionsCollection name="ExamPublishHallTicketForm"
															property="mapClass" label="value" value="key"/>
													</c:if>
												</c:if>
 -->

											</nested:select> </label></td>
											<td width="49"><c:choose>
												<c:when
													test="${editPublishHallTicket != null && editPublishHallTicket == 'edit'}">
												<table border="0">

														<tr>
															<td><input type="button" 
																id="moveOut" value="&gt;&gt;" disabled="disabled"></td>
														</tr>
														<tr>
															&nbsp;&nbsp;&nbsp;
															<td><input type="button" value="<<" id="moveIn"  disabled="disabled"></td>
														</tr>
													</table>
                                                       
                                                    <script type="text/javascript" language="javascript">
			
                                                  document.getElementById("examName").disabled=true;
                                                  document.getElementById("examType").disabled=true;
                                                  document.getElementById("mapClass").disabled=true;
                                                  document.getElementById("programTypeId").disabled=true;
                                                  document.getElementById("deanaryId").disabled=true;
                                                  </script>

													<html:hidden property="examName"/>
												<html:hidden property="examType"/>
												<html:hidden property="programTypeId"/>
                                                     
												</c:when>
												<c:otherwise>
                                    		    <script type="text/javascript" language="javascript">
		
                                                  document.getElementById("examName").disabled=false;
                                                  document.getElementById("examType").disabled=false;
                                                  document.getElementById("mapClass").disabled=false;
                                                  document.getElementById("programTypeId").disabled=false;
                                                  document.getElementById("deanaryId").disabled=false;
                                                  </script>
													<table border="0">

														<tr>
															<td><input type="button" onClick="moveoutid()"
																id="moveOut" value="&gt;&gt;"></td>
														</tr>
														<tr>
															&nbsp;&nbsp;&nbsp;
															<td><input type="button" value="<<" id="moveIn" onclick="moveinid()"></td>
														</tr>
													</table>
												</c:otherwise>
											</c:choose></td>
											<td width="120"><label> <nested:select
												property="classCodeIdsTo" styleId="selsubMap"
												styleClass="body" multiple="multiple" size="8"
												style="width:200px;">



												<logic:notEmpty name="ExamPublishHallTicketForm"
													property="mapSelectedClass">
													<nested:optionsCollection name="ExamPublishHallTicketForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</logic:notEmpty>



											</nested:select> </label></td>
										</tr>
									</table>

									</td>
								</tr>

								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.agrementName" />:</div>
									</td>
									<td width="26%" class="row-even"><nested:select
										property="agreementName" styleId="agreementName"
										styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${editPublishHallTicket == 'edit'}">
												<logic:notEmpty name="ExamPublishHallTicketForm"
													property="agreementList">
													<html:optionsCollection name="ExamPublishHallTicketForm"
														property="agreementList" label="value" value="key" />
												</logic:notEmpty>
											</c:when>

											<c:when test="${editPublishHallTicket == 'add'}">
												<logic:notEmpty name="ExamPublishHallTicketForm"
													property="agreementList">
													<html:optionsCollection name="ExamPublishHallTicketForm"
														property="agreementList" label="value" value="key" />
												</logic:notEmpty>
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamPublishHallTicketForm.programTypeId != null && ExamPublishHallTicketForm.programTypeId != ''}">
													<c:set var="mapAgree"
														value="${baseActionForm.collectionMap['mapAgree']}" />
													<c:if test="${mapAgree != null}">
														<nested:optionsCollection property="mapAgree"
															label="value" value="key" />
													</c:if>

												</c:if>
											</c:otherwise>
										</c:choose>
									</nested:select></td>

									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.footerName" />:</div>
									</td>
									<td width="26%" class="row-even"><nested:select
										property="footerName" styleId="footerName" styleClass="combo">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${editPublishHallTicket == 'edit'}">
												<logic:notEmpty name="ExamPublishHallTicketForm"
													property="footerList">
													<html:optionsCollection name="ExamPublishHallTicketForm"
														property="footerList" label="value" value="key" />
												</logic:notEmpty>
											</c:when>

											<c:when test="${editPublishHallTicket == 'add'}">
												<logic:notEmpty name="ExamPublishHallTicketForm"
													property="footerList">
													<html:optionsCollection name="ExamPublishHallTicketForm"
														property="footerList" label="value" value="key" />
												</logic:notEmpty>
											</c:when>
											<c:otherwise>
												<c:if
													test="${ExamPublishHallTicketForm.programTypeId != null && ExamPublishHallTicketForm.programTypeId != ''}">
													<c:set var="mapFooter"
														value="${baseActionForm.collectionMap['mapFooter']}" />
													<c:if test="${mapFooter != null}">
														<nested:optionsCollection property="mapFooter"
															label="value" value="key" styleClass="comboBig" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</nested:select></td>
								</tr>
								<c:if test="${ExamPublishHallTicketForm.examCenterDispaly!= null && ExamPublishHallTicketForm.examCenterDispaly == true}">
									<tr>
										<td class="row-odd">
											<div align="right">Exam Center Code:</div>
										</td>
										<td class="row-even">
											<html:text property="examCenterCode" styleClass="TextBox"
												styleId="examCenterCode" size="16" maxlength="50" name="ExamPublishHallTicketForm" />
										</td>
										<td class="row-odd">
											<div align="right">Exam Center:</div>
										</td>
										<td class="row-even">
											<html:text property="examCenter" styleClass="TextBox"
												styleId="examCenter" size="16" maxlength="100" name="ExamPublishHallTicketForm" />
										</td>
									</tr>
								</c:if>
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
									<td width="23%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.downloadStartDate" />:
									</div>
									</td>
									<td width="23%" height="25" class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="ExamPublishHallTicketForm"
												property="downLoadStartDate" styleId="downLoadStartDate"
												maxlength="10" styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
	new tcal( {
		// form name
		'formname' :'ExamPublishHallTicketForm',
		// input name
		'controlname' :'downLoadStartDate'
	});
</script></td>
										</tr>
									</table>
									</td>
									<td width="28%" class="row-odd">
									<div align="right">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.downloadEndDate" />:</div>
									</div>
									</td>
									<td width="26%" class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="ExamPublishHallTicketForm" property="downLoadEndDate"
												styleId="downLoadEndDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
	new tcal( {
		// form name
		'formname' :'ExamPublishHallTicketForm',
		// input name
		'controlname' :'downLoadEndDate'
	});
</script></td>
										</tr>
									</table>
									</td>
									<td width="28%" class="row-odd">
									<div align="right">
									<div align="right"><bean:message
										key="knowledgepro.exam.ExamMarksEntry.Students.classes.revaluationEndDate" />:</div>
									</div>
									</td>
									<td width="26%" class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="ExamPublishHallTicketForm" property="revaluationEndDate"
												styleId="revaluationEndDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'ExamPublishHallTicketForm',
													// input name
													'controlname' :'revaluationEndDate'
												});
											</script></td>
										</tr>
									</table>
									</td>
								</tr>
								<!--<tr>
								<td width="28%" class="row-odd">
									<div align="right">
									<div align="right">Extended Date:</div>
									</div>
									</td>
									<td width="26%" class="row-even">
									<table width="82" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="60"><html:text
												name="ExamPublishHallTicketForm" property="extendedDate"
												styleId="extendedDate" maxlength="10"
												styleClass="TextBox" size="10" /></td>
											<td width="40"><script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'ExamPublishHallTicketForm',
													// input name
													'controlname' :'extendedDate'
												});
											</script></td>
										</tr>
									</table>
									</td>
									<td width="28%" class="row-odd">
									</td>
									<td width="26%" class="row-even">
									</td>
									<td width="28%" class="row-odd">
									</td>
									<td width="26%" class="row-even">
									</td>
								</tr>


							--></table>
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

						<tr>
							<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>

									<td width="49%" height="35" align="right"><c:choose>
										<c:when
											test="${editPublishHallTicket != null && editPublishHallTicket == 'edit'}">

											<input name="submit" type="submit" class="formbutton"
												value="Update" onclick="changeMethod1()"/>
										</c:when>
										<c:otherwise>
											<input name="submit" type="submit" class="formbutton"
												value="Publish" onclick="changeMethod2()"/>

										</c:otherwise>
									</c:choose></td>
									<td width="2%" align="center">&nbsp;</td>
									<td width="49%" align="left"><c:choose>
										<c:when
											test="${editPublishHallTicket != null && editPublishHallTicket == 'edit'}">
											<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
										</c:when>
										<c:otherwise>
											<input type="Reset" class="formbutton" value="Reset"
												onclick="resetValue()" />

										</c:otherwise>
									</c:choose></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td width="99%" id="hDetails" colspan="10"></td>
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
hook=false;
getList();
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>
