<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
function getAvailableRoomsOfWorkLocation(){
	var workLocationId = document.getElementById("workLocatId").value;
		document.getElementById("workLocatId").value = workLocationId;
		document.getElementById("method").value = "getAvailableRoomsOfWorkLocation";
		document.examRoomAvailabilityForm.submit();	
	
}
function saveSelectedRooms(){
	document.getElementById("method").value = "saveSelectedRoomDetails";
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
function cancelPage(){
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/examRoomAvailability" method="post">
	<html:hidden property="method" styleId="method" value=""/>
	<html:hidden property="formName" value="examRoomAvailabilityForm" />
	<html:hidden property="pageType" value="1" />
	<table width="99%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Exam Allotment <span class="Bredcrumbs">&gt;&gt;Exam Room Availability&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Exam Room Availability</td>
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
									<td width="40%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Campus</div>
									</td>
									
									<td width="60%" class="row-even">
									<input type="hidden" id="tempWorkLocatId" name="tempWorkLocatId"
										value='<bean:write name="examRoomAvailabilityForm" property="workLocatId"/>' />
									<html:select property="workLocatId" styleClass="comboLarge" styleId="workLocatId">
										<html:option value="">
											-<bean:message key="knowledgepro.select" />-</html:option>
										<html:optionsCollection name="examRoomAvailabilityForm" property="workLocationMap" value="key" label="value"/>
									</html:select></td>
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
							<td height="35" align="center"><html:button property=""
								styleClass="formbutton" value="Submit"
								onclick="getAvailableRoomsOfWorkLocation()"></html:button></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<logic:notEmpty name="examRoomAvailabilityForm" property="roomMasterTO">
				<c:set var="count1" value="0"  />
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
												<td width="5%" class="row-odd">
												<div align="center" >Sl.No</div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center">Block</div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center">Floor</div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center">Room No</div>
												</td>
												<td width="10%" class="row-odd">
												<div align="center">MidSem Capacity</div>
												</td>
												<td width="15%" class="row-odd">
												<div align="center">EndSem Capacity</div>
												</td>
												<td width="5%" class="row-odd">
												SelectAll<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> 
												
												</td>
											</tr>
											<tr>
											<nested:iterate id="list" property="roomMasterTO" name="examRoomAvailabilityForm" indexId="count">
												
												<c:if test="${count < examRoomAvailabilityForm.halfLength}">
													 <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
					 								</c:choose>
													<td width="5%" height="25" class="row-even" align="center">
														<div align="center"><c:out value="${count1+1}" /></div>
														</td>
													<td class="row-even">
														<div align="center"><bean:write property="blockName" name="list" /></div>
														</td>
														<td class="row-even">
														<div align="center"><bean:write property="floor" name="list" /></div>
														</td>
														<td class="row-even">
														<div align="center"><bean:write property="roomNo" name="list" /></div>
														</td>
														<td class="row-even">
														<div align="center"><bean:write property="midSemCapacity" name="list" /></div>
														</td>
														<td class="row-even">
														<div align="center"><bean:write property="endSemCapacity" name="list" /></div>
														</td>
														<td class="row-even">
														<input type="hidden" name="roomMasterTO[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='list' property='tempChecked'/>" />
														
														<input type="checkbox" name="roomMasterTO[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>"  onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>
																</td><c:set var="count1" value="${count1 + 1}" />
																</c:if>
																
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
								<td valign="top" background="images/Tright_03_03.gif"></td>
								<td valign="top">
								<table width="100%" border="0" align="left" cellpadding="0"
									cellspacing="0">
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
											<tr style="height:25px">
											<td width="5%" class="row-odd" height="20%">
														<div align="center" >Sl.No</div>
														</td>
												<td width="15%" class="row-odd" >
														<div align="center" >Block</div>
														</td>
														<td width="10%" class="row-odd" height="20%">
														<div align="center" >Floor</div>
														</td>
														<td width="15%" class="row-odd" height="20%">
														<div align="center" >Room No</div>
														</td>
														<td width="15%" class="row-odd">
														<div align="center">MidSem Capacity</div>
														</td>
														<td width="10%" class="row-odd">
														<div align="center">EndSem Capacity</div>
														</td>
														 <td width="5%" class="row-odd" height="20%">
														 <div align="center" >Select</div>
															
														</td>
													</tr>
												
										<c:set var="c" value="0"/>
											<nested:iterate id="list" property="roomMasterTO"
														name="examRoomAvailabilityForm" indexId="count">		
												<c:set var="c" value="${c + 1}"/>											
											  <c:if test="${count >= examRoomAvailabilityForm.halfLength}">
													    <c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
															</c:when>
														<c:otherwise>
															<tr class="row-white">
															</c:otherwise>
					 										</c:choose>
					 											<td width="5%" height="25" class="row-even" align="center">
																<div align="center"><c:out value="${count1+1}" /></div>
																	</td>
																<td class="row-even">
																<div align="center"><bean:write
																property="blockName" name="list" /></div>
																</td>
																<td class="row-even">
																<div align="center"><bean:write
																	property="floor" name="list" /></div>
																</td>
																<td class="row-even">
																<div align="center"><bean:write property="roomNo" name="list" /></div>
																</td>
																<td class="row-even">
														<div align="center"><bean:write property="midSemCapacity" name="list" /></div>
														</td>
														<td class="row-even">
														<div align="center"><bean:write property="endSemCapacity" name="list" /></div>
														</td>
																<td class="row-even">
																<input type="hidden" name="roomMasterTO[<c:out value='${count}'/>].tempChecked"	id="hidden_<c:out value='${count}'/>"
																value="<nested:write name='list' property='tempChecked'/>" />
																<input type="checkbox" name="roomMasterTO[<c:out value='${count}'/>].checked" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()" />
																<script type="text/javascript">
																
																var studentId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																if(studentId == "on") {
																document.getElementById("<c:out value='${count}'/>").checked = true;
																		}		
																</script>	
																</td>
																<c:set var="count1" value="${count1 + 1}" />
															</c:if>
															
														<!--  </tr> -->
													</nested:iterate>
													 <c:if test="${(c % 2) != 0}" >
                      									<!--<tr  class="row-white">
                       									 <td width="193"  >&nbsp;</td>
                       									<td width="212" >&nbsp;</td>
                        								<td height="25" align="center" >&nbsp;
                      									</td>
                     								 </tr>
                      								--></c:if>
												</table>
												</td>
												<td width="5" background="images/right.gif"></td>
											</tr>
											<tr>
												<td height="5"><img src="images/04.gif" width="5"
													height="5" /></td>
												<td background="images/05.gif"></td>
												 <td> <img src="images/06.gif" /></td>
											</tr>
										</table>
										</td>
									</tr>
									</table>
								</td>
							</tr>
						</table>
						</td><td valign="top" background="images/Tright_3_3.gif"></td>
						</tr>
						<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td class="heading">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
								<c:when test="${RoomAvailabilityOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateSubjectGroupEntry()"></html:button>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton" value="Submit"
										onclick="saveSelectedRooms()"></html:submit>
								</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53"><c:choose>
								<c:when test="${SubjectGroupEntryOperation == 'edit'}">
									<html:button	property="" styleClass="formbutton"	onclick="cancelPage()">
									<bean:message key="knowledgepro.close" />
								</html:button>
								</c:when>
								<c:otherwise>
									<html:button	property="" styleClass="formbutton"	onclick="cancelPage()">
									<bean:message key="knowledgepro.close" />
								</html:button>
								</c:otherwise>
							</c:choose></td>
							</tr>
						
						</table>
						
						</td>
						<td valign="top" background="images/Tright_3_3.gif"></td>
					</tr>
				</logic:notEmpty>
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
