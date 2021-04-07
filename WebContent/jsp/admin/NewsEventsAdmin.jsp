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

function maxlength(field, size) {
    if (field.value.length > size) {
        field.value = field.value.substring(0, size);
    }
}

function submitParticipantsInfoAdd(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.NewsEventsEntryForm.submit();
}

function submitResourseInfoAdd(method,mode){
	document.getElementById("method").value=method;
	document.getElementById("mode").value=mode;
	document.NewsEventsEntryForm.submit();
}


function getRegFormAdmin()
{
	var sendResult=null;
	desig=document.getElementById("isRegistrationRequired").value;
		if(desig!=null && desig=="Yes")
		{
			sendResult="Yes";
		}else
		{
			sendResult="No";
		}
	return sendResult;
}

function getInvitationAdmin()
{
	var sendResult=null;
	desig=document.getElementById("isInvitationMailRequired").value;
		if(desig!=null && desig=="Yes")
		{
			sendResult="Yes";
		}else
		{
			sendResult="No";
		}
	return sendResult;
}

function addMobNewsEventsDetails() {
	
	document.getElementById("method").value="updateNewsEventsAdmin";
	document.NewsEventsEntryForm.submit();
}
function editMobNewsEventsDetails(id) {
	
	document.getElementById("selectedNewsEventsId").value=id;
	document.location.href = "NewsEventsEntry.do?method=editMobNewsEventsDetails&selectedNewsEventsId="+id;
}
function updateMobNewsEventsDetails() {

	document.getElementById("method").value="updateNewsEventsAdmin";
	document.NewsEventsEntryForm.submit();
	
}


/*function getPhoto(photoName){
	var url="NewsEventsEntry.do?method=getPhotoFromFile&photoName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}

function getIcon(photoName){
	var url="NewsEventsEntry.do?method=getIconFromFile&iconName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}
function getRegistration(photoName){
	var url="NewsEventsEntry.do?method=getRegFormFromFile&regFormName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}

function getInvitation(photoName){
	var url="NewsEventsEntry.do?method=getInvitationFromFile&invitationName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}

function getMaterial(photoName){
	var url="NewsEventsEntry.do?method=getMaterialPublishedFromFile&materialName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}

function getReport(photoName){
	var url="NewsEventsEntry.do?method=getReportFile&reportName="+photoName;
		win2 = window.open(url, "Help", "left=1350,top=550,width=400,height=300,toolbar=0,resizable=0,scrollbars=0,addressbar=0"); 
}*/
// Please do no remove admin screen jQuery required Seperately....
var jq=$.noConflict();
jq(document).ready(function(){
	var flag=getInvitationAdmin();
	if(flag==Yes){
		jq("#invitation").show();
	}else
	{
		jq("#invitation").hide();
	}
	
	var flag=getRegFormAdmin();
	if(flag==Yes){
		jq("#Registration").show();
	}else
	{
		jq("#Registration").hide();
	}
});

</script>

