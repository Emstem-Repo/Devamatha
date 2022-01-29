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
	<style>

#tdId{
	font-size:10px;
	font-weight: normal;
}
#tdIds{
	font-size:11px;
	font-weight: bold;
}
#tdIdb{
	font-size:10px;
	font-weight: bold;
}
</style>
<html:form action="/studentWiseAttendanceSummary" >
<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%" align="center">
					<div><strong>ATTENDANCE FOR CLASS <bean:write property="className" name="studentWiseAttendanceSummaryForm"/></strong></div>
					<br></br>
					<div align="left"><strong>Register No:</strong> <bean:write property="regNo" name="studentWiseAttendanceSummaryForm"/></div>
					<div align="left"><strong>Name : </strong><bean:write property="studentName" name="studentWiseAttendanceSummaryForm"/></div>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr height="20"></tr>
				<tr height="10%">
					<td height="20" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" height="10%"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				
				<tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td><img src="images/st_01.gif" width="5" height="5" /></td>
							<td width="100%" background="images/st_02.gif"></td>

							<td><img src="images/st_03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/st_left.gif"></td>
							<td valign="top">
							<table width="100%" cellspacing="1" cellpadding="2" style="border: 1px solid black; " rules="all">
								<tr>
									<td width="5%" class="row-print" id="tdIdb">
									<div align="center"><bean:message key="knowledgepro.studentlogin.attendance.slno"/></div>
									</td>
									<td width="40%" class="row-print" id="tdIdb"> <bean:message key="knowledgepro.admisn.subject.Name"/> </td>
									<td width="55%" align="center" class = "row-print">
									 <table width="100%" cellspacing="0" cellpadding="0" rules="cols">
										 <tr>
											<td height="28" align="center" width="23%" class="row-print" id="tdIdb"><bean:message key="knowledgepro.attendance.type"/> </td>
											<td align="center" class="row-print" width="20%" id="tdIdb">
											<bean:message key="knowledgepro.attendance.conducted"/></td>
											<td align="center" class="row-print" width="17%" id="tdIdb">
											<bean:message key="knowledgepro.attendance.present"/></td>
											<td align="center" class="row-print" width="17%" id="tdIdb">
											<bean:message key="knowledgepro.attendance.absent"/></td>
											<td align="center" class="row-print" width="23%" id="tdIdb">
											<bean:message key="knowledgepro.admission.totalmarks"/></td>
										</tr>
									</table>
									</td>
								</tr>
								
							<c:set var="temp" value="0"/>
							<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList">
							<logic:iterate name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList"
									id="id" indexId="count">
									
									<c:choose>
										<c:when test="${count%2 == 0}">
											<tr class="row-print">
										</c:when>
										<c:otherwise>
											<tr class="row-print">
										</c:otherwise>
									</c:choose>
									
									<td width="5%" id="tdId">
									<div align="center"><c:out value="${count+1}" /></div>
									</td>
									<td align="left" width="23%" id="tdId"><bean:write name="id" property="subjectName" /></td>
									  <td width="55%" align="center" >
									 <table width="100%" cellspacing="0" cellpadding="0" rules="cols">
							 <logic:notEmpty name="id" property="attendanceTypeList">	
								<logic:iterate name="id" id="type" property="attendanceTypeList">
									<tr>
									<td height="28" align="center" width="23%" id="tdId">
									<bean:write	name="type" property="attendanceTypeName" />
									</td>
									<td align="center" width="20%" id="tdId">
									<bean:write name="type" property="conductedClasses" /></td>
									<td align="center" width="17%" id="tdId">
									<bean:write name="type" property="classesPresent" /></td>
									<td align="center" width="17%" id="tdId">
									<logic:equal name="type" property="flag" value="false">
									<bean:write	name="type" property="classesAbsent" />
									</logic:equal>
									<logic:equal name="type" property="flag" value="true">
									<bean:write name="type" property="classesAbsent" />
									</logic:equal>
									</td>
									<td align="center" width="23%" id="tdId"><bean:write
										name="type" property="percentage" /></td>
									</tr>
									</logic:iterate>
									</logic:notEmpty>
									</table>												
									</td>
								</logic:iterate> 
								</logic:notEmpty>
								<tr>
									<td height="25%" colspan="2" class="row-print" id="tdIds">
									<div align="center">Total</div>
									</td>
									<td width="50%" height="25" align="center"  colspan="5" class = "row-print">
									 <table width="100%" cellspacing="0" cellpadding="0" border="0" >
										 <tr>
											<td  width="20%" class="row-print"> </td>
											<td  class="row-print" width="20%" id="tdIds">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentWiseAttendanceSummaryForm" property="totalConducted"/></div>
											</td>
											<td  class="row-print" width="20%" id="tdIds">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="studentWiseAttendanceSummaryForm" property="totalPresent"/> </div>
											</td>
											<td  class="row-print" width="20%" id="tdIds">
											<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalAbscent"/> </div>
											</td>
											<td  class="row-print" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
								<tr>
									<td height="25%" colspan="2" class="row-print" id="tdIds">
									<div align="center">Total Percentage</div>
									</td>
									<td width="50%" height="25" align="center"  colspan="5" class = "row-print">
									 <table width="100%" cellspacing="0" cellpadding="0" border="0" >
										 <tr>
											<td  width="20%" class="row-print"> </td>
											<td  class="row-print" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
											<td  class="row-print" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
											<td  class="row-print" width="20%">
											<div align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
											</td>
											<td  class="row-print" width="20%" id="tdIds">
											<div align="center"><bean:write name="studentWiseAttendanceSummaryForm" property="totalPercentage"/> </div>
											</td>
										</tr>
									</table>
									</td>
								</tr>
									</table>
					<table align="center">
					<tr height="20"></tr>
					<tr>
							<td class="heading" id="tdIds">
											<bean:message key="knowledgepro.show.attendance.totalmessage"/>
										</td>
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
<script type="text/javascript">window.print();</script>