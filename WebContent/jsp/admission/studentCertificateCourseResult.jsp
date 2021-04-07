<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>
<script>
function viewOptionalDetails(){
	var url = "StudentCertificateCourse.do?method=showTeacherDetails&certificateCourseId=" + document.getElementById("optionalId").value;
	myRef = window.open(url, "viewFeeDetails", "left=20,top=20,width=500,height=200,toolbar=1,resizable=0,scrollbars=1");
}

function displayDetail(id) {
	if (id!='' || id.length>0 ) {
		document.getElementById("optionalDetail").style.display = "block";
	} else {
		document.getElementById("optionalDetail").style.display = "none";
	}
}
	function showMandatoryCourse(id) {
		document.getElementById("mandatory").style.display = "block";
	}

	function hideMandatoryCourse(id) {
		if (document.getElementById(id).checked) {
			document.getElementById("mandatory").style.display = "none";
		} else {
			document.getElementById("mandatory").style.display = "block";
		}
	}
	function showOptionalCourse(id) {
		document.getElementById("optionalId").style.display = "block";
	}

	function hideOptionalCourse(id) {
		var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    if (document.getElementById(id).checked) {
		    for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
				   	if(!inputObj.checked){
						inputObj.disabled = true;
						inputObj.value="off";
				   	}else
				   		inputObj.value="on";
				}				
		    }
	    }else{
	    	for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
						inputObj.disabled = false;
						inputObj.value="off";
				}				
		    }
		    }

	}
	function viewDetails(id) {
		var url = "StudentCertificateCourse.do?method=showTeacherDetails&certificateCourseId="
				+ id;
		myRef = window
				.open(url, "viewFeeDetails",
						"left=20,top=20,width=500,height=200,toolbar=1,resizable=0,scrollbars=1");
	}
	function printCertCourse() {
		document.getElementById("printCourse").value ="true";
		document.newStudentCertificateCourseForm.submit();
	}
	function saveCertCourse() {
		document.newStudentCertificateCourseForm.submit();
	}
	function cancel() {
		document.location.href = "NewStudentCertificateCourse.do?method=initStudentCertificateCourse";
	}
	function itemSearch(searchValue){
		var sda = document.getElementById("optionalId");
		var len = sda.length;
		var searchValueLen = searchValue.length;
		for(var m =0; m<len; m++){
			sda.options[m].selected = false;		
		}
		for(var j=0; j<len; j++)
		{
			for(var i=0; i<len; i++){
			if(((sda.options[i].text).substring(0, searchValueLen).toUpperCase())  == (searchValue.toUpperCase())){
				sda.options[i].selected = true;
				break;
			}
			}
		}
		document.getElementById("certificateCourseName").value = document.getElementById("optionalId").options[document.getElementById("optionalId").selectedIndex].text;
	}
