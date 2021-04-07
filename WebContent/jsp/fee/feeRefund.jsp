<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="jquery.confirm/jquery.confirm/jquery.confirm.css" />
    <script src="jquery.confirm/jquery.confirm/jquery.confirm.js"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript"><!--
$(document).ready(function() {
	  $('#Search').click(function(){
		       var academicYear = $('#academicYear').val();
		       var challanNo = $('#challanNo').val();
	       if(academicYear== '' && challanNo==''){
	    	   $('#errorMessage').slideDown().html("<span>Academic Year is Required <br>Challan No. is Required.</span>");
	          return false;
	        }
	       else if(academicYear== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Academic Year.</span>");
	           return false;
	        }
	       else if(challanNo== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Enter Challan No.</span>");
	            return false;
	        }else{
	   		   document.feeRefundForm.submit();
	       }
	      });
	  $('#cancel').click(function(){
		  document.location.href = "feeRefund.do?method=initFeeRefund";
	  });
	  $('#Submit').click(function(){
			  var refundAmount = $('#refundAmount').val();
		      var refundMode = $('#refundMode').val();
		      var refundDate = $('#refundDate').val();
	      if(refundAmount== '' && refundMode=='' && refundDate==''){
	    	   $('#errorMessage').slideDown().html("<span>Refund Amount is Required <br>Refund Mode. is Required.<br>Refund Date is Required.</span>");
	          return false;
	      }else if(refundAmount== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Enter Refund Amount.</span>");
	           return false;
	      }else if(refundMode== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Select Refund Mode.</span>");
	            return false;
	      }else if(refundDate== ''){
	        	 $('#errorMessage').slideDown().html("<span>Please Enter Refund Date.</span>");
		            return false;
		  }else{
			  var studentId=$('input[name=selectedStudent]:radio:checked').val();
			  if(studentId==undefined){
				  studentId=$('#studentIdForDisplay').val();
			  }
			  var totalAmount=$('#temp_'+studentId).val().split('.');
			  if(parseInt(totalAmount[0])>=parseInt(refundAmount)){
				  document.getElementById("studentId").value=studentId;
				  document.getElementById("challanAmount").value=totalAmount[0];
				  if($('#challanDate').val()==''){
					  document.getElementById("challanDate").value = $('#challan_'+studentId).val();
				  }
				  document.feeRefundForm.submit();
			  }else{
				  $('#errorMessage').slideDown().html("<span><b>Refund Amount</b> Should not be more than <b>Total Amount</b>.</span>");
		            return false;
			  }
		  }
	  });
	  $('#reset').click(function(){
		  if($('#reset').val()==undefined){
		  var refundId=$('#refundId').val();
		  $.confirm({
				'message'	: '<b>Are you sure want to delete this record.<b>',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
					   	    document.location.href="feeRefund.do?method=deleteFeeRefundDetails&refundId="+refundId;
						}
					},
  	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		  }else{
			  document.getElementById("errorMessage").innerHTML = "";
			  document.getElementById("refundAmount").value = "";
			  document.getElementById("refundMode").value = "";
			  document.getElementById("refundDate").value = "";
		  }	
		  
	  });
	  $('#Delete').click(function(){
		  var refundId=$('#refundId').val();
		  $.confirm({
				'message'	: '<b>Are you sure want to delete this record.<b>',
				'buttons'	: {
					'Ok'	: {
						'class'	: 'blue',
						'action': function(){
							$.confirm.hide();
					   	    document.location.href="feeRefund.do?method=deleteFeeRefundDetails&refundId="+refundId;
						}
					},
  	       'Cancel'	:  {
						'class'	: 'gray',
						'action': function(){
							$.confirm.hide();
						}
					}
				}
			});
		  
	  });
	  
	});
