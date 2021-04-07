<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<head>
<title>:: CMS ::</title>
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
</style>
<script type="text/javascript">
function openWindow()
{
	 var url ="accountHeadsReport.do?method=getAllAccountHeadsReport&print=print";
	myRef = window.open(url,"accountHeadsReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>
</head>

<html:form action="accountHeadsReport" method="post">
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
	<td><span class="Bredcrumbs"><bean:message
		key="knowledgepro.reports" /> <span class="Bredcrumbs">&gt;&gt;
		<bean:message key ="knowledgepro.pettycash.list.sub"/>
		&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key ="knowledgepro.pettycash.list.sub"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="8" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="8" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td width="100%" valign="top">

			<display:table export="true" uid="id" name="sessionScope.pcAccountHeadsListTO" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
				<display:setProperty name="export.excel.filename" value="AccountHeadsReport.xls"/>
				<display:setProperty name="export.xml" value="false" />
				<display:setProperty name="export.csv.filename" value="AccountHeadsReport.csv"/>
			<c:choose>
				<c:when test="${temp == 0}">
			
						<display:column property="id" sortable="true" title="Account Heads Id" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="accCode" sortable="true" title="Account Code" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="accName" sortable="true" title="Account Name" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="bankAccNo" sortable="true" title="Bank Account Number" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="isActives" sortable="true" title="Is Fixed Amount" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="amount" sortable="true" title="Amount" class="row-even" headerClass="row-odd" style="width:16%"/>
						<display:column property="pcBankAccNumber" sortable="true" title="Pc BankAccount Number" class="row-even" headerClass="row-odd" style="width:12%"/>
						<display:column property="groupName" sortable="true" title="Pc Account HeadGroup Name" class="row-even" headerClass="row-odd" style="width:12%"/>
						
				<c:set var="temp" value="1" />
				</c:when>
				<c:otherwise>
						<display:column property="id" sortable="true" title="Account Heads Id" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="accCode" sortable="true" title="Account Code" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="accName" sortable="true" title="Account Name" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="bankAccNo" sortable="true" title="Bank Account Number" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="isActives" sortable="true" title="Is Fixed Amount" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="amount" sortable="true" title="Amount" class="row-white" headerClass="row-odd" style="width:16%"/>
						<display:column property="pcBankAccNumber" sortable="true" title="Pc BankAccount Number" class="row-white" headerClass="row-odd" style="width:12%"/>
						<display:column property="groupName" sortable="true" title="Pc Account HeadGroup Name" class="row-white" headerClass="row-odd" style="width:12%"/>
						
						<c:set var="temp" value="0" />
				</c:otherwise>
			</c:choose>										
			</display:table>
			<div align="center"><html:button  style="button" property="" onclick="openWindow()"><bean:message key="knowledgepro.pettycash.accheads.print"/>
							</html:button>	</div>
             </td>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>




