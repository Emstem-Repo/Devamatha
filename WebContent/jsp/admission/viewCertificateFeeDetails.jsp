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
	
		
	

</script>
<html:form action="/StudentCertificateCourse" >
	<html:hidden property="method" styleId="method" value="saveCertificateCourse"/>
	<html:hidden property="formName" value="studentCertificateCourseForm"/>
	<html:hidden property="pageType" value="1"/>
	
	<table width="500" border="0" align="center" cellpadding="0" cellspacing="0">
	 
	  <tr>
	    
	    <td width="37"><p>&nbsp;</p>
	    <p>&nbsp;</p>
	    <p>&nbsp;</p></td>
	    <td colspan="3" valign="top">
	    <table width="100%" border="0">
			<tr>
				<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9"
							height="29"></td>
						<td background="images/Tcenter.gif" class="body"><strong
							class="boxheader">Fees Details</strong></td>
	
						<td width="10"><img src="images/Tright_1_01.gif" width="9"
							height="29"></td>
					</tr>
					<tr height="10%">
						<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" height="10%">
						
						<FONT color="black" size="2px">
						 <html:messages id="msg"
							property="messages" message="true">
							<c:out value="${msg}" escapeXml="false"></c:out>
							<br>
						</html:messages> </FONT>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					
					
					<tr>
						<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
										<td>
											<div id="mandatory">
												<table width="99%" border="0" align="center" cellpadding="1" cellspacing="1" >
													<logic:notEmpty name="teacherList" >
														<tr>
														<td  class="studentrow-odd" >Teacher</td>
															<td  class="studentrow-odd" >Start Time											
															</td>
															<td  class="studentrow-odd">End Time
														
															</td>
															<td class="studentrow-odd" >Venue														
															</td>
															<td class="studentrow-odd" >Fees														
															</td>
														</tr>
														<nested:iterate name="teacherList" id="teacherList" indexId="count">
																	<tr class="studentrow-even">
															<td>
																<nested:write
																	name="teacherList" property="teacherName" />
														
															</td>
															<td  height="25" >
																<nested:write
																	name="teacherList" property="startTime" />
														
															</td>
															<td>
																<nested:write
																	name="teacherList" property="endTime" />
														
															</td>
															<td >
																<nested:write
																	name="teacherList" property="venue" />
														
															</td>
 															<td >
																<bean:write name="studentCertificateCourseForm" property="feeAmount" />
															</td>
															</tr>
															<tr class="studentrow-even">
																<td > Description:</td>
																<td colspan="4">
																<nested:write name="teacherList" property="description" />
																</td>
															</tr>
														  </nested:iterate>
														 
													</logic:notEmpty>
													
												
													
												</table>
											</div>
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
						<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
						<td width="0" background="images/TcenterD.gif"></td>
						<td><img src="images/Tright_02.gif" width="9" height="29"></td>
					</tr>
				</table>
				</td>
	
			</tr>
		</table>
	  
	</td>
	</tr>
	</table>

</html:form>
