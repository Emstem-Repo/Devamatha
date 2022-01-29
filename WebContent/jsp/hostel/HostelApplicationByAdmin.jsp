<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<script type="text/javascript">
 function goToHostelApplicationInit(){
	 document.location.href="HostelApplicationByAdmin.do?method=initHostelApplicationByAdmin";
 }
 
 function viewTermsAndConditions(){
	 document.location.href = "HostelApplicationByAdminTermsConditions.do?=";
	}

 function personalDetails(){
	 var url = "HostelApplicationByAdmin.do?method=viewPersonalDetails";
		window.open(url,'ViewPersonalDetails','left=20,top=20,width=1000,height=1000,toolbar=1,resizable=0,scrollbars=1');	
 }

 function setRoomTypeId(roomTypeId){
	    //alert(roomTypeId); 
		document.getElementById("roomTypeSelect").value = roomTypeId;
	}
 	
</script>
</head>
<html:form action="/HostelApplicationByAdmin">
	<html:hidden property="method" styleId="method"
		value="saveHlApplicationFormByAdmin" />
	<html:hidden property="formName" value="hostelApplicationByAdminForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="roomTypeSelect" styleId="roomTypeSelect" />


	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs"><a
				href="main.html" class="Bredcrumbs"><bean:message
				key="knowledgepro.hostel" /> &gt;&gt;<bean:message
				key="knowledgepro.hostel.applicationByAdmin" /> &gt;&gt; </a></span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"><bean:message
						key="knowledgepro.hostel.applicationByAdmin" /> </strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td height="10" valign="top"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">


						<tr>
							<td colspan="6" align="left">
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td height="150" valign="top" class="row-white">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td width="100%" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td width="14%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.hostelerdatabase.name" /></div>
											</td>
											<td width="10%" class="row-even"><bean:write
												name="hostelApplicationByAdminForm"
												property="hostelApplicationTO.studentOrStaffName" /></td>
											<td width="21%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.applicationByAdmin.permanentAddress" /><br>
											</div>
											</td>
											<td width="20%" class="row-even"><bean:write
												name="hostelApplicationByAdminForm"
												property="hostelApplicationTO.studentOrStaffPermanentAddressLine" /></td>
											<td width="18%" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.applicationByAdmin.communicationAddress" /><br>
											</div>
											</td>
											<td width="17%" class="row-even"><bean:write
												name="hostelApplicationByAdminForm"
												property="hostelApplicationTO.studentOrStaffCurrentAddressLine" /></td>
										</tr>
										<tr>
											<td colspan="6" align="right" class="row-even"><html:button
												styleClass="formbutton" property="" value="View Details"
												onclick="personalDetails()" /></td>
										</tr>
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
							<td width="100%" height="25" class="row-white">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td background="images/02.gif"></td>
									<td width="10"><img src="images/03.gif" width="5"
										height="5"></td>
								</tr>
								<tr>
									<td width="0" height="28" background="images/left.gif"></td>
									<td width="100%" height="28" valign="top">
									<table width="100%" height="26" border="0" cellpadding="2"
										cellspacing="1">
										<tr class="row-white">
											<td width="29%" height="24" class="row-odd">
											<div align="right"><bean:message
												key="knowledgepro.hostel.preference.roomtype" /></div>
											</td>

                                            <logic:notEmpty name="hostelApplicationByAdminForm" property="roomTypeNameList">
					                        <nested:iterate id="roomType" name="hostelApplicationByAdminForm" property="roomTypeNameList" indexId="count">
						                        <td  height="24" class="row-even">
						                        
							                        <bean:define id="roomId" property="id" name="roomType"></bean:define>
							                  	    <input type="radio" name="roomTypeId" id="roomTypeId" onclick="setRoomTypeId('<%=roomId %>')">
							                          <nested:write name="roomType" property="name"/>
						                   
						                          </td>
					                          </nested:iterate>
					                          </logic:notEmpty>
                                            </tr>
										
										<tr class="row-white"></tr>
									</table>
									</td>
									<td background="images/right.gif" width="10" height="28"></td>
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

						<logic:notEmpty name="hostelApplicationByAdminForm" property="roomTypeWithAmountList">
							
							<tr>
								<td height="150" valign="top" class="row-white">
								<table width="100%" border="0" align="center" cellpadding="0"
									cellspacing="0">
									<tr>
										<td><img src="images/01.gif" width="5" height="5" /></td>
										<td background="images/02.gif"></td>
										<td><img src="images/03.gif" width="5" height="5" /></td>
									</tr>
									<tr>
										<td width="5" background="images/left.gif"></td>
										<td width="100%" valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">
										<logic:iterate id="feeHead"
												name="hostelApplicationByAdminForm"
												property="roomTypeWithAmountList"
												type="com.kp.cms.to.hostel.RoomTypeWithAmountTO"
												indexId="count">
												<c:if test="${count == 0}">
										<tr>
										<td height="25" class="row-odd"> Room Type</td>
										<td height="25" class="row-odd">
										<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
										<logic:notEmpty name="feeHead" property="amountList">
											<logic:iterate name="feeHead" property="amountList"
												id="head">
												<td height="25" width="10%" class="row-odd"><bean:write
													name="head" property="feeTypeName" /></td>
											</logic:iterate>
										</logic:notEmpty>
										</tr>
										</table>
										</td>
										<td height="25" class="row-odd"> Total Amount </td>
										</tr>
										</c:if>
										</logic:iterate>
										<logic:iterate id="feeRoomType"
												name="hostelApplicationByAdminForm"
												property="roomTypeWithAmountList"
												type="com.kp.cms.to.hostel.RoomTypeWithAmountTO"
												indexId="count">
												<%Double totalAmt = 0.0;%>
												<tr>
													<td height="25" class="row-even">
													<div align="left"><bean:write name="feeRoomType"
														property="roomType" /></div>
													</td>
													<td height="25" class="row-even">
													<table width="100%" cellspacing="1" cellpadding="2">
													<tr> 
													<logic:notEmpty name="feeRoomType" property="amountList">
														<logic:iterate name="feeRoomType" id="feeAmt"
															property="amountList"
															type="com.kp.cms.to.hostel.HlApplicationFeeTO">
															<td width="10%" height="25" class="row-even"><bean:write
																name="feeAmt" property="amount" /></td>
															<%
															if(feeAmt.getAmount()!=null && feeAmt.getAmount()!="")
															totalAmt = totalAmt + Double.parseDouble(feeAmt.getAmount()); %>
														</logic:iterate>
													</logic:notEmpty>
													</tr>
													</table>
													</td>
													<td height="25" class="row-even"><%=totalAmt%></td>
												</tr>
											</logic:iterate>
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
						</logic:notEmpty>

						<tr>
							<td height="42" valign="top" class="body">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td background="images/02.gif"></td>
									<td width="10"><img src="images/03.gif" width="5"
										height="5"></td>
								</tr>
								<tr>
									<td width="0" height="28" background="images/left.gif"></td>
									<td width="100%" height="28" valign="top">
									<table width="100%" height="30" border="0" cellpadding="2"
										cellspacing="1">
										<tr class="row-white">
											<td height="28" class="row-even"><a href="#"
												onclick="viewTermsAndConditions()">View Terms And
											Conditions</a></td>
										</tr>
									</table>
									</td>
									<td background="images/right.gif" width="10" height="28"></td>
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
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
							<div align="right"><input name="submit" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="49%"><input type="button" class="formbutton"
								value="Cancel" onclick="goToHostelApplicationInit()" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>

				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>

