<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
function editAssignFees(id){
	document.location.href = "RoomType.do?method=editAssignFees&id="+ id;
}
function deleteAssignFees(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm) {
		document.location.href = "RoomType.do?method=deleteAssignFees&id="+ id;
	}
}
function reActivate(){
	document.location.href = "RoomType.do?method=reactivateAssignFees";
}
function BackToRoomType(){	
	document.location.href = "RoomType.do?method=initRoomType";
}
</script>
<html:form action="/RoomType">	
	<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateAssignFees" />
		</c:when>
		<c:otherwise>
	<html:hidden property="method" styleId="method" value="addAssignFees" />
	</c:otherwise>
	</c:choose>
	
	<html:hidden property="formName" value="roomTypeForm" />
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/>
			<span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.roomtype"/>
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> Assign Fees To <bean:message key="knowledgepro.roomtype"/></strong></td>

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
									<td class="row-odd" height="30">Hostel name : <nested:write name="roomTypeForm" property="hostelName"></nested:write></td>
									<td class="row-odd" height="30">Room Type : <nested:write name="roomTypeForm" property="roomType"></nested:write></td>
						</tr>
						<tr>
							<td colspan="2">
								<table width="100%">
								 <c:set var="temp" value="0" />
									<logic:notEmpty name="roomTypeForm" property="feeList">
												<nested:iterate id="feeList" name="roomTypeForm" property="feeList" indexId="count">
												<c:choose>
												<c:when test="${temp == 0}">
												<tr class="row-even">
												<td height="25" width="10%" align="center">
												 <input type="hidden" name="feeList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='feeList' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="feeList[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>" />
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
												</td>
												<td height="25"><nested:write name="feeList" property="name"/> </td>
												</tr>
												<c:set var="temp" value="1" />
												</c:when>
											<c:otherwise>
											    <tr class="row-white">
												<td height="25" width="10%" align="center">
												 <input type="hidden" name="feeList[<c:out value='${count}'/>].tempChecked"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='feeList' property='tempChecked'/>" />
																	<input
																	type="checkbox"
																	name="feeList[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>" />
																	<script type="text/javascript">
																		var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(studentId == "true") {
																			document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																	</script>
												</td>
												<td height="25"><nested:write name="feeList" property="name"/> </td>
												</tr>
												<c:set var="temp" value="0" />
												</c:otherwise>
												</c:choose>
												
												
												</nested:iterate>
											</logic:notEmpty>
											<tr></tr>
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
									<c:choose>
							<c:when test="${operation == 'edit'}">
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="Update"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:cancel value="Reset" styleClass="formbutton" ></html:cancel></td>
							</c:when>
							<c:otherwise>
								<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleClass="formbutton" value="submit"
										styleId="submitbutton">
									</html:submit>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%"><html:button property=""
							styleClass="formbutton" value="Back"
							onclick="BackToRoomType()"></html:button></td>
							</c:otherwise>
							</c:choose>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<logic:notEmpty name="roomTypeForm" property="roomTypeFeesList">	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
										   <td height="25" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.roomtype" /></div>
											</td>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
											key="knowledgepro.fee.feegroup" /></div>
											</td>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.delete" /></div>
											</td>
										</tr>
										
										<logic:iterate id="dList" name="roomTypeForm" property="roomTypeFeesList"
											indexId="count">
										<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center"><bean:write name="dList"
												property="roomTypeName" /></td>
											<td align="center"><bean:write name="dList"
												property="groupName" /></td>
											<td height="25" align="center">
											</td>
											<td height="25">
											<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteAssignFees('<bean:write name="dList" property="id" />')" /></div>
											</td>
											</tr>	
										</logic:iterate>
										
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
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
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>