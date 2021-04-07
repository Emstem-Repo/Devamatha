<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>

</head>
<script type="text/javascript">
function resetFields(){
	resetFieldAndErrMsgs();
}
function cancelPage() {
	document.location.href = "downloadApplication.do?method=initDownloadApplication";
}
function exportToExcel() {
	document.getElementById("method").value="exportToExcel";
}
function getDetails() {
	document.getElementById("method").value="getEmpOnlineResumeDetails";
}

function viewResume(id) {
	document.location.href = "downloadApplication.do?method=getEmpOnlineDetailsForPrint&employeeId="+id;
}

function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
                  inputObj.value="on";
            }
    }
}

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                        inputObj.value="on";
                  }else{
                	  inputObj.value="off";	
                      }   
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }
}

function checkValue(count){
	 var check = document.getElementById("checked"+count).checked;
	 if(check){
		 document.getElementById("checked"+count).value = "on";
	 }else{
		 document.getElementById("checked"+count).value = "off";
		 }
     
}
var print = "<c:out value='${downloadApplicationForm.printPage}'/>";
	if(print.length != 0 && print == "true"){
		var url = "downloadApplication.do?method=printResume";
		myRef = window
				.open(url, "ViewResume",
						"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
		}


	
		
	
</script>


<html:form action="/downloadApplication" method="post">
<html:hidden property="method" styleId="method" value="getEmpOnlineResumeDetails"/>	
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="downloadApplicationForm" />
<html:hidden property="employeeId" styleId="employeeId" name="downloadApplicationForm" />

<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Download Application &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Download Application</strong></td>
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
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
			<c:choose>
				<c:when test="${!downloadApplicationForm.isCjc}">
			<td height="25" class="row-odd" width="25%"><div align="right">Department:</div></td>
		        <td height="25" class="row-even">
		        	<label>
						<html:select property="departmentId" styleId="departmentId" styleClass="combo">
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="downloadApplicationForm" property="departmentList">
									<html:optionsCollection property="departmentList" label="name" value="id" />
								</logic:notEmpty>
							</html:select> 
					</label></td>
					</c:when>
					<c:otherwise>
					<td height="25" class="row-odd" width="25%"><div align="right">Subject:</div></td>
		        <td height="25" class="row-even">
		        	<label>
						<html:select property="empSubjectId" styleId="empSubjectId" styleClass="combo">
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="downloadApplicationForm" property="empSubjects">
									<html:optionsCollection property="empSubjects" label="value" value="key" />
								</logic:notEmpty>
							</html:select> 
					</label></td>
					</c:otherwise>
					</c:choose>
		        <td height="25" class="row-odd"><div align="right" id="regLabel">Post Applied:</div></td>
		        <td height="25" class="row-even" align="left">
		        <label>
					<html:select property="designationId" styleId="designationId" >
								 	 <html:option value="">- Select -</html:option>
								   		<html:option value="Teaching">Teaching</html:option>
								   		<html:option value="Non-Teaching">Non-Teaching</html:option>
										<html:option value="Guest">Guest </html:option>
								   	 </html:select>
					</label></td>
				
	       </tr>
           <tr>
             	<td height="25" class="row-odd"><div align="right" id="regLabel">Qualification level:</div></td>
		        <td height="25" class="row-even" align="left"> <label>
					<html:select property="qualificationId" styleId="qualificationId" styleClass="combo">
							<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="downloadApplicationForm" property="qualificationList">
									<html:optionsCollection property="qualificationList" label="name" value="id" />
								</logic:notEmpty>
							</html:select> 
					</label></td>
               <td height="25" class="row-odd">
					<div align="right"><bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
				</td>
				<td class="row-even">
						<html:text name="downloadApplicationForm" property="startDate" styleId="startDate" size="11" maxlength="11" />
							<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'downloadApplicationForm',
										// input name
										'controlname' :'startDate'
									});
							</script>
				</td>
				
              </tr>
              <tr>
              <td height="25" class="row-odd">
				 	<div align="right"><bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
				</td>
				<td height="25" class="row-even">
					<html:text property="endDate" styleId="endDate" size="11" maxlength="11" ></html:text>
					<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'downloadApplicationForm',
								// input name
								'controlname' :'endDate'
								});
					</script>
				</td>
				<td height="25" class="row-odd">
					<div align="right">Name:</div>
				</td>
				<td class="row-even">
					<html:text name="downloadApplicationForm" property="name" styleId="name" />
				</td>
				
              </tr>
              <tr>
              <td height="25" class="row-odd">
					<div align="right">Application Number:</div>
				</td>
				<td class="row-even">
					<html:text name="downloadApplicationForm" property="applnNo" styleId="applnNo" />
				</td>
				<td class="row-odd">
					<div align="right">Online Resume Status:</div>
				</td>
				<td  height="25" class="row-even" colspan="3">
	              <div align="left">
	             
                      <html:select property="status"  styleId="status"  name="downloadApplicationForm" >
                	     <html:option value="">-Select-</html:option>
                	     <html:option value="Application Submitted Online">Application Submitted Online</html:option>
                	     <html:option value="Forwarded">Forwarded</html:option>
                	     <html:option value="Not Selected">Not Selected</html:option>
                	     <html:option value="Pending">Pending</html:option>
                	     <html:option value="Selected">Selected</html:option>
                	     <html:option value="Shortlisted">Shortlisted</html:option>
                	</html:select> 
                	</div>
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
									<html:submit property="" styleClass="formbutton" onclick="getDetails()" value="Search"></html:submit>
										
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>
							</div>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <logic:notEmpty property="downloadEmployeeResumeTOs" name="downloadApplicationForm">
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
								<%-- 	<td align="center" height="25" class="row-odd">
									<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> select
									</td>--%>
									<td align="center" height="25" class="row-odd">
									<c:choose>
                            <c:when test="${downloadApplicationForm.isCjc }">
                                <div align="center">Subject</div>
                            </c:when>
                            <c:otherwise>
                                <div align="center">Department</div>
                            </c:otherwise>
                            </c:choose>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center">Post Applied</div>
									</td>
									<td class="row-odd">
									<div align="center">Name</div>
									</td>
									<td class="row-odd">
									<div align="center">Application No</div>
									</td>
									<td class="row-odd">
									<div align="center">Qualification level</div>
									</td>
									<td class="row-odd">
									<div align="center">Date Of Application</div>
									</td>
									<td class="row-odd">
									<div align="center">Resume Status</div>
									</td>
									<td class="row-odd">
									<div align="center">Date</div>
									</td>
									<td class="row-odd">
									<div align="center">View</div>
									</td>
								</tr>
								
									
									<nested:iterate id="to" property="downloadEmployeeResumeTOs" name="downloadApplicationForm" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<%-- <td width="4%" height="25">
										<div align="center">
										<nested:checkbox property="checked" onclick="unCheckSelectAll()"> </nested:checkbox>
										</div>
										</td>--%>
										<td align="center" width="18%" height="25"><c:choose>
                            <c:when test="${downloadApplicationForm.isCjc }">
                                <nested:write property="empSubjectName" />
                            </c:when>
                            <c:otherwise>
                                <nested:write property="departmentName" />
                            </c:otherwise>
                            </c:choose></td>
										<td align="center" width="10%" height="25"><nested:write
											 property="designationName" /></td>
										<td align="center" width="17%" height="25"><nested:write
											 property="employeeName" /></td>
										<td align="center" width="9%" height="25"><nested:write
											 property="applicationNO" /></td>
										<td align="center" width="10%" height="25"><nested:write
											 property="qualificationId" /></td>
										<td align="center" width="11%" height="25"><nested:write
											 property="dateOfApplication" /></td>
										<td align="center" width="11%" height="25"><nested:write
											 property="currentStatus" /></td>
										<td align="center" width="11%" height="25"><nested:write
											 property="currentDate" /></td>
										<td width="8%" height="25">
										<div align="center"><img src="images/View_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="viewResume('<nested:write  property="id" />')" /></div>
										</td>
									</nested:iterate>
								
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
				</logic:notEmpty>
				<tr>
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="45%" height="35">
								<div align="right">
								<logic:notEmpty property="downloadEmployeeResumeTOs" name="downloadApplicationForm">
									<html:submit property="" styleClass="formbutton" onclick="exportToExcel()" value="Export To Excel"></html:submit>
								</logic:notEmpty>
								</div>
								</td>
								<td width="2%"></td>
								<td width="53%">
								
								<logic:notEmpty property="downloadEmployeeResumeTOs" name="downloadApplicationForm">
									<div align="left">
										<html:button property="" styleClass="formbutton" onclick="cancelPage()" value="Cancel"></html:button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										
									</div>
								</logic:notEmpty>
								
							</td>
						</tr>
						</table>
					</td>
					<td valign="top" class="news"></td>
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
<!--<script type="text/javascript">
document.getElementById("status").value = "";
</script>

--></html:html>
