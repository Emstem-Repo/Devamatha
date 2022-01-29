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
	
</script>




<html:form action="/downloadApplication" method="post">
<html:hidden property="method" styleId="method" />	
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
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div>
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
            	 <td>
									<display:table export="true" uid="employeeId"  id = "employee" name="sessionScope.EmployeeResume" requestURI="" defaultorder="ascending" pagesize="10" >
									<display:setProperty name="export.excel.filename" value="Employee.xls"/>
									<display:setProperty name="export.xml" value="false" />
									<display:setProperty name="export.csv.filename" value="Employee.csv"/>
								        <display:column media="excel csv" style=" width: 200px;height: 40px" property="count" sortable="true" title="S.NO" class="row-even" headerClass="row-odd" />
										<display:column media="html excel csv" style=" width: 200px;height: 40px" property="employeeName" sortable="true" title="Employee Name" class="row-even" headerClass="row-odd" />
										<display:column media="html excel csv" style=" width: 130px;" property="departmentName" sortable="true" title="Department" class="row-even" headerClass="row-odd"/>
										<display:column media="html excel csv" style=" width: 130px;" property="applicationNO" sortable="true" title="Application Number" class="row-even" headerClass="row-odd"/>
										<display:column media="html excel csv" style=" width: 300px;" property="qualificationId" sortable="true" title="Qualification" class="row-even" headerClass="row-odd"/>
										<display:column media="html excel csv" style=" width: 300px;" property="designationName" sortable="true" title="Post Applied" class="row-even" headerClass="row-odd"/>
										<display:column media="html excel csv" style=" width: 300px;" property="mailId" sortable="true" title="EmailId" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="mobileNumber" sortable="true" title="Mobile Number" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="submitedDate" sortable="true" title="Date of Submission" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="courseNames" sortable="true" title="Course Names" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="eligibilityTest" sortable="true" title="Eligibility Test" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="gender" sortable="true" title="Gender" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="dateOfBirth" sortable="true" title="Date of Birth" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="age" sortable="true" title="Age" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="totalExp" sortable="true" title="Total Experience" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="otherInfo" sortable="true" title="Other Information" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 300px;" property="noOfPublicationsRefered" sortable="true" title="No of Publications Refereed" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="currentAddress1" sortable="true" title="Current Address Line1" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="currentAddress2" sortable="true" title="Current Address Line2" class="row-even" headerClass="row-odd"/>
										<display:column media="excel csv" style=" width: 130px;" property="currentAddress3" sortable="true" title="Current Address Line3" class="row-even" headerClass="row-odd"/>
								</display:table>
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
							<div align="center">
									<html:button property="" styleClass="formbutton" onclick="cancelPage()" value="Close"></html:button>
										
							</div>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
</html:html>