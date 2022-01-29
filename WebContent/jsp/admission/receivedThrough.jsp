<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
function reActivate(){
	document.location.href = "receivedThrough.do?method=reactivateReceivedThrough";
}
 function cancelAction(){
	 document.location.href = "receivedThrough.do?method=initReceivedThrough";
 }
 function resetReceivedThrough(){
	 alert("hello");
	 if(document.getElementById("method").value == "updateReceivedThrough"){
		 document.getElementById("receivedThrough").value=document.getElementById("origReceivedThrough").value;
		 document.getElementById("slipRequired").value=document.getElementById("origSlipRequired").value;
	 }
	 resetErrMsgs();
 }
 function resets(){
	 document.getElementById("receivedThrough").value="";
	  document.getElementById("slip1").checked = false;
	  document.getElementById("slip2").checked = false;
	 resetErrMsgs();
 }
 function editReceivedThrough(id){
	 document.location.href = "receivedThrough.do?method=editReceivedThrough&id="+id;
 }
 function deleteReceivedThrough(id){
	 document.location.href = "receivedThrough.do?method=deleteReceivedThrough&id="+id;
 }
</script>
</head>
<body>
<html:form action="/receivedThrough">
<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateReceivedThrough" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addReceivedThrough" />
		</c:otherwise>
	</c:choose>
<html:hidden property="formName" value="receivedThroughForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" styleId="method" value="addReceivedThrough" />
	<html:hidden property="origReceivedThrough"	styleId="origReceivedThrough" name="receivedThroughForm"/>
	<html:hidden property="origSlipRequired" styleId="origSlipRequired" name="receivedThroughForm"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">Admission 
			<span class="Bredcrumbs">&gt;&gt;Received Through &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Received Through</strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'>mandatoryfields</span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
					<br>
				</html:messages> </FONT></div>
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							
           <tr>
                     <td class="row-odd" width="25%">
                     <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.admission.receivedThrough"/>
								    </div>
                     </td>
                     <td class="row-even" width="25%">
                           <html:text property="receivedThrough" name="receivedThroughForm" maxlength="50" styleId="receivedThrough" size="40"></html:text> 
                     </td>
              		 <td class="row-odd" width="25%">
								    <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.admission.receivedThrough.slipRequired"/>
								    </div>
					</td>
					<td  class="row-even" width="25%">
									 <html:radio property="slipRequired" value="Yes" styleId="slip1"/>Yes&nbsp; 
									 <html:radio property="slipRequired" value="No" styleId="slip2"/>No&nbsp;
					</td>
           </tr>
           
		</table>
	</td>
	<td width="5" height="30" background="images/right.gif"></td>
</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
            <td width="49%" height="35" align="right">
            <c:choose>
					<c:when test="${operation == 'edit'}">
						<html:submit  value="Update" styleClass="formbutton" ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit  value="Submit" styleClass="formbutton" ></html:submit></c:otherwise>
				</c:choose>&nbsp;</td>
            
            <td width="49%" height="35" align="left">
            <c:choose>
						<c:when test="${operation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton" onclick="resetReceivedThrough()"></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resets()"></html:button>
						</c:otherwise>
					</c:choose>
            </td>
          </tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
							
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="20%" height="25" class="row-odd">
											<div align="center">slno</div>
											</td>
											
											
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.receivedThrough"/></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.admission.receivedThrough.slipRequired"/></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.edit"/></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.delete"/></div>
											</td>
										</tr>
										<logic:notEmpty name="receivedThroughForm" property="receivedThroughList">
									<logic:iterate id="received" name="receivedThroughForm"
										property="receivedThroughList" indexId="count">
										<tr>
										<td width="20%" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="received" property="receivedThrough" /></div> </td>
										<td width="20%" class="row-even"><div align="center"><bean:write name="received" property="slipRequired" /></div> </td>
										<td width="20%" height="25" class="row-even">
										<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editReceivedThrough('<nested:write name="received" property="id" />')" /> </div>

										</td>
										<td width="20%" height="25" class="row-even"> 
										<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteReceivedThrough('<nested:write name="received" property="id" />')" /></div> 
										</td>
										</tr>
										</logic:iterate>
										</logic:notEmpty>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							
						</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
					<tr>
					<td height="26" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>	
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</html:form>
</body>
</html>