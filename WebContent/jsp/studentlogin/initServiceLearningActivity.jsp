<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script type="text/javascript">
function getPrograme(deptname) {
	getProgramesByDepartmentId("programeMap", deptname, "programes", updateProgrammes);
}
function updateProgrammes(req) {
	updateOptionsFromMap(req, "programes", "- Select -");
}
function goToHomePage() {
	document.location.href = "StudentLoginAction.do?method=returnHomePage";
}

</script>
<html:form action="/StudentLoginAction">	

	<html:hidden property="formName" value="loginform" />
	<html:hidden property="method" styleId="method" value="submitServiceLearningActivity" />
	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/>
			<span class="Bredcrumbs">&gt;&gt;
		Service Learning Activity
			 &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">Service Learning Activity</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
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
								<td width="16%" height="25" class="studentrow-odd">
							<div align="right"><span class="Mandatory">*</span>Programme:</div>
							</td>
							<td width="16%" height="25" class="row-even"> <label>
							<html:select property="programId" styleId="programId" onchange="getPrograme(this.value)">
											<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
												<logic:notEmpty property="programmeEntryToList" name="loginform">
											  		<html:optionsCollection property="programmeEntryToList" label="programmeName" value="id" />
											   </logic:notEmpty>
										 </html:select> 
						
							 </label> <span class="star"></span>
							</td>
							<td width="16%" height="25" class="studentrow-odd" style="display:none;";>
							<div align="right"><span class="Mandatory">*</span>Department/Club: </div>
							</td>
							<td width="16%" height="25" class="row-even" style="display:none;";> <label>
							<nested:select property="departmentId" styleClass="body" styleId="programes" style="width:45%;height:15">
									<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
										<logic:notEmpty property="programeMap" name="loginform">
											<html:optionsCollection name="loginform" property="programeMap" label="value" value="key" />
										</logic:notEmpty>
									</nested:select>
						
							 </label> <span class="star"></span>
							</td>
							</tr>
							<tr>
							<td width="32%" height="25" class="studentrow-odd">
							<div align="right"><span class="Mandatory">*</span>Learning And Outcome :</div>
							</td>
							<td width="16%" height="25" class="row-even" colspan="3"> <label>
							<html:textarea property="learningAndOutcome" styleId="learningAndOutcome" style="width: 90%" cols="40" rows="4" onkeypress="return imposeMaxLength(event,this)"></html:textarea> 
							 </label> <span class="star"></span>
							</td>
							</tr>
							
							<tr>
									
									
									

     <td width="50%" height="25" align="right" class="studentrow-odd" colspan="2"><div align="right">
     <span class="Mandatory">*</span>&nbsp;Hours(24 Hour Format) :
     </div></td>
     <td width="50%" class="row-even" align="left" height="25" colspan="2"><div align="left">
       <html:text name="loginform" property="startHours" styleClass="Timings" styleId="smtime" size="2"
        maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/> :
       <html:text name="loginform" property="startMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" 
       onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
     </div></td>
     
                                     
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
						<tr>
							<td width="45%" height="35">
							<div align="right">
							<c:choose>
							<c:when test="${department == 'edit'}">
									<html:submit property="" styleClass="btnbg" value="Update"
										styleId="submitbutton">
									</html:submit>
							</c:when>
							<c:otherwise>
								<html:submit property="" styleClass="btnbg" value="Submit"
										styleId="submitbutton">
									</html:submit>
							</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<html:button property=""  styleClass="btnbg" value="Close" onclick="goToHomePage()"></html:button>
							</td>
						</tr>
					</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>	
				<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">

										<tr>
											<td width="7%" height="25" class="studentrow-odd">
											<div align="center"><bean:message key="knowledgepro.slno" /></div>
											</td>
											<td width="15%" class="studentrow-odd">
											<div align="center">Department Name</div>
											</td>
											<td width="15%" class="studentrow-odd">
											<div align="center">Programme Name</div>
											</td>
											<td width="15%" class="studentrow-odd">
											<div align="center">Learning And Outcome</div>
											</td>
											<td width="10%" class="studentrow-odd">
											<div align="center">Attended Hours</div>
											</td>
											
											
										</tr>
										
										<logic:notEmpty property="serviceLearningActivityToList" name="loginform"> 
						   		<logic:iterate id="cList" property="serviceLearningActivityToList" name="loginform"
											 indexId="count">
											<tr>
											<c:choose>
												<c:when test="${count%2 == 0}">
													<tr class="row-even">
												</c:when>
												<c:otherwise>
													<tr class="row-white">
												</c:otherwise>
											</c:choose>
											<td height="25">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
												<td align="center"><bean:write name="cList"
												property="departmentNameEntryTo.departmentName" /></td>
												<td align="center"><bean:write name="cList"
												property="programmeEntryTo.programmeName" /></td>
												<td align="center"><bean:write name="cList"
												property="learningAndActivity" /></td>
												<td align="center"><bean:write name="cList"
												property="attendedHours" /></td>
												
											
											</tr>
										</logic:iterate>
										</logic:notEmpty> 
										
									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>			
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>

		</tr>
	</table>
</html:form>
