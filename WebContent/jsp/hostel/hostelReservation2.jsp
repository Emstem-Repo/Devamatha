<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
</head>
<script type="text/javascript">
function cancel() {
	document.location.href = "HostelReservation.do?method=initHostelReservation";
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 250;
	return (Object.value.length < MaxLen);
}
function submitReservation(){
		document.hostelReservationForm.submit();
}
function getRooms(floorNo) {
	var hostelId=document.getElementById("hostelApprovedId").value;
	var roomTypeId = document.getElementById("RoomTypeApprovedId").value;
	getRoomsByFloors("roomMap", hostelId,floorNo,roomTypeId,"roomId",updateRooms);
	}
	
	function updateRooms(req) {
		updateOptionsFromMap(req, "roomId", "- Select -");
	}

</script>
<html:form action="HostelReservation" method="post">
<html:hidden property="method" styleId="method" value="submitReservationDetails"/>
<html:hidden property="formName" value="hostelReservationForm" />
<html:hidden property="roomTypeName" styleId="roomTypeName"/>
<html:hidden property="pageType" value="2"/>
<input type="hidden" name="applicationId" id="applicationId"
		value='<bean:write name="hostelReservationForm" property="applicantHostelDetails.applicationId" />' />
<input type="hidden" name="isStaff" id="isStaff"
		value='<bean:write name="hostelReservationForm" property="applicantHostelDetails.isStaff" />' />		
<input type="hidden" name="hostelApprovedId" id="hostelApprovedId"
		value='<bean:write name="hostelReservationForm" property="applicantHostelDetails.hostelId" />' />
<input type="hidden" name="RoomTypeApprovedId" id="RoomTypeApprovedId"
		value='<bean:write name="hostelReservationForm" property="applicantHostelDetails.roomTypeId" />' />
