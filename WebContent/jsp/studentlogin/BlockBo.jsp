<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function cancel() {
	document.location.href = "BlockBo.do?method=initBlockBo";
}

function addBlockBo() {
	document.getElementById("method").value="addBlockBo";
}

function editBlockBo(id) {
	document.location.href = "BlockBo.do?method=editBlockBo&id="+id;
	}

function updateBlockBo() {
	document.getElementById("method").value = "updateBlockBo"; 
	
}

function reActivate() {
	document.location.href="BlockBo.do?method=reActivateBlockBo";
}

function deleteBlockBo(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "BlockBo.do?method=deleteBlockBo&id="+id;
	}
function duplicateCheck(orderNo){
	document.getElementById("notValid").innerHTML = null;
	var locationId = document.getElementById("locationId").value;
	duplicateCheckingOfOrderNoByLocationId(orderNo,locationId,updateStudentDetails);
}
function updateStudentDetails(req){
	var responseObj = req.responseXML.documentElement;
	var value = responseObj.getElementsByTagName("value");
	if(value!=null){
		for ( var I = 0; I < value.length; I++) {
		if (value[I].firstChild != null) {
			var temp = value[I].firstChild.nodeValue;
			document.getElementById("notValid").innerHTML = temp;
		}
		}
	}
	
	}
</script>
</head>
<html:form action="/BlockBo" method="POST">
<html:hidden property="id" styleId="CMID"/>
<c:choose>
	<c:when test="${blockBo == 'edit'}">blockBo
		<html:hidden property="method" styleId="method" value="editBlockBo" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addBlockBo" />
	</c:otherwise>
</c:choose>
<html:hidden property="formName" value="blockBoForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.exam"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.hostel.blocks"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.blocks"/> </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
					<div id="notValid" style="color: red"><FONT color="red"></FONT></div>
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
            <td width="100%" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              <tr >
                 <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hlAdmission.location" />:</div></td>
				 <td width="25%" height="25" class="row-even"><span	class="star"> 
				 <html:select property="locationId" styleClass="comboLarge" styleId="locationId"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				 </html:option><html:optionsCollection name="locationList" label="empLocationName" value="empLocationId" /></html:select></span></td>
                <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.auditorium.block.name"/>:</div></td>
                <td width="25%" height="25" class="row-even"><span class="star">
                    <html:text property="blockName" styleId="blockName" size="30" maxlength="48" />
                </span></td>
               </tr>
               <tr >
                <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.exam.allotment.order.no"/>:</div></td>
                <td width="25%" height="25" class="row-even"><span class="star">
                    <html:text property="allotmentOrderNo"	styleId="allotmentOrderNo" size="2" maxlength="2" onchange="return isNumberKey(event)" onkeyup="duplicateCheck(this.value)"/>
                </span></td>
                <td width="25%" height="25" class="row-odd"><div align="right"></div></td>
                <td width="25%" height="25" class="row-even">
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
            		<c:when test="${blockBo == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateBlockBo()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addBlockBo()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 <c:choose>
					<c:when test="${blockBo == 'edit'}">
						<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="cancel()">
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
                <td width="100%" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td width="10%" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td width="30%" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.hlAdmission.location"/></td>
                    <td width="40%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.auditorium.block.name"/></td>
                    <td width="5%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.exam.allotment.order.no"/></td>
                    <td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td width="10%" height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="blockBoForm" property="blockBoList">
                <logic:iterate id="CME" name="blockBoForm" property="blockBoList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="locationName"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="blockName"/></td>
                   		<td height="25" class="row-even" align="center"><bean:write name="CME" property="blockOrderNO"/></td>
			            <td height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editBlockBo('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteBlockBo('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="locationName"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="blockName"/></td>
               			<td height="25" class="row-white" align="center"><bean:write name="CME" property="blockOrderNO"/></td>
               			<td height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editBlockBo('<bean:write name="CME" property="id"/>')"></div></td>
               			<td height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteBlockBo('<bean:write name="CME" property="id"/>')"></div></td>
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