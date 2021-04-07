<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">
function forwardAssignToroles(id){
	document.getElementById("certificateDetailsId").value=id;
	document.getElementById("method").value="forwardAssignToroles";
	document.certificateDetailsForm.submit();
}
function forwardAssignPurpose(id){
	document.getElementById("certificateDetailsId").value=id;
	document.getElementById("method").value="forwardAssignPurpose";
	document.certificateDetailsForm.submit();
}
function addCertificateDetails() {
	document.getElementById("method").value="addCertificateDetails";
}

function editCertificateDetails(id) {
	document.location.href = "CertificateDetails.do?method=editCertificateDetails&id="+id;
	}
function cancel() {
	document.location.href = "LoginAction.do?method=loginAction";
}
function updateCertificateDetails() {
	document.getElementById("method").value ="updateCertificateDetails"; 
	
}

function reActivate() {
	document.location.href="CertificateDetails.do?method=reActivateCertificateDetails";
}

function deleteCertificateDetails(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "CertificateDetails.do?method=deleteCertificateDetails&id="+id;
	}
function shows(obj,msg){
	
	document.getElementById("contents").innerHTML=msg;
	document.getElementById("messageBox").style.display="block";
	}
function hides(){
	document.getElementById("messageBox").style.display="none";
}

function DisplayTemplateButton(value)
{
	if(value!=null)
	{
		if(value=='Yes' || value=='yes' || value=='YES'){
			document.getElementById("TemplateButton").style.display="block";
		}else
		{
			document.getElementById("TemplateButton").style.display="none";
		}
	}
}

function callTempalateJsp(id)
{
	document.getElementById("certificateDetailsId").value=id;
	document.getElementById("method").value ="initGroupTemplate"; 
	document.certificateDetailsForm.submit();
}

</script>
</head>
<html:form action="/CertificateDetails" method="POST">
<html:hidden property="id" styleId="CMID"/>
<c:choose>
	<c:when test="${CertificateDetails == 'edit'}">
		<html:hidden property="method" styleId="method" value="editCertificateDetails" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addCertificateDetails" />
	</c:otherwise>
