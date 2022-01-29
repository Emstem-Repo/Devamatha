<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
<script type="text/javascript">
function submitData(method) {
	var x=window.confirm("Please Verify that all the required prints have been taken, , Press 'Ok' if you want to submit without verifying, else press Cancel ");
	if(x)
	{
		document.getElementById("method").value=method;	
	document.certificateRequestOnlineForm.submit();
	}
}


function cancelAction() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
function AssignCheckBox(selected,val,certId)
{
	var TempId = document.getElementById("hidden_"+val).checked;
	var checkedId = document.getElementById(val).checked;
	if(!checkedId) 
		{
	   		 clearFields(val,certId);
	   		 document.getElementById("hidden_"+val).checked=false;
		 }
	}	

function AssignCheckBoxPurpose(val)
{	
	var selected = document.getElementById("aaa_"+val).checked;
	
	if(selected) 
		{
	   		 document.getElementById("hid_"+val).value="true";
		}else{
			
			 document.getElementById("hid_"+val).value="false";
		}
}



function clearCheckBoxPurpose(selected,val,certId)
{
	var counter=document.getElementById("countSelection").value; 
	for(var i=0; i<5; i++)
	{
		if(document.getElementById("purpose_"+val+"_"+i).checked)
		{
		document.getElementById("purpose_"+val+"_"+i).checked=false;
		counter--;
		}
		
	}
	document.getElementById("countSelection").value=counter;
	validateCheckBox(selected,val,certId);
}

function UnselectCheckBox(selected,val,certId)
{
	var Temp = document.getElementById("hidden_"+val).checked;
	var checkedVal = document.getElementById(val).checked;
	
	if(!checkedVal) 
		{
			clearCheckBoxPurpose(selected,val,certId);
	   		document.getElementById("hidden_"+val).checked=false;
		 }
	}	
function clearFields(val,certDetailsId)
{
	for(var i=0; i<20;i++)
	{
			if(document.getElementById("month_"+val+"_"+i)!=null){
				document.getElementById("month_"+val+"_"+i).value="";
			}
			if(document.getElementById("semester_"+val+"_"+i)!=null){
				document.getElementById("semester_"+val+"_"+i).value="";
			}
			if(document.getElementById("type_"+val+"_"+i)!=null || document.getElementById("type2_"+val+"_"+i)!=null){
				document.getElementById("type_"+val+"_"+i).checked="";
				document.getElementById("type2_"+val+"_"+i).checked="";
			}
			if(document.getElementById("YOP_"+val+"_"+i)!=null){
				document.getElementById("YOP_"+val+"_"+i).value="";
			}
	}
	
	removeMarksCardRows('removeExtraMarksCard','ExpAddMore',certDetailsId);
}

function validateCheckBox(selected,val,certId) {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    //UnselectCheckBox(selected,val,certId);
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
	   			inputObj.value="true";
		   	}
	   	}
    }
   		document.getElementById("countSelection").value=checkBoxselectedCount; 
   		var isIdCard=document.getElementById("isIdCardTest").value;
   		if(checkBoxselectedCount==1 && isIdCard=="true")
   	   		{
   			document.getElementById("idCard").style.display="block";
   			document.getElementById("notIdcard").style.display="none";
   		}
   		else
   		{
   			document.getElementById("idCard").style.display="none";
   			document.getElementById("notIdcard").style.display="block";
   		}
     //  document.getElementById("err1").innerHTML = "Number of Certificates Requested is:"+checkBoxselectedCount;
}

function submitMarksCardAdd(method,mode,id){
	document.certificateRequestOnlineForm.addMoreCertId.value=id;
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.certificateRequestOnlineForm.submit();
}

function removeMarksCardRows(method,mode,id){
	document.certificateRequestOnlineForm.addMoreCertId.value=id;
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.certificateRequestOnlineForm.submit();
}
function printTemplate(id,method)
{
	document.certificateRequestOnlineForm.templateId.value=id;
	document.getElementById("method").value=method;
	document.certificateRequestOnlineForm.submit();
}
function InitailizeDownloadButton(){
	var downloadAvailable=0;
	var alreadyAdded=document.getElementById("downloadAvailable").value;
	downloadAvailable=alreadyAdded+1;
	document.getElementById("downloadAvailable").value=downloadAvailable;
}
function openHtml(id) {
	var url = "certificateRequest.do?method=getDescription&certDescId="+id;
	win2 = window.open(url, "Description", "width=500,height=500,scrollbars=yes"); 
}

