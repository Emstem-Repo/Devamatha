<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="com.kp.cms.forms.admission.AdmissionFormForm"%><html:html>
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
</style></head>

<body>
<html:form action="/admissionFormSubmit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="12"/>
<html:hidden property="formName" value="admissionFormForm"/>
<html:hidden property="onlineApply" value="true"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>  &gt;&gt; <bean:message key="knowledgepro.applicationform.tc.label"/></span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.applicationform.tc.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news">
		<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div id="errorMessage"><html:errors/></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>

        <td  valign="top" align="left">
			<FONT face="Verdana" size="1"><c:out value="${admissionFormForm.termConditions}" escapeXml="false"></c:out></FONT>
			
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
	
	<logic:notEmpty property="conditionChecklists" name="admissionFormForm">
		<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news" height="20"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      	</tr>
      
		<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>

        <td  valign="top" align="center">
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				
				<nested:iterate id="desc" property="conditionChecklists" name="admissionFormForm" indexId="count">
				<tr>
					<td align="right" valign="baseline" width="5%">
						<logic:equal value="true" property="mandatory" name="desc"><span class='Mandatory'>*</span></logic:equal>
						<%--<logic:equal value="false" property="mandatory" name="desc">&nbsp;&nbsp;&nbsp;</logic:equal>--%>
					</td>
					<td align="right" width="5%">
					<input type="hidden" id="selected_<c:out value='${count}'/>" name="selected_<c:out value='${count}'/>" value="<nested:write name='desc' property='tempChecked'/>"/>
					
					<input type="checkbox" id="selected1_<c:out value='${count}'/>" name="conditionChecklists[<c:out value='${count}'/>].checked" />
														<script type="text/javascript">
															selectedId = document.getElementById("selected_<c:out value='${count}'/>").value;
															if(selectedId == "true") {
																	document.getElementById("selected1_<c:out value='${count}'/>").checked = true;
															}		
														</script></div>
					</td>
					<td align="left" width="90%">&nbsp;&nbsp;<FONT face="Verdana" size="1"><nested:write property="checklistDescription" name="desc"/></FONT> </td>
				</tr>
				</nested:iterate>
			</table>
			
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

	</logic:notEmpty>
	


      
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35"><div align="right">
                <html:button property="" onclick="submitAdmissionForm('acceptTermsConditions')" styleClass="formbutton" value="Accept"></html:button>
            </div></td>
            <td width="1%"></td>
            <td width="51%"><div align="left">
			<c:choose>
			<c:when test="${admissionFormForm.singlePageAppln}">
	            <logic:equal value="false" name="admissionFormForm" property="outsideCourseSelected">
	            <html:button property=""  styleClass="formbutton" value="Decline" onclick="submitAdmissionForm('initOutsideSinglePageAccess')"></html:button>
				</logic:equal>
				<logic:equal value="true" name="admissionFormForm" property="outsideCourseSelected">
	            <html:button property=""  styleClass="formbutton" value="Decline" onclick="submitAdmissionForm('forwardOnlineFirstPage')"></html:button>
				</logic:equal>
			</c:when>
			<c:otherwise>
	            <logic:equal value="false" name="admissionFormForm" property="outsideCourseSelected">
	            <html:button property=""  styleClass="formbutton" value="Decline" onclick="submitAdmissionForm('initOnlineApply')"></html:button>
				</logic:equal>
				<logic:equal value="true" name="admissionFormForm" property="outsideCourseSelected">
	            <html:button property=""  styleClass="formbutton" value="Decline" onclick="submitAdmissionForm('forwardOnlineFirstPage')"></html:button>
				</logic:equal>
			</c:otherwise>
			</c:choose>
			</div></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>
</body>

</html:html>