<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<script type="text/javascript">
	function getClasses(year) {
		document.getElementById("year").value = year;
		//getClassesByYearInMuliSelect("classMap", year, "selectedClasses", updateClasses);
		document.getElementById("method").value = "setPeriodEntry";
		document.periodForm.submit();
	}
	function updateClasses(req) {
		updateOptionsFromMapForMultiSelect(req, "selectedClasses");
	}

	function loadPeriod(id) {
		document.location.href = "PeriodEntry.do?method=loadPeriod&id=" + id;
	}
	function updatePeriod() {
		resetErrMsgs();
		document.getElementById("method").value = "updatePeriod";
		document.periodForm.submit();
	}
	function addPeriod() {
		document.getElementById("method").value = "addPeriod";
		document.periodForm.submit();
	}
	function deletePeriod(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "PeriodEntry.do?method=deletePeriod&id=" + id;
		}
	}
	function clearField(field){
		if(field.value == "00")
			field.value = "";
	}
	function checkForEmpty(field){
		if(field.value.length == 0){
			field.value="00";
		}
	}
	function resetPeriodEntry() {
		var d = new Date();
		var year = d.getFullYear();
		document.getElementById("periodName").value = "";
		document.getElementById("shtime").value = "00";
		document.getElementById("smtime").value = "00";
		document.getElementById("enhtime").value = "00";
		document.getElementById("enmtime").value = "00";
		document.getElementById("year").value = year;
		var destination = document.getElementById("selectedClasses");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1].selected = false;
		}
		resetErrMsgs();
	}	
	function checkNumber(field){
		if(isNaN(field.value)){
			field.value = "00";
		}
	}
