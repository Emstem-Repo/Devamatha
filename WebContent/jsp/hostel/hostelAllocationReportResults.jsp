<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendar_us.js"></script>

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
<script type="text/javascript">

function cancelAction() {
	document.location.href = "hostelAllocationReport.do?method=initAllocationReport";
}  


</script>

<body>
<html:form action="hostelAllocationReport" method="post">
<html:hidden property="method" styleId="method" value="submitAllocationReport"/>
<html:hidden property="formName" value="hostelAllocationReportForm" />
<html:hidden property="pageType" value="1"/>
<table width="98%" border="0">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.reports"/><span class="Bredcrumbs">&gt;&gt;Hostel Allocation Report<span class="Bredcrumbs">&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10" ><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Hostel Allocation Report</strong></div></td>
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
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
      <td valign="top">       

	
	<tr>
                  <td width="5" background="images/Tright_03_03.gif"></td>
                  <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    <tr >
                      <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.date"/> </div></td>
                      <td class="row-odd" ><bean:message key="knowledgepro.hostel.name.col"/> </td>
                      <td height="25" class="row-odd" ><bean:message key="knowledgepro.roomtype"/> </td>
                      <td class="row-odd" ><bean:message key="knowledgepro.hostel.allocatedRoom"/> </td>
                         </tr>
                  
                   <c:set var="temp" value="0" />
                    <logic:notEmpty name="hostelAllocationReportForm"	property="allocationList">
                    <logic:iterate id="allocation" name="hostelAllocationReportForm" property="allocationList"
					indexId="count">
					
					<c:choose>
							
							
						
					<c:when test="${count%2 == 0}">
					  <tr >
                      <td width="19%" class="row-even" ><bean:write name="allocation" property="allocationDate"/> </td>
                      <td width="19%" height="25" class="row-even" ><bean:write name="allocation" property="hostelName"/></td>
                      <td width="17%" class="row-even" ><bean:write name="allocation" property="roomType"/></td>
                      <td width="22%" height="25" class="row-even" ><bean:write name="allocation" property="allocatedRoom"/></td>
                              </tr>
                  </c:when>
			
			<c:otherwise>
	               <tr >
                      <td class="row-white" ><bean:write name="allocation" property="allocationDate"/></td>
                      <td height="25" class="row-white" ><bean:write name="allocation" property="hostelName"/></td>
                      <td height="25" class="row-white"><bean:write name="allocation" property="roomType"/></td>
                      <td class="row-white" ><bean:write name="allocation" property="allocatedRoom"/></td>
                    </tr>
                  </c:otherwise>
                  </c:choose>
                   
					</logic:iterate>
                    </logic:notEmpty>
					<tr align="center">  <td valign="top" class="news">
		
					 		</td></tr>
                  </table></td>
                  <td width="5" height="30"  background="images/Tright_03_03.gif"></td>
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