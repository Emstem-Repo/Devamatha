<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
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

	function addHostelStudentDisciplineDetails() {
		document.getElementById("method").value = "addHostelStudentDisciplineDetails";
		document.hostelDisciplinaryDetailsForm.submit();
	}
	function deleteHostelStudentDisciplineDetails(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "hostelDisciplinaryDetails.do?method=deleteHostelStudentDisciplineDetails&id="+ id;
		}
	}
	function viewHostelStudentDisciplineDetails(id) {
		document.location.href = "hostelDisciplinaryDetails.do?method=viewHostelStudentDisciplineDetails&id="+ id;
	}
	function updateHostelStudentDisciplineDetails() {
		document.getElementById("method").value = "updateHostelStudentDisciplineDetails";
		document.hostelDisciplinaryDetailsForm.submit();
	}
	function resetMessages() {
		resetFieldAndErrMsgs();
		document.location.href = "hostelDisciplinaryDetails.do?method=initDisciplinaryDetails";
	}
	function cancell(){
			document.location.href = "hostelDisciplinaryDetails.do?method=initDisciplinaryDetails";
	}

	function maxlength(field, size) {
	    if (field.value.length > size) {
	        field.value = field.value.substring(0, size);
	    }
	}
	function verifyRegisterNumberAcc(regNo) {
		document.getElementById("registerNo").value = regNo;
		document.getElementById("method").value = "assignListToFormAcc";
		document.hostelDisciplinaryDetailsForm.submit();
	}

	function verifyRegisterNumberyear(year) {
		document.getElementById("year").value = year;
		document.getElementById("method").value = "assignListToFormAcc";
		document.hostelDisciplinaryDetailsForm.submit();
	}
	
	function displayName(req) {
		var responseObj = req.responseXML.documentElement;
		var value = responseObj.getElementsByTagName("value");
		var isMsg = false;
		if (value != null) {
			for ( var I = 0; I < value.length; I++) {
				if (value[I].firstChild != null) {
					var temp = value[I].firstChild.nodeValue;
					document.getElementById("notValid").innerHTML = temp;
					document.getElementById("notValid").style.display = "block";
					isMsg = true;
				}
			}
		}
		if (isMsg != true) {
			var items = responseObj.getElementsByTagName("studentDetails");

			for ( var I = 0; I < items.length; I++) {
				if(items[I]!=null){
					var studentName = items[I].getElementsByTagName("studentName")[0].firstChild.nodeValue;
					var studentRoom= items[I].getElementsByTagName("studentRoom")[0].firstChild.nodeValue;
					var studentBed = items[I].getElementsByTagName("studentBed")[0].firstChild.nodeValue;
					var studentBlock = items[I].getElementsByTagName("studentBlock")[0].firstChild.nodeValue;
					var studentUnit = items[I].getElementsByTagName("studentUnit")[0].firstChild.nodeValue;
					var studentClass = items[I].getElementsByTagName("studentClass")[0].firstChild.nodeValue;
					var studentHostel = items[I].getElementsByTagName("studentHostel")[0].firstChild.nodeValue;
					document.getElementById("studentName").innerHTML = studentName;
					document.getElementById("studentRoomNo").innerHTML = studentRoom;
					document.getElementById("studentBedNo").innerHTML = studentBed;
					document.getElementById("studentBlock").innerHTML = studentBlock;
					document.getElementById("studentUnit").innerHTML = studentUnit;
					document.getElementById("studentClass").innerHTML = studentClass;
					document.getElementById("studentHostel").innerHTML = studentHostel;
					document.getElementById("notValid").style.display = "none";
				}
			}
		}
	}
	function cancel() {
		
		document.location.href = "LoginAction.do?method=loginAction";
	}	
	function viewHostelStudentDisciplineDetails(id) {
		document.location.href = "hostelDisciplinaryDetails.do?method=viewHostelStudentDisciplineDetails&id="+ id;
	}
