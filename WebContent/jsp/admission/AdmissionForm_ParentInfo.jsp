<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<%@page import="com.kp.cms.constants.CMSConstants"%><html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">


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
</style>
<script type="text/javascript">
var submited=false;
function submitParentForm(method){
	if(!submited){
	submited=true;
	document.admissionFormForm.method.value=method;
	document.admissionFormForm.submit();
	
	}
}

</script>
</head>
<html:form action="/admissionFormSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="6"/>
<html:hidden property="formName" value="admissionFormForm"/>
<body>
<%String dynaMandate=""; %>
 <logic:equal value="true" property="onlineApply" name="admissionFormForm">
	<%dynaMandate="<span class='Mandatory'>*</span>"; %>
</logic:equal>
<table width="98%" border="0">
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td colspan="2" background="images/Tcenter.gif" class="body" ><div align="left"><span class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></div></td>
        <td width="9" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="46" colspan="2" class="heading"><img src="images/parent_tab.jpg" width="585" height="33" border="0">
          <div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
<tr><td valign="top" background="images/Tright_03_03.gif"></td><td><div id="errorMessage"><html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT></div></td><td></td><td valign="top" background="images/Tright_3_3.gif" class="news"></td></tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td width="430" valign="top" class="news"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="883" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="175" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="45%" height="36" class="row-odd" ><div align="right">
                    <div align="right"><%=dynaMandate%><bean:message key="knowledgepro.admission.fatherName"/></div>
                  </div></td>
                  <td width="55%" height="36" class="row-even" ><label>
                    <html:text property="fatherName" size="15" maxlength="50"></html:text>
                  </label></td>
                </tr>
                <tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.education"/></div></td>
                  <td class="row-even"><html:text property="fatherEducation" size="15" maxlength="50"></html:text></td>
                </tr>
                <tr >
                  <td class="row-odd" ><div align="right"><%=dynaMandate%><bean:message key="admissionFormForm.fatherIncome"/></div></td>
                  <td class="row-even" >
					<input type="hidden" id="fatherincomeType" name="fatherincomeType" value='<bean:write name="admissionFormForm" property="fatherIncome"/>'/>
                  <html:select property="fatherIncome"  styleClass="combo" styleId="fatherIncome">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:optionsCollection name="admissionFormForm" property="incomes" label="incomeRange" value="id"/>
							</html:select></td>
                </tr>
  				<tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.currency.label"/></div></td>
                  <td class="row-even">
					<input type="hidden" id="fathercurrencyType" name="fathercurrencyType" value='<bean:write name="admissionFormForm" property="fatherCurrency"/>'/>
                  <html:select property="fatherCurrency"  styleClass="combo" styleId="fatherCurrency">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<%String selected=""; %>
								<logic:iterate id="option" property="currencies" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
									
							</html:select></td>
                </tr>
                <tr >
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.parentinfo.occupation.label"/></div></td>
                  <td height="30" class="row-even" >
				<input type="hidden" id="fatheroccupationType" name="fatheroccupationType" value='<bean:write name="admissionFormForm" property="fatherOccupation"/>'/>
                  <html:select property="fatherOccupation" styleClass="combo" styleId="fatherOccupation">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
							</html:select></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
                  <td height="30" class="row-even" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr><td><html:text property="fatherEmail" size="15" maxlength="50"></html:text></td></tr>
						<tr><td>(e.g. name@yahoo.com)</td></tr>
					</table>
					</td>
                </tr>

            </table></td>
            <td  background="images/right.gif" width="5" height="175"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td width="505" valign="top" class="news"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="883" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="175" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="45%" height="36" class="row-odd" ><div align="right">
                      <div align="right"><%=dynaMandate%><bean:message key="knowledgepro.admission.motherName"/></div>
                  </div></td>
                  <td width="55%" height="36" class="row-even" ><label>
                    <html:text property="motherName" size="15" maxlength="50"></html:text>
                  </label></td>
                </tr>
                <tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.education"/></div></td>
                  <td class="row-even"><html:text property="motherEducation" size="15" maxlength="50"></html:text></td>
                </tr>
                <tr >
                  <td class="row-odd" ><div align="right"><%=dynaMandate%><bean:message key="admissionFormForm.fatherIncome"/></div></td>
                  <td class="row-even" >
				<input type="hidden" id="motherincomeType" name="motherincomeType" value='<bean:write name="admissionFormForm" property="motherIncome"/>'/>
                  <html:select property="motherIncome"  styleClass="combo" styleId="motherIncome">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:optionsCollection name="admissionFormForm" property="incomes" label="incomeRange" value="id"/>
							</html:select></td>
                </tr>
				<tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.currency.label"/></div></td>
                  <td class="row-even">
