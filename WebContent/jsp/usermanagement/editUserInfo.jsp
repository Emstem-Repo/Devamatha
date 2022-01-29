<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function searchUsers() {
	document.getElementById("method").value = "searchUsers";
	document.userInfoForm.submit();
}
function editUsers(id, emplId){
	document.location.href = "UserInfo.do?method=editUsers&id=" + id + "&emplId=" +emplId;
		
}


function deleteUsers(id){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "UserInfo.do?method=deleteUserInfo&id=" + id;
	}
}

</script>
<html:form action="/UserInfo" method="post">
	<html:hidden property="method" styleId="method" value="" />	
	<html:hidden property="formName" value="userInfoForm" />
	<table width="99%" border="0">
	<tr>
		<td><span class="Bredcrumbs"><bean:message
			key="knowledgepro.usermanagement.userinfo.usermanagement" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.userinfo" />
			&gt;&gt;</span></span></td>
	</tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Edit User Info </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="news">
		<div align="right"><span class='MandatoryMark'> <bean:message key="knowledgepro.mandatoryfields" /></span></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
                  <td width="25%" height="25" class="row-odd"><div align="right"> Name:</div></td>
					<td width="30%" height="25" class="row-even" align="left">
					<html:text property="firstsearchName" size="4" maxlength="30" styleId="firstsearchName"></html:text>
					<html:text property="middlesearchName" size="7" maxlength="20" styleId="middlesearchName"></html:text>
					<html:text property="lastsearchName" size="4" maxlength="30" styleId="lastsearchName"></html:text>
					</td>
                  <td class="row-odd" align="right">Date of Birth:</td>
					<td height="25" class="row-even" align="left">
					<html:text property="searchDob" styleId="searchDob" size="11" maxlength="11"></html:text>
					<script language="JavaScript">
						new tcal( {
							// form name
							'formname' :'userInfoForm',
							// input name
							'controlname' :'searchDob'
						});
					</script>
					</td>
                  </tr>
                <tr >
					<td width="25%" height="25" class="row-odd">
					<div align="right"><bean:message
						key="knowledgepro.usermanagement.userinfo.department" />:</div>
					</td>
					<td width="25%" height="25" class="row-even" align="left">&nbsp;<html:select
						property="searchDepartment" styleClass="comboExtraLarge">
						<html:option value="">
							<bean:message key="knowledgepro.admin.select" />
						</html:option>
						<html:optionsCollection property="department"
							label="value" value="key" />
					</html:select></td>
					<td width="25%" height="25" class="row-odd">&nbsp;</td>
					<td width="25%" height="25" class="row-even">&nbsp;</td>
                  
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
            <td height="35" align="center"><html:button property="" styleClass="formbutton" value="Search" onclick="searchUsers()"></html:button></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
			<logic:notEmpty name ="userinfoList" >
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>

							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td height="25" class="row-odd" align="center">Name</td>

									<td class="row-odd" align="center">User Name</td>
									<td class="row-odd" align="center">D.O.B</td>
									<td class="row-odd" align="center">Department</td>
									<td class="row-odd" align="center">Designation</td>
									<td class="row-odd" align="center">Role</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd" align="center">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
									<nested:iterate id="userinfoList"  property="userInfoTOList"	indexId="count" >
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="8%" height="25" align="center">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td width="34%" height="25" align="center"><nested:write name="userinfoList" property="employeeTO.name" /></td>
	
										<td width="25%" align="center"><nested:write name="userinfoList" property="userName" /></td>
										<td width="13%" align="center"> 
										
										<nested:notEmpty name="userinfoList" property="employeeTO.dob">
											<nested:write name="userinfoList" property="employeeTO.dob" />
										</nested:notEmpty></td>
										<td width="25%" align="center">
										<nested:notEmpty name="userinfoList" property="departmentName">
										  <nested:write name="userinfoList"  property="departmentName" />
										</nested:notEmpty>
										</td>
										<td width="25%" align="center">
										<nested:notEmpty name="userinfoList" property="designationName">
											<nested:write name="userinfoList" property="designationName" />
										</nested:notEmpty></td>
										<td width="25%" align="center">
										  <nested:notEmpty name="userinfoList" property="rolesName">
											<nested:write name="userinfoList" property="rolesName" />
										  </nested:notEmpty></td>
										<td width="11%" height="25" align="center">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor:pointer"
											onclick="editUsers('<nested:write name="userinfoList" property="id"/>', '<nested:write name="userinfoList" property="employeeTO.id"/>')">
										</div>
										</td>
										<td width="10%" height="25"  align="center">
										<div align="center"><img src="images/delete_icon.gif"
											width="16" height="16" style="cursor:pointer"
											onclick="deleteUsers('<nested:write name="userinfoList" property="id"/>')">
										</div>
										</td>
										</tr>
									</nested:iterate>
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>

							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				</logic:notEmpty>


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