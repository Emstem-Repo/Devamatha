<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function resetValues() {
		document.getElementById("name").value = "";
		document.getElementById("isEmployee_1").checked = true;
		if (document.getElementById("method").value == "updateAttributeMaster") {
			document.getElementById("name").value = document.getElementById("origName").value;
			var isEmployee = document.getElementById("origIsEmployee").value;
			if(isEmployee == "true"){
				document.getElementById("isEmployee_1").checked = true;
			}
			else
			{
				document.getElementById("isEmployee_2").checked = true;
			}
		}
		resetErrMsgs();
	}
	function editAttribute(id, name, isEmployee) {
		document.getElementById("method").value = "updateAttributeMaster";
		document.getElementById("id").value = id;
		document.getElementById("name").value = name;
		if(isEmployee == "true"){
			document.getElementById("isEmployee_1").checked = true;
		}
		else
		{
			document.getElementById("isEmployee_2").checked = true;
		}
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origName").value = name;
		document.getElementById("origIsEmployee").value = isEmployee;
		resetErrMsgs();
	}	
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "AttributeMaster.do?method=activateAttribute&id=" + id;
	}		
	function deleteAttribute(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "AttributeMaster.do?method=deleteAttribute&id="
					+ id ;
		}
	}
	
</script>
<html:form action="/AttributeMaster">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateAttributeMaster"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addAttributeMaster" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="attributeMasterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="origName" styleId="origName"/>
	<html:hidden property="origIsEmployee" styleId="origIsEmployee"/>
	  
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/> &gt;&gt; <bean:message key="knowledgepro.employee.attribute.master"/> &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.employee.attribute.master"/></strong></td>
	
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
	            <td><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	              <tr >
	                <td width="24%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> <bean:message key="knowledgepro.employee.attribute.col"/></div></td>
	                <td width="26%" class="row-even">
						<html:text property="name" styleId="name" maxlength="50" styleClass="TextBox" size="16"/>
					</td>
	                <td width="17%" class="row-odd"><div align="right"><bean:message key="knowledgepro.employee.attribute.is.employee"/></div></td>
	                <td width="33%" class="row-even"><span class="star">
	                  <input type="radio" name="isEmployee" id="isEmployee_1" value="true"  checked="checked"/> <bean:message key="knowledgepro.admin.sec.EmployeeCategory"/>
                   		<input type="radio" name="isEmployee" id="isEmployee_2" value="false"/> <bean:message key="knowledgepro.employee.attribute.student"/>
                  			<script type="text/javascript">
							var employee = "<bean:write name='attributeMasterForm' property='isEmployee'/>";
							if(employee == "false") {
			                        document.getElementById("isEmployee_2").checked = true;
							}	
					</script>
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
	
	            <td width="53%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
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
	
	                <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                <td class="row-odd" align="center"><bean:message key="knowledgepro.employee.attribute"/></td>
					<td class="row-odd" align="center"><bean:message key="knowledgepro.employee.attribute.employee.student"/></td>
					<td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
				<logic:notEmpty name="attributeList">
					<logic:iterate id="attributeList" name ="attributeList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="50%" height="25" align="center"><bean:write name = "attributeList" property="name"/></td>
							<td width="35%" height="25" align="center"><bean:write name = "attributeList" property="value"/></td>
 		                    <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editAttribute('<bean:write name="attributeList" property="id"/>', '<bean:write name="attributeList" property="name"/>'
								, '<bean:write name="attributeList" property="isEmployee"/>')"></div></td>
			                <td><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteAttribute('<bean:write name="attributeList" property="id"/>')"></div></td>
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