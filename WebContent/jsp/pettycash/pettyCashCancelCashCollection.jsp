<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<html:html>
<head>
<title>:: CMS ::</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
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
</script>
</head>
<body>
<html:form action="cancelCashCollection" method="POST">
	<html:hidden property="formName" value="cashCollectionForm"
		styleId="formName" />
	<html:hidden property="pageType" value="3" styleId="pageType" />
	<html:hidden property="method" styleId="method" value="manageCashCollection"/>
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.pettycash"/>&gt;&gt; <bean:message key="knowledgepro.pettycash.cancelCashCollection"/> &gt;&gt;</span></span></td>
    
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.pettycash.cancelCashCollection"/></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage" align="left">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
		</div>
        </td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even"><table width="100%" cellspacing="1" cellpadding="2">
            <tr>
              	
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.receiptno"/>:</div></td>
                <td class="row-even" colspan="3"><bean:write name="cashCollectionForm" property="number"/></td>
             
                <td width="22%"  class="row-odd" ><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.date"/>:</div></td>
                <td width="33%" height="25" class="row-even" ><bean:write name="cashCollectionForm" property="paidDate"/></td></tr>
                  <tr>
              	
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.time"/>:</div></td>
                <td class="row-even" colspan="3"><bean:write name="cashCollectionForm" property="paidTime"/></td>
             
              	
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.appregrollno"/>:</div></td>
                <td class="row-even" colspan="3">
               
                <logic:notEmpty name="cashCollectionForm" property="refNo" scope="session">
                <bean:write name="cashCollectionForm" property="refNo"/>
                </logic:notEmpty>
                
              <logic:notEmpty name="cashCollectionForm" property="refType" scope="session">
              <c:set var="refType"><bean:write name="cashCollectionForm" property="refType"/></c:set>
              
              <c:choose>
              <c:when test="${refType == 'rollNo'}">
               <html:radio  property="refId1" styleId="need" value="false"/>
               </c:when>
               <c:otherwise>
               <html:radio property="refId2" styleId="need" value="true" disabled="true"/>
               </c:otherwise>
                </c:choose>
                <bean:message key="knowledgepro.pettycash.rollNo" />
				
				<c:choose>
				 <c:when test="${refType == 'regNo'}">
               <html:radio property="refId3" styleId="need" value="false" />
               </c:when>
               <c:otherwise>
               <html:radio property="refId4" styleId="need" value="true" disabled="true"/>
               </c:otherwise>
				</c:choose>
				<bean:message key="knowledgepro.pettycash.regNo" />
               
                <c:choose>
                <c:when test="${refType == 'appNo'}">
               <html:radio property="refId5" styleId="need" value="false"/>
               </c:when>
               <c:otherwise>
               <html:radio property="refId6" styleId="need" value="true" disabled="true"/>
               </c:otherwise>
               </c:choose>
               <bean:message key="knowledgepro.pettycash.appNo" />
           
              </logic:notEmpty>
               </td>
              </tr>
              <tr>
              	 <td width="22%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.academicyear"/>:</div></td>
                  <td class="row-even" colspan="3"><bean:write  name="cashCollectionForm" property="academicYear"/></td>
                 <td width="22%" class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.name"/>:</div></td>
                <td class="row-even"><bean:write name="cashCollectionForm" property="name"/></td>
              </tr> 
                
                 <tr>
              <td class="row-odd"><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.comments"/>:</div></td>
              <td class="row-even">
              <html:textarea name="cashCollectionForm" property="cancelComments" styleId="cancelComments"  styleClass="TextBox"  cols="30" rows="5" ></html:textarea>
              </td> 
                         
            </table></td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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
                      <td width="29%" height="25" class="row-odd" ><bean:message key="knowledgepro.pettycash.accheadgroup.acccode"/></td>
                      <td width="31%" class="row-odd" ><bean:message key="knowledgepro.pettycash.accheadgroup.accname"/></td>
                      <td width="26%" class="row-odd"><bean:message key="knowledgepro.pettycash.cancelCashCollection.amount"/></td>
                      
                    </tr>
                    <logic:notEmpty name="cashCollectionForm" property="pcCashCollectionListTO">
                   <logic:iterate id="accountHeadGroupCode" name="cashCollectionForm" property="pcCashCollectionListTO"  indexId="count">
	           
                  <tr>
                    <td height="25" class="row-even" ><bean:write name="accountHeadGroupCode" property="accCode"/></td>
                    <td height="25" class="row-even" ><bean:write name="accountHeadGroupCode" property="accName"/></td>
                    <td class="row-even" ><bean:write name="accountHeadGroupCode" property="amount"/></td>
                  </tr>
                  
	               </logic:iterate>  
                    </logic:notEmpty>
                    
                    
                   
                    <tr>
                    <td height="25" class="row-white" >&nbsp;</td>
                    <td height="25" class="row-white" ><div align="right"><bean:message key="knowledgepro.pettycash.cancelCashCollection.total"/></div></td>
                    <td colspan="2" class="row-white" >
                    <html:text styleClass="TextBox" styleId="name" name="cashCollectionForm" property="totalAmount" size="16" maxlength="20" disabled="true"/>
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
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right">
           <html:submit styleClass="formbutton" styleId="button"><bean:message key="knowledgepro.submit"/>	
			</html:submit>
           
           </td>
            <td width="2%" height="35" align="center"> </td>
            <td width="49%" height="35" align="left">&nbsp;</td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
</body>
</html:html>





