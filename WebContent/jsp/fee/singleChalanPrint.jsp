<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.fee.feerecipt"/></title>
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
<html:form action="/FeePayment">

<html:hidden property="method" styleId="method" value="initPrintChallen"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="3"/>
<% for(int i=0;i<3;i++ ){%>
<table width="100%" border="0" >
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
			
			<logic:notEmpty name="feePaymentForm" property="printChalanList">			                  		
            <logic:iterate id="feeAccount" name="feePaymentForm" property="printChalanList" indexId="counter">
            <td  valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="images/01.gif" width="5" height="5"></td>
                  <td width="100%" background="images/02.gif"></td>
                  <td width="11" ><img src="images/03.gif" width="5" height="5"></td>
                </tr>

                <tr>
                  <td width="10" height="0"  background="images/left.gif"></td>
                  <td height="206" valign="top">
                  <table width="100%" cellspacing="1" cellpadding="2">
					<tr >
						<td colspan="4"><div align="center">
							<bean:define id="count" name="feeAccount" property="count" type="java.lang.Integer"></bean:define>
							<img src='<%=request.getContextPath()%>/printChalanLogoServlet?count=<%=count%>' alt="Image not found"/>
							</div>
						</td>
                      </tr>                     


					<tr >
						<td height="25" colspan="4" class="row-white" ><div align="center"><span class="heading"><bean:message key="knowledgepro.fee.feerecipt"/><i>&nbsp;
						<%if(i == 0){ %>
                         - Bank Copy
						<% }else if (i == 1){ %>
	                         - Student Copy
						<%} else if (i == 2){ %>
	                         - Institution Copy</i>
						<%} %>
						</i></span></div></td>
                      </tr>

                      <tr >
                        <td width="20%" height="25" class="row-even" ><bean:message key="knowledgepro.feepays.billno"/>:&nbsp;&nbsp;<bean:write name="feePaymentForm" property="billNo"/></td>
                        <td width="20%" height="25" class="row-even" >Date: &nbsp;&nbsp;<bean:write name="feePaymentForm" property="challanPrintDate"/></td>
                        <td width="20%" class="row-even" >Time: &nbsp;&nbsp;<bean:write name="feePaymentForm" property="chalanCreatedTime"/></td>
                        <td width="40%" class="row-even">A/c. No.: &nbsp;&nbsp;<font color="blue"><b><bean:write name="feeAccount" property="printAccountName"/></b></font>&nbsp;&nbsp; 
							<c:if test="${feePaymentForm.isReprintChalan}">
							Reprint 
						</c:if>
						</td>
                      </tr>
                      <tr >
                        <td width="20%" height="25" class="row-even" >Name: &nbsp;&nbsp;<bean:write name="feePaymentForm" property="studentName"/></td>
                        <td width="20%" class="row-even" >Reg No/ App No:&nbsp;&nbsp;<bean:write name="feePaymentForm" property="applicationId"/></td>
                        <td width="20%" height="25" class="row-even" >Class: &nbsp;&nbsp;<bean:write name="feePaymentForm" property="className"/></td>
                        <td width="40%" class="row-even" >Scheme No: &nbsp;&nbsp;<bean:write name="feePaymentForm" property="semesterData"/>
						&nbsp; &nbsp;<font color="blue"><b><bean:write name = "feePaymentForm" property="paymentMode"/></b></font></td>
                      </tr>
                      <tr >
                        <td height="266" colspan="4" valign="top" class="row-white" >
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                             
                              <td height="206" valign="top"><table width="100%" border="1" cellpadding="2" cellspacing="1">
                                  <tr class="row-white">
                                    <td width="50%" height="25" colspan="2" rowspan="2" class="row-odd"><div align="center">DESCRIPTIONS</div></td>
                                    <td width="30%" height="25" rowspan="2" class="row-odd"><div align="center">Additional Fees</div></td>
                                    <td width="20%" height="17" class="row-odd"><div align="center">Amount</div></td>
                                  </tr>
                                  <tr class="row-white">
                                    <td height="14" class="row-odd"><div align="center">Rs.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ps.</div></td>
                                  </tr>
                                  <tr class="row-white" >
									<td class="row-even" valign="top" height="160" width="25%">
										<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
											<logic:notEmpty name="feeAccount" property="descList">
											<logic:iterate id="desc" name = "feeAccount" property="descList">
											<tr>
											<td class="row-even" >
												 <bean:write name="desc"/>
											</td>
											</tr>
											</logic:iterate>
											</logic:notEmpty>
										</table>
									</td>
									<td class="row-even" valign="top" height="160" width="25%">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
											<logic:notEmpty  name = "feeAccount" property="desc2List">
											<logic:iterate id="desc2" name = "feeAccount" property="desc2List">
											<tr>
											<td class="row-even">
												 <bean:write name="desc2"/>
											</td>
											</tr>
											</logic:iterate>
											</logic:notEmpty>
										</table>
									</td>
									<c:set var="addlAmt" ><bean:write name="feeAccount" property="additionalAMount"/></c:set>

									<td class="row-even" valign="top" height="160">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr><td>&nbsp;</td>
										<tr>
										<tr><td>&nbsp;</td>
										<c:if test="${addlAmt > 0}">
											<logic:notEmpty name = "feeAccount" property="additionalList">
												<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
												<tr> 
												<td class="row-even">
													  <bean:write name="addl" property="feeGroupName"/>
												</td>
												</tr>
												</logic:iterate>
											</logic:notEmpty>
										</c:if>


										<tr>
										<c:set var="isConcession"><bean:write name="feeAccount" property="isConcession"/></c:set>
										<c:if test="${isConcession}">
	                                    <td align="left">Concession(voucher No.&nbsp;&nbsp;&nbsp;<bean:write name="feePaymentForm" property="concessionReferenceNo"/> )
										</td>
										</c:if>
										<c:if test="${!isConcession}">
	                                    <td colspan="2">&nbsp;
										</td>
										</c:if>
	                                  </tr>
	                                  <tr >
										<c:set var="isInstallment"><bean:write name="feeAccount" property="isInstallment"/></c:set>
										<c:if test="${isInstallment == true}">
	                                    <td align="left">Installment(voucher No.&nbsp;&nbsp;&nbsp;<bean:write name="feePaymentForm" property="installmentReferenceNo"/> )
										</td>
										</c:if>
										<c:if test="${isInstallment == false}">
	                                    <td>
										</td>
										</c:if>
	                                  </tr>

										</table>
									</td>
									<td class="row-even" valign="top" height="160">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
										<td align="right">
										<div class="heading"><bean:write name="feeAccount" property="nonAdditionalAmount"/>&nbsp;&nbsp;</div>
										</td>
										</tr>
										<tr><td>&nbsp;</td>
										</tr>
										<c:if test="${addlAmt > 0}">
										<logic:notEmpty name = "feeAccount" property="additionalList">
											<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
											<tr> 
											<td align="right" class="heading">
												<div class="heading"><bean:write name="addl" property="amount"/>&nbsp;&nbsp;</div>
											</td>
											</tr>
											</logic:iterate>
										</logic:notEmpty>
										</c:if>
										<tr>
										<td align="right">
										<div class="heading"><bean:write name="feeAccount" property="concessionAmt"/>&nbsp;&nbsp;</div>
										</td>
										</tr>
										<tr>
										<td align="right">
										<div class="heading"><bean:write name="feeAccount" property="installmentAmt"/>&nbsp;&nbsp;</div>
										</td>
										</tr>
										</table>
									</td>
                                  </tr>
								<tr class="row-white">
								<td class="row-even" colspan="3" align="center"><div class="heading">TOTAL</div>
								</td>
								<td class="row-even" align="right"><div class="heading"><bean:write name="feeAccount" property="netAmount"/>&nbsp;&nbsp;</div>
								</td>
								</tr>
								</td>
								</table>

							
                            </tr>
                          
                              <tr class="row-white" >
							<td class="row-even" valign="top"  colspan="4">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td class="row-even" align="center">
							 		<bean:write  name="feeAccount" property="bankInfo"/>
								</td>
							</tr>
							<tr class="row-white" >
								 <td height="25" class="row-even" valign="bottom" align="center">Verified: <bean:write name="username"/>&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								
								<logic:notEmpty name = "feeAccount" property="verifiedList">
									<logic:iterate id="ver" name = "feeAccount" property="verifiedList" indexId="count">
									 <bean:write name="ver"/>
									<c:if test="${count == 0}">
 									&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
 									&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
									</c:if>
									</logic:iterate>
									</logic:notEmpty>
									</td>
							</tr>

							</table>
							</td>
                             </tr>
                        </table>							

							<tr>
							<td colspan="6">
							<hr width="100%">
							</td>
							</tr>	


                        
                        </td>
                      </tr>
                      <tr >
                        <td height="20" colspan="4" class="row-white" align="center">FOR BANK USE</td>
                      </tr>
                      <tr >
                        <td height="25" colspan="4" class="row-white" >Received Rs. : &nbsp;&nbsp;<b><bean:write name="feeAccount" property="netAmount"/></b>&nbsp;&nbsp; (IN &nbsp;<bean:write name="feePaymentForm" property="currencyCode"/>)</td>
                      </tr>
                      <tr >
                        <td height="25" colspan="4" class="row-white" >Rupees: &nbsp; &nbsp;<bean:write name = "feeAccount" property="amountInWord"/></td>
                      </tr>
                      <tr >
                        <td height="30" colspan="2" class="row-white" valign="bottom"><i>Signature of the Receiving authority</i></td>
                        <td height="30" colspan="2" class="row-white"  valign="bottom" ><div align="right"><i>Bank seal with date</i></div></td>
                      </tr>
                  </table></td>
                                
                  <td  background="images/right.gif" width="11" height="3"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
           </logic:iterate> 
			</logic:notEmpty>
            
          </tr>
          <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          </tr>

        </table>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
    </table></td>
  </tr>
</table>
<%if( i != 2){ %>
	<table class="break">
	</table>
<%} %>
<%} %>
</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>
