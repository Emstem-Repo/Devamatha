<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function downloadHrPolicy(id) {
	document.getElementById("pid").value=id;
	document.location.href = "hrpolicyDownload.do?policyId="+id;
}

function resetMessages() {
	resetErrMsgs();
}

</script>
</head>
<html:form action="/hrpolicies" method="POST">
<html:hidden property="method" styleId="method"/>
<html:hidden property="formName" value="hrPolicyForm"/>
<html:hidden property="policyId" styleId="pid"/>
<html:hidden property="pageType" value="1" />

<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.employee.hrpolicies.label"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.employee.hrpolicies.label"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="25" class="heading"><div align="center"><bean:message key="knowledgepro.employee.hrpolicies.label"/></div></td>
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
              <c:set var="temp" value="0"/>
                <logic:notEmpty name="hrPolicyForm" property="policiesList">
                <logic:iterate id="pol" name="hrPolicyForm" property="policiesList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="41%" height="25" class="row-even" align="center"><bean:write name="pol" property="name"/></td>
                   		<td width="41%" class="row-even" align="center"><a href="#" onclick="downloadHrPolicy('<bean:write name="pol" property="id"/>')">Click Here</a></td>
			        </tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="41%" height="25" class="row-white" align="center"><bean:write name="pol" property="name"/></td>
               			<td width="41%" class="row-white" align="center"><a href="#" onclick="downloadHrPolicy('<bean:write name="pol" property="id"/>')">Click Here</a></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>