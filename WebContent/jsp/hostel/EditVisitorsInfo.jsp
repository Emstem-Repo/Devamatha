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
	}	
	function cancelAction(){
		document.location.href = "hostelVisitors.do?method=initHostelVisitorsInformation";
	}
	function clearField(field){
		if(field.value == "00")
			field.value = "";
	}
	function checkForEmpty(field){
		if(field.value.length == 0){
			field.value="00";
		}
	}
	function checkNumber(field){
		if(isNaN(field.value)){
			field.value = "00";
		}
	}
function editVisitorsInfo(id){
		
		document.location.href = "hostelVisitors.do?method=editVisitorsInfo&id="+id;
	}
	function deleteVisitorsInfo(id){
		document.location.href = "hostelVisitors.do?method=deleteVisitorsInfo&id="+id;
	}
	function reset(){
		document.getElementById("visitorName").value=document.getElementById("tempAmount").value;
		document.getElementById("contactNo").value=document.getElementById("tempAmount").value;
		document.getElementById("date").value=document.getElementById("tempAmount").value;
		document.getElementById("shtime").value=document.getElementById("tempinHours").value;
		document.getElementById("smtime").value=document.getElementById("tempinMins").value;
		document.getElementById("enhtime").value=document.getElementById("tempoutHours").value;
		document.getElementById("enmtime").value=document.getElementById("tempoutMins").value;
		}
