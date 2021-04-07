<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%>
<html>
<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/newsEventsDetails.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
 <script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
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



function getPhoto(photoName){
	var url="NewsEventsEntry.do?method=getPhotoFromFile&photoName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=200,height=100,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}
function ViewPhoto(id,photoname) {
	document.getElementById("selectedPhotoId").value=id;
	document.location.href = "NewsEventsEntry.do?method=ViewPhoto&selectedPhotoId="+id;
	myRef = window.open(url,"Photo","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}

function deletePhoto(photoName){
	document.location.href="NewsEventsEntry.do?method=deletePhoto&photoId="+photoName;
}



</script>

</head>
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="selectedNewsEventsId" styleId="NEC"/>
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="mode" styleId="mode" value="" />
<html:hidden property="formName" value="NewsEventsEntryForm" />
<html:hidden property="selectedPhotoId" styleId="selectedPhotoId"/>
<html:hidden property="pageType" value="12" />
<html:hidden property="screen"/>
<html:hidden property="photoListSize" name="NewsEventsEntryForm" styleId="photoListSize"/>
<html:hidden property="orgphotoListSize" name="NewsEventsEntryForm" styleId="orgphotoListSize"/>
<html:hidden property="resourseListSize" name="NewsEventsEntryForm" styleId="resourseListSize"/>
<html:hidden property="orgResListSize" name="NewsEventsEntryForm" styleId="orgResListSize"/>
<html:hidden property="participantsListSize" name="NewsEventsEntryForm" styleId="participantsListSize"/>
<html:hidden property="orgPartListSize" name="NewsEventsEntryForm" styleId="orgPartListSize"/>
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
        <td background="images/Tcenter.gif" class="heading_white" >Post Event Department Entry</td>
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
          <!--   <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>-->
          </tr>
          <tr>
           <!--  <td width="5"  background="images/left.gif"></td>-->
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="0">
              <tr>
              	<td colspan="2" class="row-odd" align="left"><bean:message key="knowledgepro.news.events"/>:</td>
              	<td colspan="2" class="row-even" >
              		<html:text property="newsOrEvents" styleId="newsOrEvents" readonly="true"/>
              	</td>
              </tr>
              <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
               <td width="30%" height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="NewsEventsEntryForm" property="academicYear"/>"/>
                <html:text property="academicYear" styleId="academicYear" readonly="true"/>
				</td>
		 		<td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/>:</div></td>
                <td width="30%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="eventTitle" styleId="eventTitle" size="40" maxlength="500" />
                </span>
                </td>
             </tr>
             <tr>
                <td width="20%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="30%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="dateFrom" styleId="dateFrom" size="20" maxlength="20" readonly="true"/>
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
                  <td width="20%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="dateTo" styleId="dateTo" size="20" maxlength="20" readonly="true"/>
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
              <tr>
                <td width="20%" height="25" class="row-odd" ><div align="left">Participants:</div></td>
                <td  class="row-even">
                <html:text property="participants" styleId="participants" readonly="true"/>
                </td>
				 <td width="20%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:hidden property="categoryId" styleId="categoryId"/>
                    <html:text property="categoryName" styleId="categoryName" readonly="true"/>
                  </span>
                </td>
            </tr>
       <tr>
       				<td height="25" class="row-odd"><div align="left" >Is Live Telecast</div></td>
                <td class="row-even"><span class="star">
                <html:text property="isLiveTelecast" readonly="true"/>
               </span></td>
       		
       	<td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.pgm.organisedBy"/> </div></td>
              <td  class="row-even">
                <html:text property="organizedBy" styleId="organizedBy" readonly="true"/> 
		</td>
	</tr>
	<logic:notEmpty name="NewsEventsEntryForm" property="departmentName">
	 <tr id="departmentTitle">
         
              		<td class="row-odd" width="20%" height="25">
              		 <bean:message key="knowledgepro.employee.Department"/></td>
                	<td colspan="3" class="row-even" height="25">
                  <html:hidden property="departmentId" styleId="departmentId"/>
                  <bean:write name="NewsEventsEntryForm" property="departmentName"/>
				</td>
	</tr>
	
	</logic:notEmpty>
	<logic:notEmpty name="NewsEventsEntryForm" property="streamName">
	 <tr id="streamTitle">
         
			   <td class="row-odd" width="20%" height="25"><bean:message key="knowledgepro.employee.DeaneryDetails"/></td>
               <td  class="row-even" height="25" colspan="3">
                  <html:hidden property="streamId" styleId="streamId"/>
		    	<bean:write name="NewsEventsEntryForm" property="streamName" />
		    	</td>
				
	</tr>
	</logic:notEmpty>
	<logic:notEmpty name="NewsEventsEntryForm" property="courseName">
	 <tr id="courseTitle">
         
			   <td class="row-odd" width="20%" height="25">
                <bean:message key="knowledgepro.admission.course"/></td>
                 <td colspan="3" class="row-even" height="25">
                     <html:hidden property="courseId" styleId="courseId"/>
		    		 <bean:write name="NewsEventsEntryForm" property="courseName" />

              	</td>
	</tr>
	
	</logic:notEmpty>
	<logic:notEmpty name="NewsEventsEntryForm" property="splCentreName">
	<tr id="SplCenterTitle">
			    <td  class="row-odd" width="20%" height="25">Special Centers</td>
			    <td colspan="3" class="row-even" height="25">
                 <html:hidden property="splCenterId" styleId="splCenterId"/>
                  <bean:write name="NewsEventsEntryForm" property="splCentreName" />
      	</td>
				
	</tr>
	</logic:notEmpty>
	 <html:hidden property="selectedviewFor" styleId="selectedviewFor"/>
	  <html:hidden property="viewFor" styleId="viewFor"/>
	  <html:hidden property="newsWebPosition" styleId="newsWebPosition"/>
	  <html:hidden property="eventWebPosition" styleId="eventWebPosition"/>
	  <html:hidden property="displayFromDate" styleId="displayFromDate"/>
	  <html:hidden property="displayToDate" styleId="displayToDate"/>
	  <html:hidden property="preApprovalRemarks" styleId="preApprovalRemarks"/>
	<!--    <html:hidden property="eventDescription" styleId="eventDescription"/>-->
	   <html:hidden property="iconName" styleId="iconName"/>
	   
       <!--  <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.position"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    	<html:text property="newsWebPosition" readonly="true"/>
                   </span></td>
             
					<td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span>
					<bean:message key="knowledgepro.admin.events.display.position"/>:</div></td>
                <td  width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="eventWebPosition" readonly="true"/>
                  </span></td>
			</tr>
			<tr >
                <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.from.date"/>:</div></td>
                <td width="30%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="displayFromDate" readonly="true" styleId="displayFromDate" size="20" maxlength="20"/>
                  </span>
                   </td>
                  <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.to.date"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="displayToDate" readonly="true" styleId="displayToDate" size="20" maxlength="20"/>
                  </span>
                </td>
              </tr>
              <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.events.view.for"/>:</div></td>
                <td width="30%" height="25" class="row-even" >
                    <span class="star">
                    
                    <bean:write name="NewsEventsEntryForm" property="viewFor" />
                   </span></td>
             
					<td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span>
					<bean:message key="knowledgepro.admin.events.pre.Approver.comments"/>:</div></td>
                <td  width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="preApprovalRemarks" readonly="true" styleId="preApprovalRemarks" size="40" maxlength="50"/>
                  </span></td>
			</tr>-->
        
        <tr>
                <td height="25" class="row-odd"><div align="left" ><bean:message key="KnowledgePro.news.events.registration.required1"/></div></td>
                <td class="row-even"><span class="star">
                <html:text property="isRegistrationRequired" readonly="true" />
                 </span></td>
             
                <td height="25" class="row-odd"><div align="left" ><bean:message key="KnowledgePro.news.events.invitation.required"/></div></td>
                <td class="row-even"><span class="star">
                <html:text property="isInvitationMailRequired" readonly="true" />
                 </span></td>
             </tr>
        <logic:notEmpty property="regFormName" name="NewsEventsEntryForm">
       <tr id="Registration">
          <td height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.newsEntry.registration.form"/>:</div></td>
          <logic:notEmpty property="regFormName" name="NewsEventsEntryForm">
          	 <html:hidden property="regFormName" name="NewsEventsEntryForm"/>
                <td class="row-even" colspan="3">
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
          
          </logic:notEmpty>
          <logic:notEmpty property="invitationName" name="NewsEventsEntryForm">
          <tr id="invitation">
          
				<td height="25" class="row-odd" >
               			 <div align="left"><bean:message key="knowledgepro.admin.newsEntry.Invitation.mail"/>:</div></td>
                 <logic:notEmpty property="invitationName" name="NewsEventsEntryForm">
                  <html:hidden property="invitationName" name="NewsEventsEntryForm"/>
                   			<td class="row-even" align="left" colspan="3"> 
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
              
          </logic:notEmpty>
     	 <tr id="IconDescription">
              <td width="20%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsNewsDescription"/>:</div></td>
                <td colspan="3" width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:textarea property="eventDescription" styleId="eventDescription" readonly="true" cols="70" rows="4"/>
                  </span></td>
              </tr>
           <!-- <tr id="IconDescription1">
                <td width="20%" height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsIconImage"/>:</div></td>
                <td width="30%" height="25" class="row-even">
                    <span class="star">
                    <html:file property="iconTmageFile" styleId="iconTmageFile" size="20" maxlength="20"/>
                  </span></td>
                  <td width="20%" height="25" class="row-odd" colspan="2"><span class="star">
                  <logic:notEmpty property="iconName" name="NewsEventsEntryForm">
                    	<img src="images/View_icon.gif"
							width="16" height="18"  style="cursor:pointer" 
							onclick="getIcon('<bean:write property="iconName" name="NewsEventsEntryForm"/>')">
            
                   </logic:notEmpty> 
                  </span></td>
              </tr>-->
              <tr>
              <td width="20%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span>Summary:</div></td>
                <td colspan="3" width="30%" height="25" class="row-even" >
                    <span class="star">
                    <html:textarea property="summary" styleId="summary"  cols="70" rows="4" onkeypress="return imposeMaxLength(this, 0, 499);" 
															onchange="return imposeMaxLength(this, 0, 500);" onkeyup="len_displaySummary(this,0,'long_len')"/>
					<input type="text" id="long_len" value="0" class="len" size="2" readonly="readonly" style="border: none; background-color: #eaeccc; text-align: right; font-size: 11px; font-family: Verdana, Arial, Helvetica, sans-serif">/500 Characters
                  
                  </span></td>
         </tr>
         
        <tr>
          <td width="20%" height="25" class="row-odd" ><div align="left">Report of Events:</div></td>
          <td width="30%" height="25" class="row-even">
           <span class="star">
                    <html:file property="eventReport" styleId="eventReport" size="20" maxlength="20" onblur="CheckReport();"/>
           </span>
           <div>(doc/pdf/xls)</div></td>
          <logic:notEmpty property="reportName" name="NewsEventsEntryForm">
          <html:hidden property="reportName" name="NewsEventsEntryForm"/>
                   			<td class="row-even" align="left" colspan="2"> 
                   			<logic:equal value="true" property="reportIsImage" name="NewsEventsEntryForm">
                   				<img src="images/View_icon.gif"
								width="16" height="18"  style="cursor:pointer" 
								onclick="getReport('<bean:write property="reportName" name="NewsEventsEntryForm"/>')">
							</logic:equal>
           			 		<logic:equal value="false" property="reportIsImage" name="NewsEventsEntryForm">
            					<a href="#" onclick="getDownloadFile('<bean:write property="reportName" name="NewsEventsEntryForm"/>','Report')"><bean:message key="knowledgepro.reports.submit" /></a>
            				</logic:equal>
           			 	</td>
           </logic:notEmpty>
            <logic:empty property="reportName" name="NewsEventsEntryForm">
                  <td class="row-even" align="left" colspan="2"></td>
           </logic:empty>
          </tr>
          
          <tr>
          
				<td height="25" class="row-odd" width="20%" >
               			 <div align="left">Materials Published:</div></td>
                <td width="30%" height="25" class="row-even" align="left">
                    		<span class="star">
                    		<html:file property="materialsPublished" styleId="materialsPublished" size="20" maxlength="20" onblur="CheckMaterial();"/></span><div>(doc/pdf/xls)</div>
                 			 </td>
                 			<logic:notEmpty property="materialName" name="NewsEventsEntryForm">
                 			<html:hidden property="materialName" name="NewsEventsEntryForm"/>
                   			<td class="row-even" align="left" colspan="2"> 
                   			<logic:equal value="true" property="materialIsImage" name="NewsEventsEntryForm">
                   				<img src="images/View_icon.gif"
								width="16" height="18"  style="cursor:pointer" 
								onclick="getMaterial('<bean:write property="materialName" name="NewsEventsEntryForm"/>')">
							</logic:equal>
           			 		<logic:equal value="false" property="materialIsImage" name="NewsEventsEntryForm">
            					<a href="#" onclick="getDownloadFile('<bean:write property="materialName" name="NewsEventsEntryForm"/>','Material')"><bean:message key="knowledgepro.reports.submit" /></a>
            				</logic:equal>
           			 	</td>
          	 </logic:notEmpty>
           			 	  <logic:empty property="materialName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left" colspan="2"> </td>
           			 	</logic:empty>
           </tr>
           <tr> 
				<td colspan="2" class="heading" align="left">Upload News Image</td>
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
							                <td  class="row-odd" width="60%"><div align="justify" >Upload Photo</div></td>
							                <td class="row-odd" width="20%"><div align="center" ><bean:message key="knowledgepro.view"/></div></td>
							                <td class="row-odd" width="18%"><div align="center" ><bean:message key="knowledgepro.delete"/></div></td>
										</tr>
										<tr>
											<td colspan="3">
												<table width="100%" class="row-even" cellspacing="1" cellpadding="2" id="addRowTable">
													<logic:notEmpty  property="photosTO" name="NewsEventsEntryForm">          
														<nested:iterate id="photo" property="photosTO" name="NewsEventsEntryForm" indexId="countPhoto">
														<c:choose>
															<c:when test="${countPhoto == 0}">
																<tr>
																		 <td align="justify" width="60%">
																		 <nested:file property="photoFile" styleId="photoFile" size="20" maxlength="20"/><div>(jpg/bmp/png/gif)</div> &nbsp;&nbsp; <nested:write property="photoName"/></td>
																		 <nested:notEmpty property="photoName">
																		 <td align="center" width="20%"><img src="images/View_icon.gif"
																		width="16" height="18"  style="cursor:pointer" 
																		onclick="getPhoto('<nested:write property="photoName"/>','<nested:write property="id"/>')"></td>
																		<td height="25" width="18%" class="row-even" align="center"><div>
														               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" 
														               				onclick="deletePhoto('<bean:write name="photo" property="id"/>')"></div></td>
														               	</nested:notEmpty>
														            </tr>
															</c:when>
															<c:otherwise>
																<nested:notEmpty property="photoName">
																	<tr>
																		 <td align="justify" width="60%">
																		 <nested:file property="photoFile" styleId="photoFile" size="20" maxlength="20"/> &nbsp;&nbsp; <nested:write property="photoName"/></td>
																		 <td align="center" width="20%"><img src="images/View_icon.gif"
																		width="16" height="18"  style="cursor:pointer" 
																		onclick="getPhoto('<nested:write property="photoName"/>','<nested:write property="id"/>')"></td>
																		<td height="25" width="18%" class="row-even" align="center"><div>
														               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" 
														               				onclick="deletePhoto('<bean:write name="photo" property="id"/>')"></div></td>
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
									            <td class="row-odd" align="center" colspan="4"> <html:button property="" styleClass="formbutton" value="Add more" onclick="addRow()"></html:button>
												    	<html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removeRow()"></html:button>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2" >
			
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
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);"></nested:text>
				</td>
				<td class="row-even" width="20%">
				 	<nested:text  property="contactNo" styleId="contactNo" onkeypress="return isNumberKey(event)" size="20" maxlength="20"></nested:text></td>
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
              		 <td class="row-odd" align="center" colspan="4"> <html:button property="" styleClass="formbutton" value="Add more" onclick="addResourseRow()"></html:button>
							<c:if test="${NewsEventsEntryForm.resourseListSize>0}">
								<html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removeResourseRow()"></html:button>
							</c:if>
					 </td>
              
            
             </tr></table></td>
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
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);"></nested:text>
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
					<nested:text property="email" styleId="<%=email_count%>" onblur="<%=avgMethod%>" size="20" maxlength="100" onkeypress="maxlength(this, 100);"></nested:text>
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
		    <html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removeContactRow()"></html:button>
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
				<td colspan="2" class="heading" align="left">Participants Details</td>
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
                <td  class="row-odd" width="40%"><div align="left" >Name of Institution</div></td>
                <td class="row-odd" width="20%"><div align="left" >No of People</div></td>
                <td  class="row-odd" width="40%"><div align="left" >Remarks</div></td>
			</tr>
		<tr>
		<td colspan="3">
			<table width="100%" class="row-even" cellspacing="1" cellpadding="2" id="addPartRowTable">
			<logic:notEmpty property="partcipantsTO" name="NewsEventsEntryForm">          
				<nested:iterate property="partcipantsTO" name="NewsEventsEntryForm" indexId="countPart">														<c:choose>
				<c:when test="${countPart == 0}">
				<tr>
				 <td class="row-even" width="40%">
				 <nested:text  property="institutionName" styleId="institutionName" size="25" maxlength="200"></nested:text></td>
				 <td class="row-even" width="20%">
				<nested:text property="noOfPeople" styleId="noOfPeople" size="20" maxlength="10" onkeypress="return isNumberKey(event)" ></nested:text>
				</td>
				 <td class="row-even" width="40%">
				<nested:textarea property="remarks" styleId="remarks" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
            </tr>
			</c:when>
			<c:otherwise>
			<nested:notEmpty property="institutionName">
			<tr>
				 <td class="row-even" width="40%">
				 <nested:text  property="institutionName" styleId="institutionName" size="25" maxlength="200"></nested:text></td>
				 <td class="row-even" width="20%">
				<nested:text property="noOfPeople" styleId="noOfPeople" size="20" maxlength="10" onkeypress="return isNumberKey(event)" ></nested:text>
				</td>
				 <td class="row-even" width="40%">
				<nested:textarea property="remarks" styleId="remarks" cols="30" rows="1" onkeypress="maxlength(this, 500);"></nested:textarea>
				</td>
                
            </tr></nested:notEmpty>
	</c:otherwise>
	</c:choose>
	</nested:iterate>
	</logic:notEmpty>
	</table>
	</td>
	</tr>
              <tr>
              		 <td class="row-odd" align="center" colspan="4"> <html:button property="" styleClass="formbutton" value="Add more" onclick="addPartRow()"></html:button>
							<c:if test="${NewsEventsEntryForm.participantsListSize>0}">
								<html:button property="" styleClass="formbutton" value="Remove"  styleId="addMore" onclick="removePartRow()"></html:button>
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
                   	<html:button property="" styleClass="formbutton" onclick="updatePostDept()">Submit For Approval</html:button>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
						<html:button property="" styleClass="formbutton" value="Cancel" onclick="ClosePostDept()">
										<bean:message key="knowledgepro.cancel"/></html:button>
				</td>
              </tr>
            </table></td>
          </tr>
         
          
               <!-- <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>-->
            </table></td>
          </tr>
          
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     <!--  <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>-->
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

/*desig=document.getElementById("organizedBy").value;
if(desig!=null)
{
	if(desig=="Course")
	{
		
	  document.getElementById("departmentTitle").style.display="none";
	  document.getElementById("departmentId").disabled=true;
		
	  document.getElementById("courseTitle").style.display="block";
	  document.getElementById("courseId").disabled=false;

	  document.getElementById("streamTitle").style.display="none";
	  document.getElementById("streamId").disabled=true;

	  document.getElementById("SplCenterTitle").style.display="none";
	  document.getElementById("splCenterId").disabled=true;
		
	}
	else if(desig=="Department")
	{
		  document.getElementById("departmentTitle").style.display="block";
		  document.getElementById("departmentId").disabled=false;

		  document.getElementById("courseTitle").style.display="none";
		  document.getElementById("courseId").disabled=true;

		  document.getElementById("streamTitle").style.display="none";
		  document.getElementById("streamId").disabled=true;

		  document.getElementById("SplCenterTitle").style.display="none";
		  document.getElementById("splCenterId").disabled=true;
						
	}
	else if(desig=="Deanery")
	{
		
		 document.getElementById("departmentTitle").style.display="none";
		  document.getElementById("departmentId").disabled=true;
		 
		  document.getElementById("courseTitle").style.display="none";
		  document.getElementById("courseId").disabled=true;

		  document.getElementById("streamTitle").style.display="block";
		  document.getElementById("streamId").disabled=false;

		  document.getElementById("SplCenterTitle").style.display="none";
		  document.getElementById("splCenterId").disabled=true;
	}
	else if(desig=="Special Centers")
	{
		  document.getElementById("SlCenterTitle").style.display="block";
		  document.getElementById("splCenterId").disabled=false;

		  document.getElementById("departmentTitle").style.display="none";
		  document.getElementById("departmentId").disabled=true;
			
		  document.getElementById("courseTitle").style.display="none";
		  document.getElementById("courseId").disabled=true;

		  document.getElementById("streamTitle").style.display="none";
		  document.getElementById("streamId").disabled=true;
 }
	else
	{
		  document.getElementById("SplCenterTitle").style.display="none";
		  document.getElementById("splCenterId").disabled=true;

		  document.getElementById("departmentTitle").style.display="none";
		  document.getElementById("departmentId").disabled=true;
			
		  document.getElementById("courseTitle").style.display="none";
		  document.getElementById("courseId").disabled=true;

		  document.getElementById("streamTitle").style.display="none";
		  document.getElementById("streamId").disabled=true;
		
}}
if(document.getElementById("InvitationYes").checked==true)
{
  document.getElementById("invitation").style.display="block";
}
else 
{
  document.getElementById("invitation").style.display="none";
}

if(document.getElementById("RegistrationYes").checked==true)
{
  document.getElementById("Registration").style.display="block";
}
else 
{
	 document.getElementById("Registration").style.display="none";
}*/	</script>
	</html>
