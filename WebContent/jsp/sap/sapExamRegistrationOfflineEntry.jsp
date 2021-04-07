<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
	<script type="text/javascript">
	function checkConscession(){
		if(document.getElementById("isCons").checked){
			document.getElementById("display").style.display="block";
		}else if(document.getElementById("isCons1").checked){
			document.getElementById("conAmount").value='';
			document.getElementById("conDetails").value='';
			document.getElementById("netAmount").value='';
			document.getElementById("tempNetAmount").value='';
			document.getElementById("display").style.display="none";
		}
	}
	function submitDetails(){
			var regNo = document.getElementById("regNo").value;
			var msg="";
			if(document.getElementById("isCons").checked){
						if(regNo == ''){
							msg=msg+"<br/>"+"Register Number is required";
						}
						var conscessionAmount =  document.getElementById("conAmount").value;
						var conscessionDetails =  document.getElementById("conDetails").value;
						if(conscessionAmount == ''){
							msg=msg+"<br/>"+"Please enter Conscession Amount";
						} 
						if(conscessionDetails == ''){
							msg=msg+"<br/>"+"Please enter Conscession Details";
						}
			}else if(document.getElementById("isCons1").checked){ 
				if(regNo == ''){
					msg=msg+"<br/>"+"Register Number is required";
				}
			}
			if(msg !=''){
				document.getElementById("errorMessages").style.display="block";
				document.getElementById("err").innerHTML=msg;
				
			}else{
				var netAmt= document.getElementById("netAmount").value;
				var a = document.getElementById("tempNetAmount").value=netAmt;
				document.getElementById("method").value="saveOfflineSAPExamRegistrationDetails";
				document.examRegDetailsForm.submit();
			}
	}
	function submitDetailsForPrint(){
		var regNo = document.getElementById("regNo").value;
		var msg="";
		if(document.getElementById("isCons").checked){
					if(regNo == ''){
						msg=msg+"<br/>"+"Register Number is required";
					}
					var conscessionAmount =  document.getElementById("conAmount").value;
					var conscessionDetails =  document.getElementById("conDetails").value;
					if(conscessionAmount == ''){
						msg=msg+"<br/>"+"Please enter Conscession Amount";
					} 
					if(conscessionDetails == ''){
						msg=msg+"<br/>"+"Please enter Conscession Details";
					}
		}else if(document.getElementById("isCons1").checked){ 
			if(regNo == ''){
				msg=msg+"<br/>"+"Register Number is required";
			}
		}
		if(msg !=''){
			document.getElementById("errorMessages").style.display="block";
			document.getElementById("err").innerHTML=msg;
			
		}else{
			var netAmt= document.getElementById("netAmount").value;
			 document.getElementById("tempNetAmount").value=netAmt;
			 document.getElementById("isPrint").value="print";
			document.getElementById("method").value="saveOfflineSAPExamRegistrationDetails";
			document.examRegDetailsForm.submit();
		}
	}
	function calculateConcessionAmount(concesstionAmount){
			var feeAmount = parseFloat(document.getElementById("feeAmount").value);
			if(concesstionAmount < feeAmount){
				var netAmount = feeAmount - concesstionAmount;
				document.getElementById("netAmount").value=netAmount;
			}else{
				var conAmount =parseFloat(concesstionAmount.substr(0,(parseFloat(concesstionAmount.length) -1)));
				document.getElementById("conAmount").value=parseFloat(conAmount);
				alert("Concession Amount should not exceeded than Fee Amount");
			}
	}	
	function reset1(){
		document.getElementById("errorMessages").style.display="none";
	}
	function cancel(){
		document.location.href="examRegistrationDetails.do?method=initOfflineSAPExamRegistration";
		checkConscession();
	}
	function len_display(Object,MaxLen,element){
	    var len_remain = MaxLen+Object.value.length;
	   if(len_remain <=100){
	    document.getElementById(element).value=len_remain; }
	}
	function imposeMaxLength1(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
</script>
<html:form action="/examRegistrationDetails" method="post">
	<html:hidden property="method" styleId="method" value=" " />
	<html:hidden property="formName" value="examRegDetailsForm" />
	<html:hidden property="feeAmount" name="examRegDetailsForm" styleId="feeAmount" />
	<html:hidden property="tempNetAmount" name="examRegDetailsForm" styleId="tempNetAmount" />
	<html:hidden property="isPrint" name="examRegDetailsForm" styleId="isPrint" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">SAP Exam <span class="Bredcrumbs">&gt;&gt;
			Offline SAP Exam Registration&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
							Offline SAP Exam Registration
						</td>
						<td width="10">
							<img src="images/Tright_1_01.gif" width="9" height="29">
						</td>
					</tr>				
					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
						<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="err"><html:errors/></span>
							</p>
							</div>
							</div>

							<div id="messages"><div class="display-info">
							<span id="msg"><html:messages id="message" property="messages" message="true"><c:out value="${message}" escapeXml="false"></c:out><br></html:messages></span>
							</div>
							</div>
							<script type="text/javascript">
								if(document.getElementById("msg")==null ||  document.getElementById("msg").innerHTML==''){
									document.getElementById("messages").style.display="none";
									}
								if(document.getElementById("err").innerHTML==''){
									document.getElementById("errorMessages").style.display="none";
									}
							</script>
							
							</td>
						</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>					
					
					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">							
												<tr>
												<td  width="40" height="25" class="row-odd"><div align="right">
													<span class="Mandatory">*</span>Register No:</div></td>
												<td  width="60%" class="row-even">
													<html:text property="regNo" name="examRegDetailsForm" styleId="regNo"></html:text>
												</td>
												</tr>
												<tr>
													<td width="40%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 215px; height: 25">
													Campus :</div>
													</td>
													<td width="60%" height="25" class="row-even" align="left">
													<bean:write name="examRegDetailsForm" property="workLocationName"/>
													</td>
													
												</tr>
												<tr>
													<td width="40%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Venue :</div>
													</td>
													<td width="60%" height="25" class="row-even" align="left">
													<bean:write name="examRegDetailsForm" property="venueName"/>
													</td>
													
												</tr>
												<tr>
													<td width="40%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Date & Time :</div>
													</td>
													<td width="60%" height="25" class="row-even" align="left">
													<bean:write name="examRegDetailsForm" property="examDate"/>&nbsp;&nbsp;(
													<bean:write name="examRegDetailsForm" property="sessionName"/>)
													</td>
												</tr>
												<tr>
													<td width="40%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Fee Amount : </div>
													</td>
													<td width="60%" height="25" class="row-even" align="left">
													<bean:write name="examRegDetailsForm" property="feeAmount"/>
													</td>
												</tr>
												<tr>
												<td  width="40%" height="25" class="row-odd" align="right">
												Concession :
												</td>
												<td width="60%" height="25" class="row-even" align="left">
												<html:radio property="isConcessionReg" name="examRegDetailsForm" value="true" styleId="isCons" onclick="checkConscession()">Yes</html:radio>
					 							 <html:radio property="isConcessionReg"  name="examRegDetailsForm" value="false" styleId="isCons1" onclick="checkConscession()">No</html:radio>
											</td>
												</tr>
											<tr>
												<td colspan="4">
													<div id="display">
														<table width="100%">
															<tr>
															  	<td  width="40%" height="25" class="row-odd" align="right">
															  		<span class="Mandatory">*</span>Concession Amount :
															  	</td>
															  	<td width="60%" height="25" class="row-even" align="left">
																	<html:text property="concessionAmount" name="examRegDetailsForm" styleId="conAmount" onkeyup="calculateConcessionAmount(this.value)"
																	onkeypress="return isNumberKey(event)" ></html:text>
																</td>
															</tr>
															<tr>
															  	<td  width="40%" height="25" class="row-odd" align="right" >
															  		<span class="Mandatory">*</span>Concession Details :
															  	</td>
															  	<td width="60%" height="25" class="row-even" align="left">
																	<html:textarea property="concessionDetails" name="examRegDetailsForm" styleId="conDetails" cols="20" rows="4" 
																	onkeypress="return imposeMaxLength1(this, 99);" onkeyup="len_display(this,0,'long_len')"></html:textarea>
																	 <input type="text" id="long_len" value="0" class="len2" size="2" readonly="readonly" 
																	 style="border: none; background-color: #E3EBE5; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">
																	 /100 Characters
																</td>
															</tr>
															<tr>
															  	<td  width="40%" height="25" class="row-odd" align="right">
															  		Net Amount :
															  	</td>
															  	<td width="60%" height="25" class="row-even" align="left">
																	<html:text property="netAmount" name="examRegDetailsForm" styleId="netAmount"  disabled="true"></html:text>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
											
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="2%"></td>
									<td width="49%" height="35">									
										<div align="center">
										<html:button property="" styleClass="formbutton" onclick="submitDetailsForPrint()">
											Save & Print</html:button>
										<html:button property="" styleClass="formbutton" onclick="submitDetails()">
											Save</html:button>
											<html:button property="" styleClass="formbutton" onclick="reset1()">
											<bean:message key="knowledgepro.reset" /></html:button>
											<html:button property="" styleClass="formbutton" onclick="cancel()">
											<bean:message key="knowledgepro.close" /></html:button>
										</div>									
									</td>
								</tr>
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
				
					<tr>
						<td height="3" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
document.getElementById("display").style.display="none";
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
if(document.getElementById("isCons").checked){
	document.getElementById("display").style.display="block";
}else if(document.getElementById("isCons1").checked){
	document.getElementById("conAmount").value='';
	document.getElementById("conDetails").value='';
	document.getElementById("netAmount").value='';
	document.getElementById("tempNetAmount").value='';
	document.getElementById("display").style.display="none";
}
function downloadHallTicket(){
	var url= "examRegistrationDetails.do?method=printHallticket";
	myRef = window.open(url, " ",
			"left=10,top=10,width=800,height=900,toolbar=0,resizable=1,scrollbars=1");
}
</script>
