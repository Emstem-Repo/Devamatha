<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function resetValues() {
		document.getElementById("type").selectedIndex = 0;
		document.getElementById("prefix").value = "";
		document.getElementById("startNo").value = "";
		document.getElementById("yearId").value = "";
		if (document.getElementById("method").value == "updateTCMaster") {
			document.getElementById("prefix").value = document.getElementById("origPrefix").value;
			document.getElementById("startNo").value = document.getElementById("origStartNo").value;
			document.getElementById("type").value = document.getElementById("origType").value;
			document.getElementById("yearId").value = document.getElementById("origYear").value;
		}
		resetErrMsgs();
	}
	
	function editTCMaster(id, type, prefix, startNo,year,createdBy,createdDate,currentNo,slNo,collegeName) {
		
		document.getElementById("method").value = "updateTCMaster";
		document.getElementById("id").value = id;
		document.getElementById("prefix").value = prefix;
		document.getElementById("startNo").value = startNo;
		document.getElementById("type").value = type;
		document.getElementById("yearId").value = year;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origPrefix").value = prefix;
		document.getElementById("origStartNo").value = startNo;
		document.getElementById("origType").value = type;
		document.getElementById("origYear").value = year;
		document.getElementById("origCreatedBy").value = createdBy;
		document.getElementById("origCreatedDate").value = createdDate;
		document.getElementById("currentNo").value = currentNo;
		document.getElementById("collegeName").value = collegeName;
		document.getElementById("origCollegName").value = collegeName;
		document.getElementById("slNo").value = slNo;
		resetErrMsgs();
	}	
	function deleteTCMaster(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "TCMaster.do?method=deleteTCMaster&id="
					+ id ;
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "TCMaster.do?method=activateTCMaster&id=" + id;
	}	
	
</script>	
<html:form action="/TCMaster">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateTCMaster"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addTCMaster" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="tcMasterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origPrefix" styleId="origPrefix" />
	<html:hidden property="origStartNo" styleId="origStartNo" />
	<html:hidden property="origType" styleId="origType" />
	<html:hidden property="origYear" styleId="origYear" />
	<html:hidden property="origCreatedBy" styleId="origCreatedBy" />
	<html:hidden property="origCreatedDate" styleId="origCreatedDate" />
	<html:hidden property="currentNo" styleId="currentNo" />
	<html:hidden property="slNo" styleId="slNo" />
	<html:hidden property="id" styleId="id" />
	<html:hidden property="origCollegName" styleId="origCollegName" />
	<table width="99%" border="0">
	  
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> &gt;&gt; <bean:message key="knowledgepro.admin.tc.master"/> &gt;&gt;</span></span></td>
	
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.admin.tc.master"/></td>
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
	                  <logic:equal value="Cjc" property="toCollege" name="tcMasterForm">
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.tc.for"/></div></td>
	                 
				      <td class="row-even" ><span class="star"> 
				      <html:select property="collegeName" styleClass="comboLarge" styleId="collegeName">
				      <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
				      <html:optionsCollection name="tcPrefixList" label="name" value="code" /></html:select></span></td>
					</logic:equal>
						<logic:notEqual value="Cjc" property="toCollege" name="tcMasterForm">
							<html:hidden property="collegeName" name="tcMasterForm" value="St Berchmans" styleId="collegeName"/>
						</logic:notEqual>			
	                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.type.col"/></div></td>
	                  <td class="row-even" ><html:select
										property="type" styleClass="TextBox" styleId="type">
										<html:option value=" "><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="TC"><bean:message key="knowledgepro.admin.tcmaster.tc"/></html:option>
										
									</html:select></td>
					<td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.prefix.col"/></div></td>
	                  <td class="row-even" ><html:text property="prefix" styleClass="TextBox"
									styleId="prefix" size="10" maxlength="10" name="tcMasterForm" /></td>
	                  <td class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.inventory.counter.master.start.no.col"/></div></td>
	                  <td class="row-even" ><html:text property="startNo" styleClass="TextBox"
									styleId="startNo" size="10" maxlength="9" name="tcMasterForm" /></td>
						<td width="10%" height="20" class="row-odd">
									<div align="right"><span class="Mandatory">*</span><bean:message
										key="knowledgepro.fee.academicyear.col" /></div>
									</td>
							<td width="16%" height="20" class="row-even">
							<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="tcMasterForm" property="year"/>"/>
							<html:select property="year" styleId="yearId" styleClass="combo">
										<html:option value=" ">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderYear></cms:renderYear>
									</html:select></td>				
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
	              <html:button property="" styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
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
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.admin.academicyear"/></td>
	                  <td class="row-odd" align="center"><bean:message key="knowledgepro.inventory.counter.master.type"/></td>
	                  <td width="200" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.inventory.counter.prefix.required"/> </td>
	                  <td width="200" height="25" class="row-odd" align="center" ><bean:message  key="knowledgepro.inventory.counter.master.start.no"/> </td>
	                  <td width="55" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
	                  <td width="59" class="row-odd" align="center" ><bean:message key="knowledgepro.delete"/></td>
	                </tr>
	                <logic:notEmpty name="tcMasterForm" property="list">
					<logic:iterate id="tcList" name="tcMasterForm" property="list" indexId="count">
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
			                  <td height="25"><div align="center"><c:out value="${count + 1}" /></div></td>
			                  <td width="319" align="center"><bean:write name = "tcList" property="year"/></td>
			                  <td width="319" align="center"><bean:write name = "tcList" property="type"/></td>
							  <td height="25" align="center"><bean:write name ="tcList" property="prefix"/></td>
			                  <td height="25" align="center"><bean:write name ="tcList" property="startNo"/></td>
			                  <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editTCMaster('<bean:write name="tcList" property="id"/>',
								'<bean:write name="tcList" property="type"/>',
								'<bean:write name="tcList" property="prefix"/>',
								'<bean:write name="tcList" property="startNo"/>',
								'<bean:write name="tcList" property="year"/>',
								'<bean:write name="tcList" property="createdBy"/>',
								'<bean:write name="tcList" property="createdDate"/>',
								'<bean:write name="tcList" property="currentNo"/>',
								'<bean:write name="tcList" property="slNo"/>',
								'<bean:write name="tcList" property="collegeName"/>')"></div></td>
			                  <td><div align="center"><img src="images/delete_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="deleteTCMaster('<bean:write name="tcList" property="id"/>')"></div></td>
			                </tr>
					</logic:iterate>
					</logic:notEmpty>
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
var year1 = document.getElementById("tempyear").value;
if (year1.length != 0) {
	document.getElementById("yearId").value = year1;
}
</script>