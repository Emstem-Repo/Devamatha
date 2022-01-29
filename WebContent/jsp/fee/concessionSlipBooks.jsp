<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script type="text/javascript"><!--
	function resetValues() {
		document.getElementById("startingNo").value = "";
		document.getElementById("endingNo").value = "";
		document.getElementById("bookNo").value = "";
		document.getElementById("type").selectedIndex = 0;
		
		if (document.getElementById("method").value == "updateConcSlipBooks") {
			document.getElementById("year").value = document.getElementById("origYear").value;
			document.getElementById("startingNo").value = document.getElementById("origStartNo").value;
			document.getElementById("endingNo").value = document.getElementById("origEndSlipNo").value;
			document.getElementById("bookNo").value = document.getElementById("origBookNo").value;
			document.getElementById("type").value = document.getElementById("origType").value;
			document.getElementById("startingPref").value = document.getElementById("origPrefix").value;
			document.getElementById("endPrefix").value = document.getElementById("origPrefix").value;
			
		}
		resetErrMsgs();
	}
	
	function editSlip(id, type, year, startNo, endNo, bookNo, startingPrefix) {
		document.getElementById("method").value = "updateConcSlipBooks";
		document.getElementById("id").value = id;
		document.getElementById("year").value =year;
		document.getElementById("bookNo").value = bookNo;
		document.getElementById("type").value = type;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origYear").value =year;
		document.getElementById("origBookNo").value = bookNo;
		document.getElementById("origType").value = type;
		//var startPrefix = "";
		//if(startNo.length >= 1){
		//	if(isNaN(startNo.charAt(0))){
		//		startPrefix = startNo.charAt(0);
		//	}
		//}
		//if(startNo.length >= 2){
		//	if(isNaN(startNo.charAt(1))){
		//		startPrefix = startPrefix + startNo.charAt(1);
		//	}
		//}
		document.getElementById("startingPref").value = startingPrefix;
		document.getElementById("endPrefix").value = startingPrefix;
		document.getElementById("origPrefix").value = startingPrefix;
		
		//var len = startPrefix.length;
		//var totalStartLen = startNo.length;
		//var totalEndLen = endNo.length;
			 
		document.getElementById("startingNo").value = startNo;
		document.getElementById("endingNo").value = endNo;
		document.getElementById("origStartNo").value = startNo;
		document.getElementById("origEndSlipNo").value = endNo;
		
		
		resetErrMsgs();
	}	
	function deleteSlipBook(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "ConcSlipBook.do?method=deleteSlipBooks&id="
					+ id ;
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "ConcSlipBook.do?method=activateSlipBooks&id=" + id;
	}	
	function assignEndNoPrefix(startPrefix){
		document.getElementById("endPrefix").value = startPrefix;
	}
	function checkNumber(field) {
		if (isNaN(field.value)) {
			field.value = "";
		}
	}
	function isAlphabets(evt)
	{  
		var charCode = (evt.which) ? evt.which : event.keyCode;
	   if(((charCode >= 97) && (charCode <= 122)) ||((charCode >= 65) && (charCode <= 90)) || charCode == 8) {
	      return true;
	   }	      
	   else{
	      return false;
		   
	   }
	}
	function checkAlphabet(field) {
		if (!isNaN(field.value)) {
			field.value = "";
		}
	}	
