<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script>
function goToFirstPage(method) {
	document.location.href = "GrievanceStudent.do?method="+method;
}
function unCheckReject(id1,id2) {
	if(document.getElementById(id1).checked){
		document.getElementById(id2).checked=false;
	}
	if(document.getElementById(id2).checked){
		document.getElementById(id1).checked=false;
	}
}
function unCheckApprove(id1,id2) {
	document.getElementById(id1).checked=false;
	if(document.getElementById(id1).checked){
		document.getElementById(id2).checked=false;
	}
	if(document.getElementById(id2).checked){
		document.getElementById(id1).checked=false;
	}
}



	
</script>

<html:form action="/GrievanceStudent">
	<html:hidden property="formName" value="grievanceStudentForm" />
	<html:hidden property="method" styleId="method" value="saveRemarks" />
	
	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><span class="Bredcrumbs">Exam	>> Grievance Student Result&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white">Grievance Student Result</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>

					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					<div align="right" class="mandatoryfield">*Mandatory fields</div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
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
									<td width="15%" height="25" class="row-odd">
									<div align="right">Grievance No :</div>
									</td>

									<td width="45%" height="25" class="row-even" ><bean:write
										property="grievanceNo" name="grievanceStudentForm"></bean:write></td>
									
									<td width="15%" class="row-odd">
									<div align="right">Register No :</div>
									</td>
									<td width="25%" class="row-even"><bean:write
										property="registerNo" name="grievanceStudentForm"></bean:write></td>
										
								</tr>
								<tr>
									<td  height="25" class="row-odd">
									<div align="right">Name :</div>
									</td>
									<td  height="25" class="row-even"><bean:write
										property="studentName" name="grievanceStudentForm"></bean:write></td>
									<td  class="row-odd">
									<div align="right">Semester :</div>
									</td>
									<td  class="row-even"><bean:write
										property="semester" name="grievanceStudentForm"></bean:write></td>

								</tr>
							<%-- 	<tr>
									<td height="25" class="row-odd">
									<div align="right">Course Code:</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="courseCode" name="grievanceStudentForm"></bean:write></td>
									<td height="25" class="row-odd">
									<div align="right">Course Title :</div>
									</td>
									<td height="25" class="row-even"><bean:write
										property="courseTitle" name="grievanceStudentForm"></bean:write></td>
								</tr>--%>
								
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
            <logic:notEmpty property="studentresultList" name="grievanceStudentForm">
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td class="heading">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top" height="25">
							<table width="100%" cellspacing="1" cellpadding="0">
								<tr height="25">

									<td class="row-odd" height="25">
									<div align="center">Sl.No</div>
									</td>
									<td class="row-odd" height="25">Course Code</td>
									<td  class="row-odd" height="25">Course Title.</td>
									
									<td class="row-odd" height="25">ESE Marks</td>
									<td class="row-odd" height="25">CIA Marks</td>
									<td class="row-odd" height="25">Grievance</td>
									<td class="row-odd" height="25">Remark</td>
									<td align="center" height="25" class="row-odd">
													Approve
																			</td>
									<td align="center" height="25" class="row-odd">
													 Reject
																			</td>
									
									

								</tr>
								
								
								<nested:iterate id="resultlist" property="studentresultList" name="grievanceStudentForm" indexId="count">
								 <%
			                  	     String adynaid="approveCheck_1"+count;
				                     String rdynaid="rejectCheck_1"+count;
				                     String checkUncheck="unCheckReject('" + adynaid + "','" + rdynaid + "')";
				                     String checkUncheck1="unCheckApprove('" + adynaid + "','" + rdynaid + "')";
			                     %>
								<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25" width="10%">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
							<td width="13%" height="39" >&nbsp;<bean:write name="resultlist" property="couresCode" /></td>
                            <td width="19%" >&nbsp;<bean:write property="courseTitle" name="resultlist"/></td>
                            <td width="8%" >&nbsp;<bean:write property="eseAwardedMarks" name="resultlist"/></td>
                            <td width="12%">&nbsp;<bean:write property="ciaAwardedMarks"  name="resultlist" /></td>
                            <td width="12%">&nbsp;<bean:write property="remark"  name="resultlist" /></td>
                           <td> <nested:text
								property="grievanceRemark"  styleClass="TextBox" 
								 ></nested:text></td>
								 <td width="10%" height="25">
														<div align="center" >
																<nested:checkbox property="approveCheck" onclick="<%=checkUncheck%>" styleId="<%=adynaid %>"> </nested:checkbox>
														</div>
								</td>
											<td width="10%" height="25">
														<div align="center" >
																<nested:checkbox property="rejectCheck" onclick="<%=checkUncheck1 %>" styleId="<%=rdynaid %>"> </nested:checkbox>
														</div>
											</td>
											</tr>
							</nested:iterate>
					
							</table>
							</td>
							<td width="5" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>

					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
	</logic:notEmpty>

				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
										<input type="button" class="formbutton"
													value="Cancel" onclick="goToFirstPage('getCandidates')" />	
								</td>
							<td width="2%"></td>
							<td width="46%">&nbsp;</td>
						</tr>						
						<tr><td style="height: 10px" align="center" colspan="5"></td></tr>						
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
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
</html:form>
