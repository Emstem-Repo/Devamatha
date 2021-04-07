<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="java.util.Map,java.util.HashMap" %>
<script type="text/javascript">

function deleteFeeDivision(id) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href="ManageFeeDivision.do?method=deleteFeeDivisionEntry&divisionId="+id;
	}
}

function resetErrorMsgs() {	
	var divisionNameOriginal =  document.getElementById("divisionNameOriginal").value;
	if(divisionNameOriginal !=null && divisionNameOriginal != '' ){
		document.getElementById("divisionName").value = divisionNameOriginal;
		resetErrMsgs();
	} else {
		resetFieldAndErrMsgs();
	}
	
}

function editFeeDivision(feeDivisionId, feeDivisionName) {	
	document.getElementById("divisionName").value = feeDivisionName;
	document.getElementById("divisionId").value = feeDivisionId;
	document.getElementById("divisionNameOriginal").value = feeDivisionName;
	document.getElementById("method").value = "editFeeDivisionEntry";
	document.getElementById("submitbutton").value = "update";
}
function reActivate() {
	var divisionName = document.getElementById("divisionName").value;	
	document.location.href="ManageFeeDivision.do?method=reactivateFeeDivisionEntry&divisionName="+divisionName;
}

</script>
<html:form action="/ManageFeeDivision" focus="divisionName">

	<c:choose>
		<c:when test=""></c:when>
	</c:choose>
	<c:choose>
		<c:when test="${admOperation != null && admOperation == 'editFeeDivisionEntry'}">
			<html:hidden property="method" styleId="method"
				value="editFeeDivisionEntry" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addFeeDivisionEntry" />
		</c:otherwise>
	</c:choose>
<html:hidden property="divisionId" styleId="divisionId"/>
<html:hidden property="divisionNameOriginal" styleId="divisionNameOriginal"/>
<html:hidden property="formName" value="feeDivisionEntryForm"/>
<html:hidden property="pageType" value="1"/>
<html:hidden property="admOperation" styleId="admOperation" value = "add"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.feedivisionentry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admission.feedivisionentry"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
       <div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
                    <td width="47%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> 
                    <bean:message key="knowledgepro.admission.feedivisionname"/>: </div></td>
                    <td width="53%" height="25" class="row-even" ><span class="star">
                    <html:text property="divisionName" styleId="divisionName"
								size="16" maxlength="50"></html:text> 
                    
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
              <tr>
                <td width="45%" height="27">
                	<div align="right"><c:choose>
										<c:when
											test="${admOperation != null && admOperation == 'editFeeDivisionEntry'}">
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
                    <td height="25" class="row-odd" ><div align="center">Division Name</div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                  	<c:set var="temp" value="0"/>
                  		<logic:iterate name="feeDivisionEntryForm" property="feeDivisionToList"
									id="id" indexId="count">
                               <c:choose>
								 <c:when test="${temp == 0}">
									<tr>
										<td width="7%" height="25" class="row-even">
										<div align="center"><c:out value="${count+1}"/></div>
										</td>
										<td width="75%" height="25" class="row-even" align="center"><bean:write
											name="id" property="name" /></td>
										<td width="10%" height="25" class="row-even">
                                        <div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor:pointer"
											onclick="editFeeDivision('<bean:write name="id" property="id" />','<bean:write name="id" property="name" />')"/>
										</div>
										<td width="8%" height="25" class="row-even">
                                       <div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor:pointer"
											onclick="deleteFeeDivision('<bean:write name="id" property="id" />')" /></div>
										</td>                                   
									</tr>
								  <c:set var="temp" value="1"/>
                                </c:when>
									<c:otherwise>
										<tr>
											<td height="25" class="row-white">
											<div align="center"><c:out value="${count+1}"/></div>
											</td>

											<td height="25" class="row-white" align="center"><bean:write name="id"
												property="name" /></td>
											<td height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor:pointer"
												onclick="editFeeDivision('<bean:write name="id" property="id" />','<bean:write name="id" property="name" />')" />
											</div>
											<td height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor:pointer"
												onclick="deleteFeeDivision('<bean:write name="id" property="id" />')" /></div>
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
