<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-tiles.tld" prefix="tiles" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.utilities.CommonUtil"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@page import="java.util.Date"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Employee Information Submission</title>
<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script language="JavaScript" src="js/admissionform.js"></script>
<script language="JavaScript" src="js/calendar_us.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">

	function closeWindow(){
		document.location.href = "PhdEmployeeApplication.do?method=initPhdEmployeesearch";
	}

	function saveEmpDetails(){
		document.getElementById("method").value="savePhdEmpDetails";
		document.phdEmployeeForms.submit();
	}
	
	function submitPhdEpployee(method,mode){
		document.getElementById("method").value=method;
		document.getElementById("mode").value=mode;
		document.phdEmployeeForms.submit();
	}

	function updatePhdEmployee(){
		  document.getElementById("method").value="updatePhdEmployee";
		  document.phdEmployeeForms.submit();
		  }
	function imposeMaxLength(field,size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
		
	// to display the text areas length 
	function len_display(field,size){
		 if (field.value.length > size) {
		        field.value = field.value.substring(0, size);
		    }
	}
		
</script>
</head>
<body>
<table width="100%" border="0">
<html:form action="/PhdEmployeeApplication" enctype="multipart/form-data">
	<html:hidden property="pageType" value="1" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="phdEmployeeForms" />
	<html:hidden property="mode" styleId="mode" value="" />
	<html:hidden property="focusValue" styleId="focusValue"/>
	<input type="hidden" id="count"/>
       <tr>
			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.phd" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.Phd.info.label" /> &gt;&gt;</span></span></td>
		</tr>
   <tr>
    <td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"/></td>
        <td width="1271" background="images/Tcenter.gif" class="body" >
			<div align="left">
				<strong class="boxheader"><bean:message key="knowledgepro.Phd.info.label"/></strong>
			</div>
		</td>
       <td width="15" >
	   <img src="images/Tright_1_01.gif" width="9" height="29"/>	   </td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
	                 <tr>
	               	    <td height="20" colspan="6" align="left">
	               	    <div align="right" style="color:red"> <span class='MandatoryMark'>* Mandatory fields</span></div>
	               	    <div id="err" style="color:red;font-family:arial;font-size:11px;"></div>
	               	    <div id="errorMessage">
	                       <FONT color="red"><html:errors/></FONT>
	                       <FONT color="green">
								<html:messages id="msg" property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
								</html:messages>
						  </FONT>
						  </div>
	               	    </td>
	                 </tr>
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>
					<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="100" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
						<td width="5" background="images/left.gif"></td>
						<td width="100%" height="22" align="left" valign="top">
						<table width="100%" height="22" border="0" cellpadding="0">
						<tr class="row-white">
						<td width="50%" height="30" align="center" class="row-even"><img
						src='<%=request.getContextPath()%>/PhotoServlet'
						height="150Px" width="150Px" /></td>
						</tr>
						</table>
						</td>
						<td background="images/right.gif" width="5" height="22"></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									 <td class="row-odd" width="25%">
									 <div align="left"><span class="Mandatory">*</span>
									     <bean:message key="knowledgepro.admin.name"/>
									  </div>
									  </td>
										<td  class="row-even" width="25%">
											<html:text property="name" styleId="name" size="35" maxlength="50" style="text-transform:uppercase;"></html:text>
										</td>
										<td class="row-odd" width="25%"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.employee.gender.required" /></div>
							  	</td>
								<td height="17" class="row-even" align="left" width="25%">
									<nested:radio property="gender" value="MALE" name="phdEmployeeForms"><bean:message key="admissionForm.studentinfo.sex.male.text"/></nested:radio>
									<nested:radio property="gender" value="FEMALE" ><bean:message key="admissionForm.studentinfo.sex.female.text"/></nested:radio>
								</td>
							</tr>
							  <tr>
							       	<td class="row-odd" width="25%"> 
									<div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admin.dateofbirth" /></div>
							  	</td>
								  <td class="row-even" width="25%">
									<html:text name="phdEmployeeForms" property="dateOfBirth" styleId="dateOfBirth" size="10" />
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'phdEmployeeForms',
												// input name
												'controlname' :'dateOfBirth'
												});
										</script>
								</td>
								 <td class="row-odd" width="25%">
									 <div align="left">
									     <bean:message key="admissionForm.studentinfo.birthplace.label"/>
									  </div>
									  </td>
										<td  class="row-even" width="25%">
											<html:text property="placeOfBirth" styleId="placeOfBirth" size="35" maxlength="50" style="text-transform:uppercase;"></html:text>
										</td>
							  </tr>
							  <tr> 
							  	 <td class="row-odd" width="25%">
							  	 <div align="left"><span class="Mandatory">*</span>
							      	<bean:message key="knowledgepro.admin.nationality"/>
							     </div>
							     </td>
								 <td  class="row-even" width="25%">
								 	 <html:select property="nationalityId">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="nationalityMap" name="phdEmployeeForms">
								   		<html:optionsCollection property="nationalityMap" label="value" value="key"/>
								   </logic:notEmpty>
							     </html:select> 
								 </td>
								 <td height="20" class="row-odd" width="25%"><div align="left">
						     	<bean:message key="admissionForm.studentinfo.religion.label" /></div></td>
								<td height="25" class="row-even" width="25%">
							    <html:select property="religionId" styleId="religionId">
								<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									<logic:notEmpty property="religionMap" name="phdEmployeeForms">
								  		<html:optionsCollection property="religionMap" label="value" value="key"/>
								   </logic:notEmpty>
							   </html:select> 
				               </td>
							  </tr>
							     <tr> 
							  <td height="25" class="row-odd" width="25%"><div align="left">Blood Group:</div></td>
								<td height="25" class="row-even" width="25%">
							      <nested:select property="bloodGroup" styleId="bloodGroup" styleClass="combo" >
							       <html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
							       <html:option value="O+VE"><bean:message key="knowledgepro.admission.report.opositive" /></html:option>
							       <html:option value="A+VE"><bean:message key="knowledgepro.admission.report.apositive" /></html:option>
							       <html:option value="B+VE"><bean:message key="knowledgepro.admission.report.bpositive" /></html:option>
							       <html:option value="AB+VE"><bean:message key="knowledgepro.admission.report.abpositive" /></html:option>
							       <html:option value="O-VE"><bean:message key="knowledgepro.admission.report.onegitive" /></html:option>
							       <html:option value="A-VE"><bean:message key="knowledgepro.admission.report.anegitive" /></html:option>
							       <html:option value="B-VE"><bean:message key="knowledgepro.admission.report.bnegitive" /></html:option>
							       <html:option value="AB-VE"><bean:message key="knowledgepro.admission.report.abnegitive" /></html:option>
							       <html:option value="NOT KNOWN"><bean:message key="knowledgepro.admission.report.unknown" /></html:option>
							      </nested:select>
							  </td>
							         <td class="row-odd" width="25%">
									 <div align="left"><bean:message key="knowledgepro.Phd.domicile.status"/></div></td>
									 <td  class="row-even" width="25%"><html:text property="domicialStatus" styleId="domicialStatus" size="25" maxlength="50"></html:text>
									</td>
				              </tr>
							
					  <tr>
					  <td class="row-odd" width="25%"><div align="left" ><bean:message key="employee.info.immigration.passportno" /></div></td>
                  		<td class="row-even" width="25%"><span class="star">
						<html:text property="passPortNo" maxlength="20" size="30"/></span></td>
					  	<td class="row-odd" width="25%"><div align="left" ><bean:message key="knowledgepro.employee.panNo" /></div></td>
                  		<td class="row-even" width="25%"><span class="star">
						<html:text property="panNo" maxlength="10" size="20"/></span></td>
					
					</tr>
					<tr>
				 		 <td class="row-odd" width="25%"><div align="left"><bean:message key="admissionFormForm.emailId" /></div>	</td>
						 <td  class="row-even" width="25%"><html:text property="email" maxlength="50" size="40"></html:text></td>
						 	<td class="row-odd" width="25%"><div align="left">
									<bean:message key="knowledgepro.Phd.dateof.award.phd" /></div>
							  	</td>
								  <td class="row-even" width="25%">
									<html:text name="phdEmployeeForms" property="dateOfAward" styleId="dateOfAward" size="10"/>
										<script
											language="JavaScript">
												new tcal( {
												// form name
												'formname' :'phdEmployeeForms',
												// input name
												'controlname' :'dateOfAward'
												});
										</script>
								</td>
				        	</tr>
				           <tr>
									<td class="row-odd" width="25%"> 
									<div align="left">
									<bean:message key="knowledgepro.admin.bankName" /></div>
							  		</td>
									<td class="row-even" width="25%"> 
									<html:text property="bankName" maxlength="50" size="40"></html:text>
									</td>
								
									<td class="row-odd" width="25%"> 
									<div align="left">
									<bean:message key="knowledgepro.employee.bankAccNo" /></div>
							  		</td>
									<td class="row-even" width="25%"> 
									<html:text property="bankAccNo" maxlength="15" onkeypress="return isNumberKey(event)" size="20"></html:text>
									</td>
						 </tr>
		
							<tr>
									<td class="row-odd" width="25%"><div align="left">
									<bean:message key="knowledgepro.hostel.reservation.branchName" /></div>
							  		</td>
									<td class="row-even" width="25%"> 
									<html:text property="bankBranch" maxlength="50"></html:text>
									</td>
										<td class="row-odd" width="25%">
									  	<div align="left">
											<bean:message key="knowledgepro.employee.upload.photo"/>:
											</div>		
											</td> 
											<td class="row-even" width="25%">
												<html:file property="empPhoto"></html:file>
											</td>
								</tr>
								<tr>
									<td class="row-odd" width="25%"><div align="left"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.Phd.subject.guide.ship" /></div>
							  		</td>
							  		 <td  class="row-even" width="25%">
								 	 <html:select property="subjectGuideShip">
								   <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
								   	<logic:notEmpty property="guideShipMap" name="phdEmployeeForms">
								   		<html:optionsCollection property="guideShipMap" label="value" value="key"/>
								   </logic:notEmpty>
							         </html:select> 
								     </td>
									 <td width="25%" height="25" class="row-odd"><div align="left"><span class="Mandatory">*</span><bean:message key="knowledgepro.phd.empanelmentNo"/>:</div></td>
                                       <td width="25%" class="row-even"><div align="left"> <span class="star">
                                        <html:text property="empanelmentNo" styleId="empanelmentNo" size="20" maxlength="16" />
                                        </span></div></td>
								</tr>
								<tr>
								<td width="25%" height="25" class="row-odd"><div align="left"><bean:message key="KnowledgePro.phd.noMphilScolars.guides"/>:</div></td>
                                   <td width="25%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noMphilScolars" styleId="noMphilScolars" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
							  <td width="25%" height="25" class="row-odd"><div align="left"><bean:message key="KnowledgePro.phd.noPhdScolars.guides"/>:</div></td>
                                   <td width="25%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noPhdScolars" styleId="noPhdScolars" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
							</tr>	
							<tr>
						       <td width="25%" height="25" class="row-odd"><div align="left"><bean:message key="KnowledgePro.phd.noPhdScolarOutside.guides"/>:</div></td>
                                   <td width="25%" class="row-even"><div align="left"> <span class="star">
                                   <html:text property="noPhdScolarOutside" styleId="noPhdScolarOutside" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                                   </span></div></td>
                             <td width="25%" height="25" class="row-odd"></td>
                             <td width="25%" class="row-even"></td>
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
					
				 <tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				   </tr>	
			    	<tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employye.educational.details"/>
					</td>
				    </tr>
		           	<tr>
						<td valign="top" class="news" colspan="3">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td><img src="images/01.gif" width="5" height="5" /></td>
								<td width="914" background="images/02.gif"></td>
								<td><img src="images/03.gif" width="5" height="5" /></td>
							</tr>
							<tr>
								<td width="5" height="30" background="images/left.gif"></td>
								<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
									<td class="row-odd" width="10%" height="25">
										<bean:message key="knowledgepro.employee.education.qualification"/>
									</td>
									<td class="row-odd" width="20%" height="25">
										<bean:message key="knowledgepro.phd.degree"/>
									</td>
									<td class="row-odd" width="30%" height="25">
										<bean:message key="knowledgepro.phd.university.name"/>
									</td>
									<td class="row-odd" width="10%" height="25">
										<bean:message key="knowledgepro.admin.state"/>
									</td>
									<td class="row-odd" width="10%" height="25">
										<bean:message key="knowledgepro.admission.totalmarks"/>
									</td>
									<td class="row-odd" width="10%" height="25">
										<bean:message key="knowledgepro.employee.yeat.completion"/>
									</td>
									<td class="row-odd" width="10%" height="25">
										<bean:message key="knowledgepro.phd.attempts.name"/>
									</td>
								</tr>
								<logic:notEmpty property="phdEmployeequalificationFixedTo" name="phdEmployeeForms">
									<nested:iterate id="qualificationTo" property="phdEmployeequalificationFixedTo" name="phdEmployeeForms" indexId="yr">
									<tr>
									<td class="row-odd" width="10%" height="25">
									<div align="left" >
										<bean:write name="qualificationTo"  property="qualification" />
									</div>
									</td>
									<%
										String styleDegree = "degree_" + yr;
									%>
									<td class="row-even" width="20%" >
										<nested:text property="degree" styleId="<%=styleDegree%>" maxlength="100" size="40" ></nested:text>
									</td>
									<% String StyleUniversity="univers_"+yr; %>
									<td class="row-even" width="30%" >
										<nested:text property="nameOfUniversity" styleId="<%=StyleUniversity%>" maxlength="50" size="46" ></nested:text>
									</td>
									<% String Stylestate="state_"+yr; %>
									<td class="row-even" width="10%" >
									   <nested:select property="qstate" styleId="<%=Stylestate%>" styleClass="comboMedium"  >
								         <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									        <logic:notEmpty property="statesMap" name="phdEmployeeForms">
								  		     <html:optionsCollection property="statesMap" label="value" value="key"/>
								            </logic:notEmpty>
							              </nested:select> 
									</td>
									<% String Stylepersent="percent_"+yr; %>
									<td class="row-even" width="10%" >
										<nested:text property="percentage" styleId="<%=Stylepersent%>" maxlength="50"></nested:text>
									</td>
									<% String dynaYearId="YOP"+yr;%>
									<td class="row-even" width="10%" >
										<c:set var="dyopid"><%=dynaYearId %></c:set>
											<nested:select property="yearOfComp"  styleId='<%=dynaYearId %>' styleClass="comboMedium" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
													<cms:renderEmployeeYear normalYear="true"></cms:renderEmployeeYear>
										</nested:select>
										<script type="text/javascript">
											var opid= '<nested:write property="yearOfComp"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
									</td>
									<td class="row-even" width="10%" >
										<nested:text property="attempts" size="10" maxlength="40"></nested:text>
									</td>
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<logic:notEmpty property="phdEmployeequalificationTos" name="phdEmployeeForms">
									<nested:iterate id="levelTo" property="phdEmployeequalificationTos" name="phdEmployeeForms" indexId="yrs">
									<tr>
									<td class="row-odd">
									<div align="left">
									<nested:select property="educationId">
										<html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									  	<logic:notEmpty property="qualificationLevelMap" name="phdEmployeeForms">
									  		<html:optionsCollection property="qualificationLevelMap" label="value" value="key" name="phdEmployeeForms"/>
									   </logic:notEmpty>
									</nested:select>
									</div>
									</td>
									<%
									    String styleDegree = "degreea_"  + yrs;
									%>
									<td class="row-even" width="20%" >
										<nested:text property="degree" styleId="<%=styleDegree%>" maxlength="100" size="40" ></nested:text>
									</td>
									<% String StyleUniversity="universa_"+yrs; %>
									<td class="row-even" width="30%" >
										<nested:text property="nameOfUniversity" styleId="<%=StyleUniversity%>" maxlength="50" size="46" ></nested:text>
									</td>
									<% String Stylestate="statea_"+yrs; %>
									<td class="row-even" width="10%" >
									   <nested:select property="qstate" styleId="<%=Stylestate%>" styleClass="comboMedium"  >
								         <html:option value=""><bean:message key="knowledgepro.select"/></html:option>
									        <logic:notEmpty property="statesMap" name="phdEmployeeForms">
								  		     <html:optionsCollection property="statesMap" label="value" value="key"/>
								            </logic:notEmpty>
							              </nested:select> 
									</td>
									<% String Stylepersent="percenta_"+yrs; %>
									<td class="row-even" width="10%" >
										<nested:text property="percentage" styleId="<%=Stylepersent%>" maxlength="50"></nested:text>
									</td>
								  <% String dynaYearId="YOPa"+yrs;%>
									<td class="row-even" width="10%" >
										<c:set var="dyopid"><%=dynaYearId %></c:set>
											<nested:select property="yearOfComp"  styleId='<%=dynaYearId %>' styleClass="comboMedium" >
												<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
													<cms:renderEmployeeYear normalYear="true"></cms:renderEmployeeYear>
										</nested:select>
										<script type="text/javascript">
											var opid= '<nested:write property="yearOfComp"/>';
											if(opid!=0)
											document.getElementById("<c:out value='${dyopid}'/>").value = opid;
										</script>
									</td>
									<td class="row-even" width="10%" >
										<nested:text property="attempts" size="10" maxlength="40"></nested:text>
									</td>
								</tr>
								</nested:iterate>
								</logic:notEmpty>
								
								<tr>
			                        <td  class="row-even" align="center" colspan="8">
			                         <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitPhdEpployee('addQualificationLevel','ExpAddMore'); return false;"></html:submit>
									 <c:if test="${phdEmployeeForms.levelSize>=1}">
			                         	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitPhdEpployee('removeQualificationLevel','ExpAddMore'); return false;"></html:submit>
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
		
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	
	             <tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.employee.teaching.experience"/>
					</td>
					</tr>
					
						<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="40%" >
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.admission.instituteName"/>
									      	</div>
									     </td>
										<td class="row-odd" width="30%" height="25" align="left"> 
											<bean:message key="knowledgepro.phd.university.name" />
									  		</td>
										
										<td class="row-odd" width="20%" align="left"> 
											<bean:message key="knowledgepro.admin.selectedSubjects"/>
										</td>
										<td class="row-odd" width="10%" align="left"> 
											<bean:message key="knowledgepro.phd.Year.of.Experience"/>
										</td>
									</tr>
									<html:hidden property="teachingExpLength" name="phdEmployeeForms" styleId="teachingExpLength"/>
									<logic:notEmpty property="teachingExperience" name="phdEmployeeForms">          
									<nested:iterate property="teachingExperience" name="phdEmployeeForms" id="experience" indexId="count">
									<tr>
								   <%String tnameofInstitute="tnameofinstitute_"+String.valueOf(count);%>
									     <td class="row-even" align="left" width="40%" >
								              <nested:text property="tNameOfInstitution" styleClass="TextBox" size="50" maxlength="50" styleId="<%=tnameofInstitute%>"></nested:text>
										</td>
										<%String nameofUniversity="tnameuniversity_"+String.valueOf(count);%>
										<td class="row-even" align="left" width="30%" >
								               <nested:text property="tNameOfUniversity" styleClass="TextBox" size="50" maxlength="50" styleId="<%=nameofUniversity%>"></nested:text>
										</td>
										<%String tSubject="tsubject_"+String.valueOf(count);%>
										<td class="row-even" align="left" width="20%" >
								               <nested:text property="tSubject" styleClass="TextBox" size="30" maxlength="50" styleId="<%=tSubject%>"></nested:text>
										</td>
										<%String tYearofexp="tyears_"+String.valueOf(count);%>
										<td class="row-even" align="left" width="10%" >
								               <nested:text property="tYearsOfExpe" styleClass="TextBox" size="10" maxlength="50" styleId="<%=tYearofexp%>"></nested:text>
										</td>
									</tr>
			                         </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitPhdEpployee('resetTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
										 <c:if test="${phdEmployeeForms.teachingExpLength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitPhdEpployee('removeTeachingExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        </c:if>
										</td> 
									</tr>
									</logic:notEmpty>
															
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
					
					
					
					
					
				<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	


	             <tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.phd.research.experience"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="40%" >
									  	 	<div align="left">
									      	<bean:message key="knowledgepro.admission.instituteName"/>
									      	</div>
									     </td>
										<td class="row-odd" width="30%" height="25" align="left"> 
											<bean:message key="knowledgepro.phd.university.name" />
									  		</td>
										
										<td class="row-odd" width="20%" align="left"> 
											<bean:message key="knowledgepro.admin.selectedSubjects"/>
										</td>
										<td class="row-odd" width="10%" align="left"> 
											<bean:message key="knowledgepro.phd.Year.of.Experience"/>
										</td>
									</tr>
									<html:hidden property="researchlength" name="phdEmployeeForms" styleId="researchlength"/>
									<logic:notEmpty property="researchExperience" name="phdEmployeeForms">          
									<nested:iterate property="researchExperience" name="phdEmployeeForms" id="experience" indexId="count">
									<tr>
									<%String rnameofInstitute="rnameofinstitute_"+String.valueOf(count);%>
									<td class="row-even" align="left">
								              <nested:text property="rNameOfInstitution" styleClass="TextBox" size="50" maxlength="50" styleId="<%=rnameofInstitute%>"></nested:text>
										</td>
										<%String rNameOfTheUniversity="rNameOfTheUniversity_"+String.valueOf(count);%>
										<td class="row-even" align="left">
								               <nested:text property="rNameOfTheUniversity" styleClass="TextBox" size="50" maxlength="50" styleId="<%=rNameOfTheUniversity%>"></nested:text>
										</td>
										<%String rSubject="rSubject_"+String.valueOf(count);%>
										<td class="row-even" align="left">
								               <nested:text property="rSubject" styleClass="TextBox" size="30" maxlength="50" styleId="<%=rSubject%>"></nested:text>
										</td>
										<%String rYearOfExper="rYearOfExper_"+String.valueOf(count);%>
										<td class="row-even" align="left">
								               <nested:text property="rYearOfExper" styleClass="TextBox" size="10" maxlength="50" styleId="<%=rYearOfExper%>"></nested:text>
										</td>
									</tr>
			                         </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitPhdEpployee('resetResearchExperienceInfo','ExpAddMore'); return false;"></html:submit>
										 <c:if test="${phdEmployeeForms.researchlength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitPhdEpployee('removeResearchExperienceInfo','ExpAddMore'); return false;"></html:submit>
				                        </c:if>
										</td> 
									</tr>
									</logic:notEmpty>
															
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
					
					
					
					
					
					
				<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	


	             <tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.phd.research.publication"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="50%" height="25" align="left"> 
											<bean:message key="knowledgepro.phd.name.title" />
									  		</td>
										
										<td class="row-odd" width="30%" align="left"> 
											<bean:message key="knowledgepro.phd.prefeed.journal"/>
										</td>
										<td class="row-odd" width="10%" align="left" colspan="2"> 
											<bean:message key="knowledgepro.phd.Year"/>
										</td>
										<td class="row-odd" width="10%" align="left"></td>
									</tr>
									<html:hidden property="publicationLength" name="phdEmployeeForms" styleId="publicationLength"/>
									<logic:notEmpty property="publicationExperience" name="phdEmployeeForms">          
									<nested:iterate property="publicationExperience" name="phdEmployeeForms" id="experience" indexId="count">
									<tr>
									<%String pNameOfTitles="pNameOfTitles_"+String.valueOf(count);%>
										<td class="row-even" align="left">
								               <nested:text property="pNameOfTitles" styleClass="TextBox" size="50" maxlength="50" styleId="<%=pNameOfTitles%>"></nested:text>
										</td>
										<%String pJournalPubli="pJournalPubli_"+String.valueOf(count);%>
										<td class="row-even" align="left">
								               <nested:text property="pJournalPubli" styleClass="TextBox" size="30" maxlength="50" styleId="<%=pJournalPubli%>"></nested:text>
										</td>
										<%String pyear="pyear_"+String.valueOf(count);%>
										<td class="row-even" align="left" colspan="2">
								               <nested:text property="pyear" styleClass="TextBox" size="20" maxlength="20" styleId="<%=pyear%>"></nested:text>
										</td>
										<td class="row-even" align="left"></td>
									</tr>
			                         </nested:iterate>
			                         <tr>
			                         	<td  class="row-even" align="center" colspan="7">
			                           <html:submit property="" styleClass="formbutton" value="Add more"  onclick="submitPhdEpployee('resetResearchpublication','ExpAddMore'); return false;"></html:submit>
										 <c:if test="${phdEmployeeForms.publicationLength>0}">
				                        	<html:submit property="" styleClass="formbutton" value="Remove"  onclick="submitPhdEpployee('removeResearchpublication','ExpAddMore'); return false;"></html:submit>
				                        </c:if>
										</td> 
									</tr>
						 <tr>
					     <td class="row-odd" width="25%"><div align="left" ><bean:message key="employee.phd.Noof.research.articles" /></div></td>
                  		<td class="row-even" width="25%"><span class="star">
						<html:text property="noOfresearch" maxlength="10"/></span></td>
					  	<td class="row-odd" width="25%"><div align="left" ><bean:message key="knowledgepro.phd.noof.book.authored" /></div></td>
                  		<td class="row-even" width="25%"><span class="star">
						<html:text property="noOfBookAuthored" maxlength="10"/></span></td>
					
					    </tr>
					 				</logic:notEmpty>
									
									
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
					
					
					
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				</tr>	
	             <tr>
					<td colspan="2" class="heading" align="left">
						<bean:message key="knowledgepro.phd.present.employeement"/>
					</td>
					</tr>
					
					<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="25%" height="25" align="left">
											<bean:message key="knowledgepro.phd.name.address" />
									  		</td>
										
										<td class="row-odd" width="30%" align="left">
											<bean:message key="knowledgepro.employee.Department"/>
										</td>
										<td class="row-odd" width="30%" align="left">
											<bean:message key="knowledgepro.employee.designation"/>
										</td>
										<td class="row-odd" width="15%" align="left">
											<bean:message key="knowledgepro.phd.Year.of.Experience"/>
										</td>
									</tr>
									<tr>
										<td class="row-even" align="left">
								               <nested:textarea property="nameAddress" styleClass="TextBox" cols="50" rows="6"  onkeypress="return imposeMaxLength(this,149);" onchange="len_display(this,149)"></nested:textarea>
										</td>
						                  <td class="row-even" align="left">
								               <nested:text property="departmentId" styleClass="TextBox" size="40" maxlength="50" ></nested:text>
										</td>
										<td class="row-even" align="left">
								               <nested:text property="desiginitionId" styleClass="TextBox" size="40" maxlength="50" ></nested:text>
										</td>
										<td class="row-even" align="left">
								               <nested:text property="yearOfExp" styleClass="TextBox" size="10" maxlength="10" ></nested:text>
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
					
					
					
					<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				   </tr>	
	                <tr>
					<td colspan="4" class="heading" align="left">
						<bean:message key="knowledgepro.phd.address.communication"/>
					</td>
					</tr>
					<tr>
						<td valign="top" class="news" colspan="3">
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
										<td class="row-odd" width="25%" height="25" align="left"> 
											<bean:message key="admissionForm.studentinfo.permAddr.label" />
									  		</td>
									     <td class="row-odd" width="25%" height="25" align="left"></td>
										<td class="row-odd" width="25%" align="left" >
											<bean:message key="knowledgepro.phd.present.address"/>
										</td>
									  	 <td class="row-odd" width="25%" height="25" align="left"></td>
									</tr>
									<tr>
										<td class="row-even" align="left" colspan="2">
								               <nested:textarea property="permanentAddress" styleClass="TextBox" cols="50" rows="5" onkeypress="return imposeMaxLength(this,498);" onchange="len_display(this,498)"></nested:textarea>
										</td>
										<td class="row-even" align="left" colspan="2">
								               <nested:textarea property="contactAddress" styleClass="TextBox" cols="50" rows="5"  onkeypress="return imposeMaxLength(this,498);" onchange="len_display(this,498)"></nested:textarea>
										</td>
									</tr>
									 <tr>
								     <td class="row-odd">
									 <div align="left">
									     <bean:message key="knowledgepro.employee.EmContact.mobile"/>
									  </div>
									  </td>
										<td class="row-even" >
											<html:text property="pAddressPhonNo" styleId="pAddressPhonNo" size="35" maxlength="10" onkeypress="return isNumberKey(event)"></html:text>
										</td>
										
									<td class="row-odd" >
									 <div align="left">
									     <bean:message key="knowledgepro.employee.EmContact.mobile"/>
									  </div>
									  </td>
										<td class="row-even" >
											<html:text property="cAddressPhonNo" styleId="cAddressPhonNo" size="35" maxlength="10" onkeypress="return isNumberKey(event)"></html:text>
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
               		<tr>
								<td background="images/05.gif"></td>
							</tr>
						</table>
						</td>
					</tr>
          		<tr>
					<td height="19" valign="top" background="images/separater.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					</table>
					</td>
					<td height="19" valign="top" background="images/separater.gif"></td>
				
                  </tr>			
                  <tr>
                  </tr>		
                  <tr>
							<td align="center" colspan="6"> 
							<c:choose>
            	         	<c:when test="${phdEmployee == 'edit'}">
              	   		    <html:button property="" styleClass="formbutton" value="Update" onclick="updatePhdEmployee()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>
              		        </c:when>
              		        <c:otherwise>
                		    <html:button property="" styleClass="formbutton" value="Submit" onclick="saveEmpDetails()"></html:button>&nbsp;&nbsp;
							<html:button property="" styleClass="formbutton" value="Close" onclick="closeWindow()"></html:button>&nbsp;&nbsp;	
              		         </c:otherwise>
                        	</c:choose>
							</td>
                </tr>
                
	             <tr>
				<td height="19" valign="top" background="images/separater.gif"></td>
					</tr>		
				<tr>
			        <td><img src="images/Tright_03_05.gif" width="9" height="29"/></td>
			        <td width="100%" background="images/TcenterD.gif"></td>
			        <td><img src="images/Tright_02.gif" width="9" height="29"/></td>
			        </tr>
			        
			        
	</table>		 
	</html:form>
			
			</table>
			
			</body>
			<script type="text/javascript">

			
			var focusField=document.getElementById("focusValue").value;
		    if(focusField != 'null'){  
			    if(document.getElementById(focusField)!=null)      
		            document.getElementById(focusField).focus();
			}
			
			var sameAddr= document.getElementById("sameAddr").checked;

			if(sameAddr==true){
				disableAddress();
			}
			if(sameAddr==false){
				enableAddress();
			}
		</script>
	</html>
			
		
			
	