function uncheckRadio() {
		 var choice = document.getElementById("type");
	 for (i = 0; i < choice.length; i++) {
	  if ( choice[i].checked = true ) 
	   choice[i].checked = false; 
	 }
	}
function IdCardSelected(selected,val,certId,fees)
{
	var checkedVal = document.getElementById(val).checked;
	
	if(checkedVal){
	document.getElementById("isIdCardTest").value=true;
	alert("The temporary ID card will be ready withing 2 working Hrs. You may collect the card from IPM by paying Rs."+fees
			+".Please download the form given below the print option and get signed by the respective HOD/Director");

	var countCheckBoxSelected=document.getElementById("countSelection").value;
	if(countCheckBoxSelected==1){
		document.getElementById("idCard").style.display="block";
		document.getElementById("notIdcard").style.display="none";
	}else
	{
		document.getElementById("idCard").style.display="none";
		document.getElementById("notIdcard").style.display="block";
	}
	}else
	{
		document.getElementById("isIdCardTest").value=false;
		document.getElementById("idCard").style.display="none";
		document.getElementById("notIdcard").style.display="block";
	}
}
function setButtons()
{
	document.getElementById("idCard").style.display="none";
	document.getElementById("notIdcard").style.display="block";
}

$(document).ready(function(){
    $("#report1 .tdIMG");
    $("#report1 .data").hide();
    $("#report1 tr:first-child").show();
    
    $("#report1 .tdIMG").click(function(){
        $(this).parent().parent().next("tr").toggle();
    });
    $('input:checkbox:checked').each(function() {
		$(this).parent().parent().next("tr").show();
	});
    $("#report1 .tdPurpose");
    $("#report1 .purpose").hide();
    $("#report1 tr:first-child").show();
    
    $("#report1 .tdPurpose").click(function(){
        $(this).parent().parent().next("tr").toggle();
    });
    $('input:checkbox:checked').each(function() {
		$(this).parent().parent().next("tr").show();
	});
    $("#report1 .tdStdRemarks");
    $("#report1 .RemarksRequired").hide();
    $("#report1 tr:first-child").show();
    
    
    $("#report1 .tdStdRemarks").click(function(){
        $(this).parent().parent().next("tr").toggle();
    });
    $('input:checkbox:checked').each(function() {
		$(this).parent().parent().next("tr").show();
	});
    $('#SubmitDescription').click(function(){
    	document.getElementById("method").value="ShowDescription";	
    	document.certificateRequestOnlineForm.submit();
    });
	});

