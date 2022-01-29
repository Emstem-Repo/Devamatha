<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<html:html>
<head>
<title>:: CMS ::</title>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>

<style type="text/css"> 
.hide {display: none} 
</style> 

<script type="text/javascript"> 
function closeWin(){

	window.close();
}
function cancelAction() {
	document.location.href = "InvRequisitionAction.do?method=initInvRequisitionReport";
}

function winOpen(reqId) {
	var url = "InvRequisitionAction.do?method=getDetails&detailId=" + reqId;
	myRef = window
			.open(url, "RequisitionReport",
					"left=20,top=20,width=400,height=800,toolbar=1,resizable=0,scrollbars=1");
}

</script> 



<style type="text/css">
<!--
body {
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
	margin-top: 5px;
}
.hide {display: none}
.hide1 {display: none}
.hide1 {display: none}
.style1 {
	color: #990000;
	font-weight: bold;
}
-->
</style></head>

<body>

<html:form action="InvRequisitionAction" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="attendanceTeacherReportForm"/>
<html:hidden property="pageType" styleId="pageType" value=""/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">Inventory &gt;&gt; Inventory Requisition Report &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" > Inventory Requisition Report</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading"><div align="right"> <span class='mandatoryfield'>* Mandatory Fields</span></div></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
		<display:table export="false" id="reqList" name="sessionScope.requisitionList" requestURI="" defaultorder="descending" pagesize="10" >
	
		<display:setProperty name="export.excel.filename" value="InventoryRequsition.xls"/>
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv.filename" value="InventoryRequsition.csv"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="requestedBy" sortable="true" title="Raised By" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="raisedOn" sortable="true" title="Raised On" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " title="Details" class="row-even" sortable="true" media="html" headerClass="row-odd">
			<A HREF="javascript:winOpen('<bean:write name="reqList" property="reqId" />');"> Details </A>
			</display:column>

			<display:column style=" padding-left: 50px; padding-right: 50px; " property="currentStatus" sortable="true" title="Status" class="row-even" headerClass="row-odd"/>
			<display:column style=" padding-left: 50px; padding-right: 50px; " property="comments" sortable="true" title="Comments" class="row-even" headerClass="row-odd"/>
			

		</display:table>
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
        <td colspan="2" class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">&nbsp;</td>
            <td width="2%" height="35" align="center"><input name="Submit22" type="button" class="formbutton" value="Cancel" onclick="cancelAction()"/></td>
            <td width="49%" height="35" align="left"></td>
          </tr>
        </table></td>
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
</body>
</html:html>

