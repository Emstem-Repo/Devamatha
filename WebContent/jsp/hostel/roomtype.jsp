<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript">
	function submitRoomType() {
		document.getElementById("method").value = "submitRoomType";
		document.roomTypeForm.submit();
	}
	function prepareImageListInAddMode(){
		document.getElementById("method").value = "prepareImageListInAddMode";
		document.roomTypeForm.submit();
	}	
	function prepareImageListInUpdateMode(){
		document.getElementById("method").value = "prepareImageListInUpdateMode";
		document.roomTypeForm.submit();
	}
	function deleteRoomType(roomTypeId){
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "RoomType.do?method=deleteRoomType&roomTypeId="
					+ roomTypeId;
		}
	}
	function reActivate() {
		document.getElementById("method").value = "reactivateRoomType";
		document.roomTypeForm.submit();
	}
	function editRoomType(roomTypeId){
		document.location.href = "RoomType.do?method=editRoomType&roomTypeId="
			+ roomTypeId;
	}
	function assignFees(id,roomType,hostelName,hostelId){
		document.location.href = "RoomType.do?method=assignFees&id="+id+"&roomType="+roomType+"&roomTypeId="+id+"&hostelId="+hostelId+"&hostelName="+hostelName;
	}
	function updateRoomType(){
		document.getElementById("method").value = "updateRoomType";
		document.roomTypeForm.submit();
	}
	function resetMessages() {
		document.getElementById("room").value = "";
		document.getElementById("desc").value = "";
		document.getElementById("occupant").value = "";
		document.getElementById("hostel").selectedIndex = 0;
		document.getElementById("file").value = "";
		resetFieldAndErrMsgs();
	}	
	function deleteImageInAddMode(position){
		document.getElementById("position").value = position;
		document.getElementById("method").value = "deleteImageInAddMode";
		document.roomTypeForm.submit();
	}
	function deleteImageInUpdateMode(imageId, position)
	{
		document.getElementById("position").value = position;
		document.getElementById("imageId").value = imageId;
		document.getElementById("method").value = "deleteImageInUpdateMode";
		document.roomTypeForm.submit();
	}
	function imposeMaxLength(evt, Object) {
		var keynum = (evt.which) ? evt.which : event.keyCode;
		if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
			return true;
		}
		var MaxLen = 250;
		return (Object.value.length < MaxLen);
	}	
</script>
<html:form action="/RoomType" method="post" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" />
<c:choose>
		<c:when test="${operation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateRoomType" />
		</c:when>
		<c:otherwise>
		<html:hidden property="method" styleId="method" value="submitRoomType" />
		</c:otherwise>
		</c:choose>
