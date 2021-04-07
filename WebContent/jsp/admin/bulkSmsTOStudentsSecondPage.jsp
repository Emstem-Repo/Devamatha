<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<style>
.message{
    background-size: 40px 40px;
    background-image: linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
                        transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
                        transparent 75%, transparent);                                      
     box-shadow: inset 0 -1px 0 rgba(255,255,255,.4);
     width: 95%;
     border: 1px solid;
     color: #fff;
     margin:10px;
     padding: 15px;
     
     text-shadow: 0 1px 0 rgba(0,0,0,.5);
     animation: animate-bg 5s linear infinite;
}


.success{
     background-color: #61b832;
     border-color: #55a12c;
}

.message h3{
     margin: 0 0 5px 0; 
     opacity: 1;
    filter: alpha(opacity=100);                                                 
}

.message p{
     margin: 0;                                                  
}

@keyframes animate-bg {
    from {
        background-position: 0 0;
    }
    to {
       background-position: -80px 0;
    }
}

</style>
<script type="text/javascript">

	function selectAll(obj) {
		var value = obj.checked;
		var inputs = document.getElementsByTagName("input");
		var inputObj;
		var checkBoxselectedCount = 0;
		for ( var count1 = 0; count1 < inputs.length; count1++) {
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
		for ( var count1 = 0; count1 < inputs.length; count1++) {
			inputObj = inputs[count1];
			var type = inputObj.getAttribute("type");
			if (type == 'checkbox' && inputObj.id != "checkAll") {
				checkBoxOthersCount++;
				if (inputObj.checked) {
					checkBoxOthersSelectedCount++;
				}
			}
		}
		if (checkBoxOthersCount != checkBoxOthersSelectedCount) {
			document.getElementById("checkAll").checked = false;
		} else {
			document.getElementById("checkAll").checked = true;
		}
	}
	function cancel(){
		document.location.href="bulkSmsToStudent.do?method=initBulkSMStoStudent";
	}
</script>

<html:form action="/bulkSmsToStudent" method="post">
	<html:hidden property="formName" value="bulkSmsToStudentForm" />
	<html:hidden property="method" styleId="method"
		value="sendBulkSMStoStudent" />
	<html:hidden property="pageType" value="2" />
	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.admin" /><span
				class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.admin.sms.marks.student.lable" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admin.sms.marks.student.lable" /></strong></div>
					</td>
					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="center">
					<table width="100%" height="185" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="20" colspan="6" align="left">
							<div align="right" style="color: red"><span
								class='MandatoryMark'>* Mandatory fields</span></div>
							<div id="err"
								style="color: red; font-family: arial; font-size: 11px;"></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td height="35" colspan="6" class="body">
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
									<!--  main table starts here -->	
										<tr>
										<td height="25" align="right" class="row-odd"><div align="right"><bean:message
												key="knowledgepro.interview.Year" /></div></td>
										<td height="25" align="left" class="row-even"><div align="left">
										<bean:write property="academicYear" name="bulkSmsToStudentForm"/>
										</div></td>
										<td height="25" align="right" class="row-odd"><div align="right"><bean:message
												key="knowledgepro.attendance.class.col" /></div></td>
										<td height="25" align="left" class="row-even"><div align="left">
										<bean:write name="bulkSmsToStudentForm" property="classNamesForDisplay"/>
										</div></td>
										
										</tr>
										<tr>
										
										<td height="25" align="right" class="row-odd"><div align="right">
										<bean:message
												key="knowledgepro.admin.bulk.sms.student.Message.label" />
										</div></td>
										<td height="25" align="left" class="row-even" colspan="3"><div align="left" class="success message"><h3>
										Dear Parent,
										<bean:write name="bulkSmsToStudentForm" property="smsMessage"/> from Principal  VET PUC</h3>
										</div></td>
										
										</tr>
										
										
								    <!-- main table end here -->
									</table>
									

									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							<br>
							
							<div class="heading" align="left">Student List</div>
							
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
									<!--  list table starts here -->	
									<tr>
									<td height="25" align="center" class="row-odd" width="100"><div align="center">Select All
									<input	type="checkbox" name="allstudent" name="checkbox2" id="checkAll"  onclick="selectAll(this)"/>
									</div></td>
									<td height="25" align="center" class="row-odd" ><div align="left">Student Name</div></td>
									<td height="25" align="center" class="row-odd"><div align="left">Register Number</div></td>
									
									</tr>		
									<nested:iterate name="bulkSmsToStudentForm" id="stu"  property="studentList"     indexId="count">
									 <tr>
									 <td height="25" align="center" class="row-even">
									  
									 	<input
							type="checkbox"
							name="studentList[<c:out value='${count}'/>].checked"
							id="check_<c:out value='${count}'/>"  onclick="unCheckSelectAll()"/>
									 </td>
									 <td height="25" align="left" class="row-even"> <nested:write property="studentName" name="stu"/></td>
									<td height="25" align="left" class="row-even"> <nested:write property="registerNo"  name="stu"/></td>
									
									 </tr>
									 
									 </nested:iterate>	
										
										
								    <!-- list table end here -->
									</table>
									

									</td>
									<td width="5" height="30" background="images/right.gif"></td>
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
							<td height="35" colspan="6" class="body">
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="45%">
									<div align="right"><html:submit property=""
										styleClass="formbutton" value="Send"></html:submit></div>
									</td>
									<td width="2%"></td>
									<td width="53%" height="45" align="left"><html:button
										property="" styleClass="formbutton" value="Cancel"
										onclick="cancel()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>