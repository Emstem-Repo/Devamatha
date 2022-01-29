<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type="text/javascript">
function submitData() {
	document.certificateRequestOnlineForm.submit();
}

function saveCertCourse() {
	document.certificateRequestOnlineForm.submit();
}
function cancelAction()
{
	document.location.href = "certificateRequest.do?method=initStudentEdit";
}

function submitMarksCardAdd(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.certificateRequestOnlineForm.submit();
}

function getStudentDetails(registerNo){
	var url  = "studentEdit.do?method=getStudentDetailsForCertificate&regNo="+registerNo;
	myRef = window.open(url,"View Student Details","left=20,top=20,width=800,height=300,toolbar=1,resizable=0,scrollbars=1");
}

function RejectAction(id,regNo){
	 var reason=prompt("Please enter the reason for Rejection:","");
	if(reason!=null && reason!="")
	{
		document.getElementById("rejectId").value=id;
		document.getElementById("regNo").value=regNo;
		document.getElementById("rejectReason").value=reason;
		document.getElementById("isReject").value="true";
		document.getElementById("method").value="RejectCertificateRequest";
		document.certificateRequestOnlineForm.submit();
	}else if(reason=="" && reason!=null)
	{
		RejectAction(id);
	}
}


function UnselectCheckBox(selected,val,certId)
{
	var Temp = document.getElementById("hidden_"+val).checked;
	var checkedVal = document.getElementById(val).checked;
	if(!checkedVal) 
	{
		    document.getElementById("hidden_"+val).checked=false;
	   		document.getElementById("hidden_"+val).value=false;
	}
	else
	{
		document.getElementById(val).checked=true;
		document.getElementById(val).value = true;
		document.getElementById("hidden_"+val).checked=true;
   		document.getElementById("hidden_"+val).value=true;
	}	
}


function selectIssuedCheckBox(selected,val,certId)
{
	
	var Temp = document.getElementById("hiddenIssued_"+val).checked;
	var checkedVal = document.getElementById("issue_"+val).checked;
	if(!checkedVal) 
		{
		    document.getElementById("hiddenIssued_"+val).checked=false;
	   		document.getElementById("hiddenIssued_"+val).value=false;
		 }
	else
	{
		document.getElementById("issue_"+val).checked=true;
		document.getElementById("issue_"+val).value = true;
		document.getElementById("hiddenIssued_"+val).checked=true;
   		document.getElementById("hiddenIssued_"+val).value=true;
	}	
}



	function RemarksAction(id,remarks)
	{
	 var rmk=prompt("Please Enter Remarks:",remarks);
		if(rmk!=null && rmk!="")
			{
				document.getElementById("remarkId").value=id;
				document.getElementById("adminRemarks").value=rmk;
				document.getElementById("method").value="RemarksAction";
				document.certificateRequestOnlineForm.submit();
			}
	else if(rmk=="" && rmk!=null)
	{
		RemarksAction(id);
	}
		}
			

$(document).ready(function(){
    $("#report1 .tdIMG");
    $("#report1 .data").hide();
    $("#report1 tr:first-child").show();
    
    $("#report1 .tdIMG").click(function(){
        $(this).parent().parent().next("tr").toggle();
    });

    var focusField=document.getElementById("focusValue").value;
	 if(focusField != null){  
		 if(document.getElementById(focusField)!=null)   
	if(focusField == "marksCardHide")
    { 
   $("#marksCardHide").toggle();
    }
	
	 }   
});

