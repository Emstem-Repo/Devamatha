<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">

function addFeeHeadings() {
	document.getElementById("method").value="addFeeHeadings";
}

function editFeeHeadings(id) {
	document.getElementById("feeHID").value=id;
	document.location.href = "feeHeadings.do?method=editFeeHeadings&feeHeadingsId="+id;
}

function updateFeeHeadings() {

	document.getElementById("method").value = "updateFeeHeadings"; 
}

function reActivate() {
	var feename = document.getElementById("fName").value;	
	document.location.href="feeHeadings.do?method=reActivateFeeHeadings&feesName="+feename;
}

function deleteFeeHeadings(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	document.getElementById("feeHID").value=id;
	if(deleteConfirm)
	document.location.href = "feeHeadings.do?method=deleteFeeHeadings&feeHeadingsId="+id;
}

</script>

<html:form action="/feeHeadings" method="POST">
<c:choose>
	<c:when test="${feeHeadingsOperation == 'edit'}">
		<html:hidden property="method" styleId="method" value="updateFeeHeadings" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addFeeHeadings" />
	</c:otherwise>
</c:choose>
<html:hidden property="pageType" value="1"/>
<html:hidden property="formName" value="feeHeadingsForm"/>
<html:hidden property="feeHeadingsId" styleId="feeHID" />
<table width="98%" border="0">
  <tr>
     <td><span class="heading">
     <bean:message key="knowledgepro.fee"/>
     <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.feesHeadings"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.feesHeadings"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
           	<td colspan="6" align="left"><br>
				<div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
        		<div id="errorMessage"><FONT color="red"><html:errors/></FONT>
               	<FONT color="green"><html:messages id="msg" property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out><br>
				</html:messages></FONT></div>
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
                    <td width="19%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.feegroup"/>:</div></td>
                    <td width="44%" height="25" class="row-even" ><span class="star">
                     <html:select property="feeGroup" styleId="feegroup" styleClass="combo">
                     <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
                     <html:optionsCollection property="feeGroupToList" label="name" value="id"/>
                     </html:select>
                    </span></td>
                    <td width="16%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.feesHeadings"/>:</div></td>
                    <td width="21%" class="row-even" ><span class="star">
                      <html:text property="feesName" styleId="fName" size="20" maxlength="50"/>
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
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
              <tr>
                <td width="45%" height="27"><div align="right">
                <c:choose>
            		<c:when test="${feeHeadingsOperation == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateFeeHeadings()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addFeeHeadings()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                <c:choose>
					<c:when test="${feeHeadingsOperation == 'edit'}">
						<html:reset property="" styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:reset>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.cancel"/></html:button>
					</c:otherwise>
				</c:choose>
                </td>
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
                    <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.fee.feegroup"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.fee.feesHeadings"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                
                   <c:set var="temp" value="0"/>
                       <logic:iterate id="fee" name="feeHeadingsForm" property="feeHeadingList" indexId="count">
	                       <c:choose>
                            <c:when test="${temp == 0}">
                           	<tr>
                   				<td width="6%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   				<td width="41%" height="25" class="row-even" align="center"><bean:write name="fee" property="feeGroupTO.name"/></td>
                   				<td width="41%" class="row-even" align="center"><bean:write name="fee" property="name"/></td>
			                    <td width="6%" height="25" class="row-even" ><div align="center">
			                    <img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editFeeHeadings('<bean:write name="fee" property="id"/>')"></div></td>
                   				<td width="6%" height="25" class="row-even" ><div align="center">
                   				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFeeHeadings('<bean:write name="fee" property="id"/>')"></div></td>
							</tr>
                      		<c:set var="temp" value="1"/>
                   		 	</c:when>
                    	    <c:otherwise>
		                      <tr>
               					<td width="6%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               					<td width="41%" height="25" class="row-white" align="center"><bean:write name="fee" property="feeGroupTO.name"/></td>
               					<td width="41%" class="row-white" align="center"><bean:write name="fee" property="name"/></td>
               					<td width="6%" height="25" class="row-white" ><div align="center">
               					<img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer" onclick="editFeeHeadings('<bean:write name="fee" property="id"/>')"></div></td>
               					<td width="6%" height="25" class="row-white" ><div align="center">
               					<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFeeHeadings('<bean:write name="fee" property="id"/>')"></div></td>
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