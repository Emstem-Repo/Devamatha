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
		document.getElementById("notValid").innerHTML = null;
		document.getElementById("errorMessage").innerHTML = null;
		document.getElementById("studentName").innerHTML = null;
		document.getElementById("studentRoomNo").innerHTML =null;
		document.getElementById("studentBedNo").innerHTML = null;
		document.getElementById("studentBlock").innerHTML = null;
		document.getElementById("studentUnit").innerHTML = null;
		document.getElementById("studentCourse").innerHTML = null;
		 resetFieldAndErrMsgs();
	}	
	function resetMessages1() {
		document.location.href = "hostelVisitors.do?method=initHostelVisitorsInformation";
	}
	function cancelAction(){
		resetFieldAndErrMsgs();
		document.location.href = "LoginAction.do?method=loginAction";
	}
	function cancelAction1(){
		document.location.href = "HostelTransaction.do?method=transactionImages";
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
	
	function searchForStudent(){
		var registerNo = document.getElementById("regNo").value;
		document.location.href = "hostelVisitors.do?method=searchVisitorsInfo&regNo="+registerNo;
	}
	function getStudentDetails(){
		document.getElementById("notValid").innerHTML = null;
		document.getElementById("errorMessage").innerHTML = null;
		document.getElementById("studentName").innerHTML = null;
		document.getElementById("studentRoomNo").innerHTML =null;
		document.getElementById("studentBedNo").innerHTML = null;
		document.getElementById("studentBlock").innerHTML = null;
		document.getElementById("studentUnit").innerHTML = null;
		document.getElementById("studentCourse").innerHTML = null;
		var academicYear = document.getElementById("academicYr").value;
		var registerNo = document.getElementById("regNo").value;
		getStudentDetailsForVisitors(academicYear,registerNo,updateStudentDetails);
		}
	function updateStudentDetails(req){
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		var isMsg = false;
		if(value!=null){
			for ( var I = 0; I < value.length; I++) {
			if (value[I].firstChild != null) {
				document.getElementById("visitorName").disabled = "true";
				document.getElementById("contactNo").disabled = "true";
				document.getElementById("date").disabled = true;
				document.getElementById("shtime").disabled = true;
				document.getElementById("smtime").disabled = true;
				document.getElementById("enhtime").disabled = true;
				document.getElementById("enmtime").disabled = true;
				var temp = value[I].firstChild.nodeValue;
				document.getElementById("notValid").innerHTML = temp;
				enableFields(true);
				isMsg = true;
			}
			}
		}
		if(isMsg=true){
		var items = responseObj.getElementsByTagName("studentDetails");
		for ( var I = 0; I < items.length; I++) {
			if(items[I]!=null){
				var studentName = items[I].getElementsByTagName("studentName")[0].firstChild.nodeValue;
				var studentRoom= items[I].getElementsByTagName("studentRoom")[0].firstChild.nodeValue;
				var studentBed = items[I].getElementsByTagName("studentBed")[0].firstChild.nodeValue;
				var studentBlock = items[I].getElementsByTagName("studentBlock")[0].firstChild.nodeValue;
				var studentUnit = items[I].getElementsByTagName("studentUnit")[0].firstChild.nodeValue;
				var studentCourse = items[I].getElementsByTagName("studentCourse")[0].firstChild.nodeValue;
				document.getElementById("studentName").innerHTML = studentName;
				document.getElementById("studentRoomNo").innerHTML = studentRoom;
				document.getElementById("studentBedNo").innerHTML = studentBed;
				document.getElementById("studentBlock").innerHTML = studentBlock;
				document.getElementById("studentUnit").innerHTML = studentUnit;
				document.getElementById("studentCourse").innerHTML = studentCourse;
				document.getElementById("visitorName").disabled = false;
				document.getElementById("contactNo").disabled = false;
				document.getElementById("date").disabled = false;
				document.getElementById("shtime").disabled = false;
				document.getElementById("smtime").disabled = false;
				document.getElementById("enhtime").disabled = false;
				document.getElementById("enmtime").disabled = false;
			}
		}
		}
		}
</script>
<html:form action="/hostelVisitors" method="post" >
	<html:hidden property="formName" value="hostelVisitorsInfoForm" />
	<html:hidden property="method" styleId="method" value="addVisitorsInformation"/>
	<html:hidden property="pageType" value="1" />
	<table width="100%" border="0">
		<tr>
			<td><span class="heading"> <bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.visitors.info" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="body"><strong
						class="boxheader"> <bean:message
						key="knowledgepro.hostel.visitors.info" /></strong></td>
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
								<div id="notValid" style="color: red"><FONT color="red"></FONT></div>
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
				  							<td class="row-odd" width="20%">
												<div align="right"><span class="Mandatory">*</span>Academic	Year :</div>
											</td>
											<td class="row-even"  width="20%">
												<input type="hidden" id="tempyear" name="tempyear" value="<bean:write name="hostelVisitorsInfoForm" property="academicYear"/>"/>
													<html:select property="academicYear"  styleClass="combo" styleId="academicYr">
													<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
													</html:select></td>	
											<td class="row-odd"  width="20%"><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.exam.reJoin.registerNo"/></div></td>
                  							<td class="row-even"  width="20%">
                  								<html:text property="regNo"	styleId="regNo" size="12" maxlength="12" onchange="getStudentDetails()"/>
											</td>
											<td class="row-odd"  width="20%"></td>
                  							<td class="row-even"  width="20%"></td>	
	                  						</tr>
										<tr>
	                  						<td class="row-odd" align="right"><bean:message key="knowledgepro.admin.name"/>:</td>
	                  						<td class="row-even">
	                  						<c:choose>
											<c:when test="${admOperation != null && admOperation == 'add'}">
												<bean:write name="hostelVisitorsInfoForm" property="studentName"/>
											</c:when>
											<c:otherwise>
											<div id="studentName" align="left" >
				  							</div>
											</c:otherwise>
										</c:choose>
	                  					</td>	
				  							<td class="row-odd" align="right"><bean:message key="knowledgepro.admin.course"/>:</td>
				  							<td class="row-even">
	                  						<c:choose>
											<c:when test="${admOperation != null && admOperation == 'add'}">
												<bean:write name="hostelVisitorsInfoForm" property="studentCourse"/>
											</c:when>
											<c:otherwise>
											<div id="studentCourse" align="left" >
				  							</div>
											</c:otherwise>
											</c:choose>
		                  					</td>
                  							<td class="row-odd" align="right"><bean:message key="knowledgepro.hostel.blocks"/>:</td>
                  							<td class="row-even">
	                  						<c:choose>
											<c:when test="${admOperation != null && admOperation == 'add'}">
												<bean:write name="hostelVisitorsInfoForm" property="studentBlock"/>
											</c:when>
											<c:otherwise>
											<div id="studentBlock" align="left" >
				  							</div>
											</c:otherwise>
											</c:choose>
		                  					</td>
	                  						</tr>
	                  						<tr>
	                  							<td class="row-odd" align="right"><bean:message key="knowledgepro.hostel.units"/>:</td>
	                  							<td class="row-even">
		                  						<c:choose>
												<c:when test="${admOperation != null && admOperation == 'add'}">
													<bean:write name="hostelVisitorsInfoForm" property="studentUnit"/>
												</c:when>
												<c:otherwise>
												<div id="studentUnit" align="left" >
					  							</div>
												</c:otherwise>
												</c:choose>
		                  						</td>
				  								<td class="row-odd" align="right"><bean:message key="knowledgepro.hostel.roomno"/>:</td>
				  								<td class="row-even">
		                  						<c:choose>
												<c:when test="${admOperation != null && admOperation == 'add'}">
													<bean:write name="hostelVisitorsInfoForm" property="studentRoomNo"/>
												</c:when>
												<c:otherwise>
												<div id="studentRoomNo" align="left" >
					  							</div>
												</c:otherwise>
												</c:choose>
		                  						</td>
                  								<td class="row-odd" align="right"><bean:message key="knowledgepro.hostel.bedno"/>:</td>
                  								<td class="row-even">
		                  						<c:choose>
												<c:when test="${admOperation != null && admOperation == 'add'}">
													<bean:write name="hostelVisitorsInfoForm" property="studentBedNo"/>
												</c:when>
												<c:otherwise>
												<div id="studentBedNo" align="left" >
					  							</div>
												</c:otherwise>
												</c:choose>
		                  						</td>
	                  						</tr>
	                  						<tr>
               									<td align="left" ><b> Enter Visitor Details</b>	</td>
                							</tr>
                							<tr>
                								<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.hostel.visitors.name"/>:</div></td>
                  								<td class="row-even" >
                  									<html:text property="visitorName"	styleId="visitorName"/>
												</td>	
												<td class="row-odd" ><div align="right">Contact No:</div></td>
                  								<td class="row-even" >
                  									<html:text property="contactNo"	styleId="contactNo" size="10" maxlength="10" onkeypress="return isNumberKey(event)"/>
												</td>
												<td class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.admin.template.Date"/>:</div></td>
                  								<td   class="row-even" width="25%" align="left">
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
	                      							<html:select property="inHours" styleId="shtime">
                    									<logic:notEmpty property="hourMap" name="hostelVisitorsInfoForm">
						   									<html:optionsCollection property="hourMap" label="value" value="key"/>
						   								</logic:notEmpty>
						   							</html:select>:
						   							<html:select property="inMins" styleId="smtime">
                    									<logic:notEmpty property="minMap" name="hostelVisitorsInfoForm">
						   									<html:optionsCollection property="minMap" label="value" value="key"/>
						   								</logic:notEmpty>
						   							</html:select>
						   							</td>
	                      						<td width="13%" class="row-odd" ><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.hostel.visitor.outtime"/>:</div></td>
	                      						<td width="19%" class="row-even" align="left">
	                      							<html:select property="outHours" styleId="enhtime">
                    									<logic:notEmpty property="hourMap" name="hostelVisitorsInfoForm">
						   									<html:optionsCollection property="hourMap" label="value" value="key"/>
						   								</logic:notEmpty>
						   							</html:select>:
						   							<html:select property="outMins" styleId="enmtime">
                    									<logic:notEmpty property="minMap" name="hostelVisitorsInfoForm">
						   									<html:optionsCollection property="minMap" label="value" value="key"/>
						   								</logic:notEmpty>
						   							</html:select>
	                      						</td>
	                      						<td width="13%" class="row-odd" ></td>
	                      						<td width="19%" class="row-even" align="left"></td>
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
												<bean:message key="knowledgepro.submit" />
											</html:submit>
									</td>
            						<td width="4%">
            						<c:choose>
										<c:when test="${hostelVisitorsInfoForm.isHlTransaction !=null &&  hostelVisitorsInfoForm.isHlTransaction !='' && hostelVisitorsInfoForm.isHlTransaction == 'true'}">
											<html:button property="" styleClass="formbutton" onclick="resetMessages1()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.admin.reset"/>
											</html:button>
										</c:otherwise>
									</c:choose>
									</td>
									<td width="4%"><html:button property="" styleClass="formbutton" onclick="searchForStudent()">
												<bean:message key="knowledgepro.admin.search"/>
											</html:button>
									</td>
            						<td width="44%" >
            						<c:choose>
										<c:when test="${hostelVisitorsInfoForm.isHlTransaction !=null &&  hostelVisitorsInfoForm.isHlTransaction !='' && hostelVisitorsInfoForm.isHlTransaction == 'true'}">
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction1()"></html:button>
										</c:when>
										<c:otherwise>
											<html:button property="" styleClass="formbutton" value="Close" onclick="cancelAction()"></html:button>
										</c:otherwise>
									</c:choose>
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
