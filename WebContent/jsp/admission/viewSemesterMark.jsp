<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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
<html:form action="/InterviewResultEntry">
<table width="98%" border="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.semestermark.main.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%"  border="0" cellpadding="0" cellspacing="0">
             <c:if test="${interviewResultEntryForm.isLanguageWiseMarks == 'false'}">
			 	<tr>
			 		<td colspan="4">
			 			<font color="red">Note &nbsp;&nbsp;:&nbsp;&nbsp;Languages not to be considered</font>
			 		</td>
			 	</tr>
			 </c:if>
             <tr>
	            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.semesternumber" /></div></td>
	            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.semestername" /></div></td>
	             <c:if test="${interviewResultEntryForm.isLanguageWiseMarks == 'true'}">
					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withlangtotal.label"/></div></td>
 					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withlangobtain.label"/></div></td>
				</c:if>
 					<c:choose>
				 	<c:when test="${interviewResultEntryForm.isLanguageWiseMarks == 'true'}">
				 		<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangobtain.label"/></div></td>
						<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangtotal.label"/></div></td>
				 	</c:when>
				 	<c:otherwise>
				 		<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.obtain.label"/></div></td>
					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.total.label"/></div></td>
				 	</c:otherwise>
				 </c:choose>
            </tr>
            <logic:iterate property="semesterList" id="semId"  type="com.kp.cms.to.admin.ApplicantMarkDetailsTO" name="interviewResultEntryForm" indexId="count">
				<c:choose>
					<c:when test="${count%2 == 0}">
						<tr class="row-even">
					</c:when>
					<c:otherwise>
						<tr class="row-white">
					</c:otherwise>
				</c:choose>
						    <td><div align="center"><bean:write name="semId" property="semesterNo"></bean:write></div></td>
				            <td><div align="center"><bean:write name="semId" property="semesterName"></bean:write></div> </td>
				            	<c:if test="${interviewResultEntryForm.isLanguageWiseMarks == 'true'}">
				            		<td><div align="center"><bean:write name="semId" property="maxMarks_languagewise"></bean:write> </div></td>
									<td><div align="center"><bean:write name="semId" property="marksObtained_languagewise"></bean:write> </div></td>
								</c:if>
							<td><div align="center"><bean:write name="semId" property="maxMarks"></bean:write> </div></td>
				            <td><div align="center"><bean:write name="semId" property="marksObtained"></bean:write> </div></td>
            			</tr>
			</logic:iterate>   
			     <tr>
                   <td height="35" colspan="6" class="body" >
	                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	                  <tr>
	                    <td width="45%" height="35"><div align="right"><input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/></div></td>
	                    <td width="2%">&nbsp;</td>
	                    <td width="53%">&nbsp;</td>
	                  </tr>
	                </table>
                   </td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
</td>
  </tr>
</table>
</html:form>