<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script type="text/javascript">
function onLoadAddrCheck()
{
	var sameAddr= document.getElementById("sameAddr").checked;

	if(sameAddr==true){
		disableTempAddress();
	}
	if(sameAddr==false){
		enableTempAddress();
	}	
}
function showSportsDescription(){
	document.getElementById("sports_description").style.display = "block";
}

function hideSportsDescription(){
	document.getElementById("sports_description").style.display = "none";
}

function showHandicappedDescription(){
	document.getElementById("handicapped_description").style.display = "block";
}

function hideHandicappedDescription(){
	document.getElementById("handicapped_description").style.display = "none";
}


function noCopyMouse(e) {
        var isRight = (e.button) ? (e.button == 2) : (e.which == 3);
        if(isRight) {
        	alert('Please write the re confimation mail');
			document.getElementById("confirmEmailId").value="";
            return false;
        }
        return true;
    }

function noCopyKey(e) {
        var isCtrl;
	isCtrl = e.ctrlKey
	if(isCtrl) {
			document.getElementById("confirmEmailId").value="";
        		return false;
        		
          	}
  	
  	if(e.keyCode == 17){
  		document.getElementById("confirmEmailId").value="";
		return false;
  	}
	
		//noCopyMouse(e);
        	return true;
}


</script>
<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>

<body>
<html:form action="/admissionFormSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="formName" value="admissionFormForm"/>
<%String dynaMandate=""; 
%>
 <logic:equal value="true" property="onlineApply" name="admissionFormForm">
	<%dynaMandate="<span class='Mandatory'>*</span>"; %>
