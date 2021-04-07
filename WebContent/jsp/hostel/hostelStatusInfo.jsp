<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
	
	<script language="JavaScript" src="js/calendar_us.js"></script>
	<link rel="stylesheet" href="css/calendar.css">
	<script language="javascript" type="text/javascript">
		function getFloors(hostelId) {			
			getFloorsByHostel("floorMap", hostelId, "floorNamesId", updateFloors);			
			getRoomTypesByHostel("roomTypeMap",hostelId,"roomTypeId",updateRoomType);			
		}
		function updateFloors(req) {
			updateOptionsFromMap(req, "floorNamesId", "- Select -");
		}
	
		function updateRoomType(req) {
			updateOptionsFromMapForMultiSelect(req, "roomTypeId");
		}

		function getHostelStatusInfoDetails(){					
			document.getElementById("method").value="getHostelStatusInfoDetails";
		}

		function clearFields(){
			
			var destination5 = document.getElementById("roomTypeId");
			for (x1=destination5.options.length-1; x1>=0; x1--) {
				destination5.options[x1]=null;
			}
			var destination6 = document.getElementById("floorNamesId");
			for (x1=destination6.options.length-1; x1>=0; x1--) {
				destination6.options[x1]=null;
			}
			resetFieldAndErrMsgs();
		}
	</script>
	
<html:form action="hostelStatusInfo" method="post">
	<html:hidden property="method" name="hostelStatusInfoForm" styleId="method" />
	<html:hidden property="formName" value="hostelStatusInfoForm" />	
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">  
  		<tr>
			<td>
				<span class="heading">
					<bean:message key="knowledgepro.hostel" />
						<span class="Bredcrumbs">&gt;&gt;
							<bean:message key="knowledgepro.hostel.hostelStatusInfo" /> &gt;&gt;
						</span>
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white"><bean:message	key="knowledgepro.hostel.hostelStatusInfo" /></td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right">
								<span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span>
							</div>
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<FONT color="green">
									<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
								</FONT>
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="44" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
												<td width="20%" height="25" class="row-odd">
													<div align="right">
														<span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.name.col" />
													</div>
												</td>
												<td width="30%" class="row-even">
													<html:select name="hostelStatusInfoForm" property="hostelId" styleId="hostelId" styleClass="combo" onchange="getFloors(this.value)">
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
														<logic:notEmpty name="hostelStatusInfoForm" property="hostelList">																												
														<html:optionsCollection name="hostelStatusInfoForm" property="hostelList" label="name" value="id"/>
														</logic:notEmpty>																												
													</html:select>
												</td>
												<td width="25%" height="25" class="row-odd">
													<div align="right">
														<span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.hostel.roomStatusOn" />:
													</div>
												</td>
												<td width="25%" class="row-even">
													<nested:text name="hostelStatusInfoForm" property="roomStatusOnDate" styleId="roomStatusOnDateId" readonly="true" styleClass="TextBox" size="14" />
														<script language="JavaScript">
															new tcal ({
																// form name
																'formname': 'hostelStatusInfoForm',
																// input name
																'controlname': 'roomStatusOnDate'
															});
														</script>
												</td>
											</tr>
										</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
											<tr>
												<td width="20%" height="25" class="row-odd">
													<div align="right">
														<bean:message key="knowledgepro.hostel.room.floor.no" />
													</div>
												</td>
												<td width="30%" class="row-even">
													<html:select name="hostelStatusInfoForm" property="floorNo" styleId="floorNamesId" styleClass="combo">													
														<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
														<c:if test="${floorMap != null}">
														<html:optionsCollection name="floorMap" label="value" value="key" />
														</c:if>
													</html:select>
												</td>
												<td width="25%" height="25" class="row-odd">
													<div align="right">
														<bean:message key="knowledgepro.hostel.roomtype" />
													</div>
												</td>
												<td width="25%" class="row-even">												
													<html:select name="hostelStatusInfoForm" multiple="true" size="3" property="roomType" styleId="roomTypeId" styleClass="combo" style="height:40px">														
														<c:if test="${roomTypeMap != null}">
														<html:optionsCollection name="roomTypeMap" label="value" value="key"/>
														</c:if>
													</html:select>													
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
						<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr bgcolor="#FFFFFF">
									<td width="100%" height="20" colspan="4">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td width="45%" height="35">
													<div align="right">												
														<html:submit property="" styleClass="formbutton" onclick="getHostelStatusInfoDetails()">
															<bean:message key="knowledgepro.submit" />
														</html:submit>
													</div>
												</td>
												<td width="2%"></td>
												<td width="53%">
													<html:button property="" styleClass="formbutton" onclick="clearFields()">
														<bean:message key="knowledgepro.reset" />
													</html:button>												
												</td>
											</tr>
										</table>
									</td>
								</tr>							
							</table>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news"></td>
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