</script>
<html:form action="/hostelDisciplinaryDetails" method="post">
<html:hidden property="formName" value="hostelDisciplinaryDetailsForm" />
<html:hidden property="id" styleId="id" />
<html:hidden property="status" styleId="status" />
<html:hidden property="pageType" value="1" />
	<c:choose>
		<c:when test="${disciplinaryOperation == 'edit'}">
			<html:hidden property="method" styleId="method" value="editHostelStudentDisciplineDetails" />
		</c:when>
		<c:otherwise>
			<html:hidden property="method" styleId="method" value="addHostelStudentDisciplineDetails" />
		</c:otherwise>
	</c:choose>

	<table width="100%" border="0">
		<tr>
			<td><span class="Bredcrumbs"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;
			<bean:message key="knowledgepro.hostel.disciplinary.details" />&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
							<bean:message key="knowledgepro.hostel.disciplinary.action.details" />
						</td>
						<td width="10">
							<img src="images/Tright_1_01.gif" width="9" height="29">
						</td>
					</tr>				
					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<div align="right">
								<FONT color="red">
									<span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span>
								</FONT>
							</div>
							<div id="errorMessage">
								<FONT color="red"><html:errors /></FONT>
								<FONT color="red"><div id="notValid" ></div></FONT>
								<FONT color="green">
									<html:messages id="msg"	property="messages" message="true">
										<c:out value="${msg}" escapeXml="false"></c:out><br>
									</html:messages>
								</FONT>
							</div>
						</td>
						<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>					

					<tr>
						<td height="30" valign="top" background="images/Tright_03_03.gif"></td>
						<td valign="top" class="news">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="100%" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">							
											<tr>
											<td  width="20%" class="row-odd" align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.disciplinary.type.name" />:</td>
											<td width="30%" height="25" class="row-even">
													<div align="left">
													
													<c:choose>
														<c:when test="${disciplinaryOperation == 'edit'}">
															<html:select property="disciplineId" styleClass="comboLarge" styleId="disciplineId" name="hostelDisciplinaryDetailsForm">
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>																														
															<html:optionsCollection name="hostelDisciplines" label="disciplineTypeName" value="id" />															
															</html:select>
														</c:when>
														<c:otherwise>
															<html:select property="disciplineId" styleClass="comboLarge" styleId="disciplineId" name="hostelDisciplinaryDetailsForm" >
															<html:option value=""><bean:message key="knowledgepro.admin.select" /></html:option>																														
															<html:optionsCollection name="hostelDisciplines" label="disciplineTypeName" value="id" />															
															</html:select>
														</c:otherwise>
													</c:choose>
													</div>
											</td>
											<td width="20%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												<bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
												<td width="30%" class="row-even">
												<input type="hidden" id="yr" name="yr"
										value="<bean:write name="hostelDisciplinaryDetailsForm" property="year"/>" />
									<html:select property="year" styleId="year" styleClass="comboLarge" name="hostelDisciplinaryDetailsForm" onchange="verifyRegisterNumberyear(this.value)">
										<cms:renderYear></cms:renderYear>
									</html:select>
									<div id="academicYearError"></div>
									</td>
									</tr>
													<tr>
												<td  width="20%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												<bean:message key="knowledgepro.hostel.reservation.registerNo" /></div></td>
											<td  width="30%" class="row-even">
													<html:text name="hostelDisciplinaryDetailsForm" property="registerNo" styleId="registerNo"
													size="15" maxlength="10" onkeypress="return isNumberKey(event)" onchange="verifyRegisterNumberAcc(this.value)"/>
												</td>
									<td width="20%" class="row-odd" ><div align="right"><span class="Mandatory">* </span><bean:message key="knowledgepro.admin.template.Date"/>:</div></td>
												<td   class="row-even" width="30%" align="left">
													<html:text  property="date" styleId="date" size="15" maxlength="16"/>
                                    				<script language="JavaScript">
                                    				
                                    				$( "#date" ).datepicker({dateFormat:"dd/mm/yy"}).datepicker("setDate",new Date());

                                     				</script>
												</td>
							</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Student Name:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left" colspan="3">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentName"/>
								</c:when>
								<c:otherwise>
									<div id="studentName" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Class:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentClass"/>
								</c:when>
								<c:otherwise>
									<div id="studentClass" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 215px; height: 25">
											Hostel:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentHostel"/>
								</c:when>
								<c:otherwise>
									<div id="studentHostel" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Room Number:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentRoomNo"/>
								</c:when>
								<c:otherwise>
									<div id="studentRoomNo" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Bed Number:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentBedNo"/>
								</c:when>
								<c:otherwise>
									<div id="studentBedNo" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
										</tr>
										<tr>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Block:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentBlock"/>
								</c:when>
								<c:otherwise>
									<div id="studentBlock" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
											<td width="20%" height="25" class="row-odd" align="right">
											<div align="right" style="width: 200px; height: 25">
											Unit:</div>
											</td>
											<td width="30%" height="25" class="row-even" align="left">
											<c:choose>
								<c:when test="${msg != null}">
									<bean:write name="hostelDisciplinaryDetailsForm" property="studentUnit"/>
								</c:when>
								<c:otherwise>
									<div id="studentUnit" align="left"></div>
								</c:otherwise>
							</c:choose>
											</td>
										</tr>
											<tr>
												<td  width="20%" height="25" class="row-odd" align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.desc.with.col" /></td>
												<td  colspan="3" width="80%" class="row-even">
													<html:textarea name="hostelDisciplinaryDetailsForm" property="description" styleId="description" cols="90" rows="4" onkeypress="maxlength(this, 249);"></html:textarea>
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
									
							<c:choose>
								<c:when test="${disciplinaryOperation == 'edit'}">
								<td width="48%" height="35">
										<div align="right">
											<html:button property="" styleClass="formbutton" value="Update"
										onclick="updateHostelStudentDisciplineDetails()"></html:button>
								</div>
									</td>
									<td width="2%"></td>
									<td width="2%">									
										<div align="left">
										<html:button property="" styleClass="formbutton" onclick="cancell()">
												<bean:message key="knowledgepro.cancel" /></html:button>
								</div>
									</td>
									<td width="2%"></td>
									<td width="46%">									
										<div align="left">
										<html:button property=""
										styleClass="formbutton" value="Close" onclick="cancel()">
									</html:button>
								</div>
								</td>						
								</c:when>
								<c:otherwise>
								<td width="48%" height="35">
										<div align="right">
									<html:button property="" styleClass="formbutton" value="Submit"
										onclick="addHostelStudentDisciplineDetails()"></html:button>
								</div>
									</td>
									<td width="2%"></td>
									<td width="2%">									
										<div align="left">
										<html:button property="" styleClass="formbutton" onclick="resetMessages()">
												<bean:message key="knowledgepro.reset" /></html:button>
								</div>
									</td>
									<td width="2%"></td>
									<td width="46%">									
										<div align="left">
										<html:button property=""
										styleClass="formbutton" value="Close" onclick="cancel()">
									</html:button>
								</div>
								</td>
								</c:otherwise>

							</c:choose>
										
								</tr>
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
					<logic:notEmpty name="hostelDisciplinaryDetailsForm" property="disciplineList">
					<tr>
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td align="center" class="row-odd"><bean:message key="knowledgepro.hostel.allocation.regno" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.admin.disciplinary.type.name" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.admin.desc" /></td>
									<td align="center" class="row-odd"><bean:message key="knowledgepro.admin.template.Date" /></td>
									<!--<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
									</td>
								--><td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.view" />
									</div>
									</td>
								</tr>
								<c:set var="temp" value="0" />
								<logic:notEmpty name="hostelDisciplinaryDetailsForm"
									property="disciplineList">
									<logic:iterate id="rec" name="hostelDisciplinaryDetailsForm"
										property="disciplineList"
										type="com.kp.cms.to.hostel.HlDisciplinaryDetailsTO" indexId="count">
										<c:choose>
											<c:when test="${temp == 0}">
											<tr>
											<td width="5%" height="25" class="row-even">
											<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" class="row-even">
											<bean:write	name="rec" property="registerNo" />
											</td>
											<td align="center" width="20%" class="row-even">
											<bean:write name="rec" property="disciplineTypeName" />
											</td>
											<td align="center" width="25%" class="row-even">
											<bean:write name="rec" property="description" />
											</td>
											<td align="center" width="20%" class="row-even">
											<bean:write name="rec" property="date" />
											</td>
											<!--<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteHostelStudentDisciplineDetails('<bean:write name="rec" property="id"/>')"></div>
											</td>
										--><td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/View_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="viewHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
										</tr>
										<c:set var="temp" value="1" />
										</c:when>
											<c:otherwise>
												<tr>
											<td width="5%" height="25" class="row-white">
												<div align="center"><c:out value="${count + 1}" /></div>
											</td>
											<td align="center" width="20%" class="row-white">
											<bean:write	name="rec" property="registerNo" />
											</td>
											<td align="center" width="20%" class="row-white">
											<bean:write name="rec" property="disciplineTypeName" />
											</td>
											<td align="center" width="25%" class="row-white">
											<bean:write name="rec" property="description" />
											</td>
											<td align="center" width="20%" class="row-white">
											<bean:write name="rec" property="date" />
											</td>
											<!--<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteHostelStudentDisciplineDetails('<bean:write name="rec" property="id"/>')"></div>
											</td>
										--><td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/View_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="viewHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
										</tr>
										<c:set var="temp" value="0" />
											</c:otherwise>
										</c:choose>
									</logic:iterate>
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				</logic:notEmpty>
				
					<tr>
						<td height="3" valign="top" background="images/Tright_03_03.gif"></td>
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
<script type="text/javascript">
	var yearId = document.getElementById("yr").value;
	if (yearId.length != null && yearId.length != 0) {
		document.getElementById("year").value = yearId;
	}
//added by manu after form submit ajax will execute
	var status=document.getElementById("status").value;
	  if(status!=null && status!=''){
	   document.getElementById("studentName").innerHTML = null;
	   document.getElementById("studentRoomNo").innerHTML =null;
	   document.getElementById("studentBedNo").innerHTML = null;
	   document.getElementById("studentBlock").innerHTML = null;
	   document.getElementById("studentUnit").innerHTML = null;
	   document.getElementById("studentHostel").innerHTML = null;
	   document.getElementById("studentClass").innerHTML = null;
	   var academicYear=document.getElementById("year").value;
	   var regNo=document.getElementById("registerNo").value;
	   if(academicYear!=null && academicYear!=""){
		    if(regNo!=null && regNo!=''){
		    verifyRegisterNumberAndGetNameAcc(regNo,academicYear,displayName);	
	        }
	    }
	else	{
		document.getElementById("academicYearError").innerHTML="Please Select Academic Year";
		document.getElementById("academicYearError").style.display = "block";	
		}
	}
//manu end code 
</script>