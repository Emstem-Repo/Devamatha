<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

function takePrint(){
	document.getElementById("method").value = "printGuideDetails";
	document.phdStudentReminderationForm.submit();
	}

function generateStudentDetails(id,studentId){
	document.location.href = "PhdStudentReminderation.do?method=generateStudentDetails&id="+id+"&studentId="+studentId;
}

function resetFormFields(){	

	document.getElementById("startDate").value = "";
	document.getElementById("endDate").value = "";
	resetErrMsgs();
}
function searchStudent(){
	document.getElementById("method").value = "studentDetailsSearch";
	document.phdStudentReminderationForm.submit();
	}

function cancel(){
	document.getElementById("method").value = "studentDetailsSearch";
	document.phdStudentReminderationForm.submit();
	}

function closeGuideedit(){
	document.location.href = "PhdStudentReminderation.do?method=searchGuidesFeesPayment";
	}
function getGConveyanceAmount(){
	var gOther = document.getElementById("gAmountOther").value;
	var grantotl=document.getElementById("gAmountTotal").value;
	var ggamount = document.getElementById("ggamount").value;
	var gconvay = document.getElementById("gAmountConveyance").value;
	gOther=parseInt(gOther);
	grantotl=parseInt(grantotl);
	ggamount=parseInt(ggamount);
	gconvay=parseInt(gconvay);
	if(grantotl>0 && gOther>0 && gconvay>0){
		grantotl=ggamount+gOther;
		grantotl = grantotl+gconvay;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+grantotl;
		document.getElementById("gAmountTotal").value = grantotl;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}else if(gconvay>0){
	var ggamount = document.getElementById("ggamount").value;
	ggamount=parseInt(ggamount);
	ggamount = ggamount+gconvay;
	var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ggamount;
	document.getElementById("gAmountTotal").value = ggamount;
	document.getElementById("gtotalAmountId").innerHTML = htm;
	}else if(grantotl>0 && gOther>0 ){
		grantotl=ggamount+gOther;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+grantotl;
		document.getElementById("gAmountTotal").value = grantotl;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}else{
		var ggamount = document.getElementById("ggamount").value;
		ggamount=parseInt(ggamount);
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ggamount;
		document.getElementById("gAmountTotal").value = ggamount;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}
}

function gAmountOthers(){
	var gconvay = document.getElementById("gAmountConveyance").value;
	var grantotl=document.getElementById("gAmountTotal").value;
	var ggamount = document.getElementById("ggamount").value;
	var gOther = document.getElementById("gAmountOther").value;
	gconvay=parseInt(gconvay);
	grantotl=parseInt(grantotl);
	ggamount=parseInt(ggamount);
	gOther=parseInt(gOther);
	if(grantotl>0 && gOther>0 && gconvay>0){
		grantotl=ggamount+gconvay;
		grantotl = grantotl+gOther;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+grantotl;
		document.getElementById("gAmountTotal").value = grantotl;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}else if(gOther>0){
	var ggamount = document.getElementById("ggamount").value;
	ggamount=parseInt(ggamount);
	ggamount = ggamount+gOther;
	var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ggamount;
	document.getElementById("gAmountTotal").value = ggamount;
	document.getElementById("gtotalAmountId").innerHTML = htm;
	}else if(grantotl>0 && gconvay>0 ){
		grantotl=ggamount+gconvay;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+grantotl;
		document.getElementById("gAmountTotal").value = grantotl;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}else{
		var ggamount = document.getElementById("ggamount").value;
		ggamount=parseInt(ggamount);
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ggamount;
		document.getElementById("gAmountTotal").value = ggamount;
		document.getElementById("gtotalAmountId").innerHTML = htm;
	}
	
}

