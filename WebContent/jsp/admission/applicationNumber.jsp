<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested" %>

<script type="text/javascript">
	function updateApplicationNo() {
		resetErrMsgs();
		document.getElementById("method").value = "updateApplicationNumber";
		document.applicationNumberForm.submit();
	}

	function addApplicationNo() {
		document.getElementById("method").value = "addApplicationNumber";
		document.applicationNumberForm.submit();
	}
	function loadApplicationNo(id) {
		document.location.href = "ApplicationNumber.do?method=loadApplicationNumberForEdit&id="
				+ id;
	}
	function deleteApplicationNo(id) {
		deleteConfirm = confirm("Are you sure you want to delete this entry?")
		if (deleteConfirm == true) {
			document.location.href = "ApplicationNumber.do?method=deleteApplicationNumber&id="
					+ id;
		}
	}
	function isNumberKey(evt) {
		
		var charCode = (evt.which) ? evt.which : event.keyCode
				if((evt.ctrlKey || evt.keyCode == 86)  ){
					
					return true;
				}
		
		<%--if(charCode == 17 || charCode == 86){
			
			return true;
		}--%>
			
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			
			return false;
		
		return true;
	}
	function checkNumber(field){
		if(isNaN(field.value)){
			field.value = "";
		}
	}

	function resetApplnumber() {
		document.getElementById("onlineAppNoFrom").value = "";
		document.getElementById("onlineAppNoTill").value = "";
		document.getElementById("offlineAppNoFrom").value = "";
		document.getElementById("offlineAppNoTill").value = "";
		resetErrMsgs();
		document.getElementById("year").value = resetYear();
	}
	function getClasses(year) {
		document.getElementById("year").value = year;
		document.getElementById("method").value = "setCourseEntry";
		document.applicationNumberForm.submit();
	}

	function getPrograms(programTypeId) {
		
		getProgramsByType("programMap",programTypeId,"selectedProgram",updatePrograms);
	}

	function updatePrograms(req) {
		
		updateOptionsFromMap(req,"selectedProgram","- Select -");
	}

	function getCourses(programId) {
		var program =  document.getElementById("selectedProgram");
		  var selectedArray = new Array();	  
		  var i;
		  var count = 0;
		  for (i=0; i<program.options.length; i++) {
		    if (program.options[i].selected) {
		      selectedArray[count] = program.options[i].value;
		      count++;
		    }
		  }
		  getCoursesByMultiplePrograms("coursesMap",selectedArray,"selectedCourse",updateCourses);
	}
	function updateCourses(req) {
		updateOptionsFromMapForMultiSelect(req,"selectedCourse");
	}
</script>

