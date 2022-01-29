<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link type="text/css" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<style type="text/css">
.comboBig1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #000000;
	text-decoration: none;
	font-weight: normal;
}
</style>
<script type="text/javascript">

function moveoutid()
{
	var sda = document.getElementById('selectedClasses');
	var len = sda.length;
	var sda1 = document.getElementById('classes');
	if(sda1.length == 0) {
		document.getElementById("moveOut").disabled = false;
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
				document.getElementById("moveIn").disabled = true;
				document.getElementById("moveOut").disabled = false;
			}
			if(sda.length <= 0)
				document.getElementById("moveIn").disabled = true;
			else
				document.getElementById("moveIn").disabled = false;
			var y=document.createElement('option');
			y.text=tmp;
			y.value = tmp1;
			y.setAttribute("class","comboBig1");
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
	var sda = document.getElementById('selectedClasses');
	var sda1 = document.getElementById('classes');
	var len = sda1.length;
	  var array = new Array();
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			array[j]=tmp1;
			sda1.remove(j);
			len--;
			j--;
			if(j<0){
				document.getElementById("moveOut").disabled = true;
				document.getElementById("moveIn").disabled = false;
			}
			if(sda1.length != 0) {
				document.getElementById("moveIn").disabled = false;
				document.getElementById("moveOut").disabled = false;
			}
			else
				document.getElementById("moveIn").disabled = false;
			var y=document.createElement('option');
			y.setAttribute("class","comboBig1");
			y.text=tmp;
			y.value = tmp1;
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
function moveoutid1()
{
	var sda = document.getElementById('selectedRoomNo');
	var len = sda.length;
	var sda1 = document.getElementById('roomNo');
	if(sda1.length == 0) {
		document.getElementById("moveOut1").disabled = false;
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
				document.getElementById("moveIn1").disabled = true;
				document.getElementById("moveOut1").disabled = false;
			}
			if(sda.length <= 0)
				document.getElementById("moveIn1").disabled = true;
			else
				document.getElementById("moveIn1").disabled = false;
			var y=document.createElement('option');
			y.text=tmp;
			y.value = tmp1;
			y.setAttribute("class","comboBig1");
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

function moveinid1()
{
	var sda = document.getElementById('selectedRoomNo');
	var sda1 = document.getElementById('roomNo');
	var len = sda1.length;
	  var array = new Array();
	for(var j=0; j<len; j++)
	{
		if(sda1[j].selected)
		{
			var tmp = sda1.options[j].text;
			var tmp1 = sda1.options[j].value;
			array[j]=tmp1;
			sda1.remove(j);
			len--;
			j--;
			if(j<0){
				document.getElementById("moveOut1").disabled = true;
				document.getElementById("moveIn1").disabled = false;
			}
			if(sda1.length != 0) {
				document.getElementById("moveIn1").disabled = false;
				document.getElementById("moveOut1").disabled = false;
			}
			else
				document.getElementById("moveIn1").disabled = false;
			var y=document.createElement('option');
			y.setAttribute("class","comboBig1");
			y.text=tmp;
			y.value = tmp1;
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
	function getClasses(examName) {
		<%--getClasesByExamName("classMap", examName, "classes", updateClasses);--%>
		moveoutid();
		document.getElementById("totalStudentsCount").value = '';
		var examType =0;
		var programId =0;
		var deanaryId =0;
		var args ="method=getClassMap&examName=" + examName+"&examType="+examType+"&programId="+programId+"&deanaryName="+deanaryId;
		var url = "AjaxRequest.do";
		requestOperationProgram(url, args, updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMapForMultiSelect(req, "classes");
		moveoutid();
	}
	function resetData() {
		resetFieldAndErrMsgs();
	}

	function getExamNameByYear(year) {
		getExamNameByExamTypeYearWise("examMap", "Both",year, "examName", updateExamName);
	}
	function updateExamName(req) {
		updateOptionsFromMap(req, "examName", "- Select -");
		updateCurrentExam(req, "examName");
	}
	function canclePage(){
		document.location.href="ExamRoomAllotment.do?method=initRoomAllotment";
	}
	function getRoomNoByWorkLocation(workLocationId){
		resetErrMsgs();
		moveoutid1();
		document.getElementById("totalRoomCount").value = '';
		args =  "method=getRoomNoByWorkLocationId&campusName="+workLocationId;
		var url = "roomAllotmentStatus.do";
		requestOperationProgram(url, args, updateRoomMap);
	}
	function updateRoomMap(req) {
		updateOptionsFromMapForMultiSelect(req, "roomNo");
	}
	function roomAllotment() {
		var sda1 = document.getElementById("selectedClasses");
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
		}	
		var sda2 = document.getElementById("classes");
		for(var j=0;j<sda2.length;j++) {
			sda2[j].selected = true;
		}
		var sda3 = document.getElementById("selectedRoomNo");
		for(var i=0;i<sda3.length;i++) {
			sda3[i].selected = true;
		}	
		var sda4 = document.getElementById("roomNo");
		for(var j=0;j<sda4.length;j++) {
			sda4[j].selected = true;
		}
			document.getElementById("method").value = "roomAllotmentNew";	
	}
	function getSelectedClassesStudents(){
		var year = document.getElementById("year").value;
		var selectedClasses = new Array();
		var sda1 = document.getElementById("selectedClasses");
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
			var tmp = sda1.options[i].text;
			selectedClasses[i]=sda1.options[i].value;
		}
		var args =  "method=getTotalStudentsForSelectedClasses&propertyName="+selectedClasses+"&academicYear="+year;
		var url = "ExamRoomAllotment.do";
		requestOperationProgram(url, args, displayTotalStudents);
	}
	function displayTotalStudents(req) {
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
		}
		document.getElementById("totalStudentsCount").value = value;
		
	}
	function getSelectedRoomCapacity(){
		var examName = document.getElementById("examName").value;
		var selectedRooms = new Array();
		var sda1 = document.getElementById("selectedRoomNo");
		for(var i=0;i<sda1.length;i++) {
			sda1[i].selected = true;
			var tmp = sda1.options[i].text;
			selectedRooms[i]=sda1.options[i].value;
		}
		var args =  "method=getTotalRoomCapacity&propertyName="+selectedRooms+"&examId="+examName;
		var url = "ExamRoomAllotment.do";
		requestOperationProgram(url, args, displayRoomCapacity);
	}
	function displayRoomCapacity(req) {
		var responseObj = req.responseXML.documentElement;
		var childNodes = responseObj.childNodes;
		var items = responseObj.getElementsByTagName("option");
		var label, value;
		for ( var i = 0; i < items.length; i++) {
			label = items[i].getElementsByTagName("optionlabel") [0].firstChild.nodeValue;
			value = items[i].getElementsByTagName("optionvalue") [0].firstChild.nodeValue;
		}
		document.getElementById("totalRoomCount").value = value;
	}
</script>

<html:form action="/ExamRoomAllotment">
<html:hidden property="formName" value="examRoomAllotmentForm"/>
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="pageType" value="1"/>
<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; Room Allotment &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Room Allotment</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage"> &nbsp;
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top">
                       <table width="100%" cellspacing="1" cellpadding="2">
							<tr>
								<td height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.fee.academicyear"/>: </div>
								</td>
								<td class="row-even">
									<input type="hidden" id="tempYear" value="<bean:write name='examRoomAllotmentForm' property='year'/>"/>
									<html:select property="year" name="examRoomAllotmentForm" styleId="year" styleClass="combo" onchange="getExamNameByYear(this.value)">
	                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	                     	   					 <cms:renderYear></cms:renderYear>
	                     			</html:select>
								</td>
								<td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Exam:</td>
								<td class="row-even">
									<div align="left">
										<html:select name="examRoomAllotmentForm" property="examId" styleId="examName" styleClass="combo" style="width:200px" onchange="getClasses(this.value)">
										<html:option value="">	<bean:message key="knowledgepro.admin.select" /> </html:option>
											<logic:notEmpty name="examRoomAllotmentForm"	property="examNameList"> 
												<html:optionsCollection property="examNameList"	name="examRoomAllotmentForm" label="value" value="key" />
											</logic:notEmpty>
									</html:select>
									</div>
								</td>
							</tr>
							<tr>
								<td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Campus:</td>
								<td class="row-even">
									<div align="left">
										<html:select property="campus" styleId="campus" name="examRoomAllotmentForm" styleClass="combo" onchange="getRoomNoByWorkLocation(this.value)">
											<html:option value="">	<bean:message key="knowledgepro.admin.select" /> </html:option>
											<logic:notEmpty name="examRoomAllotmentForm"	property="workLocationMap"> 
												<html:optionsCollection property="workLocationMap"	name="examRoomAllotmentForm" label="value" value="key" />
											</logic:notEmpty>
										</html:select>
									</div>
								</td>
								<td height="25" width="20%" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>&nbsp;Date:</div>
									</td>
									<td class="row-even" width="25%"><input type="hidden" id="date1" name="date1" value='<bean:write name="examRoomAllotmentForm" property="date"/>'/>
									<html:text name="examRoomAllotmentForm" property="date" styleId="date" size="10" maxlength="16"/>
									<script
									language="JavaScript">
									$(function(){
										 var pickerOpts = {
												 	            dateFormat:"dd/mm/yy"
												         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#date").datepicker(pickerOpts);
										});
									</script>
									</td>
							</tr>
							<tr>
								<td height="25" class="row-odd" align="right"><span class="Mandatory">*</span>Session:</td>
									<td class="row-even" width="24%" colspan="3">
									<html:select property="session" name="examRoomAllotmentForm" styleId="sessionId" styleClass="combo" >
		                             <html:option value="">--Select--</html:option> 
		                             <c:if test="${examRoomAllotmentForm.sessionMap != null}">
									<html:optionsCollection name="examRoomAllotmentForm" property="sessionMap" label="value" value="key" />
									</c:if>
		                            </html:select>
								</td>
							</tr>
							<tr>
								<td width="20%" height="15" class="row-odd">
	                            <div align="right"><span class="Mandatory">*</span>Classes:</div>
								</td>
								<td width="30%" class="row-even" align="left" colspan="3">
								<table border="0"><tr>
							    <td width="112" height="10">
							    <nested:select property="classes" name="examRoomAllotmentForm"  styleId="classes"  multiple="multiple" size="10" style="width:200px;font-family: Verdana, Arial, Helvetica, sans-serif;
									font-size: 11px; color: #000000; text-decoration: none; font-weight: normal; " >
									<c:if test="${examRoomAllotmentForm.classesMap != null}">
							    <nested:optionsCollection property="classesMap" label="value" value="key" styleClass="comboBig1" name="examRoomAllotmentForm"/>
							    </c:if>
							    </nested:select>
							    </td>
							    <td width="49">
							    <table border="0">
							    <tr><td>
							    <input type="button" align="right" value="&gt&gt;" id="moveOut" onclick="moveinid();getSelectedClassesStudents();"/>
							    </td></tr><tr>
							    <td>
							    <input type="button" align="right" value="&lt&lt;" id="moveIn" onclick="moveoutid();getSelectedClassesStudents();" />
							    </td>
							    </tr>
							    </table>
							    </td>
							    <td width="120" height="10">
								<nested:select property="selectedClasses" name="examRoomAllotmentForm" styleId="selectedClasses" multiple="multiple" size="10" style="width:200px;font-family: Verdana, Arial, Helvetica, sans-serif;
									font-size: 11px; color: #000000; text-decoration: none; font-weight: normal; ">
									<c:if test="${examRoomAllotmentForm.selectedClassesMap != null}">
								<nested:optionsCollection property="selectedClassesMap" label="value" value="key" styleClass="comboBig1" name="examRoomAllotmentForm"/>
								</c:if>
								</nested:select>
								</td>	
								<td width="20%"></td>
								<td width="20%"><strong>Total Student :</strong>&nbsp;&nbsp; </td>
								<td width="20%" style="font-size: 11px;font-weight:bold;"> 
								<html:text property="totalStudentsCount" styleId="totalStudentsCount" name="examRoomAllotmentForm"></html:text>
								</td>
								</tr>
					        	</table>
								</td>
	      					 </tr>
	      					 <tr>
								<td width="20%" height="15" class="row-odd">
	                            <div align="right"><span class="Mandatory">*</span>Room No:</div>
								</td>
								<td width="30%" class="row-even" align="left" colspan="3">
								<table border="0"><tr>
							    <td width="112" height="10">
							    <nested:select property="rooms" name="examRoomAllotmentForm"  styleId="roomNo"  multiple="multiple" size="10" style="width:200px;font-family: Verdana, Arial, Helvetica, sans-serif;
									font-size: 11px; color: #000000; text-decoration: none; font-weight: normal;">
									<c:if test="${examRoomAllotmentForm.collectionMap != null}">
							    <nested:optionsCollection property="collectionMap" label="value" value="key" styleClass="comboBig1" name="examRoomAllotmentForm"/>
							   </c:if>
							    </nested:select>
							    </td>
							    <td width="49">
							    <table border="0">
							    <tr><td>
							    <input type="button" align="right" value="&gt&gt;" id="moveOut1" onclick="moveinid1();getSelectedRoomCapacity();"/>
							    </td></tr><tr>
							    <td>
							    <input type="button" align="right" value="&lt&lt;" id="moveIn1" onclick="moveoutid1();getSelectedRoomCapacity();"/>
							    </td>
							   
							    </tr>
							    </table>
							    </td>
							    <td width="120" height="10">
								<nested:select property="selectedRooms" name="examRoomAllotmentForm" styleId="selectedRoomNo"  multiple="multiple" size="10" style="width:200px;font-family: Verdana, Arial, Helvetica, sans-serif;
									font-size: 11px; color: #000000; text-decoration: none; font-weight: normal;">
									<c:if test="${examRoomAllotmentForm.selectedRoomsMap != null}">
								<nested:optionsCollection property="selectedRoomsMap" label="value" value="key" styleClass="comboBig1" name="examRoomAllotmentForm" />
								</c:if>
								</nested:select>
								</td>	
								<td width="20%"></td>
								<td width="20%"><strong>Total Room Capacity :</strong>&nbsp;&nbsp; </td>
								<td width="20%"style="font-size: 11px;font-weight:bold;">
								<html:text property="totalRoomCount" styleId="totalRoomCount" name="examRoomAllotmentForm"></html:text>
								</td>
								</tr>
					        	</table>
								</td>
	      					 </tr>
						</table>
                       </td>
                       <td width="5" height="30"  background="images/right.gif"></td>
                     </tr>
                     <tr>
                       <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                       <td background="images/05.gif"></td>
                       <td><img src="images/06.gif" /></td>
                     </tr>
                   </table></td>
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
			   				  <html:submit property="" styleId="print" styleClass="formbutton" value="Submit" onclick="roomAllotment()"></html:submit>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetData()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
if(document.getElementById("tempYear") != null && document.getElementById("tempYear").value != null&& document.getElementById("tempYear").value !=""){
	document.getElementById("year").value=document.getElementById("tempYear").value;
}
</script>