</script>
<html:form action="/PeriodEntry" method="post">
	<c:choose>
		<c:when test="${periodOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updatePeriod" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addPeriod" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="periodForm" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs"><bean:message key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;
			Period Entry &gt;&gt;</span></span></td>
	  </tr>
	
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.attn.period.entry"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">
	        <FONT color="blue">* Note: Please Enter Time in 24 Hours Format </FONT>
				<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
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
	                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                    <tr >
	
							<td width="13%" height="25" class="row-odd" valign="top">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.interview.Year" /></div>
							</td>
							<td width="13%" class="row-even" align="left" valign="top">
							<input
								type="hidden" id="tempyear" name="tempyear"
								value='<bean:write name="periodForm" property="year"/>' />
							<html:select property="year" styleClass="combo" styleId="year" 
								onchange="getClasses(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.select" />-</html:option>
								<cms:renderAcademicYear></cms:renderAcademicYear>
							</html:select></td>

	                      <td height="25" class="row-odd" valign="top"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendance.class.col"/></div></td>
	                      <td class="row-even" align="left">
						<nested:select property="selectedClasses" styleClass="body" multiple="multiple" size="10"  styleId="selectedClasses" style="width:250px">
							<nested:optionsCollection name="periodForm" property="classMap" label="value" value="key" styleClass="comboBig"/>
						</nested:select>
						 </td>
	                      <td class="row-odd" valign="top"  width="10%"><div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.attn.period.period.name"/></div></td>
	                      <td class="row-even" align="left" valign="top">
	                      <nested:select property="periodName" styleClass="combo" styleId="periodName">
                     		<html:option value=""><bean:message key="knowledgepro.select" /></html:option>
                     		<html:option value="Hour 1">Hour 1</html:option>
                     		<html:option value="Hour 2">Hour 2</html:option>
                     		<html:option value="Hour 3">Hour 3</html:option>
                     		<html:option value="Hour 4">Hour 4</html:option>
                     		<html:option value="Hour 5">Hour 5</html:option>
                     		<html:option value="Hour 6">Hour 6</html:option>
                     		<html:option value="Hour 7">Hour 7</html:option>
                     		<html:option value="Hour 8">Hour 8</html:option>
                     		<html:option value="Hour 9">Hour 9</html:option>
                     		<html:option value="Hour 10">Hour 10</html:option>
                     		<html:option value="Period (S) 1">Period (S) 1</html:option>
                     		<html:option value="Period (S) 2">Period (S) 2</html:option>
                     		<html:option value="Period (E) 1">Period (E) 1</html:option>
                     		<html:option value="Period (E) 2">Period (E) 2</html:option>
                     		<html:option value="Period (E) 3">Period (E) 3</html:option>
                     		<html:option value="Period (E) 4">Period (E) 4</html:option>
                     		<html:option value="Period (E) 5">Period (E) 5</html:option>
                     		<html:option value="Period (M) 1">Period (M) 1</html:option>
                     		<html:option value="Period (M) 2">Period (M) 2</html:option>
                     		<html:option value="Period (M) 3">Period (M) 3</html:option>
                     		<html:option value="Period (Mph) 1">Period (Mph) 1</html:option>
                     		<html:option value="Period (Mph) 2">Period (Mph) 2</html:option>
                     		<html:option value="Period (Mph) 3">Period (Mph) 3</html:option>
                     		<html:option value="Period (Mph) 4">Period (Mph) 4</html:option>
                     		<html:option value="Period (Mph) 5">Period (Mph) 5</html:option>
                     		<html:option value="Period (Mph) 6">Period (Mph) 6</html:option>
                     		<html:option value="Period (Mph) 7">Period (Mph) 7</html:option>
                     		<html:option value="Period (Mph) 8">Period (Mph) 8</html:option>
                     		<html:option value="Period (X) 1">Period (X) 1</html:option>
                     		<html:option value="Period (X) 2">Period (X) 2</html:option>
                     		<html:option value="Period (X) 3">Period (X) 3</html:option>
                     		<html:option value="Period (X) 4">Period (X) 4</html:option>
                     		<html:option value="Period (P) 1">Period (P) 1</html:option>
                     		<html:option value="Period (P) 2">Period (P) 2</html:option>
                     		<html:option value="Period (P) 3">Period (P) 3</html:option>
                     		<html:option value="Period (P) 4">Period (P) 4</html:option>
						</nested:select>
	                      </td>
	                    </tr>
	                    <tr >
	                      <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.attn.period.from.time"/></div></td>
	                      <td width="20%" class="row-even" align="left" ><html:text name="periodForm" property="startHours" styleClass="Timings" styleId="shtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="periodForm" property="startMins" styleClass="Timings" styleId="smtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      <td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key = "knowledgepro.attn.period.to.time"/></div></td>
	                      <td width="19%" class="row-even" align="left"><html:text name="periodForm" property="endHours" styleClass="Timings" styleId="enhtime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  <html:text name="periodForm" property="endMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/></td>
	                      
	                      
	                      
	                      <td width="10%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Session</div></td>
	                       <td width="20%" class="row-even" align="left" >
	                      <nested:radio property="session" value="am" >Morning</nested:radio>
							<nested:radio property="session" value="pm" >Afternoon</nested:radio>
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
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
				<tr>
	            <td width="45%" height="35"><div align="right">
				<c:choose>
					<c:when test="${periodOperation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Update"
							onclick="updatePeriod()"></html:button>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Submit"
							onclick="addPeriod()"></html:button>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="53%"><c:choose>
						<c:when test="${periodOperation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetPeriodEntry()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>				
			<tr><td style="height: 10px" align="center" colspan="3"></td></tr>			
				</table>
			</td>
	          </tr>

	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5"></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5"></td>
	          </tr>
	          <tr>
	
	            <td width="5"  background="images/left.gif"></td>
	            <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td width="20%" class="row-odd" align="center"><bean:message key = "knowledgepro.admin.academicyear"/></td>
	                  <td width="20%" class="row-odd" align="center"><bean:message key = "knowledgepro.attendance.activityattendence.class"/></td>
	                  <td width="20%"class="row-odd" align="center"><bean:message key = "knowledgepro.attendanceentry.period"/></td>
	                  <td width="10%" height="25" class="row-odd" align="center"><bean:message key = "knowledgepro.attn.period.fromtime"/></td>
	                  <td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.attn.period.totime"/></td>
	                   <td width="16%" class="row-odd" align="center">session</td>
	                  <td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.edit"/> </td>
	                  <td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.delete"/></td>
	                </tr>
					<logic:notEmpty name = "periodForm" property="periodTOList">
					<logic:iterate id = "periodList" name = "periodForm" property="periodTOList" indexId="count">
						<bean:define id="year1" property="classSchemewiseTO.curriculumSchemeDurationTO.academicYear" name="periodList" type="java.lang.Integer"></bean:define>
						<% year1= year1.intValue(); %>
		               <c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
	   					  <td align="center" class="bodytext"><bean:write name="periodList" property="classSchemewiseTO.curriculumSchemeDurationTO.academicYear" />-<%=year1+1 %></td>
	   					  <td align="center" class="bodytext"><bean:write name="periodList" property="classSchemewiseTO.classesTo.className" /></td>
		                  <td align="center"><bean:write name = "periodList" property="name"/></td>
		                  <td height="25" align="center" >
		                  <bean:write name = "periodList" property="startTime"/></td>
		                  <td align="center" ><bean:write name = "periodList" property="endTime"/></td>
		                   <td align="center" ><bean:write name = "periodList" property="session"/></td>
		                  <td align="center" ><img src="images/edit_icon.gif" width="16" height="18"
							style="cursor:pointer"	onclick="loadPeriod('<bean:write name="periodList" property="id"/>')"></td>
		                  <td align="center" ><img src="images/delete_icon.gif" width="16" height="16"
						  style="cursor:pointer" onclick="deletePeriod('<bean:write name="periodList" property="id"/>')"></td>
		                </tr>
					</logic:iterate>
					</logic:notEmpty>
	            </table></td>
	            <td  background="images/right.gif" width="5" height="54"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" ></td>
	          </tr>
	
	        </table>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table>
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
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
</script>	