var displayPurpose=null;
function showDetailsOfStudent(studentId) {
	var year=document.getElementById("academicYear").value;
	var challanNo=document.getElementById("challanNo").value;
	var challanDate=document.getElementById("challan_"+studentId).value;
	args =  "method=checkChallanNoAlreadyExist&challanDate="+challanDate+"&challanNo="+challanNo+"&academicYear="+year;
	var url = "feeRefund.do";
	requestOperationProgram(url, args, displayRefundDetails);
	if(displayPurpose!=null && displayPurpose!=""){
		document.getElementById(displayPurpose).style.display = "none";
	}
	 displayPurpose=studentId;
	 document.getElementById(studentId).style.display = "block";
	 document.getElementById("showDetailsToEnter").style.display = "block";
	 
}
function displayRefundDetails(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	var isMsg = false;
	if (value != null) {
		for ( var I = 0; I < value.length; I++) {
			if (value[I].firstChild != null) {
				document.getElementById("refundAmount").value = "";
				document.getElementById("refundMode").value = "";
				document.getElementById("refundDate").value = "";
				document.getElementById("refundId").value = "";
				document.getElementById("updateRefundMsg").innerHTML = "";
				document.getElementById("updateRefundMsg").style.display = "none";
				if($('#Delete').length) {
					document.getElementById("Delete").value = "Reset";
					$('#Delete').attr('id','reset');
				} 
				document.getElementById("Submit").value = "Submit";
				document.getElementById("method").value="submitStudentRefundAmount";
				isMsg = true;
			}
		}
	}
	if (isMsg != true) {
		var items = responseObj.getElementsByTagName("feeRefund");

		for ( var I = 0; I < items.length; I++) {
			if(items[I]!=null){
				var refundAmount = items[I].getElementsByTagName("refundAmount")[0].firstChild.nodeValue;
				var refundMode= items[I].getElementsByTagName("refundMode")[0].firstChild.nodeValue;
				var refundDate = items[I].getElementsByTagName("refundDate")[0].firstChild.nodeValue;
				var refundId = items[I].getElementsByTagName("refundId")[0].firstChild.nodeValue;
				//var message = items[I].getElementsByTagName("message")[0].firstChild.nodeValue;
				var refundAmountFor=refundAmount.split('.');
				document.getElementById("refundAmount").value = refundAmountFor[0];
				document.getElementById("refundMode").value = refundMode;
				document.getElementById("refundDate").value = refundDate;
				document.getElementById("refundId").value = refundId;
				//document.getElementById("updateRefundMsg").innerHTML = message;
				//document.getElementById("updateRefundMsg").style.display = "block";
				document.getElementById("Submit").value = "Update";
				document.getElementById("reset").value = "Delete";
			    $('#reset').attr('id','Delete');
				document.getElementById("method").value="updateStudentRefundAmount";
			}
		}
	}
}
</script>
<html:form action="/feeRefund" method="post">
<html:hidden property="formName" value="feeRefundForm" />
<html:hidden property="pageType" value="1"/>
<html:hidden property="studentIdForDisplay" name="feeRefundForm" styleId="studentIdForDisplay"/>
<html:hidden property="refundId" name="feeRefundForm" styleId="refundId"/>
<html:hidden property="challanAmount" name="feeRefundForm" styleId="challanAmount"/>
<html:hidden property="studentId" name="feeRefundForm" styleId="studentId"/>
<html:hidden property="challanDate" name="feeRefundForm" styleId="challanDate"/>
<c:choose>
		<c:when test="${operation == 'alreadyExistRecord'}">
			<html:hidden property="method" styleId="method" value="updateStudentRefundAmount" />
		</c:when>
		<c:when test="${operation == 'continueToSavingRecord'}">
			<html:hidden property="method" styleId="method" value="submitStudentRefundAmount" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="searchStudentByChallanNo" />
		</c:otherwise>
	</c:choose>
