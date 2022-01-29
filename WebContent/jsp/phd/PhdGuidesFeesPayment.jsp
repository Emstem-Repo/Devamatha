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

function searchGuidesFees(){
	document.getElementById("method").value = "searchGuidesFeesPayment";
	document.phdStudentReminderationForm.submit();
	}
function getCurrentDate(count) {
	var currentDate = new Date();
	var day = currentDate.getDate();
	var month = currentDate.getMonth() + 1 ;
	var year = currentDate.getFullYear() ;
    var date=(day + "/" + month + "/" + year);
	var check=document.getElementById("paidDate_"+count).value;
	if(check!=null && check!=""){
	   document.getElementById("paidDate_"+count).value=null;
	}else{
		document.getElementById("paidDate_"+count).value=date;
	}
}
function editGuideFees(voucher){
	document.location.href = "PhdStudentReminderation.do?method=editGuideFees&id="+voucher;
	}
function resetFormFields(){	
	document.getElementById("startDate").value = "";
	document.getElementById("endDate").value = "";
	resetErrMsgs();
}
function cancel(){
	document.location.href = "PhdStudentReminderation.do?method=initGuidesFeesPayment";
	}
</script>
<html:form action="/PhdStudentReminderation">	
		<html:hidden property="method" styleId="method" value="saveGuideFeesPayment" />
		<html:hidden property="formName" value="phdStudentReminderationForm"/>
		<html:hidden property="pageType" value="2" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.phd.Guide.feesPayment"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.phd.Guide.feesPayment"/></strong></td>

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
		                           <td height="25" class="row-odd" align="center"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.startdate"/>:</div></td>
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
		                           <td height="25" class="row-odd" align="center"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.feepays.enddate"/>:</div></td>
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
									<html:button property="" styleClass="formbutton" value="Search" onclick="searchGuidesFees()"> </html:button>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFormFields()"></html:button></td>
						</tr>
						</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				
			<logic:notEmpty property="guideFeesPatmentList" name="phdStudentReminderationForm">
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
		                         
											  <td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.fee.voucherno" /></div></td>
											  <td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="KnowledgePro.phd.generated.date" /></div></td>
											  <td width="15%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.Guide.coguide" /></div></td>
											  <td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admission.tc.details.feespaid" /></div></td>
											  <td width="15%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.phd.SynopsisDefense.feePaidDate" /></div></td>
											  <td width="15%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.sec.FeePaymentMode.required" /></div></td>
											  <td width="20%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.employee.remarks" /></div></td>
											  <td width="5%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/>/RePrint Voucher</div></td>
											</tr>
					 <nested:iterate id="guidesFees" name="phdStudentReminderationForm" property="guideFeesPatmentList" indexId="count">
				                	<tr>
												<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-white">
														</c:when>
														<c:otherwise>
															<tr class="row-even">
														</c:otherwise>
					 								</c:choose>
						<td height="20"  align="center"><div align="center"><bean:write property="voucherNo" name="guidesFees"/></div></td>
						<td height="20"  align="center"><div align="center"><bean:write property="generatedDate" name="guidesFees"/></div></td>
						<td height="20"  align="center"><div align="left"><bean:write property="guideName" name="guidesFees"/></div></td>
						
						<td height="20"  align="center">
						<input type="hidden" name="guideFeesPatmentList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
						value="<nested:write name='guidesFees' property='tempChecked'/>" />
														
						<input type="checkbox" name="guideFeesPatmentList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onchange="getCurrentDate('<c:out value='${count}'/>')"/>
						
						<script type="text/javascript">
						var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
						if(studentId == "on") {
						document.getElementById("<c:out value='${count}'/>").checked = true;
						}		
						</script>
						</td>
						<%String paidDate="paidDate_"+String.valueOf(count);%>
					    <td height="20" ><div align="center">									
								<nested:text styleId='<%=paidDate%>'
								property="paidDate"  size="10" maxlength="10" />
										<script language="JavaScript">
							 new tcal( {
								// form name
								'formname' :'phdStudentReminderationForm',
								// input name
								'controlname' :'<%=paidDate%>'
							});
						</script></div>
					</td>
					
						<td height="20"  align="center"> 
						<span class="star">
						<nested:select property="paidMode" styleId="paidMode" styleClass="combo" >
							       <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							       <html:option value="In Person"></html:option>
							       <html:option value="By Courier"></html:option>
							       <html:option value="By Post"></html:option>
						</nested:select></span></td>
						<td height="20"  align="center"><div align="left"><nested:text property="otherRemarks" maxlength="50" size="45"/></div></td>
						<td height="20" ><div align="center">
			        		<img src="images/updateButton.jpg" width="16" height="18" style="cursor:pointer" onclick="editGuideFees('<bean:write name="guidesFees" property="voucherNo"/>')"></div></td>
					</tr> 
					</nested:iterate>
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
                        <td valign="top" background="images/Tright_03_03.gif"></td>
							<td height="50" align="center" colspan="6"> 
                		    <html:submit property="" styleClass="formbutton"><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" onclick="cancel()"><bean:message key="knowledgepro.close"/></html:button>
							</td>
						<td height="50" valign="top" colspan="8" background="images/Tright_3_3.gif" class="news"></td>
                    </tr>
				</logic:notEmpty>
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