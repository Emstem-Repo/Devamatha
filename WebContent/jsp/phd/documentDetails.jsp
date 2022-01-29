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
	document.location.href = "documentDetails.do?method=reactivateDocument";
}
function cancelAction(){
	 document.location.href = "documentDetails.do?method=initDocumentDetails";
}
function addDocument(){
	document.location.href = "documentDetails.do?method=addDocument";
}
function updateDocument(){
	 document.location.href = "documentDetails.do?method=updateDocument";
}

function editDocument(id){
	 document.location.href = "documentDetails.do?method=editDocument&id="+id;
}
function deleteDocument(id){
	 document.location.href = "documentDetails.do?method=deleteDocument&id="+id;
}

function resetDocument(){
	 document.getElementById("documentname").value="";
	 document.getElementById("submissionorder").value="";
	 if(document.getElementById("method").value == "updateDocument"){
		 document.getElementById("documentname").value=document.getElementById("origDocumentName").value;
		 document.getElementById("submissionorder").value=document.getElementById("origSubmissionOrder").value;
	
		 
	 }
	 resetErrMsgs();
}
 
</script>
</head>
<body>
<html:form action="/documentDetails">
<c:choose>
		<c:when test="${documentOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateDocument" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addDocument" />
		</c:otherwise>
	</c:choose>
<html:hidden property="formName" value="documentDetailsForm"/>
<html:hidden property="pageType" value="2" />
<html:hidden property="method" styleId="method" value="addDocument"/>
<html:hidden property="origDocumentName"	styleId="origDocumentName" name="documentDetailsForm"/>
<html:hidden property="origSubmissionOrder"	styleId="origSubmissionOrder" name="documentDetailsForm"/>
<table width="98%" border="0">
		<tr>
			<td><span class="Bredcrumbs">P.hd 
			<span class="Bredcrumbs">&gt;&gt;Document Details &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Document Details</strong></div>
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
            	<td class="row-odd" width="15%"><div align="right">&nbsp;<span class="Mandatory">*</span><bean:message key="knowledgepro.phd.document.name"/>:</div></td>
                <td  height="25" class="row-even" width="25%">
                 <html:text property="documentName" name="documentDetailsForm" maxlength="50" styleId="documentname" size="50"></html:text> 
                </td>
			    <td class="row-odd" width="15%"><div align="right">&nbsp;<span class="Mandatory">*</span><bean:message key="knowledgepro.phd.document.submissionOrder"/>:</div></td>
                <td class="row-even" width="15%">
                <html:text property="submissionOrder" name="documentDetailsForm" maxlength="50" styleId="submissionorder" size="30" onkeypress="return isNumberKey(event)"></html:text> 
                </td>
                <td  class="row-odd" width="15%"><div align="right">&nbsp;<bean:message key="knowledgepro.phd.guides.fee"/>:</div></td>
                <td class="row-even" width="15%">
                <html:text property="guideFees" name="documentDetailsForm" maxlength="50" styleId="guideFees" size="30"></html:text> 
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
					<c:when test="${documentOperation == 'edit'}">
						<html:submit  value="Update" styleClass="formbutton"  ></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit  value="Submit" styleClass="formbutton" ></html:submit></c:otherwise>
				</c:choose>
             &nbsp;
              &nbsp;
            <td width="49%" height="35" align="left">
            <c:choose>
						<c:when test="${documentOperation == 'edit'}">
							<html:cancel value="Reset" styleClass="formbutton" onclick="resetDocument()" ></html:cancel>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset" onclick="resetDocument()"></html:button>
						</c:otherwise>
					</c:choose><!--
            &nbsp;
            <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
            --></td>
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
											<td width="10%" height="25" class="row-odd">
											<div align="center">sl no</div>
											</td>
											<td width="30%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.phd.document.name"/></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.phd.document.submissionOrder"/></div>
											</td>
											<td width="20%" class="row-odd">
											<div align="center"><bean:message key="knowledgepro.phd.guides.fee"/></div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Edit</div>
											</td>
											<td width="10%" class="row-odd">
											<div align="center">Delete</div>
											</td>
										</tr>
										<logic:notEmpty name="documentDetailsForm" property="documentDetailsTOList">
									<logic:iterate id="documentdetails" name="documentDetailsForm"
										property="documentDetailsTOList" indexId="count">
										<tr>
										<td class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
										<td class="row-even"><div align="center"><bean:write name="documentdetails" property="documentName" /></div> </td>
										<td class="row-even"><div align="center"><bean:write name="documentdetails" property="submissionOrder" /></div> </td>
										<td class="row-even"><div align="center"><bean:write name="documentdetails" property="guideFees" /></div> </td>
										<td  height="25" class="row-even">
										<div align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor: pointer" onclick="editDocument('<nested:write name="documentdetails" property="id" />')" /> </div></td>
										<td  height="25" class="row-even"> 
										<div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor: pointer" onclick="deleteDocument('<nested:write name="documentdetails" property="id" />')" /></div> 
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