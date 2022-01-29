<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
	<script type="text/javascript">
	function saveEvaluators() {
		document.getElementById("method").value="saveSelectedDept";
		document.assignExternalToDepartmentForm.submit();
	}
	function cancel(){
		document.location.href="AssignExternalTodepartment.do?method=initAssignExternalToDepartmentAction";
	}
	function change(obj,id) {
        if(obj) {
            document.getElementById("sl_"+id).style.backgroundColor = "pink";
            document.getElementById("ch_"+id).style.backgroundColor = "pink";
            document.getElementById("ev_"+id).style.backgroundColor = "pink";
            document.getElementById("pn_"+id).style.backgroundColor = "pink";
            document.getElementById("mb_"+id).style.backgroundColor = "pink";
            document.getElementById("dn_"+id).style.backgroundColor = "pink";
        }else {
            if(id % 2 ==0){
		        	document.getElementById("sl_"+id).style.backgroundColor = "#eaeccc";
		            document.getElementById("ch_"+id).style.backgroundColor = "#eaeccc";
		            document.getElementById("ev_"+id).style.backgroundColor = "#eaeccc";
		            document.getElementById("pn_"+id).style.backgroundColor = "#eaeccc";
		            document.getElementById("mb_"+id).style.backgroundColor = "#eaeccc";
		            document.getElementById("dn_"+id).style.backgroundColor = "#eaeccc";
		        }else{
		        	document.getElementById("sl_"+id).style.backgroundColor = "white";
		            document.getElementById("ch_"+id).style.backgroundColor = "white";
		            document.getElementById("ev_"+id).style.backgroundColor = "white";
		            document.getElementById("pn_"+id).style.backgroundColor = "white";
		            document.getElementById("mb_"+id).style.backgroundColor = "white";
		            document.getElementById("dn_"+id).style.backgroundColor = "white";
			        }
            }
		}
	</script>
	
	<html:form action="/AssignExternalTodepartment" method="post" >
	<html:hidden property="formName" value="assignExternalToDepartmentForm"/> 
	<html:hidden property="pageType" value="1"/>
	<html:hidden property="method" styleId="method" value="getExternaDetails" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs">
				<bean:message key="knowledgepro.exam" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.exam.assign.department" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.exam.assign.department" /></strong></td>

					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
						<tr bgcolor="#FFFFFF">
							<td height="20" colspan="2">
							<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"> <html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
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
								<% boolean disable1=false;%>
										<logic:equal value="true" name="assignExternalToDepartmentForm" property="flag">
										<% disable1=true;%>
										</logic:equal>
			
									<td width="25%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span>Department Name</div>
									</td>
									<td width="25%" height="25" class="row-even"><span
										class="star"> <html:select
										property="departmentId" styleClass="comboLarge"
										styleId="departmentId" name="assignExternalToDepartmentForm" disabled='<%=disable1%>'>
										<html:option value="" >
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<logic:notEmpty name="assignExternalToDepartmentForm"
											property="deptList">
										<html:optionsCollection property="deptList"
												name="assignExternalToDepartmentForm" label="deptName" value="deptId" />
										</logic:notEmpty>
										</html:select> </span></td>
								</tr>
									</table>
									</td>
									<td width="5" height="29" background="images/right.gif"></td>
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
					<td height="25" colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="center">
									<html:submit value="Submit" styleClass="formbutton"></html:submit>
							</div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
				<logic:notEmpty property="evlList" name="assignExternalToDepartmentForm">
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
								<td class="row-odd">
												<div align="center"><bean:message key="knowledgepro.slno" /></div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Select Evaluator</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">External Evaluator name</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Pan Number</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Mobile Number</div>
												</td>
												<td align="center" height="25" class="row-odd">
												<div align="center">Department  Name</div> 
												</td>
								</tr>
								<nested:iterate id="to" property="evlList" name="assignExternalToDepartmentForm" indexId="count">
								<tr >
													<c:choose>
														<c:when test="${count%2 == 0}">
															<tr class="row-even">
														</c:when>
														<c:otherwise>
															<tr class="row-white">
														</c:otherwise>
													</c:choose>
													<td width="15%" height="25" id="sl_<c:out value='${count}'/>">
													<div align="center" ><c:out value="${count + 1}" /></div>
													</td>
													<td height="25" width="10%" align="center" id="ch_<c:out value='${count}'/>">
												 		<input type="hidden" name="evlList[<c:out value='${count}'/>].tempChecked1"
																	id="hidden_<c:out value='${count}'/>"
																	value="<nested:write name='to' property='tempChecked1'/>" />
																	<input
																	type="checkbox"
																	name="evlList[<c:out value='${count}'/>].checked"
																	id="<c:out value='${count}'/>" onclick="change(this.checked,'<c:out value='${count}'/>');"/>
																	
													</td>
													
													<td align="left" width="20%" height="25" id="ev_<c:out value='${count}'/>"><nested:write  name="to"
														 property="evaluatorName" /></td>
													<td align="left" width="20%" height="25" id="pn_<c:out value='${count}'/>"><nested:write  name="to"  
														 property="pan" /></td>
													<td align="left" width="20%" height="25" id="mb_<c:out value='${count}'/>"><nested:write  name="to"  
														 property="mobile" /></td>
													<td align="left" width="20%" height="25" id="dn_<c:out value='${count}'/>"><nested:write  name="to"  
														 property="departmentName" />
														 <script type="text/javascript">
																		var deptId = document.getElementById("hidden_<c:out value='${count}'/>").value;
																		if(deptId == "true") {
																			document.getElementById("sl_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("ch_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("ev_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("pn_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("mb_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("dn_<c:out value='${count}'/>").style.backgroundColor = "pink";
																            document.getElementById("<c:out value='${count}'/>").checked = true;
																            
																		}		
																	</script>
														 </td>
														 
									</tr>				 	
									</nested:iterate> 
							</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
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
						<tr >
							<td height="25" >
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr >
										<td width="45%" height="35" align="center">
												<html:button property="" styleClass="formbutton" value="Submit" onclick="saveEvaluators()" ></html:button>
											&nbsp;&nbsp;&nbsp;
												<html:button property="" styleClass="formbutton" value="Close" onclick="cancel()" ></html:button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
			</logic:notEmpty>
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