<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.listOfOccupancy.listOfOccupancyRegister" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.listOfOccupancy.listOfOccupancyRegister" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
							property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							<br>
						</html:messages> </FONT></div></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news">
					</td>
				</tr>
				<tr>
					<td height="43" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif">
							
							</td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" width="25%"  class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.entry.hostel.name" /></div>
									</td>
									<td height="25" width="25%" class="row-even">
										<bean:write name="listOfOccupancyRegisterForm" property="hostelName" />
									</td>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.room.floor.no" /></div>
									</td>
									<td width="25%" height="25" class="row-even">									
										<bean:write name="listOfOccupancyRegisterForm" property="floorNo" />									
									</td>
								</tr>
								<tr>
									<td width="25%" height="25" class="row-odd">
									<div align="right"><bean:message
										key="knowledgepro.hostel.checkin.roomNo" /></div>
									</td>
									<td width="25%" height="25" class="row-even">									
										<bean:write name="listOfOccupancyRegisterForm" property="roomNo" />									
									</td>
									<td width="25%" class="row-odd">&nbsp;</td>
									<td width="25%" class="row-even">&nbsp;</td>
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
						<tr>							
							<td valign="top" style="height:10px"></td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">									
									<tr>										
										<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>												
													<td style="height:25px;width:150px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.floorno" /></td>
													<td style="height:25px;width:550px;align:left" class="row-odd"><bean:message key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></td>
													<td style="height:25px;width:150px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.hostelerdatabase.roomno" /></td>
													<td style="height:25px;width:150px;align:left" class="row-odd"><bean:message	key="knowledgepro.hostel.listOfOccupancy.noOfOccupants" /></div></td>
												</tr>

								<c:set var="temp" value="0" />
								<logic:notEmpty name="listOfOccupancyRegisterForm" property="listOfOccupancyRegisterList">
									<logic:iterate id="occu" name="listOfOccupancyRegisterForm" property="listOfOccupancyRegisterList" indexId="count">

										<c:choose>
											<c:when test="${temp == 0}">
												<tr>															
													<td style="height:25px;width:150px;align:left" class="row-even"><bean:write name="occu" property="floorNo" /></td>
													<td style="height:25px;width:150px;align:left" class="row-even"><bean:write name="occu" property="studentName" /></td>
													<td style="height:25px;width:150px;align:left" class="row-even"><bean:write name="occu" property="roomNo" /></td>
													<td style="height:25px;width:150px;align:left" class="row-even"><bean:write name="occu" property="noOfOccupants" /></td>
												</tr>
											<c:set var="temp" value="1" />
											</c:when>
											<c:when test="${temp == 1}">
												<tr>															
													<td style="height:25px;width:150px;align:left" class="row-white"><bean:write name="occu" property="floorNo" /></td>
													<td style="height:25px;width:150px;align:left" class="row-white"><bean:write name="occu" property="studentName" /></td>
													<td style="height:25px;width:150px;align:left" class="row-white"><bean:write name="occu" property="roomNo" /></td>
													<td style="height:25px;width:150px;align:left" class="row-white"><bean:write name="occu" property="noOfOccupants" /></td>
												</tr>
											<c:set var="temp" value="1" />
											</c:when>													
										</c:choose>

									</logic:iterate>
								</logic:notEmpty>

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
					<td height="13" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					</td>
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