<table width="99%" border="0">
  <tr>
    <td><span class="heading"><a href="#" class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.reservation"/>  &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" ></td>
        <td colspan="2" background="images/Tcenter.gif" class="body" ><div align="left" class="heading_white"><bean:message key="knowledgepro.hostel.reservation"/></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
	  <tr>
	  	<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
    	<td height="20" colspan="2" class="news">
			<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT></div>
		</td>
		<td height="20" valign="top" background="images/Tright_3_3.gif" class="news"></td>
   	  </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <logic:equal value="true" property="applicantHostelDetails.isStaff" name="hostelReservationForm">
        	<td width="479" height="20" class="heading">Staff Information</td>
        </logic:equal>
        <logic:notEqual value="true" property="applicantHostelDetails.isStaff" name="hostelReservationForm">
        	<td width="479" height="20" class="heading">Student Information</td>
        </logic:notEqual>
        <td width="465" height="20" class="heading"><bean:message key="admissionForm.studentinfo.currAddr.label"/></td>
        <td height="20" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="heading"><table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
                  <td height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.name.col"/></div></td>
                      <td height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.hostelName" /></td>
                    </tr>
                    <tr >
                      <td width="45%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentedit.name.label"/></div></td>
                        <td width="55%" height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.applicantName" /></td>
                    </tr>
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.interview.DateofBirth"/></div></td>
                        <td height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.dateOfBirth" /></td>
                    </tr>
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admin.caste.report.col"/></div></td>
                        <td height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.caste" /></td>
                      </tr>
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.nationality.label"/></div></td>
                        <td height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.nationality" /></td>
                      </tr>
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.sex.label"/></div></td>
                        <td height="25" class="row-even"><bean:write name="hostelReservationForm" property="applicantHostelDetails.gender" /></td>
                      </tr>
                    <tr >
                      <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.foodPreference"/></div></td>
                      <td height="25" class="row-even"><img src="images/icn_nonveg.gif" width="22" height="22">
                        <input type="radio" name ="isVeg" value="false" id ="nonVeg" checked><label></label>
                        <img src="images/icn_veg.gif" width="22" height="22">
                        <input type="radio" name ="isVeg" id ="veg" value="true"><label></label></td>
                    </tr>
                    
                  </table></td>
                  <td  background="images/right.gif" width="10" height="29"></td>
                </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
        </table></td>
        <td height="94" valign="top" class="heading"><div align="right">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5"></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5"></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td height="91" valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr class="row-white">
                  <td width="43%" height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
                  <td width="57%" height="25" class="row-even"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.addressLine1" /></div></td>
                </tr>
                <tr class="row-white">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
                  <td height="25" class="row-even"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.addressLine2" /></div></td>
                </tr>
                <tr class="row-white">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.country.label"/></div></td>
                  <td height="25" class="row-even"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.country" /></div></td>
                </tr>
                <tr class="row-white">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
                  <td height="25" class="row-even"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.state" /></div></td>
                </tr>
                <tr class="row-white">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.addrs1.city.label"/></div></td>
                  <td height="25" class="row-even"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.city" /></div></td>
                </tr>
                <tr class="row-even">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
                  <td height="25"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.zipCode" /></div></td>
                </tr>
                <tr class="row-even">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
                  <td height="25"><div align="left"><bean:write name="hostelReservationForm" property="applicantHostelDetails.phone" /></div></td>
                </tr>
                <tr class="row-even">
                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
                  <td height="25">
                  <div align="left">
                  	<bean:write name="hostelReservationForm" property="applicantHostelDetails.email" />
                  	<input type="hidden" name="hlAppFormId" id="hlAppFormId" 
                  	value='<bean:write name="hostelReservationForm" property="applicantHostelDetails.hlAppFormId" />' />
                  	</div></td>
                </tr>
                
              </table></td>
              <td  background="images/right.gif" width="5" height="5"></td>
            </tr>
            <tr>
              <td height="5"><img src="images/04.gif" width="5" height="5"></td>
              <td background="images/05.gif"></td>
              <td><img src="images/06.gif"></td>
            </tr>
          </table>
        </div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>

	<tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="heading">Room Information</td>
        <td height="20" class="heading">Reservation Date</td>
        <td height="20" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="45" rowspan="3" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="45" rowspan="3" valign="top" class="heading"><table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5"></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5"></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="44%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.room.floor.no" /></div></td>
                  <td width="56%" height="25" class="row-even">
                  
                  	<html:select name="hostelReservationForm" property="floorNo" styleId="floorNo" styleClass="combo" onchange="getRooms(this.value)">
						<html:option value="">- Select -</html:option>
						<c:if test="${hostelReservationForm.hostelId != null && hostelReservationForm.hostelId != ''}">							
							<html:optionsCollection name="floorMap" label="value"	value="key" />							
						</c:if>
					</html:select> 
                  </td>
                </tr>
                <tr>
                  	<td height="25" valign="top" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.checkin.roomNo" /></div></td>
                  	<td height="25" valign="top" class="row-even">
					
						<html:select name="hostelReservationForm" property="roomId" styleId="roomId" styleClass="combo">
							<html:option value="">- Select -</html:option>
							<c:if test="${hostelReservationForm.hostelId != null && hostelReservationForm.hostelId != ''}">							
								<html:optionsCollection name="roomMap" label="value"	value="key" />							
							</c:if>
						</html:select> 
					
					</td>
                </tr>
                <tr>
                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.reservation.remarks" /></div></td>
                  <td height="25" class="row-even"><html:textarea
								property="remarks" cols="20" rows="5" styleId="remarks"
								onkeypress="return imposeMaxLength(event,this)"></html:textarea>

                    </td>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="10" height="29"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif"></td>
          </tr>
        </table>
</td>

<td height="67" valign="top" class="heading"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td height="29" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr >
                  <td width="30%" height="25" class="row-odd"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.reservation.reservationDate"/>:</div></td>
                  <td height="25" colspan="3" class="row-even"><html:text name="hostelReservationForm" property="reservationDate" styleId="reservationDate" size="10" maxlength="10"/>
                      <script language="JavaScript" type="text/javascript">
						new tcal ({
							// form name
							'formname': 'hostelReservationForm',
							// input name
							'controlname': 'reservationDate'
						});</script></td>
                </tr>
            </table></td>
            <td  background="images/right.gif" width="10" ></td>

          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td rowspan="3" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" class="heading"></td>
      </tr>
      <tr>
        <td valign="top" class="heading"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news"><div align="center">
            <table width="100%" height="27"  border="0" cellpadding="1" cellspacing="2">
              <tr>
                <td colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="45%"><div align="center">
                      <html:button property="" styleClass="formbutton" onclick="submitReservation()"><bean:message key="knowledgepro.submit" /></html:button>
						&nbsp;&nbsp; <html:button property="" styleClass="formbutton" onclick="javascript:cancel()">
							<bean:message key="knowledgepro.cancel" />
						</html:button>
                      </div></td>
                      </tr>
                </table></td>
              </tr>
            </table>
        </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td colspan="2" valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td colspan="2"  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
