<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<script type="text/javascript">

function searchEmp() {
	document.getElementById("method").value="searchEmployee";
}

function resetMessages() {
	document.getElementById("searchby").selectedIndex = "";
	document.getElementById("searchfor").value= " ";
	resetErrMsgs();
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
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even"><table width="100%" cellspacing="1" cellpadding="2">
              <tr>
                <td width="23%"  class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.serachby.col"/></div></td>
                <td width="24%" class="row-even">
	                <html:select property="searchBy" styleId="searchby" styleClass="combo">
	                    <html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
	                    <html:option value='<%=CMSConstants.SEARCH_EMP_DEPARTMENT %>'><bean:message key="knowledgepro.admin.department.report"/></html:option>
	                    <html:option value='<%=CMSConstants.SEARCH_EMP_EMPID %>'><bean:message key="employee.info.reportto.empid"/></html:option>
	                    <html:option value='<%=CMSConstants.SEARCH_EMP_EMPNAME %>'><bean:message key="employee.info.reportto.empnm"/></html:option>
	                </html:select>
                </td>
                <td width="22%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.searchfor.label.col"/> :</div></td>
                <td width="31%" class="row-even"><html:text property="searchFor" styleId="searchfor" size="20" maxlength="50" /></td>
              </tr>
            </table></td>
                  </tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">
	            <html:submit styleClass="formbutton" onclick="searchEmp()">
	            <bean:message key="knowledgepro.admin.search"/></html:submit>
	        </td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left">
	            <html:button property="" styleClass="formbutton" value="Reset" onclick="resetMessages()" >
	            <bean:message key="knowledgepro.admin.reset"/></html:button>
	        </td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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