</head>
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="selectedNewsEventsId" styleId="selectedNewsEventsId"/>
<html:hidden property="method" styleId="method" value="" />
<html:hidden property="formName" value="NewsEventsEntryForm" />
<html:hidden property="pageType" value="5" />
<html:hidden property="screen"/>
<html:hidden property="photoListSize" name="NewsEventsEntryForm" styleId="photoListSize"/>
<html:hidden property="orgphotoListSize" name="NewsEventsEntryForm" styleId="orgphotoListSize"/>
<html:hidden property="resourseListSize" name="NewsEventsEntryForm" styleId="resourseListSize"/>
<html:hidden property="orgResListSize" name="NewsEventsEntryForm" styleId="orgResListSize"/>
<html:hidden property="participantsListSize" name="NewsEventsEntryForm" styleId="participantsListSize"/>
<html:hidden property="orgPartListSize" name="NewsEventsEntryForm" styleId="orgPartListSize"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admin.mobNewsEventsDetails"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >News and Events Admin </td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            	<tr>
              	<td colspan="2" class="row-odd" align="left"> News/Events</td>
              	<td colspan="2" class="row-even">
              		 <bean:write name="NewsEventsEntryForm" property="newsOrEvents" />
              	</td>
              </tr>
              <tr>
              <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
               <td width="32%" height="25" class="row-even" >
              <bean:write name="NewsEventsEntryForm" property="academicYear" />
				</td>
		 		<td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <bean:write name="NewsEventsEntryForm" property="eventTitle" />
                    
                </span>
                </td>
             </tr>
             <tr >
                <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <bean:write name="NewsEventsEntryForm" property="dateFrom" />
                    
                                     </td>
                  <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                    <bean:write name="NewsEventsEntryForm" property="dateTo" />
                  </span>
                  
                  </td>
              </tr>
              
              <tr>
              
                <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span>Participants:</div></td>
                <td  class="row-even">
                <bean:write name="NewsEventsEntryForm" property="participants" />
                    				 </td>
				 <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="categoryName" />
               
                                    </span>
                </td>
            </tr>
       <tr>
       				<td height="25" class="row-odd"><div align="left" >Is Live Telecast</div></td>
                <td class="row-even"><span class="star">
                <bean:write name="NewsEventsEntryForm" property="isLiveTelecast" />
                </span></td>
       		
       	<td height="25" class="row-odd"><div align="left" ><bean:message key="knowledgepro.pgm.organisedBy"/> </div></td>
            <td  class="row-even">
                <bean:write name="NewsEventsEntryForm" property="organizedBy" />
			</td>
	</tr>
    <logic:notEmpty property="departmentName" name="NewsEventsEntryForm">
    <tr>
              		<td class="row-odd" >
              		 <span class="Mandatory">*</span><bean:message key="knowledgepro.employee.Department"/></td>
               <td class="row-even" colspan="3">
                	 <bean:write name="NewsEventsEntryForm" property="departmentName" />
              </td>
	</tr>
	</logic:notEmpty>
	<logic:notEmpty property="streamName" name="NewsEventsEntryForm">
	<tr id="streamTitle">
			   <td class="row-odd" ><bean:message key="knowledgepro.employee.DeaneryDetails"/></td>
               <td class="row-even" colspan="3">
                <bean:write name="NewsEventsEntryForm" property="streamName" />
               </td>
    </tr>
    </logic:notEmpty>
    <logic:notEmpty property="courseName" name="NewsEventsEntryForm">
	<tr id="courseTitle">
			   <td class="row-odd">
                <bean:message key="knowledgepro.admission.course"/></td>
                 <td class="row-even" colspan="3">
                  <bean:write name="NewsEventsEntryForm" property="courseName" />
              	</td>
     </tr>
    </logic:notEmpty>
    <logic:notEmpty property="splCentreName" name="NewsEventsEntryForm">
	<tr id="SplCenterTitle">
			    <td  class="row-odd">Special Centers</td>
			 <td class="row-even" colspan="3">
			     <bean:write name="NewsEventsEntryForm" property="splCentreName" />
             </td>
        </tr>
     </logic:notEmpty>
	         <tr>
                <td height="25" class="row-odd"><div align="left" ><bean:message key="KnowledgePro.news.events.registration.required1"/></div></td>
                <td class="row-even"><span class="star">
                 <bean:write name="NewsEventsEntryForm" property="isRegistrationRequired" />
                 </span></td>
             
                <td height="25" class="row-odd"><div align="left" ><bean:message key="KnowledgePro.news.events.invitation.required"/></div></td>
                <td class="row-even"><span class="star">
                 <bean:write name="NewsEventsEntryForm" property="isInvitationMailRequired" />
             </span></td>
             </tr>
             <tr id="Registration">
                <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.newsEntry.registration.form"/>:</div></td>
             <logic:notEmpty property="regFormName" name="NewsEventsEntryForm">
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
           			 	<td class="row-even" align="left" colspan="3"></td>
             </logic:empty>
                </tr>
                <tr id="invitation">
                  <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.newsEntry.Invitation.mail"/>:</div></td>
                	 <logic:notEmpty property="invitationName" name="NewsEventsEntryForm">
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
           			 	<td class="row-even" align="left" colspan="3"></td>
           			 	</logic:empty>
              </tr>
             <tr>
              <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.position"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="newsWebPosition" />
                   </span></td>
             
					<td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span>
					<bean:message key="knowledgepro.admin.events.display.position"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="eventWebPosition" />
                  </span></td>
			</tr>
			<tr >
                <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.from.date"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="displayFromDate" />
                  </span>
                  </td>
                  <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.news.display.to.date"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="displayToDate" />
                  </span>
                 
                  </td>
              </tr>
             <tr>
              <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.events.view.for"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="viewFor" />
                   </span></td>
             
					<td width="16%" height="25" class="row-odd" ><div align="left">Pre-Event Approver Remarks </div></td>
                	<td width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="preApprovalRemarks" />
                  </span></td>
			</tr>
			<tr id="IconDescription1">
                <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsIconImage"/>:</div></td>
                <logic:notEmpty property="iconName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left" colspan="3"> 
                   				<img src="images/View_icon.gif"
							width="16" height="18"  style="cursor:pointer" 
							onclick="getIcon('<bean:write property="iconName" name="NewsEventsEntryForm"/>')">
           			 	</td>
           			 	</logic:notEmpty>
           			 	<logic:empty property="iconName" name="NewsEventsEntryForm">
           			 	<td class="row-even" align="left" colspan="3"></td>
           			 	</logic:empty>
              </tr>
              <tr id="IconDescription">
              <td width="16%" height="25" class="row-odd" ><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsNewsDescription"/>:</div></td>
                <td colspan="3" width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="eventDescription" />
                    
                  </span></td>
              </tr>
			<logic:equal value="Post-Event" property="prePostEventAdm" name="NewsEventsEntryForm">
			<tr>
			<td width="16%" height="25" class="row-odd" ><div align="left">Post-Event Approver Remarks</div></td>
                <td colspan="3" width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="postApprovalRemarks" />
                  </span></td>
             </tr>
             <tr>
                <td width="16%" height="25" class="row-odd" ><div align="left">Summary</div></td>
                <td colspan="3" width="32%" height="25" class="row-even" >
                    <span class="star">
                     <bean:write name="NewsEventsEntryForm" property="summary" />
                  </span></td>
			</tr>
			<tr>
                <td width="16%" height="25" class="row-odd" ><div align="left">Event report:</div></td>
                	<logic:notEmpty property="reportName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left"> 
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
           <td class="row-even" align="left"> </td>
           </logic:empty>
                    
           <td width="16%" height="25" class="row-odd" ><div align="left">Materials Published:</div></td>
                <logic:notEmpty property="materialName" name="NewsEventsEntryForm">
                   			<td class="row-even" align="left" > 
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
          	  <logic:empty property="reportName" name="NewsEventsEntryForm">
           <td class="row-even" align="left"> </td>
           </logic:empty>
              </tr>
			
			</logic:equal>
			
	<tr> 
		<td colspan="2" class="heading" align="left">News Image</td>
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
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.view"/></div></td>
			</tr>
				
			<logic:notEmpty  property="photosTO" name="NewsEventsEntryForm">          
			<nested:iterate id="photo" property="photosTO" name="NewsEventsEntryForm" indexId="countPhoto">
			<tr>
				 <td  class="row-even" align="center"><img src="images/View_icon.gif"
				width="16" height="18"  style="cursor:pointer" 
				onclick="getPhoto('<nested:write property="photoName"/>','<nested:write property="id"/>')"></td>
            </tr>
              </nested:iterate>
              </logic:notEmpty>
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
								<td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
			
             <tr class="row-odd">
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.admin.name"/></div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.exam.email"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.tel"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.admin.Other.information"/></div></td>
			</tr>
				
			<logic:notEmpty property="resourseTO" name="NewsEventsEntryForm">          
			<nested:iterate property="resourseTO" name="NewsEventsEntryForm" id="empResourse" indexId="count">
			<tr>
				 <td class="row-even" >
				 <bean:write  name="empResourse"  property="resourseName" />
				 <td class="row-even">
				 <bean:write  name="empResourse"  property="email" />
				</td>
				<td class="row-even" >
				<bean:write  name="empResourse"  property="contactNo" />
				 <td class="row-even">
				 <bean:write  name="empResourse"  property="otherInfo" />
				</td>
                
            </tr>
              </nested:iterate>
              </logic:notEmpty>
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
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.admin.name"/></div></td>
                <td class="row-odd"><div align="left" ><bean:message key="knowledgepro.exam.email"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.employee.tel"/></div></td>
                <td  class="row-odd"><div align="left" ><bean:message key="knowledgepro.hostel.reservation.remarks"/></div></td>
			</tr>
				
			<logic:notEmpty property="contactTO" name="NewsEventsEntryForm">          
			<nested:iterate property="contactTO" name="NewsEventsEntryForm" id="contact" indexId="count">
			<tr>
				 <td class="row-even" >
				 <bean:write  name="contact"  property="name" /></td>
				 <td class="row-even">
				 <bean:write  name="contact"  property="email" />
				</td>
				<td class="row-even" >
				<bean:write  name="contact"  property="contactNo" /></td>
				 <td class="row-even">
				 <bean:write  name="contact"  property="remarks" />
				</td>
                
            </tr>
              </nested:iterate>
              </logic:notEmpty>
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
                <td  class="row-odd"><div align="left" >Name of Institution</div></td>
                <td class="row-odd"><div align="left" >No of People</div></td>
                <td  class="row-odd"><div align="left" >Remarks</div></td>
			</tr>
				
			<logic:notEmpty property="partcipantsTO" name="NewsEventsEntryForm">          
			<nested:iterate property="partcipantsTO" name="NewsEventsEntryForm" id="participants" indexId="count">
			<tr>
				 <td class="row-even" >
				  <bean:write  name="participants"  property="institutionName" />
				 <td class="row-even">
				 <bean:write  name="participants"  property="noOfPeople" />
				</td>
				 <td class="row-even">
				 <bean:write  name="participants"  property="remarks" />
				</td>
                
            </tr>
              </nested:iterate>
              </logic:notEmpty>
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
			<logic:equal value="Pre-Event" property="prePostEventAdm" name="NewsEventsEntryForm">
			<tr>
			<td colspan="2" class="heading" align="left">Pre-Event Publish</td>
			</tr>
             <tr class="row-odd">
                <td  class="row-odd"><div align="left" >Status</div></td> 
                <td class="row-even">
  	   				 	<html:radio property="preAdminStatus" value="Not Applicable"/>Not Applicable &nbsp; 
				 		<html:radio property="preAdminStatus" value="Publish"/>Publish &nbsp;
				 		<html:radio property="preAdminStatus" value="Not Published"/>Not Published
                </td>
			</tr>
			</logic:equal>
			<logic:equal value="Post-Event" property="prePostEventAdm" name="NewsEventsEntryForm">
			<tr>
			<td colspan="2" class="heading" align="left"> Post-Event Publish</td>
			</tr>
             <tr class="row-odd">
                <td  class="row-odd"><div align="left" >Status</div></td> 
                <td class="row-even">
  	   				 	<html:radio property="postAdminStatus" value="Not Applicable"/>Not Applicable &nbsp; 
				 		<html:radio property="postAdminStatus" value="Publish"/>Publish &nbsp;
				 		<html:radio property="postAdminStatus" value="Not Published"/>Not Published
                </td>
			</tr>
			</logic:equal>
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
                   
              	   		<html:submit property="" styleClass="formbutton" onclick="updateAdmin()"><bean:message key="knowledgepro.update"/></html:submit>
              		
                	
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 
						<html:button property="" styleClass="formbutton" value="Cancel" onclick="CancelAdmin()"></html:button>
					
				</td>
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
</html:form>
<script type="text/javascript">


</script>
	</html>
