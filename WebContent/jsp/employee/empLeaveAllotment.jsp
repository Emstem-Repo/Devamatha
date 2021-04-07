<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<link href="styles.css" rel="stylesheet" type="text/css"/>
<SCRIPT type="text/javascript">
	function reActivate(){
		
		document.location.href = "empLeaveAllotment.do?method=reactivateLeaveAllotment";
	}
	function clearMessages(){
		document.getElementById("code").value='';
		resetErrMsgs();
	}
	function shows(obj,msg){
		
		document.getElementById("messageBox").style.top=obj.offsetTop;
		document.getElementById("messageBox").style.left=obj.offsetLeft+obj.offsetWidth+5;
		document.getElementById("contents").innerHTML=msg;
		document.getElementById("messageBox").style.display="block";
		}
	function hides(){
		document.getElementById("messageBox").style.display="none";
	}
	function shows1(obj,msg){
		document.getElementById("messageBox1").style.top=obj.offsetTop;
		document.getElementById("messageBox1").style.left=obj.offsetLeft+obj.offsetWidth+5;
		document.getElementById("contents1").innerHTML=msg;
		document.getElementById("messageBox1").style.display="block";
		}
	function hides1(){
		document.getElementById("messageBox1").style.display="none";
	}
	function editLeaveAllot(id){
		document.location.href = "empLeaveAllotment.do?method=editLeaveAllotment&id="+id;
	}
	function deleteLeaveAllot(id){
		document.location.href = "empLeaveAllotment.do?method=deleteLeaveAllotment&id="+id;
	}
	function cancelAction(){
		document.location.href = "empLeaveAllotment.do?method=initEmpLeaveAllotment";
	}
	function resetFormFields(){
		document.getElementById("allottedLeave").value="";
		document.getElementById("required1").checked=false;
		document.getElementById("required2").checked=false;
		document.getElementById("noOfAccumulatedLeave").value="";
		var destination = document.getElementById("empType");
		for (x1=destination.options.length-1; x1>=0; x1--) {
			destination.options[x1].selected = false;
		}
		var destination1 = document.getElementById("leaveType");
		for (x1=destination1.options.length-1; x1>=0; x1--) {
			destination1.options[x1].selected=false;
		}
		if (document.getElementById("method").value == "updateLeaveAllotment") {
			var absence=document.getElementById("origInitRequired").value;
			if(absence == "Yes") {
	            document.getElementById("required1").checked = true;
			}	
			if(absence == "No") {
	      	  document.getElementById("required2").checked = true;
			}
			document.getElementById("allottedLeave").value = document.getElementById("origAllottedLeave").value;
			document.getElementById("empType").value = document.getElementById("origEmpType").value;
			document.getElementById("leaveType").value = document.getElementById("origLeaveType").value;
			document.getElementById("noOfAccumulatedLeave").value = document.getElementById("origAccumulatedLeave").value;
		}
		resetErrMsgs();	
	}
</SCRIPT>

