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
function resetCoursesChilds() {
	resetAcademicYear("academicYear");
}
function resetAttReport()	{
	document.getElementById("classesName").value = "";
	document.getElementById("attendanceTypeId").value = "";
	document.getElementById("startDateid").value = "";
	document.getElementById("endDateid").value = "";
	document.getElementById("subjects").value="";
	var destination5 = document.getElementById("subjects");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}	
	
	resetErrMsgs();
}
function getMultipleSubject(classId) {
	//alert("aslasd");
	var classes =  document.getElementById("classesName");
	  var selectedArray = new Array();	  
	  var i;
	  var count = 0;
	  for (i=0; i<classes.options.length; i++) {
	    if (classes.options[i].selected) {
	      selectedArray[count] = classes.options[i].value;
	      count++;
	    }
	  }
	//  alert(selectedArray);
	getSubjectByType("subjectMaps",selectedArray,"subjects",updateSubject);
}

function updateSubject(req) {
	updateOptionsFromMapMultiselect(req,"subjects","- Select -");
}

function getClasses(year) {
	getClassesByYear("classMap", year, "classesName", updateClasses);
}
function updateClasses(req) {
	var responseObj = req.responseXML.documentElement;
	var destination = document.getElementById("classesName");
	for (x1=destination.options.length-1; x1>=0; x1--) {
		destination.options[x1]=null;
	}
	var items1 = responseObj.getElementsByTagName("option");
	for (var j = 0 ; j < items1.length ; j++) {
        label = items1[j].getElementsByTagName("optionlabel")[0].firstChild.nodeValue;
	     value = items1[j].getElementsByTagName("optionvalue")[0].firstChild.nodeValue;
	     destination.options[j] = new Option(label,value);
	 }
	var destination5 = document.getElementById("subjects");
	for (x1=destination5.options.length-1; x1>=0; x1--) {
		destination5.options[x1]=null;
	}
}
</script>
<body>
<html:form action="StudentAbsenceDetails" method="post">
<html:hidden property="method" styleId="method" value="submitStudentAbsenceDetails"/>
<html:hidden property="formName" value="studentAbsenceDetailsForm" />
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/>&gt;&gt;<span class="Bredcrumbs"><bean:message key="knowledgepro.report.studentabsencedetails" />&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="5"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.report.studentabsencedetails" /></strong></td>
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
               <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col" /></div></td>
				<td height="25" class="row-even">
				<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="studentAbsenceDetailsForm" property="academicYear"/>"/>
                  <html:select property="academicYear" styleId="academicYear" styleClass="combo"  onchange="getClasses(this.value)">
  	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
	<cms:renderAcademicYear></cms:renderAcademicYear>
   			   	  </html:select>
				</td>
                <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attn.activity.att.type" /></div></td>
                <td height="25" class="row-even">
                	<html:select property="attendanceType" styleId="attendanceTypeId" onchange="getActivity(this)" style="width: 120px;" multiple="multiple" size="2">
					<c:if test="${studentAbsenceDetailsForm.attendanceTypeList != null && studentAbsenceDetailsForm.attendanceTypeList != ''}">						
						<html:optionsCollection name="studentAbsenceDetailsForm" property="attendanceTypeList" label="attendanceTypeName" value="id" />
					</c:if>	
					</html:select>
				</td>
               
            </tr>
              <tr>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /></div></td>
				 <td height="26%" height="25" class="row-even" >
				 	<html:select name="studentAbsenceDetailsForm" styleId="classesName" property="classesName" size="5" style="width: 200px;" multiple="multiple" onchange="getMultipleSubject(this.value)">
						<html:optionsCollection name="studentAbsenceDetailsForm" property="classMap" label="value" value="key" />
					</html:select> 
                </td>
                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.subject"/>:</div></td>
				<td height="26%" height="25" class="row-even" >
                    <html:select property="subjects" styleId="subjects" size="5" style="width: 200px;" multiple="multiple">
					<c:if test="${subjectMaps != null}">
			                  	<html:optionsCollection name="subjectMaps" label="value" value="key"/>
					</c:if>
                    </html:select>
				</td>
              </tr>
              
			<tr>
               <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="startDate" styleId="startDateid" size="16" maxlength="16"/>
					<script
					language="JavaScript">
					new tcal( {
						// form name
						'formname' :'studentAbsenceDetailsForm',
						// input name
						'controlname' :'startDate'
					});
					</script>
			   </td>
               <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="endDate" styleId="endDateid" size="16" maxlength="16"/>
					<script
					language="JavaScript">
					new tcal( {
						// form name
						'formname' :'studentAbsenceDetailsForm',
						// input name
						'controlname' :'endDate'
					});
					</script>
			   </td>
			</tr>
			<tr>
               <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.registernofrom" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="fromRegisterNo" styleId="fromRegisterNo" size="16" maxlength="16"/>
			   </td>
               <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.registernoto" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="toRegisterNo" styleId="toRegisterNo" size="16" maxlength="16"/>
			   </td>
			</tr>
			<tr>
               <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.rollnofrom" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="fromRollNo" styleId="fromRollNo" size="16" maxlength="16"/>
			   </td>
               <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.rollnoto" />:</div></td>
               <td height="25" class="row-even">
					<html:text name="studentAbsenceDetailsForm" property="toRollNo" styleId="toRollNo" size="16" maxlength="16"/>
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
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetAttReport()"></html:button></td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("academicYear").value=year;
}
</script>