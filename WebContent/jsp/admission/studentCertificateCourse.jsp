<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script>
	
		
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}

	function showMandatoryCourse(id){
		document.getElementById("mandatory").style.display = "block";
	}

	function hideMandatoryCourse(id){
		if(document.getElementById(id).checked){
			document.getElementById("mandatory").style.display = "none";
		}
		else{
			document.getElementById("mandatory").style.display = "block";
		}
	}
	function showOptionalCourse(id){
		document.getElementById("optional").style.display = "block";
	}

	function hideOptionalCourse(id){
		var len = parseInt(document.getElementById("mandatoryLen").value);
		
		
		if(document.getElementById(id).checked){
			for ( var count = 0; count <= len - 1; count++) {
				var styleId = "mandatory_check_" + count;
				if(id != styleId){
					document.getElementById(styleId).disabled = true;	
				}
			};
			document.getElementById("optional").style.display = "none";
		}
		else{
			document.getElementById("optional").style.display = "block";
			for ( var count = 0; count <= len - 1; count++) {
				var styleId = "mandatory_check_" + count;
				document.getElementById(styleId).disabled = false;	
			};
		}
	}
	function viewDetails(id) {
		var url = "StudentCertificateCourse.do?method=showTeacherDetails&certificateCourseId=" + id;
		myRef = window
				.open(url, "viewFeeDetails",
						"left=20,top=20,width=500,height=200,toolbar=1,resizable=0,scrollbars=1");
	}
	function printCertCourse(id) {
		/*var url = "StudentCertificateCourse.do?method=showStudentDetails";
		myRef = window
				.open(url, "certificateCourse",
						"left=20,top=20,width=700,height=900,toolbar=1,resizable=0,scrollbars=1");*/
		alert("Application fee for certificate course should be remitted at admission office");
			
		document.studentCertificateCourseForm.submit();
	}

</script>
<html:form action="/StudentCertificateCourse" >
	<html:hidden property="method" styleId="method" value="saveCertificateCourse"/>
	<html:hidden property="formName" value="studentCertificateCourseForm"/>
	<html:hidden property="pageType" value="1"/>
	
	<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="172">&nbsp;</td>
	    <td width="37">&nbsp;</td>
	    <td width="269">&nbsp;</td>
	    <td width="30">&nbsp;</td>
	    <td width="422">&nbsp;</td>
	  </tr>
	  <tr>
	    <td valign="top"><table width="100" border="0" cellspacing="0" cellpadding="0">
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td>&nbsp;</td>
	        </tr>
	        <tr>
	          <td height="30" valign="bottom"><p>&nbsp;</p>
	          <p>&nbsp;</p>
	          <p><img src="bullet_imge.gif" width="170" height="8" /></p></td>
	        </tr>
	      </table></td>
	    <td width="37"><p>&nbsp;</p>
	    <p>&nbsp;</p>
	    <p>&nbsp;</p></td>
	    <td colspan="3" valign="top">
	    <table width="100%" border="0">
			<tr>
				<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/st_Tcenter.gif" class="body"><strong
							class="boxheader">Certificate Course</strong></td>
	
						<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td height="20">
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
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr height="30">
								<td colspan="2" align="center" valign="top"><font color="yellowred">
									Application fee for certificate course should be remitted at admission office </font>
								</td>
							</tr>
							<tr>
								<td><img src="images/st_01.gif" width="5" height="5" /></td>
								<td width="914" background="images/st_02.gif"></td>
	
								<td><img src="images/st_03.gif" width="5" height="5" /></td>
							</tr>
							
							<tr>
								<td width="5" background="images/st_left.gif"></td>
								<td valign="top">
								<table width="100%" cellspacing="1" cellpadding="2">
									<tr>
										<td>
											<div id="mandatory">
												<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" >
													<logic:notEmpty name="studentCertificateCourseForm" property="mandatorycourseList" >
														<tr>
															<td align="center" class = "heading">Mandatory</td>
														</tr>
														
														<tr>
														<td>&nbsp;</td>
														</tr>
														<c:set var="manLen" value="0" />
														<nested:iterate name="studentCertificateCourseForm" property="mandatorycourseList" id="mandatorycourseList" indexId="count">
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
															%>
															<td width="75%">
																<nested:checkbox
																	property="courseCheck" styleId="<%=s1%>" onclick="hideOptionalCourse(this.id)" />&nbsp;&nbsp;&nbsp;
																<nested:write
																	name="mandatorycourseList" property="courseName" />
														
															</td>
															<td height="25" align="center">
																<div align="center"><a href="#" onclick="viewDetails('<nested:write name="mandatorycourseList" property="id"/>')">Fee Details</a>
																</div>
															</td>
															<c:set var="manLen" value="${manLen+1}" />
														  </nested:iterate>
													</logic:notEmpty>
												</table>
											</div>
										</td>
									</tr>
									<tr>
									<td><input
										type="hidden" name="mandatoryLen" id="mandatoryLen"
										value="<c:out value="${manLen}"/>" />&nbsp;</td>
									</tr>
									<logic:notEmpty name="studentCertificateCourseForm" property="optionalCourseList" >
										<tr>
											<td>
												<div id = "optional">
													<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" >
													<tr>
													<td align="center" class = "heading">Optional</td>
													</tr>
													<tr>
													<td>&nbsp;</td>
													</tr>
														
															<nested:iterate name="studentCertificateCourseForm" property="optionalCourseList" id="optionalCourseList" indexId="count">
																<c:choose>
																	<c:when test="${count%2 == 0}">
																		<tr class="studentrow-even">
																	</c:when>
																	<c:otherwise>
																		<tr class="studentrow-white">
																	</c:otherwise>
																</c:choose>
																<%
																	String s1 = "optional_check_" + count;
																				String s2 = "hidden_" + count;
																%>
																<td width="75%"> 
																	<nested:checkbox
																		property="courseCheck" styleId="<%=s1%>" onclick="hideMandatoryCourse(this.id)"/>&nbsp;&nbsp;&nbsp;
																	<nested:write
																		name="optionalCourseList" property="courseName" />
																 
																</td>
																<td align="center">
																	<div align="center"><a href="#" onclick="viewDetails('<nested:write name="optionalCourseList" property="id"/>')">Fee Details</a>
																	</div>
																</td>
														  	</nested:iterate>
													</table>
												</div>
											</td>
										</tr>
									</logic:notEmpty>
									
									
								</table>
								</td>
								<td width="5" height="30" background="images/st_right.gif"></td>
							</tr>
							
							<tr>
	
								<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
								<td background="images/st_05.gif"></td>
								<td><img src="images/st_06.gif" /></td>
							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					
					<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
						<td height="25" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="45%" height="35">
								<div align="center">
								<html:button property=""  styleClass="formbutton" value="Proceed" onclick="printCertCourse()"></html:button>
										
								</div>
								</td>
							</tr>
						</table>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
	
						<td valign="top" class="news">&nbsp;</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					<tr>
						<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/st_TcenterD.gif"></td>
						<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
				</td>
	
			</tr>
		</table>
	    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="bg_img.gif">
	  <tr>
	    <td height="32" align="center" class="copyright">Copyrights @ 2009 Knowledge Pro All rights reserved. </td>
	  </tr>
	</table>
	</td>
	</tr>
	</table>

</html:form>

