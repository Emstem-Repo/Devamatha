<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html:html>
<head>
<title>:: CMS ::</title>
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
function getinterviewType() {

	 var year = document.getElementById("year").options[document.getElementById("year").selectedIndex].value;
	 var courseId = document.getElementById("course").options[document.getElementById("course").selectedIndex].value;

	  if(courseId.length >0) {
	   getInterviewTypeByCourse("interviewMap",courseId,year,"interviewtype",updateInterviewType);  
	  
	  }
	
		
}
function updateInterviewType(req) {
	updateOptionsFromMap(req,"interviewtype","- Select -");
}

function resetInterviewCard()	{
	
	document.getElementById("programtype").selectedIndex = 0;
	resetOption("program");
	resetOption("course");
	resetOption("interviewtype");
	document.getElementById("applno").value="";
	document.getElementById("startname").value="";
	document.getElementById("birthdate").value="";
	resetErrMsgs();
}
</script>
<body>
<html:form action="interviewprocess" method="post">
<html:hidden property="method" styleId="method" value="submitPrintInterviewProcess"/>
<html:hidden property="pageType" value="2"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.interview.PrintInterviewCard" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.interview.PrintInterviewCard"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top">
                  <table width="100%" cellspacing="1" cellpadding="2">
			          <tr bgcolor="#FFFFFF">
			            <td height="20" colspan="6" class="body" align="left">
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
                    <tr >
                      <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.ApplicationNocol"/></div></td>
                      <td width="25%" height="25" class="row-even" >
                        <html:text name="interviewProcessForm" property="searchApplNo" styleClass="body" styleId="applno" size="15" maxlength="9" onkeypress="return isNumberKey(event)"/>
                     </td>
                      <td class="row-odd" ><div align="right"></div></td>
                      <td class="row-odd" >&nbsp;</td>
                    </tr>
                    <tr >
                      <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.ProgramType"/></div></td>
                      <td width="25%" class="row-white" >
			              <html:select name="interviewProcessForm" property="programType"  styleId="programtype" styleClass="combo" onchange="getPrograms(this)">
			              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			              	<html:optionsCollection name="interviewProcessForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
			              </html:select>                
                      </td>
                      <td width="24%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.Program"/></div></td>
                      <td width="26%" class="row-white" >
			              <html:select name="interviewProcessForm" property="program"  styleId="program" styleClass="combo" onchange="getCourse(this)">
			              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			              </html:select>              
                      </td>
                    </tr>
                    <tr >
                      <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.Course"/></div></td>
                      <td class="row-even" >
			              <html:select name="interviewProcessForm" property="course"  styleId="course" styleClass="combo" onchange="getinterviewType()">
				              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
			              </html:select>                            
                      </td>
                      <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.InterviewType"/></div></td>
                      <td class="row-even" >
		                  <html:select name="interviewProcessForm" property="interviewType"  styleId="interviewtype" styleClass="combo">
				              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>				              	
			              </html:select>                            
                      </td>
                    </tr>
                    <tr >
                      <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.Year"/></div></td>
                      <td class="row-even" >
                      <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="interviewProcessForm" property="years"/>"/>
		                   <html:select name="interviewProcessForm" property="years"  styleId="year" styleClass="combo" onchange="getinterviewType()">
				              	<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>            
		             		<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
						  </html:select>              			 
                      </td>
                      <td class="row-odd" ><div align="right"></div></td>
                      <td class="row-even" >
                      </td>
                    </tr>
                    <tr >
                      <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.interview.NameStartswith"/></div></td>
                      <td class="row-white" ><html:text name="interviewProcessForm" property="srartName" styleClass="body" styleId="startname" size="20" maxlength="30" /></td>
                      <td class="row-odd"><div align="right" ><bean:message key="knowledgepro.interview.DateofBirth"/></div></td>
                      <td ><html:text name="interviewProcessForm" property="birthDate" styleClass="body" styleId="birthdate" size="20" maxlength="20"  readonly="true"/>
							<script
							language="JavaScript">
							new tcal( {
								// form name
								'formname' :'interviewProcessForm',
								// input name
								'controlname' :'birthDate'
							});
						</script>
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
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <form name="form1" method="post" action="Print_interview_card_search.html">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><html:submit styleClass="formbutton">
								<bean:message key="knowledgepro.admin.search" />
							</html:submit></div>
							</td>
							<td width="2%"></td>
							<td width="53"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetInterviewCard()"></html:button></td>
						</tr>
					</table>
            </form>
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
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
if (year.length != 0) {
	document.getElementById("year").value = year;
}
</script>