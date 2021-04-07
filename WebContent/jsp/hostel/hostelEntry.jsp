<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function getStates(countryId) {
		getStatesByCountry("stateMap",countryId,"stateId",updateStates);
	}
	
	function updateStates(req) {
		updateOptionsFromMapWithOther(req,"stateId","- Select -");
	}
	function editHostelEntry(id){
		document.location.href = "HostelEntry.do?method=LoadHostelEntry&id="+ id;
	}
	function clearField(field) {
		if (field.value == "0.0"){
			field.value = "";
		}
		if(field.value == "0"){
			field.value = "";
		}
	}
	function checkForEmpty(field) {
		if (field.value.length == 0)
			field.value = "0.0";
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "HostelEntry.do?method=activateHostel&id="
				+ id;
	}
	function deleteHostelEntry(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "HostelEntry.do?method=deleteHostelEntry&id="
					+ id ;
		}
	}
	function resetValues(){
		resetFieldAndErrMsgs();
		resetOption("stateId");
		document.getElementById("isCanteenAttached_2").checked = true;		
	}	
	function showOther(srcid,destid){
		 document.getElementById(destid).style.display = "block";
	}
	function hideOther(id,destid){
		 document.getElementById(destid).style.display = "none";
	}
	function funcOtherShowHide(id,destid){
		var selectedVal=document.getElementById(id).value;
		if(selectedVal=="Other"){
			showOther(id,destid);
		}else{
			hideOther(id,destid);
		}
	}
	function downloadFile() {
		document.location.href = "HostelTermsCondtionDownLoadAction.do?=";
	}
	
	
</script>

