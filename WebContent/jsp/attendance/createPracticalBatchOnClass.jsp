<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
	function searchStudent() {
		document.getElementById("method").value = "searchStudent";
		document.createPracticalBatchForm.submit();
	}
	function savePracticalBatch(){
		document.getElementById("method").value = "savePracticalBatch";
		document.createPracticalBatchForm.submit();
	}
	function resetPracticalBatch(){
		document.getElementById("reset").value = "reset";
		document.getElementById("method").value = "getPracticalBatchDetails";
		document.createPracticalBatchForm.submit();
	}
	function cancelPracticalBatch(){
		document.getElementById("method").value = "initCreatePracticalBatch";
		document.createPracticalBatchForm.submit();
	}

	function editPracticalBatch(id){
		document.getElementById("batchId").value = id;
		document.getElementById("method").value = "editPracticalBatch";
		document.createPracticalBatchForm.submit();
	}
	function deletePracticalBatch(id){
		deleteConfirm = confirm("Are you sure want to delete this entry?")
		if (deleteConfirm) {
			document.location.href = "CreatePracticalBatch.do?method=deletePracticalBatch&batchId="
					+ id;
		}
	}
	function reActivate() {
		document.getElementById("method").value = "reActivatePracticalBatch";
		document.createPracticalBatchForm.submit();
	}
	function updatePracticalBatch()
	{
		document.getElementById("method").value = "updatePracticalBatch";
		document.createPracticalBatchForm.submit();
	}			
