<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<html:html>
<head>
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
<body>
<html:form action="viewReqStatus" method="post">
<html:hidden property="method" styleId="method" value="submitviewReqStatus"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="viewReqStatusForm" />
<html:hidden property="reportName" styleId="reportName" value="" />
<table width="98%" border="0" cellpadding="2" cellspacing="1">
  <tr>
    <td class="heading"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.studentrequisitionstatus"/>&gt;&gt;</span> </td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="100%" background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.hostel.studentrequisitionstatus"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="45" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	    
              <tr bgcolor="#FFFFFF">
				<td colspan="2" class="body" align="left">
				 <div id="errorMessage">
	            <FONT color="red"><html:errors/></FONT>
	             <FONT color="green">
						<html:messages id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out><br>
						</html:messages>
	            </FONT>
	            </div>
	            </td>
	          </tr>
            <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
          <tr>
            <td width="5" background="images/left.gif"></td>
            <td valign="top">
            
            
            <table width="100%" cellspacing="1" cellpadding="2">
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
               <td ></td>
               <td></td>
				</tr>
          <tr>
      
       <tr>
              <td><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            
            
            <td width="5" background="images/left.gif"></td>
            <td valign="top">
            
            
            <table width="100%" cellspacing="1" cellpadding="2">
         		
				         			<tr class="row-odd">
				<td class="bodytext" height="25">Note :  Contact College Administration for the further process along with the print of this page.</td>
				</tr>
				
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          </tr>
      
       <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
      <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      
      
      
      
      
      
      
      
      
      
      
      <tr>
        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		
					 <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="45%" height="35"><div align="right">
                      
                  </div></td>
                  <td  style="padding-right: 5px;"><html:button property="print" onclick="printAction()" styleClass="formbutton"  styleId="printme" ><bean:message key="knowledgepro.print" /></html:button></td>
                  <td ><html:button property="cancel" onclick="cancelAction()" styleClass="formbutton"><bean:message key="knowledgepro.cancel" /></html:button></td>
                  <td width="44%"></td>
                </tr>
              </table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html:html>