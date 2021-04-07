<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function cancelAction() {
	document.location.href = "empsearch.do?method=initEmpSearch";
}

</script>
</head>
<html:form action="/empsearch" method="POST">
<html:hidden property="method" styleId="method" value="searchEmployee" />
<html:hidden property="formName" value="searchForm"/>
<html:hidden property="pageType" value="1" />

<table width="100%" border="0">
   <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.employee.searchemployee.label"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.searchemployee.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" class="row-odd" ><bean:message key="employee.info.reportto.empid"/></td>
                <td class="row-odd" ><bean:message key="employee.info.reportto.empnm"/></td>
                <td class="row-odd" ><bean:message key="knowledgepro.admin.department.report"/></td>
                <td class="row-odd" ><bean:message key="knowledgepro.employee.jobtitle.label"/></td>
                <td class="row-odd" ><bean:message key="knowledgepro.employee.empstatus.label"/></td>
                <td class="row-odd" ><bean:message key="knowledgepro.employee.contact.mobile.ext.label"/></td>
              </tr>
              <logic:notEmpty name="searchForm" property="empSearchList">
              <logic:iterate id="emp" name="searchForm" property="empSearchList" indexId="count">
               
                   	<tr>
                   		<td width="12%" height="35" class="row-even" ><bean:write name="emp" property="empCode"/></td>
                   		<td width="20%" class="row-even" ><bean:write name="emp" property="empName"/></td>
                   		<td width="22%" class="row-even" ><bean:write name="emp" property="departmentName"/></td>
		                <td width="22%" class="row-even" ><bean:write name="emp" property="jobTitle"/></td>
		                <td width="21%" class="row-even" ><bean:write name="emp" property="jobStatus"/></td>
		                <td width="25%" class="row-even" ><bean:write name="emp" property="contactMobileExt"/></td>
			        </tr>
                   
              </logic:iterate>
              </logic:notEmpty>
                
            </table></td>
            
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="61" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
          
              <table width="100%" height="48"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="25"><div align="center">                  
					<html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button>
                  </div></td>
                </tr>
              </table>
            
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
    </td>
  </tr>
</table>
</html:form>