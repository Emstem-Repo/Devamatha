<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
var error = false;
function clearField(field){
	if(field.value == "0.0")
		field.value = "";
}
function checkForEmpty(field){
	if(field.value.length == 0)
		field.value="0.0";
	if(field.value == 0)
		field.value="0.0";
		
}
function cancelChallen() {
		document.location.href="FeePayment.do?method=initFeePaymentEditSearch";
}
function cancelChallenforPuc() {
	document.location.href="FeePayment.do?method=initFeePaymentEditSearch&PUC=true";
}

function calculateTotalConcession(field,id) {
	checkForEmpty(field);
	var count = document.getElementById("count1").value;
	
	if(isNaN(field.value)) {
		document.getElementById("err").innerHTML = "Please enter valid concession amount.";
		error = true;
		return;
	}
	
	/*for(var j=0 ; j<count ;j++) {
		var accTotalAmount = parseFloat(document.getElementById("accountTotal_"+j).value);
		var accConceAmount = parseFloat(document.getElementById("concession_"+j).value);
		if(accConceAmount > accTotalAmount) {
	    	document.getElementById("totalConcession").value = "0.0";
	    	document.getElementById("err").innerHTML = "please enter valid concession amount.";
	    	error = true;
			return;
		}	
	}*/

	error = false;
	document.getElementById("err").innerHTML = "";


	var concessionTotal = 0.0;
    for(var i=0 ; i<count ;i++) {
    	concessionTotal = concessionTotal + parseFloat(document.getElementById("concession_"+i).value);
    }            
    if(concessionTotal == 0)
    	document.getElementById("totalConcession").value = "0.0";
    else 
    	document.getElementById("totalConcession").value = concessionTotal;

}

function validateError() {
    var totalConcession = document.getElementById("totalConcession").value;
    var concesionNo = document.getElementById("concessionReferenceNo").value;
    var errorMessage = "";
    if(parseFloat(totalConcession) > 0 &&  concesionNo.length == 0)

    {
          errorMessage = errorMessage + "Concession reference number required.</br>";
    }
    if(error == true) {
          errorMessage = errorMessage + "Please enter valid concession amount.";
    }     
    document.getElementById("err").innerHTML = errorMessage;
    if(errorMessage.length != 0) {
          return false;
    }     
    return true;
}

