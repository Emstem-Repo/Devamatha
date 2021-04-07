<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>


<html:html>
<head>
<title>:: CMS ::</title>

<style type="text/css"> 
.hide {display: none} 
</style> 

<script type="text/javascript"> 
function getDetails(id){
	var url = "InvRequisitionAction.do?method=getDetails&detailId=" +id;
	myRef = window.open(url,"Details","left=20,top=20,width=700,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script> 


<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css" type="text/css">
<script language="JavaScript" src="js/calendar_us.js"></script>

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
-->
</style></head>

<body>

<html:form action="InvRequisitionAction" method="post">
<html:hidden property="method" styleId="method" value="submitInvRequisitionApproval"/>
<html:hidden property="formName" value="invRequisitionForm"/>
<html:hidden property="pageType" styleId="pageType" value=""/>
<html:hidden property="detailId" styleId="detailId" value=""/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">&gt;&gt; Inventory &gt;&gt;  Inventory Requisition Approval</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td colspan="2" background="images/Tcenter.gif" class="heading_white" > Inventory Requisition Approval</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" class="heading">&nbsp;
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"><html:messages id="msg"
		property="messages" message="true">
		<c:out value="${msg}" escapeXml="false"></c:out>
		<br>
		</html:messages> </FONT></div></td>

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
            <td width="4"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td height="25" class="row-odd" >Raised By</td>
                <td class="row-odd" >Inventory Location</td>
                <td class="row-odd" >Raised On</td>
                <td class="row-odd" >Details</td>
                <td class="row-odd" >Status</td>
                <td class="row-odd" >Comments</td>
              </tr>
              

							<logic:notEmpty name="invRequisitionForm" property="requistionApprovalList">
							<nested:iterate name="invRequisitionForm" property="requistionApprovalList" indexId="count">
								<c:choose>
								<c:when test="${temp == 0}">
                          <tr>
								<nested:hidden property="reqId"></nested:hidden>
								<td width="55" height="25" class="row-even" ><nested:write property="requestedBy"/></td>
				                <td width="95" class="row-even" ><nested:write property="department"/></td>
				                <td width="75" class="row-even" ><nested:write property="raisedOn"/></td>
								
				                <td width="49" class="row-even" ><a href="#" class="menuLink" onclick="getDetails('<nested:write property="reqId"/>')">Details</a>  </td>
				                <td width="200" class="row-even" ><nested:radio property="status"  value="Approved" styleId="approveId" > Approve</nested:radio> 
				                    <nested:radio property="status"  value="Reject" styleId="approveId" > Reject</nested:radio>
				                     
				                    <nested:radio property="status"  value="Hold" styleId="approveId" > On Hold</nested:radio>
				                    </td>
				                 <td width="200" class="row-even" >
				                   <nested:textarea property="comments" cols="25" rows="2"></nested:textarea>
				                </td>
                          </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                          <tr>
								<nested:hidden property="reqId"></nested:hidden>
								<td width="55" height="25" class="row-white" ><nested:write property="requestedBy"/></td>
				                <td width="95" class="row-white" ><nested:write property="department"/></td>
				                <td width="75" class="row-white" ><nested:write property="raisedOn"/></td>
								
				                <td width="49" class="row-white" ><a href="#" class="menuLink" onclick="getDetails('<nested:write property="reqId"/>')">Details</a></td>
				                <td width="200" class="row-white" ><nested:radio property="status"  value="Approved" styleId="approveId" > Approve</nested:radio> 
				                    <nested:radio property="status"  value="Reject" styleId="rejectId" > Reject</nested:radio>
				                     
				                    <nested:radio property="status"  value="Hold" styleId="holdId" > On Hold</nested:radio>
				                    </td>
				                 <td width="200" class="row-white" >
				                   <nested:textarea property="comments" cols="25" rows="2"></nested:textarea>
				                </td>
                          </tr>
                          <c:set var="temp" value="0" />
						</c:otherwise>
						</c:choose>
						</nested:iterate>
						</logic:notEmpty>

            </table>

</td>
            <td width="4" height="30"  background="images/right.gif"></td>
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
		<logic:notEmpty name="invRequisitionForm" property="requistionApprovalList">
          <tr>
            <td height="35" align="right"><html:submit styleClass="formbutton" value="Submit"></html:submit></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td height="35" align="left"><input name="Submit2" type="reset" class="formbutton" value="Reset" /></td>
          </tr></logic:notEmpty>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" colspan="2" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table>
</td>
  </tr>
</table>
</html:form>
</body>
</html:html>

