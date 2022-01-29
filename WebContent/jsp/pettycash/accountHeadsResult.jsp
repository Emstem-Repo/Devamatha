<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display" %>

<%@ page buffer = "500kb" %>
<link href="css/displaytag.css" rel="stylesheet" type="text/css">
<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/interviewprocess.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
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
</head>
<SCRIPT type="text/javascript">
    function cancelAction() {
    	document.location.href = "pettyCashAccHeads.do?method=initAccountHeadReport";
    }
    
</SCRIPT>
<body>

<html:form action="pettyCashAccHeads" method="post">
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="formName" value="pettyCashAccHeadForm"/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.pettycash"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.pettycash.list.sub" /><span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.pettycash.list.sub" /></strong></div></td>
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
             
		        <logic:notEmpty name="pettyCashAccHeadForm" property="startDate">
    				<logic:notEmpty name="pettyCashAccHeadForm" property="endDate"><div class="row-white" style="text-align: center; padding-bottom: 7px; text-size: 10px;">
							<FONT size="3"><bean:message key="knowledgepro.pettycash.list.sub" />  <bean:message key="knowledgepro.hostel.adminmessage.fromName" /> <bean:write name="pettyCashAccHeadForm" property="startDate"/> <bean:message key="knowledgepro.attendance.summaryreportto" /> <bean:write name="pettyCashAccHeadForm" property="endDate"/></FONT>
							</div>
    		    	</logic:notEmpty>	
       		    </logic:notEmpty>
      <td valign="top">       
<div style="overflow: auto; width: 1100px; ">
	<c:set var="temp" value="0" />  
		
		<display:table export="true" uid="accountUid"  id = "accountHead" name="sessionScope.accountHeadsList" requestURI="" defaultorder="ascending" pagesize="10">
			<display:setProperty name="export.excel.filename" value="AccountHeads.xls"/>
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv.filename" value="AccountHeads.csv"/>
		
				<display:column style=" width: 200px;height: 40px" property="accCode" sortable="true" title="Account Code" class="row-even" headerClass="row-odd" />
				<display:column style=" width: 300px;" property="accName" sortable="true" title="Account Name" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="bankAccNo" sortable="true" title="Bank Account No" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="amount" sortable="true" title="Amount" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="fixedAmt" sortable="true" title="Fixed Amount" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="userCode" sortable="true" title="User Code" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="atStation" sortable="true" title="At Station" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="atTime" sortable="true" title="At Time" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="date" sortable="true" title="At Date" class="row-even" headerClass="row-odd" media="html csv excel"/>
				<display:column style=" width: 130px;" property="academicYear" sortable="true" title="Academic Year" class="row-even" headerClass="row-odd" media="html csv excel"/>
				
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
</body>
</html:html>