function getCConveyanceAmount(){
	var cOther = document.getElementById("cAmountOther").value;
	var crantotl=document.getElementById("cAmountTotal").value;
	var ccamount = document.getElementById("ccamount").value;
	var cconvay = document.getElementById("cAmountConveyance").value;
	cOther=parseInt(cOther);
	crantotl=parseInt(crantotl);
	ccamount=parseInt(ccamount);
	cconvay=parseInt(cconvay);
	if(crantotl>0 && cOther>0 && cconvay>0){
		crantotl=ccamount+cOther;
		crantotl = crantotl+cconvay;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+crantotl;
		document.getElementById("cAmountTotal").value = crantotl;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}else if(cconvay>0){
	var ccamount = document.getElementById("ccamount").value;
	ccamount=parseInt(ccamount);
	ccamount = ccamount+cconvay;
	var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ccamount;
	document.getElementById("cAmountTotal").value = ccamount;
	document.getElementById("ctotalAmountId").innerHTML = htm;
	}else if(crantotl>0 && cOther>0 ){
		crantotl=ccamount+cOther;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+crantotl;
		document.getElementById("cAmountTotal").value = crantotl;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}else{
		var ccamount = document.getElementById("ccamount").value;
		ccamount=parseInt(ccamount);
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ccamount;
		document.getElementById("cAmountTotal").value = ccamount;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}
}

function getCothers(){
	var cconvay = document.getElementById("cAmountConveyance").value;
	var crantotl=document.getElementById("cAmountTotal").value;
	var ccamount = document.getElementById("ccamount").value;
	var cOther = document.getElementById("cAmountOther").value;
	cconvay=parseInt(cconvay);
	crantotl=parseInt(crantotl);
	ccamount=parseInt(ccamount);
	cOther=parseInt(cOther);
	if(crantotl>0 && cOther>0 && cconvay>0){
		crantotl=ccamount+cconvay;
		crantotl = crantotl+cOther;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+crantotl;
		document.getElementById("cAmountTotal").value = crantotl;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}else if(cOther>0){
	var ccamount = document.getElementById("ccamount").value;
	ccamount=parseInt(ccamount);
	ccamount = ccamount+cOther;
	var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ccamount;
	document.getElementById("cAmountTotal").value = ccamount;
	document.getElementById("ctotalAmountId").innerHTML = htm;
	}else if(crantotl>0 && cconvay>0 ){
		crantotl=ccamount+cconvay;
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+crantotl;
		document.getElementById("cAmountTotal").value = crantotl;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}else{
		var ccamount = document.getElementById("ccamount").value;
		ccamount=parseInt(ccamount);
		var htm= "<strong> Total Amount :</strong> &nbsp;&nbsp"+ccamount;
		document.getElementById("cAmountTotal").value = ccamount;
		document.getElementById("ctotalAmountId").innerHTML = htm;
	}
}

