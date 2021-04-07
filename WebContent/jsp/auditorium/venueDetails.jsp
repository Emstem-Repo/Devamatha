<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
  $('#Submit').click(function(){
       var venueName = $('#venueName').val();
       var email = $('#emailId').val();
       var blockId = $('#blockId').val();
       var roomNo = $('#roomNo').val();
       var facilityAvailable=$('#facilityAvailable').val();
       if(venueName== '' && email=='' && blockId=='' && roomNo=='' && facilityAvailable==''){
    	   $('#errorMessage').slideDown().html("<span>Please enter Venue Name <br> select Block <br> Enter RoomNo <br> Enter Email Id.<br> Enter Facilty Available.</span>");
          return false;
        }
       else if(email== ''){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Email Id.</span>");
           return false;
        }
       else if(IsEmail(email)==false){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Valid Email Id.</span>");
            return false;
        }

       else if(roomNo== ''){
        	 $('#errorMessage').slideDown().html("<span>Please Enter Room No.</span>");
            return false;
        }
       else if(blockId== ''){
        	 $('#errorMessage').slideDown().html("<span>Please select Block.</span>");
            return false;
        }else if(facilityAvailable== ''){
       	 $('#errorMessage').slideDown().html("<span>Please Enter Facility Available.</span>");
         return false;
     }
       else{
    	   document.getElementById("method").value = "addVenueDetails";
   		   document.venueDetailsForm.submit();
       }
      });
     function IsEmail(email) {
	    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	    if(!regex.test(email)) {
	       return false;
	    }else{
	       return true;
	    }
    }
});	
	function editVenue(id) {
		document.location.href = "venueDetails.do?method=editVenueDetails&id="+id;
	}

	function updateVenue() {
		document.getElementById("method").value = "updateVenueDetails";
		resetErrMsgs();
		document.venueDetailsForm.submit();
	}
	function deleteVenue(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "venueDetails.do?method=deleteVenueDetails&id="+id;
		}
	}
	function resetVenue() {
		document.getElementById("venueName").value = "";
		document.getElementById("blockId").value = "";
		document.getElementById("roomNo").value = "";
		document.getElementById("errorMessage").value = "";
		document.getElementById("facilityAvailable").value = "";
		document.getElementById("emailId").value = "";
		if(document.getElementById("method").value=="updateVenueDetails"){
			 document.getElementById("venueName").value=document.getElementById("origVenueName").value;
			 document.getElementById("blockId").value=document.getElementById("origBlockId").value;
			 document.getElementById("roomNo").value=document.getElementById("origRoomNo").value;
			 document.getElementById("emailId").value=document.getElementById("origEmailId").value;
			 document.getElementById("facilityAvailable").value=document.getElementById("origFacilityAvailable").value;
		}
		resetErrMsgs();

//	 resetFieldAndErrMsgs();
	}
	function reActivate() {
		document.location.href = "venueDetails.do?method=reactivateVenueDetails";
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
</script>

<html:form action="/venueDetails" method="post">
	<html:hidden property="formName" value="venueDetailsForm" />
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="origBlockId" name="venueDetailsForm" styleId="origBlockId"/>
	<html:hidden property="origVenueName" name="venueDetailsForm" styleId="origVenueName"/>
	<html:hidden property="origRoomNo" name="venueDetailsForm" styleId="origRoomNo"/>
	<html:hidden property="origEmailId" name="venueDetailsForm" styleId="origEmailId"/>
	<html:hidden property="origFacilityAvailable" name="venueDetailsForm" styleId="origFacilityAvailable"/>
	
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateVenueDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addVenueDetails" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>

			<td><span class="Bredcrumbs"> <bean:message
				key="knowledgepro.auditorium" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.auditorium.venue" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.auditorium.venue" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="452" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<font color="red" size="2"><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div></font>
							</td>
						</tr>

						<tr>
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
                            <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.auditorium.venue.name" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text property="venueName" name="venueDetailsForm" styleClass="TextBox" styleId="venueName"></html:text>
                            </div>
							</td>
							<td width="25%" height="25" class="row-odd">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.auditorium.block.name" />:</div>
							</td>
							<td width="25%" height="25" class="row-even"><span
								class="star"> <html:select property="blockId"
								 styleId="blockId">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="venueDetailsForm" property="blockMap"
									label="value" value="key" />
							</html:select></span></td>
						</tr>
						<tr>
							<td height="25" class="row-odd" width="25%">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.hostel.roomno" /></div>
							</td>
							<td height="25" class="row-even"><span class="star">
							<html:text property="roomNo" styleClass="TextBox"
								styleId="roomNo" name="venueDetailsForm" />
							</span></td>
							 <td width="25%" height="25" class="row-odd">
                            <div align="right"><span class="Mandatory">*</span><bean:message
								key="admissionFormForm.emailId" />:</div>
							</td>
							<td width="25%" height="25" class="row-even">
                            <div align="left">
                            <html:text property="emailId" name="venueDetailsForm" styleClass="TextBox" styleId="emailId"></html:text>
                            </div>
							</td>
						</tr>
						<tr>
							<td height="25" class="row-odd" width="25%">
							<div align="right"><span class="Mandatory">*</span><bean:message
								key="knowledgepro.auditorium.venue.facilityAvailable" /></div>
							</td>
							<td height="25" class="row-even" colspan="3"><span class="star">
							<html:textarea property="facilityAvailable" styleClass="TextBox"
								styleId="facilityAvailable" name="venueDetailsForm" cols="35"/>
							</span></td>
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
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${operation == 'edit'}">
											<html:button property="" styleClass="formbutton"
												value="Update" onclick="updateVenue()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton"
												value="Submit"  styleId="Submit"></html:button>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"></td>
									<td width="53%">
							              <html:button property="" styleClass="formbutton"
												value="Reset" onclick="resetVenue()" styleId="reset"></html:button>
										</td>
								</tr>
							</table>
							</td>
						</tr>

					<tr>
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
							<td height="25" colspan="4">
							<table width="100%" cellspacing="1" cellpadding="2">

								<tr>

									<td width="10%" height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td width="18%" height="25" class="row-odd" align="center"><bean:message
										key="knowledgepro.auditorium.venue.name" /></td>
									<td width="18%" class="row-odd" align="center"><bean:message
										key="knowledgepro.auditorium.block.name"/></td>
									<td width="18%" class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.hostel.roomno" /></div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td width="18%" class="row-odd">
									<div align="center">Delete</div>
									</td>
								</tr>
								<c:set var="slnocount" value="0" />
								<logic:iterate name="venueDetailsForm" property="venuesTO" id="venue" indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>

									<td width="5%" height="25" align="center">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td width="9%" height="25" align="center"><bean:write
										name="venue" property="venueName"/> </td>
									<td width="9%" height="25" align="center"><bean:write
										name="venue" property="blockName"/> </td>
									<td width="9%" height="25" align="center"><bean:write
										name="venue" property="roomNo"/> </td>
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18"  style="cursor:pointer" 
										onclick="editVenue('<bean:write name="venue" property="id"/>')">
									</div>
									</td>
									<td width="5%" height="25" align="center">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer" 
										onclick="deleteVenue('<bean:write name="venue" property="id"/>')">
									</div>
									</td>
								</logic:iterate>
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


					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>

				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>