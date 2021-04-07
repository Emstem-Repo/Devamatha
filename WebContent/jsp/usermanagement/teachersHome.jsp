<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<script src="js/md5.js" type="text/javascript"></script>

<script type="text/javascript" src="js/common.js">

		</script>
<head>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
</head>
<script language='JavaScript'>

	function viewDetails() {
		var url = "html/about.html";
		window.open(url,'aboutus','left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1');
	}
	function viewHelp() {
		var url = "jsp/helpFiles/index.html";
		window.open(url,'Help','left=20,top=20,width=900,height=500,toolbar=1,resizable=0,scrollbars=1');
	}
	function viewContactUsDetails() {
		var url = "html/contact_us.html";
		window.open(url,'contactus','left=20,top=20,width=325,height=175,toolbar=0,resizable=0,scrollbars=0');
	}
	var hook = true;
	$(function() {
		 $("a,.formbutton,img,input:checkbox").each(function(){
			var method = $(this).attr("onclick"); 
			if(method != undefined){
				method ="appendMethodOnBrowserClose(),"+method;
			}else{
				method ="appendMethodOnBrowserClose()";
			}
				
			$(this).attr("onclick",method);
		 });
		});

	function appendMethodOnBrowserClose(){
		hook = false;
	}
	$(function() {
		 $("a,.formbutton").click(function(){
			hook =false;
		  });
	});
	$(document).keydown(function(event) {
	    var keycode = event.keyCode;
	    if(keycode == '116') {
	       return false;
	    }
	});
	window.onbeforeunload = confirmExit;
	  function confirmExit()
	  {
		if(hook){
				var deleteConfirm = confirm("You have attempted to leave this page.  If you continue, the current session will be inactive?");
				if (deleteConfirm == true) {
					document.location.href = "LogoutAction.do?method=logout";
					return true;	
				}else{
					return false;
				}
			}else{
				hook =true;
			}
	  }
