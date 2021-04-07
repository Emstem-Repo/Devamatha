<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<%@page import="com.kp.cms.constants.CMSConstants"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Other Administrative Offices Extensions</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
</head>
<body>
<html:form action="/telephoneDictonary" method="POST" >
	<html:hidden property="formName" value="telephoneDirectoryForm" />
	
	<table border="0">
  		<tr>
    		<td>
    		<center><b><font color="blue">Other Administrative Offices Extensions</font></b></center>
    			</td>
  		</tr>
  		<tr>
  			  	<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" background="images/left.gif"></td>
								<td valign="top">
								<table width="100%" >
								<tr>
									<td>
										<c:if test="${telephoneDirectoryForm.extensionNumMap!=null}">
											<c:forEach items="${telephoneDirectoryForm.extensionNumMap}" var="map">
											<tr>
												<td>
												<table>
												<tr>
													<td align="left" class="heading" width="70%">
														<b><font size="2px"><c:out value="${map.key}"></c:out></font></b>
													</td>
													<td align="right" class="heading" width="30%">
														<b><font size="2px">Extension Number</font></b>
													</td>
												</tr>
												<tr>
												<td colspan="2">
													<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
														<tr>
															<td><img src="images/01.gif" width="5" height="5" /></td>
															<td width="914" background="images/02.gif"></td>
															<td><img src="images/03.gif" width="5" height="5" /></td>
														</tr>
														<tr>
															<td width="5" background="images/left.gif"></td>
															<td valign="top">
																<table width="100%" >
																	<c:forEach items="${map.value}" var="map1">
																	<c:choose>
                   														<c:when test="${temp == 0}">
																			<tr >
																				<td height="25" width="90%" align="left" class="row-white" style="font-size: 15px;">
																					<c:out value="${map1.key}"></c:out>
																				</td>
																				<td height="25" width="10%" align="right" class="row-white" style="font-size: 15px;">
																					<c:out value="${map1.value}"></c:out>
																				</td>
																			</tr>
																		<c:set var="temp" value="1"/>
								                   						</c:when>
								                    					<c:otherwise>
							                    							<tr >
																				<td height="25" width=""90%"" align="left" class="row-even" style="font-size: 15px;">
																					<c:out value="${map1.key}"></c:out>
																				</td>
																				<td height="25" width="10%" align="right" class="row-even" style="font-size: 15px;">
																					<c:out value="${map1.value}"></c:out>
																				</td>
																			</tr>
																			<c:set var="temp" value="0"/>
												  						</c:otherwise>
								                  					</c:choose>
																	</c:forEach>
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
												</tr>
												
												</table>
												</td>
											</tr>
											</c:forEach>
										</c:if>
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
					
  		</tr>
  </table>
</html:form>
</body>
</html>	


