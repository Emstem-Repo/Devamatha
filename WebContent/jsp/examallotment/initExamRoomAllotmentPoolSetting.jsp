<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@page import="java.util.Map,java.util.HashMap"%>
   <script type="text/javascript" src="js/auditorium/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<script type="text/javascript">
function editPoolWiseSetting(poolName,midOrEnd,schemeNo,courseId) {
	    if(midOrEnd=="Mid Sem"){
	    	midOrEnd="M";
	    }else{
	    	midOrEnd="E";
		    }
	     document.location.href = "examRoomAllotmentPool.do?method=editPoolWiseSettingDetails&poolName="+poolName+"&midOrEndSem="+midOrEnd+"&semesterNo="+schemeNo+"&selectedCourses="+courseId;
	}
	function deletePoolWiseSetting(poolId,midOrEnd,schemeNo) {
		if(midOrEnd=="Mid Sem"){
		    midOrEnd="M";
	    }else{
	    	midOrEnd="E";
		    }
		 $.confirm({
				'message'	: 'Are you sure you want to delete this entry?',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
							document.location.href = "examRoomAllotmentPool.do?method=deleteRoomAllotPoolDetails&poolName="+poolId+"&midOrEndSem="+midOrEnd+"&semesterNo="+schemeNo;
						}
					},
 	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
	}
	function resetRoomPoolWise() {
		document.getElementById("poolName").value = "";
		document.getElementById("schemeNo").value = "";
		document.getElementById("midOrEndSem").value = "";
		resetErrMsgs();

//	 resetFieldAndErrMsgs();
	}
	function moveoutid()
	{
		var sda = document.getElementById('courseValue');
		var len = sda.length;
		var sda1 = document.getElementById('unSelectesCourses');
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
		var sda = document.getElementById('courseValue');
		var sda1 = document.getElementById('unSelectesCourses');
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
	function getCoursesValue(){
		var listCourses=new Array(); 
			var mapTo1 = document.getElementById('courseValue');
			var len1 = mapTo1.length;
			for(var k=0; k<len1; k++)
			{
				listCourses.push(mapTo1[k].value);
		}
		document.getElementById("selectedCourses").value=listCourses;
	}
function getcourseMapBySem(){
	var schemeNo=document.getElementById("schemeNo").value;
	var midOrEndSem=document.getElementById("midOrEndSem").value;
	if(schemeNo!=""){
	document.location.href = "examRoomAllotmentPool.do?method=getCourseListByMidOrEndAndSchemeNo&midOrEndSem="+midOrEndSem+"&semesterNo="+schemeNo;
	}else{
	document.location.href = "examRoomAllotmentPool.do?method=getRoomAllotMentPoolWiseDetailsList&midOrEndSem="+midOrEndSem;
	}
}
function cancelRoomPoolWise(){
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>

<html:form action="/examRoomAllotmentPool" method="post">
	<html:hidden property="formName" value="examRoomAllotmentPoolForm" />
	<html:hidden property="pageType" value="2"/>
    <html:hidden property="selectedCourses" styleId="selectedCourses"/>	
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateExamRoomAllotPoolWiseDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addExamRoomAllotPoolWise" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.exam.allotment" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.exam.allotment.pool.creation" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.allotment.pool.creation" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
							</td>
						</tr>
						<tr>
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
						<% boolean disable=false; %>
						<logic:equal value="true" property="isDisable" name="examRoomAllotmentPoolForm">
						<%disable=true ; %>
						</logic:equal>
						    <td width="20%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.allotment.pool.mid.end.sem" />:</div>
							</td>
							<td width="30%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="midOrEndSem" name="examRoomAllotmentPoolForm" styleId="midOrEndSem" styleClass="combo" onchange="getcourseMapBySem()" disabled="<%=disable %>">
                            <html:option value="M">Mid Sem</html:option>
                            <html:option value="E">End Sem</html:option>
                            </html:select>
                            </div>
							</td>
						    <td width="20%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.fee.semister" />:</div>
							</td>
							<td width="30%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="semesterNo" name="examRoomAllotmentPoolForm" styleId="schemeNo" styleClass="combo" onchange="getcourseMapBySem()" disabled="<%=disable %>">
                            <html:option value="">--Select--</html:option>
                            <html:option value="1">1</html:option>
                            <html:option value="2">2</html:option>
                            <html:option value="3">3</html:option>
                            <html:option value="4">4</html:option>
                            <html:option value="5">5</html:option>
                            <html:option value="6">6</html:option>
                            <html:option value="7">7</html:option>
                            <html:option value="8">8</html:option>
                            <html:option value="9">9</html:option>
                            <html:option value="10">10</html:option>
                            </html:select>
                            </div>
							</td>
						</tr>
						<tr>
						<td width="20%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.admin.course" />:</div>
							</td>
							<td width="30%" class="row-even" align="left" >
							<table border="0"><tr>
						    <td width="112" height="15">
						    <nested:select property="unSelectesCourses" name="examRoomAllotmentPoolForm"  styleId="unSelectesCourses"  multiple="multiple" size="8" style="width:320px" styleClass="body">
						    <nested:optionsCollection property="courseMap" label="value" value="key" styleClass="comboBig" name="examRoomAllotmentPoolForm"/>
						    </nested:select>
						    </td>
						    <td width="49">
						    <table border="0">
						    <tr><td>
						    <input type="button" align="right" value="&gt&gt;" id="moveOut" onclick="moveinid()"/>
						    </td></tr><tr>
						    <td>
						    <input type="button" align="right" value="&lt&lt;" id="moveIn" onclick="moveoutid()" />
						    </td>
						    </tr>
						    </table>
						    </td>
						    <td width="120" height="15">
					<nested:select property="courseValue" name="examRoomAllotmentPoolForm" styleId="courseValue"  multiple="multiple" size="8" style="width:320px" styleClass="body">
							<c:if test="${examRoomAllotmentPoolForm.selectedCourseMap != null}">
							<nested:optionsCollection property="selectedCourseMap" label="value" value="key" styleClass="comboBig" name="examRoomAllotmentPoolForm"/>
					            </c:if>	
						</nested:select>
					</td>	
							</tr>
				        	</table>
					</td>
					 <td width="20%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.exam.allotment.pool.creation.name" />:</div>
							</td>
							<td width="30%" height="25" class="row-even">
                            <div align="left">
                            <html:select property="poolName" name="examRoomAllotmentPoolForm"  styleId="poolName" styleClass="combo" disabled="<%=disable %>">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="examRoomAllotmentPoolForm" property="examAllotRoomPoolMap" label="value" value="key" />
						    </html:select>
                            </div>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update"  styleId="Update" onclick="getCoursesValue()"></html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit"  styleId="Submit" onclick="getCoursesValue()"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<c:choose><c:when test="${operation != 'edit'}">
									<td width="2%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetRoomPoolWise()" styleId="reset"></html:button>
										</td>
										<td width="2%"></td>
									</c:when>
									</c:choose>
									<td width="49%">
							              <html:button property="" styleClass="formbutton"
												value="Cancel" onclick="cancelRoomPoolWise()" styleId="cancel"></html:button>
										</td>	
								</tr>
							</table>
							</td>
						</tr>
					<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="10%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="10%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.exam.allotment.pool.creation.name" /></td>
									<td width="40%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.admin.course" /></td>
									<td width="10%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.exam.allotment.pool.mid.end.sem" /></td>
									<td width="10%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.fee.semister" /></td>			
									<td width="10%" class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td width="15%" class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<c:set var="count" value="0" />
								<logic:notEmpty name="examRoomAllotmentPoolForm" property="midOrEndMap" >
								<logic:iterate id="midEndMap" name="examRoomAllotmentPoolForm" property="midOrEndMap" >
								<logic:iterate id="schemeMap" name="midEndMap" property="value" >
								 
								<logic:iterate id="poolMap" name="schemeMap" property="value" >
										<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
										 
									<td width="10%" height="25" align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="10%" height="25" align="center"><bean:write
										name="poolMap" property="value.poolName"/> </td>
									<td width="40%" height="25" align="center"><table width="100%">
									<logic:notEmpty property="value.courseList" name="poolMap">
									<logic:iterate id="courseID" property="value.courseList" name="poolMap">
									<tr align="left"><td align="left"><bean:write name="courseID"/></td></tr>
									</logic:iterate></logic:notEmpty>
									</table></td>
									<td width="10%" height="25" align="center"><bean:write
										name="poolMap" property="value.midOrEndSem"/> </td>
									<td width="10%" height="25" align="center"><bean:write
										name="poolMap" property="value.schemeNo"/> </td>		
									<td width="10%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editPoolWiseSetting('<bean:write name="poolMap" property="value.poolId"/>','<bean:write name="poolMap" property="value.midOrEndSem"/>','<bean:write name="poolMap" property="value.schemeNo"/>'
										,'<bean:write name="poolMap" property="value.courseIds"/>')">
									</div>
									</td>
									<td width="15%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deletePoolWiseSetting('<bean:write name="poolMap" property="value.poolId"/>','<bean:write name="poolMap" property="value.midOrEndSem"/>','<bean:write name="poolMap" property="value.schemeNo"/>')">
									</div>
									</td>
									<c:set var="count" value="${count+1}"></c:set>
								</logic:iterate>
								</logic:iterate>	
								</logic:iterate>
								</logic:notEmpty>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
