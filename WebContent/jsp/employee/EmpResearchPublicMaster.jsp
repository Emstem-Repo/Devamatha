<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	
	function resetValue() {
		document.getElementById("empResPubName").value = "";
		if (document.getElementById("method").value == "updateResPubMaster") {
			document.getElementById("empResPubName").value = document.getElementById("origEmpResPubName").value;
		}
		resetErrMsgs();
	}
	
	 function editResPubMaster(id){
		document.location.href = "empResearchPublicDetails.do?method=editResPubMaster&id=" + id;
		}
	function deleteResPubMaster(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "empResearchPublicDetails.do?method=deleteResPubMaster&id="
					+ id ;
		}
	}
	function reActivate(){
		document.location.href = "empResearchPublicDetails.do?method=reactivateResPubMaster";
	}
	
</script>
<html:form action="/empResearchPublicDetails">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateResPubMaster"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addResPubMaster" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="EmpResPubDetailsForm" />
	<html:hidden property="origEmpResPubName"	styleId="origEmpResPubName" name="EmpResPubDetailsForm"/>
	<html:hidden property="pageType" value="1" />
	  
	<table width="100%" border="0">
	  <tr>
	   <td><span class="Bredcrumbs">Employee<span
				class="Bredcrumbs">&gt;&gt; Research and Publications
			&gt;&gt;</span></span></td>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Research and Publications</strong></td>
	
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td  class="news">
			<div align="right">
				<FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
				<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
				</html:messages> </FONT></div></td>
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
	                <td width="24%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span><bean:message key="KnowledgePro.employee.Name"/>:</div></td>
	                <td width="26%" class="row-even">
					<html:text property="empResPubName" styleId="empResPubName" name="EmpResPubDetailsForm" maxlength="50"></html:text>
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
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right">
	              <c:choose>
					<c:when
						test="${operation != null && operation == 'edit'}">
						<html:submit property="" styleClass="formbutton" value="Update"
							styleId="submitbutton">
						</html:submit>
					</c:when>
					<c:otherwise>
						<html:submit property="" styleClass="formbutton" value="Submit"
							styleId="submitbutton">
						</html:submit>
					</c:otherwise>
				</c:choose>
	            </div></td>
	            <td width="2%"></td>
	
	            <td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetValue()"></html:button></td>
	          </tr>
	        </table> </td>
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
	                <td height="25" class="row-odd" align="center"><bean:message key="KnowledgePro.employee.Name"/></td>
					<td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
			<logic:notEmpty property="resPubMasterToList" name="EmpResPubDetailsForm">
					<logic:iterate id="toList" name="EmpResPubDetailsForm" indexId="count" property="resPubMasterToList">
					   <tr>
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="25%" height="25" align="center"><bean:write name = "toList" property="name"/></td>
 		                    <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editResPubMaster('<bean:write name="toList" property="id"/>')"></div></td>
			                <td width="12%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteResPubMaster('<bean:write name="toList" property="id"/>')"></div></td>
			              </tr>
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
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news">&nbsp;</td>
	
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