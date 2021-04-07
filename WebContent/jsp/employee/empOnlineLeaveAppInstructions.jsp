<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript" language="javascript">

function addLeaveAppInstructions() {
	document.getElementById("method").value="addLeaveAppInstructions";
}

function resetErrorMsgs(){
	document.getElementById("description").value = "";
	resetErrMsgs();
}
function deleteLeaveAppInstructions(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("NWID").value=id;
	if(deleteConfirm)
		document.location.href = "onlineLeaveAppInstruction.do?method=deleteLeaveAppInstructions&onlineLeaveAppId="+id;
}
function imposeMaxLength(evt, Object) {
	var keynum = (evt.which) ? evt.which : event.keyCode;
	if (keynum == 8 || keynum == 37 || keynum == 39 || keynum == 9) {
		return true;
	}
	var MaxLen = 2000;
	return (Object.value.length < MaxLen);
}
function editOnlineLeave(id){
	document.getElementById("NWID").value=id;
	document.location.href = "onlineLeaveAppInstruction.do?method=editLeaveAppInstructions&onlineLeaveAppId="+id;
}
</script>
<html:form action="/onlineLeaveAppInstruction" method="POST">
<html:hidden property="onlineLeaveAppId" styleId="NWID"/>
<html:hidden property="formName" value="onlineLeaveAppInstructionForm" />
<html:hidden property="pageType" value="1" />
<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${leave != null && leave == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateLeaveAppInstructions" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addLeaveAppInstructions" />
		</c:otherwise>
	</c:choose>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.employee"/> <span class="Bredcrumbs">&gt;&gt; Online Leave Application Instructions &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Online Leave Application Instructions</td>
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
				<td width="21%" height="25" class="row-odd">
				<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
					key="knowledgepro.admin.desc" />:</div>
				</td>
				<td colspan="3" class="row-even"><label> 
					<html:textarea property="description" styleId="description" style="width: 83%" cols="80" rows="8" onkeypress="return imposeMaxLength(event,this)"></html:textarea> 
				</label></td>
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
								<c:when
									test="${leave != null && leave == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										onclick="updateLeaveAppInstructions()">
										<bean:message key="knowledgepro.update" />
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										onclick="addLeaveAppInstructions()">
										<bean:message key="knowledgepro.submit" />
									</html:submit>
								</c:otherwise>
							</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetErrorMsgs()"></html:button>
				</td>
              </tr>
            </table></td>
          </tr>
          
          <tr>
            <td valign="top" class="news">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.admin.desc"/></td>
                     <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="onlineLeaveAppInstructionForm" property="leaveInstructionsTo">
                <logic:iterate id="INS" name="onlineLeaveAppInstructionForm" property="leaveInstructionsTo" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="41%" class="row-even" align="center"><bean:write name="INS" property="description"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editOnlineLeave('<bean:write name="INS" property="id"/>')"> </div> </td>
                   		<td width="6%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteLeaveAppInstructions('<bean:write name="INS" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="41%" class="row-white" align="center"><bean:write name="INS" property="description"/></td>
               			<td width="10%" height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editOnlineLeave('<bean:write name="INS" property="id"/>')"> </div> </td>
               			<td width="6%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteLeaveAppInstructions('<bean:write name="INS" property="id"/>')"></div></td>
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