<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="css/styles.css"/>
	<link rel="stylesheet" type="text/css" href="css/sdmenu.css"/>
    <script type="text/javascript" src="js/AC_RunActiveContent.js"></script>
    <script type="text/javascript" src="js/sdmenu/sdmenu.js"></script>
    <script type="text/javascript" src="js/ajax/Ajax.js"></script>
    <script type="text/javascript" src="js/ajax/AjaxUtil.js"></script>
    <script type="text/javascript" src="js/leftSwitchMenu.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<%@page import="com.kp.cms.constants.CMSConstants"%>
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function winOpen(attendanceID, attendanceTypeID, subjectId, studentId, classesAbsent,attendanceTypeName) {
		var url = "studentWiseAttendanceSummary.do?method=getAbsencePeriodDetails&attendanceID="
			+ attendanceID
			+ "&attendanceTypeId="
			+ attendanceTypeID
			+ "&subjectId="
			+ subjectId
		    + "&studentID=" 
		    + studentId
		    + "&classesAbsent="
		    + classesAbsent
			+ "&attendanceTypeName="
			+ attendanceTypeName
		    ;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}

	function activityOpens(activityId, studentId, classesAbsent,attendanceTypeName) {
		var url = "studentWiseAttendanceSummary.do?method=getActivityAbsencePeriodDetails&activityId="
			+ activityId
			+ "&studentID="
			+ studentId
		    + "&classesAbsent="
		    + classesAbsent
		    +"&attendanceTypeName="
		    + attendanceTypeName;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
	
	<script type="text/javascript">

function winOpenAm(am) {
	//alert(am);
	var url = "studentWiseAttendanceSummary.do?method=getAmAbsenceDetails&am="+am;
	myRef = window.open(url,"StudentAbsencePeriodDetails","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}	

function winOpenPm(pm) {
	//alert(pm);
	var url = "studentWiseAttendanceSummary.do?method=getPmAbsenceDetails&pm="+pm;
	myRef = window.open(url,"StudentAbsencePeriodDetails","left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<html:form action="/studentWiseAttendanceSummary" >

<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/st_Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/st_Tcenter.gif" class="body"><strong
						class="boxheader">SessionWise Attendance</strong></td>

					<td width="10"><img src="images/st_Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="914" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" >
								<tr>
									<td height="25" width="7%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.slno"/></div>
									</td>
									<td height="25" width="40%" class="studentrow-odd">Session </td>
									<td width="10%" height="25" class="row-white" align="center">
									 <table width="100%" cellspacing="1" cellpadding="5" border="0">
									<tr>
									<td height="25" class="studentrow-odd" width="20%">
									<div align="center"><bean:message key="knowledgepro.attendance.conducted"/> </div>
									</td>
									<td height="25" class="studentrow-odd" width="20%">
									<div align="center"><bean:message key="knowledgepro.attendance.present"/> </div>
									</td>
									<td height="25" class="studentrow-odd" width="20%">
									<div align="center"><bean:message key="knowledgepro.attendance.absent"/> </div>
									</td>
									<td height="25" class="studentrow-odd" width="20%">
									<div align="center"><bean:message key="knowledgepro.admission.totalmarks"/> </div>
									</td>
									</tr>
									</table>
									</td>
								</tr>
							
							   
								 
											<tr>
												<td width="7%" height="25" class="row-white">
												<div align="center">1</div>
												</td>
												<td width="40%" height="25" class="row-white"><bean:write
													name="studentWiseAttendanceSummaryForm" property="am" /></td>
												
												<td width="10%" height="25" class="row-white" align="center" >
												 <table width="100%" cellspacing="1" cellpadding="5" border="0">
												 <tr>
												<td width="20%" height="25" class="row-white" align="center"><bean:write
													name="studentWiseAttendanceSummaryForm" property="totamattcon" /></td>
												<td width="20%" height="25" class="row-white" align="center"><bean:write
													name="studentWiseAttendanceSummaryForm" property="totamattpre" /></td>
												<td width="20%" height="25" class="row-white" align="center">
													<A	HREF="javascript:winOpenAm('<bean:write name="studentWiseAttendanceSummaryForm" property="am" />');">
														<bean:write	name="studentWiseAttendanceSummaryForm" property="totamattabs" /></A>
												
												</td>
												<td width="20%" height="25" class="row-white" align="center"><bean:write
													name="studentWiseAttendanceSummaryForm" property="totamattper" /></td>
												</tr>
												
												</table>												
												</td>													
												</tr>
											
                              
										<tr>
											<td width="7%" height="25" class="studentrow-even">
												<div align="center">2</div>
												</td>
												<td width="40%" height="25" class="studentrow-even" align="left"><bean:write
													name="studentWiseAttendanceSummaryForm" property="pm" /></td>
											
												<td width="10%" height="25" class="studentrow-even" align="center" colspan="5"> 
												<table width="100%" cellspacing="1" cellpadding="5" border="0">
												 
												<tr>
												<td width="20%" height="25" class="studentrow-even" align="center"><bean:write
													name="studentWiseAttendanceSummaryForm" property="totpmattcon" /></td>
												<td width="20%" height="25" class="studentrow-even" align="center"><bean:write
													name="studentWiseAttendanceSummaryForm" property="totpmattpre" /></td>
												<td width="20%" height="25" class="studentrow-even" align="center">
												
													<A	HREF="javascript:winOpenPm('<bean:write name="studentWiseAttendanceSummaryForm" property="pm" />');">
														<bean:write	name="studentWiseAttendanceSummaryForm" property="totpmattabs" /></A>
												
												
											    </td>
												<td width="20%" height="25" class="studentrow-even" align="center">	
												<bean:write	name="studentWiseAttendanceSummaryForm" property="totpmattper" />
												</td>
												</tr>
												
												</table></td>												
										</tr>
									
									
									
								
								<tr>
									<td height="25"  colspan="2" class="studentrow-odd">
									<div align="center">Total </div>
									</td>
									<td  width="10%" height="25" align="center"  colspan="5" class = "studentrow-odd">
									 <table width="100%" height="100%" cellspacing="1" cellpadding="5" border="0" class="studentrow-odd">
									<tr>
											<!--<td  width="10%" class="studentrow-odd"> </td>
											--><td  class="studentrow-odd" width="20%">
											<div align=""><bean:write name="studentWiseAttendanceSummaryForm" property="totalConducted"/></div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align=""> <bean:write name="studentWiseAttendanceSummaryForm" property="totalPresent"/></div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align=""><bean:write name="studentWiseAttendanceSummaryForm" property="totalAbscent"/> </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td height="25"  colspan="2" class="studentrow-odd">
									<div align="center">Total Percentage</div>
									</td>
									<td  width="10%" height="25" align="center"  colspan="5" class = "studentrow-odd">
									 <table width="100%" height="100%" cellspacing="1" cellpadding="5" border="0" class="studentrow-odd">
									<tr>
											<!--<td  width="10%" class="studentrow-odd"> </td>
											--><td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
											<td  class="studentrow-odd" width="20%">
											<div align=""><bean:write name="studentWiseAttendanceSummaryForm" property="totalPercentage"/> </div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
									</table>
					<table align="center">
					<% if(!CMSConstants.LINK_FOR_CJC){ %>
						<tr>
						<td colspan="2" class="heading">
							<bean:message key="knowledgepro.attendance.studentLogin.percentage"/>						
						</td>
						</tr>
						<%} %>
						<tr>
							<td class="heading">
								<br/>
								<bean:message key="knowledgepro.show.attendance.message"/>
							</td>
						</tr>
						<tr>
							<td class="heading">
								<bean:message key="knowledgepro.show.attendance.totalmessage"/>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><html:button  property=""
								styleClass="btnbg" value="Cancel" onclick="cancelAction()" /></td>
						</tr>
					</table>
							
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
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td valign="top" class="news">&nbsp;</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/st_Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/st_TcenterD.gif"></td>
					<td><img src="images/st_Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="bg_img.gif">
  <tr>
    <td height="32" align="center" class="copyright">Copyrights @ 2009 Knowledge Pro All rights reserved. </td>
  </tr>
</table>
</html:form>
