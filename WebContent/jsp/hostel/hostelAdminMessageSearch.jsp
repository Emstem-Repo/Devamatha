<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<head>
<title>:: CMS ::</title>

<script language="JavaScript" src="js/calendar_us.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
function getRooms(floorNo) {
	var hostelId=document.getElementById("hostelId").value;
	getRoomsByFloor("roomsMap", hostelId,floorNo,"roomId",updateRooms);
	}
	
	function updateRooms(req) {
		updateOptionsFromMap(req, "roomId", "- Select -");
	}
function getFloors(hostelId) {			
	getFloorsByHostel("floorMap", hostelId, "floorNamesId", updateFloors);			
}
function updateFloors(req) {
	updateOptionsFromMap(req, "floorNamesId", "- Select -");
}
</script>
</head>
<html:form action="hostelAdmnMessage" method="post">
<html:hidden property="method" styleId="method" value="getHostelAdminMessageList"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="hostelAdminMessageForm" />	
	
	<table width="99%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.hostel.adminmessage.hostel"/> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.adminmessage.adminmessage"/>&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.hostel.adminmessage.adminmessage"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right" class="mandatoryfield">
          <div align="right" class="mandatoryfield"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
          <div id="errorMessage" align="left">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
			</div>
        </div></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="40" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.hostelname"/>:</div></td>
                  
                  <td height="25" class="row-even" >    
                  <html:select
					name="hostelAdminMessageForm" property="hostelId"
					styleId="hostelId" styleClass="combo"
					onchange="getFloors(this.value)">
					<html:option value="">-<bean:message
					key="knowledgepro.select" />-</html:option>
					<cms:renderHostelNames>
					</cms:renderHostelNames>
					</html:select>
                  </td>
                 <td  height="25" class="row-odd">
					<div align="right">
						<bean:message key="knowledgepro.hostel.room.floor.no" />
					</div>
				</td>
				<td class="row-even">
					<html:select name="hostelAdminMessageForm" property="floorNo" styleId="floorNamesId" styleClass="combo" onchange="getRooms(this.value)">													
						<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
						<c:if test="${floorMap != null}">
						<html:optionsCollection name="floorMap" label="value" value="key" />
						</c:if>
					</html:select>
				</td>
                </tr>
                <tr >
                <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.roomNo"/>.</div></td>
                  <td class="row-even">
                  
                  <html:select property="roomId" name="hostelAdminMessageForm"
												styleClass="body" styleId="roomId">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  
                                  <c:if
													test="${hostelAdminMessageForm.hostelId != null && hostelAdminMessageForm.hostelId != ''}">
													<c:set var="programMap"
														value="${baseActionForm.collectionMap['roomsMap']}" />
													<c:if test="${roomsMap != null}">
														<html:optionsCollection name="roomsMap" label="value"
															value="key" />
													</c:if>
								</c:if>
                      </html:select>
                  </td>
                  <td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.adminmessage.messageType"/>: </div></td>
                  <td width="32%" height="25" class="row-even" ><label></label>
                  <html:select property="messageTypeId" name="hostelAdminMessageForm"
												styleClass="body" styleId="messageTypeId">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <logic:notEmpty name="hostelAdminMessageForm" property="messageTypeMap">
                                  <html:optionsCollection name="hostelAdminMessageForm" property="messageTypeMap" label="value"  value="key" />
                                  </logic:notEmpty>
                   </html:select>
                 </td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">
                    <div align="right"><bean:message key="knowledgepro.hostel.adminmessage.fromDate"/>:</div>
                  </div></td>
                  <td height="25" class="row-even" >
                  <html:text property="fromDate" name="hostelAdminMessageForm"  styleClass="TextBox" styleId="name" size="10" maxlength="10"/>
                  <script language="JavaScript">
	new tcal ({
		// form name
		'formname': 'hostelAdminMessageForm',
		// input name
		'controlname': 'fromDate'
	});</script></td>
                  <td height="25" class="row-odd" ><div align="right">
                      <div align="right"><bean:message key="knowledgepro.hostel.adminmessage.toDate"/>:</div>
                  </div></td>
                  <td height="25" class="row-even" >
                 <html:text property="toDate" name="hostelAdminMessageForm"  styleClass="TextBox" styleId="name" size="10" maxlength="10"/>
                 
                      <script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'hostelAdminMessageForm',
							// input name
							'controlname': 'toDate'
						});</script>
				</td>
                </tr>
                <tr>
                 <td  height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.adminmessage.status"/>:</div></td>
                  <td  class="row-even">
                  
                  <html:select property="statusId" name="hostelAdminMessageForm"
												styleClass="body" styleId="statusId">
                                  <html:option value="">
                                    <bean:message key="knowledgepro.select" />
                                  </html:option>
                                  <logic:notEmpty name="hostelAdminMessageForm" property="statusMap">
                                  <html:optionsCollection name="hostelAdminMessageForm" property="statusMap" label="value"  value="key" />
                                  </logic:notEmpty>
                   </html:select>
                   </td>
                   <td class="row-odd"></td>
                   <td class="row-even"></td>	
                </tr>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="45%" height="35"><div align="right">
            <html:submit styleClass="formbutton"
										styleId="button" ><bean:message key="knowledgepro.submit"/>
							</html:submit>	
            </div></td>
            <td width="2%"></td>
            <td width="53%">
            <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
            </td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
	
</html:form>	    