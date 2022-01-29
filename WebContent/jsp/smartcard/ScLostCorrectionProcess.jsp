<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function changeStatus(count,value,id) {
	if(value=="Reject"){
		var rejectedReason=window.prompt("Please enter reason for Rejection");
		if(rejectedReason!=null){
			var newSmartCardNum = document.getElementById("newSmartCardNum_"+count).value;
			document.location.href = "ScLostCorrectionProcess.do?method=setChangeStatus&id="+ id+"&status2="+value+"&newSmartCardNum="+newSmartCardNum+"&reasonForRejection="+rejectedReason;
		}
		else
			document.location.href = "ScLostCorrectionProcess.do?method=ScLostCorrectionProcessSearch";
						
	}
	else{
		var newSmartCardNum = document.getElementById("newSmartCardNum_"+count).value;
		document.location.href = "ScLostCorrectionProcess.do?method=setChangeStatus&id="+ id+"&status2="+value+"&newSmartCardNum="+newSmartCardNum;
	}
}

function goBack(){
	document.location.href = "ScLostCorrectionProcess.do?method=initScLostCorrectionProcess";
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
                  }     
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }        
}

function getSelectedSmartCardFiles(){
	document.getElementById("method").value = "getSelectedSmartCardFiles";
}

function exportToExcel(){
	document.getElementById("method").value = "exportToExcel";
	document.ScLostCorrectionProcessForm.submit();
}
</script>

<html:form action="/ScLostCorrectionProcess" method="post">
<html:hidden property="formName" value="ScLostCorrectionProcessForm" />
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="addScLostCorrectionProcess"/>

