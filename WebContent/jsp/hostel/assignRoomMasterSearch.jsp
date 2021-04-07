<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function getFloors(unitId) {
		getFloorsByHostel("floorMap", unitId, "floorNo", updateFloors);
	}
	
	function updateFloors(req) {
		updateOptionsFromMap(req, "floorNo", "- Select -");
	}
	function addRooms() {
		document.getElementById("hostelName").value = document.getElementById("hostelId").options[document.getElementById("hostelId").selectedIndex].text;
		document.getElementById("unitName").value = document.getElementById("unit").options[document.getElementById("unit").selectedIndex].text;
		document.getElementById("blockName").value = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
		document.getElementById("method").value="submitRoomMaster";
		document.assignRoomMasterForm.submit();
	}
	function searchRooms(){
		document.getElementById("searchedHostelName").value = document.getElementById("hostelId").options[document.getElementById("hostelId").selectedIndex].text;
		document.getElementById("unitName").value = document.getElementById("unit").options[document.getElementById("unit").selectedIndex].text;
		document.getElementById("blockName").value = document.getElementById("block").options[document.getElementById("block").selectedIndex].text;
		document.getElementById("method").value="searchRoomMaster";
		document.assignRoomMasterForm.submit();
	}		
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}

	function cancelSearch()
	{
		document.getElementById("method").value="initAssignRoomMaster";
		document.assignRoomMasterForm.submit();
	}

	function editRoomEntry()
	{		
		document.getElementById("method").value="editRoomMaster";
		document.assignRoomMasterForm.submit();
	}
	function getBlocks(hostal){
		getBlocksByHostel(hostal, "block", updateBlocks);
	}
	function updateBlocks(req){
		updateOptionsFromMap(req, "block", "- Select -");
	}
	function getUnits(blockId){
		getUnitsByBlocks(blockId, "unit", updateUnits);
	}
	function updateUnits(req){
		updateOptionsFromMap(req, "unit", "- Select -");
	}
</script>

