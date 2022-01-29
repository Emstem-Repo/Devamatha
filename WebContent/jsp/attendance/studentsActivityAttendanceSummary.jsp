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
	<script>
	function cancelAction() {
		document.location.href = "StudentLoginAction.do?method=returnHomePage";
	}
	function winOpen(activityId, studentId, classesAbsent) {
		var url = "studentWiseAttendanceSummary.do?method=getActivityAbsencePeriodDetails&activityId="
			+ activityId
			+ "&studentID="
			+ studentId
		    + "&classesAbsent="
		    + classesAbsent;
		myRef = window
				.open(url,"StudentAbsencePeriodDetails",
						"left=20,top=20,width=500,height=500,toolbar=1,resizable=0,scrollbars=1");
	}
	</script>
<html:form action="/studentWiseAttendanceSummary" >

<html:hidden property="formName" value="studentWiseAttendanceSummaryForm"/>
<html:hidden property="pageType" value="1"/>

<table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="172">&nbsp;</td>
    <td width="37">&nbsp;</td>
    <td width="269">&nbsp;</td>
    <td width="30">&nbsp;</td>
    <td width="422">&nbsp;</td>
  </tr>
  <tr>
    <td valign="top"><!--<table width="170" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="180" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" class="nav"><bean:message key="knowledgepro.studentlogin.navigation"/> </td>
                <td width="13" class="tr">&nbsp;</td>
              </tr>
               <tr>
                    <td width="13" class="le">&nbsp;</td>
                    <td class=""><table border="0" cellspacing="0" cellpadding="0">                      
                        <tr>
                           <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                          <td width="137" height="21" class="navmenu"><a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance"/> </a> </td>
                          </tr>
                          <tr>
                          <td width="35" height="21" align="center"><img src="images/bullet_img.gif" width="14" height="9" /></td>
                          <td width="137" height="21" class="navmenu"><a href="studentWiseAttendanceSummary.do?method=getIndividualStudentWiseActivityAttendanceSummary" class="navmenu"><bean:message key="knowledgepro.attendance.activity.attendance"/></a></td>
                       </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                         <tr>
                          <td width="35" height="21" align="center">&nbsp;</td>
                          <td height="21" class="navmenu">&nbsp;</td>
                        </tr>
                    </table></td>
                    <td class="ri">&nbsp;</td>
                  </tr>
              <tr>
                <td class="bl">&nbsp;</td>
                <td class="bm">&nbsp;</td>
                <td class="br">&nbsp;</td>
              </tr>
          </table></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td height="30" valign="bottom"><p>&nbsp;</p>
          <p>&nbsp;</p>
          <p><img src="bullet_imge.gif" width="170" height="8" /></p></td>
        </tr>
      </table>--></td>
    <td width="37"><p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p></td>
    <td colspan="3" valign="top"><table border="0" width="600" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="2" class="nav"><bean:message key="knowledgepro.attendance"/> </td>
          <td width="220" class="tr">&nbsp;</td>
        </tr>
        <tr>&nbsp;&nbsp;</tr>
        <tr>
          <td width="13" class="le">&nbsp;</td>
          <td width="383">
          <table width="100%" cellspacing="1" cellpadding="2" >
								<tr>
									<td height="25" width="40%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.attendance.activityattendence.activitytype"/> </div>
									</td>
									<td height="25" width="15%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.attendance.conducted"/></div> </td>
									<td height="25" width="15%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.attendance.present"/></div> </td>
									<td height="25" width="15%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.attendance.absent"/></div> </td>
									<td height="25" width="15%" class="studentrow-odd">
									<div align="center"><bean:message key="knowledgepro.admission.totalmarks"/></div> </td>
								</tr>
							<c:set var="temp" value="0"/>
							<logic:notEmpty name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList">
							<logic:iterate name="studentWiseAttendanceSummaryForm" property="subjectwiseAttendanceTOList"
									id="id" indexId="count">
                               <c:choose>
								 <c:when test="${temp == 0}">
											<tr>
												<td align="center" width="40%" height="25" class="row-white">
												<div align="center"><bean:write
													name="id" property="activityName" /></div>
												</td>
												<td align="center" width="15%" height="25" class="row-white"><bean:write
													name="id" property="conductedClasses" /></td>
												<td align="center" width="15%" height="25" class="row-white"><bean:write
													name="id" property="classesPresent" /></td>
													<td align="center" width="15%" height="25" class="row-white">
													<A HREF="javascript:winOpen('<bean:write name="id" property="activityID" />','<bean:write name="id" property="studentId" />','<bean:write name="id" property="classesAbsent" />');">
													<bean:write
													name="id" property="classesAbsent" /></A> </td>
													<td align="center" width="15%" height="25" class="row-white"><bean:write
													name="id" property="percentageWithoutLeave" /></td>													
												</tr>
											<c:set var="temp" value="1"/>
                                </c:when>
									<c:otherwise>
										<tr>
											<td align="center" width="40%" height="25" class="studentrow-even">
												<div align="center"><bean:write
													name="id" property="activityName" /></div>
												</td>
												<td align="center" width="15%" height="25" class="studentrow-even"><bean:write
													name="id" property="conductedClasses" /></td>
											<td align="center" width="15%" height="25" class="studentrow-even"><bean:write
													name="id" property="classesPresent" /></td>
											<td align="center" width="15%" height="25" class="studentrow-even">
											<A HREF="javascript:winOpen('<bean:write name="id" property="activityID" />','<bean:write name="id" property="studentId" />','<bean:write name="id" property="classesAbsent" />');">
											<bean:write
													name="id" property="classesAbsent" /></A> </td>
											<td align="center" width="15%" height="25" class="studentrow-even"><bean:write
													name="id" property="percentageWithoutLeave" /></td>	
										</tr>
										<c:set var="temp" value="0" />
									</c:otherwise>
									</c:choose>
								</logic:iterate> 
								</logic:notEmpty>
									</table>
					<table align="center">
						<tr>
							<td colspan="2" align="center"><html:button  property=""
								styleClass="btnbg" value="Cancel" onclick="cancelAction()" /></td>
						</tr>
					</table>
					</td>
          <td width="220" class="ri">&nbsp;</td>
        </tr>
        <tr>
          <td class="bl">&nbsp;</td>
          <td class="bm">&nbsp;</td>
          <td class="br">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top">&nbsp;</td>
    <td>&nbsp;</td>
    <td valign="top">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="right" valign="top" >&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" background="bg_img.gif">
  <tr>
    <td height="32" align="center" class="copyright">Copyrights @ 2009 Knowledge Pro All rights reserved. </td>
  </tr>
</table>
</html:form>
