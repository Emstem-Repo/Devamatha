<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<head>
<title>:: CMS ::</title>
<script language="JavaScript" src="js/calendarinterview.js"></script>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/calendar.css">
<style type="text/css">

body {
	margin-left: 20px;
	margin-top: 30px;
	margin-right: 10px;
	margin-bottom: 30px;
}

</style>
<style>
checkprint
{
table {page-break-after:always}
}
</style>
</head>
<script type="text/javascript">
function printMe()
{
	window.print();
}
function closeMe()
{
	window.close();
}
</script>

<html:form action="/HostelAdmission" method="post">
	<html:hidden property="formName" value="hlAdmissionForm" />
	
	<table height="28" width="100%" border="1" bordercolor="black" rules='all' >
		<tr>
			<td valign="top">
				<table width="100%">
              		<tr height="30px"> 
              			<td width="70%">
              				<table>
              					<tr>
              						<td></td>
              						<td width="30%">
			              				<table  border="1" bordercolor="black" rules='all' >
			              					<tr>
			              						<td align="center"><font style="text-transform:uppercase; font-size: large ;font-weight: bold">
			              							&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.applNo"/>&nbsp;</font>
			              						</td>
			              					</tr>
			              				</table>
			              			</td>
			              			<td width="40%" align="center">
			              			<font style="text-transform:uppercase; font-size: large ;font-weight: bold">
			              				&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.hostelName"/></font>
			              			</td>
              					</tr>
              					<tr height="10px"></tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">First Name</font>	</td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.studentName"/></td>              			
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Program</font>	</td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.program"/></td>
			              			
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Application/Register No</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.regNo"/></td>              			
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Date of Birth</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.dateOfBirth"/></td>
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Religion</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.religion"/></td>
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Mobile No</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.mobNO"/></td>
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Email</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.email"/></td>
			              		</tr>
			              		<tr height="35px">
			              			<td width="5%"></td>
			              			<td><font style="font-weight: bold">Preference for Room Style</font></td><td align="left">: &nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.roomTypeName"/></td>
			              		</tr>
              				</table>
              			</td>
              			<td width="30%">
              				<table>
              					<tr>
              					
              						<td colspan="2" width="30%" align="center">
			              				<img src="images/StudentPhotos/<bean:write name="hlAdmissionForm" property="printData.studentId"/>.jpg?<%=System.currentTimeMillis() %>" alt="Photo not available" height="150Px" width="150Px">
			              			</td>
              					</tr>
              				</table>
              			</td>
              		</tr>
              		<tr><td height="5"></td> </tr>
              		<tr height="30px">
              			<td colspan="2">
              				<table>
              					<tr>
              						<td width="4%"></td>
              						<td>
              							<table  border="1" bordercolor="black" rules='all' >
			              					<tr>
			              						<td>
			              							<table width="100%">
			              								<tr>
			              									<td width="3%"></td>
						              						<td align="center"><font style="font-weight: bold">
						              							Name and Address of the Parent </font>
						              						</td>
						              						<td width="5%"></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.parentAdd1"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.parentAdd2"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.parentAdd3"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.parentAdd4"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">
						              							&nbsp;&nbsp;&nbsp;&nbsp;Tel.NO: <bean:write name="hlAdmissionForm" property="printData.parentTelNO"/>
						              						</td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">
						              							&nbsp;&nbsp;&nbsp;&nbsp;Mobile: <bean:write name="hlAdmissionForm" property="printData.parentMobNO"/>
						              						</td>
						              					</tr>
						              					<tr height="5px">
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              					</tr>
			              							</table>
			              						</td>
			              						<td>
			              							<table width="100%">
			              								<tr>
			              									<td width="3%"></td>
						              						<td align="center"><font style="font-weight: bold">
						              							Name and Address of the Guardian</font>
						              						</td>
						              						<td width="5%"></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.currentAdd1"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.currentAdd2"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.currentAdd3"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="hlAdmissionForm" property="printData.currentAdd4"/></td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">
						              							&nbsp;&nbsp;&nbsp;&nbsp;Tel.NO: <bean:write name="hlAdmissionForm" property="printData.currentTelNO"/>
						              						</td>
						              					</tr>
						              					<tr height="25px">
						              						<td colspan="3">
						              							&nbsp;&nbsp;&nbsp;&nbsp;Mobile: <bean:write name="hlAdmissionForm" property="printData.currentMobNO"/>
						              						</td>
						              					</tr>
						              					<tr height="10px">
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						              					</tr>
			              							</table>
			              						</td>
			              					</tr>
			              				</table>
              						</td>
              					</tr>
              				</table>
              			</td>
              		</tr>
              		<tr height="10"></tr>
              		<tr height="100px">
              			<td colspan="2">
              				<table>
              					<tr>
              						<td width="4%"></td>
              						<td>
              							<table  border="1" bordercolor="black" rules='all' >
			              					<tr>
			              						<td colspan="2">
			              							 <font style="font-size: large ;font-weight: bold">For Office use only</font><br/>
			              							 			<br/>
						              						     &nbsp;The above student is ADMITTED to the <b><bean:write name="hlAdmissionForm" property="printData.hostelName"/></b> <br/><br/>
						              						     academic year <bean:write name="hlAdmissionForm" property="programYear"/> in room No. ..................................... &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						              						     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
						              						     <br>
						              						     <br>
						              						     <br>
						              						     Signature of Director, <b><bean:write name="hlAdmissionForm" property="printData.hostelName"/></b>:...............................
			              						</td>
			              					</tr>
			              				</table>
              						</td>
              					</tr>
              				</table>
              			</td>
              		</tr>
              		
			    </table>
			</td>
		</tr>
		<tr height="20"> </tr>
	</table>					
</html:form>
<script type="text/javascript">
	window.print();
</script>