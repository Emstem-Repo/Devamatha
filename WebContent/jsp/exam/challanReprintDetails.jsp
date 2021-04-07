<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function cancelAction() {
		resetFieldAndErrMsgs();
	}
	function cancelPage() {
		document.location.href = "valuationChallan.do?method=initReprintChallan";
		
	}

	function checkSameSubject(ReviewerOrValuator,subjectId,boardmeetingCharge){

	       if((document.getElementById("Reviewer_"+subjectId+"_"+boardmeetingCharge))){
	        if(ReviewerOrValuator!="Reviewer"){
	            if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	             document.getElementById("Reviewer_"+subjectId+"_"+boardmeetingCharge).checked = true;
	            }else{
	            	document.getElementById("Reviewer_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                }
	        }
	       }
	       if((	document.getElementById("Valuator_"+subjectId+"_"+boardmeetingCharge))){
		         if(ReviewerOrValuator!="Valuator"){
		        	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
		                document.getElementById("Valuator_"+subjectId+"_"+boardmeetingCharge).checked = true;
		               }else{
		               	document.getElementById("Valuator_"+subjectId+"_"+boardmeetingCharge).checked = false;
		                   }
		        }
		       }
	       if((	document.getElementById("Valuator1_"+subjectId+"_"+boardmeetingCharge))){
	         if(ReviewerOrValuator!="Valuator1"){
	        	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	                document.getElementById("Valuator1_"+subjectId+"_"+boardmeetingCharge).checked = true;
	               }else{
	               	document.getElementById("Valuator1_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                   }
	        }
	       }
	       if((document.getElementById("Valuator2_"+subjectId+"_"+boardmeetingCharge))){
	         if(ReviewerOrValuator!="Valuator2"){
	        	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	                document.getElementById("Valuator2_"+subjectId+"_"+boardmeetingCharge).checked = true;
	               }else{
	               	document.getElementById("Valuator2_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                   }
	        }
	       }
	       if((document.getElementById("Project Major_"+subjectId+"_"+boardmeetingCharge))){
	         if(ReviewerOrValuator!="Project Major"){
	        	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	                document.getElementById("Project Major_"+subjectId+"_"+boardmeetingCharge).checked = true;
	               }else{
	               	document.getElementById("Project Major_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                   }
	        }
	       }
	       if((document.getElementById("Project Minor_"+subjectId+"_"+boardmeetingCharge))){
	         if(ReviewerOrValuator!="Project Minor"){
	        	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	                document.getElementById("Project Minor_"+subjectId+"_"+boardmeetingCharge).checked = true;
	               }else{
	               	document.getElementById("Project Minor_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                   }
	        }	
	       }
	       
	       if((document.getElementById("Re-Valuator_"+subjectId+"_"+boardmeetingCharge))){
	   		if(ReviewerOrValuator!="Re-Valuator"){
	   			
	            	if(document.getElementById(ReviewerOrValuator+"_"+subjectId+"_"+boardmeetingCharge).checked){
	                    document.getElementById("Re-Valuator_"+subjectId+"_"+boardmeetingCharge).checked = true;
	                	}
	                   else{
	                   	document.getElementById("Re-Valuator_"+subjectId+"_"+boardmeetingCharge).checked = false;
	                       }
	            
	   		}
	          }

	       var inputs = document.getElementsByTagName("input");
		    var inputObj;
		    var disable=false;
		    var checkBoxOthersSelectedCount = 0;
		    var checkBoxOthersCount = 0;
		    for(var count1 = 0;count1<inputs.length;count1++) {
		          inputObj = inputs[count1];
		          var type = inputObj.getAttribute("type");
		            if (type == 'checkbox' && inputObj.id != "checkAll") {
		                  checkBoxOthersCount++;
		                  if(inputObj.checked) {
		                        checkBoxOthersSelectedCount++;
		                        inputObj.value="on";
		                       
		                  }else{
		                	  inputObj.value="off";	
		                	 
		                      }   
		            }
		    }





	       
	       var checked = [];
	       $(":checkbox").each(function() {
	           if(this.checked){
	               checked.push(this.id);
	           }
	       });
	      var subjectAlreadyEnterd=Array();
	      var boardMeetingChargePerSubject=[];
	      for (var i = 0; i < checked.length; i++) {
	    	    var checkedSubject=checked[i].split("_");
	    	    if(subjectAlreadyEnterd.length!=0){
	    	    	if(!contains(subjectAlreadyEnterd,checkedSubject[1])){
	    	    	subjectAlreadyEnterd.push(checkedSubject[1]);
	    	    	boardMeetingChargePerSubject.push(checkedSubject[2]);
	    	    }
	    	    }else{
	    	    	subjectAlreadyEnterd.push(checkedSubject[1]);
	    	    	boardMeetingChargePerSubject.push(checkedSubject[2]);
	        	    }
			}
			var boardMeetingRate=0;
			var subjectCount=0;
			if(boardMeetingChargePerSubject.length!=0){
		      for (var k = 0; k < boardMeetingChargePerSubject.length; k++) {
		    	  boardMeetingRate += parseInt(boardMeetingChargePerSubject[k]);
		    	  subjectCount =subjectCount+1;
		      }
			}
			

			//function getMeetingAmount(meetingCharge){
				//var boardCharge = document.getElementById("boardMeeetingRate").value;
				//var totalAmount = boardCharge*meetingCharge;
				var totalAmount =boardMeetingRate;
				var htm = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Amount:</strong> &nbsp;&nbsp;"+totalAmount;
				document.getElementById("boardMeetingAmountId").innerHTML = htm;
				var grandTotal1 =  document.getElementById("grandTotal").value;
				var boardMeetingAmount = document.getElementById("totalBoardMeetingChargeId").value; 
				boardMeetingAmount = parseInt(boardMeetingAmount);
				totalAmount = parseInt(totalAmount);
				grandTotal1 = parseInt(grandTotal1);
				grandTotal1 = grandTotal1 - boardMeetingAmount;
				grandTotal1 = grandTotal1 + totalAmount;
				var htm1= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
				document.getElementById("grandTotal").value = grandTotal1;
				document.getElementById("totalBoardMeetingChargeId").value = totalAmount;
				document.getElementById("totalBoardMeetings").value=subjectCount;
				document.getElementById("totalAmountId").innerHTML = htm1;
			//}
			
			
		}

		function contains(arr, value) {
		    var i = arr.length;
		    while (i--) {
		        if (arr[i] === value) return true;
		    }
		    return false;
		}
		function validateDate(testdate) {
		    var date_regex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/ ;
		    if(!date_regex.test(testdate)){
		    	$.confirm({
					'message'	: '<b>Please Enter Valid Date.</b>',
					'buttons'	: {
						'Ok'	: {
							'class'	: 'blue',
							'action': function(){
								$.confirm.hide();
							}
						}
					}
				});
		    }
		}
		function checkNumeric(event) {
			return false;
			}
</SCRIPT>
<html:form action="/valuationChallan">
	<html:hidden property="formName" value="valuationChallanForm" styleId="formName" />
	<html:hidden property="method" styleId="method" value="saveAndRePrintChallan" />
	<html:hidden property="pageType" value="1" styleId="pageType" />
	<input type="hidden" id="typeExam" name="type" value='<bean:write name="valuationChallanForm" property="examType"/>' /> 
	<html:hidden property="boardMeeetingRate" styleId="boardMeeetingRate"/> 
	<html:hidden property="conveyanceCharge" styleId="conveyanceChargeRate"/> 
	<html:hidden property="grandTotal" styleId="grandTotal"/>  
	<html:hidden property="totalBoardMeetingCharge" styleId="totalBoardMeetingChargeId"/> 
	<html:hidden property="totalConveyanceCharge" styleId="totalConveyanceChargeId"/>
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam
			&gt;&gt; Exam Valuation &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">RePrint Chalan</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" class="news">

					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>

							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr height="50px">
						 			<td>Valuation Challan Details</td>
						 		</tr>
						 		
						 		<logic:notEmpty name="valuationChallanForm" property="map">
									 <tr>
										<td height="45" colspan="4" >
										<table width="100%" cellspacing="1" cellpadding="2">
							                  <tr>
							                  	<td height="25" class="row-odd" align="center"></td>
							                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
							                    <td height="25" class="row-odd" align="center">Subject</td>
							                    <td height="25" class="row-odd" align="center">Answer Scripts</td>
							                    <td height="25" class="row-odd" align="center">Total Amount</td>
							                    <td height="25" class="row-odd" align="center">Board meeting</td>
							                  </tr>
					                	<!--<logic:iterate id="to" name="valuationChallanForm" property="map">
							                  <tr>
							                    <td height="25" class="row-odd" align="left" colspan="5"><bean:write name="to" property="key"/></td>
							                  </tr>
							               	<logic:iterate id="to1" name="to" property="value" indexId="count">
							                   	<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
												</c:choose>
													<td width="10%" height="25" class="row-even" ></td>
							                   		<td width="10%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
							                   		<td width="40%" height="25" class="row-even" align="center"><bean:write name="to1" property="value.subjectName"/></td>
							                   		<td width="17%" height="25" class="row-even" align="center"><bean:write name="to1" property="value.answerScripts"/></td>
							                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="to1" property="value.totalAmount"/></td>
							               	</logic:iterate>
					                	</logic:iterate>
							                -->
							                <logic:iterate id="valuationDetailsList" name="valuationChallanForm" property="valuationDetailsList" indexId="count">
				                  <tr>
				                  <logic:equal value="0"  name="valuationDetailsList" property="valuatorsTypeCount" >
				                    <td height="25" class="row-odd" align="left" colspan="6"><bean:write name="valuationDetailsList" property="valuatorType"/></td>
				                    </logic:equal>
				                  </tr>
				                   	<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
									</c:choose>
										<td width="10%" height="25" class="row-even" ></td>
				                   		<td width="10%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
				                   		<td width="39%" height="25" class="row-even" align="left"><bean:write name="valuationDetailsList" property="subjectName"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="valuationDetailsList" property="answerScripts"/></td>
				                   		<td width="11%" height="25" class="row-even" align="center"><bean:write name="valuationDetailsList" property="totalAmount"/></td>
				                   		<td width="10%" height="25" class="row-even" align="center">
										<input type="hidden" name="valuationDetailsList[<c:out value='${count}'/>].isBoardMeetingApplicable"	id="hidden_<bean:write name="valuationDetailsList" property="valuatorType"/>_<bean:write name="valuationDetailsList" property="subjectId"/>_<bean:write name="valuationDetailsList" property="boardMeetingChargePerSub"/>"
																value="<bean:write name="valuationDetailsList"  property='isBoardMeetingApplicable'/>" />
													
														<input type="checkbox" name="valuationDetailsList[<c:out value='${count}'/>].checked" id="<bean:write name="valuationDetailsList" property="valuatorType"/>_<bean:write name="valuationDetailsList" property="subjectId"/>_<bean:write name="valuationDetailsList" property="boardMeetingChargePerSub"/>"  onclick="checkSameSubject('<bean:write name="valuationDetailsList" property="valuatorType"/>','<bean:write name="valuationDetailsList" property="subjectId"/>','<bean:write name="valuationDetailsList" property="boardMeetingChargePerSub"/>')" />
																<script type="text/javascript">
																var boardMeetingApplicable = document.getElementById("hidden_<bean:write name="valuationDetailsList" property="valuatorType"/>_<bean:write name="valuationDetailsList" property="subjectId"/>_<bean:write name="valuationDetailsList" property="boardMeetingChargePerSub"/>").value;
																if(boardMeetingApplicable == "on") {
																	document.getElementById("<bean:write name="valuationDetailsList" property="valuatorType"/>_<bean:write name="valuationDetailsList" property="subjectId"/>_<bean:write name="valuationDetailsList" property="boardMeetingChargePerSub"/>").checked = true;
																}		
																</script>
										</td>
										<c:set var="tempCount"  value="${tempCount + 1}"></c:set>
		                	</logic:iterate>
							                
							                
							                </table>
										</td>            
						    		</tr>
								</logic:notEmpty>
								
						
								<tr>
									<td height="10" colspan="4" >
										<table width="100%" cellspacing="1" cellpadding="3">
							                  	<tr>
													<td align="right" width="60%" class="row-odd" colspan="3">
													Total:
												</td>
												<td height="20" width="17%"  class="row-even" align="center">
													<bean:write name="valuationChallanForm" property="totalScripts"/>
												</td>
												<td height="20" width="20%" class="row-even" align="center">
													<bean:write name="valuationChallanForm" property="totalScriptsAmount"/>
												</td>
												</tr>
										</table>
									</td>
								</tr>
								 <tr>
									<td height="45" colspan="4" >
									<table width="100%" cellspacing="1" cellpadding="2">
						                  	<tr>
												<td width="35%"  align="right" class="row-odd">
												NO.Of Board Meetings:													
												</td>
												<td width="35%" class="row-even" align="left">
													<html:text property="totalBoardMeetings" styleId="totalBoardMeetings" onchange="validateDate(this.value)" onkeypress="return checkNumeric(event)"></html:text>	
												</td>
												<td class="row-even" align="left">
													<div id="boardMeetingAmountId"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Amount:</strong> &nbsp;&nbsp;<bean:write name="valuationChallanForm" property="totalBoardMeetingCharge"/></div>
												</td>
										   	</tr>
										   	<tr>
												<td width="35%"  align="right" class="row-odd">
												Conveyance Charge:													
												</td>
												<td width="35%" class="row-even" align="left" >
													<html:text property="totalNoOfConveyance" styleId="conveyanceChargeId" onkeypress="return isNumberKey(event)" onkeyup="getConveyanceAmount(this.value)"></html:text>	
												</td>
												<td class="row-even" align="left"> <div id="conveyanceChargeAmountId"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Amount:</strong> &nbsp;&nbsp;<bean:write name="valuationChallanForm" property="totalConveyanceCharge"/></div></td>
										   	</tr>
										   	<tr>
												<td width="35%"  align="right" class="row-odd">
												Dearness Allowance:													
												</td>
												<td class="row-even" align="left">
													<input type="hidden" name="tempDa" id="tempDaId"/>
													<html:text property="da" styleId="daId" onkeypress="return isNumberKey(event)" onkeyup="calculateTotalCharge1(this.value)"></html:text>												
												</td>
												<td class="row-even" align="left"> </td>
										   	</tr>
										   	<tr>
										   		<td width="35%"  align="right" class="row-odd">
												Travel Allowance:													
												</td>
												<td class="row-even" align="left">
													<input type="hidden" name="tempTa" id="tempTaId"/>
													<html:text property="ta" styleId="taId" onkeypress="return isNumberKey(event)" onkeyup="calculateTotalCharge2(this.value)"> </html:text>												
												</td>
												<td class="row-even" align="left"> </td>
										   	</tr>
										   	<tr>
												<td  align="right" class="row-odd">
												Any Other:													
												</td>
												<td class="row-even" align="left">
													<input type="hidden" name="tempOtherCost" id="tempOtherCostId"/>
													<html:text property="anyOther" styleId="anyOther" size="50"></html:text>	
													&nbsp;&nbsp;<html:text property="otherCost" styleId="otherCostId" onkeypress="return isNumberKey(event)" onkeyup="calculateTotalCharge3(this.value)"></html:text>												
												</td>
												<td class="row-even" align="left">
													 <div id="totalAmountId">
													 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Total Amount:</strong> &nbsp;&nbsp;<bean:write name="valuationChallanForm" property="grandTotal"/>
													 </div>
												 </td>
										   	</tr>
						                </table>
									</td>            
					    		</tr>
							</table>
							</td>

							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>

				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="20%" height="35" align="center">
							
							<html:submit value="Print" styleClass="formbutton"></html:submit>
										&nbsp;&nbsp;
							<input type="button" class="formbutton" value="Cancel" onclick="cancelPage()" />
							
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>

				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var print = "<c:out value='${valuationChallanForm.printPage}'/>";
if(print.length != 0 && print == "true"){
	var url = "valuationChallan.do?method=printChallanPrint";
	myRef = window
			.open(url, "Challan",
					"left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}
function getMeetingAmount(meetingCharge){
	var boardCharge = document.getElementById("boardMeeetingRate").value;
	var totalAmount = boardCharge*meetingCharge;
	var htm = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Amount:</strong> &nbsp;&nbsp;"+totalAmount;
	document.getElementById("boardMeetingAmountId").innerHTML = htm;
	var grandTotal1 =  document.getElementById("grandTotal").value;
	var boardMeetingAmount = document.getElementById("totalBoardMeetingChargeId").value; 
	boardMeetingAmount = parseInt(boardMeetingAmount);
	totalAmount = parseInt(totalAmount);
	grandTotal1 = parseInt(grandTotal1);
	grandTotal1 = grandTotal1 - boardMeetingAmount;
	grandTotal1 = grandTotal1 + totalAmount;
	var htm1= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
	document.getElementById("grandTotal").value = grandTotal1;
	document.getElementById("totalBoardMeetingChargeId").value = totalAmount;
	document.getElementById("totalAmountId").innerHTML = htm1;
}
function getConveyanceAmount(conveyanceCharge){
	var conveyanceChargeRate = document.getElementById("conveyanceChargeRate").value;
	var totalAmount = conveyanceCharge*conveyanceChargeRate;
	var htm = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Amount:</strong> &nbsp;&nbsp;"+totalAmount;
	document.getElementById("conveyanceChargeAmountId").innerHTML = htm;
	var grandTotal1 =  document.getElementById("grandTotal").value;  
	var totalConveyanceAmount = document.getElementById("totalConveyanceChargeId").value; 
	if(totalConveyanceAmount == null || totalConveyanceAmount == ""){
		totalConveyanceAmount=0;
	}
	totalConveyanceAmount = parseInt(totalConveyanceAmount);
	totalAmount = parseInt(totalAmount);
	grandTotal1 = parseInt(grandTotal1);
	grandTotal1 = grandTotal1 - totalConveyanceAmount;
	grandTotal1 = grandTotal1 + totalAmount;
	var htm1= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
	document.getElementById("grandTotal").value = grandTotal1;
	document.getElementById("totalConveyanceChargeId").value = totalAmount;
	document.getElementById("totalAmountId").innerHTML = htm1;
}
function calculateTotalCharge1(inputValue){
	if(inputValue == null || inputValue == ""){
		inputValue =0;
	}
	var grandTotal1 =  document.getElementById("grandTotal").value;
	var da =  document.getElementById("tempDaId").value;
	grandTotal1 = parseInt(grandTotal1);
	if(da == null || da == ""){
		da = 0;
	}
	da = parseInt(da);
	grandTotal1 = grandTotal1 - da;
	inputValue = parseInt(inputValue);
	grandTotal1 = grandTotal1 + inputValue;
	document.getElementById("grandTotal").value = grandTotal1;
	document.getElementById("tempDaId").value = inputValue;
	var htm2= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
	document.getElementById("totalAmountId").innerHTML = htm2;
}
function calculateTotalCharge2(inputValue){
	if(inputValue == null || inputValue == ""){
		inputValue =0;
	}
	var grandTotal1 =  document.getElementById("grandTotal").value;
	var ta =  document.getElementById("tempTaId").value;
	grandTotal1 = parseInt(grandTotal1);
	if(ta == null || ta == ""){
		ta = 0;
	}
	ta = parseInt(ta);
	grandTotal1 = grandTotal1 - ta;
	inputValue = parseInt(inputValue);
	grandTotal1 = grandTotal1 + inputValue;
	document.getElementById("grandTotal").value = grandTotal1;
	document.getElementById("tempTaId").value = inputValue;
	var htm2= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
	document.getElementById("totalAmountId").innerHTML = htm2;
}
function calculateTotalCharge3(inputValue){
	if(inputValue == null || inputValue == ""){
		inputValue =0;
	}
	var grandTotal1 =  document.getElementById("grandTotal").value;
	var other =  document.getElementById("tempOtherCostId").value;
	grandTotal1 = parseInt(grandTotal1);
	if(other == null || other == ""){
		other = 0;
	}
	other = parseInt(other);
	grandTotal1 = grandTotal1 - other;
	inputValue = parseInt(inputValue);
	grandTotal1 = grandTotal1 + inputValue;
	document.getElementById("grandTotal").value = grandTotal1;
	document.getElementById("tempOtherCostId").value = inputValue;
	var htm2= "<strong> Total Amount:</strong> &nbsp;&nbsp;&nbsp;"+grandTotal1;
	document.getElementById("totalAmountId").innerHTML = htm2;
}
document.getElementById("tempDaId").value = document.getElementById("daId").value;
document.getElementById("tempTaId").value = document.getElementById("taId").value;
document.getElementById("tempOtherCostId").value = document.getElementById("otherCostId").value;
</script>