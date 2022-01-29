<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
<SCRIPT type="text/javascript">
function cancelAction(){
	window.close();
}
 function getDetails(){
	 document.getElementById("method").value = "viewPreviousMonthAttendance";
	 document.viewMyAttendanceForm.submit();
 }
</SCRIPT>

<html:form action="/empViewMyAttendance">
	<html:hidden property="formName" value="viewMyAttendanceForm" />
	<html:hidden property="method" styleId="method" value="viewPreviousMonthAttendance"/>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.employee.attendance" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.employee.attendance" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
					<td colspan="4" class="heading" align="left">&nbsp;<bean:message key="knowledgepro.employee.previous.month.attendance.details"></bean:message></td>
					</tr>
					<tr>
					<td colspan="4" align="left" ><div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
					</tr>
					
					 <tr>
                    <td height="35" colspan="6" class="body" >
			        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                     <tr>
	                       <td ><img src="images/01.gif" width="5" height="5" /></td>
	                       <td width="914" background="images/02.gif"></td>
	                       <td><img src="images/03.gif" width="5" height="5" /></td>
	                     </tr>
	                     <tr>
	                       <td width="5"  background="images/left.gif"></td>
	                       <td valign="top">
	                       <table width="100%" cellspacing="1" cellpadding="2">
					        	<tr>
			                       <td class="row-odd" width="25%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.academicyear"/>:</div></td>
				        		   <td class="row-even" align="left" width="25%">
				                           <input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="viewMyAttendanceForm" property="year"/>"/>
				                           <html:select property="year" styleId="academicYear" name="viewMyAttendanceForm" styleClass="combo" >
		                       	   				 <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
		                       	   				<cms:renderEmpAttendanceYear normalYear="true"></cms:renderEmpAttendanceYear>
		                       			   </html:select>
				        			</td>
				                  <td class="row-odd" width="25%">
			                 		 <div id="classsdiv" align="right"><span class='MandatoryMark'>*</span>Month</div>
					               </td>
					               <td class="row-even" width="25%">
					                  <html:select  name="viewMyAttendanceForm" styleId="months" property="months">
					                  <html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
					                  <html:option value="JANUARY">JANUARY</html:option>   
					                  <html:option value="FEBRUARY">FEBRUARY</html:option> 
					                  <html:option value="MARCH">MARCH</html:option> 
					                  <html:option value="APRIL">APRIL</html:option> 
					                  <html:option value="MAY">MAY</html:option> 
					                  <html:option value="JUNE">JUNE</html:option> 
					                  <html:option value="JULY">JULY</html:option> 
					                  <html:option value="AUGUST">AUGUST</html:option> 
					                  <html:option value="SEPTEMBER">SEPTEMBER</html:option> 
					                  <html:option value="OCTOBER">OCTOBER</html:option> 
					                  <html:option value="NOVEMBER">NOVEMBER</html:option> 
					                  <html:option value="DECEMBER">DECEMBER</html:option>  
					                  </html:select>
				                  </td>
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
                   </table>
                   </td>
                 </tr>
					<tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr>
                       <td width="45%"><div align="center">
                       <html:submit styleClass="formbutton" value="Submit" onclick="getDetails()"></html:submit>
                       </div></td>
                    <%--    <td width="2%"></td>
                       <td width="53%" height="45" align="left">
                   	 		<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
                       </td>--%>
                     </tr>
                   </table>
                   </td>
                </tr>
					
					<tr>
					
							<td valign="top" class="news">
							
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="1310" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								 
								<tr>
									<td width="5" background="images/left.gif"></td>
									
									<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" align="center" width="13%">
													<bean:message key="knowledgepro.employee.manualAttendanceEntry.attendanceDate" /></td>
													<td class="row-odd" align="center" width="13%"><bean:message
														key="knowledgepro.employee.manualAttendanceEntry.inTime" /></td>

													<td class="row-odd" align="center" width="13%"><bean:message
														key="knowledgepro.employee.manualAttendanceEntry.outTime" /></td>
												</tr>
												<logic:notEmpty name="viewMyAttendanceForm" property="empPreviousAttTo">
												<c:set var="temp" value="0" />
												<logic:iterate name="viewMyAttendanceForm"
													property="empPreviousAttTo" id="empList" >
													<c:choose>
												<c:when test="${temp == 0}">
															<tr>
																<td height="25" class="row-even" align="center" width="13%">
																<bean:write name="empList" property="attendanceDate" /></td>
																<td class="row-even" align="center" width="13%"><bean:write
																	name="empList" property="inTime" /></td>
																<td class="row-even" align="center" width="13%"><bean:write
																	name="empList" property="outTime" /></td>
															<c:set var="temp" value="1" /></tr>
												</c:when>
												<c:otherwise>
													<tr>
																<td height="25" class="row-white" align="center" width="13%">
																<bean:write name="empList" property="attendanceDate" /></td>
																<td class="row-white" align="center" width="13%"><bean:write
																	name="empList" property="inTime" /></td>
																<td class="row-white" align="center" width="13%"><bean:write
																	name="empList" property="outTime" /></td>
															<c:set var="temp" value="0" /></tr>
															</c:otherwise>
															</c:choose>
												</logic:iterate>
												</logic:notEmpty>
											</table>
											</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif" ></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						
						
						<tr>
							<td valign="top" class="news">
							
								<div align="center">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
            <td width="49%" height="35" align="center">
          			<html:button  value="Close" styleClass="formbutton" onclick="cancelAction()" property=""></html:button>
				
				</td>
          </tr>

					</table>
					</div>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>

					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td height="20" valign="top"></td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29" /></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
