<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<link rel="stylesheet" href="css/styles.css">
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
	function submitEvent(){
		document.getElementById("method").value = "submitEvent";
		document.empEventVacationForm.submit();
	}
	function resetEvent(){
		document.getElementById("method").value = "initEmpEventVacation";
		document.empEventVacationForm.submit();
	}
	function cancelEvent(){
		document.getElementById("method").value = "initEmpEventVacation";
		document.empEventVacationForm.submit();
	}
	function editDetails(id){
		document.location.href = "empEventVacation.do?method=editEmpEventVacation&id="+id;
	}
	function deleteDetails(id){
		document.location.href = "empEventVacation.do?method=deleteEvent&id="+id;
	}
	function reActivate() {
		document.location.href="empEventVacation.do?method=resetEventType";
	}
	function searchStreamWise(streamId){
		var isTeaching=0;
		if(document.getElementById("teachingStaff_1").checked){
			isTeaching=1;
		}
		getDepartmentByStreamWiseWithTeaching(streamId,isTeaching,updateDepartmentMap);
	}
	function updateDepartmentMap(req){
			updateDepartmentFromMap(req,"department");
	}
	function getdepartment(teaching){
		var streamId=document.getElementById("streamId").value;
		getDepartmentByStreamWiseWithTeaching(streamId,teaching,updateDepartmentMap);
	}
	
