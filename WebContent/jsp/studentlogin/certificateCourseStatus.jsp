<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
<link rel="stylesheet" href="jquery/development-bundle/demos/demos.css">
<script src="jquery/development-bundle/jquery-1.7.1.js"></script>
<link rel='stylesheet' type='text/css' href="css/auditorium/start/jquery-ui.css" />
	<script type='text/javascript' src="js/auditorium/jquery-ui.min.js"></script>
<script type="text/javascript">

function cancelAction() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}
</script>
<body>
<html:form action="/StudentLoginAction" >
	<html:hidden property="formName" value="loginform" />
	<table width="98%" border="0">
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/st_Tright_03_01.gif"></td>
					<td width="100%" background="images/st_Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">Certificate Course Status</strong></div>
					</td>
					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif" height="10"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr><td height="5"></td> </tr>
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top" class="news">
							<table width="100%" cellspacing="1" cellpadding="2" >
							
							<tr height="25px">
								<td  align="left" class="heading"> 
									<c:if test="${NoOfCompletedCourses>=2}">
									<b> Your certificate program status: <font color="green">Completed</font></b>
									</c:if>
									<c:if test="${NoOfCompletedCourses<2}">
									<b> Your certificate program status: <font color="red">Not Completed</font></b>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="100%"  cellspacing="1" cellpadding="2" id="report1" border="1" >
									<tr height="25px">
                        					<td width="10%" class="studentrow-odd"><div align="center">Semester</div></td>
                        					<td width="60%" class="studentrow-odd"><div align="center">Certificate Course Name</div></td>
                        					<td width="20%" class="studentrow-odd"><div align="center">Mandatory/Optional</div></td>
                        					<td width="20%" class="studentrow-odd"><div align="center">Status</div></td>
						   		 </tr>
						     <nested:notEmpty name="loginform" property="studentMarkDetailsTOList">
								<nested:iterate property="studentMarkDetailsTOList" name="loginform" indexId="count" type="com.kp.cms.to.exam.StudentMarkDetailsTO">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="studentrow-even">
										</c:when>
										<c:otherwise>
											<tr class="studentrow-white">
										</c:otherwise>
									</c:choose>
										<td  width="212" align="center"><nested:write property="semester"/></td>
                       					<td  width="212" align="left"><nested:write  property="subjectName" /></td>
                       					<td  width="212" align="center"><nested:write property="mandOrOptional"/></td>
                       					<td  width="212" align="center"><nested:write  property="status" /></td>
                        			</tr>
								</nested:iterate>
							</nested:notEmpty>
						</table>
					</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
												<td align="center">
													<html:button property="" styleClass="btnbg" value="Close" onclick="cancelAction()"></html:button>
				 								</td>
				                			 </tr>
									</table>
								</td>							
							</tr>
							</table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
						<tr><td height="5"></td> </tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
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
</html:form>
</body>