</logic:equal>
<table width="98%" border="0">
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left" class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/>&gt;&gt;</div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr >
              <td width="100%" height="26"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div></td>
            </tr>
			<tr bgcolor="#FFFFFF">
              <td width="100%" height="46" class="heading"><img src="images/Student_tab.jpg" width="585" height="33" border="0"></td>
            </tr>
            
          </table>
            <div align="center">
              <table width="100%" height="27"  border="0" cellpadding="1" cellspacing="2">
				<tr><td valign="top" colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
                <tr>
                  <td valign="top" class="heading">&nbsp;<bean:message key="admissionForm.studentinfo.main.label"/></td>
                  <td valign="top" class="heading">&nbsp;&nbsp;&nbsp;<bean:message key="admissionForm.studentinfo.residentinfo.title"/></td>
                </tr>
			
                <tr>
                  <td width="49%" valign="top" class="heading"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="404" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>

                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="206" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                        <logic:notEqual value="true" property="onlineApply" name="admissionFormForm">
						<tr >
                          <td width="45%" height="36" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.applicationno.label"/></div></td>
                          <td width="55%" height="36" class="row-even" align="left"><label>
						  <%boolean readOnly = true; %>
						  <c:if test="${admissionFormForm.applicationError}">
						  		<%readOnly = false; %>
						  </c:if> 
						  <logic:empty property="applicationNumber" name="admissionFormForm">
						  	<%readOnly = false; %>
						  </logic:empty>
						
						  <html:text property="applicationNumber" size="9" maxlength="9" readonly="<%=readOnly%>"></html:text>
                             
                          </label></td>
                        </tr>
						</logic:notEqual>
                        <tr >
                          <td height="45" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.candidateName"/> </div></td>
                          <td height="25" class="row-even" ><div align="left">
								<%-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
	                          		<tr><td><html:text property="firstName" size="20" maxlength="30"></html:text></td><td><I>First</I></td></tr>
	                              <tr><td><html:text property="middleName" size="20" maxlength="30"></html:text></td><td><I>Middle</I></td></tr>
	                             <tr><td><html:text property="lastName" size="20" maxlength="30"></html:text></td><td><I>Last</I></td></tr>
								</table>--%>

								<html:text property="firstName" size="30" maxlength="90"></html:text></div>
							</td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.dob.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                          <td height="30" class="row-even" align="left" ><html:text property="dateOfBirth" styleId="dateOfBirth" size="11" maxlength="11"></html:text>
                              <script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'dateOfBirth'
							});
						</script></td>
                        </tr>
                       

                        <tr >
                          <td height="25" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.nationality.label"/></div></td>
                          <td height="35" class="row-even" align="left">
							<input type="hidden" id="nationType" name="nationType" value='<bean:write name="admissionFormForm" property="nationality"/>'/>
                          <html:select property="nationality" styleClass="combo" styleId="nationality">
									<option value="">-Select-</option>
								<%String selected=""; %>
								<logic:iterate id="option" property="nationalities" name="admissionFormForm">
									<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
									<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_NATIONALITY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
									<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
							</html:select>
						</td>
                        </tr>
                        <tr>
                           <td height="17" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.sex.label"/></div></td>
                           <td height="17" class="row-even" align="left"><html:radio property="gender" value="MALE"><bean:message key="admissionForm.studentinfo.sex.male.text"/></html:radio>
								<html:radio property="gender" value="FEMALE"><bean:message key="admissionForm.studentinfo.sex.female.text"/></html:radio></td>
                        </tr>
						
						 <tr>
						   <%String dynaStyle3="display:none;"; %>
									<logic:equal value="true" property="sportsPerson" name="admissionFormForm">
										<%dynaStyle3="display:block;"; %>
									</logic:equal>
					        <td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.sports.label"/> </div></td>
                           <td height="17" class="row-even" align="left"><html:radio property="sportsPerson" value="true" onclick="showSportsDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/> </html:radio>
								<html:radio property="sportsPerson" value="false" onclick="hideSportsDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>				
								</td>

                        </tr>
						
							<tr id="sports_description" style="<%=dynaStyle3 %>">
							
								<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.sportsdetails.label"/> </div></td>
								<td height="17" class="row-even" align="left"><html:text property="sportsDescription" maxlength="100" size="20"></html:text></td>
							
							</tr>
						
 						<tr>
 						   <%String dynaStyle4="display:none;"; %>
									<logic:equal value="true" property="handicapped" name="admissionFormForm">
										<%dynaStyle4="display:block;"; %>
									</logic:equal>
                           <td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.physical.label"/></div></td>
                           <td height="17" class="row-even" align="left"><html:radio property="handicapped" value="true" onclick="showHandicappedDescription()"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
								<html:radio property="handicapped" value="false" onclick="hideHandicappedDescription()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio>
								
								</td>
                        </tr>
						<tr id="handicapped_description" style="<%=dynaStyle4 %>">
							<td height="17" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.desc.label"/></div></td>
							 <td height="17" class="row-even" align="left"><html:text property="hadnicappedDescription" maxlength="80" size="20"></html:text></td>
						</tr>
                        <tr >
                          <td height="20" class="row-odd" width="40%"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.bloodgroup.label"/></div></td>
	                      <td height="20" class="row-even" align="left"><input type="hidden" id="BGType" name="BGType" value='<bean:write name="admissionFormForm" property="bloodGroup"/>'/>
                          <html:select property="bloodGroup" styleClass="combo" styleId="bgType">
								<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
								<html:option value="O+VE"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
								<html:option value="A+VE"><bean:message key="knowledgepro.admission.report.apositive"/></html:option>
								<html:option value="B+VE"><bean:message key="knowledgepro.admission.report.bpositive"/></html:option>
								<html:option value="AB+VE"><bean:message key="knowledgepro.admission.report.abpositive"/></html:option>
								<html:option value="O-VE"><bean:message key="knowledgepro.admission.report.onegitive"/></html:option>
								<html:option value="A-VE"><bean:message key="knowledgepro.admission.report.anegitive"/></html:option>
								<html:option value="B-VE"><bean:message key="knowledgepro.admission.report.bnegitive"/></html:option>
								<html:option value="AB-VE"><bean:message key="knowledgepro.admission.report.abnegitive"/></html:option>
								<html:option value="O+VE"><bean:message key="knowledgepro.admission.report.opositive"/></html:option>
								<html:option value="NOT KNOWN"><bean:message key="knowledgepro.admission.report.unknown"/></html:option>
						 </html:select>
						 </td>
                        </tr>
						<logic:equal value="true" property="displaySecondLanguage" name="admissionFormForm">
						 <tr >
                          <td height="20" class="row-odd" width="40%"><div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div></td>
	                      <td height="20" class="row-even" align="left">
                          <html:select property="secondLanguage" styleClass="body" styleId="secondLanguage">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection property="secondLanguageList"
									label="value" value="value" />
							</html:select>
						 </td>
                        </tr>
						</logic:equal>

						<tr >
                          <td height="25" class="heading" colspan="2"><div align="center"><bean:message key="admissionForm.studentinfo.birthdetails.main.label"/> </div></td>
                        </tr>
						<tr >
                          	<td height="30" class="row-even" colspan="2" align="left" >
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr >
                          				<td height="25" class="row-odd" width="45%"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.birthdetails.place.label"/></div></td>
                          				<td height="30" class="row-even" align="left" ><html:text property="birthPlace" size="15" maxlength="50"></html:text></td>
                       				 </tr>
										 <tr >
                          <td height="25" class="row-odd" width="45%"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.birthdetails.country.label"/></div></td>
                          <td height="35" class="row-even" align="left" >
								<input type="hidden" id="birthcountryId" name="birthcountryId" value='<bean:write name="admissionFormForm" property="country"/>'/>
                          		<html:select property="country" styleId="birthCountry" styleClass="combo" onchange="getStates(this.value,'birthState');" onblur="">
									<option value=""><bean:message key="knowledgepro.admin.select"/></option>
									<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
									<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
									<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
									<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
								</logic:iterate>
								</html:select>
						</td>
                        </tr>
						<tr >
							<%String dynaStyle=""; %>
									<logic:equal value="Other" property="birthState" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="birthState" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                          <td height="25" class="row-odd" width="45%"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.birthdetails.state.label"/></div></td>
                          <td height="35" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                          	<tr><td><html:select property="birthState" styleId="birthState" styleClass="combo" onchange="funcOtherShowHide('birthState','otherBirthState')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.country != null && admissionFormForm.country != ''}">
			                           					<c:set var="birthStateMap" value="${baseActionForm.collectionMap['birthStateMap']}"/>
		                            		    	 	<c:if test="${birthStateMap != null}">
		                            		    	 		<html:optionsCollection name="birthStateMap" label="value" value="key"/>
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
		                            		    	 	</c:if> 
			                        </c:if>
									
							</html:select></td></tr>
							<tr><td><html:text property="otherBirthState" size="10" maxlength="30" styleId="otherBirthState" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
						</td>
                        </tr>
								</table>
							</td>
                        </tr>

                      </table></td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table>
                   </td>
                  <td width="51%" valign="top" class="heading"><div align="right">
                    <table width="97%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td ><img src="images/01.gif" width="5" height="5"></td>
                        <td width="450" background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td width="5"  background="images/left.gif"></td>
                        <td height="206" valign="top"><table width="99%" cellspacing="1" cellpadding="2">
                            <tr >
                              <td width="44%" height="20" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.residentcatg.label"/><BR><bean:message key="admissionForm.studentinfo.residentcatg.label2"/>
								</div></td>
                              <td width="56%" height="20" class="row-even" align="left">
							<input type="hidden" id="residentType" name="residentType" value='<bean:write name="admissionFormForm" property="residentCategory"/>'/>
                              <html:select property="residentCategory" styleId="residentCatg" styleClass="combo">
									<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<logic:iterate id="option" property="residentTypes" name="admissionFormForm">
									<option value='<bean:write name="option" property="id"/>' ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
							</html:select>
							</td>
                            </tr>
                            <tr>
									<logic:equal value="Other" property="religionId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="religionId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.religion.label"/></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              	<tr><td>
								<input type="hidden" id="religionType" name="religionType" value='<bean:write name="admissionFormForm" property="religionId"/>'/>
                              	<html:select property="religionId" styleId="religionId" styleClass="combo" onchange="getSubReligion(this.value,'subreligion');funcOtherShowHide('religionId','otherReligion')">
									<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<logic:iterate id="option" property="religions" name="admissionFormForm">
									<option value='<bean:write name="option" property="religionId"/>' ><bean:write name="option" property="religionName"/> </option>
								</logic:iterate>
									<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
							</html:select></td></tr>
							<tr><td><html:text property="otherReligion" size="10" maxlength="30" styleId="otherReligion" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
                            </tr>
							<logic:equal value="true" property="casteDisplay" name="admissionFormForm">
                            <tr >
									<logic:equal value="Other" property="subReligion" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="subReligion" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              <td height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.subreligion.label"/></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              <tr><td><html:select property="subReligion" styleClass="combo" styleId="subreligion" onchange="funcOtherShowHide('subreligion','otherSubReligion')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.religionId != null && admissionFormForm.religionId != ''}">
			                           					<c:set var="subReligionMap" value="${baseActionForm.collectionMap['subReligionMap']}"/>
		                            		    	 	<c:if test="${subReligionMap != null}">
		                            		    	 		<html:optionsCollection name="subReligionMap" label="value" value="key"/>
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select></td></tr>
							<tr><td><html:text property="otherSubReligion" size="10" maxlength="30" styleId="otherSubReligion" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
                            </tr>
							</logic:equal>
                            <tr>
                              <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.castcatg.label"/></div></td>
                              <td height="20" class="row-even" align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                              		<tr><td>
									<input type="hidden" id="casteType" name="casteType" value='<bean:write name="admissionFormForm" property="castCategory"/>'/>
									<logic:equal value="Other" property="castCategory" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="castCategory" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                              		<html:select property="castCategory" styleId="castCatg" styleClass="combo" onchange="funcOtherShowHide('castCatg','otherCastCatg')">
									<option value=""><bean:message key="knowledgepro.admin.select"/></option>
										<logic:iterate id="option" property="casteList" name="admissionFormForm">
											<option value='<bean:write name="option" property="casteId"/>' ><bean:write name="option" property="casteName"/> </option>
										</logic:iterate>
									<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
							</html:select></td></tr>
							<tr><td><html:text property="otherCastCategory" size="10" maxlength="30" styleId="otherCastCatg" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
                            </tr>
                            <tr >
                              <td height="17" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.belongsto.label"/></div></td>
                              <td height="17" class="row-even" align="left"><html:radio property="areaType" value="R"><bean:message key="admissionForm.studentinfo.belongsto.rural.text"/></html:radio>
								<html:radio property="areaType" value="U"><bean:message key="admissionForm.studentinfo.belongsto.urban.text"/></html:radio></td>
                            </tr>
                            <tr>
                              <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="phone1" size="3" maxlength="4"></html:text></td></tr>
								<tr><td><bean:message key="admissionForm.phone.areacode.label"/></td><td><html:text property="phone2" size="5" maxlength="7"></html:text></td></tr>
								<tr><td><bean:message key="admissionForm.phone.main.label"/></td><td><html:text property="phone3" size="10" maxlength="10"></html:text></td></tr>
								</table>
                              </td>
                            </tr>
                            <tr>
                              <td height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="mobile1" size="4" maxlength="4"></html:text></td></tr>
                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><html:text property="mobile2" size="10" maxlength="10"></html:text></td></tr>
								</table>
                               </td>
                            </tr>
                            <tr>
                              <td height="20" class="row-odd" ><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
                              <td height="20" class="row-even" align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                   <tr><td><html:text property="emailId" size="15" maxlength="50" ></html:text></td></tr>
									<tr><td>(e.g. name@yahoo.com)</td></tr>
                                  </table>
                              </td>
                            </tr>
							<tr>
                              <td height="20" class="row-odd" ><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.confirmemail.label"/></div></td>
                              <td height="20" class="row-even" align="left"><p>
                                  <html:text property="confirmEmailId" styleId="confirmEmailId" size="15" maxlength="50" onmousedown="noCopyMouse(event)" onkeydown= "noCopyKey(event)" onkeyup="noCopyKey(event)" ></html:text>
                                  <br />
                              </p></td>
                            </tr>
                        </table></td>
                        <td  background="images/right.gif" width="10" height="3"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" ></td>
                      </tr>
                    </table>
                  </div></td>
                </tr>
			 <logic:equal value="true" property="displayExtraDetails" name="admissionFormForm">
 				<tr>
                  <td colspan="2" class="heading">&nbsp;<bean:message key="knowledgepro.applicationform.extradetails.label"/></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
						
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
	                          <tr class="row-white">
	                            <logic:equal value="true" property="displayMotherTongue" name="admissionFormForm">
								<td height="23" class="row-even"><div align="center"><bean:message key="knowledgepro.applicationform.mothertongue.label"/></div></td>
	                            <td height="23" class="row-even" align="left">
									<html:select property="motherTongue" styleClass="combo" >
										<html:option value="">-Select-</html:option>
										<html:optionsCollection  property="mothertongues" name="admissionFormForm" label="languageName" value="languageId"/>
									</html:select>
								</td>
								</logic:equal>
								<logic:equal value="true" property="displayHeightWeight" name="admissionFormForm">
								 <td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.height.label"/>
									<html:text property="height" size="5" maxlength="5"></html:text>
								</td>
								<td height="23" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.weight.label"/>
									<html:text property="weight" size="6" maxlength="6"></html:text>
								</td>
								</logic:equal>
	                          </tr>
							<logic:equal value="true" property="displayLanguageKnown" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" width="50" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.language.label"/></div></td>
	                            <td height="23" class="row-even" style="width:180px;" align="left"><bean:message key="knowledgepro.applicationform.speaklanguage.label"/>
									<html:text property="languageSpeak" size="10" maxlength="50" ></html:text>
								</td>
								<td height="23" class="row-even" align="left" style="width:180px;"><bean:message key="knowledgepro.applicationform.readlanguage.label"/>
									<html:text property="languageRead" size="10" maxlength="50" ></html:text>
								</td>
								<td height="23" class="row-even" align="left" style="width:180px;"><bean:message key="knowledgepro.applicationform.writelanguage.label"/>
									<html:text property="languageWrite" size="10" maxlength="50" ></html:text>
										
								</td>
	                          </tr>
							</logic:equal>
							
							<logic:equal value="true" property="displayTrainingDetails" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.training.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.trainingprog.label"/></td>
								 <td height="23" class="row-even" align="left">
									<html:text property="trainingProgName" size="10" maxlength="50" ></html:text>
								</td>
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.trainingduration.label"/></td>
								<td height="23" class="row-even" align="left">
									<html:text property="trainingDuration" size="10" maxlength="10" ></html:text>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.traininginst.label"/></td>
								<td height="23" class="row-even" align="left">
									<html:textarea property="trainingInstAddr" cols="25" rows="4" ></html:textarea>
								</td>
								
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.trainingpurpose.label"/></td>
								<td height="23" class="row-even" align="left">
									<html:textarea property="trainingPurpose" cols="25" rows="4"></html:textarea>
										
								</td>
	                         	 </tr>
							</logic:equal>
							
							<logic:equal value="true" property="displayAdditionalInfo" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.addninfo.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label1"/> <B><bean:write property="organizationName" name="admissionFormForm"/></B>:</td>
								 <td height="23" class="row-even" align="left">
									<html:text property="courseKnownBy" size="20" maxlength="50" ></html:text>
								</td>
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.strength.label"/></td>
								<td height="23" class="row-even" align="left">
									<html:text property="strength" size="20" maxlength="100" ></html:text>
										
								</td>
	                          </tr>
								<tr class="row-white">
	                           
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.addninfo.label2"/> <B><bean:write property="courseName" name="admissionFormForm"/> </B>:</td>
								<td height="23" class="row-even" align="left">
									<html:text property="courseOptReason" size="20" maxlength="100"  ></html:text>
								</td>
								
								<td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.weakness.label"/></td>
								<td height="23" class="row-even" align="left">
									<html:text property="weakness"  size="20" maxlength="100"></html:text>
										
								</td>
	                         	 </tr>
								<tr class="row-white">
	                           
								<td height="23" colspan="3" class="row-even" align="left"><bean:message key="knowledgepro.applicationform.addninfo.label3"/> <B><bean:write property="courseName" name="admissionFormForm"/></B>:</td>
								<td height="23" class="row-even" align="left">
									<html:textarea property="otherAddnInfo" cols="25" rows="4" ></html:textarea>
								</td>
								
	                         	 </tr>
							</logic:equal>

							<logic:equal value="true" property="displayExtracurricular" name="admissionFormForm">
							<tr class="row-white">
	                            <td height="23" colspan="4" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.extracurr.label"/></div></td>
							</tr>
							<tr class="row-white">
	                            <td height="23" class="row-even" align="right"><bean:message key="knowledgepro.applicationform.extracurr.label2"/></td>
								 <td height="23" class="row-even" align="left">
									<html:select property="extracurricularIds" styleClass="row-even" multiple="true" style="width:150Px;">
										<logic:notEmpty name="admissionFormForm" property="extracurriculars">
										<html:optionsCollection property="extracurriculars" name="admissionFormForm" label="name" value="id"/>
										</logic:notEmpty>
									</html:select>
								</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
								 <td height="23" class="row-even" align="left">&nbsp;</td>
	                          </tr>
								
							</logic:equal>
	                      </table>
					
						
					</td>
                      <td  background="images/right.gif" width="5" height="57"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>

		</logic:equal>





                <tr>
                  <td colspan="2" class="heading">&nbsp;<bean:message key="knowledgepro.applicationform.passport.label"/></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="57" valign="top"><table width="100%" height="57" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td width="212" height="23" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.passportNo.label"/></div></td>
                            <td width="236" height="23" class="row-even" align="left"><html:text property="passportNo" size="15" maxlength="15"></html:text></td>
                            <td width="224" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.passportcnt.label"/> </div></td>
                            <td width="244" class="row-even" align="left">
							<input type="hidden" id="passportcnttype" name="passportcnttype" value='<bean:write name="admissionFormForm" property="passportcountry"/>'/>
                            <html:select property="passportcountry" styleClass="combo" styleId="passportcountry">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
									<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
									<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
									<option value='<bean:write name="option" property="id"/>' <%= selected %>><bean:write name="option" property="name"/> </option>
								</logic:iterate>
							</html:select></td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.passportvalid.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                            <td height="25" class="row-white" align="left"><html:text property="passportValidity" styleId="passportValidity" styleClass="row-white" size="15" maxlength="15" ></html:text>
							<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'passportValidity'
							});
						</script></td>
                            <td class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.residentpermit.label"/></div></td>
                            <td class="row-white" align="left"><html:text property="residentPermit" styleId="residentPermit" styleClass="row-white" size="10" maxlength="15" ></html:text></td>
                          </tr>
						<tr class="row-white">
                            <td width="350" height="23" colspan="2" class="row-odd" align="right"><bean:message key="knowledgepro.applicationform.policedate.label"/><bean:message key="admissionForm.application.dateformat.label"/></td>
                            <td width="200" height="23" colspan="1" class="row-even" align="left"><html:text property="permitDate" styleId="permitDate" size="10" maxlength="10"></html:text>
							<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'permitDate'
							});
						</script>
							</td>
                            <td width="244" class="row-even" align="left">&nbsp;</td>
                          </tr>
                          
                      </table></td>
                      <td  background="images/right.gif" width="5" height="57"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading">&nbsp;<bean:message key="knowledgepro.applicationform.preference.label"/></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="31" valign="top"><table width="100%" height="85" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td height="27" class="row-odd" colspan="6"><div align="left"><bean:message key="knowledgepro.applicationform.preference.label1"/> <bean:write property="courseName" name="admissionFormForm"/><bean:message key="knowledgepro.applicationform.preference.label2"/>
 </div></td>
                          </tr>
                          <tr class="row-white">
                            <td  height="27" class="row-even">
                              <div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.firstpreference.label"/></div></td>
                           
                          <td><div align="center">
							<bean:write property="firstPref.firstprefCourseName" name="admissionFormForm"/>
                            
                            </div></td>
 							<td height="27" class="row-even"><div align="right"><bean:message key="knowledgepro.applicationform.secpreference.label"/></div></td>
                           
                            <td class="row-even"><div align="left"><span class="row-even">
                              <html:select property="secondPref.courseId" styleClass="combo" styleId="coursePref2" onchange="getUniquePreference(this.value,'coursePref3')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:optionsCollection name="admissionFormForm" property="prefcourses" label="name" value="id"/>
							</html:select>
                            </span></div></td>
 						<td height="27" class="row-even"><div align="right"><bean:message key="knowledgepro.applicationform.thirdpreference.label"/></div></td>
                            
                            <td class="row-even"><div align="left">
                              <html:select property="thirdPref.courseId" styleClass="combo" styleId="coursePref3">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.secondPref.courseId != null && admissionFormForm.secondPref.courseId != ''}">
			                           					<c:set var="preferenceMap" value="${baseActionForm.collectionMap['preferenceMap']}"/>
		                            		    	 	<c:if test="${preferenceMap != null}">
		                            		    	 		<html:optionsCollection name="preferenceMap" label="value" value="key"/>
															
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select>
                            </div></td>
                         
                          </tr>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="31"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>
		<logic:equal value="true" property="workExpNeeded" name="admissionFormForm">
				 <tr>
                  <td colspan="2" class="heading"><bean:message key="knowledgepro.applicationform.workexp.label"/></td>
                </tr>
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="31" valign="top"><table width="100%" height="113" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td height="27" class="row-odd" width="60"><div align="left"><bean:message key="knowledgepro.applicationform.orgName.label"/></div></td>
                            <td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.jobdesc.label"/></div></td>
                            <td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.fromdt.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                            <td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.todt.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
							<td height="27" class="row-odd"><div align="center"><bean:message key="knowledgepro.applicationform.lastsal.label"/></div></td>
							<td height="27" class="row-odd"><div align="center"> <bean:message key="knowledgepro.applicationform.reportto.label"/></div></td>
                          </tr>
                          <tr class="row-white">
                            <td  height="27" class="row-even"><div align="left">
								<nested:text property="firstExp.organization" styleClass="textbox" size="15" maxlength="200" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
								<nested:text property="firstExp.position" styleClass="textbox" size="10" maxlength="30" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
                             <nested:text property="firstExp.fromDate" styleId="firstExpFromdate" size="10" maxlength="10"/>
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'admissionFormForm',
									// input name
									'controlname': 'firstExpFromdate'
								});</script>
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
                             <nested:text property="firstExp.toDate" styleId="firstExpTOdate" size="10" maxlength="10" />
                              <script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'admissionFormForm',
								// input name
								'controlname': 'firstExpTOdate'
							});</script>
                            </div></td>
  							<td height="27" class="row-even"><div align="center">
								<nested:text property="firstExp.salary" styleClass="textbox" size="10" maxlength="10" />
                            </div></td>
 							<td height="27" class="row-even"><div align="center">
								<nested:text property="firstExp.reportingTo" styleClass="textbox" size="10" maxlength="50" />
                            </div></td>
                          </tr>
                          <tr class="row-white">
                            <td height="27" class="row-white"><div align="left"><span class="row-even">
                              <nested:text property="secExp.organization" styleClass="textbox" size="15" maxlength="200" />
                            </span></div></td>
                            <td height="27" class="row-white"><div align="center"><span class="row-even">
                              <nested:text property="secExp.position" styleClass="textbox" size="10" maxlength="30" />
                            </span></div></td>
                            <td class="row-white"><div align="center"><span class="row-even">
                              <nested:text property="secExp.fromDate" styleId="secExpFromdate" size="10" maxlength="10" />
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'admissionFormForm',
									// input name
									'controlname': 'secExpFromdate'
								});</script>
                            </span></div></td>	
                            <td class="row-white"><div align="center"><span class="row-even">
                           <nested:text property="secExp.toDate" styleId="secExpTOdate" size="10" maxlength="10"/>
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'admissionFormForm',
									// input name
									'controlname': 'secExpTOdate'
								});</script>
                            </span></div></td>
							<td  height="27" class="row-even"><div align="center">
								<nested:text property="secExp.salary" styleClass="textbox" size="10" maxlength="10" />
                            </div></td>
 							<td  height="27" class="row-even"><div align="center">
								<nested:text property="secExp.reportingTo" styleClass="textbox" size="10" maxlength="50" />
                            </div></td>
                          </tr>
                          <tr class="row-white">
                            <td height="27" class="row-even"><div align="left">
                              <nested:text property="thirdExp.organization" styleClass="textbox" size="15" maxlength="200" />
                            </div></td>
                            <td height="27" class="row-even"><div align="center">
                              <nested:text property="thirdExp.position" styleClass="textbox" size="10" maxlength="30" />
                            </div></td>
                            <td class="row-even"><div align="center">
                             <nested:text property="thirdExp.fromDate" styleId="thirdExpFromdate" size="10" maxlength="10" />
                              <script language="JavaScript">
								new tcal ({
									// form name
									'formname': 'admissionFormForm',
									// input name
									'controlname': 'thirdExpFromdate'
								});</script>
                            </div></td>
                            <td class="row-even"><div align="center">
                            <nested:text property="thirdExp.toDate" styleId="thirdExpTOdate" size="10" maxlength="10" />
                              <script language="JavaScript">
							new tcal ({
								// form name
								'formname': 'admissionFormForm',
								// input name
								'controlname': 'thirdExpTOdate'
							});</script>
                            </div></td>
							<td  height="27" class="row-even"><div align="center">
								<nested:text property="thirdExp.salary" styleClass="textbox" size="10" maxlength="10" />
                            </div></td>
 							<td height="27" class="row-even"><div align="center">
								<nested:text property="thirdExp.reportingTo" styleClass="textbox" size="10" maxlength="50" />
                            </div></td>
                          </tr>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="31"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>
	</logic:equal>

 <tr>
                  <td colspan="2"><span class="heading" >&nbsp;<bean:message key="admissionForm.studentinfo.currAddr.label"/></span></td>
                </tr>
                <tr>
                  <td colspan="2"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" >
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="91" valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td width="212" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                            <td width="236" height="20" class="row-even" align="left"><html:text property="tempAddr.addrLine1" size="45" maxlength="35" styleId="tempAddraddress1"></html:text></td>
                            <td width="224" class="row-odd"><div align="right">
                                <div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div>
                            </div></td>
                            <td width="244" class="row-even" align="left"><html:text property="tempAddr.addrLine2" size="55" maxlength="40" styleId="tempAddraddress2"></html:text></td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                            <td height="20" class="row-white" align="left">
                           	<html:text property="tempAddr.city" styleId="tempAddrcity" size="15" maxlength="30" ></html:text>		
							</td>
                            <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                            <td class="row-white" align="left">
								<input type="hidden" id="tempCnttype" name="tempCnttype" value='<bean:write name="admissionFormForm" property="tempAddr.countryId"/>'/>
                            		<html:select property="tempAddr.countryId" styleClass="combo" styleId="tempAddrcountry" onchange="getTempAddrStates(this.value,'tempAddrstate');">
									<option value="">-Select-</option>
									<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>

								</html:select>
							</td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                            <td height="20" class="row-even" align="left">
							<logic:equal value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td><html:select property="tempAddr.stateId" styleClass="combo" styleId="tempAddrstate" onchange="funcOtherShowHide('tempAddrstate','otherTempAddrState')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.tempAddr.countryId != null && admissionFormForm.tempAddr.countryId!= ''}">
			                           					<c:set var="tempAddrStateMap" value="${baseActionForm.collectionMap['tempAddrStateMap']}"/>
		                            		    	 	<c:if test="${tempAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="tempAddrStateMap" label="value" value="key"/>
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
		                            		    	 	</c:if> 
			                        </c:if>
									</html:select></td></tr>
							<tr><td><html:text property="tempAddr.otherState" size="10" maxlength="30" styleId="otherTempAddrState" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
                            <td class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                            <td class="row-even" align="left"><html:text property="tempAddr.pinCode" size="10" maxlength="10" styleId="tempAddrzip"></html:text></td>
                          </tr>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>

				<tr>
                  <td colspan="2"><span class="body">&nbsp;&nbsp;<B><bean:message key="knowledgepro.applicationform.sameaddr.label"/></B>
                      <html:radio property="sameTempAddr" styleId="sameAddr" value="true" onclick="disableTempAddress()"><bean:message key="knowledgepro.applicationform.yes.label"/></html:radio>
						<html:radio property="sameTempAddr" styleId="DiffAddr" value="false" onclick="enableTempAddress()"><bean:message key="knowledgepro.applicationform.No.label"/></html:radio></span></td>
                </tr>



                <tr>
                  <td colspan="2" class="heading">&nbsp;<div id="currLabel"> <bean:message key="admissionForm.studentinfo.permAddr.label"/></div></td>
                </tr>
                <tr>
                  <td colspan="2"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" id="currTable">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="91" valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
                          <tr class="row-white">
                            <td width="212" height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                            <td width="236" height="20" class="row-even" align="left"><html:text property="permAddr.addrLine1" size="45" maxlength="35" styleId="permAddraddress1"></html:text></td>
                            <td width="224" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                            <td width="244" class="row-even" align="left"><html:text property="permAddr.addrLine2" size="55" maxlength="40" styleId="permAddraddress2"></html:text></td>
                          </tr>
                          <tr class="row-even">
                            <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                            <td height="20" class="row-white" align="left">
                            <html:text property="permAddr.city" styleId="permAddrcity" size="15" maxlength="30"></html:text>
							
                            <td class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                            <td class="row-white" align="left">
							<input type="hidden" id="permCnttype" name="permCnttype" value='<bean:write name="admissionFormForm" property="permAddr.countryId"/>'/>
                            	<html:select property="permAddr.countryId" styleClass="combo" styleId="permAddrcountry" onchange="getPermAddrStates(this.value,'permAddrstate');">
								<option value=""><bean:message key="knowledgepro.admin.select"/></option>
								<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
								</html:select>	
							</td>
                          </tr>
                          <tr class="row-even">
								<logic:equal value="Other" property="permAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="permAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                            <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                            <td height="20" class="row-even" align="left">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td><html:select property="permAddr.stateId" styleClass="combo" styleId="permAddrstate" onchange="funcOtherShowHide('permAddrstate','otherPermAddrState')">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.permAddr.countryId != null && admissionFormForm.permAddr.countryId!= ''}">
			                           					<c:set var="permAddrStateMap" value="${baseActionForm.collectionMap['permAddrStateMap']}"/>
		                            		    	 	<c:if test="${permAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="permAddrStateMap" label="value" value="key"/>
															<html:option value="Other"><bean:message key="knowledgepro.admin.Other"/></html:option>
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select></td></tr>
							<tr><td><html:text property="permAddr.otherState" size="10" maxlength="30" styleId="otherPermAddrState" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
                            <td class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                            <td class="row-even" align="left"><html:text property="permAddr.pinCode" size="10" maxlength="10" styleId="permAddrzip"></html:text></td>
                          </tr>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>
                
				
               

                <tr>
                  <td colspan="2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="48%" height="21"><div align="right"><html:button property="" onclick="submitAdmissionForm('submitAdmissionFormStudentInfo')" styleClass="formbutton" value="Continue"></html:button> </div></td>
                      <td width="1%"><div align="center"></div></td>
                      <td width="51%" height="45"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetStudentDetails()"></html:button></div></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html:form>
