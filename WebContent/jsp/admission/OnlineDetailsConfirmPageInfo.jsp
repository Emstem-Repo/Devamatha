<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.kp.cms.constants.KPPropertiesConfiguration"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script type="text/javascript" src="jquery/collaps/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
<script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
 <!-- for cache controling with html code-->
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
 
 <!-- for cache controling with jsp code-->
<% 
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
response.setHeader("Expires", "0"); // Proxies.
%>
<script language="JavaScript" src="js/admission/OnlineDetailsAppCreation.js"></script>

<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<html:hidden property="focusValue" styleId="focusValue" name="onlineApplicationForm"/>
<html:hidden property="onlineApply" styleId="onlineApply" name="onlineApplicationForm"/>
<html:hidden property="pageType" value="" />
 			
 			
	
	
	<table width="80%" style="background-color: #F0F8FF" align="center">
 
    
   <tr ><td height="20px"></td></tr>   
  
  <tr ><td height="30"></td></tr>
   
   <!-- errors display -->
  <tr >
	<td  align="center">
							<div id="errorMessage" align="center">
							<FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"	property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages>
							</FONT>
							</div>
							<div id="errorMessage1" style="font-size: 11px; color: red"></div>
	</td>
	</tr>
	
 
  
 		<tr ><td height="20"></td></tr>
  
	
	
      <tr>
        <td>
        <table width="100%" border="0" cellpadding="0"  align="center" class="normal inst w" >
          <tr ><td colspan="2" height="10"></td></tr>
          <tr>
		  <td style="color:#330000; text-align:right" width="10%"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" />&nbsp;</td>
            <td width="100%" >  If you wish to preview your application click "Preview".</td>
		  </tr>
		  <tr>
		  <td style="color:#330000; text-align:right" width="6%"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" />&nbsp;</td>
			<td width="80%">  If you intend to submit the application later click "Save" and Log out. </td>
		 </tr>
		 <tr>
		 <td style="color:#330000; text-align:right" width="6%"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" />&nbsp;</td>
			<td width="80%" > Click "Edit", if corrections are required.</td>
		</tr>
		<tr>
		<td style="color:#330000; text-align:right" width="6%"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" />&nbsp;</td>
			<td width="80%" > Once final submission is made the "Edit" option will be automatically disabled.</td>
		</tr>
		 <tr>
		<td style="color:#330000; text-align:right" width="6%"><img src="images/admission/images/bullet_triangle_red.png" width="20" height="20" />&nbsp;</td>
			<td width="80%" >  Printing of application can be done only after final submission.</td>
		</tr>
		
		<tr ><td colspan="2" height="10"></td></tr>
        </table>
        </td>
      </tr>
   
  
 <tr ><td height="20px"></td></tr>   
  
  
  <tr>
  <td  width="100%" align="center">
  <html:button property="" onclick="submitAdmissionForm('submitConfirmPageOnline')" styleClass="btn" value=" Save "></html:button>
  <html:button property="" onclick="submitAdmissionForm('submitPeviewApplication')" styleClass="btn" value=" Preview "></html:button>
  <html:button property="" onclick="submitAdmissionForm('submitEditApplication')" styleClass="btn" value=" Edit "></html:button>
  </td>
  </tr>
  
  <tr ><td height="20px"></td></tr>   
  
  <tr>
  <td  width="100%" align="center">
  <html:button property="" onclick="submitFinalAdmissionForm('submitCompleteApplication')" styleClass="cntbtn" value="Submit"></html:button>
  </td>
  </tr>
  
 <tr ><td height="40"></td></tr>
   
</table>

