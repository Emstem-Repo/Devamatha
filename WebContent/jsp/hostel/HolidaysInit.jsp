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
function getCourseByProgram(programId) {
	
	var programs =  document.getElementById("programId");
	 var selectedArray = new Array();	  
	  var i;
	  var count = 0;
	  
	  for (i=0; i<programs.options.length; i++) {
	    if (programs.options[i].selected) {
	      selectedArray[count] = programs.options[i].value;
	      count++;
	    }
	  }
	   getCorsesByPrograms("courseMap",selectedArray,"courseId",updateCourse);
}
function updateCourse(req) {
	updateOptionsFromMapForCourses(req,"courseId");
}
function searchChild() {
	document.getElementById("method").value = "searchChild";
	document.editChildrenDetail.submit();
}
function deleteDetails(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "hostelHolidays.do?method=deleteHostelHolidays&id=" + id;
	}
}
function editHolidaysDetails(id) {
	document.location.href = "hostelHolidays.do?method=editHostelHolidaysDetails&id=" + id;
}
function resetDetails(){
	 
	 resetFieldAndErrMsgs();
}
var progId = document.getElementById("progId").value;
if(progId != null && progId.length != 0) {
	
	document.getElementById("programsId").value = progId;
}
function resetErrorMsgs(){
	 
	 resetFieldAndErrMsgs();
}
function cancelToHome(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function cancel(){
	document.location.href = "hostelHolidays.do?method=initHolidays";
}
function resetErrorMsgsInEdit(){
	document.getElementById("holidaysFrom").value =document.getElementById("tempHolidaysFrom").value;
	document.getElementById("holidaysTo").value =document.getElementById("tempHolidaysTo").value;
	document.getElementById("description").value =document.getElementById("tempDescription").value;;
}
function getBlock(hostelId){
	getBlockByHostel("blockMap",hostelId,"blockId",updateBlock);
	}
function updateBlock(req) {
	updateOptionsFromMap(req,"blockId","- Select -");
}
function getUnit(blockId){
	getUnitByBlock("unitMap",blockId,"unitId",updateUnit);
	}
function updateUnit(req) {
	updateOptionsFromMap(req,"unitId","- Select -");
}
</script>
<html:form action="/hostelHolidays" method="POST">
<html:hidden property="formName" value="holidayForm"/>
<html:hidden property="pageType" value="1"/>
<c:choose>
		<c:when test="${openConnection!=null && openConnection == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateHostelHolidaysDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveHostelHolidaysDetails" />
		</c:otherwise>
	</c:choose>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.hostel.holidaysOrVacation.display" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
  	<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="30"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.hostel.holidaysOrVacation.display" /></strong></div>
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
                  		<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.hostel.holidaysOrVacation"/>:
						</div>
						</td>
						<td   class="row-even" width="35%" align="left">
						<% boolean disable2=false;%>
						<logic:equal value="true" name="holidayForm" property="flag">
						<% disable2=true;%>
						</logic:equal>
							<nested:radio property="holidaysOrVacation" styleId="holidaysOrVacation" value="holidays" name="holidayForm" disabled='<%=disable2%>'>Holidays</nested:radio>
							<nested:radio property="holidaysOrVacation" styleId="holidaysOrVacation" value="vacation" disabled='<%=disable2%>'>Vacation</nested:radio>
						</td>
						<td class="row-odd" width="15%"> 
							<div align="right"><span class="Mandatory">*</span>
							<bean:message key="knowledgepro.hostel" />:</div>
						</td>
						<td   class="row-even" width="35%" align="left">
							<% boolean disable3=false;%>
							<logic:equal value="true" name="holidayForm" property="flag">
							<% disable3=true;%>
						</logic:equal>
                    		<html:select property="hostelId" styleId="hostelId" disabled='<%=disable3%>' onchange="getBlock(this.value)">
                    			<html:option value="">--Select--</html:option>
                    				<logic:notEmpty property="hostelMap" name="holidayForm">
										<html:optionsCollection property="hostelMap" label="value" value="key"/>
									</logic:notEmpty>
							</html:select>
						</td> 
					</tr>
					<tr>
						<td class="row-odd" width="15%"> 
							<div align="right"><span class="Mandatory">*</span>
								<bean:message key="knowledgepro.block" />:</div>
						</td>
						<td   class="row-even" width="35%" align="left">
							<% boolean disable4=false;%>
							<logic:equal value="true" name="holidayForm" property="flag">
							<% disable4=true;%>
						</logic:equal>
							<html:select property="blockId" styleId="blockId" disabled='<%=disable4%>' styleClass="combo" onchange="getUnit(this.value)">
								<html:option value="">--Select--</html:option>
									<c:choose>
             						<c:when test="${blockMap != null}">
             						<html:optionsCollection name="blockMap" label="value" value="key" />
									</c:when>
									<c:otherwise>
									<c:if test="${holidayForm.blockMap!= null && holidayForm.blockMap != ''}">
										<html:optionsCollection property="blockMap" name="holidayForm" label="value" value="key"/>
            		    	 		</c:if>
									</c:otherwise>
									</c:choose>
			  				</html:select>
						</td>
						<td class="row-odd" width="15%"> 
							<div align="right"><span class="Mandatory">*</span>
								<bean:message key="knowledgepro.unit" />:</div>
						</td>
						<td   class="row-even" width="35%" align="left">
							<% boolean disable5=false;%>
							<logic:equal value="true" name="holidayForm" property="flag">
							<% disable5=true;%>
						</logic:equal>
							<html:select property="unitId" disabled='<%=disable5%>' styleId="unitId"  styleClass="combo">
								<html:option value="">--Select--</html:option>
								<c:choose>
             					<c:when test="${unitMap != null}">
             					<html:optionsCollection name="unitMap" label="value" value="key" />
								</c:when>
								<c:otherwise>
									<c:if test="${holidayForm.unitMap!= null && holidayForm.unitMap != ''}">
										<html:optionsCollection property="unitMap" name="holidayForm" label="value" value="key"/>
            		    	 		</c:if>
								</c:otherwise>
								</c:choose>
			  				</html:select>
						</td> 
					</tr>
                  <tr class="row-white">
                  	<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						<bean:message key="knowledgepro.admin.program"/>:
						</div>
					</td>
					<td  class="row-even" width="35%">
					<% boolean disable=false;%>
						<logic:equal value="true" name="holidayForm" property="flag">
						<% disable=true;%>
						</logic:equal>
						<input type="hidden" id="progId" name="progId" value="<bean:write name="holidayForm" property="programsId"/>" />
						<html:select property="programsId" styleId="programId" multiple="true" size="7" disabled='<%=disable%>' onchange="getCourseByProgram(this.value)">
							<html:option value="0">Select All</html:option>
							<logic:notEmpty property="programMap" name="holidayForm">
						   		<html:optionsCollection property="programMap" label="value" value="key"/>
						   	</logic:notEmpty>
						</html:select> 
					</td>
					<td class="row-odd" width="15%">
						<div align="right"><span class="Mandatory">*</span>
						Course:
						</div>
					</td>     
            		<td width="35%" class="row-even"><input type="hidden" id="tempcourseId" name="tempcourseId" value="<bean:write name="holidayForm" property="courseId"/>" />
						<% boolean disable1=false;%>
						<logic:equal value="true" name="holidayForm" property="flag">
						<% disable1=true;%>
						</logic:equal>
						<html:select property="coursesId" styleId="courseId"  multiple="true" size="7" disabled='<%=disable1%>'>
             			 	<c:choose>
             			 		<c:when test="${courseMap != null}">
             			 			<html:option value="0">Select</html:option>
             			 			<html:optionsCollection name="courseMap" label="value" value="key" />
								</c:when>
								<c:otherwise>
									<c:if test="${holidayForm.courseMap!= null && holidayForm.courseMap != ''}">
										<html:optionsCollection property="courseMap" name="holidayForm" label="value" value="key"/>
            		    	 		</c:if>
								</c:otherwise>
							 </c:choose>
			  			</html:select>
			  		</td>
                  </tr>
                  <tr>
                  		<td class="row-odd" width="15%"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.holidays.from" />:</div>
						</td>
						<td   class="row-even" width="35%" align="left">
							<input type="hidden" name="holidayForm"	id="tempHolidaysFrom" value="<bean:write name='holidayForm' property='holidaysFrom'/>" />
							<html:text name="holidayForm" property="holidaysFrom" styleId="holidaysFrom" size="10" maxlength="16"/>
								<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'holidayForm',
										// input name
										'controlname' :'holidaysFrom'
									});
								</script>
									<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="morning" name="holidayForm">Morning</nested:radio>
									<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="evening" >Evening</nested:radio>
						</td> 
						 <td class="row-odd" width="15%"> 
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.holidays.to" />:</div> 
						</td>
						<td   class="row-even" width="35%" align="left">
						<input type="hidden" name="holidayForm"	id="tempHolidaysTo" value="<bean:write name='holidayForm' property='holidaysTo'/>" />
							<html:text name="holidayForm" property="holidaysTo" styleId="holidaysTo" size="10" maxlength="16"/>
								<script language="JavaScript">
									new tcal( {
										// form name
										'formname' :'holidayForm',
										// input name
										'controlname' :'holidaysTo'
									});
								</script>
									<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="morning" name="holidayForm">Morning</nested:radio>
									<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="evening" >Evening</nested:radio>
						</td> 
					</tr>
					<tr>
					 <td class="row-odd" width="15%" align="right">Description:</td>
					 <td   class="row-even" width="35%" align="left">
					 	<input type="hidden" name="holidayForm"	id="tempDescription" value="<bean:write name='holidayForm' property='description'/>" />
					 	<html:textarea property="description" styleId="description" name="holidayForm"></html:textarea>
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
	            <td width="45%" height="35"><div align="right">
				<c:choose>
					<c:when test="${openConnection!=null && openConnection == 'edit'}">
						<html:submit property="" styleClass="formbutton" value="Update" ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit property="" styleClass="formbutton" value="Submit"></html:submit>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="5%"><c:choose>
						<c:when test="${openConnection!=null && openConnection == 'edit'}">
							<html:button property="" value="Reset" styleClass="formbutton" onclick="resetErrorMsgsInEdit()"></html:button>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetErrorMsgs()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
				<td width="53%"><c:choose>
						<c:when test="${openConnection!=null && openConnection == 'edit'}">
							<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancel()"></html:button>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Cancel"	onclick="cancelToHome()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
				</table>
			</td>
	          </tr>
          <logic:notEmpty name="holidayForm" property="hostelHolidaysTo">
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
                    <td width="5"  height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td width="15" height="30%" class="row-odd" align="center" >Program Name</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Course Name</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Holidays From</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Holidays To</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Holidays/Vacation</td>
                    <td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="holidayForm" property="hostelHolidaysTo" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="programName"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="courseName"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="holidaysFrom"/>-<bean:write name="CME" property="holidaysFromSession"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="holidaysTo"/>-<bean:write name="CME" property="holidaysToSession"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="holidaysOrVaction"/></td>
                   		<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editHolidaysDetails('<bean:write name="CME" property="id"/>')"> </div> </td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td  height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="programName"/></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="courseName"/></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="holidaysFrom"/>-<bean:write name="CME" property="holidaysFromSession"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="holidaysTo"/>-<bean:write name="CME" property="holidaysToSession"/></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="holidaysOrVaction"/></td>
               			<td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editHolidaysDetails('<bean:write name="CME" property="id"/>')"> </div> </td>
                   		<td  height="25" class="row-white" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
                </logic:iterate>
                
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
          </logic:notEmpty>
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
                  

