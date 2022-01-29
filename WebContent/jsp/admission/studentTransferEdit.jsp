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
<html:form action="/studentEdit" method="POST">
<html:hidden property="method" value=""/>
<html:hidden property="pageType" value="8"/>
<html:hidden property="formName" value="studentEditForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Admission<span class="Bredcrumbs">&gt;&gt; <bean:message key="admissionForm.transferentry.main.label" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="admissionForm.transferentry.main.label" /></strong></div></td>
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
              <nested:iterate property="transferDetails" id="semId"  type="com.kp.cms.to.admin.ApplicantTransferDetailsTO" name="studentEditForm" indexId="count">
				<%
					String dynaStyle="";
					if(count%2==0)
						dynaStyle="row-even";
					else
						dynaStyle="row-white";
				%>
			 <tr >
	            
	            <td class="<%=dynaStyle %>" ><div align="center"><nested:text property="semesterName" size="10" maxlength="50" ></nested:text></div> </td>
	            
	            	<td class="<%=dynaStyle %>"><div align="center"><nested:text property="maxMarks" styleClass="textboxSmall" size="7" maxlength="7" ></nested:text> </div></td>
					<td class="<%=dynaStyle %>"><div align="center"><nested:text property="minMarks" styleClass="textboxSmall" size="7" maxlength="7" ></nested:text> </div></td>
				
				<td class="<%=dynaStyle %>" ><div align="center"><nested:text property="marksObtained" styleClass="textboxSmall" size="7" maxlength="7" ></nested:text> </div></td>
	            <td class="<%=dynaStyle %>" ><div align="center">
	             <%String tempyearId="tempyear_"+count; %>
	            <input type="hidden" id='<%=tempyearId %>' name='<%=tempyearId %>' value="<nested:write property="yearPass" name="semId" />" />
	            <%String dynayearId="year_"+count; %>
	           <nested:select property="yearPass" styleClass="comboSmall" styleId='<%=dynayearId %>'>
					<html:option value="">Select</html:option>
              		<cms:renderYear normalYear="true"></cms:renderYear>
				</nested:select>
				<script type="text/javascript">
					var year = document.getElementById("tempyear_<c:out value='${count}'/>").value;
					if (year.length != 0) {
						document.getElementById("year_<c:out value='${count}'/>").value = year;
					}
				</script>
				 </div></td>
				<td class="<%=dynaStyle %>" ><div align="center">
				<nested:select property="monthPass"  styleClass="comboSmall">
					<html:option value="0">Select</html:option>
					<html:option value="1">JAN</html:option>
	              	<html:option value="2">FEB</html:option>
					<html:option value="3">MAR</html:option>
					<html:option value="4">APR</html:option>
					<html:option value="5">MAY</html:option>
					<html:option value="6">JUN</html:option>
					<html:option value="7">JUL</html:option>
					<html:option value="8">AUG</html:option>
					<html:option value="9">SEPT</html:option>
					<html:option value="10">OCT</html:option>
					<html:option value="11">NOV</html:option>
					<html:option value="12">DEC</html:option>
				</nested:select>
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
				<td class="row-even" colspan="1" ><div align="center"><nested:text property="transUnvrAppNo" size="20" maxlength="50" ></nested:text> </div></td>
				<td class="row-even" colspan="1" ><div align="center"><nested:text property="transRegistationNo" size="20" maxlength="20" ></nested:text> </div></td>
				<td class="row-even" colspan="2" ><div align="left"><nested:textarea property="transArrearDetail" rows="4" cols="25"></nested:textarea> </div></td>
				<td class="row-even" colspan="2" ><div align="left"><nested:textarea property="transInstituteAddr" rows="4" cols="25"></nested:textarea> </div></td>
				</tr>  
		         <tr>
                   <td height="35" colspan="6" class="body" >
			
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
			
                  <tr>
                    <td width="45%" height="35"><div align="right">
                        <html:button property="" onclick="submitMe('submitTransferEditEntry')" styleClass="formbutton" value="Submit"></html:button>
                    </div></td>
                    <td width="2%"><div align="center"><html:button property=""  styleClass="formbutton" value="Reset" onclick='resetFieldAndErrMsgs()'></html:button></div></td>
                    <td width="53%"><div align="left"><html:button property=""  styleClass="formbutton" value="Cancel" onclick="cancelMarkWindow()"></html:button></div></td>
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