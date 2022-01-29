<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type = "text/javascript">
	function resetValues() {
		resetErrMsgs();
		
		if (document.getElementById("method").value == "updateDisciplinaryType") {
			document.getElementById("name").value = document
					.getElementById("origName").value;
		}else{
			document.getElementById("name").value = "";
		}
	}
	function editDisciplinary(id, name) {
		document.getElementById("method").value = "updateDisciplinaryType";
		document.getElementById("id").value = id;
		document.getElementById("name").value = name;
		document.getElementById("submitbutton").value = "Update";
		document.getElementById("origName").value = name;
		resetErrMsgs();
	}
	function deleteDisciplinary(id, name) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "DisciplinaryType.do?method=deleteDisciplinaryType&id="
					+ id+ "&name=" + name;
		}
	}
	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "DisciplinaryType.do?method=activateDisciplinaryType&id="
				+ id;
	}

	
</script>

<html:form action="/DisciplinaryType" focus="name">
	<c:choose>
		<c:when
			test="${disciplineOperation != null && disciplineOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateDisciplinaryType" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addDisciplinaryType" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="disciplinaryTypeForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property = "id" styleId = "id"/>
	<html:hidden property="origName" styleId="origName"/>

	<table width="99%" border="0">
	  
	  <tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.disciplinary.master"/> &gt;&gt;</span></span></td>	  
	  </tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key = "knowledgepro.hostel.disciplinary.master"/> </td>
	        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
	      </tr>
	     <tr>
			<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
			<td valign="top" class="news">
			<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
			<FONT color="green"> <html:messages id="msg"
				property="messages" message="true">
				<c:out value="${msg}" escapeXml="false"></c:out>
				<br>
			</html:messages> </FONT></div>
			</td>
			<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
		</tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
	
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td height="35" colspan="4"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                <tr>
	                  <td ><img src="images/01.gif" width="5" height="5" /></td>
	                  <td width="914" background="images/02.gif"></td>
	                  <td><img src="images/03.gif" width="5" height="5" /></td>
	                </tr>
	                <tr>
	
	                  <td width="5"  background="images/left.gif"></td>
	                  <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                      <tr >
	                        <td width="50%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.disciplinary.type"/></div></td>
	                        <td width="50%" height="25" valign="top" class="row-even" >
								<html:text property="name" styleClass="TextBox"
								styleId="name" size="50" maxlength="250" name="disciplinaryTypeForm" />
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
	              </tr>
	            <tr>
	              <td width="49%" height="35" align="right"><c:choose>
					<c:when
						test="${disciplineOperation != null && disciplineOperation == 'edit'}">
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
	              <td width="2%">&nbsp;</td>
	              <td width="49%"><html:button property=""
								styleClass="formbutton" value="Reset" onclick="resetValues()"></html:button></td>
	            </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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
	                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admin.disciplinary.type.name"/></td>
	                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/> </div></td>
	                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	                  </tr>
					<logic:iterate id="disList" name = "disList" indexId="count">
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
	                    <td width="50%" align="center"><bean:write name = "disList" property="name"/></td>
	                    <td width="5%" height="25" ><div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer"
						onclick="editDisciplinary('<bean:write name="disList" property="id"/>','<bean:write name="disList" property="name"/>')"></div></td>
	                    <td width="5%"  ><div align="center"><img src="images/delete_icon.gif"
						width="16" height="16" style="cursor:pointer"
						onclick="deleteDisciplinary('<bean:write name="disList" property="id"/>','<bean:write name="disList" property="name"/>')"></div></td>

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
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	
	      </tr>
	      <tr>
	        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
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