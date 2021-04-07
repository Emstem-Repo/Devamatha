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
<html:form action="/admissionFormSubmit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="16"/>
<html:hidden property="formName" value="admissionFormForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" /><span class="Bredcrumbs">&gt;&gt;<bean:message key="admissionForm.transferview.main.label" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.transferview.main.label" /></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div></td></tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%"  border="0" cellpadding="0" cellspacing="1">
             <tr >
	            
	            <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.semestername"/></div></td>
	            
					<td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.lateralentry.maxmark.label"/></div></td>
 					<td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.lateralentry.minmark.label" /></div></td>
			
 					<td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.lateralentry.obtainmark.label" /></div></td>
 					<td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.lateralentry.passyear.label" /></div></td>
					<td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.lateralentry.passmonth.label" /></div></td>
					
            </tr>
              <nested:iterate property="transferDetails" id="semId"  type="com.kp.cms.to.admin.ApplicantTransferDetailsTO" name="admissionFormForm" indexId="count">
				<%
					String dynaStyle="";
					if(count%2==0)
						dynaStyle="row-even";
					else
						dynaStyle="row-white";
				%>
			 <tr >
	            
	            <td class="<%=dynaStyle %>" ><div align="center"><nested:write property="semesterName"></nested:write></div> </td>
	            
	            	<td class="<%=dynaStyle %>"><div align="center"><nested:write property="maxMarks"></nested:write> </div></td>
					<td class="<%=dynaStyle %>"><div align="center"><nested:write property="minMarks"></nested:write> </div></td>
				
				<td class="<%=dynaStyle %>" ><div align="center"><nested:write property="marksObtained"></nested:write> </div></td>
	            <td class="<%=dynaStyle %>" ><div align="center">
	              <nested:write property="yearPass"></nested:write>
				 </div></td>
				<td class="<%=dynaStyle %>" ><div align="center">
				<logic:equal name="semId" property="monthPass" value="1">JANUARY</logic:equal>
					<logic:equal name="semId" property="monthPass" value="2">FEBRUARY</logic:equal>
					<logic:equal name="semId" property="monthPass" value="3">MARCH</logic:equal>
					<logic:equal name="semId" property="monthPass" value="4">APRIL</logic:equal>
					<logic:equal name="semId" property="monthPass" value="5">MAY</logic:equal>
					<logic:equal name="semId" property="monthPass" value="6">JUNE</logic:equal>
					<logic:equal name="semId" property="monthPass" value="7">JULY</logic:equal>
					<logic:equal name="semId" property="monthPass" value="8">AUGUST</logic:equal>
					<logic:equal name="semId" property="monthPass" value="10">OCTOBER</logic:equal>
					<logic:equal name="semId" property="monthPass" value="11">NOVEMBER</logic:equal>
					<logic:equal name="semId" property="monthPass" value="12">DECEMBER</logic:equal>
				 </div></td>
				
            </tr>
				
			</nested:iterate> 
				<tr>
					<td height="25" colspan="1" class="row-odd" ><div align="center"><bean:message key="admissionForm.transferentry.unvAppr.label" /></div></td>
					<td height="25" colspan="1" class="row-odd" ><div align="center"><bean:message key="admissionForm.transferentry.regAlloted.label" /></div></td>
					<td height="25" colspan="2" class="row-odd" ><div align="left"><bean:message key="admissionForm.transferentry.arrears.label" /></div></td>
					<td height="25" colspan="2" class="row-odd" ><div align="left"><bean:message key="admissionForm.transferentry.prevColg.label" /></div></td>
				</tr>
				<tr>
				<td class="row-even" colspan="1" ><div align="center"><nested:write property="transUnvrAppNo"></nested:write> </div></td>
				<td class="row-even" colspan="1" ><div align="center"><nested:write property="transRegistationNo"></nested:write> </div></td>
				<td class="row-even" colspan="2" ><div align="left"><nested:write property="transArrearDetail"></nested:write> </div></td>
				<td class="row-even" colspan="2" ><div align="left"><nested:write property="transInstituteAddr"></nested:write> </div></td>
				</tr>  
		         <tr>
                   <td height="35" colspan="6" class="body" >
			
              			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><input type="button"
										class="formbutton" value="Close"
										onclick="javascript:self.close();" /></div>
									</td>
									<td width="2%">&nbsp;</td>
									<td width="53%">&nbsp;</td>
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