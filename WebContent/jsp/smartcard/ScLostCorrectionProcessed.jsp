<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">

function editRemarks(id, value) {
	var editedRemarks=window.prompt("Edit Remarks",value);
	if(editedRemarks!=null)
		document.location.href = "ScLostCorrectionProcessed.do?method=editRemarks&id="+id+"&editedRemarks="+editedRemarks;
}

function goBack(){
	document.location.href = "ScLostCorrectionProcessed.do?method=initScLostCorrectionProcessed";
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
function addScLostCorrectionProcessed() {
	document.getElementById("method").value = "addScLostCorrectionProcessed";
	document.ScLostCorrectionProcessedForm.submit();
}

</script>

<html:form action="/ScLostCorrectionProcessed" method="post">
<html:hidden property="formName" value="ScLostCorrectionProcessedForm"/>
<html:hidden property="pageType" value="2"/>
<html:hidden property="method" styleId="method" value="addScLostCorrectionProcessed"/>

<table width="100%" border="0">
		<tr>
		<td><span class="Bredcrumbs"><bean:message key="knowledgepro.smartcard"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.smartcard.lostcorrection.processed.smartcards" /></span></span></td>
		</tr>
		<tr>
		<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message key="knowledgepro.smartcard.lostcorrection.processed" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg" property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
								
								<tr>
								<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Student">
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Processed">
										<td height="1" colspan="3" class="heading" align="left">Student : Processed Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Received">
										<td height="1" colspan="3" class="heading" align="left">Student : Received Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Issued">
										<td height="1" colspan="3" class="heading" align="left">Student : Issued Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Applied">
										<td height="1" colspan="3" class="heading" align="left">Student : Applied Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Approved">
										<td height="1" colspan="3" class="heading" align="left">Student : Approved Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Rejected">
										<td height="1" colspan="3" class="heading" align="left">Student : Rejected Cards</td>
									</logic:equal>
									
								</logic:equal>
								<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Employee">
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Processed">
										<td height="1" colspan="3" class="heading" align="left">Employee : Processed Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Received">
										<td height="1" colspan="3" class="heading" align="left">Employee : Received Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Issued">
										<td height="1" colspan="3" class="heading" align="left">Employee : Issued Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Applied">
										<td height="1" colspan="3" class="heading" align="left">Employee : Applied Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Approved">
										<td height="1" colspan="3" class="heading" align="left">Employee : Approved Cards</td>
									</logic:equal>
									<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Rejected">
										<td height="1" colspan="3" class="heading" align="left">Employee : Rejected Cards</td>
									</logic:equal>
								</logic:equal>
								</tr>
								<tr>
										<td width="4%" height="30" class="row-odd" align="center">Sl. No.</td>
										<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Student">
											<td width="10%" height="30" class="row-odd" align="center">Register No.</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Employee">
											<td width="10%" height="30" class="row-odd" align="center">Emp. ID</td>
										</logic:equal>
										<td width="16%" height="30" class="row-odd" align="center">Name</td>
										<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Student">
											<td width="13%" height="30" class="row-odd" align="center">Course</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Employee">
											<td width="13%" height="30" class="row-odd" align="center">Department</td>
										</logic:equal>
										
										<td width="7%" height="30" class="row-odd" align="center">Type</td>	
										<td width="9%" height="30" class="row-odd" align="center">Applied Date</td>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Processed">
											<td width="15%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="9%" height="30" class="row-odd" align="center">Processed Date</td>
											<td width="14%" height="30" class="row-odd" align="center">New Smart Card No.</td>
											<td width="3%" height="30" class="row-odd" align="center">Select
											<input type="checkbox" id="checkAll" onclick="selectAll(this)" height="35"/></td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Received">
											<td width="15%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="9%" height="30" class="row-odd" align="center">Received Date</td>
											<td width="14%" height="30" class="row-odd" align="center">New Smart Card No.</td>
											<td width="3%" height="30" class="row-odd" align="center">Select
											<input type="checkbox" id="checkAll" onclick="selectAll(this)" height="35"/></td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Issued">
											<td width="15%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="9%" height="30" class="row-odd" align="center">Issued Date</td>
											<td width="14%" height="30" class="row-odd" align="center">New Smart Card No.</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Applied">
										<td width="14%" height="30" class="row-odd" align="center">Remarks</td>
										<td width="5%" height="30" class="row-odd" align="center">Edit Remarks</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Approved">
											<td width="15%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="9%" height="30" class="row-odd" align="center">Approved Date</td>
										</logic:equal>
										<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Rejected">
											<td width="15%" height="30" class="row-odd" align="center">Reason for Rejection</td>
											<td width="9%" height="30" class="row-odd" align="center">Rejected Date</td>
											<td width="14%" height="30" class="row-odd" align="center">Remarks</td>
											<td width="3%" height="30" class="row-odd" align="center">Select
											<input type="checkbox" id="checkAll" onclick="selectAll(this)" height="35"/></td>
										</logic:equal>
										
								</tr>
								
								<c:set var="temp" value="0" />
								<logic:notEmpty name="ScLostCorrectionProcessedForm" property="scList">
									<nested:iterate id="scList" name="ScLostCorrectionProcessedForm" property="scList" 
									type="com.kp.cms.to.smartcard.ScLostCorrectionProcessedTO" indexId="count">
									<% String reSendRemarks="reSendRemarks_"+count;
									%>
											<tr>
											<td width="4%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Student">
												<td align="center" width="10%" class="row-even">
												<bean:write name="scList" property="regNo" /></td>
												<td align="left" width="16%" class="row-even">
												<bean:write name="scList" property="studentName" /></td>
												<td align="left" width="13%" class="row-even">
												<bean:write name="scList" property="studentCourse" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="isEmployee" value="Employee">
												<td align="center" width="10%" class="row-even">
												<bean:write name="scList" property="empId" /></td>
												<td align="left" width="16%" class="row-even">
												<bean:write name="scList" property="employeeName" /></td>
												<td align="left" width="13%" class="row-even">
												<bean:write name="scList" property="employeeDepartment" /></td>
											</logic:equal>
											
											<td align="center" width="7%" class="row-even" >
											<bean:write name="scList" property="cardType" /></td>
											<td align="center" width="9%" class="row-even">
											<bean:write name="scList" property="appliedDate" /></td>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Processed">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="9%" class="row-even">
												<bean:write name="scList" property="processedDate" /></td>
												<td align="center" width="14%" class="row-even">
												<bean:write name="scList" property="newSmartCardNo" /></td>
												<td align="left" width="3%" class="row-even">
												<div align="center">
												<nested:checkbox property="checked" onclick="unCheckSelectAll()"></nested:checkbox>
												</div></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Approved">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="9%" class="row-even">
												<bean:write name="scList" property="approvedDate" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Received">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="9%" class="row-even">
												<bean:write name="scList" property="receivedDate" /></td>
												<td align="center" width="14%" class="row-even">
												<bean:write name="scList" property="newSmartCardNo" /></td>
												<td align="left" width="3%" class="row-even">
												<div align="center">
												<nested:checkbox property="checked" onclick="unCheckSelectAll()"></nested:checkbox>
												</div></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Issued">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="center" width="9%" class="row-even">
												<bean:write name="scList" property="issuedDate" /></td>
												<td align="center" width="14%" class="row-even">
												<bean:write name="scList" property="newSmartCardNo" /></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Rejected">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="reasonForRejection" /></td>
												<td align="center" width="9%" class="row-even">
												<bean:write name="scList" property="rejectedDate" /></td>
												<td align="center" width="14%" class="row-even">
												<input type="hidden" name="re" id="re" value='<nested:write name="scList" property="reSendRemarks"/>' />
												<nested:text property="reSendRemarks" styleClass="TextBox" styleId='<%=reSendRemarks %>' size="20" maxlength="25"></nested:text></td>
												<td align="left" width="3%" class="row-even">
												<div align="center">
												<nested:checkbox property="checked" onclick="unCheckSelectAll()"></nested:checkbox>
												</div></td>
											</logic:equal>
											<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Applied">
												<td align="left" width="15%" class="row-even">
												<bean:write name="scList" property="remarks" /></td>
												<td align="left" width="5%" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editRemarks('<nested:write name="scList" property="id" />', '<nested:write name="scList" property="remarks" />')" /></div>
												</td>
											</logic:equal>
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
							<td width="48%" height="35">
							<div align="right">
							<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Processed">
							<html:button property="" styleClass="formbutton" value="Received" onclick="addScLostCorrectionProcessed()"></html:button>
							</logic:equal>
							<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Approved">
							</logic:equal>
							<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Received">
							<html:button property="" styleClass="formbutton" value="Issued" onclick="addScLostCorrectionProcessed()"></html:button>
							</logic:equal>
							<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Issued">
							</logic:equal>
							<logic:equal name="ScLostCorrectionProcessedForm" property="status" value="Rejected">
							<html:button property="" styleClass="formbutton" value="Re-Send" onclick="addScLostCorrectionProcessed()"></html:button>
							</logic:equal>
							</div>
							</td>
							<td width="2%"></td>
							<td width="50%">
							<html:button property="" styleClass="formbutton" value="Close" onclick="goBack()"></html:button></td>
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
</html:form>