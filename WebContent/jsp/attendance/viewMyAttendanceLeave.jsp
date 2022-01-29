<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript"><!--
	function searchAttendanceType() {
		document.getElementById("method").value = "searchViewMyAttnLeave";
		document.viewMyAttendanceLeaveForm.submit();
	}
	function resetFields(){
		document.getElementById("attendancedate").value = " ";
		resetErrMsgs();
	}
	function cancel(){
		document.location.href = "LoginAction.do?method=initLoginAction";
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
	function printWindow(){
		document.getElementById("method").value = "getDetailsForPrint";
		document.viewMyAttendanceLeaveForm.submit();
	}
</script>
<html:form action="/viewMyAttendanceLeave" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="viewMyAttendanceLeaveForm" />
	<html:hidden property="id" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /><span
				class="Bredcrumbs">&gt;&gt;View My Attendance Slip&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">View My Attendance Slip</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="news">
					<div align="right"><span class='MandatoryMark'> <bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
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
								 <td class="row-odd" width="40%"><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendanceentry.date"/>:</div></td>
								<td class="row-even" width="60%"><html:text name="viewMyAttendanceLeaveForm" styleId="attendancedate" property="attendanceLeaveDate" styleClass="TextBox"/>
							                  <script language="JavaScript">
														new tcal ({
															// form name
															'formname': 'viewMyAttendanceLeaveForm',
															// input name
															'controlname': 'attendanceLeaveDate'
														});</script>
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
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="35" align="center">
							
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="searchAttendanceType()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
								<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFields()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
								<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="cancel()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<logic:notEmpty name="viewMyAttendanceLeaveForm"
									property="attendanceLeaveMap">
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5"></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td height="54" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td align="center" width="5%" class="row-odd">Sl.No</td>
									<td width="12%" class="row-odd" align="center">
									SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> </td>
									<td align="center" width="15%" class="row-odd">Class</td>
									<td align="center" width="30%" height="25" class="row-odd"> Subject</td>
									<td align="center" width="20%" class="row-odd">Period</td>
									<td align="center" width="25%" class="row-odd">Absentees</td>
								</tr>
								<c:set var="temp" value="0" />
								
									<nested:iterate name="viewMyAttendanceLeaveForm" id="att"
										property="attendanceLeaveMap" indexId="count">
										
										<c:choose>
											<c:when test="${temp == 0}">
												<tr>
													<td width="6%" height="25" class="row-even" ><div align="center">
													<c:out value="${count + 1}"/></div></td>
													<td align="center"  class="row-even">
													<nested:checkbox property="checked" onclick="unCheckSelectAll()"/>
													</td>
													<td align="center"  class="row-even"><nested:write
														 property="className" /></td>
													<td align="center"  class="row-even"><nested:write
														property="subjectName" /></td>
													<td align="center"  class="row-even"><nested:write
														 property="periodName" /></td>
													<td align="center"  class="row-even"><nested:write
														property="studentRegNo" /></td>
												</tr>
												<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td width="6%" height="25" class="row-white" ><div align="center">
													<c:out value="${count + 1}"/></div></td>
													<td align="center"  class="row-white">
													<nested:checkbox property="checked" onclick="unCheckSelectAll()"/>
													</td>
													<td align="center"  class="row-white"><nested:write
														 property="className" /></td>
													<td align="center"  class="row-white"><nested:write
														property="subjectName" /></td>
													<td align="center"  class="row-white"><nested:write
														 property="periodName" /></td>
													<td align="center"  class="row-white"><nested:write
														 property="studentRegNo" /></td>
												</tr>
												<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
										</nested:iterate>
									
							</table>
							</td>
							<td background="images/right.gif" width="5" height="54"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5"></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif"></td>
						</tr>
					</table></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="center" height="35">
									<html:button property="" styleClass="formbutton" onclick="printWindow()" value="Print" ></html:button> </td>
							</tr>
					</table></td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr></logic:notEmpty>
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
	var print = "<c:out value='${viewMyAttendanceLeaveForm.printPage}'/>";
	if(print.length != 0 && print == "true"){
		var url= "viewMyAttendanceLeave.do?method=printMyAttendanceLeave";
		myRef = window.open(url, " ",
				"left=10,top=10,width=800,height=900,toolbar=0,resizable=1,scrollbars=1");
		}
</script>