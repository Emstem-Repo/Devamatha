<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function getState(countryId) {
		getStatesByCountry("stateMap",countryId,"stateId",updateStates);
		 document.getElementById("stateOthers").value = "";
		 document.getElementById("stateOthers").style.display = "none";		 
		resetOption("stateId");
	}
	
	function updateStates(req) {
		updateOptionsFromMapWithOther(req,"stateId","- Select -");
	}
	function loadVendor(id){
		document.location.href = "Vendor.do?method=loadVendor&id="
			+ id;
	}
	function viewVendor(id){
		var url ="Vendor.do?method=initView&id="+id;
		myRef = window.open(url,"viewVendor","left=20,top=20,width=900,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "Vendor.do?method=activateVendor&id=" + id;
	}
	function deleteVendor(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "Vendor.do?method=deleteVendor&id="
					+ id ;
		}
	}
	function resetValues(){
		resetFieldAndErrMsgs();
		resetOption("stateId");		
		document.getElementById("authorisedVendor_2").checked = true;		
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
	
</script>

<html:form action="Vendor" method="post">
	<html:hidden property="formName" value="vendorForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="pageType" value="1" />

	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateVendor" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addVendor" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="method" styleId="method"	value="loadStudent" />
	<table width="99%" border="0">
	
	<tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.inventory"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.inventory.vendor.master"/> &gt;&gt;</span></span></td>	  
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.vendor.master"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                <tr >
	
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.inventory.vendor.org.name"/> </div></td>
	                  <td class="row-even" ><html:text property="vendorName" styleClass="TextBox"
									styleId="vendorName" size="20" maxlength="100" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.vendor.contact.person"/> </div></td>
	                  <td class="row-even" ><html:text property="contactPerson" styleClass="TextBox"
									styleId="contactPerson" size="20" maxlength="100" name="vendorForm" /></td>
	                </tr>
	                <tr >
	
	                  <td width="26%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="admissionForm.studentinfo.addrs1.label"/></div></td>
	                  <td class="row-even" ><label>
	                  <html:text property="addressLine1" styleClass="TextBox"
									styleId="addressLine1" size="20" maxlength="100" name="vendorForm" />
	                  </label></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.addrs2.label"/></div></td>
	                  <td width="26%" class="row-even" > <html:text property="addressLine2" styleClass="TextBox"
									styleId="addressLine2" size="20" maxlength="100" name="vendorForm" /></td>
	
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="admissionForm.studentinfo.addrs1.country.label"/> </div></td>
	                  <td class="row-even" >
	                  <html:select
							property="countryId" styleClass="combo" styleId="country" onchange="getState(this.value)">
							<html:option value="">
								<bean:message key="knowledgepro.admin.select" />
							</html:option>
							<html:optionsCollection name="countriesMap" label="value"
								value="key" />
						</html:select></td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="admissionForm.studentinfo.addrs1.state.label"/></div></td>
	                  <td class="row-even" >
						<%String dynaStyle=""; %>
							<c:choose>
							<c:when test="${operation == 'edit' && vendorForm.stateOthers != null && vendorForm.stateOthers != ''}">
							<logic:equal value="Other" property="stateOthers" name="vendorForm">
								<%dynaStyle="display:block;"; %>
							</logic:equal>				
						</c:when>
							<c:otherwise>

							<logic:equal value="Other" property="stateId" name="vendorForm">
								<%dynaStyle="display:block;"; %>
							</logic:equal>
							<logic:notEqual value="Other" property="stateId" name="vendorForm">
								<%dynaStyle="display:none;"; %>
							</logic:notEqual>
							</c:otherwise>
							</c:choose>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
               					<tr>
               						<td>
	                 		 <html:select name="vendorForm" property="stateId"
								styleId="stateId" styleClass="comboLarge" onchange="funcOtherShowHide('stateId','stateOthers')">
								<html:option value="">- Select -</html:option>
								<c:choose>
									<c:when test="${operation == 'edit'}">
										<c:if
											test="${vendorForm.countryId != null && vendorForm.countryId != ''}">
											<html:optionsCollection name="stateMap" label="value"
												value="key" />
											<html:option value="Other">Other</html:option>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if
											test="${vendorForm.countryId != null && vendorForm.countryId != ''}">
											<c:set var="stateMap"
												value="${baseActionForm.collectionMap['stateMap']}" />
											<c:if test="${stateMap != null}">
												<html:optionsCollection name="stateMap" label="value"
													value="key" />
											</c:if>
											<html:option value="Other">Other</html:option>
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select>
									</td>
								</tr>
							<tr><td><html:text property="stateOthers" size="10" maxlength="30" styleId="stateOthers" style="<%=dynaStyle %>"></html:text></td></tr>
							</table>
						</td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="admissionForm.studentinfo.addrs1.city.label"/> </div></td>
	                  <td class="row-even" ><html:text property="city" styleClass="TextBox"
									styleId="city" size="20" maxlength="50" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span> <bean:message key="knowledgepro.admission.zipCode"/> </div></td>
	
	                  <td class="row-even" ><html:text property="zipCode" styleClass="TextBox"
									styleId="zipCode" size="20" maxlength="6" name="vendorForm" /></td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentinfo.phone.label"/> </div></td>
	                  <td class="row-even" >
	                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
													<tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td>
													<td><html:text property="phoneCountryCode" styleClass="TextBox" onkeypress="return isNumberKey(event)" styleId="phoneCountryCode" size="4" maxlength="3" name="vendorForm" /></td></tr>
													<tr><td><bean:message key="admissionForm.phone.areacode.label"/>
													</td><td><html:text property="phoneStateCode" onkeypress="return isNumberKey(event)" styleClass="TextBox"
																styleId="phoneStateCode" size="5" maxlength="6" name="vendorForm" /></td></tr>
													<tr><td><bean:message key="admissionForm.phone.main.label"/></td>
													<td><html:text property="phone" onkeypress="return isNumberKey(event)" styleClass="TextBox"
															styleId="phone" size="20" maxlength="30" name="vendorForm" /></td></tr>
												</table>
	                  </td>
	                  <td class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.mobile.label"/> </div></td>
	
	                  <td class="row-even" >
	                  <table width="100%" border="0" cellpadding="0" cellspacing="0">
				                              	  <tr><td><bean:message key="admissionForm.phone.cntcode.label"/></td><td><html:text property="mobileCountryCode" styleClass="TextBox"
									styleId="mobileCountryCode" onkeypress="return isNumberKey(event)" size="4" maxlength="3" name="vendorForm" /></td></tr>
				                                  <tr><td> <bean:message key="admissionForm.mob.no.label"/> </td><td><html:text property="mobile" styleClass="TextBox"
									styleId="mobile" size="20" onkeypress="return isNumberKey(event)" maxlength="30" name="vendorForm" /></td></tr>
												</table>
	                  
	                  </td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="admissionForm.studentinfo.email.label"/></div></td>
	                  <td class="row-even" ><html:text property="emailId" size="15" maxlength="50" styleId="emailId"/></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.fax"/> </div></td>
	                  <td class="row-even" ><html:text property="fax" styleClass="TextBox"
									styleId="fax" size="20" maxlength="50" name="vendorForm" />
	
					</td>
	                </tr>
	 				<tr >
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.tan"/> </div></td>
	                  <td class="row-even" ><html:text property="tan" styleClass="TextBox"
									styleId="tan" size="20" maxlength="50" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.pan"/> </div></td>
	                  <td class="row-even" ><html:text property="pan" styleClass="TextBox"
									styleId="pan" size="20" maxlength="50" name="vendorForm" /></td>
	
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.pan.pin"/> </div></td>
	                  <td class="row-even" ><html:text property="pin" styleClass="TextBox"
									styleId="pin" size="20" maxlength="50" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.vat"/> </div></td>
	                  <td class="row-even" ><html:text property="vat" styleClass="TextBox"
									styleId="vat" size="20" maxlength="50" name="vendorForm" /></td>
	                </tr>
	                <tr >
	
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.bank.ac.no"/> </div></td>
	                  <td class="row-even" ><html:text property="bankAcNo" styleClass="TextBox"
									styleId="bankAcNo" size="20" maxlength="50" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.bank"/> &amp; <bean:message key="knowledgepro.inventory.vendor.branch"/> </div></td>
	                  <td class="row-even" ><html:text property="bankBranch" styleClass="TextBox"
									styleId="bankBranch" size="20" maxlength="50" name="vendorForm" /></td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.payment.terms"/> </div></td>
	
	                  <td class="row-even" ><html:text property="paymentTerms" styleClass="TextBox"
									styleId="paymentTerms" size="20" maxlength="50" name="vendorForm" /></td>
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.delivery.schedule"/> </div></td>
	                  <td class="row-even" ><html:text property="deliverySchedule" styleClass="TextBox"
									styleId="deliverySchedule" size="20" maxlength="50" name="vendorForm" /></td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.payment.mode"/> </div></td>
	                  <td class="row-even" >
					<html:select name="vendorForm" property="paymentMode"
								styleId="paymentMode" styleClass="comboLarge">						
	                   <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
						<html:option value="Draft"><bean:message key="knowledgepro.inventory.vendor.draft"/></html:option>
	                    <html:option value ="Cash"><bean:message key="knowledgepro.inventory.vendor.cash"/></html:option>
	                    <html:option value="Electronic Transfer"><bean:message key="knowledgepro.inventory.vendor.electronic.transfer"/></html:option>
	                    <html:option value="Cheque"><bean:message key="knowledgepro.inventory.vendor.cheque"/></html:option>
					</html:select>
					</td>
	
	                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.vendor.authorised.vendor"/> </div></td>
	                  <td class="row-even" >
					 <input type="radio" name="authorisedVendor" id="authorisedVendor_1" value="true"/> <bean:message key="knowledgepro.yes"/>
                   			 <input type="radio" name="authorisedVendor" id="authorisedVendor_2" value="false" checked="checked"/> <bean:message key="knowledgepro.no"/>
							<script type="text/javascript">
								var authorisedVendor = "<bean:write name='vendorForm' property='authorisedVendor'/>";
								if(authorisedVendor == "true") {
				                        document.getElementById("authorisedVendor_1").checked = true;
								}	
							</script>

					</td>
	                </tr>
	                <tr >
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.vendor.category.item"/></div></td>
	                  <td class="row-even" ><nested:select property="selectedCategory" styleClass="body" multiple="multiple" size="10"  styleId="selectedCategory" style="width:250px">
							<nested:optionsCollection name="vendorForm" property="categoryMap" label="value" value="key" styleClass="comboBig"/>
						</nested:select></td>
	                 <td height="25" class="row-odd" valign="top"><div align="right"><bean:message key="knowledgepro.hostel.reservation.remarks"/> </div></td>
	                  <td class="row-even" valign="top">
	                  <html:textarea property="remarks" styleClass="TextBox" cols="18" rows="2" styleId="remarks"></html:textarea>
					</td>
	                </tr>
	
	              </table>
	             
	              </td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	
	            <td><img src="images/06.gif" /></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	
	            <td width="45%" height="35" align="right"> <c:choose>
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
				</c:choose></td>				
	            <td width="2%" height="35" align="center">&nbsp;</td>
	            <td width="5%" height="35" align="left">
				<c:choose>
					<c:when test="${operation == 'edit'}">
						<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton"
							value="Reset" onclick="resetValues()"></html:button>
					</c:otherwise>
				</c:choose></td><td width="2%"></td>
				<logic:notEmpty name="vendorForm" property="superMainPage">
							<td><html:button property="" styleClass="formbutton"> Go To Main Page"
 							onclick="goToMainPage('<bean:write name="vendorForm" property="superMainPage" scope="session"/>')"</html:button>
							</td></logic:notEmpty>
							<logic:empty name="vendorForm" property="superMainPage"><td></td></logic:empty>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	
	        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td ><img src="images/01.gif" width="5" height="5" /></td>
	            <td width="914" background="images/02.gif"></td>
	            <td><img src="images/03.gif" width="5" height="5" /></td>
	          </tr>
	          <tr>
	            <td width="5"  background="images/left.gif"></td>
	            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	
	              <tr >
	                <td width="34" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                <td class="row-odd" ><bean:message key="knowledgepro.inventory.vendor.org"/> </td>
	                <td class="row-odd" ><bean:message key="knowledgepro.inventory.vendor.contact"/></td>
	                <td width="132" class="row-odd" ><bean:message key="knowledgepro.inventory.vendor.address"/></td>
	                <td width="33" class="row-odd" ><bean:message key="knowledgepro.view"/></td>
	
	                <td width="28" class="row-odd"><bean:message key="knowledgepro.edit"/></td>
	                <td width="48" class="row-odd"><bean:message key="knowledgepro.delete"/></td>
	              </tr>
				<logic:iterate id="vendorList" name = "vendorList" type = "com.kp.cms.to.inventory.VendorTO" indexId="count">
              		<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
		                <td height="25" ><div align="center"><c:out value="${count + 1}" /></div></td>
		                <td width="223" ><bean:write name = "vendorList" property="vendorName"/> </td>
		                <td width="169" ><bean:write name = "vendorList" property="contactPerson"/></td>
		
		                <td ><bean:write name = "vendorList" property="vendorAddressLine1"/></td>
		                <td align="center"><img src="images/View_icon.gif" width="24" height="21"
						style="cursor:pointer" onclick="viewVendor('<bean:write name="vendorList" property="id"/>')"></td>
		                <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16"
							style="cursor:pointer" onclick="loadVendor('<bean:write name="vendorList" property="id"/>')"></div></td>
		                <td><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16"
						style="cursor:pointer" onclick="deleteVendor('<bean:write name="vendorList" property="id"/>')" ></div></td>
	              </tr>
				</logic:iterate>
	            </table>
	           
	             </td>
	            <td width="5" height="30"  background="images/right.gif"></td>
	          </tr>
	          <tr>
	            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	            <td background="images/05.gif"></td>
	            <td><img src="images/06.gif" /></td>
	
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading">&nbsp;</td>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
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