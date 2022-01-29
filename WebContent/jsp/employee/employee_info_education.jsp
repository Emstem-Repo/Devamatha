<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><bean:message key="employee.info.title"/> </title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="js/employee/employeeInfo.js"></script>

<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/employeeInfoSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="mode" value=""/>
<html:hidden property="pageType" value="11"/>
<html:hidden property="formName" value="employeeInfoForm"/>
<%String readonly="";%>
<logic:equal value="false" name="employeeInfoForm" property="adminUser"> <%readonly="readonly"; %></logic:equal>
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="employee.info.title"/><span class="Bredcrumbs"></span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="3" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="employee.info.title"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" colspan="3" class="news"><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields"/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
		<tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="3" valign="top" class="news"><div id="errorMessage"><html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
		
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="3" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
           <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="27%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.code"/> </div></td>
                <td class="row-even" colspan="3"><span class="star">
                  <html:text property="employeeDetail.code" styleClass="TextBox" size="10" maxlength="10" readonly="true"></html:text>
                </span></td>
                </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="employee.info.firstName"/> </div></td>
                <td width="22%" class="row-even"><span class="star">
                  <html:text property="employeeDetail.firstName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
                </span></td>
                <td class="row-odd"><div align="right" ><span class="Mandatory">*</span>  <bean:message key="employee.info.lastName"/> </div></td>
                <td width="29%" class="row-even"><span class="star">
					<html:text property="employeeDetail.lastName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
                  
                </span></td>
                </tr>
                <tr><td  width="27%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.midName"/> </div></td>
                <td class="row-even"  ><html:text property="employeeDetail.middleName" styleClass="TextBox" size="15" maxlength="20" readonly="true"></html:text></td>
                <td width="22%" class="row-odd"><div align="right" ><bean:message key="employee.info.nickName"/> </div></td>
               <td class="row-even"> <html:text property="employeeDetail.nickName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text></td>
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
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="3" valign="top" class="news"> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="25" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="3" valign="top" class="news">
        	<table width="650">
           	    <tr>
                <td width="7%"  class="row-white" ><a href="employeeInfoSubmit.do?method=forwardEmployeePersonalInfo" ><img  src="images/personal.gif" border="0"><br/>
                  <bean:message key="employee.info.link.Personal"/> </a></td>
                <td width="7%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeContact"><img src="images/contact.gif" border="0"><br/>
                  <bean:message key="employee.info.link.Contact"/> </a></td>
                <td width="10%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeEmergContact"><img src="images/emergency_contact.gif" border="0"><br/>
                  <bean:message key="employee.info.link.EmergContact"/> </a></td>
                <td width="10%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeDependentInfo"><img src="images/dependants.gif" border="0"><br/>
                  <bean:message key="employee.info.link.Dependents"/> </a></td>
                <td width="10%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeImmigration"><img src="images/immigration.gif" border="0"> <br/>
                  <bean:message key="employee.info.link.Immigration"/> </a></td>
                <td width="8%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeJobInfo"><img src="images/job.gif" border="0"> <br/>
                  <bean:message key="employee.info.link.Job"/> </a></td>
                <td width="8%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeReportTo"><img src="images/report-to.gif" border="0"><br/>
                  <bean:message key="employee.info.link.ReportTo"/> </a></td>
                <td width="9%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeExperience"><img src="images/tax.gif" border="0"><br/>
                  <bean:message key="employee.info.link.WorkExp"/> </a></td>
                <td width="8%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeEducationInfo"><img src="images/education.gif" border="0"> <br/>
                  <bean:message key="employee.info.link.Education"/> </a></td>
                <td width="7%" class="row-white"><a href="employeeInfoSubmit.do?method=forwardEmployeeSkills"><img src="images/skills.gif" border="0"> <br/>
                  <bean:message key="employee.info.link.Skills"/> </a></td>
                <td width="16%" class="row-white"> <a href="employeeInfoSubmit.do?method=forwardEmployeeLanguageInfo"><img src="images/languages.gif" border="0"><br/>
                  <bean:message key="employee.info.link.Languages"/> </a></td>
                  <td width="25%" class="row-white"> <a href="employeeInfoSubmit.do?method=forwardEmployeeUserInfo"><img src="images/languages.gif" border="0"><br/>
                  User Info </a></td>
              </tr>
            </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       
      <tr>
      
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="3" valign="top" class="news">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
        	<logic:notEmpty property="employeeDetail.educations" name="employeeInfoForm">          
			<nested:iterate property="employeeDetail.educations" name="employeeInfoForm" id="education" indexId="count">
				 <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="91" valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-even">
                            <td width="462" height="20" class="row-odd"><div align="right"><bean:message key="employee.info.education.title"/> </div></td>
                            <td width="468" height="20" class="row-even">
							<logic:equal value="true" name="employeeInfoForm" property="adminUser">
							<nested:select property="empEducationMasterId" styleClass="combo" >
								<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                              <logic:notEmpty property="educationMasters" name="employeeInfoForm"> 
								<html:optionsCollection property="educationMasters" name="employeeInfoForm" label="name" value="id"/>
                              </logic:notEmpty>
                            </nested:select>
							</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="empEducationMasterName"/>
								</logic:equal>
							</td>
                          </tr>
                          <tr class="row-even">
                            
                            <td class="row-odd"><div align="right"><bean:message key="employee.info.education.MajorSpl"/> </div></td>
                            <td class="row-even">
								<logic:equal value="true" name="employeeInfoForm" property="adminUser">
					               <nested:text property="specialisation" styleClass="TextBox" size="20" maxlength="50"></nested:text>
								</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="specialisation"/>
								</logic:equal>
							</td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.year"/> </div></td>
                            <td height="20" class="row-even">
								<logic:equal value="true" name="employeeInfoForm" property="adminUser">
					               <nested:text property="year" styleClass="TextBox" size="4" maxlength="4"></nested:text>
								</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="year"/>
								</logic:equal>
								
							</td>
                          </tr>
                          <tr class="row-even">
							<%
								String startId="StartDate"+count;
								String endId="EndDate"+count;
							%>
                            <td height="20" class="row-odd"><div align="right"><bean:message key="employee.info.education.gpascore"/> </div></td>
                            <td height="20" class="row-even">
								<logic:equal value="true" name="employeeInfoForm" property="adminUser">
					               <nested:text property="gpaGrade" styleClass="TextBox" size="8" maxlength="8"></nested:text>
								</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="gpaGrade"/>
								</logic:equal>
							</td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><bean:message key="employee.info.education.startDt"/> </div></td>
                            <td height="30" class="row-even">
									<logic:equal value="true" name="employeeInfoForm" property="adminUser">
					               <nested:text property="startDate" styleClass="TextBox" styleId="<%=startId %>" size="10" maxlength="10"></nested:text>
									 <script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'<%=startId %>'
										});
									</script>
								</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="startDate"/>
								</logic:equal>	</td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><bean:message key="employee.info.education.endDt"/> </div></td>
                            <td height="30" class="row-even"><logic:equal value="true" name="employeeInfoForm" property="adminUser">
					               <nested:text property="endDate" styleClass="TextBox" styleId="<%=endId %>" size="10" maxlength="10"></nested:text>
									 <script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'employeeInfoForm',
											// input name
											'controlname' :'<%=endId %>'
										});
									</script>
								</logic:equal>
								<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					               <nested:write property="endDate"/>
								</logic:equal></td>
                          </tr>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>

					<tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="25" valign="top">  &nbsp;</td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>
</nested:iterate>
</logic:notEmpty> 
<tr>
			<td width="5"  background="images/left.gif"></td>
            <td  valign="top">
            <table width="100%" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-even">
                            <td width="500" class="row-odd"></td>
                            <td  class="row-even"><html:button property="" styleClass="formbutton" value="Add more" onclick="submitEmployeeInfoAdd('resetEducationInfo','EAddMore')"/>
							</td>
                          </tr>
            </table>
            </td>
            <td  background="images/right.gif" width="5" ></td>
			</tr>

                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
<logic:equal value="true" name="employeeInfoForm" property="adminUser">
    <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          
			<tr>
            <td height="35"><div align="right"><html:button property="" styleClass="formbutton" value="Save" onclick="submitEmployeeInfo('saveEmployeeEducationInfo')"/></div></td>
            <td width="10"> <html:button property="" styleClass="formbutton" value="Reset" onclick="submitEmployeeInfo('resetEducationValues')"/></td>
            <td><div align="left"><html:button property="" styleClass="formbutton" value="Close"  onclick="submitEmployeeInfo('getEmployeeDetailsClose')"/></div> </td>
          </tr>
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr></logic:equal>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td colspan="3" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html>