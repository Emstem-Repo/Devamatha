<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">

	function getRooms(floorNo) {
	var hostelId=document.getElementById("hostelId").value;
	var roomTypeId = document.getElementById("roomTypeId").value;
		
	getRoomsByFloors("roomMap", hostelId,floorNo,roomTypeId,"roomNo",updateRooms);
	}
	
	function updateRooms(req) {
		updateOptionsFromMap(req, "roomNo", "- Select -");
	}


	function getBeds()
	{
		
		var roomTypeId = document.getElementById("roomTypeId").value;
		getBedByRoomId("bedMap",roomTypeId,"bedNo",updateBeds);
	}

	function updateBeds(req) {
		updateOptionsFromMap(req, "bedNo", "- Select -");
	}
	
	function submitAllocationDetails() {
		document.getElementById("method").value = "submitAllocationDetails";
		document.hostelAllocationForm.submit();	
	}
	
	function resetMessages() {
		document.getElementById("floorNoId").selectedIndex = 0;
		document.getElementById("roomNo").selectedIndex = 0;
		resetFieldAndErrMsgs();
	}	
		
</script>
<body>

<html:form action="/hostelAllocation" method="post" >
	<html:hidden property="formName" value="hostelAllocationForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="2" />
	
	
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading">Hostel<span class="Bredcrumbs">&gt;&gt; Allocation &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Allocation</td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
       <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="43" valign="top" background="images/Tright_03_03.gif"></td>
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
                <html:hidden property="appNo" name="hostelAllocationForm"/>
                <html:hidden property="regNo" name="hostelAllocationForm"/>
                <html:hidden property="staffId" name="hostelAllocationForm"/>
                  <td width="23%" height="25" class="row-odd" ><div align="right">Name:</div></td>
                  <td height="25" class="row-even" ><bean:write name="hostelAllocationForm" property="applicantName"/><html:hidden property="hostelId" styleId="hostelId" name="hostelAllocationForm"/>
                  <html:hidden property="roomId" styleId="roomTypeId" name="hostelAllocationForm"/>
                  </td>
                  <td height="25" class="row-odd" ><div align="right">Reservation Date:</div></td>
                  <td height="25" class="row-even" ><bean:write name="hostelAllocationForm" property="reservationDate"/><html:hidden property="reservationDate" styleId="reservationDate" name="hostelAllocationForm"/></td>
                </tr>
                <tr >
                  <td height="25" class="row-odd" ><div align="right">Room Type:</div></td>
                  <td width="27%" height="25" class="row-even" ><bean:write name="hostelAllocationForm" property="roomType"/></td>
                  <td width="24%" height="25" class="row-odd"><div align="right">Food Preference.:</div></td>
                  <td width="26%" class="row-even"><bean:write name="hostelAllocationForm" property="isVeg"/></td>
                  </tr>
                  
					<tr >
                		<td width="24%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.floorno"/>:</div></td>
                  		<td width="26%" class="row-even">
                  			<html:select property="floorNo" styleId="floorNoId" styleClass="combo" onchange="getRooms(this.value)">
								<html:option value="">- Select -</html:option>
								<logic:notEmpty name="hostelAllocationForm" property="floorNoList">
									<html:optionsCollection name="hostelAllocationForm" property="floorNoList"  label="floorNo" value="floorNo"/>
								</logic:notEmpty>
							</html:select>
						</td>
						<td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.allocation.allocationDate"/>:</div></td>
                  		<td height="25" colspan="3" class="row-even"><html:text name="hostelAllocationForm" property="allocationDate" styleId="allocationDate" size="10" maxlength="10"/>
	                      <script language="JavaScript" type="text/javascript">
							new tcal ({
								// form name
								'formname': 'hostelAllocationForm',
								// input name
								'controlname': 'allocationDate'
							});</script>
						</td>	
                  </tr>
               
                <tr >
                
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.roomno"/>:</div></td>
                  <td height="25" class="row-even" ><html:select property="roomNo" styleId="roomNo" styleClass="combo" onchange="getBeds()">
						<html:option value="">- Select -</html:option>
						<c:if test="${roomMap != null}">
						<html:optionsCollection name="roomMap" label="value" value="key"/>
						</c:if>
					
					</html:select></td>
					
				<td class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.bedno"/>:</div></td>
                  <td class="row-even"><html:select property="bedNo" styleId="bedNo" styleClass="combo">
						<html:option value="">- Select -</html:option>
									<c:if test="${bedMap != null}">
						<html:optionsCollection name="bedMap" label="value" value="value"/>
						</c:if>
					</html:select></td>
                </tr>
                
            </table></td>
            <td width="5" height="62"  background="images/right.gif"></td>
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
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="30%" height="35">&nbsp;</td>
            <td width="10%"><html:button property="" styleClass="formbutton"
												onclick="submitAllocationDetails()">
												<bean:message key="knowledgepro.submit" />
											</html:button></td>
            <td width="8%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset" />
											</html:button></td>
			<td width="8%">
				<html:cancel styleClass="formbutton" onclick="submitAllocationDetails()"></html:cancel>
			</td>								
            <td width="44%">&nbsp;</td>
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
</body>
