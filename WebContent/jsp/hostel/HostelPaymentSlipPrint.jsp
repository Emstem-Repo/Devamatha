<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-tld/displaytag.tld" prefix="display"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<head>
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
</head>
<html:form action="/HostelPaymentSlip">
<html:hidden property="method" styleId="method" value="getChallanPrintData"/>
<html:hidden property="formName" value="hostelPaymentSlipForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
	<c:choose>
		<c:when test="${hostelPaymentSlipForm.searchBy==1}">
        	<div align="centre" class="heading">Fee &nbsp;<bean:message key="knowledgepro.hostel.paymentSlip"/><br><br>
        </c:when>
        <c:otherwise>
        	<div align="centre" class="heading"></div>Fine &nbsp;<bean:message key="knowledgepro.hostel.paymentSlip"/><br><br>
        </c:otherwise>
    </c:choose>
    <tr>
        <td height="19" valign="top" ></td>
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
          <tr>
            <td><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="16%" height="24" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.entry.hostel.name"/> </div></td>
                  <td width="16%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.hostelName"/></td>
                  <td width="9%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.paymentSlip.generated.date"/></div></td>
                  <td width="10%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="date"/></td>
                </tr>
                <tr>  
                  <td width="21%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.paymentSlip.student.staffId"/>&nbsp;:&nbsp;</div></td>
                  <td width="23%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="studentOrStaffId"/></td>
                  <td width="19%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.paymentSlip.student.staff.name"/>&nbsp;:&nbsp;</div></td>
                  <td width="21%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="studentOrStaffName"/></td>
                </tr>
                <tr> 
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
        <td height="19" valign="top" ></td>
        <td class="heading"><bean:message key="knowledgepro.admission.details"/></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
        <td height="19" valign="top" ></td>
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
		            <td width="50%" class="row-odd"  ><div align="right"><bean:write name="feeDetailsTO" property="feeTypeName"/>&nbsp;:&nbsp;</div></td>
		             <td width="50%" class="row-even" ><bean:write name="feeDetailsTO" property="feeAmount"/></td>
                      
                  </tr>
               </logic:iterate>
             </logic:notEmpty>
             <tr> 
				<td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.feepays.totalamount"/>&nbsp;:&nbsp;</div></td>
		        <td width="50%" class="row-even" ><bean:write name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeTotalAmount"/></td>
              </tr>
            </c:when>
            <c:otherwise>
             <logic:notEmpty name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO">	
				  <logic:iterate id="feeDetailsTO" name="hostelPaymentSlipForm" property="hostelPaymentSlipTO.feeDetailsTO" indexId="count">
		          <tr>
		            <td width="50%" class="row-odd"  ><div align="right"><bean:write name="feeDetailsTO" property="fineDate"/>&nbsp;:&nbsp;</div></td>
		             <td width="50%" class="row-even" ><bean:write name="feeDetailsTO" property="fineAmount"/></td>  
                  </tr>
               </logic:iterate>
             </logic:notEmpty>
             <tr> 
				<td width="50%" class="row-odd"  ><div align="right"><bean:message key="knowledgepro.feepays.totalamount"/>&nbsp;:&nbsp;</div></td>
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
        <td height="26" valign="top" ></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <!--<tr>
            <td width="38%" height="35" align="right"><html:submit styleClass="formbutton"  value="Print Slip"></html:submit></td>
            <td width="2%">&nbsp;</td>
            <td width="44%"><html:button styleClass="formbutton" property="" value="Back"></html:button></td>
          </tr>
        --></table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <!--<tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    --></table></td>
  </tr>
</table>
</html:form>

<script type="text/javascript">
	window.print();
</script>

 