</script>
<body>
<html:form action="/certificateRequest" >
	<html:hidden property="formName" value="certificateRequestOnlineForm" />
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="addMoreCertId" value="" />
	<html:hidden property="certDescId" styleId="certDescId" value=""/>
	<html:hidden property="templateId" styleId="templateId" value=""/>
	<html:hidden property="downloadAvailable" styleId="downloadAvailable"/>
	<html:hidden property="countSelection" styleId="countSelection"/>
	<html:hidden property="isIdCardTest" styleId="isIdCardTest"/>
	<html:hidden property="description" styleId="description"/>
	<html:hidden property="isDescriptionDisplayed" styleId="isDescriptionDisplayed"/>
	
	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message key="knowledgepro.certificate.request"/></strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							
								
						<td align="left">
	               	    		<div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	   				<div id="errorMessage" style="color:red;font-family:arial;font-size:11px;">
	                       				<p>
										<!-- <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>-->
										<span id="err"><html:errors/></span> 
										</p>
	                       					<FONT color="green">
											<html:messages id="msg" property="messages" message="true">
											<c:out value="${msg}" escapeXml="false"></c:out><br>
											</html:messages>
						 				 </FONT>
						  			</div>
							</td>
						</tr>
						
						<tr>
							<!-- <td width="5" background="images/st_left.gif"></td>-->
							<td width="100%" valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2" >
							
							<tr height="25px">
								<td  align="left" class="heading"> <b> Please click against the Certificate which you wish to apply for</b>
								</td>
								</tr>
								<tr>
								<td class="heading">
										<b>Once the certificate is ready, you will be intimated through SMS and the same to be collected from the respective office. </b></td>
							</tr>
							
							<tr>
								<td colspan="2">
									<table width="100%"  cellspacing="1" cellpadding="2" id="report1" border="1" >
									<tr height="25px">
											<td width="10%" height="25" class="studentrow-odd" align="center"><bean:message key="knowledgepro.certificateDetails.Online.checkbox"/></td>
                        					<td width="10%" class="studentrow-odd" align="center">Sl No:</td>
                        					<td width="60%" class="studentrow-odd" align="left"><bean:message key="knowledgepro.certificate.name"/></td>
                        					<td width="20%" class="studentrow-odd" align="center"><bean:message key="knowledgepro.certificate.fees"/></td>
                        					<td width="20%" class="studentrow-odd" align="left">Print</td>
                        					<td width="10%" class="studentrow-odd" align="left">Description</td>
                        					
						                        
						   		 </tr>
						     <nested:notEmpty name="certificateRequestOnlineForm" property="certificateRequestOnlineTO">
								<nested:iterate property="certificateRequestOnlineTO.certificateDetailsTo" name="certificateRequestOnlineForm" indexId="count" type="com.kp.cms.to.admin.CertificateDetailsTo">
									<input type="hidden" id="Actualfees" name="Actualfees" value="<nested:write property="originalFees"/>"/>
									<input type="hidden" id="isIdCard" name="isIdCard" value="<nested:write property="isIdCard"/>"/>
									<nested:equal value="false" property="marksCard" >
									
									<nested:equal value="false" property="isIdCard">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
											<td  align="center" >
												<input type="hidden"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].tempChecked"
													id="hidden_<c:out value='${count}'/>"
													value="<nested:write  property='tempChecked'/>" />
						                       
						                        <input type="checkbox" class="tdPurpose"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].checked"
													id="<c:out value='${count}'/>" onclick="validateCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>'),UnselectCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')" class="tdPurpose" />
						                       
												<script type="text/javascript">
												if(document.getElementById("hidden_<c:out value='${count}'/>").value==true || document.getElementById("hidden_<c:out value='${count}'/>").value=="true") {
													document.getElementById("<c:out value='${count}'/>").checked=true;
												}
												else
												{
													document.getElementById("<c:out value='${count}'/>").checked=false;
												}
												
												</script>
											</td>
											<td  align="center"><c:out value="${count + 1}" /></td>
											<td  width="212" align="left"><nested:write property="certificateName"/></td>
                        					<td  width="212" align="center"><nested:write  property="fees" /></td>
                        					
                        					<nested:notEmpty  property="certTemplateAssignedTo">
                        					<nested:iterate property="certTemplateAssignedTo" indexId="counttemp" type="com.kp.cms.to.admin.CertificateDetailsTemplateTO">
                        					<td  width="212" align="left">
                        					<img src="images/print-icon1.png" style="cursor:pointer" width="30" height="20"
								 			onclick="printTemplate('<nested:write property="id"/>','printTemplate')" title="Print"></td>
                        					
                        					<!-- <td  width="212" align="left"><input type="button" value="Print" onclick="printTemplate('<nested:write property="id"/>','printTemplate')"/></td>-->
                        					</nested:iterate>
                        					</nested:notEmpty>
                        					<nested:empty property="certTemplateAssignedTo"><td  width="212" align="left"></td></nested:empty>
                        					
                        					<td  width="212" align="center"><img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20" 
								 onclick="openHtml('<nested:write property="id"/>')" title="Description" ></td>
                        </tr>
                        <nested:equal value="true" property="purposeOrRemarksExist">
                        <%	String purposeHide = "purposeHide_" + count; %>
                      
                      
                        <tr class="purpose" id="<%=purposeHide%>">
 						<td></td>
 						<td></td>
 						<td colspan="2">
 						 <nested:notEmpty property="assignPurposeTo">
 							<table width="100%" cellspacing="0" cellpadding="0" border="0" bordercolor="#0000ff">
                        		
							  
								<tr height="25">
								<td width="10%" class="row-odd-blue" align="center">Sl No</td>
								<td width="50%" class="row-odd-blue" align="left">Purpose</td>
								<td width="10%" class="row-odd-blue" align="left">Select</td>
								</tr>
								<nested:iterate property="assignPurposeTo" indexId="count1" type="com.kp.cms.to.admin.AssignCertificateRequestPurposeTO">
								<tr>
		                        	<td  align="center" class="row-blue"><c:out value="${count1 + 1}" /></td>
		                        	<td  class="row-blue" align="left"><nested:write property="purposeName"/></td>
									<td class="row-blue"  align="left">
									  <%	String checkPurp1 = "purpose_" + count; %>
                       				  <%	String purposeChecked = checkPurp1+"_"+count1;  %>
									<!--<nested:checkbox property="assignChecked" styleId="<%=purposeChecked%>" onclick="InitailizeDownloadButton('<c:out value='${purposeName}'/>)"></nested:checkbox>-->
									<nested:checkbox property="assignChecked" styleId="<%=purposeChecked%>" onclick="InitailizeDownloadButton()"></nested:checkbox>
									</td>
									</tr>	
															
								</nested:iterate>
						   </table>
						    </nested:notEmpty>
						 <nested:equal property="isReasonRequired" value="true">
 						<table width="100%" cellspacing="1" cellpadding="1" border="1">
							<tr height="25">
								<td width="30%" class="row-odd-blue" align="left"><span class="Mandatory">*</span>Please Enter Reason of Applying for this Certificate:-</td>
								<td width="30%" class="row-odd-blue" align="left"><nested:text property="studentRemarks" size="50" maxlength="100"/></td>
							</tr>	
					   </table>
         				</nested:equal>
						   </td>
						   <td></td>
						   <td></td>
						   </tr>
						</nested:equal>
         				</nested:equal>
         				<nested:equal value="true" property="isIdCard">
         						<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
								<td align="center" >
												<input type="hidden"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].tempChecked"
													id="hidden_<c:out value='${count}'/>"
													value="<nested:write  property='tempChecked'/>" />
						                       
						                        <input type="checkbox"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].checked"
													id="<c:out value='${count}'/>" onclick="validateCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>'),
													UnselectCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>'),
													IdCardSelected(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>','<nested:write property="originalFees"/>')" class="tdPurpose"; />
						                       
												<script type="text/javascript">
												if(document.getElementById("hidden_<c:out value='${count}'/>").value==true || document.getElementById("hidden_<c:out value='${count}'/>").value=="true")
													 {
													document.getElementById("<c:out value='${count}'/>").checked=true;
												}
												else
												{
													document.getElementById("<c:out value='${count}'/>").checked=false;
												}
												
												</script>
											</td>
											<td  align="center"><c:out value="${count + 1}" /></td>
											<td  width="212" align="left"><nested:write property="certificateName"/></td>
                        					<td  width="212" align="center"><nested:write  property="fees" /></td>
                        					
                        					<nested:notEmpty  property="certTemplateAssignedTo">
                        					<nested:iterate property="certTemplateAssignedTo" indexId="counttemp" type="com.kp.cms.to.admin.CertificateDetailsTemplateTO">
                        					<td  width="212" align="left">
                        					<img src="images/print-icon1.png" style="cursor:pointer" width="30" height="20"
								 			onclick="printTemplate('<nested:write property="id"/>','printTemplate')" title="Print"></td>
                        					
                        					</nested:iterate>
                        					</nested:notEmpty>
                        					<nested:empty property="certTemplateAssignedTo"><td  width="212" align="left"></td></nested:empty>
                        					
                        					<td  width="212" align="center"><img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml('<nested:write property="id"/>')" title="Description"></td>
                        </tr>
                         <nested:equal value="true" property="purposeOrRemarksExist">
                        <%	String purposeHide = "purposeHide_" + count; %>
                        <tr class="purpose" id="<%=purposeHide%>">
 						<td></td>
 						<td></td>
 						<td colspan="2">
 						<nested:notEmpty property="assignPurposeTo">
 							<table width="100%" cellspacing="0" cellpadding="0" border="0" bordercolor="#0000ff">
                        		
							  
								<tr height="25">
								<td width="10%" class="row-odd-blue" align="center">Sl No</td>
								<td width="50%" class="row-odd-blue" align="left">Purpose</td>
								<td width="10%" class="row-odd-blue" align="left">Select</td>
								</tr>
								<nested:iterate property="assignPurposeTo" indexId="count1" type="com.kp.cms.to.admin.AssignCertificateRequestPurposeTO">
								<tr>
		                        	<td  align="center" class="row-blue"><c:out value="${count1 + 1}" /></td>
		                        	<td  class="row-blue" align="left"><nested:write property="purposeName"/></td>
									<td class="row-blue"  align="left">
									<%	String checkPurp2 = "purpose_" + count; %>
                       				 <%	String purposeChecked = checkPurp2+"_"+count1;  %>
									<!--<nested:checkbox property="assignChecked"  styleId="<%=purposeChecked%>"  onclick="InitailizeDownloadButton('<c:out value='${purposeName}'/>)"></nested:checkbox>-->
									<nested:checkbox property="assignChecked"  styleId="<%=purposeChecked%>"  onclick="InitailizeDownloadButton()"></nested:checkbox>
									</td>
									</tr>	
															
								</nested:iterate>
								
						  
						   </table>
						  </nested:notEmpty>
						  <nested:equal property="isReasonRequired" value="true">
 							<table width="100%" cellspacing="1" cellpadding="1" border="1">
							<tr height="25">
								<td width="30%" class="row-odd-blue" align="left"><span class="Mandatory">*</span>Please Enter Reason of Applying for this Certificate:-</td>
								<td width="30%" class="row-odd-blue" align="left"><nested:text property="studentRemarks" size="50" maxlength="100"/></td>
							</tr>	
					   		</table>
         					</nested:equal>
						   </td>
						   <td></td>
						   <td></td>
						   </tr>
						</nested:equal>
						
         				</nested:equal>
         				
         				</nested:equal>
						<nested:equal value="true" property="marksCard" >
								<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
											<td align="center" >
											
												<input type="hidden"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].tempChecked"
													id="hidden_<c:out value='${count}'/>"
													value="<nested:write property='tempChecked'/>" />
						                       
						                        <input type="checkbox"
													name="certificateRequestOnlineTO.certificateDetailsTo[<c:out value='${count}'/>].checked"
													id="<c:out value='${count}'/>" onclick="validateCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>'), AssignCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')" class="tdIMG"/>
						                       
												<script type="text/javascript">
													if(document.getElementById("hidden_<c:out value='${count}'/>").value==true || document.getElementById("hidden_<c:out value='${count}'/>").value=="true")
												    {
														document.getElementById("<c:out value='${count}'/>").checked=true;
													}
													
												</script>
											</td>
											<td  align="center"><c:out value="${count + 1}" /></td>
											<td  width="212" align="left"><nested:write property="certificateName"/></td>
                        					<td  width="212" align="center"><nested:write property="fees" /></td>
                        					<nested:notEmpty  property="certTemplateAssignedTo">
                        					<nested:iterate property="certTemplateAssignedTo" indexId="counttemp" type="com.kp.cms.to.admin.CertificateDetailsTemplateTO">
                        					<td  width="212" align="left"><img src="images/print-icon1.png" style="cursor:pointer" width="20" height="20"
								 onclick="printTemplate('<nested:write property="id"/>','printTemplate')" title="Print"></td>
                        					<!-- <input type="button" value="Print" onclick="printTemplate('<nested:write property="id"/>','printTemplate')"/>-->
                        					</nested:iterate>
                        					</nested:notEmpty>
                        					<nested:empty property="certTemplateAssignedTo"><td  width="212" align="left"></td></nested:empty>
                        					<td  width="212" align="center"><img src="images/questionMark.jpg" style="cursor:pointer" width="20" height="20"
								 onclick="openHtml('<nested:write property="id"/>')" title="Description"></td>
                        		</tr>
                        		<%	String marksCardHide = "marksCardHide_" + count; %>
                        		<tr class="data" id="<%=marksCardHide%>">
 									<td colspan="9">
 										<table width="100%" cellspacing="1" cellpadding="1" border="1" bordercolor="ffffff">
                        				<nested:notEmpty property="marksCardTo" > 
                        				<tr height="25">
                        				<td width="10%" class="row-odd-blue" align="center">Sl No:</td>
                        				<td class="row-odd-blue" width="8%" align="right"> 
													<div align="center"><span class="Mandatory">*</span>
													<bean:message key="knowledgepro.exam.examDefinition.examType" /></div>
												</td>
									<td class="row-odd-blue"  width="8%" align="right"> 
													<div align="center"><span class="Mandatory">*</span>
													<bean:message key="knowledgepro.exam.examDefinition.year" /></div>
												</td>
												<td class="row-odd-blue"  width="6%" align="right" > 
													<div align="center"><span class="Mandatory">*</span>
													<bean:message key="knowledgepro.exam.examDefinition.month" /></div>
													</td>
											<td class="row-odd-blue"  width="8%" align="right"> 
													<div align="center"><span class="Mandatory">*</span>
													<bean:message key="studentView.Semester.link" /></div></td>
											<td class="row-odd-blue"></td>
                        				</tr>
										<nested:iterate property="marksCardTo"  indexId="counter" type="com.kp.cms.to.admin.CertificateRequestMarksCardTO">
	                       					
		                        				<tr>
		                        				<td  align="center" class="row-blue"><c:out value="${counter + 1}" /></td>
		                        				
		                        				<%	String style1 = "type_" + count; %>
		                        				<%	String style11 = "type2_" + count; %>
		                        				<%	String styleType = style1+"_"+counter;  %>
		                        				<%	String styleType1 = style11+"_"+counter;  %>
		                        				<td class="row-blue" width="20%" align="left">
													<nested:radio property="type" styleId='<%=styleType %>' value="Regular">Regular</nested:radio>
													<nested:radio property="type" styleId='<%=styleType1 %>' value="Supplementary">Supplementary</nested:radio>
												
												</td> 
												<%String YOP="YOP_"+count;%>
												<%String dynaYearId=YOP+"_"+counter; %>
												
												
												<td width="10%" align="left" class="row-blue">
												<input type="hidden" Id='<%=YOP %>'/>
												<c:set var="dyopid"><%=dynaYearId %></c:set>
													<nested:select property="year" styleId='<%=dynaYearId %>' styleClass="comboSmall" >
													<html:option value="">Select</html:option>
													<cms:renderCertificateYear normalYear="true"></cms:renderCertificateYear>
												</nested:select>
																				
									
										<script type="text/javascript">
											var opid= '<nested:write property="year"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
									</td>
		  	    <!--<nested:text property="year" styleClass="TextBox" size="20" maxlength="4" onkeypress="return isNumberKey(event)"></nested:text>-->
												
						<%	String style2 = "month_" + count; %>
						<%	String styleMonth = style2+"_"+counter;  %>
												<td width="10%" align="left"  class="row-blue">
													<nested:select property="month" styleId='<%=styleMonth %>' styleClass="comboSmall">
														<html:option value="">Select</html:option>
														<html:option value="JAN">JAN</html:option>
										              	<html:option value="FEB">FEB</html:option>
														<html:option value="MAR">MAR</html:option>
														<html:option value="APR">APR</html:option>
														<html:option value="MAY">MAY</html:option>
														<html:option value="JUN">JUN</html:option>
														<html:option value="JUL">JUL</html:option>
														<html:option value="AUG">AUG</html:option>
														<html:option value="SEPT">SEPT</html:option>
														<html:option value="OCT">OCT</html:option>
														<html:option value="NOV">NOV</html:option>
														<html:option value="DEC">DEC</html:option>
													</nested:select>
													
												</td>
										
										<%	String style3 = "semester_" + count; %>
										<%	String styleSemester = style3+"_"+counter;  %>
												
												<td width="10%" align="left" class="row-blue">
												<nested:select property="semester" styleId='<%=styleSemester %>' styleClass="comboSmall" >
													<option value="">Select</option>
													<html:option value="1">1</html:option>
																			<html:option value="2">2</html:option>
																			<html:option value="3">3</html:option>
																			<html:option value="4">4</html:option>
																			<html:option value="5">5</html:option>
																			<html:option value="6">6</html:option>
																			<html:option value="7">7</html:option>
																			<html:option value="8">8</html:option>
																			<html:option value="9">9</html:option>
																			<html:option value="10">10</html:option>
																			<html:option value="11">11</html:option>
																			<html:option value="12">12</html:option>						
												</nested:select>
												</td>
												<td  class="row-blue" align="left" width="20%">
													<input type="button" class="formbutton" value="Add More" onclick="submitMarksCardAdd('resetMarksCard','ExpAddMore','<nested:write property="certDetailsId"/>')"/>
												<nested:greaterThan value="0" property="marksCardLength">
														<input type="button" class="formbutton" value="Remove" onclick="submitMarksCardAdd('removeMarksCard','ExpAddMore','<nested:write property="certDetailsId"/>')"/>
												</nested:greaterThan>
												</td> 
												</tr>
		                        				</nested:iterate>
		                        				
                        			</nested:notEmpty>	
										</table></td>                       			
                        			</tr>
                        		</nested:equal>
						</nested:iterate>
					</nested:notEmpty>
						</table>
					</td>
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr class="row-white">
						<td align="left">
	               	    		<div id="err1" style="color:red;font-family:arial;font-size:11px;"></div>
	               	   				 <div id="errorMessage" style="color:red;font-family:arial;font-size:11px;">
	                       				<p>
										<!-- <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>-->
										</p>
										</div>
							</td>
							</tr>
							<tr>
                   			<td colspan="4"><div align="center" >
                   				<logic:equal value="true" property="isDescriptionDisplayed" name="certificateRequestOnlineForm">
                   				<div id="idCard">
                   					<html:button property="" styleClass="btnbg" value="Submit" onclick="submitData('calculateAmount')"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                   					&nbsp; 
									<html:button property="" styleClass="btnbg" value="Close" onclick="cancelAction()"></html:button>
                   				</div>
                   				<div id="notIdcard">
									<html:button property="" styleClass="btnbg" value="Proceed with Smart Card Payment" onclick="submitData('calculateAmount')"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp; 
									<html:button property="" styleClass="btnbg" value="Close" onclick="cancelAction()"></html:button>
									</div>
								 </logic:equal>
								 <logic:equal value="false" property="isDescriptionDisplayed" name="certificateRequestOnlineForm">
								 		<html:button property="" styleId="SubmitDescription" styleClass="btnbg" value="Submit" onclick="showDescription('ShowDescription')"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								 		<html:button property="" styleClass="btnbg" value="Close" onclick="cancelAction()"></html:button>
								 </logic:equal>
									
					</div></td>
                 </tr>
                 <tr><td>&nbsp;</td></tr>
					</table>
					</div>
				<div id="timer1">
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
						
				</tr>
				
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
</body>
<script language="JavaScript" >
var print = "<c:out value='${certificateRequestOnlineForm.printPage}'/>";
if(print!=null && print!="" && print == "true"){
	var url = "certificateRequest.do?method=printCertificateTemplate";
	myRef = window.open(url, "Print Form","left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");	
	}

