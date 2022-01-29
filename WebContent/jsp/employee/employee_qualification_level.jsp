<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	
	function resetValue() {
		document.getElementById("qualificationId").value = "";
		document.getElementById("fixed").value = "";
		document.getElementById("displayOrder").value = "";
		if (document.getElementById("method").value == "updateQualificationLevel") {
			document.getElementById("qualificationId").value = document.getElementById("origName").value;
			document.getElementById("displayOrder").value = document.getElementById("origDisplayOrder").value;
			document.getElementById("fixed").value = document.getElementById("origFixedDisplay").value;
		}
		resetErrMsgs();
	}

	function resetValues() {
		document.location.href = "qualification.do?method=initQualificationLevel";
	}
	
	 function editQualificationLevel(id){
		document.location.href = "qualification.do?method=editQualificationLevel&id=" + id;
		}
	function reActivate() {
		
		//var id = document.getElementById("duplId").value;
		document.location.href = "qualification.do?method=activateQualificationLevel";
	}		
	function deleteQualificationLevel(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "qualification.do?method=deleteQualificationLevel&id="
					+ id ;
		}
	}
	
	
</script>
<html:form action="/qualification">
	<c:choose>
		<c:when test="${QualificationLevelEntryOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value = "updateQualificationLevel"/>
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addQualificationLevel" />
		</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${QualificationLevelEntryOperation == 'add'}">
	<html:hidden property="method" styleId="method" value = "activateQualificationLevel"/>
	</c:when>
	</c:choose>
	<html:hidden property="formName" value="qualificationLevelForm" />
	<html:hidden property="duplId" name = "qualificationLevelForm" value="duplId" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origName" styleId="origName"/>
	<html:hidden property="origFixedDisplay" styleId="origFixedDisplay"/>
	<html:hidden property="origDisplayOrder" styleId="origDisplayOrder"/>
	<html:hidden property="orgPhdQualification" styleId="orgPhdQualification"/>
	<html:hidden property="id" styleId="id"/>
	  
	<table width="100%" border="0">
	  <tr>
	    <td><span class="heading"><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory"/> &gt;&gt; <bean:message key="knowledgepro.employee.qualification.level"/> &gt;&gt;</span></span></td>
	  </tr>
	  <tr>
	    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.employee.qualification.level"/></strong></td>
	
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
	                <td width="26%" class="row-even"><html:text property="name" styleId="qualificationId" name="qualificationLevelForm" maxlength="50"></html:text></td>
	                <td width="17%" class="row-odd"><div align="right"><span class="Mandatory">*</span>Display Order:</div></td>
	                <td width="33%" class="row-even"><span class="star"><html:text property="displayOrder" styleId="displayOrder" maxlength="50"  size="8" onkeypress="return isNumberKey(event)"/></span></td>
	              </tr>
	              <tr>
	                <td width="24%" height="25" class="row-odd"><div align="right" ><span class="Mandatory"></span> Fixed Display:</div></td>
	                <td width="26%" class="row-even">
					<html:radio property="fixedDisplay" name="qualificationLevelForm" value="true" styleId="fixed">Yes</html:radio>
					<html:radio property="fixedDisplay"  name="qualificationLevelForm" value="false" styleId="fixed">No</html:radio>
					</td>
					 <td width="17%" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.phd.qualification.display"/>:</div></td>
	                  <td width="33%" class="row-even">
	                  <html:radio property="phdQualification" name="qualificationLevelForm" value="true" styleId="phdfixed">Yes</html:radio>
					  <html:radio property="phdQualification"  name="qualificationLevelForm" value="false" styleId="phdfixed">No</html:radio>
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
						test="${QualificationLevelEntryOperation != null && QualificationLevelEntryOperation == 'edit'}">
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
	                <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.employee.qualification.level"/></td>
	                <td class="row-odd" align="center">Display Order</td>
	                <td class="row-odd" align="center">Fixed Display</td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.phd.qualification.display"/></div></td>
					<td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
	                <td class="row-odd" align="center"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	              </tr>
				<logic:notEmpty name="QualificationLevelList">
					<logic:iterate id="QualificationLevelList" name ="QualificationLevelList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>				
			                <td width="6%" height="25"><div align="center"><c:out value="${count + 1}"/></div></td>
			                <td width="20%" height="25" align="center"><bean:write name = "QualificationLevelList" property="name"/></td>
			                <td width="20%" align="center"><bean:write name = "QualificationLevelList" property="displayOrder"/></td>
			                 <td width="20%" align="center"><bean:write name = "QualificationLevelList" property="fixedDisplay"/></td>
			                 <td width="20%" align="center"><bean:write name = "QualificationLevelList" property="phdQualification"/></td>
 		                    <td width="7%" height="25"><div align="center"><img src="images/edit_icon.gif" alt="CMS" width="16" height="16" style="cursor:pointer"
								onclick="editQualificationLevel('<bean:write name="QualificationLevelList" property="id"/>',
								'<bean:write name="QualificationLevelList" property="name"/>','<bean:write name="QualificationLevelList" property="fixedDisplay"/>','<bean:write name="QualificationLevelList" property="displayOrder"/>')"></div></td>
			                <td width="7%" height="25"><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteQualificationLevel('<bean:write name="QualificationLevelList" property="id"/>')"></div></td>
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