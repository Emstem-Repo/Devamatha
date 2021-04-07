<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<html:html>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function cancel(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function deleteDetails(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "availableSeats.do?method=deleteAvailableSeats&id=" + id;
	}
}
function editAvailableSeats(id) {
	document.location.href = "availableSeats.do?method=editAvailableSeatsDetails&id=" + id;
}
function resetErrMsgs(){
	document.location.href = "HostelAdmission.do?method=initUploadRoomDetails"; 
}
</script>
<html:form action="/HostelAdmission" method="POST" enctype="multipart/form-data">
<html:hidden property="method" styleId="method" value="uploadRoomDetails" />
<html:hidden property="formName" value="hlAdmissionForm"/>
<html:hidden property="pageType" value="2"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="aknowledgepro.hostel.uploadroomdetails.display" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
  	<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="30"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="aknowledgepro.hostel.uploadroomdetails.display" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
     <tr>
     <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
		<div id="errorMessage"> 
		<html:errors/><FONT color="green">
			<html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out><br>
				</html:messages>
			  </FONT>
			</div></td>
		<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            	<table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
            	<tr class="row-white">
                    <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
                     <td width="25%" class="row-even"> 
                   		 <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="hlAdmissionForm" property="academicYear"/>"/>
                		<html:select styleId="academicYear"  property="academicYear" name="hlAdmissionForm" styleClass="combo" >
							<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
							<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
						</html:select></td>
                    <td class="row-odd" width="25%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hostel.name.col"/>
						</div>
					</td>
					<td  class="row-even" width="25%">
					<input type="hidden" id="hId" name="hId" value="<bean:write name="hlAdmissionForm" property="hostelId"/>" />
						<html:select property="hostelId" styleId="hostelId"  styleClass="combo">
							<html:option value="">--Select--</html:option>
							<logic:notEmpty property="hostelMap" name="hlAdmissionForm">
						   		<html:optionsCollection property="hostelMap" label="value" value="key"/>
						   	</logic:notEmpty>
						</html:select> 
					</td>
                  </tr>
			  		<tr>
			  			<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="aknowledgepro.hostel.uploadroomdetails.display"/>:
						</div>
						</td>
						<td height="25" class="row-even"><label>
							<html:file property="theFile" styleId="thefile" size="15" maxlength="30" name="hlAdmissionForm"/></label>
						</td>
					</tr>
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
         <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
           <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td width="45%" height="35">
					</td>
					<td width="5%" height="35">
						<div align="center"><html:submit styleClass="formbutton">
						<bean:message key="knowledgepro.submit" /></html:submit>
						</div>
					</td>
					<td width="5%" height="35">
						<html:button property="" styleClass="formbutton" onclick="resetErrMsgs();">
						<bean:message key="knowledgepro.admin.reset" /></html:button>
					</td>
					<td width="45%" height="35">
						<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
					</td>
				</tr>
				</table>
			</td>
	          </tr>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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
</html:html>
                  

