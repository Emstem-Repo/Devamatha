<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
var error = false;
function validateError() {

    var paymentMode = document.getElementById("paymentMode").value;
  //  var year = document.getElementById("year").value;
    var errorMessage = "";
    if(paymentMode.length == 0){
    	errorMessage = errorMessage +" Payment mode required.</br>";
    }
   // if(year.length == 0) {
    //	errorMessage = errorMessage +" Academic Year required.";
 //   }    
	document.getElementById("err").innerHTML = errorMessage;
	if(errorMessage.length != 0) {
		return false;
	}	
	return true;
}

function printChallen() {
	if(validateError()) {
		document.feePaymentForm.submit();
	}
}

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
	document.location.href="FeePayment.do?method=initFeePaymentSearch";
}

function calculateAmount(){
	var totalPaidAmt = 0;
	var totalBalance = 0;
	var totalConcession = 0;
	var grandTotalAmt = 0;
	var size = parseInt(document.getElementById("totFeeCount").value);
	var addlSize = parseInt(document.getElementById("totAddCount").value);
	for ( var count = 0; count <= size - 1; count++) {
		var curAmt = parseFloat(document
				.getElementById("feeDispTOList[" + count
						+ "].totalAmount").value);
		var curPaidAmt = parseFloat(document
				.getElementById("feeDispTOList[" + count
						+ "].paidAmount").value);
		var concAmt = parseFloat(document
				.getElementById("feeDispTOList[" + count
						+ "].concessionAmt").value);
		var balanceAmt1 = parseFloat(document.getElementById("feeDispTOList[" + count 
						+"].balanceAmt").value);
		if (isNaN(curPaidAmt) || (curPaidAmt == null)) {
			curPaidAmt = 0;
		}
		if (isNaN(concAmt) || (concAmt == null)) {
			concAmt = 0;
		}
		if((balanceAmt1 == null) || isNaN(balanceAmt1) || (balanceAmt1 == 0)){
			var balance = curAmt - curPaidAmt - concAmt;
		}else{
			var balance = balanceAmt1 - curPaidAmt - concAmt;
		}
		document.getElementById("feeDispTOList[" + count + "].balanceAmt").value  = balance;

		totalPaidAmt = totalPaidAmt + curPaidAmt;
		totalConcession = totalConcession + concAmt;
		totalBalance  = totalBalance + balance;
		grandTotalAmt = grandTotalAmt + curAmt;
	}

	for ( var count = 0; count <= addlSize - 1; count++) {
		var addlCurAmt = parseFloat(document
				.getElementById("addFeeDispTOList[" + count
						+ "].totalAmount").value);
		var addlCurPaidAmt = parseFloat(document
				.getElementById("addFeeDispTOList[" + count
						+ "].paidAmount").value);
		var addlConcAmt = parseFloat(document
				.getElementById("addFeeDispTOList[" + count
						+ "].concessionAmt").value);
		var addBalanceAmt1 = parseFloat(document.getElementById("feeDispTOList[" + count 
				+"].balanceAmt").value);
		if (isNaN(addlCurPaidAmt) || (addlCurPaidAmt == null)) {
			addlCurPaidAmt = 0;
		}
		if (isNaN(addlConcAmt) || (addlConcAmt == null)) {
			addlConcAmt = 0;
		}
		if((addBalanceAmt1 == null) || isNaN(addBalanceAmt1) || (addBalanceAmt1 == 0)){
			var balance = addlCurAmt - addlCurPaidAmt - concAmt;
		}else{
			var balance = addBalanceAmt1 - addlCurPaidAmt - concAmt;
		}
		var addlBalance = addBalanceAmt1 - addlCurPaidAmt - addlConcAmt;
		
		document.getElementById("addFeeDispTOList[" + count + "].balanceAmt").value  = addlBalance;

		totalPaidAmt = totalPaidAmt + addlCurPaidAmt;
		totalConcession = totalConcession + addlConcAmt;
		totalBalance  = totalBalance + addlBalance;
		grandTotalAmt = grandTotalAmt + addlCurAmt;
	}
	document.getElementById("grandTotal").value = grandTotalAmt;
	document.getElementById("totalPaidAmt").value = totalPaidAmt;
	document.getElementById("totalConcession").value = totalConcession;
	document.getElementById("totalBalance").value = totalBalance;	
	document.getElementById("netPayable").value = totalPaidAmt;	
		
	
	
}
function generateVoucherNo(){
	//getExamDateTime("examMap", examId, "examNameId", update);
	finYear = document.getElementById("finYear").value;
	
	//alert("adfasd");
	var args = "method=getNextVoucherNo&year=" + finYear;
	var url = "AjaxRequest.do";
	requestOperation(url, args, update);
}
function update(req)
{
	updateVocherNo(req, "date");
}

