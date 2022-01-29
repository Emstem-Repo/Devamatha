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
	function cancell(){
		document.getElementById("date").value = '';
		document.getElementById("description").value = '';
		document.getElementById("method").value = "assignListToFormCancel";
		document.hostelDisciplinaryDetailsForm.submit();
			//document.location.href = "hostelDisciplinaryDetails.do?method=initDisciplinaryDetails";
	}
</script>
<html:form action="/hostelDisciplinaryDetails" method="post">
<html:hidden property="formName" value="hostelDisciplinaryDetailsForm" />
<html:hidden property="id" styleId="id" />
<html:hidden property="pageType" value="1" />
<html:hidden property="method" styleId="method" value=""/>
<html:hidden property="disciplineId" styleId="disciplineId" name="hostelDisciplinaryDetailsForm"/>
<html:hidden property="registerNo" styleId="registerNo" name="hostelDisciplinaryDetailsForm"/>
<html:hidden property="description" styleId="description" name="hostelDisciplinaryDetailsForm"/>
<html:hidden property="date" styleId="date" name="hostelDisciplinaryDetailsForm"/>
	
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
											<td  width="20%" class="row-odd" align="right"><bean:message key="knowledgepro.admin.disciplinary.type.name" />:</td>
											<td width="30%" height="25" class="row-even">
													<div align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="disciplineName"/>
													</div>
											</td>
											<td width="20%" height="25" class="row-odd"><div align="right">
												<bean:message key="knowledgepro.fee.academicyear.col"/></div></td>
												<td width="30%" class="row-even">
													<bean:write name="hostelDisciplinaryDetailsForm" property="year"/>
												</td>
													</tr>
													<tr>
												<td  width="20%" height="25" class="row-odd"><div align="right">
												<bean:message key="knowledgepro.hostel.reservation.registerNo" /></div></td>
												<td  width="20%" class="row-even">
													<bean:write name="hostelDisciplinaryDetailsForm" property="registerNo"/>
												</td>
												<td class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.template.Date"/>:</div></td>
												<td class="row-even" width="25%" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="date"/>
												</td>
												</tr>
												<tr>
													<td width="18%" height="25" class="row-odd" align="right">
													Student Name:
													</td>
													<td width="26%" height="25" class="row-even" align="left" colspan="3">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentName"/>
													</td>
												</tr>
												<tr>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 215px; height: 25">
													Class:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentClass"/>
													</td>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 215px; height: 25">
													Hostel:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentHostel"/>
													</td>
												</tr>
												<tr>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Room Number:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentRoomNo"/>
													</td>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Bed Number:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentBedNo"/>
													</td>
												</tr>
												<tr>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Block:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentBlock"/>
													</td>
													<td width="18%" height="25" class="row-odd" align="right">
													<div align="right" style="width: 200px; height: 25">
													Unit:</div>
													</td>
													<td width="26%" height="25" class="row-even" align="left">
													<bean:write name="hostelDisciplinaryDetailsForm" property="studentUnit"/>
													</td>
												</tr>
												<tr>
												<td  width="20%" height="25" class="row-odd" align="right"><span class="Mandatory">*</span>&nbsp;<bean:message key="knowledgepro.admin.desc.with.col" /></td>
												<td  colspan="3" width="80%" class="row-even">
												<bean:write name="hostelDisciplinaryDetailsForm" property="description"/>
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
									<td width="49%" height="35">									
										<div align="center">
											<html:button property="" styleClass="formbutton" onclick="cancell()">
											<bean:message key="knowledgepro.cancel" /></html:button>
										</div>									
									</td>
								</tr>
							</table>
						</td>
						<td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
					</tr>
					
				
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
