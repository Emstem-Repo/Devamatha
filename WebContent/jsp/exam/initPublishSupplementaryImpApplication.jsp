<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
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
function editDocExam(id){
	document.location.href = "PublishSupplementaryImpApp.do?method=editPublishSupplementaryApp&id="+ id;
}
function deleteDocExam(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "PublishSupplementaryImpApp.do?method=deleteOrReactivatePublish&mode=delete&id="+ id;
	}
}
function reActivate(){
	document.getElementById("mode").value="reActive";
	document.getElementById("method").value="deleteOrReactivatePublish";
	document.publishSupplementaryImpApplicationForm.submit();
}
function resetFormFields(){
	if(document.getElementById("mode").value == "update")
	{
		var id=document.getElementById("id").value;
		editDocExam(id);
	}
	else
	{
	document.location.href = "PublishSupplementaryImpApp.do?method=initPublishSupplementary";
	}
}
function getExamsByExamTypeAndYear() {
	var examType=document.getElementById("examType").value;
	var year=document.getElementById("year").value;
	getExamNameByExamTypeAndYear("examMap", examType,year, "examNameId", updateExamName);
}
function updateExamName(req) {
	updateOptionsFromMap(req, "examNameId", "- Select -");
	updateCurrentExam(req, "examNameId");
}
function loadClassByExamName(examId){
	var year=document.getElementById("year").value;
	document.location.href = "PublishSupplementaryImpApp.do?method=loadClassByExamNameAndYear&examId="+examId+"&year="+year;
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
function setListonYearChange(){
	var year=document.getElementById("year").value;
	var examId=document.getElementById("examNameId").value;
	 var args ="method=setListOnYearChange&year="+year+"&examId="+examId;
	 var url = "PublishSupplementaryImpApp.do";
	 requestOperationProgram(url, args, updateListByYear);
}
function updateListByYear(req){
	var responseObj = req.responseXML.documentElement;
	var ToList=responseObj.getElementsByTagName("SupplyImprove");
	var htm="<tr><td height='19' valign='top' background='images/Tright_03_03.gif' colspan='2'></td><td valign='top' class='news'>";
	htm=htm+"<table width='100%' border='0' align='center' cellpadding='0' 	cellspacing='0'><tr>";
	htm=htm+"<td><img src='images/01.gif' width='5' height='5'/></td><td width='100%' background='images/02.gif'></td>";
	htm=htm+"<td><img src='images/03.gif' width='5' height='5'/></td></tr><tr><td width='5' background='images/left.gif'></td>";
	htm=htm+"<td valign='top'><table width='100%' cellspacing='1' cellpadding='2'><tr>";
	htm=htm+"<td width='5%' height='25%' class='row-odd' ><div align='center'><bean:message key='knowledgepro.slno'/></div></td>";
	htm=htm+"<td width='25%' height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.exam.examDefinition.examName'/></div></td>";
	htm=htm+"<td width='25%' height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.admission.className'/></div></td>";
	htm=htm+"<td width='15%' height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.employee.holidays.startDate'/></div></td>";
	htm=htm+"<td width='15%' height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.employee.holidays.endDate'/></div></td>";
	htm=htm+"<td width='8%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.edit'/></div></td>";
	htm=htm+"<td width='7%'  height='25' class='row-odd' ><div align='center'><bean:message key='knowledgepro.delete'/></div></td></tr>";
 				
	 for ( var i = 0; i < ToList.length; i++) {
			if(ToList[i]!=null){
				var examName= ToList[i].getElementsByTagName("examName")[0].firstChild.nodeValue;
				var className = ToList[i].getElementsByTagName("className")[0].firstChild.nodeValue;
				var startDate = ToList[i].getElementsByTagName("startDate")[0].firstChild.nodeValue;
				var endDate = ToList[i].getElementsByTagName("endDate")[0].firstChild.nodeValue;
				var tid = ToList[i].getElementsByTagName("tId")[0].firstChild.nodeValue;
				
				htm=htm+"<tr><td  height='25' class='row-even' align='center'>"+(parseInt(i)+1)+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+examName+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+className+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+startDate+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'>"+endDate+"</td>";
				htm=htm+"<td  height='25' class='row-even' align='center'><img src='images/edit_icon.gif' width='16' height='18' style='cursor: pointer' onclick='editDocExam("+tid+")'/></td>";
				htm=htm+"<td  height='25' class='row-even' align='center'><img src='images/delete_icon.gif' width='16' height='16' style='cursor: pointer' onclick='deleteDocExam("+tid+")'/></td></tr>";
			}
		}
		htm=htm+"</table></td><td width='5' height='30' background='images/right.gif'></td>";
		htm=htm+"</tr><tr><td height='5'><img src='images/04.gif' width='5' height='5' /></td>";
		htm=htm+"<td background='images/05.gif'></td><td><img src='images/06.gif'/></td></tr>";
		htm=htm+"</table></td><td valign='top' background='images/Tright_3_3.gif' class='news'></td></tr>";
		
	 document.getElementById("ToListDetail").style.display = "block";
	 document.getElementById("listDetails").style.display = "none";
	 
	 document.getElementById("ToListDetail").innerHTML = htm;
	
}
</script>
<html:form action="/PublishSupplementaryImpApp" onsubmit="getClassValues()">	
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="mode" styleId="mode" value="update" />
		</c:when>
		<c:otherwise>
		<html:hidden property="mode" styleId="mode" value="add" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="method" styleId="method" value="addOrUpdatePublish" />
	<html:hidden property="formName" value="publishSupplementaryImpApplicationForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="examType" name="publishSupplementaryImpApplicationForm" styleId="examType"/>
	<html:hidden property="stayClass" styleId="stayClass" />
	<html:hidden property="id" styleId="id" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/>
			<span class="Bredcrumbs">&gt;&gt;
			Publish Supplementary Improvement Application Entry
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Publish Supplementary Improvement Application Entry</strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'>Publish Supplementary Improvement Application</span></FONT></div>
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
							<td width="100%" background="images/02.gif"></td>
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
										value="<bean:write name="publishSupplementaryImpApplicationForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" onchange="getExamsByExamTypeAndYear(),setListonYearChange()">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd">
									<div align="right"><span class="Mandatory">*</span> Exam
									Name :</div>
									</td>

									<td class="row-even"><html:select property="examId"
										styleClass="combo" styleId="examNameId" name="publishSupplementaryImpApplicationForm" style="width:200px" onchange="loadClassByExamName(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="publishSupplementaryImpApplicationForm" property="examNameList">
											<html:optionsCollection property="examNameList"
												name="publishSupplementaryImpApplicationForm" label="value" value="key" />
										</logic:notEmpty>
									</html:select></td>
							</tr>
							<tr>
								<td width="22%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.exam.studentEligibilityEntry.classCode" />
									:</div>
									</td>
							<td height="25" class="row-even" colspan="3">

									<table border="0">
										<tr>
											<td width="112"><label>
											
											 <nested:select
												property="classCodeIdsFrom" styleClass="body"
												multiple="multiple" size="8" styleId="mapClass"
												style="width:200px">
												<logic:notEmpty name="publishSupplementaryImpApplicationForm" property="mapClass" >
														<nested:optionsCollection name="publishSupplementaryImpApplicationForm"
															property="mapClass" label="value" value="key"
															styleClass="comboBig" />
													</logic:notEmpty>
											</nested:select> </label></td>
											<td width="49"><c:choose>
												<c:when
													test="${operation != null && operation == 'edit'}">
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
			
                                                  document.getElementById("examNameId").disabled=true;
                                                  document.getElementById("mapClass").disabled=true;
                                                  </script>
	                                                 <html:hidden property="examId"/>
												</c:when>
												<c:otherwise>
                                    		    <script type="text/javascript" language="javascript">
		
                                                  document.getElementById("examNameId").disabled=false;
                                                  document.getElementById("mapClass").disabled=false;
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



												<logic:notEmpty name="publishSupplementaryImpApplicationForm"
													property="mapSelectedClass">
													<nested:optionsCollection name="publishSupplementaryImpApplicationForm"
														property="mapSelectedClass" label="value" value="key"
														styleClass="comboBig" />
												</logic:notEmpty>



											</nested:select> </label></td>
										</tr>
									</table>

									</td>
							</tr>
								<tr>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="publishSupplementaryImpApplicationForm" property="startDate" styleId="startDate" size="10" maxlength="16"/>
						             <script language="JavaScript">
										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#startDate").datepicker(pickerOpts);
											});
	                                  </script>
									</td>
									<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
									</td>
									<td class="row-even">
									<html:text name="publishSupplementaryImpApplicationForm" property="endDate" styleId="endDate" size="10" maxlength="16"/>
							 <script language="JavaScript">
										$(function(){
											 var pickerOpts = {
													 	            dateFormat:"dd/mm/yy"
													         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#endDate").datepicker(pickerOpts);
											});
	                                  </script>
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
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
									<c:choose>
							<c:when test="${operation == 'edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property="" value="Reset" styleClass="formbutton" onclick="resetFormFields()"></html:button></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty name="publishSupplementaryImpApplicationForm" property="toList">	
				<tr id="listDetails">
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="5%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="25%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.exam.examDefinition.examName" /></div>
											</td>
											<td width="25%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.admission.className" /></div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.employee.holidays.startDate" /></div>
											</td>
											<td width="15%" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.employee.holidays.endDate" /></div>
											</td>
											<td class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit" /></div>
											</td>
											<td width="8%" class="row-odd">
											<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
											</td>
										</tr>
										
										<logic:iterate id="dList" name="publishSupplementaryImpApplicationForm" property="toList" indexId="count">
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
											<td align="center"><bean:write name="dList" property="examName" /></td>
											<td align="center"><bean:write name="dList" property="className" /></td>
											<td align="center"><bean:write name="dList" property="startDate" /></td>
											<td align="center"><bean:write name="dList" property="endDate" /></td>
											<td height="25" align="center">
											<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor: pointer" onclick="editDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor: pointer" onclick="deleteDocExam('<bean:write name="dList" property="id" />')" /></div>
											</td>
											</tr>	
										</logic:iterate>
										
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>	
						</logic:notEmpty>
						<tr>
						<td colspan="3">
						 <div id="ToListDetail"></div>
						  </td>
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
document.getElementById("ToListDetail").style.display = "none";
</script>