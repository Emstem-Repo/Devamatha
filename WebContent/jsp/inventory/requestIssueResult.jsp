<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<html:form action="/RequestVsIssue">
<html:hidden property="method" value="getInventoryLocation" />
<html:hidden property="formName" value="requestVsIssueForm" />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory" /> &gt;&gt; Locationwise Request Vs Issue &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white"> Locationwise Request Vs Issue</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td height="25" colspan="2" ><div align="right"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
          	<td width="5"  background="images/left.gif"></td>
            <td valign="top">
			<div style="overflow: auto; width: 914px;">
				<display:table export="true" uid="requestIssue" name="sessionScope.requestVsIssueReport" partialList="false" requestURI="" defaultorder="ascending" pagesize="20" style="width:100%" >
					<display:caption class="heading" media="html csv excel">
						<logic:notEmpty name="requestVsIssueForm" property="invLocationName">
							Locationwise Request Vs Issue Report of <bean:write name="requestVsIssueForm" property="invLocationName"/>
						</logic:notEmpty>
						<logic:notEmpty name="requestVsIssueForm" property="startDate">
							From <bean:write name="requestVsIssueForm" property="startDate"/>
						</logic:notEmpty>
						<logic:notEmpty name="requestVsIssueForm" property="endDate">
							To <bean:write name="requestVsIssueForm" property="endDate"/>
						</logic:notEmpty>
					</display:caption>
					<display:setProperty name="export.excel.filename" value="RequestVsIssue.xls" />
					<display:setProperty name="export.csv.filename" value="RequestVsIssue.csv" />
						<display:column title="Item Name(Code)" property="nameWithCode" headerClass="row-odd" class="row-even" sortable="true" group="1" style="width:20%"></display:column>
						<display:column title="Requested Quantity" property="requestedQuantity" headerClass="row-odd" class="row-even" sortable="true" style="width:10%"></display:column>
						<display:column title="Issued Quantity" property="issuedQuantity" headerClass="row-odd" class="row-even" sortable="true" style="width:10%"></display:column>
						<display:column title="Requested By" property="requestedBy" headerClass="row-odd" class="row-even" sortable="true" style="width:15%"></display:column>
						<display:column title="Requested Date" property="requestedDate" headerClass="row-odd" class="row-even" sortable="true" style="width:15%"></display:column>
						<display:column title="Issued Date" property="issuedDate" headerClass="row-odd" class="row-even" sortable="true" style="width:15%"></display:column>
				</display:table>
			</div>
            </td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="center"><html:submit styleClass="formbutton"><bean:message key="knowledgepro.admin.back" /></html:submit></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>