<script type="text/javascript">
	onLoadAddrCheck();
	var relgId = document.getElementById("religionType").value;
	if(relgId != null && relgId.length != 0) {
		document.getElementById("religionId").value = relgId;
	}
	var resdId = document.getElementById("residentType").value;
	if(resdId != null && resdId.length != 0) {
		document.getElementById("residentCatg").value = resdId;
	}
	var nationId = document.getElementById("nationType").value;
	if(nationId != null && nationId.length != 0) {
		document.getElementById("nationality").value = nationId;
	}
	var casteId = document.getElementById("casteType").value;
	if(casteId != null && casteId.length != 0) {
		document.getElementById("castCatg").value = casteId;
	}
	var bgType = document.getElementById("BGType").value;
	if(bgType != null && bgType.length != 0) {
		document.getElementById("bgType").value = bgType;
	}
	var birthCntType = document.getElementById("birthcountryId").value;
	if(birthCntType != null && birthCntType.length != 0) {
		document.getElementById("birthCountry").value = birthCntType;
	}
	var passportCntType = document.getElementById("passportcnttype").value;
	if(passportCntType != null && passportCntType.length != 0) {
		document.getElementById("passportcountry").value = passportCntType;
	}

	var permcntType = document.getElementById("permCnttype").value;
	if(permcntType != null && permcntType.length != 0 && permcntType != 0 ) {
		document.getElementById("permAddrcountry").value = permcntType;
	}
	var tempcntType = document.getElementById("tempCnttype").value;
	if(tempcntType != null && tempcntType.length != 0 && tempcntType != 0) {
		document.getElementById("tempAddrcountry").value = tempcntType;
	}
	

	if(document.getElementById("birthState").value==""){
	setTimeout("getStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','birthState')",2000);
	}
	if(document.getElementById("permAddrstate").value==""){
	setTimeout("getPermAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','permAddrstate')",4000);
	}
	if(document.getElementById("tempAddrstate").value==""){
	setTimeout("getTempAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','tempAddrstate')",6000);
	}
	
	
</script>

</html:html>
