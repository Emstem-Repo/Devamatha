<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function resetValues() {
		document.getElementById("qualificationId").selectedIndex = 0;
		document.getElementById("education").value = "";
		if (document.getElementById("method").value == "updateEducationMaster") {
			document.getElementById("education").value = document.getElementById("origEdu").value;
			document.getElementById("qualificationId").value = document.getElementById("origQualificationId").value;
		}
		resetErrMsgs();
	}
		function editEducation(id, qId, education) {
		document.getElementById("method").value = "updateEducationMaster";
		document.getElementById("id").value = id;
		document.getElementById("education").value = education;
		document.getElementById("qualificationId").value = qId;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origEdu").value = education;
		document.getElementById("origQualificationId").value = qId;
		resetErrMsgs();
	}	
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "EducationMaster.do?method=activateEducation&id=" + id;
	}		
	function deleteEducation(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "EducationMaster.do?method=deleteEducation&id="
					+ id ;
		}
	}
	
</script>
<html:form action="/EducationMaster">
	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateEducationMaster"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addEducationMaster" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="educationMasterForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origEdu" styleId="origEdu"/>
	<html:hidden property="origQualificationId" styleId="origQualificationId"/>
	<html:hidden property="id" styleId="id"/>
	  
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/> &gt;&gt; <bean:message key="knowledgepro.employee.education.master"/> &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.employee.education.master"/></strong></td>
	
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
	                <td width="24%" height="25" class="row-odd"><div align="right" ><span class="Mandatory">*</span> Qualification Level:</div></td>
	                <td width="26%" class="row-even">
					<html:select
						property="qualificationId" styleClass="combo" styleId="qualificationId" >
						<html:option value="">
							<bean:message key="knowledgepro.admin.select" />
						</html:option>
						<html:optionsCollection name="qualificationList" label="name"
							value="id" />
					</html:select>

					</td>
	                <td width="17%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="employee.info.education.title"/></div></td>
	                <td width="33%" class="row-even"><span class="star">
	                  <html:text property="education" styleId="education" maxlength="50" styleClass="TextBox" size="16"/>
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
	
	                <td height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
	                <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.employee.education.qualification.level"/></td>
	                <td class="row-odd" align="center"><bean:message key="knowledgepro.employee.education.master.education"/></td>
					<td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
				<logic:notEmpty name="educationList">
					<logic:iterate id="educationList" name ="educationList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="5%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="31%" height="25" align="center"><bean:write name = "educationList" property="empQualificationLevel.name"/></td>
			                <td width="45%" align="center"><bean:write name = "educationList" property="name"/></td>
 		                    <td><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editEducation('<bean:write name="educationList" property="id"/>',
								'<bean:write name="educationList" property="empQualificationLevel.id"/>','<bean:write name="educationList" property="name"/>')"></div></td>
			                <td width="12%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteEducation('<bean:write name="educationList" property="id"/>')"></div></td>
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