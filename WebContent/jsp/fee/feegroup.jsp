<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap" %>
<script type="text/javascript">

function deleteFeeGroup(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm) {
		document.location.href="ManageFeeGroup.do?method=deleteFeeGroupEntry&feeGroupId="+id;
	}
}

function resetErrorMsgs() {	
	var feeGroupNameOriginal =  document.getElementById("feeGroupNameOriginal").value;
	var option = document.getElementById("originalOptional").value;
	if(feeGroupNameOriginal !=null && feeGroupNameOriginal != '' ){
		document.getElementById("feeGroupName").value = feeGroupNameOriginal;
		if(option == "true")
			document.getElementById("optional_1").checked = option;
		else
			document.getElementById("optional_2").checked = option;
		resetErrMsgs();
	} else {
		resetFieldAndErrMsgs();
	}
}

function editFeeGroup(feeGroupId, feeGroupName, optional) {	
	document.getElementById("feeGroupName").value = feeGroupName;
	document.getElementById("feeGroupId").value = feeGroupId;
	if(optional == "true"){
		document.getElementById("optional_1").checked = true;
		document.getElementById("originalOptional").value = document.getElementById("optional_1").value;
	}
	else{
		document.getElementById("optional_2").checked = true;
		document.getElementById("originalOptional").value = document.getElementById("optional_2").value;
	}
	document.getElementById("feeGroupNameOriginal").value = feeGroupName;
	document.getElementById("method").value = "editFeeGroupEntry";
	document.getElementById("submitbutton").value = "update";
}

function reActivate() {
	var feeGroupName = document.getElementById("feeGroupName").value;	
	document.location.href="ManageFeeGroup.do?method=reactivateFeeGroupEntry&feeGroupName="+feeGroupName;
}

</script>
<html:form action="/ManageFeeGroup" focus="feeGroupName">

<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'editFeeGroupEntry'}">
			<html:hidden property="method" styleId="method"
				value="editFeeGroupEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addFeeGroupEntry" />
		</c:otherwise>
	</c:choose>

<html:hidden property="feeGroupId" styleId="feeGroupId"/>
<html:hidden property="feeGroupNameOriginal" styleId="feeGroupNameOriginal"/>
<html:hidden property="originalOptional" styleId="originalOptional"/>
<html:hidden property="formName" value="feeGroupEntryForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="admOperation" styleId="admOperation" value = "add"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.feegroupentry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.feegroupentry"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
       <div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
               <tr>
               	    <td height="20" colspan="6" class="body" align="left">
					
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
       
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
           
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
            
                  <tr >
                    <td width="25%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.admission.feeGroupName"/>: </div></td>
                    <td width="25%" height="25" class="row-even" ><span class="star">
                    <html:text property="feeGroupName" styleId="feeGroupName" size="20" maxlength="50"></html:text> 
                    </span></td>
                    <td width="25%" height="25" class="row-odd" ><div align="right"> <bean:message key="knowledgepro.admission.optional"/>: </div></td>
                    <td width="25%" height="25" class="row-even" ><span class="star">
                    <html:radio property="optional" styleId="optional_1" value="true"/> Yes
                    <html:radio property="optional" styleId="optional_2" value="false"/> No
                    
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
        </table>
        </td>
        <td valign="top" background="images/Tright_3_3.gif"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
              <tr>
                <td width="45%" height="27">                
                	<div align="right"><c:choose>
										<c:when
											test="${admOperation != null && admOperation == 'editFeeGroupEntry'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
                </td>
                <td width="2%"></td>
                <td width="53%"><html:button property=""
										styleClass="formbutton" value="Reset"
										onclick="resetErrorMsgs()"></html:button></td>
              </tr>              
              <tr><td style="height: 10px" align="center" colspan="3"></td></tr>              
            </table>
       </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
     
       
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
      
      
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
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.admission.feeGroupName"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.optional"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  	<c:set var="temp" value="0"/>
                  		<logic:iterate name="feeGroupEntryForm" property="feeGroupToList"
									id="id" indexId="count">
                               <c:choose>
								 <c:when test="${temp == 0}">
									<tr>
										<td width="7%" height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"/></div>
										</td>
										<td width="35%" height="25" class="row-even" align="center">
										<bean:write name="id" property="name" /></td>
										<td width="35%" height="25" class="row-even" align="center">
										<c:choose>
										<c:when test="${id.optional == true}"> Yes </c:when>
										<c:otherwise> No </c:otherwise>
										</c:choose>
										</td>
										<td width="10%" height="25" class="row-even">
                                        <div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor:pointer"
											onclick="editFeeGroup('<bean:write name="id" property="id" />','<bean:write name="id" property="name" />',
											'<bean:write name="id" property="optional"/>')"/>
										</div>
										<td width="8%" height="25" class="row-even">
                                       <div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor:pointer"
											onclick="deleteFeeGroup('<bean:write name="id" property="id" />')" /></div>
										</td>                                   
									</tr>
								  <c:set var="temp" value="1"/>
                                </c:when>
									<c:otherwise>
										<tr>
											<td height="25" class="row-white">
											<div align="center"><c:out value="${count+1}"/></div>
											</td>

											<td height="35" class="row-white" align="center">
											<bean:write name="id" property="name" /></td>
											<td height="25" class="row-white" align="center">
											<c:choose>
											<c:when test="${id.optional == true}">Yes</c:when>
											<c:otherwise>No</c:otherwise>
											</c:choose>
											</td>
											<td height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor:pointer"
												onclick="editFeeGroup('<bean:write name="id" property="id" />','<bean:write name="id" property="name" />',
												'<bean:write name="id" property="optional"/>')" />
											</div>
											<td height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor:pointer"
												onclick="deleteFeeGroup('<bean:write name="id" property="id" />')" /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
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
        
        
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>