<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<head>
	<script type="text/javascript">
		function deleteRoomEntry(roomNo)
		{
			if(confirm("Do you want to delete this room ?"))
			{	
				document.getElementById("roomNo").value=roomNo;
				document.getElementById("method").value="deleteRoom";
				document.assignRoomMasterForm.submit();
			}	
		}

		function updateRooms()
		{
			document.getElementById("method").value="updateRooms";
			document.assignRoomMasterForm.submit();
		}

		function closeWindow(){
			document.getElementById("method").value="displayHomePage";
			document.assignRoomMasterForm.submit();
			//document.location.href = "LoginAction.do?method=loginAction";
		}	

		function getBedNo(){
			document.getElementById("method").value="getBedNumbersForEdit";
			document.assignRoomMasterForm.submit();
		}
		
	</script>
</head>
<html:form action="/AssignRoom">
	<html:hidden property="formName" value="assignRoomMasterForm" />
	<html:hidden property="method" styleId="method"	value="" />
	<html:hidden property="searchedFloorNumber" name="assignRoomMasterForm"/>
	<html:hidden property="searchedHostelId" name="assignRoomMasterForm"/>
	<html:hidden property="createdBy" name="assignRoomMasterForm"/>
	<html:hidden property="createdDate" name="assignRoomMasterForm"/>
	<html:hidden property="roomNo" name="assignRoomMasterForm" value="" styleId="roomNo"/>
	
	<html:hidden property="pageType" value="2" />
	<table width="99%" border="0">
	  
	  <tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key = "knowledgepro.hostel.assign.room.master"/> &gt;&gt;</span></span></td>	  

	</tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	
	        <td colspan="2" background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.assign.room.master"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" class="news"><div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
			<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
			</html:messages> </FONT></div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="42" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                  <tr >
	                    <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                    <td width="25%" height="25" class="row-even"><bean:write name = "assignRoomMasterForm" property="searchedHostelName"/></td>
	                   	<td width="25%" class="row-odd"><div align="right">Block</div></td>
	                    <td width="25%" class="row-even"><bean:write name = "assignRoomMasterForm" property="blockName"/></td>
	                  </tr>
	                  <tr>
	                  	<td width="25%" class="row-odd"><div align="right">Unit</div></td>
	                    <td width="25%" class="row-even"><bean:write name ="assignRoomMasterForm" property="unitName"/></td>
	                    <td width="25%" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.room.floor.no"/></div></td>
	                    <td width="25%" class="row-even"><bean:write name = "assignRoomMasterForm" property="searchedFloorNumber"/></td>
	                  </tr>
	                  <tr >
	                    <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.room.no.of.rooms"/></div></td>
	                    <td height="25" class="row-even"><bean:write name = "assignRoomMasterForm" property="noOfRooms"/></td>
	                    <td class="row-odd">&nbsp;</td>
	
	                    <td class="row-even">&nbsp;</td>
	                  </tr>
	              </table>
	                <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
	              </table></td>
	              <td width="5" height="30"  background="images/right.gif"></td>
	            </tr>
	            <tr>
	              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	
	              <td background="images/05.gif"></td>
	              <td><img src="images/06.gif" /></td>
	            </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news">&nbsp;</td>
	
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="94" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top">
	            	<table width="100%" cellspacing="1" cellpadding="2">
						<logic:notEmpty name= "assignRoomMasterForm" property="roomList" >
							<tr class="row-odd">
								<td width="15%" height="25" align="center">Room</td>
								<td width="15%" height="25" align="center">Room Type</td>
								<td width="65%" height="25" align="center">Bed Number</td>
								<td width="5%"  height="25" align="center">Delete</td>
							</tr>
							<nested:iterate name="assignRoomMasterForm" property="roomList" id="roomList" type="com.kp.cms.to.hostel.HlRoomTO" indexId="count">
				                  		<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
			                  			<td>
			                  				<nested:text property="name"	styleId="name" size="5" maxlength="20"/>
			                  			</td>
			                  			<td>
			                  				<nested:select 	property="roomTypeId" styleClass="combo"  onchange="getBedNo(this.value)">
												<option value=""><bean:message key="knowledgepro.admin.select"/></option>
												<nested:optionsCollection name="assignRoomMasterForm" label="name" value="id" property="roomTypeList" />
											</nested:select>		
			                  			</td>
			                  			<td>
			                  				<logic:notEmpty name="roomList" property="hlBeds">
						                   	<nested:iterate property="hlBeds">
						                   		<nested:equal value="true" property="isActive">
													<nested:text property="bedNo"	styleId="bedNo" size="5" maxlength="20"/>
												</nested:equal>	                   	
						                   	</nested:iterate>
						                   </logic:notEmpty>
			                  			</td>
			                  			<td>
			                  				<div align="center">
												<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteRoomEntry('<bean:write name="roomList" property="roomId"/>')">
											</div>
			                  			</td>
							</nested:iterate>
						</logic:notEmpty>
		            </table>
		        </td>
	
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right">
	                <html:submit property="" styleClass="formbutton" value="Submit" styleId="submitbutton" onclick="updateRooms()"/>
	            </div></td>
				<td width="2%"></td>
				<td width="48%"><html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        <td colspan="2" background="images/TcenterD.gif"></td>
	
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table></td>
	</tr>
</table>
</html:form>