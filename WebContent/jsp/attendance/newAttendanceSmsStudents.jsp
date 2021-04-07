<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<script type="text/javascript">
function getClasses(year) {
	getClassesByYear("classMap", year, "classes", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "classes", " - Select -");
}

	function cancelEvent() {
		document.location.href = "NewAttendanceSMS.do?method=initNewAttendanceSms";
	}
	function validateCheckBox() {
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
		    inputObj = inputs[count1];
		    var type = inputObj.getAttribute("type");
		   	if (type == 'checkbox') {
		   		if(inputObj.checked){
		   			checkBoxselectedCount++;
			   	}
			}
	    }  
	    document.getElementById("err").innerHTML = "Number of Candidates selected:"+checkBoxselectedCount;          
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/NewAttendanceSMS">
	
	<html:hidden property="method" styleId="method" value="sendSms" />
	<html:hidden property="formName" value="newAttendanceSmsForm" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td class="Bredcrumbs">
				<bean:message key="knowledgepro.admin" />
			 <span class="Bredcrumbs">&gt;&gt; Send SMS &gt;&gt;</span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Send SMS</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="6">Mark Student if you don't want to Send Message  
							<div align="right"></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
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
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td class="row-odd" align="center" height="25">Mark</td>
									<%-- <td height="25" class="row-odd" align="center">Register Number</td>--%>
									<td class="row-odd" align="center">Student Name</td>
									<td height="25" class="row-odd" align="center">Roll Number</td>
									<td class="row-odd" align="center">		Class	</td>
									
									<td class="row-odd" align="center">
									<div align="center">Periods</div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:iterate id = "student" name = "newAttendanceSmsForm" property="studentList"
									type="com.kp.cms.to.admin.StudentTO" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="5%" class="row-even" align="center">
													 <input
															type="hidden"
															name="studentList[<c:out value='${count}'/>].tempChecked"
															id="hidden_<c:out value='${count}'/>"/>
													<input
															type="checkbox"
															name="studentList[<c:out value='${count}'/>].checked"
															id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
												</td>
												<%--<td width="15%" height="25" class="row-even" align="center"><bean:write
													name="student" property="registerNo"  /></td> --%>
												<td width="40%" class="row-even" align="center"><bean:write
													name="student" property="studentName" /></td>
												<td width="10%" height="25" class="row-even" align="center"><bean:write
													name="student" property="rollNo"  /></td>
												<td width="15%" height="25" class="row-even" align="center">
													<bean:write
													name="student" property="className" />
												</td>
												<td width="25%" height="25" class="row-even" align="center">
													<bean:write
													name="student" property="periodName" />
												</td>
											</tr>
											<c:set var="temp" value="1" />
											<script type="text/javascript">
												var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
												if(studentId == "true") {
													document.getElementById("<c:out value='${count}'/>").checked = true;
												}		
											</script>
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td class="row-even" align="center">
													<input
															type="hidden"
															name="studentList[<c:out value='${count}'/>].tempChecked"
															id="hidden_<c:out value='${count}'/>"/>
													<input
															type="checkbox"
															name="studentList[<c:out value='${count}'/>].checked"
															id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
												</td>
											<%-- 	<td  height="25" class="row-even" align="center"><bean:write
													name="student" property="registerNo"  /></td>--%>
												<td  class="row-even" align="center"><bean:write
													name="student" property="studentName" /></td>
												<td  height="25" class="row-even" align="center"><bean:write
													name="student" property="rollNo"  /></td>
												<td  height="25" class="row-even" align="center">
													<bean:write
													name="student" property="className" />
												</td>
												<td  height="25" class="row-even" align="center">
													<bean:write
													name="student" property="periodName" />
												</td>
												
											</tr>
											<c:set var="temp" value="0" />
											<script type="text/javascript">
												var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
												if(studentId == "true") {
													document.getElementById("<c:out value='${count}'/>").checked = true;
												}		
											</script>
										</c:otherwise>
									</c:choose>
								</logic:iterate>

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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right">										
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property=""
										styleClass="formbutton" value="Cancel" onclick="cancelEvent()"></html:button></td>
								</tr>
							</table>
							</td>
						</tr>

				
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
              <td  valign="top" background="images/Tright_03_03.gif"></td>
              <td height="20" valign="top" ></td>
              <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
            </tr>
            <tr>
              <td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
              <td width="100%" background="images/TcenterD.gif"></td>
              <td><img src="images/Tright_02.gif" width="9" height="29" /></td>
            </tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>