<html:form action="/ApplicationNumber" method="post">
	<c:choose>
		<c:when test="${applicationNoOperation == 'edit'}">
			<html:hidden property="method" styleId="method"
				value="updateApplicationNumber" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method"
				value="addApplicationNumber" />
		</c:otherwise>
	</c:choose>
	<html:hidden property="formName" value="applicationNumberForm" />
	<html:hidden property="courseName" styleId="courseName" value="" />
	
	<html:hidden property="id" styleId="id" />
	
	<html:hidden property="pageType" value="1" />
	<table width="98%" border="0">
		<tr>

			<td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission" />
			<span class="Bredcrumbs">&gt;&gt; <bean:message
				key="knowledgepro.admission.application.entry" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader"> <bean:message
						key="knowledgepro.admission.application.entry" /></strong></div>
					</td>

					<td width="13"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<div align="right"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
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
					<td height="70" valign="top" background="images/Tright_03_03.gif"></td>

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
								  <td class="row-odd" valign="top" width="20%">
									
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
									
									 <td width="30%" height="25" class="row-even">
									<input type="hidden" id="tempyear"
										name="tempyear"
										value="<bean:write name="applicationNumberForm" property="year"/>" /><html:select
										name="applicationNumberForm" property="year" styleId="year"
										styleClass="combo" onchange="getClasses(this.value)">
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
									</html:select>
																
									<td class="row-odd" valign="top" width="20%">
									<div align="right"><span class="Mandatory">*</span>
									<bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                   					 <td height="25" class="row-even" >
                   					<html:select property="programTypeId"  styleId="programtype" styleClass="comboMedium" onchange="getPrograms(this.value)">
                 					<html:option value=""><bean:message key="knowledgepro.admin.select"/> </html:option>
				    				<html:optionsCollection name="applicationNumberForm" property="programTypeList" label="programTypeName" value="programTypeId"/>
	     							</html:select> 
               						 </td>
									
									</tr>
									<tr>
									
									<td class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message
										key="knowledgepro.admin.program" /></div>
									</td>
									<td  class="row-even" valign="top"> 
									<html:select property="selectedProgram" styleId="selectedProgram" styleClass="body" multiple="multiple" size="15" onchange="getCourses(this.value)" style="width:350px">
																		
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
        			    				<c:if test="${applicationNumberForm.programTypeId != null && applicationNumberForm.programTypeId != ''}">
            		    	 			 <logic:notEmpty name="applicationNumberForm" property="programMap">
            		    	 						<html:optionsCollection name="applicationNumberForm" property="programMap" label="value" value="key" styleClass="comboBig"/>
            		    	 			</logic:notEmpty>		
            		   					</c:if>
										
									</html:select>
									</td>
									<td class="row-odd" valign="top">
									<div align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.course.with.col"/></div>
									</td>
									<td  class="row-even" valign="top">                    
									<nested:select property="selectedCourse" styleClass="body" multiple="multiple" size="15" styleId="selectedCourse" style="width:350px">
										<logic:notEmpty name="applicationNumberForm" property="courseMap">
											<nested:optionsCollection name="applicationNumberForm" property="courseMap" label="value" value="key" styleClass="comboBig"/>
										</logic:notEmpty>
									</nested:select>
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
							<table width="100%" height="27" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="20%" height="25">
									<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admission.online.app.no"/></div>
									</td>

									<td width="33%" height="25" class="row-even"><span
										class="bodytext"><html:text
										property="onlineAppNoFrom" styleClass="TextBox"
										styleId="onlineAppNoFrom" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)"  onblur="checkNumber(this)"/> <bean:message
										key="knowledgepro.admission.app.no.till" /> <html:text
										property="onlineAppNoTill" styleClass="TextBox"
										styleId="onlineAppNoTill" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)" onblur="checkNumber(this)"/> </span>
										<c:if test="${applicationNoOperation == 'edit'}">
										current Online Appln No:
										<html:text
										property="currentOnlineApplnNo" styleClass="TextBox"
										styleId="currentOnlineApplnNo" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)"  onblur="checkNumber(this)"/>
										</c:if>
										</td>
									</tr>
									<tr class="row-odd">
									<td width="22%">
									<div align="right"><bean:message
										key="knowledgepro.admission.offline.app.no" /></div>
									</td>
									<td width="33%" class="row-even"><span class="bodytext">
									<html:text
										property="offlineAppNoFrom" styleClass="TextBox"
										styleId="offlineAppNoFrom" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)" onblur="checkNumber(this)" /> </span><bean:message
										key="knowledgepro.admission.app.no.till" /><span
										class="bodytext"> <html:text
										property="offlineAppNoTill" styleClass="TextBox"
										styleId="offlineAppNoTill" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)"  onblur="checkNumber(this)"/></span>
										<c:if test="${applicationNoOperation == 'edit'}">
										current Offline Appln No:
										<html:text
										property="currentOfflineApplnNo" styleClass="TextBox"
										styleId="currentOfflineApplnNo" size="16" maxlength="8"
										onkeypress="return isNumberKey(event)"  onblur="checkNumber(this)"/>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="35" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>

							<td width="45%" height="35">
							<div align="right"><c:choose>
								<c:when test="${applicationNoOperation == 'edit'}">
									<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateApplicationNo()"></html:button>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addApplicationNo()"></html:button>
								</c:otherwise>
							</c:choose></div>
							</td>
							<td width="2%"></td>
							<td width="53%"><c:choose>
								<c:when test="${applicationNoOperation == 'edit'}">
									<html:cancel value="Reset" styleClass="formbutton"></html:cancel>
								</c:when>
								<c:otherwise>
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetApplnumber()"></html:button>
								</c:otherwise>
							</c:choose></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>


				<tr>
					<td height="97" valign="top" background="images/Tright_03_03.gif"></td>
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
							<table width="100%" height="86" border="0" cellpadding="0"
								cellspacing="1">
								<tr class="row-odd">
									<td width="64" class="bodytext">
									<div align="center"><bean:message key="knowledgepro.slno" /></div>
									</td>
									<td  align="center" class="bodytext"><bean:message
										key="knowledgepro.admin.year" /></td>
									
									<td height="25" class="row-odd">
									<div align="center">Course</div>
									</td>
									<td  align="center" width="163" height="25" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.admission.online.appno" /></div>
									</td>
									<td  align="center" width="129" class="bodytext">
									<div align="center"><bean:message
										key="knowledgepro.admission.application" /></div>
									</td>
									<td>
									<div align="center"><bean:message key="knowledgepro.edit" /></div>
									</td>
									<td>
									<div align="center"><bean:message
										key="knowledgepro.delete" /></div>
									</td>
								</tr>
								<logic:iterate id="applicationNoList" name="applicationNoList"
									indexId="count">
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-even">
										</c:when>
										<c:otherwise>
											<tr class="row-white">
										</c:otherwise>
									</c:choose>
									<td class="bodytext">
									<div align="center"><c:out value="${count + 1}" /></div>
									</td>
									<td width="13%" align="center" class="bodytext"><bean:write name="applicationNoList"
										property="year" />-<bean:write name="applicationNoList"
										property="endYear" /> 
									</td>
									<td>
										<table width="100%" cellspacing="1" cellpadding="2">
											<logic:iterate id = "courseList" name = "applicationNoList" property="courseApplicationNoTO">
												<tr>
												<td align="center" class="bodytext"><bean:write name="courseList" property="courseTO.name" /></td>
												</tr>
											</logic:iterate>
										</table>
									</td>
									<td align="center" class="bodytext">
									<div align="center"><bean:write name="applicationNoList"
										property="onlineAppNoFrom" />-<bean:write
										name="applicationNoList" property="onlineAppNoTill" /></div>
									</td>

									<td align="center" class="bodytext"><bean:write name="applicationNoList"
										property="offlineAppNoFrom" />-<bean:write
										name="applicationNoList" property="offlineAppNoTill" /></td>
									<td width="50" height="24">
									<div align="center"><img src="images/edit_icon.gif"
										width="16" height="18" style="cursor:pointer"
										onclick="loadApplicationNo('<bean:write name="applicationNoList" property="id"/>')">

									</div>
									</td>
									<td width="54" height="24">
									<div align="center"><img src="images/delete_icon.gif"
										width="16" height="16" style="cursor:pointer"
										onclick="deleteApplicationNo('<bean:write name="applicationNoList" property="id"/>')">
									</div>
									</td>
									</tr>
								</logic:iterate>
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
				<tr>
					<td height="10" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">&nbsp;</td>
					<td width="13" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>

				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="100%" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">
	var year = document.getElementById("tempyear").value;
	if (year.length != 0) {
		document.getElementById("year").value = year;
	}
</script>