<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script type="text/javascript">
	function resetValues() {
		document.getElementById("location").value = "";
		document.getElementById("invCampusId").selectedIndex =0;
		if (document.getElementById("method").value == "updateInventoryLocation") {
			document.getElementById("location").value = document.getElementById("origLocation").value;
		}
		resetErrMsgs();
	}
	function editLocation(id, campus, loaction) {
		document.getElementById("method").value = "updateInventoryLocation";
		document.getElementById("id").value = id;
		document.getElementById("location").value = loaction;
		document.getElementById("invCampusId").value = campus;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origLocation").value = loaction;
	
		resetErrMsgs();
	}	
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "InvLocation.do?method=activateInvLocation&id=" + id;
	}		
	function deleteLocation(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "InvLocation.do?method=deleteInvLocation&id="
					+ id ;
		}
	}
	function empSearch(searchValue){
		var sda = document.getElementById("employeeId");
		var len = sda.length;
		var searchValueLen = searchValue.length;
		for(var m =0; m<len; m++){
			sda.options[m].selected = false;		
		}
		for(var j=0; j<len; j++)
		{
			for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
			}
		}
	}	
	
</script>
<html:form action="/InvLocation">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "
"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addInventoryLocation" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="inventoryLocationForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="origLocation" styleId="origLocation"/>
	  
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/>&gt;&gt; Inventory Location &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> Inventory Location</strong></td>
	
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
	              		<td width="20%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.inventory.InvCampus" /></div>
											</td>
											<td width="26%" height="25" class="row-even"><span
												class="star"> <html:select property="invCampusId"
												styleClass="comboLarge" styleId="invCampusId">
												<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>
												<html:optionsCollection name="campusList"
													label="campusName" value="id" />
											</html:select></span></td>
											
					                     <td width="26%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.admin.InvLocation" /></div>
											</td>
											<td width="26%" height="25" class="row-even"><span
												class="star"> <html:text property="location"
												styleClass="TextBox" styleId="location" size="20"
												maxlength="100" /> </span></td>
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
	
	            <td width="5%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
	            <td width="2%"></td>
				<logic:notEmpty name="inventoryLocationForm" property="superMainPage">
							<td><html:button property="" styleClass="formbutton"> Go To Main Page"
 							onclick="goToMainPage('<bean:write name="inventoryLocationForm" property="superMainPage" scope="session"/>')"</html:button>
							</td></logic:notEmpty>
							<logic:empty name="inventoryLocationForm" property="superMainPage"><td></td></logic:empty>
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
	                <td height="25" class="row-odd" align="center">Campus</td>
	                <td height="25" class="row-odd" align="center">Location</td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
				<logic:notEmpty name="locationList">
					<logic:iterate id="locationList" name ="locationList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                 <td width="30%" height="25" align="center"><bean:write name = "locationList" property="invCampusName"/></td>
			                <td width="30%" height="25" align="center"><bean:write name = "locationList" property="name"/></td>
			                <td width="12%" height="25"><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editLocation('<bean:write name="locationList" property="id"/>',
								'<bean:write name="locationList" property="invCampusId"/>','<bean:write name="locationList" property="name"/>')"></div></td>
			                <td width="12%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteLocation('<bean:write name="locationList" property="id"/>')"></div></td>
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