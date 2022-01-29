<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css">
<script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
<script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
<script type="text/javascript" src="js/leftSwitchMenu.js"></script>

<script type="text/javascript">
	function cancelAction(){
	document.location.href="StudentLoginAction.do?method=returnHomePage";
		}
	function printChallan(){
	document.location.href="StudentLoginAction.do?method=initFeeChallanPrint";
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
	function displayDetail(id) {
		if (id!='' || id.length>0 ) {
			document.getElementById("optionalDetail").style.display = "block";
		} else {
			document.getElementById("optionalDetail").style.display = "none";
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
				   	if(!inputObj.checked)
						inputObj.disabled = true;
				}				
		    }
	    }else{
	    	for(var count1 = 0;count1<inputs.length;count1++) {
			    inputObj = inputs[count1];
			    var type = inputObj.getAttribute("type");
			   	if (type == 'checkbox') {
						inputObj.disabled = false;
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
	function viewOptionalDetails(){
		var url = "StudentCertificateCourse.do?method=showTeacherDetails&certificateCourseId=" + document.getElementById("optionalId").value;
		myRef = window.open(url, "viewFeeDetails", "left=20,top=20,width=500,height=200,toolbar=1,resizable=0,scrollbars=1");
	}
	function printCertCourse() {
		document.getElementById("printCourse").value ="true";
		document.newStudentCertificateCourseForm.submit();
	}
	function saveCertCourse() {
		document.newStudentCertificateCourseForm.submit();
	}
	function submitWithoutPayment(){
		var deleteConfirm =confirm("Are you sure to register the selected certificate course? \n"+
                                         "Once registered, the fees payment should be done within 1 day at Admission Office, Central block(between 09:00 AM to 05:30 PM).\n"+
                                         "The registration will be cancelled if fees is not paid within the time specified above.");
		if (deleteConfirm == true) {
					document.getElementById("applyWithoutPayment").value=true;
					document.newStudentCertificateCourseForm.submit();
				}		
		
		
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
	<html:hidden property="method" styleId="method" value="saveCertificateCourseForStudentLogin" />
	<html:hidden property="formName" value="newStudentCertificateCourseForm" />
	<html:hidden property="pageType" value="1" />

	<html:hidden property="certificateCourseName" styleId="certificateCourseName" value="" />
	<html:hidden property="printCourse" styleId="printCourse" />
	<html:hidden property="applyWithoutPayment" styleId="applyWithoutPayment" />
	<html:hidden name="newStudentCertificateCourseForm" property="displayChallan" styleId="displayChallan" />
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
							<div id="errorMessages" class="ui-widget">
							<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
							<p>
							<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
							<strong>Alert:</strong>
							<span id="err"><html:errors/></span>
							</p>
							</div>
							</div>

							<div id="messages"><div class="display-info">
							<span id="msg"><html:messages id="message" property="messages" message="true"><c:out value="${message}" escapeXml="false"></c:out><br></html:messages></span>
							</div>
							</div>
							<script type="text/javascript">
								if(document.getElementById("msg")==null ||  document.getElementById("msg").innerHTML==''){
									document.getElementById("messages").style.display="none";
									}else if(document.getElementById("displayChallan").value==true || document.getElementById("displayChallan").value=="true"){
										document.getElementById("msg").innerHTML=document.getElementById("msg").innerHTML+" Click here to <a href='javascript:printChallan();'>print challan.</a> <br/> The Challan will be also available in the Navigation bar for 1 month";
										}
								if(document.getElementById("err").innerHTML==''){
									document.getElementById("errorMessages").style.display="none";
									}
							</script>
							
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
						
						
						
						<!-- first -->
												<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
<table width="100%" cellspacing="1" cellpadding="2">

							<logic:equal value="false" name="newStudentCertificateCourseForm" property="currentlyApplied"> 
							<logic:equal value="true" name="newStudentCertificateCourseForm" property="certificateApplied"> 
								<tr>
									<td><div class="display-info">You have Already Applied For Certificate Course: <bean:write name="newStudentCertificateCourseForm" property="certificateName"/>  </div> </td>
								</tr>
							</logic:equal>
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
									<table width="99%" border="0" align="center" cellpadding="1"
										cellspacing="1">
										<logic:notEmpty name="newStudentCertificateCourseForm" property="mandatorycourseList">
										<tr class="studentrow-odd">
												<td width="5%">&nbsp;</td>
												<td width="40%" align="center">Certificate Course Name</td>
												<td width="20%" align="center">Applied/Max intake</td>
												<td align="center"> Details</td>
											</tr>
										
											<c:set var="manLen" value="0" />
											<%int count=0; %>
											<nested:iterate name="newStudentCertificateCourseForm"
												property="mandatorycourseList" id="mandatorycourseList" >
												<logic:equal value="true" name="mandatorycourseList" property="display">
												
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="studentrow-even">
													</c:when>
													<c:otherwise>
														<tr class="studentrow-white">
													</c:otherwise>
												</c:choose>
												<%
												
													String s1 = "mandatory_check_" + count;
																String s2 = "hidden_" + count;
																count++;
												%>
												<td >
												<logic:equal value="false" name="mandatorycourseList" property="checkBoxDisplay">
												<nested:checkbox property="courseCheck"
													styleId="<%=s1%>" onclick="hideOptionalCourse(this.id)" /></logic:equal>
													<logic:equal value="true" name="mandatorycourseList" property="checkBoxDisplay">
													
													<img src="images/cancel_icon.gif" width="18" height="18"/>
													</logic:equal>
												&nbsp;&nbsp;&nbsp;
												</td>
												<td width="40%" ><nested:write name="mandatorycourseList"
													property="courseName" /></td>
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
						<tr>
							<td colspan="3" > &nbsp;</td>
						</tr>
						
												<!-- second -->
											<logic:equal value="false" name="newStudentCertificateCourseForm" property="certificateApplied">
							<logic:notEmpty name="newStudentCertificateCourseForm"
									property="optionalCourseList">
												<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							<logic:equal value="false" name="newStudentCertificateCourseForm" property="certificateApplied">
							<logic:notEmpty name="newStudentCertificateCourseForm"
									property="optionalCourseList">
									<tr>
										<td>
										<div id="optional">
										<table width="99%" border="0" align="center" cellpadding="0"
											cellspacing="0">
											<tr>
												<td class="heading">Optional Certificate Course <!--
												
												For more Optional Courses select from below
												--></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
											<td width="100%">
											<div id="optionalTable">
												<table width="100%" cellpadding="1" cellspacing="1">
													<logic:notEmpty name="newStudentCertificateCourseForm" property="optionalCourseList">
											<tr class="studentrow-odd">
												<td width="5%">&nbsp;</td>
												<td width="40%" align="center">Certificate Course Name</td>
												<td width="20%" align="center">Applied/Max intake</td>
												<td align="center"> Details</td>
											</tr>
											
											<c:set var="optLen" value="0" />
											<nested:iterate name="newStudentCertificateCourseForm"
												property="optionalCourseList" id="optionalCourseList"
												indexId="count">
												<logic:equal value="true" name="optionalCourseList" property="display">
												<c:choose>
													<c:when test="${count%2 == 0}">
														<tr class="studentrow-even">
													</c:when>
													<c:otherwise>
														<tr class="studentrow-even">
													</c:otherwise>
												</c:choose>
												<%
													String s1 = "optional_check_" + count;
																String s2 = "optional_hidden_" + count;
												%>
												<td><logic:equal value="false" name="optionalCourseList" property="checkBoxDisplay">												
												<nested:checkbox property="courseCheck"
													styleId="<%=s1%>"  onclick="hideOptionalCourse(this.id)"/></logic:equal>
													<logic:equal value="true" name="optionalCourseList" property="checkBoxDisplay">
													<img src="images/cancel_icon.gif" width="18" height="18" />
													</logic:equal></td>
												<td width="40%">
												<nested:write name="optionalCourseList"
													property="courseName" /></td>
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
												</div>
											</td>
											</tr>
										</table>
										</div>
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
						
						</logic:notEmpty></logic:equal>
						
						<tr>
							<td colspan="3" > &nbsp;</td>
						</tr>
												<!-- Third -->
												<logic:equal value="false" name="newStudentCertificateCourseForm" property="currentlyApplied">
							<logic:equal value="true" name="newStudentCertificateCourseForm" property="extraCurricularApplied">
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
							 
								<tr>
									<td><div class="display-info"> You have Already Applied For Extra Curricular Certificate Course : <bean:write name="newStudentCertificateCourseForm" property="extraCertificateName"/></div> </td>
								</tr>
													
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
							</logic:equal>
							</logic:equal>
							
							<!-- Fourth -->
							
							<logic:equal value="false" name="newStudentCertificateCourseForm" property="extraCurricularApplied"> 
							<logic:notEmpty name="newStudentCertificateCourseForm" property="extraCurricularCourseList">					
												
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">

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
														<tr class="studentrow-even">
													</c:when>
													<c:otherwise>
														<tr class="studentrow-white">
													</c:otherwise>
												</c:choose>
												<td width="5%"> <c:out value="${count+1}"></c:out> </td>
												<td width="50%">&nbsp;&nbsp;&nbsp;
												<nested:write name="extraCurricularCourseList"
													property="courseName" /></td>
												<td height="25" align="center">
												<div align="center"><a href="#"
													onclick="viewDetails('<nested:write name="extraCurricularCourseList" property="id"/>')">Details</a></div>
												</td>
											</nested:iterate>
											</table>
											</td>
											</tr>
										
							</table>
							</td>
							<td width="5" height="30" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						</logic:notEmpty>
										</logic:equal>	
					</table>
						
					<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="row-white">
                   <td colspan="2"><div align="center">
                   <logic:equal value="false" name="newStudentCertificateCourseForm" property="certificateApplied">
					<html:button property=""
								styleClass="btnbg" value="Proceed with Smart Card"
								onclick="saveCertCourse()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- <b>OR</b>
						&nbsp; <html:button property=""
								styleClass="btnbg" value="Proceed without Smart Card"
								onclick="submitWithoutPayment()"></html:button>	-->		
					</logic:equal>	&nbsp; <html:button property=""
								styleClass="btnbg" value="Close"
								onclick="cancelAction()"></html:button>
					</div></td>
                 </tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					&nbsp;
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
document.getElementById("optionalDetail").style.display = "none";
$(".display-info").fadeOut(800).fadeIn(800).fadeOut(400).fadeIn(400).fadeOut(400).fadeIn(400);
var print = "<c:out value='${newStudentCertificateCourseForm.printCourse}'/>";
if(print.length != 0 && print == "true") {
	document.getElementById("printCourse").value="false";
	var url ="StudentCertificateCourse.do?method=showStudentDetails";
	myRef = window.open(url,"certificateCourse","left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>