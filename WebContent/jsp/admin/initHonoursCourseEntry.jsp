<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script type="text/javascript">
	function editDetails(id){
		document.location.href = "honoursCourseEntry.do?method=editHonoursCourse&id="+id;
	}
	function deleteDetails(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "honoursCourseEntry.do?method=deleteHonoursCourse&id="+id;
		}
	}
	function reActivate(){
			var id=document.getElementById("dupId").value;
			document.location.href = "honoursCourseEntry.do?method=activateHonoursCourse&dupId="+id;
		}
	function resetMsgs(){
	document.getElementById("honoursCourseId").value = "";
	document.getElementById("eligibleCourseId").value = "";
	resetErrMsgs();
	if(document.getElementById("method").value == "updateHonoursCourseEntry"){
		document.getElementById("honoursCourseId").value = document.getElementById("orgHonoursCourseId").value;
		document.getElementById("eligibleCourseId").value = document.getElementById("orgEligibleCourseId").value;
	}
	}
	function resetErrorMsgs() {
		resetErrMsgs();
	}
</script>
</head>
<html:form action="/honoursCourseEntry" method="POST">
<html:hidden property="formName" value="honoursCourseEntryForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" styleId="id"/>
<html:hidden property="dupId" styleId="dupId" name="honoursCourseEntryForm"/>
<html:hidden property="orgHonoursCourseId" styleId="orgHonoursCourseId" name="honoursCourseEntryForm"/>
<html:hidden property="orgEligibleCourseId" styleId="orgEligibleCourseId" name="honoursCourseEntryForm"/>
<c:choose>
		<c:when test=""></c:when>
	</c:choose>
<c:choose>
		<c:when test="${honoursCourse!=null && honoursCourse == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateHonoursCourseEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addHonoursCourseEntry" />
		</c:otherwise>
	</c:choose>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs">Admin <span class="Bredcrumbs">&gt;&gt; HonoursCourseEntry</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >HonoursCourseEntry  </td>
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
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
            
              <tr >
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Honours Course:</div></td>
              <td align="left" height="25" class="row-even">
              <input type="hidden" id="tempHonoursCourse" name="tempHonoursCourse" value="<bean:write name="honoursCourseEntryForm" property="honoursCourseId"/>" />
			  <html:select property="honoursCourseId"  styleId="honoursCourseId" styleClass="comboMediumBig" >
			  <html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
			  <html:optionsCollection name="honoursCourseEntryForm" property="honoursCourseMap" label="value" value="key" />
			  </html:select></td>
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Eligible Course:</div></td>       
              <td align="left" width="30%" class="row-even">
              <input type="hidden" id="tempEligibleCourse" name="tempEligibleCourse" value="<bean:write name="honoursCourseEntryForm" property="eligibleCourseId"/>" />
              <html:select property="eligibleCourseId"  styleId="eligibleCourseId" styleClass="comboMediumBig">
              <html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
			<html:optionsCollection name="honoursCourseEntryForm" property="eligiableCourseMap" label="value" value="key" />
			  </html:select></td>
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
           <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
	            <td width="45%" height="35"><div align="right">
				<c:choose>
					<c:when test="${honoursCourse!=null && honoursCourse == 'edit'}">
						<html:submit property="" styleClass="formbutton" value="Update" ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit property="" styleClass="formbutton" value="Submit"></html:submit>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="53%"><c:choose>
						<c:when test="${honoursCourse!=null && honoursCourse == 'edit'}">
							<html:button property="" value="Reset" styleClass="formbutton" onclick="resetMsgs()"></html:button>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetErrorMsgs()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
				</table>
			</td>
	          </tr>
          <logic:notEmpty name="honoursCourseEntryForm" property="honoursCourseEntryTo">
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
                     <td width="15" height="30%" class="row-odd" align="center" >Honours Course</td>
                    <td width="20" height="30%" class="row-odd" align="center" >Eligible Course</td>
                       
                    <td width="5" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td width="5" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="CME" name="honoursCourseEntryForm" property="honoursCourseEntryTo" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td  height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="honoursCourse"/></td>
                   		<td  height="25" class="row-even" align="center"><bean:write name="CME" property="eligibleCourse"/></td>
                   		<td  height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editDetails('<bean:write name="CME" property="id"/>')"> </div> </td>
                   		<td  height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td  height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="honoursCourse"/></td>
               			<td  height="25" class="row-white" align="center"><bean:write name="CME" property="eligibleCourse"/></td>
               			<td  height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						 height="18" style="cursor:pointer" onclick="editDetails('<bean:write name="CME" property="id"/>')"> </div> </td>
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
<script type="text/javascript">
	var yearId = document.getElementById("tempyear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
	var sessionId = document.getElementById("tempSession").value;
	if (sessionId != null && sessionId.length != 0) {
		document.getElementById("sessionId").value = sessionId;
	}
</script>