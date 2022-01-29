<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript">
	    function cancelAction() {
	    	document.location.href = "viewReqStatus.do?method=initViewReqStatus";
	    }                        
	    function printAction(){
	  var url = "viewReqStatus.do?method=printReqStatusResult";
	 myRef = window.open(url,"View Req.Status","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
	 
	   }

	    
	</SCRIPT>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>
<body>
<html:form action="viewReqStatus" method="post">
<html:hidden property="method" styleId="method" value="submitviewReqStatus"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="viewReqStatusForm" />
<html:hidden property="reportName" styleId="reportName" value="" />
            
            <table width="100%" cellspacing="1" cellpadding="2" border="0">
               <tr>
               <td width="100%" height="25" class="white" colspan="2" ><div align="center"><%=request.getAttribute("orgName")%></div></td>
				</tr>
            <logic:notEmpty name="vReqStatusTo">
            <logic:iterate id="vReqStatus" name="vReqStatusTo">
			<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.fee.applicationnostaff"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="applino"/></div></td>
				</tr>
				<tr>
		       <td width="25%" height="25" class="row-odd" ><div align="left">Name: </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="name"/></div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.hostel.appliedDate"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="appliedDate"/> </div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.hostel.reqno"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="reqno"/> </div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.hostel.name"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="appliedHostel"/></div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.hostel.reqroomtype"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="appliedRoom"/> </div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.hostel.approvedroomtype"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="approvedRoom"/> </div></td>
				</tr>
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admission.status"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="status"/> </div></td>
				</tr>
				
				<tr>
               <td width="25%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.pettycash.fee"/> </div></td>
               <td width="75%" height="25" class="row-even" align="left"><div align="left"><bean:write name="vReqStatus" property="fees"/> </div></td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
            </table>
            
            
      
</html:form>
</body>
</html:html>
<script type="text/javascript">
	window.print();
</script>