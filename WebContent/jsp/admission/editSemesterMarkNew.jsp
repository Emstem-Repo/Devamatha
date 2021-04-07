<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
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
<script type="text/javascript">
function submitModifyDetailCancelButtonNew(){
 	document.location.href="applicationEdit.do?method=forwardModifyApplicationPage";
 }
function submitAdmissionFormNew(method){
	document.applicationEditForm.method.value=method;
	document.applicationEditForm.submit();
}
</script>
<html:form action="/applicationEdit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="11"/>
<html:hidden property="formName" value="applicationEditForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.semestermark.main.label"/> &gt;&gt;</span></span></td>
  </tr>
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
             <c:if test="${applicationEditForm.isLanguageWiseMarks == 'false'}">
			 	<tr>
			 		<td colspan="4">
			 			<font color="red">Note &nbsp;&nbsp;:&nbsp;&nbsp;Languages not to be considered</font>
			 		</td>
			 	</tr>
			 </c:if>
             <tr >
	            <td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.edit.semestermarkedit.semno.label"/> </div></td>
	            <td height="25" class="row-odd" ><bean:message key="knowledgepro.admission.semestername"/></td>
	            <c:if test="${applicationEditForm.isLanguageWiseMarks == 'true'}">
					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withlangobtain.label"/></div></td>
					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withlangtotal.label"/></div></td>
 					
				</c:if>
 					<c:choose>
				 	<c:when test="${applicationEditForm.isLanguageWiseMarks == 'true'}">
				 		<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangobtain.label"/></div></td>
						<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.withoutlangtotal.label"/></div></td>
				 	</c:when>
				 	<c:otherwise>
				 		<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.obtain.label"/></div></td>
					<td height="25" class="row-odd" ><div align="left"><bean:message key="admissionForm.semmark.total.label"/></div></td>
				 	</c:otherwise>
				 </c:choose>
 					
            </tr>
              <nested:iterate property="semesterList" id="semId"  type="com.kp.cms.to.admin.ApplicantMarkDetailsTO" name="applicationEditForm" indexId="count">
				<%
					String dynaStyle="";
					if(count%2==0)
						dynaStyle="row-even";
					else
						dynaStyle="row-white";
					String obtainedMarkWithLanguageStyleID = "marksObtained_languagewise" + count.toString();
					String totalMarkWithLanguageStyleID = "maxMarks_languagewise" + count.toString();
					String obtainedMarkWithOutLanguageStyleID = "marksObtained" + count.toString();
					String totalMarkWithOutLanguageStyleID = "maxMarks" + count.toString();
					String calc= "calctotalobtainedMarkWithLanguage(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalMarkWithLanguage = "calctotaltotalMarkWithLanguage(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalobtainedMarkWithoutLan = "calctotalobtainedMarkWithoutLan(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					String calctotalMarkWithoutLan = "calctotalMarkWithoutLan(" + CMSConstants.MAX_CANDIDATE_SEMESTERS + ")";
					
				%>
			
			 <tr >
	            <td class="<%=dynaStyle %>"><div align="center"><nested:write property="semesterNo" ></nested:write> </div></td>
	            <td class="<%=dynaStyle %>" ><div align="left"><nested:text property="semesterName" size="10" maxlength="50" ></nested:text></div> </td>
	            <c:if test="${applicationEditForm.isLanguageWiseMarks == 'true'}">
	            	<td class="<%=dynaStyle %>"><div align="left"><nested:text property="marksObtained_languagewise" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=obtainedMarkWithLanguageStyleID%>' onblur='<%=calc%>'></nested:text> </div></td>
					<td class="<%=dynaStyle %>"><div align="left"><nested:text property="maxMarks_languagewise" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=totalMarkWithLanguageStyleID%>' onblur='<%=calctotalMarkWithLanguage%>'></nested:text> </div></td>
					
				</c:if>
				<td class="<%=dynaStyle %>" ><div align="left"><nested:text property="marksObtained" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=obtainedMarkWithOutLanguageStyleID%>' onblur='<%=calctotalobtainedMarkWithoutLan%>' ></nested:text> </div></td>
				<td class="<%=dynaStyle %>" ><div align="left"><nested:text property="maxMarks" styleClass="textboxSmall" size="7" maxlength="7" styleId='<%=totalMarkWithOutLanguageStyleID%>' onblur="<%=calctotalMarkWithoutLan%>"></nested:text> </div></td>
            </tr>
				
			</nested:iterate>
			<tr>
				<td class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.subject.total.marks.disp"/></div></td>
				<td class="row-even"><div align="left">&nbsp;</div></td>
				<c:if test="${applicationEditForm.isLanguageWiseMarks == 'true'}">
				<td class="row-even"><div align="left"><html:text property="totalobtainedMarkWithLanguage" styleId="totalobtainedMarkWithLanguage" size="7" maxlength="7" readonly="true"></html:text></div></td>
				<td class="row-even"><div align="left"><html:text property="totalMarkWithLanguage" styleId="totalMarkWithLanguage" size="7" maxlength="7" readonly="true"></html:text></div></td>
				</c:if>
				<td class="row-even"><div align="left"><html:text property="totalobtainedMarkWithoutLan" size="7" maxlength="7"  styleId="totalobtainedMarkWithoutLan" readonly="true"></html:text></div></td>
				<td class="row-even"><div align="left"><html:text property="totalMarkWithoutLan" styleId="totalMarkWithoutLan" size="7" maxlength="7" readonly="true"></html:text></div></td>
			</tr>   
		         <tr>
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
			%>
	<%String dynaCancel=""; %>
				<logic:equal value="true" property="admissionEdit" name="applicationEditForm">
					<%dynaCancel="submitEditDetailCancelButton()"; %>
				</logic:equal>			
				<logic:equal value="false" property="admissionEdit" name="applicationEditForm">
					<%dynaCancel="submitModifyDetailCancelButtonNew()"; %>
				</logic:equal>	
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitAdmissionFormNew('submitSemesterEditMark')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><div align="center"><html:button property=""  styleClass="formbutton" value="Reset" onclick='resetFieldAndErrMsgs()'></html:button></div></td>
                    <td width="53%"><div align="left"><html:button property=""  styleClass="formbutton" value="Cancel" onclick='<%=dynaCancel %>'></html:button></div></td>
                  </tr>
                </table>
                            </td>
                 </tr>
                 
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
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