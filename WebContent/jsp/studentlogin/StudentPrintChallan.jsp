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
<html:form action="/StudentLoginAction">

<html:hidden property="method" styleId="method" value="initFeeChallanPrint"/>
<html:hidden property="formName" value="loginform"/>
<html:hidden property="pageType" value="3"/>

<table width="100%" border="0" >
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
			
			<logic:notEmpty name="loginform" property="printChalanList" >                
            <logic:iterate id="feeAccount" name="loginform" property="printChalanList" indexId="counter">
            <td width="50%" valign="top"><table width="98%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="images/st_01.gif" width="5" height="5"></td>
                  <td width="100%" background="images/st_02.gif"></td>
                  <td width="11" ><img src="images/st_03.gif" width="5" height="5"></td>
                </tr>

                <tr>
                  <td width="10" height="0"  background="images/st_left.gif"></td>
                  <td height="206" valign="top">
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
						<td colspan="4"><div align="center">
							<bean:define id="count" name="feeAccount" property="accId" type="java.lang.Integer"></bean:define>
							<img src='<%=request.getContextPath()%>/ChallanLogoServlet?accountId=<%=count%>' alt="Image not found"/>
							</div>
						</td>
                      </tr>

                      <tr >
						<td height="25" colspan="4" class="row-white" ><div align="center"><span class="heading"><bean:message key="knowledgepro.fee.feerecipt"/><i>&nbsp;
	                         - Student Copy
						</i></span></div></td>
                      </tr>
					<tr>
                        <td height="25" class="row-white" colspan="2" ><div align="left"><span class="heading"><bean:write name = "loginform" property="paymentMode"/> &nbsp;&nbsp;&nbsp;
							</span></div></td>
                        <td height="25" class="row-white" colspan="2"><div align="right"><span class="heading">
								&nbsp;
							</span></div></td>

					</tr>
                      <tr >
                        <td width="20%" height="25" class="studentrow-odd" ><bean:message key="knowledgepro.feepays.billno"/>:</td>
                        <td width="30%" height="25" class="studentrow-odd" ><bean:write name="loginform" property="billNo"/></td>
                        <td width="20%" class="studentrow-odd" >A/c. No.:</td>
                        <td width="30%" class="studentrow-odd" > <bean:write name="feeAccount" property="printAccountName"/></td>
                      </tr>
                      <tr >
                        <td width="20%" height="25" class="studentrow-even" >Date:</td>
                        <td width="30%" height="25" class="studentrow-even" ><bean:write name="loginform" property="challanPrintedDate"/> </td>
                        <td width="20%" class="studentrow-even" >Time:</td>
                        <td width="30%" class="studentrow-even" > <bean:write name="loginform" property="chalanCreatedTime"/> </td>
                      </tr>
                      <tr >
                        <td width="20%" height="25" class="studentrow-even" >Name:</td>
                        <td width="30%" height="25" class="studentrow-even" ><bean:write name="loginform" property="studentName"/></td>
                        <td width="20%" class="studentrow-even" >Reg No/ App No:</td>
                        <td width="30%" class="studentrow-even" > <bean:write name="loginform" property="applnNo"/> </td>
                      </tr>
                      <tr >
                        <td width="20%" height="25" class="studentrow-even" >Class:</td>
                        <td width="30%" height="25" class="studentrow-even" ><bean:write name="loginform" property="className"/> </td>
                        <td width="20%" class="studentrow-even" >Scheme No:</td>
                        <td width="30%" class="studentrow-even" > <bean:write name="loginform" property="semister"/> </td>
                      </tr>
                      <tr >
                        <td height="266" colspan="4" valign="top" class="row-white" >
                        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                              <td height="206" valign="top"><table width="100%" border="1" cellpadding="2" cellspacing="1">
                                  <tr class="row-white">
                                    <td height="25" colspan="2" rowspan="2" class="studentrow-odd" width="76%"><div align="center">DESCRIPTIONS</div></td>
                                    <td height="17" colspan="2" class="studentrow-odd" width="24%"><div align="center">Amount</div></td>
                                  </tr>
                                  <tr class="row-white">
                                    <td width="35%" height="14" class="studentrow-odd" colspan="2"><div align="center">Rs.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ps.</div></td>
                                  </tr>

                                  <tr class="row-white">
									<td class="studentrow-even" valign="top" height="150" width="38%">
										<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
											<logic:notEmpty  name = "feeAccount" property="descList">
											<logic:iterate id="desc" name = "feeAccount" property="descList">
											<tr>
											<td class="studentrow-even" >
												 <bean:write name="desc"/>
											</td>
											</tr>
											</logic:iterate>
											</logic:notEmpty>
										</table>
									</td>
									<td class="studentrow-even" valign="top" height="150" width="38%">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
											<logic:notEmpty name = "feeAccount" property="desc2List">
											<logic:iterate id="desc2" name = "feeAccount" property="desc2List">
											<tr>
											<td class="studentrow-even">
												 <bean:write name="desc2"/>
											</td>
											</tr>
											</logic:iterate>
										</logic:notEmpty>
										</table>
									</td>
									<td class="studentrow-even" valign = "middle" align="right" height="150" width="24%" colspan="2">
										<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr>
										<td align="right">
										<div class="heading"><bean:write name="feeAccount" property="nonAdditionalAmount"/>&nbsp;&nbsp;</div>
										</td>
										</tr>
										</table>
									</td>
                                  </tr>
								
							<c:set var="addlAmt"><bean:write name = "feeAccount" property="additionalAMount"/></c:set>		

							<tr class="row-white">
								<td class="studentrow-even" colspan="2" height="75" valign="top">
									<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td><div align="center" class="heading"> Additional Fees</div></td>
									</tr>
								<c:if test="${addlAmt > 0}">
                                  <tr>
									<td align="right" >
									<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
										<logic:notEmpty name = "feeAccount" property="additionalList">
											<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
											<tr> 
											<td class="studentrow-even" align="left">
												 <bean:write name="addl" property="feeGroupName"/>
											</td>
											</tr>
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
                                  </tr>
								</c:if>

                                  <tr>
									<c:set var="isConcession"><bean:write name="feeAccount" property="isConcession"/></c:set>
									<c:if test="${isConcession == true}">
                                    <td align="left">Concession(voucher No.&nbsp;&nbsp;&nbsp;<bean:write name="loginform" property="concessionReferenceNo"/> );
									</td>
									</c:if>
									<c:if test="${isConcession == false}">
                                    <td>
									</td>
									</c:if>
                                  </tr>
                                  <tr >
									<c:set var="isInstallment"><bean:write name="feeAccount" property="isInstallment"/></c:set>
									<c:if test="${isInstallment == true}">
                                    <td align="left">Installment(voucher No.&nbsp;&nbsp;&nbsp;<bean:write name="loginform" property="installmentReferenceNo"/> );
									</td>
									</c:if>
									<c:if test="${isInstallment == false}">
                                    <td>
									</td>
									</c:if>
                                  </tr>
                                  
									<c:set var="isScholarShipAmt"><bean:write name="feeAccount" property="isScholarShipAmt"/></c:set>
									<c:if test="${isScholarShipAmt == true}">
									<tr >
                                    <td align="left">Scholarship(voucher No.&nbsp;&nbsp;&nbsp;<bean:write name="loginform" property="scholarshipReferenceNo"/> );
									</td>
									</tr>
									</c:if>
                                  
								</table>
								</td>
					
							<c:set var="addlCount"><bean:write name = "feeAccount" property="addlCount"/></c:set>
							<td class="studentrow-even" height="75" valign="top">
									<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td>&nbsp;</div></td>
									</tr>
								<c:if test="${addlAmt > 0}">
                                  <tr>
									<td align="right" >
									<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
										<logic:notEmpty name = "feeAccount" property="additionalList">
											<logic:iterate id="addl" name = "feeAccount" property="additionalList" indexId="addcount">
											<tr> 
											<td>
												<div class="heading"><bean:write name="addl" property="amount"/>&nbsp;&nbsp;</div>
											</td>
											</tr>
											</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
                                  </tr>
								</c:if>

                                  <tr>
									<c:set var="isConcession"><bean:write name="feeAccount" property="isConcession"/></c:set>
									<c:if test="${isConcession == true}">
                                    <td align="right"><div class="heading"><bean:write name="feeAccount" property="concessionAmt"/>&nbsp;&nbsp; </div>
									</td>
									</c:if>
									<c:if test="${isConcession == false}">
                                    <td>
									</td>
									</c:if>
                                  </tr>
                                  <tr >
									<c:set var="isInstallment"><bean:write name="feeAccount" property="isInstallment"/></c:set>
									<c:if test="${isInstallment == true}">
                                    <td align="right"><div class="heading"><bean:write name="feeAccount" property="installmentAmt"/>&nbsp;&nbsp;</div>
									</td>
									</c:if>
									<c:if test="${isInstallment == false}">
                                    <td>
									</td>
									</c:if>
                                  </tr>
 									
									<c:if test="${isScholarShipAmt == true}">
									<c:set var="isScholarShipAmt"><bean:write name="feeAccount" property="isScholarShipAmt"/></c:set>
									<tr >
                                    <td align="right"><div class="heading"><bean:write name="feeAccount" property="scholarShipAmt"/>&nbsp;&nbsp;</div>
									</td>
									</tr>
									
									</c:if>
                                                                    
                                  
								</table>
								</td>	
	
								</tr>




                                   <tr >
                                    <td height="20" class="studentrow-even" align="right"  colspan="4"><div class="heading">
										TOTAL&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 <bean:write name="feeAccount" property="netAmount"/>&nbsp;&nbsp;</div>
                                    </td>
                                  </tr>
 								


                                  <tr class="row-white" >
											<td class="studentrow-even" valign="top" height="50" colspan="4">
											<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
			                                  <tr class="row-white" >
													<td class="studentrow-even"  colspan="3" align="right"><div class="heading">
														NET AMOUNT
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<bean:write name="feeAccount" property="netAmount"/> &nbsp;
													</div></td>
											</tr>
											<logic:notEmpty name = "feeAccount" property="bankInfoList">
											<logic:iterate id="bank" name = "feeAccount" property="bankInfoList" indexId="count2">
												<c:if test="${count2 != 0}">
													<tr>
												</c:if>
													<td class="studentrow-even">
												 		<bean:write name="bank"/>
													</td>
												<c:if test="${count2 != 0}">
													</tr>
												</c:if>
											</logic:iterate>
											</logic:notEmpty>
											</table>
											</td>
                                  	</tr>
								


								<tr class="row-white">
									<td class="studentrow-even" valign="top" height="50" colspan="4">
									<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
										<tr class="row-white">
	                                    <td class="studentrow-even" >Verified: <bean:write name="loginform" property="verifiedBy"/></td></tr>
										<tr>
	                                    <td class="studentrow-even">&nbsp;</td>
										</tr>
										<tr>
	                                    <td class="studentrow-even">&nbsp;</td>
										</tr>
	                                  </tr>
	                                  <tr class="row-white">
	                                    <td  class="studentrow-even">
											&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
										<logic:notEmpty name = "feeAccount" property="verifiedList">
											<logic:iterate id="ver" name = "feeAccount" property="verifiedList" indexId="count1">
											 <bean:write name="ver"/>
											<c:if test="${count1 == 0}">
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



								</td>
								</table>

							</td>
                             
                        </table>
                        
                        </td>
                      </tr>
                      <tr >
                        <td height="25" colspan="4" class="row-white" >Received: &nbsp;&nbsp;<b><bean:write name="feeAccount" property="netAmount"/></b> &nbsp;&nbsp; (IN &nbsp;<bean:write name="feeAccount" property="currencyCode"/>)</td>
                      </tr>
                      <tr >
                        <td height="25" colspan="4" class="row-white" >Amount in Words: &nbsp; &nbsp;<bean:write name = "feeAccount" property="amountInWord"/></td>
                      </tr>
					<c:choose>
					<c:when test="${counter == 1}">
                      <tr >
                        <td height="25" colspan="4" class="row-white" align="right"><bean:write name = "loginform" property="accwiseTotalPrintString"/></td>
                      </tr>
					</c:when>
					<c:otherwise>
                      <tr >
                        <td height="25" colspan="4" class="row-white" align="right">&nbsp;</td>
                      </tr>
					</c:otherwise>
					</c:choose>
                      <tr height="70">
                        <td class="row-white" >&nbsp;</td>
                      </tr>
						
                      <tr >
                        <td height="25" colspan="4" class="row-white" align="left">
							THIS IS A COMPUTER GENERATED STATEMENT AND DOES NOT REQUIRE A SIGNATURE
							<p style="font-size:8.5px">
							Disclaimer:<bean:write name="feeAccount" property="accountName"/> is not responsible for any inadvertent error that may have crept
							 in the above statements that is being written/published/presented above. 
							 Christ University reserves the right to correct the mistake, if any, at any point during the academic year.
							 Rules and regulations of the University are issued as per University orders from time to time.</p>
							</td>
                      </tr>
                     
                  </table></td>
                                
                  <td  background="images/st_right.gif" width="11" height="3"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/st_04.gif" width="5" height="5"></td>
                  <td background="images/st_05.gif"></td>
                  <td><img src="images/st_06.gif" ></td>
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
</html:form>
</body>
<script type="text/javascript">
	window.print();
</script>
