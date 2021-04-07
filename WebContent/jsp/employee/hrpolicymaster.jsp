<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function addHrPolicy() {
	document.getElementById("method").value="addHRPolicy";
}

function deleteHrPolicy(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("pid").value = id;
	if(deleteConfirm)
	document.location.href = "hrpolicy.do?method=deleteHRPolicy&policyId="+id;
}

function viewHrPolicy(id){
	document.getElementById("pid").value = id;
	document.location.href = "hrpolicyDownload.do?policyId="+id;
}

function resetMessages() {
	document.getElementById("policyName").value= " ";
	document.getElementById("file").value = "";
	resetErrMsgs();
}

</script>
</head>
<html:form action="/hrpolicy" method="POST" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="addHRPolicy" />
<html:hidden property="formName" value="hrPolicyForm"/>
<html:hidden property="policyId" styleId="pid"/>
<html:hidden property="pageType" value="1" />
<table width="100%" border="0">
 <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.employee.hrpolicymaster.label"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.employee.hrpolicymaster.label"/></strong></td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="23%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.policyname.label"/></div></td>
                <td width="20%" class="row-even">
                  <html:text property="policyName" styleClass="TextBox" styleId="policyName" size="16" maxlength="50"/>
                </td>
                <td width="22%" height="25" class="row-odd" ><div align="right" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.policyfile.label"/></div></td>
                <td width="35%" height="25" colspan="3" class="row-even" >
                	<html:file property="formFile" styleClass="body" styleId="file" size="50"/>
                </td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="45%" height="35"><div align="right">
              <html:submit styleClass="formbutton" onclick="addHrPolicy()"><bean:message key="knowledgepro.submit" />
              				</html:submit>
            </div></td>
            <td width="2%"></td>
            <td width="53%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
							<bean:message key="knowledgepro.admin.reset" /></html:button></td>
          </tr>
        </table> </td>
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
                <td height="25" width="6%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                <td width="40%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.employee.policyname.required"/></td>
                <td width="42%" class="row-odd" align="center"><bean:message key="knowledgepro.employee.filename.label"/></td>
                 <td width="6%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.view"/></div></td>
                <td width="6%" class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
              </tr>
              <c:set var="temp" value="0"/>
                <logic:notEmpty name="hrPolicyForm" property="policiesList">
                <logic:iterate id="pol" name="hrPolicyForm" property="policiesList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="41%" height="25" class="row-even" align="center"><bean:write name="pol" property="name"/></td>
                   		<td width="41%" class="row-even" align="center"><bean:write name="pol" property="fileName"/></td>
                   		<td width="6%" align="center" class="row-even"><div align="center">
                   		    <img src="images/View_icon.gif" width="16" height="16" style="cursor:pointer" onclick="viewHrPolicy('<bean:write name="pol" property="id"/>')"></div></td>
			            <td width="6%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteHrPolicy('<bean:write name="pol" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="41%" height="25" class="row-white" align="center"><bean:write name="pol" property="name"/></td>
               			<td width="41%" class="row-white" align="center"><bean:write name="pol" property="fileName"/></td>
               			<td width="6%" align="center" class="row-white"><div align="center">
                   		    <img src="images/View_icon.gif" width="16" height="16" style="cursor:pointer" onclick="viewHrPolicy('<bean:write name="pol" property="id"/>')"></div></td>
			            <td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteHrPolicy('<bean:write name="pol" property="id"/>')"></div></td>
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