</c:choose>
<html:hidden property="formName" value="certificateDetailsForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="certificateDetailsId" styleId="certificateDetailsId" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.certificateDetails"/></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.certificateDetails"/> </td>
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
          <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.certificate.name"/>:</div></td>
                <td width="25%" height="25" class="row-even"><span class="star">
                    <html:text property="certificateName" styleId="Name" size="50" maxlength="200"/>
                </span></div></td>
                <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.certificate.fees"/>:</div></td>
                <td width="25%" height="25" class="row-even"><span class="star">
                    <html:text property="fees" styleId="fees" size="20" maxlength="12"/>
                </span></div></td>
               </tr>
              <tr>
               <td width="25%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> Marks Card :</div></td>
	           <td width="25%" class="row-even">
					           <html:radio property="marksCard" name="certificateDetailsForm" value="Yes" styleId="fixed">Yes</html:radio>
					           <html:radio property="marksCard"  name="certificateDetailsForm" value="No" styleId="fixed">No</html:radio>
			  	</td>
			  	 <td width="25%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> Is Reason Required :</div></td>
	           <td width="25%" class="row-even">
					           <html:radio property="isReasonRequired" name="certificateDetailsForm" value="Yes" styleId="isReasonRequired">Yes</html:radio>
					           <html:radio property="isReasonRequired"  name="certificateDetailsForm" value="No" styleId="isReasonRequired">No</html:radio>
			  	</td>
              </tr>
              <tr>
              	 <td width="25%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> Is ID-Card:</div></td>
	           	 <td width="25%" class="row-even" colspan="3">
					           <html:radio property="isIdCard" name="certificateDetailsForm" value="Yes" styleId="isIdCard">Yes</html:radio>
					           <html:radio property="isIdCard"  name="certificateDetailsForm" value="No" styleId="isIdCard">No</html:radio>
			  	 </td>
              </tr>

                <tr>
				<td class="row-odd" ><div align="right">
				<bean:message key="knowledgepro.admin.desc.with.col"/> </div></td>
	            <td class="row-even" colspan="3" >
	            <html:textarea property="description" styleClass="TextBox" cols="18" rows="2" style="width: 643px; height: 293px;" styleId="description">
	            </html:textarea>
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
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                   <c:choose>
            		<c:when test="${CertificateDetails == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateCertificateDetails()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addCertificateDetails()"><bean:message key="knowledgepro.submit"/></html:submit>
                		&nbsp;&nbsp;&nbsp;
                		 <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancel()"></html:button>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 <c:choose>
					<c:when test="${CertificateDetails == 'edit'}">
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
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="40%" class="row-odd" align="center" ><bean:message key="knowledgepro.certificate.name"/></td>
                    <td height="15%" class="row-odd" align="center"><bean:message key="knowledgepro.certificate.fees"/></td>
                    <td height="20%" class="row-odd"><div align="center">Marks Card</div></td>
                     <td height="20%" class="row-odd"><div align="center">Is Reason Required</div></td>
                     <td height="20%" class="row-odd"><div align="center">Is ID-Card</div></td>
                     <td height="10%" class="row-odd" align="center" onmouseover="shows(this,'The certificate details will be available for the assigned roles')" onmouseout="hides()"><bean:message key="knowledgepro.assign.to.roles"/></td>
                     <td height="10%" class="row-odd" align="center" onmouseover="shows(this,'Assign the purpose for the certificate details')" onmouseout="hides()"><bean:message key="knowledgepro.assign.to.purpose"/></td>
                     <td height="10%" class="row-odd" align="center" onmouseover="shows(this,'Create Template')" onmouseout="hides()">Create Template</td>
                    <td height="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td height="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
               
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="certificateDetailsForm" property="certificateList">
                <logic:iterate id="CME" name="certificateDetailsForm" property="certificateList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="40%" height="25" class="row-even" align="center"><bean:write name="CME" property="certificateName"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"><bean:write name="CME" property="fees"/></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="CME" property="marksCard"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="CME" property="isReasonRequired"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="CME" property="isIdCard"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center" onmouseover="shows(this,'The certificate details will be available for the assigned roles')" onmouseout="hides()">
			        		<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="forwardAssignToroles('<bean:write name="CME" property="id"/>')"></div></td>
			            <td width="10%" height="25" class="row-even" ><div align="center" onmouseover="shows(this,'Assign the purpose for the certificate details')" onmouseout="hides()">
			        		<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="forwardAssignPurpose('<bean:write name="CME" property="id"/>')"></div></td>
			            <td width="10%" height="25" class="row-even" ><div align="center" onmouseover="shows(this,'Create a template')" onmouseout="hides()">
			        		<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="callTempalateJsp('<bean:write name="CME" property="id"/>')"></div></td>
			            <td width="5%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editCertificateDetails('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="5%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteCertificateDetails('<bean:write name="CME" property="id"/>')"></div></td>
                                             		
                   		</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="40%" height="25" class="row-white" align="center"><bean:write name="CME" property="certificateName"/></td>
               			<td width="10%" height="25" class="row-white" align="center"><bean:write name="CME" property="fees"/></td>
               			<td width="10%" class="row-white" align="center"><bean:write name="CME" property="marksCard"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
               			<td width="10%" class="row-white" align="center"><bean:write name="CME" property="isReasonRequired"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
               			<td width="10%" class="row-white" align="center"><bean:write name="CME" property="isIdCard"/><div id="messageBox" align="center"><div id="contents"></div></div></td>
               			<td width="10%" height="25" class="row-white" ><div align="center" onmouseover="shows(this,'The certificate details will be available for the assigned roles')" onmouseout="hides()">
               				<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="forwardAssignToroles('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="10%" height="25" class="row-white" ><div align="center" onmouseover="shows(this,'Assign the purpose for the certificate details')" onmouseout="hides()">
			        		<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="forwardAssignPurpose('<bean:write name="CME" property="id"/>')"></div></td>	
               			<td width="10%" height="25" class="row-white" ><div align="center" onmouseover="shows(this,'Create template')" onmouseout="hides()">
			        		<img src="images/questionMark.jpg" width="20" height="16" style="cursor:pointer" onclick="callTempalateJsp('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editCertificateDetails('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteCertificateDetails('<bean:write name="CME" property="id"/>')"></div></td>
               				
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