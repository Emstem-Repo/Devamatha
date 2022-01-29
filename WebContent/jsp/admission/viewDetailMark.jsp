<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<html:html>
<head>
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
</style>
</head>
<body>
<html:form action="/InterviewResultEntry">
<table width="98%" border="0">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.detailmarkview.main.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" ><table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22"></td>
                     </tr>
                     <tr>
						<td class="row-odd" ></td>
						<td class="row-odd" ><div align="center"><bean:message key="knowledgepro.admisn.subject.Name" /></div></td>
 						<td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.maxMark" /></div></td>
 						<td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.marksObtained" /></div></td>
					</tr>
					<%for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SUBJECTS;i++) {
						String propertyName="detailMark.subject"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						String dynajs="updatetotalMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						String dynajs2="updateObtainMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
					%>
                     <tr>
						 <td class="row-even" ><bean:message key="admissionForm.detailmark.subject.label"/> <%=i %></td>
						 <td class="row-even" ><html:text property="<%=propertyName %>" styleId="<%=propertyName %>" size="10" maxlength="30" readonly="true"></html:text></td>
						 <td class="row-even" ><html:text property="<%=totalMarkprop %>" size="7" maxlength="7" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>' readonly="true"></html:text></td>
						<td class="row-even" ><html:text property="<%=obtainMarkprop %>" size="7" maxlength="7" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>' readonly="true"></html:text></td>
					</tr>
					<%} %>
					<tr>
						<td class="row-odd"><bean:message key="knowledgepro.admission.maxMark" />:</td>
						<td class="row-even" ><html:text property="detailMark.totalMarks" styleId="totalMark" size="7" maxlength="7" readonly="true"></html:text></td>
						<td class="row-odd"><bean:message key="knowledgepro.admission.totalmarksObtained" />:</td>
						<td class="row-even" ><html:text property="detailMark.totalObtainedMarks" size="7" maxlength="7"  styleId="ObtainedMark" readonly="true"></html:text></td>
					</tr>
                   </table></td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
					<%
						String resetmethod="resetDetailMark("+CMSConstants.MAX_CANDIDATE_SUBJECTS+")";
					%>
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
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>