<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<html:html>
<script type="text/javascript">
function editDetails(id,midEnd,cycleName,sessionId){
	document.getElementById("method").value ="updateRoomCycleEntry";
	document.getElementById("id").value = id;
	document.getElementById("midEndSem").value = midEnd;
	document.getElementById("sessionId").value = sessionId;
	document.getElementById("cycleName").value = cycleName;
	document.getElementById("orgMidEndSem").value = midEnd;
	document.getElementById("orgCycleName").value = cycleName;
	ocument .getElementById("orgSessionId").value = sessionId;
	document.getElementById("submitbutton").value = "Update";
}
function resetValues() {
	document.getElementById("midEndSem").value = "";
	document.getElementById("cycleName").value = "";
	document.getElementById("sessionId").value = "";
	if (document.getElementById("method").value =="updateRoomCycleEntry") {
		document.getElementById("midEndSem").value = document .getElementById("orgMidEndSem").value;
		document.getElementById("cycleName").value = document .getElementById("orgCycleName").value;
		document.getElementById("sessionId").value = document .getElementById("orgSessionId").value;
	}
}
function deleteDetails(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?");
	if (deleteConfirm == true) {
		document.location.href = "examRoomAllotmentCycle.do?method=deleteRoomCyleEntry&id="+id;
	}
}
function cancelAction(){
	document.location.href = "LoginAction.do?method=loginAction";
}
</script>
<html:form action="/examRoomAllotmentCycle" method="post">
<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateRoomCycleEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addRoomCycleEntry" />
		</c:otherwise>
	</c:choose>
<html:hidden property="method" styleId="method" value=""/>	
<html:hidden property="pageType" value="1" />
<html:hidden property="formName" value="examRoomAllotmentCycleForm" />
<html:hidden property="id" styleId="id" name="examRoomAllotmentCycleForm"/>
<html:hidden property="orgMidEndSem" styleId="orgMidEndSem" name="examRoomAllotmentCycleForm"/>
<html:hidden property="orgCycleName" styleId="orgCycleName" name="examRoomAllotmentCycleForm"/>
<html:hidden property="orgSessionId" styleId="orgSessionId" name="examRoomAllotmentCycleForm"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Exam Allotment
			<span class="Bredcrumbs">&gt;&gt; Cycle Entry &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Cycle Entry</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="100%" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
		        <td width="25%" class="row-odd" ><div align="right">
		      <span class="Mandatory">*</span> Mid/End Sem:</div></td>
		        <td width="15%" class="row-even" colspan="3" >
		      		 <input type="hidden" id="tempEndMidSem"  value="<bean:write name="examRoomAllotmentCycleForm" property="midOrEndSem"/>"/>
                      <html:select property="midOrEndSem"  styleId="midEndSem"  name="examRoomAllotmentCycleForm"  onchange="selectSemWise(this.value)">
                      <option value="Mid Sem">Mid Sem</option>
                      <option value="End Sem">End Sem</option>
                	</html:select>
                	</td>
	       </tr>
	       <tr>
		        <td width="25%" class="row-odd" ><div align="right">
		      <span class="Mandatory">*</span> Cycle:</div></td>
		        <td width="15%" class="row-even"  >
                      <html:text property="cycleName"  styleId="cycleName"  name="examRoomAllotmentCycleForm" maxlength="40">
                	</html:text>
                	</td>
				<td class="row-odd" width="10%"><div align="right"><span class="Mandatory">*</span>Session:</div></td>
				  <td width="25%" class="row-even"  colspan="2">
	                     <html:select property="sessionId"  styleId="sessionId"  name="examRoomAllotmentCycleForm">
		                     <logic:notEmpty name="examRoomAllotmentCycleForm" property="sessionMap">
		                      <option value="">-Select-</option>
		                      <html:optionsCollection name="examRoomAllotmentCycleForm" property="sessionMap" label="value" value="key" />
		                      </logic:notEmpty>
	                	</html:select>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
										<c:choose>
								<c:when
									test="${specialization != null && specialization == 'edit'}">
									<html:submit property="" styleClass="formbutton"
										 value="Update" styleId="submitbutton">
										
									</html:submit>
								</c:when>
								<c:otherwise>
									<html:submit property="" styleClass="formbutton"
										 value="Submit" styleId="submitbutton">
									</html:submit>
								</c:otherwise>
							</c:choose>
							</div>
							</td>
							<td width="2%"></td>
							<td width="5%">
							<div align="left">
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button>
							</div>
							</td>
							<td width="44%" ><html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()">
						</html:button>
							</td>
						</tr>
			<tr>
					<td valign="top" class="news" colspan="5">
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

							<td height="25" >
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">Mid/End Sem</td>
									<td height="25" class="row-odd" align="center">Cycle</td>
									<td height="25" class="row-odd" align="center" >Session</td> 	
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								
								<logic:iterate id="to" name="examRoomAllotmentCycleForm" property="toList" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="20%" height="25" class="row-even" align="center">
												
												<bean:write name="to" property="midOrEndSem"/>
												</td>
												<td width="10%" height="25" class="row-even" align="center">
												
												<bean:write name="to" property="cycleName"  />
												</td>
												<td width="25%" height="25" class="row-even" align="center" >
												
												<bean:write name="to" property="sessionName"  />
												</td>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="midOrEndSem"/>','<bean:write name="to" property="cycleName"/>','<bean:write name="to" property="sessionId"/>')">
												</div>
												</td>
												<td width="5%" height="25" class="row-even" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteDetails('<bean:write name="to" property="id"/>')">
												</div>

												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="20%" height="25" class="row-white" align="center">
												<bean:write name="to" property="midOrEndSem"/>
												</td>
												<td width="10%" height="25" class="row-white" align="center">
												<bean:write name="to" property="cycleName"  />
												</td>
												<td width="25%" height="25" class="row-white" align="center" >
												<bean:write name="to" property="sessionName"  />
												</td>
												
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18" style="cursor:pointer" 
													onclick="editDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="midOrEndSem"/>','<bean:write name="to" property="cycleName"/>','<bean:write name="to" property="sessionId"/>')">
												</div>
												</td>
												<td width="5%" height="25" class="row-white" align="center">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16" style="cursor:pointer" 
													onclick="deleteDetails('<bean:write name="to" property="id"/>')">
												</div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
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
				</tr>			
			</table>
		</td>
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
var tempEndMidSem = document.getElementById("tempEndMidSem").value;
if (tempEndMidSem != null && tempEndMidSem.length != 0) {
	document.getElementById("midEndSem").value = tempEndMidSem;
}
</script>

</html:html>