<html:form action="HostelEntry" method="post" enctype="multipart/form-data">
	<html:hidden property="formName" value="hostelEntryForm" />
	<html:hidden property="id" styleId="id"/>
	<c:choose>
		<c:when test="${hlOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateHostelEntry" />
			<html:hidden property="pageType" value="2" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addHostelEntry" />
			<html:hidden property="pageType" value="1" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
	  <tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.entry"/> &gt;&gt;</span></span></td>	  
	  </tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	
	        <td colspan="2" background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.entry"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" class="news">
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
	        <td height="30" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	                  <td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.name"/></div></td>
	                  <td width="28%" height="25" class="row-even"><html:text property="name" styleClass="TextBox"
									styleId="name" size="30" maxlength="50" name="hostelEntryForm" /></td>
	                  <td width="22%" class="row-odd"><div align="right">Hostel For:</div></td>
	
	                  <td width="30%" class="row-even">
							<input type="radio" name="gender" id="gender_1" value="MALE" /> <bean:message key="knowledgepro.admission.male"/>
                    		<input type="radio" name="gender" id="gender_2" value="FEMALE" /> <bean:message key="knowledgepro.admission.female"/>
    						<script type="text/javascript">
								var isGender = "<bean:write name='hostelEntryForm' property='gender'/>";
								if(isGender == "MALE") {
				                        document.getElementById("gender_1").checked = "MALE";
								}
								if(isGender == "FEMALE") {
			                        document.getElementById("gender_2").checked = "FEMALE";
							}
							</script>
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
	        <td height="13" valign="top" background="images/Tright_03_03.gif"></td>
	
	        <td height="20" colspan="2" class="heading"><div align="center" class="heading">
	          <div align="left"><bean:message key="knowledgepro.hostel.entry.hostel.address"/></div>
	        </div></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="13" valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="20" colspan="2" class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5"></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5"></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td  valign="top"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
	                <tr class="row-white">
	
	                  <td width="13%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.hostel.entry.hostel.address.line1"/></div></td>
	                  <td width="19%" height="25" class="row-even"><html:text property="addressLine1" styleClass="TextBox"
									styleId="addressLine1" size="15" maxlength="250" name="hostelEntryForm" /></td>
	                  <td width="16%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.entry.hostel.address.line2"/></div></td>
	                  <td width="19%" height="25" class="row-even"><html:text property="addressLine2" styleClass="TextBox"
									styleId="addressLine2" size="15" maxlength="250" name="hostelEntryForm" /></td>
	                  <td width="13%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.country"/></div></td>
	
	                  <td width="20%" height="25" class="row-even"><html:select
								property="countryId" styleClass="combo" styleId="country" onchange="getStates(this.value)">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="countriesMap" label="value"
									value="key" />
							</html:select> </td>
	                </tr>
	                <tr class="row-white">
	                  <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.state"/></div></td>
	
	                  <td height="25">	
							<%String dynaStyle=""; %>
							<c:choose>
							<c:when test="${hlOperation == 'edit' && hostelEntryForm.stateOthers != null && hostelEntryForm.stateOthers != ''}">
							<logic:equal value="Other" property="stateOthers" name="hostelEntryForm">
								<%dynaStyle="display:block;"; %>
							</logic:equal>				
						</c:when>
							<c:otherwise>

							<logic:equal value="Other" property="stateOthers" name="hostelEntryForm">
								<%dynaStyle="display:block;"; %>
							</logic:equal>
							<logic:notEqual value="Other" property="stateOthers" name="hostelEntryForm">
								<%dynaStyle="display:none;"; %>
							</logic:notEqual>
							</c:otherwise>
							</c:choose>

							<table width="100%" border="0" cellpadding="0" cellspacing="0">
               					<tr>
               						<td>
								<html:select name="hostelEntryForm" property="stateId"
										styleId="stateId" styleClass="comboLarge" onchange="funcOtherShowHide('stateId','stateOthers')">
										<html:option value="">- Select -</html:option>
										<c:choose>
											<c:when test="${hlOperation == 'edit'}">
												<c:if
													test="${hostelEntryForm.countryId != null && hostelEntryForm.countryId != ''}">
													<html:optionsCollection name="stateMap" label="value"
														value="key" />
													<html:option value="Other">Other</html:option>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if
													test="${hostelEntryForm.countryId != null && hostelEntryForm.countryId != ''}">
													<c:set var="stateMap"
														value="${baseActionForm.collectionMap['stateMap']}" />
													<c:if test="${stateMap != null}">
														<html:optionsCollection name="stateMap" label="value"
															value="key" />
													</c:if>
												</c:if>
											</c:otherwise>
										</c:choose>
									</html:select>
									</td>
								</tr>
							<tr><td><html:text property="stateOthers" size="10" maxlength="30" styleId="stateOthers" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
							</td>
	                  <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admin.city"/>:</div></td>
	
	                  <td height="25" class="row-even"><html:text property="city" styleClass="TextBox"
									styleId="city" size="15" maxlength="50" name="hostelEntryForm" /></td>
	                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.zipCode"/></div></td>
	                  <td height="25" class="row-even"><html:text property="zipCode" styleClass="TextBox"
									styleId="zipCode" size="15" maxlength="9" name="hostelEntryForm" 
								onkeypress="return isNumberKey(event)"
								onblur="checkNumber(this)" /></td>
	                </tr>
	                <tr class="row-even">
	                  <td height="25" class="row-odd"><div align="right"><bean:message key="admissionForm.studentinfo.phone.label"/></div></td>
	                  <td height="25"><html:text property="phone" styleClass="TextBox"
							styleId="phone" size="15" maxlength="10" name="hostelEntryForm"
							onkeypress="return isNumberKey(event)"
							onblur="checkNumber(this)" /></td>
	                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.entry.fax.no"/></div></td>
	
	                  <td height="25"><html:text property="faxNo" styleClass="TextBox"
									styleId="faxNo" size="15" maxlength="10" name="hostelEntryForm" /></td>
	                  <td height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.hostel.entry.email"/></div></td>
	                  <td height="25"><html:text property="email" styleClass="TextBox"
									styleId="email" size="15" maxlength="50" name="hostelEntryForm" /></td>
	                </tr>
	            </table></td>
	            <td  background="images/right.gif" width="5" height="79"></td>
	          </tr>
	          <tr>
	
	            <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" ></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="36" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="47%" height="35"><div align="right">
	                <c:choose>
					<c:when
						test="${hlOperation != null && hlOperation == 'edit'}">
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
	            <td width="53%">
	            <c:choose>
					<c:when test="${hlOperation == 'edit'}">
						<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton"
							value="Reset" onclick="resetValues()"></html:button>
					</c:otherwise>
				</c:choose></td>
	          </tr>
	
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="94" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
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
	                  <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.hostel.entry.name"/></td>
	
	                  <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
	                  <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	                </tr>
					<logic:iterate id="hostelList" name = "hostelList" type = "com.kp.cms.to.hostel.HostelTO" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
		                  <td width="7%" height="25" ><div align="center"><c:out value="${count + 1}" /></div></td>
		                  <td width="56%" height="25"><bean:write name = "hostelList" property="name"/></td>
		                  <td width="12%" height="25"><div align="center"><img src="images/edit_icon.gif" 
							width="16" height="18" style="cursor:pointer"
							onclick="editHostelEntry('<bean:write name="hostelList" property="id"/>')" ></div></td>
		
		                  <td width="12%" height="25"><div align="center">
							<img src="images/delete_icon.gif" width="16" height="16"
							style="cursor:pointer" onclick="deleteHostelEntry('<bean:write name="hostelList" property="id"/>')"></div></td>
		                </tr>
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
	
	        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
	        <td colspan="2" valign="top" class="news"></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
	
	        <td colspan="2" background="images/TcenterD.gif"></td>
	        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
	      </tr>
	    </table>
</html:form>	    