</script>
<html:form action="/hostelVisitors" method="post" >
	<html:hidden property="formName" value="hostelVisitorsInfoForm" />
	<html:hidden property="method" styleId="method" value="updateVisitorsInformation"/>
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.visitors.info.edit" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.visitors.info.edit" /></strong></td>
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
										<tr >
	                    					<td class="row-odd" width="20%"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.name"/></div></td>
                  								<td class="row-even" width="20%">
                  								<html:select property="hostelId" styleId="hostelId"  styleClass="combo" disabled="true">
                  								<html:option value="">--Select--</html:option>
												<logic:notEmpty property="hostelMap" name="hostelVisitorsInfoForm">
						   						<html:optionsCollection property="hostelMap" label="value" value="key"/>
						   						</logic:notEmpty>
												</html:select> 
				                    		</td>
				  							<td class="row-odd" width="20%">
												<div align="right"><span class="Mandatory">*</span>Academic	Year :</div>
											</td>
											<td class="row-even"  width="20%">
												<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="hostelVisitorsInfoForm" property="academicYear"/>"/>
													<html:select property="academicYear" disabled="true" styleClass="combo" styleId="academicYr">
													<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
													</html:select></td>	
											<td class="row-odd"  width="20%"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  							<td class="row-even"  width="20%">
                  								<html:text property="regNo"	styleId="regNo" size="12" maxlength="12" disabled="true" onchange="getStudentDetails()"/>
											</td>	
	                  						</tr>
                							<tr>
                								<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.visitors.name"/>:</div></td>
                  								<td class="row-even" >
                  									<input type="hidden" name="hostelVisitorsInfoForm"	id="tempvisitorName" value="<bean:write name='hostelVisitorsInfoForm' property='visitorName'/>" />
                  									<html:text property="visitorName"	styleId="visitorName"/>
												</td>	
												<td class="row-odd" ><div align="right">Contact No:</div></td>
                  								<td class="row-even" >
                  									<input type="hidden" name="hostelVisitorsInfoForm"	id="tempcontactNo" value="<bean:write name='hostelVisitorsInfoForm' property='contactNo'/>" />
                  									<html:text property="contactNo"	styleId="contactNo" size="10" maxlength="10" onkeypress="return isNumberKey(event)"/>
												</td>
												<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.admin.template.Date"/>:</div></td>
                  								<td   class="row-even" width="25%" align="left">
                  									<input type="hidden" name="hostelVisitorsInfoForm"	id="tempdate" value="<bean:write name='hostelVisitorsInfoForm' property='date'/>" />
													<html:text  property="date" styleId="date" size="10" maxlength="16"/>
                                    				<script language="JavaScript">
     													$(function(){
									 					var pickerOpts = {
									        			dateFormat:"dd/mm/yy"
									       				};  
									  					$.datepicker.setDefaults(
									   					$.extend($.datepicker.regional[""])
									  					);
									  				$("#date").datepicker(pickerOpts);
														});
                                     				</script>
												</td> 
                							</tr>
                							<tr>
	                      						<td width="18%" height="25" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.visitor.intime"/>:</div></td>
	                      						<td width="20%" class="row-even" align="left" >
	                      							<input type="hidden" name="hostelVisitorsInfoForm"	id="tempinHours" value="<bean:write name='hostelVisitorsInfoForm' property='inHours'/>" />
	                      							<input type="hidden" name="hostelVisitorsInfoForm"	id="tempinMins" value="<bean:write name='hostelVisitorsInfoForm' property='inMins'/>" />
	                      							<html:text name="hostelVisitorsInfoForm" property="inHours" styleClass="Timings" styleId="shtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  							<html:text  property="inMins" styleClass="Timings" styleId="smtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
 						  						</td>
	                      						<td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.visitor.outtime"/>:</div></td>
	                      						<td width="19%" class="row-even" align="left">
	                      						<input type="hidden" name="hostelVisitorsInfoForm"	id="tempoutHours" value="<bean:write name='hostelVisitorsInfoForm' property='outHours'/>" />
	                      							<input type="hidden" name="hostelVisitorsInfoForm"	id="tempoutMins" value="<bean:write name='hostelVisitorsInfoForm' property='outMins'/>" />
	                      							<html:text name="hostelVisitorsInfoForm" property="outHours" styleClass="Timings" styleId="enhtime" size="2" maxlength="2"  onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>:
 						  							<html:text  property="outMins" styleClass="Timings" styleId="enmtime" size="2" maxlength="2" onfocus="clearField(this)" onblur="checkForEmpty(this), checkNumber(this)" onkeypress="return isNumberKey(event)"/>
 						  						</td>
	                      						<td class="row-odd" ><div align="right">&nbsp;</div></td>
	                      						<td class="row-even" align="left">&nbsp;</td>
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
            						<td width="38%" height="35">&nbsp;</td>
            						<td width="5%"><html:submit styleClass="formbutton">
												<bean:message key="knowledgepro.update" />
											</html:submit>
									</td>
									<td width="5%" ><html:button property="" styleClass="formbutton" value="Reset" onclick="reset()">
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
												<tr >
													<td height="25" class="row-odd" width="5%"><div align="center"><bean:message key="knowledgepro.admin.subject.subject.s1no" /></div></td>
	                  								<td width="20%" class="row-odd" align="center"><bean:message key = "knowledgepro.hostel"/></td>
	                  								<td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.exam.reJoin.registerNo"/></td>
	                  								<td width="25%" class="row-odd" align="center"><bean:message key = "knowledgepro.hostel.visitors.name"/></td>
	                  								<td width="10%"class="row-odd" align="center"><bean:message key = "knowledgepro.admin.template.Date"/></td>
	                  								<td width="10%" height="25" class="row-odd" align="center"><bean:message key = "knowledgepro.hostel.visitor.intime"/></td>
	                  								<td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.hostel.visitor.outtime"/></td>
	                  								<td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.edit"/> </td>
	                  								<td width="10%" class="row-odd" align="center"><bean:message key = "knowledgepro.delete"/></td>
	                							</tr>
												<logic:notEmpty name = "hostelVisitorsInfoForm" property="hostelVisitorsInfoTosList">
												<logic:iterate id = "visitorsList" name = "hostelVisitorsInfoForm" property="hostelVisitorsInfoTosList" indexId="count">
															<tr>
																<td height="10" class="row-even" width="5%">
																<div align="center"><c:out value="${count+1}" /></div>
																</td>
																<td align="center" class="row-even"><bean:write name="visitorsList" property="hostelName" /></td>
		                  										<td align="center" class="row-even"><bean:write name = "visitorsList" property="registerNo"/></td>
		                  										<td height="25" align="center" class="row-even"><bean:write name = "visitorsList" property="visitorName"/></td>
		                  										<td align="center" class="row-even"><bean:write name = "visitorsList" property="date"/></td>
		                  										<td align="center" class="row-even"><bean:write name = "visitorsList" property="inTime"/></td>
		                  										<td align="center" class="row-even"><bean:write name = "visitorsList" property="outTime"/></td>
		                  										<td align="center" class="row-even"><img src="images/edit_icon.gif" width="16" height="18"
																	style="cursor:pointer"	onclick="editVisitorsInfo('<bean:write name="visitorsList" property="id"/>')"></td>
		                  										<td align="center" class="row-even"><img src="images/delete_icon.gif" width="16" height="16"
						  											style="cursor:pointer" onclick="deleteVisitorsInfo('<bean:write name="visitorsList" property="id"/>')"></td>
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
