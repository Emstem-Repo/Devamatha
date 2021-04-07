<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html:html>
<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentdetails.js"></script>
<link rel="stylesheet" href="css/calendar.css">


<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<script>


function searchStreamWise(streamId){
		getDepartmentByStreamWise(streamId,updateDepartmentMap);
}
function updateDepartmentMap(req){
	updateOptionsFromMap(req,"tempDepartmentId","-Select-");
}


function cancel() {
		document.location.href = "LoginAction.do?method=loginAction";
}



function imposeMaxLengthName(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 100;
	return (Object.value.length < MaxLen);
}
function imposeMaxLengthUID(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 10;
	return (Object.value.length < MaxLen);
}
function imposeMaxLengthFingerPrintId(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9 || keynum == 46) {
		return true;
	}
	var MaxLen = 30;
	return (Object.value.length < MaxLen);
}
</script>

<body>
<html:form action="/NewsEventsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="formName" value="NewsEventsEntryForm" />
<html:hidden property="pageType" value="15" />
<html:hidden property="method" value="getSearchNewsPostEvents" styleId="method"/>
<html:hidden property="screen" />
	<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admin.mobNewsEventsDetails"/>  &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td width="954" background="images/Tcenter.gif" class="body" ><strong class="boxheader">News-Events Post-Event Entry Department </strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
     <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
<div id="errorMessage">
      			<html:errors/><FONT color="red">
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
              <td valign="top"><table width="100%" height="30" border="0" cellpadding="0" cellspacing="1">
                  <tr class="row-white">
                  	<td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
               		<td width="32%" height="25" class="row-even" >
					<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="NewsEventsEntryForm" property="tempAcademicYear"/>"/>
                 	  <html:select property="tempAcademicYear" styleId="tempAcademicYear" styleClass="combo">
  	   				 	<html:option value="">- Select -</html:option>
  	   					<cms:renderAcademicYear></cms:renderAcademicYear>
   			   		   </html:select>
				  </td>
				   <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                   <html:select property="tempCategoryId"   styleId="tempCategoryId" name="NewsEventsEntryForm" styleClass="comboMediumBig">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="NewsEventsEntryForm" property="categoryList">
									<html:optionsCollection property="categoryList" name="NewsEventsEntryForm" label="value" value="key" />
									</logic:notEmpty>
					</html:select>
                  </span>
                </td>
           
				  
                   <!--  <td width="15%" height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/>:</div></td>
                    <td width="20%" class="row-even"><html:text property="eventTitle" styleId="eventTitle" size="20" maxlength="500"/></td>
                    -->
                   
             </tr>
              <tr >
                <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="tempDateFrom" styleId="tempDateFrom" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#tempDateFrom").datepicker(pickerOpts);
										});
                  </script>
                  </td>
                  <td width="16%" height="25" class="row-odd" ><div align="left"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="32%" height="25" class="row-even" >
                    <span class="star">
                    <html:text property="tempDateTo" styleId="tempDateTo" size="20" maxlength="20"/>
                  </span>
                  <script language="JavaScript">
                  					$(function(){
											var pickerOpts = {
													 	        dateFormat:"dd/mm/yy"
													         };  
										  $.datepicker.setDefaults(
										    $.extend($.datepicker.regional[""])
										  );
										  $("#tempDateTo").datepicker(pickerOpts);
										});
                  </script>
                  </td>
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
      <!-- <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>-->
      <tr>
        <td height="52" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35"><div align="center">
               <html:submit property="" styleClass="formbutton" value="Continue"></html:submit>
               <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
            </div></td>
            
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</html:html>
