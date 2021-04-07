<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function viewHistory(itemNo, itemName){
		var url ="AmcDetails.do?method=viewHistory&selectedItemNo="+itemNo+"&itemName=" + itemName;
		myRef = window.open(url,"viewHistory","left=20,top=20,width=900,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	function clearField(field) {
		if (field.value == "0.0") {
			field.value = "";
		}
		if (field.value == "0") {
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
		
</script>

<html:form action="/AmcDetails">
	<html:hidden property="method" styleId="method" value = "updateAmcDetails"/>
	<html:hidden property="formName" value="amcDetailsForm" />
	<html:hidden property="pageType" value="2" />
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.amc.details.entry"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.amc.details.entry"/></td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="news">
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
	                  <td width="25%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.amc.details.item.category"/></div></td>
	                  <td width="25%" class="row-even" ><bean:write name="amcDetailsForm" property="itemCategoryName"/></td>
	                  <td width="25%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.inventory.update.warranty.sl.no"/></div></td>
	                  <td width="25%" class="row-even" ><bean:write name="amcDetailsForm" property="itemNo"/></td>
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
	        <td class="heading">&nbsp;</td>
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
	                  <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.amc.details.item.category.name"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.update.warranty.sl.no"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.itemname"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.amc.details.warranty.start.date"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.amc.details.warranty.end.date"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.amc.details.history"/></td>
	                  <td class="row-odd" align="center"><span class="Mandatory">* </span>Vendor</td>
	                  <td class="row-odd" align="center"><span class="Mandatory">* </span>Amount</td>
	                  <td class="row-odd" align="center"><span class="Mandatory">* </span><bean:message key="knowledgepro.inventory.amc.details.new.amc.start.date"/></td>
	                  <td class="row-odd" align="center"><span class="Mandatory">* </span><bean:message key="knowledgepro.inventory.amc.details.new.amc.end.date"/></td>
	                  <td class="row-odd" align="center"><bean:message key="admissionFormForm.comments.required"/></td>
	
	                </tr>
					<nested:iterate id="amcList" name ="amcDetailsForm" property="amcList" type = "com.kp.cms.to.inventory.InvAmcTO" indexId="count">
						<%
							String styleDate1 = "datePick" + count;
							String styleDate2 = "datePicker" + count;
						%>

	                <c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
	                  <td height="25" ><div align="center"><c:out value="${count +1}"/></div></td>
	                  <td width="143"><bean:write name = "amcList" property="invItemCategory.name"/></td>
	                  <td width="55"><bean:write name = "amcList" property="itemNo"/></td>
	                  <td width="66"><bean:write name="amcList" property="invItem.name"/></td>
	                  <td width="88" align="center"><bean:write name="amcList" property="warrantyStartDate"/></td>
	
	                  <td width="88" align="center"><bean:write name="amcList" property="warrantyEndDate"/></td>
	                  <td width="80" align="center">
						<img src="images/View_icon.gif" width="24" height="21"
						style="cursor:pointer" onclick="viewHistory('<bean:write name="amcList" property="itemNo"/>', '<bean:write name="amcList" property="invItem.name"/>')">
					</td>

						<td width="10%" height="25" class="row-even" >
						<nested:select
							property="vendorId" styleClass="combo"  >
							<option value=""><bean:message key="knowledgepro.admin.select"/></option>
							<nested:optionsCollection name="amcDetailsForm" label="vendorName" value="id" property="vendorList" />
						</nested:select>
	                   </td>
						<td width="7%" height="25" class="row-even" >
						<nested:text property="amount" styleId="amount" size="10" maxlength="10"
									onkeypress="return isDecimalNumberKey(this.value,event)"
									onkeyup="onlyTwoFractions(this,event)"
									onfocus="clearField(this)" onblur="checkForEmpty(this)"/>
						</td>
	                  <td width="13%"><table width="99%" border="0" cellspacing="0" cellpadding="0">
					  <tr>
					    <td height="25" width="13%" align="left"><nested:text property="newWarrantyStartDate" styleId='<%=styleDate1%>' size="11" maxlength="11"></nested:text>
						<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'amcDetailsForm',
								// input name
								'controlname' :'<%=styleDate1%>'
							});
						</script> </td>
					  </tr>
					</table></td>
	
	                  <td width="13%"><table width="99%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td height="25" width="13%" align="left"><nested:text property="newWarrantyEndDate" styleId='<%=styleDate2%>' size="11" maxlength="11"></nested:text>
						<script language="JavaScript">
							new tcal( {
								// form name
								'formname' :'amcDetailsForm',
								// input name
								'controlname' :'<%=styleDate2%>'
							});
						</script></td>
				  </tr>
				</table></td>
	                  <td width="88" class="row-even" ><nested:text
										property="comments"	styleId="comments"size="10" maxlength="200"/></td>
	                </tr>
					</nested:iterate>
	              </table>
				  </td>
	              <td width="5" height="30"  background="images/right.gif"></td>
	            </tr>
	            <tr>
	              <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
	              <td background="images/05.gif"></td>
	
	              <td><img src="images/06.gif" /></td>
	            </tr>
	          </table>
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	      </tr>
		<tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35"><div align="right">
	                <html:submit property="" styleClass="formbutton" value="Submit"
							styleId="submitbutton"/>
	            </div></td>
	            <td width="2%"></td>
	
	            <td width="53%"><html:button property=""
					styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"/>
				</td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>

	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table>
	
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
