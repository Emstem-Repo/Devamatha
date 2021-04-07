<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<link rel="stylesheet" href="css/calendar.css">
<html:form action="/adminClearanceCertificate" >
	<html:hidden property="method" styleId="method" value="printClearaneceCertificate" />	
	<html:hidden property="pageType" value="1" />
	<html:hidden property="formName" value="clearenceCertificateForm" />
<body class="page">
<div id="container">
<div><img width="750" src="images/waterMark.jpg" /></div>
<div style="position: absolute; left: 10px; top: 10px;"><span style="font-weight: bold;">
<logic:notEmpty name="clearenceCertificateForm" property="studentList">
		<logic:equal value="H" property="hallTicketOrMarksCard" name="clearenceCertificateForm">
		<logic:iterate id="ccto" name="clearenceCertificateForm" property="studentList">
		<table width="100%">
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
									</tr>
									<tr>	
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (University copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left"><b>  
														<bean:write name="ccto" property="name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Hall ticket blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												<logic:notEmpty name="ccto" property="comments">
													<logic:iterate id="to" name="ccto" property="comments" indexId="count">
														<tr  height="90"> 
															<td width="50%"> 
																<b><c:out value="${count + 1}" /></b>
																<b><bean:write name="to"/></b>
															</td>
															<td width="50%" align="center"> 
																
															</td>
														</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr height="25">
										<td></td>
									</tr>
									<tr>
										<td><img width="100%" src="images/dot.gif"></td>
									</tr>
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
										</tr>
									<tr>
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (Student copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left"><b>  
														<bean:write name="ccto" property="className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Hall ticket blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												
												
												<logic:notEmpty name="ccto" property="comments">
													<tr height="100">
														<td>
															<logic:iterate id="to" name="ccto" property="comments" indexId="count">
																		<b><c:out value="${count + 1}" /></b>
																		<b><bean:write name="to"/><br/></b>
															</logic:iterate>
														</td>
														<td valign="bottom">
															<b>Received Seal & Signature with date and time</b>
														</td>
													</tr>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<ul>
											    <li><b>After getting the clearance from the concerned Offices/Departments, submit the university copy at <strong> IPM, Central Block (Between 8.30 am to 5.00 pm)</strong></b></li>
				                                <li><b>Hall ticket will be released within 30 minutes after submission.</b></li>
			                                </ul>
										</td>									
									</tr>									
								</table>
		</logic:iterate>
		</logic:equal>
		<logic:equal value="M" property="hallTicketOrMarksCard" name="clearenceCertificateForm">
		<logic:iterate id="ccto" name="clearenceCertificateForm" property="studentList">
		<table width="100%">
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
									</tr>
									<tr>	
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (University copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left"><b>  
														<bean:write name="ccto" property="name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Marks Card blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												<logic:notEmpty name="ccto" property="comments">
													<logic:iterate id="to" name="ccto" property="comments" indexId="count">
														<tr  height="90"> 
															<td width="50%"> 
																<b><c:out value="${count + 1}" /></b>
																<b><bean:write name="to"/></b>
															</td>
															<td width="50%" align="center"> 
																
															</td>
														</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr height="25">
										<td></td>
									</tr>
									<tr>
										<td><img width="100%" src="images/dot.gif"></td>
									</tr>
									<tr>
										<td align="center" width="100%">
										 	<h2>CHRIST UNIVERSITY, BANGALORE</h2>
										</td>
										</tr>
									<tr>
										<td align="center" width="100%">
										 	<h4>CLEARANCE CERTIFICATE (Student copy)</h4>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%">
												<tr>
													<td align="right"><b>Name:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="name"/></b>
													</td>
													<td align="right"><b>Register No:</b></td>
													<td align="left">  <b>
														<bean:write name="ccto" property="registerNo"/></b>
													</td>
													<td align="right"><b>Class Name:</b></td>
													<td align="left"><b>  
														<bean:write name="ccto" property="className"/></b>
													</td>
												</tr>
											</table>
										</td>
										</tr>
									<tr>
										<td>
											<table width="100%" style="border: 1px solid black; " rules="all">
												<tr> 
													<td width="50%" align="center"> 
														<h4>Marks Card blocked reason</h4>
													</td>
													<td width="50%" align="center"> 
														<h4>Signature & Seal</h4>
													</td>
												</tr>
												
												
												<logic:notEmpty name="ccto" property="comments">
													<tr height="100">
														<td>
															<logic:iterate id="to" name="ccto" property="comments" indexId="count">
																		<b><c:out value="${count + 1}" /></b>
																		<b><bean:write name="to"/><br/></b>
															</logic:iterate>
														</td>
														<td valign="bottom">
															<b>Received Seal & Signature with date and time</b>
														</td>
													</tr>
												</logic:notEmpty>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<ul>
											    <li><b>After getting the clearance from the concerned Offices/Departments, submit the university copy at <strong> IPM, Central Block (Between 8.30 am to 5.00 pm)</strong></b></li>
				                                <li><b>Marks Card will be released within 30 minutes after submission.</b></li>
			                                </ul>
										</td>									
									</tr>									
								</table>
		</logic:iterate>
		</logic:equal>
	</logic:notEmpty>
</span>
</div>
</div>
</body>
</html:form>
<script type="text/javascript">
javascript:window.print();
</script>