<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory.vendor.bank" /><span class="Bredcrumbs">&gt;&gt;
			Head Quarters&gt;&gt;</span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.smartcard.lostcorrection.process" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="15" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
				<td valign="top" background="images/Tright_03_03.gif" height="30"></td>
						<td height="25" class="heading" align="left"><FONT color="red" size="2">
							The smart card files should be downloaded before changing the status to "PROCESSED"</FONT></td>
							
				<td class="news" valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
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
							<c:set var="temp" value="0" />
								
								<tr>
								<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Student">
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Approved">
										<td height="1" colspan="3" class="heading" align="left">
											Student : Approved Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Processed">
									<td height="1" colspan="3" class="heading" align="left">
										Student : Processed Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Rejected">
									<td height="1" colspan="3" class="heading" align="left">
										Student : Rejected Cards</td>
									</logic:equal>
								</logic:equal>
								<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Employee">
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Approved">
										<td height="1" colspan="3" class="heading" align="left">
											Employee : Approved Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Processed">
									<td height="1" colspan="3" class="heading" align="left">
											Employee : Processed Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Rejected">
									<td height="1" colspan="3" class="heading" align="left">
											Employee : Rejected Cards</td>
									</logic:equal>
								</logic:equal>
								</tr>
								<tr>
										<td width="2%" height="30" class="row-odd" align="center">Sl. No.</td>
										<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Student">
											<td width="7%" height="30" class="row-odd" align="center">Register No.</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Employee">
											<td width="7%" height="30" class="row-odd" align="center">Emp ID</td>
										</logic:equal>
										<td width="14%" height="30" class="row-odd" align="center">Name</td>
										<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Student">
											<td width="10%" height="30" class="row-odd" align="center">Course</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Employee">
											<td width="10%" height="30" class="row-odd" align="center">Department</td>
										</logic:equal>
										<td width="7%" height="30" class="row-odd" align="center">Old Smart Card No.</td>
										<td width="7%" height="30" class="row-odd" align="center">Bank Account No.</td>
										<td width="5%" height="30" class="row-odd" align="center">Type</td>
										<td width="6%" height="30" class="row-odd" align="center">Applied Date</td>
										<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Approved">
											<td width="12%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="6%" height="30" class="row-odd" align="center">Approved Date</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Processed">
											<td width="12%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="6%" height="30" class="row-odd" align="center">Processed Date</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Rejected">
											<td width="12%" height="30" class="row-odd" align="center">Reason for Rejection</td>
											<td width="6" height="30" class="row-odd" align="center">Rejected Date</td>
										</logic:equal>
										
										<td width="12%" height="30" class="row-odd" align="center">New SmartCard No.</td>
										<td width="8%" height="30" class="row-odd" align="center">Status</td>
											
										<td width="4%" height="30" class="row-odd" align="center" title="Click here to download all the Smart Card files together">Select
										<input type="checkbox" id="checkAll" onclick="selectAll(this)" height="35"/></td>
								</tr>
								<logic:notEmpty name="ScLostCorrectionProcessForm" property="scList">
									<nested:iterate id="scList" name="ScLostCorrectionProcessForm" property="scList" 
									type="com.kp.cms.to.smartcard.ScLostCorrectionProcessTO" indexId="count">
											<% String newSmartCardNum="newSmartCardNum_"+count;
												%>
										
											<tr>
											<td width="2%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Student">
												<td align="center" width="7%" class="row-even">
												<bean:write name="scList" property="regNo" /></td>
												<td align="left" width="7%" class="row-even">
												<bean:write name="scList" property="studentName" /></td>
												<td align="left" width="10%" class="row-even">
												<bean:write name="scList" property="studentCourse" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessForm" property="isEmployee" value="Employee">
												<td align="center" width="7%" class="row-even">
												<bean:write name="scList" property="empId" /></td>
												<td align="center" width="7%" class="row-even">
												<bean:write name="scList" property="employeeName" /></td>
												<td align="left" width="10%" class="row-even">
												<bean:write name="scList" property="employeeDepartment" /></td>
											</logic:equal>
											
											<td align="left" width="7%" class="row-even">
											<bean:write name="scList" property="oldSmartCardNo" /></td>
											<td align="left" width="7%" class="row-even">
											<bean:write name="scList" property="accNo" /></td>
											<td align="center" width="5%" class="row-even" >
											<bean:write name="scList" property="cardType" /></td>
											<td align="center" width="6%" class="row-even">
											<bean:write name="scList" property="appliedDate" /></td>
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Approved">
												<td align="left" width="12%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="6%" class="row-even">
												<bean:write name="scList" property="approvedDate" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Processed">
												<td align="left" width="12%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="6%" class="row-even">
												<bean:write name="scList" property="processedDate" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Rejected">
												<td align="left" width="12%" class="row-even">
												<bean:write name="scList" property="reasonForRejection" /></td>
												<td align="center" width="6%" class="row-even">
												<bean:write name="scList" property="rejectedDate" /></td>
											</logic:equal>
											
											<td align="center" width="12%" class="row-even">
											<input type="hidden" name="nm" id="nm" value='<nested:write name="scList" property="newSmartCardNum"/>' />
											<nested:text property="newSmartCardNum" styleClass="TextBox" styleId='<%=newSmartCardNum %>' size="20" maxlength="25"></nested:text>
											</td>
											<td align="center" width="8%" class="row-even">
											
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Approved">
											<select class="combo" name="status2" id="status2" onchange="changeStatus('<c:out value="${count}" />', this.value,'<bean:write name="scList" property="id" />')" >
												<option value="">- Select -</option>
												<option value="Process">Processed</option>
												<option value="Reject">Rejected</option>
											</select>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Processed">
											<select class="combo" name="status2" id="status2" onchange="changeStatus('<c:out value="${count}" />', this.value,'<bean:write name="scList" property="id" />')" >
												<option value="Processed">Processed</option>
												<option value="Reject">Rejected</option>
											</select>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessForm" property="status1" value="Rejected">
											<select class="combo" name="status2" id="status2" onchange="changeStatus('<c:out value="${count}" />', this.value,'<bean:write name="scList" property="id" />')" >
												<option value="Rejected">Rejected</option>
												<option value="Process">Processed</option>
											</select>
											</logic:equal>
											
											</td>
											
											<td width="4%" class="row-even" align="center">
											<div align="center">
											<input type="hidden" name="scList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='scList' property='tempChecked'/>" />
											<input type="checkbox" name="scList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																var checkedId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(checkedId == "on") {
																	document.getElementById("<c:out value='${count}'/>").checked = true;
																}
																else
																	document.getElementById("<c:out value='${count}'/>").checked = false;
																</script>
											</div>
											</td>
											
										</tr>
										<c:set var="temp" value="1" />
									</nested:iterate>
								</logic:notEmpty>
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
				<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
				<td><div></div></td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="44%" height="35">
							<div align="right">
							<html:button property="" styleClass="formbutton" value="Close" onclick="goBack()"></html:button>
							</div>
							</td>
							<td width="2%"></td>
							<td width="2%">
							<html:submit property="" styleClass="formbutton" value="Download Selected Smart Card Files"
										onclick="getSelectedSmartCardFiles()"></html:submit>
							</td>
							<td width="2%"></td>
							<td width="50%">
							<html:button property="" styleClass="formbutton" value="Export To Excel"
										onclick="exportToExcel()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
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
	
	<logic:notEmpty name="ScLostCorrectionProcessForm" property="downloadExcel">
	
	<bean:define id="downloadExcels" property="downloadExcel" name="ScLostCorrectionProcessForm"></bean:define>

	<logic:equal name="ScLostCorrectionProcessForm" property="downloadExcel" value="download">

	<SCRIPT type="text/javascript">	
	var url ="DownloadScLostCorrectionExcelAction.do";
	myRef = window.open(url,"downloadReport","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
			
	</SCRIPT>
	</logic:equal>
	</logic:notEmpty>
	
</html:form>

<script type="text/javascript">
var print = "<c:out value='${ScLostCorrectionProcessForm.print}'/>";
if(print.length != 0 && print == "true"){
	var url = "printSmartCardDataProcess.do?method=getStreamInfo";
	myRef = window .open(url, "SmartCardData", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}

</script>