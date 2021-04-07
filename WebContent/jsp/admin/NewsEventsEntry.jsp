<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%>
<html>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/newsEventsDetails.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
 
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">





</script>


</head>
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="selectedNewsEventsId" styleId="NEC"/>
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="mode" styleId="mode" value="" />
<html:hidden property="formName" value="NewsEventsEntryForm" />
<html:hidden property="selectedPhotoId" styleId="selectedPhotoId"/>
<html:hidden property="pageType" value="1" />
<html:hidden property="screen"/>
<html:hidden property="resourseListSize" name="NewsEventsEntryForm" styleId="resourseListSize"/>
<html:hidden property="orgResListSize" name="NewsEventsEntryForm" styleId="orgResListSize"/>
<html:hidden property="photoListSize" name="NewsEventsEntryForm" styleId="photoListSize"/>
<html:hidden property="orgphotoListSize" name="NewsEventsEntryForm" styleId="orgphotoListSize"/>
<html:hidden property="contactListSize" name="NewsEventsEntryForm" styleId="contactListSize"/>
<html:hidden property="orgContactListSize" name="NewsEventsEntryForm" styleId="orgContactListSize"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.mobNewsEventsDetails"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsEntry"/> </td>
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
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="0">
              <tr>
              	<td width="20%" class="row-odd" align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.news.events"/>:</td>
              	<td colspan="3" class="row-even" >
              		<html:radio property="newsOrEvents" styleId="newsOrEvents" value="Events"/>Events&nbsp;
              	 	<html:radio property="newsOrEvents" styleId="newsOrEvents" value="News"/>News 
				 	
              	</td>
              </tr>
              <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
               <td width="30%" height="25" class="row-even" >
				 <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="NewsEventsEntryForm" property="academicYear"/>"/>
                 <html:select property="academicYear" styleId="academicYear" styleClass="combo">
  	   				 <html:option value="">- Select -</html:option>
  	   				<cms:renderNewsEventsYear></cms:renderNewsEventsYear>
   			   </html:select>
				</td>
		 		<td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="eventTitle" styleId="eventTitle" size="40" maxlength="500"/>
                </span>
                </td>
             </tr>
             <tr >
                <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="dateFrom" styleId="dateFrom" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#dateFrom").datepicker(pickerOpts);
										});
                  </script>
                  </td>
                  <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="dateTo" styleId="dateTo" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#dateTo").datepicker(pickerOpts);
										});
                  </script>
                  </td>
              </tr>
              <!-- <tr >
                <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsIconImage"/>:</div></td>
                <td width="32%" height="25" class="row-even" colspan="3">
                    <span class="star">
                    <html:file property="iconTmageFile" styleId="iconTmageFile" size="20" maxlength="20"/>
                  </span></td>
                
              </tr>-->
              <tr>
              
                <td width="20%" height="25" class="row-odd" ><div align="left">Participants:</div></td>
                <td  class="row-even">
								 	 <html:select property="participants" styleId="participants" styleClass="comboMedium" >
								 	 <html:option value="">- Select - </html:option>
								   		<html:option value="Student">Student</html:option>
								   		<html:option value="Staff">Staff</html:option>
								   		<html:option value="Organizations">Organizations</html:option>
										<html:option value="General Public">General Public</html:option>
								   	</html:select>
				 </td>
				 <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                   <html:select property="categoryId"   styleId="categoryId" name="NewsEventsEntryForm" styleClass="comboMedium" onclick="calculateDates();">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="NewsEventsEntryForm" property="categoryList">
									<html:optionsCollection property="categoryList" name="NewsEventsEntryForm" label="value" value="key" />
									</logic:notEmpty>
					</html:select>
                  </span>
                </td>
            </tr>
       <tr>
       				<td height="25" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="KnowledgePro.news.events.live.telecast"/></div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="isLiveTelecast" styleId="isLiveTelecast" value="Yes"/>Yes&nbsp; 
				 <html:radio property="isLiveTelecast" styleId="isLiveTelecast" value="No"/>No
                 </span></td>
       		
       	<td height="25" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="knowledgepro.pgm.organisedBy"/> </div></td>
                <td  class="row-even">
						<html:select property="organizedBy" styleId="organizedBy" styleClass="comboMedium" onchange="ShowHideDropDowns()">
								 	 <html:option value="">- Select - </html:option>
								   		<html:option value="Course">Course</html:option>
								   		<html:option value="Department">Department</html:option>
										<html:option value="Deanery">Deanery</html:option>
										<html:option value="Special Centers">Special Centers</html:option>
						</html:select>
				</td>
	</tr>
	 <tr id="departmentTitle">
              		<td class="row-odd" ><span class="Mandatory">*</span>
              		 <bean:message key="knowledgepro.employee.Department"/></td>
                	<td class="row-even" colspan="3">
                <html:select property="departmentId" styleId="departmentId" styleClass="comboMedium">
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="departmentMap" name="NewsEventsEntryForm">
						<html:optionsCollection property="departmentMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
				</td>
				
	</tr>
	
	 <tr id="streamTitle">
         
			   <td class="row-odd" ><span class="Mandatory">*</span><bean:message key="knowledgepro.employee.DeaneryDetails"/></td>
               <td class="row-even" colspan="3">
                 <html:select property="streamId" styleId="streamId" styleClass="comboMedium" >
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="streamMap" name="NewsEventsEntryForm">
						<html:optionsCollection property="streamMap" label="value" value="key"/>
					 </logic:notEmpty>
				</html:select>
               </td>
				
	</tr>
	
	 <tr id="courseTitle">
         
			   <td class="row-odd" ><span class="Mandatory">*</span>
                <bean:message key="knowledgepro.admission.course"/></td>
                 <td class="row-even" colspan="3">
                  <html:select property="courseId" styleId="courseId" styleClass="comboMedium" >
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="courseMap1" name="NewsEventsEntryForm">
						<html:optionsCollection property="courseMap1" label="value" value="key"/>
					 </logic:notEmpty>
				  </html:select>
                 
              	</td>
				
	</tr>
	
	<tr id="SplCenterTitle">
         
			    <td  class="row-odd" ><span class="Mandatory">*</span>Special Centers</td>
			    <td class="row-even" colspan="3">
                 <html:select property="splCenterId" styleId="splCenterId" styleClass="comboMedium" >
					  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
						<logic:notEmpty property="splCenterMap" name="NewsEventsEntryForm">
						<html:optionsCollection property="splCenterMap" label="value" value="key"/>
					 </logic:notEmpty>
				 </html:select>
                      	</td>
				
	</tr>
       <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsNewsDescription"/>:</div></td>
                <td colspan="3" width="30%" height="25" class="row-even" >
                    <span class="star">
                    	<html:textarea property="eventDescription" styleId="eventDescription"  cols="80" rows="4" onkeypress="return imposeMaxLength(this, 0, 999);" 
															onchange="return imposeMaxLength(this, 0, 999);" onkeyup="len_display(this,0,'long_len')"/>
					</span></td>
				</tr>
				<tr>
				<td colspan="4" align="right" class="row-even">
						<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/1000 Characters
				</td>						
                  
        </tr>
		<tr>
                <td height="25" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="KnowledgePro.news.events.registration.required1"/></div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="isRegistrationRequired" styleId="RegistrationYes" value="Yes" onclick="showHideRegistRadio()"/>Yes&nbsp; 
				 <html:radio property="isRegistrationRequired" styleId="RegistrationNo" value="No" onclick="showHideRegistRadio()"/>No
                 </span></td>
             
                <td height="25" class="row-odd"><div align="left" ><span class="Mandatory">*</span><bean:message key="KnowledgePro.news.events.invitation.required"/></div></td>
                <td class="row-even"><span class="star">
                 <html:radio property="isInvitationMailRequired" styleId="InvitationYes" value="Yes" onclick="showHideInvitationRadio()"/>Yes&nbsp; 
				 <html:radio property="isInvitationMailRequired" styleId="InvitationNo" value="No" onclick="showHideInvitationRadio()"/>No
                 </span></td>
         </tr>
         <tr id="Registration">
          <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.newsEntry.registration.form"/>:</div></td>
          <td height="25" class="row-even">
           <span class="star">
                  <html:file property="registrationForm" styleId="registrationForm" size="20" maxlength="150" onblur="CheckRegistrationFiles()"/>
           </span><div>(doc/pdf/xls)</div></td>
           <logic:notEmpty property="regFormName" name="NewsEventsEntryForm">
           <html:hidden property="regFormName" name="NewsEventsEntryForm"/>
                   			<td class="row-even" align="left" colspan="2"> 
                   			<logic:equal value="true" property="regIsImage" name="NewsEventsEntryForm">
                   				<img src="images/View_icon.gif"
								width="16" height="18"  style="cursor:pointer" 
								onclick="getRegistration('<bean:write property="regFormName" name="NewsEventsEntryForm"/>')">
							</logic:equal>
           			 		<logic:equal value="false" property="regIsImage" name="NewsEventsEntryForm">
            					<a href="#" onclick="getDownloadFile('<bean:write property="regFormName" name="NewsEventsEntryForm"/>','Registration')"><bean:message key="knowledgepro.reports.submit" /></a>
            				</logic:equal>
           			 	</td>
           			 	</logic:notEmpty>
           			<logic:empty property="regFormName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left" colspan="2"> </td>
           			</logic:empty>
          </tr>
          
          <tr id="invitation">
				<td height="25" class="row-odd">
               			 <div align="left"><bean:message key="knowledgepro.admin.newsEntry.Invitation.mail"/>:</div></td>
                			<td height="25" class="row-even" align="left">
                    		<span class="star">
                    		<html:file property="invitationMail" styleId="invitationMail" size="20" maxlength="20" onblur="CheckInvitation();" />
                    		
                 			 </span><div>(doc/xls/pdf) OR (jpg/png/bmp/gif)</div></td>
                 			 <logic:notEmpty property="invitationName" name="NewsEventsEntryForm">
                 			 <html:hidden property="invitationName" name="NewsEventsEntryForm"/>
                   			<td class="row-even" align="left" colspan="2"> 
                   			<logic:equal value="true" property="invitationIsImage" name="NewsEventsEntryForm">
                   				<img src="images/View_icon.gif"
								width="16" height="18"  style="cursor:pointer" 
								onclick="getInvitation('<bean:write property="invitationName" name="NewsEventsEntryForm"/>')">
							</logic:equal>
           			 		<logic:equal value="false" property="invitationIsImage" name="NewsEventsEntryForm">
            					<a href="#" onclick="getDownloadFile('<bean:write property="invitationName" name="NewsEventsEntryForm"/>','Invitation')"><bean:message key="knowledgepro.reports.submit" /></a>
            				</logic:equal>
           			 	</td>
           			 	</logic:notEmpty>
           			 	 <logic:empty property="invitationName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left" colspan="2"> </td>
           			 	</logic:empty>
             </tr>
              
              <tr> 
				<td colspan="2" class="heading" align="left">Guest(s)/Resource Persons(s)</td>
			</tr>
		<tr>
			<td valign="top" class="news" colspan="4">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
				<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			
             <tr class="row-odd">
                <td  class="row-odd" width="30%"><div align="left" ><bean:message key="knowledgepro.admin.name"/></div></td>
                <td class="row-odd" width="20%"><div align="left" ><bean:message key="knowledgepro.exam.email"/></div></td>
                <td  class="row-odd" width="20%"><div align="left" ><bean:message key="knowledgepro.employee.tel"/></div></td>
                <td  class="row-odd" width="30%"><div align="left" ><bean:message key="knowledgepro.admin.Other.information"/></div></td>
			</tr>
				<tr>
			<td colspan="4">
			<table width="100%" class="row-even" cellspacing="1" cellpadding="2" id="addResourseRowTable">
			<logic:notEmpty property="resourseTO" name="NewsEventsEntryForm">          
				<nested:iterate property="resourseTO" name="NewsEventsEntryForm" indexId="countRes">	
				<%String email_count="email_resourse_"+countRes;
				String avgMethod="checkEmail("+countRes+")"; %>											
			<c:choose>
				<c:when test="${countRes == 0}">
				
				<tr>
				 <td align="justify" width="30%">
					<nested:text  property="resourseName" styleId="resourseName" size="25" maxlength="150"></nested:text></td>
				 <td class="row-even" width="20%">
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);"></nested:text>
				</td>
				<td class="row-even" width="20%">
				 	<nested:text  property="contactNo" styleId="contactNo" onkeypress="return isNumberKey(event)" size="20" maxlength="20"></nested:text></td>
				 <td class="row-even" width="30%">
					<nested:textarea property="otherInfo" styleId="otherInfo" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
			 </tr>
			</c:when>
			<c:otherwise>
			<nested:notEmpty property="resourseName">
		<tr>
				 <td align="justify" width="30%">
					<nested:text  property="resourseName" styleId="resourseName" size="25" maxlength="150"></nested:text></td>
				 <td class="row-even" width="20%">
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);" ></nested:text>
				</td>
				<td class="row-even" width="20%">
				 	<nested:text  property="contactNo" styleId="contactNo" onkeypress="return isNumberKey(event)" size="20" maxlength="20" ></nested:text></td>
				 <td class="row-even" width="30%">
					<nested:textarea property="otherInfo" styleId="otherInfo" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
			 </tr>
		</nested:notEmpty>
	</c:otherwise>
	</c:choose>
	</nested:iterate>
	</logic:notEmpty>
	</table>
	</td>
	</tr>
              <tr>
            <td class="row-odd" align="center" colspan="4"> <html:button property="" styleClass="formbutton" value="Add more" styleId="addMore" onclick="addResourseRow()"></html:button>
             <c:if test="${NewsEventsEntryForm.resourseListSize>0}">
		    <html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removeResourseRow()"></html:button>
		     </c:if>
             </td>
             </tr>
 </table></td>
				<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					 
              <tr> 
				<td colspan="2" class="heading" align="left">Contact Details</td>
			</tr>
		<tr>
			<td valign="top" class="news" colspan="4">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
				<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			
             <tr class="row-odd">
                <td  class="row-odd" width="30%"><div align="left" ><bean:message key="knowledgepro.admin.name"/></div></td>
                <td class="row-odd" width="20%"><div align="left" ><bean:message key="knowledgepro.exam.email"/></div></td>
                <td  class="row-odd" width="20%"><div align="left" ><bean:message key="knowledgepro.employee.tel"/></div></td>
                <td  class="row-odd" width="30%"><div align="left" ><bean:message key="knowledgepro.hostel.reservation.remarks"/></div></td>
			</tr>
				<tr>
			<td colspan="4">
			<table width="100%" class="row-even" cellspacing="1" cellpadding="2" id="addContactRowTable">
			<logic:notEmpty property="contactTO" name="NewsEventsEntryForm">          
				<nested:iterate property="contactTO" name="NewsEventsEntryForm" indexId="countContact">	
				<%String email_count="email_contact_"+countContact;
				String avgMethod="checkContactEmail("+countContact+")"; %>
				<c:choose>
				<c:when test="${countContact == 0}">
				<tr>
				 <td align="justify" width="30%">
					<nested:text  property="name" styleId="name" size="25" maxlength="150"></nested:text></td>
				 <td class="row-even" width="20%">
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);" ></nested:text>
				</td>
				<td class="row-even" width="20%">
				 	<nested:text  property="contactNo" styleId="contactNo" onkeypress="return isNumberKey(event)" size="20" maxlength="20"></nested:text></td>
				 <td class="row-even" width="30%">
					<nested:textarea property="remarks" styleId="remarks" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
			 </tr>
			</c:when>
			<c:otherwise>
			<nested:notEmpty property="name">
		<tr>
				 <td align="justify" width="30%">
					<nested:text  property="name" styleId="name" size="25" maxlength="150"></nested:text></td>
				 <td class="row-even" width="20%">
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);" ></nested:text>
				</td>
				<td class="row-even" width="20%">
				 	<nested:text  property="contactNo" styleId="contactNo" onkeypress="return isNumberKey(event)" size="20" maxlength="20" ></nested:text></td>
				 <td class="row-even" width="30%">
					<nested:textarea property="remarks" styleId="remarks" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
			 </tr>
		</nested:notEmpty>
	</c:otherwise>
	</c:choose>
	</nested:iterate>
	</logic:notEmpty>
	</table>
	</td>
	</tr>
              <tr>
            <td class="row-odd" align="center" colspan="4"> <html:button property="" styleClass="formbutton" value="Add more" styleId="addMore" onclick="addContactRow()"></html:button>
             <c:if test="${NewsEventsEntryForm.contactListSize>0}">
		    <html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removeContactRow()"></html:button>
		     </c:if>
             </td>
             </tr>
 </table></td>
				<td width="5" height="30" background="images/right.gif"></td>
							</tr>
							<tr>
								<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
								<td background="images/05.gif"></td>
								<td><img src="images/06.gif" /></td>
							</tr>
						</table>
						</td>
					</tr>
					
      <!--<tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">-->
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                   <c:choose>
            		<c:when test="${mobNewsEventCategoryOperation == 'edit'}">
              	   		<html:button property="" styleClass="formbutton" onclick="updateMobNewsEventsDetails()"><bean:message key="knowledgepro.update"/></html:button>
              		</c:when>
              		<c:otherwise>
                		<html:button property="" styleClass="formbutton" onclick="addMobNewsEventsDetails()">Submit For Approval</html:button>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
					<html:button property="" styleClass="formbutton" value="Cancel" onclick="Cancel()">
										<bean:message key="knowledgepro.cancel"/></html:button>
				</td>
              </tr>
            </table></td>
          </tr>
         
          
           <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
               <!--  --> <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" align="left" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/></td>
                    <td height="25" class="row-odd" align="left" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/></td>
                    <td height="25"  class="row-odd" align="left" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/></td>
                    <td height="25"  class="row-odd" align="left" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/></td>
                     <td height="25"  class="row-odd" align="left" ><bean:message key="knowledgepro.pgm.organisedBy"/></td>
                     <td height="25"  class="row-odd" align="left" ><bean:message key="knowledgepro.admission.status"/></td>
                    <td class="row-odd"><div align="left"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="left"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  
                  
                 
        <c:set var="temp" value="0"/>
           <logic:notEmpty name="NewsEventsEntryForm" property="mobNewsEventsDetails">
              <logic:iterate id="NEC1" name="NewsEventsEntryForm" property="mobNewsEventsDetails" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="left"><c:out value="${count + 1}"/></div></td>
                   		<td width="25%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="eventTitle"/></td>
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="category"/></td>
                   		<td width="15%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="dateFrom"/></td>
                   		<td width="15%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="dateTo"/></td>
                   		<logic:equal value="Course" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="courseName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Department" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="departmentName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Deanery" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="streamName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Special Centers" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="splCentreName"/></td>
                   		</logic:equal>
                   		<td width="20%" height="25" class="row-even" align="left"><bean:write name="NEC1" property="status"/></td>
			           
			            <td width="6%" height="25" class="row-even" ><div align="center">
			             <logic:equal value="true" property="isEditable" name="NEC1">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editMobNewsEventsDetails('<bean:write name="NEC1" property="id"/>')">
			        		</logic:equal></div></td>
                   		<td width="6%" height="25" class="row-even" ><div align="center">
                   		 <logic:equal value="true" property="isEditable" name="NEC1">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteMobNewsEventsDetails('<bean:write name="NEC1" property="id"/>')">
                   			</logic:equal></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="left"><c:out value="${count + 1}"/></div></td>
               			<td width="25%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="eventTitle"/></td>
               			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="category"/></td>
               			<td width="15%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="dateFrom"/></td>
               			<td width="15%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="dateTo"/></td>
               			<logic:equal value="Course" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="courseName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Department" property="orgBy" name="NEC1"> 
                   		<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="departmentName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Deanery" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="streamName"/></td>
                   		</logic:equal>
                   		<logic:equal value="Special Centers" property="orgBy" name="NEC1">
                   		<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="splCentreName"/></td>
                   		</logic:equal>
               			<td width="20%" height="25" class="row-white" align="left"><bean:write name="NEC1" property="status"/></td>
              
               			<td width="6%" height="25" class="row-white" ><div align="center">
               			<logic:equal value="true" property="isEditable" name="NEC1">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editMobNewsEventsDetails('<bean:write name="NEC1" property="id"/>')"> 
               			</logic:equal>
               			
               			</div></td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               			<logic:equal value="true" property="isEditable" name="NEC1">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteMobNewsEventsDetails('<bean:write name="NEC1" property="id"/>')">               </logic:equal>
              </div> </td>
               </tr>
               
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
              </logic:iterate>
           </logic:notEmpty>
         </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
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
</td>
</tr>
</table>
</html:form>
<script type="text/javascript">
var year = document.getElementById("tempyear").value;
if (year.length != 0) {
	document.getElementById("academicYear").value = year;
}
	</script>
	</html>
