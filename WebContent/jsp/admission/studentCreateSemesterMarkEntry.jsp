<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
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
<html:form action="/studentEdit">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="15"/>
<html:hidden property="formName" value="studentEditForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Admission<span class="Bredcrumbs">&gt;&gt; Semester/Year &gt;&gt;</span></span></td>
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
              <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                   <td height="35" colspan="6" class="body" >
						 <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                   <td height="35" colspan="6" class="body" ><table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22"></td>
                     </tr>
                     <tr>
						<td class="row-odd" ><div align="center">Semester Name</div></td>
						<c:if test="${studentEditForm.isLanguageWiseMarks == 'true'}">
								<td height="25" class="row-odd" ><div align="center">Obtained Marks with Language</div></td>
								<td height="25" class="row-odd" ><div align="center">Total Marks with Language</div></td>
 							
						</c:if>
 						<td height="25" class="row-odd" ><div align="center">Obtained Marks without Language</div></td>
						<td height="25" class="row-odd" ><div align="center">Total Marks without Language</div></td>
 						
					</tr>
					<%for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++) {
						String propertyName="detailMark.subject"+i;
						String valueSem="Semester"+i;
						String totalMarkprop="detailMark.subject"+i+"TotalMarks";
						String obtainMarkprop="detailMark.subject"+i+"ObtainedMarks";
						String totalMarkLangprop="detailMark.subject"+i+"_languagewise_TotalMarks";
						String obtainMarkLangprop="detailMark.subject"+i+"_languagewise_ObtainedMarks";
						String dynajs="updatetotalMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
						String dynajs2="updateObtainMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
						String dynajslang="updatelangtotalMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
						String dynajs2lang="updatelangObtainMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
					%>
                     <tr>
						 <td class="row-even" ><html:text property="<%=propertyName %>" styleId="<%=propertyName %>" size="10" maxlength="30" readonly="true" value="<%=valueSem %>"></html:text></td>
						 <c:if test="${studentEditForm.isLanguageWiseMarks == 'true'}">
						 		<td class="row-even" ><html:text property="<%=obtainMarkLangprop %>" size="7" maxlength="7" onblur='<%=dynajs2lang %>' styleId='<%=obtainMarkLangprop %>'></html:text></td>
								<td class="row-even" ><html:text property="<%=totalMarkLangprop %>" size="7" maxlength="7" onblur='<%=dynajslang %>' styleId='<%=totalMarkLangprop %>'></html:text></td>
								 
						 </c:if>
						<td class="row-even" ><html:text property="<%=obtainMarkprop %>" size="7" maxlength="7" onblur='<%=dynajs2 %>' styleId='<%=obtainMarkprop %>'></html:text></td>
						 <td class="row-even" ><html:text property="<%=totalMarkprop %>" size="7" maxlength="7" onblur='<%=dynajs %>' styleId='<%=totalMarkprop %>'></html:text></td>
						 
					</tr>
					<%} %>
					<tr>
						<td class="row-odd" >Total Marks:</td>
						<c:if test="${studentEditForm.isLanguageWiseMarks == 'true'}">
							<td class="row-even"><html:text property="detailMark.total_languagewise_ObtainedMarks" styleId="ObtaintedlangMark" size="7" maxlength="7" readonly="true"></html:text></td>
								<td class="row-even"><html:text property="detailMark.total_languagewise_Marks" styleId="totallangMark" size="7" maxlength="7" readonly="true"></html:text></td>
							
						</c:if>
							<td class="row-even"><html:text property="detailMark.totalObtainedMarks" size="7" maxlength="7"  styleId="ObtainedMark" readonly="true"></html:text></td>
							<td class="row-even"><html:text property="detailMark.totalMarks" styleId="totalMark" size="7" maxlength="7" readonly="true"></html:text></td>
						
					</tr>
					<html:hidden property="detailMark.populated" name="studentEditForm" value="true"/>
					</table>
					</td>
                 </tr>
                 <tr>
                   <td height="35" colspan="6" class="body" >
			<%
				String resetmethod="resetDetailMark("+CMSConstants.MAX_CANDIDATE_SEMESTERS+")";
			%>
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitMe('submitSemesterMark')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><div align="center"><html:button property=""  styleClass="formbutton" value="Reset" onclick='<%=resetmethod %>'></html:button></div></td>
                    <td width="53%"><div align="left"><html:button property=""  styleClass="formbutton" value="Cancel" onclick="cancelCreateStudentMarkWindow()"></html:button></div></td>
                  </tr>
                </table>
                            </td>
                 </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </td>
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