</script>
<html:form action="/certificateRequest" >
	<html:hidden property="formName" value="certificateRequestOnlineForm" />
	<html:hidden property="method" styleId="method" value="SubmitCompletedList"/>
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<html:hidden property="rejectId" styleId="rejectId" value=""/>
	<html:hidden property="rejectReason" styleId="rejectReason" value=""/>
	<html:hidden property="isReject" styleId="isReject" value=""/>
	<html:hidden property="adminRemarks" styleId="adminRemarks" value=""/>
	<html:hidden property="remarkId" styleId="remarkId" value=""/>
	<html:hidden property="regNo" styleId="regNo" value=""/>
	
	<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.certificateRequestOnlineTO"/> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.certificateRequestOnlineTO" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
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
										<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
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
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" class="row-white" >
							
							<tr height="25px">
								<td  align="center" width="80%" class="row-white"> <b> Certificate Pending List</b> </td>
							</tr>
							
	<tr>
				<td colspan="2">
					<table width="100%" height="30" border="1" cellpadding="2" cellspacing="1" id="report1">
					<tr height="30px">
							<td width="40" class="row-odd" align="center">Sl No:</td>
							<td width="80" class="row-odd" align="center">Applied Date</td>
							<td width="80" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.regno"/></td>
							<td width="250" class="row-odd" align="center"><bean:message key="knowledgepro.admin.name"/></td>
							<td width="150" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.classname"/></td>
           					<td width="400" class="row-odd" align="center"><bean:message key="knowledgepro.certificate.name"/></td>
           					<td width="150" class="row-odd" align="center">Reason For Certificate</td>
           					<td width="150" class="row-odd" align="center">Admin Remarks</td>
           					<td width="20" class="row-odd" align="center">Completed</td>
           					<td width="20" class="row-odd" align="center">Issued</td>
           					<td width="25" class="row-odd" align="center">Marks Card  Details</td>
           					<td width="35" class="row-odd" align="center">Reject/Reason</td>
		                        
   					</tr>
		    <logic:notEmpty name="certificateRequestOnlineForm" property="studentToList">
			<nested:iterate id="cert" property="studentToList" name="certificateRequestOnlineForm" indexId="count">
			<logic:equal value="false" property="marksCard" name="cert">
			
					<c:choose>
							<c:when test="${count%2 == 0}">
											<tr class="row-even">
							</c:when>
							<c:otherwise>
											<tr class="row-white">
							</c:otherwise>
					</c:choose>
					<td align="center"><c:out value="${count + 1}" /></td>
					<td align="center"><nested:write name="cert" property="appliedDate"/></td>
					<td  align="center">
					<div><a href="#" class="navmenu" onclick="getStudentDetails('<nested:write name="cert" property="registerNo"/>')"><nested:write name="cert" property="registerNo"/></a></div>
					</td>
					<td align="left"><nested:write name="cert" property="studentName"/></td>
					<td  align="left"><nested:write name="cert" property="className"/></td>
					<td align="left"><nested:write name="cert" property="certificateName"/></td>
					<td  align="left"><nested:write name="cert" property="studentRemarks"/></td>
					<td  align="left"><nested:write name="cert" property="adminRemarks"/><div align="right">
					<img src="images/remarks.jpg" width="35" height="30" style="cursor:pointer" onclick="RemarksAction('<nested:write property="id"/>','<nested:write property="adminRemarks"/>')"></div></td>
					
               		<td align="center" >
									<input type="hidden" name="studentToList[<c:out value='${count}'/>].tempCompletedChecked"
									id="hidden_<c:out value='${count}'/>"
									value="<nested:write name='cert' property='isCompleted'/>" />
						                       
						            <input type="checkbox"
									name="studentToList[<c:out value='${count}'/>].isCompleted"
									id="<c:out value='${count}'/>" onclick="UnselectCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')"/>
						                       
									<script type="text/javascript">
									var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
									if(studentId == "true" || studentId) 
									{
										document.getElementById("<c:out value='${count}'/>").checked = true;
										document.getElementById("<c:out value='${count}'/>").value=true;
									}else{
										document.getElementById("<c:out value='${count}'/>").checked = false;
										document.getElementById("<c:out value='${count}'/>").value=false;
									}
											
									</script>
				 </td>
				  <td align="center" >
									<input type="hidden" name="studentToList[<c:out value='${count}'/>].tempIssuedChecked"
									id="hiddenIssued_<c:out value='${count}'/>"
									value="<nested:write name='cert' property='isIssued'/>" />
						                       
						            <input type="checkbox"
									name="studentToList[<c:out value='${count}'/>].isIssued"
									id="issue_<c:out value='${count}'/>" onclick="selectIssuedCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')"/>
						                       
									<script type="text/javascript">
									var student = document.getElementById("hiddenIssued_<c:out value='${count}'/>").value;
									if(student == "true" || student) 
									{
										document.getElementById("issue_<c:out value='${count}'/>").checked = true;
										document.getElementById("issue_<c:out value='${count}'/>").value=true;
									}else{
										document.getElementById("issue_<c:out value='${count}'/>").checked = false;
										document.getElementById("issue_<c:out value='${count}'/>").value=false;
									}
											
									</script>
				 </td>
				<td></td>
					<logic:equal property="rejected" value="false" name="cert">
                        		<td align="center"><img src="images/rejectNew.jpg" width="35" height="30" style="cursor:pointer" onclick="RejectAction('<nested:write name="cert" property="id"/>','<nested:write name="cert" property="registerNo"/>')"></td>
                     </logic:equal>
                     <logic:equal property="rejected" value="true" name="cert">
                        		<td align="center"><nested:write name="cert" property="rejectReason"/></td>
                      </logic:equal>	
				<!-- <td align="center"><img src="images/rejectNew.jpg" width="35" height="30" style="cursor:pointer" onclick="RejectAction('<nested:write property="id"/>')"></td>-->
		</logic:equal>
		<logic:equal value="true" property="marksCard" name="cert">
					<c:choose>
							<c:when test="${count%2 == 0}">
											<tr class="row-even">
							</c:when>
							<c:otherwise>
											<tr class="row-white">
							</c:otherwise>
					</c:choose>
											<td  align="center"><c:out value="${count + 1}" /></td>
											<td  align="center"><nested:write name="cert" property="appliedDate"/></td>
											<td  align="center">
											<div><a href="#" class="navmenu" onclick="getStudentDetails('<nested:write name="cert" property="registerNo"/>')"><nested:write name="cert" property="registerNo"/></a></div>
											</td>
											<td  align="left"><nested:write name="cert" property="studentName"/></td>
											<td  align="left"><nested:write name="cert" property="className"/></td>
											<td  align="left"><nested:write name="cert" property="certificateName"/></td>
											<td  align="left"><nested:write name="cert" property="studentRemarks"/></td>
											<td  align="left"><nested:write name="cert" property="adminRemarks"/><div align="right">
											<img src="images/remarks.jpg" width="35" height="30" style="cursor:pointer" onclick="RemarksAction('<nested:write property="id"/>','<nested:write property="adminRemarks"/>')"></div></td>
											
											<td align="center" >
												<input type="hidden"
													name="studentToList[<c:out value='${count}'/>].tempCompletedChecked"
													id="hidden_<c:out value='${count}'/>"
													value="<nested:write name='cert' property='isCompleted'/>" />
						                       
						                        <input type="checkbox"
													name="studentToList[<c:out value='${count}'/>].isCompleted"
													id="<c:out value='${count}'/>" onclick="UnselectCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')" />
						                       
												<script type="text/javascript">
													var st = document.getElementById("hidden_<c:out value='${count}'/>").value;
													if(st == "true" || st) 
													{
														document.getElementById("<c:out value='${count}'/>").checked = true;
														document.getElementById("<c:out value='${count}'/>").value=true;
													}else{
														document.getElementById("<c:out value='${count}'/>").checked = false;
														document.getElementById("<c:out value='${count}'/>").value=false;
													}
																
												</script>
						</td>
						<td align="center" >
									<input type="hidden" name="studentToList[<c:out value='${count}'/>].tempIssuedChecked"
									id="hiddenIssued_<c:out value='${count}'/>"
									value="<nested:write name='cert' property='isIssued'/>" />
						                       
						            <input type="checkbox"
									name="studentToList[<c:out value='${count}'/>].isIssued"
									id="issue_<c:out value='${count}'/>" onclick="selectIssuedCheckBox(this.value,'<c:out value='${count}'/>','<nested:write property="id"/>')"/>
						                       
									<script type="text/javascript">
									var studentId1 = document.getElementById("hiddenIssued_<c:out value='${count}'/>").value;
									if(studentId1 == "true" || studentId1) 
									{
										document.getElementById("issue_<c:out value='${count}'/>").checked = true;
										document.getElementById("issue_<c:out value='${count}'/>").value=true;
									}else{
										document.getElementById("issue_<c:out value='${count}'/>").checked = false;
										document.getElementById("issue_<c:out value='${count}'/>").value=false;
									}
												
									</script>
				 </td>
								<td align="center"><img src="images/View_icon.gif"	width="30" height="30"  style="cursor:pointer" class="tdIMG"></td>
                        	<logic:equal property="rejected" value="false" name="cert">
                        		<td align="center"><img src="images/rejectNew.jpg" width="35" height="30" style="cursor:pointer" onclick="RejectAction('<nested:write name="cert" property="id"/>','<nested:write name="cert" property="registerNo"/>')"></td>
                        	</logic:equal>
                        	<logic:equal property="rejected" value="true" name="cert">
                        		<td align="center"><nested:write name="cert" property="rejectReason"/></td>
                        	</logic:equal>	
                        </tr>
                       <tr class="data">
 									<td colspan="8">
 										<table width="100%" cellspacing="1" cellpadding="2">
 										<logic:notEmpty property="studentToList" name="certificateRequestOnlineForm">		
                        				<logic:notEmpty property="marksCardTo" name="cert">          
										<nested:iterate property="marksCardTo" name="cert" id="markCard" indexId="counter">
                        					<!-- <tr>
				                         		<td  class="row-even" align="center" colspan="8" height="0"></td> 
				                         	</tr >-->
		                        				<tr id="marksCardHide" height="25">
		                        				
		                        				<td class="row-odd" width="10%"> 
													<div align="left">
													<bean:message key="knowledgepro.exam.examDefinition.examType" /></div></td>
													<td width="15%" class="row-even"><bean:write  name="markCard" property="type"/>
													
												</td> 
												<td class="row-odd" width="10%"> 
													<div align="left">
													<bean:message key="knowledgepro.exam.examDefinition.year" /></div></td>
													<td width="10%" class="row-even"><bean:write  name="markCard" property="year"/>
											  	  
												</td>
										<%	String styleMonth = "month_" + counter; %>
												<td class="row-odd" width="10%"> 
													<div align="left">
													<bean:message key="knowledgepro.exam.examDefinition.month" /></div></td>
													<td width="15%" class="row-even"><bean:write  name="markCard" property="month"/>
												</td>
												<td class="row-odd" width="10%"> 
													<div align="left">
													<bean:message key="studentView.Semester.link" /></div>
											  	</td>
													<td width="10%" class="row-even"><bean:write  name="markCard" property="semester"/>
												</td>
												
												</tr>
												
		                        				</nested:iterate>
		                        				
						                        <!--  <tr>
						                         	<td  class="row-even" align="center" colspan="8" height="0">
													</td> 
						                         </tr>-->
                        			</logic:notEmpty>	
                        			</logic:notEmpty>		
 										
 										</table></td>                       			
                        			</tr>
                        			
                        		</logic:equal>
						</nested:iterate>
					</logic:notEmpty>
									
					
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
                   	<td colspan="2"><div align="center">
                   				
						<html:button property="" styleClass="formbutton" value="Submit"
											onclick="saveCertCourse()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											&nbsp; 
						<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
                 <tr><td>&nbsp;</td></tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

<script language="JavaScript" >
	var focusField=document.getElementById("focusValue").value;
	if(focusField != 'null'){  
   	if(document.getElementById(focusField)!=null)      
        document.getElementById(focusField).focus();
	}

</script>