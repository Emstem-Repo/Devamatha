<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<link type="text/css"
	href="jquery/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
<script src="jquery/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<style type="text/css">
.ui-datepicker {
	background: #6B8E23;
	border: 1px solid #555;
	color: #6B8E23;
	font-family: Arial;
	font-size: 11px;
	margin-left: 10px
}
</style>
<script type="text/javascript">
	function resetMessages() {
		 resetFieldAndErrMsgs();
		 document.location.href = "hostelExemption.do?method=initHostelExemption";
	}	
	function cancelAction(){
		resetFieldAndErrMsgs();
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function getBlock(hostelId){
		getBlockByHostel("blockMap",hostelId,"blockId",updateBlock);
		}
	function updateBlock(req) {
		updateOptionsFromMap(req,"blockId","- Select -");
	}
	function getCoursebyHostelId(hostelId){
		getCoursebyHostel("courseMap",hostelId,"courseId",updateCourse);
		}
	function updateCourse(req) {
		updateOptionsFromMap(req,"courseId","- Select -");
	}
	function getClassByHostelId(hostelId){
		getClassByHostel("classMap",hostelId,"classId",updateClass);
		}
	function updateClass(req) {
		updateOptionsFromMap(req,"classId","- Select -");
	}
	function getSpecializationByCourseId(courseId){
		getSpecializationByCourse("spacialMap",courseId,"spacialId",updateSpecialation);
		}
	function updateSpecialation(req) {
		updateOptionsFromMap(req,"spacialId","- Select -");
	}
	function getUnit(blockId){
		getUnitByBlock("unitMap",blockId,"unitId",updateUnit);
		}
	function updateUnit(req) {
		updateOptionsFromMap(req,"unitId","- Select -");
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

	function saveHostelExemptionDetails(){
		document.getElementById("method").value="saveHostelExemptionDetails";
		document.hostelExemptionForm.submit();
	}
</script>
<html:form action="/hostelExemption" method="post" >
	<html:hidden property="formName" value="hostelExemptionForm" />
	<html:hidden property="method" styleId="method" value="getHostelStudentDataForExemption"/>
	<html:hidden property="pageType" value="1" />
	<html:hidden property="focus" styleId="focus" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.exemption" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.exemption" /></strong></td>
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
								<div id="notValid"><FONT color="red"></FONT></div>
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
									<% boolean disable=false;%>
										<logic:equal value="true" name="hostelExemptionForm" property="flag">
										<% disable=true;%>
										</logic:equal>
									<tr>
									<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.hostel.academic.year" /> :</div></td>
									<td width="16%" class="row-even" valign="top">
									<input
										type="hidden" id="tempyear" name="appliedYear"
										value="<bean:write name="hostelExemptionForm" property="year"/>" />
									<html:select 
										property="year" styleId="year"
										styleClass="combo" disabled='<%=disable%>'>
										<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select></td>
									<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory">*</span>
												<bean:message key="knowledgepro.hostel" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
                    							<html:select property="hostelId" styleId="hostelId" onchange="getBlock(this.value),getCoursebyHostelId(this.value),getClassByHostelId(this.value)" disabled='<%=disable%>'>
                    							<html:option value="">--Select--</html:option>
                    								<logic:notEmpty property="hostelMap" name="hostelExemptionForm">
						   								<html:optionsCollection property="hostelMap" label="value" value="key"/>
						   							</logic:notEmpty>
						   						</html:select>
											</td> 
									
									</tr>
									<tr>
                  							
											<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory"></span>
												<bean:message key="knowledgepro.block" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
												<html:select property="blockId" styleId="blockId"  styleClass="combo" onchange="getUnit(this.value)" disabled='<%=disable%>'>
													<html:option value="">--Select--</html:option>
						 								<logic:notEmpty name="hostelExemptionForm" property="blockMap">
             			 										<html:optionsCollection name="hostelExemptionForm" property="blockMap" label="value" value="key" />
															</logic:notEmpty>
			  									</html:select>
											</td>
											<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory"></span>
												<bean:message key="knowledgepro.unit" />:</div>
										</td>
										<td   class="row-even" width="35%" align="left">
											<html:select property="unitId" styleId="unitId"  styleClass="combo" disabled='<%=disable%>'>
												<html:option value="">--Select--</html:option>
						 							<logic:notEmpty name="hostelExemptionForm" property="unitMap">
             			 									<html:optionsCollection name="hostelExemptionForm" property="unitMap" label="value" value="key" />
														</logic:notEmpty>
			  								</html:select>
										</td>
									</tr>
									
									<tr>
									<td width="25%"  class="row-odd" >
				 							<div align="right"><bean:message key="knowledgepro.admin.course" />:</div>
										</td>
				 						<td width="25%"  class="row-even" align="left">
			        						<label>
												<html:select property="courseId" styleId="courseId" onchange="getSpecializationByCourseId(this.value)" disabled='<%=disable%>'>
									 			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									 			<logic:notEmpty name="hostelExemptionForm" property="courseMap">
             		                              <html:optionsCollection name="hostelExemptionForm" property="courseMap" label="value" value="key" />
				                                 </logic:notEmpty>
									   	 	</html:select>
											</label>
										</td>
									<td height="25" class="row-odd" width="25%">
										<div align="right"><bean:message key="knowledgepro.certificate.course.Semester" />:</div>
									</td>
									<td class="row-even" width="25%">
										<html:text name="hostelExemptionForm" property="semesterNo" styleId="semesterNo" styleClass="Timings" size="2" maxlength="2" disabled='<%=disable%>'/>
									</td>
									
									</tr>
									<tr>
									<td width="25%"  class="row-odd" >
				 							<div align="right"><bean:message key="knowledgepro.exam.specialization" />:</div>
										</td>
				 						<td width="25%"  class="row-even" align="left">
			        						<label>
												<html:select property="spacialId" styleId="spacialId" disabled='<%=disable%>'>
									 			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									   			<logic:notEmpty name="hostelExemptionForm" property="spacialMap">
             		                                 <html:optionsCollection name="hostelExemptionForm" property="spacialMap" label="value" value="key" />
					                             </logic:notEmpty>
									   	 	</html:select>
											</label>
										</td>
									<td width="25%"  class="row-odd" >
				 							<div align="right"><bean:message key="knowledgepro.exam.blockUnblock.class" />:</div>
										</td>
				 						<td width="25%"  class="row-even" align="left">
			        						<label>
												<html:select property="classId" styleId="classId" disabled='<%=disable%>'>
									 			<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>
									 			<logic:notEmpty name="hostelExemptionForm" property="classMap">
             		                               <html:optionsCollection name="hostelExemptionForm" property="classMap" label="value" value="key" />
					                               </logic:notEmpty>
									   	 	</html:select>
											</label>
										</td>
									
									</tr>
									<tr>
										<td height="25" class="row-odd" width="25%">
											<div align="right"><bean:message key="knowledgepro.exam.reJoin.registerNo" />:</div>
										</td>
										<td class="row-even" width="25%">
											<html:text name="hostelExemptionForm" property="registerNo" styleId="registerNo" disabled='<%=disable%>'/>
										</td>  
										<td height="25" class="row-odd" width="25%">
											<div align="right">
											<bean:message key="knowledgepro.feepays.Reason" />:</div>
										</td>
										<td class="row-even" width="25%">
										<logic:notEmpty property="reason" name="hostelExemptionForm">
											<html:text name="hostelExemptionForm" property="reason" styleId="reason" styleClass="comboExtraLarge" size="50" disabled='<%=disable%>'/>
										</logic:notEmpty>
										<logic:empty property="reason" name="hostelExemptionForm">
											<html:text name="hostelExemptionForm" property="reason" styleId="reason" styleClass="comboExtraLarge" size="50"/>
										</logic:empty>
										</td>
									</tr>
                  					<tr>
                 							<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory">*</span>
												<bean:message key="knowledgepro.holidays.from" />:</div>
											</td>
											<td   class="row-even" width="35%" align="left">
												<input type="hidden" name="hostelExemptionForm"	id="tempHolidaysFrom" value="<bean:write name='hostelExemptionForm' property='holidaysFrom'/>" />
												<html:text name="hostelExemptionForm" property="holidaysFrom" styleId="holidaysFrom" size="10" maxlength="16" disabled='<%=disable%>'/>
												<script language="JavaScript">
    													$(function(){
								 					var pickerOpts = {
								        			dateFormat:"dd/mm/yy"
								       				};  
								  					$.datepicker.setDefaults(
								   					$.extend($.datepicker.regional[""])
								  					);
								  					$("#holidaysFrom").datepicker(pickerOpts);
													});
                                   				</script>
												<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Morning" name="hostelExemptionForm" disabled='<%=disable%>'>Morning</nested:radio>
												<nested:radio property="holidaysFromSession" styleId="holidaysFromSession" value="Evening" disabled='<%=disable%>'>Evening</nested:radio>
											</td> 
							 				<td class="row-odd" width="15%"> 
												<div align="right"><span class="Mandatory">*</span>
												<bean:message key="knowledgepro.holidays.to" />:</div> 
											</td>
											<td   class="row-even" width="35%" align="left" colspan="2">
												<input type="hidden" name="hostelExemptionForm"	id="tempHolidaysTo" value="<bean:write name='hostelExemptionForm' property='holidaysTo'/>" />
												<html:text name="hostelExemptionForm" property="holidaysTo" styleId="holidaysTo" size="10" maxlength="16" disabled='<%=disable%>'/>
												<script language="JavaScript">
	    													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#holidaysTo").datepicker(pickerOpts);
														});
                                   				</script>
												<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="Morning" name="hostelExemptionForm" disabled='<%=disable%>'>Morning</nested:radio>
												<nested:radio property="holidaysToSession" styleId="holidaysToSession" value="Evening" disabled='<%=disable%>'>Evening</nested:radio>
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
						<tr>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
            						<td width="43%" height="35">&nbsp;</td>
            						<td width="7%"><html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.admin.search" />
											</html:submit>
									</td>
            						<td width="6%"><html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button>
									</td>
            						<td width="44%" ><html:button property="" styleClass="formbutton" onclick="cancelAction()">
												<bean:message key="knowledgepro.cancel"/>
											</html:button>
									</td>
          						</tr>
							</table>
							</td>
						</tr>
						</table>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				
				
				
				<logic:notEmpty name="hostelExemptionForm" property="hlExemptionList">			
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
											<td height="25" >
										<table width="100%" cellspacing="1" cellpadding="2">	
												
												
						<tr>
							<td>
								<table width="100%" cellspacing="1" cellpadding="2">
								<tr>
													<td height="25" align="center" class="row-odd" width="5%" >
													<bean:message key="knowledgepro.admin.subject.subject.s1no" />
													</td>
													<td align="center" height="25" class="row-odd">
														<input type="checkbox" id="checkAll" onclick="selectAll(this)"/> Select All
													</td>
													<td height="25" class="row-odd" align="center" width="10%"><bean:message
														key="knowledgepro.exam.reJoin.registerNo" /></td>
													<td class="row-odd" align="left" width="20%"><bean:message
														key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /></td>
													<td class="row-odd" align="left" width="20%"><bean:message
														key="knowledgepro.admin.course" /></td>	
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.block" /></td>
													<td class="row-odd" align="center" width="10%"><bean:message
													key="knowledgepro.unit" /></td>
												</tr>
												
												<nested:iterate id="student" property="hlExemptionList" name="hostelExemptionForm"   indexId="count">
													
														<c:choose>
																<c:when test="${count%2 == 0}">
																	<tr class="row-even">
																</c:when>
															<c:otherwise>
																<tr class="row-white">
															</c:otherwise>
														</c:choose>
																<td height="25"  width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td width="10%" height="25">
																	<input type="hidden" name="hlExemptionList[<c:out value='${count}'/>].selected" id="selectedhidden_<c:out value='${count}'/>"
																		value="<nested:write name='student' property='selected'/>" />	
																	<div align="center">
																		<input type="checkbox"  name="hlExemptionList[<c:out value='${count}'/>].checked1" id="<c:out value='${count}'/>" onclick="unCheckSelectAll()"/> 
																	</div>
																	<script type="text/javascript">
																		var selected = document.getElementById("selectedhidden_<c:out value='${count}'/>").value;
																		if(selected == "true") {
																		document.getElementById("<c:out value='${count}'/>").checked = true;
																		}	
																	</script>	
																</td>
																<td height="25"  align="center" width="10%"><nested:write
																	 property="registerNo" /></td>
																<td  align="left" width="20%"><nested:write
																	 property="name" /></td>
																<td  align="left" width="20%"><nested:write
																	 property="courseName" /></td>
																<td align="center" width="10%"><nested:write
																	 property="block" /></td>
																<td  align="center" width="10%"><nested:write
																	 property="unit" /></td>
															
												</nested:iterate>
												
												</table>
												
												</td>
												</tr>
												
							
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
			          
			          <tr>
			          	<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
							<td height="25" colspan="6">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td height="35" align="center">
										<html:submit  value="Submit" styleClass="formbutton"  property="" onclick="saveHostelExemptionDetails()"></html:submit>
									</td>
			          			</tr>
							</table>
							</td>
							
						</tr>
						
					</logic:notEmpty>	
				
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
<script>
var year = document.getElementById("tempyear").value;
	if(year.length != 0) {
	 	document.getElementById("year").value=year;
	}
var focusField=document.getElementById("focus").value;
   if(focusField != 'null'){  
    if(document.getElementById(focusField)!=null)      
        document.getElementById(focusField).focus();
}
</script>