</script>
<html:form action="/LoginAction">
	<html:hidden property="method" styleId="method" value="loginAction" />
	<html:hidden property="formName" value="loginform" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="loginType" styleId="loginType" name="loginform"/>
	<table width="95%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td width="9" background="images/leftShadow.gif">&nbsp;</td>
			<td width="1158" valign="top">
			<table bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0"
				width="100%">
				<tbody>
					<tr>
						<td>
						<table width="98%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tbody>
								<tr>
									<td height="87" colspan="3" valign="top">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="20%" height="25" rowspan="2" class="row-white">
											<div align="left"><img
												src='<%=request.getContextPath()%>/TopBarServlet'
												alt="Logo not available" width="238" height="100"></div>
											</td>
											<td width="80%">
											<div align="right"><img
												src="images/OnlineHeaderFinal.jpg" width="772" height="100"></div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									
									<td width="100%" background="images/Orang_CurveBG.gif">
									<div align="left">
								        <table width="100%" border="0" cellspacing="0" cellpadding="0">
											<c:choose> 
											<c:when test="${onlineuser == 1}">
												<tr><td width="15%" class="heading_white">
													
													</td></tr>
											</c:when>
											<c:otherwise>
											<tr>
												<td width="53%" height="19" class="heading_white"><a href="LoginAction.do?method=loginAction"  class="heading_white">Home</a>|<a  href="javascript:void(0)"  onclick="viewDetails()" class="heading_white">About us</a>|<a  href="javascript:void(0)"  onclick="viewContactUsDetails()" class="heading_white"> Contact Us</a></td>
									            <td width="15%" class="heading_white">Welcome:&nbsp;<%=session.getAttribute("username")%></td>
									            <td width="12%" class="heading_white"></td>
									            <td width="3%" class="heading_white"><a href="javascript:void(0)"  onclick="viewHelp()" class="heading_white">Help</a></td>
									            <td width="3%" class="heading_white"><div align="center"><a href="LogoutAction.do?method=logout"><img src="images/signout_icon.png" width="16" height="16" border="0"></a></div></td>
									            <td width="6%" class="heading_white"><a href="LogoutAction.do?method=logout"  class="heading_white">Sign Out</a></td>
											</tr>
											</c:otherwise>
											</c:choose>
								        </table>
								      </div>
									</td>
									
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td width="19%" valign="top">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tbody>
											<tr>
												<td height="209" valign="top">&nbsp;</td>
											</tr>
										</tbody>
									</table>
									</td>
									<td width="0%">&nbsp;</td>
									<td valign="top" width="58%">
									<div align="center">
									<table border="0" cellpadding="3" cellspacing="4" width="94%">
										<tbody>
											<tr>
												<td height="40" class="heading">
												<div align="center"><bean:message key="knowledgepro.welcome"/> </div>
												</td>
											</tr>
											<tr>
												<td valign="top">
												<table align="center" bgcolor="#ffffff" border="0"
													cellpadding="0" cellspacing="0" width="100%">
													<tbody>
														<tr>
															<td></td>
														</tr>
														<tr>
															<td>
															<table align="center" bgcolor="#ffffff" border="0"
																cellpadding="0" cellspacing="0" width="100%">
																<tbody>
																	<tr>
																		<td width="57%" height="198" valign="top">
																		<table width="36%" border="0" align="center"
																			cellpadding="0" cellspacing="0">
																			<tr>
																				<td width="11"><img
																					src="images/Tright_03_01.gif" width="9" height="29"></td>
																				<td width="327" background="images/Tcenter.gif"
																					class="heading_white">
																				<div align="left">Select View</div>
																				</td>
																				<td width="93"><img
																					src="images/Tright_1_01.gif" width="9" height="29"></td>
																			</tr>
																			<tr>
																			<td background="images/Tright_03_03.gif"></td>
																			<td valign="top">

																			<table width="300" border="0" cellpadding="0"
																				cellspacing="0">
																				<tr>

																					<td height="10" colspan="3">
																					
																					</td>
																				</tr><!--
																				<tr>
																					<td height="47">
																					
																					</td>
																					<td width="100">
																					<div align="center" class="heading">
																					<html:button property="" styleClass="formbutton" value="       Normal View      " onclick="homePage()"></html:button>
																					</div>
																					</td>
																				</tr>
																				--><tr bgcolor="#FFFFFF">
																					<td height="36">
																					
																					</td>

																					<td class="body">
																					<div align="right" class="heading">
																					
																					<html:button property="" styleClass="formbutton" value=" Manual  Attendance Entry    " onclick="attendancePage('Normal')"></html:button>
																					
																					</div>
																					</td>
																				</tr>
																				<tr bgcolor="#FFFFFF">
																					<td height="36">
																					</td>
																					<td class="body">
																					<div align="right" class="heading">
																					<html:button property="" styleClass="formbutton" value="  Automatic Attendance Entry    " onclick="attendancePage('timeTable')"></html:button>
																					</div>
																					</td>
																				</tr>
																				
																				<tr>
																					<td width="30%" height="35">
																					</td>
																					<td width="40%" align="right"> </td>
																					 <% String path= request.getContextPath(); %>
																					<td width="40%" align="center" class="heading"> </td>
																				</tr>
																			</table>

																			</td>
																			<td width="93" valign="top"
																				background="images/Tright_3_3.gif"></td>
																			</tr>
																			<tr>
																				<td height="1" colspan="3"
																					background="images/Tcenter.gif"></td>
																			</tr>
																		</table>
																		</td>
																	</tr>
																</tbody>
															</table>
															</td>
														</tr>

													</tbody>
												</table>
												</td>
											</tr>
										</tbody>
									</table>
									</div>
									</td>
									<td width="1%" valign="top" class="verLine">&nbsp;</td>
									<td width="22%" height="421" valign="top">
									<table border="0" cellpadding="0" cellspacing="0" width="100%">
										<tbody>
											<tr>
												<td height="209" valign="top">
												<TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0>
													<TR>
														<TD width="246" class="heading_orange">News &amp;
														Events

														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="273" align="right" valign="top"
																	bgcolor="#FFFFFF">
																<table width="100%" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<td align="right" valign="top">
																		<TABLE WIDTH=100% BORDER=0 CELLPADDING=0 CELLSPACING=0>

																			<TR>

																				<TD valign="top" bgcolor="#FFFFFF" align="justify">
																				<table width="100%" class="tableheight"
																					height="100%" border="0" cellpadding="0"
																					cellspacing="0">

																					<logic:notEmpty name="loginform"
																						property="description">
																						<c:set var="temp">
																							<bean:write name="loginform"
																								property="description" />
																						</c:set>
																					</logic:notEmpty>
																					<div id="test">
																					<marquee behavior="scroll" direction="up"
																						scrollamount="1" width="200" height="421"
																						style="padding: top 10px;"
																						onmouseover="this.setAttribute('scrollamount', 0, 0);"
																						onmouseout="this.setAttribute('scrollamount', 1, 0);" class="news">
																					<c:out
																						value='${loginform.description}' escapeXml='false' />
																					
																					</marquee>
																					</div>
																					<%-- 
															              <tr>
															                <td height="12%" valign="top" ><script type="text/javascript">
															                	//var iframesrc="../jsp/helpFiles/News_content.html"
																				document.write('<iframe id="datamain" src="html/News_content.html"  width="220" height="100" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no" ></iframe>')
															                  </script></td>
															              </tr>
															              --%>
																				</table>
																				</TD>
																				<TD>&nbsp;</TD>
																			</TR>
																			<TR>
																				<TD>&nbsp;</TD>
																				<TD>&nbsp;</TD>
																				<TD>&nbsp;</TD>
																			</TR>
																		</TABLE>
																		</td>
																	</tr>
																</table>
																</td>
															</tr>
														</table>

														</TD>
													</TR>
													<TR>
														<TD>&nbsp;</TD>
														<TD valign="top" bgcolor="#FFFFFF">
														<table width="100%" class="tableheight" height="100%"
															border="0" cellpadding="0" cellspacing="0">
															<tr>
																<td height="12%" valign="top"><script
																	type="text/javascript">
	
