<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function getCourses(programTypeID) {
	resetOption("course");
	getCoursesByProgramType1("coursesMap", programTypeID, "course", updateCourses);
}
function updateCourses(req) {
	updateOptionsFromMap(req, "course", "- Select -");
}

function getFeePaymentStatus(){
		document.getElementById("method").value = "getFeePaymentStatus";
		document.feePaymentStatusForm.submit();
		}
	function SendMailsFeePaymentStatus(){
		document.getElementById("method").value = "SendMailsFeePaymentStatus";
		document.feePaymentStatusForm.submit();
		}
	function Cancel(){
		document.location.href="PhdFeePaymentStatus.do?method=initPhdFeePaymentStatus";
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

</script>
<html:form action="/PhdFeePaymentStatus" method="post">
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="formName" value="feePaymentStatusForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.phd" /> <span class="Bredcrumbs">&gt;&gt;
				Fee Payment Status&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Fee Payment Status</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'><bean:message
						key="knowledgepro.mandatoryfields" /> </span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top">
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
									 <td height="25" class="row-odd" width="25%">
									<div align="right"><span class="Mandatory">*</span>
										<bean:message key="knowledgepro.cancelattendance.batches"/>:</div>
									</td>
									<td class="row-even" valign="top">
									<input type="hidden" id="tempbatch" name="batchApplied"
										value="<bean:write name="feePaymentStatusForm" property="batch"/>" />
									<html:select property="batch" styleId="batch" styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									
									<td height="25" class="row-odd" align="center">
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.phd.fee.Payment.For.academicYear"/>:</div></td>
		                            <td class="row-even" valign="top">
									<input type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="feePaymentStatusForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
								   </tr>
								<tr>
								<td height="25" class="row-odd"  width="25%"><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
			                	<td height="25" class="row-even" >
			                	<input type="hidden" id="currentProgramTypeId" value="<bean:write property="currentProgramType" name="feePaymentStatusForm" />"/>
			                				<html:select property="programTypeId" styleId="programType" onchange="getCourses(this.value),setProgramTypeName()" styleClass="comboMediumBig">
												<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
												<html:optionsCollection name="feePaymentStatusForm" property="programTypeList" label="programTypeName" value="programTypeId" />
											</html:select> 
			                				</td>
			                	<td height="25" class="row-odd" align="center"></td>
			                	<td height="25" class="row-even" ></td>
									</tr>
									<tr>
									<td  height="25" class="row-odd"  width="25%">
									<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
									</td>
									<td  height="25" class="row-even">
									<html:select property="selectedcourseId" styleId="course" styleClass="body" multiple="multiple" size="10" style="width:300px">
										<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="feePaymentStatusForm" property="courseList">
												<html:optionsCollection name="feePaymentStatusForm" property="courseList" label="name" value="id" />
										</logic:notEmpty>
									</html:select>
									 </td>
									 <td height="25" class="row-odd" align="center"></td>
			                	    <td height="25" class="row-even" ></td>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center"><html:submit property=""
								styleClass="formbutton" value="Search"
								onclick="getFeePaymentStatus()"></html:submit></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
		 		<logic:present name="feePaymentStatusForm" property="studentDetailsList">
					<tr>
						<td valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0">
							<tr>
								
								<td valign="top">
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
											    <td width="7%" class="row-odd" align="center">
											    SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
											    </td>
											    <td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.admin.course" /></div>
												</td>
												<td width="8%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendanceentry.regno" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.attendance.studentName" /></div>
												</td>
												<td width="7%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.feepays.billno" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.feePayment.billdate" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.feePayment.paidOn" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.reports.fees.paid" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.feepays.totalamount" /></div>
												</td>
												<td width="8%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.fee.concession" /></div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center"><bean:message
													key="knowledgepro.phd.feePayment.paidAmount" /></div>
												</td>
												</tr>
											<tr>
											<nested:iterate id="studentList" property="studentDetailsList" name="feePaymentStatusForm" indexId="count">
											      <c:choose>
													<c:when test="${count%2 == 0}">
													<tr class="row-even">
													</c:when>
													<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
					 								<td   align="center">
													<input type="hidden" name="studentDetailsList[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
													value="<nested:write name='studentList' property='tempChecked'/>" />
													
													<input type="hidden" name="studentDetailsList[<c:out value='${count}'/>].paid"	id="paid_<c:out value='${count}'/>"
													value="<nested:write name='studentList' property='paid'/>" />
														
													<input type="checkbox" name="studentDetailsList[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
													<script type="text/javascript">
													var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
													if(studentId == "on") {
													document.getElementById("<c:out value='${count}'/>").checked = true;
													}	
													var paid = document.getElementById("paid_<c:out value='${count}'/>").value;
													if(paid == "No") {
														document.getElementById("<c:out value='${count}'/>").checked = true;
														}
													</script>
													</td>
													<td > <div align="left"><bean:write property="courseName" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="registerNo" name="studentList" /></div></td>
													<td ><div align="left"><bean:write property="studentName" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="billNo" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="billDate" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="paid" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="feePaidOn" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="totalAmount" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="concession" name="studentList" /></div></td>
													<td ><div align="center"><bean:write property="paidAmout" name="studentList" /></div></td>
											</nested:iterate>
											</tr> 
											</table>
										</td>
										<td width="5" background="images/right.gif"></td>
									</tr>
									<tr>
										<td height="5"><img src="images/04.gif" width="5"
											height="5" /></td>
									<td background="images/05.gif"></td>
										 <td><img src="images/06.gif" /></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
							<td width="45%" height="35"><div align="center">
							<html:submit property="" styleClass="formbutton" onclick="SendMailsFeePaymentStatus()"> <bean:message key="knowledgepro.phd.feePayment.sendMail" />
							</html:submit>
							<html:button property="" styleClass="formbutton" onclick="Cancel()"> <bean:message key="knowledgepro.cancel" /></html:button></div>
							 </td>
						</tr>
             		    </table>
						</td><td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
				</logic:present>
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
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var batchId = document.getElementById("tempbatch").value;
	if (batchId != null && batchId.length != 0) {
		document.getElementById("batch").value = batchId;
	}
	var programType = document.getElementById("currentProgramTypeId").value;
	if (programType != null && programType.length != 0) {
		document.getElementById("programType").value = programType;
	}
</script>