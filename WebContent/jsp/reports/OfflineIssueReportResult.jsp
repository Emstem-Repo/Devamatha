<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>
<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">

<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "OfflineIssueReport.do?method=initOfflineIssue";
    }
</SCRIPT>
<html:form action="BelowRequiredPercentage" method="post">
<html:hidden property="method" styleId="method" value=""/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.reports"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.report.student.OfflineIssue.displayName"/>&gt;&gt;</span></span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.report.student.OfflineIssue.displayName"/></strong></div></td>
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
      		  <td align="center" class="heading"><bean:write property="organizationName" name="offlineIssueForm"/> </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
			<tr>
              <td width="5"  background="images/left.gif"></td>
      		  <td align="center"> <span class="heading">
      		  List of Off line Applications Issued
            </span> </td>
              <td width="5" height="30"  background="images/right.gif"></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <span class="heading"><bean:message key="knowledgepro.report.student.OfflineIssue.displayName"/> </span>
      <td valign="top">       
	<div style="overflow: auto; width: 914px; ">
		<c:set var="temp" value="0" />  
		<display:table export="true" uid="due" name="sessionScope.StudentOfflineList" requestURI="" defaultorder="ascending" pagesize="10" style="width:100%">
			<display:setProperty name="export.excel.filename" value="OfflineApplicationsIssued.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="OfflineApplicationsIssued.csv"/>
				<display:column style="padding-right: 80px;" sortable="true" title="course" property="courseName" class="row-even" headerClass="row-odd"></display:column>
				<display:column style="padding-right: 80px;" sortable="true" title="Application No." property="applicationNo" class="row-even" headerClass="row-odd"></display:column>
				<display:column style="padding-right: 80px;" sortable="true" title="Receipt No." property="receiptNo" class="row-even" headerClass="row-odd"></display:column>
				<display:column style="padding-right: 80px;" sortable="true" title="Academic Year" property="academicYear" class="row-even" headerClass="row-odd"></display:column>
				<display:column style="padding-right: 80px;" sortable="true" title="Amount" property="amount" class="row-even" headerClass="row-odd"></display:column>
				<display:column style="padding-right: 80px;" sortable="true" title="Date" property="date" class="row-even" headerClass="row-odd"></display:column>
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
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
