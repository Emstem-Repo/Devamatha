<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<script type="text/javascript">	
	function getClasses() {
		var year = document.getElementById("academicYear").value;
		var evenOrOdd="";
		if(document.getElementById("odd").checked==true)
			evenOrOdd = document.getElementById("odd").value;
		else if(document.getElementById("even").checked==true)
			evenOrOdd = document.getElementById("even").value;
		getClassesForAttendanceByYear("classMap", year, evenOrOdd, "class", updateClasses);
	}
	function updateClasses(req) {
		updateOptionsFromMap(req, "class", "- Select -");
	}
	function resetFieldsAndErrors() {
		document.getElementById("academicYear").value = "";
		document.getElementById("class").value = "";
		document.getElementById("timeHours").value = "";		
		document.getElementById("timeMins").value = "";
		
		resetErrMsgs();
	}
	function editAttendanceTime(id, classId, timeHours, timeMins, year, evenOrOdd) {
		document.location.href = "AttendanceEntry.do?method=editAttendanceTimeRestrictionClasswise&origAttTimeHours="+timeHours+"&origAttTimeMins="+timeMins+"&timeHours="+timeHours
					+"&timeMins="+timeMins+"&classId="+classId+"&attTimeRestrictionId="+id+"&year="+year+"&evenOrOdd="+evenOrOdd+"&academicYear="+year;
	}
	function deletettendanceTime(id, classId) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "AttendanceEntry.do?method=deleteAttendanceTimeRestrictionClasswise&attTimeRestrictionId="+id+"&classId="+classId;
		}
	}
</script>
<html:form action="/AttendanceEntry" method="post">
	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${operation != null && operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateAttendanceTimeRestrictionClasswise" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveAttendanceTimeRestrictionClasswise" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="method" styleId="method" value="saveAttendanceTimeRestrictionClasswise" />
	<html:hidden property="formName" value="attendanceEntryForm" />
	<html:hidden property="pageType" value="4" />
	<html:hidden property="origAttTimeHours" styleId="origAttTimeHours" />
	<html:hidden property="origAttTimeMins" styleId="origAttTimeMins" />
	<html:hidden property="attTimeRestrictionId" styleId="attTimeRestrictionId" />
	
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance.restrict" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.attendance.restrict.for.class"/> &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.timetable.for.class"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT><Font color="red"><bean:write name="attendanceEntryForm" property="errormsg"></bean:write></Font>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
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
									<div align="right"><font style="font-size: 11px"><bean:message
										key="knowledgepro.interview.Year" /></font></div>
									</td>
									<td  class="row-even"><input type="hidden"
										id="yr" name="yr"
										value='<bean:write name="attendanceEntryForm" property="academicYear"/>' />
									<html:select property="year" styleClass="combo" styleId="academicYear"
										onchange="getClasses()">
										<html:option value="">
											<bean:message key="knowledgepro.select" />-</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td height="25" class="row-odd">
									<div align="right"><font style="font-size: 11px">Semester</font></div>
									</td>
									<td  class="row-even">
										<html:radio  styleId="odd" property="evenOrOdd" value='odd' onclick="getClasses()"/> Odd 
										<html:radio  styleId="even" property="evenOrOdd" value='even' onclick="getClasses()"/> Even
									</td>										
								</tr>
								
								
	                    		<tr>									
									<td  height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><font style="font-size: 11px"><bean:message
										key="knowledgepro.attendance.class.col" /></font></div>
									</td>
									<td class="row-even">
									<nested:select property="classId" styleClass="comboLarge" styleId="class" onchange="disableDown()">
										<html:option value="">
										<bean:message key="knowledgepro.select" />-</html:option>
										<logic:notEmpty  name="attendanceEntryForm" property="classMap">
										<html:optionsCollection name="attendanceEntryForm" property="classMap" label="value" value="key" />
										</logic:notEmpty>
									</nested:select>
									</td>
									
									<td height="25" class="row-odd">Time(in 24hrs format)(hrs/mm)
									</td>
									<td  class="row-even">
									<html:text styleId="timeHours" property="timeHours" name="attendanceEntryForm"
										size="2" maxlength="2" onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)" /> : <html:text styleId="timeMins"
										property="timeMins" name="attendanceEntryForm" maxlength="2" size="2"
										onkeypress="return isNumberKey(event)"
										onblur="checkNumber(this)"
										onfocus="clearField(this)" />
									</td>
																		
								</tr>
								
								<tr >
            <td style="height: 10px;" align="center" colspan="4">
            
           		
            </td>
          </tr>
			<tr><td width="50%" colspan="2" align="right">
	           		<c:choose>
						<c:when
								test="${operation != null && operation == 'edit'}">
								<html:submit property="" styleClass="formbutton"
									value="Update" styleId="submitbutton">
								</html:submit>
						</c:when>
						<c:otherwise>
						<html:submit property="" styleClass="formbutton"
						value="Submit" styleId="submitbutton">
						</html:submit>
						</c:otherwise>
					</c:choose>
					</td>
					<td width="50%" colspan="2">&nbsp;
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetFieldsAndErrors()"></html:button>
					</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="4">
					<table width="100%">
						<logic:notEmpty name="attendanceEntryForm" property="attTimeRestrictedList">
							<tr height="30">
								<td class="row-odd" width="10%" align="center">Sl No.</td>
								<td class="row-odd" width="30%" align="center">Class Name</td>
								<td class="row-odd" width="30%" align="center">Time</td>
								<td class="row-odd" width="15%">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
								</td>
								<td class="row-odd" width="15%">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
								</td>
							</tr>
							<logic:iterate id="list" name="attendanceEntryForm" property="attTimeRestrictedList" indexId="count">
								<%count++; %>
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even" height="30">
										</c:when>
								        <c:otherwise>
											<tr class="row-white" height="30">
										</c:otherwise>
									</c:choose>
									<td align="center"><%=count %></td>
									<td align="center"><bean:write name="list" property="className"/></td>
									<td align="center"><bean:write name="list" property="time"/></td>
									<td height="30" align="center">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18"
											onclick="editAttendanceTime('<bean:write name="list" property="id"/>','<bean:write name="list" property="classSchemewiseId"/>','<bean:write name="list" property="timeHours"/>','<bean:write name="list" property="timeMins"/>','<bean:write name="list" property="acadamicYear"/>','<bean:write name="list" property="evenOrOdd"/>')" /></div>
									</td>
									<td height="30">
										<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16"
												onclick="deletettendanceTime('<bean:write name="list" property="id"/>','<bean:write name="list" property="classSchemewiseId"/>')" /></div>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
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
	         
	
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"></td>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
	var yearId = document.getElementById("yr").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}	
</script> 	