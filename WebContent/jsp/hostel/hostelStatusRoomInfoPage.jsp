<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<script language="javascript" type="text/javascript">
	function goToHostelStatusInfoPage(){
		document.getElementById("methodId").value="goToHostelStatusInfoPage";
	}
	function cancelAction(){
		document.location.href = "hostelStatusInfo.do?method=initHostelStatusInfo";
	}
</script>
<html:form action="/hostelStatusInfo" method="post">
	<html:hidden name="hostelStatusInfoForm" property="method" styleId="methodId" />
	<html:hidden property="formName" value="hostelStatusInfoForm" />	
	<table width="98%" border="0">  		
  		<tr>
			<td>
				<span class="heading">
					<bean:message key="knowledgepro.hostel" />
						<span class="Bredcrumbs">&gt;&gt;
							<bean:message key="knowledgepro.hostel.statusInfo" /> &gt;&gt;
						</span>
				</span>
			</td>
		</tr>
		
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white"><bean:message	key="knowledgepro.hostel.statusInfo" /></td>
						<td width="10"><img src="images/Tright_1_01.gif" width="9" height="29"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
						<table width="100%">
						<logic:notEmpty name="hostelStatusInfoForm" property="floorList">
						<logic:iterate id="floor" name="hostelStatusInfoForm" property="floorList">
						<tr>
						<td colspan="2" class="heading">Floor &nbsp; <bean:write name="floor" property="name"/> </td>
						</tr>
						<logic:notEmpty name="floor" property="roomTypeTOs">
						<logic:iterate id="roomType" name="floor" property="roomTypeTOs">
						<tr> 
						<td width="70%">
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
									<table width="100%">
									<tr>
									<td class="row-odd" width="20%">Room Type</td>
									<td   class="row-even" width="80%"><bean:write name="roomType" property="name"/> </td>
									</tr>
									<tr>
									<td colspan="2">
									<table width="100%">
									<tr>
									<logic:notEmpty name="roomType" property="roomTOs">
									<logic:iterate id="room" name="roomType" property="roomTOs" indexId="count">
													<c:if test="${count%10 == 0}">
														<tr height="100%" class="row-even">
													</c:if>
											<c:if test="${room.status =='1'}">
											<td class="ColorGreen" width="10%"><bean:write name="room" property="name"/> </td>
											</c:if>		
											<c:if test="${room.status =='2'}">
											<td class="ColorRed" width="10%"><bean:write name="room" property="name"/> </td>
											</c:if>	
											<c:if test="${room.status =='3'}">
											<td  class="row-white" width="10%"><bean:write name="room" property="name"/> </td>
											</c:if>	
											<c:if test="${room.status =='4'}">
											<td class="ColorYellow" width="10%"><bean:write name="room" property="name"/> </td>
											</c:if>	
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
					<td width="30%" height="114" valign="top" class="news"><table width="98%" border="0" cellpadding="0" cellspacing="0">
					          <tr>
					            <td ><img src="images/01.gif" width="5" height="5"></td>
					            <td width="914" background="images/02.gif"></td>
					            <td><img src="images/03.gif" width="5" height="5"></td>
					          </tr>
					          <tr>
					            <td width="5"  background="images/left.gif"></td>
					            <td height="79" valign="top"><table width="100%" height="131" border="0" cellpadding="0" cellspacing="1">
					                <tr class="row-white">
					                  <td width="88"  height="25" class="row-odd"><div align="center">
					                  <bean:write name="roomType" property="totalVacant"/>
					                  </div></td>
					                  <td width="164" height="20" class="ColorGreen">Vacant</td>
					                </tr>
					                <tr class="row-even">
					                  <td height="25" class="row-odd"><div align="center">
					                  <bean:write name="roomType" property="totalOccupied"/>
					                  </div></td>
					                  <td height="20" class="ColorRed"> Fully Occupied</td>
					                </tr>
					                <tr class="row-even">
					                  <td height="25" class="row-odd"><div align="center">
					                  <bean:write name="roomType" property="totalPartial"/>
					                  </div></td>
					                  <td height="23" class="row-white">Partially Occupied</td>
					                </tr>
					                <tr class="row-even">
					                  <td height="25" class="row-odd"><div align="center">
					                  <bean:write name="roomType" property="totalReserve"/>
					                  </div></td>
					                  <td height="23" class="ColorYellow">Reserved</td>
					                </tr>
					            </table></td>
					            <td  background="images/right.gif" width="5" height="79"></td>
					          </tr>
					          <tr>
					            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
					            <td background="images/05.gif"></td>
					            <td><img src="images/06.gif" ></td>
					          </tr>
					        </table></td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						
						</logic:iterate>
						</logic:notEmpty>
						<tr>
						<td colspan="2" align="center">
						<html:button property="" value="Cancel" onclick="cancelAction()" styleClass="formbutton"></html:button>
						</td>
						</tr>
						</table>
						</td>
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