<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.fee" />
			 <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.fee.refund" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong class="boxheader"> <bean:message key="knowledgepro.fee.refund" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9" 	height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<!--<font color="red" size="2"><div id="updateRefundMsg"></div></font>-->
							<FONT color="green" size="2"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
							</td>
						</tr>
						<tr>
							<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
                                 
										<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.petticash.academicYear"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even" >
									           <input type="hidden" id="tempyear" name="appliedYear"
										     value="<bean:write name="feeRefundForm" property="academicYear"/>" />
									       <html:select property="academicYear" styleId="academicYear" styleClass="combo" name="feeRefundForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select></td>
									<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="admissionFormForm.challanNo.required" /></div>
											</td>
											<td width="25%" height="25" class="row-even">
											<html:text property="challanNo" name="feeRefundForm" styleId="challanNo"></html:text>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<logic:notEmpty property="studentList" name="feeRefundForm">
								<tr>
								<td width="5" background="images/left.gif"></td>
								<td width="100%">
								<table width="100%">
								<tr >
								<td width="50%" align="center" class="row-odd" height="25" colspan="2"><font size="2" style="font-weight: bolder;">There are more than one Challan Printed Date for this Challan No. Select correct one.</font></td>
								</tr>
								<tr>
								<%int count=0; %>
								<nested:iterate id="student" property="studentList" name="feeRefundForm">
									<%if(count==2){ %>
								     <tr class="row-even">
								     <%count=0; %>
								     <%} %>
									<%if(count==1){ %>
									<td width="25%" height="25" align="left" class="row-even">
									<%}else{ %>
									<td width="25%" height="25" align="right" class="row-even">
									<%} %>
								
								<input type="radio" name="selectedStudent" value="<nested:write name="student" property="studentId"/>" onclick="showDetailsOfStudent(this.value)">
								<input type="hidden" id="challan_<nested:write name="student" property="studentId"/>" name="challanPrint" value="<nested:write name="student" property="challanPrintedDate"/>"> 
								<font color="#054591" size="3" style="font-weight: bolder"><nested:write name="student" property="challanPrintedDate"/></font></td>
								<%count=count+1; %>
								</nested:iterate>
							  
								</tr>
								</table>
								</td>
								<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								</logic:notEmpty>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<logic:notEmpty property="refundMap" name="feeRefundForm">
						<logic:iterate id="map" property="refundMap" name="feeRefundForm">
						<tr>
							<td valign="top" class="news">
							<div id="<bean:write name='map' property='key'/>" style="display: none;">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right">
										           <bean:message key="knowledgepro.fee.studentname"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even" >
									         <bean:write name="map" property="value.studentName"/>
									      </td>
									<td width="25%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.exam.blockUnblock.class" /></div>
											</td>
											<td width="25%" height="25" class="row-even">
											 <bean:write name="map" property="value.className"/>
											</td>
										</tr>
										<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right">
										           <bean:message key="knowledgepro.exam.reJoin.registerNo"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even" >
									          <bean:write name="map" property="value.registerNo"/>
									      </td>
									<td width="25%" height="25" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.fee.refund.challan.amount" /></div>
											</td>
											<td width="25%" height="25" class="row-even">
											<input type="hidden" id="temp_<bean:write name='map' property='key'/>" name="amount" value="<bean:write name='map' property='value.challanAmount'/>">
										     <bean:write name="map" property="value.challanAmount"/>
											</td>
										</tr>
											<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right">
										           <bean:message key="knowledgepro.fee.refund.group.name"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even"  colspan="3">
									            <bean:write name="map" property="value.feeGroupName"/>
									      </td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						<tr height="20"></tr>
							<tr>
							<td valign="top" class="news">
							<div id="showDetailsToEnter" style="display: none;">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top" class="news">
									<table width="100%" cellspacing="1" cellpadding="2">
                                 
										<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.fee.refund.amount"/>:</div>
									            </td>
									         <td width="25%" height="25" class="row-even" >
									          <html:text property="refundAmount" name="feeRefundForm" styleId="refundAmount"></html:text>
									      </td>
									<td width="25%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.fee.refund.mode" /></div>
											</td>
											<td width="25%" height="25" class="row-even">
											 <html:select property="refundMode" styleId="refundMode" styleClass="combo" name="feeRefundForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:optionsCollection name="feeRefundForm" property="paymentModeMap" label="value" value="key" />
									</html:select>
											</td>
										</tr>
											<tr>
											 <td width="25%%" height="25" class="row-odd" >
									            <div align="right"><span class="Mandatory">*</span>
										           <bean:message key="knowledgepro.fee.refund.date"/>:</div>
									            </td>
											<td width="25%" height="25" class="row-even" colspan="3">
											<html:text property="refundDate" name="feeRefundForm" styleId="refundDate" size="10" maxlength="10" >
											</html:text>
											<script language="JavaScript">
										$(function(){
											 var pickerOpts = {
										 	            dateFormat:"dd/mm/yy"
										         };  
											  $.datepicker.setDefaults(
											    $.extend($.datepicker.regional[""])
											  );
											  $("#refundDate").datepicker(pickerOpts);
											});
                              </script>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						      <tr>
							<td height="25">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'alreadyExistRecord'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="Submit"></html:submit>
											<html:button property="" styleClass="formbutton"
												value="Delete" styleId="Delete" ></html:button>
										</c:when>
										<c:when test="${operation == 'continueToSavingRecord'}">
											<input type="submit" class="formbutton" id="Submit" value="Submit"/>
											 <input type="button" id="reset" value="Reset" class="formbutton"/>	
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Search"  styleId="Search"></html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%">
									<c:choose>
										<c:when test="${operation == 'alreadyExistRecord' || operation == 'continueToSavingRecord'}">
											<html:button property="" styleClass="formbutton"  styleId="cancel" value="Close"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"  onclick="resetFieldAndErrMsgs()" styleId="reset" value="Reset" >
							              </html:button>
										</c:otherwise>
										</c:choose>
										</td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
var student=document.getElementById("studentIdForDisplay").value;
if(student!=""){
	 document.getElementById(student).style.display = "block";
	 document.getElementById("showDetailsToEnter").style.display = "block";
}
var methodName=document.getElementById("method").value;
if(methodName=="submitStudentRefundAmount" || methodName=="updateStudentRefundAmount"){
	document.getElementById("academicYear").disabled = true;
	document.getElementById("challanNo").disabled = true;
}else{
	document.getElementById("academicYear").disabled = false;
	document.getElementById("challanNo").disabled = false;
}
var year=document.getElementById("tempyear").value;
if(year!=null && year!=""){
	document.getElementById("academicYear").value=year;
}
</script>