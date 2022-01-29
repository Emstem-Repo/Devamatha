<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>:: CMS ::</title>

<script type="text/javascript" language="javascript">

// Functions for AJAX 
	function getPrograms(programTypeId) {
		resetOption("program");
		resetOption("course");
		getProgramsByType("programMap",programTypeId,"program",updatePrograms);
	}
	
	function updatePrograms(req) {
		resetOption("program");
		updateOptionsFromMap(req,"program","- Select -");
		resetOption("course");
	}
	
	function getCourses(programId) {
		resetOption("course");
		getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	}
	
	function updateCourses(req) {
		updateOptionsFromMap(req,"course","- Select -");
		getSubjectGroup(document.getElementById("program").value);
	}

	function getNames(){
		document.getElementById("programTypeName").value = document.getElementById("programType").options[document.getElementById("programType").selectedIndex].text;
		document.getElementById("courseName").value = document.getElementById("course").options[document.getElementById("course").selectedIndex].text;
		document.getElementById("programName").value = document.getElementById("program").options[document.getElementById("program").selectedIndex].text;
	}

	function setSubjectGroupEntry(){
		var sda1 = document.getElementById('selsubMap');
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
		}	
		var obj= document.getElementById("selsubMap").selectedIndex;
		if(obj == -1 && sda1.length == 0)
			document.getElementById("selectedIndex").value = -1;
		else
			document.getElementById("selectedIndex").value = 1;
		document.getElementById("method").value="setSubjectGroupEntry";
	}

	function updateSubjectGroupEntry(){
		var sda1 = document.getElementById('selsubMap');
		if(document.getElementById('commonSubjectGroup').checked){
						document.getElementById('commonSubjectGroup').value="on";
		}
		else{
			document.getElementById('commonSubjectGroup').value="off";
			
		}
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
		}	
		var obj= document.getElementById("selsubMap").selectedIndex;
		if(obj == -1 && sda1.length == 0){
			document.getElementById("selectedIndex").value = -1;
		}else
			document.getElementById("selectedIndex").value = 1;
			document.getElementById("method").value="updateSubjectGroupEntry";
	}

	function reActivate() {
		var subjectGroupName = document.getElementById("subjectGroupName").value;	
		
		document.location.href="subjectGroupEntry.do?method=reActivateSubjectGroupEntry&subjectGroupName="+subjectGroupName;
	}

	function resetInAddMode() {
		document.location.href = "subjectGroupEntry.do?method=initSubjectGroupEntry";
		resetFieldAndErrMsgs();
	}
	function moveoutid()
	{
		document.getElementById('searchSubLeft').value = "";
		var sda = document.getElementById('subMap');
		var len = sda.length;
		var sda1 = document.getElementById('selsubMap');
		if(sda1.length == 0) {
			document.getElementById("moveIn").disabled = false;
		}
		for(var j=0; j<len; j++)
		{
			if(sda[j].selected)
			{
				var tmp = sda.options[j].text;
				var tmp1 = sda.options[j].value;
				sda.remove(j);
				len--;
				j--;
				if(j<0){
					document.getElementById("moveOut").disabled = true;
					document.getElementById("moveIn").disabled = false;
				}
				if(sda.length <= 0)
					document.getElementById("moveOut").disabled = true;
				else
					document.getElementById("moveOut").disabled = false;
				var y=document.createElement('option');
				y.text=tmp;
				y.value = tmp1;
				y.setAttribute("class","comboBig");
				try
				{
					sda1.add(y,null);
				}
				catch(ex)
				{
					sda1.add(y);
				}
			}
		}
	}
	
	function moveinid()
	{
		var sda = document.getElementById('subMap');
		var sda1 = document.getElementById('selsubMap');
		var len = sda1.length;
		
		for(var j=0; j<len; j++)
		{
			if(sda1[j].selected)
			{
				var tmp = sda1.options[j].text;
				var tmp1 = sda1.options[j].value;
				sda1.remove(j);
				len--;
				j--;
				if(j<0){
					document.getElementById("moveIn").disabled = true;
					document.getElementById("moveOut").disabled = false;
				}
				if(sda1.length != 0) {
					document.getElementById("moveOut").disabled = false;
					document.getElementById("moveIn").disabled = false;
				}
				else
					document.getElementById("moveOut").disabled = false;
				var y=document.createElement('option');
				y.setAttribute("class","comboBig");
				y.text=tmp;
				try
				{
				sda.add(y,null);
				}
				catch(ex){
				sda.add(y);	
				}
			}
		}	
	}
	function searchSubject(searchValue){
		var sda = document.getElementById('subMap');
		var len = sda.length;
		var searchValueLen = searchValue.length;
		for(var m =0; m<len; m++){
			sda.options[m].selected = false;		
		}
		for(var j=0; j<len; j++)
		{
			for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
			}
		}
	}

	function getSubjectGroup(programId){
		
		var programTypeId = document.getElementById("programType").value;
		var program = programId;
		if(program != null && programTypeId != null){
 			getSubjectGroupForProgram(program,programTypeId,updateSubjectGroup);
			}
		}
	
	function updateSubjectGroup(req){
		
		var responseObj = req.responseXML.documentElement;
		var items = responseObj.getElementsByTagName("option");
		
		var htm="<table width='100%' cellspacing='1' cellpadding='2'>  <tr height='25px' class='row-odd'>";
		htm=htm+"<td>"+"Sl No"+"</td>"+"<td>"+"Program Type"+"</td>"+"<td>"+"Program"+"</td>"+"<td>"+"Course"+"</td>"+"<td>"+"Subject Group"+"</td>"+"<td>"+"Subject Entry"+"</td>"+"<td>"+"Edit"+"</td><td>"+"Delete"+"</td></tr>";

			if(items != null ){
				 var slNo = 1;
				for ( var i = 0; i < items.length; i++) {
					 var subGroupId = items[i].getElementsByTagName("subjectGroupId")[0].firstChild.nodeValue;
				     var programTypeId = items[i].getElementsByTagName("programTypeId")[0].firstChild.nodeValue;
				     var programId = items[i].getElementsByTagName("programId")[0].firstChild.nodeValue;
				     var courseId = items[i].getElementsByTagName("courseId")[0].firstChild.nodeValue;
				     var programTypeName = items[i].getElementsByTagName("programType")[0].firstChild.nodeValue;
				     var programName = items[i].getElementsByTagName("program")[0].firstChild.nodeValue;
				     var courseName = items[i].getElementsByTagName("course")[0].firstChild.nodeValue;
				     var subjectGroupName = items[i].getElementsByTagName("subjectGroup")[0].firstChild.nodeValue;
				     var subjectName = items[i].getElementsByTagName("subject")[0].firstChild.nodeValue;
					 if(slNo%2==0){
					     htm = htm + "<tr class='row-white'> ";
					 }else{
						 htm = htm + "<tr class='row-even'> ";
					 }

				     htm=htm + "<td width='4%' height='25'>"+slNo+ "</td>"+"<td width='12%'>"+programTypeName+ "</td>"+"<td width='13%'>"+programName+ "</td>"+"<td width='15%'>"+courseName+ "</td>"+"<td width='16%'>"+subjectGroupName+ "</td>"+"<td width='25%'>"+subjectName+ "</td>";
				     htm = htm + "<td width='7%'> <div align='center'> <img src='images/edit_icon.gif' width='16' height='18' style='cursor: pointer' onclick='editSubjectGroupEntry("+subGroupId+","+programTypeId +","+programId+","+courseId+")'/></div></td>";
				     htm = htm + "<td width='8%'> <div align='center'> <img src='images/delete_icon.gif' width='16' height='16' style='cursor: pointer' onclick='deleteSubjectGroupEntry("+subGroupId+","+programTypeId +","+programId+","+courseId+")'/></div></td>";
				     htm=htm+"</tr>";
				     slNo++;
				}
			}
			htm = htm + "</table>";
			document.getElementById("display_Details").innerHTML = htm;
		}
