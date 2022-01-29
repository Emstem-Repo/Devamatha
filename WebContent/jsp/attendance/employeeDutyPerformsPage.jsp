<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>

<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript" src="js/jquery.js">
	</script>
	<link href="css/styles.css" rel="stylesheet" type="text/css">
	<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
	<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
	<style type="text/css">
			#overlay {
     visibility: hidden;
     position: absolute;
     left: 0px;
     top: 0px;
     width:100%;
     height:100%;
     text-align:center;
     z-index: 1000;
}
	#overlay div {
     width:300px;
     margin: 100px auto;
     background-color: #fff;
     border:1px solid #000;
     padding:15px;
     text-align:center;
}
.ApprovedAudiEvents {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #434366;
	text-decoration: none;
    font-weight: bold;
}
 .panel-success>.panel-heading {
    color: #fbfbfb;
    background-color: #2196f3e3;
    border-color: #2196f3f7;
}
 .table-striped > tbody > tr:nth-child(2n+1) > td {
    background-color: #03a9f440;
}
			</style>
<script type="text/javascript">
function deleteAttendanceEntryDay(id,day) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "attendanceEntryDay.do?method=deleteAttendanceEntryDay&id="
				+ id+"&day="+day;
	}
}
function editAttendanceEntryDay(id) {
	document.getElementById("id").value=id;
	document.getElementById("dupId").value=id;
	document.attendanceEntryForm.method.value="editAttendanceEntryDay";
	document.attendanceEntryForm.submit();
	}


function reActivate(){
	var id = document.getElementById("dupId").value;
	document.location.href = "attendanceEntryDay.do?method=reActivateAttendanceEntryDay&dupId="+id;
}
function resetFormFields(){	
	document.getElementById("day").value = null;
	document.getElementById("date").value = null;
	resetErrMsgs();
	if (document.getElementById("method").value == "updateAttendanceEntryDay") {
	
		document.getElementById("id").value = document.getElementById("orgId").value;
		document.getElementById("day").value = document.getElementById("orgDay").value;
		document.getElementById("date").value = document.getElementById("orgDate").value;
	}
}
</script>
<html:form action="/EmpDutyPerformed">	
		
		<c:choose>
		<c:when test="${department == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateaddEmpDutyPerformed" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="addEmpDutyPerformed" />
		</c:otherwise>
		</c:choose>
		
	<html:hidden property="dutyPerformedId" styleId="id" />
	<html:hidden property="formName" value="attendanceEntryForm" />
	<html:hidden property="pageType" value="1" />
	
	<div class="container px-5 my-5">
	
	<div class="row">
	<FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT>
	</div>
    	<table class="table table-borderless">
    	
    	<tr><th colspan="4"><b>Employee Duty Entry</b></th></tr>
    	<tr>
    		<td align="right"><span class="Mandatory">*</span>Date :</td><td><html:text
								property="dutyPerformedDate" name="attendanceEntryForm" styleId="date" styleClass="TextBox" readonly="true"
								size="20" maxlength="30" /><span class="star"></span>
							<script language="JavaScript">
												new tcal( {
													// form name
													'formname' :'attendanceEntryForm',
													// input name
													'controlname' :'date'
												});
											</script></td>
    		<td align="right"><span class='MandatoryMark'>*</span>Duty:</td><td> <html:textarea property="dutyPerformed" rows="4" cols="50" styleId="duty" name="attendanceEntryForm"/></td>
    	</tr>	
    	<tr><td colspan="4" align="center"><b>
    		<c:choose>
							<c:when test="${department == 'edit'}">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
							</c:when>
							<c:otherwise>
								<html:submit property="" styleClass="formbutton" value="Submit"
										styleId="submitbutton">
								</html:submit>
							</c:otherwise>
							</c:choose>
							<html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button>
    	</b></td></tr>
    	<tr height="20px"></tr>
    	<%-- <tr>
											<td  height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td  class="row-odd">
											<div align="center">Date</div>
											</td>
											<td colspan="2" class="row-odd">
											<div align="center">Duty</div>
											</td>
											
											
										</tr>
    	<logic:iterate id="attList" property="dutyPerformedList" name="attendanceEntryForm"
											 indexId="count">
											 <tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose><td><div align="center"><c:out value="${count + 1}" /></div></td>
											<td align="center"  height="25" colspan="2"><bean:write name="attList"
												property="date" /></td>
												<td align="center"><bean:write name="attList"
												property="dutyPerformed" /></td>
												</tr></logic:iterate> --%>
    	</table>
</div>
	
	</html:form>