</script>
<html:form action="/NewStudentCertificateCourse">
	<html:hidden property="method" styleId="method" value="saveCertificateCourse" />
	<html:hidden property="formName" value="newStudentCertificateCourseForm" />
	<html:hidden property="pageType" value="1" />

	<html:hidden property="certificateCourseName" styleId="certificateCourseName" value="" />
	<html:hidden property="printCourse" styleId="printCourse" value="false" />
	<table width="98%" border="0">
		<tr>
			<td></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"><bean:message
						key="knowledgepro.admin.certificate.course" /></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="right">
							
							 <img src="images/cancel_icon.gif" width="18" height="18" /> - <font size="2px"> Seats are full </font>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="right">
							&nbsp;
							
							</td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">

							<table width="100%" cellspacing="1" cellpadding="2">
							<logic:equal value="true" name="newStudentCertificateCourseForm" property="certificateApplied"> 
								<tr>
									<td><div class="display-info">You have Already Applied For Certificate Course: <bean:write name="newStudentCertificateCourseForm" property="certificateName"/> </div></td>
								</tr>
							</logic:equal>
							<logic:equal value="false" name="newStudentCertificateCourseForm" property="certificateApplied">
									<logic:notEmpty name="newStudentCertificateCourseForm"
										property="mandatorycourseList">
										<tr>
											<td class="heading">Mandatory Certificate Course</td>
										</tr>
									</logic:notEmpty>
									<tr>
										<td>&nbsp;</td>
									</tr>
								<tr>
									<td>
									<div id="mandatory">
									<table width="99%" border="0" align="center" cellpadding="0"
										cellspacing="0">
										<logic:notEmpty name="newStudentCertificateCourseForm" property="mandatorycourseList">
											<tr class="row-odd">
												<td width="40%" align="center">Certificate Course Name</td>
												<td width="20%" align="center">Course MaxIntake</td>
												<td width="20%" align="center">Group MaxIntake</td>
												<td align="center"> Details</td>
											</tr>
											
											<c:set var="manLen" value="0" />
											<nested:iterate name="newStudentCertificateCourseForm"
												property="mandatorycourseList" id="mandatorycourseList"
												indexId="count">
												<logic:equal value="true" name="mandatorycourseList" property="display">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
												<%
													String s1 = "mandatory_check_" + count;
																String s2 = "hidden_" + count;
												%>
												<td width="40%">
												<logic:equal value="false" name="mandatorycourseList" property="checkBoxDisplay">
												<nested:checkbox property="courseCheck"
													styleId="<%=s1%>" onclick="hideOptionalCourse(this.id)" /></logic:equal>
													<logic:equal value="true" name="mandatorycourseList" property="checkBoxDisplay">
													
													<img src="images/cancel_icon.gif" width="18" height="18"/>
													</logic:equal>
													
													&nbsp;&nbsp;&nbsp;
												<nested:write name="mandatorycourseList"
													property="courseName" /></td>
												<td width="20%" align="center"><b>
												<nested:write name="mandatorycourseList"
													property="maxIntake" /></b>
												</td>
												<td width="20%" align="center"><b>
												<nested:write name="mandatorycourseList"
													property="groupMaxIntake" /></b>
												</td>	
												<td height="25" align="center">
												<div align="center"><a href="#"
													onclick="viewDetails('<nested:write name="mandatorycourseList" property="id"/>')">Details</a></div>
												</td>
												
												<c:set var="manLen" value="${manLen+1}" />
												</tr>
												</logic:equal>
											</nested:iterate>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>
								<tr>
									<td><input type="hidden" name="mandatoryLen"
										id="mandatoryLen" value="<c:out value="${manLen}"/>" />&nbsp;</td>
								</tr>
								<logic:notEmpty name="newStudentCertificateCourseForm" property="optionalCourseList">
									<tr>
										<td>
										<div id="optional">
										<table width="99%" border="0" align="center" cellpadding="0"
											cellspacing="0">
											
											
											<tr>
												<td class="heading">Optional Certificate Course</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
											<td>
												<table width="100%" cellpadding="0" cellspacing="0">
												<logic:notEmpty name="newStudentCertificateCourseForm" property="optionalCourseList">
											<tr class="row-odd">
												<td width="40%" align="center">Certificate Course Name</td>
												<td width="20%" align="center">Course MaxIntake</td>
												<td width="20%" align="center">Group MaxIntake</td>
												<td align="center"> Details</td>
											</tr>
											
											<c:set var="optLen" value="0" />
											<nested:iterate name="newStudentCertificateCourseForm"
												property="optionalCourseList" id="optionalCourseList"
												indexId="count">
												<logic:equal value="true" name="optionalCourseList" property="display">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
												<%
													String s1 = "optional_check_" + count;
																String s2 = "optional_hidden_" + count;
												%>
												<td width="40%">
											<logic:equal value="false" name="optionalCourseList" property="checkBoxDisplay">												
												<nested:checkbox property="courseCheck"
													styleId="<%=s1%>"  onclick="hideOptionalCourse(this.id)"/></logic:equal>
													<logic:equal value="true" name="optionalCourseList" property="checkBoxDisplay">
													<img src="images/cancel_icon.gif" width="18" height="18" />
													</logic:equal>
													 &nbsp;&nbsp;&nbsp;
												<nested:write name="optionalCourseList"
													property="courseName" /></td>
												<td width="20%" align="center"><b>
												<nested:write name="optionalCourseList"
													property="maxIntake" /></b>
												</td>
												<td width="20%" align="center"><b>
												<nested:write name="optionalCourseList"
													property="groupMaxIntake" /></b>
												</td>	
												<td height="25" align="center">
												<div align="center"><a href="#"
													onclick="viewDetails('<nested:write name="optionalCourseList" property="id"/>')">Details</a></div>
												</td>
												
												<c:set var="optLen" value="${manLen+1}" />
												</tr>
												</logic:equal>
											</nested:iterate>
										</logic:notEmpty>
												</table>
											</td>
											</tr>
										</table>
										</div>
										</td>
									</tr>
								</logic:notEmpty>
							</logic:equal>	
							<logic:equal value="true" name="newStudentCertificateCourseForm" property="extraCurricularApplied"> 
								<tr>
									<td>
									<div class="display-info">
									You have Already Applied For Extra Curricular Certificate Course:<bean:write name="newStudentCertificateCourseForm" property="extraCertificateName"/>
									</div>
									</td>
								</tr>
							</logic:equal>
							<c:set var="extLen" value="0" />
							<logic:equal value="false" name="newStudentCertificateCourseForm" property="extraCurricularApplied"> 
							<logic:notEmpty name="newStudentCertificateCourseForm" property="extraCurricularCourseList">
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td  class="heading">Extra Curricular Certificate Course</td>
											</tr>

											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
											<td>
											<table width="100%" cellpadding="1" cellspacing="1" id="extra">
											
											<nested:iterate name="newStudentCertificateCourseForm"
												property="extraCurricularCourseList" id="extraCurricularCourseList"
												indexId="count">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
												</c:choose>
												
												<%
													String s1 = "extra_check_" + count;
																String s2 = "extrahidden_" + count;
												%>
												<td width="55%"><nested:checkbox property="courseCheck"
													styleId="<%=s1%>" onclick="hideOptionalCourse(this.id)" />&nbsp;&nbsp;&nbsp;
												<nested:write name="extraCurricularCourseList"
													property="courseName" /></td>
												<td height="25" align="center">
												<div align="center"><a href="#"
													onclick="viewDetails('<nested:write name="extraCurricularCourseList" property="id"/>')">Details</a></div>
												</td>
												<c:set var="extLen" value="${extLen+1}" />
											</nested:iterate>
											</table>
											</td>
											</tr>
										</logic:notEmpty>
										</logic:equal>	

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
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="30%" height="35">
							<div align="right"><html:button property=""
								styleClass="formbutton" value="save&print"
								onclick="printCertCourse()"></html:button></div>
							</td>
							<td width="15%" height="35">
							<div align="right"><html:button property=""
								styleClass="formbutton" value="save"
								onclick="saveCertCourse()"></html:button></div>
							</td>
							<td width="45%" height="35">
							<div><html:button property="" styleClass="formbutton"
								value="Cancel" onclick="cancel()"></html:button></div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
</script>