</script>

<script language="JavaScript" >
	var focusField=document.getElementById("focusValue").value;
	if(focusField != 'null'){  
   	if(document.getElementById(focusField)!=null){    
        document.getElementById(focusField).focus();
	}}

	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
    document.getElementById("countSelection").value=checkBoxselectedCount; 
    document.getElementById("idCard").style.display="none";
	document.getElementById("notIdcard").style.display="block";
    
   // document.getElementById("err1").innerHTML = "Number of Certificates Requested is:"+checkBoxselectedCount;
    $(document).ready(function(){
   
        var messageStr=document.getElementById("description").value;
        if(messageStr!=null && messageStr!=""){
    	if(document.getElementById("isDescriptionDisplayed").value!=null && document.getElementById("isDescriptionDisplayed").value!=""){
    		document.getElementById('timer1').innerHTML = messageStr;
    		
    		$("#timer1").dialog({
		        resizable: true,
		        modal: true,
		        height: 400,
		        title: "Certificate Description",
		        width: 800,
		        close: function() {
    	    	$("#timer1").dialog("destroy");
    	    	$("#timer1").hide();
             },
            buttons: {
	               Close : function() {
      	                $("#timer1").dialog("close");
      	              	$("#timer1").dialog("destroy");
      	                $("#timer1").hide();
	                     }
       }
             
    });
    } }
    });
    
</script>