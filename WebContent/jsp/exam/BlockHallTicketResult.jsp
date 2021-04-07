<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
	function goToFirstPage(method) {
		document.location.href = "blockHallTicketProcess.do?method="+method;
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
		            if(!inputObj.disabled)
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
<html:form action="/blockHallTicketProcess" method="POST" enctype="multipart/form-data">
	<html:hidden property="formName" value="blockHallTicketProcessForm"	styleId="formName" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" styleId="method"	value="saveBlockHallTickets" />
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam	&gt;&gt; Block Hall Ticket Process&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Block Hall Ticket Process For Exam</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td height="25" class="row-odd">
									<div align="right">Exam Name :</div>
									</td>
									<td height="25" class="row-even" colspan="3"><bean:write
										property="examName" name="blockHallTicketProcessForm"></bean:write></td>
								</tr>
								<tr>
									<td width="23%" height="25" class="row-odd">
									<div align="right">Reason :</div>
									</td>
									<td width="23%" height="25" class="row-even"><bean:write
										property="comments" name="blockHallTicketProcessForm"></bean:write></td>
									<td width="28%" class="row-odd">
									<div align="right">Required Percentage :</div>
									</td>
									<td width="26%" class="row-even">
									<bean:write property="reqPercentage" name="blockHallTicketProcessForm"></bean:write>
									</td>
								</tr>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr>
									<td class="row-odd">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd">Student Name</td>
									<td height="25" class="row-odd">Register No.</td>
									<td class="row-odd">Class Name</td>
									<td class="row-odd">Percentage</td>
									<td class="row-odd">Remarks</td>
									<td class="row-odd"><input type="checkbox" id="checkAll" onclick="selectAll(this)"/> select</td>
									<td> </td>
								</tr>
								<logic:notEmpty name="blockHallTicketProcessForm" property="studentList">
								<nested:iterate property="studentList" id="examMarksEntryStudentTO"	indexId="count">
									<tr>
										<td class="row-even" align="center"><c:out value="${count+1}"></c:out> </td>
										<td class="row-even" align="center"><nested:write property="studentName" /></td>
										<td class="row-even" align="center"><nested:write property="registerNumber" /></td>
										<td class="row-even" align="center"><nested:write property="className" /> </td>
										<td class="row-even" align="center"><nested:write property="percentage" /> </td>
										<td class="row-even" align="center">
											<nested:textarea property="remarks" cols="30" rows="2"></nested:textarea>
										</td>
										<td class="row-even" align="center">
										<input
																	type="hidden"
																	name="studentList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='examMarksEntryStudentTO' property='tempChecked'/>" />
																	<nested:checkbox property="checked" styleId="<%=String.valueOf(count) %>"></nested:checkbox>
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
										</td>
									</tr>
								</nested:iterate>
								</logic:notEmpty>
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
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
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
							<input type="button" class="formbutton" value="Cancel" onclick="goToFirstPage('initBlockHallTicketProcess')" />
								</td>
							<td width="2%"></td>
							<td width="46%">&nbsp;</td>
						</tr>
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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