</script>
<html:form action="/empEventVacation" method="post">
	<html:hidden property="formName" value="empEventVacationForm" />
	<html:hidden property="method" styleId="method" value="" />
	<html:hidden property="pageType" value="1" />

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.admin.sec.EmployeeCategory" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.employee.exameventvacation" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				
				<tr>
			        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
			        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.employee.exameventvacation"/></td>
			        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
			   </tr>
				
				
				
				
				<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"><FONT color="red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></FONT></div>
					<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
					<FONT color="green"> <html:messages id="msg"
						property="messages" message="true">
						<c:out value="${msg}" escapeXml="false"></c:out>
						<br>
					</html:messages> </FONT></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
								    <div align="right"><span class="Mandatory">*</span>
								      <bean:message key="knowledgepro.employee.isTeachingStaff"/>
								    </div>
								    </td>
									<td  class="row-even">
									 <html:radio property="teachingStaff" styleId="teachingStaff_1" value="1"/> <!-- onclick="getdepartment(this.value)"-->Teaching&nbsp; 
									<html:radio property="teachingStaff" styleId="teachingStaff_2" value="0" /> <!-- onclick="getdepartment(this.value)" -->Non-Teaching&nbsp;
									</td>
                    
					<td class="row-odd" >
					<div align="right"><span class='MandatoryMark'>*</span>Stream: </div>
					</td>
					<td class="row-even" >
					<label>
					<c:choose>
					<c:when test="${eventEdit == 'edit'}">
						<c:choose>
						<c:when test="${stream != '-'}">
								<html:select property="streamId" styleId="streamId" styleClass="checkBox" disabled="true" onchange="searchStreamWise(this.value)">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="empEventVacationForm" property="streamMap">
											<html:optionsCollection property="streamMap" label="value" value="key" />
										</logic:notEmpty>
									</html:select> 
						</c:when>
						<c:otherwise>
							<html:select property="streamId" styleId="streamId" styleClass="checkBox" onchange="searchStreamWise(this.value)">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty name="empEventVacationForm" property="streamMap">
											<html:optionsCollection property="streamMap" label="value" value="key" />
										</logic:notEmpty>
									</html:select> 
						</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
								<html:select property="streamId" styleId="streamId" styleClass="checkBox" disabled="false" onchange="searchStreamWise(this.value)">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									<logic:notEmpty name="empEventVacationForm" property="streamMap" >
									<html:optionsCollection property="streamMap" label="value" value="key" />
								</logic:notEmpty>
							</html:select> 
					</c:otherwise>
					</c:choose>
					
					</label></td>
                </tr>
								<tr>
								<td class="row-odd">
								<div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.employee.type"/>:</div>
								</td>
								<td class="row-even" width="250">
									<html:select property="type" name="empEventVacationForm">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<html:option value="Exam"><bean:message key="knowledgepro.employee.exam"/></html:option>
										<html:option value="Event"><bean:message key="knowledgepro.employee.event"/></html:option>
										<html:option value="Vacation"><bean:message key="knowledgepro.employee.vacation"/></html:option>
									</html:select>
								</td>
								<td class="row-odd" >
								<div id="classsdiv" align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.usermanagement.userinfo.department"/>:</div>
								</td>
								<td  class="row-even" >
								<logic:notEmpty property="deptMap" name="empEventVacationForm">
					                  <html:select  name="empEventVacationForm" styleId="department" property="department" size="5" style="width:300px" multiple="multiple" >
					       		    		<html:optionsCollection name="empEventVacationForm" property="deptMap" label="value" value="key"/>
					                  </html:select>
					              </logic:notEmpty>
					                  </td>
					            
								</tr>
								
								<tr>
									<td class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendance.leavemodify.fromdate"/>:</div></td>
									<td class="row-even">
						                  <table width="82" border="0" cellspacing="0" cellpadding="0">
						                    <tr>
						                      <td width="60">
						                      <html:text name="empEventVacationForm" styleId="fromDate" property="fromDate" styleClass="TextBox"/>
						                      </td>
						                      <td>
						                      <script language="JavaScript">
													new tcal ({
														// form name
														'formname': 'empEventVacationForm',
														// input name
														'controlname': 'fromDate'
													});</script>
											</td>
						                    </tr>
						                  </table></td>
						            <td class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="knowledgepro.attendance.leavemodify.todate"/>:</div></td>
									<td class="row-even" >
						                  <table width="82" border="0" cellspacing="0" cellpadding="0">
						                    <tr>
						                      <td width="60">
						                      <html:text name="empEventVacationForm" styleId="toDate" property="toDate" styleClass="TextBox"/>
						                      </td>
						                      <td>
						                      <script language="JavaScript">
													new tcal ({
														// form name
														'formname': 'empEventVacationForm',
														// input name
														'controlname': 'toDate'
													});</script>
											</td>
						                    </tr>
						                  </table>
						              </td>
								</tr>
								
								<tr>
									<td class="row-odd" ><div align="right"><span class='MandatoryMark'>*</span><bean:message key="employee.info.job.achievement.desc"/>:</div></td>
									<td class="row-even" colspan="3">
										<html:textarea property="description" rows="2" cols="90" ></html:textarea>
									</td>
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
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr height="20">
					</tr>
						<tr>
						
						<td width="48%" height="35">
							<div align="right"><c:choose>
								<c:when test="${eventEdit == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="submitEvent()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="submitEvent()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td align="left">
								<html:button property="" styleId="Reset" styleClass="formbutton" value="Reset" onclick="resetEvent()"></html:button>
								<html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelEvent()"></html:button>
							</td>
						</tr>
						
						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				
				<tr>
					<td height="41" valign="top" background="images/Tright_03_03.gif"></td>

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
									<td align="center" height="25" class="row-odd"><bean:message
										key="knowledgepro.employee.type" /></td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.attendance.leavemodify.fromdate" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.attendance.leavemodify.todate" /></div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.employee.eventvacation.teachingstaff" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:notEmpty name="empEventVacationForm" property="empTo">
									<logic:iterate id="empvacationTo" name="empEventVacationForm"
										property="empTo" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="18%" height="25"><bean:write
											name="empvacationTo" property="type" /></td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empvacationTo"
											property="fromDate" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empvacationTo"
											property="toDate" /></div>
										</td>
										<td align="center" width="14%" height="25">
										<div align="center"><bean:write name="empvacationTo"
											property="teachingStaff" /></div>
										</td>
										<td width="7%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editDetails('<bean:write name="empvacationTo" property="id" />')" /></div>
										</td>
										<td width="10%" height="25" align="center">
										<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteDetails('<bean:write name="empvacationTo" property="id"/>')">
									</div>
									</td>
										
										</tr>
									</logic:iterate>
								</logic:notEmpty>
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
	
</html:form>