<html:form action="/AssignRoom">
	<html:hidden property="method" styleId="method"	value="" />
	<html:hidden property="formName" value="assignRoomMasterForm" />
	<html:hidden property="hostelName" styleId="hostelName"/>
	<html:hidden property="unitName" styleId="unitName"/>
	<html:hidden property="blockName" styleId="blockName"/>
	<html:hidden property="pageType" value="1" />
	<html:hidden property="searchedFloorNumber" styleId="searchedFloorNumber" name="assignRoomMasterForm"/>
	<html:hidden property="searchedHostelId" styleId="searchedHostelId" name="assignRoomMasterForm"/>
	<html:hidden property="searchedHostelName" styleId="searchedHostelName" name="assignRoomMasterForm"/>
	<table width="99%" border="0">
		<tr>
			<td>
				<span class="Bredcrumbs">
					<bean:message key="knowledgepro.hostel"/>
					<span class="Bredcrumbs">
						&gt;&gt;<bean:message key = "knowledgepro.hostel.assign.room.master"/> &gt;&gt;
					</span>
				</span>
			</td>	  
		</tr>
	  	<tr>	
	    	<td valign="top">
	    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	      			<tr>
	        			<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        			<td colspan="2" background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.assign.room.master"/></td>
				        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      			</tr>
	      			<tr>
	        			<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        			<td colspan="2" class="news">
	        				<div align="right">
	        					<FONT color="red">
	        						<span class='MandatoryMark'>
	        							<bean:message key="knowledgepro.mandatoryfields"/>
	        						</span>
	        					</FONT>
	        				</div>
							<div id="errorMessage">
								<FONT color="red">
									<html:errors />
								</FONT>
								<FONT color="green">
									<html:messages id="msg"	property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out>
										<br>	
									</html:messages>
								</FONT>
							</div>
						</td>
	        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr>	
	      			<tr>
				        <td height="42" valign="top" background="images/Tright_03_03.gif"></td>
				        <td colspan="2" valign="top" class="news">
				        	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	        			    	<tr>
	              					<td ><img src="images/01.gif" width="5" height="5" /></td>
	              					<td width="914" background="images/02.gif"></td>
	              					<td><img src="images/03.gif" width="5" height="5" /></td>
	            				</tr>
	            				<tr>
	              					<td width="5"  background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
	                  						<tr >
	                    						<td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                    						<td width="25%" height="25" class="row-even">
	                    							<html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" onchange="getBlocks(this.value)">
														<html:option value="">
															<bean:message key="knowledgepro.admin.select" />
														</html:option>
														<logic:notEmpty name="assignRoomMasterForm" property="hostelList">
															<html:optionsCollection name="assignRoomMasterForm" property="hostelList" label="name" value="id" />
														</logic:notEmpty>
													</html:select>
												</td>
												<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Block</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="assignRoomMasterForm" property="block" styleId="block" styleClass="combo" onchange="getUnits(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="assignRoomMasterForm" property="blockMap">
															<html:optionsCollection name="assignRoomMasterForm" property="blockMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
	                  						</tr>
	                  						<tr>
	                  							<td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Unit</div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="assignRoomMasterForm" property="unit" styleId="unit" styleClass="combo" onchange="getFloors(this.value)">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="assignRoomMasterForm" property="unitMap">
															<html:optionsCollection name="assignRoomMasterForm" property="unitMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
							                   <td width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.room.floor.no"/></div></td>
	                    						<td width="25%" class="row-even">
								                    <html:select name="assignRoomMasterForm" property="floorNo" styleId="floorNo" styleClass="combo">
														<html:option value="">- Select -</html:option>
														<logic:notEmpty name="assignRoomMasterForm" property="floorMap">
															<html:optionsCollection name="assignRoomMasterForm" property="floorMap" label="value" value="key" />
														</logic:notEmpty>
													</html:select>
												</td>
	                  						</tr>
	                  						<tr>
							                    <td height="25" class="row-odd"><div align="right"><span class="Mandatory"></span> <bean:message key="knowledgepro.hostel.room.no.of.rooms"/></div></td>
	                    						<td height="25" class="row-even">
													<html:text property="noOfRooms" styleClass="TextBox" styleId="noOfRooms" size="15" maxlength="5" name="assignRoomMasterForm" 
														onkeypress="return isNumberKey(event)"	onblur="checkNumber(this)"/>
												</td>
	                    						<td class="row-odd">&nbsp;</td>
	                    						<td class="row-even">&nbsp;</td>
	                  						</tr>
	              						</table>
	                					<table width="100%" cellspacing="1" cellpadding="2">
	                						<tr ></tr>
							            </table>
									</td>
	              					<td width="5" height="30"  background="images/right.gif"></td>
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
	        			<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
	        			<td colspan="2" valign="top" class="news">
	        				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          					<tr>
	           						<td width="48%"></td>
	           						<td height="35">
	                					<html:submit property="" styleClass="formbutton" value="Add"
											onclick="addRooms()"></html:submit>
	           						</td>
	           						<td width="2%"></td>
	           						<td height="35">
	                					<html:button property="" styleClass="formbutton" value="Search"
											onclick="searchRooms()"></html:button>
	           						</td>
	           						<td width="2%"></td>
	            					<td height="35">
	            						<html:button property="" styleClass="formbutton"
										value="Reset" onclick="resetFieldAndErrMsgs()"></html:button></td>
									<td width="48%"></td>			
	          					</tr>
	        				</table>
	        			</td>
	        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      			</tr>
	      			
	      			<c:if test="${assignRoomMasterForm.roomMaster != null}">
		      			<tr>
		      				<td height="42" valign="top" background="images/Tright_03_03.gif"></td>
		      				<td class="heading" colspan="2">
		      					Search Results
		      				</td>
		      				 <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		      			</tr>
		      			<tr>
					        <td height="42" valign="top" background="images/Tright_03_03.gif"></td>
					        <td colspan="2" valign="top" class="news">
					        	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		        			    	<tr>
		              					<td ><img src="images/01.gif" width="5" height="5" /></td>
		              					<td width="914" background="images/02.gif"></td>
		              					<td><img src="images/03.gif" width="5" height="5" /></td>
		            				</tr>
		            				<tr>
		              					<td width="5"  background="images/left.gif"></td>
										<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
		                  						<tr>
		                  							<td class="row-odd">
		                  								Sl.No
		                  							</td>
		                  							<td class="row-odd">
		                  								<bean:message key="knowledgepro.hostel.entry.hostel.name"/>
		                  							</td>
		                  							<td class="row-odd">
		                  								<bean:message key="knowledgepro.hostel.room.floor.no"/>
		                  							</td>
		                  							<td class="row-odd">
		                  								Room Names
		                  							</td>
		                  							<td class="row-odd">
		                  								Edit
		                  							</td>	
		                  						</tr>
		                  						<tr >
		                    						<td width="25%" class="row-even">1</td>
		                    						<td width="25%" class="row-even">
		                    							<bean:write name = "assignRoomMasterForm" property="roomMaster.hostelName"/>
		                    						</td>
		                    						<td width="25%" class="row-even">
		                    							<bean:write name = "assignRoomMasterForm" property="roomMaster.floorNo"/>
		                    						</td>
		                    						<td width="25%" class="row-even">
		                    							<bean:write name = "assignRoomMasterForm" property="roomMaster.roomNames"/>
		                    						</td>
		                    						<td width="25%" class="row-even">
		                    							<div align="center">
		                    								<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editRoomEntry();" >
		                    							</div>
		                    						</td>
		                  						</tr>
		              						</table>
		                					<table width="100%" cellspacing="1" cellpadding="2">
		                						<tr ></tr>
								            </table>
										</td>
		              					<td width="5" height="30"  background="images/right.gif"></td>
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
		        			<td height="36" valign="top" background="images/Tright_03_03.gif"></td>
		        			<td colspan="2" valign="top" class="news" align="center">
		        				<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelSearch()"></html:button>
		        			</td>
		        			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		      			</tr>
		      		</c:if>	
					
	      			<tr>
	        			<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	        			<td colspan="2" background="images/TcenterD.gif"></td>
				        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      			</tr>
	    		</table>
	    	</td>
	  </tr>
	</table>
</html:form>
