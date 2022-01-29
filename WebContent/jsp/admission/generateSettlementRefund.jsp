<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function generatesettlementFile() {
	document.location.href = "downloadGenerateSettlementOrRefund.do?fileType=settlement";
	myRef = window .open(url, "Download Files", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	
}
function generateRefundFile() {
	document.location.href= "downloadGenerateSettlementOrRefund.do?fileType=refund";
	myRef = window .open(url, "Download Files", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
	
}
function resetMessages() {
	document.location.href = "generateSettlementOrRefund.do?method=initGenerateSettlementOrRefundPgi";
}
function generateRefundFile() {
	document.getElementById("method").value="generateRefundFile";
	document.generateSettlementOrRefundForm.submit();
}
function downloadFile() {
	document.getElementById("method").value="downloadFile";
	document.generateSettlementOrRefundForm.submit();
}
function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
                  inputObj.value="on";
            }
    }
}

 

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
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
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }
}

</script>
<html:form action="/generateSettlementOrRefund" method="post">
	<html:hidden property="method" styleId="method" value="generateSettlementFiles" />
	<html:hidden property="formName" value="generateSettlementOrRefundForm" />
	<html:hidden property="refundFileName" name="generateSettlementOrRefundForm" styleId="refundFile"/>
	<html:hidden property="settlementFileName" name="generateSettlementOrRefundForm" styleId="settlementFile"/>
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.admission.generate.settlement.refund"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.admission.generate.settlement.refund"/></strong></td>

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
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.leavemodify.fromdate" /> :</div>
									</td>
									<td width="30%" class="row-even"><html:text styleId="fromDate" property="fromDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'generateSettlementOrRefundForm',
													// input name
													'controlname' :'fromDate'
												});
											</script>
		                           </td> 
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.attendance.leavemodify.todate" /> :</div>
									</td>
									<td width="30%" class="row-even"><html:text styleId="toDate" property="toDate" readonly="true" styleClass="TextBox"/>
											<script	language="JavaScript">
												new tcal( {
													// form name
													'formname' :'generateSettlementOrRefundForm',
													// input name
													'controlname' :'toDate'
												});
											</script></td>
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
					<table width="100%" border="0" cellspacing="2" cellpadding="3">
					<tr>
						<td><div align="center">
							<html:submit value="Generate Settlement File" styleClass="formbutton"></html:submit>&nbsp;&nbsp;&nbsp;
							<html:button property="" value="Generate Refund File" styleClass="formbutton" onclick="generateRefundFile()"></html:button>&nbsp;&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button></div>
						</td>
					</tr>
					<!--<tr>
						<logic:equal value="false" property="opensettementLink" name="generateSettlementOrRefundForm">
							<td ><div align="center">
										<a href="#" onclick="generatesettlementFile()">Generate Settlement File</a>
							&nbsp;&nbsp;<a href="#" onclick="generateRefundFile()">Generate Refund File</a></div>
							</td>
						</logic:equal>
						</tr>
					--></table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				
				<logic:notEmpty property="generateRefundPgiTo" name="generateSettlementOrRefundForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          				 <tr>
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
											<td width="20%" height="25" class="row-even" colspan="7">
											<div align="left"><bean:message  key="knowledgepro.refund.list" /></div>
											</td>
											</tr>
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> Select All
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Id</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Date</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Customer Id</div> 
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Amount</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Refund Generated</div>
												</td>
											</tr>
												<nested:iterate id="to" property="generateRefundPgiTo" name="generateSettlementOrRefundForm" indexId="count">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="10%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													<td width="15%" height="25">
													<div align="center">
													<nested:checkbox property="checked1"  onclick="unCheckSelectAll()" /> 
													</div>
													</td> 
													<td align="center" width="15%" height="25"><nested:write  name="to"
														 property="txnRefNo" /></td>
													<td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="date" /></td>
													<td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="candidateRefNo" /></td>
													<td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="amount"   />
													 </td>
													 <td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="refundGenerated"   />
													 </td>
													</nested:iterate>
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
							</tr>
							<tr>
								<td class="heading">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="right"><html:button property="" value="Generate" 
										styleClass="formbutton" onclick="downloadFile()"></html:button>&nbsp;&nbsp;
						    			</td>
									<td width="49%" height="35" align="left"><input type="button" 
									class="formbutton" value="Close" onclick="resetMessages()" /></td>
							
									</tr>
								</table>
								</td>
							</tr>
							
							
	          		</table>
	          	</td>
	          	<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	          </tr>
				</logic:notEmpty>
				<logic:notEmpty property="generateSettlementTo" name="generateSettlementOrRefundForm">		
					<tr>
						<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	          			<td>
	          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
	          				 <tr>
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
											<td width="20%" height="25" class="row-even" colspan="7">
											<div align="left"><bean:message key="knowledgepro.settlement.list" /></div>
											</td>
											</tr>
											<tr>
												<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> Select All
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Id</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Customer Id</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Amount</div> 
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Transaction Date</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Settlement Generated</div>
												</td>
											</tr>
												<nested:iterate id="to" property="generateSettlementTo" name="generateSettlementOrRefundForm" indexId="count">
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="5%" height="25">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													<td width="10%" height="25">
													<div align="center">
													<nested:checkbox property="checked1"  onclick="unCheckSelectAll()" /> 
													</div>
													</td> 
													<td align="center" width="20%" height="25"><nested:write  name="to"
														 property="txnRefNo" /></td>
													<td align="center" width="20%" height="25"><nested:write  name="to"  
														 property="candidateRefNo" /></td>
													<td align="center" width="15%" height="25"><nested:write  name="to"  
														 property="amount" /></td>
													<td align="center" width="10%" height="25"><nested:write  name="to"  
														 property="date"   />
													 </td>
													 <td align="center" width="20%" height="25"><nested:write  name="to"  
														 property="settlementGenerated"   />
													 </td>
													</nested:iterate>
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
							</tr>
							<tr>
								<td class="heading">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right"> <html:button property="" value="Generate" 
									styleClass="formbutton" onclick="downloadFile()"></html:button>&nbsp;&nbsp;
					    			</td>
									<td width="49%" height="35" align="left"><input type="button" 
									class="formbutton" value="Close" onclick="resetMessages()" /></td>
								</tr>
								</table>
								</td>
							</tr>
	          		</table>
	          	</td>
	          	<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	          </tr>
				</logic:notEmpty>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

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
<script language="JavaScript" >
	var print = "<c:out value='${generateSettlementOrRefundForm.settlementDownloadFile}'/>";
	if(print.length != 0 && print == "true"){
		hook=false;
		document.location.href = "downloadGenerateSettlementOrRefund.do?fileType=settlement";
		myRef = window .open(url, "Download Files", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
		}
	var print = "<c:out value='${generateSettlementOrRefundForm.refundDownloadFile}'/>";
	if(print.length != 0 && print == "true"){
		hook=false;
		document.location.href= "downloadGenerateSettlementOrRefund.do?fileType=refund";
		myRef = window .open(url, "Download Files", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
		}
</script>