</script>
<html:form action="/CreatePracticalBatch" method="post">
	<html:hidden property="method" styleId="method" />
	<html:hidden property="formName" value="createPracticalBatchForm" />
	<html:hidden property="reset" styleId="reset" />
	<html:hidden property="batchId" styleId="batchId"/>
	<table width="99%" border="0">
  <tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.attendance" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.attendance.createpracticalbatch" />&gt;&gt;</span></span></td>
		</tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message
						key="knowledgepro.attendance.createpracticalbatch" /> </td>
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
				<c:choose>
				<c:when test="${operation == 'search' || operation == 'edit' || operation == 'save' && createPracticalBatchForm.message != null}">
				<logic:notEmpty name="createPracticalBatchForm" property="existingStudentList">				
				<nested:iterate name="createPracticalBatchForm" property="existingStudentList" id="duplicate">
				 <FONT color="red"><nested:write name="duplicate" property="studentName"/>
				</FONT>
				</nested:iterate>
				</logic:notEmpty>
				<FONT color="red"><nested:write property="message"/></FONT>
				</c:when>
				</c:choose>
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
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendance.class.col" /></div></td>
                  <td class="row-even" ><nested:write name="createPracticalBatchForm" property="className" /></td>
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.mail.subject" /></div></td>
                  <td class="row-even" ><nested:write name="createPracticalBatchForm" property="subjectName" /></td>
                  </tr>
                   <tr >
                  <td height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.type" /></div></td>
                  <td class="row-even" ><nested:write name="createPracticalBatchForm" property="attTypeName" /></td>
                  <td class="row-odd" ><div align="right"><bean:message key="knowledgepro.attendanceentry.activitytype" /></div></td>
                  <td class="row-even" ><nested:write name="createPracticalBatchForm" property="activityName" /></td>
                  </tr>
                 <c:choose>
				<c:when test="${operation == 'search' || operation == 'edit' || operation == 'save'}">
				<tr >
                  <td height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span>&nbsp;
                  <bean:message key="knowledgepro.attendance.batchname.col" /></div></td>
                  <td class="row-even" ><html:text property="batchName" size="10" maxlength="10" styleId="batchName"></html:text></td>
                  <td class="row-odd" >&nbsp;</td>
                  <td class="row-even" >&nbsp;</td>
                  </tr>
				</c:when>
				</c:choose> 
                <tr >
                <c:choose>
	                  	<c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes' && (operation == 'search' || operation == 'save')}">
                  <td height="25" class="row-odd" ><div align="right">&nbsp;
                  <bean:message key="knowledgepro.attendance.regno.from.col" /></div></td>
                  </c:when>
	               <c:when test="${createPracticalBatchForm.regdNoDisplay != 'yes' && (operation == 'search' || operation == 'save')}">
	               <td height="25" class="row-odd" ><div align="right">&nbsp;
                  <bean:message key="knowledgepro.attendance.rollno.from.col" /></div></td>
	               </c:when>
	               </c:choose>
	               <c:choose>
	      			<c:when test="${operation == 'search' || operation == 'save'}">
                  <td class="row-even" ><html:text property="regNoFrom" size="9" maxlength="9" styleId="regNoFrom"></html:text></td>
                  </c:when>
                  </c:choose>
                  <c:choose>
	          	<c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes' && (operation == 'search' || operation == 'save')}">
                  <td class="row-odd" ><div align="right">&nbsp;
                 <bean:message key="knowledgepro.attendance.regno.to.col" /></div></td>
                 </c:when>
                <c:when test="${createPracticalBatchForm.regdNoDisplay != 'yes' && (operation == 'search' || operation == 'save')}">
                 <td class="row-odd" ><div align="right">&nbsp;
                 <bean:message key="knowledgepro.attendance.rollno.to.col"/>  </div></td>
                 </c:when>
                 </c:choose>
                 <c:choose>
	      			<c:when test="${operation == 'search'|| operation == 'save'}">
                  <td class="row-even" ><html:text property="regNoTo" size="9" maxlength="9" styleId="regNoTo"></html:text></td>
                  </c:when>
                  </c:choose>
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
          <c:choose>
	      <c:when test="${operation == 'search' || operation == 'save'}">
            <td height="35" align="center"><html:button property="" styleClass="formbutton" value="Search" onclick="searchStudent()"></html:button></td>
            </c:when>
            </c:choose>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
			<c:set var="temp" value="0" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
			<logic:notEmpty	name="createPracticalBatchForm" property="studentList">
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5"></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5"></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td height="54" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    <tr >
                    <c:choose>
	                  	<c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes'}">                 
                        <td width="160" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.regno"/> </td>
                        </c:when>
                        <c:otherwise>
                        <td width="160" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.rollno"/></td>
                        </c:otherwise>
                        </c:choose>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.student.name"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.select"/></td>
                      </tr>
					<nested:iterate name="createPracticalBatchForm" property="studentList" id="create" indexId="count">
						<c:if test="${count < createPracticalBatchForm.halfLength}">
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>                  
                      <td width="193" align="center">
                      <c:choose>
	                  <c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes'}">
                      <nested:write name="create" property="studentTO.registerNo" />
                      </c:when>
                        <c:otherwise>
                        <nested:write name="create" property="studentTO.rollNo" />
                        </c:otherwise>
                        </c:choose>
                      </td>
                      <td width="212" align="center"><nested:write name="create" property="studentTO.studentName" /></td>                     
                      <c:choose>
						<c:when test="${operation == 'search'}">                   
                      <td height="25" align="center"><label>
                        <input type="hidden" name="studentList[<c:out value='${count}'/>].tempCheckValue" id="studhidden_<c:out value='${count}'/>"
													value="<nested:write name='create' property='tempCheckValue'/>" />
						<input type="checkbox" name="studentList[<c:out value='${count}'/>].checkValue" id="<c:out value='${count}'/>" />
						<script type="text/javascript">
							var studentId2 = document.getElementById("studhidden_<c:out value='${count}'/>").value;
								if(studentId2 == "true") {
									document.getElementById("<c:out value='${count}'/>").checked = true;
									}		
						</script>
                      </label></td>
                      </c:when>
                      <c:otherwise>
                      <td height="25" align="center"><label>
                        <input type="hidden" name="studentList[<c:out value='${count}'/>].dummyCheckValue" id="studhidden_<c:out value='${count}'/>"
													value="<nested:write name='create' property='dummyCheckValue'/>" />
						<input type="checkbox" name="studentList[<c:out value='${count}'/>].checkValue" id="<c:out value='${count}'/>" />
						<script type="text/javascript">
							var studentId = document.getElementById("studhidden_<c:out value='${count}'/>").value;
								if(studentId == "true") {
									document.getElementById("<c:out value='${count}'/>").checked = true;
									}		
						</script>
                      </label></td>
                      </c:otherwise>
                      </c:choose>
                      </tr>
                      </c:if>
                      </nested:iterate>
					</table>					
					 </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">              
                  <table width="100%" cellspacing="1" cellpadding="2">
                      <tr >
                        <c:choose>
	                  	<c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes'}">                 
                        <td width="160" class="row-odd" align="center" ><bean:message key="knowledgepro.attendance.regno"/></td>
                        </c:when>
                        <c:otherwise>
                        <td width="160" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.rollno"/></td>
                        </c:otherwise>
                        </c:choose>
                        <td width="135" class="row-odd" align="center"><bean:message key="knowledgepro.attendance.student.name"/></td>
                        <td width="40" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.select"/></td>
                      </tr>
                      <c:set var="c" value="0"/>
					<nested:iterate name="createPracticalBatchForm" property="studentList" id="create" indexId="count">
					 <c:set var="c" value="${c + 1}"/>
                      <c:if test="${count >= createPracticalBatchForm.halfLength}">
                      <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>				 			
                      <td width="193" align="center">
                      <c:choose>
	                  <c:when test="${createPracticalBatchForm.regdNoDisplay == 'yes'}">
                      <nested:write name="create" property="studentTO.registerNo" />
                      </c:when>
                        <c:otherwise>
                        <nested:write name="create" property="studentTO.rollNo" />
                        </c:otherwise>
                        </c:choose>
                        </td>
                      <td width="212" align="center"><nested:write name="create" property="studentTO.studentName" /></td>                      
                      <c:choose>
						<c:when test="${operation == 'search'}">
						<td height="25" align="center">
						<input type="hidden" name="studentList[<c:out value='${count}'/>].tempCheckValue"
							id="studhidden_<c:out value='${count}'/>" value="<nested:write name='create' property='tempCheckValue'/>" />								
						<input type="checkbox" name="studentList[<c:out value='${count}'/>].checkValue" id="<c:out value='${count}'/>" />													
						<script type="text/javascript">
							var studentId3 = document.getElementById("studhidden_<c:out value='${count}'/>").value;
							if(studentId3 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
						</c:when>
						<c:otherwise>
						<td height="25" align="center">
						<input type="hidden" name="studentList[<c:out value='${count}'/>].dummyCheckValue"
							id="studhidden_<c:out value='${count}'/>" value="<nested:write name='create' property='dummyCheckValue'/>" />								
						<input type="checkbox" name="studentList[<c:out value='${count}'/>].checkValue" id="<c:out value='${count}'/>" />													
						<script type="text/javascript">
							var studentId1 = document.getElementById("studhidden_<c:out value='${count}'/>").value;
							if(studentId1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
						</td>
						</c:otherwise>
						</c:choose>				
                      </tr>
					</c:if>
                      </nested:iterate>
                      <c:if test="${(c % 2) != 0}" >
                      <tr  class="row-white">
                        <td width="193"  >&nbsp;</td>
                        <td width="212" >&nbsp;</td>
                        <td height="25" align="center" >&nbsp;
                      </td>
                      </tr>
                      </c:if>
                </table></td>
                <td  background="images/right.gif" width="5" height="54"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" ></td>
              </tr>
            </table>
			</logic:notEmpty>
			</td>           
          </tr>
        </table>
          </td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">
        <c:choose>
				<c:when test="${operation == 'search' && createPracticalBatchForm.studentList != null}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="49%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Save" onclick="savePracticalBatch()"></html:button></td>
					<td width="2%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetPracticalBatch()"></html:button></td>
					<td width="51%" height="35" align="left">
					<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelPracticalBatch()"></html:button></td>
				</tr>
			</table>
			</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${operation == 'save' && createPracticalBatchForm.studentList != null}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="45%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Save" onclick="savePracticalBatch()"></html:button></td>
					<td width="7%" height="35" align="center"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetPracticalBatch()"></html:button></td>
					<td width="48%" height="35" align="left">
					<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelPracticalBatch()"></html:button></td>
				</tr>
			</table>
			</c:when>
			</c:choose>
			<c:choose>
			<c:when test="${operation == 'edit'}">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="45%" height="35" align="right"><html:button property="" styleClass="formbutton" value="Update" onclick="updatePracticalBatch()"></html:button></td>
					<td width="7%" height="35" align="center"><html:cancel styleClass="formbutton" value="Reset" /></td>
					<td width="48%" height="35" align="left">
					<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelPracticalBatch()"></html:button></td>
				</tr>
			</table>
			</c:when>
			</c:choose>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>     
      <tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.attendanceentry.batchname" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.attn.period.selectedClasses" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.attendanceentry.subject" /></td>
									<td align="center" class="row-odd"><bean:message
										key="knowledgepro.attendance.students" /></td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="createPracticalBatchForm"
									property="allBatchList">
									<nested:iterate id="rec" name="createPracticalBatchForm"
										property="allBatchList" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
										<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="batchName" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="classSchemewiseTO.classesTo.className" /></td>
											<td align="center" width="10%" class="row-even"><nested:write
												name="rec" property="subjectTO.name" /></td>
												
											<td width="15%" align="left" class="row-even">
											<logic:notEmpty name="rec" property="batchStudentTOList">
												<nested:iterate id="st" name="rec" property="batchStudentTOList">
											<nested:write name="st" property="studentTO.studentName" />
												</nested:iterate>
											</logic:notEmpty>
											</td>
											<td align="center" width="7%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer" onclick="editPracticalBatch('<bean:write name="rec" property="id"/>')" /></div>
											</td>
											<td width="7%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer" onclick="deletePracticalBatch('<bean:write name="rec" property="id"/>')" /></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
											</c:when>
											<c:otherwise>
												<tr>
													<td width="5%" height="25" class="row-white">
													<div align="center"><c:out value="${count + 1}" /></div>
													</td>
													<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="batchName" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="classSchemewiseTO.classesTo.className" /></td>
											<td align="center" width="10%" class="row-white"><nested:write
												name="rec" property="subjectTO.name" /></td>
												
											<td width="15%" align="left" class="row-white">
											<logic:notEmpty name="rec" property="batchStudentTOList">
												<nested:iterate id="st" name="rec" property="batchStudentTOList">
											<nested:write
												name="st" property="studentTO.studentName" />						
												</nested:iterate>
											</logic:notEmpty>
											</td>
											<td align="center" width="7%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer" onclick="editPracticalBatch('<bean:write name="rec" property="id"/>')" /></div>
											</td>
											<td width="7%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer" onclick="deletePracticalBatch('<bean:write name="rec" property="id"/>')"  /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</nested:iterate>
								</logic:notEmpty>
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