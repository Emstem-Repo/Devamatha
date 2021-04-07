<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript">

function getRoomType(hostelId) {
	var hostelId =  document.getElementById("hostelId").value;
	getRoomsByHostel("roomTypeMap",hostelId,"roomId",updateRoomType);
}
function updateRoomType(req) {
	updateOptionsFromMap(req,"roomId","- Select -");
}
function getTotalSeats() {
	var hostelId =  document.getElementById("hostelId").value;
	var roomtypeId=document.getElementById("roomId").value;
	getTotalSestByRoomsAndHostel(hostelId,roomtypeId,updateTotalSeats);
}
function updateTotalSeats(req) {
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("option");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
			if(value[I].firstChild!=null){
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("totalNumOfSeatsAvail").value = temp;
			document.getElementById("totalSeats").value = temp;
			}
		}
		
	}
}
function deleteDetails(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "availableSeats.do?method=deleteAvailableSeats&id=" + id;
	}
}
function editAvailableSeats(id) {
	document.location.href = "availableSeats.do?method=editAvailableSeatsDetails&id=" + id;
}
function resetDetails(){
	 
	 resetFieldAndErrMsgs();
}

function resetErrorMsgs(){
	var roomId = document.getElementById("roomId").value;
	if (roomId != null && roomId.length != 0) {
		document.getElementById("roomId").value = null;
	}
	 resetFieldAndErrMsgs();
}
function resetErrorMsgs1(){
		document.getElementById("numOfAvailableSeats").value = null;
}
function cancelAction(){
	resetFieldAndErrMsgs();
	document.location.href = "LoginAction.do?method=loginAction";
}
function cancelAction1(){
	resetFieldAndErrMsgs();
	document.location.href = "availableSeats.do?method=initAvailableSeats";
}
</SCRIPT>

<html:form action="/availableSeats" method="POST">
<html:hidden property="formName" value="availableSeatsForm"/>
<html:hidden property="pageType" value="1"/>
	<c:choose>
		<c:when test="${openConnection == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateAvailableSeats" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="saveAvailableSeats" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="aknowledgepro.hostel.availableseats.display" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="aknowledgepro.hostel.availableseats.display" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
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
										<tr class="row-white">
                    <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
                    <td width="25%" class="row-even"> 
                    <% boolean disable2=false;%>
						<logic:equal value="true" name="availableSeatsForm" property="flag">
						<% disable2=true;%>
						</logic:equal>
                   		 <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="availableSeatsForm" property="academicYear"/>"/>
                		<html:select styleId="academicYear"  property="academicYear" name="availableSeatsForm" styleClass="combo" disabled='<%=disable2%>'>
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
						</html:select></td>
                    <td class="row-odd" width="25%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hostel.name.col"/>
						</div>
					</td>
					<td  class="row-even" width="25%">
					<% boolean disable=false;%>
						<logic:equal value="true" name="availableSeatsForm" property="flag">
						<% disable=true;%>
						</logic:equal>
						<input type="hidden" id="hId" name="hId" value="<bean:write name="availableSeatsForm" property="hostelId"/>" />
						<html:select property="hostelId" styleId="hostelId" disabled='<%=disable%>' styleClass="combo" onchange="getRoomType(this.value)">
							<html:option value="">--Select--</html:option>
							<logic:notEmpty property="hostelMap" name="availableSeatsForm">
						   		<html:optionsCollection property="hostelMap" label="value" value="key"/>
						   	</logic:notEmpty>
						</html:select> 
					</td>
                  </tr>
					 <tr>
                  <td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hoste.roomtype.col"/>
						</div>
					</td>     
            		<td width="35%" class="row-even"><input type="hidden" id="tempRTypeId" name="tempRTypeId" value="<bean:write name="availableSeatsForm" property="courseId"/>" />
						<% boolean disable1=false;%>
						<logic:equal value="true" name="availableSeatsForm" property="flag">
						<% disable1=true;%>
						</logic:equal>
						<html:select property="roomId" styleId="roomId"  disabled='<%=disable1%>' styleClass="combo" onchange="getTotalSeats()">
							<html:option value="">--Select--</html:option>
						 	<c:choose>
             			 		<c:when test="${roomTypeMap != null}">
             			 			<html:optionsCollection name="roomTypeMap" label="value" value="key" />
								</c:when>
								<c:otherwise>
									<c:if test="${availableSeatsForm.roomTypeMap!= null && availableSeatsForm.roomTypeMap!= ''}">
										<html:optionsCollection property="roomTypeMap" name="availableSeatsForm" label="value" value="key"/>
            		    	 		</c:if>
								</c:otherwise>
							 </c:choose>
			  			</html:select>
			  		</td>
			  		<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory"></span>
						<bean:message key="knowledgepro.hostel.totalnumberOfAvailable.seats"/>:
						</div>
					</td> 
					<td width="35%" class="row-even">
						<html:hidden property="totalSeats" name="availableSeatsForm" styleId="totalSeats"/>
						<html:text property="totalNumOfSeatsAvail" name="availableSeatsForm"  styleId="totalNumOfSeatsAvail" maxlength="5" size="5" disabled="true"></html:text>
			  		</td>   
			  		</tr>		
			  		<tr>
			  			<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hostel.numberOfAvailable.seats"/>:
						</div>
						</td> 
						<td width="35%" class="row-even">
						<html:text property="numOfAvailableSeats" styleId="numOfAvailableSeats" name="availableSeatsForm" maxlength="5" size="5" onkeypress="return isNumberKey(event)"></html:text>
			  			</td> 
			  		</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
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
									<div align="right"><c:choose>
										<c:when test="${openConnection == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
											
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%">
									<c:choose>
									<c:when test="${openConnection == 'edit'}">
										<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs1()"></html:button> 
									</c:when>
									<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>
									</c:otherwise>
									</c:choose>
									</td>

									<td width="53%">
									<c:choose>
									<c:when test="${openConnection == 'edit'}">
                                       <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction1()"/>
                                    </c:when>
                                    <c:otherwise>
                                       <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()"/>
                                    </c:otherwise>
                                    </c:choose>
                                       </td>
								</tr>
							</table>
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
											<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr >
                    <td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td width="15" height="30%" class="row-odd" align="center" >Academic Year</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Hostel Name</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Room Type</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Number Of Available Seats</td>
                    <td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
												 <logic:iterate id="CME" name="availableSeatsForm" property="availableSeatsList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="academicYear"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="hostelName"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="roomTypeName"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="numOfAvailableSeats"/></td>
                   		<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editAvailableSeats('<bean:write name="CME" property="id"/>')"> </div> </td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td  height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="academicYear"/></td>
                   		<td  height="25" class="row-white" align="center"><bean:write name="CME" property="hostelName"/></td>
                   		<td  height="25" class="row-white" align="center"><bean:write name="CME" property="roomTypeName"/></td>
                   		<td  height="25" class="row-white" align="center"><bean:write name="CME" property="numOfAvailableSeats"/></td>
               			<td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editAvailableSeats('<bean:write name="CME" property="id"/>')"> </div> </td>
                   		<td  height="25" class="row-white" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
											</table>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
