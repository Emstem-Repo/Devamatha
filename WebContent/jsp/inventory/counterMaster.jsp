<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
 <script src="http://code.jquery.com/jquery-latest.js"></script>
 <script language="JavaScript" src="calendar_us.js"></script>
	<link rel="stylesheet" href="calendar.css">
</head>
<script type="text/javascript">
	function resetValues() {
		document.getElementById("type").selectedIndex = 0;
		document.getElementById("prefix").value = "";
		document.getElementById("startNo").value = "";
		if (document.getElementById("method").value == "updateCounterMaster") {
			document.getElementById("prefix").value = document.getElementById("origPrefix").value;
			document.getElementById("startNo").value = document.getElementById("origStartNo").value;
			document.getElementById("type").value = document.getElementById("origType").value;
		}
		resetErrMsgs();
	}
	
	function editCounter(id, type, prefix, startNo,currentNo) {
		document.location.href = "CounterMaster.do?method=editCounter&id=" + id;
	}	
	function deleteCounter(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if (deleteConfirm) {
			document.location.href = "CounterMaster.do?method=deleteCounter&id="
					+ id ;
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "CounterMaster.do?method=activateCounter&id=" + id;
	}	

	var jq=$.noConflict();

	jq(document).ready(function(){
		if(jq("#type").val()=='Purchase Order Counter'){
			jq("#invCompanyLabel").show();
			jq("#invCompanyListId").show();
		}else{
		jq("#invCompanyLabel").hide();
		jq("#invCompanyListId").hide();
		}

		jq("#type").change(function(){
			if(jq("#type").val()=='Purchase Order Counter'){
				jq("#invCompanyLabel").show();
				jq("#invCompanyListId").show();
			}else{
				jq("#invCompanyLabel").hide();
				jq("#invCompanyListId").hide();
			}
		  });

		});
		
</script>	
<html:form action="/CounterMaster">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateCounterMaster"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addCounterMaster" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="counterMasterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origPrefix" styleId="origPrefix" />
	<html:hidden property="origStartNo" styleId="origStartNo" />
	<html:hidden property="origType" styleId="origType" />
	<html:hidden property="id" styleId="id" />
	<table width="99%" border="0">
	  
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.inventory"/> &gt;&gt; <bean:message key="knowledgepro.inventory.counter.master"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.inventory.counter.master"/></td>
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
	            	<tr>
	            	<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.admin.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="counterMasterForm" property="year"/>" />
									<html:select property="year" styleId="year"	styleClass="combo" >
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
					   <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.type.col"/></div></td>
	                  <td class="row-even" ><html:select
										property="type" styleClass="TextBox" styleId="type">
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value='<%=CMSConstants.INVENTORY_COUNTER_TYPE %>'><bean:message key="knowledgepro.counter.stockTransferCounter"/></html:option>
										<html:option value='<%=CMSConstants.PURCHASE_ORDER_COUNTER %>'><bean:message key="knowledgepro.counter.purchaseorderCounter"/></html:option>
										<html:option value='<%=CMSConstants.QUOTATION_COUNTER %>'><bean:message key="knowledgepro.counter.quotationCounter"/></html:option>
										
									</html:select></td>
	            	</tr>
	                <tr >
	                <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.prefix.col"/></div></td>
	                  <td class="row-even" ><html:text property="prefix" styleClass="TextBox"
									styleId="prefix" size="20" maxlength="10" name="counterMasterForm" /></td>
	                <td height="25" class="row-odd"><div id="invCompanyLabel" align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.InvCompany"/></div> </td>
	                <td class="row-even" ><div id="invCompanyListId">
                  <html:select property="companyId" styleId="companyId" styleClass="combo" >
                    <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					<logic:notEmpty property="invCompanyList" name="counterMasterForm">
	                   <html:optionsCollection name="counterMasterForm" property="invCompanyList" label="companyName" value="id"/>
					</logic:notEmpty>
                  </html:select></div>
                  </td>
					
	                  </tr>
	                  <tr>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.start.no.col"/></div></td>
	                  <td class="row-even" ><html:text property="startNo" styleClass="TextBox"
									styleId="startNo" size="20" maxlength="9" name="counterMasterForm" /></td>
						<td class="row-odd" ><div align="right">
						<c:if test="${operation == 'edit'}">
						<span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.current.no"/>
						</c:if>
						</div>
						
						</td>
	                  <td class="row-even" >
	                  <c:if test="${operation == 'edit'}">
	                  <html:text property="currentNo" styleClass="TextBox"
									styleId="currentNo" size="20" maxlength="9" name="counterMasterForm" />
							</c:if>
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
						<html:cancel styleClass="formbutton"></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()">
						</</html:button>
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
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.counter.master.type"/></td>
	                  <td width="230" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.counter.prefix.required"/> </td>
	                  <td width="235" height="25" class="row-odd" align="center" ><bean:message  key="knowledgepro.inventory.counter.master.start.no"/> </td>
	                  <td width="55" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
	                  <td width="59" class="row-odd" align="center" ><bean:message key="knowledgepro.delete"/></td>
	                </tr>
					<logic:iterate id="counterList" name = "counterList" indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
			                  <td height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
			                  <td width="319" align="center"><bean:write name = "counterList" property="type"/></td>
							  <td height="25" align="center"><bean:write name ="counterList" property="prefix"/></td>
			                  <td height="25" align="center"><bean:write name ="counterList" property="startNo"/></td>
			                  <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editCounter('<bean:write name="counterList" property="id"/>',
								'<bean:write name="counterList" property="type"/>','<bean:write name="counterList" property="prefix"/>','<bean:write name="counterList" property="startNo"/>','<bean:write name="counterList" property="currentNo"/>')"></div></td>
			                  <td><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="deleteCounter('<bean:write name="counterList" property="id"/>')"></div></td>
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
var year = document.getElementById("tempyear").value;
if(year.length != 0) {
 	document.getElementById("year").value=year;
}
</script>