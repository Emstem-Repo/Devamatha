<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script type="text/javascript">
	function getFloors(hostelId) {
		getFloorsByHostel("floorMap", hostelId, "floorNo", updateFloors);
	}
	
	function updateFloors(req) {
		updateOptionsFromMap(req, "floorNo", "- Select -");
	}
	function editHostelGroup(groupId){
		document.location.href = "HostelGroup.do?method=editHostelGroup&id="
			+ groupId;
	}
	
	function deleteHostelGroup(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "HostelGroup.do?method=deleteHostelGroup&id="
					+ id ;
		}
	}

	function reActivate() {
		var id = document.getElementById("id").value;
		document.location.href = "HostelGroup.do?method=activateHostelGroup&id="
				+ id;
	}
	function resetMessages() {
		document.getElementById("groupName").value = "";
		document.getElementById("hostelId").selectedIndex = 0;
		document.getElementById("floorNo").selectedIndex = 0;
		resetFieldAndErrMsgs();
	}	
	function cancelAction(){
		document.location.href = "HostelGroup.do?method=initHostelGroup";
		}
		
</script>

<html:form action="/HostelGroup" method="post" >
	<html:hidden property="formName" value="hostelGroupForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>

	<c:choose>
		<c:when test="${operation == 'edit'}">
			<html:hidden property="method" styleId="method" value="updateHostelGroup" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="saveHostelGroup" />
		</c:otherwise>
	</c:choose>

	<table width="99%" border="0">
	  
	<tr>
		<td><span class="Bredcrumbs"> <bean:message key="knowledgepro.hostel"/><span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.group.entry"/> &gt;&gt;</span></span></td>	  

	</tr>
	  <tr>
	    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
	        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.hostel.group.entry"/></td>
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
	
	                <td width="19%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.name.col"/> </div></td>
	                <td width="21%" height="25" class="row-even" >
	                <html:select property="hostelId" styleClass="comboLarge" styleId="hostelId" 
	                	onchange="getFloors(this.value)">
							<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<html:optionsCollection name="hostelList"
									label="name" value="id" />
							</html:select> </td>
	                <td width="21%" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.room.floor.no"/> </div></td>
	
	                <td width="21%" class="row-even" >
						<html:select property="floorNo" styleClass="combo"
								styleId="floorNo">
								<html:option value="">
									<bean:message key="knowledgepro.admin.select" />
								</html:option>
								<c:choose>
									<c:when test="${operation == 'edit'}">
									<c:if test="${floorMap != null}">
										<html:optionsCollection name="floorMap" label="value"
											value="key" />
									</c:if>
									</c:when>
									<c:otherwise>
											<c:if test="${floorMap != null}">
												<html:optionsCollection name="floorMap" label="value"
													value="key" />
										</c:if>
									</c:otherwise>
								</c:choose>
							</html:select>

 </td>
	                <td width="21%" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="hostel.attendance.group.name"/> </div></td>
	                <td width="21%" class="row-even" >
						<html:text property="groupName" styleClass="TextBox" styleId="groupName" size="15" maxlength="50" name="hostelGroupForm"/></td>
	
	              </tr>
	            </table></td>
	            <td width="5" background="images/right.gif"></td>
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
	        <td height="35" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	
	      <tr>
	        <td valign="top" background="images/Tright_03_03.gif"></td>
	        <td height="25" class="heading"><bean:message key="knowledgepro.hostel.group.student.selection.list"/> </td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	      <tr>
	        <td height="66" valign="top" background="images/Tright_03_03.gif"></td>
	        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5" /></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5" /></td>
	            </tr>
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
					<logic:notEmpty name="hostelGroupForm" property = "studList">
		                <tr>
						<nested:iterate name="hostelGroupForm" id = "student" property="studList" indexId="count">

						<c:choose>
							<c:when test="${operation == 'edit'}">			
								<td width="6%" height="25" class="row-odd">
								<div align="right">	
								<input type="hidden" name="studList[<c:out value='${count}'/>].dummySelected" id="studenthidden_<c:out value='${count}'/>"
											value="<nested:write name='student' property='dummySelected'/>" />				
								<input type="checkbox" name="studList[<c:out value='${count}'/>].selected" id="<c:out value='${count}'/>" />
								</div>												
								<script type="text/javascript">
								var student = document.getElementById("studenthidden_<c:out value='${count}'/>").value;
								if(student == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
								}		
								</script>														
								</td>
								<td width="50%"height="25" class="row-even"><nested:write property="name"/> </td>
							</c:when>
							<c:otherwise>		
			                  <td width="6%" height="25" class="row-odd" >
								<input type="hidden" name="studList[<c:out value='${count}'/>].dummySelected" id="studenthidden_<c:out value='${count}'/>"
														value="<nested:write name='student' property='dummySelected'/>" />	
								<input type="checkbox" name="studList[<c:out value='${count}'/>].selected" id="<c:out value='${count}'/>" >
								<script type="text/javascript">
									var student1 = document.getElementById("studenthidden_<c:out value='${count}'/>").value;
									if(student1 == "true") {
										document.getElementById("<c:out value='${count}'/>").checked = true;
									}		
								</script>
								</td>
			                  <td width="50%" height="25" class="row-even" ><bean:write name = "student"  property="name"/></td>

						</c:otherwise>
						</c:choose>
						<c:if test="${(count + 1) % 2 == 0}">
							<tr>
						</c:if>
						</nested:iterate>
		                  </tr>
					</logic:notEmpty>
	              </table>
				</td>
	              <td width="5" height="55"  background="images/right.gif"></td>
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
	        <td height="35" align="center" class="heading" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="49%" height="35" align="right"><div>
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
	            <td width="4%" align="center">&nbsp;</td>
	            <td width="49%" align="left"><div>
				<c:choose>
					<c:when test="${operation == 'edit'}">
		                <html:cancel value="Reset" styleClass="formbutton" ></html:cancel>
					</c:when>
					<c:otherwise>
		                <html:button property="" value="Reset" styleClass="formbutton" onclick="resetMessages()"></html:button>
					</c:otherwise>
				</c:choose>
				<html:button property="" value="Cancel" styleClass="formbutton" onclick="cancelAction()"></html:button>
	            </div></td>
	          </tr>
	        </table></td>
	        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
	      </tr>
	
	      <tr>
	        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	        <td class="heading" ><table width="100%"  border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td width="45%" height="35" align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	            <tr>
	              <td ><img src="images/01.gif" width="5" height="5"></td>
	              <td width="914" background="images/02.gif"></td>
	              <td><img src="images/03.gif" width="5" height="5"></td>
	
	            </tr>
	            <tr>
	              <td width="5"  background="images/left.gif"></td>
	              <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
	                  <tr >
	                    <td height="25" class="row-odd" width="5%"><div align="center"><bean:message key="knowledgepro.slno"/> </div></td>
	                    <td class="row-odd" width="25%"><bean:message key="knowledgepro.hostel.group.group.name"/> </td>
	                    <td class="row-odd" width="30%"><bean:message key="knowledgepro.hostel.hostel.entry.name"/> </td>
	                    <td class="row-odd" width="30%"><bean:message key="knowledgepro.fee.studentname"/></td>
	
	                    <td class="row-odd" width="5%"><bean:message key="knowledgepro.edit"/></td>
	                    <td class="row-odd" width="5%"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
	                    </tr>
					<logic:notEmpty name="hlGroupList">
					<nested:iterate name="hlGroupList" id="hlGroup" indexId="count">
	                  <tr>
						<c:choose>
							<c:when test="${count%2 == 0}">
								<tr class="row-even">
							</c:when>
							<c:otherwise>
								<tr class="row-white">
							</c:otherwise>
						</c:choose>
	                    <td width="6%" height="25" ><div align="center"><c:out value='${count +  1}'/></div></td>
  						<td ><bean:write name = "hlGroup" property="name"/></td>
	                    <td><bean:write name = "hlGroup" property="hostelTO.name"/></td>
	                    <td  height="25">
							<nested:iterate name="hlGroup" id="student" property="hlGroupStudentList">
								<nested:write name="student" property="name" />
							</nested:iterate> </td>
	
	                    <td align="center"><img src="images/edit_icon.gif" width="16" height="18" style="cursor:pointer"
						onclick="editHostelGroup('<nested:write name="hlGroup" property="id"/>')"></td>
	                    <td><div align="center"><img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer"
							onclick="deleteHostelGroup('<nested:write name="hlGroup" property="id"/>')"></div></td>
	                    </tr>
					</nested:iterate>
					</logic:notEmpty>
	              </table></td>
	              <td  background="images/right.gif" width="5" height="54"></td>
	            </tr>
	            <tr>
	              <td height="5"><img src="images/04.gif" width="5" height="5"></td>
	              <td background="images/05.gif"></td>
	
	              <td><img src="images/06.gif" ></td>
	            </tr>
	        </table></td>
	          </tr>
	          <tr>
	            <td align="center">&nbsp;</td>
	          </tr>
	        </table></td>
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