<input type="hidden" id="mothercurrencyType" name="mothercurrencyType" value='<bean:write name="admissionFormForm" property="motherCurrency"/>'/>
                  <html:select property="motherCurrency"  styleClass="combo" styleId="motherCurrency">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<%String selected=""; %>
								<logic:iterate id="option" property="currencies" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_CURRENCY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
							</html:select></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.parentinfo.occupation.label"/></div></td>
                  <td height="30" class="row-even" >
				<input type="hidden" id="motheroccupationType" name="motheroccupationType" value='<bean:write name="admissionFormForm" property="motherOccupation"/>'/>
                  <html:select property="motherOccupation" styleClass="combo" styleId="motherOccupation">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:optionsCollection name="admissionFormForm" property="occupations" label="occupationName" value="occupationId"/>
							</html:select></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
                  <td height="30" class="row-even" >
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr><td><html:text property="motherEmail" size="15" maxlength="50"></html:text></td></tr>
						<tr><td>(e.g. name@yahoo.com)</td></tr>
                    </table>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="5" height="175"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

	<logic:equal value="true" property="displayFamilyBackground" name="admissionFormForm">
		<tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td width="430" valign="top" class="news"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="883" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="175" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="45%" height="36" class="row-odd" ><div align="right">
                    <div align="right"><bean:message key="knowledgepro.applicationform.brothername.label"/></div>
                  </div></td>
                  <td width="55%" height="36" class="row-even" ><label>
                    <html:text property="brotherName" size="15" maxlength="100"></html:text>
                  </label></td>
                </tr>
                <tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.education"/></div></td>
                  <td class="row-even"><html:text property="brotherEducation" size="15" maxlength="100"></html:text></td>
                </tr>
                <tr >
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.income"/></div></td>
                  <td class="row-even" >
					<html:text property="brotherIncome" size="15" maxlength="100"></html:text></td>
                </tr>
  				<tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.parentinfo.occupation.label"/></div></td>
                  <td height="30" class="row-even" ><html:text property="brotherOccupation" size="15" maxlength="100"></html:text></td>
                </tr>
               
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div></td>
                  <td height="30" class="row-even" ><html:text property="brotherAge" size="15" maxlength="50"></html:text></td>
                </tr>

            </table></td>
            <td  background="images/right.gif" width="5" height="175"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td width="505" valign="top" class="news"><table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="883" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="175" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="45%" height="36" class="row-odd" ><div align="right">
                      <div align="right"><bean:message key="knowledgepro.applicationform.sistername.label"/></div>
                  </div></td>
                  <td width="55%" height="36" class="row-even" ><label>
                    <html:text property="sisterName" size="15" maxlength="100"></html:text>
                  </label></td>
                </tr>
                <tr class="row-odd">
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.education"/></div></td>
                  <td class="row-even"><html:text property="sisterEducation" size="15" maxlength="100"></html:text></td>
                </tr>
                <tr >
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.admission.income"/></div></td>
                  <td class="row-even" >
						<html:text property="sisterIncome" size="15" maxlength="100"></html:text></td>
                </tr>
				
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.parentinfo.occupation.label"/></div></td>
                  <td height="30" class="row-even" >
						<html:text property="sisterOccupation" size="15" maxlength="100"></html:text></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.applicationform.age.label"/></div></td>
                  <td height="30" class="row-even" ><html:text property="sisterAge" size="15" maxlength="50"></html:text></td>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="5" height="175"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" ></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
	</logic:equal>





      <tr>
        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="24" colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.parentaddr.label"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="91" valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="113" height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address1.label"/></div></td>
                  <td width="178" height="20" class="row-even"><html:text property="parentAddress.addrLine1" size="15" maxlength="100" styleId="parentAddraddress1"></html:text></td>
                  <td width="100" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.city"/></div></td>
                  <td width="190" class="row-even"><html:text property="parentAddress.city" styleId="parentCity" size="15" maxlength="30"></html:text></td>
                  <td width="112" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                  <td width="216" class="row-even"><html:text property="parentAddress.pinCode" size="10" maxlength="10" styleId="parentAddrzip"></html:text></td>
                </tr>
                <tr class="row-even">
                  <td height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address2.label"/></div></td>
                  <td height="20" class="row-even"><html:text property="parentAddress.addrLine2" size="15" maxlength="100" styleId="parentAddraddress2"></html:text></td>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.country"/></div></td>
                  <td class="row-even"><input type="hidden" id="parentCnttype" name="parentCnttype" value='<bean:write name="admissionFormForm" property="parentAddress.countryId"/>'/>
                  <html:select property="parentAddress.countryId" styleId="parentCountry" styleClass="combo" onchange="getParentAddrStates(this.value,'parentState')">
						<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
				  </html:select></td>
                  <td height="20" class="row-odd"><div align="right"><bean:message key="admissionFormForm.phone"/>:</div></td>
                  <td height="20" class="row-even">

						<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td align="left"><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="parentPhone1" size="4" maxlength="4" /></td></tr>
								<tr><td align="left"><bean:message key="admissionForm.phone.areacode.label"/></td><td><html:text property="parentPhone2" size="3" maxlength="7" /></td></tr>
								<tr><td align="left"><bean:message key="admissionForm.phone.main.label"/></td><td><html:text property="parentPhone3" size="10" maxlength="10" /></td></tr>
						</table>

                  </td>
                </tr>
                <tr class="row-even">
                  <td class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address3.label"/></div></td>
                  <td><html:text property="parentAddress.addrLine3" size="15" maxlength="100" styleId="parentAddraddress3"></html:text></td>
                  <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.state"/></div></td>
                  <td height="20"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
							<%String dynaStyle=""; %>
								<logic:equal value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle="display:none;"; %>
									</logic:notEqual>
                  	<html:select property="parentAddress.stateId" styleClass="combo" styleId="parentState" onchange="funcOtherShowHide('parentState','otherParentAddrState');">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.parentAddress.countryId != null && admissionFormForm.parentAddress.countryId!= ''}">
			                           					<c:set var="parentAddrStateMap" value="${baseActionForm.collectionMap['parentAddrStateMap']}"/>
		                            		    	 	<c:if test="${parentAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="parentAddrStateMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select></td></tr>
							<tr><td><html:text property="parentAddress.otherState" size="10" maxlength="30" styleId="otherParentAddrState" style="<%=dynaStyle %>"></html:text></td></tr>
							</table></td>
                  <td class="row-odd"><div align="right"><bean:message key="admissionFormForm.mobile"/>:</div></td>
                  <td height="20">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="parentMobile1" size="4" maxlength="4" /></td></tr>
                            <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><html:text property="parentMobile2" size="10" maxlength="10" /></td></tr>
						</table>
				
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="24" colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="knowledgepro.applicationform.guardianaddr.label"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="91" valign="top">
			<table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
				<tr class="row-white">
                  <td width="113" height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.guardianname.label"/></div></td>
                  <td width="468" height="20" class="row-even" colspan="3"><html:text property="guardianName" styleId="guardianName" size="50" maxlength="100"></html:text></td>
                  
                  <td width="112" class="row-odd"></td>
                  <td width="216" class="row-even"></td>
                </tr>
                <tr class="row-even">
                  <td width="113" height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address1.label"/></div></td>
                  <td width="178" height="20" class="row-even"><html:text property="guardianAddress.addrLine1" size="15" maxlength="100" styleId="guardianAddraddress1"></html:text></td>
                  <td width="100" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.city"/></div></td>
                  <td width="190" class="row-even"><html:text property="guardianAddress.city" styleId="guardianCity" size="15" maxlength="30"></html:text></td>
                  <td width="112" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                  <td width="216" class="row-even"><html:text property="guardianAddress.pinCode" size="10" maxlength="10" styleId="guardianAddrzip"></html:text></td>
                </tr>
                <tr class="row-even">
                  <td height="20" class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address2.label"/></div></td>
                  <td height="20" class="row-even"><html:text property="guardianAddress.addrLine2" size="15" maxlength="100" styleId="guardianAddraddress2"></html:text></td>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.country"/></div></td>
                  <td class="row-even"><input type="hidden" id="guardianCnttype" name="guardianCnttype" value='<bean:write name="admissionFormForm" property="guardianAddress.countryId"/>'/>
                  <html:select property="guardianAddress.countryId" styleId="guardianCountry" styleClass="combo" onchange="getGuardianAddrStates(this.value,'guardianState')">
						<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						<%String selected=""; %>
								<logic:iterate id="option" property="countries" name="admissionFormForm">
										<logic:equal value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected="selected"; %></logic:equal>
										<logic:notEqual value='<%=String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID) %>' name="option" property="id"><%selected=""; %></logic:notEqual>
										<option value='<bean:write name="option" property="id"/>' <%=selected %> ><bean:write name="option" property="name"/> </option>
								</logic:iterate>
				  </html:select></td>
                  <td height="20" class="row-odd"><div align="right"><bean:message key="admissionFormForm.phone"/>:</div></td>
                  <td height="20" class="row-even">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr><td  align="left"><bean:message key="admissionForm.phone.cntcode.label"/></td><td align="left"><html:text property="guardianPhone1" size="4" maxlength="4" /></td></tr>
								<tr><td  align="left"><bean:message key="admissionForm.phone.areacode.label"/></td><td align="left"><html:text property="guardianPhone2" size="3" maxlength="7" /></td></tr>
								<tr><td  align="left"><bean:message key="admissionForm.phone.main.label"/></td><td align="left"><html:text property="guardianPhone3" size="10" maxlength="10" /></td></tr>
						</table>
						
                  </td>
                </tr>
                <tr class="row-even">
                  <td class="row-odd"><div align="right"><bean:message key="admissionForm.parentinfo.address3.label"/></div></td>
                  <td><html:text property="guardianAddress.addrLine3" size="15" maxlength="100" styleId="guardianAddraddress3"></html:text></td>
                  <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.state"/></div></td>
                  <td height="20"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td>
							<%String dynaStyle1=""; %>
								<logic:equal value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle1="display:block;"; %>
									</logic:equal>
									<logic:notEqual value="Other" property="tempAddr.stateId" name="admissionFormForm">
										<%dynaStyle1="display:none;"; %>
									</logic:notEqual>
                  	<html:select property="guardianAddress.stateId" styleClass="combo" styleId="guardianState" onchange="funcGuardianOtherShowHide('guardianState','otherGuardianAddrState');">
									<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
									<c:if test="${admissionFormForm.guardianAddress.countryId != null && admissionFormForm.guardianAddress.countryId!= ''}">
			                           					<c:set var="guardianAddrStateMap" value="${baseActionForm.collectionMap['guardianAddrStateMap']}"/>
		                            		    	 	<c:if test="${guardianAddrStateMap != null}">
		                            		    	 		<html:optionsCollection name="guardianAddrStateMap" label="value" value="key"/>
		                            		    	 	</c:if> 
			                        </c:if>
							</html:select></td></tr>
							<tr><td><html:text property="guardianAddress.otherState" size="10" maxlength="30" styleId="otherGuardianAddrState" style="<%=dynaStyle1 %>"></html:text></td></tr>
							</table></td>
                  <td class="row-odd"><div align="right"><bean:message key="admissionFormForm.mobile"/>:</div></td>
                  <td height="20">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="guardianMobile1" size="4" maxlength="4" /></td></tr>
                            <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><html:text property="guardianMobile2" size="10" maxlength="10" /></td></tr>
						</table>

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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
              <table width="100%" height="27"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="48%" height="21"><div align="right"><html:button property="" onclick="submitParentForm('submitAdmissionFormParentInfo')" styleClass="formbutton" value="Submit"></html:button> </div></td>
                      <td width="1%"><div align="center"></div></td>
                      <td width="51%" height="45"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetParentInfo()"></html:button></div></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
            </div></td>
        <td width="9" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td colspan="2"  background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html:form>