</script>

</head>
<html:form action="/subjectGroupEntry" method="POST">
	<html:hidden property="selectedIndex" styleId="selectedIndex" />
	<c:choose>
		<c:when test="${subjectEntryOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateSubjectGroupEntry" />



		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="setSubjectGroupEntry" />
		</c:otherwise>
	</c:choose>

	<html:hidden property="formName" value="subjectGroupEntryForm" />
	<html:hidden property="subjectGroupEntryId" styleId="sgeId"
		value='<bean:write name="subjectGroupEntryForm" property="subjectGroupEntryId" />' />
	<input type="hidden" name="programTId" id="programTId"
		value='<bean:write name="subjectGroupEntryForm" property="programTypeId"/>' />

	<html:hidden property="pageType" value="1" />


	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.admin" /><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admin.subjectGroupEntry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.admin.subjectGroupEntry" /> </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
									<td width="200" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.programtype" />:</div>
									</td>
									<td width="300" height="25" class="row-even"><label>
									<html:select property="programTypeId" styleClass="comboLarge"
										styleId="programType" onchange="getPrograms(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<html:optionsCollection property="programTypeList"
											label="programTypeName" value="programTypeId" />
									</html:select></label></td>

									<td width="200" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.program" />:</div>
									</td>
									<td width="200" class="row-even"><html:select
										name="subjectGroupEntryForm" property="programId"
										styleId="program" styleClass="comboLarge"
										onchange="getCourses(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${subjectEntryOperation == 'edit'}">
												<html:optionsCollection name="programMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if
													test="${subjectGroupEntryForm.programTypeId != null && subjectGroupEntryForm.programTypeId != ''}">
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
									<td height="25" class="row-even"><html:select
										name="subjectGroupEntryForm" property="courseId"
										styleId="course" styleClass="comboExtraLarge">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<c:choose>
											<c:when test="${subjectEntryOperation == 'edit'}">
												<html:optionsCollection name="courseMap" label="value"
													value="key" />
											</c:when>
											<c:otherwise>
												<c:if test="${courseMap != null}">
													<html:optionsCollection name="courseMap" label="value"
														value="key" />
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.subjectGroupName" />:</div>
									</td>
									<td class="row-even"><span class="star"><html:text
										property="subjectGroupName" styleId="subjectGroupName"
										size="16" maxlength="50" styleClass="comboLarge" /> </span></td>
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.subjectsGroupEntry.searchSubject" />:</div>
									</td>
									<td class="row-even"><html:text property="searchSubLeft"
										styleId="searchSubLeft" size="16" maxlength="30"
										onkeyup="searchSubject(this.value)" /></td>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.subjectsGroupEntry" /> :</div>
									</td>
									
									<td class="row-even">
									<logic:notEmpty property="commonSubjectGroup" name="subjectGroupEntryForm">
									<input type="hidden" name="commonSubject" id="test" value='<bean:write property="commonSubjectGroup" name="subjectGroupEntryForm"/>'/>
									
									</logic:notEmpty>
									<html:checkbox
										property="commonSubjectGroup" styleId="commonSubjectGroup" />
									
									<script type="text/javascript" language="javascript">

									if(commonGp != null){
										var commonGp= document.getElementById("test").value;
										if(commonGp == "true") {
												document.getElementById("commonSubjectGroup").checked = true;
										}
									}
								
								</script>
									</td>
									
								
								</tr>
								<tr>
									<td class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.exam.secondLanguage" /> :</div>
									</td>
									<td class="row-even" colspan="3"><html:select
										property="secondLanguageId" styleClass="combo"
										styleId="secondLanguageId" name="subjectGroupEntryForm"
										style="width:200px">
										<html:option value="-1">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="subjectGroupEntryForm"
											property="listSecondLanguage">
											<html:optionsCollection property="listSecondLanguage"
												name="subjectGroupEntryForm" label="display" value="id" />
										</logic:notEmpty>
									</html:select></td>
								</tr>
								<tr>
									<td height="25" valign="top" width="15%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.subject" />:</div>
									</td>
									<td height="25" class="row-even" width="40%" colspan="3">
									<table>
										<tr>
											<td style="width: 620px"><label> <nested:select
												property="selectedSubjects" styleId="subMap"
												styleClass="body" multiple="multiple" size="8"
												style="width:620px">
												<nested:optionsCollection name="subjectGroupEntryForm"
													property="subjectsMap" label="value" value="key"
													styleClass="comboBig" />
											</nested:select> </label></td>
										</tr>
										<tr>
											<td width="8%" height="25" align="center"><input
												type="button" class="btndown" id="moveOut"
												onclick="moveoutid()" /> <c:choose>
												<c:when test="${subjectEntryOperation == 'edit'}">
													<input type="button" id="moveIn" onclick="moveinid()"
														class="btnup" />
												</c:when>
												<c:otherwise>
													<c:if
														test="${subjectGroupEntryForm.movedSubjectsTORight  == null }">
														<input type="button" id="moveIn" onclick="moveinid()"
															class="btnup" disabled="disabled" />
													</c:if>
													<c:if
														test="${subjectGroupEntryForm.movedSubjectsTORight  != null }">
														<input type="button" id="moveIn" onclick="moveinid()"
															class="btnup" />
													</c:if>
												</c:otherwise>
											</c:choose></td>
										</tr>
										<tr>
											<td style="width: 620px"><nested:select
												property="movedSubjectsTORight" styleId="selsubMap"
												styleClass="body" multiple="multiple" size="8"
												style="width:620px;" >
												<c:if
													test="${subjectGroupEntryForm.selectedSubjectsMap!=null && subjectGroupEntryForm.selectedSubjectsMap.size!=0}">
													<nested:optionsCollection name="subjectGroupEntryForm"
														property="selectedSubjectsMap" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select></td>
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
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${subjectEntryOperation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										onclick="updateSubjectGroupEntry()">
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										onclick="setSubjectGroupEntry()">
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${subjectEntryOperation == 'edit'}">
									<html:cancel styleClass="formbutton">
										<bean:message key="knowledgepro.admin.reset" />
									</html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetInAddMode()">
										<bean:message key="knowledgepro.cancel" />
									</html:button>
								</c:otherwise>
							</c:choose></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
							<div id="display_Details">
							<logic:equal value="true" name="subjectGroupEntryForm" property="displayList">
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.programtype" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.course" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.subjectgroup" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.subject" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>

								<logic:notEmpty name="subjectGroupEntryForm" property="subjectGroupList">
									<logic:iterate id="slist" name="subjectGroupEntryForm"
										property="subjectGroupList" indexId="count">
										<%
											String dynamicStyle = "";
																if (count % 2 != 0) {
																	dynamicStyle = "row-white";
																} else {
																	dynamicStyle = "row-even";
																}
										%>
										<tr>
											<td width="4%" height="25" class="<%=dynamicStyle%>">
											<div align="center"><%=(count + 1)%></div>
											</td>
											<td width="12%" height="25" class="<%=dynamicStyle%>">
											<div align="center"><bean:write name="slist"
												property="courseTO.programTo.programTypeTo.programTypeName" /></div>
											</td>
											<td width="13%" class="<%=dynamicStyle%>">
											<div align="center"><bean:write name="slist"
												property="courseTO.programTo.name" /></div>
											</td>
											<td width="15%" class="<%=dynamicStyle%>">
											<div align="center"><bean:write name="slist"
												property="courseTO.name" /></div>
											</td>
											<td width="16%" class="<%=dynamicStyle%>">
											<div align="center"><bean:write name="slist"
												property="name" /></div>
											</td>
											<td width="25%" class="<%=dynamicStyle%>"><bean:write
												name="slist" property="subjectNames" /><br>
											</td>
											<td width="7%" height="25" class="<%=dynamicStyle%>">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editSubjectGroupEntry('<bean:write name="slist" property="id"/>',
							                    '<bean:write name="slist" property="courseTO.programTo.programTypeTo.programTypeId"/>',
							                    '<bean:write name="slist" property="courseTO.programTo.id"/>',
							                    '<bean:write name="slist" property="courseTO.id"/>')"></div>
											</td>
											<td width="8%" height="25" class="<%=dynamicStyle%>">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteSubjectGroupEntry('<bean:write name="slist" property="id"/>')"></div>
											</td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</table>
						</logic:equal>
						</div>
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
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script type="text/javascript" language="javascript">

	var programTypeId = document.getElementById("programTId").value;
	if(programTypeId.length != 0){
		document.getElementById("programType").value= programTypeId;
	}
	
	function editSubjectGroupEntry(subjectGroupEntryId,programTypeId,programId,courseId){
		
		document.getElementById("sgeId").value= subjectGroupEntryId;
		document.location.href="subjectGroupEntry.do?method=editSubjectGroupEntry&subjectGroupEntryId="+subjectGroupEntryId+
				"&programTypeId="+programTypeId+"&programId="+programId+"&courseId="+courseId;
	}
	
	function deleteSubjectGroupEntry(id,programTypeId,programId,courseId) {
	
		deleteConfirm =confirm("Are you sure to delete this entry?");
		document.getElementById("sgeId").value=id;
		if(deleteConfirm){
			document.location.href = "subjectGroupEntry.do?method=deleteSubjectGroupEntry&subjectGroupEntryId="+id+
					"&programTypeId="+programTypeId+"&programId="+programId+"&courseId="+courseId;
		}
	}
</script>