<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<!--<link type="text/css" href="jquery-ui-1.10.0.custom/css/ui-lightness/jquery-ui-1.10.0.custom.min.css" rel="stylesheet" />
<script src="jquery-ui-1.10.0.custom/js/jquery-1.9.0.js" type="text/javascript"></script>
<script src="jquery-ui-1.10.0.custom/js/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
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
</style>-->
	<script type="text/javascript">
	function search1(){
		document.getElementById("method").value = "searchHostelStudentDisciplineDetails";
		document.hostelDisciplinaryDetailsForm.submit();
	}
	function deleteHostelStudentDisciplineDetails(id){
		deleteConfirm = confirm("Are you sure you want to delete this entry?");
		if(deleteConfirm){
			document.location.href = "hostelDisciplinaryDetails.do?method=deleteHostelStudentDisciplineDetails&id="+ id;
		}
	}
	function editHostelStudentDisciplineDetails(id) {
		document.location.href = "hostelDisciplinaryDetails.do?method=editHostelStudentDisciplineDetails&id="+ id;
	}
	function updateHostelStudentDisciplineDetails() {
		document.getElementById("method").value = "updateHostelStudentDisciplineDetails";
		document.hostelDisciplinaryDetailsForm.submit();
	}
	function resetMessages() {
		document.location.href = "hostelDisciplinaryDetails.do?method=initEditDisciplinaryDetailsAction";
	}
</script>
<html:form action="/hostelDisciplinaryDetails" method="post">
<html:hidden property="formName" value="hostelDisciplinaryDetailsForm" />
<!--<html:hidden property="method" styleId="method" value=""/>
--><html:hidden property="id" styleId="id" />
<html:hidden property="pageType" value="2" />
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
			Edit Disciplinary Action Details&gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
						<td background="images/Tcenter.gif" class="heading_white">
							Edit Disciplinary Action Details
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
								<div id="notValid"><FONT color="red"></FONT></div>
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
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
										<table width="100%" cellspacing="1" cellpadding="2">							
										 <tr>
											<td width="25%" height="25" class="row-odd"><div align="right">
												<bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
											<td width="25%" class="row-even">
												<input type="hidden" id="yr" name="yr" value="<bean:write name="hostelDisciplinaryDetailsForm" property="year"/>" />
												<html:select property="year" styleId="year" styleClass="combo" name="hostelDisciplinaryDetailsForm">
												<cms:renderAcademicYearForAdmission></cms:renderAcademicYearForAdmission>
												</html:select>
											</td>
											<td  width="25%" class="row-odd"><div align="right"><span class="Mandatory">*</span>&nbsp;
												<bean:message key="knowledgepro.hostel.reservation.registerNo" /></div></td>
											<td  width="25%" class="row-even">
												<html:text name="hostelDisciplinaryDetailsForm" property="registerNo" styleId="registerNo" size="15" maxlength="10"  />
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
									
									<td width="2%"></td>
									<td width="45%" height="35">						
										<div align="right">
											<html:button property="" styleClass="formbutton" onclick="search1()" value="Search">&nbsp;&nbsp;	
											</html:button>
										</div>									
									</td>
									<td  height="35" >									
										<div>&nbsp;&nbsp;&nbsp;&nbsp;
											<html:button property="" styleClass="formbutton" onclick="resetMessages()">
											<bean:message key="knowledgepro.reset" /></html:button>
										</div>									
									</td>
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
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.edit" />
									</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.delete" /></div>
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
											<td align="center" width="5%" height="25" class="row-even">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-even">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteHostelStudentDisciplineDetails('<bean:write name="rec" property="id"/>')"></div>
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
											<td align="center" width="5%" height="25" class="row-white">
											<div align="center"><img src="images/edit_icon.gif"
												width="16" height="18" style="cursor: pointer"
												onclick="editHostelStudentDisciplineDetails('<bean:write name="rec" property="id" />')" /></div>
											</td>
											<td width="5%" height="25" class="row-white">
											<div align="center"><img src="images/delete_icon.gif"
												width="16" height="16" style="cursor: pointer"
												onclick="deleteHostelStudentDisciplineDetails('<bean:write name="rec" property="id"/>')"></div>
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

</script>