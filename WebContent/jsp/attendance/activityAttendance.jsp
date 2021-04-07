<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>

<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
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
	function getClasses(year) {
		getClassesByYear("classMap", year, "classSchemewiseId", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "classSchemewiseId", " - Select -");
	}

	function getPeriodFrom(classSchemaId) {

		getPeriodsByClassSchemewiseId("periodMap", classSchemaId, "fromPeriod",
				updatePeriods);

	}
	function updatePeriods(req) {
		var responseObj = req.responseXML.documentElement;
		var destination = document.getElementById("fromPeriod");
		var destination2 = document.getElementById("toPeriod");
		for (x1 = destination.options.length - 1; x1 > 0; x1--) {
			destination.options[x1] = null;
		}
		for (x1 = destination2.options.length - 1; x1 > 0; x1--) {
			destination2.options[x1] = null;
		}

		var childNodes = responseObj.childNodes;
		destination.options[0] = new Option("-Select-", "");
		destination2.options[0] = new Option("-Select-", "");
		var items = responseObj.getElementsByTagName("option");

		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination.options[i + 1] = new Option(label, value);

		}

		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
			destination2.options[i + 1] = new Option(label, value);

		}

		//updateOptionsFromMap(req, "fromPeriod", "- Select -");
	}

	function getPeriodTo(classSchemaId) {
		getPeriodsByClassSchemewiseId("periodMap", classSchemaId, "toPeriod",
				updatePeriodTo);
	}
	function updatePeriodTo(req) {
		updateOptionsFromMap(req, "toPeriod", "- Select -");
	}

	function resetErrorMsgs() {
		resetErrMsgs();
		resetOption("fromPeriod");
		resetOption("toPeriod");
		resetOption("activity");		
		document.getElementById("attendanceTypeId").value = "";
		document.getElementById("classSchemewiseId").value = "";
		document.getElementById("activity").value = "";
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		document.getElementById("fromPeriod").value = "";
		document.getElementById("toPeriod").value = "";
		document.getElementById("registerNoEntry").value = "";
		document.getElementById("attendanceTypeId").value = "";

	}
	function getActivity(attendenceTypeId) {
		var selectedArray = new Array();
		var i;
		var count = 0;
		for (i = 0; i < attendenceTypeId.options.length; i++) {
			if (attendenceTypeId.options[i].selected) {
				selectedArray[count] = attendenceTypeId.options[i].value;
				count++;
			}
		}

		getActivityByType("activityMap", selectedArray, "activity",
				updateActivity);
	}

	function updateActivity(req) {
		updateOptionsFromMap(req, "activity", "- Select -");
	}
	function nextPageYes() {
		document.getElementById("checkyesNo").value="Yes";
		document.getElementById("method").value = "submitActivityAttendence"; 
		document.approveleaveForm.submit();
	}
	function previousPageNo(){
		document.getElementById("checkyesNo").value="No";
		document.getElementById("method").value = "submitActivityAttendence"; 
		document.approveleaveForm.submit();
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

	function getPeriodByClasses() {
		var fromMapClasses = document.getElementById('mapClass');
		var tomapClasses = document.getElementById('selsubMap');

		var destination1 = document.getElementById("fromPeriod");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1]=null;
		}

		var destination2 = document.getElementById("toPeriod");
		for (x1=destination2.options.length-1; x1>=0; x1--) {
			destination2.options[x1]=null;
		}

		
			destination1.options[0]=new Option("- Loading -","");
			destination2.options[0]=new Option("- Loading -","");
			var selectedClasses1 = new Array();
				
			
				var len = tomapClasses.length;
				for(var k=0; k<len; k++)
				{
					selectedClasses1.push(tomapClasses[k].value);
				}
				var url = "activityAttendance.do";
				var args = "method=getPeriodsByClassSchemewisevalues&selectedClassesArray1="+selectedClasses1;
				requestOperationProgram(url,args,updateSubjcetBatchPeriod);		
	}

	function updateSubjcetBatchPeriod(req) {
		var responseObj = req.responseXML.documentElement;
		var destination3 = document.getElementById("toPeriod");
		var destination4 = document.getElementById("fromPeriod");
		destination4.options[0]=new Option("- Select -","");
		
		destination3.options[0]=new Option("- Select -","");
		var items1 = responseObj.getElementsByTagName("period");
		for (var j = 0 ; j < items1.length ; j++) {
	        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination3.options[j+1] = new Option(label,value);
		 }
		for (var k = 0 ; k < items1.length ; k++) {
	        label = items1[k].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
		     value = items1[k].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
		     destination4.options[k+1] = new Option(label,value);
		 }
	}
	function getPeriods() {
		if(document.getElementById("no").checked==true)
		var type=document.getElementById("no").value;
		if(document.getElementById("yes").checked==true)
			var type=document.getElementById("yes").value;
		if (type == "Yes") {
			document.getElementById("showperiods").style.display = "none";
		}else if(type == "No"){
			document.getElementById("showperiods").style.display = "block";
		}
	}
	
	function getClassValues(){
		var listClasses=new Array(); 
			var mapTo1 = document.getElementById('selsubMap');
			var len1 = mapTo1.length;
			for(var k=0; k<len1; k++)
			{
				listClasses.push(mapTo1[k].value);
			}
		
		
		document.getElementById("classValues").value=listClasses;
		
	}

	function getClassesBySemType(semType){
		var year=document.getElementById("year").value;
		var url = "activityAttendance.do";
		var args = "method=getClassesBySemType&semType="+semType+"&year="+year;
		requestOperationProgram(url,args,updateClassMap);
	}
	function updateClassMap(req) {
		updateOptionsFromMapMultiselect(req, "mapClass", "- Select -");
	}