</html:html>
<script type="text/javascript">
var fincomeId = document.getElementById("fatherincomeType").value;
if(fincomeId != null && fincomeId.length != 0) {
	document.getElementById("fatherIncome").value = fincomeId;
}
var fcurrencyId = document.getElementById("fathercurrencyType").value;
if(fcurrencyId != null && fcurrencyId.length != 0) {
	document.getElementById("fatherCurrency").value = fcurrencyId;
}
var foccupationId = document.getElementById("fatheroccupationType").value;
if(foccupationId != null && foccupationId.length != 0) {
	document.getElementById("fatherOccupation").value = foccupationId;
}
var mincomeId = document.getElementById("motherincomeType").value;
if(mincomeId != null && mincomeId.length != 0) {
	document.getElementById("motherIncome").value = mincomeId;
}
var mcurrencyId = document.getElementById("mothercurrencyType").value;
if(mcurrencyId != null && mcurrencyId.length != 0) {
	document.getElementById("motherCurrency").value = mcurrencyId;
}
var moccupationId = document.getElementById("motheroccupationType").value;
if(moccupationId != null && moccupationId.length != 0) {
	document.getElementById("motherOccupation").value = moccupationId;
}
var parentcntType = document.getElementById("parentCnttype").value;
if(parentcntType != null && parentcntType.length != 0) {
	document.getElementById("parentCountry").value = parentcntType;
}

if(document.getElementById("parentState").value==""){
setTimeout("getParentAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','parentState')",2000);
}
if(document.getElementById("guardianState").value==""){
setTimeout("getGuardianAddrStates('<%=CMSConstants.KP_DEFAULT_COUNTRY_ID%>','guardianState')",4000);
}
</script>