<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/prcadmissionform.js"></script>
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
</style></head>

<body>
<html:form action="/presidanceadmissionFormSubmit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="admissionFormForm"/>
<%
	String submitjsmethod=null;
%>
<logic:notEmpty name="transactionstatus" scope="request">
	<%
		submitjsmethod="#";
	%>
</logic:notEmpty>
<logic:empty name="transactionstatus" scope="request">
	<%
		submitjsmethod="submitAdmissionForm('submitOfflineApplicationFormInfo')";
	%>
</logic:empty>
<%String dynaMandate=""; %>
 <logic:equal value="true" property="onlineApply" name="admissionFormForm">
	<%dynaMandate="<span class='Mandatory'>*</span>"; %>
</logic:equal>
		
<table width="98%" border="0">
  
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td width="935" colspan="2" background="images/Tcenter.gif" class="body" ><div align="left"><span class="boxheader"><bean:message key="knowledgepro.admission"/> &gt;&gt; <bean:message key="knowledgepro.applicationform"/> &gt;&gt;</span></div></td>
        <td width="9" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="46" colspan="2" class="heading">
			<logic:equal value="false" property="singlePageAppln" name="admissionFormForm">
			<img src="images/Application_tab.jpg" width="664" height="33" border="0">
			</logic:equal>
			
         <div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
	<tr><td valign="top" background="images/Tright_03_03.gif"></td>
	<td colspan="2">
					
				<div id="errorMessage">
      						<html:errors/><FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					</div>
				</td>
	<td valign="top" background="images/Tright_3_3.gif" class="news"></td></tr>
      
	<tr>
        <td height="24" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="24" colspan="2" class="heading">&nbsp;&nbsp;<bean:message key="admissionForm.applicationfeeinfo.main.label"/></td>
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
            <td height="75" valign="top"><table width="100%" height="75" border="0" cellpadding="0" cellspacing="1">
              <tr class="row-white" >
                <td width="16%" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                <td width="16%" height="20" class="row-even">
                <input type="hidden" id="programType" name="programType" value='<bean:write name="admissionFormForm" property="programTypeId"/>'/>
				
                <bean:write name="admissionFormForm" property="progTypeName"/>
				</td>
                <td width="16%" height="20" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.program"/>:</div></td>
                <td width="16%" height="20" class="row-even">
                <bean:write name="admissionFormForm" property="programName"/>
				</td>
                <td width="16%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.edit.firstpref.label"/> </div></td>
                <td width="16%" class="row-even">
                <bean:write name="admissionFormForm" property="courseName"/>
				</td>
              </tr>
              <!--<tr class="row-even">
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.challan.label"/></div></td>
                <td height="20" class="row-white"><html:text property="challanNo" size="15" maxlength="30"></html:text></td>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.journal.label"/></div></td>
                <td class="row-white"><html:text property="journalNo" size="15" maxlength="30"></html:text></td>
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.date.label"/><bean:message key="admissionForm.application.dateformat.label"/></div></td>
                <td height="20" class="row-white"><html:text property="applicationDate" styleId="applicationdate" size="12" maxlength="15"></html:text><script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'admissionFormForm',
								// input name
								'controlname' :'applicationdate'
							});
						</script></td>
              </tr>
              <tr class="row-even">
                <td height="20" class="row-odd"><div align="right"><%=dynaMandate%><bean:message key="admissionForm.application.amount.label"/></div></td>
                <td height="20" class="row-even">
                <logic:empty name="admissionFormForm" property="applicationAmount">
                <html:text property="applicationAmount"  size="15" maxlength="8"> </html:text>
                </logic:empty>
                <logic:notEmpty name="admissionFormForm" property="applicationAmount">
                 <html:text property="applicationAmount" disabled="true" size="15" maxlength="8"> </html:text>
                </logic:notEmpty>
                
                
               </td>
                <td height="20" class="row-odd"><div align="right"><bean:message key="knowledgepro.applicationform.branchCode"/></div></td>
                <td height="20" class="row-even"><html:text property="bankBranch"  size="15" maxlength="20"></html:text></td>
                <td class="row-odd">&nbsp;</td>
                <td height="20">&nbsp;</td>
              </tr>
            --></table></td>
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
                      <td width="48%" height="21"><div align="right"><html:button property="" onclick='<%=submitjsmethod %>' styleClass="formbutton" value="Continue"></html:button> </div></td>
                      <td width="1%"></td>
                      <td width="51%" height="45"><div align="left"><html:button property=""  styleClass="formbutton" value="Reset" onclick="resetApplicationDetails()"></html:button></div></td>
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
      <td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
        <td colspan="2" background="images/TcenterD.gif" width="100%"></td>
        <td><img src="images/Tright_02.gif" height="29" width="9"></td>        
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>

</html:html>

<script type="text/javascript">
if(document.getElementById("programType")!=null && document.getElementById("programTypeId")!=null){
var programTypeId = document.getElementById("programType").value;
if(programTypeId != null && programTypeId.length != 0) {
	document.getElementById("programTypeId").value = programTypeId;
}
}	

</script>