</script>
<html:form action="/activityAttendance">
	<html:hidden property="method" styleId="method" value="submitActivityAttendence" />
	<html:hidden property="pageType" value="6" />
	<html:hidden property="checkyesNo"  styleId="checkyesNo"/>
	<html:hidden property="formName" value="approveleaveForm" />
	<html:hidden property="classValues" styleId="classValues" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendanceentry.attendance"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.attendance.activityattendence" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.attendance.activityattendence" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/> </span></div>
								<tr>

									<td height="20" colspan="6" class="body" align="left">

									<div id="errorMessage"
										style="font-family: verdana; font-size: 10px"><FONT
										color="red"><html:errors /></FONT> <FONT color="green">
									<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>
									</html:messages> </FONT></div>
									</td>
								</tr>

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
											
											<td height="25" width="25%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admin.year" />:</div>
											</td>
											<td align="left" height="25" width="25%" class="row-even"><input type="hidden"
												id="tempyear" name="tempyear"
												value="<bean:write name="approveleaveForm" property="year"/>" />
											<html:select property="year" styleId="year" onchange="getClasses(this.value)"
												styleClass="combo">
												<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
												<cms:renderAcademicYear></cms:renderAcademicYear>
											</html:select></td>
											
											
											
										<td  height="25" class="row-odd" width="25%">
											<div align="right"><bean:message key="knowledgepro.attn.sem.type" /></div>
										</td>
										
										<td class="row-even"  width="25%">
										
										<div align="left">
										<html:radio property="semType" styleId="even" value="even" onclick="getClassesBySemType(this.value)"></html:radio>
												Even&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												
											<html:radio property="semType" styleId="odd" value="odd" onclick="getClassesBySemType(this.value)"></html:radio>
												Odd&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										
										</td>
											
										</tr>
											<tr>
											
											<td height="25" width="25%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.class" />:</div>
											</td>
											<td align="left" width="25%" class="row-even" colspan="3">
											<!--<html:select
												property="classSchemewiseId" styleClass="combo"
												styleId="classSchemewiseId" onchange="getPeriodFrom(this.value)">
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<c:choose>
													<c:when test="${classMap != null}">
														<html:optionsCollection name="classMap" label="value"
															value="key" />
													</c:when>
													<c:otherwise>
														<c:set var="classMap"
															value="${baseActionForm.collectionMap['classMap']}" />
														<c:if test="${classMap != null}">
															<html:optionsCollection name="classMap" label="value"
																value="key" />
														</c:if>
													</c:otherwise>
												</c:choose>
											</html:select>-->
									<table border="0">
										<tr>
											<td width="112"><label> <nested:select
												property="notSelectedClassIds" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">

												<c:if
													test="${approveleaveForm.classMap != null && approveleaveForm.classMap.size!=0}">
													<nested:optionsCollection name="approveleaveForm"
														property="classMap" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
												


											</nested:select> </label></td>
											<td width="49">
													<table border="0">

														<tr>
															<td><input type="button" onClick="moveoutid(),getPeriodByClasses()";
																			id="moveOut" value=">>"></td>
														</tr>
														<tr>
															<td><input type="button" value="&lt;&lt;"
																id="moveIn" onclick="moveinid(),getPeriodByClasses()";
																							></td>
														</tr>
													</table>
											</td>
											<td width="120"><label> <nested:select
												property="classSchemewiseIds" styleId="selsubMap" styleClass="body"
												multiple="multiple" size="8" style="width:200px;">
												<c:if
													test="${approveleaveForm.mapSelectedClass!=null && approveleaveForm.mapSelectedClass.size!=0}">
													<nested:optionsCollection name="approveleaveForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</c:if>
											</nested:select> </label></td>
										</tr>
									</table>
											</td>
										</tr>
											<tr>
											<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.attn.activity.att.type" /></div>
									</td>
									<td align="left" width="25%" height="25" class="row-even"><html:select
										property="attendanceTypeId" styleClass="combo"
										styleId="attendanceTypeId" onchange="getActivity(this)">
										<html:option value="">
											<bean:message key="knowledgepro.select" />
										</html:option>
										<html:optionsCollection property="attendanceTypeList" label="attendanceTypeName"
											value="id" />
									</html:select> <span class="star"></span>
									</td>
											<td width="25%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.activitytype" />:</div>
											</td>
											<td align="left" width="25%" class="row-even"><html:select
												property="activityTypeId" styleClass="combo"
												 styleId="activity">
												<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
												<c:choose>
													<c:when test="${activityMap != null}">
														<html:optionsCollection name="activityMap" label="value"
															value="key" />
													</c:when>
													<c:otherwise>
														<c:set var="activityMap"
															value="${baseActionForm.collectionMap['activityMap']}" />
														<c:if test="${activityMap != null}">
															<html:optionsCollection name="activityMap" label="value"
																value="key" />
														</c:if>
													</c:otherwise>
												</c:choose>
											</html:select></td>
										</tr>
										<tr>
											<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.regno" />:</div>
											</td>
											<td align="left" width="75%" colspan="3" class="row-even"><label> <html:textarea
												property="registerNoEntry" styleId="registerNoEntry"
												style="width: 83%" rows="3"></html:textarea> </label></td>
										</tr>
										<tr>
											<td height="25" width="25%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admission.fromdate" />:</div>
											</td>
										<td width="30%" height="25" class="row-even">
										<html:text name="approveleaveForm" property="fromDate"
										styleId="fromDate" size="10" maxlength="10" /> 
										<!--<script language="JavaScript">
										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#fromDate").datepicker(pickerOpts);
											});
										</script>-->
										 <script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'approveleaveForm',
													// input name
													'controlname' :'fromDate'
												});
											</script>
									  </td>	
											
											<td height="25" width="25%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.admission.todate" />:</div>
											</td>
											
											
											<td width="30%" height="25" class="row-even">
										<html:text name="approveleaveForm" property="toDate"
										styleId="toDate" size="10" maxlength="10" /> 
										<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'approveleaveForm',
													// input name
													'controlname' :'toDate'
												});
											</script>
										
									  </td>
											
										</tr>
										<tr>
										<td class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
												key="knowledgepro.attendance.activityattendence.full.day" />:</div>
											</td>
										
										<td class="row-even" width="75%" colspan="3">
										<input type="hidden" name="approveleaveForm" id="isPeriodSelected"
																		value="<nested:write name='approveleaveForm' property='periodSelected'/>" />	
										<div align="left">
										<html:radio property="fullDay" styleId="yes" value="Yes" onclick="getPeriods()"></html:radio>
												Yes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												
											<html:radio property="fullDay" styleId="no" value="No" onclick="getPeriods()"></html:radio>
												No&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
										<script type="text/javascript">
										var selected = document.getElementById("isPeriodSelected").value;
												if(selected == "true") {
													document.getElementById("yes").checked = true;
												}
										</script>
										</td>
										</tr>
										
										
										
									</table>

									</td>
									<td width="5" background="images/right.gif"></td>
								</tr>
								
								<tr >
								<td width="5" background="images/left.gif"></td>
										<td id="showperiods" >
										<table width="100%" cellspacing="1" cellpadding="1" border="0">
										<tr>
											<td class="row-odd" height="25" width="16%" >
											<div align="right"><bean:message
												key="knowledgepro.attendance.activityattendence.fromperiod" />:</div>
											</td>
											<td align="left" class="row-even" height="20" width="20%" >
											<html:select
												property="fromPeriod" styleClass="combo"
												styleId="fromPeriod" >
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
												<html:optionsCollection name="approveleaveForm"
													property="periodMap" label="value" value="key" />
											</html:select>
											</td>
											<td class="row-odd" height="25" width="14%" >
											<div align="right">To Period :</div>
											</td>
											<td align="left" class="row-even" height="25"  width="42%">	
											<html:select
												property="toPeriod" styleClass="combo"
												styleId="toPeriod" >
												<html:option value="">
													<bean:message key="knowledgepro.select" />-</html:option>
														
														<html:optionsCollection name="approveleaveForm"
													property="periodMap" label="value" value="key" />
			                           		   
											
											</html:select></td>
											
											</tr>
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
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
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>
						<tr>
							<td height="35" align="center"><html:submit property="" value="Submit" styleClass="formbutton" onclick="getClassValues()"></html:submit>
							&nbsp;&nbsp;&nbsp;<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button></td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="1"></td></tr>											
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				  <tr>
        <td background="images/Tright_03_03.gif" height="19" valign="top"></td>
        <td class="heading">      
        </td><td background="images/Tright_3_3.gif" valign="top"></td>
      </tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
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
			if(document.getElementById("no").checked==true)
			var type=document.getElementById("no").value;
			if(document.getElementById("yes").checked==true)
				var type=document.getElementById("yes").value;
			if (type == "Yes") {
				document.getElementById("showperiods").style.display = "none";
			}else if(type == "No"){
				document.getElementById("showperiods").style.display = "block";
			}
			
</script>