</script></td>
															</tr>
														</table>
														</TD>
														<TD>&nbsp;</TD>
													</TR>
													<TR>
														<TD>&nbsp;</TD>
														<TD>&nbsp;</TD>
														<TD>&nbsp;</TD>
													</TR>
												</TABLE>
												</td>
											</tr>
										</tbody>
									</table>
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
					<tr>
						<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tbody>
								<tr bgcolor="#ffffff">
									<td align="center" bgcolor="#e1e1e1">
									<center></center>
									</td>
								</tr>
								<tr bgcolor="#ffffff">
									<td height="18" align="left" valign="middle" bgcolor="#efefef">
									<div align="left" class="news"><bean:message key="knowledgepro.admin.copyrights"/> </div>
									</td>
								</tr>
							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
			</table>
			</td>
			<td width="9" background="images/rightShadow.gif">&nbsp;</td>
		</tr>
		<tr>
			<td></td>
			<td height="7" valign="baseline"
				background="images/shadow_bottom.gif"></td>
			<td></td>
		</tr>
	</table>
</html:form>
<SCRIPT language="JavaScript">
var browserName=navigator.appName; 
 if (browserName=="Microsoft Internet Explorer")
 {
	 alert(" If Your using InternetExplorer Please Change the Settings : Go to Tools -> Internet Options -> Privacy -> Advanced ->  Check the Override automatic cookie handling  or Use Mozilla FireFox");
 }
 function homePage(){
	 document.getElementById("loginType").value="Normal";
	 document.getElementById("method").value="loginAction";
	 document.loginform.submit();
 }
 function attendancePage(view){
	 if(!(view=='timeTable')){
	 document.getElementById("loginType").value="AttendanceView";
	 }else{
	 document.getElementById("loginType").value="AutomaticAttendanceView";

		 }
	 document.getElementById("method").value="attendanceLoginAction";
	 document.loginform.submit();
 }
</SCRIPT>