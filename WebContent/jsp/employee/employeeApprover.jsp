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
function loginPage(){
	document.location.href = "LoginAction.do?method=loginAction";
}
function saveDetails(){
	document.getElementById("method").value="saveDetails";
}
function selectAll(obj) {
    var value = obj.checked;
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox') {
                  inputObj.checked = value;
                  inputObj.value="on";
            }
    }
}

 

function unCheckSelectAll() {
    var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxOthersSelectedCount = 0;
    var checkBoxOthersCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
          inputObj = inputs[count1];
          var type = inputObj.getAttribute("type");
            if (type == 'checkbox' && inputObj.id != "checkAll") {
                  checkBoxOthersCount++;
                  if(inputObj.checked) {
                        checkBoxOthersSelectedCount++;
                        inputObj.value="on";
                  }else{
                	  inputObj.value="off";	
                      }   
            }
    }
    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
      document.getElementById("checkAll").checked = false;
    } else {
      document.getElementById("checkAll").checked = true;
    }
}
</SCRIPT>

<html:form action="/employeeApprover">
	<html:hidden property="method" styleId="method" value="getEmployeeDetails"/>
	<html:hidden property="formName" value="employeeApproverForm" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.employee" /> <span class="Bredcrumbs">&gt;&gt;
			HOD&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> HOD</strong></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="122" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" cellspacing="1" cellpadding="2">
					<tr>
							<td colspan="6" align="left">
							<div align="right"><span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields" /></span></div>
							<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
							</td>
						</tr>
					<tr>
					<td colspan="4" class="heading" align="left"></td>
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
											 		<td height="25" class="row-odd" width="25%"><div align="right"><bean:message key="knowledgepro.employee.Department"/>:</div></td>
											        <td height="25" class="row-even">
											        	<html:select property="departmentId" styleId="departmentId">
														  <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
															<logic:notEmpty property="departmentMap" name="employeeApproverForm">
															<html:optionsCollection property="departmentMap" label="value" value="key"/>
														 </logic:notEmpty>
													    </html:select>
											        </td>						
										       </tr>
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
							<td>
									<div align="center">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr>
											<td height="35" align="right" width="40%">
												<html:submit  value="Submit" styleClass="formbutton"  property=""></html:submit>
											</td>
											<td height="35" align="left" width="60%">
												&nbsp;&nbsp;&nbsp;<html:button  value="Cancel" styleClass="formbutton" onclick="loginPage()" property=""></html:button>
											</td>
								          </tr>
								          <tr height="50"></tr>
							          	<logic:notEmpty property="employeeToList" name="employeeApproverForm">
								          <tr>
								          	<td colspan="2">
								          		<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
																			<td height="25" class="row-odd">
																			<div align="center"><bean:message key="knowledgepro.slno" /></div>
																			</td>
																			<td align="center" height="25" class="row-odd">
																			<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> select
																			</td>
																			<td align="center" height="25" class="row-odd">
																			<div align="center">Employee Name</div>
																			</td>
																			<td align="center" height="25" class="row-odd">
																			<div align="center">Department</div>
																			</td>
																			<td class="row-odd">
																			<div align="center">Approver Details</div>
																			</td>
																		</tr>
																		
																			
																			<nested:iterate id="to" property="employeeToList" name="employeeApproverForm" indexId="count">
																				<c:choose>
																					<c:when test="${count%2 == 0}">
																						<tr class="row-even">
																					</c:when>
																					<c:otherwise>
																						<tr class="row-white">
																					</c:otherwise>
																				</c:choose>
																				<td width="8%" height="25">
																				<div align="center"><c:out value="${count + 1}" /></div>
																				</td>
																				<td width="10%" height="25">
																				<div align="center">
																				<nested:checkbox property="checked1" onclick="unCheckSelectAll()"> </nested:checkbox>
																				</div>
																				</td>
																				<td align="center" width="30%" height="25"><nested:write
																					 property="firstName" /></td>
																				<td align="center" width="20%" height="25"><nested:write
																					 property="departmentName" /></td>
																				<td align="center" width="30%" height="25"><nested:write
																					 property="approverName" /></td>
																			</nested:iterate>
																		
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
								          		</table>
								          	</td>
								          </tr>
								          <tr height="50"></tr>
								          <tr>
								          	<td height="25" class="row-odd" width="25%"><div align="right">Leave Approver:</div></td>
											 <td height="25" class="row-even">
											   <html:select property="approverId" styleClass="comboLarge" styleId="approverId">
													<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
													<html:optionsCollection name="approversMap" label="value" value="key" />
												</html:select>
											  </td>	       	      	
								          </tr>
								          <tr>
											<td height="35" align="right">
												<html:submit  value="Submit" styleClass="formbutton"  property="" onclick="saveDetails()"></html:submit>
											</td>
								          </tr>
								          </logic:notEmpty>
										</table>
									</div>
							</td>
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

