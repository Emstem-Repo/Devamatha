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
<title><bean:message key="employee.info.title"/></title>
<script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
<script language="JavaScript" src="js/employee/employeeInfo.js"></script>

<link href="css/styles.css" rel="stylesheet" type="text/css">
</head>
<html:form action="/employeeInfoSubmit" enctype="multipart/form-data">
<html:hidden property="method" value="getEmployeeInfo"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="employeeInfoForm"/>

<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="employee.info.title"/> <span class="Bredcrumbs"></span></span></td>
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
        <td height="20" colspan="3" class="news"></td>
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
                <td width="27%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.code"/>: </div></td>
                <td class="row-even" colspan="3" ><span class="star">
					<logic:equal value="true" name="employeeInfoForm" property="employeeFound">
                  <html:text property="employeeDetail.code" styleClass="TextBox" size="10" maxlength="10" readonly="true"></html:text>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="employeeFound">
                  <html:text property="employeeDetail.code" styleClass="TextBox" size="10" maxlength="10" ></html:text>
					</logic:equal>
                </span></td>
                
                
                
                 
                </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" >  <bean:message key="employee.info.firstName"/> :</div></td>
                <td width="22%" class="row-even"><span class="star">
					<logic:equal value="true" name="employeeInfoForm" property="employeeFound">
                  <html:text property="employeeDetail.firstName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="employeeFound">
                  <html:text property="employeeDetail.firstName" styleClass="TextBox" size="15" maxlength="30" ></html:text>
					</logic:equal>
                </span></td>
                <td class="row-odd"><div align="right" ><bean:message key="employee.info.lastName"/>: </div></td>
                <td width="29%" class="row-even"><span class="star">
					<logic:equal value="true" name="employeeInfoForm" property="employeeFound">
                 	 <html:text property="employeeDetail.lastName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="employeeFound">
                 	 <html:text property="employeeDetail.lastName" styleClass="TextBox" size="15" maxlength="30" ></html:text>
					</logic:equal>
                </span></td>
                </tr>
                <tr><td  width="27%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.midName"/>: </div></td>
                <td class="row-even"  >
					<logic:equal value="true" name="employeeInfoForm" property="employeeFound">
                <html:text property="employeeDetail.middleName" styleClass="TextBox" size="15" maxlength="20" readonly="true"></html:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="employeeFound">
                <html:text property="employeeDetail.middleName" styleClass="TextBox" size="15" maxlength="20"></html:text>
				</logic:equal>
				</td>
                <td width="22%" class="row-odd"><div align="right" ><bean:message key="employee.info.nickName"/>: </div></td>
               <td class="row-even">
				<logic:equal value="true" name="employeeInfoForm" property="employeeFound"> 
               	<html:text property="employeeDetail.nickName" styleClass="TextBox" size="15" maxlength="30" readonly="true"></html:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="employeeFound"> 
               	<html:text property="employeeDetail.nickName" styleClass="TextBox" size="15" maxlength="30"></html:text>
               	
				</logic:equal>
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
	<logic:equal value="false" property="employeeFound" name="employeeInfoForm">
	<tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			
	          <tr>
	            <td width="45%" height="35"><div align="right"><html:button property="" styleClass="formbutton" value="Search"  onclick="submitEmployeeInfo('getEmployeeDetails')"/></div></td>
	            <td width="2%"><html:button property="" styleClass="formbutton" value="Add"  onclick="submitEmployeeInfo('saveEmployeeUserInfo')"/></td> 
	            <td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="submitEmployeeInfo('initEmployeeInfo')"/></td>
	          </tr>
	          
	        </table> 
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
    </logic:equal>  
	<logic:equal value="true" property="employeeFound" name="employeeInfoForm">



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
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.SSNO"/> </div></td>
                <td width="22%" class="row-even"><span class="star">
					<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                  	<nested:text property="employeeDetail.uid" styleClass="TextBox" size="10" maxlength="15"></nested:text>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="adminUser">
                  		<nested:write property="employeeDetail.uid" ></nested:write>
					</logic:equal>
                </span></td>
                <td width="18%" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.Nationality"/> </div></td>
                <td width="32%" class="row-even">
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                <nested:select property="employeeDetail.nationalityId" styleClass="combo">
                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                  <logic:notEmpty name="employeeInfoForm" property="nationalities">
					<html:optionsCollection name="employeeInfoForm" property="nationalities" label="name" value="id"/>
                  </logic:notEmpty>
                </nested:select>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					<nested:write property="employeeDetail.nationalityName" ></nested:write>
				</logic:equal>
				</td>
                </tr>
              <tr >
                <td height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.SNNO"/> </div></td>
                <td class="row-even"><span class="star">
					<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                 <nested:text property="employeeDetail.panNo" styleClass="TextBox" size="10" maxlength="10"></nested:text>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="adminUser">
                 <nested:write property="employeeDetail.panNo" ></nested:write>
					</logic:equal>
                </span></td>
                <td class="row-odd"><div align="right" ><bean:message key="employee.info.personal.MaritalSts"/> </div></td>
                <td height="25" class="row-even" >
					<logic:equal value="true" name="employeeInfoForm" property="adminUser">
               <nested:select property="employeeDetail.maritalStatus" styleClass="combo">
                <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 <html:option value="Single">Single</html:option>
				 <html:option value="Married">Married</html:option>
				 <html:option value="Divoreced">Divorced</html:option>
				 <html:option value="Widow">Widow</html:option>	
              </nested:select></logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
               <nested:write property="employeeDetail.maritalStatus" /></logic:equal>

				</td>
              </tr>
               
                <tr>
                <td width="18%" class="row-odd"><div align="right" > <bean:message key="employee.info.personal.Gender"/> </div></td>
               <td height="25" class="row-even" >
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
               <nested:radio property="employeeDetail.gender" value="MALE" />
                            <bean:message key="admissionForm.studentinfo.sex.male.text"/>&nbsp;
                <nested:radio property="employeeDetail.gender" value="FEMALE" />
                      <bean:message key="admissionForm.studentinfo.sex.female.text"/>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
              	 <nested:write property="employeeDetail.gender" />
                            
				</logic:equal>
				</td>
				 <td width="18%" class="row-odd"><div align="right" > <bean:message key="employee.info.personal.FingerPrintId"/> </div></td>
               <td height="25" class="row-even" >
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                    <nested:text property="employeeDetail.fingerPrintId" styleClass="TextBox" size="10" maxlength="10"></nested:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
              	 <nested:write property="employeeDetail.fingerPrintId" />
                            
				</logic:equal>
				</td>
                </tr>
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.DLNO"/> </div></td>
                <td class="row-even"  >
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                <nested:text property="employeeDetail.drivingLicenseNo" styleClass="TextBox" size="10" maxlength="20"></nested:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
                <nested:write property="employeeDetail.drivingLicenseNo" />
				</logic:equal>
				</td>
                <td width="18%" class="row-odd"><div align="right" > <bean:message key="employee.info.personal.LicExpDt"/> </div></td>
               <td height="25" class="row-even" >
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
               <nested:text property="employeeDetail.licenseExpDate" styleClass="TextBox" styleId="licExpDt" size="10" maxlength="10"></nested:text>
					
                    <script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'employeeInfoForm',
								// input name
								'controlname' :'licExpDt'
							});
						</script>
					</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
              		 <nested:write property="employeeDetail.licenseExpDate" />
					</logic:equal></td>
                </tr>
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.MilServ"/> </div></td>
                <td class="row-even"  >
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
                <nested:text property="employeeDetail.militaryService" styleClass="TextBox" size="10" maxlength="20"></nested:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
                <nested:write property="employeeDetail.militaryService" />
				</logic:equal>
				</td>
                <td width="18%" class="row-odd"><div align="right" > <bean:message key="employee.info.personal.Ethrace"/> </div></td>
               <td class="row-even">
				<logic:equal value="true" name="employeeInfoForm" property="adminUser">
               <nested:text property="employeeDetail.ethinicRace" styleClass="TextBox" size="20" maxlength="20"></nested:text>
				</logic:equal>
				<logic:equal value="false" name="employeeInfoForm" property="adminUser">
              	 <nested:write property="employeeDetail.ethinicRace" />
				</logic:equal>
				</td>

                </tr>
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.personal.DOB"/></div></td>
                <td class="row-even"  ><logic:equal value="true" name="employeeInfoForm" property="adminUser">
					<nested:text property="employeeDetail.dob" styleId="dateOfBirth" styleClass="TextBox" size="10" maxlength="10"></nested:text>
                    <script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'employeeInfoForm',
								// input name
								'controlname' :'dateOfBirth'
							});
						</script>
					</logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					<nested:write property="employeeDetail.dob" ></nested:write>
                   
					</logic:equal></td>
               <td  height="25" class="row-odd"><div align="right" ><bean:message key="employee.info.job.active"/>: </div></td>
                <td class="row-even" ><span class="star">
					     <nested:hidden property="employeeDetail.activeDummy" styleId="activeValue"/>
					      <bean:message key="employee.info.job.activeYes" />
					      <nested:radio property="employeeDetail.active" styleId="check1" value="yes"></nested:radio>
                          <bean:message key="employee.info.job.activeNo" />
                           <nested:radio property="employeeDetail.active" styleId="check2" value="No"></nested:radio>
					
					   <script>
					       	var type=document.getElementById("activeValue").value;
									
								if(type=="Yes"){
									document.getElementById("check1").checked=true;
								}						
								if(type=="No"){
									document.getElementById("check2").checked=true;
									
								}
						</script>
                </span></td>
                </tr>
                <tr><td  width="28%" height="25" class="row-odd"><div align="right" ><bean:message key="admissionFormForm.fatherName"/> :</div></td>
                <td class="row-even"  >
                   <logic:equal value="true" name="employeeInfoForm" property="adminUser">
					  <nested:text property="employeeDetail.fatherName" styleId="fatherName" styleClass="TextBox" size="15" maxlength="30"></nested:text>
                    </logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					<nested:write property="employeeDetail.fatherName" ></nested:write>
                   
					</logic:equal></td>
               <td  height="25" class="row-odd"><div align="right" ><bean:message key="admissionFormForm.motherName"/>: </div></td>
                <td class="row-even" > 
                   <logic:equal value="true" name="employeeInfoForm" property="adminUser">
					  <nested:text property="employeeDetail.motherName" styleId="fatherName" styleClass="TextBox" size="15" maxlength="30"></nested:text>
                    </logic:equal>
					<logic:equal value="false" name="employeeInfoForm" property="adminUser">
					<nested:write property="employeeDetail.motherName" ></nested:write>
					</logic:equal>
                 </td>
                </tr>
                <tr>
					<td height="25" class="row-odd"><div align="right"><bean:message key="admissionFormForm.emailId" />:</div></td>
					<td height="25" class="row-even" align="left">
					   
					   <logic:equal value="true" name="employeeInfoForm" property="adminUser">
					  		<nested:text property="employeeDetail.email" styleId="emailId" styleClass="TextBox" size="15" maxlength="50"></nested:text>
                      </logic:equal>
					  <logic:equal value="false" name="employeeInfoForm" property="adminUser">
					    <nested:write property="employeeDetail.email" ></nested:write>
                   	 </logic:equal>
				   </td>
                 <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.bloodgroup.label" /></div></td>
				<td height="25" class="row-even" align="left">
				<html:select property="employeeDetail.bloodGroup" styleClass="combo" styleId="bloodGroup">
					<option value=""><bean:message key="knowledgepro.admin.select" /></option>
					<html:option value="O+ve"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
					<html:option value="A+ve"><bean:message key="knowledgepro.admission.report.apositive"/></html:option>
					<html:option value="B+ve"><bean:message key="knowledgepro.admission.report.bpositive"/></html:option>
					<html:option value="AB+ve"><bean:message key="knowledgepro.admission.report.abpositive"/></html:option>
					<html:option value="O-ve"><bean:message key="knowledgepro.admission.report.onegitive"/></html:option>
					<html:option value="A-ve"><bean:message key="knowledgepro.admission.report.anegitive"/></html:option>
					<html:option value="B-ve"><bean:message key="knowledgepro.admission.report.bnegitive"/></html:option>
					<html:option value="AB-ve"><bean:message key="knowledgepro.admission.report.abnegitive"/></html:option>
					<html:option value="Not Known"><bean:message key="knowledgepro.admission.report.unknown"/></html:option>
				</html:select>
				</td>
                </tr>
                <tr>
					<td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.attachment.photo.label" /></div></td>
					<td height="25" class="row-even" align="left" colspan="3">
					   
					   <logic:equal value="true" name="employeeInfoForm" property="adminUser">
					   
					  		<nested:file property="employeeDetail.empPhotoName" styleId="empPhotoName"></nested:file>
                      </logic:equal>
					  <logic:equal value="false" name="employeeInfoForm" property="adminUser">
					    <nested:write property="employeeDetail.empPhotoName" ></nested:write>
                   	 </logic:equal>
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
	<logic:equal value="true" name="employeeInfoForm" property="adminUser">
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			
	          <tr>
	            <td height="35"><div align="right"><html:button property="" styleClass="formbutton" value="Save"  onclick="submitEmployeePersonalInfo()"/></div></td>
	            <td width="10"><html:button property="" styleClass="formbutton" value="Reset" onclick="submitEmployeeInfo('resetPersonalInfo')"/></td>
	            <td><div align="left"><html:button property="" styleClass="formbutton" value="Close"  onclick="submitEmployeeInfo('getEmployeeDetailsClose')"/></div> </td>
	          </tr>
	          
	        </table> 
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr></logic:equal>

	</logic:equal>	

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
