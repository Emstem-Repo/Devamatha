<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" >
function goToHomePage() {
	document.location.href = "studentCarPass.do?method=initStudentCarPass";
}
</script>
<style type="text/css">
.tableBorder{
    border:solid 2px #100B0B;
    -webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 5px;
}
</style>
<html:form action="/studentCarPass" >
	<html:hidden property="method" styleId="method" value="" />	
	<html:hidden property="pageType" value="3" />
	<html:hidden property="formName" value="studentCarPassForm" />
	<table width="98%" border="0">
	<tr height="80"></tr>
		<tr>
		  <td width="5%"></td>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableBorder">
						
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>
							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td width="100%" valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" class="row-white">
							<tr><td align="left" width="20%"><table width="40%"><tr><td width="20%"><font size="2"><b>No.</b></font></td><td class="row-print" width="80%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.registrationNo"/></b></font>
							</td></tr></table></td>
							</tr>
							<tr>
							   <td width="30%"><table width="70%"><tr><td width="30%" align="left">
							  Christ University, Hosur Road,</td></tr>
                               <tr><td width="30%" align="left">Bangalore - 560 029, Karnataka, India</td></tr>
                               <tr><td width="30%" align="left"> Tel: +91 80 4012 9100 / 9600</td></tr>
                               <tr><td width="30%" align="left">Email: mail@christuniversity.in</td></tr>
                               <tr><td width="30%" align="left">Website: www.christuniversity.in</td></tr>
							   </table></td>
								<td width="70%" align="center">
								<img src='<%=CMSConstants.LOGO_URL%>'  height="100" width="210" />
								</td>
							</tr>
							<tr height="2"></tr>
							<tr height="50px">
								<td  align="center" width="80%" colspan="2" class="tableBorder"> <b> APPLICATION FOR CAR PASS - 2013 - 2014</b> </td>
							</tr>
							<tr height="10"></tr>
							<tr>
							<td width="70%"><table width="100%">
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Name  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.studentName"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Register No.  : </b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.registerNo"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Course  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.course"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Date of Birth  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.dateOfBirth"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Father Name  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.fatherName"/></b></font></td>
							</tr>
							</table>
							</td>
							<td width="30%" align="center">
								<img src='<%=session.getAttribute("STUDENT_IMAGE")%>'  height="128" width="133" />
								</td>
							</tr>
							<tr height="10"></tr>
							<tr><td width="70%">
							<table width="100%">
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Address  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.address"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Pin Code  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.pinCode"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Emergency Contact Number  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.emergencyContactNo"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Vehicle Model  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.modelOfVehicle"/></b></font></td>
							</tr>
							<tr height="5"></tr>
							<tr>
								<td class="row-print" width="50%" align="left"><font size="2"><b>Vehicle No.  :</b></font></td>
								<td class="row-print" width="50%" align="left"><font size="2"><b><bean:write name="studentCarPassForm" property="studentcarPassTo.vehicleNumber"/></b></font></td>
							</tr>
							</table>
							</td><td width="30%"></td></tr>
							<tr height="20"></tr>
							<tr>
							<td width="100%" colspan="2">
							<table class="tableBorder" width="100%">
							<tr>
							<td width="100%" colspan="2"><font size="2">I hereby undertake to abide by the parking regulations of the Institution and will solely be responsible for any accident caused inside</font></td>
							</tr>
							<tr>
							<td width="100%" colspan="2"><font size="2">the campus.</font></td>
							</tr>
							<tr><td width="100%" colspan="2"><font size="2"> I will follow the speed regulations and will be careful while driving.I will not bring any outsiders in my vehicle to the Institution.</font></td></tr>
							</table></td></tr>
							<tr height="60"></tr>
							<tr><td colspan="2" width="100%" class="row-print"><font size="2"><b>Signature of the Parent / Guardian</b></font></td></tr>
							<tr height="20"></tr>
							<tr><td colspan="2" width="100%" class="row-print"><font size="2"><b>Date :</b></font></td></tr>
							<tr height="30"></tr>
							<tr><td width="100%" colspan="2">N.B :</td></tr>
							<tr><td width="100%" colspan="2">1) Only Vehicles registered with University will be allowed to park in the Campus.</td></tr>
							<tr><td width="100%" colspan="2">2) Car Pass validity is from June 2013 to March 2014 only. </td></tr>
							<tr><td width="100%" colspan="2">3) Amount for the pass is Rs. 7500/-.</td></tr>
							<tr><td width="100%" colspan="2">4) Kindly print this form and submit to IPM with the required signature while collecting the pass.</td></tr>
							<tr><td width="100%" colspan="2">5) Passes can be collected within one working day after the online submission of the form.</td></tr>
							<tr><td width="100%" colspan="2">6) The amount can be remitted while collecting the pass.</td></tr>
							
							
							<!--<tr height="21px">
							<td colspan="2"  class="row-print-desc">
							<logic:notEmpty name="loginform" property="description1">
							<font size="6px">
								<c:out value="${loginform.description1}" escapeXml="false"></c:out>
							</font>
							</logic:notEmpty>
							</td>
							</tr>
							<tr>
							<td colspan="2" align="right">
								<img src="images/COEFinal.jpg" width="157px" height="72px" />
							</td>
							</tr>
							--></table>
							</td>
							<td width="5" height="30" background="images/st_right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/st_04.gif" width="5" height="5" /></td>
							<td background="images/st_05.gif"></td>
							<td><img src="images/st_06.gif" /></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
			<td width="5%"></td>
		</tr>
	</table>
</html:form>
<script type="text/javascript">window.print();</script>