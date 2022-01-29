<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<html:html>
<script type="text/javascript">
function moveoutid()
{
	var sda = document.getElementById('selectedCoursesId');
	var len = sda.length;
	var sda1 = document.getElementById('unselectedCoursesId');
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
	var sda = document.getElementById('selectedCoursesId');
	var sda1 = document.getElementById('unselectedCoursesId');
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
			y.setAttribute("class","comboBig");
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
function saveDetails() {
	var sda1 = document.getElementById("selectedCoursesId");
	for(var i=0;i<sda1.length;i++) {
		sda1[i].selected = true;
	}	
	var sda2 = document.getElementById("unselectedCoursesId");
	for(var j=0;j<sda2.length;j++) {
		sda2[j].selected = true;
	}
		document.getElementById("method").value = "saveExamRoomAllotmentCycleDetails";	
}
function getCoursesBySchemeNo(schemeNo){
	hook=false;
	var msg ='';
	var cycleId = document.getElementById("cycleId").value;
	if(cycleId == " "){
		msg = "Cycle is Required";
	}else{
		var args = "method=getCoursesBySchemeNo&cycleId="+cycleId+"&schemeNo="+schemeNo;
		populateSecondOptionsValuesForMuliSelect(schemeNo, args, "unselectedCoursesId", updateCourses);
	}
	if(msg!=''){
		document.getElementById("errorMessage").innerHTML="Cycle is Required";
	}else{
		document.getElementById("errorMessage").style.display="none";
	}
}
function populateSecondOptionsValuesForMuliSelect(sourceId, args, destinationProperty,
		callback) {
	if (sourceId.length != 0) {
		var destinationOption = document.getElementById(destinationProperty);
		destinationOption.options[0] = new Option("- Loading -", "");
		destinationOption.selectedIndex = 0;
		var url = "examRoomAllotmentCycle.do";
		// make an request to server passing URL need to be invoked and
		// arguments.
		requestOperationProgram(url, args, callback);
	} else {
		var destinationOption = document.getElementById(destinationProperty);
		for (x1 = destinationOption.options.length - 1; x1 > 0; x1--) {
			destinationOption.options[x1] = null;
		}
	}
}
function updateCourses(req) {
	updateOptionsFromMap(req,"unselectedCoursesId","- Select -");
}
function getMidEndAndSessionDetails(id){
	if(id != ''){
		$.ajax({
		      type: "post",
		      url: "examRoomAllotmentCycle.do?method=getMidEndSessionByCycleId",
		      data: {cycleId:id},
		      success:function(data) { 
                     var details = data.split(",");
                     document.getElementById('midSem').innerHTML = details[0];
                     document.getElementById('session').innerHTML = details[1];
			      }  
		});
	}
}
function editDetails(cycleId,schemeId,courseIds){
	document.location.href = "examRoomAllotmentCycle.do?method=editCycleDetails&cycleId="+cycleId+"&schemeNo="+schemeId+"&courseList="+courseIds;
}
function deleteDetails(cycleId,schemeId){
	document.location.href = "examRoomAllotmentCycle.do?method=deleteCycleDetails&cycleId="+cycleId+"&schemeNo="+schemeId;
}
function updateDetails(){
	var sda1 = document.getElementById("selectedCoursesId");
	for(var i=0;i<sda1.length;i++) {
		sda1[i].selected = true;
	}	
	var sda2 = document.getElementById("unselectedCoursesId");
	for(var j=0;j<sda2.length;j++) {
		sda2[j].selected = true;
	}
		document.getElementById("method").value = "updateCycleDetails";	
}
function cancelAction(){
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/examRoomAllotmentCycle" method="post">
<html:hidden property="method" styleId="method" value=""/>	
<html:hidden property="pageType" value="2" />
<html:hidden property="formName" value="examRoomAllotmentCycleForm" />
<html:hidden property="midOrEndSem" styleId="midOrEndSem" name="examRoomAllotmentCycleForm"/>
<html:hidden property="sessionName" styleId="sessionName" name="examRoomAllotmentCycleForm"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Exam Allotment
			<span class="Bredcrumbs">&gt;&gt; Exam Room Allotment Cycle &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Exam Room Allotment Cycle</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="100%" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
            <% boolean disable=false;%>
				<logic:equal value="true" name="examRoomAllotmentCycleForm" property="flag">
				<% disable=true;%>
				</logic:equal>
			<tr>
		        <td width="25%" class="row-odd" ><div align="right">
		      <span class="Mandatory">*</span> Cycle:</div></td>
		        <td width="25%" class="row-even"  >
                      <html:select property="cycleId"  styleId="cycleId"  name="examRoomAllotmentCycleForm" onchange="getMidEndAndSessionDetails(this.value)" disabled='<%=disable%>'>
                     <logic:notEmpty name="examRoomAllotmentCycleForm" property="cycleMap">
                      <option value=" ">-Select-</option>
                      <html:optionsCollection name="examRoomAllotmentCycleForm" property="cycleMap" label="value" value="key" />
                      </logic:notEmpty>
                	</html:select>
                	</td>
				<td class="row-odd" width="10%"><div align="right"><span class="Mandatory">*</span>Scheme No:</div></td>
				 <td  height="40" class="row-even" width="25%"  colspan="2">
	               <div align="left">
	               <input type="hidden" id="tempSchemeNo"  value="<bean:write name="examRoomAllotmentCycleForm" property="schemeNo"/>"/>
                      <html:select property="schemeNo"  styleId="schemeNo"  name="examRoomAllotmentCycleForm"  onchange="getCoursesBySchemeNo(this.value)" disabled='<%=disable%>'>
                     <option value=" ">-Select-</option>
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                      <option value="6">6</option>
                      <option value="7">7</option>
                      <option value="8">8</option>
                      <option value="9">9</option>
                      <option value="10">10</option>
                	</html:select> </div>
                	</td>
	       </tr>
	       <tr height="25">
		        <td width="25%" class="row-odd" ><div align="right">
		      <span class="Mandatory">*</span> Mid/End Sem:</div></td>
		        <td width="25%" class="row-even"  id="midSem">
                	</td>
				<td class="row-odd" width="10%"><div align="right"><span class="Mandatory">*</span>Session:</div></td>
				  <td width="25%" class="row-even" id="session" colspan="2">
                	</td>
	       </tr>
	       <tr height="25">
	       <td class="row-odd" align="right"></td>
	        <td class="row-odd" align="center" > UnSelected Courses </td>
	         <td class="row-odd" align="center" > </td>
	         <td class="row-odd" width="30%" align="center"> Selected Courses </td>
	          <td class="row-odd" width="10%"> </td>
	       </tr>
	        <tr>
					<td width="25%" class="row-odd" align="right">
					Course Name :
					</td>
                	<td width="25%" class="row-even" align="left">	
                	<html:select property="unselectedCourseIds" name="examRoomAllotmentCycleForm"  styleId="unselectedCoursesId"  multiple="multiple" size="10" style="width:350px;" styleClass="body">
							<html:optionsCollection name="examRoomAllotmentCycleForm" property="courseMap" label="value" value="key" styleClass="comboBig"/>
						</html:select>
						</td>
						<td class="row-even" width="10%">&nbsp;&nbsp;&nbsp;
						<input type="button" align="right" value="&gt&gt;" id="moveOut" onclick="moveinid()" height="15"/> 
						<input type="button" align="right" value="&lt&lt;" id="moveIn" onclick="moveoutid()" height="15"/>
						</td>
						<td class="row-even" width="30%" colspan="2">
						<html:select property="courseIds" name="examRoomAllotmentCycleForm"  styleId="selectedCoursesId"  multiple="multiple" size="10" style="width:350px;" styleClass="body">
	         			<logic:notEmpty name="examRoomAllotmentCycleForm" property="selectedCourseMap">
					            <html:optionsCollection name="examRoomAllotmentCycleForm" property="selectedCourseMap" label="value" value="key" styleClass="comboBig" />
						</logic:notEmpty>
						</html:select>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
										<c:choose>
								<c:when
									test="${operation != null && operation == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										onclick="updateDetails()">
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										onclick="saveDetails()">
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose>
							</div>
							</td>
							<td width="2%"></td>
							<td width="5%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetErrorMsgs()"></html:button>
							</div>
							</td>
							<td width="44%" ><html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()">
													</html:button>
							</td>
						</tr>
		<logic:notEmpty name="examRoomAllotmentCycleForm" property="cycleDetailsMap">
			<tr>
					<td valign="top" class="news" colspan="5">
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
							<td height="25" >
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">Cycle</td>
									<td height="25" class="row-odd" align="center" >Scheme No</td> 	
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<c:set var="count" value="0"  />
								<logic:iterate id="idList" name="examRoomAllotmentCycleForm" property="cycleDetailsMap" >
								<logic:iterate id="cycleMap" name="idList" property="value">
								<logic:iterate id="schemeMap" name="cycleMap" property="value">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="20%" height="25" class="row-even" align="center">
												
												<c:out value="${cycleMap.key}" />
												</td>
												<td width="10%" height="25" class="row-even" align="center">
												
												<c:out value="${schemeMap.key}" />
												</td>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editDetails('<c:out value="${idList.key}" />','<c:out value="${schemeMap.key}" />','<c:out value="${schemeMap.value}" />')">
												</div>
												</td>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteDetails('<c:out value="${idList.key}" />','<c:out value="${schemeMap.key}" />')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
											<c:set var="count" value="${count + 1}" />
										</c:when>
										<c:otherwise>
											<tr>
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="20%" height="25" class="row-white" align="center">
												<c:out value="${cycleMap.key}" />
												</td>
												<td width="10%" height="25" class="row-white" align="center">
												<c:out value="${schemeMap.key}" />
												</td>
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editDetails('<c:out value="${idList.key}" />','<c:out value="${schemeMap.key}" />','<c:out value="${schemeMap.value}" />')">
												</div>
												</td>
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteDetails('<c:out value="${idList.key}" />','<c:out value="${schemeMap.key}" />')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
											<c:set var="count" value="${count + 1}" />
										</c:otherwise>
									</c:choose>
									</logic:iterate>
									</logic:iterate>
									</logic:iterate>
							</table>
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
				</tr>	
				</logic:notEmpty>		
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
var sda1 = document.getElementById("selectedCoursesId");
for(var i=0;i<sda1.length;i++) {
	sda1[i].selected = false;
}	
var sda2 = document.getElementById("unselectedCoursesId");
for(var j=0;j<sda2.length;j++) {
	sda2[j].selected = false;
}
var midOrEndSem = document.getElementById("midOrEndSem").value;
if (midOrEndSem != null && midOrEndSem.length != 0) {
	document.getElementById("midSem").innerHTML = midOrEndSem;
}
var sessionName = document.getElementById("sessionName").value;
if (sessionName != null && sessionName.length != 0) {
	document.getElementById("session").innerHTML = sessionName;
}

var tempSchemeNo = document.getElementById("tempSchemeNo").value;
if (tempSchemeNo != null && tempSchemeNo.length != 0) {
	document.getElementById("schemeNo").value = tempSchemeNo;
}
</script>

</html:html>
