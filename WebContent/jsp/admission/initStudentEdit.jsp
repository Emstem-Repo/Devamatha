<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%
  String ipAddress=request.getRemoteAddr();
%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
</head>
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
function getSemisters(year) {
	year = document.getElementById("academicYear").value;

	var courseId = document.getElementById("course").value;
	getSemistersOnYearAndCourse("semistersMap",courseId,"semister",year,updateSemisters);
        
}

function updateSemisters(req){
	updateOptionsFromMap(req,"semister","- Select -");
}
function resetCoursesChilds() {
	resetAcademicYear("academicYear");
	resetOption("semister");

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

<body>
<html:form action="studentEdit" method="post">
<html:hidden property="method" styleId="method" value="getSearchedStudents"/>
<html:hidden property="formName" value="studentEditForm" />
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt;<bean:message key="admissionForm.studentedit.mainedit.label"/> &gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="admissionForm.studentedit.mainedit.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>	
	            <td colspan="6" class="body" align="left">
	             <div id="errorMessage">
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
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.appNo" /> </div></td>
                  <td width="24%" height="25" class="row-even" >					
					<html:text name="studentEditForm" property="applicationNo" styleClass="TextBox" styleId="applnnoID" size="12" maxlength="30" onkeypress="return isNumberKey(event)"/>
				  </td>
                  <td width="7%" class="row-even" ><div align="center" class="heading"></div></td>
                  <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.feereport.feereport.registrationno" /></div></td>
                  <td width="26%" height="25" colspan="2" class="row-even" >					
					<html:text name="studentEditForm" property="regNo" styleClass="TextBox" styleId="regNoID" size="15" maxlength="15"/>
				  </td>
                </tr>
                <tr >
                  <td width="22%" height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.roll.label" /> </div></td>
                  <td width="24%" height="25" class="row-even" >					
					<html:text name="studentEditForm" property="rollNo" styleClass="TextBox" styleId="applnnoID" size="10" maxlength="10"/>
				  </td>
                  <td width="7%" class="row-even" ><div align="center" class="heading"></div></td>
                  <td width="21%" height="25" class="row-odd" ><div align="right"></div></td>
                  <td width="26%" height="25" colspan="2" class="row-even" >					
					
				  </td>
                </tr>
                
              </table></td>
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
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>

<div align="center" class="heading">OR</div>

          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                <td height="25" class="row-even" >
                    <html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    			<html:optionsCollection name="studentEditForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     			</html:select> 
                </td>
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
                <td height="25" class="row-even" >
			      <html:select property="programId"  styleId="program" styleClass="combo" onchange="getCourses(this.value)">
          		   		<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    	<c:if test="${studentEditForm.programTypeId != null && studentEditForm.programTypeId != ''}">
            					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
            		    	 	<c:if test="${programMap != null}">
            		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
            		    	 	</c:if>	 
            		   		</c:if>
	           		</html:select>	   
				</td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.course"/>:</div></td>
                <td class="row-even" >
             <html:select property="courseId"  styleId="course" styleClass="combo" onchange="getSemisters(this.value)">
           				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
         				   		<c:if test="${studentEditForm.programId != null && studentEditForm.programId != ''}">
             						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
             		    			<c:if test="${courseMap != null}">
             		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
             		    			</c:if>	 
             		   			</c:if>
       			   </html:select>
				</td>
                <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentedit.name.label" /></div></td>
                <td height="25" colspan="4" class="row-even" >
					<html:text name="studentEditForm" property="firstName" styleClass="TextBox" styleId="name" size="12" maxlength="50"/>
                </td>
              </tr>
              <tr >
                <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.scheme.col" /></div></td>
                <td height="25" class="row-even" >
                 	<html:select property="semister" styleId="semister" styleClass="combo" onchange="getSubjectGroupsOnSemister(this.value)">
         	   				 <html:option value="">- Select -</html:option>
           				    	<c:if test="${studentEditForm.academicYear != null && studentEditForm.academicYear != ''}">
              						<c:set var="semistersMap" value="${baseActionForm.collectionMap['semistersMap']}"/>
              		    			<c:if test="${semistersMap != null}">
              		    				<html:optionsCollection name="semistersMap" label="value" value="key"/>
              		    			</c:if>	 
              		   			</c:if>
         	 			 </html:select>
				</td>
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentedit.admyear.label" /></div></td>
				<td height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="studentEditForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo" onchange="getSemisters(this.value)">
  	   				 <html:option value="">- Select -</html:option>
  	   				<cms:renderAcademicYear></cms:renderAcademicYear>
   			   </html:select>
				</td>
                </tr>
            </table></td>
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
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFeeReport()"></html:button></td>
						</tr>
					</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="931" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
//setInterval ("getRegisterNumber('<%=ipAddress%>')", 1000 );
</script>