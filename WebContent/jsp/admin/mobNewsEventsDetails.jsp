<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
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

function addMobNewsEventsDetails() {
	
	document.getElementById("method").value="addMobNewsEventsDetails";
}
function editMobNewsEventsDetails(id) {
	
	document.getElementById("NEC").value=id;
	document.location.href = "mobNewsEventsDetailsEntry.do?method=editMobNewsEventsDetails&mobNewaEventsDatailsId="+id;
}
function updateMobNewsEventsDetails() {

	document.getElementById("method").value = "updateMobNewsEventsDetails"; 
	
}
function deleteMobNewsEventsDetails(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("NEC").value=id;
	if(deleteConfirm)
	document.location.href = "mobNewsEventsDetailsEntry.do?method=deleteMobNewsEventsDetails&mobNewaEventsDatailsId="+id;
}
function reActivate() {
	
	var dupId = document.getElementById("dupId").value;
	document.location.href="mobNewsEventsDetailsEntry.do?method=reActivateMobNewsEventsDetails&dupId="+dupId;
}
</script>

</head>
<html:form action="/mobNewsEventsDetailsEntry" method="POST" enctype="multipart/form-data">
<html:hidden property="mobNewaEventsDatailsId" styleId="NEC"/>

<c:choose>
	<c:when test="${mobNewsEventCategoryOperation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateMobNewsEventsDetails" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addMobNewsEventsDetails" />
	</c:otherwise>
</c:choose>
<html:hidden property="formName" value="MobNewsEeventsDetailsForm" />
<html:hidden property="pageType" value="1" />
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
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
                  <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
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
              <tr >
                <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:text property="eventTitle" styleId="eventTitle" size="60" maxlength="500"/>
                  </span></td>
                <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsIconImage"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:file property="iconTmageFile" styleId="iconTmageFile" size="20" maxlength="20"/>
                  </span></td>
              </tr>
              <tr>
              <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                   <html:select property="categoryId"   styleId="categoryId" name="MobNewsEeventsDetailsForm" styleClass="TextBox">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="MobNewsEeventsDetailsForm" property="categoryList">
									<html:optionsCollection property="categoryList" name="MobNewsEeventsDetailsForm" label="value" value="key" />
									</logic:notEmpty>
									</html:select>
                   
                   
                    
                   
                    
                    
                  </span></td>
               <td width="16%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.mobNewsEventsDetailsNewsImage"/>:</div></td>
                <td width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:file property="eventTitleFile" styleId="eventTitleFile" size="20" maxlength="20"/>
                  </span></td>
              
              </tr>
              <tr>
              
              <td width="16%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.mobNewsEventsDetailsNewsDescription"/>:</div></td>
                <td colspan="3" width="32%" height="25" class="row-even" ><label></label>
                    <span class="star">
                    <html:textarea property="eventDescription" styleId="eventDescription"  cols="100" rows="4"/>
                  </span></td>
              
              
              
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
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                   <c:choose>
            		<c:when test="${mobNewsEventCategoryOperation == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateMobNewsEventsDetails()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addMobNewsEventsDetails()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 <c:choose>
					<c:when test="${mobNewsEventCategoryOperation == 'edit'}">
						<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.cancel"/></html:button>
					</c:otherwise>
				</c:choose>
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
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" width="150" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsTitle"/></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsCategory"/></td>
                    <td height="25" width="100" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateFrom"/></td>
                    <td height="25" width="100" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.mobNewsEventsDetailsDateTo"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  
                  
                 
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="MobNewsEeventsDetailsForm" property="mobNewsEventsDetails">
                <logic:iterate id="NEC" name="MobNewsEeventsDetailsForm" property="mobNewsEventsDetails" indexId="count">
                <c:choose>
                   
                   
                   
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="31%" height="25" class="row-even" align="center"><bean:write name="NEC" property="eventTitle"/></td>
                   		<td width="31%" height="25" class="row-even" align="center"><bean:write name="NEC" property="category"/></td>
                   		<td width="21%" height="25" class="row-even" align="center"><bean:write name="NEC" property="dateFrom"/></td>
                   		<td width="21%" height="25" class="row-even" align="center"><bean:write name="NEC" property="dateTo"/></td>
                   		
                   		
			            <td width="6%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editMobNewsEventsDetails('<bean:write name="NEC" property="id"/>')"></div></td>
                   		<td width="6%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteMobNewsEventsDetails('<bean:write name="NEC" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="31%" height="25" class="row-white" align="center"><bean:write name="NEC" property="eventTitle"/></td>
               			<td width="31%" height="25" class="row-white" align="center"><bean:write name="NEC" property="category"/></td>
               			<td width="2%" height="25" class="row-white" align="center"><bean:write name="NEC" property="dateFrom"/></td>
               			<td width="21%" height="25" class="row-white" align="center"><bean:write name="NEC" property="dateTo"/></td>
               			
               			
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editMobNewsEventsDetails('<bean:write name="NEC" property="id"/>')"></div></td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteMobNewsEventsDetails('<bean:write name="NEC" property="id"/>')"></div></td>
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
</html:form>