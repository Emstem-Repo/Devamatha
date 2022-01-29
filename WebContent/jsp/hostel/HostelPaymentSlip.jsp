<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<head>
<script type="text/javascript">
function goToPaymentSlipInitPage(){
	document.location.href = "HostelPaymentSlip.do?method=initHostelPaymentSlip";
}

function openChallanPrint(){
	//alert("hello");
	var url ="HostelPaymentSlip.do?method=printChallan";
	window.open(url,"challan_details","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>
</head>
<html:form action="/HostelPaymentSlip">
<html:hidden property="method" styleId="method" value="getChallanPrintData"/>
<html:hidden property="formName" value="hostelPaymentSlipForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.paymentSlip"/>&gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.paymentSlip"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
     <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

	        <tr>
				<td colspan="6" align="left">
				<!--<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
				--><div id="errorMessage"><FONT color="red"><html:errors/></FONT>
				<FONT color="green"><html:messages id="msg"
					property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages></FONT></div>
			    </td>
	       </tr> 
          &nbsp;&nbsp;
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="16%" height="24" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.entry.hostel.name"/> </div></td>
                  <td width="16%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.hostelName"/></td>
                  <td width="16%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.requisitionNo"/></div></td>
                  <td width="15%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.requisitionNo"/></td>
                  <td width="11%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.slipNo"/></div></td>  
                  <td width="26%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.slipNo"/></td>
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
        <td class="heading"><bean:message key="knowledgepro.admission.details"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="50%" align="center" cellpadding="2" cellspacing="1">
              <c:choose>
				<c:when test="${hostelPaymentSlipForm.searchBy==1}">
                <logic:notEmpty name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO">	
				  <logic:iterate id="feeDetailsTO" name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO" indexId="count">
		          <tr>
		            <td width="50%" class="row-odd"  ><div align="right"><bean:write name="feeDetailsTO" property="feeTypeName"/></div></td>
		             <td width="50%" class="row-even" ><bean:write name="feeDetailsTO" property="feeAmount"/></td>
                      
                  </tr>
               </logic:iterate>
             </logic:notEmpty>
             <tr> 
				<td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.feepays.totalamount"/></div></td>
		        <td width="50%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeTotalAmount"/></td>
              </tr>
            </c:when>
            <c:otherwise>
             <logic:notEmpty name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO">	
				<tr>
					<td class="row-odd">
						Date
					</td>
					<td class="row-odd">
						Amount
					</td>
					<td class="row-odd">
						Description
					</td>
				</tr>
				<logic:iterate id="feeDetailsTO" name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO" indexId="count">
		          <tr>
		            <td width="50%" class="row-even"  ><div align="right"><bean:write name="feeDetailsTO" property="fineDate"/></div></td>
		             <td width="50%" class="row-even" ><bean:write name="feeDetailsTO" property="fineAmount"/></td>  
                     <td width="50%" class="row-even" ><bean:write name="feeDetailsTO" property="fineDescription"/></td>
                  </tr>
               	</logic:iterate>
             </logic:notEmpty>
             <tr> 
				<td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.feepays.totalamount"/></div></td>
		        <td width="50%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeTotalAmount"/></td>
              </tr>      
           </c:otherwise>

			</c:choose>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="38%" height="35" align="right"><html:submit styleClass="formbutton"  value="Print Slip" onclick="openChallanPrint()"></html:submit></td>
            <td width="2%">&nbsp;</td>
            <td width="44%"><html:cancel styleClass="formbutton" value="Back"></html:cancel></td>
          </tr>
        </table></td>
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

 <!--<tr> 
				<td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.feepays.totalamount"/></div></td>
		        <td width="50%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeTotalAmount"/></td>
              </tr>
           --><!--  <tr>
            <td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.hostel.paymentSlip.fine.Amount"/></div></td>
             <td width="50%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeTotalAmount"/></td>
                    
          </tr>
            -->
