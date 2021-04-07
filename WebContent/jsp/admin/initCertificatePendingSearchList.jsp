<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/employee/employeeView.js"></script>

<script type="text/javascript">
function getPrograms(programTypeId) {
	getProgramsByType("programMap",programTypeId,"program",updatePrograms);
}

function updatePrograms(req) {
	updateOptionsFromMap(req,"program","- Select -");
}

function getCourses(programId) {
	getCoursesByProgram("coursesMap",programId,"course",updateCourses);
	
}

function updateCourses(req) {
	updateOptionsFromMap(req,"course","- Select -");
}

function getCertificateStudentEdit(studId,method) {
	document.certificateRequestOnlineForm.method.value=method;
	document.certificateRequestOnlineForm.selectedStudentId.value=studId;
	document.certificateRequestOnlineForm.submit();
}
function cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}


function resetFeeReport()	{
	
	document.getElementById("programtype").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetOption("semister");
	document.getElementById("applnnoID").value="";
	document.getElementById("regNoID").value="";	
	document.getElementById("name").value="";
	resetErrMsgs();
}

function getRegisterNumber(ipAddress) 
{
	if(document.getElementById("regNoID").value=='')
	{	
		var args = "method=getRegisterNumber&ipAddress="+ipAddress;
		var url = "AjaxRequest.do";
		requestOperation(url, args, updateRegisterNo);
	}	

}

function updateRegisterNo(req) {
	updateOptionsFromMapValues(req, "regNoID");
}
</script>
<html:form action="/certificateRequest" enctype="multipart/form-data" method="POST">
<html:hidden property="method" value="getSearchedStudents" styleId="method"/>
<html:hidden property="pageType" value="13"/>
<html:hidden property="formName" value="certificateRequestOnlineForm"/>
<html:hidden property="selectedStudentId" value="" />
	
<table width="98%" border="0">
   <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.certificate.searchpending.list" /> &gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.certificate.searchpending.list"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
	
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="35" valign="top" class="body" >
                <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       	                <tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> </FONT></div>
							<div id="errorMessage" align="left"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
							property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							<br>
							</html:messages> </FONT></div>
							</td>
					</tr>
					</table>
				</td>
              </tr>
              <tr>
                <td height="35" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    
              <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                        <tr class="row-white">
			                    <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/></div></td>
			                    <td width="20%" class="row-even"><html:text property="tempRegisterNo" styleId="tempRegisterNo" size="15" maxlength="20" /></td>
			                    
			                    <td width="15%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.name"/>:</div></td>
			                    <td width="30%" class="row-even"><html:text property="tempFirstName" styleId="tempFirstName" size="25" maxlength="100" /></td>
			        	</tr>
			        	<tr>
			        		<td height="25" class="row-odd">
							<div align="right"><bean:message key="knowledgepro.employee.holidays.startDate" />:</div>
							</td>
							<td class="row-even">
								<html:text name="certificateRequestOnlineForm" property="startDate" styleId="startDate" size="11" maxlength="11" />
									<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'certificateRequestOnlineForm',
										// input name
										'controlname' :'startDate'
									});
								</script>
							</td>
              
            	 			<td height="25" class="row-odd">
				 			<div align="right"><bean:message key="knowledgepro.employee.holidays.endDate" />:</div>
							</td>
							<td height="25" class="row-even">
								<html:text property="endDate" styleId="endDate" size="11" maxlength="11" ></html:text>
								<script language="JavaScript">
									new tcal( {
									// form name
									'formname' :'certificateRequestOnlineForm',
									// input name
									'controlname' :'endDate'
									});
								</script>
							</td>
							</tr>
							<tr>
							<td height="25" class="row-odd"><div align="right" id="regLabel">Certificate Name:</div></td>
		        			<td height="25" class="row-even" align="left" > <label>
							<html:select property="certificateId" styleId="certificateId" styleClass="combo">
								<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="certificateRequestOnlineForm" property="certificateDetails">
										<html:optionsCollection property="certificateDetails" label="certificateName" value="id" />
									</logic:notEmpty>
								</html:select> 
							</label>
							</td>
							<td height="25" class="row-odd"><div align="right">Status</div></td>
		        			<td height="25" class="row-even" align="left" > <label>
		        			 <html:select property="searchType">
								   		<html:option value="pending">Pending</html:option>
								   		<html:option value="completed">Completed</html:option>
										<html:option value="issued">Issued </html:option>
								   		<html:option value="rejected">Rejected</html:option>
							   </html:select>
							
							</label>
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
					
				</tr>
			
       
				
				<tr>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
				            <td width="49%" height="35"><div align="center">
				               <html:submit property="" styleClass="formbutton" value="Continue"></html:submit>
				               <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
				            </div></td>
				            		
						</tr>
					</table>
					</td>
					
				</tr>
              <tr>
                <td height="10" class="body" ></td>
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