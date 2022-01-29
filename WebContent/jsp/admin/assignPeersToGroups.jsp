<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<head>
<script type="text/javascript">
function getFaculty(department) {
	getFacultyByDepartment("optionMap", department, "employeeId", updateFacultyByDepartment);
}
function updateFacultyByDepartment(req) {
	updateOptionsFromMap(req,"employeeId","- Select -");
	
} 
function resetErrorMsgs() {
	
	document.getElementById("departmentId").value = "";
	var destination = document.getElementById("employeeId");
	for (x1 = destination.options.length - 1; x1 >= 0; x1--) {
		destination.options[x1] = null;
	}
	document.getElementById("groupId").value = "";
	resetErrMsgs();
}
function resetErrorMsgs1() {
	
		document.getElementById("departmentId").value = document.getElementById("deptId").value;
		
		document.getElementById("groupId").value= document.getElementById("grpId").value;
	resetErrMsgs();
}
function editDetails(id,deptId,groupId){
	document.location.href = "assignPeersToGroups.do?method=editAssignPeersGroups&id="+id+"&departmentId="+deptId+ "&groupId="+groupId;
}
function deleteDetails(id,deptId,groupId){
	deleteConfirm = confirm("Are you sure you want to delete this entry?")
	if (deleteConfirm == true) {
		document.location.href = "assignPeersToGroups.do?method=deleteAssignPeersToGroups&id="+id+"&departmentId="+deptId+ "&groupId="+groupId;;
	}
}

</script>
</head>
<html:form action="/assignPeersToGroups" method="POST">
<html:hidden property="formName" value="assignPeersGroupsForm" />
<html:hidden property="pageType" value="1" />
<html:hidden property="id" styleId="id" />
<c:choose>
		<c:when test=""></c:when>
	</c:choose>
<c:choose>
		<c:when test="${assignPeers!=null && assignPeers == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateAssignPeersToGroup" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addFacultyByDepartmentToGroups" />
		</c:otherwise>
	</c:choose>
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs">Faculty Evaluation <span class="Bredcrumbs">&gt;&gt; Assign Peers To Group</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Assign Peers To Group </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div>
					<div><FONT color="red" size="1">
						<bean:write name="assignPeersGroupsForm" property="displayErrorMsg"/><br>
						</FONT></div>
					</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            <table width="100%" cellspacing="1" cellpadding="2">
              <tr >
              <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Department:</div></td>       
              <td  align="left" class="row-even" colspan="4">
               <input type="hidden" id="deptId" name="deptId" value="<bean:write name="assignPeersGroupsForm" property="departmentId"/>" />
              <html:select property="departmentId" styleId="departmentId"  styleClass="comboBig" onchange="getFaculty(this.value)" style="width: 350px;" >
			 <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
			<html:optionsCollection name="assignPeersGroupsForm" property="departmentTo" label="name" value="id"  />
			  </html:select></td>
             </tr>
             <tr>
            <td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Faculty:</div></td>  
            <td align="left" width="30%" class="row-even">
            <input type="hidden" id="empId" name="empId" value="<bean:write name="assignPeersGroupsForm" property="empIds"/>" />
            <html:select property="empIds" size="10" style="width:250px" multiple="multiple" styleId="employeeId" styleClass="body" > 
            <c:if test="${optionMap != null}">
            <html:optionsCollection name="optionMap" label="value" value="key" styleClass="comboBig" />
            </c:if>
             </html:select></td>
             
			  <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;Group:</div></td>       
              <td align="left" width="25%" class="row-even" >
              <input type="hidden" id="grpId" name="grpId" value="<bean:write name="assignPeersGroupsForm" property="groupId"/>" />
              <html:select property="groupId" styleId="groupId" styleClass="comboLarge">
              <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
				<html:optionsCollection name="assignPeersGroupsForm" property="groupsTOs" label="name" value="id" />
			  </html:select></td>
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
             <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
            <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
           <tr>
			<td>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
	            <td width="45%" height="35"><div align="right">
				<c:choose>
					<c:when test="${assignPeers!=null && assignPeers == 'edit'}">
						<html:submit property="" styleClass="formbutton" value="Update" styleId="submitbutton"></html:submit>
					</c:when>
					<c:otherwise>
						<html:submit property="" styleClass="formbutton" value="Submit" styleId="submitbutton"></html:submit>
					</c:otherwise>
				</c:choose></div>
				</td>
				<td width="2%"></td>
					<td width="53%"><c:choose>
						<c:when test="${assignPeers!=null && assignPeers == 'edit'}">
							<html:button property="" value="Reset" styleClass="formbutton" onclick="resetErrorMsgs1()"></html:button>
						</c:when>
						<c:otherwise>
							<html:button property="" styleClass="formbutton" value="Reset"
								onclick="resetErrorMsgs()"></html:button>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
				</table>
			</td>
	          </tr>
         <logic:notEmpty name="assignPeersGroupsForm" property="peersGroupsTos">
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr >
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="30%" class="row-odd" align="center" >Department</td>
                    <td height="30%" class="row-odd" align="center" >Group</td>
                    <td height="25" class="row-odd" align="center" ><bean:message key="knowledgepro.edit"/></td>
                    <td class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                 </tr>
                <c:set var="temp" value="0"/>
                <logic:iterate id="to" name="assignPeersGroupsForm" property="peersGroupsTos" indexId="count">
               
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="5%" height="25" class="row-even" align="center"><bean:write name="to" property="departmentName"/></td>
                   		<td width="20%" height="25" class="row-even" align="center"><bean:write name="to" property="groupName"/></td>
                   		<td width="10%" height="25" class="row-even" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="departmentId"/>','<bean:write name="to" property="groupId"/>')"> </div></td>
                   		<td width="10%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="departmentId"/>','<bean:write name="to" property="groupId"/>')"></div></td>
                   	</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="5%" height="25" class="row-white" align="center"><bean:write name="to" property="departmentName"/></td>
               			<td width="20%" height="25" class="row-white" align="center"><bean:write name="to" property="groupName"/></td>
               			<td width="10%" height="25" class="row-white" align="center"> <div align="center"><img src="images/edit_icon.gif"
						width="16" height="18" style="cursor:pointer" onclick="editDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="departmentId"/>','<bean:write name="to" property="groupId"/>')"> </div></td>
                   		<td width="10%" height="25" class="row-white" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteDetails('<bean:write name="to" property="id"/>','<bean:write name="to" property="departmentId"/>','<bean:write name="to" property="groupId"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
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
          </tr>
          </logic:notEmpty>
          
          
          </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
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
<script type="text/javascript">
	var deptId = document.getElementById("deptId").value;
	if (deptId != null && deptId.length != 0) {
		document.getElementById("departmentId").value = deptId;
	}
	var grpId = document.getElementById("grpId").value;
	if (grpId != null && grpId.length != 0) {
		document.getElementById("groupId").value = grpId;
	}
	//var empId = document.getElementById("empId").value;
	//if (empId != null && empId.length != 0) {
		//document.getElementById("empIds").value = empId;
	//}
</script>