function updateFee() {
    if(validateError()) {
		document.feePaymentForm.submit();
    }
}
</script>
<html:form action="/FeePayment">
<html:hidden property="method" styleId="method" value="feePaymentEditUpdate"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="4"/>
<nested:hidden property="feePaymentEditTO.feePaymentId"></nested:hidden>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feepayment"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feepaymentdetails"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
        		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="err" style="color:red;"></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  &nbsp;
					  </div>
               	    </td>
                   </tr>
                   <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="100%" width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
	                   	<table width="100%" cellspacing="1" cellpadding="2">
	                        <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.applicationno"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.applnNo"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.studentName"/></td>
	                        </tr>
	                         <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="curriculumSchemeForm.course"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.courseName"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.admittedthrough"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.admittedThrough"/></td>
	                         </tr>
	                         <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.className"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right">Fee Division</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.feeDivisionName"/></td>
	                         </tr>
	                         <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right">Bill No:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feePaymentEditTO.feeBillNo"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right">Challan Date:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><nested:text property="feePaymentEditTO.chalanDate" name="feePaymentForm" styleId="paidDate" size="16" maxlength="16"></nested:text>
	                             <script
								language="JavaScript">
								new tcal( {
									// form name
									'formname' :'feePaymentForm',
									// input name
									'controlname' :'feePaymentEditTO.chalanDate'
								});
								</script>
	                            </td>
	                         </tr>
	                    </table>
                      </td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                </table>
        </div>
        </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
        		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                   <tr>
                   		<td height="10" colspan="4" class="body" ></td>
                   </tr>
                   <tr>
                      <td><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="100%" width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
               			<table width="100%" cellspacing="1" cellpadding="2">
	                    	<tr>
	                            <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Payment Mode :</div></td>
	                            <td width="25%" height="25" class="row-even" align="left">
	                             <nested:select property="feePaymentEditTO.feePaymentModeId" styleId="paymentMode" styleClass="combo" >
                       				   <option value=""><bean:message key="knowledgepro.admin.select"/></option>
                       				   <nested:optionsCollection name="feePaymentForm" property="paymentModeMap" label="value" value="key"/>
                     			</nested:select>
	                            </td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left">
	                            <html:select property="academicYear" styleId="year" styleClass="combo">
                     	   			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                     	   			<cms:renderYear></cms:renderYear>
                     			</html:select>
	                            </td>
	                          </tr>
	                          <tr>
	                            <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Fee Paid:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left">
	                            <input type="hidden" id="feePaidValue" value='<bean:write name="feePaymentForm" property="feePaymentEditTO.feePaid"/>'/>
	                            <input type="checkbox" name="feePaid" id="feePaidId">
	                            <script type="text/javascript">
									var menuId = document.getElementById("feePaidValue").value;
									if(menuId == "true") {
										document.getElementById("feePaidId").checked = true;
									}else{
										document.getElementById("feePaidId").checked = false;
									}			
								</script>
	                            </td>
	                            <td width="25%" height="25" class="row-odd"><div align="right">Paid Date :</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><nested:text property="feePaymentEditTO.dateTime" name="feePaymentForm" styleId="paidDate" size="16" maxlength="16"></nested:text>
	                            <script
								language="JavaScript">
								new tcal( {
									// form name
									'formname' :'feePaymentForm',
									// input name
									'controlname' :'feePaymentEditTO.dateTime'
								});
								</script>
	                            </td>
	                          </tr>
	                    </table>
                      </td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                </table>
        </div>
        </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr> 
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
              <table width="100%" height="156"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 <tr>
                      <td><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="100%" width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                 </tr>
                 <c:set var="count" value="0"/>
	            <tr> 
	                   <td width="5"  background="images/left.gif"></td>
	                   <td valign="top">
	                   <table width="100%" cellspacing="1" cellpadding="2">
				            <tr>
				            	<!--  <td height="25" class="row-odd" width="10%"><div align="center"><bean:message key="knowledgepro.admission.semester"/></div></td>
			                     <td height="25" class="row-odd" width="20%"><div align="center"><bean:message key="knowledgepro.fee.feename"/></div></td> -->
			                    <td class="row-odd" width="20%">&nbsp;</td> 
								<c:set var="width" value="${50 / count}"/>
			               	    <nested:iterate id="feeAccountName" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList"> 
			                   	  <td height="25" class="row-odd" width="<c:out value='${width}'/>%"><div align="left"><bean:write name="feeAccountName" property="accountName"/></div></td>
			           		    </nested:iterate>
			           		</tr>
	             <tr>
                     <td height="1" class="row-odd"><div align="center">&nbsp;</div></td>
                      <c:set var="count1" value="0"/>
                 	 <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap"> 
                   	  <td height="1" class="row-odd"><div align="left">&nbsp;</div></td><c:set var="count1" value="${count1 + 1 }"/>
                	 </nested:iterate>  
                 </tr>
                  
                 <tr>
                 <input type="hidden" id="count1" name="count" value="<c:out value='${count1}'/>"/>
                       <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"><bean:message key="knowledgepro.feepays.totalamount"/>: </div></td>
                       <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap" indexId="c"> 
                     	  <td height="25" class="row-even"><div align="left">
                     	  <nested:iterate id="feeAmount" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList" indexId="c1">
                     	 	 <c:if test="${c == c1}">
                     	 	     <input type="text" value='<bean:write name="feeAmount" property="totalAmount"/>' name="feeAmount" disabled="disabled" class="TextBoxDecimal"/> &nbsp; <input type="text" value='<bean:write name="feeAmount" property="discountAmount"/>' name="feeAmount" readonly="readonly" class="TextBoxDecimal"/>
                     	 	 </c:if>
                     	  </nested:iterate>
                     	  </div></td>
                  		</nested:iterate>  
		                </tr>
		                <tr>
			                     	<bean:define id="puc" name="feePaymentForm" property="pucFeePayment" ></bean:define>
	                   		<td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"> <bean:message key="knowledgepro.fee.concession"/>:</div></td>
		                   	   <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap" indexId="c"> 
		                     	  <td height="25" class="row-even"><div align="left">
									<%if(puc.toString() == "true"){
													%>
			                     	  <nested:iterate id="feeAmount" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList" indexId="c1">
										 <bean:define id="conAmt" name="feeAmount" property="concessionAmount" ></bean:define>			                     	  
										 <%if(conAmt.toString()!= null && !conAmt.toString().isEmpty()) {%>			                     	  
				                     	 	 <c:if test="${c == c1}">
				                     	 		 <input type="text" id="concession_<c:out value='${c}'/>" name="feePaymentEditTO.feePaymentDetailEditList[<c:out value="${c}"/>].concessionAmount"  maxlength="10" class="TextBoxDecimal" value='<%=conAmt%>' 
				                     	 		 onblur="calculateTotalConcession(this,'<c:out value="${c}"/>')" onfocus="clearField(this)" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"/>
				                     	 	 </c:if>
										<%} else{ %>
				                     	 	 <c:if test="${c == c1}">
				                     	 		 <input type="text" id="concession_<c:out value='${c}'/>" name="feePaymentEditTO.feePaymentDetailEditList[<c:out value="${c}"/>].concessionAmount"  maxlength="10" class="TextBoxDecimal" value="0.00"
				                     	 		 onblur="calculateTotalConcession(this,'<c:out value="${c}"/>')" onfocus="clearField(this)" onkeypress="return isDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)"/>
				                     	 	 </c:if>
										<%} %>			                     	  
			                     	  </nested:iterate>
			                     	  	<%}
									else { %>
			                    	 	<input type="text" value='<bean:write name="feeAmount" property="concessionAmount"/>' name="feeAmount" disabled="disabled" class="TextBoxDecimal"/>
			                  	 	 <%} %>    
		                	  
		                     	  </div></td>
                  			  </nested:iterate>  
		                  </tr>
		                  <tr>
	                  		   <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"><bean:message key="knowledgepro.fee.installmentamount"/>: </div></td>
		                   	   <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap" indexId="c"> 
                     	  		<td height="25" class="row-even"><div align="left">
                     	  		<nested:iterate id="feeAmount" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList" indexId="c1">
                     	 	 	<c:if test="${c == c1}">
                     	 	     <input type="text" value='<bean:write name="feeAmount" property="installmentAmount"/>' name="feeAmount" disabled="disabled" class="TextBoxDecimal"/>
                     	 	 	</c:if>
                     	  		</nested:iterate>
                     	  		</div></td>
                  			</nested:iterate> 
		                  </tr>
		                  <tr>
	                  		   <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font">Scholarship Amount :</div></td>
		                   	   <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap" indexId="c"> 
                     	  		<td height="25" class="row-even"><div align="left">
                     	  		<nested:iterate id="feeAmount" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList" indexId="c1">
                     	 	 	<c:if test="${c == c1}">
                     	 	     <input type="text" value='<bean:write name="feeAmount" property="scholarshipAmount"/>' name="feeAmount" disabled="disabled" class="TextBoxDecimal"/>
                     	 	 	</c:if>
                     	  		</nested:iterate>
                     	  		</div></td>
                  			</nested:iterate> 
		                  </tr>
		                  <tr>
	                           <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"><bean:message key="knowledgepro.fee.excessshort"/>:</div></td>
    	                    	   <nested:iterate id="feeAccount" name="feePaymentForm" property="feePaymentEditTO.allFeeAccountMap" indexId="c"> 
		                     	  <td height="25" class="row-even" ><div align="left">
		                     	  <nested:iterate id="feeAmount" name="feePaymentForm" property="feePaymentEditTO.feePaymentDetailEditList" indexId="c1">
		                     	 	 <c:if test="${c == c1}">
		                     	 	 	<nested:hidden property="feePaymentDetailId"></nested:hidden>	
		                     	 		 <nested:text property="excessShortAmount" maxlength="10" styleClass="TextBoxDecimal" onfocus="clearField(this)" onkeypress="return isNegativeDecimalNumberKey(this.value,event)" onkeyup="onlyTwoFractions(this,event)" onblur="checkForEmpty(this)" />
		                     	 	 </c:if>
		                     	  </nested:iterate>
		                     	  </div></td>
	                   		   </nested:iterate>  
		                  </tr>
		                  </table>
	                  </td>
	                 <td width="5" align="right" background="images/right.gif"></td>
	             </tr>
                 <tr> 
	                   <td width="5"  background="images/left.gif"></td>
	                   <td valign="top"  >
	                   <table width="100%" cellspacing="1" cellpadding="2">
	                      <tr>
			                   <td height="5" class="row-even" align="left" colspan="<c:out value="${count + 2}"/>"><div align="left"></div></td>
		                  </tr>
		                  <tr>
		                  	   <td height="25" class="row-odd" width="30%" colspan="2"><div align="right" class="bold-font"><bean:message key="knowledgepro.fee.grandtotal"/>:</div></td>
		                       <td height="25" class="row-even" align="left" colspan="<c:out value="${count}"/>">
		                       <div align="left">
		                       	<bean:write name="feePaymentForm" property="feePaymentEditTO.totalAmount"/>&nbsp;<bean:write name="feePaymentForm" property="feePaymentEditTO.currencyCode" />
		                       </div></td>
		                  </tr>
		                   <tr>
		                   	   <td height="25" class="row-odd" width="30%">Voucher No:&nbsp;
		                   	   <html:text name="feePaymentForm" property="feePaymentEditTO.concessionVoucherNo" styleId="concessionReferenceNo" styleClass="TextBox" maxlength="20" onkeypress="return isAlphaNumberKey(event)"></html:text></td>
		                   	   <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"><bean:message key="knowledgepro.fee.concessiontotal"/>:</div></td>
		                       <td height="25" class="row-even" align="left" colspan="<c:out value="${count}"/>"><div align="left">
		                       <nested:text property="feePaymentEditTO.totalConcessionAmount" styleId="totalConcession" readonly="true" maxlength="10" styleClass="TextBoxDecimal"></nested:text>
		                       </div></td>
		                  </tr>
		                  <tr>
	                  		   <td height="25" class="row-odd" width="30%">Voucher No:&nbsp;<bean:write name="feePaymentForm" property="feePaymentEditTO.installmentVoucherNo"/></td>
	                  		   <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font"><bean:message key="knowledgepro.fee.installmenttotal"/>:</div></td>
		                       <td height="25" class="row-even" align="left" colspan="<c:out value="${count}"/>"><div align="left">
		                       <bean:write name="feePaymentForm" property="feePaymentEditTO.totalInstallmentAmount"/>
		                       </div></td>
			              </tr>
			              <tr>
			              	   <td height="25" class="row-odd" width="30%">Voucher No:&nbsp;<bean:write name="feePaymentForm" property="feePaymentEditTO.scholarshipVoucherNo"/></td>
	                  		   <td height="25" class="row-odd" width="30%"><div align="right" class="bold-font">Scholarhip Total:</div></td>
		                       <td height="25" class="row-even" align="left" colspan="<c:out value="${count}"/>"><div align="left">
		                       <bean:write name="feePaymentForm" property="feePaymentEditTO.totalScholarshipAmount"/>
		                       </div></td>
			              </tr>
	                   </table>
	                  </td>
	                 <td width="5" align="right" background="images/right.gif"></td>
	             </tr>
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                     <tr>
                       <td colspan="3" align="center"><div align="center">
                         <html:button property="" styleClass="formbutton" value="Save" onclick="updateFee()"></html:button>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         	<%if(puc.toString() == "true"){
													%>
                         <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelChallenforPuc()"></html:button>
                         <%}else{ %>
                          <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelChallen()"></html:button>
                          <%} %>
                       </div></td>                      
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>                     
                   </table>
                   </td>
                   <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
                 <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                </tr>
                <tr>
                      <td colspan="3"> &nbsp;</td>
                      
                </tr>
              </table>
            </div>
            </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr> 
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
	document.getElementById("year").disabled = true;
</script>