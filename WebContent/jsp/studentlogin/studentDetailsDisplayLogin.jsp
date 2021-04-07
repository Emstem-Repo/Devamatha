<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<link rel="stylesheet" href="css/StudentLayout1Styles.css">
<script type="text/javascript">
	function Close() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
</script>
<html:form action="/StudentLoginAction">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="loginform" />
<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td >&nbsp;</td>
  </tr>
  <tr>
          <td valign="top" align="left"><table width="500" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.profile"/></td>
              <td width="13" class="tr">&nbsp;</td>
            </tr>
            <tr>
              <td width="13" class="le" valign="top">&nbsp;</td>
              <td valign="top"><table cellspacing="1" cellpadding="2" border="1" bordercolor="ffffff" height="410" width="100%">
                <tr>
                  <td width="151" class="studentrow-even" align="center" valign="top"><div style="border-style:solid; border-width :1px;border-color:#3292BA;height: 129px; width: 133px">
	                  <img src='<%=session.getAttribute("STUDENT_IMAGE")%>' width="133" height="129" align="top"/></div>
                  </td>
                </tr>
                <tr >
                    <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.studentlogin.name"/>:</td>
                    <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="studentName"/></td>
                </tr>
                <tr>
                    <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</td>
                    <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="className"/></td>
                 </tr>
                 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.fatherName"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="fatherName"/></td>
                 </tr>
                 <tr>  
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.motherName"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="motherName"/></td>
                 </tr>
				 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.studentinfo.nationality.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"> <bean:write name="loginform" property="nationality"/></td>
                </tr>
                    <tr>   
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.studentinfo.bloodgroup.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="bloodGroup"/></td>
                 </tr>
                 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionFormForm.emailId"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="contactMail"/></td>
                  </tr>
                    <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="knowledgepro.studentLogin.bankAcNo"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="bankAccNo"/></td>
                 </tr>     
				 <tr>
                   <td class="studentrow-odd" align="left" width="35%"><bean:message key="admissionForm.phone.main.label"/></td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="phNo1"/></td>
                   </tr>
                 <tr>
                   <td class="studentrow-odd" align="left"  width="35%"><bean:message key="admissionForm.studentinfo.currAddr.label"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="currentAddress1"/>,
                   													   <bean:write name="loginform" property="currentAddress2"/>,
                   													   <bean:write name="loginform" property="currentCity"/>,
                   													   <bean:write name="loginform" property="currentState"/>, 
                   													   <bean:write name="loginform" property="currentPincode"/>
				   </td>
				  </tr>
                  <tr>
                   <td class="studentrow-odd" align="left"  width="35%"><bean:message key="admissionForm.studentinfo.permAddr.label"/>:</td>
                   <td class="studentrow-even" align="left" width="65%"><bean:write name="loginform" property="permanentAddress1"/>,
                    												   <bean:write name="loginform" property="permanentAddress2"/>,
                    												   <bean:write name="loginform" property="permanentCity"/>, 
                   													   <bean:write name="loginform" property="permanentState"/>,
                                                                       <bean:write name="loginform" property="permanentPincode"/>
				   </td>
                 </tr> 
                
                 </table>
                 </td>
                  <td class="ri">&nbsp;</td>
           </tr>
           
           <tr>
           			<td colspan="3" align="center">
           			<html:button property="" styleClass="formbutton" value="Close" onclick="javascript:self.close();"></html:button>
           			</td>
           </tr>
            <tr>
              <td class="bl">&nbsp;</td>
              <td class="bm">&nbsp;</td>
              <td class="br">&nbsp;</td>
            </tr>
           </table>
         </td>
       </tr>
      </table>
     

</html:form>