<html:form action="/empLeaveAllotment">
	<html:hidden property="formName" value="empLeaveAllotmentForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="origEmpType"	styleId="origEmpType" name="empLeaveAllotmentForm"/>
	<html:hidden property="origLeaveType" styleId="origLeaveType" name="empLeaveAllotmentForm"/>
	<html:hidden property="origAllottedLeave"	styleId="origAllottedLeave" name="empLeaveAllotmentForm"/>
	<html:hidden property="origInitRequired"	styleId="origInitRequired" name="empLeaveAllotmentForm"/>
	<html:hidden property="origAccumulatedLeave"	styleId="origAccumulatedLeave" name="empLeaveAllotmentForm"/>
	<c:choose>
		<c:when test="${Operation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateLeaveAllotment" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addLeaveAllotment" />
		</c:otherwise>
	</c:choose>
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.employee.leave.allotment" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.employee.leave.allotment" /></strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="4">
							<div align="right"><FONT color="red"> <span
								class='MandatoryMark'><bean:message
								key="knowledgepro.mandatoryfields" /></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
							</html:messages> </FONT></div>
							</td>
							
						</tr>
						<tr>
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
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span> <bean:message
												key="knowledgepro.employee.leave.allotment.empType" /></div>
											</td>
											<td height="25" class="row-even" width="25%"><span class="star">
											<html:select name="empLeaveAllotmentForm" property="empType" styleId="empType"  style="width: 150px;" styleClass="combo">
											<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>						
						<html:optionsCollection name="empLeaveAllotmentForm" property="empTypeMap" label="value" value="key" />
					</html:select> </span><span
												class="star"></span></td>
											<td class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.employee.leave.allotment.leaveType" /></div>
											</td>
											<td class="row-even" width="25%"><span class="star"> <html:select name="empLeaveAllotmentForm" property="leaveType" styleId="leaveType"  style="width: 150px;" styleClass="combo">	
											<html:option value="">
													<bean:message key="knowledgepro.admin.select" />
												</html:option>					
						<html:optionsCollection name="empLeaveAllotmentForm" property="empLeaveMap" label="value" value="key" />
					</html:select>  </span></td>
										</tr>
										<tr>
											<td height="25" class="row-odd" width="25%">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.employee.leave.allotment.allottedLeave" /> :</div>
											</td>
											<td class="row-even" width="25%"><html:text name="empLeaveAllotmentForm"
												property="allottedLeave" styleId="allottedLeave"
												styleClass="TextBox" size="16" maxlength="50" /></td>

											<td width="20%" class="row-odd" width="25%" onmouseover="shows(this,'If initialization required is yes the leaves will be initialize to allotted leave if no the balace leave will be added to the next year allotted leave')" onmouseout="hides()">
											<div align="right"><span class="Mandatory">*</span><bean:message
												key="knowledgepro.employee.leave.allotment.initRequired" />
											:</div>
											</td>
											<td width="28%" class="row-even" width="25%"><html:radio property="initializeRequired"
												styleId="required1" value="Yes"  onmouseover="shows(this,'If initialization required is yes the leaves will be initialize to allotted leave if no the balace leave will be added to the next year allotted leave')" onmouseout="hides()"/> <bean:message
												key="knowledgepro.yes" /><html:radio property="initializeRequired"
												value="No" styleId="required2"  onmouseover="shows(this,'If initialization required is yes the leaves will be initialize to allotted leave if no the balace leave will be added to the next year allotted leave')" onmouseout="hides()"/> <bean:message
												key="knowledgepro.no" />
												 <div id="messageBox">
	                              <div id="contents"></div>
	                              </div>
												</td>
										</tr>
										<tr>
                                           <td height="25" class="row-odd" width="25%" onmouseover="shows1(this,'The leave count specified here will be added to the accumulated leave type')" onmouseout="hides1()">
											<div align="right"><bean:message
												key="knowledgepro.employee.leave.allotment.accumulated.leave" /> :</div>
	            
											</td>
											
											<td class="row-even" width="25%"><html:text name="empLeaveAllotmentForm"
												property="noOfAccumulatedLeave" styleId="noOfAccumulatedLeave"
												styleClass="TextBox" size="16" maxlength="50" onmouseover="shows1(this,'The leave count specified here will be added to the accumulated leave type')" onmouseout="hides1()"/>
											
	                              <div id="messageBox1">
	                              <div id="contents1"></div>
	                              </div>
												</td>
											<td height="25" class="row-odd" width="25%">  </td>
                                   <td height="25" class="row-even" width="25%"></td>	
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
							<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
						</tr>
						<tr>
							<td height="25" colspan="4">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="45%" height="35">
									<div align="right"><c:choose>
										<c:when test="${Operation == 'edit'}">
											<html:submit property="" styleClass="formbutton"
												value="Update" styleId="submitbutton">
											</html:submit>
											
										</c:when>
										<c:otherwise>
											<html:submit property="" styleClass="formbutton"
												value="Submit" styleId="submitbutton">
											</html:submit>
										</c:otherwise>
									</c:choose></div>
									</td>
									<td width="2%"><html:button property="" styleClass="formbutton" value="Reset" onclick="resetFormFields()"></html:button> </td>

									<td width="53%">
                                       <input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
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
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" colspan="4">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>
													<td height="25" class="row-odd" width="7%">
													<div align="center"><bean:message
														key="knowledgepro.admin.subject.subject.s1no" /></div>
													</td>
													<td height="25" class="row-odd" align="center" width="18%"><bean:message
														key="knowledgepro.employee.leave.allotment.empType" /></td>
													<td class="row-odd" align="center" width="20%"><bean:message
														key="knowledgepro.employee.leave.allotment.leaveType" /></td>

													<td class="row-odd" align="center" width="18%"><bean:message
														key="knowledgepro.employee.leave.allotment.allottedLeave" /></td>


													<td class="row-odd" align="center" width="18%"><bean:message
														key="knowledgepro.employee.leave.allotment.initRequired" />
													</td>
													<td class="row-odd" width="12%">
													<div align="center"><bean:message
														key="knowledgepro.edit" /></div>
													</td>
													<td class="row-odd" width="7%">
													<div align="center"><bean:message
														key="knowledgepro.delete" /></div>
													</td>
												</tr>
												<logic:notEmpty name="empLeaveAllotmentForm" property="leaveAllotTOList">
												<logic:iterate name="empLeaveAllotmentForm"
													property="leaveAllotTOList" id="leaveList" indexId="count">
													
															<tr>
																<td height="25" class="row-even" width="7%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td height="25" class="row-even" align="center" width="18%"><bean:write
																	name="leaveList" property="empType" /></td>
																<td class="row-even" align="center" width="20%"><bean:write
																	name="leaveList" property="leaveType" /></td>
																<td class="row-even" align="center" width="18%"><bean:write
																	name="leaveList" property="allottedLeave" /></td>
																<td class="row-even" align="center" width="18%"><bean:write
																	name="leaveList" property="initRequired" /></td>
																<td height="25" class="row-even">
																<div align="center"><img
																	src="images/edit_icon.gif" width="12%" height="18"
																	style="cursor: pointer"
																	onclick="editLeaveAllot('<bean:write name="leaveList" property="id" />')" /></div>
																</td>
																<td width="7%" height="25" class="row-even">
																<div align="center"><img
																	src="images/delete_icon.gif" width="16%" height="16"
																	style="cursor: pointer"
																	onclick="deleteLeaveAllot('<bean:write name="leaveList" property="id" />')" /></div>
																</td>
															</tr>
												</logic:iterate>
												</logic:notEmpty>
											</table>
											</td>
										</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
								</tr>

								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
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