</script>	
<html:form action="/ConcSlipBook">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateConcSlipBooks"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addConcSlipBooks" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="concessionSlipBooksForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="origType" styleId="origType"/>
	<html:hidden property="origYear" styleId="origYear"/>
	<html:hidden property="origBookNo" styleId="origBookNo"/>
	<html:hidden property="origStartNo" styleId="origStartNo"/>
	<html:hidden property="origEndSlipNo" styleId="origEndSlipNo"/>
	<html:hidden property="origPrefix" styleId="origPrefix"/>


	<table width="99%" border="0">
	  
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.fee"/> &gt;&gt; <bean:message key="knowledgepro.fee.concession.slip.books"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.fee.concession.slip.books"/></td>
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
	                  <td height="25" class="row-odd" width="15%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.type.col"/></div></td>
	                  <td class="row-even" width="20%"><html:select
										property="type" styleClass="TextBox" styleId="type">
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
									<html:option value='<%=CMSConstants.CONCESSION_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.book.conc.type"/></html:option>
									<html:option value='<%=CMSConstants.INSTALLMENT_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.installment.type"/></html:option>
									<html:option value='<%=CMSConstants.SCHOLARSHIP_TYPE %>'><bean:message key="knowledgepro.fee.concession.slip.scholarship.type"/></html:option>
									</html:select></td>
						<td class="row-odd" width="15%">
							<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.concession.slip.book.year.col"/></div>
							</td>
							<td class="row-even" width="20%"><span class="star"> <input
								type="hidden" id="tempyear" name="tempyear"
								value="<bean:write name="concessionSlipBooksForm" property="finacialYear"/>" />
							<html:select name="concessionSlipBooksForm" property="finacialYear"
								styleId="year" styleClass="combo">
								<html:option value="">- Select -</html:option>
								<cms:renderFinancialYear>
								</cms:renderFinancialYear>
							</html:select> </span></td>
						<td height="25" class="row-odd" width="10%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.concession.slip.book.book.no.col"/></div></td>
						<td class="row-even" width="20"><html:text property="bookNo" maxlength="10" styleClass="TextBox" size="20" styleId="bookNo"></html:text> </td>
	                  </tr>
					<tr>
						<td height="25" class="row-odd" width="15%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.concession.slip.book.starting.no.col"/></div></td>
						<td class="row-even" width="20%"><html:text property="startPrefix" maxlength="2" styleClass="TextBox" size="2" styleId="startingPref" onblur="checkAlphabet(this), assignEndNoPrefix(this.value)" onkeypress="return isAlphabets(event)" ></html:text>&nbsp;&nbsp;<html:text property="startingNo" maxlength="8" styleClass="TextBox" size="15" styleId="startingNo" 
							onkeypress="return isNumberKey(event)"	onblur="checkNumber(this)"></html:text> </td>
						<td height="25" class="row-odd" width="15%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.concession.slip.book.ending.slip.no.col"/></div></td>
						<td class="row-even" width="20%"><html:text property="endPrefix" maxlength="2" styleClass="TextBox" size="2" styleId="endPrefix" onkeypress="return isAlphabets(event)" onblur="checkAlphabet(this)"></html:text>&nbsp;&nbsp;<html:text property="endingNo" maxlength="8" styleClass="TextBox" size="15" styleId="endingNo"
						onkeypress="return isNumberKey(event)"	onblur="checkNumber(this)"></html:text> </td>
						<td class="row-odd" width="10%">&nbsp;</td>
						<td class="row-even" width="20%">&nbsp;</td>
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
	            <td height="35" align="right"><c:choose>
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
				<td width="3" height="35" align="center">&nbsp;</td>
	              <td width="49%">
					<c:choose>
					<c:when
						test="${operation != null && operation == 'edit'}">
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button>
					</c:when>
					<c:otherwise>
		              <html:cancel  styleClass="formbutton" value="Reset">
		              </html:cancel>
					</c:otherwise>
					</c:choose>

				</td>
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
	                  <td width="56" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.fee.concession.slip.book.year"/></td>
	                  <td width="230" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.fee.concession.slip.book.type"/> </td>
	                  <td width="235" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.fee.concession.slip.book.book.no"/> </td>
	                  <td width="235" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.fee.concession.slip.book.book.prefix"/> </td>
	                  <td width="235" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.fee.concession.slip.book.starting.no"/></td>
	                  <td width="235" height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.fee.concession.slip.book.ending.no"/></td>
	                  <td width="55" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
	                  <td width="59" class="row-odd" align="center" ><bean:message key="knowledgepro.delete"/></td>
	                </tr>
					<logic:iterate id="feeVoucherList" name = "feeVoucherList" indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
			                  <td height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
			                  <td width="319" align="center"><bean:write name = "feeVoucherList" property="financialYear"/></td>
							  <td height="25" align="center"><bean:write name ="feeVoucherList" property="type"/></td>
							  <td height="25" align="center"><bean:write name ="feeVoucherList" property="bookNo"/></td>
							  <td height="25" align="center"><bean:write name ="feeVoucherList" property="startingPrefix"/></td>
			                  <td height="25" align="center"><bean:write name ="feeVoucherList" property="startingNo"/></td>
			                  <td height="25" align="center"><bean:write name ="feeVoucherList" property="endingNo"/></td>
			                  <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editSlip('<bean:write name="feeVoucherList" property="id"/>',
								'<bean:write name="feeVoucherList" property="type"/>',
								'<bean:write name="feeVoucherList" property="finId"/>',
								'<bean:write name="feeVoucherList" property="startingNo"/>',
								'<bean:write name="feeVoucherList" property="endingNo"/>',
								'<bean:write name="feeVoucherList" property="bookNo"/>',
								'<bean:write name="feeVoucherList" property="startingPrefix"/>')"></div></td>
			                  <td><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="deleteSlipBook('<bean:write name="feeVoucherList" property="id"/>')"></div></td>
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
	        </table>      
	        <td valign="top" background="images/Tright_3_3.gif" ></td>
	
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
<script type="text/javascript">
	document.getElementById("endPrefix").readOnly = true;
</script>