<html:hidden property="formName" value="roomTypeForm" />
<html:hidden property="position" styleId="position"/>
<html:hidden property="imageId" styleId="imageId"/>
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
	<tr>
		<td><span class="heading"><bean:message key="knowledgepro.hostel"/> <span class="Bredcrumbs">&gt;&gt;
		<bean:message key="knowledgepro.roomtype"/> &gt;&gt;</span> </span></td>
	</tr>
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="9"><img src="images/Tright_03_01.gif" width="9"
					height="29"></td>
				<td background="images/Tcenter.gif" class="heading_white"><bean:message key="knowledgepro.roomtype"/></td>
				<td width="10"><img src="images/Tright_1_01.gif" width="9"
					height="29"></td>
			</tr>
			<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				<td valign="top" class="news">
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					
				<div align="right"><span class='mandatoryfield'>*
				<bean:message key="knowledgepro.mandatoryfields" /></span></div>
				</td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
								<td width="48%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.name.col"/> </div>
								</td>
								<td width="52%" colspan="2" class="row-even">
								<div align="left">
								<html:select property="hostelId" styleClass="TextBox" styleId="hostel" name="roomTypeForm">
								<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
								<logic:notEmpty name="roomTypeForm" property="hostelList">
								<html:optionsCollection property="hostelList" name="roomTypeForm" label="name" value="id" />
								</logic:notEmpty>
								</html:select>
								</div>
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
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
				<td class="heading"><bean:message key="knowledgepro.hostel.roomdetails"/> </td>
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="43" valign="top" background="images/Tright_03_03.gif"></td>
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
								<td width="12%" height="25" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.roomtype"/></div>
								</td>
								<td width="18%" height="25" class="row-even"><label></label>
								<html:text property="roomType" styleId="room" size="50" maxlength="50" styleClass="textbox"></html:text>
								</td>
								<td width="10%" height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.desc.with.col"/> </div>
								</td>
								<td width="17%" class="row-even">
								<div align="left"><span class="star">
								<html:textarea property="description" styleId="desc" cols="20" rows="2"
								onkeypress="return imposeMaxLength(event,this)"></html:textarea>
								</span></div>
								</td>
								<td width="21%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.noof.occupants"/></div>
								</td>
								<td width="22%" class="row-even">
								<html:text property="maxOccupants" styleId="occupant" size="3" maxlength="8" styleClass="textbox"></html:text>
								</td>
							</tr>
							<tr>
								<td height="25" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.upload.image"/> </div>
								</td>
								<td colspan="3" class="row-even">
								<nested:file property="image" size="25" maxlength="20" styleId="file"></nested:file>
								<c:choose>
								<c:when test="${operation == 'edit'}">
								<html:button property="" value="Add Image" styleClass="formbutton" onclick="prepareImageListInUpdateMode()"></html:button>
								</c:when>
								<c:otherwise>
								<html:button property="" value="Add Image" styleClass="formbutton" onclick="prepareImageListInAddMode()"></html:button>
								</c:otherwise>
								</c:choose>
								</td>
								<td height="25" class="row-odd">
								<div align="right">Room Category</div>
								</td>
								<td width="30%" height="25" class="row-even">
																   			   									
   			   					<html:select property="roomCategory" styleClass="comboLarge" name="roomTypeForm">
								 	 <html:option value="">- Select - </html:option>
								   		<html:option value="Student">Student </html:option>
								   		<html:option value="Warden">Warden</html:option>
										<html:option value="Guest">Guest</html:option>
							   </html:select>
														<!--<nested:text
															property="type" size="40" maxlength="10" onkeypress="return isNumberKey(event)"></nested:text>-->
															</td>
							</tr>
							<tr>
								<td height="25" class="row-odd">&nbsp;</td>
								<td colspan="5" class="row-even">
								<table width="400" border="0" cellpadding="2" cellspacing="1">
									<tr>
									<c:choose>
									<c:when test="${operation == 'edit'}">
									<logic:notEmpty property="imageList" name="roomTypeForm">
										<logic:iterate id="photo" property="imageList" name="roomTypeForm" indexId="count" type=" com.kp.cms.to.hostel.RoomTypeImageTO">
										<bean:define id="cnt" name="photo" property="countId"></bean:define>										
										<td width="50" align="left"><img src='<%=request.getContextPath()+"/ImageServlet?countID="+cnt%>'  height="70Px" width="70Px" /></td>
										<td width="350" align="left" valign="bottom"><input type="button" name="Delete" value="Delete" class="formbutton" onclick="deleteImageInUpdateMode('<bean:write name="photo" property="id"/>','<bean:write name="photo" property="countId"/>')" /> </td>
										</logic:iterate>
									</logic:notEmpty>
									</c:when>
									<c:otherwise>
									<logic:notEmpty property="imageList" name="roomTypeForm">
										<logic:iterate id="photo" property="imageList" name="roomTypeForm" indexId="count" type=" com.kp.cms.to.hostel.RoomTypeImageTO">
										<bean:define id="cnt" name="photo" property="countId"></bean:define>										
										<td width="50" align="left"><img src='<%=request.getContextPath()+"/ImageServlet?countID="+cnt%>'  height="70Px" width="70Px" /></td>
										<td width="350" align="left" valign="bottom"><input type="button" name="Delete" value="Delete" class="formbutton" onclick="deleteImageInAddMode('<bean:write name="photo" property="countId"/>')" /> </td>
										</logic:iterate>
									</logic:notEmpty>
									</c:otherwise>
									</c:choose>
									</tr>
								</table>
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
				<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
			</tr>
			<tr>
				<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
				<td valign="top" class="news">
				<table width="100%" cellspacing="1" cellpadding="2">
					<tr bgcolor="#FFFFFF">
						<td width="9%" height="20">
						<div align="center" class="heading">
						<div align="left"><bean:message key="knowledgepro.facility"/> </div>
						</div>
						</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td height="20" colspan="2">
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
								
								<logic:notEmpty name="roomTypeForm" property="facilityList">
								<tr>
								<nested:iterate id="facility" name="roomTypeForm" property="facilityList" indexId="count">
									<c:if test="${(count)%3 == 0}">
											<tr>
										</c:if>										
											
										<td width="10%" class="row-even" align="right"><nested:write  property="name"/></td>	
										<td width="6%" height="25" class="row-odd">
										<div align="left">	
										<input type="hidden" name="facilityList[<c:out value='${count}'/>].dummySelected" id="facilityhidden_<c:out value='${count}'/>"
													value="<nested:write name='facility' property='dummySelected'/>" />				
										<input type="checkbox" name="facilityList[<c:out value='${count}'/>].selected" id="<c:out value='${count}'/>" />
										</div>												
										<script type="text/javascript">
										var facility = document.getElementById("facilityhidden_<c:out value='${count}'/>").value;
										if(facility == "true") {
										document.getElementById("<c:out value='${count}'/>").checked = true;
										}		
										</script>														
										</td>
													
									</nested:iterate>
									</logic:notEmpty>
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
					<tr bgcolor="#FFFFFF">
						<td height="20" colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<c:choose>
						<c:when test="${operation == 'edit'}">
							<tr>
								<td width="49%" height="35" align="right">
								<div align="right"><html:button property="" value="Update" styleClass="formbutton" onclick="updateRoomType()">
								</html:button></div>
								</td>
								<td width="2%"></td>
								<td width="49%"><html:cancel value="Reset" styleClass="formbutton" onclick="updateRoomType()"></html:cancel></td>
							</tr>
							</c:when>
							<c:otherwise>
							<tr>
								<td width="49%" height="35" align="right">
								<div align="right"><html:button property="" value="Submit" styleClass="formbutton" onclick="submitRoomType()">
								</html:button></div>
								</td>
								<td width="2%"></td>
								<td width="49%"><html:button property="" value="Reset" styleClass="formbutton" onclick="resetMessages()"></html:button></td>
							</tr>
							</c:otherwise>
							</c:choose>
						</table>
						</td>
					</tr>
					<tr>
						<td height="45" colspan="2">
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
										<div align="center"><bean:message key="knowledgepro.slno"/> </div>
										</td>
										<td class="row-odd"><bean:message key="knowledgepro.hostel.name"/> </td>
										<td height="25" class="row-odd"><bean:message key="knowledgepro.roomtype"/> </td>
										<td class="row-odd">
										<div align="center"><bean:message key="knowledgepro.edit"/> </div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message key="knowledgepro.assignFees"/> </div>
										</td>
										<td class="row-odd">
										<div align="center"><bean:message key="knowledgepro.delete"/> </div>
										</td>
									</tr>									
								<c:set var="temp" value="0" />
								<logic:notEmpty name="roomTypeForm" property="roomTypeList">
								<nested:iterate name="roomTypeForm" id="roomtype" property="roomTypeList" indexId="count">
								<c:choose>
									<c:when test="${temp == 0}">
									<tr>
										<td width="4%" height="25" class="row-even">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>

										<td width="36%" class="row-even"><nested:write name="roomtype" property="hostelTO.name"/></td>
										<td width="43%" height="25" class="row-even"><nested:write name="roomtype" property="name"/></td>
										<td width="9%" height="25" class="row-even">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
										onclick="editRoomType('<nested:write name="roomtype" property="id"/>')"></div>
										</td>
										<td width="9%" height="25" class="row-even">
										<div align="center"><img src="images/rupee.jpg"
											width="16" height="18" style="cursor: pointer"
										onclick="assignFees('<nested:write name="roomtype" property="id"/>','<nested:write name="roomtype" property="name"/>','<nested:write name="roomtype" property="hostelTO.name"/>','<nested:write name="roomtype" property="hostelTO.id"/>')"></div>
										</td>
										<td width="8%" height="25" class="row-even">
										<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor: pointer"
										onclick="deleteRoomType('<nested:write name="roomtype" property="id"/>')"></div>
										</td>
									</tr>
									<c:set var="temp" value="1" />
									</c:when>
									<c:otherwise>				
									<tr>
										<td height="25" class="row-white">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>

										<td class="row-white"><nested:write name="roomtype" property="hostelTO.name"/></td>
										<td height="25" class="row-white"><nested:write name="roomtype" property="name"/></td>
										<td height="25" class="row-white">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
										onclick="editRoomType('<nested:write name="roomtype" property="id"/>')"></div>
										</td>
										<td height="25" class="row-white">
										<div align="center"><img src="images/rupee.jpg"
											width="16" height="18" style="cursor: pointer"
										onclick="assignFees('<nested:write name="roomtype" property="id"/>','<nested:write name="roomtype" property="name"/>','<nested:write name="roomtype" property="hostelTO.name"/>','<nested:write name="roomtype" property="hostelTO.id"/>')"></div>
										</td>
										<td height="25" class="row-white">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor: pointer"
											onclick="deleteRoomType('<nested:write name="roomtype" property="id"/>')"></div>
										</td>
									</tr>								
									<c:set var="temp" value="0" />
									</c:otherwise>
									</c:choose>
									</nested:iterate>
									</logic:notEmpty>
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
				</table>
				</td>
				<td width="10" valign="top" background="images/Tright_3_3.gif"
					class="news"></td>
			</tr>
			<tr>
				<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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
