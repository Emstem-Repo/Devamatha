<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>


<script type="text/javascript">
	function cancel() {

		document.location.href = "ExamStudentSpecialization.do?method=initExamStudentSpl";

	}

	function validateCheckBox() 
	{
		 var inputs = document.getElementsByTagName("input");
		 var inputObj;
		 var checkBoxselectedCount = 0;
		 for(var count1 = 0;count1<inputs.length;count1++) 
		 {
		 	inputObj = inputs[count1];
		 	var type = inputObj.getAttribute("type");
		 	if (type == 'checkbox') 
			{
		 		if(inputObj.checked)	
			 	{
		 			checkBoxselectedCount++;
		 		}
		 	}
		 }
		 document.getElementById("err").innerHTML = "<font size='1.5px'>Number of Students Selected is:"+checkBoxselectedCount+"</font>";
		
	} 
</script>

<html:form action="/ExamStudentSpecialization.do">

	<html:hidden property="formName" value="ExamStudentSpecializationForm" />
	<html:hidden property="pageType" value="2" />
	<c:choose>
		<c:when test="${Operation=='search'}">
			<html:hidden property="method" styleId="method" value="Apply" />
			<html:hidden property="schemeNo" styleId="schemeNo" />
			<html:hidden property="courseId" styleId="courseId" />
			<html:hidden property="sectionId" styleId="sectionId" />

		</c:when>
		<c:otherwise>
			<html:hidden property="course" />
			<html:hidden property="method" styleId="method" value="updateUnAssignedStudents" />
		</c:otherwise>
	</c:choose>



	<table width="100%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.exam" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.exam.ExamStudentSpecialization" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="98%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="20" colspan="6" >
					<div id="err"  style="color: green;"></div>
					<div  id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"><html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages></FONT>
					</div>
				</tr>
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.exam.ExamStudentSpecialization" /> </strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td width="100%" height="20">&nbsp;</td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td height="20">
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
											<td width="27%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Course:</div>
											</td>
											<td width="20%" height="25" class="row-even"><bean:write
												name="examStudentSpecializationForm" property="courseName" /></td>
											<td width="28%" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>Semester
											:</div>
											</td>
											<td width="25%" class="row-even"><bean:write
												name="examStudentSpecializationForm" property="schemeNo" /></td>
										</tr>
										<tr>

											<td width="22%" class="row-odd" height="28">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.petticash.academicYear" /></div>
											</td>
											<td width="34%" class="row-even"><bean:write
												name="examStudentSpecializationForm" property="academicYear" />
											</td>
											<td class="row-odd">
											<div align="right"><bean:message
												key="admissionForm.studentinfo.castcatg.label" /></div>
											</td>
											<td class="row-even"><bean:write
												name="examStudentSpecializationForm" property="section" /></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="25" class=" heading"></td>
						</tr>
						<tr>
							<td height="25" class=" heading">
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
									<table width="100%" height="90" border="0" cellpadding="0"
										cellspacing="1">
										<tr class="row-odd">
											<td class="bodytext" width="10">
												Sl.No
											</td>
											<td width="40" height="25" class="bodytext">
											<div align="center"><strong>Select</strong></div>
											</td>
											<td width="100" height="25" class="bodytext">Roll No.</td>
											<td width="100" class="bodytext">Register No.</td>
											<td width="100" class="bodytext">Application Number</td>
											<td width="198" height="25" class="bodytext">Student Name</td>
											<td width="159" height="25" class="bodytext">
											<div align="center">Specialization</div>
											</td>
										</tr>
										<nested:iterate property="listStudentSpec" indexId="count">
											<%!int count=0; %>
											<%if(count%2==0)
											{
											%>
												<tr class="row-even">
											<%
											}
											else
											{
											%>
												<tr class="row-white">
											<%
											}	
											%>
												<td><%=count+1%></td>
												<td height="20">
												<c:choose>
													<c:when test="${Operation=='search'}">
														<%
															String s2 = "hidden_" + count;
															String s1 = "check_" + count;
															count++;
														%>

														<nested:hidden styleId='<%=s2%>' property="isCheckedDummy" />
														<nested:checkbox styleId='<%=s1%>' property="dummyOnOrOff" onclick="validateCheckBox()"/>
                                                      
														<nested:hidden property="id"/>
														<script type="text/javascript">
															var v = document.getElementById("hidden_<c:out value='${count}'/>").value;
															if (v == "true") {
																document.getElementById("check_<c:out value='${count}'/>").checked = true;
															}
														</script>
													</c:when>
													<c:otherwise>
														<%
															String s2 = "hidden_" + count;
																					String s1 = "check_" + count;
																					count++;
														%>
														<nested:hidden styleId='<%=s2%>' property="isCheckedDummy"/>
														<nested:checkbox styleId='<%=s1%>' property="dummyOnOrOff" onclick="validateCheckBox()" />
														<nested:hidden property="studentId"/>
														<script type="text/javascript">
															var v1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
															if (v1 == "true") {
																document.getElementById("check_<c:out value='${count}'/>").checked = true;
															}
														</script>
													</c:otherwise>
												</c:choose>
												</td>
												<td height="20">&nbsp;&nbsp; <nested:write
													property="rollNo" /></td>
												<td >&nbsp;&nbsp; <nested:write
													 property="regNo" /></td>
												<td>&nbsp;&nbsp; <nested:write
													property="appNo" /></td>
												<td height="20">&nbsp;&nbsp; <nested:write
													 property="studentName" /></td>
														<td height="20" align="left">
														<div align="center"><nested:write
															property="specName" /></div>
														</td>
											</tr>
										</nested:iterate>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td height="25" class=" heading"></td>
						</tr>
						<tr bgcolor="#FFFFFF">
							<td height="20">
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

											<td width="29%" height="25" class="row-odd">
											<div align="right"><span class="Mandatory">*</span>&nbsp;Select
											Specialization :</div>
											</td>
											<td width="27%" height="25" class="row-even"><html:select
												property="searchSpec" styleClass="combo">
												<html:option value=" ">Select</html:option>
												
												<logic:notEmpty name="specializationMap">
													<html:optionsCollection
														name="specializationMap" label="value"
														value="key" />
												</logic:notEmpty>
											</html:select></td>
										</tr>
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>

						<tr>
							<td height="25" class=" heading">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
								<tr>
									<td width="45%" height="35">
									<div align="right"><html:submit styleClass="formbutton"
										value="Apply" /></div>
									</td>
									<td width="2%"></td>
									<td width="53%"><html:button property="submit"
										styleClass="formbutton" value="Cancel" onclick="cancel()" /></td>
								</tr>								
								<tr><td style="height: 10px" align="center" colspan="3"></td></tr>								
							</table>
							</td>
						</tr>


					</table>
					<div align="center">
					<table width="100%" height="10  border=" 0" cellpadding="0"
						cellspacing="0">

						<tr>
							<td></td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

</html:form>

