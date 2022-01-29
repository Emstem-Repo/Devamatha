<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<SCRIPT type="text/javascript">
function editProgramType(id,name,count) {
	document.getElementById("studentTypeId").value = id;
	document.getElementById("typeName").value=name;
	document.getElementById("typeDesc").value=document.getElementById("desc["+count+"]").value;
	document.getElementById("hiddendesc").value=document.getElementById("desc["+count+"]").value;
	document.getElementById("method").value="editStudentType";
	document.getElementById("editedName").value=name;
	document.getElementById("button").value="Update";
	resetErrMsgs();
}
function deleteProgramType(id,name,count) {
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	var desc=document.getElementById("desc["+count+"]").value;
	if (deleteConfirm == true) {
		document.location.href = "StudentCategory.do?method=deleteStudentType&studentTypeId="
				+ id+"&typeName="+name+"&typeDesc="+desc;
	}
}
function imposeMaxLength(Object)
{
	
return (Object.value.length <= 100);
}
function checkNumber(field){
	if(isNaN(field.value)){
		field.value = "";
	}
}
function resetfields()
{
	document.getElementById("typeName").value="";
	document.getElementById("typeDesc").value="";
	if(document.getElementById("method").value=="editStudentType")
	{
		document.getElementById("typeName").value=document.getElementById("editedName").value;
		document.getElementById("typeDesc").value=document.getElementById("hiddendesc").value;
	}
	resetErrMsgs();
}

function reActivate(){
	var name = document.getElementById("typeName").value;
	document.location.href = "StudentCategory.do?method=reActivateStudentType&&typeName="+name;
}
	
</SCRIPT>
<html:form action="/StudentCategory" focus="typeName">
	<html:hidden property="formName" value="studentTypeForm" styleId="formName"/>
	<html:hidden property="pageType" value="1" styleId="pageType"/>
	<html:hidden property="studentTypeId" styleId="studentTypeId" />
	<html:hidden property="editedName" value="" styleId="editedName"/>
	<html:hidden property="hiddendesc" value="" styleId="hiddendesc"/>
	<c:choose>
	<c:when test="${Update!=null}">
	<html:hidden property="method" styleId="method" value="editStudentType" />
	</c:when>
	<c:otherwise>
	<html:hidden property="method" styleId="method" value="addStudentType" />
	</c:otherwise>
	</c:choose>

<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.admin" />  
    <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admin.studentcategory" />  &gt;&gt;</span></span></td>
  </tr>
	
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"><bean:message key="knowledgepro.admin.studentcategory" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
                    <td width="30%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.studentcategory" />: </div></td>
                     <td width="30%" height="25" class="row-even">
					<html:text property="typeName" styleId="typeName" styleClass="TextBox" size="16" maxlength="43" /></td>
					</tr><br>
					<tr>
					<td width="30%" height="25" class="row-odd">
						<div align="right"><bean:message key="knowledgepro.admin.student.category.Desc.disp" />:</div></td>
					
					<td width="30%" height="25" class="row-even"><span class="star"> 
					<html:textarea property="typeDesc" styleId="typeDesc" styleClass="TextBox" onblur="return checkNumber(field)" onkeypress="return imposeMaxLength(this)"/></td>
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
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="48%" height="35"><div align="right">
              <c:choose>
					<c:when test="${Update!=null}">
						<html:submit styleClass="formbutton" styleId="button"><bean:message key="knowledgepro.update"/></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit styleClass="formbutton" styleId="button">Submit</html:submit>
					</c:otherwise>
				</c:choose>
              </div></td>
              <td width="2%"></td>
              <td width="50%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetfields()"></html:button></td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="20" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td ><img src="images/01.gif" width="5" height="5" /></td>
              <td width="914" background="images/02.gif"></td>
              <td><img src="images/03.gif" width="5" height="5" /></td>
            </tr>
            <tr>
              <td width="5"  background="images/left.gif"></td>
              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
					<td height="25" class="row-odd">
					<div align="center"><bean:message key="knowledgepro.slno" /></div></td>
					<td height="25" class="row-odd"><bean:message key="knowledgepro.admin.studentcategory" /></td>
					<td class="row-odd"><div align="center"><bean:message key="knowledgepro.admin.student.category.Desc.disp" /></div></td>
					<td class="row-odd"><div align="center"><bean:message key="knowledgepro.edit" /></div></td>
					<td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete" /></div></td>
				</tr>

                  <c:set var="temp" value="0" />
								<logic:iterate name="studentTypeForm" property="studentTypeList"
									id="stList" indexId="count">
									<c:choose>
										<c:when test="${temp == 0}">
											<tr>
												<td width="9%" height="25" class="row-even">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td width="30%" height="25" class="row-even"><bean:write
													name="stList" property="typeName" /></td>
												<td width="40%" height="25" class="row-even"><bean:write
													name="stList" property="typeDesc" /><input type="hidden"
													name="desc[<c:out value="${count}"/>]"
													id="desc[<c:out value="${count}"/>]"
													value="<bean:write
													name="stList" property="typeDesc" />" /></td>
												<td width="10%" height="25" class="row-even">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editProgramType('<bean:write name="stList" property="studentTypeId" />','<bean:write
													name="stList" property="typeName" />','<c:out value="${count}"/>')" /></div>
												</td>
												<td width="11%" height="25" class="row-even">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteProgramType('<bean:write name="stList" property="studentTypeId" />','<bean:write
													name="stList" property="typeName" />','<c:out value="${count}"/>')" /></div>
												</td>
											</tr>
											<c:set var="temp" value="1" />
										</c:when>
										<c:otherwise>
											<tr>
												<td height="25" class="row-white">
												<div align="center"><c:out value="${count+1}" /></div>
												</td>
												<td height="25" class="row-white"><bean:write
													name="stList" property="typeName" /></td>
												<td height="25" class="row-white"><bean:write
													name="stList" property="typeDesc" /> <input type="hidden"
													name="desc[<c:out value="${count}"/>]"
													id="desc[<c:out value="${count}"/>]"
													value="<bean:write
													name="stList" property="typeDesc" />" /></td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/edit_icon.gif"
													width="16" height="18"
													onclick="editProgramType('<bean:write name="stList" property="studentTypeId" />','<bean:write
													name="stList" property="typeName" />','<c:out value="${count}"/>')" /></div>
												</td>
												<td height="25" class="row-white">
												<div align="center"><img src="images/delete_icon.gif"
													width="16" height="16"
													onclick="deleteProgramType('<bean:write name="stList" property="studentTypeId" />','<bean:write
													name="stList" property="typeName" />','<c:out value="${count}"/>')" /></div>
												</td>
											</tr>
											<c:set var="temp" value="0" />
										</c:otherwise>
									</c:choose>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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