</script>

<html:form action="/FeePayment">

<html:hidden property="method" styleId="method" value="initPrintChallen"/>
<html:hidden property="formName" value="feePaymentForm"/>
<html:hidden property="pageType" value="3"/>
<input type="hidden" name="registrationNo" value='<bean:write	name="feePaymentForm"	property="registrationNo" />' />
<input type="hidden" name="rollNumber" value='<bean:write name="feePaymentForm"	property="rollNumber" />' />
<input type="hidden" name="studentId" value='<bean:write name="feePaymentForm" property="studentId" />' />
<input type="hidden" name="feeDivisionId" value='<bean:write name="feePaymentForm" property="feeDivisionId" />' />
<input type="hidden" name="currencyId" value='<bean:write name="feePaymentForm" property="currencyId" />' />

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
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
	                   	<table width="100%" cellspacing="1" cellpadding="2">
	                        <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.applicationno"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="applicationId"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.fee.studentname"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="studentName"/></td>
	                        </tr>
	                         <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="curriculumSchemeForm.course"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="courseName"/></td>
	                         	<td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.feeCategory"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="feeCategoryName"/></td>
	                         </tr>
	                         <tr>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.attendance.activityattendence.class"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><bean:write name="feePaymentForm" property="className"/></td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>Date & Time:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left"><nested:text name="feePaymentForm" property="dateTime" styleId="dateTime"/>
	                            <script language="JavaScript">
										new tcal( {
											// form name
											'formname' :'feePaymentForm',
											// input name
											'controlname' :'dateTime'
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
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
               			<table width="100%" cellspacing="1" cellpadding="2">
	                    	<tr>
	                            <td width="25%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>Payment Mode :</div></td>
	                            <td width="25%" height="25" class="row-even" align="left">
	                             <html:select property="paymentMode" styleId="paymentMode" styleClass="combo" >
                       				   <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                       				   <html:optionsCollection name="feePaymentForm" property="paymentModeMap" label="value" value="key"/>
                     					</html:select>
	                            </td>
	                            <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
	                            <td width="25%" height="25" class="row-even" align="left">
	               <!--              <html:select property="academicYear" styleId="year" styleClass="combo">
                     	   					 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                     	   					 <cms:renderYear></cms:renderYear> 
                     			   		</html:select>  -->
                     			   		<bean:define id="year1" property="academicYear" name="feePaymentForm" type="java.lang.String"></bean:define>
										<%Integer year=Integer.parseInt(year1); %>
										<div align="center"><bean:write name="feePaymentForm"
											property="academicYear" />-<%=year+1%></div>
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
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                 </tr>
                 <c:set var="count" value="0"/>
	              <tr> 
	                   <td width="5"  background="images/left.gif"></td>
	                   <td valign="top"  >
	                   <input type="hidden" id="count" name="count" value="<c:out value='${count}'/>"/>              
	                   <table width="100%" cellspacing="1" cellpadding="2">
	                      <c:set var="dataId" value="0"/>
	                      <c:set var="globalcount" value="0"/>
	                      <c:set var="feeAccountCount" value="0"/>
	                      <c:set var="printedFeeAccountHeadings" value="0"/>
	                      <tr>
	                      		<td class="row-odd">
		                      		<table>
			                      		<tr>
				                      		<td height="25" class="row-odd" width="10%"><div align="center">Scheme No</div></td>
				                      	</tr>
			                      	</table>
		                      	</td>
	                     	  <td height="25" class="row-odd" width="18%"><div align="center">Fees Name</div></td>
	                     	   <td height="25" class="row-odd" width="10%"><div align="center">Total Amount</div></td>
	                     	   <td height="25" class="row-odd" width="10%"><div align="center">Paid Amount</div></td>
	                     	   <td height="25" class="row-odd" width="10%"><div align="center">Concession Amount</div></td>
	                     	   <td height="25" class="row-odd" width="10%"><div align="center">Balance Amount</div></td>
	                     	   <td height="25" class="row-odd" width="10%"><div align="center">Remarks</div></td>
	                      </tr>
	                     
	                     <%int totFeeCount = 0; %>
	 					  <nested:iterate id="feeDisp" name="feePaymentForm" property="feePaymentDisplayTOList" indexId="feeCount">
	 					  	<nested:iterate property="feeDispTOList" indexId="count">
	 					  		<tr>
	 					  		<c:if test="${count == 0}">
		 					  		<td class="row-even">
			 					  		<table class="row-even">
				 					  		<tr>
	        	    		              		<td height="25" class="row-even" width="10%"><div align="center"><bean:write name="feeDisp" property="semester"/></div></td>
			                          		</tr>
	            	              		</table>
	                          		</td>
                   		   		</c:if>
                   		   		<c:if test="${count >0}">
		 					  		<td class="row-even">
			 					  		<table class="row-even">
				 					  		<tr>
	        	    		              		<td height="25" class="row-even" width="10%"><div align="center">&nbsp;</div></td>
			                          		</tr>
	            	              		</table>
	                          		</td>
                   		   		</c:if>
                          		
                          		<td height="25" class="row-even" width="20%"><div align="left"><bean:write name="feeDispTOList" property="feeHeadName"/></div></td>
                          		<td height="25" class="row-even" width="10%"><div align="center">
                          		<% String idTot = "feeDispTOList[" + totFeeCount + "].totalAmount"; %>
                          		<nested:text property="totalAmount" styleClass="TextBoxDecimal" maxlength="20" size="20" styleId="<%=idTot%>" 
                          		readonly="true"></nested:text></div></td>
                          		<td height="25" class="row-even" width="10%">
                          		<% String id = "feeDispTOList[" + totFeeCount + "].paidAmount"; %>
                          		<div align="center"><nested:text property="paidAmount" styleClass="TextBoxDecimal" styleId="<%=id%>"
                          		 maxlength="20" size="20" onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)"
                          		 onblur="calculateAmount()"></nested:text></div></td>
									                          		
								<% String idConc = "feeDispTOList[" + totFeeCount + "].concessionAmt"; %>
                          		<td height="25" class="row-even" width="10%"><div align="center">
                          		<nested:text  property="concessionAmt" styleClass="TextBoxDecimal" maxlength="20" styleId="<%=idConc%>"
                          		onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)"
                          		onblur="calculateAmount()"></nested:text>
                          		</div></td>
								<% String idBal = "feeDispTOList[" +  totFeeCount + "].balanceAmt"; %>
                          		<td height="25" class="row-even" width="10%"><div align="center">
                          		<nested:text property="balanceAmt" styleClass="TextBoxDecimal" maxlength="20" styleId="<%=idBal%>"
                          		onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)" readonly="true"></nested:text>
                          		</div></td>
                          		<% String idRemarks = "feeDispTOList[" +  totFeeCount + "].remarks"; %>
                          		<td height="25" class="row-even" width="10%"><div align="center">
                          		<nested:textarea property="remarks" styleClass="TextBoxDecimal"  styleId="<%=idRemarks%>"></nested:textarea>
                          		</div></td>
                          		
                          		</tr>
                          		<% totFeeCount = totFeeCount + 1; %>
	 					 	</nested:iterate> 	
	 					 	
	 					 	<c:set var="totSemCount" value="${totSemCount + 1}"></c:set>
	 					 </nested:iterate>
	 					 <tr>
	 					 
					 <%int totAddCount = 0; %>
	 					 <nested:notEmpty name="feePaymentForm" property="feePaymentAdditionalList">
		 					 <nested:iterate id="feeDisp" name="feePaymentForm" property="feePaymentAdditionalList" indexId="addCount">
		 					  	<nested:iterate property="feeDispTOList" indexId="count">
		 					  		<tr>
		 					  		<c:if test="${count == 0}">
			 					  		<td class="row-even">
				 					  		<table class="row-even">
					 					  		<tr>
		        	    		              		<td height="25" class="row-even" width="10%"><div align="center">Additional Fee</div></td>
				                          		</tr>
		            	              		</table>
		                          		</td>
	                   		   		</c:if>
	                   		   		<c:if test="${count >0}">
			 					  		<td class="row-even">
				 					  		<table class="row-even">
					 					  		<tr>
		        	    		              		<td height="25" class="row-even" width="10%"><div align="center">&nbsp;</div></td>
				                          		</tr>
		            	              		</table>
		                          		</td>
	                   		   		</c:if>
	                          		
	                          		<td height="25" class="row-even" width="20%"><div align="left"><bean:write name="feeDispTOList" property="feeHeadName"/></div></td>
	                          		<td height="25" class="row-even" width="10%"><div align="center">
	                          		<% String idTot = "addFeeDispTOList[" + totAddCount + "].totalAmount"; %>
	                          		<nested:text property="totalAmount" styleClass="TextBoxDecimal" maxlength="20" size="20" styleId="<%=idTot%>" 
	                          		readonly="true"></nested:text></div></td>
	                          		<td height="25" class="row-even" width="10%">
	                          		<% String id = "addFeeDispTOList[" + totAddCount + "].paidAmount"; %>
	                          		<div align="center"><nested:text property="paidAmount" styleClass="TextBoxDecimal" styleId="<%=id%>"
	                          		 maxlength="20" size="20" onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)"
	                          		 onblur="calculateAmount()"></nested:text></div></td>
										                          		
									<% String idConc = "addFeeDispTOList[" + totAddCount + "].concessionAmt"; %>
	                          		<td height="25" class="row-even" width="10%"><div align="center">
	                          		<nested:text property="concessionAmt" styleClass="TextBoxDecimal" maxlength="20" styleId="<%=idConc%>"
	                          		onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)"
	                          		onblur="calculateAmount()"></nested:text>
	                          		</div></td>
									<% String idBal = "addFeeDispTOList[" +  totAddCount + "].balanceAmt"; %>
	                          		<td height="25" class="row-even" width="10%"><div align="center">
	                          		<nested:text  property="balanceAmt" styleClass="TextBoxDecimal" maxlength="20" styleId="<%=idBal%>"
	                          		onkeypress="return isDecimalNumberKey(this.value,event)" onfocus="clearField(this)" onkeyup="onlyTwoFractions(this,event)" readonly="true"></nested:text>
	                          		</div></td>
	                          		
	                          		
	                          		</tr>
	                          		<% totAddCount = totAddCount + 1; %>
		 					 	</nested:iterate> 	
		 					 	
		 					 </nested:iterate>
	 					 </nested:notEmpty>
	 					 
	 					   
	 					 
	 					 
	 					 
	 					 
	 					 
	 					 <td>
	 					 <input
								type="hidden" name="totFeeCount" id="totFeeCount"
								value='<%=totFeeCount%>' />
								 <input
								type="hidden" name="totAddCount" id="totAddCount"
								value='<%=totAddCount%>' />
							</td> 
	 					 
	 					</tr>
	 					<tr>
                      		<td class="row-odd">
	                      		<table>
		                      		<tr>
			                      		<td height="25" class="row-odd" width="10%"><div align="center">&nbsp;</div></td>
			                      	</tr>
		                      	</table>
	                      	</td>
	                     	 <td height="25" class="row-odd" width="18%"><div align="center">&nbsp;</div></td>
	                     	 <td height="25" class="row-odd" width="20%"><div align="center"><html:text name="feePaymentForm" property="grandTotal" styleId="grandTotal" styleClass="TextBoxDecimal" size = "50" readonly="true"> </html:text></div></td>
	                     	 <td height="25" class="row-odd" width="20%"><div align="center"><html:text name="feePaymentForm" property="totalPaidAmt" styleId="totalPaidAmt" styleClass="TextBoxDecimal" size = "50" readonly="true"> </html:text></div></td>
	                     	 <td height="25" class="row-odd" width="20%"><div align="center"><html:text name="feePaymentForm" property="totalConcession" styleId="totalConcession" styleClass="TextBoxDecimal" size = "50" readonly="true"> </html:text></div></td>
	                     	 <td height="25" class="row-odd" width="20%"><div align="center"><html:text name="feePaymentForm" property="totalBalance" styleId="totalBalance" styleClass="TextBoxDecimal" size = "50" readonly="true"> </html:text></div></td>
	                      </tr>
	                      <tr><td style="height: 10px" align="center" colspan="2"></td></tr>
	                      <tr>	                      
	                      <bean:define id="fin" name = "feePaymentForm" property="financialYearId"></bean:define>	                     
		                    <td height="25" class="row-odd" width="20%" colspan="2"><div align="right">Concession Voucher Number:</div></td>
							<td height="25" class="row-even" width="20%" colspan="5"><div align="left">
							 <input	type="hidden" name="finYear" id="finYear"	value="<c:out value="${fin}"/>" />
							<html:text name="feePaymentForm" property="consAplha" styleId="consAplha" size = "2" maxlength="2" readonly="true"> </html:text> <html:text name="feePaymentForm" property="concessionReferenceNo" styleId="concessionReferenceNo" size = "10" > </html:text>
							<html:button property="" styleClass="formbutton" value="Generate" onclick="generateVoucherNo()">
							</html:button></div></td>
	                      </tr>	                      
	                      <tr><td style="height: 10px" align="center" colspan="2"></td></tr>	                      
	                      <tr>
		                    <td height="25" class="row-odd" width="20%" colspan="2"><div align="right">Net Payable:</div></td>
							<td height="25" class="row-even" width="20%" colspan="5"><div align="left"><nested:text name="feePaymentForm" property="netPayable" styleId="netPayable" styleClass="TextBoxDecimal" size = "20"></nested:text></div></td>		                      
	                      </tr>
		                
                         <!-- additional fee nothing but semester 0 -->
						
		                
		                
		                 
		                 
	                   </table>
	                  </td>
	                 <td width="5" align="right" background="images/right.gif"></td>
	                 </tr>
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td colspan="3" align="center"><div align="center">
                         <html:button property="" styleClass="formbutton" value="Print Challan" onclick="printChallen()"></html:button>
                         &nbsp;&nbsp;&nbsp;&nbsp;
                         <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelChallen()"></html:button>
                       </div></td>
                      
                     </tr>
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