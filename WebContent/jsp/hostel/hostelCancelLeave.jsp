<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">

function editStudentLeave(id){
	document.location.href = "hostelLeave.do?method=editStudentLeaveDetails&id="+id;
}
function cancelStudentLeave(id) {
	document.location.href = "hostelLeave.do?method=cancelStudentLeaveDetails&id="+id;
}
function winOpen(userId) {
	document.location.href = "hostelLeave.do?method=reasonForCancelLeave&id="+userId;
}
function setAcademicYear(year)
{
document.getElementById("tempAcademicYear").value=year;	
}
</script>
<html:form action="/hostelLeave" method="post">
<html:hidden property="formName" value="hostelLeaveForm" />
<html:hidden property="pageType" value="2" />
<html:hidden property="method" styleId="method"
		value="studentLeaveDetails" />

<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin.sec.EmployeeCategory" />
			<span class="Bredcrumbs">&gt;&gt; Cancel Hostel  Leave &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Cancel Hostel  Leave</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2"><tr><td width="34%" class="row-even">
            <table width="100%" cellspacing="1" cellpadding="2">
			<tr>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">* </span> <bean:message
										key="knowledgepro.admin.year" /></div>
									</td>
									<td width="30%" height="25" class="row-even">
									<input type="hidden" id="tempAcademicYear" name="tempAcademicYear" value="<bean:write name="hostelLeaveForm" property="academicYear1"/>" />
									<html:select
										property="academicYear1" styleClass="combo"
										styleId="academicYear" name="hostelLeaveForm" onchange="setAcademicYear(this.value)">
										<html:option value="">
											<bean:message key="knowledgepro.admin.select" />
										</html:option>
										<cms:renderAcademicYear></cms:renderAcademicYear>
									</html:select>
									</td>
									<td width="20%" height="25" class="row-odd">
									<div align="right"><span class="Mandatory">*</span> <bean:message
										key="knowledgepro.hostel.student.regNo" /></div>
									</td>
									<td width="30%" height="25" class="row-even"><html:text
										name="hostelLeaveForm" property="registerNo" styleId="regid"
										size="10" maxlength="10"
										onkeypress="return isNumberKey(event)" />
								</tr>	
            
            </table></td>
                  </tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right">
									<html:submit property="" styleId="button" styleClass="formbutton" value="Search"
										></html:submit>
										
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Reset"
										onclick="resetFieldAndErrMsgs()"></html:button>
							</div>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>
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
									<td align="center" height="25" class="row-odd">
									<div align="center">Student Name</div>
									</td>
									<td align="center" height="25" class="row-odd">
									<div align="center">Hostel</div>
									</td>
									<td class="row-odd">
									<div align="center">Leave From</div>
									</td>
									<td class="row-odd">
									<div align="center">Leave To</div>
									</td>
									<td class="row-odd">
									<div align="center">Leave Type</div>
									</td>
									<td class="row-odd">
									<div align="center">Status</div>
									</td>
									<td class="row-odd">
									<div align="center">Edit</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message key="knowledgepro.cancel" /></div>
									</td>
								</tr>
								<logic:notEmpty name="hostelLeaveForm" property="hostelList">
								<logic:iterate id="hostelId" name="hostelLeaveForm" property="hostelList" indexId="count">
										<c:choose>
											<c:when test="${count%2 == 0}">
												<tr class="row-even">
											</c:when>
											<c:otherwise>
												<tr class="row-white">
											</c:otherwise>
										</c:choose>
										<td width="4%" height="25">
										<div align="center"><c:out value="${count + 1}" /></div>
										</td>
										<td align="center" width="20%" height="25"><bean:write
											name="hostelId" property="name" /></td>
										<td align="center" width="20%" height="25"><bean:write
											name="hostelId" property="hostelName" /></td>
										<td align="center" width="15%" height="25"><bean:write
											name="hostelId" property="leaveFrom" /></td>
										<td align="center" width="15%" height="25"><bean:write
											name="hostelId" property="leaveTo" /></td>
										<td align="center" width="15%" height="25"><bean:write
											name="hostelId" property="leaveType" /></td>
										<td align="center" width="15%" height="25"><bean:write
											name="hostelId" property="status" /></td>
										<td width="10%" height="25">
										<div align="center"><img src="images/edit_icon.gif"
											width="16" height="18" style="cursor: pointer"
											onclick="editStudentLeave('<bean:write name="hostelId" property="id" />')" /></div>
										</td>
										<td width="10%" height="25">
										<div align="center"><img src="images/cancel_icon.gif"
											width="14" height="14" style="cursor: pointer"
											onclick="winOpen('<bean:write name="hostelId" property="id" />')" /></div>
										</td>
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
				<tr>
					<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news"></td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
      
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>
<script type="text/javascript">
	var yearId = document.getElementById("tempAcademicYear").value;
	if (yearId != null && yearId.length != 0) {
		document.getElementById("academicYear").value = yearId;
	}
</script>