function guideDocumentAmount(){
	document.getElementById("method").value = "getGuidetotalAmount";
	document.phdStudentReminderationForm.submit();
}
function updateGuideAmount(req) {
    var pos;
	 var responseObj = req.responseText;
	 var empNo =responseObj.substring(0,pos);
	alert(empNo);
}
function updatePhdGuidesFees(){
	document.getElementById("method").value = "updatePhdGuidesFees";
	document.phdStudentReminderationForm.submit();
}
</script>
<html:form action="/PhdStudentReminderation">	
		<html:hidden property="method" styleId="method" value="studentDetailsSearch" />
		<html:hidden property="formName" value="phdStudentReminderationForm"/>
		<html:hidden property="gAmountTotal" styleId="gAmountTotal"/>  
		<html:hidden property="cAmountTotal" styleId="cAmountTotal"/>  
		<html:hidden property="ggamount" styleId="ggamount"/>  
		<html:hidden property="ccamount" styleId="ccamount"/>  
		<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.phd.Guide.remenderation"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.phd.Guide.remenderation"/></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
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
		<logic:empty property="guideDetailList" name="phdStudentReminderationForm">
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							 
	                     <tr>
	                       	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
		                         <tr >
		                           <td height="25" class="row-odd" align="center"><div align="right"><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                           		<html:text styleId="startDate" property="startDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
											new tcal( {
												// form name
												'formname' :'phdStudentReminderationForm',
												// input name
												'controlname' :'startDate'
											});
											</script>
		                           </td>
		                           <td height="25" class="row-odd" align="center"><div align="right"><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
		                           <td height="25" class="row-even" align="left">
		                            		<html:text styleId="endDate" property="endDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'phdStudentReminderationForm',
													// input name
													'controlname' :'endDate'
												});
											</script>
                                   <br></td>
		                         </tr>
	                       </table>
	                       </td>
	                            </tr>
                    							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
							<td width="45%" height="35">
							<div align="right">
									<html:button property="" styleClass="formbutton" value="Search" styleId="submitbutton" onclick="searchStudent()"></html:button>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Reset"
							onclick="resetFormFields()"></html:button></td>
						</tr>
						</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			<logic:present property="studentDetailsList" name="phdStudentReminderationForm">
                        	<tr>
										<td width="5" background="images/left.gif"></td>

										<td valign="top">

										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
											   <td width="6%" height="25" class="row-odd">
												<div align="center">Already Generated</div>
												</td>
											   <td width="10%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
											   <td width="15%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.course" /></div>
												</td>
												<td width="12%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.Guide" /></div>
												</td>
												<td width="12%" height="25" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.CoGuide" /></div>
												</td>
												<td width="10%" class="row-odd" align="center">
											   <bean:message key="knowledgepro.generate" /></td>
											</tr>
											<tr>
											<logic:iterate id="studentList" property="studentDetailsList" name="phdStudentReminderationForm" indexId="count">
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td class="row-even"><div align="center"><bean:write property="printornot" name="studentList" /></div></td>
													<td class="row-even"><div align="center"><bean:write property="registerNo" name="studentList" /></div></td>
													<td class="row-even"><div align="left"><bean:write property="studentName" name="studentList" /></div></td>
													<td class="row-even"><div align="left"><bean:write property="courseName" name="studentList" /></div></td>
													<td class="row-even"><div align="left"><bean:write property="guide"	name="studentList" /></div> </td>
													<td class="row-even"><div align="left"><bean:write property="coGuide" name="studentList" /></div></td>
													<td height="25" class="row-even" ><div align="center">
			        		                        <img src="images/print-icon.png" width="16" height="18" style="cursor:pointer" onclick="generateStudentDetails('<bean:write name="studentList" property="id"/>','<bean:write name="studentList" property="studentId"/>')"></div></td>
													
												</logic:iterate>
											</tr> 
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
								</logic:present>
			</logic:empty>
			<logic:notEmpty name="phdStudentReminderationForm" property="guideName">
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
						 <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.exam.blockUnblock.regNo"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="registerNo"/>
                         </span></td>						
			             </tr>
			             <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.fee.studentname"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="studentName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowldgepro.exam.upload.marks.Course"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="courseName"/>
                         </span></td>						
			             </tr>
                         </table>
                         </td>
							<td width="5" height="29" background="images/right.gif"></td>
                        </tr>
             </logic:notEmpty>
             <logic:empty name="phdStudentReminderationForm" property="guideName">
			<logic:notEmpty name="phdStudentReminderationForm" property="coGuideName">	
                        <tr>
                        <td width="5" background="images/left.gif"></td>
						<td valign="top">
						 <table width="100%" cellspacing="1" cellpadding="2">
						 <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.exam.blockUnblock.regNo"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="registerNo"/>
                         </span></td>						
			             </tr>
			             <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.fee.studentname"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="studentName"/>
                         </span></td>						
			             </tr>
			              <tr>
			             <td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowldgepro.exam.upload.marks.Course"/></div></td>
			             <td width="32%" height="25" class="row-even" ><label></label>
                         <span class="star">
                         <bean:write name="phdStudentReminderationForm" property="courseName"/>
                         </span></td>						
			             </tr>
                         </table>
                         </td>
							<td width="5" height="29" background="images/right.gif"></td>
                        </tr>
             </logic:notEmpty>
			</logic:empty>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			
			<logic:present property="guideName" name="phdStudentReminderationForm">
			
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					 <td  class="heading" align="left">
						<bean:message key="knowledgepro.phd.guideDetails" /> :- <bean:write name="phdStudentReminderationForm" property="guideName"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
				  
                        	<tr>
							<td width="5" background="images/left.gif"></td>
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
										
											<tr>
											   <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.document.name" /></div></td>
											   <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.SubmissionDate" /></div></td>
												<td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.guides.fee" /></div></td>
											</tr>
											<tr>
											<nested:iterate id="studentList" property="guideDetailList" name="phdStudentReminderationForm" indexId="count">
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td height="25" class="row-even"><div align="center"><bean:write property="documentName" name="studentList" /></div></td>
													<td height="25" class="row-even"><div align="center"><bean:write property="submissionDate" name="studentList" /></div></td>
													<td height="25" class="row-even"><div align="center"><nested:text property="gAmount" styleId='gamount' onchange="guideDocumentAmount()" /></div></td>
												</nested:iterate>
											</tr> 
										</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
						<tr>
							<td height="45" colspan="4" >
							<table width="100%" cellspacing="1" cellpadding="2">
				                  	<tr>
										<td width="35%"  align="right" class="row-odd">
										<bean:message key="knowledgepro.exam.ConveyanceCharge" />											
										</td>
										<td width="35%" class="row-even" align="left">
											<html:text property="gAmountConveyance" styleId="gAmountConveyance" onkeypress="return isNumberKey(event)" onkeyup="getGConveyanceAmount()"></html:text>	
										</td>
										<td class="row-even" align="left"></td>
								   	</tr>
								   	<tr>
										<td  align="right" class="row-odd">
										<div>Any Other Charges:</div>&nbsp;&nbsp;
										<div>(Description)</div>										
										</td>
										<td class="row-even" align="left">
										<html:text property="gAmountOther" styleId="gAmountOther" onkeypress="return isNumberKey(event)" onkeyup="gAmountOthers()"></html:text>&nbsp;&nbsp;											
											<input type="hidden" name="tempOtherCost" id="tempOtherCostId"/>
											<html:text property="gAmountdescription" styleId="gAmountdescription" size="50" maxlength="99"></html:text>	
										</td>
										<td class="row-even" align="left">
											 <div id="gtotalAmountId">
											 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Total Amount:</strong> &nbsp;&nbsp;<bean:write name="phdStudentReminderationForm" property="gAmountTotal" />
											 </div>
										 </td>
								   	</tr>
				                </table>
							</td>            
			    		</tr>
								</logic:present>
			
			
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
			
			<logic:present property="coGuideName" name="phdStudentReminderationForm">
			
			<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						   <tr>
					<td  class="heading" align="left">
						<bean:message key="knowledgepro.phd.CoguideDetails" /> :- <bean:write name="phdStudentReminderationForm" property="coGuideName"/>
					</td>
					</tr>
					</table>
					</td>
					<td width="10" valign="top" colspan="2" background="images/Tright_3_3.gif" class="news"></td>
				  </tr>	
				  
                        	<tr>
							<td background="images/left.gif"></td>
										<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
										
											<tr>
											   <td  height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.document.name" /></div></td>
											    <td  height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.SubmissionDate" /></div></td>
												<td  height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.guides.fee" /></div></td>
											</tr>
											<tr>
											<nested:iterate id="studentList" property="coGuideDetailList" name="phdStudentReminderationForm" indexId="count">
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td height="25" class="row-even"><div align="center"><bean:write property="documentName" name="studentList" /></div></td>
													<td height="25" class="row-even"><div align="center"><bean:write property="submissionDate" name="studentList" /></div></td>
													<td height="25" class="row-even"><div align="center"><nested:text property="cAmount" styleId="camount" onchange="guideDocumentAmount()"/></div></td>
												</nested:iterate>
											</tr> 
										</table>
										</td>
										<td  background="images/right.gif"></td>
									</tr>
									
									
									<tr>
						<td height="45" colspan="4" >
							<table width="100%" cellspacing="1" cellpadding="2">
				                  	<tr>
										<td width="35%"  align="right" class="row-odd">
										<bean:message key="knowledgepro.exam.ConveyanceCharge" />											
										</td>
										<td width="35%" class="row-even" align="left">
											<html:text property="cAmountConveyance" styleId="cAmountConveyance" onkeypress="return isNumberKey(event)" onkeyup="getCConveyanceAmount()"></html:text>	
										</td>
										<td class="row-even" align="left"></td>
								   	</tr>
								   	<tr>
										<td  align="right" class="row-odd">
										<div>Any Other Charges:</div>&nbsp;&nbsp;
										<div>(Description)</div>													
										</td>
										<td class="row-even" align="left">
										<html:text property="cAmountOther" styleId="cAmountOther" onkeypress="return isNumberKey(event)" onkeyup="getCothers()"></html:text>&nbsp;&nbsp;												
											<input type="hidden" name="tempOtherCost" id="tempOtherCostId"/>
											<html:text property="cAmountdescription" styleId="cAmountdescription" size="50" maxlength="99"></html:text>	
										</td>
										<td class="row-even" align="left">
											 <div id="ctotalAmountId">
											 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Total Amount:</strong> &nbsp;&nbsp;<bean:write name="phdStudentReminderationForm" property="cAmountTotal"/>
											 </div>
										 </td>
								   	</tr>
				                </table>
							</td>            
			    		</tr>
									
								</logic:present>
			
			
			   <logic:notEmpty property="guideName" name="phdStudentReminderationForm">   
                    <tr>
                        <td valign="top" background="images/Tright_03_03.gif"></td>
							<td height="50" align="center" colspan="6"> 
						<c:choose>
            		    <c:when test="${GuideDetails == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updatePhdGuidesFees()"><bean:message key="knowledgepro.phd.update.reprint"/></html:submit>&nbsp;&nbsp;
              		    <html:button property="" styleClass="formbutton" value="Close" onclick="closeGuideedit()"></html:button>
              		    </c:when>
              		    <c:otherwise>
                		<html:button property="" styleClass="formbutton" value="Save And Print" onclick="takePrint()"></html:button>&nbsp;&nbsp;
						<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"></html:button>	
              		   </c:otherwise>
                  	</c:choose>
							</td>
						<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                    </tr>
      
          </logic:notEmpty>
           <logic:empty property="guideName" name="phdStudentReminderationForm">   
             <logic:notEmpty property="coGuideName" name="phdStudentReminderationForm">   
                    <tr>
                        <td valign="top" background="images/Tright_03_03.gif"></td>
							<td height="50" align="center" colspan="6"> 
						<c:choose>
            		    <c:when test="${GuideDetails == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updatePhdGuidesFees()"><bean:message key="knowledgepro.phd.update.reprint"/></html:submit>&nbsp;&nbsp;
              		    <html:button property="" styleClass="formbutton" value="Close" onclick="closeGuideedit()"></html:button>
              		    </c:when>
              		    <c:otherwise>
                		<html:button property="" styleClass="formbutton" value="Save And Print" onclick="takePrint()"></html:button>&nbsp;&nbsp;
						<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()"></html:button>	
              		   </c:otherwise>
                  	</c:choose>
							</td>
						<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                    </tr>
      
          </logic:notEmpty>
          </logic:empty>
			
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
var print = "<c:out value='${phdStudentReminderationForm.print}'/>";
if(print.length != 0 && print == "true"){
	var url = "PhdStudentReminderation.do?method=printStudentReminders";
	myRef = window .open(url, "Guide Reminderation Advice", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}
</script>