<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
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

function resetCancelledAdmReport()	{
	document.getElementById("programtype").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetFieldAndErrMsgs();
}
</script>
<body>
<html:form action="CancelledAdmissions" method="post">
<html:hidden property="method" styleId="method" value="searchCancelledAdmissions"/>
<html:hidden property="formName" value="cancelledAdmissionsForm" />
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/>&gt;&gt;<span class="Bredcrumbs">Cancelled Admissions&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Cancelled Admissions</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr bgcolor="#FFFFFF">
	          	<td height="20" colspan="6" class="body" align="left">
				<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>	
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
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td width="100%" valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
              <tr>
				<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col" /></div></td>
	             <td height="25" class="row-even" >
	                 <html:select property="year" styleId="academicYear" styleClass="combo">
	  	   				 <html:option value="">- Select -</html:option>
	  	   				 <cms:renderYear></cms:renderYear>
	   			   </html:select>
                </td>

                <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program.type" /></div></td>
				 <td width="25%" height="25" class="row-even" >
				 	<html:select property="programTypeId"  styleId="programtype" styleClass="combo" onchange="getPrograms(this.value)">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 			<c:if test="${programTypeList != null && programTypeList != ''}">
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
				    		</c:if>	
	     			</html:select> 
                </td>
              </tr>
              <tr>
                <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.prog" /></div></td>
                <td width="25%" height="25" class="row-even">
			      <html:select property="programId"  styleId="program" styleClass="combo" onchange="getCourses(this.value)">
          		  	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
      					<c:if test="${cancelledAdmissionsForm.programTypeId != null && cancelledAdmissionsForm.programTypeId != ''}">
          					<c:set var="programMap" value="${baseActionForm.collectionMap['programMap']}"/>
          		    	 	<c:if test="${programMap != null}">
          		    	 		<html:optionsCollection name="programMap" label="value" value="key"/>
          		    	 	</c:if>	 
          		   		</c:if>
	           		</html:select>	   
				</td>

				<td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course.with.col" /></div></td>
				 <td width="25%" height="25" class="row-even" >
				 	<html:select property="courseId"  styleId="course" styleClass="combo" >
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					  		<c:if test="${cancelledAdmissionsForm.programId != null && cancelledAdmissionsForm.programId != ''}">
	       						<c:set var="courseMap" value="${baseActionForm.collectionMap['coursesMap']}"/>
	       		    			<c:if test="${courseMap != null}">
	       		    				<html:optionsCollection name="courseMap" label="value" value="key"/>
	       		    			</c:if>	 
	       		   			</c:if>
	     			</html:select> 
                </td>

              </tr>
              <tr >
                    <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.student.from.date"/></div></td>
                    <td width="25%" class="row-even" ><html:text name="cancelledAdmissionsForm" property="fromDate" styleId="fromDate"	size="10" maxlength="10" />
                        <script language="JavaScript">
										new tcal ({
													// form name
														'formname': 'cancelledAdmissionsForm',
													// input name
														'controlname': 'fromDate'
													});
						</script>
					</td>
                    <td width="25%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.hostel.student.to.date"/></div></td>
                    <td width="25%" class="row-even" ><html:text name="cancelledAdmissionsForm" property="toDate" styleId="toDate"	size="10" maxlength="10" />
                        <script language="JavaScript">
								new tcal ({
											// form name
													'formname': 'cancelledAdmissionsForm',
											// input name
													'controlname': 'toDate'
											});
						</script>   
                 <div align="right"></div></td>
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
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetCancelledAdmReport()"></html:button></td>
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
